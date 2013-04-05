/**
 * Copyright  2013-3-4 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 上午10:00:41
 * 版本号： v1.0
*/

package com.sz7road.web.action.admin.gamedate;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gameDateMag.GameDateType;
import com.sz7road.web.model.pagination.PageInfo;
import com.sz7road.web.model.pagination.PaginationResult;
import com.sz7road.web.service.GameDataTypeService;

/**
 * 类描述：
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-4 上午10:00:41
 * 版本号： v1.0
*/
public class GameDateTypeAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private Logger logger = Logger.getLogger(GameDateTypeAction.class);

    private PageInfo page;

    private GameDateType dataType;

    private List<GameDateType> gameDateParentTypes;

    private String id;

    /**
     * 查询游戏资料的根类型
     * 创建时间： 2013-3-4 下午2:48:08
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String addGameDataType() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            gameDateParentTypes = typeService.queryGameDataParentType();
        } catch (Exception e) {
            logger.error("查询游戏资料根类型失败\n" + e.getMessage(), e);
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 增加游戏资料类型
     * 创建时间： 2013-3-4 上午10:01:31
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String addGameDataTypeSubmit() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            //判断在同一个父类型下是否已经存在该类型，如果存在则不增加
            boolean hasTypeName = typeService.hasGameDateType(dataType.getParentId(), dataType.getTypeName());
            boolean hasShortName = typeService.hasTypeShortName(dataType.getParentId(), dataType.getShortName());
            if (!hasTypeName && !hasShortName) {
                boolean isInsert = typeService.addGameDateType(dataType);
                if (isInsert) {
                    return SUCCESS;
                }
            } else {
                if (hasTypeName) {
                    this.addFieldError("dataType.typeName", this.getText("admin.dataType.typeName.exists.error"));
                } else {
                    this.addFieldError("dataType.shortName", this.getText("admin.dataType.shortName.exists.error"));
                }
            }
        } catch (Exception e) {
            logger.error("增加游戏资料类型失败\n" + e.getMessage(), e);
        }
        return INPUT;
    }

    /**
     * 删除游戏资料类型
     * 创建时间： 2013-3-4 上午10:02:28
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String deleteGameDataType() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            if (StringUtils.isNotBlank(id)) {
                String[] ids = id.split(",");
                for (String idStr : ids) {
                    if (StringUtils.isNotBlank(idStr) && StringUtils.isNumeric(idStr)) {
                        boolean result = typeService.deleteGameDateType(Integer.parseInt(idStr));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("删除游戏资料类型失败\n" + e.getMessage(), e);
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 修改游戏资料类型
     * 创建时间： 2013-3-4 上午10:03:07
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String editGameDataTypeSubmit() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            boolean hasTypeName = typeService.hasGameDateType(dataType.getParentId(), dataType.getTypeId(),
                    dataType.getTypeName());
            boolean hasShortName = typeService.hasTypeShortName(dataType.getParentId(), dataType.getTypeId(),
                    dataType.getShortName());
            if (!hasTypeName && !hasShortName) {
                boolean isEdit = typeService.editGameDateType(dataType);
                if (isEdit) {
                    return SUCCESS;
                }
            } else {
                if (hasTypeName) {
                    this.addFieldError("dataType.typeName", this.getText("admin.dataType.typeName.exists.error"));
                } else {
                    this.addFieldError("dataType.shortName", this.getText("admin.dataType.shortName.exists.error"));
                }
            }
        } catch (Exception e) {
            logger.error("编辑游戏资料类型失败\n" + e.getMessage(), e);
        }
        return INPUT;
    }

    /**
     * 根据id查询游戏资料类型
     * 创建时间： 2013-3-4 上午10:03:54
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String editGameDataType() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            gameDateParentTypes = typeService.queryGameDataParentType();
            if(StringUtils.isNotBlank(id) && StringUtils.isNumericSpace(id)){
                dataType = typeService.queryGameDateTypeById(Integer.parseInt(id));
            }
        } catch (Exception e) {
            logger.error("根据id查询游戏资料类型失败\n" + e.getMessage(), e);
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * 分页 查询游戏资料类型
     * 创建时间： 2013-3-4 上午10:04:41
     * 创建人：xin.fang
     * 参数： 
     * 返回值： String
     * 方法描述 :
    */
    public String queryGameDataTypes() {
        HttpServletRequest request = ServletActionContext.getRequest();
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();

        PageInfo pageInfo = new PageInfo();
        int startRow = 0;
        String pagerOffset = request.getParameter("pager.offset");
        int pageSize = Integer.parseInt(SystemConfig.getProperty("admin.search.page.size"));
        if (!StringUtils.isBlank(pagerOffset) && StringUtils.isNumeric(pagerOffset)) {
            startRow = Integer.parseInt(pagerOffset);
        }
        pageInfo.setStartRow(startRow);
        pageInfo.setPageSize(pageSize);
        try {
            PaginationResult<GameDateType> paginationResult = typeService.queryGameDateTypes(pageInfo);
            request.setAttribute("paginationResult", paginationResult);
        } catch (Exception e) {
            logger.error("分页查询游戏资料类型失败\n" + e.getMessage(), e);
            return INPUT;
        }
        return SUCCESS;
    }

    public void validateAddGameDataTypeSubmit() {
        validateProperties();
    }

    public void validateEditGameDataTypeSubmit() {
        validateProperties();
    }

    /**
     * 验证页面传递的属性
     * 创建时间： 2013-3-4 上午10:22:40
     * 创建人：xin.fang
     * 参数： 
     * 返回值： void
     * 方法描述 :
    */
    private void validateProperties() {
        String shortName = dataType.getShortName();
        if (StringUtils.isBlank(shortName)) {
            this.addFieldError("dataType.shortName", this.getText("admin.dataType.shortName.null.error"));
        } else {
            Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_]{1,20}$");
            Matcher matcher = pattern.matcher(shortName);
            if (!matcher.matches()) {
                this.addFieldError("dataType.shortName", this.getText("admin.dataType.shortName.format.error"));
            }
        }

        String typeName = dataType.getTypeName();
        if (StringUtils.isBlank(typeName)) {
            this.addFieldError("dataType.typeName", this.getText("admin.dataType.typeName.null.error"));
        } else {
            Pattern pattern = Pattern.compile("^(?!_)(?!.*?_$)[\u4e00-\u9fa5_a-zA-Z0-9]{1,10}$");
            Matcher matcher = pattern.matcher(typeName);
            if (!matcher.matches()) {
                this.addFieldError("dataType.typeName", this.getText("admin.dataType.typeName.format.error"));
            }
        }
        if (dataType.getParentId() == 0) {
            this.addFieldError("dataType.parentId", this.getText("admin.dataType.parentId.select.error"));
        }
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public GameDateType getDataType() {
        return dataType;
    }

    public void setDataType(GameDateType dataType) {
        this.dataType = dataType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GameDateType> getGameDateParentTypes() {
        GameDataTypeService typeService = ServiceLocator.getGameDataTypeService();
        try {
            gameDateParentTypes = typeService.queryGameDataParentType();
        } catch (Exception e) {
            logger.error("查询游戏资料根类型失败\n" + e.getMessage(), e);
        }
        return gameDateParentTypes;
    }

    public void setGameDateParentTypes(List<GameDateType> gameDateParentTypes) {
        this.gameDateParentTypes = gameDateParentTypes;
    }

   

}
