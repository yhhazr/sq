package com.sz7road.web.common.listener;

import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.DbConfig;

import com.sz7road.web.action.admin.genehtml.AutoGeneHtmlAction;
import com.sz7road.web.common.dbconfig.DbConfigImpl;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.task.InsertAdEnterTimerTask;
import com.sz7road.web.task.InsertGameEnterInfoTimerTask;


public class ApplicationInitListener implements ServletContextListener {

	private static Properties properties = new Properties();
	
	private static ServletContext context = null;
	private Timer timer = null;
	private static Logger logger = Logger.getLogger(ApplicationInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("===========Listener start.===========");
		context = event.getServletContext();
		try {
			logger.info("Getting properties");
			new SystemConfig(context);
			//字符过滤
			new DirtyFilter(context);
			initDbFactory();
			adStatistics();//广告进入统计
			//启动自动生成页面
			startAutoGeneHtml(context);
			//生成玩家排行
			new AutoGenePlayerRank();
		} catch (Exception ioe) {
			logger.error(ioe.getMessage(), ioe);
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		logger.info("===========Listener stop.===========");
		 timer.cancel();
	}


	/**
	 * 初始化数据库工厂
	 */
	private void initDbFactory() {
		try {
			Class.forName(AppConstants.SERVLET_CONTEXT_JDBC_DRIVER);
			DbConfig dbConfig = new DbConfigImpl();
			DB.getFactory(dbConfig);
		} catch (Exception e) {
			logger.error("Init DbFactory Error" + e.getMessage(), e);
		}
	}
	/**
	 * 游戏入口监听器
	 */
	private void gameEnterListener(){
		 String time=SystemConfig.getProperty("insertGameEnterInfo");//配置文件读取时间间隔
			// TODO 设置任务计划，启动和间隔时间
			  timer = new Timer(true);
			  timer.schedule(new InsertGameEnterInfoTimerTask(), 0, Integer.parseInt(time));
	}
	
	/**
	 * 启动自动生成页面
	 */
	private void startAutoGeneHtml(ServletContext context) {
		AutoGeneHtmlAction autoAction = new AutoGeneHtmlAction();
		autoAction.geneServerPageStart();
	}
	/**
	 * 广告页进入统计
	 */
	private void adStatistics(){
		timer = new Timer();
		timer.schedule(new InsertAdEnterTimerTask(),  60 * 1000, 60 * 1000);
		
	}
}
