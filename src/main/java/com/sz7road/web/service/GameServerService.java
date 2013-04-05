package com.sz7road.web.service;

import java.util.List;
import java.util.Map;

import com.sz7road.web.model.gamemanage.GameServer;
import com.sz7road.web.model.gamemanage.GameServerAllInfo;
import com.sz7road.web.model.gamemanage.GameServerStatus;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.gamemanage.PlatformResponse;
import com.sz7road.web.model.gamemanage.RoleRankInfo;
import com.sz7road.web.model.gamemanage.StopGameServerInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

public interface GameServerService {
	public PaginationResult<GameServerAllInfo> getAllGameServerByPage(PageInfo pageInfo, int sort, Map<String, String> queryMap) throws Exception; 
	public List<PlatformGameServer> getAllGameServer() throws Exception;
	public List<PlatformGameServer> getAllGameServerForBackground() throws Exception;
	public PlatformGameServer getGameServerById(int id) throws Exception;
	public PlatformResponse updateGameServer(PlatformGameServer server) throws Exception;
	public PlatformResponse addGameServer(PlatformGameServer server) throws Exception;
	public PlatformResponse removeGameServerById(String id) throws Exception;
	public PlatformResponse updateServerStatusByIds(String statusId, String ids) throws Exception;
	public boolean stopGameServerByIds(StopGameServerInfo stopServerInfo, String serverIds) throws Exception;
	public PlatformResponse updateAllServerStatus(int stop, String statusId) throws Exception;
	public Map<String, GameServerStatus> getStatusMap(String basePath) throws Exception;
	public List<StopGameServerInfo> getStopServerInfo(String ServerIdList) throws Exception;
	public List<RoleRankInfo> getRoleRankInfoListAsc() throws Exception;
	public List<PlatformGameServer> buildGameServerListAsc() throws Exception;
	public List<PlatformGameServer> buildGameServerListDesc() throws Exception;
	public List<PlatformGameServer> getPlayerRankServerList() throws Exception;
	
	public boolean removeStopInfo(String[] id) throws Exception;
	

	

}