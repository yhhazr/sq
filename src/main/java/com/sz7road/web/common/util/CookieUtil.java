package com.sz7road.web.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.sz7road.web.common.listener.SystemConfig;

/**
 * cookie工具类
 * 先进行Base64Encode再进行URLEncode
 * @author john.jiang
 *
 */
public class CookieUtil {
	private static final Logger logger =  LogManager.getLogger(CookieUtil.class);
	
	private static final String JAVA_NULL_COOKIE = "\"\"";
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
		String nameStr = null;
		String valueStr = null;
		try {
			nameStr = URLEncoder.encode(Base64Util.getBASE64(name), HTTP.UTF_8);
			if(!(value == null || "".equals(value))) {
				valueStr = URLEncoder.encode(Base64Util.getBASE64(value), HTTP.UTF_8);
			}
		} catch (UnsupportedEncodingException e) {
		}
	    Cookie cookie = new Cookie(nameStr, valueStr);
	    String domain = SystemConfig.getProperty("online.cookie.domain");
	    if(domain != null && !"".equals(domain)) {
	    	cookie.setDomain(domain);
	    }
	    cookie.setPath("/");
	    if(maxAge > 0) {
	    	cookie.setMaxAge(maxAge);
	    }
	    response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name, int maxAge){
		String nameStr = null;
		try {
			nameStr = URLEncoder.encode(Base64Util.getBASE64(name), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
		}
		Cookie cookie = new Cookie(nameStr, null);
	    String domain = SystemConfig.getProperty("online.cookie.domain");
	    if(domain != null && !"".equals(domain)) {
	    	cookie.setDomain(domain);
	    }
		cookie.setPath("/");
	    if(maxAge > 0) {
	    	cookie.setMaxAge(maxAge);
	    }
		response.addCookie(cookie);
	}
	
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = readCookieMap(request);
	    String nameStr = null;
		try {
			nameStr = URLEncoder.encode(Base64Util.getBASE64(name), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
		}
	    if(cookieMap.containsKey(nameStr)){
	        Cookie cookie = (Cookie)cookieMap.get(nameStr);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	
	public static String getCookieValueByName(HttpServletRequest request,String name) {
		Cookie cookie = getCookieByName(request, name);
		String value = null;
		if(cookie != null && !("".equals(cookie.getValue())) && !(JAVA_NULL_COOKIE.equals(cookie.getValue()))) {
			try {
				value = Base64Util.getFromBASE64(URLDecoder.decode(cookie.getValue(), HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				logger.error("cookie base64解码错误。name:" + name + " | value:" + value);
			}
		} 
		return value;
	}
	
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
	
	
	//官网专用
	//构造cookie加密部分
	public static String buildMD5Cookie(String id, String name, String time){
		String userInfo = id + name + time;
		String md5Key = SystemConfig.getProperty("MD5.key");		
		String newUserInfo = userInfo + md5Key;
		String md5UserInfo = MD5Util.getMD5String(newUserInfo).toLowerCase();
		
		return md5UserInfo;
	}
	
	//官网专用
	//从cookie中取出用户信息并转成string[]
	public static String[] getUserInfoArrayFromCookie() {
		String[] userInfo = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String userInfoInCookie = CookieUtil.getCookieValueByName(request, SystemConfig.getProperty("cookie.name"));
		if (!(userInfoInCookie == null || "".equals(userInfoInCookie)) && userInfoInCookie.indexOf(",") != -1) {
			userInfo = userInfoInCookie.split(",");
		}
		return userInfo;
	}
	
}
