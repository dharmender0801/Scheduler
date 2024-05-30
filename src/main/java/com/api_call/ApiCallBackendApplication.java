package com.api_call;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiCallBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCallBackendApplication.class, args);
	}

}
