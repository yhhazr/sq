package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.rolemanage.PermissionInfo;

/**
 * The home interface of User EJB
 * @author Jimmy Huang
 */
public interface PermissionService {
	
	public Permission getPermissionsByPermId(Integer permId) throws Exception;

	public List<Permission> getPermissionsByUserId(Integer userId) throws Exception;
	
	public List<Permission> getPermissionsByParentPermId(Integer parentPermId) throws Exception;
	
	public List<PermissionInfo> getAllPermissionsTree() throws Exception;
	
	public List<PermissionInfo> getAllMenusTree(Integer userId) throws Exception;
	
	public List<Permission> getAllParentPermissions() throws Exception;

	public List<PermissionInfo> getPermissionByRole(Integer roleId) throws Exception;
	
	public List<Integer> getPermIdListByRole(Integer roleId) throws Exception;

	public boolean createPermission(Permission permission) throws Exception;
	
	public boolean updatePermission(Permission permission) throws Exception;
	
	public void deletePermission(Integer permId) throws Exception;

	public void insertRolePermission(Integer roleId, List permId) throws Exception;
	
	
}
