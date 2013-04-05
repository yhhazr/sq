/**
 * $Header: /data/cvs/esmcol/src/java/com/gs/sitecore/dto/adminuser/AdminUserRight.java,v 1.6 2008/08/28 08:53:12 mzheng Exp $ 
 * $Revision: 1.6 $ 
 * $Date: 2008/08/28 08:53:12 $ 
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

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.rolemanage.PermissionInfo;

/**
 * DTO object of the rights, related tables are:<br>
 * <blockquote>
 * ADMIN_MODULE<br>
 * ADMIN_FUNCTION<br>
 * ADMIN_USER_RIGHT
 * </blockquote>
 * 
 * @author Jansen Wang
 * @version $Id: AdminUserRight.java,v 1.6 2008/08/28 08:53:12 mzheng Exp $
 */

public class AdminUserRight {

	private static final long serialVersionUID = -5818873258765990302L;

	/**
	 * functions property
	 */
	private List<Permission> permissions = null;

	private List<PermissionInfo> menuPermInfoList = null;

	/**
	 * UserLoginInfo property
	 */
	private UserInfo loginInfo = null;

	/**
	 * currentUser property
	 */
	private String currentUser = null;

	/**
	 * userId property
	 */
	private Integer userId;


	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public UserInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(UserInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the currentUser.
	 */
	public String getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser The currentUser to set.
	 */
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public List<PermissionInfo> getMenuPermInfoList() {
		return menuPermInfoList;
	}

	public void setMenuPermInfoList(List<PermissionInfo> menuPermInfoList) {
		this.menuPermInfoList = menuPermInfoList;
	}

	/**
	 * return true if the input function id exists in the functionid list
	 * false if the input function id doesn't exist in the functionid list
	 *
	 * @param funId String the function id inputed
	 * @return whether has the function privileges
	 */
	public boolean hasFunction(String permId) {
		if (!StringUtils.isEmpty(permId) && StringUtils.isNumeric(permId) && (permissions.size() > 0)) {
			for (int i = 0; i < permissions.size(); i++) {
				//AdminFunction eachFunction = (AdminFunction) functions.get(i);
				Permission permission = (Permission) permissions.get(i);
				if (permId.equals(permission.getPermId().toString())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * return true if the input module id exists in the moduleid list
	 * false if the input module id doesn't exist in the moduleid list
	 * @param modId String the Module id inputed
	 * @return whether has the module privileges.
	 */
	//	public boolean hasModule(String modId) {
	//		if (!StringUtils.isEmpty(modId) && StringUtils.isNumeric(modId) && (functions.size() > 0)) {
	//			for (int i = 0; i < functions.size(); i++) {
	//				AdminFunction eachFunction = (AdminFunction) functions.get(i);
	//				if (modId.equals(eachFunction.getModuleId().toString())) {
	//					return true;
	//				}
	//			}
	//		}
	//		return false;
	//	}

}
