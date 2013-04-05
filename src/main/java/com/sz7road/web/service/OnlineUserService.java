package com.sz7road.web.service;

import com.sz7road.web.model.onlineUser.LoginInfo;

public interface OnlineUserService {

	public LoginInfo servSignUp(String username, String password, String site) throws Exception;
	
	public LoginInfo servLoginAuth(String username, String password, String remoteIp) throws Exception;
	
	public LoginInfo servCheckAccount(String value, String type, String details) throws Exception;
	
	
	
}
