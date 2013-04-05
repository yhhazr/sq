/**
 * Copyright  2013-3-4 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:22:19
 * 版本号： v1.0
*/

package com.sz7road.web.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-4 上午11:22:19
 * 版本号： v1.0
*/
public interface GameDataTypeDao {

    /**
     * 增加游戏资料类型
     * 创建时间： 2013-3-4 上午11:29:42
     * 创建人：xin.fang
     * 参数： 
     * @param dataType 游戏资料类型 GameDateType
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean addGameDateType(GameDateType dataType) throws SQLException;

    /**
     * 删除游戏资料类型
     * 创建时间： 2013-3-4 上午11:30:49
     * 创建人：xin.fang
     * 参数： 
     * @param typeId 类型id
     * 返回值： boolean
     * 方法描述 :
     * @throws SQLException 
    */
    boolean deleteGameDateType(int typeId) throws SQLException;

    /**
     * 在同一父类型下是否还有其他类型的类型名和该类型的类型名相同，
     * 如果typeId为-1则说明是父类型下是否存在有该类型名的游戏资料类型
     * 创建时间： 2013-3-4 上午11:31:39
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
     * 在同一父类型下是否还有其他类型的类型简称名和该类型的类型简称名相同, 
     * 如果typeId为-1则说明是父类型下是否存在有该类型简称名的游戏资料类型
     * 创建时间： 2013-3-4 上午11:32:49
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
     * 创建时间： 2013-3-4 上午11:33:13
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
     * 创建时间： 2013-3-4 上午11:33:48
     * 创建人：xin.fang
     * 参数： 
     * @param typeId 类型id
     * 返回值： GameDateType
     * 方法描述 :
     * @throws SQLException 
    */
    GameDateType queryGameDateTypeById(int typeId) throws SQLException;

    /**
     * 获取游戏资料类型的总数
     * 创建时间： 2013-3-4 上午11:34:08
     * 创建人：xin.fang
     * 参数： 
     * 返回值： int
     * 方法描述 :
     * @throws SQLException 
    */
    int getCount() throws SQLException;

    /**
     * 分页查询游戏资料类型
     * 创建时间： 2013-3-4 上午11:34:34
     * 创建人：xin.fang
     * 参数： 
     * @param pageInfo 分页对象PageInfo
     * 返回值： List<GameDateType>
     * 方法描述 :
     * @throws SQLException 
    */
    List<GameDateType> queryGameDateTypes(PageInfo pageInfo) throws SQLException;

    /**
     * 查询游戏资料的根类型
     * 创建时间： 2013-3-4 下午2:30:57
     * 创建人：xin.fang
     * 参数： 
     * 返回值： List<GameDateType>
     * 方法描述 :
     * @throws SQLException 
    */
    List<GameDateType> queryGameDataParentType() throws SQLException;

}
