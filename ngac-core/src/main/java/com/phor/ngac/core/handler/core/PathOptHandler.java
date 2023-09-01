package com.phor.ngac.core.handler.core;

import com.phor.ngac.consts.CommonAccess;
import com.phor.ngac.entity.po.node.pc.PolicyClass;
import org.neo4j.driver.types.Path;

import java.util.List;

/**
 * neo4j路径操作顶级接口
 *
 * @date 2023/8/11
 */
public interface PathOptHandler {
    /**
     * 获取路径中的PC节点
     *
     * @param path 路径
     * @return PC节点列表
     */
    List<PolicyClass> getPolicyClassNodeFromPath(List<Path> path);

    /**
     * 获取路径中的权限控制列表
     *
     * @param path 路径
     * @return 权限控制列表
     */
    List<CommonAccess> getAccessListFromPath(List<Path> path);

    /**
     * 获取路径中的禁止控制列表
     *
     * @param path 路径
     * @return 禁止控制列表
     */
    List<CommonAccess> getPhbListFromPath(List<Path> path);
}
