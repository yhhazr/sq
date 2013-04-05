<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
 %>
<script type="text/javascript" src="<%=path %>/static/js/waiting.js"></script>
<body>
	<s:form name="createHtmlSubmit" namespace="/homepageManage"
		action="createHtmlSubmit" method="post" theme="simple">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.homepage.name" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="homepageItem.name" cssClass="editbox4" size="20" maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>homepageItem.name</s:param>
							</s:fielderror> </span></td>
				</tr>

				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.homepage.showPosition" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="homepageItem.position" cssClass="editbox4" size="20" maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>homepageItem.position</s:param>
							</s:fielderror> </span></td>
				</tr>

				
				<tr>
					<td colspan="2" width="100%" bgcolor="#f2f2f2"><span
						class="login_txt_bt"><s:fielderror cssStyle="color:red">
								<s:param>homepageItem.content</s:param>
							</s:fielderror> 
							</span>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="left">
					<textarea name="homepageItem.content"
							class="xheditor {tools:'full',html5Upload:false,upMultiple:'1',upImgUrl:'<%=path %>/newsManage/upload.action',upImgExt:'jpg,jpeg,gif,bmp,png'}"
							cols='150' rows='20'></textarea></td>
				</tr>
				<tr>				
					<td colspan="2" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.homepage.createHtml"
							method="createHtmlSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>			
</body>
</html>