/**
 * Copyright  2013-2-5 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:38:17
 * 版本号： v1.0
*/

package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-5 上午10:38:17
 * 版本号： v1.0
*/
public interface AdInfoDao {

    /**
     * 创建时间： 2013-2-5 上午10:38:35
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 : 获取广告信息的总数
     * @throws SQLException 
    */
    int getAdInfoCount() throws SQLException;

    /**
     * 创建时间： 2013-2-5 上午10:39:06
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<AdInfo>
     * 方法描述 : 分页查询广告信息
     * @throws SQLException 
    */
    List<AdInfo> queryAllAdInfo(PageInfo pageInfo) throws SQLException;

    /**
     * 创建时间： 2013-2-5 下午12:17:14
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 : 获取广告注册信息的总数
     * @throws SQLException 
    */
    int getRegInfoCount() throws SQLException;

    /**
     * 创建时间： 2013-2-5 下午12:17:47
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<RegInfo>
     * 方法描述 : 分页获取广告注册信息
     * @throws SQLException 
    */
    List<RegInfo> queryAllRegInfo(PageInfo pageInfo) throws SQLException;
}
