<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>神曲下载中心_第七大道《神曲》官方网站</title>
<meta name="keywords" content="" />
<meta name="description" content=""  />

<!--[if IE]>
<script src="${staticHost}/js/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="${staticHost}/css/main.css?ver=${version}" type="text/css" />
<link rel="stylesheet" href="${staticHost}/css/inside.css?ver=${version}" type="text/css" />
<link rel="stylesheet" href="${staticHost}/css/top.css?ver=${version}" type="text/css" />
<link href="${staticHost}/css/cb_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- 顶部导航条start -->
<#include "*/common/headerNav.ftl">
<!-- 顶部导航条end -->

<div class="bg3">
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
                                    <a href="${serverHost}/">首页</a> &gt;
                                    <a href="${serverHost}/picture/picList.html">下载中心</a> &gt;
                                    <strong>下载列表</strong>
                                </div>
                                <h2 class="txt2"></h2>
                            </div>
                            <div class="blank40"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod7 newsContent imgList switch4">
                                        <h3 class="switchTab">
                                            <span class="on"><a  href="javascript:void(0);" id="wallpaper" class="n1" onclick="transfar('${serverHost}/picture/91_1.html', this.id)">游戏壁纸</a></span>
                                            <span><a  href="javascript:void(0);" class="n2" id="paint" onclick="transfar('${serverHost}/picture/90_1.html', this.id)">游戏原画</a></span>
                                            <span><a  href="javascript:void(0);" class="n3" id="video" onclick="transfar('${serverHost}/picture/0_1.html', this.id)">游戏视频</a></span>
                                            <span><a href="javascript:void(0);" class="n4" id="cartoon" onclick="transfar('${serverHost}/picture/93_1.html',this.id)">游戏漫画</a></span>
                                            <span><a href="javascript:void(0);" class="n6" id="screenshot" onclick="transfar('${serverHost}/picture/94_1.html', this.id)">玩家风采<!--此处数据为原版之游戏截图--></a></span>
                                            
                                        </h3>
                                        <div class="downloadBox" id="downloadBox">
                                            
                                            <#include "picListContentCenter.ftl">
                                            
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

<!-- 底部start -->
<#include "*/common/footer.ftl">
<!-- 底部end -->
<style type="text/css">
#photo_list li{ position: relative;}
#photo_list li .see{display:none;text-align:center;left:0;position:absolute;top:0;width:80px;height:21px;padding:60px 62px;z-index:1;}
#photo_list li .see a{color:#FF8400;margin-right:5px;}
</style>
</body>
<script type="text/javascript" src="${staticHost}/js/config.js?v=1351046516271"></script>

<script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticHost}/js/tinybox.js"></script>
<script type="text/javascript" src="${staticHost}/js/global.js?v=1351046516271" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/user.js?v=1351046516271" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/game.js?v=1351046516271" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/quickRegist.js?v=1351046516271" charset="UTF-8"></script>
<script type="text/javascript" src="${staticHost}/js/getCard.js?v=1351046516271"></script>
<script type="text/javascript" src="${staticHost}/js/gameEnterInfoStatistics.js?v=1351046516271" charset="utf-8"></script>
<script type="text/javascript" src="http://v2.jiathis.com/code/jia.js" charset="utf-8"></script>


<script type="text/javascript" src="${staticHost}/js/clearbox.js"></script> 
<script type="text/javascript">


function transfar(url, obj){
   
    $.get(url, function(data){
        $("#downloadBox").html(data);
        CB_Init();
    });
}

$(function(){
    $(".switchTab a").live("click",function(){
        var index = $(".switchTab a").index(this);
        $(this).parent().addClass("on").siblings().removeClass("on");
    });
    $("#photo_list li ").live("mouseover",function(){
        $(this).children(".see").show();
        $(this).children(".imgListPic").children("img").css("opacity","0.5");
    })
    $("#photo_list li ").live("mouseout",function(){
        $(this).children(".see").hide();
        $(this).children(".imgListPic").children("img").css("opacity","1");
    })
    var searchVal = location.search.split("=")[1];
    if(searchVal=="wallpaper"){
        transfar('${serverHost}/picture/91_1.html','wallpaper');
        $(".switchTab a").eq(0).click();
    }else if(searchVal=="paint"){
        transfar('${serverHost}/picture/90_1.html','paint');3
        $(".switchTab a").eq(1).click();
    }else if(searchVal=="video"){
        transfar('${serverHost}/picture/0_1.html','video');
        $(".switchTab a").eq(2).click();
    }else if(searchVal=="cartoon"){
        transfar('${serverHost}/picture/93_1.html','cartoon');
        $(".switchTab a").eq(3).click();
    }else if(searchVal=="screenshot"){
        transfar('${serverHost}/picture/92_1.html','screenshot');
        $(".switchTab a").eq(4).click();
    }else{
        //do
    }
})   
</script>

</html>
