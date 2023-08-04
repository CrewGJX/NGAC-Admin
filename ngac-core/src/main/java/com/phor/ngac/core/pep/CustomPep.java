package com.phor.ngac.core.pep;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.core.pdp.PolicyDecisionPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("pep")
public class CustomPep implements PolicyEnforcementPoint {

    /**
     * 一个enforcement可能有多个pdp，用于不同种pc的决策。
     */
    @Resource
    private PolicyDecisionPoint menuRolePdp;

    @Resource
    private PolicyDecisionPoint indicatorPdp;

    @Resource
    private PolicyDecisionPoint nePdp;

    @Override
    public boolean enforcePolicy(String subject, String resource, String action) {
        Decision decision = menuRolePdp.makeDecision(subject, resource, action);

        // 根据决策结果进行相应处理
        if (decision == Decision.ALLOW) {
            // 允许访问
            return true;
        } else {
            // 拒绝访问
            return false;
        }
    }
}
