package com.phor.ngac.service.template;

import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.core.epp.events.*;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.vo.requests.admin.AdminOpt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Data
@Slf4j
@SuppressWarnings("rawtypes")
public class AdminServiceTemplate<T extends AdminOpt> {
    protected Class<? extends BaseEvent> eventClass;
    // 操作类型枚举类型
    protected CommonAccess.MetaAccess operateTypeEnum;
    // 校验节点类型，如user类节点的操作，需要校验userNode节点的权限
    protected NodeEnum authNodeEnum;
    // 校验关系类型，如access类关系的操作，需要校验access类关系的权限
    protected NodeEnum authRelationEnum;
    protected T baseAdminVo;

    public NodeAdminServiceTemplate<T> node() {
        return new NodeAdminServiceTemplate<>();
    }

    public RelationAdminServiceTemplate<T> relation() {
        return new RelationAdminServiceTemplate<>();
    }

    public Enum getOperateEnum() {
        throw new UnsupportedOperationException("父类不支持此操作，请使用子类");
    }

    public Enum getAuthNodeEnum() {
        return Optional.ofNullable(authNodeEnum).orElse(authRelationEnum);
    }

    @Data
    @SuppressWarnings("unused")
    public static class NodeAdminServiceTemplate<T extends AdminOpt> extends AdminServiceTemplate<T> {
        // 实际最后操作的节点类型
        private NodeEnum operateNodeEnum;

        public NodeAdminServiceTemplate<T> add() {
            this.eventClass = AddNodeEvent.class;
            this.operateTypeEnum = CommonAccess.MetaAccess.CREATE;
            return this;
        }

        public NodeAdminServiceTemplate<T> delete() {
            this.eventClass = DeleteNodeEvent.class;
            this.operateTypeEnum = CommonAccess.MetaAccess.DELETE;
            return this;
        }

        public NodeAdminServiceTemplate<T> user() {
            this.operateNodeEnum = NodeEnum.USER;
            super.authNodeEnum = NodeEnum.AUTH_USER_NODE;
            return this;
        }

        public NodeAdminServiceTemplate<T> role() {
            this.operateNodeEnum = NodeEnum.ROLE;
            super.authNodeEnum = NodeEnum.AUTH_ROLE_NODE;
            return this;
        }

        @Override
        public NodeEnum getOperateEnum() {
            return this.operateNodeEnum;
        }

        public NodeAdminServiceTemplate<T> data(T baseAdminVo) {
            this.baseAdminVo = baseAdminVo;
            return this;
        }
    }

    @Data
    public static class RelationAdminServiceTemplate<T extends AdminOpt> extends AdminServiceTemplate<T> {
        private RelationEnum relationEnum;
        private CommonNode source;
        private CommonNode target;
        private Long id;
        private String name;

        public RelationAdminServiceTemplate<T> data(T baseAdminVo) {
            this.baseAdminVo = baseAdminVo;
            return this;
        }

        public RelationAdminServiceTemplate<T> add() {
            this.eventClass = AddRelationEvent.class;
            this.operateTypeEnum = CommonAccess.MetaAccess.CREATE;
            return this;
        }

        public RelationAdminServiceTemplate<T> delete() {
            this.eventClass = DeleteRelationEvent.class;
            this.operateTypeEnum = CommonAccess.MetaAccess.DELETE;
            return this;
        }

        public RelationAdminServiceTemplate<T> access() {
            this.relationEnum = RelationEnum.ACCESS;
            this.authRelationEnum = NodeEnum.AUTH_ACCESS_RELATION;
            return this;
        }

        @Override
        public Enum getOperateEnum() {
            return this.relationEnum;
        }
    }
}