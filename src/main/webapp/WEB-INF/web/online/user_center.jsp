<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" charset="UTF-8" href="http://www.game7road.com/static/css/7road.css" type="text/css"/>
<script type="text/javascript" charset="UTF-8" src="http://www.game7road.com/static/js/jquery.js"></script>
<script type="text/javascript" src="http://www.game7road.com/static/js/Extension.js"></script>
<script  type="text/javascript">
var noLogin = "用户名：<input id='login_account' name='login_account' size='10' class='flatGray login_input' type='text'><br> 密　码：<input id='password' name='password' size='10' class='flatGray login_input' type='password'><br><input id='remember_me' class='flatGray' type='checkbox'> 记住用户名（慎用）<br><br><a href='javascript:void(0);' onclick='login()'><input id='login_button' src='http://www.game7road.com/static/images/login_button.jpg' type='image'></a> <a href='http://www.game7road.com/game_7road/registration.action' id='q_b1'><img src='http://www.game7road.com/static/images/register_button.jpg' border='0'></a><br><hr> <a href='/game_7road/forgetPassword.action'><font color='black'>忘记密码？</font></a>";
$(document).ready(function() {
	var islogin = $.getCookie('USERINFO');
    
	if(islogin!=null && islogin!=""){
		var userInfo = islogin.split(",");
		var date = new Date(parseInt(userInfo[2].substring(0,13)));
		var loginTime = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();
		var login =  "您好&nbsp;<span class='orange'>"+userInfo[1]+"</span><br><hr>登录时间："+loginTime+"<br><a href='http://www.game7road.com/game_7road/userCenter.action' target='_top'><img src='http://www.game7road.com/static/images/modify_button.gif' border='0' height='21' width='58'></a> <a href='http://www.game7road.com/step1.html'><img src='http://www.game7road.com/static/images/pay_button.gif' border='0' height='21' width='48'></a> <a href='javascript:void(0);' onclick='logout()' target='_self'><img src='http://www.game7road.com/static/images/exit_button.gif' border='0' height='21' width='48'></a><div id='last_games'> 上次玩过的游戏：</div><ul><li><img src='http://www.game7road.com/static/images/arrow_2.gif' border='0' height='7' width='4'> 尚未玩过游戏</li></ul>";
		$.ajax({
			url:"http://www.game7road.com/game_7road/checkLogin.action",
			type: "POST",
			dataType:"json",
			async: false,
			success:function(json) {
				if(json.result == "true"){
					$("#login_middle").html(login);
				}else{
					$("#login_middle").html(noLogin);
				}				
			}
		});
	}else{
		$("#login_middle").html(noLogin);		
	}
});
function logout(){
	$.removeCookie("USERINFO","","/");
	$("#login_middle").html(noLogin);
}
function login(){

	var loginAccount = $("input[name='login_account']").val();
	var password = $("input[name='password']").val();
	var rememberMe = $("#remember_me").attr("checked");
	
	if(!/^[A-Za-z0-9_@\.]{1,30}$/.test(loginAccount)){
		alert("请输入正确的用户名！用户名为1到30位的英文或数字！");
		return;
	}

	if(!/^.{1,30}$/.test(password)){
		alert("请输入正确的密码！密码长度为1到30！");
		return;
	}
	$.ajax({
		url:"http://www.game7road.com/game_7road/loginSubmit.action",
		data:{"userName":loginAccount,
		"password1":password,
		"remember_me":rememberMe},
		type: "POST",
		dataType:"json",
		async: false,
		success:function(json) {
			if(json.result == "true"){
				var islogin = $.getCookie('USERINFO');
				var userInfo = islogin.split(",");
				var date = new Date(parseInt(userInfo[2].substring(0,13)));
				var loginTime = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();	
				var login =  "您好&nbsp;<span class='orange'>"+userInfo[1]+"</span><br><hr>登录时间："+loginTime+"<br><a href='http://www.game7road.com/game_7road/userCenter.action' target='_top'><img src='http://www.game7road.com/static/images/modify_button.gif' border='0' height='21' width='58'></a> <a href='http://www.game7road.com/step1.html'><img src='http://www.game7road.com/static/images/pay_button.gif' border='0' height='21' width='48'></a> <a href='javascript:void(0);' onclick='logout()' target='_self'><img src='http://www.game7road.com/static/images/exit_button.gif' border='0' height='21' width='48'></a><div id='last_games'> 上次玩过的游戏：</div><ul><li><img src='http://www.game7road.com/static/images/arrow_2.gif' border='0' height='7' width='4'> 尚未玩过游戏</li></ul>";
				$("#login_middle").html(login);
			}
			else{
				$("#login_middle").html(noLogin);
				alert("用户名或密码错误");
			}
				
		}
	});
}
</script>

</head>
<body>
<div id="nav">
    <img src="http://www.game7road.com/images/nav_left.jpg" border="0" />
    <ul>
        <li class="home"><a href="http://www.game7road.com/index.html" target="_self">首&nbsp;&nbsp;页</a></li>
        <li class="menu"><a href="http://www.game7road.com/game_7road/userCenter.action" target="_self">用户中心</a></li>
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
<div id="main">
    <div id="left">
        <div id="login">
    <div id="login_top"></div>
    <div id="login_middle">
    </div>   
    <div id="login_bottom"></div>
</div>
        <div class="home_menu">
            <div class="frame_title">
                <div class="left"></div>
                <div class="content">用户中心</div>
                <div class="right"></div>
            </div>
            <div class="frame_content">
                <div class="content">
                    <ul>
                    	
                    	<li class="on"><a href="http://www.game7road.com/game_7road/userCenter.action" target="_self" class="home_link">修改密码</a></li>
                    	<!--<li><a href="/users/chgquestion.php" target="_self" class="home_link">修改密保问题答案</a></li>-->
                    	<li><a href="http://www.game7road.com/step1.html" target="_blank" class="home_link">帐号充值</a></li>
                    	
                    	<li><a href="http://www.37wan.com/html/news/2010/0803/3299.html" target="_blank" class="home_link">用户注册服务协议</a></li>
                    	
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="right">
        <div class="list_content" id="chgpwd" >
            <div class="frame_title">
                <div class="left"></div>
                <div class="content">修改密码</div>
                <div class="right"></div>
            </div>
            <div class="frame_content">
				<div class="user_home">
					<div class="user_home_input">
						<div class="user_home_list">
							<dl>
								<dt>请输入旧密码：</dt>
								<dd><input id="old_password" name="old_password" size="10" class="flatGray login_input" type="password"><span id="old_password_img"></span></dd>
							</dl>
						</div>
						<div class="user_home_list">
							<dl><dt>&nbsp;</dt><dd><span id="old_password_info"></span></dd></dl>
						</div>
						<div class="user_home_list">
							<dl>
								<dt>请输入新密码：</dt>
								<dd><input id="new_password1" name="new_password1" size="10" class="flatGray login_input" type="password"><span id="new_password1_img"></span></dd>
							</dl>
						</div>
						<div class="user_home_list">
							<dl><dt>&nbsp;</dt><dd><span id="new_password1_info"></span></dd></dl>
						</div>
						<div class="user_home_list">
							<dl>
								<dt>再输入新密码：</dt>
								<dd><input id="new_password2" name="new_password2" size="10" class="flatGray login_input" type="password"><span id="new_password2_img"></span></dd>
							</dl>
						</div>
						<div class="user_home_list">
							<dl><dt>&nbsp;</dt><dd><span id="new_password2_info"></span></dd></dl>
						</div>
						<div class="user_home_list">
							<dl>
								<dt><span class="point">*</span>&nbsp;电子邮件：</dt>
								<dd><input id="qemail" name="qemail" size="20" class="flatGray" type="text"><span id="qemail_img"></span></dd>
							</dl>
						</div>
						<div class="user_home_list">
							<dl><dt>&nbsp;</dt><dd><span id="qemail_info"></span></dd></dl>
						</div>
					</div>
					<div id="user_home_info_1"></div>
					<div class="user_home_btn"><img src="http://www.game7road.com/static/images/ok_button.jpg" id="change_password" style="cursor: pointer;"></div>
				</div>
            </div>
        </div>
       
    </div>
	<script type="text/javascript">
		var is_bind = '';
		function showinfo(info){
			$("#user_home_info_2").html(info);
		}
		function checkMobile(mobile){
			 var reg0=/^13\d{5,9}$/;   //130--139。至少7位
			 var reg1=/^153\d{8}$/;  //联通153。至少7位
			 var reg2=/^159\d{8}$/;  //移动159。至少7位
			 var reg3=/^158\d{8}$/;
			 var reg4=/^150\d{8}$/;
			 var reg5=/^188\d{8}$/;
			 var reg6=/^189\d{8}$/;
			 var reg7=/^15\d{5,9}$/;   //150--159。至少7位
			 var reg8=/^18\d{5,9}$/;   //180--189。至少7位
			 var my=false;
			 if (reg0.test(mobile))my=true;
			 if (reg1.test(mobile))my=true;
			 if (reg2.test(mobile))my=true;
			 if (reg3.test(mobile))my=true;
			 if (reg4.test(mobile))my=true;
			 if (reg5.test(mobile))my=true;
			 if (reg6.test(mobile))my=true;
			 if (reg7.test(mobile))my=true;
			 if (reg8.test(mobile))my=true;
			 if (!my){
				return '对不起，您输入的手机号码错误。';
				//return "true";
			 }else{
				return "true";
			 }
		}
		$(function(){
			var isidcard = "true3";
			if(isidcard){
				$("#id_card_number_info").html("&nbsp;&nbsp;<span class='info_text'>您的身份证不正确，请立即联系37wan客服进行修改</span>");
				$("#id_card_number_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
			}
			var isquestion = "";
			if(isquestion){
				$("#question_info").html("<span class='info_text'>密保问题是取回密码的重要途径，修改请联系37wan客服</span>");
			}else{
				$("#question_info").html("<span class='info_text'>密保问题是取回密码的重要途径，请立即修改</span>");
			}
			var isanswer = "";
			if(isanswer){
				$("#answer_info").html("<span class='info_text'>密保答案是取回密码的重要途径，修改请联系37wan客服</span>");
			}else{
				$("#answer_info").html("<span class='info_text'>密保答案是取回密码的重要途径，请立即修改</span>");
			}
			if(is_bind=="1"){
				$("#phone_img").after("<font color='blue'>已安全绑定</font>");
				$("#phone_info").html("<span class='info_text'>如需要修改手机号码，请联系37wan客服</span>");
			}else{
				$("#phone_img").after("<a href='/users/home.php?page=coin' target='_self'><font color='blue'>免费绑定</font></a>");
			}
			$("#old_password").blur(function(){
				$("#user_home_info_1").html("");
				if($("#old_password").val()!=''){
					$.post(
					"checkOldPassword.action",
					{password1:$("#old_password").val()},
					function (json){
						if(json.result=='true'){
							$("#old_password_info").html("");
							$("#old_password_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_1.jpg'/>");
						}else{
							$("#old_password_info").html("&nbsp;&nbsp;<span class='info_text'>"+json.result+"</span>");
							$("#old_password_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
						}
					});
					
				}else{
					$("#old_password_info").html("&nbsp;&nbsp;<span class='info_text'>请输入旧密码！</span>");
					$("#old_password_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
				}
			});
			$("#new_password1").blur(function(){
				$("#user_home_info_1").html("");
				if($("#new_password1").val()!=''){
					$("#new_password1_info").html("");
					$("#new_password1_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_1.jpg'/>");
				}else{
					$("#new_password1_info").html("&nbsp;&nbsp;<span class='info_text'>请输入新密码！</span>");
					$("#new_password1_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
				}
			});
			$("#new_password2").blur(function(){
				$("#user_home_info_1").html("");
				if($("#new_password2").val()==$("#new_password1").val()){
					$("#new_password2_info").html("");
					$("#new_password2_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_1.jpg'/>");
					if($("#new_password2").val()!=''){
						$("#new_password2_info").html("");
						$("#new_password2_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_1.jpg'/>");
					}else{
						$("#new_password2_info").html("&nbsp;&nbsp;<span class='info_text'>请再次输入新密码！</span>");
						$("#new_password2_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
					}
				}else{
					$("#new_password2_info").html("&nbsp;&nbsp;<span class='info_text'>两次输入的新密码不一致！</span>");
					$("#new_password2_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
				}
			});
			$("#qemail").blur(function(){
				$("#user_home_info_1").html("");
				r=parseInt($("#qemail").val().search(strReg));
				if(r!=-1){
					$("#qemail_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_1.jpg'/>");
				}else{
					$("#qemail_info").html("&nbsp;&nbsp;<span class='info_text'>请正确填写邮箱！</span>");
					$("#qemail_img").html("&nbsp;&nbsp;<img src='http://www.game7road.com/static/images/mark_2.jpg'/>");
				}
			});
			$("#change_password").click(function(){
				var oldPassword = $("#old_password").val();
				var newPassword1 = $("#new_password1").val();
				var newPassword2 = $("#new_password2").val();
				var qemail = $("#qemail").val();
				if (oldPassword == "") {
					alert("请输入旧密码！");
					return;
				}
				if (newPassword1 == "") {
					alert("请输入新密码！");
					return;
				}
				if (newPassword2 == "") {
					alert("请再次输入新密码！");
					return;
				}
				if (qemail == "") {
					alert("请输入邮箱地址！");
					return;
				}
				if (newPassword1 != newPassword2) {
					alert("两次输入的新密码不一致！");
					return;
				}
				$.post("changePassword.action",{password1:oldPassword,password2:newPassword1,email:qemail},function(json){
					if(json.result == "true"){
						alert("修改成功!");
						return;
					}else{
						alert(json.result);
						return;
					}
				});
			});
			
			var strReg="";
			var r;
			strReg=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/i;
			$("#name").blur(function(){
				$("#user_home_info_2").html("");
				if($("#name").val()!=''){
					if(/^[\u4e00-\u9fa5]+$/.test($("#name").val()) ){
						$("#name_info").html("");
						$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}else{
						$("#name_info").html("&nbsp;&nbsp;<span class='info_text'>必填项。请输入真实姓名，修改请联系37wan客服。</span>");
						$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					}
				}else{
					$("#name_info").html("&nbsp;&nbsp;<span class='info_text'>必填项。请输入真实姓名，修改请联系37wan客服。</span>");
					$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
				}
			});
			$("#id_card_number").blur(function(){
				$("#user_home_info_2").html("");
				$.getJSON("/users/home.php?action=isIdcard&id_card_number="+$("#id_card_number").val(), function(json){
					if(json.ret=='true'){
						$("#id_card_number_info").html("");
						$("#id_card_number_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}else{
						$("#id_card_number_info").html("&nbsp;&nbsp;<span class='info_text'>"+json.ret+"</span>");
						$("#id_card_number_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					}
				});
			});
			$("#email").blur(function(){
				$("#user_home_info_2").html("");
				if($("#email").val()!="********"){
					r=parseInt($("#email").val().search(strReg));
					if(r!=-1){
						$("#email_info").html("<span class='info_text'>如需要修改邮箱，请联系37wan客服</span>");
						$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}else{
						$("#email_info").html("&nbsp;&nbsp;<span class='info_text'>邮箱 是必填项，您将可以通过它找回丢失的密码。</span>");
						$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					}
				}else{
					$("#email_info").html("<span class='info_text'>如需要修改邮箱，请联系37wan客服</span>");
					$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}
			});
			$("#phone1").blur(function(){
				$("#user_home_info_2").html("");
				if($("#phone").val()!=''){
					if($("#tmp_phone").val()!=""){
						var tmp_phone = $("#tmp_phone").val();
					}else{
						var tmp_phone = $("#phone").val();
					}
					var txt = checkMobile(tmp_phone);
					if(txt!='true'){
						$("#phone_info").html("<font color=red>"+txt+"</font>");
						$("#phone_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					}else{
						if(is_bind=="1"){
							$("#phone_info").html("<span class='info_text'>如需要修改手机号码，请联系37wan客服</span>");
						}else{
							$("#phone_info").html("");
						}
						$("#phone_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}
				}else{
					$("#phone_info").html("&nbsp;&nbsp;<span class='info_text'>电话号码 是必填项，可以让我们更好的为您提供服务。</span>");
					$("#phone_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
				}
			});
			$("#qqmsn1").blur(function(){
				$("#user_home_info_2").html("");
				if($("#qqmsn").val()!=''){
					$("#qqmsn_info").html("");
					$("#qqmsn_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}else{
					$("#qqmsn_info").html("&nbsp;&nbsp;<span class='info_text'>请您输牢记您输入的 QQ/MSN号码 。</span>");
					$("#qqmsn_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}
			});/*
			$("#question1").blur(function(){
				$("#user_home_info_2").html("");
				if($("#question").val()!=''){
					$("#question_info").html("");
					$("#question_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}else{
					$("#question_info").html("&nbsp;&nbsp;<span class='info_text'>请填写您的密保问题</span>");
					$("#question_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
				}
			});
			$("#answer1").blur(function(){
				$("#user_home_info_2").html("");
				if($("#answer").val()!=''){
					$("#answer_info").html("");
						$("#answer_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}else{
					$("#answer_info").html("&nbsp;&nbsp;<span class='info_text'>请填写您的密保答案，并请您输牢记您输入的 密保问题 。</span>");
						$("#answer_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
				}
			});*/
			$("#update_info").click(function(){
				var name = $("#name").val();
				var idCardNumber = $("#id_card_number").val();
				//var question = $("#question").val();
				//var answer = $("#answer").val();
				var phone = $("#phone").val();
				var qqmsn = $("#qqmsn").val();
				var email = $("#email").val();
				r=parseInt(email.search(strReg));
				if(r==-1){
//					alert("邮箱有误");
//					return;
				}
				if($("#name").val()!=''){
					if(/^[\u4e00-\u9fa5]+$/.test($("#name").val()) ){
						$("#name_info").html("");
						$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}else{
						$("#name_info").html("&nbsp;&nbsp;<span class='info_text'>必填项。请输入真实姓名，修改请联系37wan客服。</span>");
						$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
						if($("#name").attr("readonly")!=true){
							return;
						}
					}
				}else{
					$("#name_info").html("&nbsp;&nbsp;<span class='info_text'>必填项。请输入真实姓名，修改请联系37wan客服。</span>");
					$("#name_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					return;
				}
				if($("#email").val()!="********"){
					r=parseInt($("#email").val().search(strReg));
					if(r!=-1){
						$("#email_info").html("<span class='info_text'>如需要修改邮箱，请联系37wan客服</span>");
						$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}else{
						$("#email_info").html("&nbsp;&nbsp;<span class='info_text'>邮箱 是必填项，您将可以通过它找回丢失的密码。</span>");
						$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
						return;
					}
				}else{
					$("#email_info").html("<span class='info_text'>如需要修改邮箱，请联系37wan客服</span>");
					$("#email_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
				}
				if($("#phone").val()!=''){
					if($("#tmp_phone").val()!=""){
						var tmp_phone = $("#tmp_phone").val();
					}else{
						var tmp_phone = $("#phone").val();
					}
					var txt = checkMobile(tmp_phone);
					//var txt = checkMobile($("#phone").val())
					if(txt!='true'){
						$("#phone_info").html("<font color=red>"+txt+"</font>");
						$("#phone_img").html("&nbsp;&nbsp;<img src='/images/mark_2.jpg'/>");
					}else{
						if(is_bind=="1"){
							$("#phone_info").html("<span class='info_text'>如需要修改手机号码，请联系37wan客服</span>");
						}else{
							$("#phone_info").html("");
						}
						$("#phone_img").html("&nbsp;&nbsp;<img src='/images/mark_1.jpg'/>");
					}
				}
				if($("#answer").val()!=''&&$("#answer").val()!='********'){
					$("#answer_info").html("<span class='info_text'>请牢记您的密保答案，以后我们将不显示明文答案！</span>");
				}
				$.remoteMethod("/users/home.php","updateInfo",{
					"name" : name,
					"id_card_number" : idCardNumber,
					"email" : email,
					//"question" : question,
					//"answer" : answer,
					"phone" : phone,
					"qq_msn" : qqmsn
				});
			});
		});
	</script>
</div>



</body></html>