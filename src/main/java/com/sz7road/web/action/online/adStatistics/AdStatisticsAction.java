package com.sz7road.web.action.online.adStatistics;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.service.AdStatisticsService;

public class AdStatisticsAction extends ActionSupport {

	private String adId;
	private String site;
	private Logger logger = Logger.getLogger(AdStatisticsAction.class);
	
	public String enterFromAd(){
		String result = SUCCESS;
		AdStatisticsService adStatisticsService = ServiceLocator.getAdStatisticsService();
		try {
			if(adId != null){
				SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String adInfo = adId + "," + site + "," + fromat.format(new Date());
				HttpServletResponse response = ServletActionContext.getResponse();
				HttpServletRequest request = ServletActionContext.getRequest();
				int maxAge = 7 * 24 * 60 * 60;
				String sign = CookieUtil.getCookieValueByName(request, "SIGN");
				if(sign == null || !adId.equals(sign)){
					CookieUtil.addCookie(response, "ADINFO", adInfo, maxAge);
					CookieUtil.addCookie(response, "SIGN", adId, maxAge);
					adStatisticsService.enterCount(Integer.parseInt(adId), site);
				}
			}
		} catch (Exception e) {
			logger.error("广告页进入次数统计出错" + e);
		}
		return result;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	
	
}
