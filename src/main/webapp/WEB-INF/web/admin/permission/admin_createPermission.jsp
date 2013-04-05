<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="createPermissionSubmit" namespace="/permManage" action="createPermissionSubmit" method="post" theme="simple">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.parent"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="parentPermissionList" name="permission.parentPermId" listKey="permId" listValue="permName" value="permission.parentPermId"  headerKey="0" headerValue="%{getText('admin.permission.selectPermission')}"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>permission.parentPermId</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.permName"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="permission.permName" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>permission.permName</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.permDesc"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="permission.permDesc" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>permission.permDesc</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.permUrl"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="permission.permUrl" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>permission.permUrl</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.orderId"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="permission.orderId" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>permission.orderId</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.common.enable"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:checkbox name="permission.restrictedFlag" />
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.permission.isMenu"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:checkbox name="permission.menuFlag"/>
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
				<s:submit key="admin.permManage.createPermission" method="createPermissionSubmit" name="Submit" cssClass="button" theme="simple" />
			</td>
		</tr>

	</s:form>
	</tbody>
</table>