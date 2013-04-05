var global={
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
//顶部弹出层登陆按钮

function topModalLogin(){
  var uid=$("#toplog_userName").val();
  var pwd=$("#toplog_password").val();
  if(!uid||!pwd){
    $(".error-msg").text("帐号或密码不能为空");
    return false;
  }
  else{
    loginFunction($("#toplog_userName").val(),$("#toplog_password").val(),true);
  }
  
}

$(".popLoginBtn").bind("click",topModalLogin);//注册登陆按钮点击
$("#toplog_password").bind("keydown",function(event){//注册回车登陆
   if (event.keyCode == '13') {
     event.preventDefault();
     topModalLogin();
   }
})
//登陆
function loginFunction(uid,pwd){
    $.post(global.loginUrl,{"inName":uid,"password1":pwd},function(data){
        if(data.result==="true"){
          loginSuccess(data);
          $(".newbieSelectServer").show();
          $(".newbieLogin ").hide();
        }else{
          $(".error-msg").text(data.respMsg);
          
        }    
    });

}

//成功之后
function loginSuccess(data)
{
   //同步顶部
   $(".topLoginInfo").hide();
   $(".topLoginedInfo").show().children("a.idx_name").text("欢迎您,"+data.outName);
   
   $("body").append(data.bbsData);//同步论坛
   $(".userSpan").text(data.outName.length > 10 ? data.outName.substring(0,8)+".." : data.outName);
}


function lockScreen(boolean){
	if(boolean){
		$(".lockScreen").height($(document).height()).css("opacity","0.6").show();
	}else{
		$(".lockScreen").hide();	
	}
}

//注册用户点击事件
$("#quickregister").bind("click",registerLogic);
//注册的实际逻辑
function registerLogic(){
  var userName=$("#topreg_userName");
  var pwd=$("#topreg_password1");
  var confirm_pwd=$("#topreg_password2");
  var captcha=$("#topreg_verifyCode");
  if($("#topcheckbox").attr("checked")!="checked"){
    alert("您还没有选择同意并接受用户注册协议书");
    return false;
  }else if(isRegisterOk(userName,pwd,confirm_pwd,captcha)){
    var flag= registerFunction(userName.val(),pwd.val(),confirm_pwd.val(),captcha.val());  
    return flag;
  }
  else{
    return false;
  }

}

/*注销开始*/
$("a.indexLogout").bind("click",function(){
  $.post(global.logoutUrl,function(data){
    if(data.result==="true"){
      $(".loginInfo,.topLoginInfo").show();
      $(".logined,.topLoginedInfo").hide();
      
      $("body").append(data.bbsData);//同步论坛
    }
    else{
      alert("请稍后再试");
    }

  })
})


function registerFunction(uid,pwd,confirm_pwd,captcha)
{
  var flag=true;
  $("#toptab_4").text("注册中...");
  $("#toptab_4").show();

  jQuery.ajax({
    url: global.registerUrl,
    type: 'POST',
    data: {"inName":uid,"password1":pwd,"password2":confirm_pwd,"verifyCode":captcha},
    async:false,
    success: function(data, textStatus, xhr) {
        if(data.result==="true"){
        //设置一个传入的data对象
        global.registerData.bbsData=data.bbsData;
        global.registerData.outName=data.outName;
        loginSuccess(global.registerData);
        $(".newbieReg").hide();
        $(".newbieSelectServer").show();
        $(".newbieReg input").val("").next("span").removeClass("yesTip");//注册成功后重置弹出层
      }
      else{
        $("#toptab_4").text(data.respMsg);
        flag=false;
      }
    },
    error: function(xhr, textStatus, errorThrown) {
      $("#toptab_4").text("服务器连接超时,请稍后再试");
      flag=false;
    }
  });
  return flag;
  
}
//判断用户名js验证
$("#topreg_userName").bind("blur",function(){
  var username=$(this);
  var flag=true;
  if(!username.val())
  {
    $("#toptab_1").text("用户名不能为空").addClass("msg_red").removeClass("msg_default");
    username.next("span").addClass("noTip").removeClass("yesTip");
  }
  else
  {
    //定义不合法字符
    var unauthorizedNames=['gamemaster','gm','shit','bitch','fvc','phuc','fuk','shenqu','fuck','admin','7road'];
    $.each(unauthorizedNames,function(index,valueName){

      if(valueName.toLowerCase().indexOf(username.val())>-1){
          $("#toptab_1").text("该用户名不合法").addClass("msg_red").removeClass("msg_default");;
          username.next("span").addClass("noTip").removeClass("yesTip");
          flag=false;
          return ;
      }

    })
    
    if(flag){
      $.get(global.checkUserNameUrl,{"inName":$(this).val()},function(data){
        if(data.result==="false"){
          $("#toptab_1").text(data.respMsg).addClass("msg_red").removeClass("msg_default");;
          username.next("span").addClass("noTip").removeClass("yesTip");
        }
        else{
          username.next("span").addClass("yesTip").removeClass("noTip");
        }
      })
    }

    
  }
  
}).bind("focus",function(){
  $("#toptab_1").text($(this).attr("tips")).addClass("msg_default").removeClass("msg_red");
  $(this).next("span").removeClass("noTip");
})


//判断密码js验证
$("#topreg_password1").bind("blur",function(){
  var pwdValLength=$.trim(this.value).length;
  if(pwdValLength<6||pwdValLength>20){
    $("#toptab_2").text("密码长度为6-20位").addClass("msg_red").removeClass("msg_default");
    $(this).next("span").addClass("noTip").removeClass("yesTip");
  }
  else if(pwdValLength<1){
     $("#toptab_2").text("密码不能为空").addClass("msg_red").removeClass("msg_default");
     $(this).next("span").addClass("noTip").removeClass("yesTip");
  }
  else{
    //判断成功
    $(this).next("span").addClass("yesTip").removeClass("noTip");
  }
}).bind("focus",function(){
    $("#toptab_2").text($(this).attr("tips")).addClass("msg_default").removeClass("msg_red");
    $(this).next("span").removeClass("noTip");
})


//判断确认密码框js验证
$("#topreg_password2").bind("blur",function(){
  var pwdValLength=$.trim(this.value).length;
  if(pwdValLength<6||pwdValLength>20){
    $("#toptab_3").text("密码长度为6-20位").addClass("msg_red").removeClass("msg_default");
    $(this).next("span").addClass("noTip").removeClass("yesTip");
  }
 else if(pwdValLength<1){
    $("#toptab_3").text("密码不能为空").addClass("msg_red").removeClass("msg_default");
    $(this).next("span").addClass("noTip").removeClass("yesTip");
  }
 else if($("#topreg_password1").val()!==$(this).val()){
    $("#toptab_3").text("两个密码不一致").addClass("msg_red").removeClass("msg_default");
    $(this).next("span").addClass("noTip").removeClass("yesTip");
  }
  else{
    $(this).next("span").addClass("yesTip").removeClass("noTip");
  }
}).bind("focus",function(){
  $("#toptab_3").text($(this).attr("tips")).addClass("msg_default").removeClass("msg_red");
  $(this).next("span").removeClass("noTip");
})


//验证码js验证
$("#topreg_verifyCode").bind("blur",function(){
  if(!$(this).val())
  {
    $("#toptab_4").text("验证码不能为空").addClass("msg_red").removeClass("msg_default");;
  }
}).bind("focus",function(){
  $("#toptab_4").text($(this).attr("tips")).addClass("msg_default").removeClass("msg_red");;
}).bind("keydown",function(event){
    if (event.keyCode == '13') {
      var regElem=document.getElementById("quickregister");

      
      if(document.all){
        regElem.click();
      }else {
        var evt = document.createEvent("MouseEvents");
        evt.initEvent("click",true,true);
        regElem.dispatchEvent(evt);
      }
      return false; 


   }

})
//注册的实际逻辑
function registerLogic(){

  var userName=$("#topreg_userName");
  var pwd=$("#topreg_password1");
  var confirm_pwd=$("#topreg_password2");
  var captcha=$("#topreg_verifyCode");
  if($("#topcheckbox").attr("checked")!="checked"){
    alert("您还没有选择同意并接受用户注册协议书");
    return false;
  }else if(isRegisterOk(userName,pwd,confirm_pwd,captcha)){
    var flag= registerFunction(userName.val(),pwd.val(),confirm_pwd.val(),captcha.val());  
    return flag;
  }
  else{
    return false;
  }

}

//客户端js验证
function isRegisterOk(userName,pwd,confirm_pwd,captcha){
  var usernameReg=/^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,19}$/;
  //5个重复字符
  var regRepeat = /([a-z0-9A-Z_])\1{4,}/;
  
  var flag=true;
  /*判断是否为空*/
  if(!userName.val())
  {
    $("#toptab_1").text("用户名不能为空").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }
  if(!pwd.val())
  {
    $("#toptab_2").text("密码不能为空").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }
  if(!confirm_pwd.val())
  {
    $("#toptab_3").text("确认密码不能为空").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }
  if(!captcha.val())
  {
    $("#toptab_4").text("验证码不能为空").addClass("msg_red").removeClass("msg_default");
	$("#topreg_verifyCodeImg").click();
    flag=false;
  }
  else{
    jQuery.ajax({
      url: global.serverHost+"/game7road/checkVerifyCode.action",
      data: {verifyCode: captcha.val()},
      async:false,
      complete: function(xhr, textStatus) {
        //called when complete
      },
      success: function(data, textStatus, xhr) {
        if(data.result==="false"){
          $("#toptab_4").text("验证码输入错误").addClass("msg_red").removeClass("msg_default");
		  $("#topreg_verifyCodeImg").click();
          flag=false;
        }
      },
      error: function(xhr, textStatus, errorThrown) {
        //called when there is an error
        flag=false;
        $("#toptab_4").text("服务器连接超时,请稍后再试").addClass("msg_red").removeClass("msg_default");
      }
    });
    
  }

  /*判断字段规则*/
  if(!usernameReg.test(userName.val()))
  {
    $("#toptab_1").text("字母开头，6-20个字母数字").addClass("msg_red").removeClass("msg_default");
    flag=false;
  }

  if(regRepeat.test(userName.val()))
  {
    $("#toptab_1").text("用户名不能有5个重复字符").addClass("msg_red").removeClass("msg_default");
    flag=false;
  }

  if(pwd.val()!==confirm_pwd.val())
  {
    $("#toptab_3").text("两个密码不一致").addClass("msg_red").removeClass("msg_default");
    flag=false;
  }
  return flag;
}
//领取礼包按钮
function newbieFunc(){
//载入检测登录
  $.get(global.checkLogin+"?timestamp="+(+ new Date()),function(data){
    if(data.result==="true"){
      $(".newbieSelectServer").show();
      lockScreen(true);
    }else{
      $(".newbieLogin").show();
      lockScreen(true);
    }
  });
}

$(function(){
    if($.browser.msie){
     if(($.browser.version=="6.0" || $.browser.version=="7.0") && Number(document.documentElement.clientWidth)<= 1024){
        $(".newGiftBg").css("left","78px");
      }else{
        $(".newGiftBg").css("left","60px");
      }
    }else if($.browser.webkit){
      $(".newGiftBg").css("left","58px");
    }
    $("#sqPop").hover(function(){
        $(".dBox").show();
        $("#serverList").hide();
    },function(){
        $(".dBox").hide();
        $("#serverList").show();
    });
    $(".topGame").hover(function(){
        $(".navSelectCon").show();
    },function(){
        $(".navSelectCon").hide();
    });
    $("#toplog_userName,#toplog_password").bind("focus",function(){
      $(".error-msg").empty();
    });
    $(".newbieReg .popInput1").bind("focus",function(){
       $("#toptab_4").empty();
    });
    $(".popChangePic").live("click",function(){
      $("#topreg_verifyCodeImg").click();
    });
    $(".newbieMenu span").bind("click",function(){
        if($(this).hasClass("loginSpan")){
            $(".newbieLogin").show();
            $(".newbieReg").hide();
            $(".error-msg").empty();
        }else{
            $(".newbieLogin").hide();
            $(".newbieReg").show();
            $("#topreg_verifyCodeImg").click();
            $("#toptab_4,.msg_default").empty();
            $(".leftmargin").removeClass(".noTip").removeClass(".yesTip");
        }
    });
    $(".newbieClose").live("click",function(){
        $(this).parent().hide();
        lockScreen(false);
    });
    $(".quickregister").live("click",function(){
      $(".newbieLogin").hide();
      $(".newbieReg").show();
      $("#toptab_4,.msg_default").empty();
      $(".leftmargin").removeClass(".noTip").removeClass(".yesTip");
    });
    //选择服务器获取激活码
    $(".applyBtn").live("click",function(){
    	var num=$.trim($('#serverList option:selected').val());
    	if(Number(num)==-1){
    		alert("请选择您所在的服务器!");
    		return false;
    	}else{
			$.ajax({
				type: "GET",
				url: "http://sq.7road.com/game7road/noviceCard.action?read="+num,
        async:false,
				dataType: "json",
				success: function(json){
					$(".newbieSelectServer").hide();
					$(".newbieConvertCode").show();
					$(".newbieConvertCode .codeSpan").text(json.retu);

				},
        error:function(json){
          alert("服务器连接超时请稍后再试！")
        }
			}); 
    	}
		
    });
    $(".copySpan").live("click",function(){
		try{
			window.clipboardData.setData("Text",$(this).siblings(".codeSpan").text());
			alert("复制成功!");
		}
		catch(e){
			alert("当前浏览器不支持此功能，请更换IE浏览器或手动复制！");
		}
	});
	//页面载入检测是否登录
	$.get(global.checkLogin+"?timestamp="+(+ new Date()),function(data){
		if(data.result==="true"){
			$(".topLoginInfo").hide();
			$(".topLoginedInfo").show().children("a.idx_name").text("欢迎您,"+data.outName);
      $(".userSpan").text(data.outName.length > 10 ? data.outName.substring(0,8)+".." : data.outName);
			$("body").append(data.bbsData);//同步论坛
		}
	});

})
