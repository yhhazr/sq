/*
 * name of func/param: sl == serverList
 * */

$(function(){
	$('.floatDiv').remove();
	var gameUrl = '#';
	
	getNewestGameServerUrl({
		func: function(slnewGameServerUrl) {
//			$("#btntoreg").attr("href", slnewGameServerUrl);
			gameUrl = slnewGameServerUrl;
			actualGameUrl=baseGameUrl+$.cookie('ngid');
		}
	});
	//验证登陆
	var logedFlag = checkLoged({func: serverListCheckLogin});
	
	$('#reg_userName').blur(userNameOnBlur);
	$('#reg_password1').blur(password1OnBlur);
	$('#reg_password2').blur(password2OnBlur);
	$('#reg_verifyCode').blur(verifyCodeOnBlur);
    
	
	
	
	//登录提交
	$('#btntoLogin').unbind('click', slClickLoginButtonInvalid).bind('click', slClickLoginButton);
	
	//退出登陆	
	slBindLogoutButton();
	
	//选服页登陆游戏
	$(".server,.serverBox_a").click(function(){
		if(!$('#tabLogined').is(":hidden")) {
			
			var canLogGame = enterGame($(this).attr('id'));
			if(canLogGame == 0) {
				window.location.href=baseGameUrl+$(this).attr('id') + '&timestamp=' + Date.parse(new Date());
			}
		}else {

			$(".topLogin").click();
		}
		return false;
	});	
	

	//登陆最近游戏服务器
	$("#lastserverBtn").live('click', function(){
		if($('#lastserver').attr('href') != "#") {
			var gameUrl = $(this).attr('href');
			var actualGameUrl=$(this).attr("actualGameUrl");
			var canLogGame = enterGame($(this).attr('serverId'));
			if(canLogGame == 0) {
				window.location.href=actualGameUrl;
			}
		}
		return false;
	});	
	
	//快速进入游戏
	$('#enter_game-btn').click(function(){
		var gameUrl = "#";
		if($('#loged_hidden').is(':hidden')) {
			$(".topLogin").click();
		}else{
			var serverOrderId = $('#serverId_content').val();
			var ex = /^\d+$/;
			if(ex.test(serverOrderId)) {
				if(serverOrderId <= idList.length-1){
					var canLogGame = enterGame(idList[serverOrderId-1]);
					if(canLogGame == 0) {
						window.location.href=baseGameUrl+idList[serverOrderId-1] + '&timestamp=' + Date.parse(new Date());
					}
				}
				else{
					alert('服务器尚未开启。');
				}											
			}else{
				alert('请输入要进入的服务器区号。');
			}
		}
		
		return false;
	});	


	//注册提交
	$('#btntoreg').live('click', function(){
		var name = $('#reg_userName').val();
		var pwd = $('#reg_password1').val();
		var confirmPwd = $('#reg_password2').val();
		var checkVerifyCodeFlag=checkVerifyCode("reg_verifyCode","reg_verifyCodeImg","tab2_4");
		if($("#checkbox").attr("checked")!="checked"){
			alert("您还没有选择同意并接受用户注册协议书");
			return false;
		}
		if(checkVerifyCodeFlag){
			$('.reg_message').empty().text('注册中...').show();
			var flag = registSubmit(name, pwd, confirmPwd);
			if(flag == 0) {
				$('#hidden').html(bbsLoginMsg);
				var canLogGame = enterGame($.cookie('ngid'));
				if(canLogGame == 0) {
					setTimeout(function(){
						window.location.href=baseGameUrl+$.cookie('ngid');	
					}, 1500);
					return false;
				}else{
					$('#loged_name').html(logedName);
					$('#lastserver').html(newestRecomendServerName);
					$('#lastserverBtn .hotIco9').removeClass('hotIco9').addClass('hotIco');
					$('#lastserverBtn .degree').empty().text('100°');
					$('#lastserverBtn .red:last').empty().text('火爆开启');
					getNewestGameServerUrl({
						func: function(gameUrl) {
							$('#lastserver').attr('href', baseGameUrl+$.cookie('ngid'));		
						}
					});
					$('#tabReg').hide();
					$('#tabLogin').hide();
					$('#tabLogined').show();   
					$('.t2').unbind('click');	
					return false;
				}
			}else if(flag == 1){
				document.getElementById("reg_verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
				$('.reg_message').empty().text(regErrorMsg).show();
			//flag == 5 没有勾选同意用户注册服务协议
			}else if(flag == 5) {
				
			}
			else {
				document.getElementById("reg_verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
				$('.reg_message').empty().text(regErrorMsg).show();
			}
		}
		var getEnterInfoFlag=getGameEnterInfo(POSITION9);//记录注册提交
		return false;
	});
	
	//点击收藏夹
	$('.xf-menu_03').click(function(){
		bookmarkit();
		return false;
	});
	
	//回车键登录函数
	$('#log_verifyCode').keyup(function(e){
		var keyPressed = e?e.keyCode:window.event.keyCode;
	    if (keyPressed == 13) {
//	    	$('#btntoLogin').unbind('click', slClickLoginButtonInvalid).bind('click', slClickLoginButton);
	    	$('#btntoLogin').click();
	    }		
	});
	$('#log_password').keyup(function(e){
		var keyPressed = e?e.keyCode:window.event.keyCode;
	    if (keyPressed == 13) {
//	    	$('#btntoLogin').unbind('click', slClickLoginButtonInvalid).bind('click', slClickLoginButton);
	    	$('#btntoLogin').click();
	    }		
	});
	//回车键触发注册提交函数
	$('#reg_verifyCode').keyup(function(e){
		var keyPressed = e?e.keyCode:window.event.keyCode;
	    if (keyPressed == 13) {
	    	$('#btntoreg').click();
	    }		
	});
	
	//页面载入检查顶端登录是否成功，改变页面
    // $.get("http://sq.7road.com/game7road/checkLogin.action",function(data){
    //     if(data.result==="true"){
    //        serverListCheckLogin(logedFlag, lastGameZoneId);
    //     }
    // });
	//监听顶端退出
	$("a.indexLogout").bind("click",function(){
		$.get("http://sq.7road.com/game7road/indexLogout.action",function(data){
			if(data.result==="true"){
				$(".loginInfo,.topLoginInfo").show();
				$(".logined,.topLoginedInfo").hide();
				$(".popLock,.popLogin,.popLogined,.popReg,error-msg").hide();
				serverListCheckReturnOfLogout(0);//参数0 表示顶端退出成功
				$("#tabLogined").hide();
				$("#tabLogin").show();
			}else{
				alert("请稍后再试");
			}
			
		})
	})
	
	//监听弹窗即时登录
	
	$("#entergame").bind("click",function(){
		var topUserVal = $("#toplog_userName").val();
		var topPassrVal = $("#toplog_password").val();
		if(topUserVal !="" || topPassrVal !=""){
           $.get("http://sq.7road.com/game7road/loginSubmit.action",{"inName":topUserVal,"password1":topPassrVal},function(data){
                if(data.result==="true"){
					$(".loginInfo,.topLoginInfo").hide();
					$(".logined,.topLoginedInfo").show();
					$(".popLock,.popLogin,.popLogined,.popReg,error-msg").hide();
                    serverListCheckReturnOfLogin(0,data.lastGameZoneId);
					$(".topLoginedInfo .idx_name").text("欢迎您,"+data.outName);
					$('#loged_name').html(data.outName);
                }else{
					$(".error-msg").text("用户名或密码错误。");	
				}
            })
		}else{
			alert("账号或者密码不能为空")
		}	
	});
	$("#toplog_password").bind("keydown",function(event){
		if (event.keyCode == '13') {
			event.preventDefault();
			$("#entergame").click();
		}
	})
	
}); 









//登录按钮点击函数
var slClickLoginButton = function() {
	$('#btntoLogin').unbind('click', slClickLoginButton).bind('click', slClickLoginButtonInvalid);
	var name = $('#log_userName').val();
	var pwd = $('#log_password').val();
	var checkVerifyCodeFlag=checkVerifyCode("log_verifyCode","log_verifyCodeImg","tab1_3");
	if(checkVerifyCodeFlag){
		$('.log_message').empty().text('登录中...').show();
		var flag = loginSumbit(name, pwd, {func: serverListCheckReturnOfLogin});
	}else {
		$('#btntoLogin').unbind('click', slClickLoginButtonInvalid).bind('click', slClickLoginButton);
	}
	
	var getEnterInfoFlag=getGameEnterInfo(POSITION8);//记录登陆提交

	return false;
}

//登录中登录按钮无效函数
var slClickLoginButtonInvalid = function() {
	
	return false;
}

//登录返回值判断函数
var serverListCheckReturnOfLogin = function(flag, lastGameZoneId) {
	$('#btntoLogin').unbind('click', slClickLoginButtonInvalid).bind('click', slClickLoginButton);
	if(flag == 0) {
		$(".topLoginedInfo").show();
		$(".topLoginedInfo .idx_name").text("欢迎您,"+logedName);
		$(".topLoginInfo").hide();
		
		
		$('#hidden').html(bbsLoginMsg);
		$('#loged_name').html(logedName);
		$('#lastserver').html(lastServerName);
		$('#lastserverBtn').attr({'href':lastServerUrl,"serverId":lastGameZoneId,"actualGameUrl":baseGameUrl+lastGameZoneId});		
		if(serverStop == 1){
			$('#lastserverBtn').removeClass('server').addClass('maintain');
			$('#lastserverBtn .red').empty().text('正在维护中').css('color', '#2C2C2C');
		}else if(lastServerName == '进入推荐服务器' || $('.recommendServer .server:first').attr('id') == lastGameZoneId) {
			if(lastServerName == '进入推荐服务器') {
				$('#lastserver').html(newestRecomendServerName);
			}
			$('#lastserverBtn .hotIco9').removeClass('hotIco9').addClass('hotIco');
			$('#lastserverBtn .degree').empty().text('100°');
			$('#lastserverBtn .red:last').empty().text('火爆开启');
		} 
		$('#tabReg').hide();
		$('#tabLogin').hide();
		$('#tabLogined').show();   
		$('.t2').unbind('click');
		//changeHeaderAtOnce(logedName);
	}else if(flag == 3) {
		loginVerifyFlag=true;
		$('.log_message').html(logErrorMsg).show();
		document.getElementById("log_verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
	}else if(flag == 1) {
		$('.log_message').html(logErrorMsg).show();
		document.getElementById("log_verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
	}else {
		document.getElementById("log_verifyCodeImg").src=SERVER+'/captcha.do?'+(new Date().getTime());
		$('.log_message').empty().text(logErrorMsg).show();
	}
}

var loginVerifyFlag=false;
//注册时账号框焦点离开函数
function userNameOnBlur(){
	var name = $("#reg_userName");
	var flag = uidOnBlur(name.val(), {func: function(flag){
			if(flag == 0) {
				$("#tab2_1").css('color', '#65EC60');
			}else{
				name.addClass("g-ipt-err");
				$("#tab2_1").css('color','#EE4D4D');
			}
			$("#tab2_1").empty().text(regUserMsg);
		}});
}


//注册时验证首次输入的密码
function password1OnBlur(){
	var pwd = $('#reg_password1');
	var flag = pwdOnBlur(pwd.val());
	if(flag == 0) {
		$("#tab2_2").css('color', '#65EC60');
	}else{
		$("#tab2_2").css('color','#EE4D4D');
		pwd.addClass("g-ipt-err");
	}
	$("#tab2_2").empty().text(regFirstPwdMsg);
}	

//注册时验证再次输入的密码
function password2OnBlur(){
	var pwd = $('#reg_password1');
	var confirmPwd = $("#reg_password2");
	var flag = confirmPwdOnBlur(pwd.val(), confirmPwd.val());
	if(flag == 0) {
		$("#tab2_3").css('color', '#65EC60');
	}else{
		$("#tab2_3").css('color','#EE4D4D');
		confirmPwd.addClass("g-ipt-err");
		
	}
	$("#tab2_3").empty().text(regSecondPwdMsg);
}
//注册时验证验证码
function  verifyCodeOnBlur(){
	checkVerifyCode("reg_verifyCode","reg_verifyCodeImg","tab2_4");
}
function  checkVerifyCode(_id,_imgID,tipsID){
	var flag = true;
	if(loginVerifyFlag){
		$("#log_verify_tr").show();
		$("#log_verify_tr2").show();
	}
	if(_id=="reg_verifyCode"){
		var verifyCode=$('#'+_id);
		var verifyImg=$('#'+_imgID);
		var tips=$('#tab2_4');
		if(!verifyCode.val())
		{
			verifyCode.addClass("g-ipt-err");
			tips.empty().text("请输入验证码").css('color','#EE4D4D');
			flag=false;
		}else if(verifyCode.val().length!=4){
			verifyCode.addClass("g-ipt-err");
			tips.empty().text("验证码位数不对").css('color','#EE4D4D');
			flag=false;
		}else{		
			$.ajax({
					type: "get",
					url: SERVER+"/checkVerifyCode.action?verifyCode="+verifyCode.val(),
					async: false,
					dataType: "json",
					success: function(data){
							if(data.result == "false"){
								verifyCode.addClass("g-ipt-err");
								tips.empty().text("验证码错误").css('color','#EE4D4D');
								verifyImg[0].src=SERVER+'/captcha.do?'+(new Date().getTime());
								flag = false;
							}else{
								verifyCode.removeClass("g-ipt-err");
								tips.empty().text("验证码正确").css('color','#65EC60');
							}
					}			
			});
		}
	}
	return flag;
}

//收藏夹
function bookmarkit(){
   if(window.sidebar && "object" == typeof( window.sidebar ) && "function" == typeof(window.sidebar.addPanel)){
       window.sidebar.addPanel( '《神曲》官方网站 -- 第七大道', HOST, '' );
    }
    else if (document.all && "object" == typeof(window.external)){
       window.external.addFavorite(HOST,'《神曲》官方网站 -- 第七大道');
    } else {
    	alert("您在使用webKit内核的浏览器，请使用 ctrl+D 添加收藏哦！");
    }
   return false;
}

//弹出层关闭时判断登录改变首页header状态 //callback
var checkHeaderAtClosed = function(){	
	var logedFlag = checkLoged({
		func1: serverListCheckLogin
	});
}; 
//弹出层进入游戏 //callback2
var enterGameonBox = function(url, data){	
	$('#hidden').html(data);
	TINY.box.hide();
	openwin(url);
};
//同步论坛登录 callback3
var loginBBS = function(data){
	$('#hidden').html(data);	
}; 

//退出登录时改变首页header状态 callback5
var changeHeaderWhenLogout = function(){
	
	$('.head_login_suc').hide();
	$('.head_login_content').show();
}

//验证登录
var serverListCheckLogin = function(logedFlag, lastGameZoneId){
	var lastGameZoneId = lastGameZoneId ? lastGameZoneId : $(".recommendServer:first a").attr("id");
	if(logedFlag == 0){//登录成功
		$(".topLoginedInfo").show();
		$(".topLoginedInfo .idx_name").text("欢迎您,"+logedName);
		$(".topLoginInfo").hide();
		
		$('#hidden').html(bbsLoginMsg);//同步论坛方面
		$('#loged_name').html(logedName);
		if(lastServerUrl != "#") {//能取到上次登录的服务器
			$('#lastserver').html(lastServerName);
			$('#lastserverBtn').attr({'href':LOGINGAME+lastGameZoneId,"serverId":lastGameZoneId,"actualGameUrl":baseGameUrl+lastGameZoneId});
			if(serverStop == 1){
				$('#lastserverBtn').removeClass('server').addClass('maintain');
				$('#lastserverBtn .red').empty().text('正在维护中').css('color', '#2C2C2C');
			}else if(lastServerName == '进入推荐服务器' || $('.recommendServer .server:first').attr('id') == lastGameZoneId) {
				if(lastServerName == '进入推荐服务器') {
					$('#lastserver').html(newestRecomendServerName);
				}
				$('#lastserverBtn .hotIco9').removeClass('hotIco9').addClass('hotIco');
				$('#lastserverBtn .degree').empty().text('100°');
				$('#lastserverBtn .red').empty().text('火爆开启');
			} 
		}else {// 没上次登录服务器就推荐最新服务器
			$('#lastserver').html(newestRecomendServerName);
			$('#lastserverBtn .hotIco9').removeClass('hotIco9').addClass('hotIco');
			$('#lastserverBtn .degree').empty().text('100°');
			$('#lastserverBtn .red:last').empty().text('火爆开启');
			//alert("无上次登陆服务器")
			//$('#lastserverBtn').attr({'href':newestGameServerUrl,"serverId":getNewestGameServerId()});
		}
		$('#tabReg').hide();
		$('#tabLogin').hide();
		$('#tabLogined').show();   
		$('.t1,.t2').unbind('click');
		//退出登陆	
		$('#login-out, .indexLogout').die('click', slClickLogoutButtonInvalid).live('click', slClickLogoutButton);
	}
}

//退出按钮执行函数
var slClickLogoutButton = function(){
	$('#login-out, .indexLogout').die('click', slClickLogoutButton).live('click', slClickLogoutButtonInvalid);
	var flag = logout({func1: slBindLogoutButton, func2: serverListCheckReturnOfLogout}, {func: slBindLogoutButton});
	return false;
}

//退出按钮点击时无效函数
var slClickLogoutButtonInvalid = function() {
	return false;
}

//退出登录方法返回值判断函数
var serverListCheckReturnOfLogout = function(flag) {
	if(flag == 0) {
		$(".topLoginInfo").show();
		$(".topLoginedInfo").hide();
		$('#hidden').html(bbsLoginMsg);
		
		
		$('#tabLogined').hide();
		$('#loged_name').empty();
		$('#tabLogin').show();
		$('.start_game').attr('href', HOST+'/serverList.html');
		
		bindTabClick();
		$('.log_message').hide();		
	}
}

//退出登录绑定函数
var slBindLogoutButton = function() {
	$('#login-out, .indexLogout').die('click', slClickLogoutButtonInvalid).live('click', slClickLogoutButton);
}


//登陆注册Tab绑定click事件
function bindTabClick(){
	$('.t1').bind('click', function(evt){
		var o = $(evt.currentTarget);
		if($('#loged_name').html() != null && $('#loged_name').html() != "") {
			o.parent().addClass('loginRegTab1').removeClass('loginRegTab2');
			$('#tabReg').hide();
			$('#tabLogin').hide();
			$('#tabLogined').show();
		}else {
			o.parent().addClass('loginRegTab1').removeClass('loginRegTab2');
			$('#tabReg').hide();
			$('#tabLogined').hide();
			$('#tabLogin').show();
			$("#log_userName").focus();
		}
	});
	$('.t2').bind('click', function(evt){
		var o = $(evt.currentTarget);
		o.parent().addClass('loginRegTab2').removeClass('loginRegTab1');
		$('#tabLogin').hide();
		$('#tabLogined').hide();
		$('#tabReg').show();
		$("#reg_userName").focus();
	});
}

function loginLeftTop(){
	var getEnterInfoFlag=getGameEnterInfo(POSITION1);//记录位置为左上角
	TINY.box.show({iframe:HOST + '/login.html',boxid:'frameless',width:474,height:335,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){checkHeaderAtClosed()}});
}
function registLeftTop(){
	var getEnterInfoFlag=getGameEnterInfo(POSITION2);//记录位置为左上角的注册
	TINY.box.show({iframe:HOST + '/regist.html',boxid:'frameless',width:474,height:378,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){checkHeaderAtClosed()}});
}



