package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GameInfoDao;
import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.gamemanage.GameType;
import com.sz7road.web.model.pagination.PageInfo;

public class GameInfoDaoImpl implements GameInfoDao{
	//private static final String INSERT_NEWS_SQL = "INSERT INTO ddt_news (type_id,news_content,state_id,art_title,create_date) values(?,?,?,?,?)";
	
	//private static final String DELETE_NEWS_SQL = "DELETE FROM ddt_news WHERE news_id = ?";

	//private static final String UPDATE_NEWS_SQL = "UPDATE ddt_news  SET type_id = ?,news_content = ?,exp1=?,state_id=?,art_title=?,exp2=?,exp3=?,exp4=?,exp5=? WHERE news_id = ? ";

	//private static final String SELECT_NEWS_SQL = "SELECT * FROM ddt_news WHERE news_id = ? ";
	
	private static final String SELECT_ALL_GAMEINFO_SQL = "SELECT a.*,b.type_name FROM game_info a,game_type b WHERE  a.type_id = b.type_id";
	private static final String SELECT_COUNTS="SELECT count(*) as count from game_info";
	//private static final String SELECT_ALL_NEWS_TYPES_SQL = "SELECT * FROM ddt_news_type";
	
	//private static final String SELECT_ALL_NEWS_STATES_SQL = "SELECT * FROM ddt_news_state";
	private static GameInfoDaoImpl _instance = new GameInfoDaoImpl();

	private GameInfoDaoImpl() {}
	public static GameInfoDaoImpl getInstance() {
		return _instance;
	}  
	@Override
	
	public boolean insertGameInfo(GameInfo gi) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateGameInfo(GameInfo gi) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteGameInfo(int id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameInfo getGameInfoById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameInfo> getGameInfos(PageInfo pageInfo) throws Exception {
		final List<GameInfo> newsList = new ArrayList<GameInfo>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_GAMEINFO_SQL);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameInfo info = new GameInfo();	
					info.setGameId(rs.getInt("game_id"));
					info.setGameName(rs.getString("game_name"));
					info.setConversionScale(rs.getInt("conversion_scale"));
					info.setImageUrl(rs.getString("image_url"));
					info.setTypeId(rs.getInt("type_id"));
					info.setTypeName(rs.getString("type_name"));
					info.setUnitName(rs.getString("unit_name"));
					
					newsList.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return newsList;
	}
	public List<GameInfo> getAllGameInfos() throws Exception {
		final List<GameInfo> newsList = new ArrayList<GameInfo>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_ALL_GAMEINFO_SQL);
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameInfo info = new GameInfo();	
					info.setGameId(rs.getInt("game_id"));
					info.setGameName(rs.getString("game_name"));
					info.setConversionScale(rs.getInt("conversion_scale"));
					info.setImageUrl(rs.getString("image_url"));
					info.setTypeId(rs.getInt("type_id"));
					info.setTypeName(rs.getString("type_name"));
					info.setUnitName(rs.getString("unit_name"));
					
					newsList.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return newsList;
	}
	@Override
	public List<GameType> getTypes() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGameInfoCount() throws Exception {
		final int[] count = { 0 };
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_COUNTS);
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("count");
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return count[0];
	}

	


}
