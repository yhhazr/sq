package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;

public interface NewsDao {

	public boolean insertNews(final News news) throws Exception;
	
	public boolean updateNews(final News news) throws Exception; 
	
	public boolean deleteNews(final int newsId) throws Exception;
	
	public News selectNewsById(final int newsId) throws Exception;
	
	public List<News> selectNews() throws Exception;
	
	public List<News> selectNews(PageInfo pageInfo) throws Exception;
	
	public List<NewsType> getTypes() throws Exception;
	
	public List<NewsState> getStates() throws Exception;
	
	public int getNewsCount() throws Exception;
	
//	public List<HomepageItem> readHomepageByType(int typeId, String position, int size) throws Exception;
	
	public List<News> selectNewsByType(int typeId, PageInfo pageInfo) throws Exception;

	public List<News> selectNewsByState(int stateId, PageInfo pageInfo) throws Exception;

	public List<News> selectNewsOnline(PageInfo pageInfo) throws Exception;
	
	public List<News> selectNewsOnline() throws Exception;
	
	public int getNewsCountOnline() throws Exception;
	
	public List<News> selectNewsByState(int stateId) throws Exception;
	
	public int getCountByType(final int typeId) throws Exception;
	
	public int getCountByState(final int stateId) throws Exception;

	 /**
     * 获取出热点新闻外的新闻
     * 创建时间： 2013-2-28 下午8:54:04
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<News>
     * 方法描述 :
	 * @throws SQLException 
    */
    public List<News> getNewsByTypeExcludeStateHost(int typeId, PageInfo pageInfo, int [] stateIds) throws SQLException;

    /**
     * 根据标题关键字查询新闻总数
     * 创建时间： 2013-3-1 下午2:43:30
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :
     * @throws SQLException 
    */
    public int getNewsCountOnline(String selectCondition) throws SQLException;

    /**
     * 根据标题关键字查询新闻
     * 创建时间： 2013-3-1 下午2:48:02
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<News>
     * 方法描述 :
     * @throws SQLException 
    */
    public List<News> selectNewsOnline(PageInfo pageInfo, String selectCondition) throws SQLException;
}
