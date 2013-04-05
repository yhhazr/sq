/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:20:42
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.signmanage.Sign;
import com.sz7road.web.model.signmanage.SignExChangeGiftValue;
import com.sz7road.web.model.signmanage.SignExchangeGift;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午11:20:42
 * 版本号： v1.0
*/
public interface SignService {

    /**
     * 用户牵到
     * 创建时间： 2013-1-17 下午3:11:46
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
     * @throws Exception 
    */
    Sign userSign(int userId, int gameId) throws Exception;

    /**
     * 礼包兑换
     * 创建时间： 2013-1-17 下午3:58:55
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
     * @throws Exception 
    */
    String exchangeGife(int userId, int gameId, int giftTypeId, String userName) throws Exception;

    /**
     * 根据用户名查询用户领取的礼包
     * 创建时间： 2013-1-18 下午5:38:19
     * 创建人：xin.fang
     * 参数：
     * 返回值： List<SignExChangeGiftValue>
     * 方法描述 :
     * @throws SQLException 
    */
    List<SignExChangeGiftValue> queryReceiveGift(int userId) throws SQLException;
    
    /**
     * 获取用户签到信息
     * 创建时间： 2013-1-22 下午3:58:29
     * 创建人：xin.fang
     * 参数：
     * 返回值： Sign
     * 方法描述 :
    */
    Sign getSignInfo(int userId, int gameId) throws Exception;

    /**
     * 查询所用用户兑换的礼包
     * 创建时间： 2013-1-21 上午10:16:35
     * 创建人：xin.fang
     * 参数：
     * 返回值：PaginationResult<SignExchangeGift>
     * 方法描述 :
     * @param pageInfo 
     * @throws SQLException 
    */
    PaginationResult<SignExchangeGift> queryAllUserSignExchageGift(PageInfo pageInfo) throws SQLException;

}
