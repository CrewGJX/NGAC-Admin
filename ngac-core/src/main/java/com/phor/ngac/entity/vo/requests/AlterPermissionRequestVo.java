package com.phor.ngac.entity.vo.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlterPermissionRequestVo {
    @NotEmpty(message = "用户名不能为空")
    private String loginUserName;

    @NotEmpty(message = "权限类型不能为空")
    private String permissionType;

    @NotEmpty(message = "权限名称不能为空")
    private String permissionName;
}
