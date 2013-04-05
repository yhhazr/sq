package com.sz7road.web.model.gamemanage;

public class GameServerAllInfo {
	private PlatformGameServer server;
	private StopGameServerInfo stopInfo;
	private String enterKey;
	
	public GameServerAllInfo() {
	}
	
	public GameServerAllInfo(PlatformGameServer server,
			StopGameServerInfo stopInfo) {
		super();
		this.server = server;
		this.stopInfo = stopInfo;
	}
	public GameServerAllInfo(PlatformGameServer server,
			StopGameServerInfo stopInfo, String enterKey) {
		super();
		this.server = server;
		this.stopInfo = stopInfo;
		this.enterKey = enterKey;
	}

	public PlatformGameServer getServer() {
		return server;
	}
	public void setServer(PlatformGameServer server) {
		this.server = server;
	}
	public StopGameServerInfo getStopInfo() {
		return stopInfo;
	}
	public void setStopInfo(StopGameServerInfo stopInfo) {
		this.stopInfo = stopInfo;
	}

	public String getEnterKey() {
		return enterKey;
	}

	public void setEnterKey(String enterKey) {
		this.enterKey = enterKey;
	}
	
	

}
