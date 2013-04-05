package com.sz7road.web.action.admin.genehtml;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;
import com.sz7road.web.service.GeneHtmlService;
import com.sz7road.web.service.impl.AutoGeneHtmlTimeServiceImpl;
import com.sz7road.web.service.impl.GeneHtmlServiceImpl;


public class AutoGeneHtmlAction extends ActionSupport {
	private static final Logger logger = LogManager.getLogger(AutoGeneHtmlAction.class);
	private static String FUTURE_TIME = "3000-12-12 00:00:00";

	private static Timer timer = null;
	private File templateFile = null;
	
	private static long PERIOD_TIME = 20 * 1000;
	private static long DELAY_TIME = 30 * 1000;
	
	private String fromPage;
	
	public String geneServerPageStart() {
		timer = new Timer();
		TimerTask task = new TimerTask() {
			GeneHtmlService geneHtmlService = new GeneHtmlServiceImpl();
			PlatformClient client = new PlatformClient();
			@Override
			public void run() {
//				logger.info("auto gene html is running...");
				long startTime = getNextTime();
				long nowTime = new Date().getTime();
				if(startTime >= (nowTime - (DELAY_TIME + PERIOD_TIME)) && startTime < (nowTime - DELAY_TIME)){
					//自动生成页面时，调用清除缓存接口（已注释）
//					try {
//						client.cleanPlatformCache();
//					} catch (Exception e) {
//						logger.error("auto clean platform cache error.", e); 
//					}
					try {
						geneHtmlService.geneIndexHtml("/index.ftl", "/index.html");
						geneHtmlService.geneNewsList("/news/newsList.ftl", "/news/newsList.html");
						geneHtmlService.geneServerList("/serverList.ftl", "/serverList.html");
						geneHtmlService.genePicList("/picture/picList.ftl", "/picture/picList.html");
						geneHtmlService.geneNewbGift("/others/newbGift.ftl", "/others/newbGift.html");
						geneHtmlService.geneGameData("/gamedata/dataList.ftl", "/gamedata/dataList.html");
						geneHtmlService.geneActivityHtml("/activity/activity.ftl", "/activity/activity.html");
						geneHtmlService.geneLoginEXE("", "");
						geneHtmlService.geneSkillPage("/others/skill.html", "/skill.html");
						//geneHtmlService.geneBaiduPage("", "");
						logger.info("auto gene html success.");
					} catch (Exception e) {
						logger.error("auto gene html error.", e); 
					}
				}
			}
		};
		timer.scheduleAtFixedRate(task, DELAY_TIME, PERIOD_TIME);
		if("true".equals(fromPage)) {
			Map<String, Object> actionContext = this.getActionContext();
			ServletContext servletContext = (ServletContext) actionContext.get("servletContext");
			servletContext.setAttribute("geneServerHtmlStatus", "running");
		}
		return SUCCESS;
	}

	public String geneServerPageStop() {
		if (timer != null) {
			timer.cancel();
			if("true".equals(fromPage)) {
				Map<String, Object> actionContext = this.getActionContext();
				ServletContext servletContext = (ServletContext) actionContext.get("servletContext");
				servletContext.setAttribute("geneServerHtmlStatus", "stop");
			}
		}
		return SUCCESS;
	}

	// 得到下一次运行的时间
	private long getNextTime() {
		long nowTime = new Date().getTime();
		long nextTime = DateUtil.parse(FUTURE_TIME).getTime();
		
		AutoGeneHtmlTimeServiceImpl autoTimeServ = AutoGeneHtmlTimeServiceImpl.getInstance();
		try {
			List<AutoGeneHtmlTime> timeList = autoTimeServ.getAllAutoGeneHtmlTime();
			if(timeList != null && timeList.size() > 0) {
				for (AutoGeneHtmlTime autoTime : timeList) {
					String timeStr = autoTime.getGeneTime();
					int id = autoTime.getId();
					if (StringUtils.isNumeric(timeStr)) {
						// 时间选为离现在最近的时间
						long time = new Date(Long.parseLong(timeStr)).getTime();
						if (time >= nowTime - (DELAY_TIME + PERIOD_TIME)) {
							if (time - nowTime < nextTime - nowTime)
								nextTime = time;
						} else {
							autoTimeServ.removeAutoGeneHtmlTimeById(id);
						}
					} else {
						autoTimeServ.removeAutoGeneHtmlTimeById(id);
					}
				}
			}
		} catch (Exception e) {
			logger.error("get the last auto gene time error.", e);
		}

		return nextTime;
	}
	
	//获得action上下文
	@JSON(serialize = false)
	private Map<String, Object> getActionContext() {
		Map<String, Object> actionContext = new HashMap<String, Object>();
		ServletContext servletContext = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = request.getSession();
		
		actionContext.put("request", request);
		actionContext.put("response", response);
		actionContext.put("servletContext", servletContext);
		actionContext.put("session", session);
		
		return actionContext;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}
	

}
