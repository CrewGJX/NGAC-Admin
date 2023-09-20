package com.phor.ngac.core.sql;

import cn.hutool.core.stream.StreamUtil;
import com.phor.ngac.entity.dto.ColumnInfo;
import com.phor.ngac.entity.dto.DatabaseInfo;
import com.phor.ngac.entity.dto.TableInfo;
import com.phor.ngac.exception.TableAliasNotDefinedException;
import com.phor.ngac.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
//@SpringBootTest
class TestJSqlParser {
    @Resource
    private AuthenticateService authenticateService;

    @Test
    void testSqlParser() {
        // 示例sql语句
        String sqlStr = "select *\n" +
//        String sqlStr = "select icc.imsi, icc.msisdn, icd.imei\n" +
                "from iop_cmp_card icc\n" +
                "         left join iop_cmp_card_dev iccd on icc.id = iccd.card_id\n" +
                "         left join iop_cmp_dev icd on iccd.dev_id = icd.id\n" +
                "where pn_name = 'L-5G-130-2021-10-1634105593190'\n" +
                "  and icd.imei is not null\n" +
                "  and icd.imei <> '';";

        PlainSelect select = null;
        try {
            // 分析语句
            select = (PlainSelect) CCJSqlParserUtil.parse(sqlStr);
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }

        // 主表信息
        Table table = (Table) select.getFromItem();
        // 也可以从sqlSessionFactory中获取当前链接的数据库信息作为主表信息
        Database database = table.getDatabase();
        List<Join> joins = select.getJoins();

        // 获取所有连表查询的别名映射
        Map<String, String> tableNameAliasMap = Stream.concat(Stream.of(table), joins.stream().map(Join::getRightItem).map(Table.class::cast))
                .collect(Collectors.toMap(t -> t.getAlias().getName(), Table::getName));

        // 获取所有涉及的列名
        Collection<TableInfo> tableInfos = StreamUtil.of(select.getSelectItems())
                .map(selectItem -> selectItem.getExpression().toString())
                .map(columnName -> {
                    if (!columnName.contains(".")) {
                        // 无别名查询，暂无法获取表名
                        throw new TableAliasNotDefinedException(String.format("'%s'无别名查询，暂无法获取表名", columnName));
                    }
                    String[] split = columnName.split("\\.");
                    String actualTableName = tableNameAliasMap.get(split[0]);
                    String actualColumnName = split[1];

                    TableInfo tableInfo = new TableInfo();
                    tableInfo.setTableName(actualTableName);

                    ColumnInfo columnInfo = new ColumnInfo();
                    columnInfo.setColumnName(actualColumnName);

                    tableInfo.setColumnInfos(Lists.newArrayList(columnInfo));
                    return tableInfo;
                }).collect(
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

        DatabaseInfo databaseInfo = new DatabaseInfo();
        databaseInfo.setTableInfos(Lists.newArrayList(tableInfos));
        databaseInfo.setDatabaseName(Optional.ofNullable(database.getDatabaseName()).orElse("数据库A"));
        log.info("汇聚结果 {}", databaseInfo);

        // 无权列名剔除
    }
}
