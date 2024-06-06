package com.apicall.scheduler;

import java.util.List;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.apicall.model.ThreadMaster;
import com.apicall.respository.ProductVariantRespository;
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
	ThreadMasterRepository masterReposiThreadMasterRepository;

	@Scheduled(fixedRate = 60000)
	public void scheduleApiCalls() {

		List<ThreadMaster> threadMasters = masterReposiThreadMasterRepository.findAll();
		threadMasters.parallelStream().forEach(n -> {
			taskExecutor.execute(() -> schedulerService.callExternalApi(n));
		});
	}

}
