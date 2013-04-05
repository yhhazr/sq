/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:44:16
 * 版本号： v1.0
*/

package com.sz7road.web.action.online.signmanage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.signmanage.Sign;
import com.sz7road.web.model.signmanage.SignExChangeGiftValue;
import com.sz7road.web.service.SignService;

/**
 * 类描述：用户签到
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午10:44:16
 * 版本号： v1.0
*/
public class SignAction extends ActionSupport {
    private static final long serialVersionUID = -7964605488648081976L;

    private Logger logger = Logger.getLogger(SignAction.class);

    /**
    * 字段描述：游戏id
    */
    private static final int GAME_ID = Integer.parseInt(SystemConfig.getProperty("sq.game.id"));

    private String result;

    private String type;

    private String totalScore;

    private String continueSignCount;

    private List<Integer> signHistory;

    private List<SignExChangeGiftValue> gifts;
    
    private String token;
    
    /**
     * 创建时间： 2013-3-19 上午11:43:05
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :构造防止表单提交的token值
    */
    public String geneToken(){
        geneMyToken();
        return SUCCESS;
    }
    
    private void geneMyToken(){
        try {
            String tokenStr = System.currentTimeMillis() +  new Random().nextInt() + "";
            token = MD5Util.getMD5String(MD5Util.bytesToHex(tokenStr.getBytes()));
            ServletActionContext.getRequest().getSession().setAttribute("token", token);
        } catch (Exception e) {
            logger.error("生成防止表单提交的token值失败" + e.getMessage(), e);
        }
    }
    
    /**
     * 创建时间： 2013-3-19 上午11:50:27
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 : 判断token是否相同
    */
    private boolean isToken(){
        String sessionToken = (String) ServletActionContext.getRequest().getSession().getAttribute("token");
        if(sessionToken.equals(token)){
            return true;
        }
        return false;
    }
    
    /**
     * 用户签到
     * 创建时间： 2013-1-14 下午6:24:02
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
    */
    public String userSign() {
        String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
        if (userInfo == null || userInfo.length != 4) {
            result = "unLogin";
            return SUCCESS;
        }
        int userId = Integer.parseInt(userInfo[0]);

        SignService signService = ServiceLocator.getSignService();
        try {
            Sign sign = signService.userSign(userId, GAME_ID);
            if (null == sign) {
                logger.error("用户签到失败， 访问平台没有响应");
                result = "error";
                return SUCCESS;
            }
            if (sign.getCode() == 300) {
                result = "hasSigned";
                return SUCCESS;
            }
            if (sign.getCode() == 204 || sign.getCode() == 208) {
                logger.error("用户签到失败，平台返回状态码为： " + sign.getCode());
                result = "failure";
                return SUCCESS;
            }
            if (sign.getCode() == 404) {
                logger.error("用户签到失败，平台异常");
                result = "error";
                return SUCCESS;
            }
            if (sign.getCode() == 200 || sign.getCode() == 206) {
                totalScore = String.valueOf(sign.getTotalScore());
                continueSignCount = String.valueOf(sign.getContinueSignCount());
                signHistory = this.LongToHistoryList(sign.getSignHistory(), sign.getLastModifyDate());
                gifts = signService.queryReceiveGift(userId);
                result = "success";
            }
        } catch (Exception e) {
            result = "error";
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 将签到历史时间转换成以日期中的天表示并存入列表中，
     * 例如：昨天是18号，如果昨天签到则将18存入列表中，如果没签到则不存 
     * 创建时间： 2013-1-18 下午3:10:54
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
    */
    private List<Integer> LongToHistoryList(long history, long dateTime) {
        List<Integer> listStr = new ArrayList<Integer>();
        Date date = new Date(dateTime);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(new Date());
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        //如果获取的date的年和月不等于当前时间的年和月，就直接返回空的listStr
        if ( newCalendar.YEAR != calendar.YEAR ||newCalendar.MONTH != calendar.MONTH){
            return listStr;
        }
        
        
        String dateStr = DateUtil.format(date, "dd");
        int time = 1;
        if (StringUtils.isNotBlank(dateStr) && StringUtils.isNumeric(dateStr)) {
            time = Integer.parseInt(dateStr);
        }
        char[] binaryStr = Long.toBinaryString(history).toCharArray();
        int preTime = 1;
        for (int i = binaryStr.length - 1; i >= 0; i--) {
            if (binaryStr[i] == '1') {
                if (time >= 1) {
                    listStr.add(time);
                } else {
                    listStr.add(Integer.valueOf("5" + preTime));
                }
            }
            time--;
            if (time < 1) {
                preTime++;
            }
        }
        return listStr;
    }

    /**
     * 查询用户总积分
     * 创建时间： 2013-1-14 下午6:24:15
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
    */
    public String querySignScore() {
        String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
        if (userInfo == null || userInfo.length != 4) {
            result = "unLogin";
            return SUCCESS;
        }
        SignService signService = ServiceLocator.getSignService();
        try {
            int userId = Integer.parseInt(userInfo[0]);
            //int scoureCount = signService.queryScoureCount(userId, GAME_ID);
            //result = String.valueOf(scoureCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 用户兑换礼包
     * 创建时间： 2013-1-14 下午6:25:47
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
    */
    public String exchangeGift() {
        String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
        if(!isToken()){
            result = "repeat";
            return SUCCESS;
        }
        geneMyToken();
        
        if (userInfo == null || userInfo.length != 4) {
            result = "unLogin";
            return SUCCESS;
        }
        
        if (StringUtils.isBlank(type)) {
            result = "unSelect";
            return SUCCESS;
        }
        SignService signService = ServiceLocator.getSignService();
        try {
            int userId = Integer.parseInt(userInfo[0]);
            result = signService.exchangeGife(userId, GAME_ID, Integer.parseInt(type), userInfo[1]);
            //            List<SignExChangeGiftValue> sGifts = signService.queryReceiveGift(userId);
            //            gifts = JSONArray.fromObject(sGifts).toString();
            gifts = signService.queryReceiveGift(userId);
            Sign sign = signService.getSignInfo(userId, GAME_ID);
            if (null == sign) {
                result = "error";
                return SUCCESS;
            }
            if (sign.getCode() == 100 || sign.getCode() == 404) {
                logger.error("查询用户签到信息失败， 返回状态码为： " + sign.getCode());
            } else {
                totalScore = String.valueOf(sign.getTotalScore());
            }
        } catch (Exception e) {
            result = "error";
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @JSON(serialize = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getContinueSignCount() {
        return continueSignCount;
    }

    public void setContinueSignCount(String continueSignCount) {
        this.continueSignCount = continueSignCount;
    }

    public List<Integer> getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(List<Integer> signHistory) {
        this.signHistory = signHistory;
    }

    public List<SignExChangeGiftValue> getGifts() {
        return gifts;
    }

    public void setGifts(List<SignExChangeGiftValue> gifts) {
        this.gifts = gifts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
