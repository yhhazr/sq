<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title></title>
        <link href="${staticHost}/css/sq_login.css?v=${version}" rel="stylesheet" type="text/css" />
        <link rel="shortcut icon" href="${imageHost[0]}/fav_icon.ico" />
        <script type="text/javascript">
        var downloadnewVersion=window.confirm("有新版本的客户端,请点击下载!");
        window.location.href="http://219.129.216.161/updates/shenqu/7road/7road神曲极速登录器.exe";

     </script>
    </head>

    <body>
        <div class="main">
            <div class="news">
                <div class="lbpic">
				     <div id="banner">    
				    <div id="banner_bg"></div>  <!--标题背景-->
				    <div id="banner_info"></div> <!--标题-->
				    <ul>
				        <li class="on">1</li>
				        <li>2</li>
				        <li>3</li>
				        <li>4</li>
				    </ul>
				   <div id="banner_list">
				        <a href="<#list urlItems as urlItem><#if urlItem_index == 0>${urlItem.url}</#if></#list>" target="_blank"><img src="${imageHost[0]}/loginexe/lunbo_1.jpg?v=${version}" /></a>
				        <a href="<#list urlItems as urlItem><#if urlItem_index == 1>${urlItem.url}</#if></#list>" target="_blank"><img src="${imageHost[1]}/loginexe/lunbo_2.jpg?v=${version}"/></a>
				        <a href="<#list urlItems as urlItem><#if urlItem_index == 2>${urlItem.url}</#if></#list>" target="_blank"><img src="${imageHost[2]}/loginexe/lunbo_3.jpg?v=${version}"/></a>
				        <a href="<#list urlItems as urlItem><#if urlItem_index == 3>${urlItem.url}</#if></#list>" target="_blank"><img src="${imageHost[3]}/loginexe/lunbo_4.jpg?v=${version}"/></a>
				    </div>
				</div>
                </div>
                <div  class="list">
                    <iframe src="logexe_news.html" scrolling="no" allowTransparency="true"  frameborder="0" style="overflow:hidden; margin:0px; display:block;"></iframe>
                </div>
            </div>

            <div class="login">
        
                <div class="login_c">
                    <p class="id">
                        <input name="username" type="text" class="input1" id="username" maxlength="20" value="请输入账号" tabindex="1" />
                    </p>
                    <p class="pass"><input name="textfield" type="password" class="input1" id="password" maxlength="20" style="display:none" tabindex="2" /><input name="textfield" type="text" class="input1" id="passwordText" value="请输入密码"/></p>
                    <!--  <p id="verifyP" class="verify" style="display:none">
                        <input name="verifyCode" type="text" class="input1" id="verifyCode" maxlength="4" style="width:95px" value="请输入验证码"/><img id="verifyCodeImg" style="vertical-align:middle;cursor:pointer;" src="${serverBasePath}/captcha.do" width="58px" height="20px"  class="logpic" onClick="javascript:this.src='${serverBasePath}/captcha.do?'+(new Date().getTime());">
                    </p> -->
                    <div style="margin:5px 0 0 20px;*margin-left:30px; height:28px; line-height:28px;color:#daa236;">
    					<a id="remeMe" ><img src="${imageHost[5]}/loginexe/tb_01.gif" width="26" height="28" style="vertical-align: middle;" /></a>
    					<!-- <img class="pngfix" src="${imageHost[0]}/loginexe/jzmm.png" width="67" height="24" /> -->
                            <span>记住帐号</span>
                             <a href="logexe_reg.html"  style="color:#daa236;margin-left:20px;">注册账号</a> | <a href="${accountCenterPath}/forget.html" target="_window" style="color:#daa236;" >找回密码</a>
                       
                         
                    </div>

                </div>
                <div class="login_r" align="center">
                    <a href="javascript:checkLogin();" tabindex="3"><img src="${imageHost[1]}/loginexe/btn_login.jpg" width="173" height="85" border="0"/></a><br />
                   
                    <div id="tips" class="tips"></div>
                </div>
                    
            	</div>
        </div>
	 <script src="${staticHost}/js/config.js?v=${version}"></script>
         <script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"  charset="UTF-8"></script>
         <script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
	 <script type="text/javascript" src="${staticHost}/js/loginexe_log.js?v=${version}"  charset="UTF-8"></script>

  <!--[if IE 6]><script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js"></script>
    <script type="text/javascript">
         DD_belatedPNG.fix(".pngfix");
    </script>
  <![endif]-->
    </body>
</html>
