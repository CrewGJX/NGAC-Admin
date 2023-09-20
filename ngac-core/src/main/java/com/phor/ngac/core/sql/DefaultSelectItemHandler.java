package com.phor.ngac.core.sql;


public class DefaultSelectItemHandler extends AbstractSelectItemHandler {
    @Override
    public void handleColumnName(SqlParser sqlParser, String originColumnName, String alias) {
        throw new UnsupportedOperationException("不支持的操作");
    }
}
