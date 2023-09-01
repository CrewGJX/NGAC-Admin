package com.phor.ngac.consts;

import com.google.common.collect.Lists;
import com.phor.ngac.entity.po.node.BaseNode;
import com.phor.ngac.entity.po.node.o.Resource;
import com.phor.ngac.entity.po.node.oa.ResourceGroup;
import com.phor.ngac.entity.po.node.pc.PolicyClass;
import com.phor.ngac.entity.po.node.u.User;
import com.phor.ngac.entity.po.node.ua.Role;
import com.phor.ngac.entity.po.node.ua.UserGroup;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public enum NodeEnum {
    UNKNOWN(Collections.singletonList("unknown"), BaseNode.class),
    // 策略类
    PC(Collections.singletonList("policyClass"), PolicyClass.class),
    // 用户
    USER(Lists.newArrayList("user", "u"), User.class),
    // 角色
    ROLE(Collections.singletonList("role"), Role.class),
    // 用户组
    USER_GROUP(Collections.singletonList("userGroup"), UserGroup.class),
    // 资源
    RESOURCE(Lists.newArrayList("resource", "o"), Resource.class),
    // 资源组
    RESOURCE_GROUP(Collections.singletonList("resourceGroup"), ResourceGroup.class),
    // alter用户节点的权限
    USER_NODE(Lists.newArrayList("resource", "o"), null, "userNode"),
    // alter角色节点的权限
    ROLE_NODE(Lists.newArrayList("resource", "o"), null, "roleNode"),
    // alter pc 节点的权限
    PC_NODE(Lists.newArrayList("pcNode", "o"), null, "pcNode"),
    ;

    private final List<String> label;

    private final Class<? extends BaseNode> nodeClass;

    private String name = null;

    NodeEnum(List<String> label, Class<? extends BaseNode> nodeClass) {
        this.label = label;
        this.nodeClass = nodeClass;
    }

    NodeEnum(List<String> label, Class<? extends BaseNode> nodeClass, String name) {
        this.label = label;
        this.nodeClass = nodeClass;
        this.name = name;
    }

    @SuppressWarnings("all")
    public static NodeEnum getByLabel(List<String> label) {
        for (NodeEnum nodeEnum : NodeEnum.values()) {
            if (nodeEnum.getLabel().containsAll(label)) {
                return nodeEnum;
            }
        }
        return UNKNOWN;
    }

    public static NodeEnum getByName(String name) {
        for (NodeEnum nodeEnum : NodeEnum.values()) {
            if (Optional.ofNullable(nodeEnum.getName()).orElse(StringUtils.EMPTY).equals(name)) {
                return nodeEnum;
            }
        }
        return UNKNOWN;
    }
}