package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.rolemanage.PermissionInfo;

public interface PermissionDao {

	public boolean createPermission(final Permission permission) throws Exception;
	
	public boolean insertRolePermission(final Integer roleId, final Integer permId) throws Exception;
	
	public boolean insertRolePermissionBatch(final Integer roleId, final Integer[] permIds) throws Exception;

	public Permission getPermissionsByPermId(final Integer permId) throws Exception;
	
	public List<PermissionInfo> getPermissionByRole(final Integer roleId) throws Exception;
	
	public List<Integer> getPermIdListByRole(final Integer roleId) throws Exception;
	
	public boolean updatePermission(Permission permission) throws Exception;
	
	public boolean deletePermission(Integer permId) throws Exception;

	public boolean deleteRolePermission(final Integer roleId) throws Exception;

	public List<Permission> getPermissionsByUserId(final Integer userId) throws Exception;
	
	public List<Permission> getAllParentPermissions() throws Exception;
	
	public List<Permission> getPermissionsByParentPermId(Integer parentPermId) throws Exception;
	
	public List<Permission> getAllPermissions() throws Exception;
	
	public List<Permission> getAllMenuPermissions(final Integer userId) throws Exception;

}