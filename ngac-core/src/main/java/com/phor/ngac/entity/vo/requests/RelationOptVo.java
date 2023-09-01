package com.phor.ngac.entity.vo.requests;

import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import lombok.Data;

@Deprecated
@Data
public class RelationOptVo {
    private CommonNode source;
    private CommonNode target;
    private CommonRelation commonRelation;
}
