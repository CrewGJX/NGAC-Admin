package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.factory.PolicyDecisionPointFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Setter
@Component
public class RolePolicyDecisionPoint implements PolicyDecisionPoint {
    private PolicyClassEnum pc;

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Resource
    private PolicyDecisionPointFactory policyDecisionPointFactory;

    @Override
    public Decision makeDecision(String subject, String resource, String action) {
        neo4jPap.getPolicyRelatedResources(this.pc);
        neo4jPap.getAccessRights(subject, resource);
        neo4jPap.getProhibitions(subject, resource);
        return Decision.ALLOW;
    }

    @Bean("menuRolePdp")
    public PolicyDecisionPoint init() {
        return policyDecisionPointFactory.buildDecisionPoint(PolicyClassEnum.MENU_ROLE);
    }
}
