/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:09:57
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.HttpClientUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.dao.NewServerReservationDao;
import com.sz7road.web.dao.impl.NewServerReservationDaoImpl;
import com.sz7road.web.model.newServerReservation.NewServerReservation;
import com.sz7road.web.model.onlineUser.LoginInfo;
import com.sz7road.web.service.NewServerReservationService;
import com.sz7road.web.service.OnlineUserService;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午4:09:57
 * 版本号： v1.0
*/
public class NewServerReservationServiceImpl implements NewServerReservationService {
    
    private static NewServerReservationServiceImpl _instance;
    
    private NewServerReservationDao dao;
    private NewServerReservationServiceImpl(){
        dao = NewServerReservationDaoImpl.getInstance();
    }
    
    public static NewServerReservationServiceImpl getInstance() {
        if(null == _instance){
            _instance = new NewServerReservationServiceImpl();
        }
        return _instance;
    }

    @Override
    public boolean queryUserHasReserveation(String userName) throws SQLException {
        int count = dao.queryUserHasReserveation(userName);
        if(count <= 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean addNewServerReservation(NewServerReservation reservation) throws SQLException {
        return dao.addNewServerReservation(reservation);
    }

    @Override
    public NewServerReservation queryNewServerReservationByUserNameAndVerifyCode(String userName, String verifyCode)
            throws SQLException {
        return dao.queryNewServerReservationByUserNameAndVerifyCode(userName, verifyCode);
    }

    @Override
    public boolean updateNewServerReservation(NewServerReservation reservation) throws SQLException {
        return dao.updateNewServerReservation(reservation);
    }

    @Override
    public String getActivateCode1(int userId) throws Exception {
        String actiId = SystemConfig.getProperty("user.new.server.reservation.activation.code.activeid1");
        String response = getActivateCode(actiId, userId);
        return response;
    }

    @Override
    public boolean requestIntervalLessThanMinute(String userName) throws SQLException {
        NewServerReservation reservation = dao.queryNewServerReservationByUserNameLastest(userName);
        if(reservation.getId() !=0){
            long nowTime = new Date().getTime();
            long lastTime = reservation.getCreateDate().getTime();
            long intervalTime = nowTime - lastTime;
            if(intervalTime <= AppConstants.USER_NEW_SERVER_RESERVATION_INTERVAL){
                return true;
            }
        }
        return false;
    }

    @Override
    public int everyDayReservationCount() throws SQLException {
        return dao.everyDayReservationCount();
    }

    @Override
    public String getActivateCode2(int userId) throws Exception {
        String actiId = SystemConfig.getProperty("user.new.server.reservation.activation.code.activeid2");
        String response = getActivateCode(actiId, userId);
        return response;
    }
    
    /**
     * 创建时间： 2013-3-30 下午2:36:54
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :想中控获取激活码
    */
    private synchronized String getActivateCode(String activateId, int userId) throws Exception{
        String response = "";
        String sign = activateId + SystemConfig.getProperty("user.sign.activation.md5.suf");
        String md5Sign = MD5Util.getMD5String(sign).toLowerCase();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("activityId", activateId));
        nvps.add(new BasicNameValuePair("giftBagType", SystemConfig.getProperty("user.sign.activation.giftBagType")));
        nvps.add(new BasicNameValuePair("useScope", SystemConfig.getProperty("user.sign.activation.useScope")));
        nvps.add(new BasicNameValuePair("sign", md5Sign));
        nvps.add(new BasicNameValuePair("userId", String.valueOf(userId)));
        nvps.add(new BasicNameValuePair("site", "7road_0300"));
        //String url = "http://10.10.3.170/cdkey/querySingle";
        String url = SystemConfig.getProperty("user.sign.activation.code.path");
        response = HttpClientUtil.post(url, nvps);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return response;
    }

    @Override
    public NewServerReservation queryUserNewsServerReserveationByUserName(String userName) throws SQLException {
        return dao.queryNewServerReservationByUserNameLastest(userName);
    }

    @Override
    public NewServerReservation queryUserNewsServerReserveationByPhoneNum(String phoneNum) throws SQLException {
        return dao.queryUserNewsServerReserveationByPhoneNum(phoneNum);
    }
    
}
