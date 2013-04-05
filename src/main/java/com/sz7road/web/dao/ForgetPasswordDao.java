package com.sz7road.web.dao;

import com.sz7road.web.model.onlineUser.OnlineUser;

public interface ForgetPasswordDao {

	public boolean createInfo(OnlineUser user) throws Exception;
	
	public OnlineUser getInfo(String userName) throws Exception;
	
	public boolean deleteInfo(String userName) throws Exception;
	
}
