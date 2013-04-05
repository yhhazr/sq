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
	<style type="text/css">
		.green {color:green;}
		.red {color:red;}
	</style>
</head>
<body>
		<table class="line_table" width="90%" cellspacing="0" cellpadding="0"
			border="0" align="center" bgcolor="#f2f2f2">
			<tbody>
				<tr height="30"><td colspan="5" bgcolor="#f2f2f2"></td>
				</tr>
				<tr>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2" align="right">
						游戏名
						<s:text	name="admin.common.colon" />
					</td>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2">神曲</td>
					
					<!-- server list -->
					<td width="5%" colspan="3" height="30" bgcolor="#f2f2f2" class="left_txt2" align="left">
					</td>
				</tr>
				<tr>
					<!-- userId -->
					<td width="25%" height="30" bgcolor="#f2f2f2" class="left_txt2" align="right">
						<span>用户名</span> 
						<s:text	name="admin.common.colon" />
					</td>
					<td width="5%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="userId" id="userId" cssClass="editbox4" size="22" maxlength="20" disabled="true"/> 	
						<span id="userId_msg" class="login_txt_bt"></span>
					</td>
					
					<!-- server list -->
					<td width="4%" height="30" bgcolor="#f2f2f2" class="left_txt2" align="left">
						<span>服务区</span> 
						<s:text	name="admin.common.colon" />
					</td>
					<td width="5%" height="30" bgcolor="#f2f2f2">
						<s:select list="serverList" id="gameZoneId" name="gameZoneId" listKey="id" value="gameZoneId"
							listValue="serverName" />
					</td>
					<td width="15%" height="30" bgcolor="#f2f2f2">
					</td>
				</tr>
				<tr>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2" align="right">
					</td>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2" id="userId_msg">用户名正确</td>
					
					<td width="5%" colspan="3" height="30" bgcolor="#f2f2f2" class="left_txt2" align="left">
					</td>

				</tr>
				
				<!-- amount -->				
				<tr>
					<td width="10%" height="30" bgcolor="#f2f2f2" class="left_txt2"  align="right">
						<span>金额</span>
						<s:text name="admin.common.colon" />
					</td>
					<td width="20%" height="30" bgcolor="#f2f2f2">
						<s:textfield name="amount" cssClass="editbox4" size="12"  maxlength="10" id="amount"/> 
						<span class="login_txt_bt"></span>
					</td>
					<td colspan="3" bgcolor="#f2f2f2"></td>
				</tr>

				<tr>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2" align="right">
					</td>
					<td width="5%" height="30" bgcolor="#f2f2f2" class="left_txt2" id="amount_msg">充值的金额正确</td>
					
					<td width="5%" height="30" colspan="3" bgcolor="#f2f2f2" class="left_txt2" align="left">
					</td>
	
				</tr>
				
				<tr>
					<td colspan="1"  bgcolor="#f2f2f2"></td>					
					<td colspan="1" height="30" width="20%" bgcolor="#f2f2f2" align="left" class="left_txt2">
						<input type="button" value="充值测试" id="testRecharge"/>
					</td>
					<td colspan="3" height="30" bgcolor="#f2f2f2" align="left" class="left_txt2">
						<span id="recharge_msg"></span>
					</td>
				</tr>
				<tr height="30">
					
				</tr>

			</tbody>
		</table>
	
</body>
	<script src="<%=path %>/static/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
$(function(){	
	$('#testRecharge').unbind('click', rechargeButtonClickUnbind).bind('click', rechargeButtonClick);
	
	$('#amount').blur(function(){
		var amount = $('#amount').val();
		if(checkAmount(amount)){
			$('#amount_msg').empty().text('充值的金额正确');
		}else {
			$('#amount_msg').empty().text('请输入10元以上的充值金额');
		}
	});
});

//充值按钮点击函数
var rechargeButtonClick = function() {
	$('#recharge_msg').empty();
	$('#testRecharge').unbind('click', rechargeButtonClick).bind('click', rechargeButtonClickUnbind);
	var user_id = $('#userId').val();
	var gamezone_id = $('#gameZoneId option:selected').val();
	var amount = $('#amount').val();
	
	if(checkRecharge(amount) === 0) {
		recharge(user_id, gamezone_id, amount);
	}else{
		$('#testRecharge').unbind('click', rechargeButtonClickUnbind).bind('click', rechargeButtonClick);
	}
}

//充值按钮点击功能解绑函数
var rechargeButtonClickUnbind = function() {
	return false;
}

//充值提交函数
var recharge = function(user_id, gamezone_id, amount){
	var url = '<%=path %>/testSubmit/recharge.action';
	$.ajax({
        type:"get",
        url:url,
        async: true,
        timeout: 5000,
        data:{
        	userId:user_id,
        	gameZoneId:gamezone_id,
        	amount:amount
        },
        beforeSend:function (XMLHttpRequest, textStatus) {
			$('#recharge_msg').empty().text('正在充值……');
        },
        success:function (msg) {
            if(msg.result == 'true'){
            	$('#recharge_msg').empty().text('充值成功');
            	if(window.confirm('充值成功！\n\n游戏：神曲    服务区：' + $('#gameZoneId option:selected').text() + '  \n账号：' + msg.userId + '  金额：' + msg.amount + '元\n\n是否再次测试？')){
            		window.location.href = window.location.href;
            	}
            }else{
            	$('#recharge_msg').empty().text('充值失败');
            }
        },
        error:function() {
        	$('#recharge_msg').empty().text('充值失败');
        },
        complete:function() {
        	$('#testRecharge').unbind('click', rechargeButtonClickUnbind).bind('click', rechargeButtonClick);
        }
    });
	return false;
}

//验证金额
var checkAmount = function(amount) {
	var result = false;
	if($.isNumeric(amount) && amount >= 10) {
		result = true;
	}
	return result;
}

//充值提交验证
var checkRecharge = function(amount){
	var isLegal = 0;
	if(!checkAmount(amount)) {
		isLegal += 1;
	}
	return isLegal;
}

</script>

</html>
