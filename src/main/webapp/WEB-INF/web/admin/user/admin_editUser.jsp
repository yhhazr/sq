<%@page import="com.sz7road.web.model.usermanage.UserInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String userId = request.getParameter("userId");
%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="editUserSubmit" namespace="/userManage" action="editUserSubmit" method="post" theme="simple">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.userId" /><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="user.userId" cssClass="editbox4" size="20" readonly="true"/>
			</td>
			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.userName" /><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="user.userName" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.userName</s:param>
					</s:fielderror>
				</span>
			</td>
			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.old.passWord"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:password name="user.oldPassWord" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.oldPassWord</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.passWord"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:password name="user.passWord" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.passWord</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.rePassWord"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:password name="user.rePassWord" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.rePassWord</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.emailAddr"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="user.emailAddr" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.emailAddr</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.role"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="roleInfoList" name="user.roleId" listKey="roleId" listValue="roleName" value="user.roleId"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.roleId</s:param>
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
				<s:submit key="admin.userManage.editUser" name="Submit" cssClass="button" theme="simple" />
			</td>
		</tr>
	</s:form>
	</tbody>
</table>