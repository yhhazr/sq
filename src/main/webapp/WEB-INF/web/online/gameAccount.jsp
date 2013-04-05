<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sz7road.web.model.gamemanage.GameInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<html>
<head>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html;charset=utf-8"%> 
<%
	String baseURL=SystemConfig.getProperty("online.homepage.baseURL");
	String path = request.getContextPath();
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>充值中心| webgame-37wan网页游戏平台</title>
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/37wan.css" type="text/css" />
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/2010pay.css" type="text/css"/>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/jquery.js"></script>
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/alert.css" type="text/css"/>
<link href="<%=path %>/static/style/formValidatorStyle.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" charset="UTF-8" href="http://www.37wan.com/37wancss/auto_scroll.css" type="text/css"/>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/alert.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/alert.bgiframe.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/md5.js"></script>

<base target="_self" />
</head>
<body><div id="top">
    <div id="top_content">
        <img id="speaker" src="<%=baseURL%>/images/speaker.gif" border="0" />
        <div id="top_message"><ul id="scroll_ul"></ul></div>
        <a href="http://www.37wan.com/"><img id="home" src="<%=baseURL%>/images/home.gif" border="0" /></a>
        <div id="top_nav"><a href="http://www.37wan.com/users/register.php">用户注册</a> | <a href="http://www.37wan.com">用户登录</a> | <a href="javascript:window.external.AddFavorite('http://www.37wan.com','37wan网页游戏平台')" target="_self">收藏本站</a> | <a href="#" target="_self" style="BEHAVIOR: url(#default#homepage)" onclick="this.style.behavior='url(#default#homepage)';this.setHomePage('http://www.37wan.com');">设为首页</a> 37wan </div>
    </div>
</div>

<script type="text/javascript" src="http://www.37wan.com/js/auto_scroll.js" charset="UTF-8"></script>
<%-- <script type="text/javascript" src="<%=baseURL%>/js/jquery.validate.js"></script> --%>
<script type="text/javascript" src="<%=baseURL%>/js/messages_cn.js"></script>
<script type="text/javascript">

$(document).ready(function(){

//初始值
var gameObj=$("#game_id");
$("#coin_desciption").find("option:selected").empty().text("对应"+gameObj.attr("unitName"));
$("#scale").find("option:selected").empty().text(gameObj.attr("scale"));
	$.ajax({
            url:"getGameServer.action",
            type: "GET",
            data:{gameId:gameObj.val()},
            dataType:'json',
            success:function(json) {
            //empty
            $("#server_id").empty();
            var html="<option value=0>请选择服务器</option>";
               var obj=json.serverList;
               for(i=0;i<obj.length;i++)
               {
               	html+="<option value="+obj[i].id+">"+obj[i].serverName+"</option>";
               }
               $("#server_id").append(html);
       }
     });
	$("#game_id").change( function() {
		var temp=$(this).val();
		var unitName=$(this).find("option:selected").attr("unitName");
		var scale=$(this).find("option:selected").attr("scale");
		$.ajax({
            url:"getGameServer.action",
            type: "GET",
            data:{gameId:temp},
            dataType:'json',
            success:function(json) {
            //empty
            $("#server_id").empty();
            var html="<option value=0>请选择服务器</option>";
               var obj=json.serverList;
			   var len=json.serverList.length;
			   if(len<1) return;
               for(i=0;i<len;i++)
               {
               	html+="<option value="+obj[i].id+">"+obj[i].serverName+"</option>";
               }
               $("#server_id").append(html);
			   $("#coin_desciption").empty().text("对应"+unitName + "：");
			   $("#scale").empty().text(scale);
			   $("#coin").val($("#card_money").val()*$("#scale").text());
            }
   		 }); 		
	 });
	$("#refresh_actor_name").click(function(){
	    // getSQ_Actorname(); 
	    getRoleList();
	 });
});




//获取角色
function getRoleList()
{	
	
	var uname=$("#username").val();
	if(uname=="" || uname==null)
	{
			alert("用户名不能为空!!");
			return ;
	}
	var game=$("#game_id").val();
	if(game=="" || null == game){
		alert("请选择游戏!!");
		return ;
	}
	var server = $("#server_id").val();
	if(document.getElementById("server_id").options[0].selected){
		alert("请选择服务器!!");
		return ;
	}
	var url="getRoleList.action";
	$.ajax({
		url:url,
		data:{username:uname,site:'sqtest_0001'},
		dataType:'json',
		success:function(data)
		{	
		var list=data.roleList;
		var len=list.length;
		if(len<1){ 
			alert("您还未创建角色!!");
			return;
		};
		$("#actor_name").empty();
		$("#actor_name").append("<option value='select'>请选择角色</option>");
			for(i=0;i<len;i++)
				$("#actor_name").append("<option value='"+i+"'>"+list[i]+"</option>");
		}
	});	
	//金币的换算
	$("#card_money").change(function(){
		$("#coin").val($(this).val()*$("#scale").text());
	});
}

	
	//特殊字符
	$("#username,#username_1").keyup(function () {
		var str = ['@', '#', '$', '%', '^', '&', '*', '<', '>', '/','.'];
             for (var i = 0; i < str.length; i++) {
                if ($(this).val().indexOf(str[i]) >= 0) {
                    alert("输入内容不能包含： '" + str[i] + "'  字符！");
                      $(this).val($(this).val().replace(str[i], ""));
                      return;
                }
             }
       });
	



//用户验证
function userNameOnBlur(){
	var nickname = document.getElementById("username");
	if(nickname.value == "" || nickname.value == null){
		$('#userNameTip').empty();
		nickname.className = "g-ipt";
		return;
	}
	if(nickname.value.length > 50 || nickname.value.length < 6){
		$('#userNameTip').empty().html("<span class='font_1'><B class='ico-warning'></B><SPAN class=txt-err>用户名长度应在6~50个字符</SPAN></span>");
		nickname.className = "g-ipt-err";
	}else{
		var url = "onlineGame/checkAccountUserName.action?gameAccountOrder.userName="+nickname.value;
		$.get(url,function(data){
			if(data.result == "true"){
				$('#userNameTip').empty().html("<span class='font_1'><B class='ico-succ'></B><SPAN class=txt-succ>该用户名存在</SPAN></span>");
				nickname.className = "g-ipt";
			}else{
				$('#userNameTip').empty().html("<span class='font_1'><B class='ico-warning'></B><SPAN class=txt-err>用户名不存在，请确认后输出</SPAN></span>");
				nickname.className = "g-ipt-err";
			}
		});
	}
}

function userNameOnFocus(){
	var nickname = document.getElementById("username");
	nickname.className = "g-ipt-active";
	$('#userNameTip').empty();
}

function userName1OnFocus(){
	var password2 = document.getElementById("username_1");
	password2.className = "g-ipt-active";
	$('#userName1Tip').empty();
}
function userName1OnBlur(){
	var username = document.getElementById("username");
	var username1 = document.getElementById("username_1");
	if(username1.value == "" || username1.value == null){
		$('#userName1Tip').empty();
		username1.className = "g-ipt";
		return;
	}
	if(username1.value != username.value){
		$('#userName1Tip').empty().html("<span class='font_1'><span class='ico-warning'></span><SPAN class=txt-err>两次输入账号不一致</SPAN></span>");
		username1.className = "g-ipt-err";
	}else{
		$('#userName1Tip').empty().html("<span class='font_1'><span class='ico-succ'></span><SPAN class=txt-succ>两次输入账号相同</SPAN></span>");
		username1.className = "g-ipt";
	}
}

//金额验证
$("#money").keyup(function () {
	
	});
	
function checkMoney(){
   var money= document.getElementById("money");
   var regx = new RegExp("^(([1-4][0-9]{0,4})|(5[0-9]{0,3})|50000)$"); 
   if(!regx.test(money.value)){
	   $("#moneyTip").html("<span class='noticeWrap'><span class='ico-warning'></span><SPAN class=txt-err>请输入1~50000的整数</SPAN></span>");
	   if(!document.getElementById("card_money").options[0].selected){
		   $('#moneyTip').empty().html("<span class='font_1'>请在此输入你想充值的金额(范围1~50000的整数)</span>");
	   }   
   }else{
	   document.getElementById("card_money").options[0].selected = true;
	   $('#moneyTip').empty().html("<span class='font_1'>请在此输入你想充值的金额(范围1~50000的整数)</span>");
	   $("#coin").val(money.value * $("#scale").text());
   }
}

function moneyOnFocus(){
	var nickname = document.getElementById("money");
	$('#moneyTip').empty().html("<span class='font_1'>请在此输入你想充值的金额(范围1~50000的整数)</span>");
}

//显示或隐藏其他金额的输入
function moneyChange(){
	if(!document.getElementById("card_money").options[0].selected){
		$("#coin").val($("#card_money").val()*$("#scale").text());
		$("#money").val("");
		moneyOnFocus();
	}else{
		checkMoney();
	}
}

//检测角色
function selectRole(){
	var role = $("#actor_name");
	if(role.val() == null || role.val == "select" || role.val == ""){
		$("#gameRoleTip").empty().html("<span class='noticeWrap'><span class='ico-warning'></span><SPAN class=txt-err>请选择角色</SPAN></span>");
	}else if(document.getElementById("actor_name").options[0].selected){
		$("#gameRoleTip").empty().html("<span class='noticeWrap'><span class='ico-warning'></span><SPAN class=txt-err>请选择角色</SPAN></span>");
	}else{
		$("#gameRoleTip").empty();
	}
}

</script>
<div id="logo_banner">
    <div id="logo"><img src="<%=baseURL%>/images/logo.jpg" border="0" /></div>
    <div id="banner">
        <img  alt="37wan" width="700" height="100" border="0" />
    </div>
</div>
<div id="nav">
    <img src="http://www.game7road.com/images/nav_left.jpg" border="0" />
    <ul>
        <li class="home"><a href="http://www.game7road.com/index.html" target="_self">首&nbsp;&nbsp;页</a></li>
        <li class="menu"><a href="http://www.game7road.com/game7road/userCenter.action" target="_self">用户中心</a></li>
        <li class="hot"><a href="http://www.game7road.com/serverList.html">游戏中心</a></li>
        <li class="menu"><a href="http://www.game7road.com/news/list/list_news.html">新闻资讯</a></li>
        <li class="menu"><a >游戏攻略</a></li>
        <li class="menu"><a >游戏心情</a></li>
        <li class="hot"><a href="http://www.game7road.com/step1.html">充值中心</a></li>
        <li class="menu"><a >客服中心</a></li>
        <li class="menu"><a href="http://forum.game7road.com">官方论坛</a></li>
        <li class="hot"><a href="http://www.game7road.com/serverList.html">服务器列表</a></li>
    </ul>
    <img src="http:www.game7road.com/images/nav_right.jpg" border="0" />
</div>

<div id="recommend">
    <span class="recommend_game">&nbsp;&nbsp;最新游戏推荐：</span>
	<a href="http://long.37wan.com/" class="hot">龙将</a> 
	<a href="http://sq.37wan.com/" class="hot">神曲</a> 
	<a href="http://sg.37wan.com/" class="hot">盛世三国</a> 
	<a href="http://sxd.37wan.com/" class="hot">神仙道</a> 
	<a href="http://lc.37wan.com/" class="hot">龙城</a> 
	<a href="http://jxqy.37wan.com/" class="hot">剑侠奇缘</a> 
	<a href="http://aszt.37wan.com/" class="hot">傲视遮天</a> 
	<a href="http://www.37wan.com/mhfx/" class="hot">梦幻飞仙</a> 
	<a href="http://chumo.37wan.com/" class="hot">除魔</a> 
	<a href="http://frxz2.37wan.com/">凡人修真2</a> 
	<a href="http://www.37wan.com/yxwz/">英雄王座</a> 
	<a href="http://csxy.37wan.com/">创世仙缘</a> 
	<a href="http://www.37wan.com/xksj/">侠客世界</a> 
	<a href="http://mogong.37wan.com/">墨攻</a> 
	<a href="http://hysj.37wan.com/">火影世界</a> 
	<a href="http://dsz.37wan.com/">大商战</a> 
	<a href="http://xl.37wan.com/">降龙十八掌</a> 
	<a href="http://www.37wan.com/game_list.php">更多&gt;&gt;</a>
</div><div class="pay_main">
	<div class="pay_step">
	<div class="step_img"><img src="<%=baseURL%>/images/2010step_1_0.jpg" width="155" height="66" border="0" /></div>
<div class="step_arrow"></div>
<div class="step_img"><img src="<%=baseURL%>/images/2010step_2_0.jpg" width="155" height="66" border="0" /></div>
<div class="step_arrow"></div>
<div class="step_img"><img src="<%=baseURL%>/images/2010step_3_1.jpg" width="155" height="66" border="0" /></div>
<div class="step_arrow"></div>
<div class="step_img"><img src="<%=baseURL%>/images/2010step_5_0.jpg" width="155" height="66" border="0" /></div>	</div>
	<div class="pay_middle">
		<div class="paystep3_content">
			<div class="header">
			</div>
			<div class="middle">
				<div class="payinput_payway">
	<ul>
		<li class="header"></li>
										 <li class="selectway">
				 				 <a href="javascript:void(0);" target="_self" onclick="changePayWay(25);">
				 			 <span>银行卡支付 
						<s:iterator value="#request.gameList" id="user" status="u">  
       
               				 <s:property value="user.gameId" />  
             
               			</s:iterator></span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="<%=path %>/WEB-INF/web/online/gameAccount.jsp?step=3&pay_way_id=1&username=" target="_self" onclick="changePayWay(1);">
				 			 <span>银行卡支付(易宝)
				 			 </span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=7&username=" target="_self" onclick="changePayWay(7);">
				 			 <span>支付宝余额支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=24&username=" target="_self" onclick="changePayWay(24);">
				 			 <span>神州行(神州付)支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=2&username=" target="_self" onclick="changePayWay(1);">
				 			 <span>神州行(易宝)支付</span>
			 </a>
			 </li>
			 		 										<!--空-->
					 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=23&username=" target="_self">
				 			 <span>骏网卡官方直充</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=4&username=" target="_self">
				 			 <span>盛大游戏卡支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=14&username=" target="_self">
				 			 <span>神州行(快钱)支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=10&username=" target="_self">
				 			 <span>联通卡支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=18&username=" target="_self">
				 			 <span>网易卡</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=19&username=" target="_self">
				 			 <span>完美卡</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=12&username=" target="_self">
				 			 <span>征途卡</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=20&username=" target="_self">
				 			 <span>天下一卡通</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=17&username=" target="_self">
				 			 <span>全国固话充值(V币)</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=29&username=" target="_self">
				 			 <span>电信固话手机充值</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=5&username=" target="_self">
				 			 <span>168固话支付</span>
			 </a>
			 </li>
			 		 										 <li class="way">
				 				 <a href="/index.php?step=3&pay_way_id=13&username=" target="_self">
				 			 <span>人工汇款充值</span>
			 </a>
			 </li>
			 		 										<!--空-->
					 																																											</ul>
</div>				

				<div class="payinput_bg">
					<jsp:include page="/WEB-INF/web/online/gameAccount/bank_card.jsp" flush="true" />
					
				</div>
			</div>
			<div class="bottom"></div>
		</div>
		<div class="pay_right">
		<div class="pay_notice">
	<div class="frame_title">
		<div class="left"></div>
		<div class="content">充值公告</div>
		<div class="right"></div>
	</div>
	<div class="frame_content">
		<ul id ="pay_notice_content">
		</ul>
	</div>
</div>
<div class="pay_ptcoin_img">
	<a href="/index.php?step=2&pay_for=platform" ><img src="<%=baseURL%>/images/2010pay_platform_coin.jpg" border="0px"/></a>
</div>
<div class="pay_faq">
	<a href="/help.php?helpname=faq" target="_blank"><img src="<%=baseURL%>/images/2010pay_faq.jpg" border="0px"/></a>
</div>
<div class="pay_service">
	<div class="frame_title">
		<div class="left"></div>
		<div class="content">客服热线</div>
		<div class="right"></div>
	</div>
	<div class="frame_content">
		<div class="content" >
		<img src="<%=baseURL%>/images/ico2.jpg" />充值咨询：<a target="_blank" href="http://www.37wan.com/service.html"><img border="0" alt="点击这里给我发消息" src="http://www.37wan.com/<%=baseURL%>/images/online.gif" /></a><br/>
		<img src="<%=baseURL%>/images/ico3.jpg" />客服电话：020-85553223<br/>&nbsp;&nbsp;&nbsp;&nbsp;(7×24小时)<br/>
		<img src="<%=baseURL%>/images/ico1.jpg" />客服传真：020-85524832<br/>
	    </div>
	</div>
</div>		</div>
	</div>
</div>
<div id="fullBg"></div>
<div id="msg">
<div id="msgcon">
	<div id="msgtop">
		<div id="text">充值订单确认</div>
		<div id="close"><img src="<%=baseURL%>/images/pop/pop_close_1.gif" alt="关闭" width="15" height="15" id="cPic" /></div>
	</div>
	<div id="ctt">
		<div id="al_content">
			<form name="form_pop" action="/platform/pay_req.php" method="post">
			<table width="95%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#99CCFF" id="empList" style="margin-top:10px">
			<tr>
			<td height="30" colspan="2" align="left"><span style="color:#FE7313;">&nbsp;*&nbsp;</span>温馨提示：确认提交后请勿刷新页面以免充值失败！</td>
			</tr>
			<tr>
			<td height="30" align="right">充值方式&nbsp;</td>
			<td height="30"  align="left">&nbsp;平台币支付</td>
			</tr>
			<tr>
			<td height="30" align="right">充值游戏&nbsp;</td>
			<td height="30"  align="left">&nbsp;<span id="pop_game_name"></span></td>
			</tr>
			<tr>
			<td height="30" align="right">充值游戏服&nbsp;</td>
			<td height="30"  align="left">&nbsp;<span id="pop_game_server_name"></span></td>
			</tr>
			<tr>
			<td height="30" align="right">充入帐号&nbsp;</td>
			<td height="30"  align="left">&nbsp;<span id="pop_user_name"></span></td>
			</tr>
			<tr>
			<td height="30" align="right">充值面额&nbsp;</td>
			<td height="30"  align="left">&nbsp;<span id="pop_pingtai_coin"></span>平台币</td>
			</tr>
			<tr>
			<td height="30" align="right">获得游戏币&nbsp;</td>
			<td height="30"  align="left">&nbsp;<span id="pop_game_coin"></span></td>
			</tr>
			<tr>
			<td height="30" align="right">平台币支付密码&nbsp;</td>
			<td height="30"  align="left">&nbsp;<input type="password" id="pay_password" size="25" /><span style="color:#FE7313;">&nbsp;*&nbsp;</span><a href="http://www.37wan.com/users/userpassword2.php" target="_blank">没有平台币支付密码？马上设置</a></td>
			</tr>
			<tr>
			<td height="30" colspan="2" align="center"><input name="btnSubmit" type="button" class="btnblue" value="确认提交" onclick="checkPopSubmit()" /></td>
			</tr>
			</table>
			</form>
		</div>
	</div>
	</div>
</div>
<!--平台币充值msg-->
<div class="footer_info">
版权所有 37wan<a href="http://www.37wan.com" target="_blank"><font color="#E6630B"><strong>网页游戏</strong></font></a>&nbsp;&nbsp;
<a href="http://www.37wan.com/html/aboutus/" target="_blank">公司介绍</a> | 
<a href="http://www.37wan.com/html/business/" target="_blank">商务合作</a>  | 
<a href="http://www.37wan.com/html/service/" target="_blank">客服中心</a> | 
<a href="http://bbs.37wan.com" target="_blank">游戏论坛</a>| 
<a href="http://www.37wan.com/html/job/" target="_blank">人才招聘</a><br />
广州海岩网络科技有限公司 
<a href="http://www.37wan.com/www.php" target="_blank">文网文[2009]280号</a> 
粤ICP备08038385号 
<a href="http://www.37wan.com/icp.php" target="_blank">增值电信业务经营许可证粤B2-20065178</a>
<a href="http://www.37wan.com/cwz.php" target="_blank">新出网证(粤)字041号</a>
<br />24小时客服电话：020-85553223　
文化部网络游戏举报和联系电子邮箱：wlwh@vip.sina.com 纠纷处理方式：联系客服或依《用户协议》约定方式处理<br/>
<a href="http://210.76.65.188/webrecord/innernet/Welcome.jsp?bano=4408013012594" target="_blank">
<img style="border:0 none" src="<%=baseURL%>/images/waicon.gif" height="25"/></a>
<a href="http://210.76.65.188/newwebsite/index.htm" target="_blank">
<img style="border:0 none" src="<%=baseURL%>/images/gt.jpg" height="25"/></a>
</div>
<script type="text/javascript" src="<%=baseURL%>/js/show_pay.js"></script> 
</body>
</html>

