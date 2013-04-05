<%@page import="com.sz7road.web.model.homepagemanage.HomepageItem"%>
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
	PaginationResult<HomepageItem> pageationResult = (PaginationResult<HomepageItem>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="7%">			
				<s:submit key="admin.common.deleteChecked" 
					name="Submit" cssClass="button" theme="simple" onclick="deleteCheckedFlash()"/>
			
		</td>
		<td align="left" width="93%">
				<s:submit key="admin.homepage.createFlash"
					name="Submit" cssClass="button" theme="simple" onclick="createFlash()"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/indexFlash/queryIndexFlashList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="flashItems">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.flashItems.id'/>">
				</display:column> 
				<display:column property="content"   titleKey="admin.serverResource.uploadFileName"/>
				<display:column property="url"   titleKey="admin.serverResource.url"/>
				<display:column property="createDate"   titleKey="admin.news.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column  titleKey="admin.common.operation" href='<%=contextPath + "/indexFlash/editFlash.action"%>'>
				<s:url id="editUrl" namespace="/indexFlash" action="editFlash">
              		<s:param name="id"><s:property value="#attr.flashItems.id"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteFlash('<s:property value="#attr.flashItems.id"/>','<s:property value="#attr.flashItems.content"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
</body>
</html>
<script src="<%=contextPath %>/static/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=contextPath %>/static/js/waiting.js"></script>
<script type="text/javascript">
	highlightTableRows("flashItems");  

	function deleteFlash(flashId,content){
		if(window.confirm("<s:text name="admin.serverResource.delete.message"><s:param>"+ content + "</s:param></s:text>"))
			location.href="<%=contextPath%>/indexFlash/removeFlash.action?id="+flashId;
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
	function deleteCheckedFlash(){
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
			location.href="<%=contextPath%>/indexFlash/removeFlash.action?id="+ids;
		else
			return;		
	}
	function createFlash(){
		location.href="<%=contextPath%>/indexFlash/createFlash.action";
	}

</script>	