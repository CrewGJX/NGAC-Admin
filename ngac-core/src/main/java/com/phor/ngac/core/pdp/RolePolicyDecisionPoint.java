package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.utils.Neo4jOptUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Setter
@Configuration
public class RolePolicyDecisionPoint extends BasePdp {
    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Resource
    private Neo4jOptUtils neo4jOptUtils;

    @Override
    public Decision makeDecision(String user, String menu, String action) {
        // 获取用户对菜单的权限集合
        AccessRight accessRights = neo4jPap.getAccessRights(user, menu);
        // 获取用户对菜单的禁止集合
        AccessRight prohibitions = neo4jPap.getProhibitions(user, menu);
        // 取差集
        AccessRight difference = neo4jOptUtils.difference(accessRights, prohibitions);
        log.info("权限-禁止=effective权限: {}", difference);

        // 判断是否包含action
        boolean isMatch = isArContainsAction(difference, action);
        log.debug("操作是否在权限列表中: {}", isMatch);

        return isMatch ? Decision.ALLOW : Decision.DENY;
    }

    @Override
    public Decision makeDecision(String subject, NodeEnum nodeEnum, String action) {
        throw new UnsupportedOperationException();
    }

    @Bean("menuRolePdp")
    public PolicyDecisionPoint init() {
        return PolicyDecisionPointFactory.buildDecisionPoint(PdpTypeEnum.MENU_ROLE);
    }
}
