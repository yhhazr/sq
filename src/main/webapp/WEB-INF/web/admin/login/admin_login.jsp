<%@page import="com.sz7road.web.common.util.AppConstants"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%
	String contextPath = request.getContextPath();
%>
<html>
	<head>
		<link  rel="stylesheet" type="text/css" href="<%=contextPath%>/static/style/adminStyle.css">
		<title>网站管理员登陆</title>
		<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #1D3647;
}
-->
</style>
	<body>
			<table width="100%" height="166" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="42" valign="top">
				<table width="100%" height="42" border="0" cellpadding="0"
					cellspacing="0" class="login_top_bg">
					<tr>
						<td width="1%" height="21">
							&nbsp;
						</td>
						<td height="42">
							&nbsp;
						</td>
						<td width="17%">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<table width="100%" height="532" border="0" cellpadding="0"
					cellspacing="0" class="login_bg">
					<tr>
						<td width="49%" align="right">
							<table width="91%" height="532" border="0" cellpadding="0"
								cellspacing="0" class="login_bg2">
								<tr>
									<td height="138" valign="top">
										<table width="89%" height="427" border="0" cellpadding="0"
											cellspacing="0">
											<tr>
												<td height="149">
													&nbsp;
												</td>
											</tr>
											<tr>
												<td height="200" align="right" valign="top">
													<img src="<%=contextPath%>/static/images/logo.png" width="279" height="68">
												</td>
											</tr>

										</table>
									</td>
								</tr>

							</table>
						</td>
						<td width="1%">
							&nbsp;
						</td>
						
						<td width="50%" valign="bottom">
						
							<table width="100%" height="59" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td width="4%">
										&nbsp;
									</td>
									<td width="96%" height="38">
										<span class="login_txt_bt">
											<s:property value="#session.visitError"/>
											<s:actionerror cssStyle="color:red"/>
										</span>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
									<td height="21">
										<table cellSpacing="0" cellPadding="0" width="100%" border="0"
											id="table211" height="328">
											<tr>
												<td height="164" colspan="2" align="middle">
													<s:form name="adminLogin" action="adminLoginSubmit" method="post" theme="simple">
													
														<table cellSpacing="0" cellPadding="0" width="100%"
															border="0" height="143" id="table212">
															
															<tr>
																<td width="13%" height="38" class="top_hui_text">
																	<span class="login_txt"><s:text name="admin.user.userName"/>&nbsp;&nbsp; </span>
																</td>
																<td height="38" colspan="2" class="top_hui_text">
																	<s:textfield name="userName" cssClass="editbox4" size="20"/>
																	<span class="login_txt_bt">
																		<s:fielderror cssStyle="color:red">
																			<s:param>userName</s:param>
																		</s:fielderror>
																	</span>
																</td>
															</tr>
															<tr>
																<td width="13%" height="35" class="top_hui_text">
																	<span class="login_txt"><s:text name="admin.user.passWord"/>&nbsp;&nbsp; </span>
																</td>
																<td height="35" colspan="2" class="top_hui_text">
																		<s:password name="passWord" cssClass="editbox4" size="20"/>
																	<span class="login_txt_bt">
																		<s:fielderror cssStyle="color:red">
																			<s:param>passWord</s:param>
																		</s:fielderror>
																	</span>
																</td>
															</tr>
																									
															<tr>
																<td width="13%" height="35">
																	<span class="login_txt"><s:text name="admin.user.verifyCode"/></span>
																</td>
																<td height="35" colspan="2" class="top_hui_text">
																		<s:textfield name="verifyCode" cssClass="wenbenkuang" size="10" maxlength="4" theme="simple"/>
																		<img style="vertical-align:middle;cursor:pointer;" src="captcha.do" width="90px" height="25px" title="Change verify image" onclick="javascript:this.src='captcha.do?'+(new Date().getTime());">
																		<span class="login_txt_bt">
																			<s:fielderror cssStyle="color:red">
																				<s:param>verifyCode</s:param>
																			</s:fielderror>	
																		</span>															
																</td>
															</tr>
															<tr>
																<td height="35">
																	&nbsp;
																</td>
																<td width="20%" height="35">
																		<s:submit value="登陆" name="Submit" cssClass="button" theme="simple"/>
																</td>
																<td width="67%" class="top_hui_text">
																		<s:reset value="取消" name="reset" cssClass="button" theme="simple"/>
																</td>
															</tr>
														</table>
														<br>
													</s:form>
												</td>
											</tr>
											<tr>
												<td width="433" height="164" align="right" valign="bottom">
													<img src="<%=contextPath%>/static/images/login-wel.gif" width="242" height="138">
												</td>
												<td width="57" align="right" valign="bottom">
													&nbsp;
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="20">
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					class="login-buttom-bg">
					<tr>
						<td align="center">
							<span class="login-buttom-txt">Copyright &copy; 2011-2012
								sq.7road.com</span>
							<span class="login-buttom-txt">Powered by 技术支持部-网站开发组</span>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</body>
</html>

