package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.Shenqu;
import com.sz7road.web.dao.impl.ShenquImpl;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.ShenquSer;

public class ShenquSerImpl implements ShenquSer {
	Shenqu shquDao = new ShenquImpl();

	@Override
	public PaginationResult<News> getAllNewsByType(PageInfo pageInfo, int typeId) throws Exception {
		int total = shquDao.getCountByType(typeId);
		List<News> newsList = shquDao.getAllNewsByType(pageInfo, typeId);
		PaginationResult<News> pageResult = new PaginationResult<News>(total, newsList);

		return pageResult;
	}

}
