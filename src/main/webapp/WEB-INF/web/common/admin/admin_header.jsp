<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="com.sz7road.web.common.util.AppConstants"%>
<%@page import="com.sz7road.web.model.usermanage.AdminUserRight"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String contextPath = request.getContextPath();
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="74%" height="38" class="admin_txt">管理员：<b><s:property value="#session.userRight.currentUser"/></b> 您好,感谢登陆使用！</td>
        <s:iterator var="permInfo" value="#session.userRight.permissions">
        	<s:if test="#permInfo.permId == 140">
		       <td><input type="button" onclick="genePlayerRank()" href="javascript:void(0);" value="生成玩家排行页"/></td>
        	</s:if>
        </s:iterator>
       <s:iterator var="permInfo" value="#session.userRight.permissions">
        	<s:if test="#permInfo.permId == 126">
		       <td><a onclick="createAllHtml()" href="javascript:void(0);" ><img src="<%=contextPath%>/static/images/html.jpg" alt=""  height="20" border="0"></a></td>
        	</s:if>
        </s:iterator>
        <td width="22%"><a href="#" target="_self" onclick="logout();"><img src="<%=contextPath%>/static/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
        <td width="4%">&nbsp;</td>
      </tr>
      <tr>
        <td height="19" colspan="3">&nbsp;</td>
      </tr>
</table>
<script src="<%=contextPath %>/static/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=contextPath %>/static/js/waiting.js"></script>
<script language="JavaScript">


function logout(){
		if(window.confirm("<s:text name="admin.common.logout.message"/>"))
			location.href="<%=contextPath%>/adminLogout.action";
		else
			return;
}
function createAllHtml(){
	if(window.confirm("确定生成 全部页面 吗？")){
		var url = '<%=contextPath %>/geneAllHtml.action?version=' + new Date().getTime();
		var message = '生成失败';
		$.ajax({
	        type:"get",
	        url:url,
	        async: true,
	        beforeSend:function (XMLHttpRequest, textStatus) {
				showdiv('页面生成中，请稍后……');
	        },
	        success:function (msg) {
	            if(msg.result == 'true'){
	            	message = '生成成功';
	            }
	        },
	        complete:function(){
	        	$('#msgDiv').remove();
	        	$('#bgDiv').remove();
				alert(message);	
	        }
	    });
	}
	return false;
}
function createNewsHtml(){
	if(window.confirm("确定生成 新闻页面 吗？")){
		var url = '<%=contextPath %>/geneHtml/geneNewsHtml.action?version=' + new Date().getTime();
		var message = '生成失败';
		$.ajax({
	        type:"get",
	        url:url,
	        async: true,
	        beforeSend:function (XMLHttpRequest, textStatus) {
				showdiv('页面生成中，请稍后……');
	        },
	        success:function (msg) {
	            if(msg.result == 'true'){
	            	message = '生成成功';
	            }
	        },
	        complete:function(){
	        	$('#msgDiv').remove();
	        	$('#bgDiv').remove();
				alert(message);	
	        }
	    });
	}
	return false;
}
//生成开服图片flash资源
function geneStartServerResrcHtml(){
	if(window.confirm("确定生成 开服图片和flash页面 吗？")){
		var url = '<%=contextPath %>/geneHtml/geneStartServerResrcHtml.action?version=' + new Date().getTime();
		var message = '生成失败';
		$.ajax({
	        type:"get",
	        url:url,
	        async: true,
	        beforeSend:function (XMLHttpRequest, textStatus) {
				showdiv('页面生成中，请稍后……');
	        },
	        success:function (msg) {
	            if(msg.result == 'true'){
	            	message = '生成成功';
	            }
	        },
	        complete:function(){
	        	$('#msgDiv').remove();
	        	$('#bgDiv').remove();
	        	alert(message);	
	        }
	    });
	}
	return false;
	
}
//生成玩家排行页
function genePlayerRank(){
	if(window.confirm("确定生成 玩家排行页 吗？")){
		var url = '<%=contextPath %>/genePlayerRank.action?version=' + new Date().getTime();
		var message = '生成失败';
		$.ajax({
	        type:"get",
	        url:url,
	        async: true,
	        beforeSend:function (XMLHttpRequest, textStatus) {
				showdiv('页面生成中，请稍后……');
	        },
	        success:function (msg) {
	            if(msg.result == 'true'){
	            	message = '生成成功';
	            }
	        },
	        complete:function(){
	        	$('#msgDiv').remove();
	        	$('#bgDiv').remove();
	        	alert(message);	
	        }
	    });
	}
	return false;
	
}
</script>