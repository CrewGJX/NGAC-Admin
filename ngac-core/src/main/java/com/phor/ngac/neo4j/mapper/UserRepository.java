package com.phor.ngac.neo4j.mapper;

import com.phor.ngac.neo4j.entity.node.u.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {
    User findByName(String name);
}
