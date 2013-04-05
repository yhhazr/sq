package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.CaptchaDao;
import com.sz7road.web.model.captcha.FunctionUsageLog;

public class CaptchaDaoImpl implements CaptchaDao {
	
	private final static String INSERT_SQL = "INSERT INTO ddt_function_usage_log(identifier_id,function_code,usage_time) VALUES(?,?,?)";
	
	private final static String QUERY_USAGELOG_COUNT_SQL =  "SELECT count(1) count FROM ddt_function_usage_log WHERE identifier_id = ? AND function_code = ? AND usage_time > ?";
	
	private final static String DELETE_SQL =  "DELETE FROM ddt_function_usage_log WHERE identifier_id = ? AND function_code = ?";

	private static CaptchaDao _instance = new CaptchaDaoImpl();
	
	private CaptchaDaoImpl() {}

	public static CaptchaDao getInstance() {
		return _instance;
	}
	
	@Override
	public void insertUserAttemptLog(final FunctionUsageLog log) throws Exception {

		 DB.insertUpdate(INSERT_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, log.getIdentifierId());
				ps.setString(2, log.getFunctionCode());
				ps.setTimestamp(3, new Timestamp(log.getUsageTime().getTime()));
				ps.executeUpdate();
			}
		});
	}

	@Override
	public int queryUserAttemptCnt(final String identifierId, final String funcCode,final Long duration) throws Exception {
		final Date newDate = new Date();
		long myTime = (newDate.getTime() / 1000) - 60 * duration.longValue();
		newDate.setTime(myTime * 1000);
		final int[] count = { 0 };
		DB.select(QUERY_USAGELOG_COUNT_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					count[0] = rs.getInt("count");
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, identifierId);
				stmt.setString(2, funcCode);
				stmt.setTimestamp(3, new Timestamp(newDate.getTime()));
			}
		});
		return count[0];
	
	}

	@Override
	public void deleteFuncAttemptParam(final String identifierId, final String funcCode) throws Exception {
		DB.insertUpdate(DELETE_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, identifierId);
				ps.setString(2, funcCode);
				ps.executeUpdate();
			}
		});
	}

}
