package com.sz7road.web.model.gamemanage;

public class GameServerStatus {
	private int id;
	private String statusId;
	private String statusName;
	
	public GameServerStatus() {
	}
	
	public GameServerStatus(String statusId, String statusName) {
		super();
		this.statusId = statusId;
		this.statusName = statusName;
	}

	public GameServerStatus(int id, String statusId, String statusName) {
		super();
		this.id = id;
		this.statusId = statusId;
		this.statusName = statusName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	

	
}
