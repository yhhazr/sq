package com.sz7road.web.service;

import java.util.Map;

public interface DirtyService {

	public boolean writeDirty(String s) throws Exception;
	
	public Map<Integer,String> getDirty() throws Exception;
}
