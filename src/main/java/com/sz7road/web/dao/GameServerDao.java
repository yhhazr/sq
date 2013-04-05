package com.sz7road.web.dao;

import java.util.List;
import java.util.Set;

import com.sz7road.web.model.gamemanage.GameServer;

public interface GameServerDao {

	public List<GameServer> getAllGameServer() throws Exception;

	public GameServer getGameServerById(int id) throws Exception;
	
	public List<GameServer> getGameServersByGameId(int gameId) throws Exception;
	
	public List<GameServer> getAllRecommendGameServer() throws Exception;
	
	public List<Integer> getAllRecommendGameServerId() throws Exception;
	
	public GameServer getTheLastGameServer() throws Exception;
	
	public List<GameServer> getAllRecommendGameServerWithTime() throws Exception;
	
	public GameServer getGameServerTimeById(final int id) throws Exception;
	
	public GameServer getGameServerByUrl(final String url) throws Exception;
	
	public GameServer getGameServerStateById(final int id) throws Exception;
}
