package com.phor.ngac.service.impl;

import com.phor.ngac.core.pap.PolicyAdministrationPoint;
import com.phor.ngac.core.pep.PolicyEnforcementPoint;
import com.phor.ngac.neo4j.entity.node.u.User;
import com.phor.ngac.service.AuthenticateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    @Resource
    private PolicyEnforcementPoint pep;

    @Resource
    private PolicyAdministrationPoint neo4jPap;

    @Override
    public boolean visitMenu(String userName, String resource, String action) {
        return pep.enforcePolicy(userName, resource, action);
    }

    @Override
    public User findUser(String name) {
        return neo4jPap.findUser(name);
    }
}
