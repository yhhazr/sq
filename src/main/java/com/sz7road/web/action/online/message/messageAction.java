package com.sz7road.web.action.online.message;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.messagemanage.MsgInfoEx;
import com.sz7road.web.service.MsgService;

public class messageAction extends ActionSupport {
	private static final Logger logger = LogManager.getLogger(UserManageAction.class); 
	private static final long serialVersionUID = 1L;
	private String typeId;
	private String titleId;
	private String userId;
	private String content;
	private List<MsgInfoEx> msgList = new ArrayList<MsgInfoEx>();
	static int count = 0;
	
	public String publicMsg(){
		String result = "";
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		count = count + 1;
		session.setAttribute("number", count);
		session.setMaxInactiveInterval(1000 * 60 * 5);
		
		int num = (Integer) session.getAttribute("number");
		if(num < 3){
			result = this.operate();
		}else{
			
		}
		
		System.out.println(typeId + ":" + titleId + ":" + userId + ":" + content);
		
		//System.out.println(visitorIp + ":" + visitorhost + ":" + visitorPort + ":" + visitorUsername + ":" + visitorUrl);
		return result;
	}
	
	private String operate(){
		String result = INPUT;
		HttpServletRequest req = (HttpServletRequest)ServletActionContext.getRequest();
		String visitorIp=req.getRemoteAddr();
		//String visitorhost = req.getRemoteHost();
		//int visitorPort=req.getRemotePort();
		//String visitorUsername=req.getRemoteUser();
		//String visitorUrl=req.getRequestURI();
		
		MsgInfoEx msgInfoEx = new MsgInfoEx();
		msgInfoEx.setTypeId(Integer.parseInt(typeId));
		msgInfoEx.setTitleId(Integer.parseInt(titleId));
		msgInfoEx.setUserId(Integer.parseInt(userId));
		msgInfoEx.setMsgContent(content);
		msgInfoEx.setIpAddress(visitorIp);
		msgInfoEx.setStateId(1);
		MsgService msgService = ServiceLocator.getMsgService();
		try{
			boolean flag = msgService.createNewMsg(msgInfoEx);
			setMsgList(msgService.getMsgInfoByTitleId(Integer.parseInt(titleId), Integer.parseInt(typeId)));
			if(flag){
				result = SUCCESS;
			}
		}catch(Exception e){
			result = INPUT;
			logger.error("create new msg:" + e.getMessage(), e);
		}
		return result;
	}


	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<MsgInfoEx> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MsgInfoEx> msgList) {
		this.msgList = msgList;
	}
}
