package com.apicall.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = UserMaster.TABLE_NAME)
@ToString
public class UserMaster {
	public static final String TABLE_NAME = "daily_sync_users2";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String myshopifyDomain;
	private String storeId;
	private String shopCreatedAt;
	private String shopOwner;
	private String token;
	private String guid;
	private Integer isPaid;
	private String planName;
	private String credits;
	private String appPlan;
	private String appPlanId;
	private String billingDate;
	private String currency;
	private String countryCode;
	private String rememberToken;
	private Integer syncFrequency;
	private Long locationId;
	@Column(columnDefinition = "DATE")
	private Date lastRunDate;
	@Column(columnDefinition = "DATE")
	private Date nextRunDate;

}
