package com.phor.ngac.core.pip;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.map.MapUtil;
import com.google.common.collect.Lists;
import com.phor.ngac.core.handler.Neo4jAbstractOptTemplate;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.StringJoiner;

/**
 * neo4j-cypher执行器
 *
 * @date 2023/8/11
 */
@Slf4j
@Component
public class CypherRunner extends Neo4jAbstractOptTemplate {
    // cyp文件路径前缀
    public static final String PATH_PREFIX = "classpath:mapper";
    private final ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();
    private final ThreadLocal<Result> resultThreadLocal = new ThreadLocal<>();
    @Autowired
    @Qualifier("neo4jDriver")
    private Driver neo4jDriver;

    /**
     * 使用neo4j-driver执行cypher
     *
     * @param cypher cypher语句
     * @param params 参数
     * @return 执行结果
     */
    public Result driverRunCypherWithoutTx(String cypher, Map<String, Object> params) {
        log.debug("cypher: {}, params: {}", cypher, params);
        return neo4jDriver.session().run(cypher, params);
    }

    public Result driverRunCypherWithoutTx(String cypher) {
        log.debug("cypher: {}", cypher);
        return neo4jDriver.session().run(cypher);
    }

    public CypherRunner driverRunCypherWithTx(String cypher, Map<String, Object> params) {
        log.debug("cypher: {}, params: {}", cypher, params);

        Transaction transaction = transactionThreadLocal.get();
        Result result = transaction.run(cypher, params);
        resultThreadLocal.set(result);
        return this;
    }

    public CypherRunner driverRunCypherWithTx(String cypher) {
        return driverRunCypherWithTx(cypher, MapUtil.empty());
    }

    public CypherRunner beginTx() {
        Transaction transaction = neo4jDriver.session().beginTransaction();
        transactionThreadLocal.set(transaction);
        return this;
    }

    public CypherRunner commit() {
        Transaction transaction = transactionThreadLocal.get();
        transaction.commit();
        transactionThreadLocal.remove();
        return this;
    }

    public Result result() {
        Result result = resultThreadLocal.get();
        resultThreadLocal.remove();

        return result;
    }

    /**
     * 读取cyp文件
     *
     * @param fileName 文件名
     * @return cql内容
     */
    protected String readFileFromResource(String fileName) {
        StringJoiner fullPath = new StringJoiner("/");
        fullPath.add(PATH_PREFIX).add(fileName);
        ClassPathResource mapperFile = new ClassPathResource(fullPath.toString());
        return String.join("\n", IoUtil.readUtf8Lines(mapperFile.getStream(), Lists.newArrayList()));
    }
}
