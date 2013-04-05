package com.sz7road.web.service;

import java.util.Map;


public interface GeneNewsPageService {
	
	public boolean hasMoreNews();
	
	public Map<String, Object> getConcreteNewsPageData() throws Exception;
	
	public boolean hasMoreNewsList();
	
	public Map<String, Object> getNewsListPageData() throws Exception;
	
}
