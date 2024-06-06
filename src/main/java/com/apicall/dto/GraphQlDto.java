package com.apicall.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GraphQlDto {

	private String productId;
	private List<Variants> variants;
	private Input input;

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Input {
		private String reason;
		private List<Quantities> setQuantities;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Variants {
		private String id;
		private Float price;
		private Float compareAtPrice;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Quantities {
		private String inventoryItemId;
		private String locationId;
		private Long quantity;
	}

}
