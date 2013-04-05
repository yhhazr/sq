package com.sz7road.web.service;

public interface GeneHtmlService {

	public void geneIndexHtml(String ftlFile, String htmlFile) throws Exception;
	
	public void geneServerList(String ftlFile, String htmlFile) throws Exception;
	
	public void geneNewsList(String ftlFile, String htmlFile) throws Exception;
	
	public void genePicList(String ftlFile, String htmlFile) throws Exception;
	
	public void genePlayerRank(String ftlFile, String htmlFile ) throws Exception;
	
	public void geneGameData(String ftlFile, String htmlFile) throws Exception;
	
	public void geneNewbGift(String ftlFile, String htmlFile) throws Exception;
	
	public void geneActivityHtml(String ftlFile,String htmlFile) throws Exception;
	
	public void geneLoginEXE(String ftlFile, String htmlFile) throws Exception;
	
	public void geneSkillPage(String ftlFile, String htmlFile);
	
	public void geneBaiduPage(String ftlFile, String htmlFile);
	
}
