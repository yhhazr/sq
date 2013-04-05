<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.photomanage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	String contextPath = request.getContextPath();
	PaginationResult<PhotoCat> pageationResult = (PaginationResult<PhotoCat>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="7%">			
				<s:submit key="admin.common.deleteChecked" 
					name="Submit" cssClass="button" theme="simple" onclick="deleteCheckedPhotoCat1()"/>
			
		</td>
		<td align="left" width="93%">
			
				<s:submit key="admin.photo.createPhotoCat"
					name="Submit" cssClass="button" theme="simple" onclick="createPhotoCat()"/>
			
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/photoManage/queryPhotoCatList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="photoCat">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' onclick='checkAll()'>"> 
					<input name='checklist' type="checkbox" id="<s:property value='#attr.photoCat.catId'/>">
				</display:column> 
				<display:column property="catName"   titleKey="admin.photo.photoCatName"/>
				<display:column property="catDesc"   titleKey="admin.photo.photoCatDesc"/>
				<display:column property="imageUrl"   titleKey="admin.photo.photoCatImage"/>
				<display:column property="createDate"   titleKey="admin.news.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column titleKey="admin.common.recommend">
				<s:if test="%{#attr.photoCat.homepageShow==false}">
					<s:text name="admin.common.no"/>
				</s:if> 
				<s:else>
					<s:text name="admin.common.yes"/>
				</s:else>
				</display:column>
				<display:column titleKey="admin.photo.photoUpload">
					<s:url id="uploadPhoto" namespace="/photoManage" action="uploadPhoto">
              			<s:param name="catId"><s:property value="#attr.photoCat.catId"/></s:param>
       				</s:url>
					<s:a href="%{uploadPhoto}"><s:text name="admin.photo.photoUpload"/></s:a>
				</display:column>
				<display:column  titleKey="admin.common.operation" href='<%=contextPath + "/newsManage/editNews.action"%>'>
				<s:url id="editUrl" namespace="/photoManage" action="editPhotoCat">
              		<s:param name="catId"><s:property value="#attr.photoCat.catId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deletePhotoCat('<s:property value="#attr.photoCat.catId"/>','<s:property value="#attr.photoCat.catName"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">
	highlightTableRows("photoCat");  

	function deletePhotoCat(catId,catName){
		if(window.confirm("<s:text name="admin.photoCat.delete.message"><s:param>"+ catName + "</s:param></s:text>"))
			location.href="<%=contextPath%>/photoManage/deletePhotoCat.action?catId="+catId;
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
	function deleteCheckedPhotoCat1(){
		var checkBoxs = document.getElementsByName("checklist");
		var ids = "";
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true){
				ids = ids+checkBoxs[i].id+",";
			}
		}
		if(ids == ""){
			return;
		}
		if(window.confirm("<s:text name="admin.common.deleteChecked.message"></s:text>"))
			location.href="<%=contextPath%>/photoManage/deletePhotoCat.action?catId="+ids;
		else
			return;		
	}
	function createPhotoCat(){
		location.href="<%=contextPath%>/photoManage/createPhotoCat.action";
	}
</script>	