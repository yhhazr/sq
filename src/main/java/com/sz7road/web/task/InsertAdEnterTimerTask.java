package com.sz7road.web.task;

import java.util.TimerTask;

import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.service.AdStatisticsService;

public class InsertAdEnterTimerTask extends TimerTask {

	@Override
	public void run() {
		AdStatisticsService adStatisticsService = ServiceLocator.getAdStatisticsService();
		try {
			adStatisticsService.insertAdStatistics();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
