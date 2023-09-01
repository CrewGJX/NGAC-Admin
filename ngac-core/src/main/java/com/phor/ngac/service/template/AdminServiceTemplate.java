package com.phor.ngac.service.template;

import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.core.epp.events.AddNodeEvent;
import com.phor.ngac.core.epp.events.BaseEvent;
import com.phor.ngac.core.epp.events.DeleteNodeEvent;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.vo.requests.admin.AdminOpt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@SuppressWarnings("rawtypes")
public class AdminServiceTemplate<T extends AdminOpt> {
    protected Class<? extends BaseEvent> eventClass;
    protected CommonAccess.MetaAccess operateEnum;
    protected T baseAdminVo;

    public NodeAdminServiceTemplate<T> node() {
        return new NodeAdminServiceTemplate<>();
    }

    public RelationAdminServiceTemplate<T> relation() {
        return new RelationAdminServiceTemplate<>();
    }

    public Enum getFindEnum() {
        return null;
    }

    @Data
    @SuppressWarnings("unused")
    public static class NodeAdminServiceTemplate<T extends AdminOpt> extends AdminServiceTemplate<T> {
        private NodeEnum nodeEnum;

        public NodeAdminServiceTemplate<T> add() {
            this.eventClass = AddNodeEvent.class;
            this.operateEnum = CommonAccess.MetaAccess.CREATE;
            return this;
        }

        public NodeAdminServiceTemplate<T> delete() {
            this.eventClass = DeleteNodeEvent.class;
            this.operateEnum = CommonAccess.MetaAccess.DELETE;
            return this;
        }

        public NodeAdminServiceTemplate<T> user() {
            this.nodeEnum = NodeEnum.USER;
            return this;
        }

        @Override
        public Enum getFindEnum() {
            return this.nodeEnum;
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
    }
}