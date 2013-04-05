package com.sz7road.web.service;


import java.util.List;

import com.sz7road.web.model.gameDateMag.GameDate;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface GameDateSer {
	public PaginationResult<GameDateEx> readAllGameDate(PageInfo pageInfo) throws Exception; 
	public GameDateEx readGameDateById(int id) throws Exception;
	public List<GameDateType> getAllType() throws Exception;
	public boolean updateGameDate(GameDate game) throws Exception;
	public boolean createGameDate(GameDate game) throws Exception;

	
	//页面生成数据
	public List<GameDateType> getGameDateParentType(int parentId) throws Exception;
	public List<GameDateType> getGameDateFristType() throws Exception;
	public List<GameDate> getGameDataChildType(int typeId) throws Exception;

	public boolean deleteGameDate(int id) throws Exception;
	public List<GameDateEx> getAllGameData(int typeId) throws Exception;
	public List<GameDateEx> getHomepageGameData(int typeId) throws Exception;
	
}

