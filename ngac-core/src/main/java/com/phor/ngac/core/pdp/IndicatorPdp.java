package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import org.springframework.stereotype.Component;

/**
 * 指标决策器
 *
 * @date 2023/8/4
 */
@Component("indicatorPdp")
public class IndicatorPdp implements PolicyDecisionPoint {
    @Override
    public Decision makeDecision(String subject, String resource, String action) {
        return null;
    }

    @Override
    public Decision makeDecision(String subject, NodeEnum nodeEnum, String action) {
        return null;
    }
}
