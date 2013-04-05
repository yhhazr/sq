package com.sz7road.web.action.online.novcard;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.service.NoviceCardService;
import com.sz7road.web.service.OnlineUserService;
import com.sz7road.web.service.impl.NoviceCardServiceImpl;


public class NoviceCardAction extends ActionSupport{

	private static final long serialVersionUID = -3629208475586021204L;
	private static final Logger logger = LogManager.getLogger(NoviceCardAction.class);
	
	private String read;
	private String retu;
	
	@Override
	public String execute() throws Exception {
		retu = "000000000000";
		if (read == null || !read.startsWith("7road_")) {
			return SUCCESS;
		}
		
		//验证是否登录
		String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
		if (userInfo == null || userInfo.length != 4) {
			return SUCCESS;
		}
		String sign = CookieUtil.buildMD5Cookie(userInfo[0], userInfo[1], userInfo[2]);
		if (!sign.equals(userInfo[3])) {
			return SUCCESS;
		}
		OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
		LoginInfo logInfo = onlineUserService.servCheckAccount(userInfo[1], "1", "true");
		
		//取激活码
		if (logInfo != null && "success".equals(logInfo.getRespFlag())) {
			NoviceCardService noviceCardService = NoviceCardServiceImpl.getInstance();
			String userId = userInfo[0];
			retu = noviceCardService.queryCDKey(userId, read);
		}
		return SUCCESS;
	}
	

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

	public String getRetu() {
		return retu;
	}

	public void setRetu(String retu) {
		this.retu = retu;
	}


	
}