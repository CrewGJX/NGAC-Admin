package com.phor.ngac.pip;

import com.phor.ngac.neo4j.entity.User;

public interface PolicyInformationPoint {
    User findUser(String name);
}
