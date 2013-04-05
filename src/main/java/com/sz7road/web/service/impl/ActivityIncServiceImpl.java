/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午3:40:35
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.dao.ActivityIncDao;
import com.sz7road.web.dao.impl.ActivityIncDaoImpl;
import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.ActivityIncService;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午3:40:35
 * 版本号： v1.0
*/
public class ActivityIncServiceImpl implements ActivityIncService {
    
    private static ActivityIncServiceImpl _instance;
    
    private ActivityIncDao dao;
    
    private ActivityIncServiceImpl(){
        dao = ActivityIncDaoImpl.getInstance();
    }

    public static ActivityIncService getInstance() {
       if(null == _instance){
           _instance = new ActivityIncServiceImpl();
       }
        return _instance;
    }

    @Override
    public boolean addActivityInc(ActivityInc inc) throws SQLException {
        return dao.addActivityInc(inc);
    }

    @Override
    public ActivityInc queryActivityIncById(int activityId) throws SQLException {
        return dao.queryActivityIncById(activityId);
    }

    @Override
    public boolean editActivityInc(ActivityInc inc) throws SQLException {
        return dao.editActivityInc(inc);
    }

    @Override
    public boolean deleteActivityincById(int activityId) throws SQLException {
        return dao.deleteActivityincById(activityId);
    }

    @Override
    public PaginationResult<ActivityInc> queryActivityInc(PageInfo pageInfo, int serquenceId) throws SQLException {
        int count = dao.getActivityIncCount();
        List<ActivityInc> activityIncList = dao.queryActivity(pageInfo, serquenceId);
        PaginationResult<ActivityInc> result = new PaginationResult<ActivityInc>(count, activityIncList);
        return result;
    }

}
