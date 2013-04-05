package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.AdStatisticsDao;
import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;

public class AdStatisticsDaoImpl implements AdStatisticsDao {

	private static final String SELECT_ADINFO_SQL = "select * from ad_info";
	private static final String SELECT_REGINFO_SQL = "select adId,userName,regTime,clickTime from reg_info where userName=?";
	private static final String UPDATE_ADINFO_SQL = "update ad_info set enterTimes=?,regTimes=?,date=? where id=?";
	private static final String INSERT_ADINFO_SQL = "insert into ad_info(adId,site,enterTimes,regTimes,date) values(?,?,?,?,?)";
	private static final String INSERT_REGINFO_SQL = "insert into reg_info(userId,userName,adId,regTime,clickTime) values(?,?,?,?,?)";
	
	@Override
	public AdInfo getAdStatisticsByAdId(final int adId, final String date) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_ADINFO_SQL);
		sql.append(" where adId=? and date=?");
		final AdInfo info = new AdInfo();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setId(rs.getInt("id"));
					info.setAdId(rs.getInt("adId"));
					info.setEnterTimes(rs.getInt("enterTimes"));
					info.setRegTimes(rs.getInt("regTimes"));
					info.setSite(rs.getString("site"));
					info.setDate(rs.getString("date"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, adId);
				stmt.setString(2, date);
			}
		});

		return info;

	}

	@Override
	public boolean updateAdStatistics(final AdInfo adInfo) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_ADINFO_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, adInfo.getEnterTimes());
				ps.setInt(2, adInfo.getRegTimes());
				ps.setString(3, adInfo.getDate());
				ps.setInt(4, adInfo.getId());
				ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public boolean insertAdStatistics(final AdInfo adInfo) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_ADINFO_SQL,new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setInt(1, adInfo.getAdId());
					ps.setString(2,adInfo.getSite());
					ps.setInt(3, adInfo.getEnterTimes());
					ps.setInt(4, adInfo.getRegTimes());
					ps.setString(5, adInfo.getDate());
					ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public boolean insertRegInfo(final RegInfo regInfo) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_REGINFO_SQL,new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setInt(1, regInfo.getUseId());
					ps.setString(2,regInfo.getUserName());
					ps.setInt(3, regInfo.getAdId());
					ps.setTimestamp(4, new Timestamp(regInfo.getRegTime().getTime()));
					ps.setTimestamp(5, new Timestamp(regInfo.getClickTime().getTime()));
					ps.executeUpdate();
			}
		});
		return result;
	}

	@Override
	public RegInfo getRegInfoByUserName(final String userName) throws Exception {
		StringBuffer sql = new StringBuffer(SELECT_REGINFO_SQL);
		final RegInfo regInfo = new RegInfo();
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					regInfo.setUserName(rs.getString("userName"));
					regInfo.setAdId(rs.getInt("adId"));
					regInfo.setRegTime(rs.getTimestamp("regTime"));
					regInfo.setClickTime(rs.getTimestamp("clickTime"));
				}
			}
			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, userName);
			}
		});
		if(regInfo.getUserName() == null){
			return null;
		}else{
			return regInfo;
		}
	}

}
