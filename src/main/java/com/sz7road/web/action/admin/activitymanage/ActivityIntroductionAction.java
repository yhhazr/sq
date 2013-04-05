/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午2:52:50
 * 版本号： v1.0
*/

package com.sz7road.web.action.admin.activitymanage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.ActivityIncService;

/**
 * 类描述：活动简介管理
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午2:52:50
 * 版本号： v1.0
*/
public class ActivityIntroductionAction extends ActionSupport implements RequestAware, SessionAware {

    private static final long serialVersionUID = 3930338186251680234L;

    private Logger logger = Logger.getLogger(ActivityIntroductionAction.class);

    private Map<String, Object> requestMap;

    private Map<String, Object> sessionMap;

    private PageInfo pager;

    private ActivityInc activityInc;

    private int activityId;

    //文件上传
    private File filedata;
    private String filedataFileName;

    private String message;

    private String id;

    private String startDateStr;

    private String endDateStr;

    /**
     * 增加活动简介
     * 创建时间： 2013-2-22 下午2:56:00
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String createActivityIncSubmit() {
        String activityImgName = "";
        if (null != filedata && StringUtils.isNotBlank(filedataFileName)) {
            activityImgName = this.uploadFile();
            if (activityImgName.equals("failure")) {
                activityImgName = "";
                message = "系统繁忙，请稍后重新提交";
                return INPUT;
            }
        }

        ActivityInc inc = new ActivityInc();
        inc.setActivityName(activityInc.getActivityName());
        inc.setActivityIntroduction(activityInc.getActivityIntroduction());
        inc.setActivityImg(activityImgName);

        String url = activityInc.getActivityUrl();
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        inc.setActivityUrl(url);
        Date date = DateUtil.parse(startDateStr);
        inc.setStartDate(DateUtil.parse(startDateStr));
        inc.setEndDate(DateUtil.parse(endDateStr));

        ActivityIncService service = ServiceLocator.getActivityIncService();
        try {
            boolean isAddActivityInc = service.addActivityInc(inc);
            if (!isAddActivityInc) {
                logger.error("增加活动简介失败");
                message = "增加活动简介失败！";
                return INPUT;
            }
        } catch (Exception e) {
            logger.error("增加活动简介失败");
            e.printStackTrace();
            message = "增加活动简介失败";
            return INPUT;
        }

        return SUCCESS;
    }

    /**
     * 根据id查询活动简介
     * 创建时间： 2013-2-22 下午3:13:23
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String queryActivityIncById() {
        ActivityIncService service = ServiceLocator.getActivityIncService();
        try {
            activityInc = service.queryActivityIncById(activityId);
            startDateStr = DateUtil.format(activityInc.getStartDate());
            endDateStr = DateUtil.format(activityInc.getEndDate());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            message = "查询活动简介失败";
            return INPUT;
        }
        if (null == activityInc) {
            logger.error("利用id查询活动简介失败");
            message = "系统繁忙，请稍后重新提交";
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 编辑活动简介
     * 创建时间： 2013-2-22 下午2:56:45
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String editActivityIncSubmit() {
        String activityImgName = "";
        if (null != filedata && StringUtils.isNotBlank(filedataFileName)) {
            activityImgName = this.uploadFile();
            if (activityImgName.equals("failure")) {
                activityImgName = "";
                message = "系统繁忙，请稍后重新提交";
                return INPUT;
            }
        }

        ActivityInc inc = new ActivityInc();
        inc.setActivityId(activityInc.getActivityId());
        inc.setActivityName(activityInc.getActivityName());
        inc.setActivityIntroduction(activityInc.getActivityIntroduction());

        if (null == filedata) {
            inc.setActivityImg(activityInc.getActivityImg());
        } else {
            inc.setActivityImg(activityImgName);
        }

        String url = activityInc.getActivityUrl();
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        inc.setActivityUrl(url);

        inc.setStartDate(DateUtil.parse(startDateStr));
        inc.setEndDate(DateUtil.parse(endDateStr));

        ActivityIncService service = ServiceLocator.getActivityIncService();
        try {
            boolean isUpdate = service.editActivityInc(inc);
            if (!isUpdate) {
                message = "编辑活动简介失败！";
                return INPUT;
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            message = "编辑活动简介失败";
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 删除活动简介
     * 创建时间： 2013-2-22 下午2:57:35
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String deleteActivityInc() {
        ActivityIncService service = ServiceLocator.getActivityIncService();
        try {
            boolean isDelete = true;
            int idInt = 0;
            if (StringUtils.isNotBlank(id)) {
                String[] ids = id.split(",");
                for (String str : ids) {
                    if (StringUtils.isNumeric(str)) {
                        idInt = Integer.valueOf(str);
                        isDelete = isDelete && service.deleteActivityincById(idInt);
                    }
                }
            }

            if (!isDelete) {
                message = "删除活动简介失败";
                return INPUT;
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            message = "删除活动简介失败";
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 查询活动简介
     * 创建时间： 2013-2-22 下午2:58:16
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String queryActivityInc() {
        HttpServletRequest request = ServletActionContext.getRequest();
        ActivityIncService service = ServiceLocator.getActivityIncService();

        PageInfo pageInfo = new PageInfo();
        int startRow = 0;
        String pagerOffset = request.getParameter("pager.offset");
        int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
        if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
            startRow = Integer.parseInt(pagerOffset);
        }
        pageInfo.setStartRow(startRow);
        pageInfo.setPageSize(pageSize);

        try {
            PaginationResult<ActivityInc> paginationResult = service.queryActivityInc(pageInfo, 0);
            requestMap.put("paginationResult", paginationResult);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            message = "分页查询活动简介失败";
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 文件上传,并返回文件名
     * 创建时间： 2013-2-22 下午3:16:16
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    private String uploadFile() {
        String saveRealFilePath = ServletActionContext.getServletContext().getRealPath("/upload/image/activityImg");
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
        try {
            if (!SystemConfig.getProperty("server.image.realpath").equals("")) {
                File savefile1 = new File(SystemConfig.getProperty("server.image.realpath") + "/activityImg/" + newName);
                FileUtils.copyFile(filedata, savefile1);
            }
            FileUtils.copyFile(filedata, savefile);
        } catch (IOException e) {
            logger.error("上传的文件保存出错，" + e.getMessage());
            return "failure";
        }
        return newName;
    }

    /**
     * 创建活动简介的验证方法
     * 创建时间： 2013-2-26 下午4:17:34
     * 创建人：xin.fang
     * 参数： 
     * 返回值： void
     * 方法描述 :
    */
    public void validateCreateActivityIncSubmit() {
        this.fieldValidate();
        if (null == filedata) {
            this.addFieldError("filedata", this.getText("admin.activityInc.validate.image.null.error"));
        }
    }
    
    /**
     * 创建时间： 2013-2-26 下午4:17:58
     * 创建人：xin.fang
     * 参数： 
     * 返回值： void
     * 方法描述 : 编辑活动简介的验证方法
    */
    public void validateEditActivityIncSubmit(){
        this.fieldValidate();
    }
    
    /**
     * 创建时间： 2013-2-26 下午4:21:06
     * 创建人：xin.fang
     * 参数： 
     * 返回值： void
     * 方法描述 : 对相关属性的验证
    */
    private void fieldValidate(){
        if (StringUtils.isBlank(activityInc.getActivityName())) {
            this.addFieldError("activityInc.activityName", this.getText("admin.activityInc.validate.name.null.error"));
        }
        if (activityInc.getActivityName().length() > 50) {
            this.addFieldError("activityInc.activityName", this.getText("admin.activityInc.validate.name.length.error"));
        }

        if (StringUtils.isBlank(activityInc.getActivityUrl())) {
            this.addFieldError("activityInc.activityUrl",
                    this.getText("admin.activityInc.validate.activityUrl.null.error"));
        }
        
        if (activityInc.getActivityUrl().length() > 200) {
            this.addFieldError("activityInc.activityUrl",
                    this.getText("admin.activityInc.validate.activityUrl.length.error"));
        }

        if (StringUtils.isBlank(activityInc.getActivityIntroduction())) {
            this.addFieldError("activityInc.activityIntroduction",
                    this.getText("admin.activityInc.validate.activityIntroduction.null.error"));
        }

        if (activityInc.getActivityIntroduction().length() > 100) {
            this.addFieldError("activityInc.activityIntroduction",
                    this.getText("admin.activityInc.validate.activityIntroduction.length.error"));
        }
        
        if(StringUtils.isBlank(startDateStr)){
            this.addFieldError("startDateStr", this.getText("admin.activityInc.validate.startdate.null.error"));
        }
        
        if(StringUtils.isBlank(endDateStr)){
            this.addFieldError("endDateStr", this.getText("admin.activityInc.validate.enddate.null.error"));
        }
        
        Date startDate = null;
        Date endDate = null;
        if(StringUtils.isNotBlank(startDateStr)){
            startDate = DateUtil.parse(startDateStr);
            if(null ==  startDate){
                this.addFieldError("startDateStr", this.getText("admin.activityInc.validate.date.format.error"));
            }
        }
        
        if(StringUtils.isNotBlank(endDateStr)){
            endDate = DateUtil.parse(endDateStr);
            if(null ==  endDate){
                this.addFieldError("endDateStr", this.getText("admin.activityInc.validate.date.format.error"));
            }
        }
        
        if(null != startDate && null != endDate){
            if(endDate.getTime() < startDate.getTime()){
                this.addFieldError("endDateStr", this.getText("admin.activityInc.validate.date.early.error"));
            }
        }
    }

    public PageInfo getPager() {
        return pager;
    }

    public void setPager(PageInfo pager) {
        this.pager = pager;
    }

    public ActivityInc getActivityInc() {
        return activityInc;
    }

    public void setActivityInc(ActivityInc activityInc) {
        this.activityInc = activityInc;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    @Override
    public void setRequest(Map<String, Object> request) {
        this.requestMap = request;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.sessionMap = session;
    }
}
