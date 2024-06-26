package com.apicall.scheduler;

import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apicall.model.SchedulerChecker;
import com.apicall.model.ThreadMaster;
import com.apicall.respository.ProductVariantRespository;
import com.apicall.respository.SchedulerCheckerRepository;
import com.apicall.respository.ThreadMasterRepository;
import com.apicall.service.SchedulerService;

@Component
@Service
public class Scheduler {
	@Autowired
	private SchedulerService schedulerService;
	@Autowired
	private Executor taskExecutor;

	@Autowired
	private ProductVariantRespository productVariantRespository;

	@Autowired
	SchedulerCheckerRepository checkerRepository;

	@Autowired
	ThreadMasterRepository masterReposiThreadMasterRepository;

	@Scheduled(fixedRate = 60000)
	public void scheduleApiCalls() {
		SchedulerChecker checker = checkerRepository.findById(1L).get();
		if (Boolean.TRUE.equals(checker.getStatus())) {
			List<ThreadMaster> threadMasters = masterReposiThreadMasterRepository.findAll();
			threadMasters.parallelStream().forEach(n -> {
				taskExecutor.execute(() -> schedulerService.callExternalApi(n));
			});
		}
	}

	@Scheduled(cron = "0 0 10 * * ?")
	public void procedureCall() {
		SchedulerChecker checker = checkerRepository.findById(1L).get();
		if (Boolean.TRUE.equals(checker.getStatus())) {
			productVariantRespository.product_variant_thread();
		}
	}

}
