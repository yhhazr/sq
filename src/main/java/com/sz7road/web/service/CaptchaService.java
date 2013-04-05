package com.sz7road.web.service;

import java.rmi.RemoteException;

import org.omg.CORBA.portable.ApplicationException;

import com.sz7road.web.model.captcha.FunctionUsageLog;

public interface CaptchaService {

	/**
	 * insert a record when do a action
	 * 
	 * @param log the log Object that contains the log base information
	 * @throws ApplicationException
	 * @throws RemoteException
	 */
	public void AddUserAttemptLog(FunctionUsageLog log) throws Exception;

	
	/**
	 * Get user attempt times in a duration time 
	 * by identifierId,funcCode,duration
	 * @param identifierId is the ip address of the user
	 * @param funcCode is the function code
	 * @param duration the duration time with unit is minute
	 * @return Long the times user do a same action in a duration
	 * @throws ApplicationException
	 * @throws RemoteException
	 */
	public int getUserAttemptCnt(String identifierId, String funcCode, Long duration) throws Exception;

	/**
	 * Remove user login failed logs.
	 * @param identifierId is the ip address of the user
	 * @param funcCode is the function code
	 * @throws ApplicationException
	 * @throws RemoteException
	 */
	public void removeUserLoginFailedLog(String identifierId, String funcCode) throws Exception;
}