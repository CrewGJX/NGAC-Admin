package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;

public interface PolicyDecisionPoint {
    /**
     * 做出决策
     *
     * @param subject 主体
     * @param object  对象
     * @param action  操作
     * @return 决策结果
     */
    Decision makeDecision(String subject, String object, String action);

    Decision makeDecision(String subject, NodeEnum nodeEnum, String action);
}
