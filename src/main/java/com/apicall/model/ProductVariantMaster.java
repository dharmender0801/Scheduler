package com.apicall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@Entity
@Table(name = ProductVariantMaster.TABLE_NAME)
@ToString
@NamedStoredProcedureQuery(name = "update_product_in_thread", procedureName = "update_product_in_thread")
public class ProductVariantMaster {

	public static final String TABLE_NAME = "daily_sync_product_variants";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long storeId;
	private Long shopifyProductId;
	private Long shopifyVariantId;
	private String variantTitle;
	private String price;
	private String compareAtPrice;
	private String sku;
	private Long inventoryItemId;
	private String inventoryQuantity;
	private String productLink;
	private Long userId;
	private Long threadId;
	private Boolean status;

}
