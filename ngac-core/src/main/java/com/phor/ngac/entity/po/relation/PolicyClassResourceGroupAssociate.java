package com.phor.ngac.entity.po.relation;

import com.phor.ngac.entity.po.node.oa.ResourceGroup;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class PolicyClassResourceGroupAssociate implements BaseRelation {
    @RelationshipId
    @GeneratedValue
    private Long id;

    @TargetNode
    private ResourceGroup resourceGroup;
}
