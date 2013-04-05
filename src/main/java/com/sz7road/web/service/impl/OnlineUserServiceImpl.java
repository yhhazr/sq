package com.sz7road.web.service.impl;

import net.sf.json.JSONObject;

import com.sz7road.web.common.util.JsonUtil;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.model.onlineUser.OnlineUser;
import com.sz7road.web.service.OnlineUserService;

public class OnlineUserServiceImpl implements OnlineUserService {
	private static OnlineUserServiceImpl _this;
	
	public synchronized static OnlineUserServiceImpl getInstance() {
		if (_this == null)
			_this = new OnlineUserServiceImpl();
		return _this;
	}
	
	
	private PlatformClient client = new PlatformClient();
	
	@Override
	public LoginInfo servSignUp(String username, String password, String site) throws Exception {
		LoginInfo logInfo = new LoginInfo();
		String respStr = client.signUp(username, password, site);
		if(!(respStr == null || "".equals(respStr))) {
			logInfo = this.checkRespStr(respStr);
		}
		return logInfo;
	}
	
	@Override
	public LoginInfo servLoginAuth(String username, String password, String remoteIp) throws Exception {
		LoginInfo logInfo = new LoginInfo();
		String respStr = client.loginAuth(username, password, remoteIp);
		if(!(respStr == null || "".equals(respStr))) {
			logInfo = this.checkRespStr(respStr);
		}
		return logInfo;
	}
	
	@Override
	public LoginInfo servCheckAccount(String value, String type, String details) throws Exception {
		LoginInfo logInfo = new LoginInfo();
		String respStr = client.checkAccount(value, type, details);
		if(!(respStr == null || "".equals(respStr))) {
			logInfo = this.checkRespStr(respStr);
		}
		return logInfo;
	}
	
	
	//判断平台接口返回的数据信息
	private LoginInfo checkRespStr(String respStr) {
		LoginInfo logInfo = new LoginInfo(); 
		String msgStr = respStr.substring(respStr.indexOf("=") + 1);
		if(respStr.indexOf("success") == 0) {
			if(msgStr.indexOf("{") == 0 && msgStr.indexOf("}") != -1) {
				logInfo = JsonUtil.jsonStrToBean(msgStr, LoginInfo.class);
			}
			logInfo.setRespFlag("success");
		} else if(respStr.indexOf("failure") == 0) {
			logInfo.setRespFlag("failure");
		} else if(respStr.indexOf("invalid") == 0) {
			logInfo.setRespFlag("invalid");
		} else if(respStr.indexOf("error") == 0) {
			logInfo.setRespFlag("error");
		}
		
		return logInfo;
	}
	




}
