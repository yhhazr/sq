package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.dao.PhotoDao;
import com.sz7road.web.dao.VideoDao;
import com.sz7road.web.dao.impl.PhotoDaoImpl;
import com.sz7road.web.dao.impl.VideoDaoImpl;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GenePhotoPageService;

public class GenePhotoPageServiceImpl implements GenePhotoPageService {
	
	private static final Logger logger = LogManager.getLogger(GenePhotoPageServiceImpl.class);

	private static GenePhotoPageService _this;
	
	private static PhotoDao photoDao;
	private static VideoDao videoDao;
	private static GameServerService gameServerService;
	
	private List<PlatformGameServer> gameServerListDesc = new ArrayList<PlatformGameServer>();  
	
	private Photo photo = null;
	private List<Photo> concretePhotoList = new ArrayList<Photo>();
	private int concretePhotoIndex = 0;
	private boolean morePhoto = true;
	
	private List<PhotoCat> photoCatList = new ArrayList<PhotoCat>();
//	private List<Photo> photoListByCat = new ArrayList<Photo>();
	private int photoCatListIndex = 0;
	private int photoPageNumber = 0;
	private int photoPageCount = 0; 
	private boolean morePhotoList = true;
	private static final int PHOTO_PAGE_SIZE = 12;
	
//	private VideoInfo video = null;
	private List<VideoInfo> concreteVideoList = new ArrayList<VideoInfo>();
	private int concreteVideoIndex = 0;
	private boolean moreVideo = true;

//	private List<VideoInfo> videoList = new ArrayList<VideoInfo>();
	private int videoPageNumber = 0;
	private int videoPageCount = 0; 
	private boolean moreVideoList = true;
	private static final int VIDEO_PAGE_SIZE = 12;

	private static final int SERVER_SIZE = 5;
	
	private GenePhotoPageServiceImpl() {
		photoDao = PhotoDaoImpl.getInstance();
		videoDao = VideoDaoImpl.getInstance();
		gameServerService = GameServerServiceImpl.getInstance();
	}
	
	public synchronized static GenePhotoPageService getInstance() {
		if (_this == null)
			_this = new GenePhotoPageServiceImpl();
		return _this;
	}
	
	//判断生成具体图片页时是否还有新闻数据
	@Override
	public boolean hasMorePhoto() {
		boolean currentMorePhoto = morePhoto;
		if(!morePhoto) {
			morePhoto = true;
			concretePhotoList.clear();
			gameServerListDesc.clear();
		}
		
		return currentMorePhoto; 
	}
	
	//获得单个图片内页数据（action层循环调用） 
	@Override
	public Map<String, Object> getConcretePhotoPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(concretePhotoList.size() == 0) {
			try {
				concretePhotoList = photoDao.getPhotoListOnline();
			} catch (Exception e) {
				logger.error("生成图片内页数据异常。", e);
				throw e;
			}
			concretePhotoIndex = 0;
		}
		if(concretePhotoIndex <= (concretePhotoList.size() - 1)) {
			photo = concretePhotoList.get(concretePhotoIndex);
			dataMap.put("photo", photo);
			//生成所有内页下方图片列表
			int catId = photo.getCatId();
			List<Photo> photos = getAllPhotoByCatId(catId);
			dataMap.put("allPhotos", photos);
		}
		concretePhotoIndex ++;
		if(concretePhotoIndex >= concretePhotoList.size()) {
			concretePhotoIndex = 0;
			morePhoto = false;
		}
	
		return dataMap;
	}

	//判断生成具体视频页时是否还有新闻数据
	@Override
	public boolean hasMoreVideo() {
		boolean currentMoreVideo = moreVideo;
		if(!moreVideo) {
			moreVideo = true;
			concreteVideoList.clear();
			gameServerListDesc.clear();
		}
		
		return currentMoreVideo; 
	}
	
	//获得单个视频内页数据（action层循环调用） 
	@Override
	public Map<String, Object> getConcreteVideoPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(concreteVideoList.size() == 0) {
			try {
				concreteVideoList = videoDao.selectAllVideoInfo();
			} catch (Exception e) {
				logger.error("生成视频内页数据异常。", e);
				throw e;
			}
			concreteVideoIndex = 0;
		}
		if(concreteVideoIndex <= (concreteVideoList.size() - 1)) {
			VideoInfo video = concreteVideoList.get(concreteVideoIndex);
			dataMap.put("video", video);
			//生成所有视频内页下方列表
			List<VideoInfo> videos = getAllVideo();
			dataMap.put("allVideos", videos);
		}
		concreteVideoIndex ++;
		if(concreteVideoIndex >= concreteVideoList.size()) {
			concreteVideoIndex = 0;
			moreVideo = false;
		}
		
		return dataMap;
	}
	
	//判断生成图片列表页时是否还有图片数据
	@Override
	public boolean hasMorePhotoList() {
		boolean currentMorePhotoList = morePhotoList;
		if(!morePhotoList) {
			morePhotoList = true;
			photoCatList.clear();
			gameServerListDesc.clear();
		}
		
		return currentMorePhotoList; 
	}
	
	//获得单个图片列表页数据（action层循环调用） 
	@Override
	public Map<String, Object> getPhotoListPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(photoCatList.size() == 0) {
			photoCatList = getPhotoCats(); 
			photoCatListIndex = 0;
		}
		if(photoCatList.size() > 0) {
			int catId = photoCatList.get(photoCatListIndex).getCatId();
			if(photoPageCount == 0) {
				int newsCount = photoDao.getPhotoCountByCatOnline(catId);
				if (newsCount % PHOTO_PAGE_SIZE == 0) {
					photoPageCount = newsCount / PHOTO_PAGE_SIZE;
				} else {
					photoPageCount = newsCount / PHOTO_PAGE_SIZE + 1;
				}
			}
			List<Photo> photoListByCat = new ArrayList<Photo>();
			photoListByCat = photoDao.getPhotoByCatOnline(catId, new PageInfo(photoPageNumber * PHOTO_PAGE_SIZE, PHOTO_PAGE_SIZE));
			dataMap.put("photos", photoListByCat);
			dataMap.put("totalPage", photoPageCount);
			dataMap.put("currentPage", photoPageNumber + 1);
			dataMap.put("catId", catId);
			photoPageNumber ++;
			if(photoPageNumber >= photoPageCount) {
				photoPageCount = 0;
				photoPageNumber = 0;
				photoCatListIndex ++;
				if(photoCatList.size() <= photoCatListIndex) {
					photoCatListIndex = 0;
					morePhotoList = false;
				}
			}
		}
		
		return dataMap;
	}

	//判断生成视频列表页时是否还有图片数据
	@Override
	public boolean hasMoreVideoList() {
		boolean currentMorePhotoList = moreVideoList;
		if(!moreVideoList) {
			moreVideoList = true;
			gameServerListDesc.clear();
		}
		
		return currentMorePhotoList; 
	}
	
	//获得单个视频列表页数据（action层循环调用）
	@Override
	public Map<String, Object> getVideoListPageData() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(videoPageCount == 0) {
			int videoCount = videoDao.getVideoInfoCount();
			if (videoCount % VIDEO_PAGE_SIZE == 0) {
				videoPageCount = videoCount / VIDEO_PAGE_SIZE;
			} else {
				videoPageCount = videoCount / VIDEO_PAGE_SIZE + 1;
			}
		}
		List<VideoInfo> videoList = new ArrayList<VideoInfo>();
		videoList = videoDao.findAllVideo(new PageInfo(videoPageNumber * VIDEO_PAGE_SIZE, VIDEO_PAGE_SIZE));
		dataMap.put("videos", videoList);
		dataMap.put("totalPage", videoPageCount);
		dataMap.put("currentPage", videoPageNumber + 1);
		dataMap.put("catId", 0);
		videoPageNumber ++;
		if(videoPageNumber >= videoPageCount) {
			videoPageCount = 0;
			videoPageNumber = 0;
			moreVideoList = false;
		}
		
		return dataMap;
	}
	
	//获得图片类型列表
	private List<PhotoCat> getPhotoCats() throws Exception {
		List<PhotoCat> cats = new ArrayList<PhotoCat>();
		try {
			cats = photoDao.getPhotoCat();
		} catch (Exception e) {
			logger.error("获得图片类型列表异常", e);
			throw e;
		}
		
		return cats;
	}
	
	//生成玩家排行服务器列表
	private List<PlatformGameServer> getPlayerRankGameServerList() throws Exception {
		
		return gameServerService.getPlayerRankServerList();
	}
	
	//首页服务区列表（倒序）
	private List<PlatformGameServer> getGameServerList(int count) throws Exception {
		if(gameServerListDesc.size() == 0) {
			gameServerListDesc = gameServerService.buildGameServerListDesc();
		}
		List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
		if(gameServerListDesc.size() >= count) {
			serverListDesc = gameServerListDesc.subList(0, count);
		}
		
		return serverListDesc;
	}
	
	//根据cat获得所有图片数据列表
	public List<Photo> getAllPhotoByCatId(int catId) throws Exception {
		List<Photo> photos = new ArrayList<Photo>();
		try {
			photos = photoDao.getPhotoListOnline(catId);
		} catch (Exception e) {
			logger.error("根据类型获得全部图片异常。", e);
			throw e;
		}
		
		return photos;
	}

	//获得所有视频数据列表
	public List<VideoInfo> getAllVideo() throws Exception {
		List<VideoInfo> videos = new ArrayList<VideoInfo>();
		try {
			videos = videoDao.selectAllVideoInfo();
		} catch (Exception e) {
			logger.error("获得全部视频异常。", e);
			throw e;
		}
		
		return videos;
	}

}
