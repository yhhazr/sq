/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:22:33
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.HttpClientUtil;
import com.sz7road.web.common.util.JsonUtil;
import com.sz7road.web.common.util.MD5Util;
import com.sz7road.web.dao.SignDao;
import com.sz7road.web.dao.impl.SignDaoImpl;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.model.signmanage.Sign;
import com.sz7road.web.model.signmanage.SignExChangeGiftValue;
import com.sz7road.web.model.signmanage.SignExchangeGift;
import com.sz7road.web.service.SignService;

/**
 * 类描述：用户签到service层实现
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午11:22:33
 * 版本号： v1.0
*/
public class SignServiceImpl implements SignService {

    private Logger logger = Logger.getLogger(SignServiceImpl.class);

    /**
    * 字段描述：由于某些原因，需重新请求平台的最大次数
    */
    private static int REQUEST_TIMES = 5;

    private static SignServiceImpl _instance;

    private SignDao signDao;

    private SignServiceImpl() {
        signDao = SignDaoImpl.getInstance();
    }

    /**
     * 创建时间： 2013-1-14 上午11:22:39
     * 创建人：xin.fang
     * 参数：
     * 返回值： SignService
     * 方法描述 :
    */
    public static SignServiceImpl getInstance() {
        if (null == _instance) {
            _instance = new SignServiceImpl();
        }
        return _instance;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.service.SignService#userSign(int, int)
    */
    @Override
    public Sign userSign(int userId, int gameId) throws Exception {
        Sign sign = this.requestToPlatSign(userId, gameId);
        //如果sign为null则重新向用户平台发送REQUEST_TIMES次签到请求
        if (null == sign) {
            while (REQUEST_TIMES-- > 0) {
                sign = this.requestToPlatSign(userId, gameId);
            }
            REQUEST_TIMES = 5;
            if (null == sign) {
                logger.error("用户签到失败，平台无响应");
                return null;
            }
        }
        return sign;
    }

    /**
     * 向用户平台发送签到请求
     * 创建时间： 2013-1-24 上午11:47:21
     * 创建人：xin.fang
     * 参数：
     * 返回值： Sign
     * 方法描述 :
    */
    private Sign requestToPlatSign(int userId, int gameId) throws Exception {
        String userSignUrl = SystemConfig.getProperty("user.sign.path");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("uid", String.valueOf(userId)));
        nvps.add(new BasicNameValuePair("gid", String.valueOf(gameId)));
        String result = HttpClientUtil.post(userSignUrl, nvps);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        Sign sign = JsonUtil.jsonStrToBean(result, Sign.class);
        return sign;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.service.SignService#exchangeGife(int, int, int)
    */
    @Override
    public String exchangeGife(int userId, int gameId, int giftTypeId, String userName) throws Exception {

        //查询兑换的礼包类型是否存在
        int count = signDao.queryGiftType(giftTypeId);
        if (count <= 0) {
            return "unExists";
        }

        //查询用户是否领取礼包
        boolean isReceive = this.queryReceiveGift(userId, gameId, giftTypeId);
        if (isReceive) {
            return "hasReceive";
        }

        //获取用户在该游戏gameid的中的总积分
        int totlaScore = getSignTotalSocre(userId, gameId);
        //表示获取用户签到总积分失败
        if (totlaScore == -1) {
            return "error";
        }

        //获取兑换礼包所需的积分
        int giftScore = signDao.queryTypeScore(giftTypeId);
        if (totlaScore < giftScore) {
            return "lessScore";
        }

        /*
         * 1.get the gift activation code 
         * 2.reduce the sign score 
         * 3.save the gift activation to database
        */
        String activationCode = "";
        SignExchangeGift exchangeGift = new SignExchangeGift();
        exchangeGift.setUserId(userId);
        exchangeGift.setUserName(userName);
        exchangeGift.setGiftId(giftTypeId);
        //获取礼包的激活码
        activationCode = getTheSignActivationCode(giftTypeId, userId);
        if (activationCode.equals("error") || activationCode.equals("finish")) {
            return activationCode;
        }

        //将用户领取的礼包信息存入数据库
        exchangeGift.setActivationCode(activationCode);
        exchangeGift.setProvided(true);
        boolean addSignExchangeGift = signDao.addSignExchangeGift(exchangeGift);
        if (!addSignExchangeGift) {
            logger.error("官网插入用户领取的礼包信息失败");
            return "error";
        }

        //通知平台减去用户兑换 礼包相应的积分
        String reduceScore = notifyPlatReduceScore(userId, gameId, giftScore);
        if (reduceScore.equals("lessScore") || reduceScore.equals("error")) {
            return reduceScore;
        }
        return activationCode;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.service.SignService#getSignInfo(int, int)
    */
    @Override
    public Sign getSignInfo(int userId, int gameId) throws Exception {
        Sign sign = this.getSignInfo2Plat(userId, gameId);
        if (null == sign) {
            while (REQUEST_TIMES-- > 0) {
                sign = this.getSignInfo2Plat(userId, gameId);
            }
            REQUEST_TIMES = 5;
            if (null == sign) {
                logger.error("获取用户签到积分信息失败，平台无响应");
                return null;
            }
        }
        return sign;
    }

    /**
     * 向平台发送获取用户签到信息的请求
     * 创建时间： 2013-1-24 下午12:25:47
     * 创建人：xin.fang
     * 参数：
     * 返回值： Sign
     * 方法描述 :
    */
    private Sign getSignInfo2Plat(int userId, int gameId) throws Exception {
        Sign sign = null;
        String querySingTotalSocreUrl = SystemConfig.getProperty("user.sign.score.path");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("uid", String.valueOf(userId)));
        nvps.add(new BasicNameValuePair("gid", String.valueOf(gameId)));
        String result = HttpClientUtil.post(querySingTotalSocreUrl, nvps);
        // result = {"code":200,"totalScore":405,"continueSignCount":1,"signHistory":1,"msg":"成功的查询到签到信息!","lastModifyDate":1356537600000}
        if (StringUtils.isBlank(result)) {
            return null;
        }
        sign = JsonUtil.jsonStrToBean(result, Sign.class);
        return sign;
    }

    /**
     * 从中控获取激活码
     * 创建时间： 2013-1-24 下午4:09:17
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
    */
    private synchronized String getActivationCode(int giftTypeId, int userId) throws Exception {
        String response = "";
        //String actiId = "950ebc08-359d-48da-8a6d-49175ccda8c8";

        String actiId = SystemConfig.getProperty("user.sign.activation.code.activeid" + giftTypeId);
        String sign = actiId + SystemConfig.getProperty("user.sign.activation.md5.suf");
        String md5Sign = MD5Util.getMD5String(sign).toLowerCase();

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("activityId", actiId));
        nvps.add(new BasicNameValuePair("giftBagType", SystemConfig.getProperty("user.sign.activation.giftBagType")));
        nvps.add(new BasicNameValuePair("useScope", SystemConfig.getProperty("user.sign.activation.useScope")));
        nvps.add(new BasicNameValuePair("sign", md5Sign));
        nvps.add(new BasicNameValuePair("userId", String.valueOf(userId)));
        nvps.add(new BasicNameValuePair("site", "7road"));
        
        //String url = "http://10.10.3.170/cdkey/querySingle";
        String url = SystemConfig.getProperty("user.sign.activation.code.path");
        response = HttpClientUtil.post(url, nvps);
        if (StringUtils.isBlank(response)) {
            return null;
        }
        return response;
    }

    /**
     * 获取激活码，并实现重连机制
     * 创建时间： 2013-1-17 下午4:56:17
     * 创建人：xin.fang
     * 参数：
     * 返回值： String
     * 方法描述 :
     * @throws Exception 
    */
    private String getTheSignActivationCode(int giftTypeId, int userId) throws Exception {
        String activationCode = getActivationCode(giftTypeId, userId);
        if (StringUtils.isBlank(activationCode)) {
            while (REQUEST_TIMES-- > 0) {
                activationCode = getActivationCode(giftTypeId, userId);
            }
            REQUEST_TIMES = 5;
            if (StringUtils.isBlank(activationCode)) {
                logger.error("获取用户签到礼包激活码失败，访问中控无响应");
                return "error";
            }
        }
        if (activationCode.equals("finish")) {
            logger.info("签到活动已结束！");
            return "finish";
        }
        if (activationCode.length() != Integer.parseInt(SystemConfig.getProperty("sign.active.code.length"))) {
            logger.error("获取激活码失败，激活码长度不正确");
            return "error";
        }
        return activationCode;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.service.SignService#queryReceiveGift(int)
    */

    @Override
    public List<SignExChangeGiftValue> queryReceiveGift(int userId) throws SQLException {
        List<SignExchangeGift> exchangeGifts = signDao.queryReceiveGift(userId);
        List<SignExChangeGiftValue> giftValues = new ArrayList<SignExChangeGiftValue>();
        SignExChangeGiftValue giftValue = null;
        for (SignExchangeGift gift : exchangeGifts) {
            giftValue = new SignExChangeGiftValue();
            giftValue.setUserName(gift.getUserName());
            giftValue.setGiftId(gift.getGiftId());
            giftValue.setGiftName(gift.getGiftName());
            giftValue.setActivationCode(gift.getActivationCode());
            giftValue.setExchangeDateStr(DateUtil.format(gift.getExchangeDate(), "yyyy-MM-dd HH:mm:ss"));
            giftValues.add(giftValue);
        }
        return giftValues;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.service.SignService#queryAllUserSignExchageGift()
    */
    @Override
    public PaginationResult<SignExchangeGift> queryAllUserSignExchageGift(PageInfo pageInfo) throws SQLException {
        int count = signDao.getSignExchangeGiftCount();
        List<SignExchangeGift> gifts = signDao.queryAllUserSignExchageGift(pageInfo);
        PaginationResult<SignExchangeGift> giftPage = new PaginationResult<SignExchangeGift>(count, gifts);
        return giftPage;
    }

    /**
     * 查询礼包是否已经发放
     * 创建时间： 2013-1-21 下午5:24:41
     * 创建人：xin.fang
     * 参数：
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    private boolean queryReceiveGift(int userId, int gameId, int giftTypeId) throws SQLException {
        List<SignExchangeGift> gifts = signDao.queryReceiveGift(userId);
        for (SignExchangeGift gift : gifts) {
            if (gift.getGiftId() == giftTypeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户签到总积分
     * 创建时间： 2013-1-21 下午4:48:27
     * 创建人：xin.fang
     * 参数：
     * 返回值： int
     * 方法描述 :
     * @throws Exception 
    */
    private int getSignTotalSocre(int userId, int gameId) throws Exception {
        Sign sign = getSignInfo(userId, gameId);
        if (null == sign) {
            logger.error("获取用户签到积分信息失败，平台无响应");
            return -1;
        }
        if (sign.getCode() == 404) {
            logger.error("获取用户签到积分信息失败，平台发生异常");
            return -1;
        }
        return sign.getTotalScore();
    }

    /**
     * 通知平台减去用户积分
     * 创建时间： 2013-1-21 下午2:51:51
     * 创建人：xin.fang
     * 参数：
     * 返回值： Sign
     * 方法描述 :
     * @throws Exception 
    */
    private String notifyPlatReduceScore(int userId, int gameId, int giftScore) throws Exception {
        Sign reduceSign = notifyPlatReduceSignScore(userId, gameId, giftScore);
        if (null == reduceSign) {
            while (REQUEST_TIMES-- > 0) {
                reduceSign = notifyPlatReduceSignScore(userId, gameId, giftScore);
            }
            REQUEST_TIMES = 5;
            if (null == reduceSign) {
                logger.error("用户平台减去领取礼包的积分失败， 平台无响应");
                return "error";
            }
        }
        //如果返回类型的code值不为200则说明平台减分操作失败，设置用户激活码为未启用状态
        if (reduceSign.getCode() == 400 || reduceSign.getCode() == 404) {
            logger.error("用户平台减去领取礼包的积分失败， 返回code为：" + reduceSign.getCode() + ", 正在设置用户激活码为未启用状态");
            signDao.updateLastSignExchangeGiftPrivateByUserIdAndGameId(userId, gameId, false);
            return "error";
        }
        //用户积分不够
        if (reduceSign.getCode() == 300) {
            logger.error("用户平台减去领取礼包的积分失败， 用户积分不足, 正在设置用户激活码为未启用状态");
            signDao.updateLastSignExchangeGiftPrivateByUserIdAndGameId(userId, gameId, false);
            return "lessScore";
        }
        return "success";
    }

    private Sign notifyPlatReduceSignScore(int userId, int gameId, int giftScore) throws Exception {
        String reduceSingTotalSocreUrl = SystemConfig.getProperty("user.sign.reduce.path");
        List<NameValuePair> reNvps = new ArrayList<NameValuePair>();
        reNvps.add(new BasicNameValuePair("uid", String.valueOf(userId)));
        reNvps.add(new BasicNameValuePair("gid", String.valueOf(gameId)));
        reNvps.add(new BasicNameValuePair("giftPackScore", String.valueOf(giftScore)));
        String reStr = HttpClientUtil.post(reduceSingTotalSocreUrl, reNvps);
        if (StringUtils.isBlank(reStr)) {
            return null;
        }
        Sign sign = JsonUtil.jsonStrToBean(reStr, Sign.class);
        return sign;
    }

}
