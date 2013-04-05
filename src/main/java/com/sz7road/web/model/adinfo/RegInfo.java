package com.sz7road.web.model.adinfo;

import java.util.Date;

public class RegInfo {

	private int id;
	private int useId;
	private String userName;
	private int adId;
	private Date regTime;
	private Date clickTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUseId() {
		return useId;
	}
	public void setUseId(int useId) {
		this.useId = useId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Date getClickTime() {
		return clickTime;
	}
	public void setClickTime(Date clickTime) {
		this.clickTime = clickTime;
	}
	
}
