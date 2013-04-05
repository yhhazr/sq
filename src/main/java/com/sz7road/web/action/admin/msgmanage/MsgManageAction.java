package com.sz7road.web.action.admin.msgmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.messagemanage.MsgInfo;
import com.sz7road.web.model.messagemanage.MsgInfoEx;
import com.sz7road.web.model.messagemanage.MsgOpType;
import com.sz7road.web.model.messagemanage.MsgState;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.MsgService;

public class MsgManageAction extends ActionSupport implements RequestAware,SessionAware{
	
	private static final long serialVersionUID = -6628013055565069196L;
	private static final Logger logger = LogManager.getLogger(UserManageAction.class); 
	private Map<String, Object> sessionMap;
	private Map<String, Object> requestMap;
	private static List<MsgOpType> msgTypeList;
	private static List<MsgState> msgStateList;
	private List<MsgInfo> msgInfoList;
	//private MsgInfo msgInfo;
	private MsgInfoEx msgInfoEx;
	private int msgId;
	private String msgIdStr;

	private static MsgService msgService = ServiceLocator.getMsgService();
	static{
		try {
			msgTypeList = msgService.getMsgOpTypeList();
			msgStateList = msgService.getMsgStateList();
		} catch (Exception e) {
			logger.error("query type and state Error:" + e.getMessage(), e);
		}
	}
	//留言列表控制
	public String queryMsgList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = INPUT;
		try {
			PageInfo pageInfo = new PageInfo();
			int startRow = 0;
			String pagerOffset = request.getParameter("pager.offset");
			int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
			if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
				startRow = Integer.parseInt(pagerOffset);
			}
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			int typeId = 0;
			int stateId = 0;
			if(msgInfoEx == null){
				typeId = 0;
				stateId = 0;
			}else{
				typeId = msgInfoEx.getTypeId();
				stateId = msgInfoEx.getStateId();
			}
			
			PaginationResult<MsgInfoEx> pageationResult = msgService.getMsgInfoList(pageInfo, typeId, stateId);
			requestMap.put("pageationResult", pageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("query list Error:" + e.getMessage(), e);
		}
		return result;
	}
	//留言编辑
	public String editMsg(){
		String result = INPUT;
		try {
			msgInfoEx = msgService.getMsgInfoById(msgId);
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("query msgInfo by id error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	//留言编辑提交
	public String editMsgSubmit(){
		String result = INPUT;
		try {
			boolean flag = msgService.updateMsgInfo(msgInfoEx);
			if(flag){
				result = SUCCESS;
			}
		} catch (Exception e) {
			result = INPUT;
			logger.error("edit submit error:" + e.getMessage(), e);
		}
		return result;
	}
	//留言删除
	public String deleteMsg(){
		String result = INPUT;
		String[] str = msgIdStr.split(",");
		List<Integer> idList = new ArrayList<Integer>();
		for(int i=0;i<str.length;i++){
			idList.add(Integer.parseInt(str[i]));
		}
		try {
			boolean flag = msgService.deleteMsgInfo(idList);
			if(flag){
				result = SUCCESS;
			}
		} catch (Exception e) {
			result = INPUT;
			logger.error("delete msg error:" + e.getMessage(), e);
		}
		return result;
	}
	
	

	
	
	public MsgInfoEx getMsgInfoEx() {
		return msgInfoEx;
	}
	public void setMsgInfoEx(MsgInfoEx msgInfoEx) {
		this.msgInfoEx = msgInfoEx;
	}
	public List<MsgOpType> getMsgTypeList() {
		return msgTypeList;
	}

	public void setMsgTypeList(List<MsgOpType> msgTypeList) {
		this.msgTypeList = msgTypeList;
	}

	public List<MsgState> getMsgStateList() {
		return msgStateList;
	}

	public void setMsgStateList(List<MsgState> msgStateList) {
		this.msgStateList = msgStateList;
	}
	
	


	public int getMsgId() {
		return msgId;
	}
	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.requestMap = request;
	}
	public String getMsgIdStr() {
		return msgIdStr;
	}
	public void setMsgIdStr(String msgIdStr) {
		this.msgIdStr = msgIdStr;
	}

	
}
