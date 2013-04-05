/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午2:59:29
 * 版本号： v1.0
*/

package com.sz7road.web.model.activity;

import java.util.Date;

/**
 * 类描述：神曲活动简介bean
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午2:59:29
 * 版本号： v1.0
*/
public class ActivityInc {
    private int activityId;
    
    private String activityName;
    
    private String activityIntroduction;
    
    private String activityUrl;
    
    private String activityImg;
    
    private Date startDate;
    
    private Date endDate;
    
    private Date createDate;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityIntroduction() {
        return activityIntroduction;
    }

    public void setActivityIntroduction(String activityIntroduction) {
        this.activityIntroduction = activityIntroduction;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    
}
