package com.phor.ngac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@EnableWebMvc
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfiguration {

    @Bean(value = "ngacApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.phor.ngac.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title("ngac接口")
                .description("<div style='font-size:14px;color:red;'>ngac接口</div>")
                .termsOfServiceUrl("")
                .contact(new Contact("phor", "", ""))
                .version("1.0")
                .build();
    }


}