package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import org.springframework.stereotype.Component;

/**
 * 网元决策器
 *
 * @date 2023/8/4
 */
@Component("nePdp")
public class NetworkElementPdp implements PolicyDecisionPoint {
    @Override
    public Decision makeDecision(String subject, String resource, String action) {
        return null;
    }
}
