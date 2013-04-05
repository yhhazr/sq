<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
	border="0">
	<tbody>
		<s:form name="assignPermissionSubmit" namespace="/roleManage"
			action="assignPermissionSubmit" method="post" theme="simple">
			<s:hidden name="role.roleId" />
			<tr>
				<td width="100%" height="30" bgcolor="#f2f2f2" align="left"
					class="left_bt2">
					<s:iterator var="permInfo" value="permInfoList" status="st">
						<tr>
							<td width="100%" height="30" bgcolor="#f2f2f2" align="left"
								class="left_bt2">
								<s:if test="(#permInfo.permId) in needCheckedPermInfoList"> 
								<s:checkbox name="permCheckBox" value="true" fieldValue="%{#permInfo.permId}" />
								</s:if>
								<s:else> 
									<s:checkbox name="permCheckBox" value="false" fieldValue="%{#permInfo.permId}" />
								</s:else> 
								<s:property value="#permInfo.permName" />
							</td>
						</tr>
						<tr>
							<td width="100%" height="30" bgcolor="" align="left"
								class="left_txt2">
								<s:iterator var="childPermission"
									value="#permInfo.childPermissions" status="st1">
									<s:if test="(#childPermission.permId) in needCheckedPermInfoList"> 
										<s:checkbox name="permCheckBox" value="true" fieldValue="%{#childPermission.permId}" />
									</s:if>
									<s:else> 
										<s:checkbox name="permCheckBox" value="false" fieldValue="%{#childPermission.permId}" />
									</s:else> 
									<s:property value="#childPermission.permName" />
								</s:iterator>
							</td>
						</tr>
					</s:iterator>
				</td>
			</tr>
			<tr>
				<td width="20%" height="30" bgcolor="#f2f2f2" align="center"
					class="left_txt2">
					<s:submit key="admin.roleManage.editRole" method="assignPermissionSubmit"
						name="Submit" cssClass="button" theme="simple" />
				</td>
			</tr>

		</s:form>
	</tbody>
</table>
