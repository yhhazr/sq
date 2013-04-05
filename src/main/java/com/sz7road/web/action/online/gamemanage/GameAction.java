package com.sz7road.web.action.online.gamemanage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.online.user.UserAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.HttpClientUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.gamemanage.GameServerInfo;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.service.AdStatisticsService;
import com.sz7road.web.service.OnlineUserService;

public class GameAction extends ActionSupport {
	private static final Logger logger = LogManager.getLogger(GameAction.class);

	private int id;

	private String serverName;

	private long nowTime;

	private int nowHour;

	private int code;

	private String message;

//	private String gameUrl;
	// 游戏id
	private String gameId;
	// 游戏区服id
	private String serverId;

	// 登录游戏验证
	public String checkLoginGame() {
		PlatformClient client = PlatformClient.getInstance();
		code = 1;

		String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
		if (userInfo != null && userInfo.length == 4) {
			String sign = CookieUtil.buildMD5Cookie(userInfo[0], userInfo[1],
					userInfo[2]);
			if (!sign.equals(userInfo[3])) {
				return SUCCESS;
			}
			LoginInfo logInfo = null;
			OnlineUserService onlineUserService = ServiceLocator
					.getOnlineUserService();
			try {
				logInfo = onlineUserService.servCheckAccount(userInfo[1], "1",
						"true");
			} catch (Exception e) {
				logger.error("check login error", e);
			}
			String gameUrl = SystemConfig.getProperty("login.game.real.path") + serverId;
			if (logInfo != null && "success".equals(logInfo.getRespFlag())) {
				try {
					if(serverId != null && !"".equals(serverId) && gameUrl != null && gameUrl.startsWith(SystemConfig.getProperty("platform.connect.base.url"))) {
						String response = client.checkLoginGame(gameUrl + "&_u="
								+ userInfo[1]);
						if (response != null && response.startsWith("{")
								&& response.endsWith("}")) {
							JSONObject jsonObj = JSONObject.fromObject(response);
							code = jsonObj.getInt("code");
							message = jsonObj.getString("msg");
						}
					}else{
						message = "请求参数错误！";
					}
						
				} catch (Exception e) {
					
					logger.error(
							"connect platform check login game interface error.",
							e);
				}
			}
		} else {
			code = 507;
			message = "您已经退出登录，请刷新后重新登录。";
		}
		if (code != 0 && (message == null || "".equals(message))) {
			message = "服务器连接超时，请重试一次。";
		}

		return SUCCESS;
	}

	// 得到最新推荐服务器Id
	public String acquireRecommendServerId() {
		id = -1;
		try {
			PlatformClient client = PlatformClient.getInstance();
			PlatformGameServer server = client.findLastRecommendServer();
			if (server != null && StringUtils.isNotBlank(server.getId())
					&& StringUtils.isNumeric(server.getId())) {
				id = Integer.parseInt(server.getId());
				serverName = server.getServerName();
			}
		} catch (Exception e) {
			logger.info("get theLastServerId error.", e);
		}
		return SUCCESS;
	}

	// 得到当前时间
	public String acquireNowTime() {
		Date nowDate = new Date();
		nowTime = nowDate.getTime();
		nowHour = DateUtil.hour();

		return SUCCESS;
	}

	/**
	 * 根据客服端传送的gameId和serverId获取该区服信息
	 * 
	 * @author hai.yuan
	 * @return
	 * @createDate 2012-12-11 上午10:29:46
	 */
	@JSON(serialize=false)
	public String getGameNameById() {
		AdStatisticsService adStatisticsService = ServiceLocator.getAdStatisticsService();
		try {
			GameServerInfo server = adStatisticsService.getServerById(serverId);
			if(server != null){
				serverName = server.getServerName();
				String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
				if(userInfo != null && userInfo.length == 4 ){
					RegInfo regInfo = adStatisticsService.checkUserName(userInfo[1]);
					if(regInfo != null){
						String site = geneSite(Integer.parseInt(server.getServerNo()));
						List<NameValuePair> nvps = new ArrayList<NameValuePair>();
						SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String clickTime = fromat.format(regInfo.getClickTime());
						String regTime = fromat.format(regInfo.getRegTime());
						nvps.add(new BasicNameValuePair("clickTime", clickTime));
						nvps.add(new BasicNameValuePair("regTime", regTime));
						nvps.add(new BasicNameValuePair("adid", Integer.toString(regInfo.getAdId())));
						nvps.add(new BasicNameValuePair("site", site));
						nvps.add(new BasicNameValuePair("userid", userInfo[0]));
						nvps.add(new BasicNameValuePair("username", userInfo[1]));
						nvps.add(new BasicNameValuePair("sid", server.getMainServerNo()));
						String sign = MD5Util.getMD5String(Integer.toString(regInfo.getAdId()) + userInfo[0] + userInfo[1] + site + server.getMainServerNo() + "SNTTQ-S67roadfD11-777yxd33ban-0668-69b8FN-AD11-sheen-111SGEEN");
						nvps.add(new BasicNameValuePair("sign", sign));
						String response = HttpClientUtil.get(SystemConfig.getProperty("adStatistics.url"), nvps);
						if(response.charAt(11) != '0'){
							logger.error("数据插入游戏失败：" + response);
						}
					}
				}
			}			
		} catch (Exception e) {
			logger.error("取得游戏服务器错误" + e.getMessage(),e);
		}
		return SUCCESS;
	}
	
	private String geneSite(int serverId) {
		String site = null;
		if (serverId < 10) {
			site = "7road_" + "000" + serverId;
		} else if (serverId < 100){
			site = "7road_" + "00" + serverId;
		} else if (serverId < 1000) {
			site = "7road_" + "0" + serverId;
		} else {
			site = "7road_" + serverId;
		}
		
		return site; 
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getNowHour() {
		return nowHour;
	}

	public void setNowHour(int nowHour) {
		this.nowHour = nowHour;
	}

	public long getNowTime() {
		return nowTime;
	}

	public void setNowTime(long nowTime) {
		this.nowTime = nowTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

//	public String getGameUrl() {
//		return gameUrl;
//	}
//
//	public void setGameUrl(String gameUrl) {
//		this.gameUrl = gameUrl;
//	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
