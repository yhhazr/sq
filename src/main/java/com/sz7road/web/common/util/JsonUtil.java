package com.sz7road.web.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	//组装json数组参数方法
	public static <E> JSON beanToJSONArray(E... element){
		JSONArray jsonArray = JSONArray.fromObject(element);
		
		return jsonArray;
	}
	
	//json中提取list方法
	public static <E> List<E> jsonStrToGetList(String jsonString, String listName, Class<E> pojoClass){
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		List<E> list = null;
        JSONObject jsonItem;
		if(jsonObj.has(listName)) {
			list = new ArrayList<E>();
			String jsonListStr = jsonObj.getString(listName);
			if(jsonListStr.startsWith("[") && jsonListStr.endsWith("]")){
				JSONArray jsonArray = jsonObj.getJSONArray(listName);
				if(jsonArray != null){
					Map<String, Class<E>> map = new HashMap<String, Class<E>>(); 
					map.put(listName, pojoClass); 
					for(int i = 0; i<jsonArray.size(); i++){
						jsonItem = jsonArray.getJSONObject(i);
						@SuppressWarnings("unchecked")
						E server = (E)JSONObject.toBean(jsonItem, pojoClass, map); 
						list.add(server);
					}
				}
			}
		}

		return list;
	} 
	
	//json转换list方法
	public static <E> List<E> jsonStrToList(String jsonString, Class<E> pojoClass){
		List<E> list = new ArrayList<E>();
		JSONObject jsonItem;
		if(jsonString != null && !"".equals(jsonString)){
			JSONArray jsonArray = JSONArray.fromObject(jsonString);
			for(int i = 0; i<jsonArray.size(); i++){
				jsonItem = jsonArray.getJSONObject(i);
				@SuppressWarnings("unchecked")
				E server = (E)JSONObject.toBean(jsonItem, pojoClass); 
				list.add(server);
			}
		}
		  
		return list;
	}
	
	//json转换bean方法
	public static <E> E jsonStrToBean(String jsonString, Class<E> pojoClass){
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		@SuppressWarnings("unchecked")
		E server = (E)JSONObject.toBean(jsonObj, pojoClass);
		
		return server;
	}
	
	
	
	
	
}
