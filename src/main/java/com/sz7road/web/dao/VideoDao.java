package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.model.vediomanage.VideoState;

public interface VideoDao {
	/**
	 * @param the path of the video
	 * @return fail(1)/success(0)*/
	public int uploadVideo(VideoInfo video) throws Exception;
	
	public boolean deleteVideo(List<Integer> IdList) throws Exception;
	
	public VideoInfo findVideo(int video_id) throws Exception;
	
	public int saveVideo(VideoInfo video) throws Exception;
	
	public List<VideoInfo> findAllVideo(PageInfo pageInfo) throws Exception;
	
	public List<VideoState> findVideoState() throws Exception;
	
	public int getVideoInfoCount() throws Exception;
	
	public int editVideoInfoById(VideoInfo videoInfo) throws Exception;

	public List<VideoInfo> selectAllVideoInfo() throws Exception;
}
