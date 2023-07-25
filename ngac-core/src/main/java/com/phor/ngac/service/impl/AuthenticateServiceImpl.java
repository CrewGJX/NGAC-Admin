package com.phor.ngac.service.impl;

import com.phor.ngac.pep.PolicyEnforcementPoint;
import com.phor.ngac.service.AuthenticateService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

    @Resource
    private PolicyEnforcementPoint customPep;

    @PostConstruct
    public void init() {
    }

    @Override
    public boolean visitResource(String name, String resource, String action) {
        customPep.enforcePolicy(name, resource, action);
        return false;
    }

    @Override
    public String findUser(String name) {
        return null;
    }
}
