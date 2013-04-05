package com.sz7road.web.action.admin.gamemanage;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

//游戏充值主页
public class GameAmountAction extends ActionSupport implements RequestAware, SessionAware, ServletContextAware{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GameAmountAction.class);
	
	@Override
	public String execute() throws Exception {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 String step=request.getParameter("action");
		 if(step=="" ||step==null)
			 return INPUT;
		 else 
			 return SUCCESS;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRequest(Map<String, Object> arg0) {
		// TODO Auto-generated method stub
		
	}
}
