package com.sz7road.web.interceptor;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.model.rolemanage.Permission;
import com.sz7road.web.model.usermanage.AdminUserRight;

public class GlobalInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(GlobalInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = Action.LOGIN;
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		String requestUri = request.getServletPath();
		Locale locale = request.getLocale();
		Map<String, Object> sessionMap = actionContext.getSession();
		AdminUserRight userRight = (AdminUserRight) sessionMap.get(AppConstants.ADMINUSER_USER_RIGHT);
		if ("/adminLoginSubmit.action".equals(requestUri) || "/adminLogin.action".equals(requestUri)) {
			result = invocation.invoke();
		} else {
			if (userRight != null) {
				if ((userRight.getPermissions() == null) || (userRight.getPermissions().size() == 0)) {
					sessionMap.put("visitError", LocalizedTextUtil.findDefaultText("admin.visit.errors.hasNoPrivilege", locale));
					logger.info(LocalizedTextUtil.findDefaultText("admin.visit.errors.hasNoPrivilege", locale));
				} else {
					for(Permission permission : userRight.getPermissions()){
						if(requestUri.equals(permission.getPermUrl())){
							result = invocation.invoke();
						}
					}
				}
			} else {
				logger.info(LocalizedTextUtil.findDefaultText("admin.visit.errors.hasNoPrivilege", locale, new Object[] { requestUri }));
				sessionMap.put("visitError", LocalizedTextUtil.findDefaultText("admin.visit.errors.loginFirst", locale));
			}
		}

		return result;

	}

}
