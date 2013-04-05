package com.sz7road.web.service;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

/**
 * 
 * @author hai.yuan
 *
 */
public interface NewsService {
	
	public boolean insertNews(final News news) throws Exception;
	
	public boolean updateNews(final News news) throws Exception; 
	
	public boolean deleteNews(final int newsId) throws Exception;
	
	public News getNewsById(final int newsId) throws Exception;
	
	public PaginationResult<News> getNews(PageInfo pageInfo) throws Exception;
	
	public List<NewsType> getTypes() throws Exception;
	
	public List<NewsState> getStates() throws Exception;
	
//	public List<HomepageItem> readHomepageByType(int typeId, String position, int size) throws Exception;
	
	public int getNewsCount() throws Exception;
	
	public PaginationResult<News> getNewsOnline(PageInfo pageInfo) throws Exception;

    /**
     * 根据标题关键字查询新闻
     * 创建时间： 2013-3-1 下午2:42:26
     * 创建人：xin.fang
     * 参数： 
     * 返回值： PaginationResult<News>
     * 方法描述 :
     * @throws SQLException 
    */
    public PaginationResult<News> queryNewsListByTitleKeyword(PageInfo pageInfo, String selectCondition) throws SQLException;
}
