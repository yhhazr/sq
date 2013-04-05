package com.sz7road.web.dao;

import com.sz7road.web.model.captcha.FunctionUsageLog;


public interface CaptchaDao {


	public void insertUserAttemptLog(FunctionUsageLog log) throws Exception;

	
	public int queryUserAttemptCnt(String identifierId, String funcCode, Long duration) throws Exception;

	
	public void deleteFuncAttemptParam(String identifierId, String funcCode)throws Exception;

}
