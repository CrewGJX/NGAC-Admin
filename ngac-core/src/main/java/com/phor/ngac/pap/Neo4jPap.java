package com.phor.ngac.pap;

import com.phor.ngac.neo4j.entity.User;
import com.phor.ngac.pip.Neo4jPip;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Neo4jPap implements PolicyAdministrationPoint {

    @Resource
    private Neo4jPip pip;

    @Override
    public User findUser(String name) {
        return pip.findUser(name);
    }
}
