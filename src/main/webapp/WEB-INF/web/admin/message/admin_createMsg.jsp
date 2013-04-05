<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	<s:form name="editVideoSubmit" namespace="/videoManage" action="editVideoSubmit" method="post" theme="simple" enctype="multipart/form-data">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.videoManage.videoType"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
				<span>
					<s:hidden name="videoInfo.videoId" value="%{videoInfo.videoId}"/>
				</span>
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select name="videoInfo.videoType" list="#{false:getText('admin.videoManage.videoOutLink'),true:getText('admin.videoManage.videoInnerLink')}" listkey="key" listValue="value" value="videoInfo.videoType">
				</s:select>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.videoManage.videoName"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="videoInfo.videoTitle" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>videoInfo.videoTitle</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
	
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.videoManage.videoLink"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="videoInfo.videoName" cssClass="editbox4" size="20"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>videoInfo.videoName</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.videoManage.videoShow"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:radio name="videoInfo.enableFlag" list="#{true:getText('admin.videoManage.videoDisplay'),false:getText('admin.videoManage.videoHidden') }" listKey="key" listValue="value" value="videoInfo.enableFlag"/>
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
				<s:text name="admin.videoManage.videoCurrentState"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="videoStateList" listKey="stateId" listValue="stateName" headerKey=""  value="videoInfo.stateId" name="videoInfo.stateId" />
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
				<s:text name="admin.videoManage.videoShortImg"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:file name="uploadPath"  />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>uploadPath</s:param>
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