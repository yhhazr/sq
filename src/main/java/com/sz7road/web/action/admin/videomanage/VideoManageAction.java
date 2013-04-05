package com.sz7road.web.action.admin.videomanage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.model.vediomanage.VideoState;
import com.sz7road.web.service.VideoService;

public class VideoManageAction extends ActionSupport implements RequestAware, SessionAware, ServletContextAware{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	private List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
	private static VideoService videoService = ServiceLocator.getVideoService();
	
	private String videoId;
	private VideoInfo videoInfo;
	
	private static List<VideoState> videoStateList;
	
	private Map<String, Object> requestMap;
	@SuppressWarnings("unused")
	private Map<String, Object> sessionMap;
	private PageInfo pager;
	//文件上传
	private File uploadPath;
	private String uploadPathFileName;
	private String uploadPathContentType;
	private ServletContext context;
	
	private static String videoPicName = null;
	
	static {
		try {
			videoStateList = videoService.selectVideoState();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//新增视频
	public String addNewVideo(){
		try {
			videoStateList = videoService.selectVideoState();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	//视频列表
	public String getAllVideoInfoList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = INPUT;
		try {
			videoStateList = videoService.selectVideoState();
			PageInfo pageInfo = new PageInfo();
			int startRow = 0;
			String pagerOffset = request.getParameter("pager.offset");
			int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
			if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
				startRow = Integer.parseInt(pagerOffset);
			}
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			PaginationResult<VideoInfo> pageationResult = videoService.getAllVideoInfo(pageInfo);
		
			requestMap.put("pageationResult", pageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("video list Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	//视频编辑信息
	public String editVideoInfo() {
		String result = INPUT;
		int videoIdInt = 0;
		try {
			videoStateList = videoService.selectVideoState();
			if (!StringUtils.isBlank(videoId) && StringUtils.isNumeric(videoId)) {
				videoIdInt = Integer.parseInt(videoId);
			}
			this.getVideoInfoList();
			videoInfo = videoService.findVideo(videoIdInt);
			videoPicName = videoInfo.getVideoPicName();
			//requestMap.put("videoEditInfo", videoInfo);
			if (videoInfo != null && !StringUtils.isBlank(videoInfo.getVideoLink())) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("edit video info Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	
	//删除视频
	public String deleteVideoInfoSubmit(){
		//StringBuilder sb = new StringBuilder();
		String result = INPUT;
		List<Integer> list = new ArrayList<Integer>();
		String[] str = videoId.split(",");
		for(int i=0;i<str.length;i++){
			list.add(Integer.parseInt(str[i]));
		}
		try {
			videoService.deleteVideo(list);
			result = SUCCESS;
		} catch (Exception e) {
			logger.error("delete video Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	
	//视频编辑提交
	public String editVideoSubmit(){
		String result = INPUT;
		
		try {
			videoStateList = videoService.selectVideoState();
			VideoInfo info = new VideoInfo();
			info.setVideoId(videoInfo.getVideoId());
			info.setEnableFlag(videoInfo.isEnableFlag());
			info.setVideoLink(videoInfo.getVideoLink());
			info.setVideoTitle(videoInfo.getVideoTitle());
			info.setStateId(videoInfo.getStateId());
			info.setVideoType(videoInfo.isVideoType());
			if(uploadPath == null){
				info.setVideoPicName(videoPicName);
			}else{
				String realPath = context.getRealPath("/upload");
				UUID uuid = UUID.randomUUID();
				StringBuilder str = new StringBuilder();
				str.append(uuid.toString());
				str.append(uploadPathFileName.substring(uploadPathFileName.lastIndexOf(".")));
				String picName = str.toString();
				File target = new File(realPath, picName);
				File savefile = new File(SystemConfig.getProperty("server.image.realpath")+"/" + picName);
				info.setVideoPicName(picName);
				FileUtils.copyFile(uploadPath, target); 
				FileUtils.copyFile(uploadPath,savefile);
			}
			
			boolean flag = videoService.editVideo(info);
			if(flag){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
			
		} catch (Exception e) {
			result = INPUT;
			logger.error("edit video Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	//新增视频提交
	public String createVideoSubmit(){
		String result = INPUT;
		String realPath = context.getRealPath("/upload");
		UUID uuid = UUID.randomUUID();
		StringBuilder str = new StringBuilder();
		str.append(uuid.toString());
		str.append(uploadPathFileName.substring(uploadPathFileName.lastIndexOf(".")));
		String picName = str.toString();
		File target = new File(realPath, picName);
		File file = new File(realPath);
		if(!file.exists()){
			file.mkdir();
		}
		File savefile = new File(SystemConfig.getProperty("server.image.realpath")+ "/" + picName);
		try {
			videoStateList = videoService.selectVideoState();
			VideoInfo info = new VideoInfo();
			info.setEnableFlag(videoInfo.isEnableFlag());
			info.setVideoLink(videoInfo.getVideoLink());
			info.setVideoTitle(videoInfo.getVideoTitle());
			info.setStateId(videoInfo.getStateId());
			info.setVideoType(videoInfo.isVideoType());
			info.setVideoPicName(picName);
			boolean flag = videoService.uploadVideo(info);
			//logger.error(flag);
			if(flag){
				result = SUCCESS;
			}
			FileUtils.copyFile(uploadPath, target); 
			FileUtils.copyFile(uploadPath,savefile);
		} catch (Exception e) {
			result = INPUT;
			logger.error("add new video Error:" + e.getMessage(), e);
		}
		logger.error(result);
		return result;
	}
	
	
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.requestMap = request;
	}
	
	@Override
	public void setServletContext(ServletContext context) {
			this.context = context;
	}



	public String getUploadPathContentType() {
		return uploadPathContentType;
	}

	public void setUploadPathContentType(String uploadPathContentType) {
		this.uploadPathContentType = uploadPathContentType;
	}

	public File getUploadPath() {
		return uploadPath;
	}


	public void setUploadPath(File uploadPath) {
		this.uploadPath = uploadPath;
	}


	public ServletContext getContext() {
		return context;
	}


	public List<VideoState> getVideoStateList() {
		return videoStateList;
	}


	public void setVideoStateList(List<VideoState> videoStateList) {
		this.videoStateList = videoStateList;
	}

	public String getVideoId() {
		return videoId;
	}


	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}


	public VideoService getVideoService() {
		return videoService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	

	public String getUploadPathFileName() {
		return uploadPathFileName;
	}


	public void setUploadPathFileName(String uploadPathFileName) {
		this.uploadPathFileName = uploadPathFileName;
	}


	public PageInfo getPager() {
		return pager;
	}

	public void setPager(PageInfo pager) {
		this.pager = pager;
	}

	

	public List<VideoInfo> getVideoInfoList() {
		return videoInfoList;
	}

	public void setVideoInfoList(List<VideoInfo> videoInfoList) {
		this.videoInfoList = videoInfoList;
	}


	public VideoInfo getVideoInfo() {
		return videoInfo;
	}


	public void setVideoInfo(VideoInfo videoInfo) {
		this.videoInfo = videoInfo;
	}


	
	
}
