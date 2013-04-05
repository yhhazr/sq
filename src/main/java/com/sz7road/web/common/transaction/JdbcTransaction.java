package com.sz7road.web.common.transaction;

import java.sql.SQLException;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.Transaction;

public class JdbcTransaction {

	private ThreadLocal<Transaction> threadLocal = new ThreadLocal<Transaction>();

	private static JdbcTransaction jdbcTransaction = null; 
	
	private JdbcTransaction(){
	}
	public static JdbcTransaction getInstance(){
		if(jdbcTransaction == null){
			jdbcTransaction =  new JdbcTransaction();
		}
		return jdbcTransaction;
	} 
	
	public Transaction getAndBeginTransaction() throws SQLException {
		Transaction tran = threadLocal.get();
		if (tran == null) {
			tran = DB.beginTransaction();
			threadLocal.set(tran);
		}
		return tran;
	}

	public void commit() throws SQLException{
		Transaction tran = threadLocal.get();
		if (tran != null) {
			tran.commit();
			threadLocal.remove();
		}
	}

}
