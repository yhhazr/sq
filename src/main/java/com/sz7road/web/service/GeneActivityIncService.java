/**
 * Copyright  2013-2-26 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:19:41
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.util.Map;

/**
 * 类描述：活动简介页面生成所需数据接口
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-26 下午5:19:41
 * 版本号： v1.0
*/
public interface GeneActivityIncService {

    /**
     * 创建时间： 2013-2-26 下午5:32:36
     * 创建人：xin.fang
     * 参数： 
     * 返回值： boolean
     * 方法描述 : 查看是否还有更多的活动简介数据
    */
    boolean hasMoreData();

    /**
     * 创建时间： 2013-2-26 下午5:33:10
     * 创建人：xin.fang
     * 参数： 
     * 返回值： Map<String,Object>
     * 方法描述 : 获取活动简介数据
    */
    Map<String, Object> getActivityIncData();

}
