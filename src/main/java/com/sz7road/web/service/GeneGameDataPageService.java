package com.sz7road.web.service;

import java.util.Map;

public interface GeneGameDataPageService {
	
	public Map<String, Object> getGameDataListPageData() throws Exception;
	
	public boolean hasMoreGameData();
	
	public Map<String, Object> getConcreteGameDataPageData() throws Exception;
	
//	public Map<String, Object> getNewbGuidePageData() throws Exception;
	
}
