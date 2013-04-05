<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="editMsgSubmit" namespace="/msgManage" action="editMsgSubmit" method="post" theme="simple" >
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.msgType"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
				<s:hidden name="msgInfoEx.msgId" value="%{msgInfoEx.msgId}"/>
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="msgInfoEx.typeName" value="%{msgInfoEx.typeName}" readonly="true" cssClass="editbox4" size="20"/>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.titleName"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="msgInfoEx.titleName" value="%{msgInfoEx.titleName}" readonly="true" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.titleName</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
	
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.msgState"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="msgStateList" listKey="stateId" listValue="stateName" value="%{msgInfoEx.stateId}" name="msgInfoEx.stateId" />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.stateId</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.msgTitle"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="msgInfoEx.userNickName" value="%{msgInfoEx.userNickName}" cssClass="editbox4" size="20" readonly="true"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.msgTitle</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.ipAddress"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="msgInfoEx.ipAddress" value="%{msgInfoEx.ipAddress}" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.ipAddress</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.support"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="msgInfoEx.support" value="%{msgInfoEx.support}" maxlength="7" cssClass="editbox4" size="20" />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.support</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.msgManage.msgContent"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textarea name="msgInfoEx.msgContent" value="%{msgInfoEx.msgContent}" cols="60" rows="10" />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>msgInfo.msgContent</s:param>
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
				<s:submit key="admin.videoManage.upload" name="Submit" cssClass="button" theme="simple" />
			</td>
		</tr>
	</s:form>
	</tbody>
</table>
<script>
	function num(){
		var obj = document.getElementsByName("msgInfo.support").value;
		var reg = "^/[0-9]+/$";
		if(!obj.match(reg)){
			alert("obj");
		}
	}
</script>