/**
 * Copyright  2013-3-4 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:00:20
 * 版本号： v1.0
*/

package com.sz7road.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-4 上午11:00:20
 * 版本号： v1.0
*/
public interface GameDataTypeService {

    /**
     * 父类型下是否已存在该类型名
     * 创建时间： 2013-3-4 上午11:04:14
     * 创建人：xin.fang
     * 参数： 
     * @param parentId 父类型id
     * @param typeName 类型名
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean hasGameDateType(int parentId, String typeName) throws SQLException;

    /**
     * 父类型下是否已存在该类型简称名
     * 创建时间： 2013-3-4 上午11:05:32
     * 创建人：xin.fang
     * 参数： 
     * @param parentId 父类型id
     * @param shortName 类型简称名
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean hasTypeShortName(int parentId, String shortName) throws SQLException;

    /**
     * 增加游戏资料类型
     * 创建时间： 2013-3-4 上午11:07:00
     * 创建人：xin.fang
     * 参数： 
     * @param dataType 游戏资料类型GameDateType
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean addGameDateType(GameDateType dataType) throws SQLException;

    /**
     * 根据类型id删除游戏资料类型
     * 创建时间： 2013-3-4 上午11:07:52
     * 创建人：xin.fang
     * 参数：
     * @param typeId 游戏资料类型id 
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean deleteGameDateType(int typeId) throws SQLException;

    /**
     * 在同一父类型下是否还有其他类型的类型名和该类型的类型名相同
     * 创建时间： 2013-3-4 上午11:08:55
     * 创建人：xin.fang
     * 参数： 
     * @param parentId 父类型id
     * @param typeId 类型id
     * @param typeName 类型名
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean hasGameDateType(int parentId, int typeId, String typeName) throws SQLException;

    /**
     * 在同一父类型下是否还有其他类型的类型简称名和该类型的类型简称名相同
     * 创建时间： 2013-3-4 上午11:12:04
     * 创建人：xin.fang
     * 参数： 
     * @param parentId 父类型id
     * @param typeId 类型id
     * @param shortName 类型简称名
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean hasTypeShortName(int parentId, int typeId, String shortName) throws SQLException;

    /**
     * 编辑游戏资料类型
     * 创建时间： 2013-3-4 上午11:12:58
     * 创建人：xin.fang
     * 参数： 
     * @param dataType 游戏资料类型GameDateType
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean editGameDateType(GameDateType dataType) throws SQLException;

    /**
     * 根据类型id获取游戏资料类型
     * 创建时间： 2013-3-4 上午11:13:32
     * 创建人：xin.fang
     * 参数： 
     * @param typeId 类型id
     * 返回值： GameDateType
     * 方法描述 :
     * @throws SQLException 
    */
    GameDateType queryGameDateTypeById(int typeId) throws SQLException;

    /**
     * 分页查询游戏资料类型
     * 创建时间： 2013-3-4 上午11:14:10
     * 创建人：xin.fang
     * 参数： 
     * @param pageInfo 分页对象PageInfo
     * 返回值： PaginationResult<GameDateType>
     * 方法描述 :
     * @throws SQLException 
    */
    PaginationResult<GameDateType> queryGameDateTypes(PageInfo pageInfo) throws SQLException;

    /**
     * 查询游戏资料类型的根类型
     * 创建时间： 2013-3-4 下午2:30:11
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<GameDateType>
     * 方法描述 :
     * @throws SQLException 
    */
    List<GameDateType> queryGameDataParentType() throws SQLException;

}
