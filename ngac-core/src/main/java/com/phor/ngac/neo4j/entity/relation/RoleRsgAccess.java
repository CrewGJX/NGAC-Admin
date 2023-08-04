package com.phor.ngac.neo4j.entity.relation;

import com.phor.ngac.neo4j.entity.node.oa.ResourceGroup;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class RoleRsgAccess {
    @RelationshipId
    private Long id;

    @Property
    private boolean read;

    @Property
    private boolean write;

    @TargetNode
    private ResourceGroup resourceGroup;
}
