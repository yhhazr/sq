var loginVerifyFlag=false;
$(function(){
	focusInput('focusInput', 'normalInput'); 
	//文本框取得焦点
	$("#userName").focus(); 
	//验证是否登录
	var logedFlag = checkLoged({func: loginBoxCheckReturnOfCheckLogin});
		
	$('#submit').unbind('click', boxClickLoginButtonInvalid).bind('click', boxClickLoginButton);
		
	$('#verifyCode').keyup(function(e){
		var keyPressed = e?e.keyCode:window.event.keyCode;
	    if (keyPressed == 13) {
	    	$('#submit').click();
	    }		
	});

	$('#password1').keyup(function(e){
		var keyPressed = e?e.keyCode:window.event.keyCode;
	    if (keyPressed == 13) {
	    	$('#submit').click();
	    }		
	});
	//登陆最近游戏服务器
	$(".lastserver").live('click', function(){
		var gameUrl = $('.lastserver').attr('href');
		if(gameUrl != "#" && gameUrl != "" && gameUrl != null) {
			var flag = enterGame(gameUrl);
			if(flag == 0) {
				return true;
			}else {
				return false;
			}
		} else {
			return false;
		}
	});
	
	//退出登陆
	loginBoxBindLogoutButton();
	
});

//登录按钮点击执行函数
var boxClickLoginButton = function() {
	$('.log_message').empty().text("登录中...").show();
	$('#submit').unbind('click', boxClickLoginButton).bind('click', boxClickLoginButtonInvalid);
	var name = $('#userName').val();
	var pwd = $('#password1').val();
	var checkverify=checkverifyCode();
	if(checkverify){
		var flag = loginSumbit(name, pwd, {func: boxCheckReturnOfLogin});
	}else{
		$('#submit').unbind('click', boxClickLoginButtonInvalid).bind('click', boxClickLoginButton);
	}
	return false;
}

//登录按钮点击无效函数
var boxClickLoginButtonInvalid = function() {
	return false;
}

//退出按钮点击执行函数
var boxClickLogoutButton = function() {
	$('.logout').unbind('click', boxClickLogoutButton).bind('click', boxClickLogoutButtonInvalid);
	var flag = logout({func1: loginBoxCheckReturnOfLogout, func2: loginBoxBindLogoutButton}, {func: loginBoxBindLogoutButton});
	return false;
}

//退出按钮点击无效函数
var boxClickLogoutButtonInvalid = function() {
	return false;
}


//已登录验证方法返回值判断函数
var loginBoxCheckReturnOfCheckLogin = function(logedFlag) {
	if(logedFlag === 0) {
		$('.username').html(logedName);
		$('.login_time').html(getDate());
		$('.lastserver').attr('href', lastServerUrl);
		$('.lastserver').html(lastServerName);
		$('.login-area').html($('#loged').html());
		return;
	}else{
		var srcBefore= $("#loginBtn").attr("src");
		var srcHover=srcBefore.replace("login_btn.jpg","login_btn_on.jpg");
		$("#loginBtn").hover(
				function () {
					$("#loginBtn").attr("src",srcHover);
				},
				function () {
					$("#loginBtn").attr("src",srcBefore);
				}
		);
	}
}

//退出登录方法返回值判断函数
var loginBoxCheckReturnOfLogout = function(flag) {
	if(flag === 0) {
		window.parent.loginBBS(bbsLoginMsg);
		window.parent.changeHeaderWhenLogout();
		window.location.href=window.location.href;
	}
}

//退出按钮绑定函数
var loginBoxBindLogoutButton = function() {
	$('.logout').die('click', boxClickLogoutButtonInvalid).live('click', boxClickLogoutButton);
}

//登录方法返回值判断函数
var boxCheckReturnOfLogin = function(flag) {
	$('#submit').unbind('click', boxClickLoginButtonInvalid).bind('click', boxClickLoginButton);
	if(flag === 0) {
		$('.username').html(logedName);
		$('.login_time').html(getDate());
		$('.lastserver').html(lastServerName);
		$('.lastserver').attr('href', lastServerUrl);
		$('.login-area').html($('#loged').html());
		$('.hidden').html(bbsLoginMsg);
		window.parent.changeHeaderAtOnce(logedName);
	}else if(flag === 1) {
		document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
	}else if(flag === 3) {
		loginVerifyFlag=true;
		document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
	}else {
		document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
	}
	$('.log_message').empty().text(logErrorMsg).show();
} 

			
//获得当前时间
function getDate(){
	var date = new Date(); 
	var now = "";
	now = date.getFullYear()+"-";
	now = now + (date.getMonth()+1)+"-";
	now = now + date.getDate()+" ";
	now = now + date.getHours()+":";
	now = now + date.getMinutes()+":";
	now = now + date.getSeconds()+"";
	return now;
}

function focusInput(focusClass, normalClass) { 
	var elements = document.getElementsByTagName("input");  
	for (var i=0; i < elements.length; i++) {    
		if (elements[i].type != "button" && elements[i].type != "submit" && elements[i].type != "reset") {    
			elements[i].onfocus = function() { this.className = focusClass; };      
			elements[i].onblur = function() { this.className = normalClass||''; };     
		}    
	} 
}
function checkverifyCode(){
	var result=true;
	if(loginVerifyFlag){
			$("#verifyCodeLi").show();
			var  verifyCode =$("#verifyCode")[0];
			if(verifyCode.value == "" || verifyCode.value == null){			
				$('.log_message').html('请输入验证码');
				document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
				result = false;
			}else{
				if(verifyCode.value.length != 4){
					$('.log_message').html('验证码位数不够');
					document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
					result = false;
				}else{
					$.ajax({
					type: "get",
					url: SERVER+"/checkVerifyCode.action?verifyCode="+verifyCode.value,
					async: false,
					dataType: "json",
					success: function(data){
						if(data.result == "false"){
							$('.log_message').html('验证码错误');
							document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
							result = false;
						}
					}			
					});
					
				}
			}
	}
			return result;
		}