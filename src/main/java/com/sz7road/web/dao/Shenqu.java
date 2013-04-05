package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;

public interface Shenqu {
	public List<News> getAllNewsByType(PageInfo pageInfo, int typeId) throws Exception;
	
	public int getCountByType(int typeId) throws Exception;
}
