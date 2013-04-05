/**
 * $Header: /data/cvs/esmcol/src/java/com/gs/sitecore/common/utils/ServiceLocator.java,v 1.8 2009/03/17 03:39:20 mzheng Exp $ 
 * $Revision: 1.8 $ 
 * $Date: 2009/03/17 03:39:20 $ 
 * 
 * ==================================================================== 
 * 
 * Copyright (c) 2006 Media Data Systems Pte Ltd All Rights Reserved. 
 * This software is the confidential and proprietary information of 
 * Media Data Systems Pte Ltd. You shall not disclose such Confidential 
 * Information. 
 * 
 * ==================================================================== 
 * 
 */

package com.sz7road.web.common.util;

import com.sz7road.web.service.ActivityIncService;
import com.sz7road.web.service.AdInfoService;
import com.sz7road.web.service.AdStatisticsService;
import com.sz7road.web.service.CaptchaService;
import com.sz7road.web.service.DirtyService;
import com.sz7road.web.service.DownloadCenterSer;
import com.sz7road.web.service.GameDataTypeService;
import com.sz7road.web.service.GameDateSer;
import com.sz7road.web.service.GameEnterInfoStatisticsService;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GameService;
import com.sz7road.web.service.GeneHomepageService;
import com.sz7road.web.service.GeneNewsPageService;
import com.sz7road.web.service.HomepageService;
import com.sz7road.web.service.MsgService;
import com.sz7road.web.service.NewServerReservationService;
import com.sz7road.web.service.NewsService;
import com.sz7road.web.service.OnlineUserService;
import com.sz7road.web.service.PermissionService;
import com.sz7road.web.service.PhotoService;
import com.sz7road.web.service.RoleService;
import com.sz7road.web.service.SignService;
import com.sz7road.web.service.SkillTemplateService;
import com.sz7road.web.service.TempHtmlService;
import com.sz7road.web.service.UserService;
import com.sz7road.web.service.VideoService;
import com.sz7road.web.service.impl.ActivityIncServiceImpl;
import com.sz7road.web.service.impl.AdInfoServiceImpl;
import com.sz7road.web.service.impl.AdStatisticsServiceImpl;
import com.sz7road.web.service.impl.CaptchaServiceImpl;
import com.sz7road.web.service.impl.DirtyServiceImpl;
import com.sz7road.web.service.impl.DownloadCenterSerImpl;
import com.sz7road.web.service.impl.GameDataTypeServiceImpl;
import com.sz7road.web.service.impl.GameDateSerImpl;
import com.sz7road.web.service.impl.GameEnterInfoStatisticsServiceImpl;
import com.sz7road.web.service.impl.GameServerServiceImpl;
import com.sz7road.web.service.impl.GameServiceImpl;
import com.sz7road.web.service.impl.GeneHomepageServiceImpl;
import com.sz7road.web.service.impl.GeneNewsPageServiceImpl;
import com.sz7road.web.service.impl.HomepageServiceImpl;
import com.sz7road.web.service.impl.MsgServiceImpl;
import com.sz7road.web.service.impl.NewServerReservationServiceImpl;
import com.sz7road.web.service.impl.NewsServiceImpl;
import com.sz7road.web.service.impl.OnlineUserServiceImpl;
import com.sz7road.web.service.impl.PermissionServiceImpl;
import com.sz7road.web.service.impl.PhotoServiceImpl;
import com.sz7road.web.service.impl.RoleServiceImpl;
import com.sz7road.web.service.impl.SignServiceImpl;
import com.sz7road.web.service.impl.SkillTemplateServiceImpl;
import com.sz7road.web.service.impl.TempHtmlServiceImpl;
import com.sz7road.web.service.impl.UserServiceImpl;
import com.sz7road.web.service.impl.VideoServiceImpl;

/**
 * Utility class to provide business object service
 * @author Jimmy Huang
 */
public class ServiceLocator {

	/**
	 * @return AppParameterService
	 */
	public static UserService getUserService() {
		return UserServiceImpl.getInstance();
	}
	public static PermissionService getPermissionService() {
		return PermissionServiceImpl.getInstance();
	}
	public static RoleService getRoleService() {
		return RoleServiceImpl.getInstance();
	}
	public static CaptchaService getCaptchaService() {
		return CaptchaServiceImpl.getInstance();
	}

	public static VideoService getVideoService(){
		return VideoServiceImpl.getInstance();
	}
	
	public static NewsService getNewsService(){
		return NewsServiceImpl.getInstance();
	}
	
	public static PhotoService getPhotoService(){
		return PhotoServiceImpl.getInstance();
	}

	public static MsgService getMsgService(){
		return MsgServiceImpl.getInstance();
	}
	
	public static DirtyService getDirtyService(){
		return DirtyServiceImpl.getInstance();
	}
	
//	public static GenerateHtmlService getGenerateHtmlService(){
//		return GenerateHtmlServiceImpl.getInstance();
//	}

	public static HomepageService getHomepageService(){
		return HomepageServiceImpl.getInstance();
	}
	
	public static OnlineUserService getOnlineUserService(){
		return OnlineUserServiceImpl.getInstance();
	}
	
	public static TempHtmlService getTempHtmlrService(){
		return TempHtmlServiceImpl.getInstance();
	}
	public static GameService getGameService()
	{
		return GameServiceImpl.getInstance();
	}
	public static GameDateSer getGameDateSer(){
		return GameDateSerImpl.getInstance();
	}
	public static DownloadCenterSer getDownloadSer(){
		return DownloadCenterSerImpl.getInstance();
	}
	public static GameServerService getGameServerService(){
		return GameServerServiceImpl.getInstance();
	}
	public static GameEnterInfoStatisticsService getGameEnterInfoStatisticsService(){
		return GameEnterInfoStatisticsServiceImpl.getInstance();
	}
	public static SkillTemplateService getSkillTemplateService(){
		return 	SkillTemplateServiceImpl.getInstance();
	}

	public static GeneHomepageService getGeneHomepageService(){
		return 	GeneHomepageServiceImpl.getInstance();
	}

	public static GeneNewsPageService getGeneNewsPageService(){
		return 	GeneNewsPageServiceImpl.getInstance();
	}

	public static AdStatisticsService getAdStatisticsService(){
		return AdStatisticsServiceImpl.getInstance();
	}

    /**
     * 创建时间： 2013-1-14 上午11:21:46
     * 创建人：xin.fang
     * 参数：
     * 返回值： SignService
     * 方法描述 :
    */
    public static SignService getSignService() {
        return SignServiceImpl.getInstance();
    }
    
    /**
     * 创建时间： 2013-2-5 上午10:32:17
     * 创建人：xin.fang
     * 参数： 
     * 返回值： AdInfoService
     * 方法描述 : 获取广告信息的service
    */
    public static AdInfoService getAdInfoService() {
        return AdInfoServiceImpl.getInstance();
    }

    public static ActivityIncService getActivityIncService() {
        return ActivityIncServiceImpl.getInstance();
    }
    
    public static GameDataTypeService getGameDataTypeService() {
        return GameDataTypeServiceImpl.getInstance();
    }
    public static NewServerReservationService getNewServerReservationService() {
        return NewServerReservationServiceImpl.getInstance();
    }
}
