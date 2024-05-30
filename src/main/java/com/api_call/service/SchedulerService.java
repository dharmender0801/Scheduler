package com.api_call.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

	@Autowired
	RestTemplate restTemplate;

	Integer check = 0;

	@Autowired
	private ApplicationContext context;

	public void callExternalApi() {
		log.info("{}",check);
//		String url = "https://api.rainforestapi.com/request?api_key=D383BC46A1CA450B950CAE290006ECCE&type=product&url=https%3A//www.amazon.com/ProtoArc-EM11-NL-Rechargeable-Multi-Device/dp/B0CX18LHWS";
//		try {
//			String response = restTemplate.getForObject(url, String.class);
////			System.out.println("Response: " + response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		log.info("This Test : {}", url);
		check++;
		if (check == 10000) {
			System.out.println(check);
			SpringApplication.exit(context, () -> 0);

		}
	}

}
