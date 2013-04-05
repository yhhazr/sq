package com.sz7road.web.action.admin.genehtml;




import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sz7road.web.common.util.GeneHtml;
import com.sz7road.web.service.GeneHtmlService;
import com.sz7road.web.service.impl.GeneHtmlServiceImpl;

public class ExecuteFtlAction extends ActionSupport {

	private String ftlFile;
	private String htmlFile;
	
	@Override
	public String execute() throws Exception {
		String url = ServletActionContext.getRequest().getRequestURI();
		ftlFile = ftlMapping(url);
		htmlFile = ftlFile.substring(0, ftlFile.lastIndexOf(".ftl")) + ".html";
		String sign = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		GeneHtmlService geneHtmlService = new GeneHtmlServiceImpl();
		if(sign.equals("index")){
			geneHtmlService.geneIndexHtml(ftlFile, htmlFile);
		}
		if(sign.equals("serverList")){
			geneHtmlService.geneServerList(ftlFile, htmlFile);
		}
		if(sign.equals("newsList")){
			geneHtmlService.geneNewsList(ftlFile, htmlFile);
		}
		if(sign.equals("rank")){
			geneHtmlService.genePlayerRank(ftlFile, htmlFile);
		}
		if(sign.equals("picList")){
			geneHtmlService.genePicList(ftlFile, htmlFile);
		}
		if(sign.equals("dataList")){
			geneHtmlService.geneGameData(ftlFile, htmlFile);
		}
		if(sign.equals("newbGift")){
			geneHtmlService.geneNewbGift(ftlFile, htmlFile);
		}
		if(sign.equals("activity")){
			geneHtmlService.geneActivityHtml(ftlFile, htmlFile);
		}
		if(sign.equals("loginexeServer")){
			htmlFile = "/xf.html";
			geneHtmlService.geneLoginEXE("", "");
		}
		if(sign.equals("loginExeLayout")){
			htmlFile="/logexe_log.html";
			geneHtmlService.geneLoginEXE("", "");
		}
		if(sign.equals("logexe_news")){
			htmlFile="/logexe_news.html";
			geneHtmlService.geneLoginEXE("", "");
		}
		if(sign.equals("registExeLayout")){
			htmlFile="/logexe_reg.html";
			geneHtmlService.geneLoginEXE("", "");
		}
		return SUCCESS;
	}
	
	public String ftlMapping(String url){
		String result = "";
		String[] par = url.split("/");
		for (int i = 2; i < par.length; i++) {
			result = result + "/" + par[i];
		}
		return result;
	}
	
	public String getHtmlFile() {
		return htmlFile;
	}

	
}
