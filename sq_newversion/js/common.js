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


//获取某一区所有排行榜
function getRankData()
{
    var serverId=$(".gameArea :selected").val();

    $.get(global.serverHost+"/player/"+serverId+".html",function(data){
        
        $(".ranking #rank").html(data);
        getSpecifiedRankData($("select.gameRank"));
    });
}
//获取某区某类型排行榜
function getSpecifiedRankData(obj)
{
     var selectedIndex= obj.children("option:selected").index();
    $(".ranking #rank > div").eq(selectedIndex).show();
    $(".ranking #rank > div").not(":eq("+selectedIndex+")").hide();
}
//注册服务区更改事件
$("select.gameArea").bind("change",getRankData);
$("select.gameRank").bind("change",function(){
    getSpecifiedRankData($(this));
});





//placehoder问题

 /* var uidField=$("#uid"),pwdField=$("#pwd");
  var uidPlaceholder=$("#uidPlaceholder"),pwdPlaceholder=$("#pwdPlaceholder");

  pwdPlaceholder.live("focus",function(){  
    pwdPlaceholder.hide();     
    pwdField.show().focus().focus();
  });  
  
  uidPlaceholder.live("focus",function(){  
    uidPlaceholder.hide(); 
    uidField.show().focus().focus(); 
    
  });  

  pwdField.blur(function(){  
      if(pwdField.val() == '') {  
          pwdPlaceholder.show();  
          pwdField.hide();  
      }
       
  });

  uidField.blur(function(){  
      if(uidField.val() == '') {  
          uidPlaceholder.show();  
          uidField.hide();  
      }
      
      
  });    
*/



 
/*登陆开始*/
//左边登陆框登陆按钮

function leftSideLogin(){
  if(!isLeftLoginOk())
  {
    return false;
  }

  loginFunction($("#uid").val(),$("#pwd").val(),false);
  
}

$(".loginBtn").bind("click",leftSideLogin);

$("#pwd").bind("keydown",function(event){
   if (event.keyCode == '13') {
     event.preventDefault();
     leftSideLogin();
   }
})

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

//刷新页面后检测是否登陆以及获取最新的区服

$(function(){
	$.get(global.checkLogin+"?timestamp="+(+ new Date()),function(data){
		if(data.result==="true"){
			loginSuccess(data);
		}
	});
	


$.get(global.getLatestServerUrl,function(data){
    $("#quickregister").attr("href",global.actualGameUrl+data.id.toString()+"&timestamp="+(+new Date()));
    global.registerData={
      "lastGameZoneId":data.id,
      "lastGameZoneName":data.serverName
    }

  })
    //获取最新排行榜
    getRankData();
})


//检测服务区能否进入
function checkServerCanLogin(serverId)
{
  var flag=true;
  jQuery.ajax({
    url: global.checkLoginGame,
    type: 'POST',
    data: {"serverId": serverId },
    async:false,
    timeout:5000,
    success: function(data, textStatus, xhr) {
      if(data.code!==0){
          flag=false;
          alert(data.message);
      }
    },
    error: function(xhr, textStatus, errorThrown) {
      flag=false;
      alert("服务器连接超时,请稍后再试");
    }
  });
  return flag;
}


//登陆
function loginFunction(uid,pwd,isModal)
{
    $.post(global.loginUrl,{"inName":uid,"password1":pwd},function(data){
        if(data.result==="true")
        {
          loginSuccess(data);
          //是否是弹出层
          if(isModal){
            $(".popLogined").show();
            $(".popLogin").hide();
          }
        }
        else
        {
          if(isModal){
            $(".error-msg").text(data.respMsg);
          }
          else{
            alert(data.respMsg);
          }  
            

        }    
    })
}
//左侧登陆框条件判断
function isLeftLoginOk()
{
    var flag=true;
    var userNameVal=$("#uid").val(),pwdVal=$("#pwd").val();
 
    if(!userNameVal||!pwdVal)
    {
        alert("帐号或密码不能为空");
        return false;
    }

    
   
     

    return flag;
}

//成功之后
function loginSuccess(data)
{
   var lastGameZoneId = data.lastGameZoneId ? data.lastGameZoneId : $(".latestService .latest a").attr("serverid");
   var lastGameZoneName = data.lastGameZoneName ? data.lastGameZoneName : $(".latestService .latest a em").eq(0).text();
   var actualGameUrl=global.actualGameUrl+lastGameZoneId.toString()+"&timestamp="+(+new Date());
    //同步左边登陆栏
   $(".loginInfo").hide();
   $(".logined").show();

   $(".loginedInfo span").text(data.outName);
   $(".recentService a").attr("href",actualGameUrl).unbind('click').click(function(){
     return checkServerCanLogin(lastGameZoneId)
   }).text(lastGameZoneName)
   //同步顶部
   $(".topLoginInfo").hide();
   $(".topLoginedInfo").show().children("a.idx_name").text("欢迎您,"+data.outName);

   //同步弹出层
   $("#toploged_name").text(data.outName);
   $("a#toplastserverBtn").attr("href",actualGameUrl).children("strong.serverName").unbind('click').click(function(){
     return checkServerCanLogin(lastGameZoneId)
   }).text(lastGameZoneName)


   $("body").append(data.bbsData);//同步论坛

}


/*登陆结束*/

/*注销开始*/
$("a.indexLogout").bind("click",function(){
  $.post(global.logoutUrl,function(data){
    if(data.result==="true"){
      $(".loginInfo,.topLoginInfo").show();
      $(".logined,.topLoginedInfo").hide();
      $(".popLock,.popLogin,.popLogined,.popReg,error-msg").hide();
      $("body").append(data.bbsData);//同步论坛
    }
    else{
      alert("请稍后再试");
    }

  })
})

/*注销结束*/

/*注册开始*/
//注册
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
        $(".popClose").trigger("click");//注册成功后隐藏注册框
        $(".popMain2 input").val("").next("span").removeClass("yesTip");//注册成功后重置弹出层
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


//注册用户点击事件
$("#quickregister").bind("click",registerLogic);


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
    $("#toptab_1").text("字母开头，6-20个字母数字").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }

  if(regRepeat.test(userName.val()))
  {
    $("#toptab_1").text("用户名不能有5个重复字符").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }

  if(pwd.val()!==confirm_pwd.val())
  {
    $("#toptab_3").text("两个密码不一致").addClass("msg_red").removeClass("msg_default");;
    flag=false;
  }
  return flag;
}


//注册最新服的点击事件
$(".latestService ul li a").bind("click",function(){
    var flag=true;
    var serverid=$(this).attr("serverid");
    jQuery.ajax({
      url: global.checkLogin,
      async:false,
      cache:false,
      success: function(data, textStatus, xhr) {
        if(data.result==="false"){
          $(".popMain1 input").attr("value","");
          $(".error-msg").empty();
          $(".popLock,.popLogin").show(); 
          flag=false;
        }
        else{
         flag= checkServerCanLogin(serverid);
        }
      },
      error: function(xhr, textStatus, errorThrown) {
        flag=false;
        alert("服务器连接超时,请稍后再试");
      }
    });

    return flag;
    
})

//获取URL参数
function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
}

//判断是否从广告页进入
$(function(){
	var adId = getUrlParam("adId");
	var site = getUrlParam("site");
	if(adId != "" && adId != null && !isNaN(adId)){
		$.get(global.adStatisticsUrl + "?adId=" + adId + "&site=" + site,function(data){
		});
	}


})
