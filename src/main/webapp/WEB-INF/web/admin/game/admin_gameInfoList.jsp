<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.model.gamemanage.GameInfo"%>
<%
	String contextPath = request.getContextPath();
	PaginationResult<GameInfo> pageationResult = (PaginationResult<GameInfo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%">
	<tr>
		<td>
			<s:submit key="admin.gameManage.deleteSelect" name="Submit" cssClass="button" theme="simple" onclick="deleteChecked()"/>
			&nbsp;<s:submit key="admin.gameManage.addGame" name="Submit" cssClass="button" theme="simple" onclick="location='../videoManage/createVideoForm.action'"/>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/videoManage/queryVideos.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="gameInfo">
				
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.gameInfo.gameId'/>">
				</display:column> 
				<display:column property="gameName" titleKey="admin.gameManage.gameName"/>
				<display:column property="typeName" titleKey="admin.gameManage.gameType" />			
				<display:column property="imageUrl" titleKey="admin.gameManage.imageUrl" />
				<display:column property="unitName" titleKey="admin.gameManage.unitName" />
				<display:column property="conversionScale" titleKey="admin.gameManage.conversionScal" />

			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
		</pg:pager>
		</td>
	</tr>
	
</table>
<script type="text/javascript">
	highlightTableRows("videoInfo");  
</script>
<script>
	function deleteVideo(videoId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ videoId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/videoManage/deleteVideoSubmit.action?videoId="+videoId;
		else
			return;
	}
</script>
<script type="text/javascript">
	function checkAll(){
	var obj = document.getElementsByTagName("input");
	var j = 0;
		for(var i=0;i<obj.length;i++){
			if(obj[i].checked==true){
				j++;
			}
		}
		if(j==obj.length-1){
			for(var i=0;i<obj.length;i++){
				obj[i].checked=false;
			}
		}else{
			for(var i=0;i<obj.length;i++){
				obj[i].checked=true;
			}
		}
	}
	
	function deleteChecked(){
		var obj = document.getElementsByTagName("input");
		var idList = "";
		for(var i=0;i<obj.length;i++){
			if(obj[i].checked==true && obj[i].name != "checklist" && obj[i].name != "Submit"){
				idList = idList + obj[i].id + ",";
			}
		}
		if(idList == ""){
			alert("no checked");
			return false;
		}
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ idList.substring(0,idList.lastIndexOf(",")) + "</s:param></s:text>")){
			location.href="<%=contextPath%>/videoManage/deleteVideoSubmit.action?videoId="+idList;
		}else
			return;
		
	}
</script>
	