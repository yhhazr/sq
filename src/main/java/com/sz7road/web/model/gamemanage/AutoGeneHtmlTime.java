package com.sz7road.web.model.gamemanage;

public class AutoGeneHtmlTime {
	private int id;
	private String geneTime;
	private String geneType;

	public AutoGeneHtmlTime() {
		super();
	}
	
	public AutoGeneHtmlTime(String geneTime, String geneType) {
		super();
		this.geneTime = geneTime;
		this.geneType = geneType;
	}

	public AutoGeneHtmlTime(String geneTime) {
		super();
		this.geneTime = geneTime;
	}

	public AutoGeneHtmlTime(int id, String geneTime, String geneType) {
		super();
		this.id = id;
		this.geneTime = geneTime;
		this.geneType = geneType;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGeneTime() {
		return geneTime;
	}
	public void setGeneTime(String geneTime) {
		this.geneTime = geneTime;
	}
	public String getGeneType() {
		return geneType;
	}
	public void setGeneType(String geneType) {
		this.geneType = geneType;
	}
	
	

}
