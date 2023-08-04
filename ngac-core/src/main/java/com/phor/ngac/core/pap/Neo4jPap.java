package com.phor.ngac.core.pap;

import com.phor.ngac.consts.AccessRight;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.core.pip.Neo4jPip;
import com.phor.ngac.neo4j.entity.node.u.User;
import org.neo4j.driver.types.Node;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class Neo4jPap implements PolicyAdministrationPoint {

    @Resource
    private Neo4jPip pip;

    @Override
    public User findUser(String name) {
        return pip.findUser(name);
    }

    @Override
    public List<AccessRight> getAccessRights(String subject, String resource) {
        return pip.getAccessRights(subject, resource);
    }

    @Override
    public List<Node> getPolicyRelatedResources(PolicyClassEnum policyClass) {
        pip.getPolicyRelatedResources(policyClass);
        return null;
    }

    @Override
    public List<AccessRight> getProhibitions(String subject, String resource) {
        return pip.getProhibitions(subject, resource);
    }
}
