package com.api_call.scheduler;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.api_call.service.SchedulerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Scheduler {

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private Executor taskExecutor;

	private static final int TOTAL_CALLS = 100000;
	private static final int CALLS_PER_MINUTE = TOTAL_CALLS / 60;

	@Scheduled(fixedRate = 600) // Runs every minute
	public void scheduleApiCalls() {
		for (int i = 0; i < CALLS_PER_MINUTE; i++) {
			taskExecutor.execute(schedulerService::callExternalApi);
		}
	}

}
