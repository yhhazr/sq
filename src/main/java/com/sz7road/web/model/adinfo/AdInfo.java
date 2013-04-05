package com.sz7road.web.model.adinfo;

public class AdInfo {

	private int id;
	private int adId;
	private String site;
	private int enterTimes;
	private int regTimes;
	private String date;
	private boolean isChange;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public int getEnterTimes() {
		return enterTimes;
	}
	public void setEnterTimes(int enterTimes) {
		this.enterTimes = enterTimes;
	}
	public int getRegTimes() {
		return regTimes;
	}
	public void setRegTimes(int regTimes) {
		this.regTimes = regTimes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isChange() {
		return isChange;
	}
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}
	
}
