<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.sz7road.web.common.util.AppConstants"%>
<%@page import="com.sz7road.web.model.usermanage.AdminUserRight"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<div style="float: left" id="my_menu" class="sdmenu">
<s:iterator var="permInfo" value="#session.userRight.menuPermInfoList">
	<div>
		<span><s:property value="#permInfo.permName"/></span>
		<s:iterator var="childPerm" value="#permInfo.childPermissions">
			<a href="<%=contextPath%><s:property value="#childPerm.permUrl"/>">
				<s:property value="#childPerm.permName"/>
			</a>
		</s:iterator>
	</div>
</s:iterator>
</div>

