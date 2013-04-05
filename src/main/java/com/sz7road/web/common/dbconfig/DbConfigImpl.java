package com.sz7road.web.common.dbconfig;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.pureart.persistement.database.easydb.DbConfig;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;

public class DbConfigImpl implements DbConfig {

	private static Logger logger = Logger.getLogger(DbConfigImpl.class);

	@Override
	public String getJdbcUrl() {
		return SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_URL);	
	}

	@Override
	public String getUsername() {
		return SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_USERNAME);
	}

	@Override
	public String getPassword() {
		return SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_PASSWORD);
	}

	@Override
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@Override
	public int getMaxConnectionCount() {
		int maxConnectionCount = 0;
		String maxConnectionCountString = SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_MAXCONNECTIONCOUNT);
		try {
			maxConnectionCount = Integer.parseInt(maxConnectionCountString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return maxConnectionCount;
	}

	@Override
	public int getMinConnectionCount() {
		int minConnectionCount = 0;
		String minConnectionCountString = SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_MINCONNECTIONCOUNT);
		try {
			minConnectionCount = Integer.parseInt(minConnectionCountString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return minConnectionCount;
	}

	@Override
	public boolean isLasyMode() {
		boolean isLasyMode = false;
		String isLasyModeString = SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_ISLASYMODE);
		try {
			isLasyMode = Boolean.parseBoolean(isLasyModeString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return isLasyMode;
	}

	@Override
	public boolean isLogSql() {
		boolean showSql = false;
		String showSqlString = SystemConfig.getProperty(AppConstants.SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_SHOWSQL);
		try {
			showSql = Boolean.parseBoolean(showSqlString);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return showSql;
	}

	@Override
	public Properties getProperties() {
		return null;
	}

	@Override
	public long getConnectionTimeout() {
		return 30000;
	}

}
