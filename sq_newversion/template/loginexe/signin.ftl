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
    <div class="doc sign-in"><!-- Document start -->	
        <div class="container">
            <div class="news clearfix"><!-- News start -->
                <div class="hd"></div>
                <div class="bd">
                    <ul class="news-list">
                        <#list newsList as news>
                            <#if news_index lt 3>
                                <li class="hot">
                                    <a href="${serverHost}/news/detail/${news.typeId}_${news.newsId}.html" title="${news.artTitle}" target="_blank">
                                        <#if news.artTitle?length lt 30>
                                            ${news.artTitle}
                                        <#else>
                                            ${news.artTitle[0..27]}...
                                        </#if>
                                    </a>
                                    <span class="date">[${news.modifyDate?string("yyyy-MM-dd")}]</span>
                                </li>
                            <#else>
                                <li>
                                    <a href="${serverHost}/news/detail/${news.typeId}_${news.newsId}.html" title="${news.artTitle}" target="_blank">
                                        <#if news.artTitle?length lt 30>
                                            ${news.artTitle}
                                        <#else>
                                            ${news.artTitle[0..27]}...
                                        </#if>
                                    </a>
                                    <span class="date">[${news.modifyDate?string("yyyy-MM-dd")}]</span>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </div><!-- News end -->
            <div class="signin clearfix"><!-- signin start -->
                <form id="signin" action="" method="POST">
                    <div class="signin-l">
                        <div class="field username-field">
                            <label class="title" for="username">用户名：</label>
                            <input name="inName" id="username" tabindex="1" type="text" maxlength="32" />
                            <label class="inside" for="username">请输入用户名</label>
                        </div>
                        <div class="field password-field">
                            <label class="title" for="password">密码：</label>
                            <input name="password1" id="password" tabindex="2" type="password" maxlength="20" />
                            <label class="inside" for="password">请输入密码</label>
                        </div>
                        <div class="remember-field">
                            <label for="remember" class="checkbox"></label>
                            <input type="checkbox" id="remember" checked="checked" />
                            <a href="" class="text">记住帐号</a>
                        </div>    
                    </div>

                    <div class="signin-r">
                        <div class="submit-field clearfix">
                            <input type="submit" id="submit" tabindex="3" class="normal" value="" />
                            <a href="logexe_signup.html" class="fl">注册帐号</a>
                            <a href="http://account.7road.com/forget.html" target="_blank" class="fr">找回密码</a>
                        </div>
                    </div>
                    <div id="tips"></div>
                </form>
            </div><!-- signin end -->
        </div>
    </div><!-- Document end -->
    <script src="http://lib.sinaapp.com/js/jquery/1.9.0/jquery.min.js"></script>
    <script src="${staticHost}/js/sq-common.js?ver=${version}"></script>
    <script src="${staticHost}/js/sq-client.js?ver=${version}"></script>
    <script>
        $(function (){
            checkCookie();
            $("inside").css("display","block");
            $("#signin .field input")
                .on("focus.labelFX", function(){
                    $(this).next().hide();
                })
                .on("blur", function(){
                    $(this).next()[!this.value ? "show" : "hide"]();
                })
                .trigger("blur");
            
            $(".remember-field label, a.text").on("click", function(e){
                e.preventDefault();
                $(".checkbox").toggleClass("unchecked");
            });
        });
    </script>
</body>
</html>