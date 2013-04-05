/**
 * Copyright  2013-2-5 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:33:36
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sz7road.web.dao.AdInfoDao;
import com.sz7road.web.dao.impl.AdInfoDaoImpl;
import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.AdInfoService;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-5 上午10:33:36
 * 版本号： v1.0
*/
public class AdInfoServiceImpl implements AdInfoService {
    
    private static AdInfoServiceImpl _instance;
    
    private AdInfoDao dao;
    private AdInfoServiceImpl(){
        dao = AdInfoDaoImpl.getInstance();
    }
    
    public static AdInfoService getInstance() {
        if(null == _instance){
            _instance = new AdInfoServiceImpl();
        }
        return _instance;
    }

    @Override
    public PaginationResult<AdInfo> quereyAllAdInfo(PageInfo pageInfo) throws SQLException {
        int totalCount = dao.getAdInfoCount();
        List<AdInfo> adInfos = dao.queryAllAdInfo(pageInfo);
        PaginationResult<AdInfo> paginationResult = new PaginationResult<AdInfo>(totalCount, adInfos);
        return paginationResult;
    }

    @Override
    public PaginationResult<RegInfo> quereyAllRegInfo(PageInfo pageInfo) throws SQLException {
        int totalCount = dao.getRegInfoCount();
        List<RegInfo> regInfos = dao.queryAllRegInfo(pageInfo);
        PaginationResult<RegInfo> paginationResult = new PaginationResult<RegInfo>(totalCount, regInfos);
        return paginationResult;
    }

}
