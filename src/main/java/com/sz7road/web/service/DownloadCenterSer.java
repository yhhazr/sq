package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.model.vediomanage.VideoInfo;

public interface DownloadCenterSer {
	public PaginationResult<Photo> getPhotos(PageInfo page, int id) throws Exception;
	
	public List<PhotoCat> getCats() throws Exception;
	
	public PaginationResult<VideoInfo> getVideos(PageInfo page) throws Exception;
	
	public int getCount(int id) throws Exception;
}
