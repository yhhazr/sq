package com.sz7road.web.common.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.sz7road.web.service.GeneHtmlService;
import com.sz7road.web.service.impl.GeneHtmlServiceImpl;

public class AutoGenePlayerRank {
	
	private Logger logger = Logger.getLogger(AutoGenePlayerRank.class); 

	public AutoGenePlayerRank(){
		start();
	}
	
	public void start(){
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			GeneHtmlService geneHtmlService = new GeneHtmlServiceImpl();
			@Override
			public void run() {
				try {
					geneHtmlService.genePlayerRank("/common/rank.ftl", "");
					logger.info("自动生成玩家排行成功！");
				} catch (Exception e) {
					logger.error("自动生成玩家排行错误：" + e);
				}
				
			}
		};
		long period = 24 * 60 * 60 * 1000;
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 2);
		calendar.set(Calendar.MINUTE, 30);
		Date startDate = new Date(calendar.getTimeInMillis());
		if(System.currentTimeMillis() > startDate.getTime()){
			startDate = new Date(startDate.getTime() + period);
		}
		timer.schedule(task, startDate, period);
	}
}
