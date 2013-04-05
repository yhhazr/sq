/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午6:28:45
 * 版本号： v1.0
*/

package com.sz7road.web.model.signmanage;

import java.util.Date;

/**
 * 类描述：积分换取礼包的pojo
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 下午6:28:45
 * 版本号： v1.0
*/
public class SignExchangeGift {
    private int id;

    private int userId;

    private String userName;

    private int giftId;

    private String giftName;

    private String activationCode;

    private Date exchangeDate;
    
    /**
    * 字段描述：礼包发放是否成功
    */
    private boolean provided;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public boolean getProvided() {
        return provided;
    }

    public void setProvided(boolean provided) {
        this.provided = provided;
    }
}
