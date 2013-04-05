
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
	<title></title>
	<meta name="Keywords" content="神曲,神曲官网,神曲新手卡,神曲礼包,神曲职业"/>
	<meta name="description" content="神曲官网-神曲一款角色扮演类的网页游戏，神曲首页，提供神曲下载，神曲BOSS攻略，神曲新手卡，神曲礼包，神曲职业，神曲论坛等一系列的详细游戏介绍，休闲娱乐，从神曲开始。" />
	<link rel="stylesheet" href="${staticHost}/css/baiduADFlash.css?v=${version}" charset="utf-8"/>
<style type="text/css">
.blank20{ height:20px; display:block; overflow:hidden; clear:both;}
</style>    
</head>
<body>

	<div class="dvflash" style="display: ;">
		<div class="panel reg"  id="regtable" style="display: none;">
			<!-- 登陆 -->
			<div class="xf-tab-login">
				<table border="0" id="login_panel" style="display: none;">
					<tr>
						<td class="tbtitle" style="width: 150px;">帐&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
						<td><input type="text" class="in2" id="uid" maxlength="20" /></td>
						<td class="tip">&nbsp;<span id="tab1_1"></span></td>
					</tr>

					<tr>
						<td class="tbtitle" style="width: 150px;">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
						<td><input type="password" class="in2" id="pwd" maxlength="20" /></td>
						<td class="tip">&nbsp;<span id="tab1_2"></span></td>
					</tr>
					<tr id="log_verifyTr" style="display:none">
						<td class="tbtitle" style="width: 150px;">验证码：</td>
						<td><input type="text" class="in2" id="verifyCode" maxlength="4" style="width:120px;vertical-align:middle;"/><img id="verifyCodeImg" style="vertical-align:middle;cursor:pointer;" src="${serverBasePath}/captcha.do" width="58px" height="27px"  class="logpic" onClick="javascript:this.src='${serverBasePath}/captcha.do?'+(new Date().getTime());"></td>
						<td class="tip"><span id="tab1_3" style="height:15px;line-height:15px;overflow:hidden;display:block;"></span><a style="height:15px;line-height:15px;overflow:hidden;display:block;" href="javascript:refleshVerifyCode('verifyCodeImg')">看不清，换一张</a></td>
					</tr>
					<!-- <tr>
						<td></td>
						<td colspan="2" style="font-size: 13px; color: #f7c500;">&nbsp;<span id="login_tip" style="margin-left: 12px;"></span></td>
					</tr> -->

					<tr>
						<td></td>
						<td >
							<div><span id="login_tip" style="margin-left: 12px;font-size: 13px; color: #f7c500;"></span></div>
							<a href="javascript:void(0);" class="btn-login" id="btntoLogin"></a>
						</td>
						<td></td>	
					</tr>
                  <tr>
				  <td style="height:14px;line-height:14px;padding:10px 0 0;"></td>
						<td colspan="2" style="height:14px;line-height:14px;padding:10px 0 0;"><a href="javascript:void(0);" title="" class="quick-login">快速注册</a><a href="http://account.7road.com//forgetpwd.html" target="_blank" title="" class="quick-login">忘记密码</a></td>
				  </tr>
					

				</table>
				
				<div id="tabLogined" class="xf-tab-logined" style="display: none;">
                <div class="blank20"></div>
					<div style="padding-left:50px;">
						<div class="txtlogin_info">
							<span style="font-size: 13px; color: #f7c500;font-weight: bold;" id="username">XXXXXX</span>, 您好！
							[<a style="font-size: 13px; color: #f7c500;font-weight: bold;" href="javascript:logout();">退出</a>]
						</div>
						<div class="txtsafe_info" >
							<a href="${accountCenterPath}" class="btn2" >完善密保资料</a>
							请绑定邮箱确保帐号安全！
						</div>
						<div class="txtlastlogin_info" style="margin-top:20px;" >您上次登陆的服务器是：</div>
						<div class="txtserver_info" >
							<a href="javascript:void(0);" class="btn3" id="lastserver">【服务器】</a>
							
						</div>
					</div>
				</div> 

				<table border="0" id="reg_panel" >
					<tr>
						<td class="tbtitle">帐&nbsp;&nbsp;&nbsp;号:</td>
						<td><input type="text" class="in2" id="reg_uid" maxlength="20" /></td>
						<td class="tip">&nbsp;<span id="tab2_1">长度6-20,由字母数字组成.</span></td>
					</tr>

					<tr>
						<td class="tbtitle">密&nbsp;&nbsp;&nbsp;码:</td>
						<td><input type="password" class="in2" id="reg_pwd" maxlength="20"/></td>
						<td class="tip">&nbsp;<span id="tab2_2">长度6-20个字符</span></td>
					</tr>
					<tr>
						<td class="tbtitle">确认密码:</td>
						<td><input type="password" class="in2" id="reg_confirm_pwd" maxlength="20" /></td>
						<td class="tip">&nbsp;<span id="tab2_3">两次输入的密码请保持一致</span></td>
					</tr>
					<tr>
						<td style="height: 30px;line-height: 30px;"></td>
						<td colspan="2"  style="height: 30px;line-height: 30px;padding-left: 10px;font-size: 13px;">
							<input type="checkbox" id="agreement" checked="checked" /><label> 同意并接受</label><a href="${accountCenterPath}/register_notice.html" title="" target="_blank">用户注册服务协议</a>
							
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="2">
							<a href="javascript:void(0);" class="btn-reg" id="btntoReg"></a><a href="#" title="" class="old-login">老用户登录</a>
							<span id="reg_tip" class="reg_tip"></span>
						</td>
					</tr>
				</table>
			</div>

		
		</div>
		<object id="falsh1" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="1000" height="600">
			<param name="movie" value="${imageHost[0]}/flash/ads5_bg.swf?v=${version}">
			<param name="quality" value="high">
			<param name="wmode" value="transparent">
			<param name="swfversion" value="9.0.45.0">
			<param name="allowScriptAccess" value="always">
			<!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
			<param name="expressinstall" value="${imageHost[0]}/flash/expressInstall.swf">
			<!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
			<!--[if !IE]>-->
			<object type="application/x-shockwave-flash" data="${imageHost[0]}/flash/ads5_bg.swf?v=${version}" width="1000" height="600">
			  <!--<![endif]-->
			  <param name="quality" value="high">
			  <param name="wmode" value="transparent">
			  <param name="swfversion" value="9.0.45.0">
			  <param name="allowScriptAccess" value="always">
			  <param name="expressinstall" value="${imageHost[0]}/flash/expressInstall.swf">
			  <!--[if !IE]>-->
			</object>
			<!--<![endif]-->
		</object>
	</div>
	<script type="text/javascript">
		window.setTimeout(showRegTable,2000);
		function showRegTable(){
			document.getElementById("regtable").style.display="";
		}
	</script>

	<script type="text/javascript" src="${staticHost}/js/config.js?v=${version}"></script>
	<script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"  charset="UTF-8"></script>
	<script type="text/javascript" src="${staticHost}/js/jquery.cookie.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${staticHost}/js/global.js?v=${version}" charset="UTF-8"></script>
	<script type="text/javascript" src="${staticHost}/js/game.js?v=${version}" charset="UTF-8"></script>
	<script type="text/javascript" src="${staticHost}/js/user.js?v=${version}" charset="UTF-8"></script>
	<script type="text/javascript" src="${staticHost}/js/advs.js?v=${version}" charset="UTF-8"></script>
	<!--[if IE 6]>
	<script src="${staticHost}/js/DD_belatedPNG.js"></script>
	<script>
	  /* EXAMPLE */
	  DD_belatedPNG.fix('.login,.reg');
	  
	  /* string argument can be any CSS selector */
	  /* .png_bg example is unnecessary */
	  /* change it to what suits you! */
	</script>
	<![endif]-->
	<div style="display: none;" class="login">
		<script src="http://s15.cnzz.com/stat.php?id=4167865&web_id=4167865" language="JavaScript"></script>
	</div>
</body>
</html>