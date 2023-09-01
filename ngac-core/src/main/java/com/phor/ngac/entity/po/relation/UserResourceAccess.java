package com.phor.ngac.entity.po.relation;

import com.phor.ngac.entity.po.node.o.Indicator;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@RelationshipProperties
public class UserResourceAccess implements BaseRelation {
    @RelationshipId
    @GeneratedValue
    private Long id;

    @Property
    private List<String> type;

    @TargetNode
    private Indicator indicator;
}
