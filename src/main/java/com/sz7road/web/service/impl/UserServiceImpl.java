/**
 * $Header: /data/cvs/esmcol/src/java/com/gs/sitecore/service/user/UserServiceImpl.java,v 1.5 2009/03/10 01:54:48 mzheng Exp $ 
 * $Revision: 1.5 $ 
 * $Date: 2009/03/10 01:54:48 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 */
package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.RoleDao;
import com.sz7road.web.dao.UserDao;
import com.sz7road.web.dao.impl.RoleDaoImpl;
import com.sz7road.web.dao.impl.UserDaoImpl;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.rolemanage.UserRoleInfo;
import com.sz7road.web.model.usermanage.UserInfo;
import com.sz7road.web.service.UserService;

/**
 *	This class will	call the function to handle	the	user function. *
 *
 *	@author	Jimmy Huang
 */
public class UserServiceImpl implements UserService {

	private static UserServiceImpl _this;

	private static UserDao userDao;

	private static RoleDao roleDao;

	/**
	 * Constructor
	 */
	private UserServiceImpl() {
		userDao = UserDaoImpl.getInstance();
		roleDao = RoleDaoImpl.getInstance();
	}

	/**
	 * @return UserBusiness
	 */
	public synchronized static UserServiceImpl getInstance() {
		if (_this == null)
			_this = new UserServiceImpl();
		return _this;
	}

	@Override
	public boolean createUser(UserInfo userInfo) throws Exception {
		boolean result = false;
		UserRoleInfo userRoleInfo = new UserRoleInfo();
		userRoleInfo.setRoleId(userInfo.getRoleId());
		int userId = userDao.insertUserLoginInfo(userInfo);
		userRoleInfo.setUserId(userId);
		boolean insertUserRoleResult = roleDao.insertUserRole(userRoleInfo);
		if (userId > 0 && insertUserRoleResult) {
			result = true;
		}
		return result;
	}

	@Override
	public Integer getUserIdByLoginName(String loginName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInfo getUserLoginInfoByUserName(String loginName) throws Exception {
		return userDao.getUserLoginInfoByUserName(loginName);
	}

	@Override
	public boolean updateUserLoginInfo(UserInfo userInfo) throws Exception {
		UserRoleInfo userRoleInfo = new UserRoleInfo();
		userRoleInfo.setRoleId(userInfo.getRoleId());
		userRoleInfo.setUserId(userInfo.getUserId());
		boolean updateUserResult = userDao.updateUserLoginInfo(userInfo);
		boolean updateUserRoleResult = roleDao.updateUserRole(userRoleInfo);
		return updateUserResult && updateUserRoleResult;
	}

	@Override
	public PaginationResult<UserInfo> getUserLoginInfoList(PageInfo pageInfo) throws Exception {
		int total = userDao.getUserLoginInfoListCount();
		List<UserInfo> userInfoList = userDao.getUserLoginInfoList(pageInfo);
		PaginationResult<UserInfo> pageResult = new PaginationResult<UserInfo>(total, userInfoList);
		return pageResult;
	}

	@Override
	public UserInfo getUserLoginInfoByUserId(Integer userId) throws Exception {
		return userDao.getUserLoginInfoByUserId(userId);
	}

	@Override
	public boolean deleteUser(Integer userId) throws Exception {
		return userDao.deleteUserLoginInfo(userId);
	}

}
