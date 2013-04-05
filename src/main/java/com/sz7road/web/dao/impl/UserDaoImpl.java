package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.UserDao;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.usermanage.UserInfo;

/**
 * This class handle all db operation for user. 
 * 
 * @author Jimmy Huang
 * @version $Id: UserDAO.java,v 1.18 2010/12/08 08:49:22 Jimmy Huang Exp $
 *  
 */
public class UserDaoImpl implements UserDao {

	private static final String INSERT_USER_SQL = "INSERT INTO ddt_user (LOGIN_NAME,PASS_WORD,EMAIL_ADDR) values(?,?,?)";

	private static final String SELECT_USER_BY_LOGINNAME_SQL = "SELECT user_id,login_name,pass_word,email_addr FROM ddt_user where LOGIN_NAME= ? ";

	private static final String SELECT_USER_BY_USERID_SQL = "SELECT u.user_id,u.login_name,u.pass_word,u.email_addr,ur.role_id,r.role_name FROM ddt_user u,ddt_user_role ur,ddt_role r WHERE u.user_id= ? and u.user_id = ur.user_id and ur.role_id = r.role_id";

	private static final String SELECT_USERS_SQL = "SELECT u.user_id,u.login_name,u.pass_word,u.email_addr,r.role_name FROM ddt_user u,ddt_user_role ur,ddt_role r WHERE u.user_id = ur.user_id AND ur.role_id = r.role_id";

	private static final String UPDATE_USER_SQL = "UPDATE ddt_user SET login_name= ?, pass_word=?, email_addr=? WHERE user_id = ?";

	private static final String DELETE_USER_SQL = "DELETE FROM ddt_user WHERE user_id= ? ";

	private static UserDaoImpl _instance = new UserDaoImpl();

	/**
	 * Constructor
	 */
	private UserDaoImpl() {}

	/**
	 * Get singleton <code>UserRolePermissionDAO</code> instance
	 * @return <code>UserRolePermissionDAO</code>
	 */
	public static UserDaoImpl getInstance() {
		return _instance;
	}

	/**
	 * Insert user login inforamtion to user table
	 * @param loginInfo user login information
	 */
	public int insertUserLoginInfo(final UserInfo loginInfo) throws Exception {
		final int[] userIdArray = { 0 };
		PreparedStatement pstmt = DB.prepareStatement(INSERT_USER_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		DB.insertUpdate(pstmt, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, loginInfo.getUserName());
				ps.setString(2, loginInfo.getPassWord());
				ps.setString(3, loginInfo.getEmailAddr());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					userIdArray[0] = rs.getInt(1);
				}
			}

		});

		return userIdArray[0];
	}

	/**
	 * Retrieve user login information by specified user id.
	 * @param userId user id
	 * @return UserLogin information object
	 */
	public UserInfo getUserLoginInfoByUserName(final String loginName) throws Exception {
		final UserInfo info = new UserInfo();
		DB.select(SELECT_USER_BY_LOGINNAME_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserId(rs.getInt("USER_ID"));
					info.setUserName(rs.getString("LOGIN_NAME"));
					info.setPassWord(rs.getString("PASS_WORD"));
					info.setEmailAddr(rs.getString("EMAIL_ADDR"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setString(1, loginName);
			}
		});

		return info;
	}

	/**
	 * Retrieve user login information by specified user id.
	 * @param userId user id
	 * @return UserLogin information object
	 */
	public UserInfo getUserLoginInfoByUserId(final Integer userId) throws Exception {
		final UserInfo info = new UserInfo();
		DB.select(SELECT_USER_BY_USERID_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserId(rs.getInt("USER_ID"));
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setRoleName(rs.getString("ROLE_NAME"));
					info.setUserName(rs.getString("LOGIN_NAME"));
					info.setPassWord(rs.getString("PASS_WORD"));
					info.setEmailAddr(rs.getString("EMAIL_ADDR"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
			}
		});

		return info;
	}

	/**
	 * Update the user login information by specified user id in UserLoginInfo object.
	 * Other information in UserLoginInfo will be updated into DB.
	 * @param loginInfo user login infomation
	 */
	public boolean updateUserLoginInfo(final UserInfo loginInfo) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_USER_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, loginInfo.getUserName());
				ps.setString(2, loginInfo.getPassWord());
				ps.setString(3, loginInfo.getEmailAddr());
				ps.setInt(4, loginInfo.getUserId());
				ps.executeUpdate();
			}

		});

		return result;
	}

	public boolean deleteUserLoginInfo(final Integer userId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_USER_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
				stmt.executeUpdate();
			}
		});

		return result;
	}

	@Override
	public List<UserInfo> getUserLoginInfoList(PageInfo pageInfo) throws Exception {
		final List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_USERS_SQL);
		sql.append(" limit ").append(pageInfo.getStartRow()).append(",").append(pageInfo.getPageSize());
		DB.select(sql.toString(), new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					UserInfo info = new UserInfo();
					info.setUserId(rs.getInt("USER_ID"));
					info.setUserName(rs.getString("LOGIN_NAME"));
					info.setPassWord(rs.getString("PASS_WORD"));
					info.setEmailAddr(rs.getString("EMAIL_ADDR"));
					info.setRoleName(rs.getString("ROLE_NAME"));
					userInfoList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return userInfoList;
	}

	public int getUserLoginInfoListCount() throws Exception {
		final int[] count = { 0 };
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) count from (");
		sql.append(SELECT_USERS_SQL);
		sql.append(") a ");
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
