package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.common.util.StringUtil;
import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.PhotoDao;
import com.sz7road.web.dao.VideoDao;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.dao.impl.PhotoDaoImpl;
import com.sz7road.web.dao.impl.VideoDaoImpl;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.service.GameDateSer;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneHomepageService;

public class GeneHomepageServiceImpl implements GeneHomepageService {
	
	private static final Logger logger = LogManager.getLogger(GeneHomepageServiceImpl.class);
	
	private static GeneHomepageServiceImpl _this;
	
	private static HomepageItemDao homepageItemDao;
	private static GameServerService gameServerService;
	private static PhotoDao photoDao;
	private static NewsDao newsDao;
	private static VideoDao videoDao;
	
	private static final int SERVER_SIZE = 5;
	
	private GeneHomepageServiceImpl() {
		homepageItemDao = HomepageItemDaoImpl.getInstance();
		photoDao = PhotoDaoImpl.getInstance();
		newsDao = NewsDaoImpl.getInstance();
		videoDao = VideoDaoImpl.getInstance();
		gameServerService = GameServerServiceImpl.getInstance();
	}

	
	public synchronized static GeneHomepageService getInstance() {
		if (_this == null)
			_this = new GeneHomepageServiceImpl();
		return _this;
	}
	
	@Override
	public Map<String, Object> getHomepageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("carouselImages", getImages(HomepageItem.POSITION_CAROUSEL, 1000));
		List<News> headlineNewsList = getHomepageNewsByState(News.STATE_HEADLINE, 1);
		News headlineNews = null;
		if(headlineNewsList.size() > 0) {
			headlineNews = headlineNewsList.get(0);
		}
		dataMap.put("headlineNews", headlineNews);
		dataMap.put("hotList", getHomepageNewsByState(News.STATE_HOT, 5));

		dataMap.put("newsList", getHomePageNewsByType(News.TYPE_NEWS, 8));
		dataMap.put("bulletinList", getHomePageNewsByType(News.TYPE_BULLETIN, 8));
		dataMap.put("activityList", getHomePageNewsByType(News.TYPE_ACTIVITY, 8));
		dataMap.put("lantieList", getHomePageNewsByType(News.TYPE_BLUEPOST, 8));
		
		dataMap.put("rightImages", getImages(HomepageItem.POSITION_ACTIVITY, 3));
		dataMap.put("friendly", getImages(HomepageItem.POSITION_MEDIA, 1000));
		
		dataMap.put("playerPhotos", getPhoto(Photo.CATEGORY_PLAYER_PHOTO, 4));
		dataMap.put("wallpapers", getPhoto(Photo.CATEGORY_WALLPAPER, 4));
		dataMap.put("paints", getPhoto(Photo.CATEGORY_PAINT, 4));
		dataMap.put("cartoons", getPhoto(Photo.CATEGORY_CARTOON, 4));
		dataMap.put("videos", getVideoList(4));
		
		dataMap.put("newServers", getGameServerList(SERVER_SIZE));
		dataMap.put("rankServers", getPlayerRankGameServerList());
		dataMap.putAll(getGameDataList());

		
		
		return dataMap; 
	}
	
	//生成首页图片数据 homepageItem表
	public List<HomepageItem> getImages(String position, int imagesCount) throws Exception {
		List<HomepageItem> imageList = new ArrayList<HomepageItem>();
		try {
			imageList = homepageItemDao.selectItemByTypeIdAndPosition(HomepageItem.TYPE_PICTURE, position, imagesCount);
		} catch (Exception e) {
			logger.error("获得首页图片异常。position:" + position, e);
			throw e;
		}
		
		return imageList;
	}
	
	//生成首页相册图片数据  photo表
	private List<Photo> getPhoto(int typeId, int count) throws Exception {
		List<Photo> photos = new ArrayList<Photo>();
		try {
			photos = photoDao.getPhotoByCatOnHomepage(typeId, new PageInfo(0, count));
		} catch(Exception e) {
			logger.error("获得首页相册表中的图片异常。type:" + typeId, e);
			throw e;
		}
		
		return photos;
	}

	//根据类型生成首页新闻数据
    private List<News> getHomePageNewsByType(int typeId, int count) throws Exception {
		List<News> newsList = new ArrayList<News>();
		try {
			newsList = newsDao.getNewsByTypeExcludeStateHost(typeId, new PageInfo(0, count), new int[]{News.STATE_HOT, News.STATE_HEADLINE});
			for (News theNews : newsList) {
				theNews.setIntroduction(getIntroduction(theNews.getNewsContent()));
			}
		} catch (Exception e) {
			logger.error("获得首页新闻异常", e);
			throw e;
		}
		
		return newsList;
	}
	
	//根据状态生成首页热点新闻数据，头条数据
	private List<News> getHomepageNewsByState(int stateId, int count) throws Exception {
		List<News> newsList = newsDao.selectNewsByState(stateId, new PageInfo(0, count));
		for (News theNews : newsList) {
			theNews.setIntroduction(getIntroduction(theNews.getNewsContent()));
		}
		
		return newsList;
	}

	//生成首页游戏资料列表数据
	private Map<String, Object> getGameDataList() throws Exception {
		Map<String, Object> gameDataMap = new HashMap<String, Object>(); 
		// 所有一级标题
		int i = 1;
		GameDateSer gameSer = ServiceLocator.getGameDateSer();
		List<GameDateType> listFristList = gameSer.getGameDateFristType();
		for (GameDateType type1 : listFristList) {
			List<GameDateType> list = gameSer.getGameDateParentType(type1.getTypeId());
			List<GameDateEx> list1 = new ArrayList<GameDateEx>();
			for (GameDateType type2 : list) {
				 list1.addAll(gameSer.getHomepageGameData(type2.getTypeId()));
			}
			gameDataMap.put("gameDate" + i, list1);
			i++;
		}
		
		return gameDataMap;
	}
	
	//生成玩家排行服务器列表
	private List<PlatformGameServer> getPlayerRankGameServerList() throws Exception {
		
		return gameServerService.getPlayerRankServerList();
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
	
	//取得首页视频
	private List<VideoInfo> getVideoList(int count) throws Exception{

		return videoDao.findAllVideo(new PageInfo(0, count));
	}

	//截取新闻简介
	private String getIntroduction(String contend){
		final int LINE_SIZE = 4;
		final int LINE_LENGTH = 30;
		int intrSize = LINE_LENGTH * LINE_SIZE;
		String introduction = contend.replaceAll("<(.[^>]*)>", "");
		introduction = introduction.replaceAll("&nbsp;", "");
		introduction = introduction.replaceAll("\\s", "");
		try {
			if(introduction.length() > intrSize){
				introduction = StringUtil.substring(introduction, intrSize);
				introduction = introduction + "...";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return introduction;
	}
	

}
