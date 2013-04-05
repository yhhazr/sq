<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<jsp:useBean id="currentPageNumber" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="totalItems" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="maxPageItems" type="java.lang.Integer" scope="request"/>

<br>
<span class="tabletitle">Result&nbsp;Page:&nbsp;</span>
<span class="content">
<%
int totalItemCount = totalItems.intValue();
int pageSise = maxPageItems.intValue();
if(totalItemCount < 1){
%>
	没有查询到任何记录. &nbsp;&nbsp;
	
<%
}else if(totalItemCount == 1){
%>
	1 - <%= totalItemCount %> of <%= totalItemCount %> record.&nbsp;&nbsp;	
<%
}else if(totalItemCount <= pageSise){
%>
	1 - <%= totalItemCount %> of <%= totalItemCount %> records.&nbsp;&nbsp;
<%
}
%>
<pg:index export="total=itemCount">
<pg:page export="first,last">
    <%= first %> - <%= last %> of <%= total %> records.&nbsp;&nbsp;
</pg:page>
<pg:first export="firstPageUrl=pageUrl" unless="current">
  <a href="<%= firstPageUrl %>">第一页</a>
</pg:first>
<pg:prev export="prevPageUrl=pageUrl">
  <a href="<%= prevPageUrl %>">前一页</a>
</pg:prev>
<pg:pages><%
  if (pageNumber == currentPageNumber) {
    %> <b><%= pageNumber %></b> <%
  } else {
    %> <a href="<%= pageUrl %>"><%= pageNumber %></a> <%
  }
%></pg:pages>
<pg:next export="nextPageUrl=pageUrl">
  <a href="<%= nextPageUrl %>">后一页</a>
</pg:next>
<pg:last export="lastPageUrl=pageUrl" unless="current">
  <a href="<%= lastPageUrl %>">末页</a>
</pg:last>
</pg:index>
</span>
<br>