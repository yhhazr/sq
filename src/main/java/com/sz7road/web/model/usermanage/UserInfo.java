/**
 * $Header: /data/cvs/chinasiteplatform/src/java/com/gs/sitecore/dto/user/UserLoginInfo.java,v 1.7 2009/07/21 07:29:36 mzheng Exp $ 
 * $Revision: 1.7 $ 
 * $Date: 2009/07/21 07:29:36 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.model.usermanage;

/**
 * User login DTO object, store data in <code>USERS</code> table.
 * Store user's login information.
 * 
 * @author Jimmy Huang
 * @version $Id: UserLoginInfo.java,v 1.7 2009/07/21 07:29:36 mzheng Exp $
 */
public class UserInfo {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	
	private Integer roleId;
	
	private String roleName;

	private String userName;
	
	private String oldPassWord;
	
	private String rePassWord;

	private String passWord;

	private String emailAddr;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassWord() {
		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}

	public String getRePassWord() {
		return rePassWord;
	}

	public void setRePassWord(String rePassWord) {
		this.rePassWord = rePassWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
