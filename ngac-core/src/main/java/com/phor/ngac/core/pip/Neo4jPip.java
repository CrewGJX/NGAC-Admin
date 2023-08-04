package com.phor.ngac.core.pip;

import com.google.common.collect.Lists;
import com.phor.ngac.consts.AccessRight;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.neo4j.entity.node.pc.PolicyClass;
import com.phor.ngac.neo4j.entity.node.u.User;
import com.phor.ngac.neo4j.mapper.PolicyRepository;
import com.phor.ngac.neo4j.mapper.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Neo4jPip implements PolicyInformationPoint {
    @Resource
    private UserRepository userRepository;

    @Resource
    private PolicyRepository policyRepository;

    @Autowired
    @Qualifier("neo4jDriver")
    private Driver neo4jDriver;

    @Override
    public User findUser(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<AccessRight> getAccessRights(String subject, String resource) {
        Map<String, Object> map = new HashMap<>();

        map.put("user", subject);
        map.put("resource", resource);

        Result result = runCypher("match path = (source:user)-[r:*1..]->(target) where source.name = $subject return path", map);

        while (result.hasNext()) {
            Record next = result.next();
            next.fields().forEach(field -> {
                log.info("field: {}", field);
            });
        }
        return Lists.newArrayList();
    }

    @Override
    public void getPolicyRelatedResources(PolicyClassEnum policyClass) {
        // 与pc之间关联的root resource
        PolicyClass pc = policyRepository.findByName(policyClass.name());
        log.info("oaAssociatedWithPc: {}", pc);
    }

    public List<AccessRight> getProhibitions(String subject, String resource) {
        Map<String, Object> param = new HashMap<>();
        param.put("subject", subject);
        param.put("resource", resource);
        Result result = runCypher("match p = (s:user {name: $subject})-[:prohibition*]-(t{name: $resource}) return p", param);

        result.list().forEach(record -> {
            record.fields().forEach(field -> {
                log.info("field: {}", field);
            });
        });
        return Lists.newArrayList();
    }

    private Result runCypher(String cypher, Map<String, Object> params) {
        log.info("cypher: {}, params: {}", cypher, params);
        return neo4jDriver.session().run(cypher, params);
    }
}
