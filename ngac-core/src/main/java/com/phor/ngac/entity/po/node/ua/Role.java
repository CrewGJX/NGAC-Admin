package com.phor.ngac.entity.po.node.ua;

import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.relation.RoleRsgAccess;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node("role")
public class Role implements BaseNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship("access")
    private List<RoleRsgAccess> roleRsgAssociate;
}
