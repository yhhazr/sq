package com.sz7road.web.action.admin.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.rolemanage.PermissionInfo;
import com.sz7road.web.model.rolemanage.RoleInfo;
import com.sz7road.web.service.PermissionService;
import com.sz7road.web.service.RoleService;

public class RoleManageAction extends ActionSupport implements RequestAware, SessionAware {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RoleManageAction.class);
	private Map<String, Object> sessionMap;
	private Map<String, Object> requestMap;
	private boolean roleEnable;
	private String roleId;
	private RoleInfo role;
	private Integer[] permCheckBox = new Integer[]{};
	private List<PermissionInfo> permInfoList;
	List<Integer> needCheckedPermInfoList = new ArrayList<Integer>();

	public Integer[] getPermCheckBox() {
		return permCheckBox;
	}

	public void setPermCheckBox(Integer[] permCheckBox) {
		this.permCheckBox = permCheckBox;
	}

	public String queryRoleList() {
		String result = INPUT;
		try {
			RoleService roleService = ServiceLocator.getRoleService();
			List<RoleInfo> roleInfoList = roleService.getAllRoleInfoList();
			requestMap.put("roleInfoList", roleInfoList);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Query Role List Error:" + e.getMessage(), e);
		}
		return result;
	}

	public String createRoleSubmit() {
		String result = INPUT;
		RoleService roleService = ServiceLocator.getRoleService();
		try {
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRoleName(role.getRoleName());
			roleInfo.setRoleDesc(role.getRoleDesc());
			roleInfo.setEnableFlag(role.isEnableFlag());
			boolean createResult = roleService.createRole(roleInfo);
			if (createResult) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Create Role Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editRole() {
		String result = INPUT;
		RoleService roleService = ServiceLocator.getRoleService();
		int roleIdInt = 0;
		try {
			if (!StringUtils.isBlank(roleId) && StringUtils.isNumeric(roleId)) {
				roleIdInt = Integer.parseInt(roleId);
			}
			role = roleService.getRoleById(roleIdInt);
			if (role != null && !StringUtils.isBlank(role.getRoleName())) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Edit Role Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editRoleSubmit() {
		String result = INPUT;
		RoleService roleService = ServiceLocator.getRoleService();
		try {
			RoleInfo roleInfo = new RoleInfo();
			roleInfo.setRoleId(role.getRoleId());
			roleInfo.setRoleName(role.getRoleName());
			roleInfo.setRoleDesc(role.getRoleDesc());
			roleInfo.setEnableFlag(role.isEnableFlag());
			boolean updateResult = roleService.updateRoleInfo(roleInfo);
			if (updateResult) {
				result = SUCCESS;
			}

		} catch (Exception e) {
			logger.error("Admin Edit Role Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	
	public String assignPermission() {
		String result = INPUT;
		PermissionService permService = ServiceLocator.getPermissionService();
		RoleService roleService = ServiceLocator.getRoleService();
		int roleIdInt = 0;
		try {
			if (!StringUtils.isBlank(roleId) && StringUtils.isNumeric(roleId)) {
				roleIdInt = Integer.parseInt(roleId);
			}
			role = roleService.getRoleById(roleIdInt);
			needCheckedPermInfoList = permService.getPermIdListByRole(roleIdInt);
			this.getPermInfoList();
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("Admin Assign Permission Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String assignPermissionSubmit() {
		String result = INPUT;
		RoleService roleService = ServiceLocator.getRoleService();
		try {
			boolean insertResult = roleService.assignPermission(role.getRoleId(), permCheckBox);
			if (insertResult) {
				result = SUCCESS;
			}

		} catch (Exception e) {
			logger.error("Admin Assign Permission Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String deleteRole() {
		String result = INPUT;
		RoleService roleService = ServiceLocator.getRoleService();
		int roleIdInt = 0;
		try {
			if (!StringUtils.isBlank(roleId) && StringUtils.isNumeric(roleId)) {
				roleIdInt = Integer.parseInt(roleId);
			}
			boolean deleteResult = roleService.deleteRole(roleIdInt);
			if (deleteResult) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Delete Role Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public RoleInfo getRole() {
		return role;
	}

	public void setRole(RoleInfo role) {
		this.role = role;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean isRoleEnable() {
		return roleEnable;
	}

	public void setRoleEnable(boolean roleEnable) {
		this.roleEnable = roleEnable;
	}

	public List<Integer> getNeedCheckedPermInfoList() {
		return needCheckedPermInfoList;
	}

	public void setNeedCheckedPermInfoList(List<Integer> needCheckedPermInfoList) {
		this.needCheckedPermInfoList = needCheckedPermInfoList;
	}

	public List<PermissionInfo> getPermInfoList() {
		PermissionService permissionService = ServiceLocator.getPermissionService();
		try {
			permInfoList = permissionService.getAllPermissionsTree();
		} catch (Exception e) {
			logger.error("Admin Get Role List Error:" + e.getMessage(), e);
		}
		return permInfoList;
	}

	public void setPermInfoList(List<PermissionInfo> permInfoList) {
		this.permInfoList = permInfoList;
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
