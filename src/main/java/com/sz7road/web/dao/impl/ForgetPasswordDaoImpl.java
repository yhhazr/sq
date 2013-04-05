package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.ForgetPasswordDao;
import com.sz7road.web.model.onlineUser.OnlineUser;

public class ForgetPasswordDaoImpl implements ForgetPasswordDao {
	
	private static String INSERT_INFO_SQL = "INSERT INTO forget_password (username,authkey) values(?,?)";
	
	private static String DELETE_INFO_SQL = "DELETE FROM forget_password WHERE username = ?";
	
	private static String SELECT_INFO_SQL = "SELECT * FROM forget_password WHERE username = ? order by authkey desc limit 1";
	
	private static ForgetPasswordDaoImpl _instance = new ForgetPasswordDaoImpl();

	private ForgetPasswordDaoImpl() {}
	  
	public static ForgetPasswordDaoImpl getInstance() {
		return _instance;
	}
	
	public boolean createInfo(final OnlineUser user) throws Exception {
		boolean result = false;	
		result = DB.insertUpdate(INSERT_INFO_SQL,new IUStH() {			
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setString(1, user.getUserName());
					ps.setString(2, user.getEmail());				
					ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public OnlineUser getInfo(final String userName) throws Exception {
		final OnlineUser info = new OnlineUser();
		DB.select(SELECT_INFO_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserName(rs.getString("username"));
					info.setEmail(rs.getString("authkey"));
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, userName);
			}
		});
		return info;
	}

	@Override
	public boolean deleteInfo(final String userName) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_INFO_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
				ps.executeUpdate();
			}
		});
		return result;
	}

}
