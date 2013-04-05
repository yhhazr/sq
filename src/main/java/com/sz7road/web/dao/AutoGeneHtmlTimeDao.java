package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;

public interface AutoGeneHtmlTimeDao {
	public List<AutoGeneHtmlTime> findAllAutoGeneHtmlTime() throws Exception;
	
	public boolean deleteAutoGeneHtmlTimeById(final int id) throws Exception;
	
	public boolean updateAutoGeneHtmlTimeById(final AutoGeneHtmlTime autoTime) throws Exception;
	
	public boolean insertAutoGeneHtmlTime(AutoGeneHtmlTime autoTime) throws Exception;
	

}
