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
    <div class="doc server clearfix"><!-- Document start -->	
        <div class="container">
            <div class="welcome clearfix">
                <h1><em></em> 欢迎您登录，请选择服务器！<a href="" class="logout">注销</a></h1>
                <p><span class="last"><a href=""></a></span> <a href="" class="gamestart"></a></p>
            </div>
            <div class="servers">
                <div class="servers-area">
                <#assign n=100>
                    <ul class="tabs">
                        <#if ((serverList?size%n)?int != 0)>
                            <#assign i=(serverList?size/n)?int + 1>
                        <#else>
                            <#assign i=(serverList?size/n)?int>
                        </#if>
                        <#list 0..i-1 as t>
                            <#if t_index == 0>
                                <li class="tab active">
                            <#else>
                                <li class="tab">
                            </#if>
                                <#if t_index == 0>
                                    <a  title="推荐服务器" href="javascript:void(0)">推荐服务器</a>
                                <#else>
                                    <a  title="${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区" href="javascript:void(0)">${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区</a>
                                </#if>
                            </li>
                        </#list>
                    </ul>
                    <div class="quick-start clearfix">
                        <form action="" method="POST" id="quick-start">
                            <input type="text" id="number" name="number" value="<#if serverList?? && serverList?size gt 0><#list serverList as server><#if (server.recommand == 'true' && server.serverStatus == '1')>${server.serverNo}<#break></#if></#list></#if>" />
                            <input type="submit" id="submit" name="submit" class="" value="" />
                        </form>
                    </div>

                    <div class="server-area">
                        <div class="scrollbar"><div class="track"><div class="thumb"><div class="end"></div></div></div></div>
                        <div class="viewport">
                            <div class="overview">
                                <ul>
                                    <#if serverList?? && serverList?size gt 0>
                                        <#assign remainder=(serverList?size %n)?int >
                                        <#list serverList as server>
                                            <#if (server.serverStatus == '1')>
                                                 <li class="hot"><a href="javascript:void(0)" serverId="${server.id}" title="${server.serverName}" target="_blank">${server.serverName}</a></li> 
                                            </#if>
                                            <#if (server.serverStatus == '-2')>
                                                 <li class="maintain"><a href="javascript:void(0)" serverId="${server.id}" title="${server.serverName}" target="_blank">${server.serverName}</a></li> 
                                            </#if>
                                            <#if ((server_index+1) == remainder) || (((server_index+1)>=(remainder + n)) && ((server_index+1-remainder)%n == 0)) >                      
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="server-area none">
                                                <div class="scrollbar"><div class="track"><div class="thumb"><div class="end"></div></div></div></div>
                                                <div class="viewport">
                                                    <div class="overview">
                                                <ul>
                                            </#if>
                                        </#list>
                                    </#if>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div id="serverid-list" style="display:none"><#list serverList as server>${server.id} </#list></div>
                </div>
            </div>
        </div>
    </div><!-- Document end -->
    <script src="http://lib.sinaapp.com/js/jquery/1.9.0/jquery.min.js"></script>
    <script src="${staticHost}/js/jquery.tinyscrollbar.min.js"></script>
    <script src="${staticHost}/js/road.js"></script>
    <script src="${staticHost}/js/sq-common.js?ver=${version}"></script>
    <script src="${staticHost}/js/sq-client.js?ver=${version}"></script>
    <!--[if IE 6 ]>
        <script>document.execCommand("BackgroundImageCache",false,true);</script>
    <![endif]-->
    <script>
        $(function (){
            checkUserIsLogined();
            // tabs
            $('.tab a').click(function (e){
                e.preventDefault();
            })
            $('.tabs .tab').on('mouseenter', function (){
                var index = $(".tabs li").index(this);
                $(this).addClass("active").siblings().removeClass("active");
                
                $(".server-area").eq(index).show().siblings(".server-area").hide();
                // scrollbar
                $('.server-area').eq(index).tinyscrollbar({size:97,sizethumb:30});
            });

            // scrollbar
            $('.server-area').tinyscrollbar({size:97,sizethumb:30});
        })
    </script>
</body>
</html>