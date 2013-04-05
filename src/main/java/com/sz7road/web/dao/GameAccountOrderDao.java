package com.sz7road.web.dao;

import com.sz7road.web.model.gamemanage.GameAccountOrder;

public interface GameAccountOrderDao {
	public boolean insertIntoOrder(GameAccountOrder account)throws Exception;
}
