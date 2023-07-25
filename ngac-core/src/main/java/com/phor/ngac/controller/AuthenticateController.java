package com.phor.ngac.controller;

import com.phor.ngac.service.AuthenticateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/authenticate")
public class AuthenticateController {
    @Resource
    private AuthenticateService authenticateService;

    @GetMapping("visitResource/{user}/{resource}/{action}")
    public boolean visitResource(@PathVariable("user") String name,
                                 @PathVariable("resource") String resource,
                                 @PathVariable("action") String action) {
        return authenticateService.visitResource(name, resource, action);
    }

    @GetMapping("findUser/{user}")
    public String findUser(@PathVariable("user") String name) {
        return authenticateService.findUser(name);
    }
}
