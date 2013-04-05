package com.sz7road.web.service;

import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.gamemanage.GameServerInfo;

public interface AdStatisticsService {

	public void enterCount(int adId , String sit) throws Exception;

	public void regCount(RegInfo regInfo,String site) throws Exception;
	
	public void insertAdStatistics() throws Exception;
	
	public GameServerInfo getServerById(String serverId) throws Exception;
	
	public RegInfo checkUserName(String userName) throws Exception;
}
