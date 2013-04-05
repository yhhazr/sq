package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.GameDateDao;
import com.sz7road.web.model.gameDateMag.GameDate;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;

public class GameDateDaoImpl implements GameDateDao {

	private static final String GET_ALL_GAMEDATE = "select dgd.*,dgdt.type_short,dgdt.type_name from ddt_game_date dgd,ddt_game_date_type dgdt where dgd.type_id=dgdt.type_id";
	private static final String GET_HOMEPAGE_GAMEDATE = "select dgd.*,dgdt.type_short,dgdt.type_name from ddt_game_date dgd,ddt_game_date_type dgdt where dgd.type_id=dgdt.type_id and dgd.state_id=4";
	private static final String	READ_ALL_GAMEDATE= "select dgd.*,dgdt.type_short,dgdt.type_name,dns.state_name from ddt_game_date dgd,ddt_game_date_type dgdt,ddt_news_state dns where dgd.type_id=dgdt.type_id and dgd.state_id=dns.state_id";
	private static final String GET_COUNT = "select count(id) from ddt_game_date";
	private static final String GET_ALL_TYPE = "select * from ddt_game_date_type";
	private static final String DELETE_GAME = "delete from ddt_game_date where id=?";
	private static final String UPDATE_GAME = "update ddt_game_date set type_id=?,state_id=?,art_title=?,content=?,order_num=? where id=?";
	private static final String CREATE_GAME = "insert into ddt_game_date(type_id,state_id,art_title,content,create_date,order_num) values(?,?,?,?,?,?)";
	private static final String READ_GAMEDATE_BY_TYPE = "select * from ddt_game_date";
	//private static final String GET_PARENT_TYPE = "select * from ddt_game_date_type where parent_id=-1";

	public static GameDateDaoImpl _instence = new GameDateDaoImpl();

	public static GameDateDaoImpl getInstence() {
		return _instence;
	}

	@Override
	public List<GameDateEx> readAllGameDate(PageInfo pageInfo) throws Exception {
		final List<GameDateEx> date = new ArrayList<GameDateEx>();
		StringBuilder str = new StringBuilder();
		str.append(READ_ALL_GAMEDATE);
		str.append(" limit ").append(pageInfo.getStartRow()).append(",")
				.append(pageInfo.getPageSize());
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateEx game = new GameDateEx();
					game.setId(String.valueOf(rs.getInt("id")));
					game.setTypeId(rs.getInt("type_id"));
					game.setTypeName(rs.getString("type_name"));
					game.setStateId(rs.getInt("state_id"));
					game.setStateName(rs.getString("state_name"));
					game.setArtTitle(rs.getString("art_title"));
					game.setContent(rs.getString("content"));
					game.setShortName(rs.getString("type_short"));
					game.setCreateDate(rs.getTimestamp("create_date"));
					game.setOrderNum(String.valueOf(rs.getInt("order_num")));
					date.add(game);
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
			}

		});
		return date;
	}

	@Override
	public int getCount() throws Exception {
		final int[] count = { 0 };
		StringBuilder str = new StringBuilder();
		str.append(GET_COUNT);
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet arg0) throws SQLException {
				while (arg0.next()) {
					count[0] = arg0.getInt(1);
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
			}

		});
		return count[0];
	}

	@Override
	public GameDateEx readGameDateById(final int id) throws Exception {
		final GameDateEx game = new GameDateEx();
		StringBuilder str = new StringBuilder();
		str.append(READ_ALL_GAMEDATE);
		str.append(" and dgd.id=?");
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					game.setId(String.valueOf(rs.getInt("id")));
					game.setTypeId(rs.getInt("type_id"));
					game.setTypeName(rs.getString("type_name"));
					game.setStateId(rs.getInt("state_id"));
					game.setStateName(rs.getString("state_name"));
					game.setArtTitle(rs.getString("art_title"));
					game.setContent(rs.getString("content"));
					game.setShortName(rs.getString("type_short"));
					game.setCreateDate(rs.getTimestamp("create_date"));
					game.setOrderNum(rs.getString("order_num"));
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
				arg0.setInt(1, id);
			}

		});
		return game;
	}

	@Override
	public List<GameDateType> getAllType() throws Exception {
		final List<GameDateType> list = new ArrayList<GameDateType>();
		StringBuilder str = new StringBuilder();
		str.append(GET_ALL_TYPE);
		str.append(" order by type_id");
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateType game = new GameDateType();
					game.setTypeId(rs.getInt("type_id"));
					game.setTypeName(rs.getString("type_name"));
					game.setShortName(rs.getString("type_short"));
					game.setParentId(rs.getInt("parent_id"));
					list.add(game);
				}
			}

			@Override
			public void setParams(PreparedStatement arg0) throws SQLException {
			}

		});
		return list;
	}

	@Override
	public boolean updateGameDate(final GameDate game) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_GAME, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, game.getTypeId());
				ps.setInt(2, game.getStateId());
				ps.setString(3, game.getArtTitle());
				ps.setString(4, game.getContent());
				ps.setInt(5, Integer.parseInt(game.getOrderNum()));
				ps.setInt(6, Integer.parseInt(game.getId()));
				ps.executeUpdate();
			}

		});
		return result;
	}

	@Override
	public boolean createGameDate(final GameDate game) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(CREATE_GAME, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, game.getTypeId());
				ps.setInt(2, game.getStateId());
				ps.setString(3, game.getArtTitle());
				ps.setString(4, game.getContent());
				ps.setTimestamp(5, DateUtil.getCurrentTimestamp());
				ps.setInt(6, Integer.parseInt(game.getOrderNum()));
				ps.executeUpdate();
			}

		});
		return result;
	}
	
	@Override
	public List<GameDateType> getGameDateParentType(final int parentId)
			throws Exception {
		final List<GameDateType> list = new ArrayList<GameDateType>();
		StringBuilder str = new StringBuilder();
		str.append(GET_ALL_TYPE);
		str.append(" where parent_id = ? order by  order_id");
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateType gameDateType = new GameDateType();
					gameDateType.setParentId(rs.getInt("parent_id"));
					gameDateType.setTypeId(rs.getInt("type_id"));
					gameDateType.setShortName(rs.getString("type_short"));
					gameDateType.setTypeName(rs.getString("type_name"));
					list.add(gameDateType);
				}

			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, parentId);
			}
		});
		return list;
	}

	@Override
	public List<GameDate> getGameDataChildType(final int typeId)
			throws Exception {
		final List<GameDate> list = new ArrayList<GameDate>();
		StringBuilder str = new StringBuilder();
		str.append(READ_GAMEDATE_BY_TYPE);
		str.append(" where type_id = ? order by order_num");
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while(rs.next()){
				GameDate gameDate = new GameDate();
				gameDate.setId(String.valueOf(rs.getInt("id")));
				gameDate.setTypeId(rs.getInt("type_id"));
				gameDate.setStateId(rs.getInt("state_id"));
				gameDate.setArtTitle(rs.getString("art_title"));
				gameDate.setContent(rs.getString("content"));
				gameDate.setCreateDate(rs.getDate("create_date"));
				gameDate.setOrderNum(String.valueOf(rs.getInt("order_num")));
				list.add(gameDate);
			}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, typeId);
			}
		});
		return list;
	}

	@Override
	public boolean deleteGameDate(final int id) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_GAME, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setInt(1, id);

				ps.executeUpdate();
			}

		});
		return result;
	}

	@Override
	public List<GameDateEx> getAllGameData(final int typeId) throws Exception {
		final List<GameDateEx> list = new ArrayList<GameDateEx>();
		StringBuilder str = new StringBuilder();
		str.append(GET_ALL_GAMEDATE);
		str.append(" and dgd.type_id=").append(typeId);
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateEx gameDate = new GameDateEx();
					gameDate.setId(String.valueOf(rs.getInt("id")));
					gameDate.setTypeId(rs.getInt("type_id"));
					gameDate.setStateId(rs.getInt("state_id"));
					gameDate.setArtTitle(rs.getString("art_title"));
					gameDate.setContent(rs.getString("content"));
					gameDate.setCreateDate(rs.getDate("create_date"));
					gameDate.setOrderNum(String.valueOf(rs.getInt("order_num")));
					gameDate.setShortName(rs.getString("type_short"));
					gameDate.setTypeName(rs.getString("type_name"));
					list.add(gameDate);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return list;
	}

	@Override
	public List<GameDateEx> getHomepageGameData(int typeId) throws Exception {
		final List<GameDateEx> list = new ArrayList<GameDateEx>();
		StringBuilder str = new StringBuilder();
		str.append(GET_HOMEPAGE_GAMEDATE);
		str.append(" and dgd.type_id=").append(typeId);
		DB.select(str.toString(), new ParamReadStH() {

			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateEx gameDate = new GameDateEx();
					gameDate.setId(String.valueOf(rs.getInt("id")));
					gameDate.setTypeId(rs.getInt("type_id"));
					gameDate.setStateId(rs.getInt("state_id"));
					gameDate.setArtTitle(rs.getString("art_title"));
					gameDate.setContent(rs.getString("content"));
					gameDate.setCreateDate(rs.getDate("create_date"));
					gameDate.setOrderNum(String.valueOf(rs.getInt("order_num")));
					gameDate.setShortName(rs.getString("type_short"));
					gameDate.setTypeName(rs.getString("type_name"));
					list.add(gameDate);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return list;
	}
	
	@Override
	public List<GameDateEx> getAllGameData(final int typeId, final int id, final PageInfo pageInfo) throws Exception {
		final List<GameDateEx> list = new ArrayList<GameDateEx>();
		StringBuilder str = new StringBuilder();
		str.append(GET_ALL_GAMEDATE);
		str.append(" and dgd.type_id=? ");
		str.append(" and dgd.id<>? ");
		str.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(str.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateEx gameDate = new GameDateEx();
					gameDate.setId(String.valueOf(rs.getInt("id")));
					gameDate.setTypeId(rs.getInt("type_id"));
					gameDate.setStateId(rs.getInt("state_id"));
					gameDate.setArtTitle(rs.getString("art_title"));
					gameDate.setContent(rs.getString("content"));
					gameDate.setCreateDate(rs.getDate("create_date"));
					gameDate.setOrderNum(String.valueOf(rs.getInt("order_num")));
					gameDate.setShortName(rs.getString("type_short"));
					gameDate.setTypeName(rs.getString("type_name"));
					list.add(gameDate);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, typeId);
				stmt.setInt(2, id);
			}
		});
		return list;
	}

	@Override
	public List<GameDateEx> getAllGameData() throws Exception {
		final List<GameDateEx> list = new ArrayList<GameDateEx>();
		StringBuilder str = new StringBuilder();
		str.append(GET_ALL_GAMEDATE);
		DB.select(str.toString(), new ParamReadStH() {
			
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameDateEx gameDate = new GameDateEx();
					gameDate.setId(String.valueOf(rs.getInt("id")));
					gameDate.setTypeId(rs.getInt("type_id"));
					gameDate.setStateId(rs.getInt("state_id"));
					gameDate.setArtTitle(rs.getString("art_title"));
					gameDate.setContent(rs.getString("content"));
					gameDate.setCreateDate(rs.getDate("create_date"));
					gameDate.setOrderNum(String.valueOf(rs.getInt("order_num")));
					gameDate.setShortName(rs.getString("type_short"));
					gameDate.setTypeName(rs.getString("type_name"));
					list.add(gameDate);
				}
			}
			
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
			}
		});
		return list;
	}



}
