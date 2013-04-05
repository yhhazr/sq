package com.sz7road.web.common.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.sz7road.web.common.util.AppConstants;

public class DirtyFilter {

	private static Map<Character,List<String>> map = new HashMap<Character,List<String>>();
	private static Logger logger = Logger.getLogger(SystemConfig.class);
	
	public DirtyFilter(){}
	
	public DirtyFilter(ServletContext context){
		init(context);
	}
	//初始化，提取文本中的字符表
	private void init(ServletContext context){
		
		String path = AppConstants.DIRTY_CODE_TXT;
		List<String> ls = new ArrayList<String>();
		try{
			//InputStream is = context.getResourceAsStream(path);
			String real = context.getRealPath(path);
			File file = new File(real);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String str = "";
			while((str = in.readLine()) != null){
				String[] temp = str.split("-");
				for(String s:temp){
					ls.add(s);
				}
			}
			in.close();
		}catch(Exception e){
			logger.error("file read error:" + e.getMessage() , e);
		}
		Set<Character> set = new HashSet<Character>();
		for(int i=0;i<ls.size();i++){
			set.add(ls.get(i).charAt(0));
		}
		Iterator<Character> it = set.iterator();
		while(it.hasNext()){
			List<String> list = new ArrayList<String>();
			char c = it.next();
			for(int i = 0;i<ls.size();i++){
				if(c == ls.get(i).charAt(0)){
					list.add(ls.get(i));
				}
			}
			map.put(c, list);
		}
	}
	//字符串替换方法
	private String replace(String s,int start,int end){
		StringBuilder sb = new StringBuilder(3);
		sb.append(s.substring(0,start));
		String str = "*";
		for(int i = 1;i<end - start;i++){
			str = str + "*";
		}
		sb.append(str);
		sb.append(s.substring(end));
		return sb.toString();
	}
	//字符过滤的外部方法
	private String filter(String dirty){
		String temp = dirty;
		for(int i = 0;i<temp.length();i++){
			if(map.containsKey(temp.charAt(i))){
				List <String> ls = map.get(temp.charAt(i));
				for(String s:ls){
					boolean flag = temp.regionMatches(i, s, 0, s.length());
					if(flag){
						temp = replace(temp, i, i + s.length());
						i = i + s.length();
						break;
					}
				}
			}
		}
		return temp;
	}
	
	public static String getFilter(String str) throws Exception{
		
		return DirtyFilter.class.newInstance().filter(str);
		
		
	}
}
