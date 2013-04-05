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
 	<style type="text/css">
		.ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
		.ui-timepicker-div dl { text-align: left; }
		.ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
		.ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
		.ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
		.tr_hide{ display: none;}
 	</style>
</head>
<script type="text/javascript" src="<%=path %>/static/js/waiting.js"></script>
<body>
	<s:form name="editGameServerSubmit" namespace="/gameServer"
		action="editGameServerSubmit" method="post" theme="simple"  enctype="multipart/form-data">
		<table class="line_table" width="100%" cellspacing="0" cellpadding="0"
			border="0" align="center">
			<tbody>
				<s:hidden name="gameServer.id" value="%{gameServer.id}"/>
				<s:hidden name="gameServer.createTime" value="%{gameServer.createTime}"/>
				<tr>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.serverName" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textfield name="gameServer.serverName" cssClass="editbox4" size="25"  maxlength="20"/> 
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>gameServer.serverName</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.serverNo" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textfield name="gameServer.serverNo" cssClass="editbox4" size="15"  maxlength="10"/>
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>gameServer.serverNo</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
			
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.serverStatus" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
						<s:select list="statusList" name="gameServer.serverStatus" listKey="statusId"
							listValue="statusName" value="gameServer.serverStatus" id="status_select"/> 
						<span class="login_txt_bt">
							<s:fielderror cssStyle="color:red">
								<s:param>gameServer.serverStatus</s:param>
							</s:fielderror> 
						</span>
					</td>
				</tr>
				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.recommand" /><s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
						<s:radio name="gameServer.recommand" list="#{'true':'是','false':'否'}" value="gameServer.recommand" listKey="key" listValue="value"/>
						<span class="login_txt_bt"> 
							<s:fielderror cssStyle="color:red">
								<s:param>gameServer.recommand</s:param>
							</s:fielderror>
						</span>
					</td>
				</tr>
				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.openingTime" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textfield name="gameServer.openingTime" cssClass="editbox4 datepicker" size="25"  maxlength="20" />
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>gameServer.openingTime</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
				<!-- 维护信息开始 -->
				<tr class='maintain tr_hide'>
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
				<tr class='maintain tr_hide'>
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
				<tr class='maintain tr_hide'>
					<td width="20%" height="30" bgcolor="#f2f2f2" align="right" class="left_txt2">
						<s:text name="admin.gameServer.stop.message" />
						<s:text name="admin.common.colon" />
					</td>
					<td width="3%" bgcolor="#f2f2f2">
						&nbsp;
					</td>
					<td width="30%" height="30" bgcolor="#f2f2f2">
					<s:textarea name="stopServerInfo.message" cssClass="editbox4" cols="25" rows="5"/> 
					<span class="login_txt_bt">
						<s:fielderror cssStyle="color:red">
							<s:param>stopServerInfo.message</s:param>
						</s:fielderror> 
					</span></td>
				</tr>
				<!-- 维护信息结束 -->
				<tr>
					
					<td colspan="3" width="100%" height="30" bgcolor="#f2f2f2" align="center"
						class="left_txt2"><s:submit key="admin.gameServer.editServer"
							method="editGameServerSubmit" name="Submit" cssClass="button"
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
		var checkedValue=$("#status_select").find("option:selected").attr('value');
     	if(checkedValue == -2 && $('.maintain').not(':visible')){
			$('.maintain').show();	     	
     	}
	
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
	    
	     $('#status_select').change(function(){
	     	var checkedValue=$("#status_select").find("option:selected").attr('value');
	     	if(checkedValue == -2 && $('.maintain').not(':visible')){
				$('.maintain').show();	     	
	     	}else if(checkedValue != -2 && $('.maintain').is(':visible')){
	     		$('.maintain').hide();
	     	}
	     });
	});
</script>
</html>
