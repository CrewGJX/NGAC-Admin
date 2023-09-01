package com.phor.ngac.entity.po.relation;

import com.phor.ngac.entity.po.node.ua.Role;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * (user)-[assign]->(role)
 *
 * @date 2023/8/3
 */
@RelationshipProperties
@Data
public class UserRoleAssign implements BaseRelation {
    @RelationshipId
    @GeneratedValue
    private Long id;

    @TargetNode
    private Role role;
}
