package com.phor.ngac.controller;

import com.phor.ngac.entity.dto.UserPermission;
import com.phor.ngac.entity.vo.requests.admin.node.RoleAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.node.UserAdminOpt;
import com.phor.ngac.entity.vo.requests.admin.relation.AccessRelationAdminOpt;
import com.phor.ngac.entity.vo.responses.CommonResponse;
import com.phor.ngac.service.AdministrativeOptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 管理员操作
 *
 * @date 2023/8/2
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "管理员操作")
public class AdministrativeOptController {
    @Resource
    private AdministrativeOptService adminService;

    @PostMapping("addUser")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    @ApiImplicitParam(name = "loginUserName", value = "登录用户名", required = true, dataType = "String", paramType = "query")
    public CommonResponse<Boolean> addUser(@RequestParam String loginUserName,
                                           @RequestBody UserAdminOpt userAdminOptRequestVo) {
        boolean isSuccess = adminService.addUser(loginUserName, userAdminOptRequestVo);
        return CommonResponse.response(isSuccess);
    }

    @PostMapping("addRole")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public CommonResponse<Boolean> addRole(@RequestParam String loginUserName,
                                           @RequestBody RoleAdminOpt roleAdminOptRequestVo) {
        boolean isSuccess = adminService.addRole(loginUserName, roleAdminOptRequestVo);
        return CommonResponse.response(isSuccess);
    }

    @PostMapping("addPolicyClass")
    @ApiOperation(value = "添加策略类", notes = "添加策略类")
    public String addPolicyClass() {
        return null;
    }

    @PostMapping("addPermission")
    @ApiOperation(value = "增加权限", notes = "增加权限")
    public CommonResponse<Boolean> addAccess(@RequestParam String loginUserName, @RequestBody AccessRelationAdminOpt accessRelationAdminOpt) {
        boolean isSuccess = adminService.addPermission(loginUserName, accessRelationAdminOpt);
        return CommonResponse.response(isSuccess);
    }

    @GetMapping("findUserAndPermissions/{user}")
    @ApiOperation(value = "查询用户及权限集", notes = "根据用户名查询用户及权限集")
    public CommonResponse<UserPermission> findUserAndPermissions(@PathVariable("user") String name) {
        UserPermission userPermission = adminService.findUserAndPermissions(name);
        return CommonResponse.success(userPermission);
    }
}
