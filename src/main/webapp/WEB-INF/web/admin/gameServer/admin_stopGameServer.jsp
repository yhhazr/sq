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
<head>
	<link rel="stylesheet" href="<%=path %>/static/style/datepicker.css">
 	<link rel="stylesheet" href="<%=path %>/static/style/jquery.ui.all.css">
 	<link rel="stylesheet" href="<%=path %>/static/style/colorbox.css">
 	<style type="text/css">
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
		.ui-timepicker-div dl { text-align: left; }
		.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
		.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
		.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
		.mgnt10 { margin-top: 10px; }
 	</style>
</head>
<script type="text/javascript" src="<%=path %>/static/js/waiting.js"></script>
<body>

	<s:form name="stopServerStatusByIdsSubmit" namespace="/gameServer" cssClass="mgnt10"
		action="stopServerStatusByIdsSubmit" method="post" theme="simple"  enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0" align="center">
			<tbody>
				<s:hidden name="serverIds" value="%{serverIds}"/>
				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.stop.startTime" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textfield name="stopServerInfo.startTime" cssClass="editbox4 datepicker" size="25"  maxlength="20" />
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>stopServerInfo.startTime</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.stop.stopTime" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textfield name="stopServerInfo.endTime" cssClass="editbox4 datepicker" size="25"  maxlength="20" />
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>stopServerInfo.endTime</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" class="left_txt2"  align="right">
						<s:text name="admin.gameServer.stop.message" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textarea name="stopServerInfo.message" cssClass="editbox4" cols="25" rows="3"/> 
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>stopServerInfo.message</s:param>
						</s:fielderror> 
					</span></td>
				</tr>

				<tr>
					<td colspan="3" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.gameServer.stop.editStopInfo"
							method="stopServerStatusByIdsSubmit" name="Submit" cssClass="button"
							theme="simple" onclick="showdiv('数据提交中,请稍候........ ')"/></td>
				</tr>
			</tbody>
		</table>
	</s:form>
	
</body>
<script src="<%=path %>/static/js/jquery-1.7.1.js"></script>
<script src="<%=path %>/static/js/background/jquery-ui-1.8.19.custom.js"></script>
<script src="<%=path %>/static/js/background/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$(function(){
		$('.datepicker').datetimepicker({  
	        ampm: false,//上午下午是否显示  
	        timeFormat: 'hh:mm:ss',//时间模式  
	        stepHour: 1,//拖动时间时的间隔  
	        stepMinute: 1,//拖动分钟时的间隔  
	        dateFormat:'yy-mm-dd', //日期格式设定  
	        showHour: true,//是否显示小时，默认是true  
	        showMinute: true,//是否显示分钟，默认是true  
	        showSecond: true
	    });  
	});
</script>
</html>
