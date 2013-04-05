package com.sz7road.web.action.admin.dirtymanage;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.service.DirtyService;

public class DirtyManageAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(UserManageAction.class);

	private static DirtyService dirtyService = ServiceLocator.getDirtyService();
	private Map<Integer,String> map;
	private String str;
	public String queryDirtyList(){
		String result = INPUT;
		try {
			StringBuilder sb = new StringBuilder();
			map = dirtyService.getDirty();
			if(map.isEmpty()){
				str = "";
			}else{
				Iterator it = map.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry en = (Map.Entry) it.next();
					sb.append(en.getValue() + "，");
				}
				str = sb.toString().substring(0,sb.toString().lastIndexOf("，"));
			}
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("query dirty error:" + e.getMessage() , e);
		}
		return result;
	}
	
	public String modifyDirtySubmit(){
		String result = INPUT;
		try {
			dirtyService.writeDirty(str);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("write dirty error:" + e.getMessage() , e);
		}
		return result;
	}
	
	
	public Map<Integer,String> getMap() {
		return map;
	}

	public void setMap(Map<Integer,String> map) {
		this.map = map;
	}


	public String getStr() {
		return str;
	}


	public void setStr(String str) {
		this.str = str;
	}
	
}
