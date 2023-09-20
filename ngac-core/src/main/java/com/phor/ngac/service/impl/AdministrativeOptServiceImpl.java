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
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.admin.AdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.NodeAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.RoleAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.UserAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.relation.AccessRelationAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.relation.RelationAdminOpt;
import com.phor.ngac.exception.DuplicateDefinedException;
import com.phor.ngac.exception.NodeNotFountException;
import com.phor.ngac.service.AdministrativeOptService;
import com.phor.ngac.service.AuthenticateService;
import com.phor.ngac.service.template.AdminServiceTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service("adminService")
@SuppressWarnings("unused")
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
    public boolean addUser(String loginUserName, UserAdminOpt userAdminOptRequestVo) {
        String userName = userAdminOptRequestVo.getUserName();
        // 判断用户是否存在
        if (nodeIsExist(NodeEnum.USER, userName)) return false;

        AdminServiceTemplate.NodeAdminServiceTemplate<UserAdminOpt> nodeTemplate = new AdminServiceTemplate<UserAdminOpt>()
                .node().user().add().data(userAdminOptRequestVo);

        return alterPermissionAuth(loginUserName, nodeTemplate);
    }

    @Override
    public boolean addRole(String loginUserName, RoleAdminOpt roleAdminOpt) {
        String roleName = roleAdminOpt.getRoleName();
        // 判断用户是否存在
        if (nodeIsExist(NodeEnum.ROLE, roleName)) return false;

        AdminServiceTemplate.NodeAdminServiceTemplate<RoleAdminOpt> nodeTemplate = new AdminServiceTemplate<RoleAdminOpt>()
                .node().role().add().data(roleAdminOpt);

        return alterPermissionAuth(loginUserName, nodeTemplate);
    }

    @Override
    public boolean addPermission(String loginUserName, AccessRelationAdminOpt accessRelationAdminOpt) {
        CommonNode source = accessRelationAdminOpt.getSource();
        CommonNode target = accessRelationAdminOpt.getTarget();
        CommonRelation relation = accessRelationAdminOpt.getRelation();
        // 判断节点是否存在
        if (!nodeIsExist(source.getId(), NodeEnum.getByLabel(source.getLabels()), source.getName()))
            throw new NodeNotFountException();
        if (!nodeIsExist(source.getId(), NodeEnum.getByLabel(target.getLabels()), target.getName()))
            throw new NodeNotFountException();

        // 判断关系是否存在
        if (relationIsExist(RelationEnum.getByType(relation.getType()), source, target))
            throw new DuplicateDefinedException();

        AdminServiceTemplate.RelationAdminServiceTemplate<RelationAdminOpt> relationTemplate = new AdminServiceTemplate<RelationAdminOpt>()
                .relation().access().add().data(accessRelationAdminOpt);

        return alterPermissionAuth(loginUserName, relationTemplate);
    }

    public void addUserAssignUserGroup() {
    }

    @SuppressWarnings("unchecked")
    private <T extends AdminOpt, K extends AdminServiceTemplate<T>> boolean alterPermissionAuth(String loginUserName, K template) {
        Enum<?> findEnum = template.getOperateEnum();

        if (findEnum instanceof NodeEnum) {
            return alterNodePermission(loginUserName, (AdminServiceTemplate.NodeAdminServiceTemplate<NodeAdminOpt>) template);
        } else {
            return alterRelationPermission(loginUserName, (AdminServiceTemplate.RelationAdminServiceTemplate<RelationAdminOpt>) template);
        }
    }

    private boolean alterNodePermission(String loginUserName, AdminServiceTemplate.NodeAdminServiceTemplate<NodeAdminOpt> template) {
        // 节点不存在，查询权限
        NodeEnum nodeEnum = Enum.valueOf(NodeEnum.class, template.getAuthNodeEnum().name());
        boolean permission = authenticateService.alterPermission(AlterPermissionRequestVo.builder()
                .loginUserName(loginUserName)
                // NodeEnum.USER_NODE || NodeEnum.ROLE_NODE etc.
                .permissionName(nodeEnum.getName())
                .permissionType(template.getOperateTypeEnum().getType())
                .build());

        // 权限校验通过，发送事件
        if (permission) {
            String nodeName = template.getBaseAdminVo().getNodeName();

            BaseEvent nodeEvent = EventFactory.builder(template.getEventClass())
                    .node()
                    .label(template.getOperateEnum().getLabel())
                    .name(nodeName)
                    .build();
            eventPublisher.publish(nodeEvent);
        }
        return permission;
    }

    private boolean alterRelationPermission(String loginUserName, AdminServiceTemplate.RelationAdminServiceTemplate<RelationAdminOpt> template) {
        NodeEnum nodeEnum = Enum.valueOf(NodeEnum.class, template.getAuthNodeEnum().name());
        boolean permission = authenticateService.alterPermission(AlterPermissionRequestVo.builder()
                .loginUserName(loginUserName)
                // NodeEnum.USER_NODE || NodeEnum.ROLE_NODE etc.
                .permissionName(nodeEnum.getName())
                .permissionType(template.getOperateTypeEnum().getType())
                .build());

        // 权限校验通过，发送事件
        if (permission) {
            RelationAdminOpt baseAdminVo = template.getBaseAdminVo();

            BaseEvent nodeEvent = EventFactory.builder(template.getEventClass())
                    .relation()
                    .sourceNode(baseAdminVo.getSource())
                    .targetNode(baseAdminVo.getTarget())
                    .relationData(baseAdminVo.getRelation())
                    .build();
            eventPublisher.publish(nodeEvent);
        }
        return permission;
    }

    private boolean nodeIsExist(Long id, NodeEnum nodeEnum, String name) {
        BaseNode node = neo4jPap.findNode(nodeEnum, id, name);
        return Objects.nonNull(node) && Objects.nonNull(node.getId());
    }

    private boolean nodeIsExist(NodeEnum nodeEnum, String name) {
        return nodeIsExist(null, nodeEnum, name);
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
