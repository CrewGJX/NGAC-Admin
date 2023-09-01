package com.phor.ngac.entity.vo.requests.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("角色管理操作请求对象")
public class RoleAdminOpt extends NodeAdminOpt {
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "角色名称", required = true)
    private String roleName;

    @Override
    public String getNodeName() {
        return roleName;
    }
}
