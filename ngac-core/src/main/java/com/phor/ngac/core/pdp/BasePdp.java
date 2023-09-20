package com.phor.ngac.core.pdp;

import com.phor.ngac.consts.Decision;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.PdpTypeEnum;
import com.phor.ngac.core.handler.core.AccessRightOptTemplate;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.utils.Neo4jOptUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Getter
@Setter
@Slf4j
@Component
public abstract class BasePdp extends AccessRightOptTemplate implements PolicyDecisionPoint {

    @Resource
    private Neo4jOptUtils neo4jOptUtils;

    @Resource
    private PolicyAdministrationPoint neo4jPap;

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

    @Override
    public Decision makeDecision(String subject, String object, String action) {
        // 获取用户对菜单的权限集合
        AccessRight accessRights = neo4jPap.getAccessRights(subject, object);
        // 获取用户对菜单的禁止集合
        AccessRight prohibitions = neo4jPap.getProhibitions(subject, object);
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
        String object = nodeEnum.getName();
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
}
