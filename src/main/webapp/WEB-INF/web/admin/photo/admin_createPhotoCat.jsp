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
<s:form name="createPhotoCatSubmit" namespace="/photoManage" action="createPhotoCatSubmit" method="post" theme="simple" enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoCatName"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="photoCat.catName" cssClass="editbox4" size="20" maxlength="50"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photoCat.catName</s:param>
					</s:fielderror>
				</span>
			</td>			
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.common.recommend"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="#{false:getText('admin.common.no'),true:getText('admin.common.yes')}" name="photoCat.homepageShow" listkey="key" listValue="value" />
			
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoCatDesc"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textarea name="photoCat.catDesc" cols="40"></s:textarea>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photoCat.catDesc</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoCatImage"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:file name="catImage"></s:file>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>catImage</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="left"
				class="left_txt2">
				<s:submit key="admin.photo.createPhotoCat" method="createPhotoCatSubmit" name="Submit" cssClass="button" theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/>
			</td>
		</tr>
		</tbody>
		</table>
	</s:form>
</body>
</html>