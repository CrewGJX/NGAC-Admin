package com.phor.ngac.neo4j.entity.relation;

import com.phor.ngac.neo4j.entity.node.ua.UserGroup;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

/**
 * (user)-[assign]->(userGroup)
 *
 * @date 2023/8/3
 */
@RelationshipProperties
@Data
public class UserGroupAssign {
    @RelationshipId
    private Long id;

    @TargetNode
    private UserGroup userGroup;
}
