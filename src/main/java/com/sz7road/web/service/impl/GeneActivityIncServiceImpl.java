/**
 * Copyright  2013-2-26 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:20:37
 * 版本号： v1.0
*/

package com.sz7road.web.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.dao.ActivityIncDao;
import com.sz7road.web.dao.impl.ActivityIncDaoImpl;
import com.sz7road.web.model.activity.ActivityInc;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.service.GeneActivityIncService;

/**
 * 类描述：活动简介页面生成所需数据接口实现类
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-26 下午5:20:37
 * 版本号： v1.0
*/
public class GeneActivityIncServiceImpl implements GeneActivityIncService {

    private ActivityIncDao dao;

    private int pageCount;

    private int pageSize;

    private int currentPage;

    public GeneActivityIncServiceImpl() {
        dao = ActivityIncDaoImpl.getInstance();
        setPageCount();
    }

    private void setPageCount() {
        String pageSizeStr = SystemConfig.getProperty("activity.introduction.page.size");
        if (StringUtils.isNumeric(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = AppConstants.ACTIVITY_INC_PAGE_SIZE;
        }
        try {
            int count = dao.getActivityIncCount();
            pageCount = (count + (pageSize - 1)) / pageSize;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasMoreData() {
        if (currentPage < pageCount) {
            return true;
        }
        return false;
    }

    @Override
    public Map<String, Object> getActivityIncData() {
        PageInfo pageInfo = new PageInfo(currentPage * pageSize, pageSize);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            List<ActivityInc> activityIncs = dao.queryActivity(pageInfo, 0);
            for (ActivityInc activityInc : activityIncs) {
//                if(activityInc.getActivityName().length() > 11){
//                    activityInc.setActivityName(activityInc.getActivityName().substring(0, 11) + "...");
//                }
                if(activityInc.getActivityIntroduction().length() > 80){
                    activityInc.setActivityIntroduction(activityInc.getActivityIntroduction().substring(0, 80) + "...");
                }
            }
            dataMap.put("activityIncs", activityIncs);
            dataMap.put("totalPage", pageCount);
            dataMap.put("currentPage", currentPage + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currentPage++;
        return dataMap;
    }

}
