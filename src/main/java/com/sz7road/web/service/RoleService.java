package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.rolemanage.RoleInfo;

public interface RoleService {
	
	public List<RoleInfo> getAllRoleInfoList() throws Exception;

	public boolean createRole(RoleInfo roleInfo) throws Exception;
	
	public boolean deleteRole(Integer roleId) throws Exception;
	
	public RoleInfo getRoleById(Integer roleId) throws Exception;
	
	public boolean updateRoleInfo(RoleInfo roleInfo) throws Exception;
	
	public boolean assignPermission(Integer roleId,Integer[] permIdArray) throws Exception;
}
