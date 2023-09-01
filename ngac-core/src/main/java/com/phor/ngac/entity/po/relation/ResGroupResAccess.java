package com.phor.ngac.entity.po.relation;

import com.phor.ngac.entity.po.node.o.Resource;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@RelationshipProperties
@Data
public class ResGroupResAccess implements BaseRelation {
    @RelationshipId
    @GeneratedValue
    private Long id;

    @Property
    private List<String> type;

    @TargetNode
    private Resource resource;
}
