<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    	<link rel="stylesheet" href="${staticHost}/css/sq_regist.css?v=${version}">
    	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
          <link rel="shortcut icon" href="${imageHost[0]}/fav_icon.ico" />
    </head>
	<body>
    <div class="page">
       
        <div class="loginArea">
        	
            <div class="loginBox">
            	<ul>
                	<li ><input id="username" name="" type="text" class="loginput" value="请输入用户名" maxlength="20"></li>
                    <li class=" loginput2"><input id="password1" name="" type="password" class="loginput" style="display:none" maxlength="20"><input id="password1Text" name="" type="text" class="loginput"  value="请输入密码"></li>
                    <li class=" loginput2"><input  id="password2" name="" type="password" class="loginput"  style="display:none" maxlength="20"><input id="password2Text" name="" type="text" class="loginput"  value="请再次输入密码"></li>
                    <li class=" loginput3"><input id="verifyCode" name="verifyCode" maxlength="4" type="text"  value="请输入验证码" class="captcha-input">
                     <img id="verifyCodeImg" style="vertical-align:middle;cursor:pointer;" src="${serverBasePath}/captcha.do" width="58px" height="20px"  class="logpic" onClick="javascript:this.src='${serverBasePath}/captcha.do?'+(new Date().getTime());"></li>
                </ul>
          </div>
            <div class="loginbtn">
            	<p><a href="javascript:regist()"><img src="${imageHost[3]}/loginexe/regist_min.png" width="295" height="66"></a></p>
                
                <div id="tips" class="tips"></div>
            </div>
            <div class="loginuser"><a href="logexe_log.html"><img src="${imageHost[4]}/loginexe/back_to_login.png" width="65" height="21"></a></div>
        </div>
    </div>
	 </body>
	  <script src="${staticHost}/js/config.js?v=${version}"></script>
          <script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"  charset="UTF-8"></script>
          <script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
	  <script type="text/javascript" src="${staticHost}/js/loginexe_reg.js?v=${version}"  charset="UTF-8"></script>
      <!--[if IE 6]><script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
         DD_belatedPNG.fix(".loginbtn img,.loginuser img");
    </script>
  <![endif]-->
</html>
