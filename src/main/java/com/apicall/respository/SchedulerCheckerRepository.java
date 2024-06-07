package com.apicall.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apicall.model.SchedulerChecker;

public interface SchedulerCheckerRepository extends JpaRepository<SchedulerChecker, Long> {

}
