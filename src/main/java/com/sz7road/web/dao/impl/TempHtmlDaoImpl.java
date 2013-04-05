package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.dao.TempHtmlDao;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;

public class TempHtmlDaoImpl implements TempHtmlDao {

	private static TempHtmlDaoImpl _instance = new TempHtmlDaoImpl();

	private TempHtmlDaoImpl() {}

	public static TempHtmlDaoImpl getInstance() {
		return _instance;
	}
	
	private static String SELECT_4NEWS_SQL =  "SELECT * from ddt_news WHERE type_id = 3 ORDER BY create_date DESC LIMIT 4";
	
	private static String SELECT_CAROUSEL_SQL = "SELECT * from ddt_homepage_manage WHERE position = 'carousel' AND type_id = 4";
	
	private static String SELECT_FRIENDLY_SQL = "SELECT * from ddt_homepage_manage WHERE position = 'friendly' AND type_id = 4";

	private static String SELECT_COOPERATE_SQL = "SELECT * from ddt_homepage_manage WHERE position = 'cooperate' AND type_id = 4";
	
	private static String SELECT_ALL_NEWS_SQL = "SELECT dn.news_id,dn.type_id,dn.art_title,dn.news_content,dnt.type_short_name,dnt.type_name,dn.create_date FROM ddt_news dn,ddt_news_type dnt WHERE dn.type_id = dnt.type_id ";
	
	public List<News> get4News() throws Exception {
		final List<News> newsInfoList = new ArrayList<News>();
		DB.select(SELECT_4NEWS_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					News newsInfo = new News();
					newsInfo.setNewsId(rs.getInt("news_id"));
					newsInfo.setTypeId(rs.getInt("type_id"));
					newsInfo.setTypeId(rs.getInt("state_id"));
					newsInfo.setArtTitle(rs.getString("art_title"));
					newsInfo.setCreateDate(rs.getDate("create_date"));
					newsInfoList.add(newsInfo);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				
			}
		});
		return newsInfoList;
	}

	@Override
	public List<HomepageItem> getCarouselImage() throws Exception {
		final List<HomepageItem> imageList = new ArrayList<HomepageItem>();
		DB.select(SELECT_CAROUSEL_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem image = new HomepageItem();
					image.setContent(SystemConfig.getProperty("online.image.url")+rs.getString("content"));
					image.setId(rs.getInt("id"));
					image.setCreateDate(rs.getTimestamp("create_date"));
					image.setName(rs.getString("name"));
					image.setPosition(rs.getString("position"));
					image.setTypeId(rs.getInt("type_id"));
					image.setUrl(rs.getString("url"));
					imageList.add(image);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {				
			}
		});
		return imageList;
	}

	@Override
	public List<HomepageItem> getCooperateImage() throws Exception {
		final List<HomepageItem> imageList = new ArrayList<HomepageItem>();
		DB.select(SELECT_COOPERATE_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem image = new HomepageItem();
					image.setContent(SystemConfig.getProperty("online.image.url")+rs.getString("content"));
					image.setId(rs.getInt("id"));
					image.setCreateDate(rs.getTimestamp("create_date"));
					image.setName(rs.getString("name"));
					image.setPosition(rs.getString("position"));
					image.setTypeId(rs.getInt("type_id"));
					image.setUrl(rs.getString("url"));
					imageList.add(image);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {				
			}
		});
		return imageList;
	}

	@Override
	public List<HomepageItem> getFriendlyImage() throws Exception {
		final List<HomepageItem> imageList = new ArrayList<HomepageItem>();
		DB.select(SELECT_FRIENDLY_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					HomepageItem image = new HomepageItem();
					image.setContent(SystemConfig.getProperty("online.image.url")+rs.getString("content"));
					image.setId(rs.getInt("id"));
					image.setCreateDate(rs.getTimestamp("create_date"));
					image.setName(rs.getString("name"));
					image.setPosition(rs.getString("position"));
					image.setTypeId(rs.getInt("type_id"));
					image.setUrl(rs.getString("url"));
					imageList.add(image);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {				
			}
		});
		return imageList;
	}

	@Override
	public List<News> getAllNews() throws Exception {
		final List<News> newsInfoList = new ArrayList<News>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_NEWS_SQL);
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					News newsInfo = new News();
					newsInfo.setNewsId(rs.getInt("NEWS_ID"));
					newsInfo.setTypeId(rs.getInt("TYPE_ID"));
					newsInfo.setArtTitle(rs.getString("ART_TITLE"));
					newsInfo.setNewsContent(rs.getString("NEWS_CONTENT"));
					newsInfo.setTypeShortName(rs.getString("TYPE_SHORT_NAME"));
					newsInfo.setTypeName(rs.getString("TYPE_NAME"));
					newsInfo.setCreateDate(rs.getDate("CREATE_DATE"));
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
