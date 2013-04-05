package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.gamemanage.GameType;
import com.sz7road.web.model.pagination.PageInfo;

public interface GameInfoDao {
public boolean insertGameInfo(final GameInfo gi) throws Exception;
	
	public boolean updateGameInfo(final GameInfo gi) throws Exception; 
	
	public boolean deleteGameInfo(final int id) throws Exception;
	
	public GameInfo getGameInfoById(final int id) throws Exception;
	
	public List<GameInfo> getGameInfos(PageInfo pageInfo) throws Exception;
	
	public List<GameType> getTypes() throws Exception;
	
//	public List<NewsState> getStates() throws Exception;
	
	public int getGameInfoCount() throws 	Exception;
	
	public List<GameInfo> getAllGameInfos() throws Exception;
}
