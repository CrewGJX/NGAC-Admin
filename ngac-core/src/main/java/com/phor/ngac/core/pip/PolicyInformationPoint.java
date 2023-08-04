package com.phor.ngac.core.pip;

import com.phor.ngac.consts.AccessRight;
import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.neo4j.entity.node.u.User;

import java.util.List;

public interface PolicyInformationPoint {
    /**
     * 根据用户名查找用户
     *
     * @param name 用户名
     * @return 用户
     */
    User findUser(String name);

    /**
     * 获取用户主体对资源客体的权限集合
     *
     * @param subject  用户
     * @param resource 资源
     * @return 权限集合
     */
    List<AccessRight> getAccessRights(String subject, String resource);

    /**
     * 获取pc关联的所有资源
     *
     * @param policyClass 策略类型
     */
    void getPolicyRelatedResources(PolicyClassEnum policyClass);
}
