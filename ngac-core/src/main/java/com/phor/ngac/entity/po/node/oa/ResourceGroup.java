package com.phor.ngac.entity.po.node.oa;

import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.relation.ResGroupResAccess;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@Node("resourceGroup")
public class ResourceGroup implements BaseNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship("assign")
    private List<ResGroupResAccess> resGroupResAccess;
}
