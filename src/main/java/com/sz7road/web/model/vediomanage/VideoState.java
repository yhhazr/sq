package com.sz7road.web.model.vediomanage;

public class VideoState {
	private int stateId;
	private String stateName;
	
	public VideoState(){
		
	}
	public VideoState(int stateId,String statName){
		this.stateId = stateId;
		this.stateName = statName;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	

	
}
