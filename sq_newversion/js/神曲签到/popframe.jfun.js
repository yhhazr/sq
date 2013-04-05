/******************************************/
/**用户操作**/
var uLogin={
	/*显示登录*/
	showLogin:function(){
		popframe.mark(1);
		$(".server-response-err").html("");
		$(".win-login").css("display","block");
		},
	/*隐藏登录*/
	hideLogin:function(){
		popframe.mark(0);
		$(".win-login").css("display","none");	
		},
	/*登录后初始化*/
	initLogin:function(json){
			$("#tologin").hide();
			$("#relogin").show();
			$(".user_logined_a").show();
			$(".username_a").html(json.outName);
			$(".date table").find('td[date]').removeClass("yes");
			//初始化积分及签到日期
			$("#relogin").find(".score_a").text(json.totalScore || 0);
			$("#relogin").find(".nums_a").text(json.continueSignCount || 0);
			var dataList=json.signHistory;
			if(dataList==null){popframe.show("tishi_box","系统繁忙,请重试."); return;}
			for(var i in dataList){
					$('table td[date="' + dataList[i] + '"]').addClass('yes');
				 }
			//获取token
			$.getJSON("http://sq.7road.com/game7road/mytoken.action?t="+Math.random(),function(json){
					window._token=json.token;
				})
			//更新礼包
			uLogin.upScore(json);
			
		},
	/*更新领取的礼包*/
	upScore:function(json){
			var hml="";
			var sco_len=json.gifts.length;
			if(sco_len==0)
			{
				$(".award_a").empty();
				return;
			}
			for(var i=0;i<sco_len;i++)
			{
				var giftObj=json.gifts[i];
				hml+="<p>"+giftObj["exchangeDateStr"]+"领取"+giftObj["giftName"]+":"+giftObj["activationCode"]+"</p>"
			}
			$(".award_a").html(hml);
			$(".score_a").html(json.totalScore);
		},
	/*发送登录请求*/
	goLogin:function(){
			
			var uName=$("#uName").val();
			var uPass=$("#uPass").val();
			if(uName=="" || uPass==""){$(".server-response-err").html("用户名或密码不能为空!");return;}
			var args="?inName="+escape(uName)+"&password1="+escape(uPass);
			$.ajax({
					type: "POST",
					url: "http://sq.7road.com/game7road/loginSubmit.action"+args+"&t="+Math.random(),
					dataType:"json",
					success:function(json){
						 //是否记住用户
					    var remVa=$("#rember-me").attr("checked");
					    if(remVa){
								uLogin.addcookie("inName",uName,"password1",uPass);
							}
						if(json.result!="false"){
							uLogin.hideLogin();
							uLogin.initLogin(json);
						}else{
							$(".server-response-err").html(json.respMsg);
							}
						}
					});
		},
	/*退出*/
	outLogin:function(){
			$.getJSON("http://sq.7road.com/game7road/indexLogout.action",function(json){
					$("#tologin").show();
					$("#relogin").hide();
					$(".user").hide();
					$(".username_a").html("");
					$(".date table").find('td[date]').removeClass("yes");
					var scriptHml=json.bbsData;
					if(scriptHml=="" || scriptHml.indexOf("script")==-1){return;}
					var dvScript=document.createElement("div");
					dvScript.innerHTML=scriptHml;
					document.body.appendChild(dvScript);
					//删除COOKIE
					uLogin.deletecookie("inName");
					
				});
		},
	/*验证是否登录*/
	checkLogin:function(pd){//不传参数表示只是判断是否登录
			$.getJSON("http://sq.7road.com/game7road/checkLogin.action?timestamp="+(+new Date()),function(json){
					if(pd){
							if(json.result!="false"){
								uLogin.initLogin(json);
							}	
						}else{
								if(json.result=="false"){return false;}
								return true;
							}
				});
		},
	/**签到**/
	sign:function(){
		//简易判断是否登录
			if($(".username_a").html()==""){uLogin.showLogin();return;}
			$.ajax({
				type: "POST",
				url: "http://sq.7road.com/game7road/sign.action"+"?t="+Math.random(),
				dataType: "json",
				success:function(json){
					switch(json.result){
						 case "unLogin"://未登录
						 	  uLogin.showLogin();
						 	  break;
						 case "hasSigned"://已签到
						 	  popframe.show("tishi_box","您今天已经签到!");
						 	  break;
						 case "failure"://签到失败
						 	  popframe.show("tishi_box","签到失败!");
						 	  break;
						 case "error"://系统错误
						 	  popframe.show("tishi_box","系统错误,请重新签!");
						 	  break;
						 case "success"://成功
							  var dataList=json.signHistory;
							  if(dataList==null || dataList=="" || dataList.length<1){popframe.show("tishi_box","系统繁忙,请重试.");return;}
						 	  $("#relogin").find(".score_a").text(json.totalScore || 0);
							  $("#relogin").find(".nums_a").text(json.continueSignCount || 0);
							  $(".date table").find('td[date]').removeClass("yes");//防止日期造成的错误
							  //当前的签到日期
							  for(var i in dataList){
								  	$('table td[date="' + dataList[i] + '"]').addClass('yes');
								  }
						 	  alert("签到成功");
						 	  break;
						 default:break;
						}
			 	}
			})
		},
	/*领取礼包*/
	getScroe:function(type){
		//简易判断是否登录
		if($(".username_a").html()==""){uLogin.showLogin();return;};
		var e=popframe.getEvent();
		var etg=e.target||e.srcElement;
		if(typeof type=="undefined"){var type=etg.getAttribute("award");}
		$.ajax({
			url: "http://sq.7road.com/game7road/exchangeGift.action?type="+type+"&t="+Math.random()+"&token="+window._token,
			type: "GET",
			dataType: "json",
			success:function(json){
					var oldTok=window._token;
					window._token=json.token;
					switch(json.result){
							case "unLogin"://未登录
								 uLogin.showLogin();
								 break;
							case "repeat"://未登录
								 window._token=oldTok;
								 popframe.show("tishi_box","表单重复提交!");
								 break;
							case "unSelect"://未选择礼包
								 popframe.show("tishi_box","未选择礼包类型!");
								 break;
							case "unExists":
							 	 popframe.show("tishi_box","选择的礼包类型不存在!");
								 break;
							case "hasReceive":
								 popframe.show("tishi_box","您已经领取过该礼包!");
								 break;
							case "lessScore":
								 popframe.show("tishi_box","您的积分不足领取该礼包!");
								 break;
							case "finish":
								 popframe.show("tishi_box","活动已经结束,谢谢您的参与!");
								 break;
							case "error":
								 alert("系统错误");
								 break;
							default:
								
								var activeCode=json.result;
								var typeName=gettypename(type);
								popframe.show("duihuan_box",activeCode,typeName);
								uLogin.upScore(json);//更新礼包
								break;
								
						}
				}
			});
			
		},
	//复制内容
	copyVa:function(id)
		{
			var _id = $("#"+id);
			if(!window.clipboardData){alert("您的浏览器不支持复制功能,请您手动复制.");}
			window.clipboardData.setData("Text",_id.value);
			alert("复制成功!");
		},
	//删除cookie
		deletecookie:function (name){
				var date=new Date();
				date.setTime(date.getTime()-10000);
				document.cookie=name+"=;expires="+date.toGMTString();
			},
	//写入cookie
	 addcookie:function(name,nameVal,pass,passVal,time){
		 	var date=new Date();
			date.setTime(date.getTime() +  ((time || 24) * 60 * 60 * 1000));//记住7天
			document.cookie=name+"="+escape(nameVal)+";"+pass+"="+escape(passVal)+";expires="+date.toGMTString()+";path=/";
		 }
	};
//////////      弹出     ///////////
var popframe = {
    /** 添加html  **/
    draw : function(id,code,name){
        var html;
		switch(id)
		{
			case "duihuan_box":
				html = '<div id="duihuan_box"><h3>兑换礼包</h3><div id="duihuan_inner"><p>恭喜您获得第七大道《神曲》<span>'+name+'礼包</span></p>'
					  +'<textarea name="" cols="" rows="" class="code_txt" id="convert">'+code+'</textarea><div id="duihuan_bottom">'
					  +'<input name="" type="button" value="复制" class="copy_btn" onclick="uLogin.copyVa(\'convert\')"/><a class="close" href="javascript:popframe.hide(\'duihuan_box\');" title="关闭">关闭</a></div></div></div>';
				break;
			case "tishi_box":
				html = '<div id="tishi_box"><div id="tishi_box_top"><h1>温馨提示</h1><a class="close" href="javascript:popframe.hide(\'tishi_box\');" title="关闭">关闭</a> </div>'
					  +'<div id="tishi_inner"> <img src="http://image.7road.com/ico.jpg" width="100" height="100" /><h3>感谢参与</h3><p>'+code+'</p>'
					  +'<input name="" type="button" value="确定" class="tishi_sumbit_btn" onclick="javascript:popframe.hide(\'tishi_box\');"/></div></div>';
				break;
			default:
				break;
		}
		popframe.insertHtml(html);
    },
    /**  弹出居中**/
    show:function(id,code,name){
        popframe.draw(id,code,name);
        var _id = document.getElementById(id);
        popframe.mark(1);
    },
    /** 关闭 **/
    hide: function(id) {
        popframe.mark(0);
        var _id = document.getElementById(id);
        _id.parentNode.removeChild(_id);
    },
	mark : function(show) {
        var fbg = $("#__frame__").length;
        if(fbg==0){
            var html;
            html = '<div id="__frame__" style="display:none;position:absolute;top:0;left:0;background:#000;filter:alpha(opacity=70);-moz-opacity:0.7;-khtml-opacity: 0.7;opacity: 0.7;z-index:9999;border:none;width:100%;">';
            html += '<iframe scrolling="no" frameborder="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;border:none;filter:alpha(opacity=0);-moz-opacity:0;-khtml-opacity: 0;opacity:0;">';
            html += '</iframe>';
            html += '</div>';
			popframe.insertHtml(html);
        }
        fbg = $("#__frame__");
        if(show){
            fbg.height($(document).height() + "px");
            fbg.show();
        }else{
            fbg.hide();
        }
    },
	//插入元素
	 insertHtml : function(html){
        var frag = document.createDocumentFragment();
        var div = document.createElement("div");
        div.innerHTML = html;
        for (var i = 0, ii = div.childNodes.length; i < ii; i++) {
            frag.appendChild(div.childNodes[i]);
        }
        document.body.insertBefore(frag,document.body.firstChild);//document.body.appendChild(frag);//后插可能效率差点
    },
	//获取对象源
	getEvent:function()
    {  
        if(document.all)  return window.event;    
        func=popframe.getEvent.caller;        
        while(func!=null){  
            var arg0=func.arguments[0]; 
            if(arg0) 
            { 
              if((arg0.constructor==Event || arg0.constructor ==MouseEvent) || (typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation))
              {  
              return arg0; 
              } 
            } 
            func=func.caller; 
        } 
        return null; 
    } 
};