package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.sz7road.web.dao.GameDateDao;
import com.sz7road.web.dao.impl.GameDateDaoImpl;
import com.sz7road.web.model.gameDateMag.GameDate;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameDateSer;

public class GameDateSerImpl implements GameDateSer {

	private static GameDateSerImpl _this;
	
	private static GameDateDao gameDao;
	
	private GameDateSerImpl() {
		gameDao = GameDateDaoImpl.getInstence();
	}

	/**
	 * @return UserBusiness
	 */
	public synchronized static GameDateSerImpl getInstance() {
		if (_this == null)
			_this = new GameDateSerImpl();
		return _this;
	}
	@Override
	public PaginationResult<GameDateEx> readAllGameDate(PageInfo pageInfo) throws Exception {
		int total = gameDao.getCount();
		List<GameDateEx> date = gameDao.readAllGameDate(pageInfo);
		PaginationResult<GameDateEx> pageResult = new PaginationResult<GameDateEx>(total, date);
		return pageResult;
	}

	@Override
	public GameDateEx readGameDateById(int id) throws Exception {
		return gameDao.readGameDateById(id);
	}

	@Override
	public List<GameDateType> getAllType() throws Exception {
		List<GameDateType> types = gameDao.getAllType();
		List<GameDateType> gameTypes = new ArrayList<GameDateType>();
		for(GameDateType type:types){
			if(type.getParentId() != -1){
				gameTypes.add(type);
			}
		}
		return gameTypes;
	}

	@Override
	public boolean updateGameDate(GameDate game) throws Exception {
		
		return gameDao.updateGameDate(game);
	}

	@Override
	public boolean createGameDate(GameDate game) throws Exception {
		return gameDao.createGameDate(game);
	}


	@Override
	public List<GameDateType> getGameDateParentType(int parentId)
			throws Exception {
		return gameDao.getGameDateParentType(parentId);
		
	}

	@Override
	public List<GameDate> getGameDataChildType(int typeId) throws Exception {
		return gameDao.getGameDataChildType(typeId);
	}


	@Override
	public boolean deleteGameDate(int id) throws Exception {
		
		return gameDao.deleteGameDate(id);
	}


	@Override
	public List<GameDateType> getGameDateFristType() throws Exception {
		List<GameDateType> types = gameDao.getAllType();
		List<GameDateType> gameTypes = new ArrayList<GameDateType>();
		for(GameDateType type:types){
			if(type.getParentId() == -1){
				gameTypes.add(type);
			}
		}
		return gameTypes;
	}

	@Override
	public List<GameDateEx> getAllGameData(int typeId) throws Exception {
		
		return gameDao.getAllGameData(typeId);
	}

	@Override
	public List<GameDateEx> getHomepageGameData(int typeId) throws Exception {

		return gameDao.getHomepageGameData(typeId);
	}



}
