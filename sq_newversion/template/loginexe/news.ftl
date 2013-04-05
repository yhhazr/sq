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
    <div class="doc sign-in news-page clearfix"><!-- Document start -->   
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
        </div>
    </div><!-- Document end -->
</body>
</html>