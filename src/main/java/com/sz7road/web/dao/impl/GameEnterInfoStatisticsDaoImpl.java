/**
 * 
 */
package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.GameEnterInfoStatisticsDao;
import com.sz7road.web.model.gameEnterInfo.GameEnterInfo;

/**
 * @author hokin.jim
 *
 */
public class GameEnterInfoStatisticsDaoImpl implements
		GameEnterInfoStatisticsDao {
	private static final String INSERT_GAMEENTERINFO_SQL = "INSERT INTO game_statistics_enterInfo (position,times,beginTime,endTime) values(?,?,?,?)";
	private static final String UPDATE_GAMEENTERINFO_SQL = "UPDATE game_statistics_enterInfo SET times = ?,beginTime=?,endTime=? WHERE id = ?";
	private static final String GET_GAMEENTERINFO_SQL = "SELECT * FROM game_statistics_enterInfo WHERE position = ? AND beginTime=? ";
	private static GameEnterInfoStatisticsDaoImpl _instance = new GameEnterInfoStatisticsDaoImpl();

	private GameEnterInfoStatisticsDaoImpl() {}
	  
	public static GameEnterInfoStatisticsDaoImpl getInstance() {
		return _instance;
	}
	@Override
	public boolean insertGameEnterInfo(final GameEnterInfo gameEnterInfo)
			throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_GAMEENTERINFO_SQL,new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setString(1, gameEnterInfo.getPosition());
					ps.setString(2, gameEnterInfo.getTimes());
					ps.setString(3, gameEnterInfo.getBeginTime());
					ps.setString(4, gameEnterInfo.getEndTime());
					ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public boolean updateGameEnterInfo(final GameEnterInfo gameEnterInfo)
			throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_GAMEENTERINFO_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, gameEnterInfo.getTimes());
				ps.setString(2, gameEnterInfo.getBeginTime());
				ps.setString(3, gameEnterInfo.getEndTime());
				ps.setInt(4, gameEnterInfo.getId());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public GameEnterInfo getGameEnterInfoByPostion(final String positionKey,final String date)
			throws Exception {
		final GameEnterInfo info=new GameEnterInfo();
		 DB.select(GET_GAMEENTERINFO_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setId(rs.getInt("id"));
					info.setPosition(positionKey);
					info.setTimes(rs.getString("times"));
					info.setBeginTime(rs.getString("beginTime"));
					info.setEndTime(rs.getString("endTime"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, positionKey);
				stmt.setString(2, date);
			}
		});
		return info;
	}

}
