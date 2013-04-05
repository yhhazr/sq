<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻列表_第七大道《神曲》官方网站</title>
<meta name="keywords" content=""/>
 <meta name="description" content="" />

<!--[if IE]>
<script src="${staticHost}/js/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="${staticHost}/css/main.css?ver=${version}" type="text/css" />
<link rel="stylesheet" href="${staticHost}/css/inside.css?ver=${version}" type="text/css" />
<link rel="stylesheet" href="${staticHost}/css/top.css?ver=${version}" type="text/css" />
</head>
<body>
<!-- 顶部导航条start -->
<#include "*/common/headerNav.ftl">
<!-- 顶部导航条end -->

<div class="bg1">
    <div class="screenCenter">
        <!-- 头部start -->
        <#include "*/common/header.ftl">
        <!-- 头部end -->
        <section class="pageMain">
            
            <!-- 左边start -->
            <#include "*/common/list_Leftside.ftl">
            <!-- 左边end -->
            <!-- 右边start -->
            <div class="pageModRight f_r">
                <div class="rightTopBg"></div>
                <div class="blank11"></div>
                <div class="pageRight1">
                    <div class="pageRight2">
                        <div class="pageRight3">
                            <div class="pageTxt">
                                <div class="pageNav">
                                    <a href="${serverHost}">首页</a> &gt;
                                    <a href="${serverHost}/news/newsList.html">新闻中心</a> &gt;
                                    <strong>新闻列表</strong>
                                </div>
                                <h2 class="txt1"></h2>
                            </div>
                            <div class="newsListImg">
                                <a href="${newestNews.url!}" ><img src="${imageHost[0]}/${newestNews.content!}"  /></a>
                                
                            </div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod3 newsContent switch5">
                                        <h3 class="switchTab">
                                            <span class="on"><a href="javascript:void(0);" class="n1" onclick="change('${serverHost}/news/list_0_1.html',this)" id="list-nav-1" >热点</a></span>
                                            <span><a  href="javascript:void(0);" class="n2" onclick="change('${serverHost}/news/list_1_1.html',this)" id="list-nav-2">新闻</a></span>
                                            <span><a  href="javascript:void(0);" class="n3" onclick="change('${serverHost}/news/list_2_1.html',this)" id="list-nav-3">公告</a></span>
                                            <span><a href="javascript:void(0);" class="n4" onclick="change('${serverHost}/news/list_3_1.html',this)" id="list-nav-4">活动</a></span>
                                            <span><a  href="javascript:void(0);" class="n5" onclick="change('${serverHost}/news/list_5_1.html',this)" id="list-nav-5">蓝贴</a></span>
                                            <!--a title="更多" href="javascript:void(0);" class="more1">&gt; 更多</a-->
                                        </h3>
                                        <div class="pageNewsList" id="pageNewsList">
                                            <#include "newsListContent.ftl">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="rightBottomBg"></div>
            </div>
            <!-- 右边end -->
            <div class="blank10"></div>
            
      </section> 
	</div>
</div>
<div class="blank10"></div>


<#include "*/common/footer.ftl">
<style type="text/css">
.backToTop {display: block;width: 22px;height: 119px;position: fixed;_position: absolute;left: 50%;bottom: 160px;_margin-bottom: 160px;cursor: pointer;
margin-left: 500px;background-image: url(http://image.7road.com/toTopBg.png);display:none;
_top: expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)));
}

</style>
</body>



<script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticHost}/js/tinybox.js"></script>

<script type="text/javascript" src="${staticHost}/js/config.js?v=1346986236185"></script>
<script type="text/javascript" src="${staticHost}/js/global.js?v=1346986236185" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/user.js?v=1346986236185" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/game.js?v=1346986236185" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/switch.js"  charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/list.js"  charset="UTF-8"></script>
<script type="text/javascript">
$(function(){
    $(".switchTab a").live("click",function(){
        var index = $(".switchTab a").index(this);
        $(this).parent().addClass("on").siblings().removeClass("on");
    });
    var searchVal = location.search.split("=")[1];
    if(searchVal=="hotnews"){
        change('${serverHost}/news/list_0_1.html','list-nav-1');
        $(".switchTab a").eq(0).click();
    }else if(searchVal=="news"){
        change('${serverHost}/news/list_1_1.html','list-nav-2');
        $(".switchTab a").eq(1).click();
    }else if(searchVal=="bulletin"){
        change('${serverHost}/news/list_2_1.html','list-nav-3');
        $(".switchTab a").eq(2).click();
    }else if(searchVal=="activity"){
        change('${serverHost}/news/list_3_1.html','list-nav-4');
        $(".switchTab a").eq(3).click();
    }else if(searchVal=="lantie"){
        change('${serverHost}/news/list_5_1.html','list-nav-5');
        $(".switchTab a").eq(4).click();
    }else{
        //do
    }
});

(function() {    var $backToTopTxt = "返回顶部", $backToTopEle = $('<a class="backToTop" href="javascript:void(0)" target="_self"></a>').appendTo($("body")).attr("title", $backToTopTxt).click(function() {            $("html, body").animate({ scrollTop: 0 }, 120);    }), $backToTopFun = function() {        var st = $(document).scrollTop(), winh = $(window).height();        (st > 100)? $backToTopEle.show(): $backToTopEle.hide();           };    $(window).bind("scroll", $backToTopFun);    $(function() { $backToTopFun(); });})();
$(window).scroll(function(){if($(window).width()<=1055){$(".backToTop").css({"right":"5px","left":"auto","margin-left":"auto"})}else{$(".backToTop").css({"margin-left":500,"left":"50%"})}});
setTimeout(function(){$(window).resize(function(){if($(window).width()<=1055){$(".backToTop").css({"right":"5px","left":"auto","margin-left":"auto"})}else{$(".backToTop").css({"margin-left":500,"left":"50%"})}});},1000);

</script>  
</html>
