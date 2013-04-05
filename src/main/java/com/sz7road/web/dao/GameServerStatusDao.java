package com.sz7road.web.dao;

import java.util.List;

import com.sz7road.web.model.gamemanage.GameServerStatus;

public interface GameServerStatusDao {
	public List<GameServerStatus> findAllGameServerStatus() throws Exception;

}
