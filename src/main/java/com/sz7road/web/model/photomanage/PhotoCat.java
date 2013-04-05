package com.sz7road.web.model.photomanage;

import java.util.Date;

public class PhotoCat {

	private int catId;
	
	private String catName;
	
	private String catDesc;
	
	private boolean homepageShow;
	
	private String imageUrl;
	
	private Date createDate;

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public boolean isHomepageShow() {
		return homepageShow;
	}

	public void setHomepageShow(boolean homepageShow) {
		this.homepageShow = homepageShow;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
