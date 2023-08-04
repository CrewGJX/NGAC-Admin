package com.phor.ngac.neo4j.entity.node.ua;

import com.phor.ngac.neo4j.entity.relation.RoleRsgAccess;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node("role")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship("access")
    private List<RoleRsgAccess> roleRsgAssociate;
}
