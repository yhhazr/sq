<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    String path = request.getContextPath();
%>
<head>
	<link rel="stylesheet" href="<%=path %>/static/style/datepicker.css">
 	<link rel="stylesheet" href="<%=path %>/static/style/jquery.ui.all.css">
 	<style type="text/css">
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
		.ui-timepicker-div dl { text-align: left; }
		.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
		.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
		.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
 	</style>
</head>
<script type="text/javascript" src="<%=path%>/static/js/waiting.js"></script>
<body>
	<s:form name="editActivityIncSubmit" namespace="/activityIncManage"
		action="editActivityInc" method="post" theme="simple"
		enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0">
			<tbody>
				<s:hidden name="activityInc.activityId"></s:hidden>
				<s:hidden name="activityInc.activityImg"></s:hidden>
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activityInc.name" /> 
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="activityInc.activityName" cssClass="editbox4" size="20"
								maxlength="50" /> 
						<span class="login_txt_bt"> 
							<s:fielderror cssStyle="color:red">
								<s:param>activityInc.activityName</s:param>
							</s:fielderror> 
						</span>
					</td>
				</tr>

				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activityInc.url" /> 
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="activityInc.activityUrl" cssClass="editbox4" size="20"
							maxlength="200" /> 
							<span class="login_txt_bt"> 
								<s:fielderror cssStyle="color:red">
									<s:param>activityInc.activityUrl</s:param>
								</s:fielderror> 
							</span>
					</td>
				</tr>

				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activity.uploadImage" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:file name="filedata" accept="image/gif"></s:file> 
						<span class="login_txt_bt">
							<s:fielderror cssStyle="color:red">
								<s:param>filedata</s:param>
							</s:fielderror> 
						</span>
					</td>
				</tr>

				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activity.startDate" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="startDateStr" cssClass="editbox4 datepicker"  readonly="true" size="20"
							maxlength="50" /> 
							<span class="login_txt_bt"> 
								<s:fielderror cssStyle="color:red">
									<s:param>startDateStr</s:param>
								</s:fielderror>
						</span>
					</td>
				</tr>
				
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activity.endDate" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="endDateStr" cssClass="editbox4" id="datepicker" readonly="true" size="20"
							maxlength="50" /> 
							<span class="login_txt_bt"> 
								<s:fielderror cssStyle="color:red">
									<s:param>endDateStr</s:param>
								</s:fielderror>
						</span>
					</td>
				</tr>
				
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
						class="left_txt2">
						<s:text name="admin.activity.introduction" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">&nbsp;</td>
					<td width="32%" height="30" bgcolor="#f2f2f2">
						<s:textarea name="activityInc.activityIntroduction" cols="40"></s:textarea>
						<span class="login_txt_bt">
							<s:fielderror cssStyle="color:red">
								<s:param>activityInc.activityIntroduction</s:param>
							</s:fielderror>
						</span>
					</td>
				</tr>

				<tr>
					<td colspan="3" width="100%" height="30" bgcolor="#f2f2f2"
						align="center" class="left_txt2"><s:submit
							key="admin.activity.editActivityInc" method="editActivityInc"
							name="Submit" cssClass="button" theme="simple"
							onclick="showdiv('数据提交中,请稍候........ ')" /></td>
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
		
		$('#datepicker').datetimepicker({  
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