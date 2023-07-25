package com.phor.ngac.pep;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.pdp.PolicyDecisionPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomPep implements PolicyEnforcementPoint {

    /**
     * 一个enforcement可能有多个pdp，用于不同种pc的决策。需要使用factory+builder模式构建pdp。
     */


    private PolicyDecisionPoint pdp;

    @Override
    public boolean enforcePolicy(String subject, String resource, String action) {
        Decision decision = pdp.makeDecision(subject, resource, action);

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
