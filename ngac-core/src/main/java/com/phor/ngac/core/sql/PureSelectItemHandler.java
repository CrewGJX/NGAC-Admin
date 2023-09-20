package com.phor.ngac.core.sql;

import com.phor.ngac.exception.TableAliasNotDefinedException;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 纯查询类函数
 *
 * @version 0.1
 * @since 0.1
 */
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class PureSelectItemHandler extends AbstractSelectItemHandler {
    private String actualTableName;

    private String actualColumnName;

    private String tableAlias;

    private String columnAlias;

    public PureSelectItemHandler(Map<String, String> aliasTableNameMap) {
        super();
        super.aliasTableNameMap = aliasTableNameMap;
    }

    @Override
    public void handleColumnName(SqlParser sqlParser, String originColumnName, String alias) {
        log.info("纯查询类函数处理器");
        if (!originColumnName.contains(".")) {
            // 无别名查询，暂无法获取表名
            throw new TableAliasNotDefinedException(String.format("'%s'无别名查询，暂无法获取表名", originColumnName));
        }
        String[] split = originColumnName.split("\\.");

        this.tableAlias = split[0];
        this.actualTableName = super.aliasTableNameMap.get(tableAlias);
        this.actualColumnName = split[1];
        this.columnAlias = alias;
    }

    @Override
    public String getSqlColumn() {
        return String.format("%s%s%s",
                StringUtils.isEmpty(this.tableAlias) ||
                        this.tableAlias.equals(this.actualTableName) // 无别名查询，去除别名，直接使用表名
                        ? "" : this.tableAlias + ".",
                this.actualColumnName,
                StringUtils.isEmpty(this.columnAlias) ? "" : " as " + this.columnAlias);
    }

    @Override
    public String getActualColumnName() {
        return actualColumnName;
    }

    @Override
    public String getActualTableName() {
        return actualTableName;
    }
}
