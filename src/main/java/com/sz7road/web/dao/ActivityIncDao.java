/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:01:04
 * 版本号： v1.0
*/

package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午5:01:04
 * 版本号： v1.0
*/
public interface ActivityIncDao {

    /** 
     * 创建时间： 2013-2-22 下午5:20:05
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 ：增加活动简介
     * @throws SQLException 
    */
    boolean addActivityInc(ActivityInc inc) throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午5:20:26
     * 创建人：xin.fang
     * 参数： 
     * @param activityId 活动简介id
     * 返回值： ActivityInc
     * 方法描述 :根据id查询活动简介
     * @throws SQLException 
    */
    ActivityInc queryActivityIncById(int activityId) throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午5:21:03
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 : 编辑活动简介
     * @throws SQLException 
    */
    boolean editActivityInc(ActivityInc inc) throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午5:21:21
     * 创建人：xin.fang
     * 参数： 
     * @param activityId 活动简介id
     * 返回值： boolean
     * 方法描述 : 根据id删除活动简介
     * @throws SQLException 
    */
    boolean deleteActivityincById(int activityId) throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午5:22:06
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :获取活动简介的总数
     * @throws SQLException 
    */
    int getActivityIncCount() throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午5:22:24
     * 创建人：xin.fang
     * 参数： 
     * @param pageInfo
     * @param sequenceId 排序id， 0为倒叙，1为顺序
     * 返回值： List<ActivityInc>
     * 方法描述 :分页获取活动简介
     * @throws SQLException 
    */
    List<ActivityInc> queryActivity(PageInfo pageInfo, int sequenceId) throws SQLException;

}
