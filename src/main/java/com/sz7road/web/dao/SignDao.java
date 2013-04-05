/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:25:03
 * 版本号： v1.0
*/

package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.signmanage.SignExchangeGift;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午11:25:03
 * 版本号： v1.0
*/
public interface SignDao {


    /**
     * 根据礼包id查询每个礼包需要的积分
     * 创建时间： 2013-1-15 上午9:53:53
     * 创建人：xin.fang
     * 参数：
     * 返回值： int
     * 方法描述 :
     * @throws SQLException 
    */
    int queryTypeScore(int typeId) throws SQLException;

    /**
     * 将用户领取的礼包信息存入数据库
     * 创建时间： 2013-1-17 下午4:29:50
     * 创建人：xin.fang
     * 参数：
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean addSignExchangeGift(SignExchangeGift exchangeGift) throws SQLException;

    /**
     * 查询用户领取的礼包
     * 创建时间： 2013-1-18 下午2:19:11
     * 创建人：xin.fang
     * 参数：
     * 返回值： List<SignExchangeGift>
     * 方法描述 :
     * @throws SQLException 
    */
    List<SignExchangeGift> queryReceiveGift(int userId) throws SQLException;

    /**
     * 根据用户id和游戏id更新用户礼包的领取状态
     * 创建时间： 2013-1-18 下午5:37:47
     * 创建人：xin.fang
     * 参数：userId用户id， gameId游戏id, provided用户激活码领取的状态（true表示领取成功， false表示领取失败）
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean updateLastSignExchangeGiftPrivateByUserIdAndGameId(int userId, int gameId, boolean provided) throws SQLException;

    /**
     * 根据用户id和游戏id获取用户最后一次的激活码
     * 创建时间： 2013-1-18 下午6:01:33
     * 创建人：xin.fang
     * 参数：
     * 返回值： SignExchangeGift
     * 方法描述 :
     * @throws SQLException 
    */
    SignExchangeGift getLastSignExchangeByUserIdAndGameId(int userId, int gameId) throws SQLException;

    /**
     * 查询所用用户兑换的礼包
     * 创建时间： 2013-1-21 上午10:17:41
     * 创建人：xin.fang
     * 参数：
     * 返回值： List<SignExchangeGift>
     * 方法描述 :
     * @param pageInfo 
     * @throws SQLException 
    */
    List<SignExchangeGift> queryAllUserSignExchageGift(PageInfo pageInfo) throws SQLException;

    /**
     * 获取用户兑换礼包的总个数
     * 创建时间： 2013-1-21 上午10:30:12
     * 创建人：xin.fang
     * 参数：
     * 返回值： int
     * 方法描述 :
     * @throws SQLException 
    */
    int getSignExchangeGiftCount() throws SQLException;

    /**
     * 根据给定的gift类型id查询礼包类型是否存在
     * 创建时间： 2013-1-24 下午2:29:20
     * 创建人：xin.fang
     * 参数：
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    int queryGiftType(int giftTypeId) throws SQLException;
}
