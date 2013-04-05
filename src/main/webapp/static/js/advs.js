var loginVerifyFlag=false;
// 推荐直接进入的游戏区ID
function getNewGamezoneId(){
	var newGamezoneId=getNewestGameServerId();
	if(newGamezoneId)
	{
		return newGamezoneId;
	}else{
		//return 938;
		//return 561;
		//return 865;
		return 942;
	}
}


//登陆按钮函数
function loginHandler()
{
	var uid=$("#uid"),pwd=$("#pwd"),verifyCode=$("#verifyCode"),flag=true;
	$("#tab1_1,#tab1_2").empty();
	$(".g-ipt-err").removeClass("g-ipt-err");
	if(!uid.val())
	{
		uid.addClass("g-ipt-err");
		$("#tab1_1").text("请输入帐号").removeClass('green').addClass('red');
		//document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		flag=false;
	}
	if(!pwd.val())
	{
		$("#pwd").addClass("g-ipt-err");
		$("#tab1_2").text("请输入密码").removeClass('green').addClass('red');
		//document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		flag=false;
		
	}
//	if(loginVerifyFlag){
//		//显示验证码模块
//		$("#log_verifyTr").show();
//	if(!verifyCode.val())
//	{
//		verifyCode.addClass("g-ipt-err");
//		$("#tab1_3").text("请输入验证码").removeClass('green').addClass('red');
//			document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
//		flag=false;
//	}else if(verifyCode.val().length!=4){
//		verifyCode.addClass("g-ipt-err");
//		$("#tab1_3").text("验证码位数不对").removeClass('green').addClass('red');
//			document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
//		flag=false;
//	}else{		
//		$.ajax({
//				type: "get",
//				url: SERVER+"/checkVerifyCode.action?verifyCode="+verifyCode.val(),
//				async: false,
//				dataType: "json",
//				success: function(data){
//						if(data.result == "false"){
//							verifyCode.addClass("g-ipt-err");
//							$("#tab1_3").text("验证码错误").removeClass('green').addClass('red');
//							document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
//							flag = false;
//						}
//				}			
//		});
//		}
//	}

	if(!flag)
	{
		return;
	}
	$('#login_tip').html('正在登录...');
	$.post(SERVER + "/loginSubmit.action", 
	{
		inName: $('#uid').val(),
		password1: $('#pwd').val()
	}, 
	function(data)
	{
		if(data.result == 'false') {
		//	if(data.showLoginVerifyFlag ==true){
		//		loginVerifyFlag=true;
		//	}
			$('#login_tip').html(data.respMsg);
			//document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		} else if(data.result == 'true') {
			//getUserInfo(data);
			location = HOST + '/serverList.html?site='+getSite();
		} else {
			$('#login_tip').html("连接服务器超时，请稍后再试。");
			//document.getElementById("verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		}
	});
	return false;
}

var regRepeat = /([a-z0-9A-Z_])\1{4,}/;  //5位重复字符
var regLetterFirst = /^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,19}$/; 
var keyArray = new Array('GameMaster','gm','GM','shit','bitch','fvc','phuc','fuk','shenqu','fuck', 'FUCK', 'Fuck','admin','7road');

//注册按钮函数
function regHandler()
{
	var uid=$("#reg_uid"),pwd=$("#reg_pwd"),confirm_pwd=$("#reg_confirm_pwd"),verifyCode=$("#reg_verifyCode"),flag=true;
	$("#tab2_1,#tab2_2,#tab2_3").empty();
	$(".g-ipt-err").removeClass("g-ipt-err");

	if(pwd.val().length<6 || pwd.val().length>20){
		pwd.addClass("g-ipt-err");
		$("#tab2_2").text("密码为6-20位长度").removeClass('green').addClass('red');
		flag=false;
	}
	if(!pwd.val())
	{
		pwd.addClass("g-ipt-err");
		$("#tab2_2").text("请输入密码").removeClass('green').addClass('red');
		flag=false;
		
	}
	if(!confirm_pwd.val())
	{
		confirm_pwd.addClass("g-ipt-err");
		$("#tab2_3").text("请输入确认密码").removeClass('green').addClass('red');
		flag=false;
	}
	if(!regLetterFirst.test(uid.val())){
		uid.addClass("g-ipt-err");
		$("#tab2_1").html("字母开头，6~20个字母数字").removeClass('green').addClass('red');
		flag=false;
	}
	if(regRepeat.test(uid.val())){
		uid.addClass("g-ipt-err");
		$("#tab2_1").text("用户名已注册").removeClass('green').addClass('red');
		flag=false;
	}
	if(!checkKeyword(uid.val())){
		uid.addClass("g-ipt-err");
		$("#tab2_1").text("用户名已注册").removeClass('green').addClass('red');
		flag=false;
	}
	if(!uid.val())
	{
		uid.addClass("g-ipt-err");
		$("#tab2_1").text("请输入帐号").removeClass('green').addClass('red');
		flag=false;
	}
	if(pwd.val()!=confirm_pwd.val()){
		confirm_pwd.addClass("g-ipt-err");
		$("#tab2_3").text("两次输入密码不一致").removeClass('green').addClass('red');
		flag=false;
	}
	
	if(!$('#agreement').attr('checked')){
		$("#reg_tip").text("请先阅读协议并同意后进行操作");
		flag=false;
	}
	if(!flag)
	{
		return;
	}
	
	$('#reg_tip').html('正在注册...');
	var siteId = getSite();
	var newGamezoneId = getNewGamezoneId();
	$.ajax({
		   type: "post",
		   dataType: "json",
		   url: SERVER + "/registrationSubmit.action",
		   async: false,				   
		   data: {
			   inName: uid.val(),
				password1: pwd.val(),
				password2: confirm_pwd.val(),
				site: siteId
		   },
		   success:function(data) {
			   if(data.result == 'false') {
					$('#reg_tip').html(data.data);
				} else if(data.result == 'true') {
					$('#reg_tip').html(data.bbsData);
					var canLogGame = enterGame(LOGINGAME + newGamezoneId);
					if(canLogGame == 0) {
						setTimeout(function(){location = LOGINGAME + newGamezoneId + '&timestamp=' + Date.parse(new Date());	},1500);
						return true;
					}else {
						window.location.href=HOST;
//						return false;
					}
				} else {
					$('#reg_tip').html("连接服务器超时...");
					return false;
				}	
		   	}
		 });
	return false;
}

function logout(){
	$.cookie('VVNFUklORk8%3D', null, { path: '/',domain: COOKIE_NAME});

	$.get(SERVER + "/indexLogout.action?timestamp=a" + new Date().getTime(), {
	}, function(data){    				
		if(data.result == 'true') {
			$('#login_tip').empty();
			$('#uid, #pwd').val('');
			$('#tabLogined').hide();
			$('#login_panel').show();
		}
	});
}

//检查屏蔽关键字
function checkKeyword(str){   		
	for(i=0; i<keyArray.length; i++) {
		if(str.indexOf(keyArray[i])!=-1) {
			return false;
		}
	}
	return true;
}
//注册时账号框焦点离开函数
function uidOnBlur(){
	var uid=$("#reg_uid"),pwd=$("#reg_pwd"),confirm_pwd=$("#reg_confirm_pwd");	
	$("#tab2_1").empty();
	if(!regLetterFirst.test(uid.val())) {
		uid.addClass("g-ipt-err");
		$("#tab2_1").html("字母开头，6-20个字母数字").removeClass('green').addClass('red');
	}else if(regRepeat.test(uid.val())){
		uid.addClass("g-ipt-err");
		$("#tab2_1").text("用户名已注册").removeClass('green').addClass('red');
	}else if(!checkKeyword(uid.val())){
		uid.addClass("g-ipt-err");
		$("#tab2_1").text("用户名已注册").removeClass('green').addClass('red');
	}else{
		var url = SERVER + "/checkUserName.action?inName="+uid.val();
		$.get(url,function(data){
			if(data.result == "false"){
				//alert('该用户名已被注册');
				uid.addClass("g-ipt-err");
				$("#tab2_1").text("该用户名已被注册").removeClass('green').addClass('red');				
			}else{
				uid.removeClass("g-ipt-err");
				$("#tab2_1").text("该账号可以使用").removeClass('red').addClass('green');
			}
		});
	}
}
//登录时账号框焦点离开函数
$("#uid").blur(function(){
	var uid = $("#uid");
	var url = SERVER + "/checkUserName.action?inName="+uid.val();
		$.get(url,function(data){
			if(data.result == "true"){
				uid.addClass("g-ipt-err");
				$("#tab1_1").text("用户名不存在").removeClass('green').addClass('red');			
			}else{
				uid.removeClass("g-ipt-err");
				$("#tab1_1").text("").removeClass('red');
			}
		});
});
//注册时首次输入密码焦点离开函数
function pwdOnBlur(){
	var pwd=$("#reg_pwd");
	if(!pwd.val()){
		pwd.addClass("g-ipt-err");
		$("#tab2_2").text("请输入密码").removeClass('green').addClass('red');		
	}else if(pwd.val().length > 20 || pwd.val().length < 6){
		pwd.addClass("g-ipt-err");
		$("#tab2_2").text("密码为6-20位长度").removeClass('green').addClass('red');
	}else{
		pwd.removeClass("g-ipt-err");
		$("#tab2_2").text("密码正确").removeClass('red').addClass('green');
	}
}	

//注册时再次输入密码焦点离开函数
function confirmPwdOnBlur(){
	var pwd=$("#reg_pwd"),confirm_pwd=$("#reg_confirm_pwd");
	if(!confirm_pwd.val()){
		confirm_pwd.addClass("g-ipt-err");
		$("#tab2_3").text("请输入确认密码").removeClass('green').addClass('red');
	}else if(pwd.val() != confirm_pwd.val()){	
		confirm_pwd.addClass("g-ipt-err");	
		$("#tab2_3").text("两次密码不一致").removeClass('green').addClass('red');
	}else if(pwd.val() == confirm_pwd.val()){
		confirm_pwd.removeClass("g-ipt-err");
		$("#tab2_3").text("密码正确").removeClass('red').addClass('green');
	}	
}
$(function(){

	//验证是否登录
	var logedFlag = checkLoged({func: adCheckReturnOfCheckLoged});
	
	
//    var actionUrl=SERVER + "/checkLogin.action?timestamp=a"+new Date().getTime() ;
//    // getServerId();
//    $.get(actionUrl, function (data){                     
//        if (data.result == 'true' ) {
//        	getUserInfo(data);
//         } 
//    });
    //焦点定位在账号框
    $("#reg_uid").focus();

    $("#reg_uid").blur(function(){
    	uidOnBlur();
    });
    $("#reg_pwd").blur(function(){
    	pwdOnBlur();
    });
    $("#reg_confirm_pwd").blur(function(){
		confirmPwdOnBlur();
    });

	//登陆按钮点击事件和input回车事件
	$("#btntoLogin").click(loginHandler);
	$("#verifyCode").keydown(function(event)
	{
		if(event.keyCode==13)
		{
			loginHandler();
		}
	});
	$("#pwd").keydown(function(event)
	{
		if(event.keyCode==13)
		{
			loginHandler();
		}
	});

	//注册按钮点击事件和input回车事件
	$("#btntoReg").click(regHandler);
	$("#reg_confirm_pwd").keydown(function(event)
	{
		if(event.keyCode==13)
			{
				regHandler();
			}
	});


	
	$('#lastserver').click(function(){
		var canLogGame = enterGame($(this).attr('href'));
		if(canLogGame == 0) {
			setTimeout(function(){location = $(this).attr('href');	},1500);
			return true;
		}else {
			return false;
		}
	});



	//切换
	$(".old-login").click(function()
	{
		$("#login_panel").show();
		$("span[id^='tab']").empty();
		$("#reg_panel").hide();
		$(".panel").addClass("login").removeClass("reg");
		$("#uid").focus();				
	});
	
	$(".quick-login").click(function()
	{
		$("#login_panel").hide();
		$("span[id^='tab']").empty();
		$("#reg_uid,#reg_pwd,#reg_confirm_pwd").removeClass("g-ipt-err");
		$("#tab2_1").text("长度6-20,由字母数字组成.");
		$("#tab2_2").text("长度6-20个字符");
		$("#tab2_3").text("两次输入的密码请保持一致");
		$("#reg_panel").show();
		$(".panel").addClass("reg").removeClass("login");
		$("#reg_uid").focus();
	});
	 setInterval("flash_title()",500);
});

var adCheckReturnOfCheckLoged = function(logedFlag) {
	if(logedFlag == 0) {
		$(".panel").addClass("login").removeClass("reg");
		$('#login_tip').append(bbsLoginMsg);
		$("#login_panel, #reg_panel").hide();
		$(".xf-tab-logined").show();
		$('#username').text(logedName);
		$('#lastserver').text(lastServerName).attr('href',lastServerUrl);	
	}
}

// 标题闪动
var SHOW1="第七大道神曲_全球首创QTE战斗游戏，超震撼体验！";
var SHOW2=window.location.href;
 SHOW2=SHOW2.replace("http://","");
var i=0;
function flash_title(){
 		if(i==0) {
                $("title").text(SHOW1);
                i = 1 ;
        }
        else {
                $("title").text(SHOW2);
                i = 0 ;
        }
}