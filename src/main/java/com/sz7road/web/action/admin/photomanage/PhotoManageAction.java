package com.sz7road.web.action.admin.photomanage;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.photomanage.Photo;
import com.sz7road.web.model.photomanage.PhotoCat;
import com.sz7road.web.service.PhotoService;

public class PhotoManageAction extends ActionSupport implements RequestAware, SessionAware{
	
	private static final long serialVersionUID = 7093364827897194720L;

	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	
	private File uploadify;
	
	private File catImage;
	
	private String catImageFileName;
	 
	private String uploadifyFileName;
	 
	private String uploadifyContentType;
	
	private String catId;
	
	private String photoId;
	
	private String width;
	
	private String height;
	
	private String marginLeft;
	
	private String marginTop;
	
	private String photoName;
	
	private String result;
	
	private String photoShow;
	
	private PhotoCat photoCat;
	
	private Photo photo;
	
	private PageInfo pager;
	
	private Map<String, Object> requestMap;
    
    private Map<String, Object> sessionMap;
    
    public String deletePhoto(){
    	String result = INPUT;
		PhotoService service = ServiceLocator.getPhotoService();
		Photo photoInfo = null;
		int photoIdInt = 0;
		try {
			String[] s = photoId.split(",");
			for (int i = 0; i < s.length; i++) {
				if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
					photoIdInt = Integer.parseInt(s[i]);
				}
				photoInfo = service.getPhotoById(photoIdInt);
				boolean deleteResult = false;
				if(null != photoInfo){
					deleteResult = service.deletePhotoByPhotoId(photoIdInt);
				}
				if (deleteResult) {
					result = SUCCESS;
				}
			}
			catId = String.valueOf(photoInfo.getCatId());
			ServletActionContext.getRequest().setAttribute("catId", catId);
		} catch (Exception e) {
			logger.error("Admin Delete News Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String editPhotoCatSubmit(){
    	String result = INPUT;
		PhotoService service = ServiceLocator.getPhotoService();
		try {
				PhotoCat photoCatInfo = new PhotoCat();
				photoCatInfo.setCatId(photoCat.getCatId());
				photoCatInfo.setCatDesc(photoCat.getCatDesc());
				photoCatInfo.setCatName(photoCat.getCatName());
				photoCatInfo.setHomepageShow(photoCat.isHomepageShow());
				if(catImage == null){
					photoCatInfo.setImageUrl(photoCat.getImageUrl());
				}else{
					String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image");  
					File fileDir = new File(saveRealFilePath);  
				    if (!fileDir.exists()) {  
				        fileDir.mkdirs();  
				    } 
				    File savefile; 
				    String newName = "";
				    String nowTime = ""+new Date().getTime();
				    if(catImageFileName.lastIndexOf(".")>=0){
				    	newName = nowTime + catImageFileName.substring(catImageFileName.lastIndexOf("."));
				    }
				    savefile = new File(saveRealFilePath + "/" + newName);
				    FileUtils.copyFile(catImage,savefile);
//				    FTP ftp = new FTP(savefile, newName, SystemConfig.getProperty("admin.ftp.image.path"));    
//				    Thread thread = new Thread(ftp);
//				    thread.start();
				    photoCatInfo.setImageUrl(newName);
				}
				
				boolean updateResult = service.updatePhotoCat(photoCatInfo);
				if (updateResult) {
					result = SUCCESS;
				
				} else {
					result = INPUT;
				}

		} catch (Exception e) {
			logger.error("Admin Edit News Submit Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String editPhotoCat(){
    	String result = INPUT;
		PhotoService service = ServiceLocator.getPhotoService();
		int photoCatIdInt = 0;
		try {
			if (!StringUtils.isBlank(catId) && StringUtils.isNumeric(catId)) {
				photoCatIdInt = Integer.parseInt(catId);
			}
			photoCat = service.getPhotoCatById(photoCatIdInt);
			if (photoCat != null && !StringUtils.isBlank(photoCat.getCatName())) {
				result = SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Admin Edit News Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
    
    public String deletePhotoCat(){
    	String result = INPUT;
		PhotoService service = ServiceLocator.getPhotoService();
		int photoCatIdInt = 0;
		try {
			String[] s = catId.split(",");
			for (int i = 0; i < s.length; i++) {
				if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
					photoCatIdInt = Integer.parseInt(s[i]);
				}
				boolean deleteResult = service.deletePhotoCat(photoCatIdInt);
				if (deleteResult) {
					result = SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("Admin Delete News Error:" + e.getMessage(), e);
			result = INPUT;
		}
		return result;
    }
	
	public String queryPhotoCatList(){
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
				PhotoService photoService = ServiceLocator.getPhotoService();
				PaginationResult<PhotoCat> pageationResult = photoService.getPhotoCatList(pageInfo);
				requestMap.put("pageationResult", pageationResult);
				result = SUCCESS;
			} catch (Exception e) {
				result = INPUT;
				logger.error("Admin Query PhotoCat List Error:" + e.getMessage(), e);
			}
			return result;
	}
	
	public String createPhotoCat(){
		return SUCCESS;
	}
	
	public String createPhotoCatSubmit() throws Exception{
		String result = INPUT;
		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image");  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    String newName = "";
	    long nowTime = new Date().getTime();
	    if(catImageFileName.lastIndexOf(".")>=0){
	    	newName = nowTime + catImageFileName.substring(catImageFileName.lastIndexOf("."));
	    }
	    savefile = new File(saveRealFilePath + "/" + newName);
	    FileUtils.copyFile(catImage,savefile);
//	    FTP ftp = new FTP(savefile, newName, SystemConfig.getProperty("admin.ftp.image.path"));    
//	    Thread thread = new Thread(ftp);
//	    thread.start();
		PhotoService photoService = ServiceLocator.getPhotoService();
		try {
			PhotoCat photoCatInfo = new PhotoCat();
			photoCatInfo.setCatDesc(photoCat.getCatDesc());
			photoCatInfo.setCatName(photoCat.getCatName());
			photoCatInfo.setHomepageShow(photoCat.isHomepageShow());
			photoCatInfo.setImageUrl(newName);		
			boolean createResult = photoService.insertPhotoCat(photoCatInfo);
			if(createResult){
				result = SUCCESS;
			}else{
				result = INPUT;
			}
		}catch (Exception e) {
			logger.error("Admin Create PhotoCat Submit Error:" + e.getMessage(), e);
		}
		return result;
	}
	
	public String uploadPhoto()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		PhotoService photoService = ServiceLocator.getPhotoService();
		List<Photo> photoList = photoService.getPhotoList(Integer.parseInt(catId));
		request.setAttribute("photoList", photoList);
		
		PageInfo pageInfo = new PageInfo();
		int startRow = 0;
		String pagerOffset = request.getParameter("pager.offset");
		int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
		if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
			startRow = Integer.parseInt(pagerOffset);
		}
		pageInfo.setStartRow(startRow);
		pageInfo.setPageSize(pageSize);
		PaginationResult<Photo> pageationResult = photoService.getPhotoList(pageInfo, Integer.parseInt(catId));
		requestMap.put("pageationResult", pageationResult);
		
		photoShowStorage();
		return SUCCESS;
	}
	
	
	public String upload()throws Exception{
		String newName = "";
		PhotoService photoService = ServiceLocator.getPhotoService(); 
		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/photo");  
		File fileDir = new File(saveRealFilePath);  
	    if (!fileDir.exists()) {  
	        fileDir.mkdirs();  
	    } 
	    File savefile; 
	    if(uploadifyFileName.lastIndexOf(".")>=0){
	    	newName = UUID.randomUUID() + uploadifyFileName.substring(uploadifyFileName.lastIndexOf("."));
	    }
	    savefile = new File(saveRealFilePath + "/"+ newName);
	    try {
			Photo photo  = new Photo();
			photo.setCatId(Integer.parseInt(catId));
			photo.setPhotoName(newName);
			photo.setPhotoShow(photoShow);
			photo.setTitle("神曲图片");
			boolean result = photoService.insertPhoto(photo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    FileUtils.copyFile(uploadify,savefile);	 
	    String saveFilePath = SystemConfig.getProperty("server.image.realpath")+"/photo";
	    File saveFileDir = new File(saveFilePath);
	    if(!saveFileDir.exists()){
	    	saveFileDir.mkdirs();
	    }
	    File savefile2 = new File(saveFilePath+"/"+newName);
	    FileUtils.copyFile(uploadify,savefile2);
	    //FTP ftp = new FTP(savefile, newName, SystemConfig.getProperty("admin.ftp.photo.path"));    
	    //Thread thread = new Thread(ftp);
	   // thread.start();
	    HttpServletResponse response = ServletActionContext.getResponse();
	    response.setCharacterEncoding("utf-8");
	    response.getWriter().print(uploadifyFileName);
	    return null;
	}

	public String editPhoto(){
		int photoIdInt = 0;
		PhotoService photoService = ServiceLocator.getPhotoService(); 
		photoShowStorage();
		try {
			if (!StringUtils.isBlank(photoId) && StringUtils.isNumeric(photoId)) {
				photoIdInt = Integer.parseInt(photoId);
			}
			photo = photoService.getPhotoById(photoIdInt);
			photoCat = photoService.getPhotoCatById(photo.getCatId()); 
			if (photo != null && !StringUtils.isBlank(photo.getPhotoName())) {
				return SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return INPUT;
		}
		return INPUT;
	}
	
	public String editPhotoSubmit(){
		try {
			PhotoService photoService = ServiceLocator.getPhotoService(); 
			Photo photoInfo = new Photo();
			PhotoCat photoCatInfo = photoService.getPhotoCatByName(photoCat.getCatName());
			catId = String.valueOf(photoCatInfo.getCatId());
			photoInfo.setCatId(photoCatInfo.getCatId());
			photoInfo.setPhotoId(photo.getPhotoId());
			photoInfo.setPhotoName(photo.getPhotoName());
			photoInfo.setPhotoShow(photo.getPhotoShow());
			photoInfo.setTitle(photo.getTitle());
			photoInfo.setThumbnail(photoService.getPhotoById(photo.getPhotoId()).getThumbnail());
			boolean updateResult = photoService.editPhoto(photoInfo);
			ServletActionContext.getRequest().setAttribute("catId", photoCatInfo.getCatId());
			if(updateResult){
				return SUCCESS;
			}else{
				return INPUT;
			}
		} catch (Exception e) {
			logger.error("edit photo is failure", e);
			return INPUT;
		}
	}
	
	public String saveThumbnail(){
		try {
			PhotoService photoService = ServiceLocator.getPhotoService(); 
			Photo photoInfo = new Photo();
			photoInfo = photoService.getPhotoById(Integer.parseInt(photoId));
			File file = new File(SystemConfig.getProperty("server.image.realpath")+"/photo");
			if(!file.exists()){
				file.mkdirs();
			}
			Thumbnails.of(SystemConfig.getProperty("server.image.realpath")+"/photo/"+photoInfo.getPhotoName())
				.sourceRegion(Integer.parseInt(marginLeft), Integer.parseInt(marginTop), Integer.parseInt(width), Integer.parseInt(height))
				.size(162, 148).keepAspectRatio(false)
				.toFile(SystemConfig.getProperty("server.image.realpath")+"/photo"+"/s-"+photoInfo.getPhotoName());
			photoInfo.setThumbnail("s-"+photoInfo.getPhotoName());
			if(photoService.editPhoto(photoInfo)){
				result = "true";
			}else{
				result = "false";
			}
		} catch (Exception e) {
			logger.error("save thumbnail is failure");
			result = "false";
		}
		return SUCCESS;
	}
	
	public String deleteThumbnail(){
		try {
			PhotoService photoService = ServiceLocator.getPhotoService(); 
			Photo photoInfo = new Photo();
			photoInfo = photoService.getPhotoById(Integer.parseInt(photoId));
			photoInfo.setThumbnail(null);
			if(photoService.editPhoto(photoInfo)){
				result = "true";
				return SUCCESS;
			}else{
				result = "false";
				return INPUT;
			}
		} catch (Exception e) {
			logger.error("delete thumbnail is failure");
			return INPUT;
		}
	}
	
	public void photoShowStorage(){
		Map<String, String> photoShowMap = new HashMap<String, String>();
		photoShowMap.put("false", this.getText("admin.photo.show.hidden"));
		photoShowMap.put("true", this.getText("admin.photo.show.block"));
		requestMap.put("photoShowMap", photoShowMap);
	}
	@JSON(serialize = false)
	public File getUploadify() {
		return uploadify;
	}
	
	public void setUploadify(File uploadify) {
		this.uploadify = uploadify;
	}
	@JSON(serialize = false)
	public String getUploadifyFileName() {
		return uploadifyFileName;
	}
	
	public void setUploadifyFileName(String uploadifyFileName) {
		this.uploadifyFileName = uploadifyFileName;
	}
	@JSON(serialize = false)
	public String getUploadifyContentType() {
		return uploadifyContentType;
	}
	
	public void setUploadifyContentType(String uploadifyContentType) {
		this.uploadifyContentType = uploadifyContentType;
	}
	@JSON(serialize = false)
	public String getCatId() {
		return catId;
	}
	
	public void setCatId(String catId) {
		this.catId = catId;
	}
	@JSON(serialize = false)
	public PhotoCat getPhotoCat() {
		return photoCat;
	}

	public void setPhotoCat(PhotoCat photoCat) {
		this.photoCat = photoCat;
	}
	@JSON(serialize = false)
	public File getCatImage() {
		return catImage;
	}

	public void setCatImage(File catImage) {
		this.catImage = catImage;
	}
	@JSON(serialize = false)
	public String getCatImageFileName() {
		return catImageFileName;
	}

	public void setCatImageFileName(String catImageFileName) {
		this.catImageFileName = catImageFileName;
	}
	@JSON(serialize = false)
	public static Logger getLogger() {
		return logger;
	}
	@JSON(serialize = false)
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequest(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}
	
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	@JSON(serialize = false)
	public PageInfo getPager() {
		return pager;
	}

	public void setPager(PageInfo pager) {
		this.pager = pager;
	}
	@JSON(serialize = false)
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	@JSON(serialize = false)
	public String getPhotoShow() {
		return photoShow;
	}

	public void setPhotoShow(String photoShow) {
		this.photoShow = photoShow;
	}
	@JSON(serialize = false)
	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	@JSON(serialize = false)
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	@JSON(serialize = false)
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	@JSON(serialize = false)
	public String getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}
	@JSON(serialize = false)
	public String getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
