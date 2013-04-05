/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:01:25
 * 版本号： v1.0
*/

package com.sz7road.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.pureart.persistement.database.easydb.DB;
import org.pureart.persistement.database.easydb.IUStH;
import org.pureart.persistement.database.easydb.ParamReadStH;

import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.dao.ActivityIncDao;
import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午5:01:25
 * 版本号： v1.0
*/
public class ActivityIncDaoImpl implements ActivityIncDao {

    private static ActivityIncDaoImpl _instance = new ActivityIncDaoImpl();

    private final String INSERT_ACTIVITYINC = "INSERT INTO ddt_activity_introduction(activity_name, activity_inc, activity_url,"
            + " activity_img, start_date, end_date, create_date) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private final String QUERY_ACTIVITYINC_BY_ID = "SELECT * FROM ddt_activity_introduction WHERE activity_id = ?";

    private final String UPDATE_AVTIVITYINC = "UPDATE ddt_activity_introduction SET activity_name = ?, activity_inc = ?, "
            + " activity_url = ?, activity_img = ?, start_date = ?, end_date = ? WHERE activity_id = ?";
    
    private final String DELETE_ACTIVITYINC = "DELETE FROM ddt_activity_introduction WHERE activity_id = ?";
    
    private final String ACTIVITYINC_COUNT = "SELECT COUNT(*) as count FROM ddt_activity_introduction";
    
    private final String SELECT_ACTIVITYINC = "SELECT * FROM ddt_activity_introduction";

    private ActivityIncDaoImpl() {
    }

    public static ActivityIncDao getInstance() {
        return _instance;
    }

    @Override
    public boolean addActivityInc(final ActivityInc inc) throws SQLException {

        boolean isInsert = DB.insertUpdate(INSERT_ACTIVITYINC, new IUStH() {

            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, inc.getActivityName());
                stmt.setString(2, inc.getActivityIntroduction());
                stmt.setString(3, inc.getActivityUrl());
                stmt.setString(4, inc.getActivityImg());
                stmt.setTimestamp(5, new Timestamp(inc.getStartDate().getTime()));
                stmt.setTimestamp(6, new Timestamp(inc.getEndDate().getTime()));
                stmt.setTimestamp(7, DateUtil.getCurrentTimestamp());
                stmt.executeUpdate();
            }
        });

        return isInsert;
    }

    @Override
    public ActivityInc queryActivityIncById(final int activityId) throws SQLException {
        final ActivityInc activityInc = new ActivityInc();
        DB.select(QUERY_ACTIVITYINC_BY_ID, new ParamReadStH() {

            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    activityInc.setActivityId(rs.getInt("activity_id"));
                    activityInc.setActivityImg(rs.getString("activity_img"));
                    activityInc.setActivityIntroduction(rs.getString("activity_inc"));
                    activityInc.setActivityName(rs.getString("activity_name"));
                    activityInc.setActivityUrl(rs.getString("activity_url"));
                    activityInc.setCreateDate(rs.getTimestamp("create_date"));
                    activityInc.setEndDate(rs.getTimestamp("end_date"));
                    activityInc.setStartDate(rs.getTimestamp("start_date"));
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, activityId);
            }
        });
        return activityInc;
    }

    @Override
    public boolean editActivityInc(final ActivityInc inc) throws SQLException {
        boolean isUpdate = DB.insertUpdate(UPDATE_AVTIVITYINC, new IUStH() {
            
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, inc.getActivityName());
                stmt.setString(2, inc.getActivityIntroduction());
                stmt.setString(3, inc.getActivityUrl());
                stmt.setString(4, inc.getActivityImg());
                stmt.setTimestamp(5, new Timestamp(inc.getStartDate().getTime()));
                stmt.setTimestamp(6, new Timestamp(inc.getEndDate().getTime()));
                stmt.setInt(7, inc.getActivityId());
                stmt.executeUpdate();
            }
        });
        return isUpdate;
    }

    @Override
    public boolean deleteActivityincById(final int activityId) throws SQLException {
        boolean isDelete = DB.insertUpdate(DELETE_ACTIVITYINC, new IUStH() {
            
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, activityId);
                stmt.executeUpdate();
            }
        });
        return isDelete;
    }

    @Override
    public int getActivityIncCount() throws SQLException {
        final int [] count = new int[1];
        DB.select(ACTIVITYINC_COUNT, new ParamReadStH() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                while(rs.next()){
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
    public List<ActivityInc> queryActivity(PageInfo pageInfo, int sequenceId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_ACTIVITYINC);
        if(sequenceId == 0){
            sql.append(" order by create_date desc ");
        }
        sql.append(" limit ").append(pageInfo.getStartRow()).append(" , ").append(pageInfo.getPageSize());
        final List<ActivityInc> list = new ArrayList<ActivityInc>();
        DB.select(sql.toString(), new ParamReadStH() {
            
            @Override
            public void handleRead(ResultSet rs) throws SQLException {
                ActivityInc inc = null;
                while(rs.next()){
                   inc = new ActivityInc();
                   inc.setActivityId(rs.getInt("activity_id"));
                   inc.setActivityImg(rs.getString("activity_img"));
                   inc.setActivityIntroduction(rs.getString("activity_inc"));
                   inc.setActivityName(rs.getString("activity_name"));
                   inc.setActivityUrl(rs.getString("activity_url"));
                   inc.setCreateDate(rs.getTimestamp("create_date"));
                   inc.setEndDate(rs.getTimestamp("end_date"));
                   inc.setStartDate(rs.getTimestamp("start_date"));
                   list.add(inc);
               }
            }
            
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                
            }
        });
        return list;
    }

}
