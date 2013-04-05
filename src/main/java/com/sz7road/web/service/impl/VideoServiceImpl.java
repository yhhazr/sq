package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.VideoDao;
import com.sz7road.web.dao.impl.VideoDaoImpl;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.model.vediomanage.VideoState;
import com.sz7road.web.service.VideoService;

public class VideoServiceImpl implements VideoService{

	
	private static VideoServiceImpl _this;
	
	private static VideoDao videoDao;

	private VideoServiceImpl(){
		videoDao = VideoDaoImpl.getInstance();
	}
	
	@Override
	public PaginationResult<VideoInfo> getAllVideoInfo(PageInfo pageInfo) throws Exception {
		int total = videoDao.getVideoInfoCount();
		List<VideoInfo> videoInfoList = videoDao.findAllVideo(pageInfo);
		PaginationResult<VideoInfo> pageResult = new PaginationResult<VideoInfo>(total, videoInfoList);

		return pageResult;
	}
	

	@Override
	public boolean deleteVideo(List<Integer> video_id) throws Exception {

		boolean flag = videoDao.deleteVideo(video_id);
		return flag;
	}

	@Override
	public boolean editVideo(VideoInfo video_info) throws Exception {
		videoDao.editVideoInfoById(video_info);
		return true;
	}

	@Override
	public VideoInfo findVideo(int video_id) throws Exception {
		VideoInfo info = videoDao.findVideo(video_id);
		return info;
	}

	@Override
	public boolean uploadVideo(VideoInfo video) throws Exception {
		boolean flag = false;
		int id = videoDao.uploadVideo(video);
		if(id > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public List<VideoState> selectVideoState() throws Exception {
		List<VideoState> list = videoDao.findVideoState();
		return list;
	}

	public static VideoService getInstance() {
		if (_this == null)
			_this = new VideoServiceImpl();
		return _this;
	}






}
