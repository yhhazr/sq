/**
 * Copyright  2013-1-17 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:22:06
 * 版本号： v1.0
*/

package com.sz7road.web.model.signmanage;

/**
 * 类描述：积分礼包pojo
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-17 下午4:22:06
 * 版本号： v1.0
*/
public class SignGift {
    private int giftId;

    private String giftName;

    private int giftScore;

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public int getGiftScore() {
        return giftScore;
    }

    public void setGiftScore(int giftScore) {
        this.giftScore = giftScore;
    }

}
