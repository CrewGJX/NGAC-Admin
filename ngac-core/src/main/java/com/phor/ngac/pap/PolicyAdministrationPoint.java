package com.phor.ngac.pap;

import com.phor.ngac.neo4j.entity.User;

public interface PolicyAdministrationPoint {
    User findUser(String name);
}
