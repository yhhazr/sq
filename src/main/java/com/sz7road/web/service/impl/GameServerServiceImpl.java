package com.sz7road.web.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.action.admin.testmanage.EnterGameTestAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.JsonUtil;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.dao.GameServerStatusDao;
import com.sz7road.web.dao.impl.GameServerStatusDaoImpl;
import com.sz7road.web.model.gamemanage.GameServerAllInfo;
import com.sz7road.web.model.gamemanage.GameServerStatus;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.gamemanage.PlatformResponse;
import com.sz7road.web.model.gamemanage.RoleRankInfo;
import com.sz7road.web.model.gamemanage.StopGameServerInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameServerService;

public class GameServerServiceImpl implements GameServerService {
	private static final Logger logger = LogManager.getLogger(GameServerServiceImpl.class);
	private static GameServerServiceImpl _this;
	private static PlatformClient client;
	private Map<String, GameServerStatus> statusMap;
	
	private GameServerServiceImpl() {
		client = PlatformClient.getInstance();
	}

	public synchronized static GameServerServiceImpl getInstance() {
		if (_this == null)
			_this = new GameServerServiceImpl();
		return _this;
	}
	
	//分页查询
	@Override
	public PaginationResult<GameServerAllInfo> getAllGameServerByPage(PageInfo pageInfo, int sort, Map<String, String> queryMap) {
		PaginationResult<GameServerAllInfo> page = null;
		int total = 0;
		List<Integer> totalList = new ArrayList<Integer>();
		List<PlatformGameServer> serverList = new ArrayList<PlatformGameServer>();
		try {
			serverList = client.findGameServer(pageInfo, sort, queryMap, totalList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<StopGameServerInfo> stopInfoList = new ArrayList<StopGameServerInfo>();
		try {
			stopInfoList = this.getStopServerInfoList(serverList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<GameServerAllInfo> allInfoList = this.combineServerInfo(serverList, stopInfoList);
		if(totalList.size() == 0) 
			total = 0;
		else 
			total = totalList.get(0);
		page = new PaginationResult<GameServerAllInfo>(total, allInfoList);
		
		return page;
	}
	
	//得到当前页的维护信息list
	private List<StopGameServerInfo> getStopServerInfoList(List<PlatformGameServer> serverList) throws Exception {
		List<StopGameServerInfo> stopInfoList = null;
		StringBuffer idsBuffer = new StringBuffer();
		String ids = null;
		if (!(serverList == null || serverList.size() == 0)) {
			for (PlatformGameServer server : serverList) {
				idsBuffer.append("," + server.getId());
			}
			if (idsBuffer.length() > 0){
				ids = idsBuffer.toString().substring(1);
			}
			if (ids.length() > 0) {
				stopInfoList = client.findStopServerInfo("[" + ids + "]");
			}
		}
		
		return stopInfoList;
	}
	
	//匹配服务区信息和服务区维护信息
	private List<GameServerAllInfo> combineServerInfo(List<PlatformGameServer> serverList, List<StopGameServerInfo> stopInfoList) {
		List<GameServerAllInfo> allInfoList = new ArrayList<GameServerAllInfo>();
		if(!(serverList == null || serverList.size() == 0)) {
			for (PlatformGameServer server : serverList) {
				String pfId = server.getId();
				String enterKey = null;
				if(StringUtils.isNumeric(pfId)){
					enterKey = new EnterGameTestAction().buildRechargeTestSign(Integer.parseInt(pfId));
				}
				StopGameServerInfo stopGameInfo = new StopGameServerInfo();
				if (stopInfoList != null && stopInfoList.size() > 0) {
					for (int i = 0; i < stopInfoList.size(); i++) {
						StopGameServerInfo stopGameInfoTemp = stopInfoList.get(i);
						String stopServerId = stopGameInfoTemp.getServerId();
						if (StringUtils.isNumeric(stopServerId) && StringUtils.isNumeric(pfId) && stopServerId.equals(pfId)) {
							formatServerTime(stopGameInfoTemp);
							stopGameInfo = stopGameInfoTemp;
							stopInfoList.remove(i);
						}
					}	
				}
				allInfoList.add(new GameServerAllInfo(server, stopGameInfo, enterKey));
			}
		}
		
		return allInfoList;
	}
	
	//格式化维护信息的时间
	private void formatServerTime(StopGameServerInfo stopGameInfo) {
		String startTime = stopGameInfo.getStartTime();
		String endTime = stopGameInfo.getEndTime();
		if(StringUtils.isNumeric(startTime)){
			String startTimeStampStr = DateUtil.format(new Date(Long.parseLong(startTime)));
			stopGameInfo.setStartTime(startTimeStampStr);
		}
		if(StringUtils.isNumeric(endTime)){
			String endTimeStampStr = DateUtil.format(new Date(Long.parseLong(endTime)));
			stopGameInfo.setEndTime(endTimeStampStr);
		}
	}
	
	//全查(前台)
	@Override
	public List<PlatformGameServer> getAllGameServer() throws Exception {
		List<PlatformGameServer> serverList = client.findAllGameServer();
		long DELAY_TIME = 30 * 1000;
		//删除未到开启时间的服务区
        for (Iterator it = serverList.iterator(); it.hasNext();) {                 
        	PlatformGameServer server = (PlatformGameServer)it.next();  
        	String openingTimeStr = server.getOpeningTime();
			if(StringUtils.isNumeric(openingTimeStr)){
				long openingTime = Long.valueOf(openingTimeStr);
				long nowTime = new Date().getTime();
				if(openingTime > (nowTime + DELAY_TIME)){
					it.remove();
				}
			}else{
				it.remove();
			}
        }
		
		return serverList;
	}
	
	//全查（后台）
	public List<PlatformGameServer> getAllGameServerForBackground() throws Exception {
		//查询所有服务区
		PageInfo pageInfo = new PageInfo();
		pageInfo.setStartRow(1);
		pageInfo.setPageSize(9999);
		pageInfo.setOrder("id");
		pageInfo.setOffset("1");
		//组合查询
		Map<String, String> queryMap = new HashMap<String, String>();
		//排序
		int sort = 0;
		List<PlatformGameServer> serverList = new ArrayList<PlatformGameServer>();
		List<Integer> totalList = new ArrayList<Integer>();
		serverList = client.findGameServer(pageInfo, sort, queryMap, totalList);
		
		return serverList;
	}
	
	//根据Id查询一条
	@Override 
	public PlatformGameServer getGameServerById(int id) throws Exception {
		PlatformGameServer server = null;
		String jsonStr = "";
//		jsonStr = "{\"id\":9,\"serverNo\":9,\"serverName\":\"server9\",\"gameId\":1, \"serverStatus\":2,\"recommand\":true, \"openingTime\":1340011268877, \"createTime\":1330011268877}";
		PageInfo page = new PageInfo(0, 1);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("id", String.valueOf(id));
		List<Integer> total = new ArrayList<Integer>();
		List<PlatformGameServer> serverList = client.findGameServer(page, 1, queryMap, total);
		if(serverList != null && serverList.size() == 1){
			server = serverList.get(0);
		}
		
		return server;
	}

	//修改一条
	@Override
	public PlatformResponse updateGameServer(PlatformGameServer server) throws Exception {
		PlatformResponse resp = null;
		String respStr = client.editGameServer(server);
		if(respStr != null){
			resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
		}
		
		return resp;
	}
	

	//批量新增
	@Override
	public PlatformResponse addGameServer(PlatformGameServer server) throws Exception {
		PlatformResponse resp = null;
		String respStr = client.addGameServer(server);
		if(respStr != null){
			resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
		}
		return resp;
	}

	
	//批量修改服务区状态
	@Override
	public PlatformResponse updateServerStatusByIds(String statusId, String ids) throws Exception {
		PlatformResponse resp = null;
		String respStr = client.editGameServerStatusByIds(statusId, ids);
		if(respStr != null){
			resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
		}

		return resp;
	}

	//批量停服
	@Override
	public boolean stopGameServerByIds(StopGameServerInfo stopServerInfo, String serverIds) throws Exception {
		String startTime = stopServerInfo.getStartTime();
		String endTime = stopServerInfo.getEndTime();
		String startTimeStampStr = String.valueOf(DateUtil.parse(startTime).getTime());
		String endTimeStampStr = String.valueOf(DateUtil.parse(endTime).getTime());
		stopServerInfo.setStartTime(startTimeStampStr);
		stopServerInfo.setEndTime(endTimeStampStr);
		
		return client.stopGameServerByIds(stopServerInfo, serverIds);
	}

	//全区开服
	@Override
	public PlatformResponse updateAllServerStatus(int stop, String statusId) throws Exception {
		PlatformResponse resp = null;

		if(StringUtils.isNotBlank(statusId) && StringUtils.isNumeric(statusId)){
			String respStr = client.editAllGameServerStatus(stop, statusId);
			if(respStr != null){
				resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
			}
		}
		
		return resp;
	}
	
	//查询停服信息
	public List<StopGameServerInfo> getStopServerInfo(String ServerIdList) throws Exception{
		List<StopGameServerInfo> stopServerInfoList = client.findStopServerInfo(ServerIdList);
		if(stopServerInfoList != null && stopServerInfoList.size() > 0){
			for (StopGameServerInfo stopGameServerInfo : stopServerInfoList) {
				String startTimeStampStr = stopGameServerInfo.getStartTime();
				String endTimeStampStr = stopGameServerInfo.getEndTime();
				if(StringUtils.isNumeric(startTimeStampStr)){
					String startTimeFormat = DateUtil.format(new Date(Long.parseLong(startTimeStampStr)));
					stopGameServerInfo.setStartTime(startTimeFormat);
				}
				if(StringUtils.isNumeric(endTimeStampStr)){
					String endTimeFormat = DateUtil.format(new Date(Long.parseLong(endTimeStampStr)));
					stopGameServerInfo.setEndTime(endTimeFormat);
				}
			}
		}
		
		return stopServerInfoList;
	}
	
	//删除维护信息
	public boolean removeStopInfo(String[] id) throws Exception {
		boolean isRemoved = false;
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < id.length; i++) {
			sb.append(id[i]).append(",");
		}
		String ids = sb.substring(0, sb.length() - 1);
		ids += "]";
		String respStr = client.removeStopInfo(ids);
		PlatformResponse resp = null;
		if(respStr != null){
			resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
		}
		if (resp != null && resp.getCode() == 200) {
			isRemoved = true;
		}
		
		return isRemoved;
	}
	
	//删除一条
	@Override
	public PlatformResponse removeGameServerById(String id) throws Exception {
		PlatformResponse resp = null;
		String respStr = client.editGameServerStatusByIds("-1", "[" + id + "]");
		if(respStr != null){
			resp = JsonUtil.jsonStrToBean(respStr, PlatformResponse.class);
		}

		return resp;
	}
	
	//取得玩家排行url
	public List<RoleRankInfo> getRoleRankInfoListAsc() throws Exception{

		return client.findRoleRankList();
	}

	//创建GameStatus对象map
	public Map<String, GameServerStatus> getStatusMap(String basePath) throws Exception {
		statusMap = new HashMap<String, GameServerStatus>();
		GameServerStatusDao gameServerStatusDao = GameServerStatusDaoImpl.getInstance();
		List<GameServerStatus> statusList = gameServerStatusDao.findAllGameServerStatus();
		for (GameServerStatus status : statusList) {
			String statusId = status.getStatusId();
			statusMap.put(statusId, status);
		}
		
//        Properties prep = new Properties();
//        try{
//        	prep.load(new FileInputStream(new File(basePath + "/" + AppConstants.SERVLET_CONTEXT_ATTRIBUTE_GAMESERVER_STATUS_RESOURCES_PROPS_FILENAME)));
//        }catch(Exception e){
//        	logger.error("load properties error in build map", e);
//        }
//        Iterator itr = prep.entrySet().iterator();
//        while (itr.hasNext()){
//            Entry e = (Entry)itr.next();
//            statusMap.put(String.valueOf(e.getKey()), new GameServerStatus(Integer.parseInt(String.valueOf(e.getKey())), String.valueOf(e.getValue())));
//        }
		return statusMap;
	}
	
	
	//得到玩家排行服务器列表（没有玩家排行的不显示）
	@Override
	public List<PlatformGameServer> getPlayerRankServerList() throws Exception {
		List<PlatformGameServer> serverList = buildGameServerListDesc();
		List<PlatformGameServer> result = new ArrayList<PlatformGameServer>();
		for (int i = 0; i < serverList.size(); i++) {
			String htmlFileName = serverList.get(i).getId() + ".html";
			File file = new File(SystemConfig.getProperty("server.html.path") + "/player/" + htmlFileName);
			if(file.exists()){
				result.add(serverList.get(i));
			}
		}
		return result;
	}
	
	//服务器列表资源（倒序）
	@Override
	public List<PlatformGameServer> buildGameServerListDesc() throws Exception {
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		List<PlatformGameServer> serverListAsc = this.buildGameServerListAsc();
		for (int i = serverListAsc.size() - 1; i >= 0; i--) {
			serverListDesc.add(serverListAsc.get(i));
		}
		
		return serverListDesc;
	}
	
	//服务器列表资源(正序)
	@Override
	public List<PlatformGameServer> buildGameServerListAsc() throws Exception {
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = getAllGameServer();
			Collections.sort(serverListAsc, new Comparator<PlatformGameServer>() {
				public int compare(PlatformGameServer server0,PlatformGameServer server1) {
					return new Integer(server0.getServerNo()).compareTo(new Integer(server1.getServerNo()));
				}
			});
		} catch (Exception e) {
			logger.error("build game server list error.", e);
			throw e;
		}

		return serverListAsc;
	}
	
	
	
	
	
	public void setStatusMap(Map<String, GameServerStatus> statusMap) {

		this.statusMap = statusMap;
	}

	

	
}
