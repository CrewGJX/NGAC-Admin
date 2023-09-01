package com.phor.ngac.entity.vo.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AlterPermissionRequestVo {
    @NotEmpty(message = "用户名不能为空")
    private String loginUserName;

    @NotEmpty(message = "权限类型不能为空")
    private String permissionType;

    @NotEmpty(message = "权限名称不能为空")
    private String permissionName;
}
