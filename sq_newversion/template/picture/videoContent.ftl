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
<script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"></script>  
<script type="text/javascript" src="${staticHost}/js/scroll.js"></script>
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
                                    <a href="${serverHost}/picture/picList.html">下载中心</a> &gt;
                                    <strong>视频详细</strong>
                                </div>
                                <h2 class="txt2"></h2>
                            </div>
                            <div class="blank40"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod4 newsContent imgList">
                                        <h3 class="switchTab">
                                            <span><a  href="${serverHost}/picture/picList.html?searchVal=wallpaper"  class="n1" >游戏壁纸</a></span>
                                            <span><a  href="${serverHost}/picture/picList.html?searchVal=paint" class="n2"  >游戏原画</a></span>
                                            <span class="on"><a href="${serverHost}/picture/picList.html?searchVal=video" class="n3"  >游戏视频</a></span>
                                            <span><a  href="${serverHost}/picture/picList.html?searchVal=cartoon" class="n4"  >游戏漫画</a></span>
                                            <span><a  href="${serverHost}/picture/picList.html?searchVal=screenshot" class="n6" >玩家风采<!--此处数据为原版之游戏截图--></a></span>
                                        </h3>
                                        <div class="movePicArea">
                                            <div class="imgDetail">
                                                <#if video.videoLink??> 
                                                   <div >
                                                        <object id="FlashID" codebase="" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="600" height="400">
                                                            <param name="movie" value="${video.videoLink}">
                                                            <param name="wmode" value="transparent"> 
                                                            <param name="allowScriptAcces" value="always">
                                                            <param name="quality" value="high">
                                                            <embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="${video.videoLink}" allownetworking="internal" allowscriptaccess="never" invokeurls="false" wmode="transparent" width="607" height="405">
                                                        </object>
                                                    </div> 
                                                </#if>
                                                
                                            </div>
                                                 
                                           
                                        </div>
                                        <div class="movePicTxt">${video.videoTitle!}</div>
                                        <div class="movePicBtn" style="display:none">
                                            <a href="javascript:void(0);" target="_blank" title="1440x900">1440x900</a>
                                            <a href="javascript:void(0);" target="_blank" title="1280x1024">1280x1024</a>
                                        </div>
                                        <div class="movePicList">
                                            <span onMouseUp="ISL_StopDown_1()" class="rightBtn" onMouseDown="ISL_GoDown_1()" onMouseOut="ISL_StopDown_1()" title="left"></span>
                                            <div class="pcont" id="ISL_Cont_1">
                                                <div class="ScrCont">
                                                    <div id="List1_1">
                                                        <#--此处A click时候需要取得当前swf的URL并在上面div中切换-->
                                                        <#list allVideos as allVideo>
                                                            
                                                            <a class="picBg"  href="${serverHost}/picture/v_p${allVideo.videoId}.html">
                                                                <img src="${imageHost[0]}/${allVideo.videoPicName}" id="${allVideo.videoId}" alt="${allVideo.videoLink}" />
                                                            </a>

                                                        </#list>
                                                    </div>
                                                    <div id="List2_1"></div>
                                                </div>
                                            </div>
                                            <span onMouseUp="ISL_StopUp_1()" class="leftBtn" onMouseDown="ISL_GoUp_1()" onMouseOut="ISL_StopUp_1()" title="right"></span>
                                            <script type="text/javascript">picrun_ini();</script>
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
</body>

<script type="text/javascript" src="${staticHost}/js/main.js?ver=${version}"></script>  
<script type="text/javascript" src="${staticHost}/js/imgview.js?ver=${version}" charset="utf-8"></script>
<script type="text/javascript">
function videoTra(url, obj){
    //var arr = $(".downloadNav a").css({background:"url('http://image5.7road.com/dl_menu.jpg')",color:"#fff"});
    //$("#" + obj).css({background:"url('http://image3.7road.com/dl_menu_on.jpg')",color:"#022E5A"});
    
    window.location = url;
}


function transfar(url, obj){
   // var arr = $(".downloadNav a").css({background:"url('http://image5.7road.com/dl_menu.jpg')",color:"#fff"});
   // $("#" + obj).css({background:"url('http://image5.7road.com/dl_menu_on.jpg')",color:"#022E5A"});
    $.get(url, function(data){
        $("#downloadBox").html(data);
        CB_Init();
    });
}


//点击滚动图片，
$(function(){
    //$("#colee_left img").bind("mouseenter", function(){show3(this);});
    //$("#List1_1 img").bind("click", function(){playervideo(this.id);});

});

function playervideo(id){
    var url = "v_p" + id + ".html";
    var player = $(".imgDetail");
    $(player).html("");
    $.get(url, function(data){
        $(player).html(data);
    });
}
/*function show3(obj){
    var rect = obj.getBoundingClientRect();
    var cx = rect.left;
    var cy = rect.top;  
    var width = obj.getAttribute("width");
    var height = obj.getAttribute("height");
    var src = obj.getAttribute("id");
    var alt = obj.getAttribute("alt");//下载用地址
    createDiv2(cx, cy, width, height, src, alt);
}*/

</script>

</html>
