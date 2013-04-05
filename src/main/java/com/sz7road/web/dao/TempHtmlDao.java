package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;

public interface TempHtmlDao {

	public List<News> get4News() throws Exception;
	
	public List<HomepageItem> getCarouselImage() throws Exception;
	
	public List<HomepageItem> getCooperateImage() throws Exception;
	
	public List<HomepageItem> getFriendlyImage() throws Exception;
	//新闻内容页
	public List<News> getAllNews() throws Exception;
}
