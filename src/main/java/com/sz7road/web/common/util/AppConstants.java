package com.sz7road.web.common.util;

public class AppConstants {
	
	public static final String ADMINUSER_USER_RIGHT = "userRight";

	public static final String SERVLET_CONTEXT_ATTRIBUTE_DB_RESOURCES_PROPS_FILENAME = "WEB-INF/classes/DBConfig.properties";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_APPCONFIG_RESOURCES_PROPS_FILENAME = "WEB-INF/classes/ApplicationConfig.properties";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_GAMESERVER_STATUS_RESOURCES_PROPS_FILENAME = "WEB-INF/classes/gameServerStatus.properties";
	//public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS = "resource_property";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS = "resource_property";
	
	public static final String SERVLET_CONTEXT_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_URL = "jdbc.url";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_USERNAME = "jdbc.username";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_PASSWORD = "jdbc.password";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_MAXCONNECTIONCOUNT = "jdbc.maxConnectionCount";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_MINCONNECTIONCOUNT = "jdbc.minConnectionCount";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_ISLASYMODE = "jdbc.isLasyMode";
	public static final String SERVLET_CONTEXT_ATTRIBUTE_RESOURCES_PROPS_SHOWSQL = "jdbc.showSql";
	
	
	public static final String FUNCTION_CODE_ADMINLOGIN = "ADMIN_LOGIN";
	
	public static final String ONLINE_NEWS_TYPE_NEWS = "NEWS";
	public static final String ONLINE_NEWS_TYPE_BULLETIN = "BULLETIN";
	public static final String ONLINE_NEWS_TYPE_MEDIA = "MEDIA";
	public static final String ONLINE_NEWS_TYPE_ACTIVITY = "ACTIVITY";

	public static final String DIRTY_CODE_TXT = "WEB-INF/classes/dirtyCode.txt";
	//新闻的中类型ID
	public static final int ONLINE_NEWS_TYPEID_NEWS=1;
	public static final int ONLINE_NEWS_TYPEID_ACIVITY=2;
	public static final int ONLINE_NEWS_TYPEID_MEDIA=3;
	public static final int ONLINE_NEWS_TYPEID_PRIMER=4;
	public static final int ONLINE_NEWS_TYPEID_FEATURE=5;
	public static final int ONLINE_NEWS_TYPEID_GROW=6;
	public static final int ONLINE_NEWS_TYPEID_EQUIPMENT=7;
	public static final int ONLINE_NEWS_TYPEID_ARTICLE=8;
	public static final int ONLINE_NEWS_TYPEID_METHOD=9;
	public static final int ONLINE_NEWS_TYPEID_HOTHIT=10;
	//图片、文字、、、类型ID
	public static final int ONLINE_HOMEPAGE_TYPEID_FLASH=1;
	public static final int ONLINE_HOMEPAGE_TYPEID_VIDEO=2;
	public static final int ONLINE_HOMEPAGE_TYPEID_WORD=3;
	public static final int ONLINE_HOMEPAGE_TYPEID_PIC=4;
	public static final int ONLINE_HOMEPAGE_TYPEID_WORDADNPIC=5;
	
	
	//通行证平台接口url
	public static final String PLATFORM_ACCESS_BASE_URL = "platform.connect.base.url";
	public static final String PLATFORM_ACCESS_LOGINAUTH = "platform.connect.path.LoginAuth";
	public static final String PLATFORM_ACCESS_SIGNUP = "platform.connect.path.SignUp";
	public static final String PLATFORM_ACCESS_CHECKACCOUNT = "platform.connect.path.CheckAccount";
	
	//游戏平台接口url
	public static final String PLATFORM_MANAGER_BASE_URL = "platform.mamager.base.url";
	public static final String PLATFORM_SERVER_BACKGROUND = "platform.connect.gameServer.background";
	public static final String PLATFORM_SERVER_BACKGROUND_STOPCHECKEDSERVER = "platform.connect.gameServer.stopCheckedServer";
	public static final String PLATFORM_SERVER_ONLINE_ALLSERVER = "platform.connect.gameServer.online.listGameServerTable";
	public static final String PLATFORM_SERVER_ONLINE_LATEESTSERVER = "platform.connect.gameServer.online.latestGameServer";
	
	//平台游戏服务区管理
	public static final int GAME_ID = 1;
	public static final String TOTAL = "total";
	public static final String STOP_STATUS = "-2";
	public static final String START_STATUS = "0";
	//平台游戏服务区连接参数名
	public static final String REQ_JSON = "reqJson";
	public static final String ACTION = "action";
	public static final String LIST = "list";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String UPDATE_STATUS = "us";
	public static final String GAME_ID_NAME = "_g";
	public static final String PAGE_INDEX = "pageIndex";
	public static final String PAGE_SIZE = "pageSize";
	public static final String GAME_LIST = "list";
	public static final String STATUS = "s";
	public static final String PAGE_ORDER = "order";
	public static final String PAGE_SORT = "sort";
	public static final String PAGE_QUERY = "query";
	public static final String All_SERVER = "all";
	public static final String STOP_CHECKED_SERVER = "updateMaintain";
	public static final String MAINTAIN_JSON = "maintainJson";
	public static final String SERVER_ID_ARRAY = "serverIdArray";
	
	
	public static final String LOGIN_TOOLS_NAME = "7road神曲极速登录器.exe";
	//官网登陆注册入口
	public static final String ENTER_POSITION1="homePage_regist";
	public static final String ENTER_POSITION2="homePage_login";
	public static final String ENTER_POSITION3="homePage_novice";
	public static final String ENTER_POSITION4="homePage_pay";
	public static final String ENTER_POSITION5="homePage_flash_startGame";
	public static final String ENTER_POSITION6="homePage_flash_regist";
	public static final String ENTER_POSITION7="homePage_flash_pay";
	public static final String ENTER_POSITION8="serverlist_login";
	public static final String ENTER_POSITION9="serverlist_regist";
	
	public static final String ENTER_POSITION10="homePage_flashCarousel1";
	public static final String ENTER_POSITION11="homePage_flashCarousel2";
	public static final String ENTER_POSITION12="homePage_flashCarousel3";
	public static final String ENTER_POSITION13="homePage_flashCarousel4";
	public static final String ENTER_POSITION14="homePage_flashCarousel5";
	public static final String ENTER_POSITION15="homePage_flashCarousel6";
	
	public static final String ENTER_POSITION16="homePage_gameData";
	public static final String ENTER_POSITION17="homePage_agency";
	public static final String ENTER_POSITION18="homePage_download";
	public static final String ENTER_POSITION19="homePage_toolbox";
	
	public static final String ENTER_POSITION20="homePage_rightAD1";
	public static final String ENTER_POSITION21="homePage_rightAD2";
	public static final String ENTER_POSITION22="homePage_rightAD3";
	public static final String ENTER_POSITION23="homePage_regist_fromBaidu";
	public static final String ENTER_POSITION24="homePage_closeRegist";
	
	public static final String ENTER_POSITION25="homepage_bookmarkit";
	public static final String ENTER_POSITION26="homepage_homepage";
	public static final String ENTER_POSITION27="homepage_newPlayerGuide";
	public static final String ENTER_POSITION28="homepage_top_gameData";
	public static final String ENTER_POSITION29="homepage_bbs";
	public static final String ENTER_POSITION30="homepage_hotNews";
	public static final String ENTER_POSITION31="homepage_newsInfo";
	public static final String ENTER_POSITION32="homepage_serverList";
	public static final String ENTER_POSITION33="homepage_serverListArea";
	public static final String ENTER_POSITION34="homepage_newPic";
	public static final String ENTER_POSITION35="homepage_newsListArea";
	public static final String ENTER_POSITION36="homepage_navi1_news";
	public static final String ENTER_POSITION37="homepage_navi1_annoc";
	public static final String ENTER_POSITION38="homepage_navi1_activity";
	public static final String ENTER_POSITION39="homepage_navi1_media";
	public static final String ENTER_POSITION40="homepage_navi1_more";
	public static final String ENTER_POSITION41="homepage_navi1_content_news";
	public static final String ENTER_POSITION42="homepage_navi1_content_annoc";
	public static final String ENTER_POSITION43="homepage_navi1_content_activity";
	public static final String ENTER_POSITION44="homepage_navi1_content_media";
	public static final String ENTER_POSITION45="homepage_topPlayers";
	public static final String ENTER_POSITION46="homepage_navi2_screenshot";
	public static final String ENTER_POSITION47="homepage_navi2_wallpaper";
	public static final String ENTER_POSITION48="homepage_navi2_painting";
	public static final String ENTER_POSITION49="homepage_navi2_cartoon";
	public static final String ENTER_POSITION50="homepage_navi2_more";
	public static final String ENTER_POSITION51="homepage_navi2_content_screenshot";
	public static final String ENTER_POSITION52="homepage_navi2_content_wallpaper";
	public static final String ENTER_POSITION53="homepage_navi2_content_painting";
	public static final String ENTER_POSITION54="homepage_navi2_content_cartoon";
	public static final String ENTER_POSITION55="homepage_qr_floatSmall";
	public static final String ENTER_POSITION56="homepage_qr_quickRegistClick";
	public static final String ENTER_POSITION57="homepage_qr_sina";
	public static final String ENTER_POSITION58="homepage_qr_qq";
	public static final String ENTER_POSITION59="homepage_qr_kaixin";
	public static final String ENTER_POSITION60="homepage_qr_renren";
	public static final String ENTER_POSITION61="homepage_qr_quickRegistAble";
	public static final String ENTER_POSITION62="homepage_qr_07073_1";
	public static final String ENTER_POSITION63="homepage_qr_07073_2";
	public static final String ENTER_POSITION64="homepage_navi1_bluePosts";
	public static final String ENTER_POSITION65="homepage_navi1_content_bluePosts";
	
	//开区资源
	public static final int HOMEPAGE_FLASH_TYPE_ID = 6;
	public static final int HOMEPAGE_IMAGE_TYPE_ID = 7;
	public static final String HOMEPAGE_FLASH_PRE_NAME = "";
	public static final String HOMEPAGE_IMAGE_PRE_NAME = "lunbo_";
	public static final String HOMEPAGE_ORDER_PARAM = "content";
	
	//玩家排行url
	public static final String PLAYER_RANKING_URL_FIRST = "http://s";
	public static final String PLAYER_RANKING_URL_SECOND = ".shenquol.com/xml/total_unzip.xml";
	
	//===========CDN相关设置开始===========//
	//CDN推送相关参数
	public static final String CDN_USERNAME = "7road";
	public static final String CDN_PASSWORD = "T7dd+/)itD";
	//CDN推送刷新的url地址
	public static final String[] CDN_URLS = new String[]{"http://sq.7road.com/index.html","http://sq.7road.com/serverList.html"};
	
	//CDN推送刷新的目录
	public static final String[] CDN_DIRS = new String[]{"http://sq.7road.com/gameData/","http://sq.7road.com/news/"};
	
	//CDN推送结果返回的地址
	public static final String CDN_CALLBACK_URL = "";
	public static final String[] CDN_CALLBCXK_EMAIL = new String[]{""};
	public static final boolean CDN_ACPTNOTICE = false;
	
	//CDN推送刷新地址
	public static final String CDN_POST_URL = "https://r.chinacache.com/content/refresh";
	
	//CDN返回结果查询地址
	public static final String CDN_SELECT_URL = "https://r.chinacache.com/content/refresh/{r_id}";
	//===========CDN相关设置结束===========//

	//活动简介页默认pagesize
    public static final int ACTIVITY_INC_PAGE_SIZE = 5;

    //用户新服预约两次请求间的时间间隔
    public static final long USER_NEW_SERVER_RESERVATION_INTERVAL = 1 * 60 * 1000;
    
    //新服预约验证码过期时间
    public static final long USER_NEW_SERVER_RESERVATION_EXPIRED = 10 * 60 * 1000;
    //新服预约每天前？名可以送两个激活码的
    public static final int USER_NEW_SERVER_REVERSION_EVERDAY_COUNT = 300;

    //新服预约同一用户最多请求次数
    public static final int USER_NEW_SERVER_REVERSION_REQUEST_TIMES = 10;

    public static final String USER_NEW_SERVER_RESERVATION_PRE_MSG = "尊敬的第七大道神曲用户，您预约300服手机验证码为：";

    public static final String USER_NEW_SERVER_RESERVATION_LAST_MSG = "别忘了4月4号上线领奖哟";
}
