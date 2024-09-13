package com.example.demo.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value(("${open.api.title}"))
    private String title;
    @Value(("${open.api.version}"))
    private String version;

    @Bean
    public OpenAPI customApi(){
        return new OpenAPI()
                .info(new Info()
                .title(title)
                .version(version)
                .description("This is sample Api")
                .license(new License().name("AN NGUYEN").url("Apache"))
        )
                ;
    }
    @Bean
    public GroupedOpenApi publicApi2(){
        return GroupedOpenApi.builder()
                .group("Student")
                .pathsToMatch("/api/student/**")
                .build();
    }
    @Bean
    public GroupedOpenApi publicApi1(){
        return GroupedOpenApi.builder()
                .group("Admin")
                .pathsToMatch("/api/admin/**")
                .build();
    }
}