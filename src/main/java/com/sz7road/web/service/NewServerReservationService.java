/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:09:16
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.sql.SQLException;

import com.sz7road.web.model.newServerReservation.NewServerReservation;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午4:09:16
 * 版本号： v1.0
*/
public interface NewServerReservationService {

    /**
     * 创建时间： 2013-3-29 下午4:26:24
     * 创建人：xin.fang
     * 参数： userName 用户名
     * 返回值： boolean 预约返回true
     * 方法描述 : 查询用户是否已预约新服
     * @throws SQLException 
    */
    boolean queryUserHasReserveation(String userName) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午5:46:38
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :增加用户预约新服
     * @throws SQLException 
    */
    boolean addNewServerReservation(NewServerReservation reservation) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午6:04:10
     * 创建人：xin.fang
     * 参数： userName 用户名， verifyCode 新服预约验证码
     * 返回值： NewServerReservation
     * 方法描述 :根据用户名和预约验证码查询预约信息
     * @throws SQLException 
    */
    NewServerReservation queryNewServerReservationByUserNameAndVerifyCode(String userName, String verifyCode) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午6:20:06
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :更新新服预约
     * @throws SQLException 
    */
    boolean updateNewServerReservation(NewServerReservation reservation) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午7:00:01
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :获取激活码
     * @throws Exception 
    */
    String getActivateCode1(int userId) throws Exception;

    /**
     * 创建时间： 2013-3-30 上午11:38:59
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :同一个用户两次获取验证码的时间
     * @throws SQLException 
    */
    boolean requestIntervalLessThanMinute(String userName) throws SQLException;

    /**
     * 创建时间： 2013-3-30 下午2:25:51
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :每一天到当前时间的新服预约的总数
     * @throws SQLException 
    */
    int everyDayReservationCount() throws SQLException;

    /**
     * 创建时间： 2013-3-30 下午2:34:27
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :获取第二个人激活码
     * @throws Exception 
    */
    String getActivateCode2(int userId) throws Exception;

    /**
     * 创建时间： 2013-4-1 上午10:54:26
     * 创建人：xin.fang
     * 参数： 
     * 返回值： NewServerReservation
     * 方法描述 :根据用户名查询新服预约信息
     * @throws SQLException 
    */
    NewServerReservation queryUserNewsServerReserveationByUserName(String userName) throws SQLException;

    /**
     * 创建时间： 2013-4-1 下午2:07:06
     * 创建人：xin.fang
     * 参数： 
     * 返回值： NewServerReservation
     * 方法描述 :根据手机号查询用户是否预约新服
     * @throws SQLException 
    */
    NewServerReservation queryUserNewsServerReserveationByPhoneNum(String phoneNum) throws SQLException;

}
