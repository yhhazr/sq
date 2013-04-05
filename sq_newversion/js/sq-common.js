var global = {
	"registerData":{},
	"serverHost":"http://sq.7road.com",
	"loginUrl":"http://sq.7road.com/game7road/loginSubmit.action",
	"logoutUrl":"http://sq.7road.com/game7road/indexLogout.action",
	"registerUrl":"http://sq.7road.com/game7road/registrationSubmit.action",
	"checkUserNameUrl":"http://sq.7road.com/game7road/checkUserName.action",
	"checkLogin":"http://sq.7road.com/game7road/checkLogin.action",//检测是否已经登陆
	"checkLoginGame":"http://sq.7road.com/game7road/onlineGame/checkLoginGame.action",//检测服务器是否能够进入
	"getLatestServerUrl":"http://sq.7road.com/game7road/onlineGame/acquireId.action",//获取最新能够登陆的区服信息
	"gameUrl":"http://account.7road.com/PlayGame2?g=1&game=S&subGame=0&z=",
	"adStatisticsUrl":"http://sq.7road.com/game7road/enterFromAd.action",
	"actualGameUrl":"http://sq.7road.com/gameStart/gameStart.html?g=1&serverId="
}

function ajaxFn(url,isAsync,param,successFn,errorFn,aditionalEvent){
	$.ajax({
		url:url,
		type:"post",
		data:param,
		async:isAsync,
		timeout:5000,
		headers:{
			"isAjaxRequest":"true"
		},
		success:function(data, textStatus, jqXHR){
			successFn(data,textStatus,jqXHR,aditionalEvent);
		},
		error:function(jqXHR, textStatus, errorThrown){
			if(errorFn){
				errorFn(jqXHR, textStatus, errorThrown);
			}
			else{
				alert("链接超时,请稍后再试");
			}	
		}
	})
}

// 登录
var login = function (uid,pwd,successFn,errorFn){
	var isAsync = true;
	ajaxFn(global.loginUrl, isAsync, {inName:uid,password1:pwd}, successFn, errorFn);
}

// 注册
var register = function (uid,pwd,confirm_pwd,successFn,errorFn){
	var isAsync = false;
	ajaxFn(global.registerUrl, isAsync, {inName:uid,password1:pwd,password2:confirm_pwd}, successFn, errorFn);
}

//检查用户是否登陆
var checkIsLogined = function (successFn,errorFn){
	var isAsync = true;
	ajaxFn(global.checkLogin, isAsync, {}, successFn, errorFn);
}

//检查用户名是否存在
var checkUserNameExisted = function (uid,successFn,errorFn){
	var isAsync = false;
	ajaxFn(global.checkUserNameUrl, isAsync, {inName:uid}, successFn, errorFn);
}

//注销
var logout = function (successFn,errorFn){
	var isAsync = true;
	ajaxFn(global.logoutUrl, isAsync, {}, successFn, errorFn);
}

//检测是否能能入游戏
var checkLoginGame = function (gameId,serverId,successFn,errorFn,aditionalEvent){
	var isAsync = false;
	ajaxFn(global.checkLoginGame, isAsync, {serverId:serverId,gameId:gameId}, successFn, errorFn, aditionalEvent);
}