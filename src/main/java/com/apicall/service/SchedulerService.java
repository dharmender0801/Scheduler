package com.apicall.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apicall.dto.ResponseDto;
import com.apicall.model.ProductVariantMaster;
import com.apicall.model.ThreadMaster;
import com.apicall.model.UserMaster;
import com.apicall.respository.ProductVariantRespository;
import com.apicall.respository.UserMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchedulerService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ProductVariantRespository productVariantRespository;
	@Autowired
	UserMasterRepository masterRepository;
	@Autowired
	GraphQlService graphQlService;

	public void callExternalApi(ThreadMaster thread) {

		UserMaster userMaster = getUser(thread.getUserId());
		if (Boolean.TRUE.equals(Objects.nonNull(userMaster))) {
			List<ProductVariantMaster> list = productVariantRespository
					.findByUserIdAndThreadIdAndStatus(thread.getUserId(), thread.getThreadNo(), true);
			list.parallelStream().forEach(n -> {
				callRestEndPoint(n, userMaster);
			});
		}

	}

	public void callRestEndPoint(ProductVariantMaster n, UserMaster userMaster) {
		String Url = "https://api.asindataapi.com/request?api_key=72E0DC05F3BF49DA8CA967786650063D&type=product&url="
				+ n.getProductLink();
		ResponseDto responseDto = restTemplate.getForObject(Url, ResponseDto.class);
		log.info("Amazon Response : {}", responseDto);
		if (Boolean.TRUE.equals(Objects.nonNull(responseDto))
				&& Boolean.TRUE.equals(Objects.nonNull(responseDto.getProduct()))
				&& Boolean.TRUE.equals(Objects.nonNull(responseDto.getProduct().getBuyboxWinner()))
				&& Boolean.TRUE.equals(Objects.nonNull(responseDto.getProduct().getBuyboxWinner().getPrice()))

		) {
			graphQlService.PushToGraphQl(responseDto.getProduct().getBuyboxWinner(), n, userMaster);
			userMaster.setLastRunDate(new Date());
			masterRepository.save(userMaster);

		}
		n.setStatus(false);
		productVariantRespository.save(n);

	}

//	@Cacheable(value = "id")
	public UserMaster getUser(Long id) {
		return masterRepository.findByIdAndNextRunDate(id, new Date());
	}

}
