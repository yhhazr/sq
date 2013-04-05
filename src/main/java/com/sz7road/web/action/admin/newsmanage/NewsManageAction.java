package com.sz7road.web.action.admin.newsmanage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
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
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.newsmanage.NewsState;
import com.sz7road.web.model.newsmanage.NewsType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.NewsService;

public class NewsManageAction extends ActionSupport implements RequestAware, SessionAware {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(UserManageAction.class);

    private static final int FILE_MAX_SIZE = 1024 * 40;

    private static List<NewsType> newsTypeList = new ArrayList<NewsType>();

    private static List<NewsState> newsStateList = new ArrayList<NewsState>();

    private News news;

    private File filedata;

    //	private String filedataContentType;

    private String filedataFileName;

    private String err;

    private String msg;

    private String message;

    private PageInfo pager;

    private String newsId;

    private Map<String, Object> requestMap;

    private Map<String, Object> sessionMap;

    //	private String fileExt = "jpg,jpeg,gif,bmp,png";

    private String selectCondition;

    //文件上传
    private File uploadPath;
    private String uploadPathFileName;
    //	private String uploadPathContentType;
    private ServletContext context;

    private static String new_img = null;

    static {
        try {
            NewsService newsService = ServiceLocator.getNewsService();
            newsStateList = newsService.getStates();
            newsTypeList = newsService.getTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
    * 排除在html编辑器中只有空格的情况
    * @author xin.fang
    * @param content
    * @return
    * @createDate 2012-11-13 上午11:11:34
    */
    public boolean gameDataContentIsEmpty(String content) {
        String formatContent = content.replaceAll("&nbsp;", "");
        if (formatContent.replaceAll("<br />", "").trim().equals("")) {
            return true;
        }
        return false;
    }

    public String editNewsSubmit() {
        String result = INPUT;
        NewsService service = ServiceLocator.getNewsService();
        try {
            News newsInfo = new News();
            newsInfo.setArtTitle(news.getArtTitle());
            newsInfo.setExp1("0");
            newsInfo.setExp2("0");
            newsInfo.setExp3("0");
            newsInfo.setExp4("0");
            newsInfo.setExp5("0");
            newsInfo.setStateId(news.getStateId());
            newsInfo.setTypeId(news.getTypeId());
            newsInfo.setNewsId(news.getNewsId());
            if (gameDataContentIsEmpty(news.getNewsContent())) {
                this.addFieldError("news.newsContent", this.getText("admin.news.validate.newsContent.null.error"));
                return INPUT;
            }
            newsInfo.setNewsContent(news.getNewsContent());
            
            if (uploadPath == null) {
                newsInfo.setNewsImg(new_img);
            } else {
                String realPath = ServletActionContext.getServletContext().getRealPath("/upload/image");
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdir();
                }
                UUID uuid = UUID.randomUUID();
                StringBuilder str = new StringBuilder();
                str.append(uuid.toString());
                str.append(uploadPathFileName.substring(uploadPathFileName.lastIndexOf(".")));
                String picName = str.toString();
                File target = new File(realPath, picName);
                newsInfo.setNewsImg(picName);
                FileUtils.copyFile(uploadPath, target);
                if (!SystemConfig.getProperty("server.image.realpath").equals("")) {
                    File savefile = new File(SystemConfig.getProperty("server.image.realpath") + "/" + picName);
                    FileUtils.copyFile(uploadPath, savefile);
                }
            }

            boolean updateResult = service.updateNews(newsInfo);
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

    public String editNews() {
        String result = INPUT;
        NewsService service = ServiceLocator.getNewsService();
        int newsIdInt = 0;
        try {
            if (!StringUtils.isBlank(newsId) && StringUtils.isNumeric(newsId)) {
                newsIdInt = Integer.parseInt(newsId);
            }
            this.getNewsStateList();
            this.getNewsTypeList();
            news = service.getNewsById(newsIdInt);
            new_img = news.getNewsImg();
            File fls = new File(SystemConfig.getProperty("server.image.realpath") + "/" + new_img);
            uploadPath = fls;
            if (news != null && !StringUtils.isBlank(news.getArtTitle())) {
                result = SUCCESS;
            }
        } catch (Exception e) {
            logger.error("Admin Edit News Error:" + e.getMessage(), e);
            result = INPUT;
        }
        return result;
    }

    public String deleteNews() {
        String result = INPUT;
        NewsService service = ServiceLocator.getNewsService();
        int newsIdInt = 0;
        try {
            String[] s = newsId.split(",");
            for (int i = 0; i < s.length; i++) {
                if (!StringUtils.isBlank(s[i]) && StringUtils.isNumeric(s[i])) {
                    newsIdInt = Integer.parseInt(s[i]);
                }
                boolean deleteResult = service.deleteNews(newsIdInt);
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
    

    public String createNewsSubmit() {
        String result = INPUT;
        NewsService newsService = ServiceLocator.getNewsService();
        String realPath = ServletActionContext.getServletContext().getRealPath("/upload/image");
        File target = null;
        String picName = null;
        if (uploadPath != null && uploadPath.exists()) {
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdir();
            }
            UUID uuid = UUID.randomUUID();
            StringBuilder str = new StringBuilder();
            str.append(uuid.toString());
            str.append(uploadPathFileName.substring(uploadPathFileName.lastIndexOf(".")));
            picName = str.toString();
            target = new File(realPath, picName);
        }

        try {
            News newsInfo = new News();
            newsInfo.setArtTitle(news.getArtTitle());
            if (gameDataContentIsEmpty(news.getNewsContent())) {
                this.addFieldError("news.newsContent", this.getText("admin.news.validate.newsContent.null.error"));
                return INPUT;
            }
            newsInfo.setNewsContent(news.getNewsContent());
            newsInfo.setTypeId(news.getTypeId());
            newsInfo.setStateId(news.getStateId());
            newsInfo.setNewsImg(picName);
            
            boolean createResult = newsService.insertNews(newsInfo);
            if (createResult) {
                if (target != null && target.exists()) {
                    FileUtils.copyFile(uploadPath, target);
                    if (!SystemConfig.getProperty("server.image.realpath").equals("")) {
                        File savefile = new File(SystemConfig.getProperty("server.image.realpath") + "/" + picName);
                        FileUtils.copyFile(uploadPath, savefile);
                    }
                }
                result = SUCCESS;
            } else {
                result = INPUT;
            }
        } catch (Exception e) {
            logger.error("Admin Create News Submit Error:" + e.getMessage(), e);
        }
        return result;
    }

    public String createNews() {
        String result = INPUT;
        try {
            this.getNewsTypeList();
            this.getNewsStateList();
            result = SUCCESS;
        } catch (Exception e) {
            logger.error("Admin Create News Error:" + e.getMessage(), e);
            result = INPUT;
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
        //			//解决xheditor upload流被关闭保存问题
        //			filedata.delete();
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

    public String queryNewsList() {
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
            NewsService newsService = ServiceLocator.getNewsService();
            PaginationResult<News> pageationResult = newsService.getNews(pageInfo);
            List<News> newsList = pageationResult.getResultList();
            //			logger.info("######pagerOffset:" + pagerOffset);
            //			logger.info("######startRow:" + startRow);
            //			for(News news : newsList){
            //				logger.info("######newsId:" + news.getNewsId());
            //				logger.info("######newsTitle:" + news.getArtTitle());
            //				logger.info("##########################################################");
            //			}
            requestMap.put("pageationResult", pageationResult);
            result = SUCCESS;
        } catch (Exception e) {
            result = INPUT;
            logger.error("Admin Query News List Error:" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 根据标题或者标题关键字查询新闻
     * 创建时间： 2013-3-1 下午2:39:42
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String queryNewsListByTitleKeyword() {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
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
            NewsService newsService = ServiceLocator.getNewsService();
            PaginationResult<News> pageationResult = null;
            if (StringUtils.isBlank(selectCondition)) {
                pageationResult = newsService.getNews(pageInfo);
            } else {
                pageationResult = newsService.queryNewsListByTitleKeyword(pageInfo, selectCondition);
            }
            requestMap.put("pageationResult", pageationResult);
            result = SUCCESS;
        } catch (Exception e) {
            result = INPUT;
            logger.error("Admin Query News List by News title keyword Error:" + e.getMessage(), e);
        }
        return result;
    }

    public File getFiledata() {
        return filedata;
    }

    public void setFiledata(File filedata) {
        this.filedata = filedata;
    }

    //	public String getFiledataContentType() {
    //		return filedataContentType;
    //	}
    //
    //	public void setFiledataContentType(String filedataContentType) {
    //		this.filedataContentType = filedataContentType;
    //	}

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

    //	public String getFileExt() {
    //		return fileExt;
    //	}
    //
    //	public void setFileExt(String fileExt) {
    //		this.fileExt = fileExt;
    //	}

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public PageInfo getPager() {
        return pager;
    }

    public void setPager(PageInfo pager) {
        this.pager = pager;
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

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public File getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(File uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getUploadPathFileName() {
        return uploadPathFileName;
    }

    public void setUploadPathFileName(String uploadPathFileName) {
        this.uploadPathFileName = uploadPathFileName;
    }

    //	public String getUploadPathContentType() {
    //		return uploadPathContentType;
    //	}
    //
    //	public void setUploadPathContentType(String uploadPathContentType) {
    //		this.uploadPathContentType = uploadPathContentType;
    //	}

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public List<NewsType> getNewsTypeList() {
        return newsTypeList;
    }

    public void setNewsTypeList(List<NewsType> newsTypeList) {
        NewsManageAction.newsTypeList = newsTypeList;
    }

    public List<NewsState> getNewsStateList() {
        return newsStateList;
    }

    public void setNewsStateList(List<NewsState> newsStateList) {
        NewsManageAction.newsStateList = newsStateList;
    }

    public String getSelectCondition() {
        return selectCondition;
    }

    public void setSelectCondition(String selectCondition) {
        this.selectCondition = selectCondition;
    }

}
