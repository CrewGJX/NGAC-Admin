package com.phor.ngac.neo4j.entity.relation;

import com.phor.ngac.neo4j.entity.node.ua.Role;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * (user)-[assign]->(role)
 *
 * @date 2023/8/3
 */
@RelationshipProperties
@Data
public class UserRoleAssign {
    @Id
    @GeneratedValue
    private Long id;

    @TargetNode
    private Role role;
}
