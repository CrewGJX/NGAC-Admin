package com.phor.ngac.core.sql;

import com.phor.ngac.exception.TableAliasNotDefinedException;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class ComplexSelectItemHandler extends AbstractSelectItemHandler {
    private String actualTableName;

    private String actualColumnName;

    private String tableAlias;

    private String columnAlias;

    public ComplexSelectItemHandler(Map<String, String> aliasTableNameMap) {
        super();
        super.aliasTableNameMap = aliasTableNameMap;
    }

    @Override
    public String getSqlColumn() {
        return String.format("%s%s%s",
                StringUtils.isEmpty(this.tableAlias) ? "" : this.tableAlias + ".",
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

    @Override
    public void handleColumnName(SqlParser sqlParser, String originColumnName, String alias) {
        this.columnAlias = alias;
        boolean isCount = originColumnName.contains("count(");
        Pattern compile = Pattern.compile("(\\w+)\\(([\\w\\\\.]+)\\).*");
        // 目前只是简单判断count函数，后续需要完善
        // count(1) count(*) count(字段名) count(表名.字段名)
        if (isCount) {
            boolean contains = originColumnName.contains(".");

            if (contains) {
                originColumnName = originColumnName.substring(originColumnName.indexOf(".") + 1);
            }

            this.tableAlias = Strings.EMPTY;
            this.actualColumnName = originColumnName;
            this.actualTableName = "auth_temp";
            return;
        }

        if (!originColumnName.contains(".")) {
            // 无别名查询，暂无法获取表名
            throw new TableAliasNotDefinedException(String.format("'%s'无别名查询，暂无法获取表名", originColumnName));
        }

        originColumnName = originColumnName.replaceAll(compile.pattern(), "$2");
        String[] split = originColumnName.split("\\.");

        this.tableAlias = split[0];
        this.actualTableName = super.aliasTableNameMap.get(tableAlias);
        this.actualColumnName = split[1];
    }
}
