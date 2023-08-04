package com.phor.ngac.neo4j.entity.relation;

import com.phor.ngac.neo4j.entity.node.o.Resource;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
public class ResGroupResAccess {
    @RelationshipId
    private Long id;

    private boolean read;

    private boolean write;

    @TargetNode
    private Resource resource;
}
