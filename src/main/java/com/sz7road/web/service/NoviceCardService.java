package com.sz7road.web.service;

public interface NoviceCardService {
	
	/**
	 * 获得新手卡激活码
	 * @param userId
	 * @param site
	 * @return
	 */
	public String queryCDKey(String userId, String site) throws Exception;
	
}
