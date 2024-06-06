package com.apicall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
@EnableWebMvc
@EnableSpringDataWebSupport
public class ApiCallBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCallBackendApplication.class, args);
	}

}
