<!doctype html>
<!--[if lt IE 7 ]> <html class="ie ie6 js" lang="en"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie ie7 js" lang="en"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie ie8 js" lang="en"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie ie9 js" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--><html class="js"><!--<![endif]-->
<head id="www-7road-com">
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="Description" content="" />
    <meta name="Keywords" content="" />
    <meta name="Copyright" content="Copyright www.7road.com 2011. All Rights Reserved.">
    <title>神曲-第七大道神曲</title>
    <link rel="stylesheet" href="${staticHost}/css/normalize.css?ver=${version}" type="text/css" />
	<link rel="stylesheet" href="${staticHost}/css/sq-client.css?ver=${version}" />
</head>
<body>
    <div class="doc sign-up clearfix"><!-- Document start -->	
        <div class="container">
            <div class="signup clearfix"><!-- signup start -->
                <form id="signup" action="" method="POST">
                    <div class="username-field field">
                        <label class="title" for="username">用户名：</label>
                        <input name="inName" id="username" tabindex="1" type="text" maxlength="32" />
                        <label class="inside" for="username">请输入用户名</label>
                        <label class="tips"></label>
                    </div>
                    <div class="password-field field">
                        <label class="title" for="password">密码：</label>
                        <input name="password1" id="password" tabindex="2" type="password" maxlength="20" />
                        <label class="inside" for="password">请输入密码</label>
                        <label class="tips"></label>
                    </div>
                    <div class="repeat-field field">
                        <label class="title" for="repeat">确认密码：</label>
                        <input type="password" id="repeat" name="password2" tabindex="3" />
                        <label class="inside" for="repeat">请确认密码</label>
                        <label class="tips"></label>
                    </div>
                    <div class="captcha-field field">
                        <label class="title" for="captcha">验证码：</label>
                        <input type="text" id="captcha" name="captcha" tabindex="4" maxlength="4" />
                        <label class="inside" for="captcha">请输入验证码</label>
                        <img id="verifyCodeImg" src="${serverBasePath}/captcha.do" width="58" height="20" class="logpic" />
                        <a class="refresh" href="">刷新验证码</a>
                        <label class="tips"></label>
                    </div>
                    <div class="submit-field">
                        <input type="submit" id="submit" value="" tabindex="5" />
                        <a href="logexe_signin.html">返回登录</a>
                        <div id="tips"></div>
                    </div>
                </form>
            </div><!-- signup end -->
        </div>
    </div><!-- Document end -->
    <script src="http://lib.sinaapp.com/js/jquery/1.9.0/jquery.min.js"></script>
    <script src="${staticHost}/js/sq-common.js?ver=${version}"></script>
    <script src="${staticHost}/js/sq-client.js?ver=${version}"></script>
    <script>
        $(function (){
            $("inside").css("display","block");
            $("#signup .field input")
                .on("focus.labelFX", function(){
                    $(this).next().hide();
                })
                .on("blur", function(){
                    $(this).next()[!this.value ? "show" : "hide"]();
                })
                .trigger("blur");
            $(".logpic, .refresh").on("click", function (e){
                e.preventDefault();
                var src = "${serverBasePath}/captcha.do?"+ (new Date().getTime());
                $(".logpic").attr("src", src);
            });
        });
    </script>
</body>
</html>