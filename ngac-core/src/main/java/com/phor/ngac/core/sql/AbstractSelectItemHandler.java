package com.phor.ngac.core.sql;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列解析器调度入口，主要负责判断是否为纯查询类函数，还是具有复杂逻辑的函数
 * 如果是纯查询类函数，直接调用纯查询类函数处理器 {@link PureSelectItemHandler}
 * 如果是复杂逻辑函数，拆分并分别调用对应的复杂逻辑函数处理器 {@link ComplexSelectItemHandler}etc.
 *
 * @see 0.1
 * @since 0.1
 */
@Data
@Slf4j
public abstract class AbstractSelectItemHandler {
    protected Map<String, String> aliasTableNameMap;

    protected AbstractSelectItemHandler delegate;

    public boolean isComplexSelectItem(String originColumnName) {
        // 特征：包含函数、别名、聚合函数
        Pattern compile = Pattern.compile("(count\\([\\w\\\\.]+\\))|(sum\\([\\w\\\\.]+\\))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(originColumnName);
        return matcher.find();
    }

    // 策略调度入口
    public void handle(SqlParser sqlParser, String originColumnName, String alias) {
        this.delegate = isComplexSelectItem(originColumnName) ?
                new ComplexSelectItemHandler(aliasTableNameMap) : new PureSelectItemHandler(aliasTableNameMap);
        log.info("当前处理器：{}", this.delegate.getClass().getSimpleName());
        this.delegate.handleColumnName(sqlParser, originColumnName, alias);
    }

    public String getSqlColumn() {
        return this.delegate.getSqlColumn();
    }

    public abstract void handleColumnName(SqlParser sqlParser, String originColumnName, String alias);

    public String getActualTableName() {
        return this.delegate.getActualTableName();
    }


    public String getActualColumnName() {
        return this.delegate.getActualColumnName();
    }
}
