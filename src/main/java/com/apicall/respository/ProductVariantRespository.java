package com.apicall.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.apicall.model.ProductVariantMaster;

public interface ProductVariantRespository extends JpaRepository<ProductVariantMaster, Long> {

	@Procedure(name = "CALL update_product_in_thread")
	void update_product_in_thread();

	List<ProductVariantMaster> findByUserIdAndThreadIdAndStatus(Long userId, Long threadNo, boolean b);
}
