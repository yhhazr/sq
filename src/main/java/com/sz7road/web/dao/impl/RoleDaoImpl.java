/**
 * $Header: /data/cvs/chinasiteplatform/src/java/com/gs/sitecore/dao/UserRoleDAO.java,v 1.11 2010/11/05 08:46:44 joeycao Exp $ 
 * $Revision: 1.11 $ 
 * $Date: 2010/11/05 08:46:44 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;
import org.pureart.persistement.database.easydb.Transaction;

import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.RoleDao;
import com.sz7road.web.model.rolemanage.RoleInfo;
import com.sz7road.web.model.rolemanage.UserRoleInfo;

public class RoleDaoImpl implements RoleDao {

	private static final String INSERT_ROLE_SQL = "INSERT INTO ddt_role (role_name,role_desc,create_date,l_upd_date,enable_flag) values(?,?,?,?,?) ";

	private static final String INSERT_USER_ROLE_SQL = "INSERT INTO ddt_user_role (USER_ID,ROLE_ID,CREATE_DATE) VALUES(?,?,?)";

	private static final String SELECT_USERS_SQL = "select USER_ID,LOGIN_NAME,PASSWORD,EMAIL_ADDR from DDT_USER where USER_ID= ? ";

	private static final String SELECT_USER_ROLE_BY_USERID_SQL = "SELECT user_id,role_id,create_date FROM ddt_user_role WHERE user_id = ?";

	private static final String SELECT_USER_ROLE_BY_ROLEID_SQL = "SELECT user_id,role_id,create_date FROM ddt_user_role WHERE role_id = ?";

	private static final String SELECT_USER_ROLE_SQL = "SELECT user_id,role_id,create_date FROM ddt_user_role WHERE user_id = ? AND role_id = ?";

	private static final String SELECT_ALL_ROLES_SQL = "SELECT role_id,role_name,role_desc,create_date,l_upd_date,enable_flag FROM ddt_role  ";

	private static final String SELECT_ROLE_SQL = "SELECT role_id,role_name,role_desc,create_date,l_upd_date,enable_flag FROM ddt_role WHERE role_id = ? ";

	private static final String UPDATE_ROLE_SQL = "UPDATE ddt_role  SET role_name = ?,role_desc = ?,L_UPD_DATE=?,enable_flag=? WHERE role_id = ? ";

	private static final String UPDATE_USER_ROLE_SQL = "UPDATE ddt_user_role  SET role_id = ? WHERE user_id = ? ";

	private static final String DELETE_ROLE_SQL = "DELETE FROM ddt_role  WHERE role_id = ?";

	private static final String DELETE_USER_ROLE_BY_USERID_SQL = "DELETE FROM ddt_user_role WHERE user_id = ?";

	private static final String DELETE_USER_ROLE_BY_ROLEID_SQL = "DELETE FROM ddt_user_role WHERE role_id = ?";

	private static final String DELETE_ROLE_PERMISSION_SQL = "DELETE FROM ddt_role_permission  WHERE role_id = ?";

	
	private static RoleDaoImpl _instance = new RoleDaoImpl();
	/**
	 * Constructor
	 */
	private RoleDaoImpl() {
	}

	/**
	 * Get singleton <code>UserRolePermissionDAO</code> instance
	 * @return <code>UserRolePermissionDAO</code>
	 */
	public static RoleDaoImpl getInstance() {
		return _instance;
	}
	@Override
	public boolean insertRole(final RoleInfo role) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_ROLE_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, role.getRoleName());
				ps.setString(2, role.getRoleDesc());
				ps.setTimestamp(3, DateUtil.getCurrentTimestamp());
				ps.setTimestamp(4, DateUtil.getCurrentTimestamp());
				ps.setBoolean(5, role.isEnableFlag());
				ps.executeUpdate();
			}
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#updateRole(com.sz7road.web.model.rolemanage.RoleInfo)
	 */
	@Override
	public boolean updateRole(final RoleInfo role) throws Exception {

		boolean result = false;
		result = DB.insertUpdate(UPDATE_ROLE_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setString(1, role.getRoleName());
				ps.setString(2, role.getRoleDesc());
				ps.setTimestamp(3, DateUtil.getCurrentTimestamp());
				ps.setBoolean(4, role.isEnableFlag());
				ps.setInt(5, role.getRoleId());
				ps.executeUpdate();
			}
		});

		return result;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#updateUserRole(com.sz7road.web.model.rolemanage.UserRoleInfo)
	 */
	@Override
	public boolean updateUserRole(final UserRoleInfo role) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(UPDATE_USER_ROLE_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, role.getRoleId());
				ps.setInt(2, role.getUserId());
				ps.executeUpdate();
			}
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#insertUserRole(com.sz7road.web.model.rolemanage.UserRoleInfo)
	 */
	@Override
	public boolean insertUserRole(final UserRoleInfo role) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_USER_ROLE_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, role.getUserId());
				ps.setInt(2, role.getRoleId());
				ps.setTimestamp(3, DateUtil.getCurrentTimestamp());
				ps.executeUpdate();
			}
		});
		return result;

	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#deleteRole(int)
	 */
	@Override
	public boolean deleteRole(final Integer roleId) throws Exception {
		final boolean[] deleteResult = { false };
		Transaction tran = JdbcTransaction.getInstance().getAndBeginTransaction();
		tran.insertUpdate(DELETE_ROLE_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, roleId);
				int result = ps.executeUpdate();
				if(result >0){
					deleteResult[0] = true;
				}
			}
		});
		return deleteResult[0];
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#deleteRolePermission(int)
	 */
	@Override
	public boolean deleteRolePermission(final Integer roleId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_ROLE_PERMISSION_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, roleId);
				ps.executeUpdate();
			}
		});
		return result;

	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#delUserRoleByUserId(int)
	 */
	@Override
	public boolean delUserRoleByUserId(final Integer userId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_USER_ROLE_BY_USERID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, userId);
				ps.executeUpdate();
			}
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#delUserRoleByRoleId(int)
	 */
	@Override
	public boolean delUserRoleByRoleId(final Integer roleId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(DELETE_USER_ROLE_BY_ROLEID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, roleId);
				ps.executeUpdate();
			}
		});
		return result;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#getUserRoleByUserId(int)
	 */
	@Override
	public UserRoleInfo getUserRoleByUserId(final Integer userId) throws Exception {
		final UserRoleInfo info = new UserRoleInfo();
		DB.select(SELECT_USER_ROLE_BY_USERID_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserId(rs.getInt("USER_ID"));
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setCreateDate(new Date(rs.getTimestamp("CREATE_DATE").getTime()));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
			}
		});

		return info;

	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#getUserRoleByRoleId(int)
	 */
	@Override
	public UserRoleInfo getUserRoleByRoleId(final Integer roleId) throws Exception {
		final UserRoleInfo info = new UserRoleInfo();
		DB.select(SELECT_USER_ROLE_BY_ROLEID_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserId(rs.getInt("USER_ID"));
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setCreateDate(new Date(rs.getTimestamp("CREATE_DATE").getTime()));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, roleId);
			}
		});

		return info;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#getUserRole(int, int)
	 */
	@Override
	public UserRoleInfo getUserRole(final Integer userId, final Integer roleId) throws SQLException {
		final UserRoleInfo info = new UserRoleInfo();
		DB.select(SELECT_USER_ROLE_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setUserId(rs.getInt("USER_ID"));
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setCreateDate(new Date(rs.getTimestamp("CREATE_DATE").getTime()));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
				stmt.setInt(2, roleId);
			}
		});

		return info;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#getRole(int)
	 */
	@Override
	public RoleInfo getRole(final Integer roleId) throws Exception {
		final RoleInfo info = new RoleInfo();
		DB.select(SELECT_ROLE_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setRoleName(rs.getString("ROLE_NAME"));
					info.setRoleDesc(rs.getString("ROLE_DESC"));
					info.setCreateDate(new Date(rs.getTimestamp("CREATE_DATE").getTime()));
					info.setLupdate(new Date(rs.getTimestamp("L_UPD_DATE").getTime()));
					info.setEnableFlag(rs.getBoolean("ENABLE_FLAG"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, roleId);
			}
		});

		return info;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.UserRoleDao#getRoles()
	 */
	@Override
	public List<RoleInfo> getRoles() throws Exception {
		final List<RoleInfo> roleInfoList = new ArrayList<RoleInfo>();
		DB.select(SELECT_ALL_ROLES_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					RoleInfo info = new RoleInfo();
					info.setRoleId(rs.getInt("ROLE_ID"));
					info.setRoleName(rs.getString("ROLE_NAME"));
					info.setRoleDesc(rs.getString("ROLE_DESC"));
					info.setCreateDate(new Date(rs.getTimestamp("CREATE_DATE").getTime()));
					info.setLupdate(new Date(rs.getTimestamp("L_UPD_DATE").getTime()));
					info.setEnableFlag(rs.getBoolean("ENABLE_FLAG"));
					roleInfoList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return roleInfoList;
	}

}
