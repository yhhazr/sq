package com.sz7road.web.task;

import java.util.Date;
import java.util.HashMap;
import java.util.TimerTask;

import org.apache.log4j.LogManager;

import com.opensymphony.xwork2.util.logging.Logger;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gameEnterInfo.GameEnterInfo;
import com.sz7road.web.service.GameEnterInfoStatisticsService;

public class InsertGameEnterInfoTimerTask extends TimerTask{
	@Override
	public void run() {
		// TODO 执行定时任务
		Date date =new Date();
		String now=DateUtil.format(date,DateUtil.DATE_FORMAT);
		GameEnterInfoStatisticsService geiss=ServiceLocator.getGameEnterInfoStatisticsService();
		HashMap<String, GameEnterInfo> map=geiss.getCacheMap();
		try {
			if(map.get(AppConstants.ENTER_POSITION1) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION1,now);
			if(map.get(AppConstants.ENTER_POSITION2) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION2,now);
			if(map.get(AppConstants.ENTER_POSITION3) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION3,now);
			if(map.get(AppConstants.ENTER_POSITION4) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION4,now);
			if(map.get(AppConstants.ENTER_POSITION5) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION5,now);
			if(map.get(AppConstants.ENTER_POSITION6) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION6,now);
			if(map.get(AppConstants.ENTER_POSITION7) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION7,now);
			if(map.get(AppConstants.ENTER_POSITION8) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION8,now);
			if(map.get(AppConstants.ENTER_POSITION9) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION9,now);
			if(map.get(AppConstants.ENTER_POSITION10) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION10,now);
			if(map.get(AppConstants.ENTER_POSITION11) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION11,now);
			if(map.get(AppConstants.ENTER_POSITION12) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION12,now);
			if(map.get(AppConstants.ENTER_POSITION13) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION13,now);
			if(map.get(AppConstants.ENTER_POSITION14) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION14,now);
			if(map.get(AppConstants.ENTER_POSITION15) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION15,now);
			if(map.get(AppConstants.ENTER_POSITION16) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION16,now);
			if(map.get(AppConstants.ENTER_POSITION17) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION17,now);
			if(map.get(AppConstants.ENTER_POSITION18) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION18,now);
			if(map.get(AppConstants.ENTER_POSITION19) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION19,now);
			if(map.get(AppConstants.ENTER_POSITION20) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION20,now);
			if(map.get(AppConstants.ENTER_POSITION21) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION21,now);
			if(map.get(AppConstants.ENTER_POSITION22) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION22,now);
			if(map.get(AppConstants.ENTER_POSITION23) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION23,now);
			if(map.get(AppConstants.ENTER_POSITION24) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION24,now);
			if(map.get(AppConstants.ENTER_POSITION25) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION25,now);
			if(map.get(AppConstants.ENTER_POSITION26) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION26,now);
			if(map.get(AppConstants.ENTER_POSITION27) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION27,now);
			if(map.get(AppConstants.ENTER_POSITION28) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION28,now);
			if(map.get(AppConstants.ENTER_POSITION29) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION29,now);
			if(map.get(AppConstants.ENTER_POSITION30) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION30,now);
			if(map.get(AppConstants.ENTER_POSITION31) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION31,now);
			if(map.get(AppConstants.ENTER_POSITION32) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION32,now);
			if(map.get(AppConstants.ENTER_POSITION33) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION33,now);
			if(map.get(AppConstants.ENTER_POSITION34) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION34,now);
			if(map.get(AppConstants.ENTER_POSITION35) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION35,now);
			if(map.get(AppConstants.ENTER_POSITION36) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION36,now);
			if(map.get(AppConstants.ENTER_POSITION37) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION37,now);
			if(map.get(AppConstants.ENTER_POSITION38) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION38,now);
			if(map.get(AppConstants.ENTER_POSITION39) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION39,now);
			if(map.get(AppConstants.ENTER_POSITION40) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION40,now);
			if(map.get(AppConstants.ENTER_POSITION41) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION41,now);
			if(map.get(AppConstants.ENTER_POSITION42) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION42,now);
			if(map.get(AppConstants.ENTER_POSITION43) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION43,now);
			if(map.get(AppConstants.ENTER_POSITION44) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION44,now);
			if(map.get(AppConstants.ENTER_POSITION45) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION45,now);
			if(map.get(AppConstants.ENTER_POSITION46) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION46,now);
			if(map.get(AppConstants.ENTER_POSITION47) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION47,now);
			if(map.get(AppConstants.ENTER_POSITION48) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION48,now);
			if(map.get(AppConstants.ENTER_POSITION49) != null)
					geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION49,now);
			if(map.get(AppConstants.ENTER_POSITION50) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION50,now);
			if(map.get(AppConstants.ENTER_POSITION51) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION51,now);
			if(map.get(AppConstants.ENTER_POSITION52) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION52,now);
			if(map.get(AppConstants.ENTER_POSITION53) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION53,now);
			if(map.get(AppConstants.ENTER_POSITION54) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION54,now);
			if(map.get(AppConstants.ENTER_POSITION55) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION55,now);
			if(map.get(AppConstants.ENTER_POSITION56) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION56,now);
			if(map.get(AppConstants.ENTER_POSITION57) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION57,now);
			if(map.get(AppConstants.ENTER_POSITION58) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION58,now);
			if(map.get(AppConstants.ENTER_POSITION59) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION59,now);
			if(map.get(AppConstants.ENTER_POSITION60) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION60,now);
			if(map.get(AppConstants.ENTER_POSITION61) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION61,now);
			if(map.get(AppConstants.ENTER_POSITION62) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION62,now);
			if(map.get(AppConstants.ENTER_POSITION63) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION63,now);
			if(map.get(AppConstants.ENTER_POSITION64) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION64,now);
			if(map.get(AppConstants.ENTER_POSITION65) != null)
				geiss.insertGameEnterInfoStatistics(AppConstants.ENTER_POSITION65,now);
		} catch (Exception e) {
			
		}
	}

}
