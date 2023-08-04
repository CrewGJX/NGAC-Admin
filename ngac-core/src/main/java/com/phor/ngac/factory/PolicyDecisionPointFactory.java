package com.phor.ngac.factory;

import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.core.pdp.PolicyDecisionPoint;
import com.phor.ngac.core.pdp.RolePolicyDecisionPoint;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PolicyDecisionPointFactory {
    public PolicyDecisionPoint buildDecisionPoint(PolicyClassEnum pc) {
        RolePolicyDecisionPoint rolePolicyDecisionPoint = new RolePolicyDecisionPoint();
        rolePolicyDecisionPoint.setPc(pc);
        return rolePolicyDecisionPoint;
    }
}
