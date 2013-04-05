package com.sz7road.web.model.newsmanage;

import java.util.Date;

public class News {
	public static final int STATE_BOTTOM = 1;
	public static final int STATE_COMMON = 2;
	public static final int STATE_HOT = 3;
	public static final int STATE_TOP = 4;
	public static final int STATE_HIDEN = 5;
	public static final int STATE_HEADLINE = 6;

	public static final int TYPE_NEWS = 1;
	public static final int TYPE_BULLETIN = 2;
	public static final int TYPE_ACTIVITY = 3;
	public static final int TYPE_MEDIA = 4;
	public static final int TYPE_BLUEPOST = 5;

	private int newsId;
	
	private int typeId;
	
	private String newsContent;
	
	private int stateId;
	
	private String artTitle;
	
	private String exp1;
	
	private String exp2;
	
	private String exp3;
	
	private String exp4;
	
	private String exp5;
	
	private String exps;
	
	private int hits;
	
	private Date createDate;
	
	private String getData;
	
	private String stateName;
	
	private String typeName;
	
	private String typeShortName;
	
	private String introduction;//简介
	
	private String newsImg;
	
	private Date modifyDate;

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public String getArtTitle() {
		return artTitle;
	}

	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}
	

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	public String getExp2() {
		return exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

	public String getExp3() {
		return exp3;
	}

	public void setExp3(String exp3) {
		this.exp3 = exp3;
	}

	public String getExp4() {
		return exp4;
	}

	public void setExp4(String exp4) {
		this.exp4 = exp4;
	}

	public String getExp5() {
		return exp5;
	}

	public void setExp5(String exp5) {
		this.exp5 = exp5;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getExps() {
		return exps;
	}

	public void setExps(String exps) {
		this.exps = exps;
	}

	public String getTypeShortName() {
		return typeShortName;
	}

	public void setTypeShortName(String typeShortName) {
		this.typeShortName = typeShortName;
	}

	public String getNewsImg() {
		return newsImg;
	}

	public void setNewsImg(String newsImg) {
		this.newsImg = newsImg;
	}

	public String getGetData() {
		return getData;
	}

	public void setGetData(String getData) {
		this.getData = getData;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}
