var keyArray = new Array('GameMaster','gm','GM','shit','bitch','fvc','phuc','fuk','shenqu','fuck', 'FUCK', 'Fuck','admin','7road');
var reg = /^[a-zA-Z0-9_]{6,20}$/;
var regRepeat = /([A-Za-z0-9_])\1{4,}/;
//检查屏蔽关键字
function checkKeyword(str){	
	for(i=0; i<keyArray.length; i++) {
		if(str.indexOf(keyArray[i])!=-1) {
			return false;
		}
	}
	return true;
}

//获得参数site的值
function getSite() {
	var value = "";
	var args=new Object();     
	var query=location.search.substring(1);//获取查询串     
	var pairs=query.split("&");//获得参数数组     
	for(var i=0;i<pairs.length;i++){     
		var pos=pairs[i].indexOf('=');//查找name=value  
		if(pos==-1) continue;//如果没有找到就跳过     
        var argname=pairs[i].substring(0,pos);//提取name   
		if(argname=="site") {
			value=pairs[i].substring(pos+1);//提取value
			$.cookie('site', value, {expires:0, path: '/',domain: COOKIE_NAME });
//			$.cookie('site', value, {expires:0, path: '/'});
			break;
		}
	}
	if(value=="") {
		value = $.cookie('site');
		if(value == null) {
			value = "";
		}
	}
	return value;     
}

$(function(){	
	focusInput('focusInput', 'normalInput'); 
	$("#userName").focus(); 
	

		//注册
		$('#submit').click(function(){
			
			if($('#imgTitle').is(':hidden')) {
				$('.login_error').html("注册中...");
				if(checkRegist() == true) {
					var value = getSite();
					$.post(SERVER + "/registrationSubmit.action", {
							userName: $('#userName').val(),
							password1: $('#password1').val(),
							password2: $('#password2').val(),
							site: value
						}, function(data){
							if(data.result == 'false') {
								$('.login_error').html(data.data);
							} else if(data.result == 'true') {
								
								if(getRequestParm("backUrl")&&data.bbsData!=null&&data.bbsData!=""){
									$('.hidden').html(data.bbsData);
								}
								else{
									var url = LOGINGAME + data.recommendServerId;
									$('.hidden').html(data.bbsData);
								    $.ajax({
								        type:"get",
								        dataType:"jsonp",
								        url:url,
								        jsonp:"jsoncallback",
								        data:{isAjax:true},
								        beforeSend:function (XMLHttpRequest, textStatus) {
								        },
								        success:function (msg) {
								            if(msg == true){
												setTimeout(function(){
													window.location.href=url;
												},1500);
								            }else{
								            	$('.hidden').html(data.bbsData);
								            	$('.login_error').html("注册成功。请到官网登陆游戏。");
												window.parent.callback4(data.username);
								            }
								        },
								        error:function(msg){
								        	$('.hidden').html(data.bbsData);
								        	$('.login_error').html("注册成功。请到官网登陆游戏。");
											window.parent.callback4(data.username);
								        }
								    });					
								}
							} else {
								$('.login_error').html("连接服务器超时，请稍后再试。");
							}	
					});	
					
				} else {
					$('.login_error').html('请检查你的账号密码格式。');
				}
				return false;
			}
			
		});
		$('#password2').keyup(function(){
			var keyPressed;
		    if (window.event)
		        keyPressed = window.event.keyCode; // IE
		    else
		        keyPressed = e.which; // Firefox
		    if (keyPressed == 13) {
		    	$('#submit').click();
		    }		
		});
		$('#password12').keyup(function(){
			var keyPressed;
		    if (window.event)
		        keyPressed = window.event.keyCode; // IE
		    else
		        keyPressed = e.which; // Firefox
		    if (keyPressed == 13) {
		    	tologin2();
		    }		
		});



	
	
});

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

var keyArray = new Array('gm','GM','fuk','FUK','shenqu','fuck', 'FUCK', 'Fuck','admin','7road');
//检查屏蔽关键字
function checkKeyword(str){	
	for(i=0; i<keyArray.length; i++) {
		if(str.indexOf(keyArray[i])!=-1) {
			return false;
		}
	}
	return true;
}
function checkRegist() {
	var result = true;
	var nickname = document.getElementById("userName");
	var password1 = document.getElementById("password1");
	var password2 = document.getElementById("password2");
	var agreed=document.getElementById("agreed");
	if(nickname.value == "" || nickname.value == null){
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入用户名</SPAN></P>");
		nickname.className = "g-ipt-err";
		result = false;
	}else{
		if(nickname.value.length > 20 || nickname.value.length < 6){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(!reg.test(nickname.value)) {
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名应由字母 、数字 、下划线组成</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(!regLetterFirst.test(nickname.value)) {
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名第一位应为字母</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(regRepeat.test(nickname.value)) {
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(!checkKeyword(nickname.value)){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名含有非法字符</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else{
			var url = SERVER + "/checkUserName.action?userName="+nickname.value;
			$.ajax({
				type: "get",
				url: url,
				async: false,
				dataType: "json",
				success: function(data){
					if(data.result == "false"){
						$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
						nickname.className = "g-ipt-err";
						result = false;
					}else{
						$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>用户名正确</SPAN></P>");
						nickname.className = "g-ipt";
					}
				}			
			});
		}
	}
	if(password1.value == "" || password1.value == null){
		$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入密码</SPAN></P>");
		password1.className = "g-ipt-err";
		result = false;
	}
	if(password1.value.length > 20 || password1.value.length < 6){
		$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
		password1.className = "g-ipt-err";
		result = false;
	}else{
		$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
		password1.className = "g-ipt";
	}
	if(password2.value == "" || password2.value == null){
		$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请再次输入密码</SPAN></P>");
		password2.className = "g-ipt-err";
		result = false;
	}else{
		if(password2.value != password1.value){
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次密码不一致</SPAN></P>");
			password2.className = "g-ipt-err";
			result = false;
		}else{
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
			password2.className = "g-ipt";
		}
	}
	 if(agreed.checked!=true){
		alert("请确认用户协议");
		result = false;
	 }
	return result;
}
	
	//验证登录
    function checkLogin() {
 
		var result = true;
		var nickname = document.getElementById("userName");
		var password1 = document.getElementById("password1");
		if(nickname.value == "" || nickname.value == null){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入用户名</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else{
			if(nickname.value.length > 20 || nickname.value.length < 6){
				$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
				nickname.className = "g-ipt-err";
				result = false;
			}
		}
		if(password1.value == "" || password1.value == null){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入密码</SPAN></P>");
			password1.className = "g-ipt-err";
			result = false;
		}
		if(password1.value.length > 20 || password1.value.length < 6){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			password1.className = "g-ipt-err";
			result = false;
		}	
		return result;
		}

	
	
	
	
	function focusInput(focusClass, normalClass) { 
		var elements = document.getElementsByTagName("input");  
		for (var i=0; i < elements.length; i++) {    
			if (elements[i].type != "button" && elements[i].type != "submit" && elements[i].type != "reset") {    
				elements[i].onfocus = function() { this.className = focusClass; };      
				elements[i].onblur = function() { this.className = normalClass||'';
					this.className = normalClass||''; 
					if(this.id=="userName"){
						userNameOnBlur();
					}else if(this.id=="password1"){
						password1OnBlur();
					}else if(this.id=="password2"){
						password2OnBlur();
					}
				
				};     
				}    
			} 
		} 

var callback = function callback(url, data){	
	$('#hidden').html(data);
	//window.location.href=url;
	openwin(url);
};


var openwin = function openwin(url) {
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
function getRequestParm(paraName){ 
	 var sUrl  =  location.href;
	 var sReg  =  "(?:\\?|&){1}"+paraName+"=([^&]*)"
	 var re=new RegExp(sReg,"gi");
	 re.exec(sUrl);
	 return unescape(RegExp.$1);
} 
function callbackLinkUrl(){
	var parmVal=getRequestParm("backUrl");
	if(parmVal){
		window.location.href=parmVal;
		return false;
	}
	return true;
}
function sycBBS(url){
	//var reg=/src\s*=\s*"(.*?)"/;
	//url=url.match(reg)[1];
//	$.getScript(url,function(){
//		callbackLinkUrl();
//	})
	// $.ajax({
	// 	type: "get",
	// 	url: url,
	// 	async: false,
	// 	dataType: "html",
	// 	success: callbackLinkUrl
	// });
	callbackLinkUrl();
}




function userNameOnBlur(){
	var reg = /^[a-zA-Z0-9_]{6,20}$/;
	var regRepeat = /([A-Za-z0-9_])\1{4,}/;
	var nickname = document.getElementById("userName");
	if(nickname.value == "" || nickname.value == null){
		$('#userNameTip').empty();
		nickname.className = "g-ipt";
		return;
	}
	if(nickname.value.length > 20 || nickname.value.length < 6){
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
		nickname.className = "g-ipt-err";
	}else if(!reg.test(nickname.value)){
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名应由字母、数字、下划线组成</SPAN></P>");
		nickname.className = "g-ipt-err";
		result = false;
	}else if(!regLetterFirst.test(nickname.value)) {
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名第一位应为字母</SPAN></P>");
		nickname.className = "g-ipt-err";
		result = false;
	}else if(regRepeat.test(nickname.value)){
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
		nickname.className = "g-ipt-err";
		result = false;
	}
	else if(!checkKeyword(nickname.value)){
		$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名含有非法字符</SPAN></P>");
		nickname.className = "g-ipt-err";
		result = false;
	}else{
		var url = SERVER + "/checkUserName.action?userName="+nickname.value;
		$.get(url,function(data){
			if(data.result == "false"){
				$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
				nickname.className = "g-ipt-err";
			}else{
				$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>用户名正确</SPAN></P>");
				nickname.className = "g-ipt";
			}
		});
	}
}

	function password1OnBlur(){
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		if($('#imgTitle').is(':hidden')) {
		if(password1.value == "" || password1.value == null){
			$('#password1Tip').empty();
			password1.className = "g-ipt";
			return;
		}
		if(password1.value.length > 20 || password1.value.length < 6){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			password1.className = "g-ipt-err";
		}else{
			if(password2.value != "" && password2.value != null){
				if(password2.value != password1.value){
					$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次密码不一致</SPAN></P>");
					$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password2.className = "g-ipt-err";
					password1.className = "g-ipt";
				}else{
					$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password1.className = "g-ipt";
					$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password2.className = "g-ipt";
				}
			}else{
				$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
				password1.className = "g-ipt";
			}
		}
		}
	}

	function password2OnBlur(){
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		if($('#imgTitle').is(':hidden')) {
		if(password2.value == "" || password2.value == null){
			$('#password2Tip').empty();
			password2.className = "g-ipt";
			return;
		}
		if(password2.value != password1.value){
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次密码不一致</SPAN></P>");
			password2.className = "g-ipt-err";
		}else{
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
			password2.className = "g-ipt";
		}
	}
	}
	var obj = {};
	var flag = 0;
	function changeTab(){
		flag = flag==0?1:0;
		if(flag == 0){
			$('#btn2Text').html('老用户登录');
			$('#line1').show();
			$('#line2').show();
			$('#imgTitle').hide();
			$('#submit2').hide();
			$('#submit').show();
			$('#userName2').hide();
			$('#password12').hide();
			$('#userName').show();
			$('#password1').show();

			$('#userNameTip').show();
			$('#password1Tip').show();
			//$('#errtip1').show();
		}else{
			$('#line1').hide();
			$('#line2').hide();
			$('#imgTitle').show();
			$('#submit2').show();
			$('#submit').hide();
			$('#btn2Text').html('快速注册');
			$('#userName2').show();
			$('#password12').show();
			$('#userName').hide();
			$('#password1').hide();

			$('#userNameTip').hide();
			$('#password1Tip').hide();
			//$('#errtip1').hide();
		}
		$('#userNameTip').html('');
		$('#password1Tip').html('');
		$('#errtip1').html('');
	}

function tologin2() {
	$('#userNameTip').html('');
	$('#password1Tip').html('');
	$('#errtip1').css('color','#000');
	//alert('0');
	//if(checkLogin() == true) {	
		$.post(SERVER + "/loginSubmit.action", {
			userName: $('#userName2').val(),
			password1: $('#password12').val()
		}, function(data){
			//$('.text').hide();
			$('#userNameTip').html('');
			$('#password1Tip').html('');
			if(data.result == 'false') {
				$('#errtip1').html("用户名或密码错误，请重新登录。");
			} else if(data.result == 'true') {
//				$('.username').html(data.data);
//				$('.login_time').html(getDate());
//				$('#TabTab03Con2').html('<div class="title" id="title"></div>' + $('#loged').html());
//				$('.hidden').html(data.bbsData);
//				$('#font1').attr('onmouseover', '');
//				window.parent.callback4(data.data);
				//alert(data.data);
				$('#errtip1').html('');
				$.post(SERVER + "/onlineGame/goGameServer.action", {
					serverId : 1
				}, function(data1){
					if(data1.result == 'false') {
						$('#errtip1').html("服务器忙，请选择其他服务器");
					} else if(data1.result == 'true') {
						//window.parent.callback2(data1.data, "");
						location = data1.data;
					} else {
						$('#errtip1').html("连接服务器超时，请稍后再试。");								
					}
				}); 

			} else {
				$('#errtip1').html("连接服务器超时，请稍后再试。");
			}
			$('#errtip1').css('color','#fff');
		}); 
	//} else {
	//	$('.text').hide();
	//	alert('用户名或密码错误');
	//}
}

