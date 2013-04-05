package com.sz7road.web.model.photomanage;

public class Photo {
	
	public static int CATEGORY_PAINT = 90;
	public static int CATEGORY_WALLPAPER = 91;
	public static int CATEGORY_VIDEO = 92;
	public static int CATEGORY_CARTOON = 93;
	public static int CATEGORY_PLAYER_PHOTO = 94;

	private int photoId;
	
	private int catId;
	
	private String catName;
	
	private String photoName;
	
	private String photoShow;
	
	private String photoShowChina;
	
	private String thumbnail;
	
	private String title;

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoShow() {
		return photoShow;
	}

	public void setPhotoShow(String photoShow) {
		this.photoShow = photoShow;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getPhotoShowChina() {
		return photoShowChina;
	}

	public void setPhotoShowChina(String photoShowChina) {
		this.photoShowChina = photoShowChina;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
}
