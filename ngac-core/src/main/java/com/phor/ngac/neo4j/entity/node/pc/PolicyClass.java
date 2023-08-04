package com.phor.ngac.neo4j.entity.node.pc;

import com.phor.ngac.neo4j.entity.relation.PolicyClassIndicatorAssociate;
import com.phor.ngac.neo4j.entity.relation.PolicyClassNetworkElementAssociate;
import com.phor.ngac.neo4j.entity.relation.PolicyClassResourceGroupAssociate;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 策略类
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Data
@Node("policyClass")
public class PolicyClass {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "associate")
    private PolicyClassIndicatorAssociate policyClassIndicatorAssociate;

    @Relationship(type = "associate")
    private PolicyClassNetworkElementAssociate policyClassNetworkElementAssociate;

    @Relationship(type = "associate")
    private PolicyClassResourceGroupAssociate policyClassResourceGroupAssociate;
}
