package com.phor.ngac.controller;

import com.phor.ngac.core.pip.Neo4jPip;
import com.phor.ngac.entity.vo.requests.AlterPermissionRequestVo;
import com.phor.ngac.entity.vo.requests.UserMenuAuthRequestVo;
import com.phor.ngac.entity.vo.responses.CommonResponse;
import com.phor.ngac.service.AuthenticateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "认证接口")
@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
    @Resource
    private AuthenticateService authenticateService;

    @Resource
    private Neo4jPip neo4jPip;

    @PostMapping("visitMenu")
    @ApiOperation(value = "访问菜单", notes = "访问菜单鉴权")
    public CommonResponse<String> visitMenu(@RequestBody @Validated UserMenuAuthRequestVo requestVo) {
        boolean isAuthorized = authenticateService.visitMenu(requestVo);
        return CommonResponse.success(isAuthorized ? "authorized" : "unauthorized");
    }

    @PostMapping("alterPermission")
    @ApiOperation(value = "修改权限", notes = "修改权限")
    public CommonResponse<String> alterPermission(@RequestBody @Validated AlterPermissionRequestVo requestVo) {
        boolean isAuthorized = authenticateService.alterPermission(requestVo);
        return CommonResponse.success(isAuthorized ? "authorized" : "unauthorized");
    }
}
