package com.sz7road.web.service;

import java.util.List;

import com.sz7road.web.model.gamemanage.AutoGeneHtmlTime;

public interface AutoGeneHtmlTimeService {
	public List<AutoGeneHtmlTime> getAllAutoGeneHtmlTime() throws Exception;
	
	public boolean removeAutoGeneHtmlTimeById(final int id) throws Exception;
	
	public boolean editAutoGeneHtmlTimeById(final AutoGeneHtmlTime autoTime) throws Exception;
	
	public boolean createAutoGeneHtmlTime(AutoGeneHtmlTime autoTime) throws Exception;

}
