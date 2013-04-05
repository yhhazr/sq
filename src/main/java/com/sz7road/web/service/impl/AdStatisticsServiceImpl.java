package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.HttpClientUtil;
import com.sz7road.web.common.util.JedisFactory;
import com.sz7road.web.common.util.JsonUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.dao.AdStatisticsDao;
import com.sz7road.web.dao.impl.AdStatisticsDaoImpl;
import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.gamemanage.GameServerInfo;
import com.sz7road.web.service.AdStatisticsService;

public class AdStatisticsServiceImpl implements AdStatisticsService {

	private JedisFactory jedisFactory = new JedisFactory();
	private static AdStatisticsServiceImpl _this;
	private AdStatisticsDao adStatisticsDao;
	private Logger logger = Logger.getLogger(AdStatisticsServiceImpl.class);

	public AdStatisticsServiceImpl() {
		adStatisticsDao = new AdStatisticsDaoImpl();
	}

	public synchronized static AdStatisticsServiceImpl getInstance() {
		if (_this == null)
			_this = new AdStatisticsServiceImpl();
		return _this;
	}

	@Override
	public synchronized void enterCount(int adId, String site) throws Exception {
		Jedis jedis = jedisFactory.getJedisInstance();
		String adInfoString = jedis.get("sq"+Integer.toString(adId));
		if (adInfoString != null && !"".equals(adInfoString)) {
			AdInfo adInfo = JsonUtil.jsonStrToBean(adInfoString, AdInfo.class);
			adInfo.setEnterTimes(adInfo.getEnterTimes() + 1);
			adInfo.setChange(true);
			jedis.set("sq"+Integer.toString(adId), JSONObject.fromObject(adInfo)
					.toString());
		} else {
			AdInfo adInfo = new AdInfo();
			String date = DateUtil.format(new Date(), DateUtil.DATE_FORMAT);
			adInfo.setDate(date);
			adInfo.setAdId(adId);
			adInfo.setChange(true);
			adInfo.setSite(site);
			adInfo.setEnterTimes(1);
			adInfo.setRegTimes(0);
			jedis.set("sq"+Integer.toString(adId), JSONObject.fromObject(adInfo)
					.toString());
		}
		jedisFactory.release(jedis);
	}

	@Override
	public synchronized void insertAdStatistics() {
		Jedis jedis = jedisFactory.getJedisInstance();
		String date = DateUtil.format(new Date(), DateUtil.DATE_FORMAT);
		try {
			Iterator<String> it = jedis.keys("sq*").iterator();
			while (it.hasNext()) {
				String key = it.next();
				String adInfoString = jedis.get(key);
				AdInfo info = JsonUtil
						.jsonStrToBean(adInfoString, AdInfo.class);
				if (info.isChange()) {
					AdInfo adInfo = adStatisticsDao.getAdStatisticsByAdId(
							info.getAdId(), date);
					if (date.equals(info.getDate())
							&& date.equals(adInfo.getDate())) {
						if (adInfo.getEnterTimes() > info.getEnterTimes()) { // 如果服务器重启，缓存被清空时执行
							int enterTimes = adInfo.getEnterTimes();
							adInfo.setEnterTimes(info.getEnterTimes()
									+ adInfo.getEnterTimes());
							info.setEnterTimes(info.getEnterTimes()
									+ enterTimes);
						} else {
							adInfo.setEnterTimes(info.getEnterTimes());
						}
						if (adInfo.getRegTimes() > info.getRegTimes()) {
							int regTimes = adInfo.getRegTimes();
							adInfo.setRegTimes(info.getRegTimes()
									+ adInfo.getRegTimes());
							info.setRegTimes(info.getRegTimes() + regTimes);
						} else {
							adInfo.setRegTimes(info.getRegTimes());
						}
						info.setChange(false);
						jedis.set("sq"+Integer.toString(info.getAdId()), JSONObject
								.fromObject(info).toString());
						adStatisticsDao.updateAdStatistics(adInfo);
					} else if (date.equals(info.getDate())
							&& !date.equals(adInfo.getDate())) {
						info.setChange(false);
						jedis.set("sq"+Integer.toString(info.getAdId()), JSONObject
								.fromObject(info).toString());
						adStatisticsDao.insertAdStatistics(info);
					} else if (!date.equals(info.getDate())
							&& !date.equals(adInfo.getDate())) {
						jedis.del(key);
					}
				}
			}
		} catch (Exception e) {
			logger.error("缓存插入到数据库出错：" + e.getMessage());
		} finally {
			jedisFactory.release(jedis);
		}
	}

	@Override
	public synchronized void regCount(RegInfo regInfo, String site)
			throws Exception {
		Jedis jedis = jedisFactory.getJedisInstance();
		String adInfoString = jedis.get("sq"+Integer.toString(regInfo.getAdId()));
		if (adInfoString != null && adInfoString != "") {
			AdInfo adInfo = JsonUtil.jsonStrToBean(adInfoString, AdInfo.class);
			adInfo.setRegTimes(adInfo.getRegTimes() + 1);
			adInfo.setChange(true);
			jedis.set("sq"+Integer.toString(regInfo.getAdId()), JSONObject
					.fromObject(adInfo).toString());
		} else {
			AdInfo adInfo = new AdInfo();
			String date = DateUtil.format(new Date(), DateUtil.DATE_FORMAT);
			adInfo.setAdId(regInfo.getAdId());
			adInfo.setDate(date);
			adInfo.setChange(true);
			adInfo.setSite(site);
			adInfo.setRegTimes(1);
			jedis.set("sq"+Integer.toString(regInfo.getAdId()), JSONObject
					.fromObject(adInfo).toString());
		}
		adStatisticsDao.insertRegInfo(regInfo);
		jedisFactory.release(jedis);
	}

	@Override
	public GameServerInfo getServerById(String serverId) throws Exception {
		GameServerInfo server = null;
		String serverListUrl = SystemConfig.getProperty("platform.connect.base.url") + "GetSqMergerInfo?s=" + System.currentTimeMillis();
		String response = HttpClientUtil.post(serverListUrl, new ArrayList<NameValuePair>());
		if(response != null && response.substring(0, response.indexOf("=")).equals("success")){
			String serverListStr = response.substring(response.indexOf("=") + 1, response.length());
			if (serverListStr.startsWith("[") && serverListStr.endsWith("]")) {
				List<GameServerInfo> serverList = new ArrayList<GameServerInfo>();
				serverList = JsonUtil.jsonStrToList(serverListStr, GameServerInfo.class);
				for (GameServerInfo gameServerInfo : serverList) {
					if(gameServerInfo.getServerId().equals(serverId)){
						server = gameServerInfo;
					}
				}
			}
		}
		return server;
	}

	@Override
	public RegInfo checkUserName(String userName) throws Exception {
		RegInfo regInfo = adStatisticsDao.getRegInfoByUserName(userName);
		return regInfo;
	}
}
