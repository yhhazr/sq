/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:52:00
 * 版本号： v1.0
*/

package com.sz7road.web.model.signmanage;

import java.util.Date;

/**
 * 类描述：签到的pojo
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午10:52:00
 * 版本号： v1.0
*/
public class Sign {

    /**
     * 字段描述：处理结果
     * 200 处理成功
     * 300 已经签到
     * 404 处理失败，sql错误
     */
    private int code;

    /**
     * 字段描述：签到总积分
     */
    private int totalScore;

    /**
     * 字段描述：连续签到次数
     */
    private int continueSignCount;

    /**
     * 字段描述：签到的历史记录
     */
    private long signHistory;

    /**
     * 字段描述：最后签到时间
     */
    private long lastModifyDate;

    /**
    * 字段描述：返回的描述
    */
    private String msg;

    public Sign() {
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getContinueSignCount() {
        return continueSignCount;
    }

    public void setContinueSignCount(int continueSignCount) {
        this.continueSignCount = continueSignCount;
    }

    public long getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(long lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(long signHistory) {
        this.signHistory = signHistory;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
