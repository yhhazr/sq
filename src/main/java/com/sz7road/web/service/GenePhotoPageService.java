package com.sz7road.web.service;

import java.util.Map;

public interface GenePhotoPageService {
	
	public boolean hasMorePhoto();
	
	public Map<String, Object> getConcretePhotoPageData() throws Exception;
	
	public boolean hasMorePhotoList();
	
	public Map<String, Object> getPhotoListPageData() throws Exception;
	
	
	
	public boolean hasMoreVideoList();
	
	public Map<String, Object> getVideoListPageData() throws Exception;
	
	public boolean hasMoreVideo();
	
	public Map<String, Object> getConcreteVideoPageData() throws Exception;
	
}
