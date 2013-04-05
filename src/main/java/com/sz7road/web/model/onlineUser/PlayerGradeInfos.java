package com.sz7road.web.model.onlineUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页玩家排行  玩家实体类
 * 创建时间  2012-10-11
 * @author john.jiang
 *
 */
public class PlayerGradeInfos {
	
	private int serverId;
	private List<PlayerGradeInfo> infoList = new ArrayList<PlayerGradeInfo>();
	
	public PlayerGradeInfos() {
	}

	public PlayerGradeInfos(int serverId, List<PlayerGradeInfo> infoList) {
		super();
		this.serverId = serverId;
		this.infoList = infoList;
	}

	public static class PlayerGradeInfo {
		
		private String nickName;
		private String grades;
		private String fightCapacity;
		
		public PlayerGradeInfo(){}

		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getGrades() {
			return grades;
		}
		public void setGrades(String grades) {
			this.grades = grades;
		}

		public String getFightCapacity() {
			return fightCapacity;
		}

		public void setFightCapacity(String fightCapacity) {
			this.fightCapacity = fightCapacity;
		}
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public List<PlayerGradeInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<PlayerGradeInfo> infoList) {
		this.infoList = infoList;
	}


	
	

	
}
