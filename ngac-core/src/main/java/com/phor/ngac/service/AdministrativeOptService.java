package com.phor.ngac.service;

import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.vo.requests.admin.node.RoleAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.UserAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.relation.AccessRelationAdminOpt;

public interface AdministrativeOptService {
    UserPermission findUserAndPermissions(String name);

    /**
     * 添加用户
     *
     * @param loginUserName         登录用户名
     * @param userAdminOptRequestVo 用户信息
     */
    boolean addUser(String loginUserName, UserAdminOpt userAdminOptRequestVo);

    /**
     * 添加角色
     *
     * @param loginUserName         登录用户名
     * @param userAdminOptRequestVo 角色信息
     */
    boolean addRole(String loginUserName, RoleAdminOpt userAdminOptRequestVo);

    boolean addPermission(String loginUserName, AccessRelationAdminOpt accessRelationAdminOpt);
}
