/*右侧快速注册模块开始*/
var userNameRight = 0;
var firstPwdRight = 0;
var confirmPwdRight = 0;
var captchaRight = 0;

//用户名验证函数
function quickCheckUserName(userName){
	var flag = uidOnBlur(userName);

	return flag;
}

//第一次密码验证函数
function quickCheckFirstPwd(firstPwd){
	var flag = pwdOnBlur(firstPwd);
	
	return flag;
}

//第二次密码验证函数
function quickCheckConfirmPwd(firstPwd, confirmPwd){
	var flag = confirmPwdOnBlur(firstPwd, confirmPwd);
	
	return flag;
}

//验证码验证函数
function quickCheckCaptcha(captcha){
	var flag = 1;
	if(!captcha){
		captchaMsgTip = "请输入验证码";
	}else if(captcha.length!=4){
		captchaMsgTip = "位数不对";
	}else{		
		$.ajax({
				type: "get",
				url: SERVER+"/checkVerifyCode.action?verifyCode="+captcha,
				async: false,
				dataType: "json",
				success: function(data){
						if(data.result == "false"){
							captchaMsgTip = "验证码错误";
							$('#qrCaptchaImg').attr('src', SERVER+'/captcha.do?'+(new Date().getTime()));
						}else{
							flag = 0;
						}
				}			
		});
	}
		
	return flag;
}

//注册提交
function quickRegist(){
	var qrFlag = 1;
	getGameEnterInfo(POSITION56);
	var canRegist = userNameRight + firstPwdRight + confirmPwdRight + captchaRight;	
	if(canRegist == 15){
		var name = $('#quickUserName').val();
		var pwd = $('#quickFirstPwd').val();
		var confirmPwd = $('#quickConfirmPwd').val();
		var flag = registSubmit(name, pwd, confirmPwd);
		if(flag == 0) {
			$('#hidden').html(bbsLoginMsg);
			$('#qrUsername').empty().html(logedName);
			$('#qrLoginTime').empty().html(getDate());
			$('#qrLastserver').empty().html('进入推荐服务器');
			getNewestGameServerUrl({func: quickRegistGetGameUrl});
			changeHeaderAtOnce(logedName);
			
			qrFlag = 0;
			getGameEnterInfo(POSITION61);//注册成功
		}else {
			$('#quickUserName').blur();
			$('#quickCaptcha').blur();
			$('#qrCaptchaImg').attr('src', SERVER+'/captcha.do?'+(new Date().getTime()));
		}		
	}
	return qrFlag;
} 

//把游戏地址写入按钮
var quickRegistGetGameUrl = function(gameUrl) {
	$('#qrLastserver').attr('href', gameUrl);
	$('#qrNewestserver').attr('href', gameUrl);
	$("#quickRegist").attr("href", gameUrl);
}

//回车键触发注册提交函数
$('#quickCaptcha').keyup(function(e){
	var keyPressed = e?e.keyCode:window.event.keyCode;
    if (keyPressed == 13) {
    	$('#quickCaptcha').blur();
    	trnsferUrl("quickRegist");
    }	
});

function trnsferUrl(id){     
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

$(function(){
	
	var quickUserName = '';
	var quickFirstPwd = '';
	var quickConfirmPwd = '';
	var quickCaptcha = '';
	
	var userDefaultTip = '字母开头，6-20字母数字';
	var userEmptyTip = '请输入账户名';
	var fPwdEmptyTip = '请输入密码';
	var cPwdErrorTip = '两次密码不一致';
	var cPwdEmptyTip = '请再次输入密码';
	var errorCaptchaTip = '验证码错误';
	var emptyCaptchaTip = '请输入验证码';
	var captchaMsgTip = '请输入验证码';
	
	var gameZoneUrl = getNewestGameServerUrl({func: quickRegistGetGameUrl});
	
	
	//账号焦点离开函数
	$('#quickUserName').blur(function(){
		var userName = $('#quickUserName').val();
		if(userName != userDefaultTip && userName != userEmptyTip && userName != regUserMsg){
			quickUserName = userName;
		}
		userNameRight = 0;
		$('#quickUserName').css('color', 'red');
		if(userName && userName != ''){
			var flag = quickCheckUserName(userName);
			if(flag == 1){
				$('#quickUserName').empty().val(regUserMsg);
				$('#quickUserNameFlag').attr('src', 'http://image.7road.com/errorDot.png');
			}else{
				userNameRight = 1;
				$('#quickUserName').css('color', 'green');
				$('#quickUserNameFlag').attr('src', 'http://image.7road.com/rightDot.png');
			}			
		}else{
			$('#quickUserName').empty().val(userEmptyTip);
			$('#quickUserNameFlag').attr('src', 'http://image.7road.com/errorDot.png');
		}
	});
	
	//账号输入框获得焦点函数
	$('#quickUserName').focus(function(){
		$('#quickUserName').empty().val(quickUserName);
	});
	
	//第一次输入密码框焦点离开函数
	$('#quickFirstPwd').blur(function(){
		var firstPwd = $('#quickFirstPwd').val();
		quickFirstPwd = firstPwd;
		firstPwdRight = 0;
		$('#quickFirstPwd').css('color', 'red');
		$('#reQuickFirstPwd').css('color', 'red');
		if(firstPwd && firstPwd != ''){
			var flag = quickCheckFirstPwd(firstPwd);
			if(flag == 1){
				$('#quickFirstPwd').addClass('qrHide');
				$('#reQuickFirstPwd').removeClass('qrHide').val(regFirstPwdMsg);
				$('#quickFirstPwdFlag').attr('src', 'http://image.7road.com/errorDot.png');
			}else{
				firstPwdRight = 2;
				$('#quickFirstPwd').css('color', 'green');
				$('#quickFirstPwdFlag').attr('src', 'http://image.7road.com/rightDot.png');
			}			
		}else{
			$('#quickFirstPwd').addClass('qrHide');
			$('#reQuickFirstPwd').removeClass('qrHide').val(fPwdEmptyTip);
			$('#quickFirstPwdFlag').attr('src', 'http://image.7road.com/errorDot.png');
		}
	});
	
	//第一次输入密码框获得焦点函数
	$('#reQuickFirstPwd').focus(function(){
		$('#reQuickFirstPwd').addClass('qrHide');
		$('#quickFirstPwd').removeClass('qrHide').empty().val(quickFirstPwd).focus();
	});
	
	//再次输入密码框焦点离开函数
	$('#quickConfirmPwd').blur(function(){
		var confirmPwd = $('#quickConfirmPwd').val();
		var firstPwd = $('#quickFirstPwd').val();
		quickConfirmPwd = confirmPwd;
		confirmPwdRight = 0;
		$('#quickConfirmPwd').css('color', 'red');
		$('#reQuickConfirmPwd').css('color', 'red');
		if(confirmPwd && confirmPwd != ''){
			var flag = quickCheckConfirmPwd(firstPwd, confirmPwd);
			if(flag == 1){
				$('#quickConfirmPwd').addClass('qrHide');
				$('#reQuickConfirmPwd').removeClass('qrHide').val(cPwdErrorTip);
				$('#quickConfirmPwdFlag').attr('src', 'http://image.7road.com/errorDot.png');
			}else{
				confirmPwdRight = 4;
				$('#quickConfirmPwd').css('color', 'green');
				$('#quickConfirmPwdFlag').attr('src', 'http://image.7road.com/rightDot.png');
			}			
		}else{
			$('#quickConfirmPwd').addClass('qrHide');
			$('#reQuickConfirmPwd').removeClass('qrHide').val(cPwdEmptyTip);
			$('#quickConfirmPwdFlag').attr('src', 'http://image.7road.com/errorDot.png');
		}
	});
	
	//再次输入密码框获得焦点函数
	$('#reQuickConfirmPwd').focus(function(){
		$('#reQuickConfirmPwd').addClass('qrHide');
		$('#quickConfirmPwd').removeClass('qrHide').empty().val(quickConfirmPwd).focus();
	});

	//验证码输入框焦点离开函数
	$('#quickCaptcha').blur(function(){
		var captcha = $('#quickCaptcha').val();
		if(captcha != captchaMsgTip && captcha != errorCaptchaTip && captcha != emptyCaptchaTip){
			quickCaptcha = captcha;
		}
		captchaRight = 0;
		$('#quickCaptcha').css('color', 'red');
		if(captcha && captcha != ''){
			var flag = quickCheckCaptcha(captcha);
			if(flag == 1){
				$('#quickCaptcha').empty().val(errorCaptchaTip);
			}else{
				captchaRight = 8;
				$('#quickCaptcha').css('color', 'green');
			}			
		}else{
			$('#quickCaptcha').empty().val(emptyCaptchaTip);
		}
	});
	
	//验证码输入框获得焦点函数
	$('#quickCaptcha').focus(function(){
		$('#quickCaptcha').empty().val(quickCaptcha);
	});
	
	//注册按钮点击函数
	$('#quickRegist').click(function(){
		var flag = quickRegist();
		if(flag == 0){
			var gameUrl = $(this).attr('href');
			var canLogGame = enterGame(gameUrl);
			if(canLogGame == 0) {
				$('#rqContainter').hide();
				$('#rqIsRequest').show();
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	});
	
	//登陆最近游戏服务器
	$("#qrLastserver, #qrNewestserver").live('click', function(){
		var gameUrl = $(this).attr('href');
		if(gameUrl && gameUrl != "#" && gameUrl != "") {
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

	
	
	//微博分享模块功能函数
	$('.share_button').click(function(){
		var isIE=!!window.ActiveXObject;
		var isIE6=isIE&&!window.XMLHttpRequest;
		var shareUrl = 'http://www.jiathis.com/send/';
		var url = window.location.href;
		if(url.lastIndexOf('#') != -1){
			url = url.substring(0, url.lastIndexOf('#'));
		}		
		var title = $('TITLE').text();
		title = title.replace('#A0#A0#A0#A0', '');
		title = title.replace('#A0#A0', '');
		title = title.replace('#A0', '');
		var summary = "";
		var $content = $('div#news_content p:lt(2)');
		if($content && $.trim($content.text()).length > 0){
			summary = $.trim($content.text()).substring(0,40)+'...';			
		}		
		var pic = $('div#news_content img').attr('src');
		var webid = "";
		var id = $(this).attr('id');
		if(id == 'share_tsina'){
			var getEnterInfoFlag=getGameEnterInfo(POSITION57);
			webid = 'tsina';
		}
		if(id == 'share_tqq'){
			var getEnterInfoFlag=getGameEnterInfo(POSITION58);
			webid = 'tqq';
		}
		if(id == 'share_kaixin001'){
			var getEnterInfoFlag=getGameEnterInfo(POSITION59);
			webid = 'kaixin001';
			title += " " + summary;
		}
		if(id == 'share_renren'){
			var getEnterInfoFlag=getGameEnterInfo(POSITION60);
			webid = 'renren';
			summary += " " + url;
		}
		if(url == 'http://sq.7road.com/' || url.lastIndexOf('list') != -1 || url.lastIndexOf('serverList.html') != -1){
			if(id == 'share_renren'){
				summary = url;
				shareUrl += '?webid='+webid+'&url='+url+'&title='+title+'&summary='+summary;
			}else{
				shareUrl += '?webid='+webid+'&url='+url+'&title='+title;
			}
		}else if(!pic){
			shareUrl += '?webid='+webid+'&url='+url+'&title='+title+'&summary='+summary;
		}else{
			shareUrl += '?webid='+webid+'&url='+url+'&title='+title+'&summary='+summary + '&pic='+pic;
		}
		openwin(encodeURI(shareUrl));
		return false;
	});
	
});
/*右侧快速注册模块结束*/

//打开url链接
var openwin = function openwin(url) {
//	window.open(url).location=url;
	var a = document.createElement("a");
	a.setAttribute("href", url);
	a.setAttribute("target", "_blank");
	a.setAttribute("id", "submit");
	document.body.appendChild(a);
	if(window.MessageEvent){
		var evt = document.createEvent("MouseEvents");  
	    evt.initEvent("click", true, true);  
	    a.dispatchEvent(evt);
	} else {
		a.click();
	}
};

/* 注册成功部分开始 */


//退出登陆	
$('#qrLogout').live('click', function(){
	var flag = logout({func: quickRegistCheckReturnOfLogout});
	
	return false;
});

//退出登录方法返回值判断函数
var quickRegistCheckReturnOfLogout = function(flag) {
	if(flag == 0) {
		$('.head_login_suc').hide();
		$('.head_login_content').show();
		$('.start_game').attr('href', HOST+'/serverList.html');
		$('#rqContainter').show();
		$('#rqIsRequest').hide();		
	}
}

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

//弹出层登录成功后立即改变页面登录状态
var callback4 = function callback4(data){
	$('.head_login_content').hide();
	$('.head_login_suc').html("<div class='idx_loged'><a href='" +PLATE_FORM_HOST+"' class='idx_name' target='_blank'>欢迎您,   "
			+ data
	+ "&nbsp&nbsp&nbsp</a><a href='#' class='logout'>退出</a></div>").css({"position":"relative","right":"80px", "z-index":"100"});
	$('.head_login_suc').show();	
	
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

/* 注册成功部分结束 */