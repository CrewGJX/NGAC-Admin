package com.phor.ngac.neo4j.entity.node.u;

import com.phor.ngac.neo4j.entity.relation.UserGroupAssign;
import com.phor.ngac.neo4j.entity.relation.UserNetworkElementAccess;
import com.phor.ngac.neo4j.entity.relation.UserResourceAccess;
import com.phor.ngac.neo4j.entity.relation.UserRoleAssign;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * 用户类
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Node("user")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "assign")
    private List<UserRoleAssign> userRoleAssign;

    @Relationship(type = "assign")
    private UserGroupAssign userGroupAssign;

    @Relationship(type = "access")
    private UserResourceAccess userResourceAccess;

    @Relationship(type = "access")
    private UserNetworkElementAccess userNetworkElementAccess;
}
