var sizeFlag=getRequestParms('site');//判断URL是否有site
//注册时账号框焦点离开函数
function userNameOnBlur(){
	var name = $("#userName");
	var nameVal = $("#userName").val();
	var flag = uidOnBlur(name.val(), {func: registBoxCheckReturnOfCheckUsername});
	
}

//账号验证方法返回值判断函数
var registBoxCheckReturnOfCheckUsername = function(flag) {
	if(flag === 1) {
//		name.addClass("g-ipt-err");
		$("#userNameTip").removeClass('txt-succ').addClass('txt-err');
	}else if(flag === 0){
		$("#userNameTip").removeClass('txt-err').addClass('txt-succ');
	}
	$("#userNameTip").html(regUserMsg).show();
}

//注册时验证首次输入的密码
function password1OnBlur(){
	var pwd = $('#password1');
	var flag = pwdOnBlur(pwd.val());
	if(flag === 1) {
		pwd.addClass("g-ipt-err");
		$("#password1Tip").removeClass('txt-succ').addClass('txt-err');
	}else if(flag === 0){
		$("#password1Tip").removeClass('txt-err').addClass('txt-succ');
	}
	$("#password1Tip").text(regFirstPwdMsg);
}


//注册时验证再次输入的密码
function password2OnBlur(){
	var pwd = $('#password1');
	var confirmPwd = $("#password2");
	var flag = confirmPwdOnBlur(pwd.val(), confirmPwd.val());
	if(flag === 1) {
		confirmPwd.addClass("g-ipt-err");
		$("#password2Tip").removeClass('txt-succ').addClass('txt-err');
	}else if(flag === 0){
		$("#password2Tip").removeClass('txt-err').addClass('txt-succ');
	}
	$("#password2Tip").text(regSecondPwdMsg);
}



//注册提交函数
function reRegistSubmit(){
	var isReg = 1;
	var name = $('#userName').val();
	var pwd = $('#password1').val();
	var confirmPwd = $('#password2').val();
	var checkverify;
	if (sizeFlag) {
		checkverify=true;
	}else{
		checkverify= checkverifyCode();
	}
	if(checkverify){
		$('.reg_message').html('注册中...');
		var flag = registSubmit(name, pwd, confirmPwd);
		if(flag == 0) {
			$('.hidden').html(bbsLoginMsg);
			$('.username').empty().html(logedName);
			$('.login_time').empty().html(getDate());
			$('.lastserver').html('进入推荐服务器');
			getNewestGameServerUrl({
				func: function(newServerUrl){
					$('.lastserver').attr('href', newServerUrl);
				}});
			$('.login-area').empty().html($('#loged').html());
			window.parent.changeHeaderAtOnce(logedName);
			window.parent.loginBBS(bbsLoginMsg);//同步论坛登录
			isReg = 0;
		}else if(flag == 1){
			$('.log_message').empty().text(regErrorMsg).show();
			document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		}else {
			$('.log_message').empty().text("连接服务器超时，请稍后再试").show();
			document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		}
		return isReg;
	}
}

$(function(){
    //验证是否登录
	var logedFlag = checkLoged({func: registBoxCheckReturnOfCheckLogin});

	//判断是否要显示验证码
	if (sizeFlag) {
		$('#verifyCodeTR').hide();
	}else{
		$('#verifyCodeTR').show();
	}
	getNewestGameServerUrl({
		func: function(gameZoneUrl) {
			$("#submit").attr("href", gameZoneUrl);
		}
	});
	focusInput('focusInput', 'normalInput'); 
	$("#userName").focus();
	$('#userName').blur(userNameOnBlur);
	$('#password1').blur(password1OnBlur);
	$('#password2').blur(password2OnBlur);
		
	//退出登陆
	registBoxBindLogoutButton();
	
	//注册
	$('.submit').click(function(){
		$('.log_message').html("注册中...");
		var isReg = reRegistSubmit();
		if(isReg == 0) {
			var canLogGame = enterGame($(this).attr('href'));
			if(canLogGame === 0) {
				return true;
			}else {
				return false;
			}
		}else{
			document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
			return false;
		}
	});
	
});


//登陆最近游戏服务器
$(".lastserver").live('click', function(){
	var gameUrl = $('.lastserver').attr('href');
	if(gameUrl != "#" && gameUrl != "" && gameUrl != null) {
		var flag = enterGame(gameUrl);
		if(flag === 0) {
			return true;
		}else {
			return false;
		}
	} else {
		return false;
	}
});	

//已登录验证函数返回值判断函数
var registBoxCheckReturnOfCheckLogin = function(logedFlag) {
	if(logedFlag === 0) {
		$('.username').html(logedName);
		$('.login_time').html(getDate());
		$('.lastserver').attr('href', lastServerUrl);
		$('.lastserver').html(lastServerName);
		$('.login-area').html($('#loged').html());
		return;
	}else{
  	    var srcBefore= $("#registBtn").attr("src");
  		var srcHover=srcBefore.replace("tc_quick_regist.jpg","tc_quick_regist_on.jpg");
  		 $("#registBtn").hover(
  				 function () {
  					 $("#registBtn").attr("src",srcHover);
  				 },
  				 function () {
  					 $("#registBtn").attr("src",srcBefore);
  				 }
  		);
	}
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


//退出按钮点击执行函数
var boxRegistClickLogoutButton = function(){
	$('.logout').unbind('click', boxRegistClickLogoutButton).bind('click', boxRegistClickLogoutButtonInvalid);
	var flag = logout({func1: registBoxCheckReturnOfLogout, func2: registBoxBindLogoutButton}, {func: registBoxBindLogoutButton});

	return false;
}

//退出按钮点击无效函数
var boxRegistClickLogoutButtonInvalid = function(){
	
	return false;
}

//退出登录方法返回值判断函数
var registBoxCheckReturnOfLogout = function(flag) {
	if(flag === 0) {
		window.parent.loginBBS(bbsLoginMsg);
		window.parent.changeHeaderWhenLogout();
		window.location.href=window.location.href;
		
	}
}

//退出登录按钮绑定函数
var registBoxBindLogoutButton = function() {
	$('.logout').die('click', boxRegistClickLogoutButtonInvalid).live('click', boxRegistClickLogoutButton);
}

function focusInput(focusClass, normalClass) { 
	var elements = document.getElementsByTagName("input");  
	for (var i=0; i < elements.length; i++) {    
		if (elements[i].type != "button" && elements[i].type != "submit" && elements[i].type != "reset") {    
			elements[i].onfocus = function() {
				this.className = focusClass;
			};      
			elements[i].onblur = function() { 
				this.className = normalClass||''; 
			};     
		}    
	} 
} 
function trnsferUrl(id)
{     
	  if(document.all){
          document.getElementById(id).click();   
        }else { 
       	  var evt = document.createEvent("MouseEvents"); 
          evt.initEvent("click",true,true); 
          document.getElementById(id).dispatchEvent(evt); 
     	} 
   // document.getElementById(id).click();
   return false;  
} 

$('#password2').keyup(function(e){
	var keyPressed = e?e.keyCode:window.event.keyCode;
    if (keyPressed === 13) {
    	//$('.submit').click();
    	trnsferUrl("submit");
    }		
});
$('#verifyCode').keyup(function(e){
	var keyPressed = e?e.keyCode:window.event.keyCode;
    if (keyPressed === 13) {
    	//$('.submit').click();
    	trnsferUrl("submit");
    }		
});
function checkverifyCode(){
			var  verifyCode =$("#verifyCode")[0];
			var result=true;
			if(verifyCode.value == "" || verifyCode.value == null){
				$("#verifyCode").addClass("g-ipt-err");
				$("#verifyCodeTip").removeClass('txt-succ').addClass('txt-err');
				$("#verifyCodeTip").text('请输入验证码');			
				document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());			
				result = false;
			}else{
				if(verifyCode.value.length != 4){
					$("#verifyCode").addClass("g-ipt-err");
					$("#verifyCodeTip").removeClass('txt-succ').addClass('txt-err');
					$("#verifyCodeTip").text('验证码位数不够');
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
							$("#verifyCode").addClass("g-ipt-err");
							$("#verifyCodeTip").removeClass('txt-succ').addClass('txt-err');
							$("#verifyCodeTip").text('验证码错误');
							document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
							result = false;
						}
					}			
					});
					
				}
			}
			return result;
		}
function getRequestParms(paraName){ 
	var reg= new RegExp("(^|&)"+paraName+"=([^&]*)(&|$)");   
	var r=window.parent.location.search.substr(1).match(reg);   //父容器URL
	if(r!=null) return unescape(r[2]);return null;   
} 