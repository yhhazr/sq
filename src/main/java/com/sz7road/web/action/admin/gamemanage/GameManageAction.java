package com.sz7road.web.action.admin.gamemanage;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils; 
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gamemanage.GameInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameService;


public class GameManageAction extends ActionSupport implements RequestAware, SessionAware, ServletContextAware{
	private static final Logger logger = LogManager.getLogger(GameManageAction.class);

	private Map<String, Object> requestMap;
	private ServletContext context;
	private Map<String, Object> sessionMap;
	public String queryGameInfoList(){
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
				GameService service = ServiceLocator.getGameService();
				PaginationResult<GameInfo> pageationResult = service.getGameInfo(pageInfo);
				requestMap.put("pageationResult", pageationResult);
				result = SUCCESS;
			} catch (Exception e) {
				result = INPUT;
				logger.error("Admin Query GameInfo List Error:" + e.getMessage(), e);
			}
			return result;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context=context;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap=session;	
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.requestMap=request;		
	}
}
