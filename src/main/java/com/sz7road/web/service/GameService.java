package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.gamemanage.GameType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface GameService {
	public boolean insertGameInfo(final GameInfo gameInfo) throws Exception;
	
	public boolean updateGameInfo(final GameInfo gameInfo) throws Exception; 
	
	public boolean deleteGameInfo(final int gameInfoId) throws Exception;
	
	public GameInfo getGameInfoById(final int gameInfoId) throws Exception;
	
	public PaginationResult<GameInfo> getGameInfo(PageInfo pageInfo) throws Exception;
	
	public List<GameType> getTypes() throws Exception;
	
	//public List<GameInfoState> getStates() throws Exception;
}
