package com.phor.ngac.entity.vo.requests.admin.relation;

import com.phor.ngac.consts.RelationEnum;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.entity.vo.requests.admin.AdminOpt;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("关系管理操作请求对象")
public class RelationAdminOpt extends AdminOpt {
    private CommonNode source;
    private CommonNode target;
    private CommonRelation relation;

    public RelationAdminOpt(RelationEnum relationEnum) {
        super();
        CommonRelation commonRelation = new CommonRelation();
        commonRelation.setType(relationEnum.getType());
        this.relation = commonRelation;
    }
}