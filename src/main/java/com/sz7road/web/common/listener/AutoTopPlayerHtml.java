package com.sz7road.web.common.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import com.sz7road.web.common.util.AppConstants;
import com.sz7road.web.common.util.ServiceLocator;
import com.sz7road.web.model.gamemanage.PlatformGameServer;
import com.sz7road.web.model.gamemanage.RoleRankInfo;
import com.sz7road.web.service.GameServerService;
import com.sz7road.web.service.GeneHomepageService;
import com.sz7road.web.service.HomepageService;
import com.sz7road.web.service.impl.GameServerServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AutoTopPlayerHtml {

	private Map<String, Object> dataMap = new HashMap<String, Object>();
	private List<PlatformGameServer> serverForPlayerList = new ArrayList<PlatformGameServer>();
	private Configuration freemarker_cfg = null;
	File templateFile = null;
	private static final Logger logger = LogManager.getLogger(AutoTopPlayerHtml.class);

	public AutoTopPlayerHtml(ServletContext context) {
		begin(context);
	}

	public void begin(ServletContext context) {
		long daySpan = 24 * 60 * 60 * 1000;
		try {
			templateFile = new File(context.getRealPath("/") + "WEB-INF/template");

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '23:00:00'");
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
			if (System.currentTimeMillis() > startTime.getTime())
				startTime = new Date(startTime.getTime() + daySpan);
			Timer t = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					System.out.println("create topPlayers html");
					boolean isGene = true;
					GameServerService gameServiceService = GameServerServiceImpl.getInstance();
					GeneHomepageService geneHomepageService = ServiceLocator.getGeneHomepageService();
					List<RoleRankInfo> roleRankListAsc = null;

					String sGeneFilePath = "/player";
					try {
						roleRankListAsc = gameServiceService.getRoleRankInfoListAsc();
						if (roleRankListAsc != null) {
							for (RoleRankInfo roleRank : roleRankListAsc) {
								if (roleRank != null) {
									StringBuffer sFileName = new StringBuffer();
									//dally??
									if (roleRank.getUrl() == null || "".equals(roleRank.getUrl())) {
										continue;
									}
//									List<DallyInfo> diList = geneHomepageService.getDallyInfos(roleRank.getUrl());
//									if (diList.size() == Integer.parseInt(SystemConfig.getProperty("dallyInfos.show.size"))) {
//										dataMap.put("dallyInfos", diList);
										sFileName.append(roleRank.getId());
										sFileName.append(".html");
										isGene = geneHtmlFile("homepage/playerRightPage.ftl", dataMap, sGeneFilePath, sFileName.toString()) && isGene;
										dataMap.remove("dallyInfos");
									}
								}
							}
//						}
					} catch (Exception e) {
						isGene = false;
						logger.error("Generate generatePlayerRightPage Html Error:" + e.getMessage(), e);
					}
				}
			};
			t.scheduleAtFixedRate(task, startTime, daySpan);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String buildPlayerRankUrl(String id) {
		String url = AppConstants.PLAYER_RANKING_URL_FIRST + id + AppConstants.PLAYER_RANKING_URL_SECOND;

		return url;
	}

	private List<PlatformGameServer> buildServerListAsc() {
		GameServerService gameServiceService = GameServerServiceImpl.getInstance();
		List<PlatformGameServer> serverListAsc = new ArrayList<PlatformGameServer>();
		try {
			serverListAsc = gameServiceService.getAllGameServer();
			Collections.sort(serverListAsc, new Comparator<PlatformGameServer>() {
				public int compare(PlatformGameServer server0, PlatformGameServer server1) {

					return new Integer(server0.getServerNo()).compareTo(new Integer(server1.getServerNo()));
				}
			});
		} catch (Exception e) {
			logger.error("build game server list error.", e);
		}

		return serverListAsc;
	}

	public static boolean creatDirs(String aParentDir, String aSubDir) {
		File aFile = new File(aParentDir);
		if (!aFile.exists()) {
			aFile.mkdirs();
		}
		File aSubFile = new File(aParentDir + aSubDir);
		if (!aSubFile.exists()) {
			return aSubFile.mkdirs();
		} else {
			return true;
		}
	}

	@JSON(serialize = false)
	protected Configuration getFreeMarkerCFG() {
		if (null == freemarker_cfg) {
			// Initialize the FreeMarker configuration;
			// - Create a configuration instance
			//File templateFile = new File(ServletActionContext.getServletContext().getRealPath("/") + "WEB-INF/template");
			freemarker_cfg = new Configuration();
			try {
				freemarker_cfg.setDirectoryForTemplateLoading(templateFile);
				freemarker_cfg.setEncoding(Locale.CHINA, "UTF-8");
			} catch (IOException e) {
				logger.error("Get tempalte error", e);
			}
		}

		return freemarker_cfg;
	}

	@JSON(serialize = false)
	public boolean geneHtmlFile(String templateFileName, Map<String, Object> propMap, String htmlFilePath, String htmlFileName) {
		// @todo 从配置中取得要静态文件存放的根路径:需要改为自己的属性类调用
		String sRootDir = new String();
		if (!SystemConfig.getProperty("server.html.path").equals("")) {
			sRootDir = SystemConfig.getProperty("server.html.path");
		} else {
			sRootDir = ServletActionContext.getRequest().getRealPath("/webpage");
		}
		logger.info("sRootDir:" + sRootDir);
		try {
			if (propMap == null) {
				propMap = new HashMap<String, Object>();
			}

			Template t = getFreeMarkerCFG().getTemplate(templateFileName);

			// 如果根路径存在,则递归创建子目录
			creatDirs(sRootDir, htmlFilePath);
			File afile = new File(sRootDir + "/" + htmlFilePath + "/" + htmlFileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));
			t.process(propMap, out);
			out.flush();
			out.close();

		} catch (TemplateException e) {
			logger.error("Error while processing FreeMarker template " + templateFileName, e);
			return false;
		} catch (IOException e) {
			logger.error("Error while generate Static Html File " + htmlFileName, e);
			return false;
		}

		return true;
	}
}
