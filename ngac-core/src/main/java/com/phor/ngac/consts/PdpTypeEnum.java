package com.phor.ngac.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PdpTypeEnum {
    MENU_ROLE("menuRole", "菜单权限控制"),
    ALTER_PERMISSION("alterPermission", "权限修改"),
    DATABASE_COLUMN("databaseColumn", "数据库列权限控制"),
    ;

    private final String nodeType;

    private final String name;
}
