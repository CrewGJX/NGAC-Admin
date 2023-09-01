package com.phor.ngac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "com.phor.ngac.mapper")
@EnableDiscoveryClient
public class NgacCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(NgacCoreApplication.class, args);
    }
}
