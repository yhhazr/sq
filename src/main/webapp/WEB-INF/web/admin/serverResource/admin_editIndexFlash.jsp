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
	<s:form name="editFlashSubmit" namespace="/indexFlash"
		action="editFlashSubmit" method="post" theme="simple" enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<s:hidden name="flashItem.id" ></s:hidden>
				<s:hidden name="flashItem.createDate" ></s:hidden>
				<s:hidden name="flashItem.content" ></s:hidden>
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2"><s:text name="admin.serverResource.uploadFileName" /><s:text
							name="admin.common.colon" /></td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="52%" height="30" bgcolor="#f2f2f2">
						<s:text name="%{#attr.flashItem.content}"></s:text>
					</td>
				</tr>
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.homepage.uploadFlash"/><s:text name="admin.common.colon"/>
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="52%" height="30" bgcolor="#f2f2f2">
						<s:file name="flashFile"  accept="image/swf"></s:file>
						<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>flashFile</s:param>
						</s:fielderror>
					</span>
					</td>
				</tr>
				
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2"><s:text name="admin.homepage.url" /><s:text
							name="admin.common.colon" /></td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="52%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="flashItem.url" cssClass="editbox4" size="30" maxlength="150"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>flashItem.url</s:param>
							</s:fielderror> </span></td>
				</tr>
				
				<tr>				
					<td colspan="3" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.homepage.editFlash"
							method="editFlashSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>		

</body>
</html>