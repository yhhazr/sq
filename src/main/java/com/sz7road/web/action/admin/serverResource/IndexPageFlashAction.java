package com.sz7road.web.action.admin.serverResource;

import java.applet.AppletContext;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.common.util.StringUtil;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.HomepageService;
import com.sz7road.web.service.NewsService;

/**
 * 使用ddt_homepage_manage表 对应对象 HomepageItem flashItem; HomepageItem imageItem;
 * 增加ddt_homepage_type表数据  6 开区flash; 7 开区图片（登陆器）
 *
 */

public class IndexPageFlashAction extends ActionSupport implements RequestAware, SessionAware{
	private static final Logger logger = LogManager.getLogger(IndexPageFlashAction.class);
	
	private HomepageItem flashItem;

	private File flashFile;
	
	private String flashFileFileName;

	private Map<String, Object> requestMap;
    
    private Map<String, Object> sessionMap;
    
    private String flashUrl;
    
    private String id;
    
    private static final String UPLOADPATH = SystemConfig.getProperty("gameServer.index.flash.upload.path");
    
    //首页flash列表
    public String queryIndexFlashList() throws Exception{
    	String result = INPUT;
    	result = SUCCESS;
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			PageInfo pageInfo = new PageInfo();
			int startRow = 0;
			String pagerOffset = request.getParameter("pager.offset");
			int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
			if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
				startRow = Integer.parseInt(pagerOffset);
			}
			pageInfo.setStartRow(startRow);
			pageInfo.setPageSize(pageSize);
			HomepageService homepageService = ServiceLocator.getHomepageService();
			PaginationResult<HomepageItem> flashPageationResult = homepageService.getItemListOrderByParam(pageInfo, AppConstants.HOMEPAGE_FLASH_TYPE_ID, AppConstants.HOMEPAGE_ORDER_PARAM, false);
			requestMap.put("pageationResult", flashPageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Query index flash List Error:" + e.getMessage(), e);
		}

		return result;
    }
    


    //创建开区flash准备
    public String createFlashPrepare() throws Exception{
    	String result = INPUT;
    	result = SUCCESS;
    	return result;
    }
    
    //创建flash提交
    public String createFlashSubmit() throws Exception{
		String result = INPUT;
		String newName = "";
		newName = AppConstants.HOMEPAGE_FLASH_PRE_NAME + flashFileFileName.substring(0, flashFileFileName.lastIndexOf("."));
		String position = "flash_" + newName;
		String saveRealFilePath = SystemConfig.getProperty("server.image.realpath") + UPLOADPATH;  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    if(flashFileFileName.lastIndexOf(".")>=0){
	    	newName = newName + flashFileFileName.substring(flashFileFileName.lastIndexOf(".")).toLowerCase();
	    }
	    savefile = new File(saveRealFilePath + "/" + newName);
	    FileUtils.copyFile(flashFile, savefile);
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			flashItem.setContent(newName);			
			flashItem.setName(newName);			
			flashItem.setTypeId(AppConstants.HOMEPAGE_FLASH_TYPE_ID);
			flashItem.setPosition(position);
			flashItem.setCreateDate(new Date());
			String flashUrl = StringUtil.editUrlString(flashItem.getUrl());
			flashItem.setUrl(flashUrl);
			boolean createResult = homepageService.insertItem(flashItem);
			if (createResult) {
				result = SUCCESS;
			} else {
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Create Flash for start server Submit Error:" + e.getMessage(), e);
		}
		return result;
    }

    //编辑开区flash准备
    public String editFlashPrepare(){
    	String result = INPUT;
    	HomepageService homepageService = ServiceLocator.getHomepageService();
    	if(!StringUtils.isBlank(id) && StringUtils.isNumeric(id)){
    		try {
				flashItem = homepageService.getItem(Integer.parseInt(id));
				result = SUCCESS;
			} catch (Exception e) {
				logger.error("get index flash by id error ", e);
			}
    	}
    	return result;
    }
     
	//编辑flash提交
	public String editFlashSubmit() throws Exception{
		String result = INPUT;
		if(flashFile != null){
			String newName = "";
			String saveRealFilePath = SystemConfig.getProperty("server.image.realpath") + UPLOADPATH;  
			File fileDir = new File(saveRealFilePath);  
			if (!fileDir.exists()) {  
				fileDir.mkdirs();  
			} 
			newName = flashItem.getContent();
			File savefile = new File(saveRealFilePath + "/" + newName);
			FileUtils.copyFile(flashFile, savefile);
		}
		String flashUrl = StringUtil.editUrlString(flashItem.getUrl());
		flashItem.setUrl(flashUrl);
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			boolean editResult = homepageService.update(flashItem);
			if (editResult) {
				result = SUCCESS;
			} else {
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("edit Flash for start server Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	//删除flash信息
    public String removeFlash(){
    	String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		int itemIdInt = 0;
		try {
			String[] s = id.split(",");
			for (int i = 0; i < s.length; i++) {
				if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
					itemIdInt = Integer.parseInt(s[i]);
				}
				boolean deleteResult = service.deleteItem(itemIdInt);
				if (deleteResult) {
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("Admin Delete Item Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }

	public HomepageItem getFlashItem() {
		return flashItem;
	}

	public void setFlashItem(HomepageItem flashItem) {
		this.flashItem = flashItem;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public File getFlashFile() {
		return flashFile;
	}


	public void setFlashFile(File flashFile) {
		this.flashFile = flashFile;
	}

	public String getFlashFileFileName() {
		return flashFileFileName;
	}

	public void setFlashFileFileName(String flashFileFileName) {
		this.flashFileFileName = flashFileFileName;
	}

	public String getFlashUrl() {
		return flashUrl;
	}


	public void setFlashUrl(String flashUrl) {
		this.flashUrl = flashUrl;
	}

	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}





	


}
