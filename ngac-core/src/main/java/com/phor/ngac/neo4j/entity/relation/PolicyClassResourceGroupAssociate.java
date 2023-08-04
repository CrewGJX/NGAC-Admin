package com.phor.ngac.neo4j.entity.relation;

import com.phor.ngac.neo4j.entity.node.oa.ResourceGroup;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class PolicyClassResourceGroupAssociate {
    @RelationshipId
    private Long id;

    @TargetNode
    private ResourceGroup resourceGroup;
}
