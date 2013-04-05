package com.sz7road.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.GameEnterInfoStatisticsDao;
import com.sz7road.web.dao.impl.GameEnterInfoStatisticsDaoImpl;
import com.sz7road.web.model.gameEnterInfo.GameEnterInfo;
import com.sz7road.web.service.GameEnterInfoStatisticsService;

public class GameEnterInfoStatisticsServiceImpl implements
		GameEnterInfoStatisticsService {
	
	private static GameEnterInfoStatisticsServiceImpl _this;
 	private static GameEnterInfoStatisticsDao gameEnterInfoStatisticsDao;
	protected static final HashMap<String, GameEnterInfo> map = new HashMap<String, GameEnterInfo>(); // Cache table 
	private GameEnterInfoStatisticsServiceImpl() {
		gameEnterInfoStatisticsDao = GameEnterInfoStatisticsDaoImpl.getInstance();
	}
	
	/**
	 * @return UserBusiness
	 */
	public synchronized static GameEnterInfoStatisticsServiceImpl getInstance() {
		if (_this == null)
			_this = new GameEnterInfoStatisticsServiceImpl();
		return _this;
	}

	public  synchronized boolean insertGameEnterInfoStatistics(
			String  positionKey,String date) throws Exception {
		// TODO Auto-generated method stub
		GameEnterInfo gameEnterInfo =(GameEnterInfo)map.get(new String(positionKey));
		//获取当天的数据库信息
		GameEnterInfo gameEnterInfo2 = gameEnterInfoStatisticsDao.getGameEnterInfoByPostion(positionKey,date);	
		if((gameEnterInfo!=null&&date.equals(gameEnterInfo.getBeginTime())&&!date.equals(gameEnterInfo2.getBeginTime()))){
			gameEnterInfoStatisticsDao.insertGameEnterInfo(gameEnterInfo);
			return true;
		}else if(gameEnterInfo!=null&&date.equals(gameEnterInfo.getBeginTime())&&date.equals(gameEnterInfo2.getBeginTime())){
			gameEnterInfo.setId(gameEnterInfo2.getId());
			gameEnterInfoStatisticsDao.updateGameEnterInfo(gameEnterInfo);
			return true;
		}else if(gameEnterInfo!=null&&!date.equals(gameEnterInfo.getBeginTime())&&!date.equals(gameEnterInfo2.getBeginTime())){
			gameEnterInfo.setTimes("0");
			gameEnterInfo.setBeginTime(date);
			gameEnterInfo.setEndTime(date);
			map.put(new String(positionKey), gameEnterInfo);
			return true;
		}else{
			return false;
		}
	}


	public static boolean resetCacheBean(String positionKey) throws Exception {
		// TODO Auto-generated method stub
		map.put(new String(positionKey), null);
		return true;
	}
	
	protected static synchronized void loadDataSource(String positionKey,String date) {
		try {
			GameEnterInfo gameEnterInfo = gameEnterInfoStatisticsDao.getGameEnterInfoByPostion(positionKey,date);
			map.put(new String(positionKey), gameEnterInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public synchronized boolean countTimes(String positionKey) throws Exception {
		// TODO Auto-generated method stub
		GameEnterInfo gameEnterInfo =(GameEnterInfo)map.get(new String(positionKey));
		Date date =new Date();
		String now=DateUtil.format(date,DateUtil.DATE_FORMAT);
		Long count= (long) 0;
		if(gameEnterInfo!=null&&!gameEnterInfo.getTimes().trim().equals("")){
			if(!now.equals(gameEnterInfo.getBeginTime()))
				count=(long) 0;
			else
				count=Long.parseLong(gameEnterInfo.getTimes());
			count++;
			gameEnterInfo.setTimes(count.toString());
			gameEnterInfo.setPosition(positionKey);
			gameEnterInfo.setBeginTime(now);
			gameEnterInfo.setEndTime(now);
			map.put(new String(positionKey),gameEnterInfo);
		}else{
			loadDataSource(positionKey,now);//从数据库同步缓存数据
			 gameEnterInfo =(GameEnterInfo)map.get(new String(positionKey));//重新获取缓存数据
			 if(gameEnterInfo.getTimes()!=null)
			  count=Long.parseLong(gameEnterInfo.getTimes());
				count++;
				gameEnterInfo.setTimes(count.toString());
				gameEnterInfo.setPosition(positionKey);
				gameEnterInfo.setBeginTime(now);
				gameEnterInfo.setEndTime(now);
				map.put(new String(positionKey),gameEnterInfo);
		}
		return true;
	}

	@Override
	public  HashMap<String, GameEnterInfo> getCacheMap() {
		// TODO Auto-generated method stub
		return this.map;
	}
	


}
