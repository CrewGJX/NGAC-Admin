package com.phor.ngac.entity.po.node.o;

import com.phor.ngac.entity.po.node.BaseNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("indicator")
public class Indicator implements BaseNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;
}
