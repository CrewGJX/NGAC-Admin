package com.phor.ngac.neo4j.entity.node.ua;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Data
@Node("userGroup")
public class UserGroup {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;
}
