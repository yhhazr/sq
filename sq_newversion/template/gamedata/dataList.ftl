<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>神曲资料站_第七大道《神曲》官方网站</title>
<meta name="Keywords" content="神曲,神曲官网,神曲新手卡,神曲礼包,神曲职业" />
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
<#include "../common/headerNav.ftl">
<!-- 顶部导航条end -->

<div class="bg2">
    <div class="screenCenter">
        <!-- 头部start -->
        <#include "../common/header.ftl">
        <!-- 头部end -->
        <section class="pageMain">
        
            
            <!-- 左边start -->
            <#include "../common/list_Leftside.ftl">
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
                                    <a href="${serverHost}" >首页</a> &gt;
                                    <strong>资料站</strong>
                                </div>
                                <h2 class="txt3"></h2>
                            </div>
                            <div class="blank40"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod6">
                                        <h3><span class="c1">初入神曲</span></h3>
                                        <ul>
                                            <#list list01?keys as gameType>
                                            <li>
                                                <span>${gameType}<em>&gt;</em></span>
                                                <div <#if ((list01[gameType]?size)>6)> class="line2 f_l" <#else> class="line1 f_l" </#if> >
                                                    <#list list01[gameType] as gamedate><a href="${serverHost}/gamedata/${gamedate.id}.html#A0" title="${gamedate.artTitle!}" target="_blank"><em></em>${gamedate.artTitle!}</a></#list>  
                                                </div>
                                            </li>   
                                            </#list>
                                        </ul>                                       
                                    </div>
                                </div>
                            </div>
                            <div class="blank10"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod6">
                                        <h3><span class="c2">高手进阶</span></h3>
                                        <ul>
                                            <#list list02?keys as gameType>
                                            <li>
                                                <span>${gameType}<em>&gt;</em></span>
                                                <div <#if ((list02[gameType]?size)>6)> class="line2 f_l" <#else> class="line1 f_l" </#if> >
                                                    <#list list02[gameType] as gamedate><a href="${serverHost}/gamedata/${gamedate.id}.html#A0" title="${gamedate.artTitle!}" target="_blank"><em></em>${gamedate.artTitle!}</a></#list>  
                                                </div>
                                            </li>   
                                            </#list>
                                        </ul>                                       
                                    </div>
                                </div>
                            </div>
                            <div class="blank10"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod6">
                                        <h3><span class="c3">特色系统</span></h3>
                                        <ul>
                                            <#list list03?keys as gameType>
                                            <li>
                                                <span>${gameType}<em>&gt;</em></span>
                                                <div <#if ((list03[gameType]?size)>6)> class="line2 f_l" <#else> class="line1 f_l" </#if> >
                                                    <#list list03[gameType] as gamedate><a href="${serverHost}/gamedata/${gamedate.id}.html#A0" title="${gamedate.artTitle!}" target="_blank"><em></em>${gamedate.artTitle!}</a></#list>  
                                                </div>
                                            </li>   
                                            </#list>
                                        </ul>                                       
                                    </div>
                                </div>
                            </div>  
                            <div class="blank10"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod6">
                                        <h3><span class="c4">大型玩法</span></h3>
                                        <ul>
                                            <#list list04?keys as gameType>
                                            <li>
                                                <span>${gameType}<em>&gt;</em></span>
                                                <div <#if ((list04[gameType]?size)>6)> class="line2 f_l" <#else> class="line1 f_l" </#if> >
                                                    <#list list04[gameType] as gamedate><a href="${serverHost}/gamedata/${gamedate.id}.html#A0" title="${gamedate.artTitle!}" target="_blank"><em></em>${gamedate.artTitle!}</a></#list>  
                                                </div>
                                            </li>   
                                            </#list>
                                        </ul>                                       
                                    </div>
                                </div>
                            </div>  
                            <div class="blank10"></div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod6">
                                        <h3><span class="c5">大型玩法</span></h3>
                                        <div class="btn1">
                                            <a href="http://sq.7road.com/professionIntroduction/profession1.html" target="_blank" >男战士</a>
                                            <a href="http://sq.7road.com/professionIntroduction/profession2.html" target="_blank" >女战士</a>
                                            <a href="http://sq.7road.com/professionIntroduction/profession3.html" target="_blank" >男射手</a>
                                            <a href="http://sq.7road.com/professionIntroduction/profession4.html" target="_blank" >女射手</a>
                                            <a href="http://sq.7road.com/professionIntroduction/profession5.html" target="_blank" >男法师</a>
                                            <a href="http://sq.7road.com/professionIntroduction/profession6.html" target="_blank" >女法师</a>
                                            <div class="blank10"></div>
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
<#include "../common/footer.ftl">
<!-- 底部end -->
<!--[if ie 6]>
<script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js" ></script>
<script type="text/javascript"> 
    DD_belatedPNG.fix('.btn1 a');
   
</script
<![endif]-->

</body>
</html>
