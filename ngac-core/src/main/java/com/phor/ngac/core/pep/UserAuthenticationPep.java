package com.phor.ngac.core.pep;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.core.pdp.PolicyDecisionPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("pep")
public class UserAuthenticationPep implements PolicyEnforcementPoint {

    /**
     * 一个enforcement可能有多个pdp，用于不同种pc的决策。
     */
    @Resource
    private PolicyDecisionPoint menuRolePdp;

    @Resource
    private PolicyDecisionPoint indicatorPdp;

    @Resource
    private PolicyDecisionPoint nePdp;

    @Resource
    private PolicyDecisionPoint alterPermissionPdp;

    @Override
    public boolean enforcePolicy(String user, String menu, String action) {
        // pdp进行决策
        Decision decision = menuRolePdp.makeDecision(user, menu, action);
        log.info("menuRolePdp做出决策: {}", decision);

        // 根据决策结果进行相应处理
        return decision == Decision.ALLOW;
    }

    @Override
    public boolean enforcePolicy(String subject, NodeEnum nodeEnum, String action) {
        Decision decision = alterPermissionPdp.makeDecision(subject, nodeEnum, action);
        log.info("alterPermissionPdp做出决策: {}", decision);

        return decision == Decision.ALLOW;
    }
}
