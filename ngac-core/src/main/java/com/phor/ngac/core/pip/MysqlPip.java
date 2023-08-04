package com.phor.ngac.core.pip;

import com.phor.ngac.consts.AccessRight;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.neo4j.entity.node.u.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MysqlPip implements PolicyInformationPoint {
    @Override
    public User findUser(String name) {
        return null;
    }

    @Override
    public List<AccessRight> getAccessRights(String subject, String resource) {
        return null;
    }

    @Override
    public void getPolicyRelatedResources(PolicyClassEnum policyClass) {

    }
}
