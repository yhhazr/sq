package com.sz7road.web.model.gamemanage;

public class GameAccountOrder {
	//流水号
	private String id;
	private int gameId;
	private String gameName;
	private int serverId;
	private String serverName;
	private String userName;
	private String userName1;
	public String getUserName1() {
		return userName1;
	}
	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}
	private String roleName;
	private int amount;
	private int otherAmount;
	private int gameAmount;
	
	public int getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(int otherAmount) {
		this.otherAmount = otherAmount;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getGameAmount() {
		return gameAmount;
	}
	public void setGameAmount(int gameAmount) {
		this.gameAmount = gameAmount;
	}

}
