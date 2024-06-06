package com.apicall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apicall.dto.GraphQlDto;
import com.apicall.dto.GraphQlDto.Input;
import com.apicall.dto.GraphQlDto.Quantities;
import com.apicall.dto.GraphQlDto.Variants;
import com.apicall.dto.ResponseDto.Product.buyBoxWinner;
import com.apicall.dto.ResponseDto.Product.buyBoxWinner.Availability;
import com.apicall.model.ProductVariantMaster;
import com.apicall.model.UserMaster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GraphQlService {

	@Autowired
	RestTemplate restTemplate;

	public void PushToGraphQl(buyBoxWinner buyboxWinner, ProductVariantMaster n, UserMaster userMaster) {
		log.info("Buy Box Winner : {} , Product Variant Master : {},User Master : {}", buyboxWinner, n, userMaster);
		GraphQlDto graphQlDto = new GraphQlDto();
		graphQlDto.setProductId("gid://shopify/Product/" + n.getShopifyProductId());
		if (Boolean.TRUE.equals(Objects.nonNull(buyboxWinner.getPrice()))) {
			List<Variants> variants = getVariantList(buyboxWinner, n);
			graphQlDto.setVariants(variants);
		}
		if (Boolean.TRUE.equals(Objects.nonNull(buyboxWinner.getAvailability()))) {
			List<Quantities> quantities = getQuantityList(buyboxWinner.getAvailability(), userMaster, n);
			Input input = new Input();
			input.setReason("correction");
			input.setSetQuantities(quantities);
			graphQlDto.setInput(input);
		}

		PushingTOGraphQl(graphQlDto, userMaster);

	}

	private void PushingTOGraphQl(GraphQlDto graphQlDto, UserMaster userMaster) {
		String graphQlUrl = "https://prime-japan.myshopify.com/admin/api/2024-01/graphql.json";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-Shopify-Access-Token", userMaster.getToken());

		String requestBody = "{\"query\":\"mutation " + serializeToJson(graphQlDto) + "\"}";
		log.info("Request Body is sending : {}", requestBody);
		HttpEntity<GraphQlDto> requestEntity = new HttpEntity<>(graphQlDto, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(graphQlUrl, HttpMethod.POST, requestEntity,
				String.class);
		log.info("Response From Graph Ql : {}", responseEntity);

	}

	private List<Quantities> getQuantityList(Availability availability, UserMaster userMaster, ProductVariantMaster n) {
		List<Quantities> quantities = new ArrayList<>();
		Quantities quantities2 = new Quantities();
		quantities2.setInventoryItemId("gid://shopify/InventoryItem/" + n.getInventoryItemId());
		quantities2.setLocationId("gid://shopify/Location/" + userMaster.getLocationId());
		quantities2.setQuantity(generateRandomNumber());
		quantities.add(quantities2);
		return quantities;
	}

	private String serializeToJson(GraphQlDto graphQlDto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(graphQlDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializing GraphQlDto to JSON", e);
		}
	}

	private List<Variants> getVariantList(buyBoxWinner buyboxWinner, ProductVariantMaster n) {
		List<Variants> variants = new ArrayList<>();
		Variants variants2 = new Variants();
		variants2.setId("gid://shopify/ProductVariant/" + n.getShopifyVariantId());
		variants2.setPrice(buyboxWinner.getPrice().getValue());
		if (Boolean.TRUE.equals(Objects.nonNull(buyboxWinner.getRrp()))) {
			variants2.setCompareAtPrice(buyboxWinner.getRrp().getValue());
		}
		variants.add(variants2);
		return variants;
	}

	public static Long generateRandomNumber() {
		Random random = new Random();
		return random.nextLong(9) + 21;
	}

}
