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
import com.apicall.dto.GraphQlDto.Variables;
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
//		log.info("Buy Box Winner : {} , Product Variant Master : {},User Master : {}", buyboxWinner, n, userMaster);
		GraphQlDto graphQlDto = new GraphQlDto();
		String query = "mutation batchProductUpdates( $productId: ID!, $variants: "
				+ "[ProductVariantsBulkInput!]!, $input: InventorySetOnHandQuantitiesInput! ) "
				+ "{ productVariantsBulkUpdate( productId: $productId, variants: $variants )"
				+ " { product { id } userErrors { field message } } inventorySetOnHandQuantities(input: $input) "
				+ "{ inventoryAdjustmentGroup { id } userErrors { field message } } }";
		graphQlDto.setQuery(query);
		Variables variables = new Variables();
		variables.setProductId("gid://shopify/Product/" + n.getShopifyProductId());
		if (Boolean.TRUE.equals(Objects.nonNull(buyboxWinner.getPrice()))) {
			List<Variants> variants = getVariantList(buyboxWinner, n);
			variables.setVariants(variants);
		}
		List<Quantities> quantities = getQuantityList(buyboxWinner.getAvailability(), userMaster, n);
		Input input = new Input();
		input.setReason("correction");
		input.setSetQuantities(quantities);
		variables.setInput(input);
		graphQlDto.setVariables(variables);
		PushingTOGraphQl(graphQlDto, userMaster);

	}

	private void PushingTOGraphQl(GraphQlDto graphQlDto, UserMaster userMaster) {
		try {
			String graphQlUrl = "https://" + userMaster.getMyshopifyDomain() + "/admin/api/2024-01/graphql.json";
			log.info("End Point : {}", graphQlUrl);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Shopify-Access-Token", userMaster.getToken());
			headers.set("Accept", "application/json");
			log.info("Request Body is sending : {}", serializeToJson(graphQlDto));
			HttpEntity<GraphQlDto> requestEntity = new HttpEntity<>(graphQlDto, headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(graphQlUrl, HttpMethod.POST, requestEntity,
					String.class);
			log.info("Response From Graph Ql : {}", responseEntity.getBody());
		} catch (Exception e) {
			log.error("Exception : {}", e);
		}

	}

	private List<Quantities> getQuantityList(Availability availability, UserMaster userMaster, ProductVariantMaster n) {
		List<Quantities> quantities = new ArrayList<>();
		Quantities quantities2 = new Quantities();
		quantities2.setInventoryItemId("gid://shopify/InventoryItem/" + n.getInventoryItemId());
		quantities2.setLocationId("gid://shopify/Location/" + userMaster.getLocationId());
		quantities2.setQuantity(Boolean.TRUE.equals(Objects.nonNull(availability)) ? generateRandomNumber() : 0L);
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
