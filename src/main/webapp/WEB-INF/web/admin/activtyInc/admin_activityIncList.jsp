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
	PaginationResult<PhotoCat> paginationResult = (PaginationResult<PhotoCat>)request.getAttribute("paginationResult");
	request.setAttribute("totalItems", new Integer(paginationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="7%">			
				<s:submit key="admin.common.deleteChecked" 
					name="Submit" cssClass="button" theme="simple" onclick="deleteCheckedPhotoCat1()"/>
			
		</td>
		<td align="left" width="93%">
			
				<s:submit key="admin.activityInc.createActivityInc"
					name="Submit" cssClass="button" theme="simple" onclick="createItem()"/>
			
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=paginationResult.getTotal()%>" url='<%=request.getContextPath() + "/activityIncManage/queryAllActivityInc.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.paginationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="activityInc">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox'  onclick='checkAll()'>"> 
					<input name="checklist" type="checkbox" id="<s:property value='#attr.activityInc.activityId'/>">
				</display:column> 
				<display:column property="activityName"   titleKey="admin.activityInc.name"/>
				<display:column property="activityUrl"   titleKey="admin.activityInc.url"/>
				<display:column property="startDate"   titleKey="admin.activity.startDate"/>
				<display:column property="endDate"   titleKey="admin.activity.endDate"/>
				<display:column property="createDate"   titleKey="admin.activity.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>					
				<display:column  titleKey="admin.common.operation" href='<%=contextPath + "/activityIncManage/queryActivityInc.action"%>'>
				<s:url id="editUrl" namespace="/activityIncManage" action="queryActivityInc">
              		<s:param name="activityId"><s:property value="#attr.activityInc.activityId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteItem('<s:property value="#attr.activityInc.activityId"/>','<s:property value="#attr.activityInc.activityName"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
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
	highlightTableRows("activityInc");  

	function deleteItem(id,name){
		if(window.confirm("<s:text name="admin.item.delete.message"><s:param>"+ name + "</s:param></s:text>"))
			location.href="<%=contextPath%>/activityIncManage/deleteActivityInc.action?id="+id;
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
			location.href="<%=contextPath%>/activityIncManage/deleteActivityInc.action?id="+ids;
		else
			return;		
	}
	function createItem(){
		location.href="<%=contextPath%>/activityIncManage/createActivityInc.action";
	}
</script>	