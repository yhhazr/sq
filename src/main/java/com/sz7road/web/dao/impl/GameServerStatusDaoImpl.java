package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GameServerDao;
import com.sz7road.web.dao.GameServerStatusDao;
import com.sz7road.web.model.gamemanage.GameServerStatus;

public class GameServerStatusDaoImpl implements GameServerStatusDao {
	public static GameServerStatusDaoImpl instance = new GameServerStatusDaoImpl();
	public static GameServerStatusDao getInstance() {
		return instance;
	}
	private static final String SELECT_GAME_SERVER_STATUS = "select * from game_server_status ";
	
	@Override
	public List<GameServerStatus> findAllGameServerStatus() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_GAME_SERVER_STATUS);
		final List<GameServerStatus> items = new ArrayList<GameServerStatus>();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					GameServerStatus info = new GameServerStatus();
					info.setId(rs.getInt("id"));
					info.setStatusId(rs.getString("statusId"));
					info.setStatusName(rs.getString("statusName"));
					items.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {}
		});
		return items;
	}
	
}
