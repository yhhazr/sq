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
	<s:form name="editNewsSubmit" namespace="/newsManage"
		action="editNewsSubmit" method="post" theme="simple" onsubmit="ttt()" enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<s:hidden name="news.newsId"/>
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.newsName" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:textfield
							name="news.artTitle" cssClass="editbox4" size="40"  maxlength="50"/> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>news.artTitle</s:param>
							</s:fielderror> </span></td>
				</tr>
				<!-- 新闻图片上传开始 -->
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center" class="left_txt2">
						<s:text name="admin.news.newsImg"/><s:text name="admin.common.colon"/>
					</td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2">
						<s:file name="uploadPath" accept="test/*" id="path" value=""/>
						<span class="login_txt_bt">
							<s:fielderror cssStyle="color:red">
								<s:param>uploadPath</s:param>
							</s:fielderror>
						</span>
					</td>
				</tr>
				<!-- 新闻图片上传结束 -->
				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.type" /> <s:text
							name="admin.common.colon" /></td>
					
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:select
							list="newsTypeList" name="news.typeId" listKey="newsTypeId"
							listValue="newsTypeName" value="news.typeId" headerKey="0"
							headerValue="%{getText('admin.news.selectType')}" /> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>news.typeId</s:param>
							</s:fielderror> </span></td>
				</tr>

				<tr align="left">
					<td width="10%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:text name="admin.news.state" /> <s:text
							name="admin.common.colon" /></td>
				
					<td width="90%" height="30" bgcolor="#f2f2f2"><s:select
							list="newsStateList" name="news.stateId" listKey="newsStateId"
							listValue="newsStateName" value="news.stateId" headerKey="0"
							headerValue="%{getText('admin.news.selectState')}" /> <span
						class="login_txt_bt"> <s:fielderror cssStyle="color:red">
								<s:param>news.stateId</s:param>
							</s:fielderror> </span></td>
				</tr>
				<tr>
					<td colspan="2" width="100%" bgcolor="#f2f2f2"><span
						class="login_txt_bt"><s:fielderror cssStyle="color:red">
								<s:param>news.newsContent</s:param>
							</s:fielderror> 
							</span>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left"><textarea name="news.newsContent"
							class="xheditor {tools:'full',html5Upload:false,upMultiple:'1',upImgUrl:'upload.action',upImgExt:'jpg,jpeg,gif,bmp,png',disableContextmenu:'true'}"
							cols='150' rows='20'><s:property value="news.newsContent"/></textarea></td>
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
	<script type="text/javascript">
		function ttt(){
		var exps = document.getElementsByName("news.exps")[0].value;
		var exp1 = document.getElementsByName("news.exp1")[0].value;
		var exp2 = document.getElementsByName("news.exp2")[0].value;
		var exp3 = document.getElementsByName("news.exp3")[0].value;
		var exp4 = document.getElementsByName("news.exp4")[0].value;
		var exp5 = document.getElementsByName("news.exp5")[0].value;
		if(exp1 == "" || exp2 == "" ||exp3 == "" ||exp4 == "" ||exp5 == "" ){
			exps = "";
		}else{
			exps = exp1+exp2+exp3+exp4+exp5;
		}
		document.getElementsByName("news.exps")[0].value = exps;
		}
	</script>
</body>
</html>
