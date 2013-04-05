package com.sz7road.web.service.impl;

import java.util.List;

import com.sz7road.web.dao.AutoGeneHtmlTimeDao;
import com.sz7road.web.dao.GameDateDao;
import com.sz7road.web.dao.impl.AutoGeneHtmlTimeDaoImpl;
import com.sz7road.web.dao.impl.GameDateDaoImpl;
import com.sz7road.web.dao.impl.GameServerStatusDaoImpl;
import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;
import com.sz7road.web.service.AutoGeneHtmlTimeService;

public class AutoGeneHtmlTimeServiceImpl implements AutoGeneHtmlTimeService {
	private static AutoGeneHtmlTimeServiceImpl instance;
	
	private static AutoGeneHtmlTimeDao autoTimeDao;
	
	private AutoGeneHtmlTimeServiceImpl() {
		autoTimeDao = AutoGeneHtmlTimeDaoImpl.getInstance();
	}

	public synchronized static AutoGeneHtmlTimeServiceImpl getInstance() {
		if (instance == null) instance = new AutoGeneHtmlTimeServiceImpl();
		return instance;
	}

	@Override
	public List<AutoGeneHtmlTime> getAllAutoGeneHtmlTime() throws Exception {
		
		return autoTimeDao.findAllAutoGeneHtmlTime();
	}

	@Override
	public boolean removeAutoGeneHtmlTimeById(int id) throws Exception {

		return autoTimeDao.deleteAutoGeneHtmlTimeById(id);
	}

	@Override
	public boolean editAutoGeneHtmlTimeById(AutoGeneHtmlTime autoTime) throws Exception {

		return autoTimeDao.updateAutoGeneHtmlTimeById(autoTime);
	}

	@Override
	public boolean createAutoGeneHtmlTime(AutoGeneHtmlTime autoTime) throws Exception {
		
		return autoTimeDao.insertAutoGeneHtmlTime(autoTime);
	}



}
