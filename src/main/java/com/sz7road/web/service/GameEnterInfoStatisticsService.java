/**
 * 
 */
package com.sz7road.web.service;

import java.util.HashMap;

import com.sz7road.web.model.gameEnterInfo.GameEnterInfo;





/**
 * @author hokin.jim
 *
 */
public interface GameEnterInfoStatisticsService {
	public boolean countTimes(String positionKey)throws Exception;
	public boolean insertGameEnterInfoStatistics(String positionKey,String date) throws Exception;
	public HashMap<String, GameEnterInfo> getCacheMap();
}
