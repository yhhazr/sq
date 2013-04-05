<%@page import="com.sz7road.web.model.adinfo.RegInfo"%>
<%@page import="com.sz7road.web.model.adinfo.AdInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.newsmanage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	String contextPath = request.getContextPath();
	PaginationResult<RegInfo> paginationResult = (PaginationResult<RegInfo>)request.getAttribute("paginationResult");
	request.setAttribute("totalItems", new Integer(paginationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.adinfo.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td colspan="2">
		<pg:pager items="<%=paginationResult.getTotal()%>" url='<%=request.getContextPath() + "/adInfoManage/queryAllRegInfo.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.adinfo.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.adinfo.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.paginationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="reginfo">
				<%-- Table columns --%>
				<display:column property="id"   titleKey="admin.id"/>
				<display:column property="useId"   titleKey="admin.ad.userId"/>
				<display:column property="userName"   titleKey="admin.ad.userName"/>
				<display:column property="adId"   titleKey="admin.ad.id"/>
				<display:column property="regTime"   titleKey="admin.ad.regTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
	highlightTableRows("news");  

	function deleteNews(newsId,artTitle){
		if(window.confirm("<s:text name="admin.news.delete.message"><s:param>"+ artTitle + "</s:param></s:text>"))
			location.href="<%=contextPath%>/newsManage/deleteNews.action?newsId="+newsId;
		else
			return;
	}
	function checkAll(){
		var checkBoxs = document.getElementsByTagName("input");
		var j = 0;
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true){
				j++;
			}
		}
		if(j == checkBoxs.length - 1){
			for(var k = 0 ; k < checkBoxs.length; k++){
			checkBoxs[k].checked = false;
			}
		}else{
			for(var i = 0; i < checkBoxs.length; i++){
				checkBoxs[i].checked = true;
				}
		}
	}
	function deleteCheckedNews1(){
		var checkBoxs = document.getElementsByTagName("input");
		var ids = "";
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true && checkBoxs[i].name != "Submit" && checkBoxs[i].name != "checklist"){
				ids = ids+checkBoxs[i].id+",";
			}
		}
		if(ids == ""){
			return;
		}
		if(window.confirm("<s:text name="admin.common.deleteChecked.message"></s:text>"))
			location.href="<%=contextPath%>/newsManage/deleteNews.action?newsId="+ids;
		else
			return;		
	}
	function createNews(){
		location.href="<%=contextPath%>/newsManage/createNews.action";
	}
</script>	