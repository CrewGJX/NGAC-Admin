package com.phor.ngac.core.pap;

import com.phor.ngac.consts.NodeEnum;
import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.BaseRelation;

public interface PolicyAdministrationPoint extends NodeOpt, RelationOpt {
    UserPermission findUserAndPermissions(String name);

    AccessRight getAccessRights(String user, String object);

    AccessRight getProhibitions(String user, String object);

    void addNode(String cypher);

    void deleteNode(String cypher);

    BaseNode findNode(NodeEnum nodeEnum, Long id, String name);

    void addRelation(String cypher);

    void deleteRelation(String cypher);

    BaseRelation findRelation(RelationEnum relationEnum, Long id, String name);

    BaseRelation findRelation(CommonNode source, CommonNode target);

    BaseRelation findRelation(CommonNode source, CommonNode target, RelationEnum relationEnum);

    AccessRight getAccessRights(String user, String object, NodeEnum nodeEnum);
}
