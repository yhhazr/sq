/**
 * Copyright  2013-2-5 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:30:36
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.sql.SQLException;

import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-5 上午10:30:36
 * 版本号： v1.0
*/
public interface AdInfoService {

    /**
     * 创建时间： 2013-2-5 上午10:30:46
     * 创建人：xin.fang
     * 参数： 
     * @param pageInfo 分页对象
     * 返回值： PaginationResult<AdInfo>
     * 方法描述 : 分页获取广告信息
     * @throws SQLException 
    */
    PaginationResult<AdInfo> quereyAllAdInfo(PageInfo pageInfo) throws SQLException;

    /**
     * 创建时间： 2013-2-5 下午12:15:59
     * 创建人：xin.fang
     * 参数： 
     * 返回值： PaginationResult<RegInfo>
     * 方法描述 :分页获取所有的广告注册信息
     * @throws SQLException 
    */
    PaginationResult<RegInfo> quereyAllRegInfo(PageInfo pageInfo) throws SQLException;

}
