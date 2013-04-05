package com.sz7road.web.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.sz7road.web.common.listener.SystemConfig;


public class HttpUtil {

	public static String getCookie(HttpServletRequest request){
		Cookie[]  cookies = request.getCookies();
		String userInfo = "";
		for (int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals(SystemConfig.getProperty("cookie.name"))){
				userInfo = cookies[i].getValue();
			}
		}
		return userInfo;
	}
}
