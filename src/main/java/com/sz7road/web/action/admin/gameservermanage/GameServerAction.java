package com.sz7road.web.action.admin.gameservermanage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils; 
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;
import com.sz7road.web.model.gamemanage.GameServerAllInfo;
import com.sz7road.web.model.gamemanage.GameServerStatus;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.gamemanage.PlatformResponse;
import com.sz7road.web.model.gamemanage.StopGameServerInfo;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.OnlineUserService;
import com.sz7road.web.service.impl.AutoGeneHtmlTimeServiceImpl;
import com.sz7road.web.service.impl.GameServerServiceImpl;

/**
 * 2012.10.31 同步平台维护接口优化
 * @author john.jiang
 *
 */
public class GameServerAction extends ActionSupport implements RequestAware, SessionAware{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	private Map<String, Object> requestMap;
	private Map<String, Object> sessionMap;

	private List<GameServerStatus> statusList;
	private Map<String, GameServerStatus> statusMap;
	private String statusId;
	private PageInfo pager;
	private String id;
	private PlatformGameServer gameServer;
	private String isDesc;
	private String stopServer;
	private String respMessage;
	private HttpServletRequest request;
	private String serverIds;
	private StopGameServerInfo stopServerInfo;
	private int code;
	private String message;
	private String gameUrl;
	
	private static GameServerService gameServerServ = ServiceLocator.getGameServerService();
	
	private static String GAME_ID = String.valueOf(AppConstants.GAME_ID);
	private static final String PROPERTIES_FILE_NAME = "WEB-INF/classes/timeOfGeneServerHtml.properties";
	private static final String PROPERTIES_FILE_PATH = getBasePath() + "/" + PROPERTIES_FILE_NAME; 
	private String geneServerHtmlStatus;
	
	//分页查询
	public String queryGameServerByPage(){
		geneServerHtmlStatus = (String)ServletActionContext.getServletContext().getAttribute("geneServerHtmlStatus");
		if(geneServerHtmlStatus == null){
			geneServerHtmlStatus = "running";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = INPUT;
		
		try {
			PageInfo pageInfo = new PageInfo();
			int startRow = 1;
			String pagerOffset = request.getParameter("pager.offset");
			int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
			if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
				startRow = Integer.parseInt(pagerOffset);
			}
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			pageInfo.setOrder("id");
			//组合查询
			Map<String, String> queryMap = new HashMap<String, String>();;
			if(statusId != null && !"".equals(statusId)){
				queryMap.put("statusId", statusId);
			}
			//排序
			int sort = 0;
			if("false".equals(isDesc)){
				sort = 1;
			}
			PaginationResult<GameServerAllInfo> pageationResult = gameServerServ.getAllGameServerByPage(pageInfo, sort, queryMap);
			if(pageationResult != null && pageationResult.getResultList() != null){
				requestMap.put("pageationResult", pageationResult);
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("query gameServer by page error" + e.getMessage(), e);
		}
		return result;
	}
	
	//选服页测试页面服务器列表分页查询
	public String queryOnlineGameServerByPage() {
		String result = INPUT;
		HttpServletRequest request = ServletActionContext.getRequest();
		PageInfo pageInfo = new PageInfo();
		int startRow = 1;
		String pagerOffset = request.getParameter("pager.offset");
		int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
		if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
			startRow = Integer.parseInt(pagerOffset);
		}
		pageInfo.setStartRow(startRow);
		pageInfo.setPageSize(pageSize);
		pageInfo.setOrder("id");
		
		GameServerService gameServiceService = GameServerServiceImpl.getInstance();
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = gameServiceService.getAllGameServer();
			Collections.sort(serverListAsc, new Comparator<PlatformGameServer>() {
					public int compare(PlatformGameServer server0,PlatformGameServer server1) {
						return new Integer(server1.getServerNo()).compareTo(new Integer(server0.getServerNo()));
					}
			});
			formatServerTime(serverListAsc);
			List<PlatformGameServer> nowPageList = new ArrayList<PlatformGameServer>();
			int length = serverListAsc.size();
			if(pagerOffset == null) pagerOffset = "0";
			for (int i = 0; i < pageSize; i++) {
				if(Integer.parseInt(pagerOffset) + i >= length) break;
				nowPageList.add(serverListAsc.get(Integer.parseInt(pagerOffset) + i)); 
			}
			PaginationResult<PlatformGameServer> pageationResult = new PaginationResult<PlatformGameServer>(serverListAsc.size(), nowPageList);
			if(pageationResult != null && pageationResult.getResultList() != null){
				requestMap.put("pageationResult", pageationResult);
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("check online game server list error.", e);
		}
		
		return result;
	}

	//访问修改页，根据Id查询信息
	public String editGameServerPrepare(){
		String result = INPUT;
		String stopServerInfoIdList = null;
		int serverId = 0;
		if (!StringUtils.isBlank(id) && StringUtils.isNumeric(id)) {
			serverId = Integer.parseInt(id);
			stopServerInfoIdList = "[" + id + "]"; 
		}
		try {
			List<StopGameServerInfo> stopServerInfoList = gameServerServ.getStopServerInfo(stopServerInfoIdList);
			stopServerInfo = (stopServerInfoList != null && stopServerInfoList.size() > 0) ? stopServerInfoList.get(0) : null;
		} catch (Exception e1) {
			logger.error("get stopServerInfo error.", e1);
		}
		try {
			gameServer = gameServerServ.getGameServerById(serverId);
			if(gameServer != null && gameServer.getId() != null && !"".equals(gameServer.getId()) && !"0".equals(gameServer.getId())){
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("query gameServer by id error" + e.getMessage(), e);
		}
		return result;
	}
	
	//修改提交
	public String editGameServerSubmit(){
		String result = INPUT;
		gameServer.setGameId(GAME_ID);
		PlatformResponse resp = null;
		String status = gameServer.getServerStatus();
		try {
			if(!StringUtils.isBlank(gameServer.getId()) && StringUtils.isNumeric(gameServer.getId())) {
				if("-2".equals(gameServer.getServerStatus())){
					if(checkStopServerInfo(stopServerInfo)){
						long nowTime = new Date().getTime();
						long startTime = DateUtil.parse(stopServerInfo.getStartTime()).getTime();
						long endTime = DateUtil.parse(stopServerInfo.getEndTime()).getTime();
						if(nowTime < startTime || nowTime > endTime){
							gameServer.setServerStatus("1");
						}
					}else{
						return result;
					}
				}
				resp = gameServerServ.updateGameServer(gameServer);
				if (resp != null && resp.getCode() == 0) {
//					this.addStartTimeIntoProperties(String.valueOf(new Date().getTime()), gameServer.getOpeningTime());
					this.addStartTimeIntoDB(gameServer.getOpeningTime());
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("update gameServer submit error" + e.getMessage(), e);
		}
		//修改维护信息
		String stopServerInfoIdList = null;
		if (!StringUtils.isBlank(gameServer.getId()) && StringUtils.isNumeric(gameServer.getId())) {
			stopServerInfoIdList = "[" + gameServer.getId() + "]"; 
		}
		if("-2".equals(status)){
			result = INPUT;
			if(stopServerInfoIdList != null){
				boolean isStop = this.stopServerImpl(stopServerInfoIdList, stopServerInfo);
				if(isStop){
					result = SUCCESS;
				}
			}
		}else{
			if(checkStopServerInfo(stopServerInfo)){
				long nowTime = new Date().getTime();
				long startTime = DateUtil.parse(stopServerInfo.getStartTime()).getTime();
				long endTime = DateUtil.parse(stopServerInfo.getEndTime()).getTime();
				//维护进行中
				if(nowTime < endTime && nowTime >= startTime){
					result = INPUT;
					if(stopServerInfoIdList != null){
						String startTimeFormat = DateUtil.format(new Date(startTime));
						stopServerInfo.setEndTime(startTimeFormat);
						boolean isStop = this.stopServerImpl(stopServerInfoIdList, stopServerInfo);
						if(isStop){
							result = SUCCESS;
						}
					}
				}
			}
		}
		
		return result;
	}
	

	//访问新增页
	public String createGameServerPrepare(){
		String result = INPUT;
		result = SUCCESS;

		return result;
	}
	
	//新增提交
	public String createGameServerSubmit(){
		String result = INPUT;
		PlatformResponse resp = null;
		gameServer.setGameId(GAME_ID);
		gameServer.setCreateTime(String.valueOf(new Date().getTime()));
		try {
			if (!StringUtils.isBlank(gameServer.getId()) && StringUtils.isNumeric(gameServer.getId())) {
				resp = gameServerServ.addGameServer(gameServer);
				if (resp != null && resp.getCode() == 0) {
//					this.addStartTimeIntoProperties(String.valueOf(new Date().getTime()), gameServer.getOpeningTime());
					this.addStartTimeIntoDB(gameServer.getOpeningTime());
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("add gameServer submit error" + e.getMessage(), e);
		}
		return result;
	}
	
	//删除提交
	public String deleteGameServerSubmit(){
		String result = INPUT;
		PlatformResponse resp = null;
		try {
			if (!StringUtils.isBlank(id) && StringUtils.isNumeric(id)) {
				resp = gameServerServ.removeGameServerById(id);
				if (resp != null && resp.getCode() == 0) {
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("delete gameServer submit error" + e.getMessage(), e);
		}
		
		return result;
	}
	
	//删除维护信息
	public String removeServerStopInfo() {
		String result = INPUT;
		String[] ids = id.split(",");
		try {
			boolean isRemoved = gameServerServ.removeStopInfo(ids);
			if (isRemoved) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("删除维护信息异常。" ,e);
		}
		
		return result;
	}
	
	//批量修改状态（旧实现  平台接口逻辑已修改，此方法废弃）
	@Deprecated
	public String editServerStatusByIdsOld(){
		String result = INPUT;
		String ids = "";
		PlatformResponse resp = null;
		String[] s = id.split(",");
		for (int i = 0; i < s.length; i++) {
			if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
				ids += s[i];
				if(i < s.length - 1){
					ids += ",";
				}
			}
		}
		ids = "[" + ids + "]";
		try {
			resp = gameServerServ.updateServerStatusByIds(statusId, ids);
		} catch (Exception e) {
			logger.error("edit gameServer status error" + e.getMessage(), e);
		}
		if (resp != null && resp.getCode() == 0) {
			result = SUCCESS;
		}
		//修改维护信息
		try {
			List<StopGameServerInfo> stopServerInfoList = gameServerServ.getStopServerInfo(ids);
			if(stopServerInfoList != null && stopServerInfoList.size() > 0){
				for (StopGameServerInfo stopGameServerInfo : stopServerInfoList) {
					if(checkStopServerInfo(stopGameServerInfo)){
						long nowTime = new Date().getTime();
						long startTime = DateUtil.parse(stopGameServerInfo.getStartTime()).getTime();
						long endTime = DateUtil.parse(stopGameServerInfo.getEndTime()).getTime();
						//维护进行中
						if(nowTime < endTime && nowTime >= startTime){
							result = INPUT;
							String startTimeFormat = DateUtil.format(new Date(startTime));
							stopGameServerInfo.setEndTime(startTimeFormat);
							boolean isStop = this.stopServerImpl("[" + stopGameServerInfo.getServerId() + "]", stopGameServerInfo);
							if(isStop){
								result = SUCCESS;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("edit stop info error.", e);
		}
		
		return result;
	}

	//批量修改状态
	public String editServerStatusByIds(){
		String result = INPUT;
		String ids = "";
		PlatformResponse resp = null;
		String[] s = id.split(",");
		for (int i = 0; i < s.length; i++) {
			if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
				ids += s[i];
				if(i < s.length - 1){
					ids += ",";
				}
			}
		}
		ids = "[" + ids + "]";
		
		logger.error("ids str:" + id + " | ids:" + ids);
		
		StopGameServerInfo stopGameServerInfo = new StopGameServerInfo();
		String nowTimeFormat = DateUtil.format(new Date());
		stopGameServerInfo.setStartTime(nowTimeFormat);
		stopGameServerInfo.setEndTime(nowTimeFormat);
		stopGameServerInfo.setMessage(" ");
		boolean isStop = this.stopServerImpl(ids, stopGameServerInfo);
		if(isStop) {
			result = SUCCESS;
		}
		
		return result;
	}
	
	//批量停服准备
	public String stopServerStatusByIdsPrepare(){
		String result = INPUT;
		if("all".equals(id)){
			this.serverIds = "all";
			result = SUCCESS;
		}else{
			String ids = "";
			String[] s = id.split(",");
			if(s.length > 0){
				for (int i = 0; i < s.length; i++) {
					if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
						ids += s[i];
						if(i < s.length - 1){
							ids += ",";
						}
					}
				}
				ids = "[" + ids + "]";
				this.serverIds = ids;
				result = SUCCESS;
			}
		}
		
		return result; 
	}

	//批量停服提交 + 全区停服
	public String stopServerStatusByIdsSubmit(){
		String result = INPUT;
		boolean isStop = false;
		if(serverIds != null && !"".equals(serverIds)){
			isStop = this.stopServerImpl(serverIds, stopServerInfo);
		}
		if(isStop){
			result = SUCCESS;
		}

		return result;
	}
	
	//全区开服（旧实现  平台接口逻辑已修改，此方法废弃）
	@Deprecated
	public String startAllGameServerStatusOld() throws Exception{
		String result = INPUT;
		
		List<GameServerAllInfo> gameServerAllInfoList = null;
		try {
			PageInfo pageInfo = new PageInfo();
			int startRow = 1;
			int pageSize = 10000;
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			pageInfo.setOrder("id");
			//组合查询
			Map<String, String> queryMap = new HashMap<String, String>();;
			queryMap.put("serverStatus", "-2");
			//排序
			int sort = 0;
			PaginationResult<GameServerAllInfo> pageationResult = gameServerServ.getAllGameServerByPage(pageInfo, sort, queryMap);
			if(pageationResult != null && pageationResult.getResultList() != null){
				gameServerAllInfoList = pageationResult.getResultList();
			}

			if(gameServerAllInfoList != null && gameServerAllInfoList.size() > 0){
				boolean isStop = true;
				for (GameServerAllInfo gameServerAllInfo : gameServerAllInfoList) {
					StopGameServerInfo stopGameServerInfo = gameServerAllInfo.getStopInfo();
					if(stopGameServerInfo != null && checkStopServerInfo(stopGameServerInfo)){
						long nowTime = new Date().getTime();
						long startTime = DateUtil.parse(stopGameServerInfo.getStartTime()).getTime();
						long endTime = DateUtil.parse(stopGameServerInfo.getEndTime()).getTime();
						//维护进行中
						if(nowTime < endTime && nowTime >= startTime){
							result = INPUT;
							String startTimeFormat = DateUtil.format(new Date(startTime));
							stopGameServerInfo.setEndTime(startTimeFormat);
							isStop = this.stopServerImpl("[" + stopGameServerInfo.getServerId() + "]", stopGameServerInfo) && isStop;
						}
					}
				}
				if(isStop){
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("start all server from stop status error.", e);
		}
		
		PlatformResponse resp = null;
		int stopFlag = -2;
		resp = gameServerServ.updateAllServerStatus(stopFlag, AppConstants.START_STATUS);
		if (resp != null && resp.getCode() == 0) {
			result = SUCCESS;
		}
		
		return result; 
	}

	//全区开服
	public String startAllGameServerStatus() throws Exception{
		String result = INPUT;
		
		StopGameServerInfo stopGameServerInfo = new StopGameServerInfo();
		String nowTimeFormat = DateUtil.format(new Date());
		stopGameServerInfo.setStartTime(nowTimeFormat);
		stopGameServerInfo.setEndTime(nowTimeFormat);
		stopGameServerInfo.setMessage(" ");
		boolean isStop = this.stopServerImpl("all", stopGameServerInfo);
		if(isStop) {
			result = SUCCESS;
		}
		
		return result; 
	}
	
	//更新维护服务区信息具体实现方法
	private boolean stopServerImpl(String idsStr, StopGameServerInfo stopGameServerInfo){
		boolean isStop = false;
		try{
			if(checkStopServerInfo(stopGameServerInfo)){
				isStop = gameServerServ.stopGameServerByIds(stopGameServerInfo, idsStr);
			}
		}catch(Exception e){
			logger.error("stop gameServer submit error" + e.getMessage(), e);
		}
		if(isStop){
			this.addStartTimeIntoDB(stopGameServerInfo.getStartTime());
			if(stopGameServerInfo.getStartTime().equals(stopGameServerInfo.getEndTime())){
				this.addStartTimeIntoDB(String.valueOf(new Date().getTime()));
			}else{
				this.addStartTimeIntoDB(stopGameServerInfo.getEndTime());
			}
		}
		
		return isStop;
	}
	
	//检查维护信息是否合法
	private boolean checkStopServerInfo(StopGameServerInfo stopGameServerInfo){
		boolean isLegal = false;
		if(stopGameServerInfo != null){
			String timeFormatRegex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
			String startTimeStr = stopGameServerInfo.getStartTime();
			String endTimeStr = stopGameServerInfo.getEndTime();
			boolean checkStartTimeValue = startTimeStr.matches(timeFormatRegex);
			boolean checkEndTimeValue = endTimeStr.matches(timeFormatRegex);
			boolean checkMessageValue = stopGameServerInfo.getMessage() != null && !"".equals(stopGameServerInfo.getMessage());
			if(checkStartTimeValue && checkEndTimeValue && checkMessageValue){
				isLegal = DateUtil.parse(startTimeStr).getTime() <= DateUtil.parse(endTimeStr).getTime();
			}
		}
		return isLegal;
	}
	
	//写入开始生成服务器页面的时间
	private boolean addStartTimeIntoDB(String startTime){
		boolean isInsert = false; 
		AutoGeneHtmlTimeServiceImpl autoTimeServ = AutoGeneHtmlTimeServiceImpl.getInstance();
		try {
			isInsert = autoTimeServ.createAutoGeneHtmlTime(new AutoGeneHtmlTime(startTime));
		} catch (Exception e) {
			logger.error("insert auto time error.", e);
		}
		
		return isInsert;
	}
	
	
	/*后台进入游戏开始*/
	public String bgCheckLoginGame(){
		PlatformClient client = PlatformClient.getInstance();
		code = 1;
		String response;
		String name = null;
		//账号登陆
		boolean isLogin = false;
		if(!this.checkAccountLogin()){
			isLogin = this.defaultAccountLogin();
			name = SystemConfig.getProperty("background.test.enterGame.username");
		}else {
			isLogin = true;
			String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
			name = userInfo[1];
		}
		//进入游戏
		if(!isLogin){
			message = "默认账号未登陆";
		}else {
			try {
				response = client.checkLoginGame(gameUrl + "&_u=" + name);
				if(response != null && response.startsWith("{") && response.endsWith("}")){
					JSONObject jsonObj = JSONObject.fromObject(response);
					code = jsonObj.getInt("code");
					message = jsonObj.getString("msg");
				}
			} catch (Exception e) {
				logger.error("get username from config properties error.", e);
			}
		}

		return SUCCESS;
	}
	
	//判断是否已有账号登陆
	private boolean checkAccountLogin() {
		boolean result = false;
		//判断是否已登录
		String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
		if (userInfo != null && userInfo.length == 4) {
			String sign = CookieUtil.buildMD5Cookie(userInfo[0], userInfo[1], userInfo[2]);
			if(sign.equals(userInfo[3])){
				result = true;
			}
		}
		return result;
	}
	
	//后台默认账号登陆
	private boolean defaultAccountLogin() {
		boolean result = false;
		OnlineUserService onlineUserService = ServiceLocator.getOnlineUserService();
		String remoteIp = this.getIpAddr(ServletActionContext.getRequest());
		String name = SystemConfig.getProperty("background.test.enterGame.username");
		String password = SystemConfig.getProperty("background.test.enterGame.password");
		try {
			LoginInfo logInfo = onlineUserService.servLoginAuth(name, password, remoteIp);
			if(logInfo != null && logInfo.getUser() != null && "success".equals(logInfo.getRespFlag())) {
				long nowTime = new Date().getTime();
				String userInfo = logInfo.getUser().getId() + "," + logInfo.getUser().getUserName() + "," + nowTime;
				String sign = CookieUtil.buildMD5Cookie(String.valueOf(logInfo.getUser().getId()), logInfo.getUser().getUserName(), String.valueOf(nowTime));
				String cookieUserInfo = userInfo + "," + sign;
				HttpServletResponse response = ServletActionContext.getResponse();
				CookieUtil.addCookie(response, SystemConfig.getProperty("cookie.name"), cookieUserInfo, 0);
				result = true;
			}
		}catch (Exception e) {
			logger.error("default login Error:" + e.getMessage(), e);
		}
		return result;
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
	/*后台进入游戏结束*/
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}
	@Override
	public void setRequest(Map<String, Object> request) {
		this.requestMap = request;
	}
	public PageInfo getPager() {
		return pager;
	}
	public void setPager(PageInfo pager) {
		this.pager = pager;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public PlatformGameServer getGameServer() {
		return gameServer;
	}

	public void setGameServer(PlatformGameServer gameServer) {
		this.gameServer = gameServer;
	}
	
	//格式化开区时间
	private void formatServerTime(List<PlatformGameServer> serverList) {
		if(serverList != null && serverList.size() > 0) {
			for (PlatformGameServer server : serverList) {
				String openingTime = server.getOpeningTime();
				if(StringUtils.isNumeric(openingTime)){
					String OpeningTimeStr = DateUtil.format(new Date(Long.parseLong(openingTime)));
					server.setOpeningTime(OpeningTimeStr);
				}
			}
		}
	}

	//构造服务区状态下拉列表
	public List<GameServerStatus> getStatusList() throws Exception {
		List<GameServerStatus> statusList = new ArrayList<GameServerStatus>();
		String basePath = this.getRequest().getRealPath("/");
		Map<String, GameServerStatus> statusMap = gameServerServ.getStatusMap(basePath);
		Iterator iter = statusMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry entry = (Entry) iter.next();
			GameServerStatus serverStatus = (GameServerStatus) entry.getValue();
			if(serverStatus.getId() != -1){
				statusList.add(serverStatus);
			}
		}
		
		return statusList;
	}

	//得到服务器根目录
	public static String getBasePath(){
		return getRequest().getRealPath("/");
	}

	public void setStatusList(List<GameServerStatus> statusList) {
		this.statusList = statusList;
	}
	
	public Map<String, GameServerStatus> getStatusMap() throws Exception {
		String basePath = this.getRequest().getRealPath("/");
		Map<String, GameServerStatus> statusMap = gameServerServ.getStatusMap(basePath);
		return statusMap;
	}

	public void setStatusMap(Map<String, GameServerStatus> statusMap) {
		this.statusMap = statusMap;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getIsDesc() {
		return isDesc;
	}

	public void setIsDesc(String isDesc) {
		this.isDesc = isDesc;
	}

	public String getStopServer() {
		return stopServer;
	}

	public void setStopServer(String stopServer) {
		this.stopServer = stopServer;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public static HttpServletRequest getRequest() {
		ActionContext ctx = ActionContext.getContext();       
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getServerIds() {
		return serverIds;
	}

	public void setServerIds(String serverIds) {
		this.serverIds = serverIds;
	}

	public StopGameServerInfo getStopServerInfo() {
		return stopServerInfo;
	}

	public void setStopServerInfo(StopGameServerInfo stopServerInfo) {
		this.stopServerInfo = stopServerInfo;
	}

	public String getGeneServerHtmlStatus() {
		return geneServerHtmlStatus;
	}

	public void setGeneServerHtmlStatus(String geneServerHtmlStatus) {
		this.geneServerHtmlStatus = geneServerHtmlStatus;
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

	public String getGameUrl() {
		return gameUrl;
	}

	public void setGameUrl(String gameUrl) {
		this.gameUrl = gameUrl;
	}
	
}
