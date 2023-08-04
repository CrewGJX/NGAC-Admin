package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;

public interface PolicyDecisionPoint {
    Decision makeDecision(String subject, String resource, String action);
}
