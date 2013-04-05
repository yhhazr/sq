package com.sz7road.web.dao;

import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;

public interface AdStatisticsDao {

	public AdInfo getAdStatisticsByAdId(int adId, String date) throws Exception ;
	
	public boolean updateAdStatistics(AdInfo adInfo) throws Exception;
	
	public boolean insertAdStatistics(AdInfo adInfo) throws Exception;
	
	public boolean insertRegInfo(RegInfo regInfo) throws Exception;
	
	public RegInfo getRegInfoByUserName(String userName) throws Exception; 
}
