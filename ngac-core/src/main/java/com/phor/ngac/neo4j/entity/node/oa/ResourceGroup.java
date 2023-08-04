package com.phor.ngac.neo4j.entity.node.oa;

import com.phor.ngac.neo4j.entity.relation.ResGroupResAccess;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node("resourceGroup")
public class ResourceGroup {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship("assign")
    private List<ResGroupResAccess> resGroupResAccess;
}
