/**
 * Copyright  2013-2-22 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午3:39:26
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.sql.SQLException;

import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-22 下午3:39:26
 * 版本号： v1.0
*/
public interface ActivityIncService {

    /**
     * 增加活动简介
     * 创建时间： 2013-2-22 下午3:42:05
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean addActivityInc(ActivityInc inc) throws SQLException;

    /**
     * 根据id查询活动简介
     * 创建时间： 2013-2-22 下午3:52:15
     * 创建人：xin.fang
     * 参数： 
     * 返回值： ActivityInc
     * 方法描述 :
     * @throws SQLException 
    */
    ActivityInc queryActivityIncById(int activityId) throws SQLException;

    /**
     * 创建时间： 2013-2-22 下午4:36:02
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 : 修改活动简介
     * @throws SQLException 
    */
    boolean editActivityInc(ActivityInc inc) throws SQLException;

    /**
     * 删除活动简介
     * 创建时间： 2013-2-22 下午4:39:18
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean deleteActivityincById(int activityId) throws SQLException;

    /**
     * 分页获取活动简介
     * 创建时间： 2013-2-22 下午4:46:57
     * 创建人：xin.fang
     * 参数： 
     * @param pageInfo 分页对象
     * @param serquenceId 排序id, 0为倒叙，1为顺序
     * 返回值： PaginationResult<ActivityInc>
     * 方法描述 :
     * @throws SQLException 
    */
    PaginationResult<ActivityInc> queryActivityInc(PageInfo pageInfo, int serquenceId) throws SQLException;


}
