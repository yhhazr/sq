package com.sz7road.web.model.onlineUser;

import com.sz7road.web.model.gamemanage.PlatformGameServer;

public class LoginInfo {
	private String respFlag;
	private PlatformGameServer gs;
	private OnlineUser user;


	public String getRespFlag() {
		return respFlag;
	}
	public void setRespFlag(String respFlag) {
		this.respFlag = respFlag;
	}
	public PlatformGameServer getGs() {
		return gs;
	}
	public void setGs(PlatformGameServer gs) {
		this.gs = gs;
	}
	public OnlineUser getUser() {
		return user;
	}
	public void setUser(OnlineUser user) {
		this.user = user;
	}

	
	

}
