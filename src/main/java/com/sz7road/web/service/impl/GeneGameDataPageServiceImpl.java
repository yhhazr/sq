package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.dao.GameDateDao;
import com.sz7road.web.dao.impl.GameDateDaoImpl;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.GameDateSer;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneGameDataPageService;
import com.sz7road.web.service.GeneNewsPageService;

public class GeneGameDataPageServiceImpl implements GeneGameDataPageService {
	
	private static final Logger logger = LogManager.getLogger(GeneGameDataPageServiceImpl.class);

	private static GeneGameDataPageService _this;
	
	private GameDateDao gameDataDao;
	private GameServerService gameServerService;
	
	private GameDateEx gameData = null;
//	private Map<String, Object> commonDataMap = getGameDataPageCommonData();
	private List<GameDateEx> concreteGameDataList = new ArrayList<GameDateEx>();
	private int concreteGameDataIndex = 0;
	private boolean moreGameData = true;
	
	private List<PlatformGameServer> gameServerListDesc = new ArrayList<PlatformGameServer>();
	
	private static final int SERVER_SIZE = 5;
	private static final int RELATED_GAME_DATA_SIZE = 6;
	private static final int NEWB_GUIDE_ID = 34;
	
	
	private GeneGameDataPageServiceImpl() {
		gameDataDao = GameDateDaoImpl.getInstence();
//		homepageItemDao = HomepageItemDaoImpl.getInstance();
		gameServerService = GameServerServiceImpl.getInstance();
	}

	public synchronized static GeneGameDataPageService getInstance() {
		if (_this == null)
			_this = new GeneGameDataPageServiceImpl();
		return _this;
	}

	//获得游戏资料列表页
	@Override
	public Map<String, Object> getGameDataListPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			GameDateSer gameSer = ServiceLocator.getGameDateSer();
			// 所有一级标题
			int i = 1;
			List<GameDateType> fristTypeList = gameSer.getGameDateFristType();
			for (GameDateType firstType : fristTypeList) {
				LinkedHashMap<String, Object> listMap = new LinkedHashMap<String, Object>();
				List<GameDateType> parentTypelist = gameSer.getGameDateParentType(firstType.getTypeId());
				for (GameDateType parentType : parentTypelist) {
					List<GameDateEx> gameDataList = gameSer.getAllGameData(parentType.getTypeId());
					listMap.put(parentType.getTypeName(), gameDataList);
				}
				dataMap.put("list0" + i, listMap);
				i++;
			}
		} catch (Exception e) {
			logger.error("获得游戏资料列表页数据异常。", e);
			throw e;
		}
		
		return dataMap;
	}

//	//获得新手指引资料页数据
//	@Override
//	public Map<String, Object> getNewbGuidePageData() throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>(); 
//		Map<String, Object> commonDataMap = getGameDataPageCommonData();
//		dataMap.putAll(commonDataMap);
//		GameDateEx gameData = gameDataDao.readGameDateById(NEWB_GUIDE_ID);
//		dataMap.put("gameData", gameData);
//		//生成相关资料
//		List<GameDateEx> relatedGameDataList = new ArrayList<GameDateEx>();
//		String id = gameData.getId();
//		if(StringUtils.isNumeric(id)) {
//			relatedGameDataList = getRelatedNewsList(gameData.getTypeId(), RELATED_GAME_DATA_SIZE, Integer.valueOf(id));
//		}
//		dataMap.put("relatedGameDataList", relatedGameDataList);
//		
//		dataMap.put("newServers", getGameServerList(SERVER_SIZE));
//		dataMap.put("rankServers", getPlayerRankGameServerList());
//		
//		return dataMap;
//	}

	
	//判断是否还有更多的游戏资料内页数据
	@Override
	public boolean hasMoreGameData() {
		boolean currentMoreNews = moreGameData;
		if(!moreGameData) {
			moreGameData = true;
			concreteGameDataList.clear();
			gameServerListDesc.clear();
		}
		return currentMoreNews;
	}
	
	//获得单一游戏资料内页数据
	@Override
	public Map<String, Object> getConcreteGameDataPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> commonDataMap = getGameDataPageCommonData();
		dataMap.putAll(commonDataMap);
		
		if(concreteGameDataList.size() == 0) {
			try {
				concreteGameDataList = gameDataDao.getAllGameData();
				if(concreteGameDataList.size() == 0) {
					moreGameData = false;
				}
			} catch (Exception e) {
				logger.error("获得游戏资料内页数据异常。", e);
				throw e;
			}
			concreteGameDataIndex = 0;
		}
		
		if(concreteGameDataIndex <= (concreteGameDataList.size() - 1)) {
			gameData = concreteGameDataList.get(concreteGameDataIndex);
			dataMap.put("gameData", gameData);
		}
		concreteGameDataIndex ++;
		if(concreteGameDataIndex == concreteGameDataList.size()) {
			concreteGameDataIndex = 0;
			moreGameData = false;
		}
		
		//生成相关资料
		List<GameDateEx> relatedGameDataList = new ArrayList<GameDateEx>();
		String id = gameData.getId();
		if(StringUtils.isNumeric(id)) {
			relatedGameDataList = getRelatedNewsList(gameData.getTypeId(), RELATED_GAME_DATA_SIZE, Integer.valueOf(id));
		}
		dataMap.put("relatedGameDataList", relatedGameDataList);
	
		
		return dataMap;
	}
	
	//获得游戏资料内页通用数据
	public Map<String, Object> getGameDataPageCommonData() {
		Map<String, Object> commonDataMap = new HashMap<String, Object>();
		GameDateSer gameSer = ServiceLocator.getGameDateSer();
		List<GameDateType> types = new ArrayList<GameDateType>();
		try {
			types = gameSer.getAllType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<GameDateType> rudiment = new ArrayList<GameDateType>();// p_id = 1
		List<GameDateType> building = new ArrayList<GameDateType>();// p_id = 2
		List<GameDateType> charect = new ArrayList<GameDateType>();// p_id = 3
		List<GameDateType> movement = new ArrayList<GameDateType>();// p_id = 4
		for (GameDateType type : types) {
			int id = type.getParentId();
			if (id == 1) {
				rudiment.add(type);
			} else if (id == 2) {
				building.add(type);
			} else if (id == 3) {
				charect.add(type);
			} else {
				movement.add(type);
			}
		}
		commonDataMap.put("rudiment", rudiment);
		commonDataMap.put("building", building);
		commonDataMap.put("charect", charect);
		commonDataMap.put("movement", movement);
		
		return commonDataMap;
	}
	
	//生成玩家排行服务器列表
	private List<PlatformGameServer> getPlayerRankGameServerList() throws Exception {
		if(gameServerListDesc.size() == 0) {
			gameServerListDesc = gameServerService.buildGameServerListDesc();
		}
		
		return gameServerListDesc;
	}
	
	//首页服务区列表（倒序）
	private List<PlatformGameServer> getGameServerList(int count) throws Exception {
		if(gameServerListDesc.size() == 0) {
			gameServerListDesc = gameServerService.buildGameServerListDesc();
		}
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		if(gameServerListDesc.size() >= count) {
			serverListDesc = gameServerListDesc.subList(0, count);
		}
		
		return serverListDesc;
	}

	//生成玩家排行服务器列表(资料列表页专用)
	private List<PlatformGameServer> getPlayerRankGameServerListForGameDataListPage() throws Exception {
		
		return gameServerService.getPlayerRankServerList();
	}
	
	//首页服务区列表（倒序）（资料列表页专用）
	private List<PlatformGameServer> getGameServerListForGameDataListPage(int count) throws Exception {
		List<PlatformGameServer> gameServerListDesc = gameServerService.buildGameServerListDesc();
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		if(gameServerListDesc.size() >= count) {
			serverListDesc = gameServerListDesc.subList(0, count);
		}

		return serverListDesc;
	}

	//生成相关新闻列表
	public List<GameDateEx> getRelatedNewsList(int typeId, int size, int newsId) throws Exception {
		List<GameDateEx> gameDataList = new ArrayList<GameDateEx>();
		gameDataList = gameDataDao.getAllGameData(typeId, newsId, new PageInfo(0, size));

		return gameDataList; 
	}
		

}
