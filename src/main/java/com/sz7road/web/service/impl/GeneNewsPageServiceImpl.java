package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.dao.impl.NewsDaoImpl;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneNewsPageService;

/**
 * 第一种获得所有新闻数据的方法：
 * while(hasMoreNews()){getConcreteNewsPageData();}
 * @author john.jiang
 *
 */
public class GeneNewsPageServiceImpl implements GeneNewsPageService {

    private static final Logger logger = LogManager.getLogger(GeneNewsPageServiceImpl.class);

    private static GeneNewsPageService _this;

    private static NewsDao newsDao;
    private static HomepageItemDao homepageItemDao;
    private static GameServerService gameServerService;

    private News news = null;
    private List<News> concreteNewsList = new ArrayList<News>();
    private int concreteNewsIndex = 0;
    private boolean moreNews = true;

    private List<PlatformGameServer> gameServerListDesc = new ArrayList<PlatformGameServer>();

    private List<NewsType> newsTypeList = new ArrayList<NewsType>();
    //	private List<News> newsListByType = new ArrayList<News>();
    //	private List<News> newsListByState = new ArrayList<News>();
    private int newsTypeListIndex = 0;
    private int pageNumber = 0;
    private int pageCount = 0;
    private boolean moreNewsList = true;
    private boolean getNewsListByTypeOver = false;
    private boolean getNewsListByStateOver = false;
    private static final int PAGE_SIZE = Integer.parseInt(SystemConfig.getProperty("list.show.page.size"));

    private static final int SERVER_SIZE = 5;
    private static final int RELATED_NEWS_SIZE = 6;

    //模板数据命名
    private static String NEWS_LIST = "newsList";
    private static String TOTAL_PAGE = "totalPage";
    private static String CURRENT_PAGE = "currentPage";
    private static String TYPE_ID = "typeId";

    public GeneNewsPageServiceImpl() {
        newsDao = NewsDaoImpl.getInstance();
        homepageItemDao = HomepageItemDaoImpl.getInstance();
        gameServerService = GameServerServiceImpl.getInstance();
    }

    public synchronized static GeneNewsPageService getInstance() {
        if (_this == null)
            _this = new GeneNewsPageServiceImpl();
        return _this;
    }

    //判断生成具体新闻页时是否还有新闻数据
    @Override
    public boolean hasMoreNews() {
        boolean currentMoreNews = moreNews;
        if (!moreNews) {
            moreNews = true;
            gameServerListDesc.clear();
            concreteNewsList.clear();
        }

        return currentMoreNews;
    }

    //获得单个新闻内页数据（action层循环调用） 
    @Override
    public Map<String, Object> getConcreteNewsPageData() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (concreteNewsList.size() == 0) {
            try {
                concreteNewsList = getAllNews();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            concreteNewsIndex = 0;
        }
        List<News> relatedNewsList = new ArrayList<News>();
        if (concreteNewsIndex <= (concreteNewsList.size() - 1)) {
            news = concreteNewsList.get(concreteNewsIndex);
            dataMap.put("news", news);
            int id = news.getNewsId();
            int typeId = news.getTypeId();
            relatedNewsList = getRelatedNewsList(typeId, RELATED_NEWS_SIZE + 1, id);
        }
        dataMap.put("relatedNewsList", relatedNewsList);
        concreteNewsIndex++;
        if (concreteNewsIndex >= concreteNewsList.size()) {
            concreteNewsIndex = 0;
            moreNews = false;
        }

        return dataMap;
    }

    //	//生成所有新闻列表页数据
    //	@Override
    //	public Map<String, Object> getAllNewsListData() throws Exception {
    //		Map<String, Object> dataMap = new HashMap<String, Object>();
    //		List<News> news;
    //		try {
    //			news = newsDao.selectNewsOnline();
    //		} catch (Exception e) {
    //			logger.error("生成所有新闻列表数据异常。", e);
    //			throw e;
    //		}
    //		dataMap.put("news", news);
    //		dataMap.put("totalCount", news.size());
    //		
    //		return dataMap;
    //	}

    //	//获得所有新闻内页数据
    //	public List<Map<String, Object>> getAllConcreteNewsPageData() {
    //		Map<String, Object> map = new HashMap<String, Object>();
    //		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    //		try {
    //			List<News> newsList = newsDao.selectNewsOnline();
    //			for (News news : newsList) {
    //				map.put("news", news);
    //				dataList.add(map);
    //			}
    //		} catch (Exception e) {
    //			logger.error("生成所有新闻内页数据时，获得所有新闻数据异常。", e);
    //		}
    //		
    //		return dataList;
    //	}

    //判断生成新闻列表页时是否还有新闻数据
    @Override
    public boolean hasMoreNewsList() {
        boolean currentMoreNewsList = moreNewsList;
        if (!moreNewsList) {
            moreNewsList = true;
            getNewsListByTypeOver = false;
            getNewsListByStateOver = false;
            gameServerListDesc.clear();
            newsTypeList.clear();
        }

        return currentMoreNewsList;
    }

    //获得单个新闻列表页数据（action层循环调用） 
    @Override
    public Map<String, Object> getNewsListPageData() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (!getNewsListByStateOver) {
            dataMap = getNewsListByStatePageData();
        } else if (!getNewsListByTypeOver) {
            dataMap = getNewsListByTypePageData();
        } else {
            moreNewsList = false;
            getNewsListByStateOver = false;
            getNewsListByTypeOver = false;
        }

        return dataMap;
    }

    //根据type获得单个新闻列表页数据（action层循环调用） 
    public Map<String, Object> getNewsListByTypePageData() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();

        if (newsTypeList.size() == 0) {
            newsTypeList = getNewsTypes();
            newsTypeListIndex = 0;
        }
        if (newsTypeList.size() > 0) {
            int typeId = newsTypeList.get(newsTypeListIndex).getNewsTypeId();
            if (pageCount == 0) {
                int newsCount = getNewsCountByType(typeId);
                if (newsCount % PAGE_SIZE == 0) {
                    pageCount = newsCount / PAGE_SIZE;
                } else {
                    pageCount = newsCount / PAGE_SIZE + 1;
                }
            }
            List<News> newsListByType = new ArrayList<News>();
            newsListByType = getNewsByTypeExcludeStateHost(typeId, new PageInfo(pageNumber * PAGE_SIZE, PAGE_SIZE),
                    new int[]{News.STATE_HOT, News.STATE_HEADLINE});
            dataMap.put("newsList", newsListByType);
            dataMap.put("totalPage", pageCount);
            dataMap.put("currentPage", pageNumber + 1);
            dataMap.put("typeId", typeId);
            pageNumber++;
            if (pageNumber >= pageCount) {
                pageCount = 0;
                pageNumber = 0;
                newsTypeListIndex++;
                if (newsTypeList.size() <= newsTypeListIndex) {
                    newsTypeListIndex = 0;
                    getNewsListByTypeOver = true;
                    checkGetNewsListDataOver();
                }
            }
        }

        return dataMap;
    }

    /**
     * 获取出热点新闻外的新闻
     * 创建时间： 2013-2-28 下午8:54:04
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<News>
     * 方法描述 :
     * @throws SQLException 
    */
    private List<News> getNewsByTypeExcludeStateHost(int typeId, PageInfo pageInfo, int [] stateIds) throws SQLException {
        List<News> newsList = newsDao.getNewsByTypeExcludeStateHost(typeId, pageInfo, stateIds);
        for (News news : newsList) {
            String introduction = getIntroduction(news.getNewsContent());
            news.setIntroduction(introduction);
        }

        return newsList;
    }

    //根据state获得单个新闻列表页数据（action层循环调用）
    public Map<String, Object> getNewsListByStatePageData() throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        if (pageCount == 0) {
            int newsCount = newsDao.getCountByState(News.STATE_HOT);
            if (newsCount % PAGE_SIZE == 0) {
                pageCount = newsCount / PAGE_SIZE;
            } else {
                pageCount = newsCount / PAGE_SIZE + 1;
            }
        }
        List<News> newsListByState = new ArrayList<News>();
        newsListByState = newsDao.selectNewsByState(News.STATE_HOT, new PageInfo(pageNumber * PAGE_SIZE, PAGE_SIZE));
        dataMap.put("newsList", newsListByState);
        dataMap.put("totalPage", pageCount);
        dataMap.put("currentPage", pageNumber + 1);
        dataMap.put("typeId", 0);
        pageNumber++;
        if (pageNumber >= pageCount) {
            pageCount = 0;
            pageNumber = 0;
            getNewsListByStateOver = true;
            checkGetNewsListDataOver();
        }

        return dataMap;
    }

    public void checkGetNewsListDataOver() {
        if (getNewsListByStateOver && getNewsListByTypeOver) {
            moreNewsList = false;
        }
    }

    //生成相关新闻列表
    public List<News> getRelatedNewsList(int typeId, int size, int newsId) throws Exception {
        List<News> newsList = new ArrayList<News>();
        newsList = newsDao.selectNewsByType(typeId, new PageInfo(0, size));
        for (int i = 0; i < newsList.size(); i++) {
            News currentNews = newsList.get(i);
            if (currentNews.getNewsId() == newsId) {
                newsList.remove(i);
            }
        }
        if (newsList.size() > RELATED_NEWS_SIZE) {
            newsList.remove(newsList.size() - 1);
        }

        return newsList;
    }

    //根据type获得新闻
    public List<News> getNewsByType(int typeId, PageInfo pageInfo) throws Exception {
        List<News> newsList = newsDao.selectNewsByType(typeId, pageInfo);
        for (News news : newsList) {
            String introduction = getIntroduction(news.getNewsContent());
            news.setIntroduction(introduction);
        }

        return newsList;
    }

    //构造新闻简介
    public String getIntroduction(String contend) {
        String introduction = contend.replaceAll("<(.[^>]*)>", "");
        introduction = introduction.replaceAll("&nbsp;", "");
        introduction = introduction.replaceAll("\\s", "");
        if (introduction.length() > 30 * 4) {
            introduction = introduction.substring(0, 30 * 4);
            introduction = introduction + "...";
        }

        return introduction;
    }

    //获得新闻类型列表
    private List<NewsType> getNewsTypes() throws Exception {
        List<NewsType> types = new ArrayList<NewsType>();
        try {
            types = newsDao.getTypes();
        } catch (Exception e) {
            logger.error("获得新闻类型列表异常", e);
            throw e;
        }

        return types;
    }

    //获得所有分页新闻
    public List<News> getNews(PageInfo pageInfo) throws Exception {

        return newsDao.selectNews(pageInfo);
    }

    public int getNewsCount() throws Exception {

        return newsDao.getNewsCountOnline();
    }

    public int getNewsCountByType(int typeId) throws Exception {

        return newsDao.getCountByType(typeId);
    }

    //获得所有新闻
    public List<News> getAllNews() throws Exception {

        return newsDao.selectNewsOnline();
    }

    //生成玩家排行服务器列表
    private List<PlatformGameServer> getPlayerRankGameServerList() throws Exception {

        return gameServerService.getPlayerRankServerList();
    }

    //首页服务区列表（倒序）
    private List<PlatformGameServer> getGameServerList(int count) throws Exception {
        if (gameServerListDesc.size() == 0) {
            gameServerListDesc = gameServerService.buildGameServerListDesc();
        }
        List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
        if (gameServerListDesc.size() >= count) {
            serverListDesc = gameServerListDesc.subList(0, count);
        }

        return serverListDesc;
    }

    //生成最新新闻图片数据
    public HomepageItem getNewestNewsData() {
        HomepageItem newestNews = null;
        try {
            List<HomepageItem> newestNewsList = homepageItemDao.selectItemByTypeIdAndPosition(
                    HomepageItem.TYPE_PICTURE, HomepageItem.POSITION_NEWEST_NEWS, 1);
            if (newestNewsList.size() > 0) {
                newestNews = newestNewsList.get(0);
            }
        } catch (Exception e) {
            logger.error("生成新闻页最新新闻图片数据异常。", e);
        }

        return newestNews;
    }

}
