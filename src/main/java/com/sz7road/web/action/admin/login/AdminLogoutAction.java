package com.sz7road.web.action.admin.login;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.AppConstants;

public class AdminLogoutAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	private Map<String, Object> sessionMap;

	public String execute() {
		sessionMap.remove(AppConstants.ADMINUSER_USER_RIGHT);
		return "success";
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;

	}

}
