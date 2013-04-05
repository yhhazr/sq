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
	PaginationResult<UserInfo> pageationResult = (PaginationResult<UserInfo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%">
	<tr>
		<td>
			<s:form name="createUser" namespace="/userManage" action="createUserForm"
				method="post" theme="simple">
				<s:submit key="admin.userManage.createUser" method="createUser"
					name="Submit" cssClass="button" theme="simple" />
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/userManage/queryUsers.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="userInfo">
				<%-- Table columns --%>
				<display:column property="userId"   titleKey="admin.user.userId"/>
				<display:column property="userName" titleKey="admin.user.userName" />
				<display:column property="roleName" titleKey="admin.user.role" />
				<display:column property="emailAddr" titleKey="admin.user.emailAddr" />
				<display:column  titleKey="admin.common.operation" href='<%=contextPath + "/userManage/editUserForm.action"%>'>
				<s:url id="editUrl" namespace="/userManage" action="editUserForm">
              		<s:param name="userId"><s:property value="#attr.userInfo.userId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteUser('<s:property value="#attr.userInfo.userId"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
<script type="text/javascript">
	highlightTableRows("userInfo");  
</script>
<script>
	function deleteUser(userId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ userId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/userManage/deleteUser.action?userId="+userId;
		else
			return;
	}
</script>	