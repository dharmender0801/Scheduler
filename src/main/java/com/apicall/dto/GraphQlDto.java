package com.apicall.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GraphQlDto {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String query;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Variables variables;

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Variables {
		private String productId;
		private List<Variants> variants;
		private Input input;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Input {
		private String reason;
		private List<Quantities> setQuantities;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Variants {
		private String id;
		private Float price;
		private Float compareAtPrice;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Quantities {
		private String inventoryItemId;
		private String locationId;
		private Long quantity;
	}

}
