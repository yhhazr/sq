package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneHomepageService;
import com.sz7road.web.service.GeneServerListService;


public class GeneServerListServiceImpl implements GeneServerListService {
	private static final Logger logger = LogManager.getLogger(GeneHomepageServiceImpl.class);
	
	private static GeneServerListServiceImpl _this;
	
	public synchronized static GeneServerListService getInstance() {
		if (_this == null)
			_this = new GeneServerListServiceImpl();
		return _this;
	}
	
	@Override
	public Map<String, Object> getServerListData(){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("serverListDesc", getServerListDesc());
		dataMap.put("serverListAsc", getServerListAsc());
		dataMap.put("serverListNewsList", getServerListNewsData());
		return dataMap; 
	}
	
	// 服务器列表资源（倒序）
	private List<PlatformGameServer> getServerListDesc() {
		List<PlatformGameServer> serverListAsc = getServerListAsc();
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		for (int i = serverListAsc.size() - 1; i >= 0; i--) {
			serverListDesc.add(serverListAsc.get(i));
		}
		
		return serverListDesc;
	}
	
	// 服务器列表资源（正序）
	private List<PlatformGameServer> getServerListAsc() {
		GameServerService gameServiceService = GameServerServiceImpl.getInstance();
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = gameServiceService.getAllGameServer();
			Collections.sort(serverListAsc, new Comparator<PlatformGameServer>() {
					public int compare(PlatformGameServer server0,PlatformGameServer server1) {
						return new Integer(server0.getServerNo()).compareTo(new Integer(server1.getServerNo()));
					}
			});
		} catch (Exception e) {
			logger.error("获得服务区列表错误。", e);
		}

		return serverListAsc;
	}
	
	//生成选服页新闻
	private List<News> getServerListNewsData() {
		NewsDao newsDao = NewsDaoImpl.getInstance();
		
		List<News> newsList = new ArrayList<News>();
		try {
			List<News> newsListSrc = newsDao.selectNewsByType(2, new PageInfo(0, 5));
			for (News news : newsListSrc) {
				int length = news.getArtTitle().length();
				if (length > 17) {
					news.setArtTitle(news.getArtTitle().substring(0, 17) + "...");
				}
				newsList.add(news);
			}
		} catch (Exception e) {
			logger.error("选服页获得新闻错误。", e);
		}
		
		return newsList;
	}
	
	
	
}
