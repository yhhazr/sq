/**
 * Copyright  2013-2-5 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午9:59:02
 * 版本号： v1.0
*/

package com.sz7road.web.action.admin.adinfomanage;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.adinfo.AdInfo;
import com.sz7road.web.model.adinfo.RegInfo;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.AdInfoService;

/**
 * 类描述： 管理广告点击及注册信息
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-2-5 上午9:59:02
 * 版本号： v1.0
*/
public class AdInfoManageAction extends ActionSupport implements RequestAware {
    
    private static final long serialVersionUID = 1L;
    
    private Map<String, Object> requestMap;
    
    private PageInfo pager;
    
    /**
     * 查询所有的广告信息
     * 创建时间： 2013-2-5 上午10:56:10
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String queryAllAdInfo(){
        System.out.println("开始获取adinfo==========");
        PageInfo pageInfo = new PageInfo();
        HttpServletRequest request = ServletActionContext.getRequest();
        int startRow = 0;
        String pagerOffset = request.getParameter("pager.offset");
        int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.adinfo.page.size"));
        if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
            startRow = Integer.parseInt(pagerOffset);
        }
        pageInfo.setStartRow(startRow);
        pageInfo.setPageSize(pageSize);
        AdInfoService adService = ServiceLocator.getAdInfoService();
        try {
            System.out.println("开始获取adinfo==========list");
            PaginationResult<AdInfo> paginationResult = adService.quereyAllAdInfo(pageInfo);
            requestMap.put("paginationResult", paginationResult);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("adInfo查询失败====1");
            return INPUT;
        }
        System.out.println("adInfo查询成功====1");
        return SUCCESS;
    }
    
    
    /**
     * 创建时间： 2013-2-5 下午2:12:36
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :查询所有的广告注册信息
    */
    public String  queryAllRegInfo(){
        PageInfo pageInfo = new PageInfo();
        HttpServletRequest request = ServletActionContext.getRequest();
        int startRow = 0;
        String pagerOffset = request.getParameter("pager.offset");
        int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.adinfo.page.size"));
        if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
            startRow = Integer.parseInt(pagerOffset);
        }
        pageInfo.setStartRow(startRow);
        pageInfo.setPageSize(pageSize);
        
        AdInfoService adService = ServiceLocator.getAdInfoService();
        try {
            PaginationResult<RegInfo> paginationResult = adService.quereyAllRegInfo(pageInfo);
            requestMap.put("paginationResult", paginationResult);
        } catch (SQLException e) {
            e.printStackTrace();
            return INPUT;
        }
        return SUCCESS;
    }

    public PageInfo getPager() {
        return pager;
    }

    public void setPager(PageInfo pager) {
        this.pager = pager;
    }

    @Override
    public void setRequest(Map<String, Object> request) {
        this.requestMap = request;
    }
}
