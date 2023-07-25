package com.phor.ngac.neo4j.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 策略类
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Data
@Node("policy_class")
public class PolicyClass {
    @Id
    private String name;
}
