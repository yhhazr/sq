package com.sz7road.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sz7road.web.common.listener.SystemConfig;
import com.sz7road.web.common.util.DateUtil;
import com.sz7road.web.common.util.GeneHtml;
import com.sz7road.web.dao.HomepageItemDao;
import com.sz7road.web.dao.impl.HomepageItemDaoImpl;
import com.sz7road.web.model.gameDateMag.GameDateEx;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.homepagemanage.HomepageItem;
import com.sz7road.web.model.newsmanage.News;
import com.sz7road.web.model.onlineUser.PlayerGradeInfos;
import com.sz7road.web.model.vediomanage.VideoInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneActivityIncService;
import com.sz7road.web.service.GeneFunctionPageDataService;
import com.sz7road.web.service.GeneGameDataPageService;
import com.sz7road.web.service.GeneHomepageService;
import com.sz7road.web.service.GeneHtmlService;
import com.sz7road.web.service.GeneNewsPageService;
import com.sz7road.web.service.GenePhotoPageService;
import com.sz7road.web.service.GenePlayerRankService;
import com.sz7road.web.service.GeneServerListService;

public class GeneHtmlServiceImpl implements GeneHtmlService {

    private static final Logger logger = LogManager.getLogger(GeneHtmlServiceImpl.class);
    private Map<String, Object> dataMap = new HashMap<String, Object>();
    private static String serverBasePath = SystemConfig.getProperty("server.basePath");
    private static String serverHost = SystemConfig.getProperty("server.host");
    private static String imageHost = SystemConfig.getProperty("image.server.host");
    private static String imageHost1 = SystemConfig.getProperty("image.server.host1");
    private static String imageHost2 = SystemConfig.getProperty("image.server.host2");
    private static String imageHost3 = SystemConfig.getProperty("image.server.host3");
    private static String imageHost4 = SystemConfig.getProperty("image.server.host4");
    private static String imageHost5 = SystemConfig.getProperty("image.server.host5");
    private static String staticHost = SystemConfig.getProperty("static.server.host");
    private static String accountCenterPath = SystemConfig.getProperty("platform.connect.base.url");
    private static String payPath = SystemConfig.getProperty("pay.path");
    private static String bbsPath = SystemConfig.getProperty("bbs.path");
    private static String loginGamePath = SystemConfig.getProperty("login.game.path");
    private static String loginGameRealPath = SystemConfig.getProperty("login.game.real.path");
    private GameServerService gameServerService = GameServerServiceImpl.getInstance();
    private HomepageItemDao homepageItemDao = HomepageItemDaoImpl.getInstance();
    private static final int SERVER_SIZE = 5;

    public GeneHtmlServiceImpl() {
        //放入全局变量
        dataMap.put("staticHost", staticHost);
        dataMap.put("serverHost", serverHost);
        dataMap.put("homepagePath", serverHost);
        dataMap.put("payPath", payPath);
        dataMap.put("bbsPath", bbsPath);
        String[] imageHosts = { imageHost, imageHost1, imageHost2, imageHost3, imageHost4, imageHost5 };
        dataMap.put("imageHost", imageHosts);
        dataMap.put("serverBasePath", serverBasePath);
        dataMap.put("accountCenterPath", accountCenterPath);
        dataMap.put("serviceCenterPath", accountCenterPath);
        dataMap.put("loginGamePath", loginGamePath);
        dataMap.put("loginGameRealPath", loginGameRealPath);
        String version = createVersionNumber();
        dataMap.put("version", version);
        dataMap.put("newestNews", getNewestNewsData());
        dataMap.put("newServers", getGameServerList(SERVER_SIZE));
        dataMap.put("rankServers", getPlayerRankGameServerList());
    }

    //生成首页
    @Override
    public void geneIndexHtml(String ftlFile, String htmlFile) throws Exception {
        GeneHomepageService geneHomepageService = GeneHomepageServiceImpl.getInstance();
        try {
            Map<String, Object> homepageDateMap = geneHomepageService.getHomepageData();
            dataMap.putAll(homepageDateMap);
            GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
            logger.info("生成首页成功！");
        } catch (Exception e) {
            logger.error("生成首页错误：", e);
        }

    }

    //生成选服页
    @Override
    public void geneServerList(String ftlFile, String htmlFile) throws Exception {
        GeneServerListService geneServerListService = GeneServerListServiceImpl.getInstance();
        try {
            Map<String, Object> serverListMap = geneServerListService.getServerListData();
            dataMap.putAll(serverListMap);
            GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
            logger.info("生成选服页成功！");
        } catch (Exception e) {
            logger.error("生成选服页错误：", e);
        }
    }

    //生成新闻页
    @Override
    public void geneNewsList(String ftlFile, String htmlFile) throws Exception {
        GeneNewsPageService geneNewsPageService = new GeneNewsPageServiceImpl();
        //新闻列表
        logger.error("新闻列表生成开始 " + DateUtil.formatNow());
        while (geneNewsPageService.hasMoreNewsList()) {
            try {
                Map<String, Object> newsListPageDataMap = geneNewsPageService.getNewsListPageData();
                dataMap.putAll(newsListPageDataMap);
                int typeId = (Integer) dataMap.get("typeId");
                int page = (Integer) dataMap.get("currentPage");
                if (typeId == 0 && page == 1) {
                    GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
                }
                GeneHtml.geneHtmlFile("/news/newsListContent.ftl", dataMap, "/news/list_" + typeId + "_" + page
                        + ".html");
                logger.info("生成新闻列表成功！");
            } catch (Exception e) {
                logger.error("生成新闻列表错误：" + e, e);
            }
        }
        logger.error("新闻列表生成结束 " + DateUtil.formatNow());
        //新闻内容
        logger.error("新闻内页生成开始 " + DateUtil.formatNow());
        while (geneNewsPageService.hasMoreNews()) {
            try {
                Map<String, Object> newsPageDataMap = geneNewsPageService.getConcreteNewsPageData();
                dataMap.putAll(newsPageDataMap);
                News news = (News) dataMap.get("news");
                GeneHtml.geneHtmlFile("/news/newsContent.ftl", dataMap,
                        "/news/detail/" + news.getTypeId() + "_" + news.getNewsId() + ".html");
                logger.info("生成新闻内页成功！");
            } catch (Exception e) {
                logger.error("生成新闻内容错误：" + e);
            }
        }
        logger.error("新闻内页生成结束 " + DateUtil.formatNow());
    }

    //生成图片页
    @Override
    public void genePicList(String ftlFile, String htmlFile) throws Exception {
        GenePhotoPageService genePhotoPageService = GenePhotoPageServiceImpl.getInstance();
        //图片列表
        while (genePhotoPageService.hasMorePhotoList()) {
            try {
                Map<String, Object> photoListPageDataMap = genePhotoPageService.getPhotoListPageData();
                dataMap.putAll(photoListPageDataMap);
                int catId = (Integer) dataMap.get("catId");
                int page = (Integer) dataMap.get("currentPage");
                if (catId == 91 && page == 1) {
                    GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
                }
                GeneHtml.geneHtmlFile("/picture/picListContentCenter.ftl", dataMap, "/picture/" + catId + "_" + page
                        + ".html");
                logger.info("生成图片列表成功！");
            } catch (Exception e) {
                logger.error("生成图片列表错误：" + e);
            }
        }
        //视频列表
        while (genePhotoPageService.hasMoreVideoList()) {
            try {
                Map<String, Object> videoListMap = genePhotoPageService.getVideoListPageData();
                dataMap.putAll(videoListMap);
                int catId = (Integer) dataMap.get("catId");
                int page = (Integer) dataMap.get("currentPage");
                GeneHtml.geneHtmlFile("/picture/videoListContent.ftl", dataMap, "/picture/" + catId + "_" + page
                        + ".html");
                logger.info("生成视频列表成功！");
            } catch (Exception e) {
                logger.error("生成视频列表错误：" + e);
            }
        }
        //视频内页
        while (genePhotoPageService.hasMoreVideo()) {
            try {
                Map<String, Object> videoMap = genePhotoPageService.getConcreteVideoPageData();
                dataMap.putAll(videoMap);
                VideoInfo videoInfo = (VideoInfo) videoMap.get("video");
                GeneHtml.geneHtmlFile("/picture/videoContent.ftl", dataMap, "/picture/v_p" + videoInfo.getVideoId()
                        + ".html");
                logger.info("生成视频内页成功！");
            } catch (Exception e) {
                logger.error("生成视频内页错误：" + e);
            }
        }
    }

    //生成玩家排行
    @Override
    public void genePlayerRank(String ftlFile, String htmlFile) throws Exception {
        GenePlayerRankService genePlayerRankService = GenePlayerRankServiceImpl.getInstance();
        try {
            Map<String, Map<String, Object>> playerRankMap = genePlayerRankService.getPlayerRankData();
            Iterator it = playerRankMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry m = (Map.Entry) it.next();
                HashMap dd = (HashMap) m.getValue();
                Iterator it1 = dd.entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry m1 = (Map.Entry) it1.next();
                    dataMap.put((String) m1.getKey(), m1.getValue());
                }
                PlayerGradeInfos playerGradeInfos = (PlayerGradeInfos) dataMap.get("playerGradeRank");
                if (playerGradeInfos.getInfoList().size() > 0) {
                    GeneHtml.geneHtmlFile(ftlFile, dataMap, "/player/" + (String) m.getKey() + ".html");
                }
            }
            logger.info("生成玩家排行成功！");
        } catch (Exception e) {
            logger.error("生成玩家排行错误：" + e);
        }
    }

    //生成游戏资料
    @Override
    public void geneGameData(String ftlFile, String htmlFile) throws Exception {
        //游戏资料列表
        GeneGameDataPageService geneGameDataService = GeneGameDataPageServiceImpl.getInstance();
        try {
            Map<String, Object> gameDataListDataMap = geneGameDataService.getGameDataListPageData();
            dataMap.putAll(gameDataListDataMap);
            GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
            logger.info("生成游戏资料列表成功！");
        } catch (Exception e) {
            logger.error("生成游戏资料列表错误：" + e);
        }
        //游戏资料内页
        try {
            while (geneGameDataService.hasMoreGameData()) {
                Map<String, Object> gameDataDataMap = geneGameDataService.getConcreteGameDataPageData();
                dataMap.putAll(gameDataDataMap);
                GameDateEx gameData = (GameDateEx) gameDataDataMap.get("gameData");
                String id = gameData.getId();
                GeneHtml.geneHtmlFile("/gamedata/dataContent.ftl", dataMap, "/gamedata/" + id + ".html");
            }
            logger.info("生成游戏资料内页成功！");
        } catch (Exception e) {
            logger.error("生成游戏资料内页错误：" + e);
        }
    }

    //生成新手礼包页
    @Override
    public void geneNewbGift(String ftlFile, String htmlFile) throws Exception {
        GeneFunctionPageDataService geneFuncService = GeneFunctionPageDataServiceImpl.getInstance();
        try {
            Map<String, Object> newbGiftDataMap = geneFuncService.getNewbGiftPageData();
            dataMap.putAll(newbGiftDataMap);
            GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
            logger.info("生成新手礼包成功！");
        } catch (Exception e) {
            logger.error("生成新手礼包页错误：" + e);
        }
    }

    //生成活动列表页
    @Override
    public void geneActivityHtml(String ftlFile, String htmlFile) throws Exception {
        GeneActivityIncService service = new GeneActivityIncServiceImpl();
        int i = 1;
        while (service.hasMoreData()) {
            Map<String, Object> activityIncDataMap = service.getActivityIncData();
            try {
                dataMap.putAll(activityIncDataMap);
                if (i == 1) {
                    GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
                }
                GeneHtml.geneHtmlFile("/activity/activityContent.ftl", dataMap, "/activity/activity_list_" + i
                        + ".html");
                i++;
            } catch (Exception e) {
                logger.error("生成活动列表页错误：" + e);
            }
            logger.info("生成活动列表成功！");
        }
//                GeneFunctionPageDataService geneFuncService = GeneFunctionPageDataServiceImpl.getInstance();
//                try {
//                    Map<String, Object> newbGiftDataMap = geneFuncService.getNewbGiftPageData();
//                    dataMap.putAll(newbGiftDataMap);
//                    GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
//                    logger.info("生成活动列表成功！");
//                } catch (Exception e) {
//                    logger.error("生成活动列表页错误： " + e);
//                }
    }

    //生成登陆器页面
    @Override
    public void geneLoginEXE(String ftlFile, String htmlFile) throws Exception {
        GeneFunctionPageDataService geneFuncService = GeneFunctionPageDataServiceImpl.getInstance();
        try {
            Map<String, Object> newbGiftDataMap = geneFuncService.getLoginEXEPageData();
            dataMap.putAll(newbGiftDataMap);
            GeneHtml.geneHtmlFile("/loginexe/server.ftl", dataMap, "/logexe_server.html");
            GeneHtml.geneHtmlFile("/loginexe/signin.ftl", dataMap, "/logexe_signin.html");
            GeneHtml.geneHtmlFile("/loginexe/signup.ftl", dataMap, "/logexe_signup.html");
            GeneHtml.geneHtmlFile("/loginexe/news.ftl", dataMap, "/newLogexe_news.html");
            GeneHtml.geneHtmlFile("/loginexe/logexe_news.ftl", dataMap, "/logexe_news.html");
            GeneHtml.geneHtmlFile("/loginexe/loginExeLayout.ftl", dataMap, "/logexe_log.html");
            GeneHtml.geneHtmlFile("/loginexe/loginexeServer.ftl", dataMap, "/xf.html");
            GeneHtml.geneHtmlFile("/loginexe/registExeLayout.ftl", dataMap, "/logexe_reg.html");
            logger.info("生成登录器成功！");
        } catch (Exception e) {
            logger.error("生成登陆器页错误：" + e);
        }
    }

    //生成技能加点器页面
    @Override
    public void geneSkillPage(String ftlFile, String htmlFile) {
        try {
            Map<String, Object> skillPageDataMap = new HashMap<String, Object>();
            dataMap.putAll(skillPageDataMap);
            GeneHtml.geneHtmlFile(ftlFile, dataMap, htmlFile);
            logger.info("生成技能加点成功！");
        } catch (Exception e) {
            logger.error("生成技能加点页错误：" + e);
        }
    }

    //生成百度页
    @Override
    public void geneBaiduPage(String ftlFile, String htmlFile) {
        try {
            Map<String, Object> baiduPageDataMap = new HashMap<String, Object>();
            dataMap.putAll(baiduPageDataMap);
            GeneHtml.geneHtmlFile("/others/baiduADPageFlash1.ftl", dataMap, "/regist_independent_1.html");
            GeneHtml.geneHtmlFile("/others/baiduADPageFlash2.ftl", dataMap, "/regist_independent_2.html");
            GeneHtml.geneHtmlFile("/others/baiduADPageFlash3.ftl", dataMap, "/regist_independent_3.html");
            GeneHtml.geneHtmlFile("/others/baiduADPageFlash4.ftl", dataMap, "/regist_independent_4.html");
            GeneHtml.geneHtmlFile("/others/baiduADPageFlash5.ftl", dataMap, "/regist_independent_5.html");
            logger.info("生成百度页成功！");
        } catch (Exception e) {
            logger.error("生成百度页错误：" + e);
        }
    }

    //生成版本号
    private String createVersionNumber() {
        return String.valueOf(new Date().getTime());
    }

    //生成最新新闻图片数据
    private HomepageItem getNewestNewsData() {
        HomepageItem newestNews = null;
        try {
            List<HomepageItem> newestNewsList = homepageItemDao.selectItemByTypeIdAndPosition(
                    HomepageItem.TYPE_PICTURE, HomepageItem.POSITION_NEWEST_NEWS, 1);
            if (newestNewsList.size() > 0) {
                newestNews = newestNewsList.get(0);
            }
        } catch (Exception e) {
            logger.error("生成新闻页最新新闻图片数据异常。", e);
        }
        return newestNews;
    }

    //服务区列表（倒序）
    private List<PlatformGameServer> getGameServerList(int count) {
        List<PlatformGameServer> allServerListDesc = new ArrayList<PlatformGameServer>();
        List<PlatformGameServer> serverListDesc = new ArrayList<PlatformGameServer>();
        try {
            allServerListDesc = gameServerService.buildGameServerListDesc();
            if (allServerListDesc.size() >= count) {
                serverListDesc = allServerListDesc.subList(0, count);
            }
        } catch (Exception e) {
            logger.error("取得服务区列表错误：", e);
        }

        return serverListDesc;
    }

    // 取得玩家排行服务器列表
    private List<PlatformGameServer> getPlayerRankGameServerList() {
        List<PlatformGameServer> playerRankServerList = new ArrayList<PlatformGameServer>();
        try {
            playerRankServerList = gameServerService.getPlayerRankServerList();
        } catch (Exception e) {
            logger.error("取得玩家排行服务区错误：", e);
        }
        return playerRankServerList;
    }
}
