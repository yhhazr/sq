/**
 * Copyright  2013-3-4 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午11:01:29
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sz7road.web.dao.GameDataTypeDao;
import com.sz7road.web.dao.impl.GameDataTypeDaoImpl;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameDataTypeService;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-4 上午11:01:29
 * 版本号： v1.0
*/
public class GameDataTypeServiceImpl implements GameDataTypeService {
    
    
    private static GameDataTypeServiceImpl _instance;
    private GameDataTypeDao typeDao;
    private GameDataTypeServiceImpl(){
        typeDao = GameDataTypeDaoImpl.getInstance();
    }
    
    public static GameDataTypeService getInstance() {
        if(null == _instance){
            _instance = new GameDataTypeServiceImpl();
        }
        return _instance;
    }

    @Override
    public boolean hasGameDateType(int parentId, String typeName) throws SQLException {
        return this.hasGameDateType(parentId, -1, typeName);
    }

    @Override
    public boolean hasTypeShortName(int parentId, String shortName) throws SQLException {
        return this.hasTypeShortName(parentId, -1, shortName);
    }

    @Override
    public boolean addGameDateType(GameDateType dataType) throws SQLException {
        return typeDao.addGameDateType(dataType);
    }

    @Override
    public boolean deleteGameDateType(int typeId) throws SQLException {
        return typeDao.deleteGameDateType(typeId);
    }

    @Override
    public boolean hasGameDateType(int parentId, int typeId, String typeName) throws SQLException {
        return typeDao.hasGameDateType(parentId, typeId, typeName);
    }

    @Override
    public boolean hasTypeShortName(int parentId, int typeId, String shortName) throws SQLException {
        return typeDao.hasTypeShortName(parentId, typeId, shortName);
    }

    @Override
    public boolean editGameDateType(GameDateType dataType) throws SQLException {
        return typeDao.editGameDateType(dataType);
    }

    @Override
    public GameDateType queryGameDateTypeById(int typeId) throws SQLException {
        return typeDao.queryGameDateTypeById(typeId);
    }

    @Override
    public PaginationResult<GameDateType> queryGameDateTypes(PageInfo pageInfo) throws SQLException {
        int count = typeDao.getCount();
        List<GameDateType> types = typeDao.queryGameDateTypes(pageInfo);
        PaginationResult<GameDateType> paginationResult = new PaginationResult<GameDateType>(count, types);
        return paginationResult;
    }

    @Override
    public List<GameDateType> queryGameDataParentType() throws SQLException {
        return typeDao.queryGameDataParentType();
    }

}
