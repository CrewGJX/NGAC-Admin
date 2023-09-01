package com.phor.ngac.service;

import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.vo.requests.admin.RoleAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.UserAdminOpt;

public interface AdministrativeOptService {
    UserPermission findUserAndPermissions(String name);

    /**
     * 添加用户
     *
     * @param loginUserName         登录用户名
     * @param userAdminOptRequestVo 用户信息
     */
    void addUser(String loginUserName, UserAdminOpt userAdminOptRequestVo);

    /**
     * 添加角色
     *
     * @param loginUserName         登录用户名
     * @param userAdminOptRequestVo 角色信息
     */
    void addRole(String loginUserName, RoleAdminOpt userAdminOptRequestVo);
}
