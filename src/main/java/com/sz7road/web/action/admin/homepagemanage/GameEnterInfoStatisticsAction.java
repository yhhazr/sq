package com.sz7road.web.action.admin.homepagemanage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.service.GameEnterInfoStatisticsService;

public class GameEnterInfoStatisticsAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GameEnterInfoStatisticsAction.class);
	private String positionKey;
	private boolean json;
	public String getGameEnterInfo(){
			json=false;
		GameEnterInfoStatisticsService geiss=ServiceLocator.getGameEnterInfoStatisticsService();
		try {
			json=geiss.countTimes(positionKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info("getGameEnterInfo exception"+e.toString());
		}

			return SUCCESS;
	}
	public String getPositionKey() {
		return positionKey;
	}

	public void setPositionKey(String positionKey) {
		this.positionKey = positionKey;
	}
	public boolean isJson() {
		return json;
	}
	public void setJson(boolean json) {
		this.json = json;
	}
	
	
}
