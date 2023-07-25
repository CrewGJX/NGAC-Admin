package com.phor.ngac.neo4j.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 超级策略类
 * 所有的策略类在neo里都会指向这个类
 *
 * @version 0.1
 * @since 0.1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("super_policy_class")
public class SuperPolicyClass extends PolicyClass {
}
