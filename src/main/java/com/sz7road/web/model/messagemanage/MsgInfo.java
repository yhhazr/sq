package com.sz7road.web.model.messagemanage;

import java.util.Date;

public class MsgInfo {

	private int msgId;
	private int typeId;
	private int titleId;
	private int stateId;
	private int userId;
	private String msgContent;
	private String ipAddress;
	private Date createDate;
	private String support;
	
	private String typeName;
	private String stateName;
	
	private String titleName;
	
	public MsgInfo(){
		
	}

	
	
	public String getTypeName() {
		return typeName;
	}



	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



	public String getStateName() {
		return stateName;
	}



	public void setStateName(String stateName) {
		this.stateName = stateName;
	}



	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getTitleId() {
		return titleId;
	}

	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}



	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getSupport() {
		return support;
	}



	public void setSupport(String support) {
		this.support = support;
	}



	public String getTitleName() {
		return titleName;
	}



	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	
	
}
