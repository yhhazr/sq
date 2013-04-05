package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.NewsService;
/**
 * 
 * @author hai.yuan
 *
 */
public class NewsServiceImpl implements NewsService {
	
	private static NewsServiceImpl _this;
	
	private static NewsDao newsDao;
	
	private NewsServiceImpl() {
		newsDao = NewsDaoImpl.getInstance();
	}

	public synchronized static NewsServiceImpl getInstance() {
		if (_this == null)
			_this = new NewsServiceImpl();
		return _this;
	}

	@Override
	public boolean insertNews(News news) throws Exception {
		
		return newsDao.insertNews(news);
	}

	@Override
	public boolean updateNews(News news) throws Exception {
		
		return newsDao.updateNews(news);
	}

	@Override
	public boolean deleteNews(int newsId) throws Exception {
		
		return newsDao.deleteNews(newsId);
	}

	@Override
	public News getNewsById(int newsId) throws Exception {
		
		return newsDao.selectNewsById(newsId);
	}

	@Override
	public PaginationResult<News> getNews(PageInfo pageInfo) throws Exception {
		int total = newsDao.getNewsCount();
		List<News> newsList = newsDao.selectNews(pageInfo);
		PaginationResult<News> pageResult = new PaginationResult<News>(total, newsList);
		return pageResult;
	}

	@Override
	public List<NewsType> getTypes() throws Exception {
		
		return newsDao.getTypes();
	}

	@Override
	public List<NewsState> getStates() throws Exception {
		
		return newsDao.getStates();
	}

//	@Override
//	public List<HomepageItem> readHomepageByType(int typeId, String position, int size) throws Exception {
//		
//		return newsDao.readHomepageByType(typeId, position, size);
//	}

	@Override
	public int getNewsCount() throws Exception {
		return newsDao.getNewsCount();
	}
	
	@Override
	public PaginationResult<News> getNewsOnline(PageInfo pageInfo) throws Exception {
		int total = newsDao.getNewsCountOnline();
		List<News> newsList = newsDao.selectNewsOnline(pageInfo);
		PaginationResult<News> pageResult = new PaginationResult<News>(total, newsList);
		return pageResult;
	}

    @Override
    public PaginationResult<News> queryNewsListByTitleKeyword(PageInfo pageInfo, String selectCondition) throws SQLException {
        int total = newsDao.getNewsCountOnline(selectCondition);
        List<News> newsList = newsDao.selectNewsOnline(pageInfo, selectCondition);
        PaginationResult<News> pageResult = new PaginationResult<News>(total, newsList);
        return pageResult;
    }
}