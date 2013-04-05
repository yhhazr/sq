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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=contextPath %>/static/style/colorbox.css">
<title>Insert title here</title>
</head>
<body>
<table style="margin-top: 10px; width: 100%" >
	<tr>
		<td width="1%">			
				<s:submit key="admin.gameServer.editCheckedServerStatus" 
					name="Submit" cssClass="button colorBox_iframe" theme="simple" onclick="editCheckedServer()"/>
			
		</td>
		<td width="3%">			
				<s:select list="statusList" name="gameServer.serverStatus" listKey="statusId"
							listValue="statusName" value="gameServer.serverStatus" headerKey="aaaa"
							headerValue="%{getText('admin.gameServer.serverStatus')}" /> 
			
		</td>
		<td align="left" width="3%">
				<s:submit value="删除维护信息"
					name="Submit" cssClass="button" theme="simple" onclick="removeStopInfo()"/>
		</td>
		<td align="left" width="7%">
				<s:submit key="admin.gameServer.createServer"
					name="Submit" cssClass="button" theme="simple" onclick="createServer()"/>
		</td>
		<td align="right" width="7%">
				自动生成服务区列表：
		</td>
		<td align="left" width="3%">
			<s:if test="%{#attr.geneServerHtmlStatus=='stop'}">
			停止
			</s:if>
			<s:if test="%{#attr.geneServerHtmlStatus=='running'}">
			正在运行……
			</s:if>
		</td>			
		<td align="left" width="3%">
			<s:if test="%{#attr.geneServerHtmlStatus=='stop'}">
				<input type="button" value="开始" onclick="startGeneServerHtmlTimeTask()">
			</s:if>
			<s:if test="%{#attr.geneServerHtmlStatus=='running'}">
				<input type="button" value="停止" onclick="stopGeneServerHtmlTimeTask()">
			</s:if>
		</td>
		<td align="right" width="1%">
				<s:submit key="admin.gameServer.stopAllServer"
					name="Submit" cssClass="button colorBox_iframe" theme="simple" onclick="stopAllServer()"/>
			
		</td>
		<td align="left" width="1%">
				<s:submit key="admin.gameServer.startAllServer"
					name="Submit" cssClass="button" theme="simple" onclick="startAllServer()"/>
			
		</td>
	</tr>
	<tr>
		<td colspan="9">
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/gameServer/queryGameServerList.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:98%"
				requestURI="" id="gameServerItem">
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.gameServerItem.server.id'/>">
				</display:column> 
				<display:column property="server.id"   titleKey="admin.gameServer.id"/>
				<display:column property="server.serverName"   titleKey="admin.gameServer.serverName"/>
				<display:column property="server.serverNo"   titleKey="admin.gameServer.serverNo"/>
				<display:column titleKey="admin.gameServer.serverStatus">
					<s:property value="#attr.statusMap.get(#attr.gameServerItem.server.serverStatus).statusName"></s:property>
				</display:column>
				<display:column   titleKey="admin.gameServer.recommand">
					<s:if test="%{#attr.gameServerItem.server.recommand=='false'}">
						<s:text name="admin.common.no"/>
					</s:if> 
					<s:else>
						<s:text name="admin.common.yes"/>
					</s:else>
				</display:column>
				<display:column property="server.openingTime"   titleKey="admin.gameServer.openingTime"/>
				<display:column property="stopInfo.startTime"   titleKey="admin.gameServer.stop.startTime"/>
				<display:column property="stopInfo.endTime"   titleKey="admin.gameServer.stop.stopTime"/>
				<display:column  titleKey="admin.common.operation" >
					<s:url id="editUrl" namespace="/gameServer" action="editGameServer">
	              		<s:param name="id"><s:property value="#attr.gameServerItem.server.id"/></s:param>
	       			</s:url>
	       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
					<a href="#" onClick="deleteServer('<s:property value="#attr.gameServerItem.server.id"/>','<s:property value="#attr.gameServerItem.server.serverName"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
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
	

	function deleteServer(serverId,serverName){
		if(window.confirm("<s:text name="admin.gameServer.delete.message"><s:param>"+ serverName + "</s:param></s:text>"))
			location.href="<%=contextPath%>/gameServer/deleteGameServerSubmit.action?id="+serverId;
		else
			return;
	}
	function stopAllServer(){
		if(window.confirm("<s:text name="admin.gameServer.stopAllServer.message"></s:text>")){
			$('.colorBox_iframe').colorbox({iframe:true, width:'40%', height:'60%', href:'<%=contextPath%>/gameServer/stopServerStatusByIds.action?id=all', onClosed:refresh_page});
		}else
			return;
	}
	function startAllServer(){
		if(window.confirm("<s:text name="admin.gameServer.startAllServer.message"></s:text>"))
			location.href="<%=contextPath%>/gameServer/startAllGameServerStatus.action";
		else
			return;
	}
	function checkAll(){
		var checkBoxs = document.getElementsByTagName("input");
		var j = 0;
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true){
				j++;
			}
		}
		if(j == checkBoxs.length - 1){
			for(var k = 0 ; k < checkBoxs.length; k++){
				checkBoxs[k].checked = false;
			}
		}else{
			for(var i = 0; i < checkBoxs.length; i++){
				checkBoxs[i].checked = true;
			}
		}
	}
	function editCheckedServer(){
		var checkBoxs = document.getElementsByTagName("input");
		var ids = "";
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true && checkBoxs[i].name != "Submit" && checkBoxs[i].name != "checklist"){
				ids = ids+checkBoxs[i].id+",";
			}
		}
		if(ids == ""){
			alert('请选择要更新的服务区');
			return false;
		}
		var status = document.getElementById('gameServer_serverStatus');
		var statusId = status.value;
		var statusValue = status.options[status.selectedIndex].text;
		if(statusId && !/^\-?\d{0,10}$/.test(statusId)){
			alert('请选择要更新的状态');
			return false;
		}
		if(window.confirm("<s:text name="admin.gameServer.editCheckedServer.message"><s:param>"+ statusValue + "</s:param></s:text>")){
			if(statusId == -2){
				$('.colorBox_iframe').colorbox({iframe:true, width:'40%', height:'60%', href:'<%=contextPath%>/gameServer/stopServerStatusByIds.action?id='+ids, onClosed:refresh_page});
				return false;
			}else{
				location.href="<%=contextPath%>/gameServer/editCheckedServersStatus.action?id="+ids+"&statusId="+statusId;
				return false;
			}
		}else{
			return false;		
		}		
	}

	//删除维护信息	
	function removeStopInfo(){
		var checkBoxs = document.getElementsByTagName("input");
		var ids = "";
		for(var i = 0; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true && checkBoxs[i].name != "Submit" && checkBoxs[i].name != "checklist"){
				ids = ids+checkBoxs[i].id+",";
			}
		}
		if(ids == ""){
			alert('请选择要更新的服务区');
			return false;
		}
		if(window.confirm("确定要删除所选区服的维护信息吗？")){
			location.href="<%=contextPath%>/gameServer/removeStopInfo.action?id="+ids;
			return false;			
		}else{
			return false;		
		}		
	}
	
	function createServer(){
		location.href="<%=contextPath%>/gameServer/createGameServer.action";
	}
	
	function startGeneServerHtmlTimeTask(){
		location.href="<%=contextPath%>/geneHtml/startServerTimerTask.action?fromPage=true";
	}
	function stopGeneServerHtmlTimeTask(){
		location.href="<%=contextPath%>/geneHtml/stopServerTimerTask.action?fromPage=true";
	}
	
	var this_href = window.location.href;
	//刷新页面函数
	var refresh_page = function refreshPage(){
		window.location.href = this_href;
	}
	
/*	
	function orderItemsInit(){
		var id_tag = $('thead th:eq(1)');
		id_tag.attr('id', 'id').css('cursor', 'pointer');
		var serverNo_tag = $('thead th:eq(3)');
		serverNo_tag.attr('id', 'serverNo').css('cursor', 'pointer');
		var serverStatus_tag = $('thead th:eq(4)');
		serverStatus_tag.attr('id', 'serverStatus').css('cursor', 'pointer');
		var openingTime_tag = $('thead th:eq(6)');
		openingTime_tag.attr('id', 'openingTime').css('cursor', 'pointer');
		var startTime_tag = $('thead th:eq(7)');
		startTime_tag.attr('id', 'startTime').css('cursor', 'pointer');
		var endTime_tag = $('thead th:eq(8)');
		endTime_tag.attr('id', 'endTime').css('cursor', 'pointer');
		
		return new Array(id_tag, serverNo_tag, serverStatus_tag, openingTime_tag, startTime_tag, endTime_tag);
	}
*/	
	

		
</script>	