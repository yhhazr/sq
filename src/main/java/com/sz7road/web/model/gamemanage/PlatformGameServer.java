package com.sz7road.web.model.gamemanage;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sz7road.web.common.util.DateUtil;



public class PlatformGameServer {
	private String id;
	private String serverNo;
	private String serverName;
	private String gameId;
	private String serverStatus;
	private String createTime;
	private String openingTime;
	private String recommand;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServerNo() {
		return serverNo;
	}
	public void setServerNo(String serverNo) {
		this.serverNo = serverNo;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerStatus() {
		return serverStatus;
	}
	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getRecommand() {
		return recommand;
	}
	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}

	

	
	
	

//	public void setOpeningTime(Long openingTime) {
//		this.openingTimeStr = DateUtil.format(new Date(openingTime));
//		this.openingTime = openingTime;
//	}

//	public void setOpeningTimeStr(String openingTimeStr) {
//		this.openingTime = DateUtil.parse(openingTimeStr).getTime();
//		this.openingTimeStr = openingTimeStr;
//	}


	

}
