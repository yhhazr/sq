<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="createUserSubmit" namespace="/userManage" action="createUserSubmit" method="post" theme="simple">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.user.userName"/><s:text name="admin.common.colon"/>
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
				<s:select list="roleInfoList" name="user.roleId" listKey="roleId" listValue="roleName" value="user.roleId"  headerKey="0" headerValue="%{getText('admin.user.selectRole')}"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>user.roleId</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<textarea name="content" class="xheditor {upImgUrl:'{upload.php}'}" cols="150" rows="20" >test</textarea>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="left"
				class="left_txt2">
				<s:submit key="admin.userManage.createUser" method="createUserSubmit" name="Submit" cssClass="button" theme="simple" />
			</td>
		</tr>

	</s:form>
	</tbody>
</table>