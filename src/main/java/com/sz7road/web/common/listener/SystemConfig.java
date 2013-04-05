/**
 * $Header: /data/cvs/chinasiteplatform/src/java/com/gs/sitecore/common/utils/SystemConfig.java,v 1.7 2009/03/05 06:25:24 mzheng Exp $ 
 * $Revision: 1.7 $ 
 * $Date: 2009/03/05 06:25:24 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.common.listener;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.sz7road.web.common.util.AppConstants;

/**
 * Property file loader, this class will load the properties file in $CONFIG_PATH/config into Properties.
 * and it also provide a method to read the value of the properties by key. 
 * @author Jimmy Shi
 * @version $Id: SystemConfig.java,v 1.7 2009/03/05 06:25:24 mzheng Exp $
 */
public class SystemConfig {

	private static Properties properties = new Properties();

	private static Logger logger = Logger.getLogger(SystemConfig.class);

	public SystemConfig(ServletContext context){
		initProperties(context);
	}


	/**
	 * Load some config files into properties object.
	 * 
	 * @return ""
	 */
	private static void initProperties(ServletContext context) {
		Properties prop = new Properties();
		try {
			String dbFileName = AppConstants.SERVLET_CONTEXT_ATTRIBUTE_DB_RESOURCES_PROPS_FILENAME;
			InputStream dbFileStream = context.getResourceAsStream(dbFileName);
			prop.load(dbFileStream);

			String appConfigFileName = AppConstants.SERVLET_CONTEXT_ATTRIBUTE_APPCONFIG_RESOURCES_PROPS_FILENAME;
			InputStream appConfigFileStream = context.getResourceAsStream(appConfigFileName);
			prop.load(appConfigFileStream);

//			String gameServerStatusFileName = AppConstants.SERVLET_CONTEXT_ATTRIBUTE_GAMESERVER_STATUS_RESOURCES_PROPS_FILENAME;
//			InputStream gameServerStatusFileStream = context.getResourceAsStream(gameServerStatusFileName);
//			prop.load(gameServerStatusFileStream);
			

			if (dbFileStream != null) {
				dbFileStream.close();
			}
			if (appConfigFileStream != null) {
				appConfigFileStream.close();
			}
//			if (gameServerStatusFileStream != null) {
//				gameServerStatusFileStream.close();
//			}
		} catch (Exception e) {
			logger.error("Init Properties error!" + e.getMessage(), e);
		}
		properties = prop;
		logger.info("===Init all of the properties success===");
	}

	/**
	 * Get property value by property name.
	 * @param name The property name
	 * @return The property value.
	 */
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
