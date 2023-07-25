package com.phor.ngac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.phor.ngac.neo4j.mapper")
public class NgacCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(NgacCoreApplication.class, args);
    }
}
