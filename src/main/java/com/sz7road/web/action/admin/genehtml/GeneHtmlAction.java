package com.sz7road.web.action.admin.genehtml;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.service.CDNManageService;
import com.sz7road.web.service.GeneHtmlService;
import com.sz7road.web.service.impl.CDNManageServiceImpl;
import com.sz7road.web.service.impl.GeneHtmlServiceImpl;

public class GeneHtmlAction extends ActionSupport {

	private String result;

	// 生成全站HTML
	public String geneAllHtml() {
		result = "false";
		GeneHtmlService geneHtmlService = new GeneHtmlServiceImpl();
		try {
			// 首页
			geneHtmlService.geneIndexHtml("/index.ftl", "/index.html");
			// 新闻列表页
			geneHtmlService.geneNewsList("/news/newsList.ftl",
					"/news/newsList.html");
			// 选服页
			geneHtmlService.geneServerList("/serverList.ftl",
					"/serverList.html");
			// 图片列表页
			geneHtmlService.genePicList("/picture/picList.ftl",
					"/picture/picList.html");
			// 新手礼包
			geneHtmlService.geneNewbGift("/others/newbGift.ftl",
					"/others/newbGift.html");
			// 游戏资料
			geneHtmlService.geneGameData("/gamedata/dataList.ftl",
					"/gamedata/dataList.html");
			// 活动页
			geneHtmlService.geneActivityHtml("/activity/activity.ftl",
					"/activity/activity.html");
			// 登录器
			geneHtmlService.geneLoginEXE("", "");
			// 技能加点
			geneHtmlService.geneSkillPage("/others/skill.html", "/skill.html");
			// 百度页
			// geneHtmlService.geneBaiduPage("", "");

			result = "true";
		} catch (Exception e) {
			result = "false";
		}
		// 如果生成成功则将新生成的页面进行CDN推送
		if (result.equals("true")) {
			// 开启线程进行CDN推送
			new Thread(new Runnable() {

				@Override
				public void run() {
					CDNManageService cdn = new CDNManageServiceImpl();
					cdn.cdnPost();
				}
			}).start();
		}
		return SUCCESS;
	}

	// 生成玩家排行
	public String genePlayerRank() {
		result = "false";
		GeneHtmlService geneHtmlService = new GeneHtmlServiceImpl();
		try {
			geneHtmlService.genePlayerRank("/common/rank.ftl", "");
			result = "true";
		} catch (Exception e) {
			result = "false";
		}
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
