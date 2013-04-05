package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;

import com.sz7road.web.dao.GameAccountOrderDao;
import com.sz7road.web.model.gamemanage.GameAccountOrder;

public class GameAccountOrderDaoImpl implements GameAccountOrderDao{
	private static GameAccountOrderDao instance=new GameAccountOrderDaoImpl();
	private static final Logger logger = LogManager.getLogger(GameAccountOrderDaoImpl.class);
	public static GameAccountOrderDao getInstance()
	{
		return instance;
	}
	private static String INSERT_ORDER_SQL="insert into game_order (id,game_id,game_name,server_id,server_name,user_name,role_name,amount,other_amount,game_amount) values(?,?,?,?,?,?,?,?,?,?)";
	public boolean insertIntoOrder(final GameAccountOrder  account)   {
		logger.info("enter insertIntoOrder");
		boolean flag=true;
		try
		{
		PreparedStatement pstmt = DB.prepareStatement(INSERT_ORDER_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
		DB.insertUpdate(pstmt, new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
				//account.setId(new Date().getTime())/1000);
				ps.setString(1, new Date().getTime()/1000+"");
				ps.setInt(2, account.getGameId());
				ps.setString(3, account.getGameName());
				ps.setInt(4, account.getServerId());
				ps.setString(5, account.getServerName());
				ps.setString(6, account.getUserName());
				ps.setString(7, account.getRoleName());
				ps.setInt(8, account.getAmount());
				ps.setInt(9, account.getOtherAmount());
				ps.setInt(10, account.getGameAmount());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				
			}

		});
		}catch (Exception e) {
			logger.info(e.getMessage());
			flag=false;
		}
		return flag;
	}


}
