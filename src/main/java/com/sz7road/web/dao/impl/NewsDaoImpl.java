package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.StringUtil;
import com.sz7road.web.dao.NewsDao;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;

public class NewsDaoImpl implements NewsDao {

    private static final Logger logger = LogManager.getLogger(NewsDaoImpl.class);

    private static final String INSERT_NEWS_SQL = "INSERT INTO ddt_news (type_id,news_content,state_id,art_title,create_date,news_img,modify_date) values(?,?,?,?,?,?,?)";

    private static final String DELETE_NEWS_SQL = "DELETE FROM ddt_news WHERE news_id = ?";

    private static final String UPDATE_NEWS_SQL = "UPDATE ddt_news  SET type_id = ?,news_content = ?,exp1=?,state_id=?,art_title=?,exp2=?,exp3=?,exp4=?,exp5=?,news_img=?,modify_date=? WHERE news_id = ? ";

    private static final String SELECT_NEWS_SQL = "SELECT n.news_img, n.news_content, n.type_id,n.news_id,n.art_title,n.create_date,s.state_name,t.type_name,n.state_id, n.modify_date FROM ddt_news n,ddt_news_state s,ddt_news_type t WHERE n.state_id = s.state_id AND n.type_id = t.type_id ";

    //	private static final String NEWS_ID_CONDITION = " AND news_id=? ";
    //
    //	private static final String TYPE_ID_CONDITION = " AND type_id=? ";
    //
    //	private static final String STATE_ID_CONDITION = " AND state_id=? ";
    //	
    private static final String ORDER_BY = " ORDER BY ";

    private static final String SELECT_NEWS_TYPES_SQL = "SELECT * FROM ddt_news_type WHERE 1 ";

    private static final String SELECT_NEWS_STATES_SQL = "SELECT * FROM ddt_news_state WHERE 1 ";

    private static final String SELECT_NEWES_COUNT = "select count(1) count from ddt_news WHERE 1 ";

    private static final String UPDATE_NEWS_STATE_TOP_TO_ORDINARY = "UPDATE ddt_news SET state_id = "
            + News.STATE_COMMON + " WHERE state_id = " + News.STATE_TOP
            + " and type_id = ? order by modify_date limit ? ";

    private static final String UPDATE_NEWS_STATE_HEADLINE_TO_HOT = "UPDATE ddt_news SET state_id = " + News.STATE_HOT
            + " WHERE state_id = " + News.STATE_HEADLINE + " order by modify_date limit ? ";

    private static final String QUERY_NEWS_COUNT_BY_TITLE_KEYWORD = "SELECT COUNT(1) AS count FROM ddt_news WHERE state_id <> 5 and art_title LIKE";

    private static NewsDaoImpl _instance = new NewsDaoImpl();

    private NewsDaoImpl() {
    }

    public static NewsDaoImpl getInstance() {
        return _instance;
    }

    @Override
    public boolean insertNews(final News news) throws Exception {
        boolean result = true;
        result = result && DB.insertUpdate(INSERT_NEWS_SQL, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, news.getTypeId());
                ps.setString(2, news.getNewsContent());
                ps.setInt(3, news.getStateId());
                ps.setString(4, news.getArtTitle());
                ps.setTimestamp(5, DateUtil.getCurrentTimestamp());
                ps.setString(6, news.getNewsImg());
                ps.setTimestamp(7, DateUtil.getCurrentTimestamp());
                ps.executeUpdate();
            }
        });
        if (news.getStateId() == News.STATE_TOP) {
            result = result && updateStateTopToOrdinare(news.getTypeId());
        }
        if (news.getStateId() == News.STATE_HEADLINE) {
            result = result && updateNewsStateHeadLineToOrdinary();
        }
        return result;
    }

    @Override
    public boolean updateNews(final News news) throws Exception {
        boolean result = true;

        result = result && DB.insertUpdate(UPDATE_NEWS_SQL, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, news.getTypeId());
                ps.setString(2, news.getNewsContent());
                ps.setInt(3, Integer.parseInt(news.getExp1()));
                ps.setInt(4, news.getStateId());
                ps.setString(5, news.getArtTitle());
                ps.setInt(6, Integer.parseInt(news.getExp2()));
                ps.setInt(7, Integer.parseInt(news.getExp3()));
                ps.setInt(8, Integer.parseInt(news.getExp4()));
                ps.setInt(9, Integer.parseInt(news.getExp5()));
                ps.setString(10, news.getNewsImg());
                ps.setTimestamp(11, DateUtil.getCurrentTimestamp());
                ps.setInt(12, news.getNewsId());
                ps.executeUpdate();
            }
        });

        if (news.getStateId() == News.STATE_TOP) {
            result = result && updateStateTopToOrdinare(news.getTypeId());
        }
        if (news.getStateId() == News.STATE_HEADLINE) {
            result = result && updateNewsStateHeadLineToOrdinary();
        }
        return result;
    }

    /**
     * 创建时间： 2013-2-28 下午9:37:09
     * 创建人：xin.fang
     * 参数： 
     * @param typeId 新闻类型id
     * 返回值： boolean
     * 方法描述 : 将每种新闻的置顶类型（除最后增加或修改的三条新闻）转换成普通类型
    */
    private boolean updateStateTopToOrdinare(final int typeId) throws SQLException {
        boolean result = true;
        final int[] count = new int[1];
        StringBuilder sql = new StringBuilder(SELECT_NEWES_COUNT);
        sql.append(" and type_id = ?");
        sql.append(" and state_id = ");
        sql.append(News.STATE_TOP);

        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
            }
        });

        //如果是置顶类型的新闻则保留最后修改或增加的3条新闻，其余全降级为热点新闻
        if (count[0] > 3) {
            result = result && DB.insertUpdate(UPDATE_NEWS_STATE_TOP_TO_ORDINARY, new IUStH() {

                @Override
                public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, typeId);
                    stmt.setInt(2, count[0] - 3);
                    stmt.executeUpdate();
                }
            });
        }
        return result;
    }

    /**
     * 创建时间： 2013-3-1 上午10:19:51
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 : 将头条新闻修改成热点新闻
     * @throws SQLException 
    */
    private boolean updateNewsStateHeadLineToOrdinary() throws SQLException {
        boolean result = true;
        final int[] count = new int[1];
        StringBuilder sql = new StringBuilder(SELECT_NEWES_COUNT);
        sql.append(" and state_id = ");
        sql.append(News.STATE_HEADLINE);

        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });

        //如果查询出的头条新闻条数大于一条则将最后修改或增加的保留，其余全部降级为热点新闻
        if (count[0] > 1) {
            result = result && DB.insertUpdate(UPDATE_NEWS_STATE_HEADLINE_TO_HOT, new IUStH() {

                @Override
                public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, count[0] - 1);
                    stmt.executeUpdate();
                }
            });
        }
        return result;
    }

    @Override
    public boolean deleteNews(final int newsId) throws Exception {
        boolean result = false;
        result = DB.insertUpdate(DELETE_NEWS_SQL, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.setInt(1, newsId);
                ps.executeUpdate();
            }
        });
        return result;
    }

    @Override
    public News selectNewsById(final int newsId) throws Exception {
        StringBuffer sql = new StringBuffer(SELECT_NEWS_SQL);
        sql.append(" AND n.news_id=? ");
        final News info = new News();
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    info.setArtTitle(rs.getString("art_title"));
                    info.setCreateDate(rs.getTimestamp("create_date"));
                    //					info.setExp1(""+rs.getInt("exp1"));
                    //					info.setExp2(""+rs.getInt("exp2"));
                    //					info.setExp3(""+rs.getInt("exp3"));
                    //					info.setExp4(""+rs.getInt("exp4"));
                    //					info.setExp5(""+rs.getInt("exp5"));
                    //					info.setHits(rs.getInt("hits"));
                    info.setNewsContent(rs.getString("news_content"));
                    info.setNewsId(rs.getInt("news_id"));
                    info.setStateId(rs.getInt("state_id"));
                    info.setStateName(rs.getString("state_name"));
                    info.setTypeId(rs.getInt("type_id"));
                    info.setNewsImg(rs.getString("news_img"));
                    info.setModifyDate(rs.getTimestamp("modify_date"));
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, newsId);
            }
        });

        return info;
    }

    @Override
    public List<News> selectNews() throws Exception {
        final List<News> newsList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        sql.append(ORDER_BY).append(" n.state_id DESC, n.create_date DESC");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News info = new News();
                    info.setTypeId(rs.getInt("type_id"));
                    info.setArtTitle(rs.getString("art_title"));
                    info.setCreateDate(rs.getTimestamp("create_date"));
                    info.setNewsId(rs.getInt("news_id"));
                    info.setTypeName(rs.getString("type_name"));
                    info.setStateName(rs.getString("state_name"));
                    info.setStateId(rs.getInt("state_id"));
                    newsList.add(info);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsList;
    }

    @Override
    public List<News> selectNews(PageInfo pageInfo) throws Exception {
        final List<News> newsList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        sql.append(ORDER_BY).append(" n.state_id DESC, n.modify_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News info = new News();
                    info.setTypeId(rs.getInt("type_id"));
                    info.setArtTitle(rs.getString("art_title"));
                    info.setCreateDate(rs.getTimestamp("create_date"));
                    info.setNewsId(rs.getInt("news_id"));
                    info.setTypeName(rs.getString("type_name"));
                    info.setStateId(rs.getInt("state_id"));
                    info.setStateName(rs.getString("state_name"));
                    newsList.add(info);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsList;
    }

    @Override
    public List<NewsType> getTypes() throws Exception {
        final List<NewsType> newsTypeList = new ArrayList<NewsType>();
        DB.select(SELECT_NEWS_TYPES_SQL, new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    NewsType newsType = new NewsType();
                    newsType.setNewsTypeId(rs.getInt("type_id"));
                    newsType.setNewsTypeName(rs.getString("type_name"));
                    newsType.setNewsTypeShortName(rs.getString("type_short_name"));
                    newsTypeList.add(newsType);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsTypeList;
    }

    @Override
    public List<NewsState> getStates() throws Exception {
        final List<NewsState> newsStateList = new ArrayList<NewsState>();
        DB.select(SELECT_NEWS_STATES_SQL, new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    NewsState newsState = new NewsState();
                    newsState.setNewsStateId(rs.getInt("state_id"));
                    newsState.setNewsStateName(rs.getString("state_name"));
                    newsStateList.add(newsState);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsStateList;
    }

    @Override
    public int getNewsCount() throws Exception {
        final int[] count = { 0 };
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWES_COUNT);
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return count[0];
    }

    @Override
    public int getNewsCountOnline() throws Exception {
        final int[] count = { 0 };
        StringBuilder sql = new StringBuilder(SELECT_NEWES_COUNT);
        sql.append(" AND state_id <> 5");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return count[0];
    }

    //根据type查询新闻条数 （前台）
    @Override
    public int getCountByType(final int typeId) throws Exception {
        final int[] count = { 0 };
        StringBuilder sql = new StringBuilder(SELECT_NEWES_COUNT);
        sql.append(" AND type_id=? ");
        sql.append(" AND state_id <> 5");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
            }
        });
        return count[0];
    }

    //根据state查询新闻条数 （前台）
    @Override
    public int getCountByState(final int stateId) throws Exception {
        final int[] count = { 0 };
        StringBuilder sql = new StringBuilder(SELECT_NEWES_COUNT);
        sql.append(" AND state_id=? ");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, stateId);
            }
        });
        return count[0];
    }

    @Override
    public List<News> selectNewsByType(final int typeId, PageInfo pageInfo) throws Exception {
        final List<News> newsInfoList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        if (typeId != 0) {
            sql.append(" AND n.type_id=? ");
        }
        sql.append(" and n.state_id <> 5 ");
        sql.append(" order by n.state_id DESC, n.create_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News newsInfo = new News();
                    newsInfo.setNewsId(rs.getInt("news_id"));
                    newsInfo.setTypeId(rs.getInt("type_id"));
                    newsInfo.setTypeName(rs.getString("type_name"));
                    newsInfo.setStateId(rs.getInt("state_id"));
                    newsInfo.setStateName(rs.getString("state_name"));
                    newsInfo.setArtTitle(rs.getString("art_title"));
                    newsInfo.setCreateDate(rs.getTimestamp("create_date"));
                    newsInfo.setNewsContent(rs.getString("news_content"));
                    newsInfo.setNewsImg(rs.getString("news_img"));
                    newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
                    newsInfoList.add(newsInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
            }
        });
        return newsInfoList;
    }

    @Override
    public List<News> selectNewsByState(final int stateId) throws Exception {
        final List<News> newsInfoList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        if (stateId != 0) {
            sql.append(" AND n.state_id=? ");
        }
        sql.append(" order by n.create_date DESC");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News newsInfo = new News();
                    newsInfo.setNewsId(rs.getInt("news_id"));
                    newsInfo.setTypeId(rs.getInt("type_id"));
                    newsInfo.setTypeName(rs.getString("type_name"));
                    newsInfo.setStateName(rs.getString("state_name"));
                    newsInfo.setStateId(rs.getInt("state_id"));
                    newsInfo.setArtTitle(rs.getString("art_title"));
                    newsInfo.setCreateDate(rs.getTimestamp("create_date"));
                    newsInfo.setNewsContent(rs.getString("news_content"));
                    newsInfo.setNewsImg(rs.getString("news_img"));
                    newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
                    newsInfoList.add(newsInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, stateId);
            }
        });
        return newsInfoList;
    }

    @Override
    public List<News> selectNewsByState(final int stateId, PageInfo pageInfo) throws Exception {
        final List<News> newsInfoList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder(SELECT_NEWS_SQL);
        if (stateId != 0) {
            sql.append(" AND n.state_id=? ");
        }
        sql.append(" order by n.modify_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News newsInfo = new News();
                    newsInfo.setNewsId(rs.getInt("news_id"));
                    newsInfo.setTypeId(rs.getInt("type_id"));
                    newsInfo.setStateId(rs.getInt("state_id"));
                    newsInfo.setTypeName(rs.getString("type_name"));
                    newsInfo.setStateName(rs.getString("state_name"));
                    newsInfo.setArtTitle(rs.getString("art_title"));
                    newsInfo.setCreateDate(rs.getTimestamp("create_date"));
                    newsInfo.setNewsContent(rs.getString("news_content"));
                    newsInfo.setNewsImg(rs.getString("news_img"));
                    newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
                    newsInfoList.add(newsInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, stateId);
            }
        });
        return newsInfoList;
    }

    @Override
    public List<News> selectNewsOnline(PageInfo pageInfo) throws Exception {
        final List<News> newsList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        sql.append(" and n.state_id <> 5");
        sql.append(" order by n.state_id DESC, n.modify_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News info = new News();
                    info.setTypeId(rs.getInt("type_id"));
                    info.setStateId(rs.getInt("state_id"));
                    info.setTypeName(rs.getString("type_name"));
                    info.setStateName(rs.getString("state_name"));
                    info.setArtTitle(rs.getString("art_title"));
                    if (rs.getString("art_title") != null) {
                        info.setArtTitle(StringUtil.substring(rs.getString("art_title"), 22) + "...");
                    }
                    info.setCreateDate(rs.getTimestamp("create_date"));
                    info.setNewsId(rs.getInt("news_id"));
                    info.setTypeName(rs.getString("type_name"));
                    info.setModifyDate(rs.getTimestamp("modify_date"));
                    newsList.add(info);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsList;
    }

    @Override
    public List<News> selectNewsOnline() throws Exception {
        final List<News> newsList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NEWS_SQL);
        sql.append(" and n.state_id <> 5");
        sql.append(" order by n.state_id DESC, n.create_date DESC");
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News info = new News();
                    info.setTypeId(rs.getInt("type_id"));
                    info.setStateId(rs.getInt("state_id"));
                    info.setStateName(rs.getString("state_name"));
                    info.setArtTitle(rs.getString("art_title"));
                    info.setCreateDate(rs.getTimestamp("create_date"));
                    info.setNewsId(rs.getInt("news_id"));
                    info.setNewsContent(rs.getString("news_content"));
                    info.setTypeName(rs.getString("type_name"));
                    info.setModifyDate(rs.getTimestamp("modify_date"));
                    newsList.add(info);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsList;
    }

    @Override
    public List<News> getNewsByTypeExcludeStateHost(final int typeId, PageInfo pageInfo, final int [] stateIds)
            throws SQLException {
        final List<News> newsInfoList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder(SELECT_NEWS_SQL);
        if (typeId != 0) {
            sql.append(" and n.type_id= ? ");
        }
        sql.append(" and n.state_id <> 5 ");
        if(null != stateIds){
           for (int stateId : stateIds) {
               sql.append(" and n.state_id <> " + stateId );
           }
        }
        sql.append(" order by n.state_id DESC, n.modify_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News newsInfo = new News();
                    newsInfo.setNewsId(rs.getInt("news_id"));
                    newsInfo.setTypeId(rs.getInt("type_id"));
                    newsInfo.setTypeName(rs.getString("type_name"));
                    newsInfo.setStateId(rs.getInt("state_id"));
                    newsInfo.setStateName(rs.getString("state_name"));
                    newsInfo.setArtTitle(rs.getString("art_title"));
                    newsInfo.setCreateDate(rs.getTimestamp("create_date"));
                    newsInfo.setNewsContent(rs.getString("news_content"));
                    newsInfo.setNewsImg(rs.getString("news_img"));
                    newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
                    newsInfoList.add(newsInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);
            }
        });
        return newsInfoList;
    }

    @Override
    public int getNewsCountOnline(final String selectCondition) throws SQLException {
        final int[] count = new int[1];
        StringBuilder sql = new StringBuilder(QUERY_NEWS_COUNT_BY_TITLE_KEYWORD);
        sql.append(" '%").append(selectCondition).append("%' ");
        DB.select(sql.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return count[0];
    }

    @Override
    public List<News> selectNewsOnline(PageInfo pageInfo, final String selectCondition) throws SQLException {
        final List<News> newsInfoList = new ArrayList<News>();
        StringBuilder sql = new StringBuilder(SELECT_NEWS_SQL);
        sql.append(" and n.state_id <> 5");
        sql.append(" and art_title like '%").append(selectCondition).append("%' ");
        sql.append(" order by n.state_id DESC, n.modify_date DESC");
        sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    News newsInfo = new News();
                    newsInfo.setNewsId(rs.getInt("news_id"));
                    newsInfo.setTypeId(rs.getInt("type_id"));
                    newsInfo.setTypeName(rs.getString("type_name"));
                    newsInfo.setStateId(rs.getInt("state_id"));
                    newsInfo.setStateName(rs.getString("state_name"));
                    newsInfo.setArtTitle(rs.getString("art_title"));
                    newsInfo.setCreateDate(rs.getTimestamp("create_date"));
                    newsInfo.setNewsContent(rs.getString("news_content"));
                    newsInfo.setNewsImg(rs.getString("news_img"));
                    newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
                    newsInfoList.add(newsInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
            }
        });
        return newsInfoList;
    }

}
