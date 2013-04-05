package com.sz7road.web.action.admin.testmanage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.service.GameServerService;

public class RechargeTestAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	static final String RECHARGE_KEY = "SQTWO-S67roadfTestCharg-356yghs3y-06578-6HJNK-SG11-interf-111SGEEN";
	static final int SQ_GAME_ID = 1;
	static final int MIN_AMOUNT = 10;
	
	private String userId;
	private int gameId;
	private int gameZoneId;
	private String amount;
	private String result;
	
	private List<PlatformGameServer> serverList = new ArrayList<PlatformGameServer>();
	
	private static GameServerService gameServerServ = ServiceLocator.getGameServerService();
	private static PlatformClient client = PlatformClient.getInstance();
	private static final Logger logger = LogManager.getLogger(RechargeTestAction.class);

	public String testPrepare() {
		try {
			//http://manager.7road.com/GameWithServer?action=list&_g=1&pageIndex=1&pageSize=9999&order=serverNo&sort=0
			serverList = gameServerServ.getAllGameServerForBackground();
		} catch (Exception e) {
			logger.error("调用平台的后台接口获得全部服务区异常。", e);
		}
		setDefaultProp();
		amount = String.valueOf(MIN_AMOUNT);
		
		return SUCCESS;
	}
	
	
	public String testSubmit() throws Exception {
		gameId = SQ_GAME_ID;
		int isLegal = checkParms(userId, gameId, gameZoneId, amount);
		if(isLegal != 0) {
			result = "false";
		}else {
			String sign = this.buildRechargeTestSign(userId, gameId, gameZoneId, amount);
			String resp = client.testRecharge(sign, userId, gameId, gameZoneId, amount);
			result = "true";
		}
		
		return SUCCESS;
	}
	
	//构造充值测试sign的值
	public String buildRechargeTestSign(String userId, int gameId, int gameZoneId, String amount) {
		
		return MD5Util.getMD5String("u=" + userId + "|_g=" + gameId + "|_z=" + gameZoneId + "|amount=" + amount + "|" + RECHARGE_KEY);
	}
	
	//验证参数是否正确
	public int checkParms(String userId, int gameId, int gameZoneId, String amount) {
		int result = 0;
		if(StringUtils.isBlank(userId) || !SystemConfig.getProperty("background.test.enterGame.username").equals(userId)) {
			result += 1;
		}else if(!(StringUtils.isNumeric(amount) && Integer.parseInt(amount) >= MIN_AMOUNT)) {
			result += 2;
		}else if(gameId != SQ_GAME_ID) {
			result += 4;
		}else if(gameZoneId == 0) {
			result += 8;
		}
		
		if(result == 0) {
			this.amount = String.valueOf(Integer.parseInt(amount));
		}

		return result;
	}

	//设置固定的参数值
	private void setDefaultProp() {
		userId = SystemConfig.getProperty("background.test.enterGame.username");
		gameId = SQ_GAME_ID;
	}
	
	
	/*
	 * getter & setter
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getGameZoneId() {
		return gameZoneId;
	}

	public void setGameZoneId(int gameZoneId) {
		this.gameZoneId = gameZoneId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public List<PlatformGameServer> getServerList() {
		return serverList;
	}

	public void setServerList(List<PlatformGameServer> serverList) {
		this.serverList = serverList;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	

}
