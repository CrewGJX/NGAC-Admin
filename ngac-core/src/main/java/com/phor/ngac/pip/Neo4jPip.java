package com.phor.ngac.pip;

import com.phor.ngac.neo4j.entity.User;
import com.phor.ngac.neo4j.mapper.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Neo4jPip implements PolicyInformationPoint {
    @Resource
    private UserRepository userRepository;

    @Override
    public User findUser(String name) {
        return userRepository.findByName(name);
    }
}
