<%@page import="com.sz7road.web.model.homepagemanage.HomepageItem"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.newsmanage.*" %>
<%@page import="com.sz7road.web.model.gamemanage.GameServerStatus" %>
<%@page import="com.sz7road.web.model.gamemanage.PlatformGameServer" %>
<%@page import="com.sz7road.web.model.gamemanage.StopGameServerInfo" %>
<%@page import="com.sz7road.web.model.gamemanage.GameServerAllInfo" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%
	String contextPath = request.getContextPath();
	PaginationResult<GameServerAllInfo> pageationResult = (PaginationResult<GameServerAllInfo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
	List<GameServerStatus> statusList = (List<GameServerStatus>)request.getAttribute("statusList");
	String LOGINGAME = "http://account.7road.com/PlayGame2?g=1&game=S&subGame=0&z=";
	long timestamp = new Date().getTime();
	String name = SystemConfig.getProperty("background.test.enterGame.username");
	String password = SystemConfig.getProperty("background.test.enterGame.password");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=contextPath %>/static/style/colorbox.css">
<title>Insert title here</title>
</head>
<body>
<table style="margin-top: 10px; width: 100%" >
	<tr width="100%">
		<td align="left" width="30%">			
			官网选服页服务器列表
		</td>
		<td align="left" width="1%">
			<input type="button" value="清除缓存" id="cleanCacheButton"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/gameServer/queryOnlineGameServerList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:98%"
				requestURI="" id="gameServerItem">
				<%-- Table columns --%>
				<display:column property="id"   titleKey="admin.gameServer.id"/>
				<display:column property="serverName"   titleKey="admin.gameServer.serverName"/>
				<display:column property="serverNo"   titleKey="admin.gameServer.serverNo"/>
				<display:column titleKey="admin.gameServer.serverStatus">
					<s:property value="#attr.statusMap.get(#attr.gameServerItem.serverStatus).statusName"></s:property>
				</display:column>
				<display:column   titleKey="admin.gameServer.recommand">
					<s:if test="%{#attr.gameServerItem.recommand=='false'}">
						<s:text name="admin.common.no"/>
					</s:if> 
					<s:else>
						<s:text name="admin.common.yes"/>
					</s:else>
				</display:column>
				<display:column property="openingTime" titleKey="admin.gameServer.openingTime"/>
				<display:column titleKey="admin.gameServer.gameTest">
					<a href="<%= LOGINGAME%><s:property value="#attr.gameServerItem.id"/>&timestamp=<%= timestamp %>" class="server" target="_blank" ><s:text name="admin.gameServer.enterGame"/></a>&nbsp;
				</display:column>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
</body>
</html>
<script src="<%=contextPath %>/static/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=contextPath %>/static/js/background/jquery.colorbox.js"></script>
<script type="text/javascript">
	highlightTableRows("gameServerItem");  
	
//	var SERVER="http://localhost:8080/game_7road";
	var SERVER="http://sq.7road.com/game7road";
	var PLATFORM_PATH = 'http://account.7road.com/Sync?type=sync&s=';
	
	$(function(){
		$(".server").click(function(){
			//登录平台
			var gameUrl = $(this).attr('href');
			var canLogGame = enterGame(gameUrl);
			if (canLogGame == 0) {
				return true;
			} else {
				return false;
			}
		});
		
		//清除缓存
		$('#cleanCacheButton').click(function(){
			var clean_url = PLATFORM_PATH + new Date().getTime();
			$.ajax({
				   type: "get",
				   dataType:"jsonp",
       			   jsonp:"jsoncallback",
				   url: clean_url,
				   async: true,
				   success:function(msg){
				   	  if(msg.code == '0') alert('缓存清除成功！');
				   	  else alert('缓存清除失败！');
				   },
				   error:function(){
				   	  alert('缓存清除失败！');
				   }
			});
		});
		
	});
	
	//进入游戏函数
	function enterGame(gameUrl) {
		var flag = 1;
		var code = 1;
		var message = "";
		if(gameUrl && gameUrl != '' && gameUrl != '#'){
			$.ajax({
				   type: "get",
				   dataType: "json",
				   url: SERVER + "/bgLoginGame/checkLoginGame.action?timestamp=a" + new Date().getTime(),
				   async: false,
				   data:{gameUrl:gameUrl},
				   success:function(respMsg){
					   code = respMsg.code;
					   message = respMsg.message;
				   }
			});
			if(code != 0){
				flag = 1;
				if(message != null && message != ""){
					alert(message);
				}else{
					alert("服务器连接超时，请稍后再试。");
				}
			}else{
				flag = 0;
			}
		}
		return flag;
	}
	
	//刷新页面函数
	var refresh_page = function refreshPage(){
		window.location.href = this_href;
	}
	
	
	
	

		
</script>	