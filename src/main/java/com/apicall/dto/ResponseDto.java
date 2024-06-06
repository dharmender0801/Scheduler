package com.apicall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto {
	@JsonProperty("product")
	private Product product;

	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public static class Product {
		@JsonProperty("title")
		private String title;
		@JsonProperty("buybox_winner")
		private buyBoxWinner buyboxWinner;

		@AllArgsConstructor
		@NoArgsConstructor
		@Data
		public static class buyBoxWinner {
			@JsonProperty("availability")
			private Availability availability;
			@JsonProperty("price")
			private RawData price;
			@JsonProperty("rrp")
			private RawData rrp;

			@AllArgsConstructor
			@NoArgsConstructor
			@Data
			public static class Availability {
				@JsonProperty("type")
				private String type;
				@JsonProperty("raw")
				private String raw;
				@JsonProperty("dispatch_days")
				private Integer dispatchDays;
			}

			@AllArgsConstructor
			@NoArgsConstructor
			@Data
			public static class RawData {
				@JsonProperty("symbol")
				private String symbol;
				@JsonProperty("value")
				private Float value;
				@JsonProperty("currency")
				private String currency;
				@JsonProperty("raw")
				private String raw;
			}
		}
	}

}
