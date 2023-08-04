package com.phor.ngac.neo4j.mapper;

import com.phor.ngac.neo4j.entity.node.pc.PolicyClass;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PolicyRepository extends Neo4jRepository<PolicyClass, Long> {
    PolicyClass findByName(String name);
}
