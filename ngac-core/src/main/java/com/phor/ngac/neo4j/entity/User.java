package com.phor.ngac.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 用户类
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Node("user")
@Data
public class User {
    @Id
    private String name;
}
