package com.sz7road.web.action.admin.homepagemanage;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils; 
import org.apache.http.protocol.HTTP;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.common.util.StringUtil;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.HomepageService;
import com.sz7road.web.service.OnlineUserService;


public class HomepageManageAction extends ActionSupport implements RequestAware, SessionAware{

	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	
	private static final String FILE_TAG_NAME = "file";
	private static final String POSITION_MEDIA = "media";
	private static final int MEDIA_MAX_SIZE = 5 * 1024;
	
	private HomepageItem homepageItem;
	
	private File file;
	
	private String fileFileName;
	
	private Map<String, Object> requestMap;
    
    private Map<String, Object> sessionMap;
    
    private String id;
    
    private String typeId;
    
    private PageInfo pager;
    
    public String editFlashSubmit(){
    	String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		try {
				HomepageItem info = new HomepageItem();
				info.setName(homepageItem.getName());
				info.setPosition(homepageItem.getPosition());
				info.setId(homepageItem.getId());
				if(file == null){
					info.setContent(homepageItem.getContent());
				}else{
					String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/flash");  
					File fileDir = new File(saveRealFilePath);  
				    if (!fileDir.exists()) {  
				        fileDir.mkdirs();  
				    } 
				    File savefile; 
				    String newName = "";
				    String nowTime = ""+new Date().getTime();
				    if(fileFileName.lastIndexOf(".")>=0){
				    	newName = nowTime + fileFileName.substring(fileFileName.lastIndexOf("."));
				    }
				    savefile = new File(saveRealFilePath + "/" + newName);
				    FileUtils.copyFile(file,savefile);
				    info.setContent(newName);
				}				
				boolean updateResult = service.update(info);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit Flash Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String editImageSubmit(){
    	String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		try {
				HomepageItem info = new HomepageItem();
				info.setName(homepageItem.getName());
				info.setPosition(homepageItem.getPosition());
				info.setId(homepageItem.getId());
				String imageUrl = StringUtil.editUrlString(homepageItem.getUrl());
				info.setUrl(imageUrl);
				if(file == null){
					info.setContent(homepageItem.getContent());
				}else{
					String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image");  
					File fileDir = new File(saveRealFilePath);  
				    if (!fileDir.exists()) {  
				        fileDir.mkdirs();  
				    } 
				    File savefile; 
				    String newName = "";
				    String nowTime = ""+new Date().getTime();
				    if(fileFileName.lastIndexOf(".")>=0){
				    	newName = nowTime + fileFileName.substring(fileFileName.lastIndexOf("."));
				    }
				    savefile = new File(saveRealFilePath + "/" + newName);
				    if(!SystemConfig.getProperty("server.image.realpath").equals("")){
				    	File savefile1 = new File(SystemConfig.getProperty("server.image.realpath") + "/" + newName);
				    	FileUtils.copyFile(file,savefile1);
				    }	
				    FileUtils.copyFile(file,savefile);
				    info.setContent(newName);
				}				
				boolean updateResult = service.update(info);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit Flash Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String editItem(){
    	String result = INPUT;
    	HomepageService service = ServiceLocator.getHomepageService();
    	int itemIdInt = 0;
		try {
			if (!StringUtils.isBlank(id) && StringUtils.isNumeric(id)) {
				itemIdInt = Integer.parseInt(id);
			}
			homepageItem = service.getItem(itemIdInt);
			if (homepageItem != null && !StringUtils.isBlank(homepageItem.getName())) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Edit Item Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String deleteItem(){
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
	public  String createItem(){
		return SUCCESS;
	}
	public String queryItemList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		 String result = INPUT;
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
				PaginationResult<HomepageItem> pageationResult = homepageService.getItemList(pageInfo,Integer.parseInt(typeId));
				requestMap.put("pageationResult", pageationResult);
				if("1".equals(typeId)){
					result = "flash";
				}else if("2".equals(typeId)){
					result = "media";
				}else if("3".equals(typeId)){
					result = "text";
				}else if("4".equals(typeId)){
					result = "image";
				}else if("5".equals(typeId)){
					result = "html";
				}		
			} catch (Exception e) {
				result = INPUT;
				logger.error("Admin Query Item List Error:" + e.getMessage(), e);
			}
			return result;
	}
	public String createFlashSubmit() throws Exception{
		String result = INPUT;
		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/flash");  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    String newName = "";
	    long nowTime = new Date().getTime();
	    if(fileFileName.lastIndexOf(".")>=0){
	    	newName = nowTime + fileFileName.substring(fileFileName.lastIndexOf("."));
	    }
	    savefile = new File(saveRealFilePath + "/" + newName);
	    FileUtils.copyFile(file,savefile);
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			HomepageItem itemInfo = new HomepageItem();
			itemInfo.setName(homepageItem.getName());
			itemInfo.setContent(newName);
			itemInfo.setPosition(homepageItem.getPosition());
			itemInfo.setTypeId(1);
			boolean createResult = homepageService.insertItem(itemInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create Flash Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	public String createImageSubmit() throws Exception{
		String result = INPUT;
		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image");  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    String newName = "";
	    long nowTime = new Date().getTime();
	    if(fileFileName.lastIndexOf(".")>=0){
	    	newName = nowTime + fileFileName.substring(fileFileName.lastIndexOf("."));
	    }
	    savefile = new File(saveRealFilePath + "/" + newName);
	    if(!SystemConfig.getProperty("server.image.realpath").equals("")){
	    	File savefile1 = new File(SystemConfig.getProperty("server.image.realpath") + "/" + newName);
	    	FileUtils.copyFile(file,savefile1);
	    }	
	    FileUtils.copyFile(file,savefile);
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			HomepageItem itemInfo = new HomepageItem();
			itemInfo.setName(homepageItem.getName());
			String imageUrl = StringUtil.editUrlString(homepageItem.getUrl());
			itemInfo.setUrl(imageUrl);
			itemInfo.setContent(newName);
			itemInfo.setPosition(homepageItem.getPosition());
			itemInfo.setTypeId(4);
			boolean createResult = homepageService.insertItem(itemInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create Image Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	public String createMediaSubmit() throws Exception{
		String result = INPUT;
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			HomepageItem itemInfo = new HomepageItem();
			itemInfo.setName(homepageItem.getName());
			itemInfo.setTypeId(2);
			itemInfo.setUrl(homepageItem.getUrl());
			itemInfo.setPosition(homepageItem.getPosition());
			boolean createResult = homepageService.insertItem(itemInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create Media Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	public String createTextSubmit() throws Exception{
		String result = INPUT;
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			HomepageItem itemInfo = new HomepageItem();
			itemInfo.setName(homepageItem.getName());
			itemInfo.setTypeId(3);
			itemInfo.setContent(homepageItem.getContent());
			itemInfo.setUrl(homepageItem.getUrl());
			itemInfo.setPosition(homepageItem.getPosition());
			boolean createResult = homepageService.insertItem(itemInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create Text Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	public String createHtmlSubmit() throws Exception{
		String result = INPUT;
		HomepageService homepageService = ServiceLocator.getHomepageService();
		try {
			HomepageItem itemInfo = new HomepageItem();
			itemInfo.setName(homepageItem.getName());
			itemInfo.setTypeId(5);
			itemInfo.setContent(homepageItem.getContent());
			itemInfo.setPosition(homepageItem.getPosition());
			boolean createResult = homepageService.insertItem(itemInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create Html Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	public String editMediaSubmit(){
		String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		try {
				HomepageItem info = new HomepageItem();
				info.setName(homepageItem.getName());
				info.setPosition(homepageItem.getPosition());
				info.setId(homepageItem.getId());
				info.setUrl(homepageItem.getUrl());
				boolean updateResult = service.update(info);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit Media Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}

	public String editTextSubmit(){
		String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		try {
				HomepageItem info = new HomepageItem();
				info.setName(homepageItem.getName());
				info.setPosition(homepageItem.getPosition());
				info.setId(homepageItem.getId());
				info.setContent(homepageItem.getContent());
				info.setUrl(homepageItem.getUrl());
				boolean updateResult = service.update(info);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit Text Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	
	public String editHtmlSubmit(){
		String result = INPUT;
		HomepageService service = ServiceLocator.getHomepageService();
		try {
				HomepageItem info = new HomepageItem();
				info.setName(homepageItem.getName());
				info.setPosition(homepageItem.getPosition());
				info.setId(homepageItem.getId());
				info.setContent(homepageItem.getContent());
				boolean updateResult = service.update(info);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit Media Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
	}
	
	//编辑图片验证
	public void validateEditImageSubmit() {
		if(homepageItem != null && POSITION_MEDIA.equals(homepageItem.getPosition())) {
			imageValidateImpl(MEDIA_MAX_SIZE, FILE_TAG_NAME);
		}
	}
	
	//新增图片验证
	public void validateCreateImageSubmit() {
		if(homepageItem != null && POSITION_MEDIA.equals(homepageItem.getPosition())) {
			imageValidateImpl(MEDIA_MAX_SIZE, FILE_TAG_NAME);
		}
	}
	
	//文件上传验证具体方法
	private void imageValidateImpl(int maxSize, String tag) {
		if(file != null) {
			long size = file.length();
			if(size > maxSize) {
				addFieldError(tag, "图片大于" + maxSize / 1024 + "KB，请重新上传");
			}
		}
	}

	public HomepageItem getHomepageItem() {
		return homepageItem;
	}

	public void setHomepageItem(HomepageItem homepageItem) {
		this.homepageItem = homepageItem;
	}

	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getFileFileName() {
		return fileFileName;
	}
	
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	public Map<String, Object> getRequestMap() {
		return requestMap;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public PageInfo getPager() {
		return pager;
	}

	public void setPager(PageInfo page) {
		this.pager = page;
	}
	
	
	
}
