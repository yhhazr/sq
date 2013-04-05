<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.gameDateMag.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	String contextPath = request.getContextPath();
	PaginationResult<GameDateEx> pageationResult = (PaginationResult<GameDateEx>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="7%">			
				<s:submit key="admin.common.deleteChecked" 
					name="Submit" cssClass="button" theme="simple" onclick="deleteCheckedNews1()"/>
			
		</td>
		<td align="left" width="93%">
			
				<s:submit key="admin.newsManage.createNews"
					name="Submit" cssClass="button" theme="simple" onclick="createNews()"/>
			
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/gameDate/queryGameList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="game">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.game.id'/>">
				</display:column> 
				<display:column property="artTitle"   titleKey="admin.news.newsName"/>
				<display:column property="stateName"   titleKey="admin.news.state"/>
				<display:column property="typeName"  sortable="true" titleKey="admin.news.type"/>
				<display:column property="orderNum"  sortable="true" titleKey="admin.gameDate.orderID"/>
				<display:column property="createDate"   titleKey="admin.news.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column  titleKey="admin.common.operation" >
				<s:url id="editUrl" namespace="/gameDate" action="editGameDate">
              		<s:param name="id"><s:property value="#attr.game.id"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteGame('<s:property value="#attr.game.id"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
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
	highlightTableRows("game");  

	function deleteGame(id){
		if(window.confirm("<s:text name="admin.news.delete.message"><s:param>"+ id + "</s:param></s:text>"))
			location.href="<%=contextPath%>/gameDate/deleteGameDateSubmit.action?id="+id;
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
			location.href="<%=contextPath%>/gameDate/deleteGameDateSubmit.action?id="+ids;
		else
			return;		
	}
	function createNews(){
		location.href="<%=contextPath%>/gameDate/createGameDate.action";
	}
</script>	