/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午4:28:45
 * 版本号： v1.0
*/

package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.newServerReservation.NewServerReservation;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午4:28:45
 * 版本号： v1.0
*/
public interface NewServerReservationDao {

    /**
     * 创建时间： 2013-3-29 下午4:31:11
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :查询用户是否已预约新服
     * @throws SQLException 
    */
    int queryUserHasReserveation(String userName) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午5:48:45
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :增加用户预约新服
     * @throws SQLException 
    */
    boolean addNewServerReservation(NewServerReservation reservation) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午6:05:37
     * 创建人：xin.fang
     * 参数： userName 用户名， verifyCode 新服预约验证码
     * 返回值： NewServerReservation
     * 方法描述 :根据用户名和预约验证码查询预约信息
     * @throws SQLException 
    */
    NewServerReservation queryNewServerReservationByUserNameAndVerifyCode(String userName, String verifyCode) throws SQLException;

    /**
     * 创建时间： 2013-3-29 下午6:20:51
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :更新新服预约
     * @throws SQLException 
    */
    boolean updateNewServerReservation(NewServerReservation reservation) throws SQLException;

    /**
     * 创建时间： 2013-3-30 上午11:52:35
     * 创建人：xin.fang
     * 参数： 
     * 返回值： NewServerReservation
     * 方法描述 : 根据用户名获取新服预约信息
     * @throws SQLException 
    */
    List<NewServerReservation> queryNewServerReservationByUserName(String userName) throws SQLException;

    /**
     * 创建时间： 2013-3-30 下午12:32:49
     * 创建人：xin.fang
     * 参数： 
     * 返回值： NewServerReservation
     * 方法描述 :根据用户名查询用户最后一次的新服预约信息
     * @throws SQLException 
    */
    NewServerReservation queryNewServerReservationByUserNameLastest(String userName) throws SQLException;

    /**
     * 创建时间： 2013-3-30 下午2:27:18
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :每一天到当前时间的新服预约的总数
     * @throws SQLException 
    */
    int everyDayReservationCount() throws SQLException;

    /**
     * 创建时间： 2013-4-1 下午2:08:11
     * 创建人：xin.fang
     * 参数： 
     * 返回值： NewServerReservation
     * 方法描述 :根据手机号查询用户预约信息
     * @throws SQLException 
    */
    NewServerReservation queryUserNewsServerReserveationByPhoneNum(String phoneNum) throws SQLException;

}
