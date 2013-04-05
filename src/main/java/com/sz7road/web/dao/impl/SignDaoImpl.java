/**
 * Copyright  2013-1-14 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:25:28
 * 版本号： v1.0
*/

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.SignDao;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.signmanage.SignExchangeGift;

/**
 * 类描述：用户签到dao层实现
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-1-14 上午11:25:28
 * 版本号： v1.0
*/
public class SignDaoImpl implements SignDao {

    /**
    * 字段描述：查询每个礼包兑换分数的sql语句
    */
    private static final String SELECT_TYPE_SCORE = "SELECT score FROM game_sign_gift WHERE gift_id = ?";

    /**
    * 字段描述：将用户兑换的礼包存入数据库的语句
    */
    private static final String ADD_SIGNEXCHANGGIFT = "INSERT INTO game_sign_exchange_gift(user_id, user_name, gift_id, "
            + " activation_code, provided, create_date) VALUES(?, ?, ?, ?, ?, ?)";

    /**
    * 字段描述：查询给用户成功发放的激活码
    */
    private static final String SELECT_USER_RECEIVE_GIFT = "SELECT e.user_id, e.user_name, e.gift_id, "
            + " activation_code, e.create_date, g.gift_name FROM game_sign_exchange_gift e,  game_sign_gift g"
            + " WHERE e.gift_id = g.gift_id and user_id = ? and e.provided = ? ORDER BY create_date";

    /**
    * 字段描述：将用户的激活码设为可用
    */
    private static final String UPDATE_PROVIDE = "UPDATE game_sign_exchange_gift SET provided = ? WHERE id in "
            + " (SELECT t.id FROM (SELECT id FROM game_sign_exchange_gift WHERE user_id = ? order by create_date desc limit 0,1) as t)";

    /**
    * 字段描述：根据用户查询最后一次发放的激活码
    */
    private static final String QUERY_LAST_EXCHANGE_BY_USERID_AND_GAMEID = "SELECT * FROM game_sign_exchange_gift WHERE user_id = ? AND game_id = ? "
            + "ORDER BY CREATE_DATE LIMIT 0,1";

    /**
    * 字段描述：查询所有的发放的激活码
    */
    private static final String QUERY_ALL_EXCHANGE_GIFT = "SELECT e.user_id, e.user_name, e.gift_id, "
            + " activation_code, e.create_date, g.gift_name FROM game_sign_exchange_gift e,  game_sign_gift g"
            + " WHERE e.gift_id = g.gift_id";

    
    /**
    * 字段描述：查询兑换礼包的总数
    */
    private static final String QUERY_EXCHAGEGIFT_COUNT = "SELECT COUNT(1) AS count FROM game_sign_exchange_gift";
    
    private static final String QUERY_GIFT_BY_ID = "SELECT COUNT(1) AS count FROM game_sign_gift WHERE gift_id = ?";

    private static SignDaoImpl dao = new SignDaoImpl();

    private SignDaoImpl() {
    }

    /**
     * 创建时间： 2013-1-14 上午11:25:33
     * 创建人：xin.fang
     * 参数：
     * 返回值： SignDao
     * 方法描述 :
    */
    public static SignDao getInstance() {
        return dao;
    }

    @Override
    public int queryTypeScore(final int typeId) throws SQLException {
        final int[] score = new int[1];
        DB.select(SELECT_TYPE_SCORE, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    score[0] = rs.getInt("score");
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, typeId);

            }
        });
        return score[0];
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#addSignExchangeGift(com.sz7road.web.model.signmanage.SignExchangeGift)
    */
    @Override
    public boolean addSignExchangeGift(final SignExchangeGift exchangeGift) throws SQLException {
        boolean result = DB.insertUpdate(ADD_SIGNEXCHANGGIFT, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, exchangeGift.getUserId());
                stmt.setString(2, exchangeGift.getUserName());
                stmt.setInt(3, exchangeGift.getGiftId());
                stmt.setString(4, exchangeGift.getActivationCode());
                stmt.setBoolean(5, exchangeGift.getProvided());
                stmt.setTimestamp(6, DateUtil.getCurrentTimestamp());
                stmt.executeUpdate();
            }
        });
        return result;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#queryReceiveGift(int)
    */

    @Override
    public List<SignExchangeGift> queryReceiveGift(final int userId) throws SQLException {
        final List<SignExchangeGift> gifts = new ArrayList<SignExchangeGift>();
        DB.select(SELECT_USER_RECEIVE_GIFT, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                SignExchangeGift gift = null;
                while (rs.next()) {
                    gift = new SignExchangeGift();
                    gift.setUserId(rs.getInt("user_id"));
                    gift.setUserName(rs.getString("user_name"));
                    gift.setGiftId(rs.getInt("gift_id"));
                    gift.setGiftName(rs.getString("gift_name"));
                    gift.setActivationCode(rs.getString("activation_code"));
                    gift.setExchangeDate(rs.getTimestamp("create_date"));
                    gifts.add(gift);
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, userId);
                stmt.setBoolean(2, true);
            }
        });
        return gifts;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#updateLastSignExchangeGiftPrivateByUserIdAndGameId(int, int)
    */

    @Override
    public boolean updateLastSignExchangeGiftPrivateByUserIdAndGameId(final int userId, final int gameId,
            final boolean provided) throws SQLException {
        boolean result = DB.insertUpdate(UPDATE_PROVIDE, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setBoolean(1, provided);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        });
        return result;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#getLastSignExchangeByUserIdAndGameId(int, int)
    */

    @Override
    public SignExchangeGift getLastSignExchangeByUserIdAndGameId(final int userId, final int gameId)
            throws SQLException {
        final SignExchangeGift[] gifts = new SignExchangeGift[1];
        DB.select(QUERY_LAST_EXCHANGE_BY_USERID_AND_GAMEID, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    SignExchangeGift gift = new SignExchangeGift();
                    gift = new SignExchangeGift();
                    gift.setUserId(rs.getInt("user_id"));
                    gift.setUserName(rs.getString("user_name"));
                    gift.setGiftId(rs.getInt("gift_id"));
                    gift.setGiftName(rs.getString("gift_name"));
                    gift.setActivationCode(rs.getString("activation_code"));
                    gift.setExchangeDate(rs.getTimestamp("create_date"));
                    gifts[0] = gift;
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, userId);
                stmt.setInt(2, gameId);
            }
        });
        return gifts[0];
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#queryAllUserSignExchageGift()
    */

    @Override
    public List<SignExchangeGift> queryAllUserSignExchageGift(PageInfo pageInfo) throws SQLException {
        final List<SignExchangeGift> gifts = new ArrayList<SignExchangeGift>();
        StringBuilder sql = new StringBuilder();
        sql.append(QUERY_ALL_EXCHANGE_GIFT);
        sql.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());

        DB.select(sql.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    SignExchangeGift gift = new SignExchangeGift();
                    gift = new SignExchangeGift();
                    gift.setUserId(rs.getInt("user_id"));
                    gift.setUserName(rs.getString("user_name"));
                    gift.setGiftId(rs.getInt("gift_id"));
                    gift.setGiftName(rs.getString("gift_name"));
                    gift.setActivationCode(rs.getString("activation_code"));
                    gift.setExchangeDate(rs.getTimestamp("create_date"));
                    gifts.add(gift);
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {

            }
        });
        return gifts;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.SignDao#getSignExchangeGiftCount()
    */

    @Override
    public int getSignExchangeGiftCount() throws SQLException {
        final int[] count = new int[1];
        DB.select(QUERY_EXCHAGEGIFT_COUNT, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    count[0] = rs.getInt("count");
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {

            }
        });
        return count[0];
    }

    @Override
    public int queryGiftType(final int giftTypeId) throws SQLException {
        final int [] count = new int[1];
        DB.select(QUERY_GIFT_BY_ID, new ParamReadStH() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while(rs.next()){
                    count[0] = rs.getInt("count");
                }
                
            }
            
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
               stmt.setInt(1, giftTypeId);
                
            }
        });
        return count[0];
    }
    
    

}
