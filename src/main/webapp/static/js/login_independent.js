	//登录提交
	$(function(){
		focusInput('focusInput', 'normalInput'); 
		$("#userName").focus(); 
		$('#submit').click(function(){
			if(checkLogin() == true) {		
			$.post(SERVER + "/loginSubmit.action", {
					userName: $('#userName').val(),
					password1: $('#password1').val()
				}, function(data){
					if(data.result == 'false') {
						$('.login_error').html(data.data);
					} else if(data.result == 'true'||data.result=='notLoginGame') {
						if(data.bbsData!=null&&data.bbsData!=""){
							sycBBS(data.bbsData);
						}
					} else {
						$('.login_error').html("连接服务器超时，请稍后再试。");
					}
			}); 
		return false;	
		} else {
			$('.login_error').html('用户名或密码错误');
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


var callback = function callback(url, data){	
	$('#hidden').html(data);
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
function focusInput(focusClass, normalClass) { 
	var elements = document.getElementsByTagName("input");  
	for (var i=0; i < elements.length; i++) {    
		if (elements[i].type != "button" && elements[i].type != "submit" && elements[i].type != "reset") {    
			elements[i].onfocus = function() { this.className = focusClass; };      
			elements[i].onblur = function() { this.className = normalClass||''; };     
			}    
		} 
	} 
function getRequestParm(paraName){ 
	 var sUrl  =  location.href;
	 var sReg  =  "(?:\\?|&){1}"+paraName+"=([^&]*)"
	 var re=new RegExp(sReg,"gi");
	 re.exec(sUrl);
	 return unescape(RegExp.$1);
} 
function callbackLinkUrl(){
	var parmVal=getRequestParm("backUrl");
	if(parmVal!=null&&parmVal!=""){
		window.location.href=parmVal;
		return false;
	}
	return true;
}
function sycBBS(url){
	var reg=/src\s*=\s*"(.*?)"/;
	url=url.match(reg)[1];
	$.ajax({
		type: "get",
		url: url,
		async: false,
		dataType: "script",
		complete: callbackLinkUrl
	});	
}

$('#password1').keyup(function(e){
	var keyPressed = e?e.keyCode:window.event.keyCode;
	if (keyPressed == 13) {
		$('#reg_submit').click();
	}		
});