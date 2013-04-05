<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function submit(){
		if(document.getElementById("strTime").value>=0 && document.getElementById("strTime").value <= 23 ){
			location.href = "begin.action?strTime="+document.getElementById("strTime").value;
		}else{
			alert("设置时间应在0到23之间！");
		}
	}
</script>
<body>
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="状态"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:if test="%{#attr.status=='stop'}">
				停止
				</s:if>
				<s:if test="%{#attr.status=='runing'}">
				正在运行。。。。
				</s:if>
			</td>			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="设置时间"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:if test="%{#attr.status=='stop'}">
				<s:textfield name="strTime"></s:textfield>(0-23)
				</s:if>
				<s:if test="%{#attr.status=='runing'}">
				<s:textfield name="strTime" readonly="true"></s:textfield>
				</s:if>
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
				<s:if test="%{#attr.status=='stop'}">
					<input type="button" value="开始" onclick="submit()">
				</s:if>
				<s:if test="%{#attr.status=='runing'}">
					<input type="button" value="停止" onclick="javascript:location.href='stop.action';">
				</s:if>
			</td>	
		</tr>
			
			
			
		</table>
			
</body>
</html>