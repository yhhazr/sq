<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
 %>
<script type="text/javascript" src="<%=path %>/static/js/waiting.js"></script>
<body>
	<s:form name="editGameSubmit" namespace="/gameDate"
		action="editGameDateSubmit" method="post" theme="simple"  enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<s:hidden name="gameDate.id" value="%{gameDate.id}"/>
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.newsName" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="gameDate.artTitle" cssClass="editbox4" size="40"  maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>gameDate.artTitle</s:param>
							</s:fielderror> </span></td>
				</tr>
			
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.type" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:select
							list="types" name="gameDate.typeId" listKey="typeId"
							listValue="typeName" value="gameDate.typeId" headerKey="0"
							headerValue="%{getText('admin.news.selectType')}" /> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>gameDate.typeId</s:param>
							</s:fielderror> </span></td>
				</tr>

				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.state" /> <s:text
							name="admin.common.colon" /></td>
				
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:select
							list="states" name="gameDate.stateId" listKey="newsStateId"
							listValue="newsStateName" value="gameDate.stateId" headerKey="0"
							headerValue="%{getText('admin.news.selectState')}" /> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>gameDate.stateId</s:param>
							</s:fielderror> </span></td>
				</tr>
				<tr>
					<td rowspan="2" width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.gameDate.orderID" /> <s:text
							name="admin.common.colon" /></td>
					<td width="90%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="gameDate.orderNum"  value="%{gameDate.orderNum}"/>
						<span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>gameDate.orderNum</s:param>
							</s:fielderror> </span>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" width="100%" bgcolor="#f2f2f2"><span
						class="login_txt_bt"><s:fielderror cssStyle="color:red">
								<s:param>gameDate.content</s:param>
							</s:fielderror> 
							</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left"><textarea name="gameDate.content"
							class="xheditor {tools:'full',html5Upload:false,upMultiple:'1',upImgUrl:'upload.action',upImgExt:'jpg,jpeg,gif,bmp,png',disableContextmenu:'true'}"
							cols='150' rows='20'><s:property value="gameDate.content"/></textarea></td>
				</tr>
				<tr>
					
					<td colspan="2" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.news.editNews"
							method="editNewsSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
</body>
</html>
