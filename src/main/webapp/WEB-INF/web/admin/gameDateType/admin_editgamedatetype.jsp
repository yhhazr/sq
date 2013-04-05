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
	<s:form name="editGameDataTypeSubmit" namespace="/gameDataType"
		action="editGameDataTypeSubmit" method="post" theme="simple"  enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<s:hidden name="dataType.typeId" value="%{dataType.typeId}"/>
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.gameData.typeName" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="dataType.typeName" cssClass="editbox4" size="40"  maxlength="10"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>dataType.typeName</s:param>
							</s:fielderror> </span></td>
				</tr>

				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.gameData.typeShort" /> <s:text
							name="admin.common.colon" /></td>
					<td width="90%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="dataType.shortName" cssClass="editbox4" size="40"  maxlength="20" />
						<span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>dataType.shortName</s:param>
							</s:fielderror> </span>
					</td>
				</tr>
			
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.gameData.parent" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:select
							list="gameDateParentTypes" name="dataType.parentId" listKey="typeId"
							listValue="typeName" value="dataType.parentId" headerKey="0"
							headerValue="%{getText('admin.gameData.selectParent')}" /> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>dataType</s:param>
							</s:fielderror> </span></td>
				</tr>
				
				<tr>
					<td  width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.gameDate.orderID" /> <s:text
							name="admin.common.colon" /></td>
					<td width="90%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="dataType.orderId" lable="" value="%{dataType.orderId}"/>
						<span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>dataType.orderId</s:param>
							</s:fielderror> </span>
					</td>
				</tr>
				
				<tr>
					
					<td colspan="2" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.gameData.editType"
							method="editGameDataTypeSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
</body>
</html>
