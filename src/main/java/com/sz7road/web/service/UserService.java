/**
 * $Header: /data/cvs/esmcol/src/java/com/gs/sitecore/service/user/UserService.java,v 1.8 2009/03/10 01:54:48 mzheng Exp $ 
 * $Revision: 1.8 $ 
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
 * 
 */

package com.sz7road.web.service;

import java.rmi.RemoteException;

import org.omg.CORBA.portable.ApplicationException;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.usermanage.UserInfo;

/**
 * This class define the business method for User
 *
 * @author Jimmy Huang
 */
public interface UserService {

	/**
	 * Create user interface
	 * @param User user
	 * @return Long
	 * @throws RemoteException
	 * @throws ApplicationException
	 */
	public boolean createUser(UserInfo user) throws Exception;
	
	public boolean deleteUser(Integer userId) throws Exception;

	public Integer getUserIdByLoginName(String loginName) throws Exception;

	public UserInfo getUserLoginInfoByUserName(String loginName) throws Exception;
	
	public UserInfo getUserLoginInfoByUserId(Integer userId) throws Exception;

	public boolean updateUserLoginInfo(UserInfo loginInfo) throws Exception;

	public PaginationResult<UserInfo> getUserLoginInfoList(PageInfo pageInfo) throws Exception;

}
