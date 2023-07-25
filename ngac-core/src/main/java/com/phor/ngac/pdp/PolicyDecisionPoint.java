package com.phor.ngac.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.neo4j.mapper.UserRepository;
import org.neo4j.driver.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class PolicyDecisionPoint {

    @Resource
    private UserRepository userRepository;

    @Resource
    private Transaction transaction;

    public Decision makeDecision(String subject, String resource, String action) {
        Map<String, Object> map = new HashMap<>();

        map.put("user", subject);
        map.put("resource", resource);
        map.put("action", action);

        transaction.run("MATCH (a:user{name:$user}),(b:resource{name:$resource}),p=shortestpath((a)-[*..]-(b))RETURN p", map);
        return null;
    }
}
