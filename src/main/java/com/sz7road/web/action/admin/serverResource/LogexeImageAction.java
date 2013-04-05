package com.sz7road.web.action.admin.serverResource;

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
public class LogexeImageAction extends ActionSupport implements RequestAware, SessionAware{
	private static final Logger logger = LogManager.getLogger(LogexeImageAction.class);
	
	private HomepageItem imageItem;
	
	private File imageFile;
	
	private String imageFileFileName;
	
	private Map<String, Object> requestMap;
    
    private Map<String, Object> sessionMap;
    
    private String imageUrl;
    
    private String imageResultMessage;
    
    private String id;
    
    private static final String UPLOADPATH = SystemConfig.getProperty("gameServer.logexe.image.upload.path");
    
    //登录器图片列表
    public String queryLogexeImageList() throws Exception{
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
			PaginationResult<HomepageItem> logexeImagePageationResult = homepageService.getItemListOrderByParam(pageInfo, AppConstants.HOMEPAGE_IMAGE_TYPE_ID, AppConstants.HOMEPAGE_ORDER_PARAM, false);
			requestMap.put("pageationResult", logexeImagePageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("Admin Query index flash List Error:" + e.getMessage(), e);
		}

		return result;
    }
    
	
    //创建登录器图片准备
    public String createLogexeImagePrepare() throws Exception{
    	String result = INPUT;
    	result = SUCCESS;
    	return result;
    }
    
    //创建登录器图片提交
    public String createLogexeImageSubmit() throws Exception{
    	String result = INPUT;
		String newName = "";
		newName = AppConstants.HOMEPAGE_IMAGE_PRE_NAME + imageFileFileName.substring(0, imageFileFileName.lastIndexOf("."));
		String position = "logExeImage_" + newName;
		String saveRealFilePath = SystemConfig.getProperty("server.image.realpath") + UPLOADPATH;  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    if(imageFileFileName.lastIndexOf(".")>=0){
	    	newName = newName + imageFileFileName.substring(imageFileFileName.lastIndexOf(".")).toLowerCase();
	    }
	    savefile = new File(saveRealFilePath + "/" + newName);
	    FileUtils.copyFile(imageFile,savefile);
	   
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			imageItem.setContent(newName);			
			imageItem.setName(newName);			
			imageItem.setTypeId(AppConstants.HOMEPAGE_IMAGE_TYPE_ID);
			imageItem.setPosition(position);
			imageItem.setCreateDate(new Date());
			String imageUrl = StringUtil.editUrlString(imageItem.getUrl());
			imageItem.setUrl(imageUrl);
			boolean createResult  = homepageService.insertItem(imageItem);
			if (createResult) {
				result = SUCCESS;
			} else {
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Create Image for start server Submit Error:" + e.getMessage(), e);
		}
		return result;
    }

    //编辑登录器图片准备
    public String editLogexeImagePrepare() throws Exception{
    	String result = INPUT;
    	HomepageService homepageService = ServiceLocator.getHomepageService();
    	if(!StringUtils.isBlank(id) && StringUtils.isNumeric(id)){
    		try {
				imageItem = homepageService.getItem(Integer.parseInt(id));
				result = SUCCESS;
			} catch (Exception e) {
				logger.error("get index flash by id error ", e);
			}
    	}
    	return result;
    }
	
	//编辑登录器图片提交
	public String editLogexeImageSubmit() throws Exception{
		String result = INPUT;
		if(imageFile != null){
			String newName = "";
			String saveRealFilePath = SystemConfig.getProperty("server.image.realpath") + UPLOADPATH;  
			File fileDir = new File(saveRealFilePath);  
		    if (!fileDir.exists()) {  
		        fileDir.mkdirs();  
		    } 
		    newName = imageItem.getContent();
		    File savefile = new File(saveRealFilePath + "/" + newName);
		    FileUtils.copyFile(imageFile,savefile);
		}
		String imageUrl = StringUtil.editUrlString(imageItem.getUrl());
		imageItem.setUrl(imageUrl);
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			boolean editResult = homepageService.update(imageItem);
			if (editResult) {
				result = SUCCESS;
			} else {
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Create Image for start server Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	//删除image信息
    public String removeLogexeImage(){
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

	public HomepageItem getImageItem() {
		return imageItem;
	}

	public void setImageItem(HomepageItem imageItem) {
		this.imageItem = imageItem;
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

	public File getImageFile() {
		return imageFile;
	}


	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}

	public String getImageFileFileName() {
		return imageFileFileName;
	}

	public void setImageFileFileName(String imageFileFileName) {
		this.imageFileFileName = imageFileFileName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public String getImageResultMessage() {
		return imageResultMessage;
	}

	public void setImageResultMessage(String imageResultMessage) {
		this.imageResultMessage = imageResultMessage;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	


	


}
