package com.phor.ngac.service;

import com.phor.ngac.neo4j.entity.node.u.User;

public interface AuthenticateService {
    boolean visitMenu(String name, String resource, String action);

    User findUser(String name);
}
