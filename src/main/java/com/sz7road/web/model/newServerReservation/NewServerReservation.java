/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:17:54
 * 版本号： v1.0
*/

package com.sz7road.web.model.newServerReservation;

import java.util.Date;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午4:17:54
 * 版本号： v1.0
*/
public class NewServerReservation {
    private int id;

    private String userName;

    private String verifyCode;

    private String phoneNum;

    private boolean verify;

    private String activationCode1;

    private String activationCode2;

    private String ipAddress;

    private int requestTimes;

    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean getVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getActivationCode1() {
        return activationCode1;
    }

    public void setActivationCode1(String activationCode1) {
        this.activationCode1 = activationCode1;
    }

    public String getActivationCode2() {
        return activationCode2;
    }

    public void setActivationCode2(String activationCode2) {
        this.activationCode2 = activationCode2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getRequestTimes() {
        return requestTimes;
    }

    public void setRequestTimes(int requestTimes) {
        this.requestTimes = requestTimes;
    }

}
