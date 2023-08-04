package com.phor.ngac.controller;

import com.phor.ngac.core.pip.Neo4jPip;
import com.phor.ngac.entity.requests.UserRequestVo;
import com.phor.ngac.entity.responses.CommonResponse;
import com.phor.ngac.neo4j.entity.node.u.User;
import com.phor.ngac.service.AuthenticateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public CommonResponse<Boolean> visitResource(@RequestBody @Validated UserRequestVo requestVo) {
        boolean isAuthorized = authenticateService.visitMenu(requestVo.getName(), requestVo.getResource(), requestVo.getAction());
        return CommonResponse.success(isAuthorized);
    }

    @GetMapping("findUser/{user}")
    @ApiOperation(value = "查询用户", notes = "根据用户名查询用户")
    public CommonResponse<User> findUser(@PathVariable("user") String name) {
        User user = authenticateService.findUser(name);
        return CommonResponse.success(user);
    }
}
