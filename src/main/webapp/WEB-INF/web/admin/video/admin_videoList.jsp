<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.model.vediomanage.VideoInfo"%>
<%
	String contextPath = request.getContextPath();
	PaginationResult<VideoInfo> pageationResult = (PaginationResult<VideoInfo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%">
	<tr>
		<td>
			<s:submit key="admin.videoManage.deleteSelect" name="Submit" cssClass="button" theme="simple" onclick="deleteChecked()"/>
			&nbsp;<s:submit key="admin.videoManage.addVideo" name="Submit" cssClass="button" theme="simple" onclick="location='../videoManage/createVideoForm.action'"/>
		</td>
		<td></td>
	</tr>
	<tr>
		<td>
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/videoManage/queryVideos.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="videoInfo">
				
				<%-- Table columns --%>
				<display:column  title="<input type='checkbox' name='checklist'  onclick='checkAll()'>"> 
					<input type="checkbox" id="<s:property value='#attr.videoInfo.videoId'/>">
				</display:column> 
				<display:column property="videoTitle" titleKey="admin.videoManage.videoTitle"/>
				<display:column property="videoLink" titleKey="admin.videoManage.videoLink" />
				<display:column property="videoPicName" titleKey="admin.videoManage.videoImage" />
				<display:column titleKey="admin.videoManage.videoType" >
					<s:if test="%{#attr.videoInfo.videoType==false}">
						<s:text name="admin.videoManage.videoOutLink"/>
					</s:if>
					<s:else>
						<s:text name="admin.videoManage.videoInnerLink"/>
					</s:else>
					
				</display:column>
				<display:column property="createDate" titleKey="admin.videoManage.videoCreateDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column property="lastUpdDate" titleKey="admin.videoManage.videoOperateDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
				<display:column titleKey="admin.videoManage.videoCurrentState" >
					<s:if test="%{#attr.videoInfo.stateId==1}">
						<s:text name="admin.videoManage.common"/>
					</s:if>
					<s:elseif test="%{#attr.videoInfo.stateId==2}">
						<s:text name="admin.videoManage.recommend"/>
					</s:elseif>
					<s:elseif test="%{#attr.videoInfo.stateId==3}">
						<s:text name="admin.videoManage.top"/>
					</s:elseif>
					<s:else>
						<s:text name="admin.videoManage.bottom"/>
					</s:else>
				</display:column>
				<display:column  titleKey="admin.videoManage.videoOperation">
				<s:url id="editUrl" namespace="/videoManage" action="editVideoForm">
              		<s:param name="videoId"><s:property value="#attr.videoInfo.videoId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a>
				<a href="#" onClick="deleteVideo('<s:property value="#attr.videoInfo.videoId"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
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
	