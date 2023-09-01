package com.phor.ngac.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 非管理性权限
 *
 * @author Phor
 * @version 0.1
 * @since 0.1
 */
@Getter
@AllArgsConstructor
public enum CommonAccess {
    CREATE("create", "创建"),
    UPDATE("update", "更新"),
    READ("read", "读取"),
    DELETE("delete", "删除"),
    UNKONWN("unknown", "未知");

    private final String type;

    private final String desc;

    public static CommonAccess getAccess(String type) {
        for (CommonAccess commonAccess : CommonAccess.values()) {
            if (commonAccess.getType().equals(type)) {
                return commonAccess;
            }
        }
        return UNKONWN;
    }

    public static CommonAccess.MetaAccess getMetaAccess(String metaType) {
        for (CommonAccess.MetaAccess metaAccess : CommonAccess.MetaAccess.values()) {
            if (metaAccess.getType().equals(metaType)) {
                return metaAccess;
            }
        }
        return MetaAccess.UNKONWN;
    }

    @Getter
    public enum MetaAccess {
        CREATE("create", "创建"),
        UPDATE("update", "更新"),
        READ("read", "读取"),
        DELETE("delete", "删除"),
        UNKONWN("unknown", "未知");

        private final String type;

        private final String desc;

        MetaAccess(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }
    }
}
