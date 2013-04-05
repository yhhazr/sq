package com.sz7road.web.service.impl;

import com.sz7road.web.dao.CaptchaDao;
import com.sz7road.web.dao.impl.CaptchaDaoImpl;
import com.sz7road.web.model.captcha.FunctionUsageLog;
import com.sz7road.web.service.CaptchaService;

public class CaptchaServiceImpl implements CaptchaService {

	private static CaptchaServiceImpl _this;
	private static CaptchaDao dao = null;
	
	/**
	 * Constructor
	 */
	private CaptchaServiceImpl() {
		dao = CaptchaDaoImpl.getInstance();
	}

	/**
	 * @return PermissionBusiness
	 */
	public synchronized static CaptchaServiceImpl getInstance() {
		if (_this == null)
			_this = new CaptchaServiceImpl();
		return _this;
	}
	
	@Override
	public void AddUserAttemptLog(FunctionUsageLog log) throws Exception {
		dao.insertUserAttemptLog(log);
	}

	@Override
	public int getUserAttemptCnt(String identifierId, String funcCode, Long duration) throws Exception {
		return dao.queryUserAttemptCnt(identifierId, funcCode, duration);
	}

	@Override
	public void removeUserLoginFailedLog(String identifierId, String funcCode) throws Exception {
		dao.deleteFuncAttemptParam(identifierId, funcCode);
	}

}
