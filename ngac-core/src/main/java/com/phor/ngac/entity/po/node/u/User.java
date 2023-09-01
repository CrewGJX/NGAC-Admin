package com.phor.ngac.entity.po.node.u;

import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.relation.UserGroupAssign;
import com.phor.ngac.entity.po.relation.UserRoleAssign;
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
public class User implements BaseNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "assign")
    private List<UserRoleAssign> userRoleAssign;

    @Relationship(type = "assign")
    private UserGroupAssign userGroupAssign;
}
