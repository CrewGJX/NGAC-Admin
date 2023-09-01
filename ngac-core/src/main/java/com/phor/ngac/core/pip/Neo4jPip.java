package com.phor.ngac.core.pip;

import com.google.common.collect.Lists;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.po.node.u.User;
import com.phor.ngac.exception.PolicyClassException;
import com.phor.ngac.mapper.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * neo4j数据库操作类
 * todo 需要拆出集合操作放在pap里，pip只需查询原始结果，也即cypher执行结果即可
 *
 * @date 2023/8/17
 */
@Slf4j
@Component
public class Neo4jPip extends CypherRunner implements PolicyInformationPoint {
    @Resource
    private UserRepository userRepository;

    @Autowired
    private Neo4jClient neo4jClient;

    @Override
    public Optional<User> findUserAndRelations(String name) {
        return userRepository.findByName(name);
    }

    /**
     * <ol>
     *     <li>获取资源的pc受控路径</li>
     *     <li>遍历pc节点</li>
     *     <li>构建u -> oa <- pc</li>
     *     <li>同种pc内权限取并集</li>
     *     <li>不同pc间权限取交集</li>
     * </ol>
     *
     * @param user   用户
     * @param object 资源
     * @return 用户对资源的权限集合
     */
    @Override
    public List<AccessRight> getAccessRights(String user, String object) throws PolicyClassException {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("object", object);

        // 读取cypher中的cql
        String cypher = readFileFromResource("findUserAccess.cyp");

        // 执行cql
        Collection<AccessRight> allAr = neo4jClient.query(cypher)
                .bindAll(param) // 关联参数
                .fetchAs(AccessRight.class) // 返回类型
                .mappedBy((typeSystem, resultRecord) -> getAccessRightFromRecord(resultRecord)) // 通路映射
                .all();

        // 同种pc内权限取并集
        Map<String, AccessRight> policyArMap = allAr.stream()
                .collect(Collectors.groupingBy(
                        AccessRight::getPolicy, Collectors.reducing(AccessRight.emptyAccess(), this::union)));

        // 无权限，直接返回
        if (MapUtils.isEmpty(policyArMap) || policyArMap.values().stream()
                .noneMatch(value -> CollectionUtils.isNotEmpty(value.getCommonAccessList()))) {
            return Lists.newArrayList(AccessRight.emptyAccess());
        }

        // 不同pc间取交集
        AccessRight accessRight = policyArMap.values().stream()
                .reduce(AccessRight.fullAccess(), this::intersection);

        accessRight.setUser(user);
        accessRight.setObject(object);

        log.info("accessRight: {}", accessRight);
        return Lists.newArrayList(accessRight);
    }

    /**
     * 规范中需要3种模式的phb {user_deny, ua_deny, process_deny}
     * 这里只实现user_deny
     *
     * @param user   用户
     * @param object 资源
     * @return
     */
    @Override
    public List<AccessRight> getProhibitions(String user, String object) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        param.put("object", object);

        String cypher = readFileFromResource("findUserPhb.cyp");

        Result result = driverRunCypherWithoutTx(cypher, param);
        List<Record> recordList = result.list();
        List<AccessRight> prohibition = recordList.stream()
                .flatMap(resultRecord -> resultRecord.values().stream()
                        .map(Value::asPath)
                        .map(path -> {
                            AccessRight.AccessRightBuilder builder = AccessRight.builder();
                            builder.user(user)
                                    .object(object)
                                    .policy("prohibition")
                                    .commonAccessList(getPhbListFromPath(Collections.singletonList(path)));
                            log.debug("phb path: {}", path);
                            return builder.build();
                        }))
                .collect(Collectors.toList());
        log.info("prohibition: {}", prohibition);
        return prohibition;
    }
}
