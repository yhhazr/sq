<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.model.usermanage.UserInfo"%>
<%
	String contextPath = request.getContextPath();
%>
<table style="margin-top: 10px; width: 100%">
	<tr>
		<td>
			<s:form name="createPermissionForm" namespace="/permManage" action="createPermissionForm"
				method="post" theme="simple">
				<s:submit key="admin.permManage.createPermission" name="Submit" cssClass="button" theme="simple" />
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
			<display:table name="requestScope.permissionList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="permInfo">
				<%-- Table columns --%>
				<display:column property="permId" titleKey="admin.permission.permId"/>
				<display:column property="parentPermId" titleKey="admin.permission.parentId"/>
				<display:column property="permName" titleKey="admin.permission.permName" />
				<display:column property="permDesc" titleKey="admin.permission.permDesc" />
				<display:column property="permUrl" titleKey="admin.permission.permUrl"/>
				<display:column property="orderId" titleKey="admin.permission.orderId"/>
				<display:column property="menuFlag" titleKey="admin.permission.isMenu"/>
				<display:column property="restrictedFlag" titleKey="admin.common.enable"/>
				<display:column  titleKey="admin.common.operation">
				<s:url id="queryChildPermissionsUrl" namespace="/permManage" action="queryPermissions">
              		<s:param name="permId"><s:property value="#attr.permInfo.permId"/></s:param>
       			</s:url>
				<s:url id="editUrl" namespace="/permManage" action="editPermissionForm">
              		<s:param name="permId"><s:property value="#attr.permInfo.permId"/></s:param>
       			</s:url>
       			<s:a href="%{queryChildPermissionsUrl}"><s:text name="admin.permission.queryChildPermission"/></s:a>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a>
				<a href="#" onClick="deleteRole('<s:property value="#attr.permInfo.permId"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	highlightTableRows("permInfo"); 
</script>
<script>
	function deleteRole(permId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ permId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/permManage/deletePermission.action?permId="+permId;
		else
			return;
	}
</script>	