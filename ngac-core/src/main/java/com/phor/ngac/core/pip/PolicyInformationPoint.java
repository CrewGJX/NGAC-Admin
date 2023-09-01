package com.phor.ngac.core.pip;

import com.phor.ngac.entity.dto.AccessRight;
import com.phor.ngac.entity.po.node.u.User;
import com.phor.ngac.exception.PolicyClassException;

import java.util.List;
import java.util.Optional;

public interface PolicyInformationPoint {
    Optional<User> findUserAndRelations(String name);

    /**
     * 获取用户主体对资源客体的权限集合
     *
     * @param user  用户
     * @param object 资源
     * @return 权限集合
     */
    List<AccessRight> getAccessRights(String user, String object) throws PolicyClassException;

    /**
     * 获取用户主体对资源客体的禁止集合
     *
     * @param user  用户
     * @param object 资源
     * @return 禁止集合
     */
    List<AccessRight> getProhibitions(String user, String object);
}
