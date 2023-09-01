package com.phor.ngac.service.impl;

import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.core.epp.EventFactory;
import com.phor.ngac.core.epp.events.BaseEvent;
import com.phor.ngac.core.epp.publisher.EventPublisher;
import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.core.pep.PolicyEnforcementPoint;
import com.phor.ngac.core.pip.PolicyInformationPoint;
import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.BaseRelation;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.admin.*;
import com.phor.ngac.service.AdministrativeOptService;
import com.phor.ngac.service.AuthenticateService;
import com.phor.ngac.service.template.AdminServiceTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service("adminService")
public class AdministrativeOptServiceImpl implements AdministrativeOptService {

    @Resource
    private PolicyEnforcementPoint pep;

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Resource
    private PolicyInformationPoint neo4jPip;

    @Resource
    private EventPublisher eventPublisher;

    @Resource
    private AuthenticateService authenticateService;

    @Override
    public UserPermission findUserAndPermissions(String name) {
        return neo4jPap.findUserAndPermissions(name);
    }

    @Override
    public void addUser(String loginUserName, UserAdminOpt userAdminOptRequestVo) {
        String userName = userAdminOptRequestVo.getUserName();
        // 判断用户是否存在
        if (nodeIsExist(NodeEnum.USER, userName)) return;

        AdminServiceTemplate.NodeAdminServiceTemplate<UserAdminOpt> nodeTemplate = new AdminServiceTemplate<UserAdminOpt>()
                .node().user().add().data(userAdminOptRequestVo);

        alterPermissionAuth(loginUserName, nodeTemplate);
    }

    @Override
    public void addRole(String loginUserName, RoleAdminOpt roleAdminOpt) {
        String roleName = roleAdminOpt.getRoleName();
        // 判断用户是否存在
        if (nodeIsExist(NodeEnum.ROLE, roleName)) return;

        AdminServiceTemplate.NodeAdminServiceTemplate<RoleAdminOpt> nodeTemplate = new AdminServiceTemplate<RoleAdminOpt>()
                .node().add().data(roleAdminOpt);

        alterPermissionAuth(loginUserName, nodeTemplate);
    }

    public void addUserAssignUserGroup() {
    }

    @SuppressWarnings("unchecked")
    private <T extends AdminOpt, K extends AdminServiceTemplate<T>> void alterPermissionAuth(String loginUserName, K template) {
        Enum<?> findEnum = template.getFindEnum();

        if (findEnum instanceof NodeEnum) {
            alterNodePermission(loginUserName, (AdminServiceTemplate.NodeAdminServiceTemplate<NodeAdminOpt>) template);
        } else {
            alterRelationPermission(loginUserName, (AdminServiceTemplate.RelationAdminServiceTemplate<RelationAdminOpt>) template);
        }
    }

    private void alterNodePermission(String loginUserName, AdminServiceTemplate.NodeAdminServiceTemplate<NodeAdminOpt> template) {
        // 节点不存在，查询权限
        NodeEnum nodeEnum = Enum.valueOf(NodeEnum.class, template.getFindEnum().name());
        boolean permission = authenticateService.alterPermission(AlterPermissionRequestVo.builder()
                .loginUserName(loginUserName)
                .permissionName(nodeEnum.getLabel().stream().findFirst().orElse(null))
                .permissionType(template.getOperateEnum().getType())
                .build());

        // 权限校验通过，发送事件
        if (permission) {
            String nodeName = template.getBaseAdminVo().getNodeName();

            BaseEvent nodeEvent = EventFactory.builder(template.getEventClass())
                    .node()
                    .label(nodeEnum)
                    .name(nodeName)
                    .build();
            eventPublisher.publish(nodeEvent);
        }
    }

    private void alterRelationPermission(String loginUserName, AdminServiceTemplate.RelationAdminServiceTemplate<RelationAdminOpt> template) {

    }

    private boolean nodeIsExist(NodeEnum nodeEnum, String name) {
        BaseNode node = neo4jPap.findNode(nodeEnum, null, name);
        return Objects.nonNull(node) && Objects.nonNull(node.getId());
    }

    private boolean relationIsExist(RelationEnum relationEnum, CommonNode sourceNode, CommonNode targetNode) {
        BaseRelation relation = neo4jPap.findRelation(sourceNode, targetNode, relationEnum);
        return Objects.nonNull(relation) && Objects.nonNull(relation.getId());
    }

    private boolean relationIsExist(RelationEnum relationEnum, String name, Long id) {
        BaseRelation relation = neo4jPap.findRelation(relationEnum, id, name);
        return Objects.nonNull(relation) && Objects.nonNull(relation.getId());
    }
}
