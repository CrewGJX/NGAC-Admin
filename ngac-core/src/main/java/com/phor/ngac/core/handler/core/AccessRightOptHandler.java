package com.phor.ngac.core.handler.core;

import com.phor.ngac.entity.dto.AccessRight;

/**
 * 权限操作顶级接口
 *
 * @date 2023/8/11
 */
public interface AccessRightOptHandler {
    /**
     * 获取权限集合的交集
     *
     * @param a 一个权限集合
     * @param b 另一个权限集合
     * @return 交集
     */
    AccessRight intersection(AccessRight a, AccessRight b);

    /**
     * 获取权限集合的并集
     *
     * @param a 一个权限集合
     * @param b 另一个权限集合
     * @return 并集
     */
    AccessRight union(AccessRight a, AccessRight b);

    /**
     * 获取权限集合的差集
     *
     * @param a 一个权限集合
     * @param b 另一个权限集合
     * @return 差集
     */
    AccessRight difference(AccessRight a, AccessRight b);

    /**
     * 判断权限集合是否包含某个权限
     *
     * @param ar     权限集合
     * @param action 权限
     * @return 是否包含
     */
    boolean isArContainsAction(AccessRight ar, String action);
}
