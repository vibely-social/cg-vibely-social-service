package com.cg_vibely_social_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CgVibelySocialServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CgVibelySocialServiceApplication.class, args);
	}

}
