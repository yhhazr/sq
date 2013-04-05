package com.sz7road.web.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.model.onlineUser.GuildInfos;
import com.sz7road.web.model.onlineUser.PlayerGradeInfos;

/**
 * 2012-10-11 修改：新增 getPlayerFightInfo() getGuildGradeInfo() getGuildFightInfo()
 * @author john.jiang
 *
 */
public class PlayerRankBuilder {	
	private static final Logger logger = LogManager.getLogger(PlayerRankBuilder.class);
	
	static int PLAYER_RANK_SIZE = 0;
	
	static {
		final String PLAYER_RANK_SIZE_STRING = SystemConfig.getProperty("dallyInfos.show.size");
		if(StringUtils.isNumeric(PLAYER_RANK_SIZE_STRING)) {
			PLAYER_RANK_SIZE = Integer.parseInt(PLAYER_RANK_SIZE_STRING);
		}
	}
	
	//获得玩家等级排行数据
	public static List<PlayerGradeInfos.PlayerGradeInfo> getDallyInfo(String url) throws Exception{
		List<PlayerGradeInfos.PlayerGradeInfo> dallyInfos = new ArrayList<PlayerGradeInfos.PlayerGradeInfo>();
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element root = doc.getRootElement();
			Iterator<?> iterator = root.elementIterator("TotalAdd");
			while(iterator.hasNext()){
				Element second = (Element)iterator.next();
				PlayerGradeInfos.PlayerGradeInfo info = new PlayerGradeInfos.PlayerGradeInfo();
				info.setNickName(second.attributeValue("nikcName"));
				info.setGrades(second.attributeValue("grades"));
				dallyInfos.add(info);
				if(dallyInfos.size() == PLAYER_RANK_SIZE){
					break;
				}
			}
			logger.info("Get player grade rank data success:" + url);
		}catch (Exception e) {
			logger.error("Get player grade rank data error:" + url);
		}
		return dallyInfos;
	}
	
	//获得玩家战斗力排行数据
	public static List<PlayerGradeInfos.PlayerGradeInfo> getPlayerFightInfo(String url) throws Exception{
		List<PlayerGradeInfos.PlayerGradeInfo> dallyInfos = new ArrayList<PlayerGradeInfos.PlayerGradeInfo>();
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element root = doc.getRootElement();
			Iterator<?> iterator = root.elementIterator("PlayerFight");
			while(iterator.hasNext()){
				Element second = (Element)iterator.next();
				PlayerGradeInfos.PlayerGradeInfo info = new PlayerGradeInfos.PlayerGradeInfo();
				info.setNickName(second.attributeValue("nikcName"));
				info.setFightCapacity(second.attributeValue("FightCapacity"));
				dallyInfos.add(info);
				if(dallyInfos.size() == PLAYER_RANK_SIZE){
					break;
				}
			}
			logger.info("Get player fight rank data success:" + url);
		}catch (Exception e) {
			logger.error("Get player fight rank data error:" + url);
		}
		return dallyInfos;
	}
	
	//获得公会等级排行数据
	public static List<GuildInfos.GuildInfo> getGuildGradeInfo(String url) throws Exception{
		List<GuildInfos.GuildInfo> guildInfos = new ArrayList<GuildInfos.GuildInfo>();
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element root = doc.getRootElement();
			Iterator<?> iterator = root.elementIterator("orderItems");
			while(iterator.hasNext()){
				Element second = (Element)iterator.next();
				GuildInfos.GuildInfo info = new GuildInfos.GuildInfo();
				info.setConsortiaName(second.attributeValue("ConsortiaName"));
				info.setLevels(second.attributeValue("Levels"));
				guildInfos.add(info);
				if(guildInfos.size() == PLAYER_RANK_SIZE){
					break;
				}
			}
			logger.info("Get guild grade rank data success:" + url);
		}catch (Exception e) {
			logger.error("Get guild grade rank data error:" + url);
		}
		return guildInfos;
	}
	
	//获得公会战斗力排行数据
	public static List<GuildInfos.GuildInfo> getGuildFightInfo(String url) throws Exception{
		List<GuildInfos.GuildInfo> guildInfos = new ArrayList<GuildInfos.GuildInfo>();
		try{
			SAXReader reader = new SAXReader();
			Document doc = reader.read(url);
			Element root = doc.getRootElement();
			Iterator<?> iterator = root.elementIterator("fightItems");
			while(iterator.hasNext()){
				Element second = (Element)iterator.next();
				GuildInfos.GuildInfo info = new GuildInfos.GuildInfo();
				info.setConsortiaName(second.attributeValue("ConsortiaName"));
				info.setFightPower(second.attributeValue("FightPower"));
				guildInfos.add(info);
				if(guildInfos.size() == PLAYER_RANK_SIZE){
					break;
				}
			}
			logger.info("Get guild fight rank data success:" + url);
		}catch (Exception e) {
			logger.error("Get guild fight data error:" + url);
		}
		return guildInfos;
	}
}
