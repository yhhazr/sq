package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.AutoGeneHtmlTimeDao;
import com.sz7road.web.dao.GameServerStatusDao;
import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;

public class AutoGeneHtmlTimeDaoImpl implements AutoGeneHtmlTimeDao {
	private static final String SELECT_AUTO_GENEHTML_TIME = "select * from auto_genehtml_time ";
	private static final String UPDATE_AUTO_GENEHTML_TIME = "update auto_genehtml_time set geneTime=?, geneType=? where id=? ;";
	private static final String INSERT_AUTO_GENEHTML_TIME = "insert into auto_genehtml_time (geneTime, geneType) values (?,?);";
	private static final String DELETE_AUTO_GENEHTML_TIME = "delete from auto_genehtml_time where id=? ;";

	public static AutoGeneHtmlTimeDaoImpl instance = new AutoGeneHtmlTimeDaoImpl();
	public static AutoGeneHtmlTimeDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<AutoGeneHtmlTime> findAllAutoGeneHtmlTime() throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_AUTO_GENEHTML_TIME);
		final List<AutoGeneHtmlTime> items = new ArrayList<AutoGeneHtmlTime>();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					AutoGeneHtmlTime info = new AutoGeneHtmlTime();
					info.setGeneTime(rs.getString("geneTime"));
					info.setGeneType(rs.getString("geneType"));
					info.setId(rs.getInt("id"));
					items.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement ps) throws SQLException {}
		});
		return items;
	}

	@Override
	public boolean deleteAutoGeneHtmlTimeById(final int id) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_AUTO_GENEHTML_TIME, new IUStH() {

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
	public boolean updateAutoGeneHtmlTimeById(final AutoGeneHtmlTime autoTime) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_AUTO_GENEHTML_TIME, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setString(1, autoTime.getGeneTime());
				ps.setString(2, autoTime.getGeneType());
				ps.setInt(3, autoTime.getId());
				ps.executeUpdate();
			}

		});
		return result;
	}

	@Override
	public boolean insertAutoGeneHtmlTime(final AutoGeneHtmlTime autoTime)
			throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_AUTO_GENEHTML_TIME, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps)
					throws SQLException {
				ps.setString(1, autoTime.getGeneTime());
				ps.setString(2, autoTime.getGeneType());
				ps.executeUpdate();
			}

		});
		return result;
	}

}
