package com.phor.ngac.controller;

import com.phor.ngac.consts.PolicyClassEnum;
import com.phor.ngac.core.pip.Neo4jPip;
import com.phor.ngac.entity.responses.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private Neo4jPip neo4jPip;

    @GetMapping
    public CommonResponse<String> test() {
        neo4jPip.getPolicyRelatedResources(PolicyClassEnum.MENU_ROLE);
        return CommonResponse.success("test");
    }
}
