package com.apicall.respository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.apicall.model.UserMaster;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

	@Query("SELECT u FROM UserMaster u WHERE u.id = :id AND u.nextRunDate = DATE(:date)")
	UserMaster findByIdAndNextRunDate(Long id, Date date);

}
