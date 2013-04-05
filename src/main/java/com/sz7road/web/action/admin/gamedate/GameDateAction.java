package com.sz7road.web.action.admin.gamedate;

import java.io.File;
import java.util.ArrayList;
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
import com.sz7road.web.action.admin.usermanage.UserManageAction;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gameDateMag.GameDate;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameDateSer;
import com.sz7road.web.service.NewsService;

public class GameDateAction extends ActionSupport implements RequestAware, SessionAware{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(UserManageAction.class);
	private static final int FILE_MAX_SIZE = 1024 * 40;
	private Map<String, Object> requestMap;
	private Map<String, Object> sessionMap;
	private PageInfo pager;
	private String filedataFileName;
	private File filedata;
	private String err;
	private String msg;
	private String message;
	

	//
	private static GameDateSer gameSer = ServiceLocator.getGameDateSer();
	private static NewsService newsSer = ServiceLocator.getNewsService();
	private String id;
	private static List<GameDateType> types = new ArrayList<GameDateType>();
	private static List<NewsState> states = new ArrayList<NewsState>();
	static{
		try {
			types = gameSer.getAllType();
			states = newsSer.getStates();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private GameDateEx gameDate;
	
	public String queryList(){
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
			PaginationResult<GameDateEx> pageationResult = gameSer.readAllGameDate(pageInfo);
			requestMap.put("pageationResult", pageationResult);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("query gameDate error" + e.getMessage(), e);
		}
		return result;
	}

	public String editGameDate(){
		
		String result = INPUT;
		int gameId = 0;
		if (!StringUtils.isBlank(id) && StringUtils.isNumeric(id)) {
			gameId = Integer.parseInt(id);
		}
		try {
			types = gameSer.getAllType();
			states = newsSer.getStates();
			gameDate = gameSer.readGameDateById(gameId);
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("edit gameDate error" + e.getMessage(), e);
		}
		return result;
	}
	
	public String editGameDateSubmit(){
		String result = INPUT;
		try {
			GameDate game = new GameDate();
			game.setId(gameDate.getId());
			game.setArtTitle(gameDate.getArtTitle());
			game.setContent(gameDate.getContent());
			game.setStateId(gameDate.getStateId());
			game.setTypeId(gameDate.getTypeId());
			if(gameDate.getOrderNum().trim().equals("")){
				game.setOrderNum("0");
			}
			game.setOrderNum(gameDate.getOrderNum());
			boolean flag = gameSer.updateGameDate(game);
			if(flag){
				result = SUCCESS;
			}
		} catch (Exception e) {
			result = INPUT;
			logger.error("edit submit gameDate error" + e.getMessage(), e);
		}
		return result;
	}
	


	public String createGameDate(){
		String result = INPUT;
		
		try {
			types = gameSer.getAllType();
			states = newsSer.getStates();
			result = SUCCESS;
		} catch (Exception e) {
			result = INPUT;
			logger.error("create gameDate error" + e.getMessage(), e);
		}

		return result;
	}
	
	public String createGameDateSubmit(){
		String result = INPUT;
		try {
			GameDate game = new GameDate();
			game.setArtTitle(gameDate.getArtTitle());
			game.setContent(gameDate.getContent());
			game.setStateId(gameDate.getStateId());
			game.setTypeId(gameDate.getTypeId());
			if(gameDate.getOrderNum().trim().equals("")){
				game.setOrderNum("0");
			}
			game.setOrderNum(gameDate.getOrderNum());
			boolean flag = gameSer.createGameDate(game);
			if(flag){
				result = SUCCESS;
			}
		} catch (Exception e) {
			result = INPUT;
			logger.error("create submit gameDate error" + e.getMessage(), e);
		}
		return result;
	}
	
	public String deleteGameDateSubmit(){
		String result = INPUT;
		String[] s = id.split(",");
		int gameId = 0;
		for(int i = 0; i < s.length; i++){
			if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
				gameId = Integer.parseInt(s[i]);
			}
			boolean deleteResult;
			try {
				deleteResult = gameSer.deleteGameDate(gameId);
				if (deleteResult) {
					result = SUCCESS;
				}
			} catch (Exception e) {
				result = INPUT;
				logger.error("delete submit gameDate error" + e.getMessage(), e);
			}
		}
		return result;
	}
	
	public String upload() throws Exception {
//		long size = filedata.length();
//		//判断图片大小是否在40kb以内
//		if(size > FILE_MAX_SIZE) {
//			msg = "";
//			err = "图片大于" + FILE_MAX_SIZE / 1024 + "KB，请重新上传。";
//			printInfo(err, msg);
//			return SUCCESS;
//		}
		String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image");
		File fileDir = new File(saveRealFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File savefile;
		String newName = "";
		long nowTime = new Date().getTime();
		if (filedataFileName.lastIndexOf(".") >= 0) {
			newName = nowTime + filedataFileName.substring(filedataFileName.lastIndexOf("."));
		}
		savefile = new File(saveRealFilePath + "/" + newName);
		if (!SystemConfig.getProperty("server.image.realpath").equals("")) {
			File savefile1 = new File(SystemConfig.getProperty("server.image.realpath") + "/" + newName);
			FileUtils.copyFile(filedata, savefile1);
		}
		FileUtils.copyFile(filedata, savefile);
		msg = SystemConfig.getProperty("image.server.host") + "/" + newName;
		err = "";
		printInfo(err, msg);
		return SUCCESS;
	}
	
	public void printInfo(String err, String newFileName) {
		message = "{\"err\":\"" + err + "\",\"msg\":\"" + newFileName + "\"}";
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}
	@Override
	public void setRequest(Map<String, Object> request) {
		this.requestMap = request;
	}
	public PageInfo getPager() {
		return pager;
	}
	public void setPager(PageInfo pager) {
		this.pager = pager;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<GameDateType> getTypes() {
		return types;
	}

	public void setTypes(List<GameDateType> types) {
		this.types = types;
	}

	public List<NewsState> getStates() {
		return states;
	}

	public void setStates(List<NewsState> states) {
		this.states = states;
	}

	public GameDateEx getGameDate() {
		return gameDate;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}
	
	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}
	
	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setGameDate(GameDateEx gameDate) {
		this.gameDate = gameDate;
	}
}
