package com.phor.ngac.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员操作
 *
 * @date 2023/8/2
 */
@RestController
@RequestMapping("/admin")
public class AdministrativeOptController {
    @PostMapping("addUser")
    public String addUser() {
        return null;
    }

    @PostMapping("addRole")
    public String addRole() {
        return null;
    }

    @PostMapping("addPolicyClass")
    public String addPolicyClass() {
        return null;
    }

    @PostMapping("addAuthorization")
    public String addAuthorization() {
        return null;
    }
}
