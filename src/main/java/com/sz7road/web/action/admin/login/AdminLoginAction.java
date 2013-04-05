package com.sz7road.web.action.admin.login;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.servlet.CaptchaServlet;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.common.util.Wildcard;
import com.sz7road.web.model.captcha.FunctionUsageLog;
import com.sz7road.web.model.rolemanage.PermissionInfo;
import com.sz7road.web.model.usermanage.AdminUserRight;
import com.sz7road.web.model.usermanage.UserInfo;
import com.sz7road.web.service.CaptchaService;
import com.sz7road.web.service.PermissionService;
import com.sz7road.web.service.UserService;

public class AdminLoginAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(AdminLoginAction.class);
	private String userName;
	private String passWord;
	private String verifyCode;
	private Map<String,Object> sessionMap;
	
	public String adminLogin() {
		String result = INPUT;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			UserInfo userInfo = new UserInfo();
			String remoteAddr = getIpAddr(request);
			boolean allowLogin = checkRemoteAddrInSide(remoteAddr);
			if (!allowLogin) {
				logger.info("Reject admin login, UserName:[" + userName + "], IP:[" + remoteAddr + "]");
				this.addActionError(this.getText("admin.login.errors.IP.hasNoPrivilege"));
				return INPUT;
			} else {
				logger.info("Allow admin login, UserName:[" + userName + "], IP:[" + remoteAddr + "]");
				UserService service = ServiceLocator.getUserService();
				userInfo = service.getUserLoginInfoByUserName(userName);
				if (userInfo != null && DigestUtils.md5Hex(passWord).equals(userInfo.getPassWord())) {
					AdminUserRight userRight = new AdminUserRight();
					userRight.setCurrentUser(userName);
					userRight.setUserId(userInfo.getUserId());
					userRight.setLoginInfo(userInfo);
					PermissionService permService = ServiceLocator.getPermissionService();
					userRight.setPermissions(permService.getPermissionsByUserId(userInfo.getUserId()));
					
					List<PermissionInfo> permissionInfoList = permService.getAllMenusTree(userInfo.getUserId());
					userRight.setMenuPermInfoList(permissionInfoList);
					sessionMap.put(AppConstants.ADMINUSER_USER_RIGHT, userRight);
					if ((userRight.getPermissions() == null) || (userRight.getPermissions().size() == 0)) {
						this.addActionError(this.getText("admin.login.errors.hasNoPrivilege"));
						result = INPUT;
					} else{
						result = SUCCESS;
					}
				} else {
					this.addActionError(this.getText("admin.user.validate.error.loginFailed"));
					result = INPUT;
				}
				
				if (request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY) != null) {
					String verifyImg = (String) request.getSession().getAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY);
					if (verifyCode != null && (verifyCode.trim().equals("") || !verifyCode.equalsIgnoreCase(verifyImg))) {
						this.addActionError(this.getText("admin.user.validate.verifyCode.error"));
					}
				} 
				
				CaptchaService cservice = ServiceLocator.getCaptchaService();
				Long duration = Long.valueOf(SystemConfig.getProperty("login.locked.time"));
				int maxLoginTimes = Integer.parseInt(SystemConfig.getProperty("allowed.login.times"));
				FunctionUsageLog usageLog = new FunctionUsageLog();
				usageLog.setIdentifierId(remoteAddr);
				usageLog.setFunctionCode(AppConstants.FUNCTION_CODE_ADMINLOGIN);
				int failedCnt = cservice.getUserAttemptCnt(usageLog.getIdentifierId(), usageLog.getFunctionCode(), duration);
				
				if (this.getActionErrors().size() > 0 && failedCnt < maxLoginTimes) {
					//insert attempt login log
					usageLog.setUsageTime(new Date());
					cservice.AddUserAttemptLog(usageLog);
					failedCnt++;
					result = INPUT;
				}
				if (failedCnt >= maxLoginTimes) {
					this.clearErrors();
					this.addActionError(this.getText("admin.login.errors.account.locked"));
					result = INPUT;
				} 
				request.getSession().removeAttribute(CaptchaServlet.KAPTCHA_SESSION_KEY);
			}
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Login Error:" + e.getMessage(), e);
		}
		return result;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;		
	}

	private boolean checkRemoteAddrInSide(String remoteAddr) {
		String ips = SystemConfig.getProperty("admin.access.allowedips");
		if (ips != null) {
			return Wildcard.matches(ips, remoteAddr, "\\|");
		}
		return false;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
}
