package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;

import com.sz7road.web.common.transaction.JdbcTransaction;
import com.sz7road.web.dao.PermissionDao;
import com.sz7road.web.dao.impl.PermissionDaoImpl;
import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.rolemanage.PermissionInfo;
import com.sz7road.web.service.PermissionService;

/**
 * Service Implementation for module user permission
 * 
 * @author Jimmy Huang
 * @version $Id: PermissionServiceImpl.java,v 1.3 2008/08/28 08:53:15 Jimmy Huang Exp $
 */
public class PermissionServiceImpl implements PermissionService {
	private static PermissionServiceImpl _this;
	private static PermissionDao dao = null;

	/**
	 * Constructor
	 */
	private PermissionServiceImpl() {
		dao = PermissionDaoImpl.getInstance();
	}

	/**
	 * @return PermissionBusiness
	 */
	public synchronized static PermissionServiceImpl getInstance() {
		if (_this == null)
			_this = new PermissionServiceImpl();
		return _this;
	}

	@Override
	public List<Permission> getPermissionsByUserId(Integer userId) throws Exception {
		return dao.getPermissionsByUserId(userId);
	}

	@Override
	public List<PermissionInfo> getAllPermissionsTree() throws Exception {
		List<Permission> permList = dao.getAllPermissions();
		List<PermissionInfo> permInfoList = loadPermissionInfoTree(permList, 1);
		return permInfoList;
	}
	
	@Override
	public List<PermissionInfo> getAllMenusTree(Integer userId) throws Exception {
		List<Permission> permList = dao.getAllMenuPermissions(userId);
		List<PermissionInfo> permInfoList = loadPermissionInfoTree(permList, 1);
		return permInfoList;
	}

	private List<PermissionInfo> loadPermissionInfoTree(List<Permission> menuList, int parentId) {
		List<Permission> rootList = new ArrayList<Permission>();
		List<PermissionInfo> permInfoList = new ArrayList<PermissionInfo>();
		for (Permission perm : menuList) {
			if (perm.getParentPermId() == parentId) {
				rootList.add(perm);
			}
		}
		if (rootList != null && rootList.size() > 0) {
			for (Permission perm : rootList) {
				PermissionInfo info = new PermissionInfo();
				info.setPermId(perm.getPermId());
				info.setParentPermId(perm.getParentPermId());
				info.setPermName(perm.getPermName());
				info.setPermDesc(perm.getPermDesc());
				info.setPermUrl(perm.getPermUrl());
				info.setChildPermissions(loadPermissionInfoTree(menuList, perm.getPermId()));
				permInfoList.add(info);
			}
		}
		return permInfoList;
	}

	@Override
	public List<PermissionInfo> getPermissionByRole(Integer roleId) throws Exception {
		return dao.getPermissionByRole(roleId);
	}

	@Override
	public void insertRolePermission(Integer roleId, List permIdList) throws Exception {
		DB.beginTransaction();
		if (permIdList.size() > 0) {
			dao.deleteRolePermission(roleId);
			// insert permission one by one.
			for (int i = 0; i < permIdList.size(); i++) {
				Integer eachPermId = Integer.valueOf(permIdList.get(i).toString());
				dao.insertRolePermission(roleId, eachPermId);
			}
		}

	}

	@Override
	public List<Integer> getPermIdListByRole(Integer roleId) throws Exception {
		return dao.getPermIdListByRole(roleId);
	}

	@Override
	public List<Permission> getAllParentPermissions() throws Exception {
		return dao.getAllParentPermissions();
	}

	@Override
	public boolean createPermission(Permission permission) throws Exception {
		return dao.createPermission(permission);
	}

	@Override
	public List<Permission> getPermissionsByParentPermId(Integer parentPermId) throws Exception {
		return dao.getPermissionsByParentPermId(parentPermId);
	}

	@Override
	public Permission getPermissionsByPermId(Integer permId) throws Exception {
		return dao.getPermissionsByPermId(permId);
	}

	@Override
	public boolean updatePermission(Permission permission) throws Exception {
		return dao.updatePermission(permission);
	}

	@Override
	public void deletePermission(Integer permId) throws Exception {
		JdbcTransaction tran = JdbcTransaction.getInstance();
		List<Permission> permList = dao.getPermissionsByParentPermId(permId);
		tran.getAndBeginTransaction();
		dao.deletePermission(permId);
		for(Permission perm : permList){
			dao.deletePermission(perm.getPermId());
		}
		tran.commit();
	}

}
