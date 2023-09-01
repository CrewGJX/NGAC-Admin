package com.phor.ngac.entity.vo.requests.admin.node;

import com.phor.ngac.entity.vo.requests.admin.AdminOpt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("节点管理操作请求对象")
public class NodeAdminOpt extends AdminOpt {
    @ApiModelProperty(value = "节点名称", hidden = true)
    private String nodeName;
    @ApiModelProperty(value = "节点id", hidden = true)
    private Long nodeId;
}
