<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%@page import="com.sz7road.web.model.usermanage.UserInfo"%>
<%
	String contextPath = request.getContextPath();
	PaginationResult<UserInfo> pageationResult = (PaginationResult<UserInfo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>
<table style="margin-top: 10px; width: 100%">
	<tr>
		<td>
			<s:submit key="admin.videoManage.deleteSelect" name="Submit" cssClass="button" theme="simple" onclick="deleteSelect()"/>
			&nbsp;<!-- s:submit key="admin.msgManage.createMsg" name="Submit" cssClass="button" theme="simple" onclick="location='../msgManage/createMsgForm.action'"/-->
			
		</td>
	</tr>
	<tr>
		<td>
			<s:form name="queryMsgByTypeAndState" namespace="/msgManage" action="queryMsg" method="post" theme="simple">
				<s:select list="msgTypeList" listKey="typeId" listValue="typeName" headerKey="0" headerValue="--种类--" name="msgInfoEx.typeId" />
				<s:select list="msgStateList" listKey="stateId" listValue="stateName" headerKey="0" headerValue="--状态--" name="msgInfoEx.stateId" />
				<s:submit key="admin.videoManage.upload" method="queryMsgByTypeAndState" name="Submit" cssClass="button" theme="simple" id="query"/>
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
		<pg:pager items="<%=pageationResult.getTotal()%>" url='<%=request.getContextPath() + "/msgManage/queryMsg.action"%>' index="center" maxPageItems='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.page.size")) %>'
									maxIndexPages='<%= Integer.parseInt(SystemConfig.getProperty("admin.search.max.index.pages")) %>' isOffset="true" export="offset,currentPageNumber=pageNumber" scope="request">
			<display:table name="requestScope.pageationResult.resultList" class="tablelist" 
				defaultsort="0" cellpadding="0" cellspacing="0" style="width:90%"
				requestURI="" id="msgInfo">
				<%-- Table columns --%>
				<display:column title="<input type='checkbox' name='checkList' onclick='checkAll()'/>">
				<input type="checkbox" id="<s:property value='#attr.msgInfo.msgId'/>">
				</display:column>
				<display:column property="msgId"   titleKey="admin.msgManage.msgId"/>
				<display:column property="typeName" titleKey="admin.msgManage.msgType" />
				<display:column property="titleId" titleKey="admin.msgManage.titleId" />
				<display:column property="titleName"   titleKey="admin.msgManage.titleName"/>
				<display:column property="stateName" titleKey="admin.msgManage.msgState" />
				<display:column property="userNickName"   titleKey="admin.msgManage.msgTitle" />
				<display:column property="msgContent"   titleKey="admin.msgManage.msgContent" />
				<display:column property="ipAddress"   titleKey="admin.msgManage.ipAddress" />
				<display:column property="createDate"   titleKey="admin.msgManage.createDate" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
				<display:column property="support"   titleKey="admin.msgManage.support"/>
				<display:column  titleKey="admin.common.operation" >
				<s:url id="editUrl" namespace="/msgManage" action="editMsgForm">
              		<s:param name="msgId"><s:property value="#attr.msgInfo.msgId"/></s:param>
       			</s:url>
       			<s:a href="%{editUrl}"><s:text name="admin.common.edit"/></s:a> 
				<a href="#" onClick="deleteMsg('<s:property value="#attr.msgInfo.msgId"/>')"><s:text name="admin.common.delete"/></a>&nbsp;
				</display:column>
			</display:table>
			<jsp:include page="/WEB-INF/web/common/pagination.jsp" flush="true" />
			</pg:pager>
		</td>
	</tr>
</table>
<script type="text/javascript">
	highlightTableRows("msgInfo");  
</script>
<script>
	function deleteMsg(msgId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ msgId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/msgManage/deleteMsg.action?msgIdStr="+msgId;
		else
			return;
	}
	
	function checkAll(){
		var obj = document.getElementsByTagName("input");
		var j = 0;
		for(var i = 0;i<obj.length;i++){
			if(obj[i].checked==true){
				j++;
			}
		}
		if(j == obj.length-1){
			for(var i = 0;i<obj.length;i++){
				obj[i].checked = false;
			}
		}else{
			for(var i = 0;i<obj.length;i++){
				obj[i].checked = true;
			}
		}
	}
	
	function deleteSelect(){
		var obj = document.getElementsByTagName("input");
		var idStr = "";
		for(var i = 0;i<obj.length;i++){
			if(obj[i].checked == true && obj[i].name != "Submit" && obj[i].name != "checklist" && obj[i].id != "query"){
				idStr = idStr + obj[i].id + ",";
			}
		}
		
		if(idStr == ""){
			alert("没有选中");
			return false;
		}
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ idStr.substring(0, idStr.lastIndexOf(",")) + "</s:param></s:text>"))
			location.href="<%=contextPath%>/msgManage/deleteMsg.action?msgIdStr="+idStr;
		else
			return;
		
	}
</script>	