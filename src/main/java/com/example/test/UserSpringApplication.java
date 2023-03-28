package com.example.test;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "Sms Service",
                version = "v1.0.0",
                description = "Sms Service Api"
        )

)
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class UserSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSpringApplication.class, args);

    }

}
