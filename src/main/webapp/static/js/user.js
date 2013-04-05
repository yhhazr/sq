var regUserVads = 0;//0.验证通过，1.为空，2不符（长度，组成），3.重复字，4.非法字符
var regUserMsg = "该用户名已注册";
var regFirstPwdMsg = "";
var regSecondPwdMsg = "";
var regValidate = 1;//0.验证通过，1.未通过
var logValidate = 1;//0.验证通过，1.未通过 

//登录相关
var logedName = "";
var bbsLoginMsg = "";
var lastServerUrl = "#";
var lastGameZoneId = "";
var lastServerName = "进入推荐服务器";
var serverStop = 0;

//注册相关
var regErrorMsg = "注册失败，请重新注册。";
var logErrorMsg = "登录失败，请重新登录";

var username_right = 0;
var first_pwd_right = 0;
var confirm_pwd_right = 0;
var captcha_right = 0;


//验证是否登录函数  0.已登录，1.未登录
function checkLoged(successFunc) {	
	var flag = 1;
	var state = null;
    var actionUrl=SERVER + "/checkLogin.action?timestamp=a"+new Date().getTime();
	var logInfo = $.cookie('VVNFUklORk8%3D');
	if(logInfo != null && logInfo != "") {
		$.ajax({
			   type: "get",
			   dataType: "json",
			   url: actionUrl,
			   async: true,
			   timeout: 5000,
			   success:function(msg){
				   if (msg.result == 'true') {
					   bbsLoginMsg = msg.bbsData;
					   state = msg.state;
					   logedName = msg.outName;

					   if(msg.lastGameZoneId != 0 && msg.lastGameZoneId != "" && msg.lastGameZoneId != null) {
						   lastServerName = msg.lastGameZoneName;
						   lastGameZoneId = msg.lastGameZoneId;
						   lastServerUrl = LOGINGAME + msg.lastGameZoneId + '&timestamp=' + Date.parse(new Date());
						   //-2 == stop
							if(state == '-2') {
//								lastServerUrl = "javascript:alert('服务器正在维护，请进入其他服务器进行游戏。');";
								serverStop = 1;
							} 
					   }else {
						   lastServerName = "进入推荐服务器";
						   getNewestGameServerUrl({
							   func: function(gameUrl) {
								   newestGameServerUrl = gameUrl;
								   lastServerUrl = gameUrl;
							   }
						   });
					   }
					   flag = 0;
					   
					   for(var func in successFunc) {
						   successFunc[func](flag, lastGameZoneId);
					   }
					   
				   }
			   }
		});
	}
	return flag;
}



//注册提交函数(同步)
function registSubmit(name, pwd, confirmPwd) {
	var flag = 1;
	var canRegist = username_right + first_pwd_right + confirm_pwd_right + captcha_right;
    if(document.getElementById('checkbox').checked != true) {
		$('.reg_message').hide();
		flag = 5;
		alert('您还没有选择同意并接受用户注册服务协议');		
	}else if(canRegist === 7) {	
			var siteId = getSite();
			$.ajax({
				   type: "post",
				   dataType: "json",
				   url: SERVER + "/registrationSubmit.action",
				   async: false,	
				   timeout: 5000,
				   data: {
					   	inName: name,
						password1: pwd,
						password2: confirmPwd,
						site: siteId
				   },
				   success:function(msg) {
					   if(msg.result == 'false') {
						   regErrorMsg = msg.respMsg;
						   flag = 1;
						} else if(msg.result == 'true') {				
							bbsLoginMsg = msg.bbsData;
							logedName = msg.outName;
							flag = 0;
						} else {
							flag = 2;
						}
				   	},
				   error:function(msg){
					   regErrorMsg = "连接服务器超时，请重新再试。";
					   flag = 2;
			        }
			});
	}
	return flag;
}

//登录提交前验证函数
function loginAdvs(name, pwd){
	var flag = true;
	if(name == null || name == ""){
		flag = false;
	}
	if(pwd == null || pwd == "") {
		flag = false;
	}
	return flag;
}


//登录提交函数 0.登录成功，1.用户名或密码错误，2.服务器错误 3.连续登陆错误超过3次
function loginSumbit(name, pwd, successFunc){
	var flag = 1;
	if(loginAdvs(name, pwd)) {
		$.ajax({
			   type: "post",
			   dataType: "json",
			   url: SERVER + "/loginSubmit.action",
			   async: true,
			   timeout: 5000,
			   data: {
					inName: name,
					password1: pwd
			   },
			   success:function(msg) {
				    logErrorMsg = msg.respMsg;
					if(msg.result == 'false') {
						if(msg.showLoginVerifyFlag ==true){
							flag = 3;
						}else{
							flag = 1;
						}
					} else if(msg.result == 'true') {
						bbsLoginMsg = msg.bbsData;
						logedName = msg.outName;
						state = msg.state;
						if(msg.lastGameZoneId != 0 && msg.lastGameZoneName != null && msg.lastGameZoneName != "") {
							lastServerName = msg.lastGameZoneName;
							lastGameZoneId = msg.lastGameZoneId;
							lastServerUrl = LOGINGAME + msg.lastGameZoneId + '&timestamp=' + Date.parse(new Date());							
							if(state == '-2') {
//								lastServerUrl = "javascript:alert('服务器正在维护，请进入其他服务器进行游戏。');";
								serverStop = 1;
							}
						}else {
						   lastServerName = "进入推荐服务器";
						   getNewestGameServerUrl({
							   func: function(gameUrl) {
								   newestGameServerUrl = gameUrl;
								   lastServerUrl = gameUrl;
							   }
						   });
						}
						flag = 0;
					}else {
						flag = 2;
					}
					for(var func in successFunc) {
						successFunc[func](flag, lastGameZoneId);
					}
			   },
			   error: function() {
				    flag = 2;
				    logErrorMsg = '连接服务器超时，请稍后再试。';
					for(var func in successFunc) {
						successFunc[func](flag, lastGameZoneId);
					}
			   }
		});
	}else {
		logErrorMsg = '请输入用户名和密码';
		for(var func in successFunc) {
			successFunc[func](flag);
		}
	}
	return flag;
}

//注册时账号框焦点离开函数
function uidOnBlur(name, functions){
	var flag = 1;
	regUserVads = 0;
	if(!regLetterFirst.test(name)) {
		regUserVads = 1;
//		regUserMsg = "账号不符合规范 [<a style='color:#fff;vertical-align: middle;cursor: pointer;' onclick='alert(\"字母开头；只能包含数字和字母；长度6-20.\");'>?</a>] ";
		regUserMsg = "字母开头，6-20个字母数字";
	}else if(regRepeat.test(name)){
		regUserMsg = "该用户名已注册";		
	}else if(!checkKeyword(name)){
		regUserMsg = "该用户名已注册";
	}else{
		var url = SERVER + "/checkUserName.action";
		$.ajax({
			   type: "get",
			   dataType: "json",
			   url: url,
			   async: true,
			   timeout: 5000,
			   data: {
					inName: name
			   },
			   success:function(msg) {
				   if(msg.result == "false"){
						regUserMsg = "该用户名已注册";
					}else{
						flag = 0;
						regUserMsg = "该账号可以使用";
						username_right = 1;
						
					}
				   for(var func in functions) {
						functions[func](flag);
				   }
			   }
		});
	}
	for(var func in functions) {
		functions[func](flag);
	}
	
	return flag;
}

//注册时首次输入密码焦点离开函数
function pwdOnBlur(pwd){
	var flag = 1;
	if(!pwd){
		regFirstPwdMsg = "请输入密码";
	}else if(pwd.length > 20 || pwd.length < 6){
		regFirstPwdMsg = "密码为6-20位长度";
	}else{
		regFirstPwdMsg = "密码正确";
		flag = 0;
		first_pwd_right = 2;
	}
	return flag;
}	

//注册时再次输入密码焦点离开函数
function confirmPwdOnBlur(pwd, confirmPwd){
	var regSecondPwdVads = 1;
	if(!confirmPwd){
		regSecondPwdMsg = "请输入确认密码";
	}else if(confirmPwd.length > 20 || confirmPwd.length < 6){	
		regSecondPwdMsg = "密码为6-20位长度";
	}else if(confirmPwd != pwd){	
		regSecondPwdMsg = "两次密码不一致";
	}else if(pwd == confirmPwd){
		regSecondPwdMsg = "密码正确";
		regSecondPwdVads = 0;
		confirm_pwd_right = 4;
	}
	return regSecondPwdVads;
}

//退出登录
function logout(successFunc, errorFunc){
	var isLogout = 1;
	$.cookie('VVNFUklORk8%3D', null, { path: '/',domain: COOKIE_NAME });
	$.ajax({
		   type: "get",
		   dataType: "json",
		   url: SERVER + "/indexLogout.action?timestamp=a" + new Date().getTime(),
		   async: true,
		   timeout: 5000,
		   success:function(msg) {
			   if(msg.result == 'true') {
				   bbsLoginMsg = msg.bbsData;
				   isLogout = 0;
				   for(var func in successFunc) {
					   successFunc[func](isLogout);
				   }
			   }
		   },
		   error:function() {
			   for(var func in errorFunc) {
				   errorFunc[func]();
			   }			   
		   }
	});
	return isLogout;
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
//刷新验证码
function refleshVerifyCode(id){
	document.getElementById(id).src=SERVER+'/captcha.do?'+(new Date().getTime());
}


