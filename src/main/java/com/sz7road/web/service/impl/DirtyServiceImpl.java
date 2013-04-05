package com.sz7road.web.service.impl;

import java.util.Map;

import com.sz7road.web.dao.DirtyDao;
import com.sz7road.web.dao.impl.DirtyDaoImpl;
import com.sz7road.web.service.DirtyService;

public class DirtyServiceImpl implements DirtyService{

	private static DirtyDao dirtyDao;
	private static DirtyServiceImpl _instance;
	static {
		dirtyDao = DirtyDaoImpl.getInstance();
	}
	public synchronized static DirtyServiceImpl getInstance(){
		if(_instance == null){
			_instance = new DirtyServiceImpl();
		}
		return _instance;
	}
	
	@Override
	public boolean writeDirty(String s) throws Exception {
		boolean flag = dirtyDao.writeDirty(s);
		return flag;
	}

	@Override
	public Map<Integer, String> getDirty() throws Exception {
		Map<Integer,String> map = dirtyDao.getDirty();
		return map;
	}

}
