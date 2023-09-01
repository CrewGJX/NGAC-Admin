package com.phor.ngac.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FtlEnum {
    // 增加node
    ADD_NODE(FtlPrefixEnum.NODE, "addNode"),
    // 删除node
    DELETE_NODE(FtlPrefixEnum.NODE, "deleteNode"),
    // 查询node
    FIND_NODE(FtlPrefixEnum.NODE, "findNode"),
    // 增加relation
    ADD_RELATION(FtlPrefixEnum.RELATION, "addRelation"),
    // 删除relation
    DEL_RELATION(FtlPrefixEnum.RELATION, "delRelation"),
    // 查询relation
    FIND_RELATION(FtlPrefixEnum.RELATION, "findRelation"),
    ;

    private final FtlPrefixEnum ftlPrefix;
    private final String ftlPath;

    public String getFullFtlPath() {
        return ftlPrefix.getPrefix() + "/" + ftlPath + FtlSuffixEnum.FTL.getSuffix();
    }

    @Getter
    @AllArgsConstructor
    enum FtlPrefixEnum {
        NODE("node"),
        RELATION("relation"),
        ;
        private final String prefix;
    }

    @Getter
    @AllArgsConstructor
    enum FtlSuffixEnum {
        FTL(".ftl");
        private final String suffix;
    }
}
