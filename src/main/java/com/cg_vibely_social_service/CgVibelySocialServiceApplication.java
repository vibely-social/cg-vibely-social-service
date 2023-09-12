package com.cg_vibely_social_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
//@OpenAPIDefinition(
//        info = @Info(title = "Vibely Social Web API", version = "1.0-alpha", description = "API endpoint used to get data from server")
//)
public class CgVibelySocialServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CgVibelySocialServiceApplication.class, args);
    }

}
