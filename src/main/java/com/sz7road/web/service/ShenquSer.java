package com.sz7road.web.service;

import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface ShenquSer {
	public PaginationResult<News> getAllNewsByType(PageInfo pageInfo, int typeId) throws Exception;
	
}
