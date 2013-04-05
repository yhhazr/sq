<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${gameData.artTitle}_第七大道《神曲》官方网站</title>
<meta name="Keywords" content="神曲,神曲官网,神曲新手卡,神曲礼包,神曲职业"/>
<meta name="description" content="神曲官网-神曲一款角色扮演类的网页游戏，神曲首页，提供神曲下载，神曲BOSS攻略，神曲新手卡，神曲礼包，神曲职业，神曲论坛等一系列的详细游戏介绍，休闲娱乐，从神曲开始。" />

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


<div class="bg4">
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
                                    <a href="${serverHost}/" >首页</a> &gt;
                                    <a href="${serverHost}/gamedata/dataList.html" >资料站</a> &gt;
                                    <strong>资料站详细</strong>
                                </div>
                                <h2 <#if gameData.id=="28" || gameData.id=="34" || gameData.id=="39" || gameData.id=="43" || gameData.id=="48" || gameData.id=="103" || gameData.id=="106" > class="txt4" <#else> class="txt3" </#if> ></h2>
                            </div>
                            <div class="blank40"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod5 newsContent">
                                    <#if gameData.id=="28" || gameData.id=="34" || gameData.id=="39" || gameData.id=="43" || gameData.id=="48" || gameData.id=="103" || gameData.id=="106" >
                                    <div class="movePicBtn1">
                                        <a title="创建角色" href="28.html">创建角色</a>
                                        <a title="基本操作" href="34.html">基本操作</a>
                                        <a title="职业介绍" href="39.html">职业介绍</a>
                                        <a title="跳过新手" href="43.html">跳过新手</a>
                                        <a title="兵种介绍" href="48.html">兵种介绍</a>
                                        <a title="基础资源" href="103.html">基础资源</a>
                                        <a title="登录器下载" href="106.html">登录器下载</a>
                                    </div>
                                    </#if>

                                       
                                        
                                        <div class="pageNews">
                                            <#--资料标题-->
                                            <h5>${gameData.artTitle}</h5>
                                            <div class="pageNewsNav">
                                                <span>作者：神曲运营团队</span>
                                                <span>时间：${gameData.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                                                <span>字体：【<a href="javascript:changeSize('16')">大</a> <a href="javascript:changeSize('14')">中</a> <a href="javascript:changeSize('12')">小</a>】</span>
                                                <span>分享到：</span>
                                                <div class="jiathis_style" style="height: 16px; float:left; padding:0 5px">
                                                    <a class="jiathis_button_qzone"></a>
                                                    <a class="jiathis_button_tsina"></a>
                                                    <a class="jiathis_button_tqq"></a>
                                                    <a class="jiathis_button_renren"></a>
                                                    <a class="jiathis_button_kaixin001"></a>
                                                    <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                                                </div>
                                                <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1349923448311961" charset="utf-8"></script>
                                            </div>
                                            <!--正文start-->
                                            <div class="newsDetail" id="news_content">
                                                <#--资料正文-->
                                                ${gameData.content}
                                            </div>
                                            <!--正文start-->
                                            
                                            <!--相关新闻start-->
                                            <div class="relatedNews">
                                                <p><span>&gt;</span>相关新闻</p>
                                                <ul>
                                                    <#list relatedGameDataList as relatedGameData>
                                                        <li>
                                                            <span>&gt;</span>
                                                            <a href="${relatedGameData.id}.html#A0" title="${relatedGameData.artTitle!}">${relatedGameData.artTitle!}</a>
                                                        </li>
                                                        
                                                    </#list>
                                                </ul>
                                            </div>
                                            <!--相关新闻end-->
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

<#if gameData.id=="28" || gameData.id=="34" || gameData.id=="39" || gameData.id=="43" || gameData.id=="48" || gameData.id=="103" || gameData.id=="106" >
<!--[if ie 6]>
<script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js" ></script>
<script type="text/javascript"> 
    DD_belatedPNG.fix('.movePicBtn1 a');
    DD_belatedPNG.fix(".newsDetail img");
</script
<![endif]-->
</#if>

<script type="text/javascript" src="${staticHost}/js/newsFontSize.js?ver=${version}"></script> 
</html>
