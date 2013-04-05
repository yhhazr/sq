package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.gameDateMag.GameDate;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;

public interface GameDateDao {
	//游戏资料后端数据库操作
	public List<GameDateEx> readAllGameDate(PageInfo pageInfo) throws Exception; 
	public int getCount() throws Exception;
	public GameDateEx readGameDateById(int id) throws Exception;
	public List<GameDateType> getAllType() throws Exception;
	public boolean updateGameDate(GameDate game) throws Exception;
	public boolean createGameDate(GameDate game) throws Exception;
	public boolean deleteGameDate(int id) throws Exception;
	//页面生成数据库操作
	//获取一级标题(4个,神曲入门，建筑介绍，特色系统，活动玩法 parentId=-1)
	//获取一级标题下的二级标题
	//依据parentId来获取二级标题(parentId != -1)
	public List<GameDateType> getGameDateParentType(int parentId) throws Exception;
	//依据typeId来获取二级标题内容(依据order_num)排序
	public List<GameDate> getGameDataChildType(int typeId) throws Exception;
	
	
	public List<GameDateEx> getHomepageGameData(int typeId) throws Exception;
	
	
	public List<GameDateEx> getAllGameData(int typeId) throws Exception;
	
	public List<GameDateEx> getAllGameData() throws Exception;
	
	public List<GameDateEx> getAllGameData(final int typeId, final int id, final PageInfo pageInfo) throws Exception;
}
