<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="神曲,神曲官网,神曲新手卡,神曲礼包,神曲职业"/>
<meta name="description" content="神曲官网-神曲一款角色扮演类的网页游戏，神曲首页，提供神曲下载，神曲BOSS攻略，神曲新手卡，神曲礼包，神曲职业，神曲论坛等一系列的详细游戏介绍，休闲娱乐，从神曲开始。" />
<title>神曲服务器列表_第七大道《神曲》官方网站</title>
<link rel="shortcut icon" href="http://image.7road.com/fav_icon.ico" />
<link href="${staticHost}/css/newServerList.css" rel="stylesheet" type="text/css" />
<link href="${staticHost}/css/top.css" rel="stylesheet" type="text/css" />
<link href="${staticHost}/css/nieCommon.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    
.popMain2 .loginRegTxt{color:#ff9307;font-size:14px; font-family: 微软雅黑,宋体; width: auto; text-align: left;}
.popMain2{ line-height:18px; font-family:微软雅黑,宋体;}
</style>
<!--[if IE]>
<script src="${staticHost}/js/html5.js"></script>
<![endif]-->
</head>


<body>
<!-- 顶部导航条start -->
<#include "*/common/headerNav.ftl">
<!-- 顶部导航条end -->


<div class="main">
	<div class="serverContent relative">
		<div class="backIndex"><a href="${serverHost}" class="a" title="官网首页">官网首页</a><a href="http://pay.7road.com/index.html?gameid=1&type=game" class="b" title="充值入口">充值入口</a></div>
        <div class="serverLeft zoom">
			<!--推荐服务器 start-->
           
            <#if serverListDesc?? && serverListDesc?size gt 0>

                    

                <#list serverListDesc as server>
                    <#if (server.recommand == 'true')>

                       <div class="recommendServer">
                            <a href="javascript:void(0);" id="${server.id}" class="serverBox_a left" title="推荐服务器">
	                            <strong class="serverName">${server.serverName}</strong>
	                            <span class="netDot"></span>
	                            <span class="hotDot"></span>
	                            <span class="hotTxt">今日火爆指数：</span>
	                            
	                            <#if (server.serverStatus == '-2')>
		                            <span class="hotIco9"></span>
		                            <span class="degree">100°</span>
		                            <span class="red" style="font-size:12px;">正在维护中</span>
	                            <#else>
		                            <span class="hotIco"></span>
		                            <span class="degree">100°</span>
		                            <span class="red_1">火爆开启</span>                              
	                            </#if>                        
                         	</a>
                        </div>
                        <#if server_index == 1>
                            <#break>
                        </#if>
                    </#if>
                </#list>
            </#if>
            
            <!--推荐服务器 end-->
            <div class="blank"></div>
            <div class="serverListNav right">
                <span class="txt">输入服数</span>
                <input type="text" value="<#if serverListDesc?? && serverListDesc?size gt 0><#list serverListDesc as server><#if (server.recommand == 'true' && server.serverStatus == '1')>${server.serverNo}<#break></#if></#list></#if>" class="serverListInput" maxlength="3" id="serverId_content" />
                <span class="txt">服</span>
                <a href="#" class="serverListEnter f12px" id="enter_game-btn" title="进入游戏">进入游戏</a>
            </div>
            <div class="switchWrap1 left">
                <#assign n=100>
                <div class="navTab">
                    <#if ((serverListDesc?size%n)?int != 0)>
						<#assign i=(serverListDesc?size/n)?int + 1>
					<#else>
						<#assign i=(serverListDesc?size/n)?int>
					</#if>										
					<#list 0..i-1 as t>	
						<a <#if t_index == 0>class="on"</#if> title="${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区" href="javascript:void(0)">${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区</a>
					</#list>
					
                </div>
                <div class="switchMainWrap zoom">
                    <div class="switchMain" style="display: block; ">
                        <#if serverListDesc?? && serverListDesc?size gt 0>
		                    	<#assign remainder=(serverListDesc?size %n)?int >
			                    <#list serverListDesc as server>	                    
		                    		<#if (server.serverStatus == '-2') >
				                    	<a href="#" id="${server.id}" class="server maintain" title="${server.serverName}">
		                                    <strong class="serverName">${server.serverName}</strong>
		                                    <span class="hotTxt">今日火爆指数：</span>
		                                    <span class="hotIco9"></span>
		                                    <span class="degree">90°</span>
		                                    <span class="red" style="font-size:12px;">正在维护中</span>
		                                </a>
		                   			<#elseif (server.serverStatus == '-1')>
		                    		<#else>
				                    	<a href="#" id="${server.id}" class="server" title="${server.serverName}">
		                                    <strong class="serverName">${server.serverName}</strong>
											 <#if (server.recommand == 'true' && server.serverStatus == '1')>
												<span class="netDot"></span>
		                                    	<span class="hotTxt">今日火爆指数：</span>
		                                    	<span class="hotIco"></span>
		                                    	<span class="degree">100°</span>
		                                    	<span class="red" style="font-size:12px;">火爆开启</span>	
											<#else>
		                                    	<span class="hotTxt">今日火爆指数：</span>
		                                    	<span class="hotIco9"></span>
		                                    	<span class="degree">90°</span>
		                                    	<span class="red" style="color:#C89C66; font-size:12px;">正常开启</span>
											</#if>	                                    
		                                </a>
		                      		</#if>
									<#if ((server_index+1) == remainder) || (((server_index+1)>=(remainder + n)) && ((server_index+1-remainder)%n == 0)) >
			                    			<div class="blank20"></div>
			                    		</div>
		                        		<div class="switchMain" style="display:none">
			                    	</#if>                                   
		                    	</#list>
	                    	</#if>            
                </div>
            </div>
        </div>
    </div>
        <div class="leftBtmBack"></div>
        <div class="serverRight">
        	<div class="loginRegTab loginRegTab1">
                <a href="javascript:void(0);" class="t1"></a>
                <a href="javascript:void(0);" class="t2"></a>
            </div>
            <div class="loginRegContentWrap">
                <div class="loginRegContent">
                    <!--登陆start-->
                            <div id="tabLogin" class="login">
                                <table border="0">
                                    <tr>
                                        <td class="loginRegTxt">帐&nbsp;&nbsp;&nbsp;号&nbsp;</td><td><input type="text" class="inputTxt2" id="log_userName" maxlength="20" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" /></td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab1_1" class="msg_default">字母开头，6-20个字母数字</span></td>
                                    </tr>
                                    <tr>
                                        <td class="loginRegTxt">密&nbsp;&nbsp;&nbsp;码&nbsp;</td><td><input type="password" class="inputTxt2" id="log_password" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  maxlength="20"  /></td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab1_2" class="msg_default">长度为6-20位</span></td>
                                    </tr>
                                    <!--tr id="log_verify_tr" style="display:none">
                                        <td class="loginRegTxt">验证码&nbsp;</td><td><input type="text" class="in2" id="log_verifyCode" maxlength="4" /><img id="log_verifyCodeImg" style="vertical-align:middle;cursor:pointer;padding-left:5px" src="${serverBasePath}/captcha.do" width="58px" height="25px"  class="logpic" onClick="javascript:this.src='${serverBasePath}/captcha.do?'+(new Date().getTime());">
                                            <a class="changePic" href="javascript:refleshVerifyCode('log_verifyCodeImg')">换一张</a>
                                        </td>
                                    </tr>
                                    <tr id="log_verify_tr2" style="display:none">
                                    </tr-->
                                    <tr>
                                        <td colspan="2">
                                            <a href="javascript:void(0);" class="loginBtn" id="btntoLogin"></a>
                                        </td>
                                    </tr>
                                </table>
                                <div class="log_message">
                                    <span id="tab1_3" style='display:none;'>登陆中...</span>
                                </div>
                                <div class="regForget">
                                    <a href="#" title="快速注册" id="log_quickReg">快速注册</a>
                                    <a href="http://account.7road.com/forget.html" target="_blank" title="忘记密码">忘记密码</a>
                                </div>
                            </div>
                            <!--登陆end-->
        
                            <!--已登陆start-->
                            <div id="tabLogined" class="logined" style="display:none;font-size:14px;">
                                <div class="">
                                    <span style="color:#ff9507;font-weight:bold;" id="loged_name"></span>, 您好！
                                    [<a style="color: #ff9507;font-weight: bold;" href="javascript:void(0);" id="login-out">退出</a>]<!--slLogout-->
                                </div>
                                <a href="${serviceCenterPath}" target='_blank' class="btn2">完善密保资料</a>
                                <div class="" style="font-size:13px;color:#ffde00;"><a href='${serviceCenterPath}' target='_blank' id='bindEmail'>请绑定邮箱确保帐号安全！</a></div>
                                <div class="" style="margin-top:5px; color:#c89b65">您上次登陆的服务器是：</div>
                                <div class="recommendServer" style="margin:10px 0 0 0; width:240px; border:1px solid #470e0a; background-color:#1b0000;">
                                    <a href="javascript:void(0);" class="btn3" id='lastserverBtn'>
                                        <strong id="lastserver" class="serverName" style="color:#ed9600; height:28px;">还没有登录游戏</strong>
                                        <span class="hotTxt" style="color:#ed9600">今日火爆指数：</span>
                                        <span class="hotIco9"></span>
                                        <span class="hotDot"></span>
                                        <span class="netDot" style="margin: 0; position: absolute; top: 9px; left: 155px; *left: 155px;"></span>
                                        <span class="degree" style="color:red">90°</span>
                                        <span class="red f12px" style="color:red">正常开启</span>
                                    </a>
                                </div>
                                <div id='loged_hidden'></div>
                            </div>
                            <!--已登陆end-->
                            
                            <!--注册start-->
                            <div id="tabReg" class="reg" style="display:none;">
                                <table border="0">
                                    <tr>
                                        <td class="loginRegTxt">帐&nbsp;&nbsp;&nbsp;号&nbsp;</td><td><input type="text" class="inputTxt2" id="reg_userName" onBlur="userNameOnBlur()" maxlength="20" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  onkeyup="value=value.replace(/\s/g,'')"  /></td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab2_1" class="msg_default">字母开头，6-20个字母数字</span></td>
                                    </tr>
                                    <tr>
                                        <td class="loginRegTxt">密&nbsp;&nbsp;&nbsp;码&nbsp;</td><td><input type="password" class="inputTxt2" id="reg_password1" onBlur="password1OnBlur()"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  maxlength="20"  onkeyup="value=value.replace(/\s/g,'')"  /></td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab2_2" class="msg_default">长度为6-20位</span></td>
                                    </tr>
                                    <tr>
                                        <td class="loginRegTxt">确&nbsp;&nbsp;&nbsp;认&nbsp;</td><td><input type="password" class="inputTxt2" id="reg_password2" onBlur="password2OnBlur()"  onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  onkeyup="value=value.replace(/\s/g,'')" maxlength="20" /></td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab2_3" class="msg_default">长度为6-20位</span></td>
                                    </tr>
                                    <tr class="height20">
                                        <td class="loginRegTxt">验证码&nbsp;</td>
                                        <td>
                                        <input type="text" class="in2" id="reg_verifyCode" maxlength="4" />
                                        <img id="reg_verifyCodeImg" style="vertical-align:middle;cursor:pointer;padding-left:5px" src="${serverBasePath}/captcha.do" width="58px" height="25px"  class="logpic" onClick="javascript:this.src='${serverBasePath}/captcha.do?'+(new Date().getTime());">&nbsp<a class="changePic" href="javascript:refleshVerifyCode('reg_verifyCodeImg')">换一张</a>
                                        </td>
                                    </tr>
                                    <tr class="height20">
                                        <td></td><td>&nbsp;<span id="tab2_4" class="msg_default"></span></td>
                                    </tr>
                                    <tr>
                                      <td colspan="2" align="center" style="color:#555; padding-top:10px;"><input class="checkbox" type="checkbox" name="checkbox" id="checkbox" checked="true" />
                                        <span style='color:#9b6e2e;font-size:12px;'>同意并接受</span>&nbsp;<a class="serviceXie" href="${serviceCenterPath}/register_notice.html" target='_blank'>《用户注册服务协议》</a></td>
                                      </tr>
                                    
                                      <tr>
                                        <td colspan="2">
                                            <a href="javascript:void(0);" class="regBtn" id="btntoreg"></a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" style="color: #ffde00;text-align: center; ">&nbsp;<span id="tab22_4" class='reg_message' style='display: none; font-size: 12px;  '>注册中...</span></td>
                                    </tr>
                                </table>
                            </div>
                            <!--注册end-->
                    <div class="blank"></div>
                </div>
            </div>

            <div class="loginBoxBtm"></div>
            <div class="rightMiddle">
                <div class="latestNews" id="tab_new_list">
                    <h2><a href="http://sq.7road.com/news/newsList.html?searchVal=bulletin" target="_blank">+MORE</a></h2>
                    <ul class=" mt_5">
                            <#list serverListNewsList?sort_by(["createDate"])?reverse as new>
								<li><span >[${new.createDate?string("MM-dd")}]</span><a href="news/detail/${new.typeId}_${new.newsId}.html#A0" target='_blank'>${new.artTitle}</a></li>
							</#list>
                    </ul>
                </div>
                <div class="therrBtn">
                    <a href="http://sq.7road.com/others/newbGift.html" target="_blank" class="a"></a>
                    <a href="http://sq.7road.com/activity/OpenGameZone/index.html" target="_blank" class="b"></a>
                    <a href="http://pay.7road.com/?gameid=1&type=game" target="_blank" class="c"></a>
                </div>
                <div class="serviceTel zoom">
                  	  <a  href="javascript:void(0)" style="cursor:default"><span>第七大道客服热线</span><br /><strong>0755-61886777</strong></a>
                      <a  href="http://crm2.qq.com/page/portalpage/wpa.php?uin=800073277&f=1&ty=1&aty=0&a=&from=6" target="_blank" style=" margin-bottom:12px;"><span>第七大道QQ在线客服</span><br /><strong>(24小时在线客服)</strong></a>
                      <a  href="mailto:kefu@7road.com" ><span>客服投诉邮箱</span><br /><strong>kefu@7road.com</strong></a>
                </div>
                  
            </div>
        </div>
        
    </div>
</div>

<style type="text/css">

/*底部*/
.footerWrap{clear:both;height:150px;margin-top:30px;background:url(http://image.7road.com/footerBg-x.jpg) repeat-x;}
.footer{width:1000px;padding-top:25px;margin:0 auto;line-height:18px;}
.footer .footerLogo{float:left;width:205px;height:63px;margin:0 0 0 100px;display:inline;}
.footer .footerLogo a{display:block;width:205px;height:63px;background:url(http://image.7road.com/footerLogo.jpg) no-repeat;text-indent:-999em;overflow:hidden;}
.footer .footerNav{float:left;padding:0 0 0 30px;color:#b8b8b8;overflow:hidden;font-size:12px;border-left:1px solid #e8e8e8;}
.footer .footerNav a{margin:0 6px;color:#212121;font-family:Arial,'微软雅黑';}
.footer .footerNav p{padding-left:5px;color:#646464;font-family:Arial,'微软雅黑';}
.helpInfo{padding:10px 0 0 0px;}
.helpInfo a{float:left;margin:0 10px 0 0;}
</style>
<!-- 底部start -->


<div id="platformFooter"></div>
<script type="text/javascript" src="http://static.7road.com/platform/js/src/platformCommonFooter.js"></script>
<!-- 底部end -->

<a href="#" class="openGameUrl" target="_blank"><span></span></a>



<div id='hidden' style='display:none;'></div>
</body>
<script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"></script>
<script type="text/javascript">
$(function(){
    var recommendServerNum = $(".serverLeft .recommendServer").length;
    if(Number(recommendServerNum)==2){
        $(".serverLeft .recommendServer").css("margin-left","26px")
    }
})
</script>

<script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticHost}/js/tinybox.js"></script>
<script type="text/javascript" src="${staticHost}/js/config.js?v=${version}"></script>
<script type="text/javascript" src="${staticHost}/js/global.js?v=${version}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/user.js?v=${version}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/game.js?v=${version}" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/switch.js"  charset="UTF-8"></script>
<script type="text/javascript">
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
    "gameUrl":"http://account.7road.com/PlayGame2?g=1&game=S&subGame=0&z="

}

$(function(){

	$('.loginRegTab a').click(function(evt){
		var o = $(evt.currentTarget);
		if(o.attr('class')=='t1'){
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
		}else{
			o.parent().addClass('loginRegTab2').removeClass('loginRegTab1');
			$('#tabLogin').hide();
			$('#tabLogined').hide();
			$('#tabReg').show();
			$("#reg_userName").focus();
		}
	});
	
	$('#log_quickReg').click(function(){
			$('.t1').parent().addClass('loginRegTab2').removeClass('loginRegTab1');
			$('#tabLogin').hide();
			$('#tabLogined').hide();
			$('#tabReg').show();
			$("#reg_userName").focus();
			return false;
	});
    $(".inputTxt2").focus(function(){
        $(this).css("border","1px solid #0fbaed");
    });
    $(".inputTxt2").blur(function(){
        $(this).css("border","1px solid #015295");
    });
        
    $(".in2").focus(function(){
        $(this).css("border","1px solid #0fbaed");
    });
    $(".in2").blur(function(){
        $(this).css("border","1px solid #015295");
    });
    $(".navTab a").hover(function(){
        var index = $(".navTab a").index(this);
        $(this).addClass("on").siblings().removeClass("on")
        $(".switchMain").eq(index).show().siblings(".switchMain").hide();
    }) ;
    $.get(global.getLatestServerUrl,function(data){
        global.registerData={
            "lastGameZoneId":data.id,
            "lastGameZoneName":data.serverName
        }
    })

    //注册开始
    /*注册开始*/
//注册

var unauthorizedNames=['gamemaster','gm','shit','bitch','fvc','phuc','fuk','shenqu','fuck','admin','7road'];
//判断用户名js验证
$("#topreg_userName").bind("blur",function(){
  var username=$(this);
  var flag=true;
  for(i=0;i<unauthorizedNames.length;i++){
    if(username.val().indexOf(unauthorizedNames[i])==0){
        $("#toptab_1").text("用户名含有非法字符").addClass("msg_red").removeClass("msg_default");
        username.next("span").addClass("noTip").removeClass("yesTip");
        
    }
  };
  if(!username.val())
  {
    $("#toptab_1").text("用户名不能为空").addClass("msg_red").removeClass("msg_default");
    username.next("span").addClass("noTip").removeClass("yesTip");
  }
  else
  {
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
        event.preventDefault();
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




function registerFunction(uid,pwd,confirm_pwd,captcha)
{
  var flag=true;
  $("#toptab_4").show().text("注册中...");

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

        loginSuccess(data);
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



function registerLogic(){

  var userName=$("#topreg_userName");
  var pwd=$("#topreg_password1");
  var confirm_pwd=$("#topreg_password2");
  var captcha=$("#topreg_verifyCode");
  if($("#topcheckbox").attr("checked")!="checked"){
    alert("您还没有选择同意并接受用户注册协议书");
    return false;
  }else if($(".popMain2 span").hasClass("msg_red")){
    return false;
  }else if(isRegisterOk(userName,pwd,confirm_pwd,captcha)){
    var flag= registerFunction(userName.val(),pwd.val(),confirm_pwd.val(),captcha.val());
    $(".popClose").trigger("click");//注册成功后隐藏注册框
    return flag;
  }else{
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
          $("#topreg_verifyCodeImg").click()
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
    var serverUrl=this.href+"&timestamp="+(+new Date());
    var flag=true;
    jQuery.ajax({
      url: global.checkLogin,
      async:false,
      success: function(data, textStatus, xhr) {
        if(data.result==="false"){
          $(".topLogin").trigger("click");
          flag=false;
        }
        else{
         flag= checkServerCanLogin(serverUrl);
        }
      },
      error: function(xhr, textStatus, errorThrown) {
        flag=false;
        alert("服务器连接超时,请稍后再试");
      }
    });

    return flag;
    });
});
function tip1(index, msg){
	$('#tab1_'+index).html(msg);
}
function tip2(index, msg){
	$('#tab2_'+index).html(msg);
}




function loginSuccess(data)
{
    var serverUrl="http://account.7road.com/PlayGame2?g=1&game=S&subGame=0&z="+global.registerData.lastGameZoneId.toString()+"&timestamp="+(+new Date());
    var actualGameUrl="http://sq.7road.com/gameStart/gameStart.html?g=1&serverId="+global.registerData.lastGameZoneId.toString()+"&timestamp="+(+new Date());   
    //同步顶部
    $(".topLoginInfo").hide();
    $(".topLoginedInfo").show();
    $(".topLoginedInfo .idx_name").text("欢迎您,"+data.outName);
    //同步论坛方面
    $('#hidden').html(bbsLoginMsg);
    
    //同步左侧注册成功之后
    $('#loged_name').html(data.outName);
    $('#lastserver').html(newestRecomendServerName);
    $('#lastserverBtn .hotIco9').removeClass('hotIco9').addClass('hotIco');
    $('#lastserverBtn .degree').empty().text('100°');
    $('#lastserverBtn .red:last').empty().text('火爆开启');
    $('#lastserver').attr('href', actualGameUrl);     
    $('#tabReg').hide();
    $('#tabLogin').hide();
    $('#tabLogined').show();   
    $('.t2').unbind('click')
    $(".popMain2 input").val("").next("span").removeClass("yesTip");
    $(".popReg").hide();
   //注册成功后打开游戏页
    $("#lastserverBtn").attr({"target":"_blank","href":serverUrl,"actualGameUrl":actualGameUrl});
    $("#lastserver").click();
}
//检测服务区能否进入

var idList=new Array(<#list serverListAsc as server>${server.id}, </#list>0);
</script>

<script type="text/javascript" src="${staticHost}/js/serverList.js?v=${version}"  charset="UTF-8"></script>
<!--[if ie 6]>
<script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js" ></script>
<script type="text/javascript"> 
    DD_belatedPNG.fix('.serverLeft');
    DD_belatedPNG.fix('.loginRegTab');
    DD_belatedPNG.fix('.loginRegTab2');
    DD_belatedPNG.fix('.rightMiddle');
</script
<![endif]-->
</html>
