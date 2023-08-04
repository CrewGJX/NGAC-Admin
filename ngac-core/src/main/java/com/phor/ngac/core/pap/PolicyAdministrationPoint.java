package com.phor.ngac.core.pap;

import com.phor.ngac.consts.AccessRight;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.neo4j.entity.node.u.User;
import org.neo4j.driver.types.Node;

import java.util.List;

public interface PolicyAdministrationPoint {
    User findUser(String name);

    List<AccessRight> getAccessRights(String subject, String resource);

    List<Node> getPolicyRelatedResources(PolicyClassEnum policyClass);

    List<AccessRight> getProhibitions(String subject, String resource);
}
