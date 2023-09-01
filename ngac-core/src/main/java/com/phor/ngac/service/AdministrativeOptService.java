package com.phor.ngac.service;

import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.po.node.CommonNode;
import com.phor.ngac.entity.po.relation.CommonRelation;
import com.phor.ngac.entity.vo.requests.admin.node.RoleAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.UserAdminOpt;

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

    String addPermission(String loginUserName, CommonNode source, CommonNode target, CommonRelation relation);
}
