<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${news.artTitle}" />
<meta name="description" content="神曲" />
<title>${news.artTitle}__第七大道《神曲》官方网站</title>
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
                                    <strong>新闻正文</strong>
                                </div>
                                <h2 class="txt1"></h2>
                            </div>
                            <div class="newsListImg">
                                <a href="${newestNews.url!}" ><img src="${imageHost[0]}/${newestNews.content!}"  /></a>
                            </div>
                            <div class="pageMod1">
                                <div class="pageMod2">
                                    <div class="pageMod3 newsContent">
                                        <h3 class="switchTab">
                                            <span><a  href="${serverHost}/news/newsList.html?searchVal=hotnews" class="n1">热点</a></span>
                                            <span><a  href="${serverHost}/news/newsList.html?searchVal=news" class="n2">新闻</a></span>
                                            <span><a  href="${serverHost}/news/newsList.html?searchVal=bulletin" class="n3">公告</a></span>
                                            <span><a  href="${serverHost}/news/newsList.html?searchVal=activity" class="n4">活动</a></span>
                                            <span><a  href="${serverHost}/news/newsList.html?searchVal=lantie" class="n5">蓝贴</a></span>
                                            <a  href="${serverHost}/news/newsList.html?searchVal=hotnews" class="more1">+MORE</a>
                                        </h3>
                                        <div class="pageNews">
                                            <h5>${news.artTitle}</h5>
                                            <div class="pageNewsNav">
                                                <span>作者：神曲运营团队</span>
                                                <span>时间：${news.modifyDate?string("yyyy-MM-dd HH:mm:ss")}
</span>
                                                <span>字体：【<a href="javascript:changeSize('16')">大</a> <a href="javascript:changeSize('14')">中</a> <a href="javascript:changeSize('12')">小</a>】</span>
                                                <span>分享到：</span>
                                                <!-- JiaThis Button BEGIN -->
                                                <span class="jiathis_style">
                                                    <a class="jiathis_button_qzone"></a>
                                                    <a class="jiathis_button_tsina"></a>
                                                    <a class="jiathis_button_tqq"></a>
                                                    <a class="jiathis_button_renren"></a>
                                                    <a class="jiathis_button_kaixin001"></a>
                                                    <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                                                    <a class="jiathis_counter_style"></a>
                                                </span>
                                                <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1351068499586158" charset="utf-8"></script>
                                                <!-- JiaThis Button END -->
                                            </div>
                                            <!--正文start-->
                                            <div class="newsDetail" id="news_content">
                                                ${news.newsContent}
                                            </div>
                                            <!--正文start-->
                                            
                                            <!--相关新闻start-->
                                            <div class="relatedNews">
                                                <p><span>&gt;</span>相关新闻</p>
                                                <ul>
                                                    
                                                    <#list relatedNewsList as relatedNews>
                                                        <li>
                                                            <span>&gt;</span>
                                                            <a href="${relatedNews.typeId}_${relatedNews.newsId}.html#A0" title="${relatedNews.artTitle!}">${relatedNews.artTitle!}</a>
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

<script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="${staticHost}/js/main.js?ver=${version}"></script>  

<!--[if IE 6]><script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix(".newsDetail img");
</script>
<![endif]-->
</body>
<script type="text/javascript" src="${staticHost}/js/newsFontSize.js?ver=${version}"></script> 
</html>
