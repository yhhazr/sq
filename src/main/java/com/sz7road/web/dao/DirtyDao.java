package com.sz7road.web.dao;

import java.util.Map;

public interface DirtyDao {

	public boolean writeDirty(String s) throws Exception;
	
	public Map<Integer,String> getDirty() throws Exception;
}
