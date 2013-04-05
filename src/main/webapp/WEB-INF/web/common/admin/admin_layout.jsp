<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/tiles-jsp.tld" prefix="tiles"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head>
		<s:set name="title" ><tiles:getAsString name="titleKey" /></s:set>
		<title><s:text name="admin.title"/> - <s:text name="%{#title}"/></title>
		<meta http-equiv=Content-Type content=text/html;charset=UTF-8 />
		<link href="<%=contextPath%>/static/style/adminStyle.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/style/alternative.css" /> 
		<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/style/sdmenu.css" />
		<script type="text/javascript" src="<%=contextPath%>/static/js/sdmenu.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/static/js/jquery-1.7.1.js"></script>
		<script type="text/javascript" src="<%=contextPath%>/static/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
		<script type="text/javascript">
	//          
	var myMenu;
	window.onload = function() {
		myMenu = new SDMenu("my_menu");
		myMenu.init();
	};
	//
</script>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #EEF2FB;
}
-->
</style>
	</head>
	<body leftmargin="0" topmargin="0">
		<table width="100%" height="64" border="0" cellpadding="0"
			cellspacing="0" class="admin_topbg">
			<tr>
				<td width="41%" height="64">
					<img src="<%=contextPath%>/static/images/logo.gif" width="262" height="64">
				</td>
				<td width="59%" valign="top">
					<tiles:insertAttribute name="header" />
				</td>
			</tr>
		</table>
		<table width="10%" height="64" border="0" cellpadding="0"
			cellspacing="0" align="left">
			<tr>
				<td width="10%">
					<tiles:insertAttribute name="leftbar" />
				</td>
			</tr>
		</table>
		<table width="87%" height="64" border="0" cellpadding="0"
			cellspacing="0" align="left">
			<tr>
				<td width="90%" valign="top">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tbody>
							<tr>
								<td width="17" valign="top"
									background="<%=contextPath%>/static/images/mail_leftbg.gif">
									<img width="17" height="29"
										src="<%=contextPath%>/static/images/left-top-right.gif">
								</td>
								<td valign="top" background="<%=contextPath%>/static/images/content-bg.gif">
									<table width="100%" height="31" cellspacing="0" cellpadding="0"
										border="0" id="table2" class="left_topbg">
										<tbody>
											<tr>
												<td height="31">
													<div class="titlebt">
														后台管理
													</div>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
								<td width="16" valign="top"
									background="<%=contextPath%>/static/images/mail_rightbg.gif">
									<img width="16" height="29"
										src="<%=contextPath%>/static/images/nav-right-bg.gif">
								</td>
							</tr>
							<tr>
								<td valign="middle" background="<%=contextPath%>/static/images/mail_leftbg.gif">
									&nbsp;
								</td>
								<td valign="top" bgcolor="#F7F8F9" height="580">
										<tiles:insertAttribute name="content" />
								</td>
								<td background="<%=contextPath%>/static/images/mail_rightbg.gif">
									&nbsp;
								</td>
							</tr>

						</tbody>
					</table>

				</td>
			</tr>
		</table>
		<table width="100%" style="margin-top: 10px" height="20"
			cellspacing="0" cellpadding="0" border="0" class="login-buttom-bg">
			<tbody>
				<tr>
					<td align="center">
						<tiles:insertAttribute name="footer" />
					</td>
				</tr>
			</tbody>
		</table>

	</body>
</html>


