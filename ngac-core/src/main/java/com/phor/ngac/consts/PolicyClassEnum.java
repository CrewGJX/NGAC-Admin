package com.phor.ngac.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolicyClassEnum {
    MENU_ROLE("policyClass", "菜单权限控制");

    private String nodeType;

    private String name;
}
