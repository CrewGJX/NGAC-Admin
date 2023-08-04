package com.phor.ngac.entity.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRequestVo {
    @NotEmpty(message = "用户名不能为空")
    private String name;

    @NotEmpty(message = "资源不能为空")
    private String resource;

    @NotEmpty(message = "操作不能为空")
    private String action;
}
