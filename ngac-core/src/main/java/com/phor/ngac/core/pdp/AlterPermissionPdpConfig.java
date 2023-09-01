package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.utils.Neo4jOptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        String object = nodeEnum.getLabel().stream().findFirst().orElse(StringUtils.EMPTY);
        // 获取用户对admin resource的权限集合
        AccessRight accessRights = neo4jPap.getAccessRights(subject, object, nodeEnum);
        // 获取用户对admin resource的禁止集合
        AccessRight prohibitions = neo4jPap.getProhibitions(subject, object);
        // 取差集
        AccessRight difference = neo4jOptUtils.difference(accessRights, prohibitions);
        log.info("权限-禁止=effective权限: {}", difference);

        // 判断是否包含action
        boolean isMatch = isArContainsAction(difference, action);
        log.debug("操作是否在权限列表中: {}", isMatch);

        return isMatch ? Decision.ALLOW : Decision.DENY;
    }

    @Bean("alterPermissionPdp")
    public PolicyDecisionPoint init() {
        return PolicyDecisionPointFactory.buildDecisionPoint(PdpTypeEnum.ALTER_PERMISSION);
    }
}
