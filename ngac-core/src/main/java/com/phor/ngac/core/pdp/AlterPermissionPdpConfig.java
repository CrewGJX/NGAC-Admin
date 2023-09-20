package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.utils.Neo4jOptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class AlterPermissionPdpConfig extends BasePdp {

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Resource
    private Neo4jOptUtils neo4jOptUtils;

    @Override
    public Decision makeDecision(String user, String object, String action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Decision makeDecision(String subject, NodeEnum nodeEnum, String action) {
        return super.makeDecision(subject, nodeEnum, action);
    }

    @Bean("alterPermissionPdp")
    public PolicyDecisionPoint init() {
        return PolicyDecisionPointFactory.buildDecisionPoint(PdpTypeEnum.ALTER_PERMISSION);
    }
}
