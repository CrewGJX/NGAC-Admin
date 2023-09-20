package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.utils.Neo4jOptUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Setter
@Configuration
public class DatabaseColumnPdpConfig extends BasePdp {

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Resource
    private Neo4jOptUtils neo4jOptUtils;

    @Override
    public Decision makeDecision(String subject, String object, String action) {
        log.info("用户: {}, 数据库-表-列: {}, 操作: {}", subject, object, action);
        return super.makeDecision(subject, object, action);
    }

    @Override
    public Decision makeDecision(String subject, NodeEnum nodeEnum, String action) {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Bean
    public PolicyDecisionPoint init() {
        return PolicyDecisionPointFactory.buildDecisionPoint(PdpTypeEnum.DATABASE_COLUMN);
    }
}
