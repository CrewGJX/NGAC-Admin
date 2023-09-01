package com.phor.ngac.entity.vo.requests.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户管理操作请求对象")
public class UserAdminOpt extends NodeAdminOpt {
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名称", required = true)
    private String userName;

    @ApiModelProperty(value = "用户专网")
    private String userPnNetwork;

    @Override
    public String getNodeName() {
        return userName;
    }
}
