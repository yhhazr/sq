package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GenerateHtmlDao;
import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.gamemanage.GameServer;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;

public class GenerateHtmlDaoImpl implements GenerateHtmlDao {

	private static GenerateHtmlDaoImpl _instance = new GenerateHtmlDaoImpl();

	private GenerateHtmlDaoImpl() {
	}

	public static GenerateHtmlDaoImpl getInstance() {
		return _instance;
	}

	private static final String SELECT_NEWS_BY_ID_SQL = "SELECT dn.news_id,dn.type_id,dn.art_title,dnt.type_short_name,dnt.type_name,dn.create_date FROM ddt_news dn,ddt_news_type dnt WHERE dn.type_id = dnt.type_id AND dnt.type_id = ? order by dn.create_date limit ?";

	private static final String SELECT_ALLNEWS_BY_ID_SQL = "SELECT dn.news_id,dn.type_id,dn.art_title,dnt.type_short_name,dnt.type_name,dn.create_date,dn.modify_date FROM ddt_news dn,ddt_news_type dnt WHERE dn.type_id = dnt.type_id AND dnt.type_id = ? order by dn.create_date";
	
	private static final String SELECT_LEFT_RIGHT_SQL = "select * from ddt_homepage_manage where type_id=? and position=? order by create_date DESC";
	
	private static final String SELECT_PAGE_BY_POSITION_SQL = "select * from ddt_homepage_manage where  position=?";
	
	private static final String GET_ALL_NEWSBY_TYPE = "select dn.*,dnt.* from ddt_news dn,ddt_news_type dnt where dn.type_id=dnt.type_id and dn.type_id=?";
	//state_id = 5表示新闻状态为影藏状态
	private static final String GET_COUNT = "select count(news_id) count from ddt_news where type_id=?";
	
	private static final String GET_NEWS_TYPE = "select * from ddt_news_type";
	
	private static final String GET_ALL_NEWS = "select * from ddt_news";
	
	private static final String SELECT_ALL_GAMEINFO_SQL = "SELECT a.*,b.type_name FROM game_info a,game_type b WHERE  a.type_id = b.type_id";
	
	private static final String SELECT_ALL_GAMESERVER_SQL = "select * from game_server where game_id=? and State='sHot' order by order_id ";

	private static final String SELECT_GAMESERVER_SQL = "select * from game_server order by order_id desc ";
	
//	private static final String SELECT_HOME_PHOTO = "select * from ddt_photo where cat_id=?";
	
//	public List<Photo> getHomePhoto(final int typeId, final String position) throws Exception {
//		
//		StringBuilder sb = new StringBuilder();
//		sb.append(SELECT_HOME_PHOTO);
//		if(typeId == 94){
//			sb.append(" AND photo_show = 'true' ORDER BY photo_id DESC limit 0,4");
//		}else{
//			sb.append(" AND photo_show = 'true' ORDER BY photo_id DESC limit 0,3"); 
//		}
//		final List<Photo> list = new ArrayList<Photo>();
//		DB.select(sb.toString(), new ParamReadStH() {
//
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					Photo item = new Photo();
//					item.setPhotoId(rs.getInt("photo_id"));
//					item.setCatId(rs.getInt("cat_id"));
//					item.setPhotoName(rs.getString("photo_name"));
//					item.setThumbnail(rs.getString("thumbnail"));
//					list.add(item);
//				}
//			}
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, typeId);
//			}
//
//		});
//		return list;
//	}
//	


//	@Override
//	public List<News> getInfobyID(final int TypeID, final int count)
//			throws Exception {
//		final List<News> newsInfoList = new ArrayList<News>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(SELECT_NEWS_BY_ID_SQL);
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					News newsInfo = new News();
//					newsInfo.setNewsId(rs.getInt("NEWS_ID"));
//					newsInfo.setTypeId(rs.getInt("TYPE_ID"));
//					newsInfo.setArtTitle(rs.getString("ART_TITLE"));
//					newsInfo.setTypeName(rs.getString("TYPE_NAME"));
//					newsInfo.setTypeShortName(rs.getString("TYPE_SHORT_NAME"));
//					newsInfo.setCreateDate(rs.getDate("CREATE_DATE"));
//					newsInfoList.add(newsInfo);
//				}
//			}
//
//			@Override
//			public void setParams(PreparedStatement stmt) throws SQLException {
//				stmt.setInt(1, TypeID);
//				// int rows =
//				// Integer.parseInt(SystemConfig.getProperty("online.homepage.news.showNum"));
//				stmt.setInt(2, count);
//			}
//		});
//		return newsInfoList;
//	}

//	@Override
//	public List<News> getAllInfobyID(final int TypeID) throws Exception {
//		final List<News> newsInfoList = new ArrayList<News>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(SELECT_ALLNEWS_BY_ID_SQL);
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					News newsInfo = new News();
//					newsInfo.setNewsId(rs.getInt("NEWS_ID"));
//					newsInfo.setTypeId(rs.getInt("TYPE_ID"));
//					newsInfo.setArtTitle(rs.getString("ART_TITLE"));
//					newsInfo.setTypeName(rs.getString("TYPE_NAME"));
//					newsInfo.setTypeShortName(rs.getString("TYPE_SHORT_NAME"));
//					newsInfo.setCreateDate(rs.getDate("CREATE_DATE"));
//					newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
//					newsInfoList.add(newsInfo);
//				}
//			}
//
//			@Override
//			public void setParams(PreparedStatement stmt) throws SQLException {
//				stmt.setInt(1, TypeID);
//				// int rows =
//				// Integer.parseInt(SystemConfig.getProperty("online.homepage.news.showNum"));
//				//stmt.setInt(2, count);
//			}
//		});
//		return newsInfoList;
//	}

	
	
//	@Override
//	public List<News> getAllNewsByType(final PageInfo pageInfo, final int typeId) throws Exception {
//		final List<News> list = new ArrayList<News>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(GET_ALL_NEWSBY_TYPE);
//		sql.append(" and dn.state_id <> 5");
//		sql.append(" order by state_id DESC, modify_date DESC");
//		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
//		DB.select(sql.toString(), new ParamReadStH() {
//			
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while(rs.next()){
//					News news = new News();
//					news.setNewsId(rs.getInt("news_id"));
//					news.setStateId(rs.getInt("state_id"));
//					news.setArtTitle(rs.getString("art_title"));
//					news.setNewsContent(rs.getString("news_content"));
//					news.setCreateDate(rs.getDate("create_date"));
//					news.setTypeShortName(rs.getString("type_short_name"));
//					news.setTypeName(rs.getString("type_name"));
//					news.setTypeId(rs.getInt("type_id"));
//					news.setModifyDate(rs.getTimestamp("modify_date"));
//					list.add(news);
//				}
//			}
//			
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, typeId);
//				//ps.execute();
//			}
//		});
//		return list;
//	}

//	@Override
//	public int getCountByType(final int typeId) throws Exception {
//		final int[] count = {0};
//		StringBuilder sb = new StringBuilder();
//		sb.append(GET_COUNT);
//		sb.append(" and state_id <> 5");
//		DB.select(sb.toString(), new ParamReadStH() {
//			
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while(rs.next()){
//					count[0] = rs.getInt("count");
//				}
//			}
//			
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, typeId);
//				//ps.execute();
//			}
//		});
//		return count[0];
//	}

//	@Override
//	public List<NewsType> getNewType() throws Exception {
//		final List<NewsType> list = new ArrayList<NewsType>();
//		DB.select(GET_NEWS_TYPE, new ParamReadStH() {
//			
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while(rs.next()){
//					NewsType news = new NewsType();
//					news.setNewsTypeId(rs.getInt("type_id"));
//					//news.setNewsTypeShortName(rs.getString("type_short_name"));
//					news.setNewsTypeName(rs.getString("type_name"));
//					list.add(news);
//				}
//			}
//			
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {}
//		});
//		return list;
//	}

//	@Override
//	public List<News> getAllNewsInfo() throws Exception {
//		final List<News> newsInfoList = new ArrayList<News>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(GET_ALL_NEWS);
//		sql.append(" where state_id <> 5");
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					News newsInfo = new News();
//					newsInfo.setNewsId(rs.getInt("news_id"));
//					newsInfo.setTypeId(rs.getInt("type_id"));
//					newsInfo.setArtTitle(rs.getString("art_title"));
//					//newsInfo.setTypeName(rs.getString("type_name"));
//					//newsInfo.setTypeShortName(rs.getString("type_short_name"));
//					newsInfo.setCreateDate(rs.getTimestamp("create_date"));
//					newsInfo.setNewsContent(rs.getString("news_content"));
//					newsInfo.setStateId(rs.getInt("state_id"));
//					newsInfo.setModifyDate(rs.getTimestamp("modify_date"));
//					newsInfoList.add(newsInfo);
//				}
//			}
//
//			@Override
//			public void setParams(PreparedStatement stmt) throws SQLException {}
//		});
//		return newsInfoList;
//	}

//	@Override
//	public List<GameInfo> getGameList() throws Exception {
//		final List<GameInfo> newsList = new ArrayList<GameInfo>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(SELECT_ALL_GAMEINFO_SQL);
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					GameInfo info = new GameInfo();	
//					info.setGameId(rs.getInt("game_id"));
//					info.setGameName(rs.getString("game_name"));
//					info.setConversionScale(rs.getInt("conversion_scale"));
//					info.setImageUrl(rs.getString("image_url"));
//					info.setTypeId(rs.getInt("type_id"));
//					info.setTypeName(rs.getString("type_name"));
//					info.setUnitName(rs.getString("unit_name"));	
//					newsList.add(info);
//				}
//			}
//			@Override
//			public void setParams(PreparedStatement stmt) throws SQLException {}
//		});
//		return newsList;
//	}
//	@Override
//	public List<GameServer>getGameServerListById(final int gameId, boolean isDesc)throws Exception{
//		final List<GameServer> newsList = new ArrayList<GameServer>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(SELECT_ALL_GAMESERVER_SQL);
//		if(isDesc){
//			sql.append(" desc ");
//		}
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					GameServer info = new GameServer();	
//					info.setChargeKey(rs.getString("ChargeKey"));
//					info.setChargeUrl(rs.getString("ChargeUrl"));
//					info.setGameId(rs.getInt("game_id"));
//					info.setId(rs.getInt("id"));
//					info.setLink(rs.getString("Link"));
//					info.setLoginKey(rs.getString("LoginKey"));
//					info.setLoginUrl(rs.getString("LoginUrl"));
//					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
//					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
//					info.setSite(rs.getString("Site"));
//					info.setState(rs.getString("State"));
//					info.setRecommend(rs.getString("recommend"));
//					info.setServerName(rs.getString("server_name"));
//					info.setTopGradeUrl(rs.getString("top_grade_url"));
//					info.setOrderId(rs.getInt("order_id"));
//					info.setStateInformation(rs.getString("StateInformation"));
//					info.setStartGameTime(rs.getTimestamp("start_game_date"));
//					newsList.add(info);
//				}
//			}
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, gameId);
//				//ps.execute();
//			}
//		});
//		return newsList;
//	}
	
	
//	@Override
//	public List<GameServer> getAllServerListByGameId(final int gameId) throws Exception{
//		final List<GameServer> serverList = new ArrayList<GameServer>();
//		StringBuilder sql = new StringBuilder();
//		sql.append(SELECT_GAMESERVER_SQL);
//		DB.select(sql.toString(), new ParamReadStH() {
//			@Override
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					GameServer info = new GameServer();	
//					info.setChargeKey(rs.getString("ChargeKey"));
//					info.setChargeUrl(rs.getString("ChargeUrl"));
//					info.setGameId(rs.getInt("game_id"));
//					info.setId(rs.getInt("id"));
//					info.setLink(rs.getString("Link"));
//					info.setLoginKey(rs.getString("LoginKey"));
//					info.setLoginUrl(rs.getString("LoginUrl"));
//					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
//					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
//					info.setSite(rs.getString("Site"));
//					info.setState(rs.getString("State"));
//					info.setRecommend(rs.getString("recommend"));
//					info.setServerName(rs.getString("server_name"));
//					info.setTopGradeUrl(rs.getString("top_grade_url"));
//					info.setOrderId(rs.getInt("order_id"));
//					info.setStateInformation(rs.getString("StateInformation"));
//					serverList.add(info);
//				}
//			}
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//			
//			}
//		});
//		return serverList;
//	}
	
//	//获取类型获取页面元素列表
//	@Override
//	public List<HomepageItem> getInfoByPosition(final String position)
//			throws Exception {
//		final List<HomepageItem> list = new ArrayList<HomepageItem>();
//		DB.select(SELECT_PAGE_BY_POSITION_SQL, new ParamReadStH() {
//
//			@Override 
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					HomepageItem item = new HomepageItem();
//					item.setId(rs.getInt("id"));
//					item.setName(rs.getString("name"));
//					item.setUrl(rs.getString("url"));
//					item.setTypeId(Integer.parseInt(rs.getString("type_id")));
//					item.setContent(rs.getString("content"));
//					item.setPosition(position);
//					item.setCreateDate(rs.getTimestamp("create_date"));
//					list.add(item);
//				}
//			}
//
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setString(1, position);
//			}
//
//		});
//		return list;
//	}
//	
//	public List<News> getNews(final int id) throws Exception{
//		StringBuilder sb = new StringBuilder();
//		sb.append(GET_ALL_NEWS);
//		sb.append(" where state_id = ?");
//		sb.append(" order by modify_date DESC");
//		final List<News> infolist = new ArrayList<News>();
//		
//		DB.select(sb.toString(), new ParamReadStH() {
//			@Override 
//			public void handleRead(ResultSet rs) throws SQLException {
//				while (rs.next()) {
//					News info = new News();
//					info.setArtTitle(rs.getString("art_title"));
//					info.setCreateDate(rs.getTimestamp("create_date"));
//					info.setExp1(""+rs.getInt("exp1"));
//					info.setExp2(""+rs.getInt("exp2"));
//					info.setExp3(""+rs.getInt("exp3"));
//					info.setExp4(""+rs.getInt("exp4"));
//					info.setExp5(""+rs.getInt("exp5"));
//					info.setHits(rs.getInt("hits"));
//					info.setNewsContent(rs.getString("news_content"));
//					info.setNewsId(rs.getInt("news_id"));
//					info.setStateId(rs.getInt("state_id"));
//					info.setTypeId(rs.getInt("type_id"));
//					info.setNewsImg(rs.getString("news_img"));
//					info.setModifyDate(rs.getTimestamp("modify_date"));
//					infolist.add(info);
//				}
//			}
//
//			@Override
//			public void setParams(PreparedStatement ps) throws SQLException {
//				ps.setInt(1, id);
//			}
//
//		});
//		return infolist;
//		
//	}
}
