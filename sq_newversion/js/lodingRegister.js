var flag = false;
var xmlHttpReq = null;
var siteId="";
var adId="";
var EventUtil = {
	addHandler : function(element,type,handler){
		if(element.addEventListener){
			element.addEventListener(type,handler,false);	
		}else if(element.attachEvent){
			element.attachEvent("on"+type,handler)
		}else{
			element["on"+type]=handler;	
		}
	},
	removeHandler:function(element,type,handler){
		if(element.removeEventListener){
			element.removeEventListener(type,handler,false);	
		}else if(element.detachEvent){
			element.detachEvent("on"+type,handler);	
		}else{
			element["on"+type]=null;	
		}
	},
	getEvent:function(event){
		return event ? event : window.event;
	},
	getTarget:function(event){
		return event.target || event.srcElement;
	},
	preventDefault:function(event){
		if(event.preventDefault){
			event.preventDefault();	
		}else{
			event.returnValue = false;	
		}
	},
	setCookie : function(name,value,time){
		var strsec = getsec(time);
	    var exp = new Date();
	    exp.setTime(exp.getTime() + strsec*1);
	    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	},
	getCookie : function(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg)){
			return (arr[2]);
		}else{
			return null;
		}
	}
};
var urlAction = {
	checkLoginGame:"http://sq.7road.com/game7road/onlineGame/checkLoginGame.action",
	getLatestServerUrl:"http://sq.7road.com/game7road/onlineGame/acquireId.action",
	checkUserNameUrl:"http://sq.7road.com/game7road/checkUserName.action",
	registerUrl:"http://sq.7road.com/game7road/registrationSubmit.action",
	gameFromUrl:"http://sq.7road.com/game7road/enterFromAd.action",
	subGameUrl:"http://account.7road.com/PlayGame2?g=1&game=S&subGame=0&z=",
	newgameurl:"http://sq.7road.com/gameStart/gameStart.html?g=1&serverId="
};
function  $(obj){
	return document.getElementById(obj);
}
function redColor(obj,color){
	return obj.style.color = color;
}
function promptTxt(obj,msgType){
	return obj.innerHTML=obj.getAttribute(msgType);
}
function editClass(obj,className){
	return obj.className = className;
}



function getsec(str){
   var str1=str.substring(1,str.length)*1;
   var str2=str.substring(0,1);
   if (str2=="s"){
        return str1*1000;
   }else if (str2=="h"){
       return str1*60*60*1000;
   }else if (str2=="d"){
       return str1*24*60*60*1000;
   }
}

if(getUrlParam("adId")!=null){
	EventUtil.setCookie("adId",getUrlParam("adId"),"d7");
}
if(getUrlParam("site")!=null){
	EventUtil.setCookie("siteId",getUrlParam("site"),"d7");
}
EventUtil.addHandler($("btntoReg"),"click",registerLogic);
EventUtil.addHandler($("reg_pwd"),"keydown",preventKey);


//如果有确认密码
if($("reg_confirm_pwd")){
	EventUtil.addHandler($("reg_uid"),"blur",checkuserName);
	EventUtil.addHandler($("reg_pwd"),"blur",pwdBlur);
	EventUtil.addHandler($("reg_confirm_pwd"),"blur",confirmPwdBlur);
}else{
	EventUtil.addHandler($("reg_uid"),"blur",checkuserName);
	EventUtil.addHandler($("reg_pwd"),"blur",pwdBlur);
}

function registerLogic(){
	var userName=$("reg_uid").value,pwd=$("reg_pwd").value;
	var confirm_pwd="";
	if($("reg_confirm_pwd")){confirm_pwd=$("reg_confirm_pwd").value;}else{confirm_pwd=$("reg_pwd").value;}
	if($("agreement").checked!=true){
		alert("您还没有选择同意并接受用户注册协议书");
    	return false;
	}else if(isRegisterOk(userName,pwd,confirm_pwd)){
		registerFunction(userName,pwd,confirm_pwd);  
	}
}


function pwdBlur(){
	if($("reg_pwd").value.length>5 && $("reg_pwd").value.length<21 && $("reg_pwd").value.split(" ").length<2){
		promptTxt($("tab2_2"),"truemsg");
		editClass($("tab2_2"),"trueClass");
		flag=true;
	}else{
		promptTxt($("tab2_2"),"errormsg");
		editClass($("tab2_2"),"errorClass");
		flag=false;
	}
}


function confirmPwdBlur(){
	var pwd2 = $("reg_confirm_pwd").value,pwd1 = $("reg_pwd").value;
	if(pwd1.length>5 && pwd1.length<21){
		if(pwd2!=pwd1){
			promptTxt($("tab2_3"),"unequallymsg");
			editClass($("tab2_3"),"errorClass");
			flag=false;
		}else{
			promptTxt($("tab2_3"),"truemsg");
			editClass($("tab2_3"),"trueClass");
			flag=true;
		}
	}
}

function preventKey(event){
	var lKeyCode = window.event ? event.keyCode : event.which;  
	if(lKeyCode=="13"){
		EventUtil.preventDefault(event);
		registerLogic();
	}
}
function isRegisterOk(userName,pwd,confirm_pwd){
	var usernameReg=/^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,19}$/;
	var regRepeat = /([a-z0-9A-Z_])\1{4,}/;
	var flag=true;
	if(!userName){
		promptTxt($("tab2_1"),"nullmsg");
		editClass($("tab2_1"),"errorClass");
		flag=false;
	}
	/*判断字段规则*/
	if(!usernameReg.test(userName)){
		promptTxt($("tab2_1"),"errormsg");
		editClass($("tab2_1"),"errorClass");
		flag=false;
	}
	if(regRepeat.test(userName)){
		promptTxt($("tab2_1"),"fiveerrormsg");
		editClass($("tab2_1"),"errorClass");
		flag=false;
	}
	if(pwd.split(" ").length>1){
		promptTxt($("tab2_2"),"errormsg");
		editClass($("tab2_2"),"errorClass");
		flag=false;		
	}
	if(confirm_pwd.split(" ").length>1){
		promptTxt($("tab2_3"),"errormsg");
		editClass($("tab2_3"),"errorClass");
		flag=false;	
	}
	if($("reg_confirm_pwd")){//是否有确认密码
		if(confirm_pwd!=pwd){
			promptTxt($("tab2_3"),"unequallymsg");
			editClass($("tab2_3"),"errorClass");
			flag=false;
		}
	}else{
		if(!pwd){
			promptTxt($("tab2_2"),"nullmsg");
			editClass($("tab2_2"),"errorClass");
			flag=false;
		}
	}
	return flag;
}



//判断用户名是否合法
function checkuserName(){
	var that = $("reg_uid").value;
	var unauthorizedNames=['gamemaster','gm','shit','bitch','fvc','phuc','fuk','shenqu','fuck','admin','7road'];
	flag=true;
	if(!that){
		promptTxt($("tab2_1"),"nullmsg");
		editClass($("tab2_1"),"errorClass");
	}else if(that.length>20 || that.length<6){
		promptTxt($("tab2_1"),"errormsg");
		editClass($("tab2_1"),"errorClass");
	}else{
		for(i=0;i<unauthorizedNames.length;i++){
			if(that.indexOf(unauthorizedNames[i])>-1){
				promptTxt($("tab2_1"),"unrulemsg");
				editClass($("tab2_1"),"errorClass");
				flag=false;
			}
		};
		if(flag){
			createXHR();
			var checkUserNameUrl =  urlAction.checkUserNameUrl;
			checkUserNameUrl = addURI(checkUserNameUrl,"inName",that);
			if(xmlHttpReq!=null){
				xmlHttpReq.open("get",checkUserNameUrl,false);
				xmlHttpReq.onreadystatechange = function(){
					if(xmlHttpReq.readyState==4){
						if(xmlHttpReq.status>=200 && xmlHttpReq.status <300 || xmlHttpReq.status == 304){
							var data = eval("("+xmlHttpReq.responseText+")")
							if(data.result=="false"){
								$("tab2_1").innerHTML=data.respMsg;
								editClass($("tab2_1"),"errorClass");
							}else{
								promptTxt($("tab2_1"),"truemsg");
								editClass($("tab2_1"),"trueClass");
							}
						}else{
							//alert("服务器繁忙请稍后再试")	
						}
					}	
				}
				xmlHttpReq.send(null);
			}
		}
	}
}



//注册开始
function registerFunction(userName,pwd,confirm_pwd){
	if(flag){
		$("reg_tip").innerHTML="注册中...";
		createXHR();
		var submitData = "inName="+userName+"&password1="+pwd+"&password2="+confirm_pwd;
		if(xmlHttpReq!=null){
			xmlHttpReq.open("post",urlAction.registerUrl,false);
			xmlHttpReq.onreadystatechange = function(){
				if(xmlHttpReq.readyState==4){
					if(xmlHttpReq.status>=200 && xmlHttpReq.status <300 || xmlHttpReq.status == 304){
						var data = eval("("+xmlHttpReq.responseText+")")
						if(data.result=="true"){
							registerSucceed(data);
						}else{
							$("reg_tip").innerHTML = data.respMsg;
						}
					}else{
						alert("服务器繁忙请稍后再试")	
					}
				}	
			}
			xmlHttpReq.setRequestHeader("Content-Type","application/x-www-form-urlencoded;"); 
			xmlHttpReq.send(submitData);
		}
	}
}

function registerSucceed(data){
	$("syncBbs").innerHTML = data.bbsData;
	//进入新服
	createXHR();
	if(xmlHttpReq!=null){
		xmlHttpReq.open("get",urlAction.getLatestServerUrl,false);
		xmlHttpReq.onreadystatechange = function(){
			if(xmlHttpReq.readyState==4){
				if(xmlHttpReq.status>=200 && xmlHttpReq.status <300 || xmlHttpReq.status == 304){
					var data = eval("("+xmlHttpReq.responseText+")")
					if(Number(data.id)>0){
						var checkServerId = data.id;
						checkGameLogin(checkServerId);
					}else{
						
					}
				}else{
					alert("服务器繁忙请稍后再试")	
				}
			}	
		}
		xmlHttpReq.send(null);
	};
	
}


//注册成功之后 提交 site , id
adId = getUrlParam("adId");
siteId = getUrlParam("site");
if(!adId){
	adId = EventUtil.getCookie("adId");
}
if(!siteId){
	siteId= EventUtil.getCookie("siteId");
}
if(adId != "" && adId != null){
	createXHR();
	var gameFromUrl = urlAction.gameFromUrl;
	gameFromUrl = addURI(gameFromUrl,"adId",adId);
	gameFromUrl = addURI(gameFromUrl,"site",siteId);
	xmlHttpReq.open("get",gameFromUrl,false);
	xmlHttpReq.onreadystatechange = function(){
		if(xmlHttpReq.readyState==4){
			if(xmlHttpReq.status>=200 && xmlHttpReq.status<300 || xmlHttpReq.status==304){
				//t
			}
		}
	}
	xmlHttpReq.send(null);
}



function checkGameLogin(checkServerId){
	createXHR();
	//var noUriGameUrl =gameUrl;
	gameUrl = addURI(urlAction.checkLoginGame,"serverId",checkServerId);
	if(xmlHttpReq!=null){
		xmlHttpReq.open("get",gameUrl,false);
		xmlHttpReq.onreadystatechange = function(){
			if(xmlHttpReq.readyState==4){
				if(xmlHttpReq.status>=200 && xmlHttpReq.status <300 || xmlHttpReq.status == 304){
					var data = eval("("+xmlHttpReq.responseText+")")
					if(Number(data.code)==0){//可以进入
						setTimeout(function(){document.location.href =  urlAction.newgameurl+checkServerId.toString()+"&timestamp="+(+new Date());},500)
					}else{//最新服异常跳至官网首页
						setTimeout(function(){document.location.href =  "http://sq.7road.com/";},500)
					}
				}else{
					alert("服务器繁忙请稍后再试")	
				}
			}	
		}
		xmlHttpReq.send(null);
	}
}

function createXHR(){
	if(window.ActiveXObject){
		xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
	}else if(window.XMLHttpRequest){
		xmlHttpReq =  new XMLHttpRequest();
	}
}

function addURI(url,name,val){
	url += (url.indexOf("?")==-1 ? "?" : "&");
	url += encodeURIComponent(name) + "=" + encodeURIComponent(val);
	return url;
}

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}







var titleOne=document.title,titleTwo=window.location.href,titleIndex=0;;
titleTwo=titleTwo.replace("http://","");
function showhTitle(){
	if(titleIndex==0) {
		document.title = titleOne;
		titleIndex = 1 ;
	}else{
		document.title = titleTwo;
		titleIndex = 0 ;
	}
}
setInterval(showhTitle,500);

