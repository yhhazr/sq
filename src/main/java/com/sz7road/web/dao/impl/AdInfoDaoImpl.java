/**
 * Copyright  2013-2-5 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:39:53
 * 版本号： v1.0
*/

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.dao.AdInfoDao;
import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-5 上午10:39:53
 * 版本号： v1.0
*/
public class AdInfoDaoImpl implements AdInfoDao {

    private static final String QUERY_ALL_ADINFO = "SELECT * FROM ad_info ORDER BY date desc";

    private static final String QUERY_ADINFO_COUNT = "SELECT COUNT(1) AS count FROM ad_info";

    private static final String QUERY_REGINFO_COUNT = "SELECT COUNT(1) AS count FROM reg_info";

    private static final String QUERY_ALL_REGINFO = "SELECT * FROM reg_info ORDER BY regTime desc";

    private static AdInfoDaoImpl impl = new AdInfoDaoImpl();

    private AdInfoDaoImpl() {
    }

    public static AdInfoDao getInstance() {
        return impl;
    }

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.AdInfoDao#getAdInfoCount()
    */

    @Override
    public int getAdInfoCount() throws SQLException {
        final int[] count = new int[1];
        DB.select(QUERY_ADINFO_COUNT, new ParamReadStH() {

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

    /* (non-Javadoc)
    * @see com.sz7road.web.dao.AdInfoDao#queryAllAdInfo(com.sz7road.web.model.pagination.PageInfo)
    */

    @Override
    public List<AdInfo> queryAllAdInfo(PageInfo pageInfo) throws SQLException {
        final List<AdInfo> infos = new ArrayList<AdInfo>();
        StringBuilder sql = new StringBuilder();
        sql.append(QUERY_ALL_ADINFO);
        sql.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                AdInfo adInfo = null;
                while (rs.next()) {
                    adInfo = new AdInfo();
                    adInfo.setAdId(rs.getInt("adId"));
                    adInfo.setId(rs.getInt("id"));
                    adInfo.setSite(rs.getString("site"));
                    adInfo.setEnterTimes(rs.getInt("enterTimes"));
                    adInfo.setRegTimes(rs.getInt("regTimes"));
                    adInfo.setDate(rs.getString("date"));
                    infos.add(adInfo);
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {

            }
        });
        return infos;
    }

    @Override
    public int getRegInfoCount() throws SQLException {
        final int[] count = new int[1];
        DB.select(QUERY_REGINFO_COUNT, new ParamReadStH() {

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
    public List<RegInfo> queryAllRegInfo(PageInfo pageInfo) throws SQLException {
        final List<RegInfo> infos = new ArrayList<RegInfo>();
        StringBuilder sql = new StringBuilder();
        sql.append(QUERY_ALL_REGINFO);
        sql.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        DB.select(sql.toString(), new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                RegInfo regInfo = null;
                while (rs.next()) {
                    regInfo = new RegInfo();
                    regInfo.setId(rs.getInt("id"));
                    regInfo.setUseId(rs.getInt("userId"));
                    regInfo.setUserName(rs.getString("userName"));
                    regInfo.setAdId(rs.getInt("adId"));
                    regInfo.setRegTime(rs.getTimestamp("regTime"));
                    infos.add(regInfo);
                }
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {

            }
        });
        return infos;
    }

}
