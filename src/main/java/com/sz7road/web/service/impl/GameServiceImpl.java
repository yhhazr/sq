package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.GameInfoDao;
import com.sz7road.web.dao.impl.GameInfoDaoImpl;
import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.gamemanage.GameType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameService;

public class GameServiceImpl implements GameService{
	private static GameServiceImpl _this;
	
	private static GameInfoDao dao;
	
	private GameServiceImpl() {
		dao = GameInfoDaoImpl.getInstance();                               
	}

	/**
	 * @return UserBusiness
	 */
	public synchronized static GameServiceImpl getInstance() {
		if (_this == null)
			_this = new GameServiceImpl();
		return _this;
	}
	@Override
	public boolean insertGameInfo(GameInfo gameInfo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateGameInfo(GameInfo gameInfo) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGameInfo(int gameInfoId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameInfo getGameInfoById(int gameInfoId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationResult<GameInfo> getGameInfo(PageInfo pageInfo)
			throws Exception {
		int total = dao.getGameInfoCount();
		List<GameInfo> gameInfoList = dao.getGameInfos(pageInfo);
		PaginationResult<GameInfo> pageResult = new PaginationResult<GameInfo>(total, gameInfoList);
		return pageResult;
	}

	@Override
	public List<GameType> getTypes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
