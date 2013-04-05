package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;
import org.pureart.persistement.database.easydb.Transaction;

import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.PermissionDao;
import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.rolemanage.PermissionInfo;

public class PermissionDaoImpl implements PermissionDao {

	private static final String SELECT_PERMISSIONS_BY_ROLE_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a, ddt_role_permission b WHERE a.perm_id = b.perm_id AND b.role_id =? order by a.order_id";

	//private static final String SELECT_PERMISSIONS_BY_ROLEID_SQL = "SELECT perm_id,parent_perm_id,perm_name,perm_desc,perm_url,restricted_flag FROM role_permission WHERE role_id = ?";

	private static final String SELECT_PERMISSIONS_BY_USERID_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a, ddt_role_permission b, ddt_user_role c"
			+ " WHERE a.perm_id = b.perm_id AND b.role_id = c.role_id AND c.user_id =? order by a.order_id";

	private static final String SELECT_ALL_PERMISSIONS_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a ";

	private static final String SELECT_ALL_MENU_PERMISSIONS_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a, ddt_role_permission b, ddt_user_role c"
			+ " WHERE a.menu_flag = 1 and a.perm_id = b.perm_id AND b.role_id = c.role_id AND c.user_id =? order by a.order_id";

	private static final String SELECT_ALL_PARENT_PERMISSIONS_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a where a.parent_perm_id <= 1 order by a.order_id";

	private static final String SELECT_CHILD_PERMISSIONS_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a where a.parent_perm_id = ? order by a.order_id";

	private static final String SELECT_PERMISSION_BY_ID_SQL = "SELECT a.perm_id,a.parent_perm_id,a.perm_name,a.perm_desc,a.perm_url,a.restricted_flag,a.menu_flag,a.order_id"
			+ " FROM ddt_permission a where a.perm_id = ? order by a.order_id";

	private static final String INSERT_PERMISSION_SQL = "INSERT INTO ddt_permission(parent_perm_id,perm_name,perm_desc,perm_url,order_id,restricted_flag,menu_flag) VALUES(?,?,?,?,?,?,?)";

	private static final String UPDATE_PERMISSION_SQL = "UPDATE ddt_permission set parent_perm_id=?,perm_name=?,perm_desc=?,perm_url=?,order_id=?,restricted_flag=?,menu_flag=? where perm_id=?";
	
	private static final String INSERT_ROLE_PERMISSION_SQL = "INSERT INTO ddt_role_permission(role_id,perm_id,create_date) VALUES(?,?,?)";

	private static final String DELETE_ROLE_PERMISSION_BY_ROLEID_SQL = "DELETE FROM ddt_role_permission WHERE role_id = ?";
	
	private static final String DELETE_PERMISSION_BY_ID_SQL = "DELETE FROM ddt_permission WHERE perm_id = ?";

	private static PermissionDao _instance = new PermissionDaoImpl();

	/**
	 * Constructor
	 */
	private PermissionDaoImpl() {}

	/**
	 * @return UserDAO
	 */
	public static PermissionDao getInstance() {
		return _instance;
	}

	@Override
	public List<PermissionInfo> getPermissionByRole(final Integer roleId) throws Exception {
		final List<PermissionInfo> permissionInfoList = new ArrayList<PermissionInfo>();
		DB.select(SELECT_PERMISSIONS_BY_ROLE_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					PermissionInfo info = new PermissionInfo();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setPermUrl(rs.getString("perm_url"));
					info.setOrderId(rs.getInt("order_id"));
					info.setMenuFlag(rs.getString("menu_flag"));
					info.setRestrictedFlag(rs.getString("restricted_flag"));
					permissionInfoList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, roleId);
			}
		});
		return permissionInfoList;
	}

	/* (non-Javadoc)
	 * @see com.sz7road.web.dao.impl.PermissionDao#insertRolePermission(int, int)
	 */
	@Override
	public boolean insertRolePermission(final Integer roleId, final Integer permId) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_ROLE_PERMISSION_SQL, new IUStH() {

			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, roleId);
				ps.setInt(2, permId);
				ps.setTimestamp(3, DateUtil.getCurrentTimestamp());
				ps.executeUpdate();
			}
		});
		return result;

	}

	@Override
	public boolean deleteRolePermission(final Integer roleId) throws Exception {
		final boolean[] deleteResult = { false };
		Transaction tran = JdbcTransaction.getInstance().getAndBeginTransaction();
		tran.insertUpdate(DELETE_ROLE_PERMISSION_BY_ROLEID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, roleId);
				int result = ps.executeUpdate();
				if (result >= 0) {
					deleteResult[0] = true;
				}
			}
		});
		return deleteResult[0];

	}

	@Override
	public List<Permission> getPermissionsByUserId(final Integer userId) throws Exception {
		final List<Permission> permissionList = new ArrayList<Permission>();
		DB.select(SELECT_PERMISSIONS_BY_USERID_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Permission info = new Permission();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
					permissionList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
			}
		});
		return permissionList;
	}

	@Override
	public List<Permission> getAllPermissions() throws Exception {
		final List<Permission> permissionList = new ArrayList<Permission>();
		DB.select(SELECT_ALL_PERMISSIONS_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Permission info = new Permission();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
					permissionList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return permissionList;
	}

	@Override
	public List<Permission> getAllMenuPermissions(final Integer userId) throws Exception {
		final List<Permission> permissionList = new ArrayList<Permission>();
		DB.select(SELECT_ALL_MENU_PERMISSIONS_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Permission info = new Permission();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
					permissionList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, userId);
			}
		});
		return permissionList;
	}

	@Override
	public List<Permission> getAllParentPermissions() throws Exception {

		final List<Permission> permissionList = new ArrayList<Permission>();
		DB.select(SELECT_ALL_PARENT_PERMISSIONS_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Permission info = new Permission();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
					permissionList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {}
		});
		return permissionList;

	}

	@Override
	public List<Integer> getPermIdListByRole(final Integer roleId) throws Exception {

		final List<Integer> permissionIdList = new ArrayList<Integer>();
		DB.select(SELECT_PERMISSIONS_BY_ROLE_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					permissionIdList.add(rs.getInt("perm_id"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, roleId);
			}
		});
		return permissionIdList;

	}

	@Override
	public boolean insertRolePermissionBatch(final Integer roleId, final Integer[] permIds) throws Exception {
		Transaction tran = JdbcTransaction.getInstance().getAndBeginTransaction();
		final boolean[] insertResult = { true };
		for (final Integer permId : permIds) {
			tran.insertUpdate(INSERT_ROLE_PERMISSION_SQL, new IUStH() {
				@Override
				public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
					ps.setInt(1, roleId);
					ps.setInt(2, permId);
					ps.setTimestamp(3, DateUtil.getCurrentTimestamp());
					int result = ps.executeUpdate();
					if (result <= 0) {
						insertResult[0] = false;
					}
				}
			});
			if (!insertResult[0]) {
				return false;
			}
		}
		return insertResult[0];
	}

	@Override
	public boolean createPermission(final Permission permission) throws Exception {
		boolean result = false;
		result = DB.insertUpdate(INSERT_PERMISSION_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, permission.getParentPermId());
				ps.setString(2, permission.getPermName());
				ps.setString(3, permission.getPermDesc());
				ps.setString(4, permission.getPermUrl());
				ps.setInt(5, new Integer(permission.getOrderId()));
				ps.setBoolean(6, permission.isRestrictedFlag());
				ps.setBoolean(7, permission.isMenuFlag());
				ps.executeUpdate();
			}
		});
		return result;

	}

	@Override
	public List<Permission> getPermissionsByParentPermId(final Integer parentPermId) throws Exception {
		final List<Permission> permissionList = new ArrayList<Permission>();
		DB.select(SELECT_CHILD_PERMISSIONS_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					Permission info = new Permission();
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
					permissionList.add(info);
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, parentPermId);
			}
		});
		return permissionList;

	}

	@Override
	public Permission getPermissionsByPermId(final Integer permId) throws Exception {
		final Permission info = new Permission();
		DB.select(SELECT_PERMISSION_BY_ID_SQL, new ParamReadStH() {
			@Override
			public void handleRead(ResultSet rs) throws SQLException {
				while (rs.next()) {
					info.setPermId(rs.getInt("perm_id"));
					info.setParentPermId(rs.getInt("parent_perm_id"));
					info.setPermName(rs.getString("perm_name"));
					info.setPermDesc(rs.getString("perm_desc"));
					info.setOrderId(String.valueOf(rs.getInt("order_id")));
					info.setPermUrl(rs.getString("perm_url"));
					info.setMenuFlag(rs.getBoolean("menu_flag"));
					info.setRestrictedFlag(rs.getBoolean("restricted_flag"));
				}
			}

			@Override
			public void setParams(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, permId);
			}
		});
		return info;
	}

	@Override
	public boolean updatePermission(final Permission permission) throws Exception {

		boolean result = false;
		result = DB.insertUpdate(UPDATE_PERMISSION_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, permission.getParentPermId());
				ps.setString(2, permission.getPermName());
				ps.setString(3, permission.getPermDesc());
				ps.setString(4, permission.getPermUrl());
				ps.setInt(5, new Integer(permission.getOrderId()));
				ps.setBoolean(6, permission.isRestrictedFlag());
				ps.setBoolean(7, permission.isMenuFlag());
				ps.setInt(8, permission.getPermId());
				ps.executeUpdate();
			}
		});
		return result;

	
	}

	@Override
	public boolean deletePermission(final Integer permId) throws Exception {
		final boolean[] deleteResult = { false };
		Transaction tran = JdbcTransaction.getInstance().getAndBeginTransaction();
		tran.insertUpdate(DELETE_PERMISSION_BY_ID_SQL, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				ps.setInt(1, permId);
				int result = ps.executeUpdate();
				if (result >= 0) {
					deleteResult[0] = true;
				}
			}
		});
		return deleteResult[0];

	
	}

}
