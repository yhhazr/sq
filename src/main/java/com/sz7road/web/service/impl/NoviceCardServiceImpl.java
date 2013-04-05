package com.sz7road.web.service.impl;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.util.PlatformClient;
import com.sz7road.web.service.NoviceCardService;

public class NoviceCardServiceImpl implements NoviceCardService {
	
	private static final Logger logger = LogManager.getLogger(NoviceCardServiceImpl.class);
	
	private final ReentrantLock lock = new ReentrantLock();
	
	private static NoviceCardServiceImpl _this;
	
	public synchronized static NoviceCardServiceImpl getInstance() {
		if (_this == null)
			_this = new NoviceCardServiceImpl();
		return _this;
	}
	
	private PlatformClient client = PlatformClient.getInstance();
	
	@Override
	public String queryCDKey(String userId, String site) throws Exception {
		String key = "000000000000";
		try {
			lock.lock();
			String cdKey = checkNoviceCard(userId, site);
			if (cdKey != null) {
				if ("false".equals(cdKey.trim())) {
					key = findNoviceCard(userId, site);
				} else {
					key = cdKey;
				}
			}
		} catch(Exception e) {
			logger.error("获得激活码异常。", e);
		} finally {
			lock.unlock();
		}
		return key;
	}
	
	/**
	 * 检查是否已领取新手卡
	 * 	已领取:返回已领的激活码
	 * 	未领取：返回false
	 * @return
	 */
	public String checkNoviceCard(String userId, String site) throws Exception {
		PlatformClient client = PlatformClient.getInstance();
		String cdKey = client.queryNaviceCardCDKey(userId, site);
		return cdKey;
	}

	/**
	 * 获得新手卡激活码
	 * @param userId
	 * @param site
	 * @return
	 */
	public String findNoviceCard(String userId, String site) throws Exception {
		PlatformClient client = PlatformClient.getInstance();
		String cdKey = client.queryNaviceCardSingleCDKey(userId, site);
		return cdKey;
	}
	
	

}
