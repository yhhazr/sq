package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.model.onlineUser.GuildInfos;
import com.sz7road.web.common.util.PlayerRankBuilder;
import com.sz7road.web.model.gamemanage.RoleRankInfo;
import com.sz7road.web.model.onlineUser.PlayerGradeInfos;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneNewsPageService;
import com.sz7road.web.service.GenePlayerRankService;

public class GenePlayerRankServiceImpl implements GenePlayerRankService {
	
	private static final Logger logger = LogManager.getLogger(GenePlayerRankServiceImpl.class);
	
	private static GenePlayerRankService _this;
	
	public synchronized static GenePlayerRankService getInstance() {
		if (_this == null)
			_this = new GenePlayerRankServiceImpl();
		return _this;
	}
	
	//生成所有服务区的数据
	@Override
	public Map<String, Map<String, Object>> getPlayerRankData() {
		Map<String, Map<String, Object>> dataMap = new HashMap<String, Map<String, Object>>();
		
		GameServerService gameServerService = GameServerServiceImpl.getInstance();
		try {
			List<RoleRankInfo> roleRankListAsc = gameServerService.getRoleRankInfoListAsc();
			if (roleRankListAsc != null) {
				for (RoleRankInfo roleRank : roleRankListAsc) {
					if (roleRank != null) {
						if (roleRank.getUrl() == null || "".equals(roleRank.getUrl())) {
							continue;
						}
						Map<String, Object> playerMap = new HashMap<String, Object>();
						playerMap.put("guildGradeRank", getGuildGradeRankPage(roleRank));
						playerMap.put("playerGradeRank", getPlayerGradeRankPage(roleRank));
						playerMap.put("playerFightRankPage", getPlayerFightRankPage(roleRank));
						playerMap.put("constainFightRankPage", getConstainFightRankPage(roleRank));
						dataMap.put(String.valueOf(roleRank.getId()), playerMap);
					}
				}
			}
		} catch (Exception e) {
			logger.error("连接平台获得玩家排行接口地址异常", e);
		}
		
		return dataMap;
	}
	
	//生成玩家等级排行页
	private PlayerGradeInfos getPlayerGradeRankPage(RoleRankInfo roleRank) {
		PlayerGradeInfos infos = null;
		String url = roleRank.getUrl();
		int serverId = roleRank.getId();
		try {
			List<PlayerGradeInfos.PlayerGradeInfo> diList = PlayerRankBuilder.getDallyInfo(url);
			infos = new PlayerGradeInfos(serverId, diList);
		} catch (Exception e) {
			logger.error("生成玩家等级排行页错误。服务区：" + serverId, e);
		}
		
		return infos;
	}
	
	//生成公会等级排行页
	private GuildInfos getGuildGradeRankPage(RoleRankInfo roleRank) {
		GuildInfos infos = null;
		String originalUrl = roleRank.getUrl();
		String url = originalUrl.substring(0, originalUrl.lastIndexOf("/")) + "/constain_unzip.xml";
		int serverId = roleRank.getId();
		try {
			List<GuildInfos.GuildInfo> diList = PlayerRankBuilder.getGuildGradeInfo(url);
			infos = new GuildInfos(serverId, diList);
		} catch (Exception e) {
			logger.error("生成公会等级排行页错误。服务区：" + serverId, e);
		}
		
		return infos;
	}
	
	//生成玩家战斗力排行页
	private PlayerGradeInfos getPlayerFightRankPage(RoleRankInfo roleRank) {
		PlayerGradeInfos infos = null;
		String originalUrl = roleRank.getUrl();
		String url = originalUrl.substring(0, originalUrl.lastIndexOf("/")) + "/fight_unzip.xml";
		int serverId = roleRank.getId();
		try {
			List<PlayerGradeInfos.PlayerGradeInfo> diList  = PlayerRankBuilder.getPlayerFightInfo(url);
			infos = new PlayerGradeInfos(serverId, diList);
		} catch (Exception e) {
			logger.error("生成玩家战斗力排行页错误。服务区：" + serverId, e);
		}
		
		return infos;
	}
	
	//生成公会战斗力排行页
	private GuildInfos getConstainFightRankPage(RoleRankInfo roleRank) {
		GuildInfos infos = null;
		String originalUrl = roleRank.getUrl();
		String url = originalUrl.substring(0, originalUrl.lastIndexOf("/")) + "/constainfight_unzip.xml";
		int serverId = roleRank.getId();
		List<GuildInfos.GuildInfo> diList = new ArrayList<GuildInfos.GuildInfo>();
		try {
			diList = PlayerRankBuilder.getGuildFightInfo(url);
			infos = new GuildInfos(serverId, diList);
		} catch (Exception e) {
			logger.error("获得公会战斗力排行数据错误。服务区：" + serverId, e);
		}
		
		return infos;
	}
	
}
