package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GameServerDao;
import com.sz7road.web.model.gamemanage.GameServer;

public class GameServerDaoImpl implements GameServerDao {
	private static final String SELECT_GAME_SERVER = "select * from game_server ";
	private static final String ORDER_BY_ORDER_ID_DESC = " order by order_id desc ";
	private static final String GET_COUNT = "select count(id) from game_server ";
	private static final String UPDATE_GAME_SERVER = "update game_server set game_id=?,link=?,selectRoleUrl=?,state=?,stateInfomation=?,site=?,PlayGameUrl=?,LoginKey=?,LoginUrl=?,ChargeKey=?,ChargeUrl=?,server_name=?,recommend=?,top_grade_url=?,order_id=?,start_game_date=? where id=? ;";
	private static final String INSERT_GAME_SERVER = "insert into game_server (game_id,link,selectRoleUrl,state,stateInfomation,site,PlayGameUrl,LoginKey,LoginUrl,ChargeUrl,server_name,recommend,top_grade_url,order_id,start_game_date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String DELETE_GAME_SERVER_BY_ID = "delete from game_server where id=? ;";
	private static String SELECT_GAME_SERVER_BY_ID = "select id, server_name, state,  from game_server where id=?";
	private static String SELECT_GAME_TIME_BY_ID = "select id, server_name, state, start_game_date from game_server where id=?";
	private static String SELECT_GAME_INFO_BY_ID = "select * from game_server where id=?";
	private static String SELECT_GAME_STATE_BY_ID = "select state from game_server where id=?";
	private static String SELECT_GAME_INFO_BY_URL = "select id, server_name, State, start_game_date from game_server where PlayGameUrl like ?";
	private static String SELECT_RECOMMEND_GAME_INFOS = "select * from game_server where recommend = 'Y' and game_id = 1  and state ='sHot' ";
	private static String SELECT_RECOMMEND_GAME_ID_WITH_TIME = "select id, server_name, start_game_date from game_server where recommend = 'Y' and game_id = 1 and state ='sHot' ORDER BY order_id DESC";
	private static String SELECT_RECOMMEND_GAME_ID = "select id from game_server where recommend = 'Y' and game_id = 1 and state ='sHot'  ORDER BY order_id DESC";
	private static String SELECT_ALL_GAME_INFO = "select * from game_server";
	private static String SELECT_GAME_INFOS_BY_GAMEID = "select * from game_server where game_id=?";
	private static String SELECT_THE_LAST_GAME_INFO = "select * from game_server where recommend = 'Y' AND game_id = 1 AND state ='sHot' ORDER BY order_id DESC limit 1 ";
	public static GameServerDaoImpl instance = new GameServerDaoImpl();

	private GameServerDaoImpl() {
	};
	
	public static GameServerDao getInstance() {
		return instance;
	}

	
	
	@Override
	public GameServer getGameServerById(final int id) throws Exception {
		final GameServer info = new GameServer();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_GAME_SERVER);
		sql.append("where id=? ");
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setChargeKey(rs.getString("ChargeKey"));
					info.setChargeUrl(rs.getString("ChargeUrl"));
					info.setGameId(rs.getInt("game_Id"));
					info.setId(rs.getInt("Id"));
					info.setLink(rs.getString("Link"));
					info.setLoginKey(rs.getString("LoginKey"));
					info.setLoginUrl(rs.getString("LoginUrl"));
					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
					info.setSite(rs.getString("Site"));
					info.setState(rs.getString("State"));
					info.setStateInformation(rs.getString("StateInformation"));
					info.setServerName(rs.getString("server_name"));
					info.setRecommend(rs.getString("recommend"));
					info.setStartGameTime(rs.getTimestamp("start_game_date"));
					info.setTopGradeUrl(rs.getString("top_grade_url"));
					info.setOrderId(rs.getInt("order_id"));
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
		return info;
	}
	
	@Override
	public List<GameServer> getAllGameServer() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_GAME_SERVER);
		sql.append(ORDER_BY_ORDER_ID_DESC);
		final List<GameServer> items = new ArrayList<GameServer>();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameServer info = new GameServer();
					info.setChargeKey(rs.getString("ChargeKey"));
					info.setChargeUrl(rs.getString("ChargeUrl"));
					info.setGameId(rs.getInt("game_Id"));
					info.setId(rs.getInt("Id"));
					info.setLink(rs.getString("Link"));
					info.setLoginKey(rs.getString("LoginKey"));
					info.setLoginUrl(rs.getString("LoginUrl"));
					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
					info.setSite(rs.getString("Site"));
					info.setState(rs.getString("State"));
					info.setStateInformation(rs.getString("StateInformation"));
					info.setServerName(rs.getString("server_name"));
					info.setRecommend(rs.getString("recommend"));
					info.setStartGameTime(rs.getTimestamp("start_game_date"));
					info.setTopGradeUrl(rs.getString("top_grade_url"));
					info.setOrderId(rs.getInt("order_id"));
					items.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {}
		});
		return items;
	}

	@Override
	public List<GameServer> getAllRecommendGameServer() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_RECOMMEND_GAME_INFOS);
		sql.append(ORDER_BY_ORDER_ID_DESC);
		final List<GameServer> item = new ArrayList<GameServer>();		
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameServer info = new GameServer();
					info.setChargeKey(rs.getString("ChargeKey"));
					info.setChargeUrl(rs.getString("ChargeUrl"));
					info.setGameId(rs.getInt("game_id"));
					info.setId(rs.getInt("id"));
					info.setLink(rs.getString("Link"));
					info.setLoginKey(rs.getString("LoginKey"));
					info.setLoginUrl(rs.getString("LoginUrl"));
					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
					info.setSite(rs.getString("Site"));
					info.setState(rs.getString("State"));
					info.setStateInformation(rs.getString("StateInformation"));
					info.setServerName(rs.getString("server_name"));
					info.setTopGradeUrl(rs.getString("top_grade_url"));
					info.setRecommend(rs.getString("recommend"));
					info.setStartGameTime(rs.getTimestamp("start_game_date"));
					info.setOrderId(rs.getInt("order_id"));
					item.add(info);
				}
			}
			
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				// ps.setInt(1, id);
				
			}
		});
		return item;
	}

	@Override
	public List<Integer> getAllRecommendGameServerId() throws Exception {
		final List<Integer> item = new ArrayList<Integer>();
		DB.select(SELECT_RECOMMEND_GAME_INFOS, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item.add(rs.getInt("id"));
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				// ps.setInt(1, id);
			}
		});
		return item;
	}
	
	@Override
	public List<GameServer> getAllRecommendGameServerWithTime() throws Exception {
		final List<GameServer> servers = new ArrayList<GameServer>();
		DB.select(SELECT_RECOMMEND_GAME_ID_WITH_TIME, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameServer info = new GameServer();
					info.setId(rs.getInt("id"));
					info.setServerName(rs.getString("server_name"));
					info.setStartGameTime(rs.getTimestamp("start_game_date"));
					servers.add(info);
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				// ps.setInt(1, id);
			}
		});
		return servers;
	}

	@Override
	public GameServer getTheLastGameServer() throws Exception {
		final GameServer gameServer = new GameServer();
		DB.select(SELECT_THE_LAST_GAME_INFO, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					gameServer.setGameId(rs.getInt("game_Id"));
					gameServer.setId(rs.getInt("Id"));
					gameServer.setServerName(rs.getString("server_name"));
					gameServer.setRecommend(rs.getString("recommend"));
					gameServer.setState(rs.getString("State"));
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				// ps.setInt(1, id);
			}
		});
		return gameServer;
	}


	@Override
	public List<GameServer> getGameServersByGameId(final int gameId)
			throws Exception {
		final List<GameServer> item = new ArrayList<GameServer>();
		DB.select(SELECT_GAME_INFOS_BY_GAMEID, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameServer info = new GameServer();
					info.setChargeKey(rs.getString("ChargeKey"));
					info.setChargeUrl(rs.getString("ChargeUrl"));
					info.setGameId(rs.getInt("game_Id"));
					info.setId(rs.getInt("Id"));
					info.setLink(rs.getString("Link"));
					info.setLoginKey(rs.getString("LoginKey"));
					info.setLoginUrl(rs.getString("LoginUrl"));
					info.setPlayGameUrl(rs.getString("PlayGameUrl"));
					info.setSelectRoleUrl(rs.getString("SelectRoleUrl"));
					info.setSite(rs.getString("Site"));
					info.setState(rs.getString("State"));
					info.setStateInformation(rs.getString("StateInformation"));
					info.setServerName(rs.getString("server_name"));
					info.setRecommend(rs.getString("recommend"));
					item.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				 ps.setInt(1, gameId);

			}
		});
		return item;
	}

	@Override
	public GameServer getGameServerByUrl(final String url) throws Exception {
		final GameServer item = new GameServer();
		DB.select(SELECT_GAME_INFO_BY_URL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item.setId(rs.getInt("Id"));
					item.setServerName(rs.getString("server_name"));
					item.setStartGameTime(rs.getTimestamp("start_game_date"));
					item.setState(rs.getString("State"));
					break;
				}
			}
			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setString(1, "%" + url + "%");
			}
		});
		return item;
	}

	@Override
	public GameServer getGameServerTimeById(final int id) throws Exception {
		final GameServer item = new GameServer();
		DB.select(SELECT_GAME_TIME_BY_ID, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item.setId(rs.getInt("Id"));
					item.setServerName(rs.getString("server_name"));
					item.setStartGameTime(rs.getTimestamp("start_game_date"));
					item.setState(rs.getString("State"));
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);

			}
		});
		return item;
	}
	
	
	//根据Id查找服务器状态
	@Override
	public GameServer getGameServerStateById(final int id) throws Exception {
		final GameServer item = new GameServer();
		DB.select(SELECT_GAME_STATE_BY_ID, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					item.setState(rs.getString("State"));
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {
				ps.setInt(1, id);
			}
		});
		return item;
	}


}
