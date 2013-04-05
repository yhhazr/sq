package com.sz7road.web.action.admin.usermanage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.rolemanage.RoleInfo;
import com.sz7road.web.model.usermanage.UserInfo;
import com.sz7road.web.service.RoleService;
import com.sz7road.web.service.UserService;

public class UserManageAction extends ActionSupport implements RequestAware, SessionAware {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	private Map<String, Object> sessionMap;
	private Map<String, Object> requestMap;
	private String userId;
	private UserInfo user;
	private PageInfo pager;
	private List<RoleInfo> roleInfoList;

	public String queryUserList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = INPUT;
		try {
			PageInfo pageInfo = new PageInfo();
			int startRow = 0;
			String pagerOffset = request.getParameter("pager.offset");
			int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
			if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
				startRow = Integer.parseInt(pagerOffset);
			}
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			UserService userService = ServiceLocator.getUserService();
			PaginationResult<UserInfo> pageationResult = userService.getUserLoginInfoList(pageInfo);
			requestMap.put("pageationResult", pageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Login Error:" + e.getMessage(), e);
		}
		return result;
	}

	public String createUser() {
		String result = INPUT;
		try {
			this.getRoleInfoList();
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String createUserSubmit() {
		String result = INPUT;
		UserService userService = ServiceLocator.getUserService();
		try {
			UserInfo dbUserInfo = userService.getUserLoginInfoByUserName(user.getUserName());
			if (dbUserInfo != null && !user.getUserName().equals(dbUserInfo.getUserName())) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserName(user.getUserName());
				userInfo.setPassWord(DigestUtils.md5Hex(user.getPassWord()));
				userInfo.setEmailAddr(user.getEmailAddr());
				userInfo.setRoleId(user.getRoleId());
				boolean createResult = userService.createUser(userInfo);
				if (createResult) {
					result = SUCCESS;
				}
			} else {
				this.addFieldError("user.userName", this.getText("admin.createUser.userName.duplicate.error"));
			}

		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editUser() {
		String result = INPUT;
		UserService service = ServiceLocator.getUserService();
		int userIdInt = 0;
		try {
			if (!StringUtils.isBlank(userId) && StringUtils.isNumeric(userId)) {
				userIdInt = Integer.parseInt(userId);
			}
			this.getRoleInfoList();
			user = service.getUserLoginInfoByUserId(userIdInt);
			if (user != null && !StringUtils.isBlank(user.getUserName())) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editUserSubmit() {
		String result = INPUT;
		UserService service = ServiceLocator.getUserService();
		try {
			UserInfo dbUserInfo = service.getUserLoginInfoByUserId(user.getUserId());
			String md5OldPassWord = DigestUtils.md5Hex(user.getOldPassWord());
			if (dbUserInfo != null && md5OldPassWord.equals(dbUserInfo.getPassWord())) {
				dbUserInfo.setUserName(user.getUserName());
				dbUserInfo.setPassWord(DigestUtils.md5Hex(user.getPassWord()));
				dbUserInfo.setEmailAddr(user.getEmailAddr());
				dbUserInfo.setRoleId(user.getRoleId());
				boolean updateResult = service.updateUserLoginInfo(dbUserInfo);
				if (updateResult) {
					result = SUCCESS;
				}
			} else {
				this.addFieldError("user.oldPassWord", this.getText("admin.editUser.oldPassWord.wrong.error"));
			}

		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String deleteUser() {
		String result = INPUT;
		UserService service = ServiceLocator.getUserService();
		int userIdInt = 0;
		try {
			if (!StringUtils.isBlank(userId) && StringUtils.isNumeric(userId)) {
				userIdInt = Integer.parseInt(userId);
			}
			boolean deleteResult = service.deleteUser(userIdInt);
			if (deleteResult) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Create User Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public PageInfo getPager() {
		return pager;
	}

	public void setPager(PageInfo pager) {
		this.pager = pager;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<RoleInfo> getRoleInfoList() {
		RoleService roleService = ServiceLocator.getRoleService();
		try {
			roleInfoList = roleService.getAllRoleInfoList();
		} catch (Exception e) {
			logger.error("Admin Get Role List Error:" + e.getMessage(), e);
		}
		return roleInfoList;
	}

	public void setRoleInfoList(List<RoleInfo> roleInfoList) {
		this.roleInfoList = roleInfoList;
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
