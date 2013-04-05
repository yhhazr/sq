package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneFunctionPageDataService;

public class GeneFunctionPageDataServiceImpl implements GeneFunctionPageDataService {
	
	private static final Logger logger = LogManager.getLogger(GeneFunctionPageDataServiceImpl.class);

	private static GeneFunctionPageDataService _this;
	
	private static HomepageItemDao homepageItemDao;
	
	private GameServerService gameServerService;
	private NewsDao newsDao;
	private HomepageItemDao homepageDao;
	
	private static final int SERVER_SIZE = 5;
	private static final int LOGINEXE_NEWS_SIZE = 5;
	
	private GeneFunctionPageDataServiceImpl() {
		gameServerService = GameServerServiceImpl.getInstance();
		homepageDao = HomepageItemDaoImpl.getInstance();
		newsDao = NewsDaoImpl.getInstance();
	}

	public synchronized static GeneFunctionPageDataService getInstance() {
		if (_this == null)
			_this = new GeneFunctionPageDataServiceImpl();
		return _this;
	}
	
	//生成新手礼包页面数据
	@Override
	public Map<String, Object> getNewbGiftPageData() throws Exception {
		homepageItemDao = HomepageItemDaoImpl.getInstance();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		List<PlatformGameServer> serverListAsc = getGameServerListAsc();
		dataMap.put("newbGiftServers", serverListAsc);
		return dataMap;
	}
	
	//生成登陆器页面数据
	@Override
	public Map<String, Object> getLoginEXEPageData() {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			//List<News> newsList = newsDao.selectNewsOnline(new PageInfo(0, LOGINEXE_NEWS_SIZE));
		    List<News> newsList = newsDao.selectNewsByState(News.STATE_HOT, new PageInfo(0, LOGINEXE_NEWS_SIZE));
		    int i = 0;
		    //截取新闻标题长度
//		    for (News news : newsList) {
//		        String title = news.getArtTitle();
//		        if( i < 3){
//		            if(StringUtil.getStringByteLength(title) > 40){
//	                    news.setArtTitle(StringUtil.substring(title, 40) + "...");
//	                }
//		        }else{
//		            if(StringUtil.getStringByteLength(title) > 46){
//                        news.setArtTitle(StringUtil.substring(title, 46) + "...");
//                    }
//		        }
//		        
//            }
			dataMap.put("newsList", newsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dataMap.put("serverList", getLoginEXEServerList());
		dataMap.put("serverListAsc", getLoginexeServerListAsc());
		
		List<HomepageItem> urlItems = new ArrayList<HomepageItem>();
		try {
			urlItems = homepageDao.findItemListByTypeId(AppConstants.HOMEPAGE_IMAGE_TYPE_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataMap.put("urlItems", urlItems);
		
		dataMap.put("loginToolsPath", SystemConfig.getProperty("login.tools.path") + AppConstants.LOGIN_TOOLS_NAME);
		
		return dataMap;
	}
	
	//服务区列表（正序）
	private List<PlatformGameServer> getGameServerListAsc() {
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = gameServerService.buildGameServerListAsc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverListAsc;
	}

	//登录器服务区列表（正序）
	private List<PlatformGameServer> getLoginexeServerListAsc() {
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = gameServerService.buildGameServerListAsc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverListAsc;
	}
	
	//登录器服务器列表
	private List<PlatformGameServer> getLoginEXEServerList(){
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		try {
			serverListDesc = gameServerService.buildGameServerListDesc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverListDesc;
	}
	
	//生成玩家排行服务器列表、  生成倒序服务器列表
	private List<PlatformGameServer> getPlayerRankGameServerList() {
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		try {
			serverListDesc = gameServerService.getPlayerRankServerList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverListDesc;
	}
	
	//首页服务区列表（倒序）
	private List<PlatformGameServer> getGameServerList(int count) throws Exception {
		List<PlatformGameServer> gameServerListDesc = gameServerService.buildGameServerListDesc();
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		if(gameServerListDesc.size() >= count) {
			serverListDesc = gameServerListDesc.subList(0, count);
		}
		
		return serverListDesc;
	}
	
	//生成最新新闻图片数据
		public HomepageItem getNewestNewsData() {
			HomepageItem newestNews = null;
			try {
				List<HomepageItem> newestNewsList = homepageItemDao.selectItemByTypeIdAndPosition(HomepageItem.TYPE_PICTURE, HomepageItem.POSITION_NEWEST_NEWS, 1);
				if(newestNewsList.size() > 0) {
					newestNews = newestNewsList.get(0);
				}
			} catch (Exception e) {
				logger.error("生成新闻页最新新闻图片数据异常。", e);
			}
			
			return newestNews;
		}

}
