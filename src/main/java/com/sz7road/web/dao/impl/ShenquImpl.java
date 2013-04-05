package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.Shenqu;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;

public class ShenquImpl implements Shenqu{

	private static final String GET_ALL_NEWSBY_TYPE = "select dn.*,dnt.* from ddt_news dn,ddt_news_type dnt where dn.type_id=dnt.type_id and dn.type_id=?";
	
	private static final String GET_COUNT = "select count(news_id) count from ddt_news where type_id=?";
	@Override
	public List<News> getAllNewsByType(final PageInfo pageInfo, final int typeId) throws Exception {
		final List<News> list = new ArrayList<News>();
		StringBuilder sql = new StringBuilder();
		sql.append(GET_ALL_NEWSBY_TYPE);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					News news = new News();
					news.setNewsId(rs.getInt("news_id"));
					news.setStateId(rs.getInt("state_id"));
					news.setArtTitle(rs.getString("art_title"));
					news.setNewsContent(rs.getString("news_content"));
					news.setCreateDate(rs.getDate("create_date"));
					news.setTypeShortName(rs.getString("type_short_name"));
					news.setTypeName(rs.getString("type_name"));
					list.add(news);
				}
			}
			
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typeId);
				//ps.execute();
			}
		});
		return list;
	}

	@Override
	public int getCountByType(final int typeId) throws Exception {
		final int[] count = {0};
		DB.select(GET_COUNT, new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
					count[0] = rs.getInt("count");
				}
			}
			
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, typeId);
				//ps.execute();
			}
		});
		return count[0];
	}

}
