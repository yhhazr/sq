package com.sz7road.web.action.admin.testmanage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.PlatformClient;

public class EnterGameTestAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final int GAME_ID = 1; 
	private static final String ENTER_KEY = "SQTWO-S67roadfGEnterKey-356yghs3y-06578-6HJNK-SG11-interf-111SGEEN";

	private int gameZoneId;
	private String result;
	
	private static PlatformClient client = PlatformClient.getInstance();
	private static final Logger logger = LogManager.getLogger(EnterGameTestAction.class);
	

	//构造充值测试sign的值
	public String buildRechargeTestSign(int gameZoneId) {
		
		return MD5Util.getMD5String("gid=" + GAME_ID + "|zid=" + gameZoneId + "|" + ENTER_KEY);
	}
	
	public int checkParams(int gameZoneId) {
		int isLegal = 0;
		if(gameZoneId <= 0) {
			isLegal += 1;
		}
		
		return isLegal;
	}

	/*
	 * getter setter
	 */
	public int getGameZoneId() {
		return gameZoneId;
	}

	public void setGameZoneId(int gameZoneId) {
		this.gameZoneId = gameZoneId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
