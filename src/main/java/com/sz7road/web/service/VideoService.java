package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.model.vediomanage.VideoState;

public interface VideoService {
	
	
	public PaginationResult<VideoInfo> getAllVideoInfo(PageInfo pageInfo) throws Exception;
	
	public boolean deleteVideo(List<Integer> video_id) throws Exception;
	
	public VideoInfo findVideo(int video_id) throws Exception;
	
	public boolean editVideo(VideoInfo video_info) throws Exception;
	
	public boolean uploadVideo(VideoInfo video) throws Exception;
	
	public List<VideoState> selectVideoState() throws Exception; 
}
