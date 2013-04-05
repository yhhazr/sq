package com.sz7road.web.model.onlineUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页玩家排行  公会实体类
 * 创建时间  2012-10-11
 * @author john.jiang
 *
 */
public class GuildInfos {
	
	private int serverId;
	private List<GuildInfo> infoList = new ArrayList<GuildInfo>(); 
	
	public GuildInfos() {
	}

	public GuildInfos(int serverId, List<GuildInfo> infoList) {
		super();
		this.serverId = serverId;
		this.infoList = infoList;
	}

	public static class GuildInfo {
		
		private String consortiaName;
		private String levels;
		private String fightPower;
		
		public GuildInfo() {}
		
		public String getConsortiaName() {
			return consortiaName;
		}
		public void setConsortiaName(String consortiaName) {
			this.consortiaName = consortiaName;
		}
		public String getLevels() {
			return levels;
		}
		public void setLevels(String levels) {
			this.levels = levels;
		}
		public String getFightPower() {
			return fightPower;
		}
		public void setFightPower(String fightPower) {
			this.fightPower = fightPower;
		}
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public List<GuildInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<GuildInfo> infoList) {
		this.infoList = infoList;
	}
	

}
