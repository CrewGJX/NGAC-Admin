package com.phor.ngac.neo4j.mapper;

import com.phor.ngac.neo4j.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, String> {
    User findByName(String name);
}
