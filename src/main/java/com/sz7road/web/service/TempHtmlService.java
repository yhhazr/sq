package com.sz7road.web.service;

import java.util.List;
import java.util.Map;

import com.sz7road.web.model.newsmanage.News;

public interface TempHtmlService {

	public Map<String, Object> createIndex() throws Exception;
	
	public List<News> getAllNews() throws Exception;
}
