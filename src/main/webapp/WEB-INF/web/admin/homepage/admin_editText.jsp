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
	<s:form name="editTextSubmit" namespace="/homepageManage"
		action="editTextSubmit" method="post" theme="simple" enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
			<s:hidden name="homepageItem.id"></s:hidden>
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2"><s:text name="admin.homepage.name" /><s:text
							name="admin.common.colon" /></td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="32%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="homepageItem.name" cssClass="editbox4" size="20" maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>homepageItem.name</s:param>
							</s:fielderror> </span></td>
				</tr>
				
				
				
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2"><s:text name="admin.homepage.url" /><s:text
							name="admin.common.colon" /></td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="32%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="homepageItem.url" cssClass="editbox4" size="20" maxlength="150"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>homepageItem.url</s:param>
							</s:fielderror> </span></td>
				</tr>
				
				<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.homepage.content"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textarea name="homepageItem.content" cols="40"></s:textarea>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>homepageItem.content</s:param>
					</s:fielderror>
				</span>
			</td>
				</tr>
	
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2"><s:text name="admin.homepage.showPosition" /><s:text
							name="admin.common.colon" /></td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="32%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="homepageItem.position" cssClass="editbox4" size="20" maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>homepageItem.position</s:param>
							</s:fielderror> </span></td>
				</tr>
				
				<tr>				
					<td colspan="3" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.homepage.editText"
							method="editTextSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>		
</body>
</html>