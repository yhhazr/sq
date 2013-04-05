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
	PaginationResult<GameDateEx> paginationResult = (PaginationResult<GameDateEx>)request.getAttribute("paginationResult");
	request.setAttribute("totalItems", new Integer(paginationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="7%">			
				<s:submit key="admin.common.deleteChecked" 
					name="Submit" cssClass="button" theme="simple" onclick="deleteCheckeDataType()"/>
			
		</td>
		<td align="left" width="93%">
			
				<s:submit key="admin.gameData.addType"
					name="Submit" cssClass="button" theme="simple" onclick="createDataType()"/>
			
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=paginationResult.getTotal()%>" url='<%=request.getContextPath() + "/gameDataType/queryGameDataTypeList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.paginationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="gameDateType">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.gameDateType.typeId'/>">
				</display:column> 
				<display:column property="typeId"   titleKey="admin.gameData.typeId"/>
				<display:column property="typeName"   titleKey="admin.gameData.typeName"/>
				<display:column property="shortName"   titleKey="admin.gameData.typeShort"/>
				<display:column property="parentName" sortable="true" titleKey="admin.gameData.parent" />	
				<display:column property="orderId" sortable="true"  titleKey="admin.gameDate.orderID" />	
				<display:column  titleKey="admin.common.operation" >
				<s:url id="editUrl" namespace="/gameDataType" action="editGameDataType">
              		<s:param name="id"><s:property value="#attr.gameDateType.typeId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteDataType('<s:property value="#attr.gameDateType.typeId"/>','<s:property value="#attr.gameDateType.typeName"/>' )"><s:text name="admin.common.delete"/></a>&nbsp;
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
	highlightTableRows("gameDateType");  

	function deleteDataType(id, name){
		if(window.confirm("<s:text name="admin.dataType.delete.message"><s:param>"+ name + "</s:param></s:text>"))
			location.href="<%=contextPath%>/gameDataType/deleteGameDataType.action?id="+id;
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
	function deleteCheckeDataType(){
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
		if(window.confirm("<s:text name="admin.dataType.deleteChecked.message"></s:text>"))
			location.href="<%=contextPath%>/gameDataType/deleteGameDataType.action?id="+ids;
		else
			return;		
	}
	function createDataType(){
		location.href="<%=contextPath%>/gameDataType/addGameDataType.action";
	}
</script>	