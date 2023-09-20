package com.phor.ngac.core.sql;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.stream.StreamUtil;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.google.common.collect.Lists;
import com.phor.ngac.config.AuthUtils;
import com.phor.ngac.entity.dto.ColumnInfo;
import com.phor.ngac.entity.dto.DatabaseInfo;
import com.phor.ngac.entity.dto.TableInfo;
import com.phor.ngac.exception.SqlParseException;
import com.phor.ngac.service.AuthenticateService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.jdbc.PgConnection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Setter
@Getter
@Component
public class SqlParser {
    private ThreadLocal<Map<String, String>> aliasTableNameMap = new ThreadLocal<>();

    private ThreadLocal<Map<String, String>> tableNameAliasMap = new ThreadLocal<>();

    @Resource
    private AuthenticateService authenticateService;

    private SqlParser() {
    }

    /**
     * sql解析器
     * 暂不支持跨库查询的解析。
     *
     * @param sql        sql语句
     * @param connection 数据库连接
     * @return 解析并完成鉴权后的sql语句
     */
    public String parse(String sql, DruidPooledConnection connection) {
        PlainSelect select;
        try {
            // 分析语句
            select = (PlainSelect) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            throw new SqlParseException("解析sql失败", e);
        }

        // 主表信息
        Table table = (Table) select.getFromItem();
        // 也可以从sqlSessionFactory中获取当前链接的数据库信息作为主表信息
        PgConnection conn = (PgConnection) connection.getConnection();
        String databaseName = conn.getQueryExecutor().getDatabase();
        Database database = table.getDatabase();
        List<Join> joins = Optional.ofNullable(select.getJoins()).orElse(Lists.newArrayList());

        // 设置别名映射和表名映射
        Map<String, String> map = Stream.concat(Stream.of(table),
                        joins.stream().map(Join::getRightItem).filter(Objects::nonNull).map(Table.class::cast))
                .collect(Collectors.toMap(t -> Optional.ofNullable(t.getAlias()).map(Alias::getName).orElse(t.getName()), Table::getName))
                .entrySet().stream()
                .filter(entry -> StringUtils.isNotEmpty(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        log.info("aliasTableNameMap: {}", map);

        aliasTableNameMap.set(map);

        tableNameAliasMap.set(MapUtil.reverse(map));

        // 获取所有涉及的表名
        Collection<TableInfo> tableInfos = getTableInfos(select);

        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setTableInfos(Lists.newArrayList(tableInfos));
        databaseInfo.setDatabaseName(Optional.ofNullable(database.getDatabaseName()).orElse(databaseName));

        String loginUserName = AuthUtils.getLoginUserName();
        // 列权限校验
        List<DatabaseInfo> passColumnList = authenticateService.batchVisitTableColumn(loginUserName, Lists.newArrayList(databaseInfo));
        if (CollectionUtils.isEmpty(passColumnList)) {
            throw new SqlParseException("无权限访问");
        }

        // 重置为空查询列
        select.setSelectItems(Lists.newArrayList());

        // 筛选出有权限访问的列
        List<String> columnList = passColumnList.stream()
                .map(DatabaseInfo::getTableInfos)
                .flatMap(Collection::stream)
                .flatMap(tableInfo -> tableInfo.getColumnInfos().stream().map(ColumnInfo::getSqlColumn)) // 还原成sql中的列名
                .collect(Collectors.toList());

        // 生成新的sql
        for (String columnName : columnList) {
            select.addSelectItems(new Column(columnName));
        }

        // 重置threadLocal
        reset();
        return select.toString();
    }

    private void reset() {
        this.tableNameAliasMap.remove();
        this.aliasTableNameMap.remove();
    }

    private Collection<TableInfo> getTableInfos(PlainSelect select) {
        // 获取所有涉及的列名
        return StreamUtil.of(select.getSelectItems())
                .map(selectItem -> {
                    String columnName = selectItem.getExpression().toString();
                    if (!columnName.contains(".")) {
                        columnName = ((Table) select.getFromItem()).getName() + "." + columnName;
                    }
                    return columnName + "|" + Optional.ofNullable(selectItem.getAlias()).map(Alias::getName).orElse("");
                })
                .map(fullColumn -> {
                    String[] split = fullColumn.split("\\|");
                    String columnName = split[0];

                    String columnAlias = "";
                    if (split.length > 1) {
                        columnAlias = split[1];
                    }
                    // 初始化别名和表名映射
                    AbstractSelectItemHandler selectItemHandler = new DefaultSelectItemHandler();
                    selectItemHandler.setAliasTableNameMap(aliasTableNameMap.get());

                    // 列名解析入口
                    selectItemHandler.handle(this, columnName, columnAlias);

                    String actualTableName = selectItemHandler.getActualTableName();
                    String actualColumnName = selectItemHandler.getActualColumnName();
                    String sqlColumn = selectItemHandler.getSqlColumn();

                    // 组装表信息
                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setTableName(actualTableName);

                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setColumnName(actualColumnName);
                    columnInfo.setSqlColumn(sqlColumn);

                    tableInfo.setColumnInfos(Lists.newArrayList(columnInfo));
                    return tableInfo;
                }).collect(
                        // 按表名分组
                        Collectors.groupingBy(TableInfo::getTableName,
                                Collector.of(TableInfo::new, (o, p) -> {
                                    List<ColumnInfo> columnInfos = o.getColumnInfos();
                                    if (CollectionUtils.isEmpty(columnInfos)) {
                                        o.setTableName(p.getTableName());
                                        o.setColumnInfos(p.getColumnInfos());
                                    } else {
                                        columnInfos.addAll(p.getColumnInfos());
                                    }
                                }, (o, p) -> o)
                        )
                ).values();
    }
}
