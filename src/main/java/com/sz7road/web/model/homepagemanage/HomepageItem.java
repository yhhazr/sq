package com.sz7road.web.model.homepagemanage;

import java.util.Date;

public class HomepageItem {
//	public static final String POSITION_VIDEO = "video";
//	public static final String POSITION_WALLPAPER = "wallpaper";
//	public static final String POSITION_SCREENSHOT = "screenshot";
//	public static final String POSITION_PAINT = "paint";
//	public static final String POSITION_CARTOON = "cartoon";
	public static final String POSITION_MEDIA = "media";
	public static final String POSITION_ACTIVITY = "activity";
	public static final String POSITION_CAROUSEL = "carouselImages";
	public static final String POSITION_NEWEST_NEWS = "news";

	public static final int TYPE_FLASH = 1;
	public static final int TYPE_VIDEO = 2;
	public static final int TYPE_FONT = 3;
	public static final int TYPE_PICTURE = 4;
	public static final int TYPE_FONT_AND_PICTURE = 5;
	public static final int TYPE_START_GAME_FLASH = 6;
	public static final int TYPE_START_GAME_PICTURE = 7;

	private int id;
	
	private String name;
	
	private String url;
	
	private int typeId;
	
	private String content;
	
	private String position;
	
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}	
}
