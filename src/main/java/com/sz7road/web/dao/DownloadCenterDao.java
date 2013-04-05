package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.model.vediomanage.VideoInfo;

public interface DownloadCenterDao {
	public List<Photo> getPhotos(PageInfo page, int id) throws Exception;
	
	public List<PhotoCat> getCats() throws Exception;
	
	public List<VideoInfo> getVideos(PageInfo page) throws Exception;
	
	public int getCount(int id) throws Exception;
}
