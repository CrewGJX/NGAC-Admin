package com.phor.ngac.config;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.phor.ngac.core.sql.SqlParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.Properties;

@Slf4j
@Component
@Intercepts(value = {@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisInterceptor implements Interceptor {

    @Resource
    private SqlParser sqlParser;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        // 获取sql执行器
        DruidPooledConnection connection = (DruidPooledConnection) args[0];
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        // 获取原始sql语句
        String sql = boundSql.getSql();
        log.info("原始sql语句：{}", sql);
        // sql解析和修改
        String newSql = sqlParser.parse(sql, connection);
        log.info("修改后sql语句：{}", newSql);
        // 通过反射修改sql语句
        ReflectUtil.setFieldValue(boundSql, "sql", newSql);
        // 实际执行sql语句
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
