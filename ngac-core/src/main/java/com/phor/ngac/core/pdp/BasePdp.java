package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.handler.core.AccessRightOptTemplate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePdp extends AccessRightOptTemplate implements PolicyDecisionPoint {
    private PdpTypeEnum pc;

    static class AllDenyPdp extends BasePdp {
        @Override
        public Decision makeDecision(String user, String object, String action) {
            return Decision.DENY;
        }

        @Override
        public Decision makeDecision(String subject, NodeEnum nodeEnum, String action) {
            return Decision.DENY;
        }
    }
}
