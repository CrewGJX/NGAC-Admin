package com.phor.ngac.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationEnum {
    // 用于描述OA和UA之间的权限集合
    ACCESS("access"),
    // 用于描述U和O之间的禁止集合
    PROHIBITION("prohibition"),
    // 用于描述OA和OA之间，UA和UA之间的集合继承关系
    ASSIGN("assign"),
    // 用于描述PC和OA之间的关系
    ASSOCIATE("associate"),
    // 用于描述SUPER_PC和PC之间的关系, SUPER_USER和USER之间的关系
    INHERIT("inherit"),
    ;

    private final String type;

}
