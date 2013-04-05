package com.sz7road.web.action.admin.permission;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.service.PermissionService;

public class PermissionManageAction extends ActionSupport implements RequestAware, SessionAware {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(PermissionManageAction.class);
	private Map<String, Object> sessionMap;
	private Map<String, Object> requestMap;
	private List<Permission> parentPermissionList;
	private List<Permission> permissionList;
	private Permission permission;
	private String permId;

	public String queryPermissions() {
		String result = INPUT;
		int parentPermIdInt = 0;
		try {
			if (!StringUtils.isBlank(permId) && StringUtils.isNumeric(permId)) {
				parentPermIdInt = Integer.parseInt(permId);
			}
			PermissionService permService = ServiceLocator.getPermissionService();
			if (parentPermIdInt == 0) {
				permissionList = permService.getAllParentPermissions();
			} else {
				permissionList = permService.getPermissionsByParentPermId(parentPermIdInt);
			}
			requestMap.put("permissionList", permissionList);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Login Error:" + e.getMessage(), e);
		}
		return result;
	}

	public String createPermission() {
		String result = INPUT;
		try {
			this.getParentPermissionList();
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String createPermissionSubmit() {
		String result = INPUT;
		PermissionService permService = ServiceLocator.getPermissionService();
		try {
			Permission permInfo = new Permission();
			permInfo.setParentPermId(permission.getParentPermId());
			permInfo.setOrderId(permission.getOrderId());
			permInfo.setPermName(permission.getPermName());
			permInfo.setPermDesc(permission.getPermDesc());
			permInfo.setPermUrl(permission.getPermUrl());
			permInfo.setMenuFlag(permission.isMenuFlag());
			permInfo.setRestrictedFlag(permission.isRestrictedFlag());
			boolean createResult = permService.createPermission(permInfo);
			if (createResult) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Create Role Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editPermission() {
		String result = INPUT;
		PermissionService permService = ServiceLocator.getPermissionService();
		int permIdInt = 0;
		try {
			if (!StringUtils.isBlank(permId) && StringUtils.isNumeric(permId)) {
				permIdInt = Integer.parseInt(permId);
			}
			permission = permService.getPermissionsByPermId(permIdInt);
			if (permission != null && !StringUtils.isBlank(permission.getPermName())) {
				this.getParentPermissionList();
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Edit Role Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editPermissionSubmit() {
		String result = INPUT;
		PermissionService permService = ServiceLocator.getPermissionService();
		try {
			Permission perm = new Permission();
			perm.setPermId(permission.getPermId());
			perm.setParentPermId(permission.getParentPermId());
			perm.setPermName(permission.getPermName());
			perm.setPermDesc(permission.getPermDesc());
			perm.setOrderId(permission.getOrderId());
			perm.setPermUrl(permission.getPermUrl());
			perm.setRestrictedFlag(permission.isRestrictedFlag());
			perm.setMenuFlag(permission.isMenuFlag());
			boolean updateResult = permService.updatePermission(perm);
			if (updateResult) {
				result = SUCCESS;
			}

		} catch (Exception e) {
			logger.error("Admin Edit Role Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String deletePermission() {
		String result = INPUT;
		PermissionService permService = ServiceLocator.getPermissionService();
		int permIdInt = 0;
		try {
			if (!StringUtils.isBlank(permId) && StringUtils.isNumeric(permId)) {
				permIdInt = Integer.parseInt(permId);
			}
			permService.deletePermission(permIdInt);
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("Admin Delete Role Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public List<Permission> getParentPermissionList() {
		PermissionService permService = ServiceLocator.getPermissionService();
		try {
			parentPermissionList = permService.getAllParentPermissions();
		} catch (Exception e) {
			logger.error("Admin Get Role List Error:" + e.getMessage(), e);
		}
		return parentPermissionList;
	}

	public void setParentPermissionList(List<Permission> parentPermissionList) {
		this.parentPermissionList = parentPermissionList;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}

	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	@Override
	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
