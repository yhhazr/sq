<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="dirty" namespace="/dirty" action="modifyDirtySubmit" method="post" theme="simple" >
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.msgContent"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textarea name="str" value="%{str}" cols="100" rows="10" />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param></s:param>
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
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:submit key="admin.videoManage.upload" name="Submit" cssClass="button" theme="simple" align="centre"/>
			</td>
		</tr>
		

	</s:form>
	</tbody>
</table>