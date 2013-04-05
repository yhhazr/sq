/**
 * 
 */
package com.sz7road.web.dao;

import com.sz7road.web.model.gameEnterInfo.GameEnterInfo;

/**
 * 游戏入口统计Dao接口
 * @author hokin.jim
 *
 */
public interface GameEnterInfoStatisticsDao {
	public boolean insertGameEnterInfo(GameEnterInfo gameEnterInfo) throws Exception;
	public boolean updateGameEnterInfo(GameEnterInfo gameEnterInfo) throws Exception;
	public GameEnterInfo getGameEnterInfoByPostion(String positionKey,String date)throws Exception;
}	
