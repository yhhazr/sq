package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.dao.PermissionDao;
import com.sz7road.web.dao.RoleDao;
import com.sz7road.web.dao.impl.PermissionDaoImpl;
import com.sz7road.web.dao.impl.RoleDaoImpl;
import com.sz7road.web.model.rolemanage.RoleInfo;
import com.sz7road.web.service.RoleService;

public class RoleServiceImpl implements RoleService {
	private static RoleServiceImpl _this;
	private static RoleDao roleDao = null;
	private static PermissionDao permDao = null;

	/**
	 * Constructor
	 */
	private RoleServiceImpl() {
		roleDao = RoleDaoImpl.getInstance();
		permDao = PermissionDaoImpl.getInstance();
	}

	/**
	 * @return PermissionBusiness
	 */
	public synchronized static RoleServiceImpl getInstance() {
		if (_this == null)
			_this = new RoleServiceImpl();
		return _this;
	}

	@Override
	public List<RoleInfo> getAllRoleInfoList() throws Exception {
		return roleDao.getRoles();
	}

	@Override
	public boolean createRole(RoleInfo roleInfo) throws Exception {
		return roleDao.insertRole(roleInfo);
	}

	@Override
	public boolean deleteRole(Integer roleId) throws Exception {
		boolean result = false;
		JdbcTransaction tran = JdbcTransaction.getInstance();
		tran.getAndBeginTransaction();
		boolean deleteRoleResult = roleDao.deleteRole(roleId);
		boolean deleteRolePermResult = permDao.deleteRolePermission(roleId);
		tran.commit();
		if (deleteRoleResult && deleteRolePermResult) {
			result = true;
		}
		return result;
	}

	@Override
	public RoleInfo getRoleById(Integer roleId) throws Exception {
		return roleDao.getRole(roleId);
	}

	@Override
	public boolean updateRoleInfo(RoleInfo roleInfo) throws Exception {
		return roleDao.updateRole(roleInfo);
	}

	@Override
	public boolean assignPermission(Integer roleId, Integer[] permIds) throws Exception {
		boolean result = false;
		JdbcTransaction tran = JdbcTransaction.getInstance();
		tran.getAndBeginTransaction();
		boolean deleteRolePermResult = permDao.deleteRolePermission(roleId);
		boolean insertRolePermissionResult = false;
		if (permIds != null && permIds.length > 0) {
			insertRolePermissionResult = permDao.insertRolePermissionBatch(roleId, permIds);
		}else{
			insertRolePermissionResult = true;
		}
		tran.commit();
		if (deleteRolePermResult && insertRolePermissionResult) {
			result = true;
		}
		return result;

	}

}
