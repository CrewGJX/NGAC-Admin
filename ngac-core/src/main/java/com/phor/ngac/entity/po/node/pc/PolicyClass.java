package com.phor.ngac.entity.po.node.pc;

import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.relation.PolicyClassIndicatorAssociate;
import com.phor.ngac.entity.po.relation.PolicyClassNetworkElementAssociate;
import com.phor.ngac.entity.po.relation.PolicyClassResourceGroupAssociate;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * 策略类
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Data
@Node("policyClass")
public class PolicyClass implements Comparable<PolicyClass>, BaseNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "associate")
    private List<PolicyClassIndicatorAssociate> policyClassIndicatorAssociate;

    @Relationship(type = "associate")
    private List<PolicyClassNetworkElementAssociate> policyClassNetworkElementAssociate;

    @Relationship(type = "associate")
    private List<PolicyClassResourceGroupAssociate> policyClassResourceGroupAssociate;

    @Override
    public int compareTo(PolicyClass o) {
        return this.name.compareTo(o.name);
    }
}
