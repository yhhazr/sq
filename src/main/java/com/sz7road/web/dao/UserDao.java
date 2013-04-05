/**
 * $Header: /data/cvs/chinasiteplatform/src/java/com/gs/sitecore/dao/UserDAO.java,v 1.18 2010/12/08 08:49:22 joeycao Exp $
 * $Revision: 1.18 $
 * $Date: 2010/12/08 08:49:22 $
 *
 * ==================================================================== 
 * 
 * Copyright (c) 2007 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.usermanage.UserInfo;

/**
 * This class handle all db operation for user. 
 * 
 * @author Jimmy Huang
 *  
 */
public interface UserDao {

	/**
	 * Insert user login inforamtion to user table
	 * @param loginInfo user login information
	 */
	public int insertUserLoginInfo(UserInfo loginInfo) throws Exception;

	/**
	 * Retrieve user login information by specified user id.
	 * @param userId user id
	 * @return UserLogin information object
	 */
	public UserInfo getUserLoginInfoByUserName(String loginName) throws Exception;
	
	/**
	 * Retrieve user login information by specified user id.
	 * @param userId user id
	 * @return UserLogin information object
	 */
	public UserInfo getUserLoginInfoByUserId(Integer userId) throws Exception;

	/**
	 * Update the user login information by specified user id in UserLoginInfo object.
	 * Other information in UserLoginInfo will be updated into DB.
	 * @param loginInfo user login infomation
	 */
	public boolean updateUserLoginInfo(UserInfo loginInfo) throws Exception;
	
	/**
	 * Delete user login info.
	 * @param int userId
	 */
	public boolean deleteUserLoginInfo(Integer userId) throws Exception;
	

	public List<UserInfo> getUserLoginInfoList(PageInfo pageInfo) throws Exception;
	
	public int getUserLoginInfoListCount() throws Exception;

}
