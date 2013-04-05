package com.sz7road.web.model.vediomanage;

public class VideoInfoEx extends VideoInfo{

	private String videoTypeName;
	private String enableFlagName;
	private String stateName;
	
	public String getEnableFlagName() {
		return enableFlagName;
	}
	public void setEnableFlagName(String enableFlagName) {
		this.enableFlagName = enableFlagName;
	}
	public String getVideoTypeName() {
		return videoTypeName;
	}
	public void setVideoTypeName(String videoTypeName) {
		this.videoTypeName = videoTypeName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
