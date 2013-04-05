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
			<s:form name="createRole" namespace="/roleManage" action="createRoleForm"
				method="post" theme="simple">
				<s:submit key="admin.roleManage.createRole" name="Submit" cssClass="button" theme="simple" />
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
			<display:table name="requestScope.roleInfoList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="roleInfo">
				<%-- Table columns --%>
				<display:column property="roleId" titleKey="admin.role.roleId"/>
				<display:column property="roleName" titleKey="admin.role.roleName" />
				<display:column property="roleDesc" titleKey="admin.role.roleDesc" />
				<display:column property="createDate" titleKey="admin.role.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
				<display:column property="lupdate" titleKey="admin.role.lupdateDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column  titleKey="admin.common.operation">
				<s:url id="assignPermissionUrl" namespace="/roleManage" action="assignPermissionForm">
              		<s:param name="roleId"><s:property value="#attr.roleInfo.roleId"/></s:param>
       			</s:url>
				<s:url id="editUrl" namespace="/roleManage" action="editRoleForm">
              		<s:param name="roleId"><s:property value="#attr.roleInfo.roleId"/></s:param>
       			</s:url>
       			<s:a href="%{assignPermissionUrl}"><s:text name="admin.roleManage.assignPermission"/></s:a>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a>
				<a href="#" onClick="deleteRole('<s:property value="#attr.roleInfo.roleId"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
		</td>
	</tr>
</table>
<script type="text/javascript">
	highlightTableRows("roleInfo"); 
</script>
<script>
	function deleteRole(roleId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ roleId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/roleManage/deleteRole.action?roleId="+roleId;
		else
			return;
	}
</script>	