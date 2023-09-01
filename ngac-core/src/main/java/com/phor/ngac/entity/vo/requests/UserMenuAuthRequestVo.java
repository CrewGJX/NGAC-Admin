package com.phor.ngac.entity.vo.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserMenuAuthRequestVo {
    @NotEmpty(message = "用户名不能为空")
    private String name;

    @NotEmpty(message = "菜单名称不能为空")
    private String menu;
}
