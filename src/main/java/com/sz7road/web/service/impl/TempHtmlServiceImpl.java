package com.sz7road.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sz7road.web.dao.TempHtmlDao;
import com.sz7road.web.dao.impl.TempHtmlDaoImpl;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.service.TempHtmlService;

public class TempHtmlServiceImpl implements TempHtmlService {

	private static TempHtmlServiceImpl _this;

	private static TempHtmlDao tempHtmlDao;
	
	private TempHtmlServiceImpl() {
		tempHtmlDao = TempHtmlDaoImpl.getInstance();
	}

	public synchronized static TempHtmlServiceImpl getInstance() {
		if (_this == null)
			_this = new TempHtmlServiceImpl();
		return _this;
	}
	@Override
	public Map<String, Object> createIndex() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("newsList", tempHtmlDao.get4News());
		dataMap.put("carouselList", tempHtmlDao.getCarouselImage());
		dataMap.put("cooperateList", tempHtmlDao.getCooperateImage());
		dataMap.put("friendlyList", tempHtmlDao.getFriendlyImage());
		return dataMap;
	}

	@Override
	public List<News> getAllNews() throws Exception {
		
		return tempHtmlDao.getAllNews();
	}


}
