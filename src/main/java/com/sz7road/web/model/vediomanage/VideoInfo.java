package com.sz7road.web.model.vediomanage;

import java.util.Date;

public class VideoInfo {

	private int videoId;
	private int stateId;
	private String videoTitle;
	private String videoLink;
	private String videoPicName;
	private boolean videoType;
	private boolean enableFlag;
	private int top;
	private int step;
	private Date createDate;
	private Date lastUpdDate;
	
	public VideoInfo(){
		
	}
	
	
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getVideoTitle() {
		return videoTitle;
	}
	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoPicName() {
		return videoPicName;
	}
	public void setVideoPicName(String videoPicName) {
		this.videoPicName = videoPicName;
	}
	public boolean isVideoType() {
		return videoType;
	}
	public void setVideoType(boolean videoType) {
		this.videoType = videoType;
	}
	public boolean isEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(boolean enableFlag) {
		this.enableFlag = enableFlag;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}


	public String getVideoLink() {
		return videoLink;
	}


	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
	
	
	
	
}
