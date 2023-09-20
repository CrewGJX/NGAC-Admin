package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.PdpTypeEnum;
import lombok.Data;

import java.util.Objects;

/**
 * 决策器工厂
 *
 * @version 0.1
 */
@Data
public class PolicyDecisionPointFactory {
    private PolicyDecisionPointFactory() {
    }

    public static PolicyDecisionPoint buildDecisionPoint(PdpTypeEnum pc) {
        if (Objects.isNull(pc)) {
            return new BasePdp.AllDenyPdp();
        }
        BasePdp policyDecisionPoint;
        switch (pc) {
            case MENU_ROLE:
                policyDecisionPoint = new MenuRolePdpConfig();
                break;
            case ALTER_PERMISSION:
                policyDecisionPoint = new AlterPermissionPdpConfig();
                break;
            case DATABASE_COLUMN:
                policyDecisionPoint = new DatabaseColumnPdpConfig();
                break;
            default:
                policyDecisionPoint = new BasePdp.AllDenyPdp();
                break;
        }
        policyDecisionPoint.setPc(pc);
        return policyDecisionPoint;
    }
}
