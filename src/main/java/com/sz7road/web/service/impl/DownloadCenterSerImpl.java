package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.DownloadCenterDao;
import com.sz7road.web.dao.VideoDao;
import com.sz7road.web.dao.impl.DownloadCenterDaoImpl;
import com.sz7road.web.dao.impl.VideoDaoImpl;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.service.DownloadCenterSer;

public class DownloadCenterSerImpl implements DownloadCenterSer{

	private static DownloadCenterDao downloadDao;
	
	private static DownloadCenterSerImpl _this;
	
	public synchronized static DownloadCenterSerImpl getInstance() {
		if (_this == null)
			_this = new DownloadCenterSerImpl();
		return _this;
	}
	public DownloadCenterSerImpl(){
		downloadDao = DownloadCenterDaoImpl.getInstence();
	}
	
	


	@Override
	public List<PhotoCat> getCats() throws Exception {
		List<PhotoCat> list = downloadDao.getCats();
		return list;
	}
	@Override
	public PaginationResult<Photo> getPhotos(PageInfo page, int id) throws Exception {
		int total = downloadDao.getCount(id);
		List<Photo> list = downloadDao.getPhotos(page, id);
		PaginationResult<Photo> pageResult = new PaginationResult<Photo>(total, list);
		return pageResult;
	}
	@Override
	public PaginationResult<VideoInfo> getVideos(PageInfo page) throws Exception {
		VideoDao vd = VideoDaoImpl.getInstance();
		int total = vd.getVideoInfoCount();
		List<VideoInfo> list = downloadDao.getVideos(page);
		PaginationResult<VideoInfo> pageResult = new PaginationResult<VideoInfo>(total, list);
		return pageResult;
	}
	@Override
	public int getCount(int id) throws Exception {
		
		return downloadDao.getCount(id);
	}
	

}
