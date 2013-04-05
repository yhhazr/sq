/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午3:22:29
 * 版本号： v1.0
*/

package com.sz7road.web.action.online.newServerReservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.CookieUtil;
import com.sz7road.web.common.util.GeneVerificationCodeUtil;
import com.sz7road.web.common.util.IpAddressUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.MoblieSendMsgUtil;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.newServerReservation.NewServerReservation;
import com.sz7road.web.service.NewServerReservationService;

/**
 * 类描述：新服预约300服
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午3:22:29
 * 版本号： v1.0
*/
public class NewServerReservationAction extends ActionSupport {

    private static final long serialVersionUID = 4228140591749201073L;

    private Logger logger = Logger.getLogger(NewServerReservationAction.class);

    private String token;

    private String phoneNum;

    private String verifyCode;

    private String userName;

    private List<String> activeCode;

    private String resultMsg;
    
    private String msg;
    
    private int userId;

    private boolean isLogin() {
        String[] userInfo = CookieUtil.getUserInfoArrayFromCookie();
        if (userInfo != null && userInfo.length == 4) {
            String sign = CookieUtil.buildMD5Cookie(userInfo[0], userInfo[1], userInfo[2]);
            if (sign.equals(userInfo[3])) {
                userName = userInfo[1];
                userId = Integer.parseInt(userInfo[0]);
                return true;
            }
        }
        resultMsg = "unLogin";
        return false;
//        userName = "fangxin208";
//        userId = 312;
//        return true;
    }

    /**
     * 创建时间： 2013-4-1 上午9:53:09
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :预约新服是否开始，开始返回true
    */
    private boolean isNewServerReservationStart() {
        //活动开始时间
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2013, Calendar.APRIL, 1, 10, 0);
        Date startDate = startCalendar.getTime();

        //现在时间
        Date nowDate = new Date();

        if (nowDate.getTime() >= startDate.getTime()) {
            return true;
        }
        return false;
    }
    
    /**
     * 创建时间： 2013-4-1 上午9:53:21
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 ：预约新是否服结束,结束返回true
    */
    private boolean isNewServerReservationEnd() {

        //活动结束时间
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2013, Calendar.APRIL, 4, 10, 0);
        Date endDate = endCalendar.getTime();
        //现在时间
        Date nowDate = new Date();

        if (nowDate.getTime() <= endDate.getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 创建时间： 2013-3-25 下午2:55:25
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 : 防止表单重复提交的标志
    */
    public String myToken() {
        geneToken();
        return SUCCESS;
    }

    private String geneToken() {
        StringBuilder builder = new StringBuilder(String.valueOf(System.currentTimeMillis()));
        builder.append(new Random());
        HttpServletRequest request = ServletActionContext.getRequest();
        String myToken = MD5Util.getMD5String(builder.toString());
        request.getSession().setAttribute("token", myToken);
        token = myToken;
        return token;
    }

    public String addUserToNewServerReservation() {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String serssionStr = (String) request.getSession().getAttribute("token");
            if (StringUtils.isBlank(serssionStr)) {
                resultMsg = "tokenError";
                geneToken();
                return SUCCESS;
            }
            //判断表单是否重复提交
            if (!serssionStr.equals(token)) {
                resultMsg = "tokenError";
                geneToken();
                return SUCCESS;
            }
            //重置token状态
            geneToken();
            
            //判断用户是否登录
            if (!isLogin()) {
                return SUCCESS;
            }

            //判断是否到新服预约的时间
            if (!isNewServerReservationStart()) {
                resultMsg = "unStart";
                return SUCCESS;
            }
            
            //判断新服预约是否结束
            if (isNewServerReservationEnd()) {
                resultMsg = "isEnd";
                return SUCCESS;
            }

            //判断手机号码是否符合格式
            if (StringUtils.isBlank(phoneNum)) {
                resultMsg = "phoneError";
                return SUCCESS;
            }
            Pattern p = Pattern.compile("^((13[0-9])|145|147|(15[0-3,5-9])|(18[0-9]))\\d{8}$");
            Matcher matcher = p.matcher(phoneNum);
            if (!matcher.matches()) {
                resultMsg = "phoneError";
                //logger.error("phoneNum: " + phoneNum);
                return SUCCESS;
            }

            
            NewServerReservationService service = ServiceLocator.getNewServerReservationService();
//            boolean isExists = service.queryUserHasReserveation(userName);
//            if (isExists) {
//                resultMsg = "signed";
//                return SUCCESS;
//            }
            
            //判断用户已经预约新服
            NewServerReservation reservation = service.queryUserNewsServerReserveationByUserName(userName);
            if (reservation.getId() != 0 && reservation.getVerify()){
                resultMsg = "signed";
                List<String> activeCodes = new ArrayList<String>();
                activeCodes.add(reservation.getActivationCode1());
                activeCodes.add(reservation.getActivationCode2()); 
                activeCode = activeCodes;
                return SUCCESS;
            }
            
            
            //判断用户两次请求新服预约的时间间隔
            boolean isRequestIntervalLessThanMinute = service.requestIntervalLessThanMinute(userName);
            if (isRequestIntervalLessThanMinute) {
                resultMsg = "interval";
                return SUCCESS;
            }
            
            //判断用户请求是否超过最大请求次数
            if(reservation.getId() != 0 && reservation.getRequestTimes() 
                    >= AppConstants.USER_NEW_SERVER_REVERSION_REQUEST_TIMES){
                resultMsg = "requestTimes";
                return SUCCESS;
            }
            
            //生成手机验证码并存入数据库
            String verificationCode = GeneVerificationCodeUtil.geneVerificationCode();
            if(reservation.getId() != 0){
                reservation.setVerifyCode(verificationCode);
                reservation.setIpAddress(IpAddressUtil.getIpAddr(request));
                reservation.setRequestTimes(reservation.getRequestTimes() + 1);
                boolean update = service.updateNewServerReservation(reservation);
                if (!update) {
                    resultMsg = "failure";
                    return SUCCESS;
                }
            }else{
                NewServerReservation phoneReservation = service.queryUserNewsServerReserveationByPhoneNum(phoneNum);
                if(phoneReservation.getId() != 0){
                    resultMsg = "phone";
                    return SUCCESS;
                }
                
                reservation.setUserName(userName);
                reservation.setPhoneNum(phoneNum);
                reservation.setVerify(false);
                reservation.setVerifyCode(verificationCode);
                reservation.setIpAddress(IpAddressUtil.getIpAddr(request));
                reservation.setRequestTimes(reservation.getRequestTimes() + 1);
                boolean isInsert = service.addNewServerReservation(reservation);
                if (!isInsert) {
                    resultMsg = "failure";
                    return SUCCESS;
                }
            }
            StringBuilder content = new StringBuilder();
            content.append(AppConstants.USER_NEW_SERVER_RESERVATION_PRE_MSG);
            content.append(verificationCode).append("，");
            content.append(AppConstants.USER_NEW_SERVER_RESERVATION_LAST_MSG);
            
            String response = MoblieSendMsgUtil.sendMessage(phoneNum, content.toString());
            if (StringUtils.isBlank(response)) {
                resultMsg = "failure";
                return SUCCESS;
            }
            //verifyCode = verificationCode;
            resultMsg = "success";
        } catch (Exception e) {
            resultMsg = "error";
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }

    public String validatorUserVerifyCode() {
        //验证用户的手机验证码是否有效、发放激活码
        if (!isLogin()) {
            return SUCCESS;
        }
        if (StringUtils.isBlank(verifyCode)) {
            resultMsg = "verifyCode";
            return SUCCESS;
        }
        Pattern pattern = Pattern.compile("[A-Za-z0-9]{6}");
        if (!pattern.matcher(verifyCode).matches()) {
            resultMsg = "verifyCode";
            return SUCCESS;
        }
        NewServerReservationService service = ServiceLocator.getNewServerReservationService();
        try {
            NewServerReservation reservation = service.queryNewServerReservationByUserNameAndVerifyCode(userName,
                    verifyCode);
            //判断该用户是否持有该verifyCode
            if (reservation.getId() == 0) {
                resultMsg = "notExists";
                return SUCCESS;
            }

            //判断该用户是否已经预约
            if (reservation.getVerify()) {
                resultMsg = "signed";
                return SUCCESS;
            }
            //验证码是否过期
            long nowTime = new Date().getTime();
            long lastTime = reservation.getCreateDate().getTime();
            long expiredTime = nowTime - lastTime;
            if (expiredTime > AppConstants.USER_NEW_SERVER_RESERVATION_EXPIRED) {
                resultMsg = "expired";
                return SUCCESS;
            }

            List<String> activateCodes = new ArrayList<String>();
            //获取新服预约激活码
            String activateCode = service.getActivateCode1(userId);
            if (StringUtils.isBlank(activateCode)) {
                logger.error("获取预约礼包1激活码失败！");
                resultMsg = "error";
                return SUCCESS;
            }
            if (activateCode.equals("finish")) {
                logger.error("没有预约礼包1激活码！");
                resultMsg = "finish";
                return SUCCESS;
            }
            activateCodes.add(activateCode);
            
            //是否是每天前300名的预约用户
            int count = service.everyDayReservationCount();
            String activateCode2 = "";
            if (count <= AppConstants.USER_NEW_SERVER_REVERSION_EVERDAY_COUNT) {
                activateCode2 = service.getActivateCode2(userId);
                if (StringUtils.isBlank(activateCode2)) {
                    logger.error("获取预约礼包2激活码失败！");
                    resultMsg = "error";
                    return SUCCESS;
                }
                if (activateCode2.equals("finish")) {
                    logger.error("没有预约礼包2激活码！");
                    resultMsg = "finish";
                    return SUCCESS;
                }
                activateCodes.add(activateCode2);
            }

            //将激活码存入数据库
            reservation.setActivationCode1(activateCode);
            reservation.setActivationCode2(activateCode2);
            reservation.setVerify(true);
            boolean isUpdate = service.updateNewServerReservation(reservation);
            if (!isUpdate) {
                resultMsg = "failure";
                return SUCCESS;
            }
            activeCode = activateCodes;
            resultMsg = "success";
        } catch (Exception e) {
            resultMsg = "error";
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }
    
    
    /**
     * 创建时间： 2013-4-1 上午11:44:00
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :查询用户是否已经预约新服
    */
    public String queryUserIsSignUp(){
        try {
            if (!isLogin()) {
                return SUCCESS;
            }
            NewServerReservationService service = ServiceLocator.getNewServerReservationService();
            //判断用户已经预约新服
            NewServerReservation reservation = service.queryUserNewsServerReserveationByUserName(userName);
            if (reservation.getId() != 0 && reservation.getVerify()){
                resultMsg = "signed";
                activeCode = new ArrayList<String>();
                activeCode.add(reservation.getActivationCode1());
                activeCode.add(reservation.getActivationCode2());
            }else{
                resultMsg = "unSign";
                //判断是否到新服预约的时间
                if (!isNewServerReservationStart()) {
                    resultMsg = "unStart";
                }
                //判断新服预约是否结束
                if (isNewServerReservationEnd()) {
                    resultMsg = "isEnd";
                }
            }
        } catch (Exception e) {
            resultMsg = "error";
            logger.error(e.getMessage(), e);
        }
        return SUCCESS;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(List<String> activeCode) {
        this.activeCode = activeCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
