<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title></title>
        <link href="${staticHost}/css/sq_login.css?v=${version}" rel="stylesheet" type="text/css" />
		<style>
			ul {width:315px; margin:0px; padding:0px; list-style-position: inside; list-style-type: square;line-height: 25px;font-size: 12px;}
			ul li{width:315px; margin:0px; padding:0px; }
			ul li a{ color:#c7892c; text-decoration:none; }
			ul li a:hover{ color:#ff0000; text-decoration:none;}
		</style>
    </head>
	<body  bgColor="transparent">
	<div class="news">		
		<ul>
        <#list newsList as news>
	         <#if news_index lt 3> 
				<li><a href="${serverHost}/news/detail/${news.typeId}_${news.newsId}.html#A0" target="_blank" title="${news.artTitle}" style="color:red;font-weight:bold">${news.artTitle}</a></li>
			 <#else>
				<li><a href="${serverHost}/news/detail/${news.typeId}_${news.newsId}.html#A0" target="_blank" title="${news.artTitle}">${news.artTitle}</a></li>
			</#if>
		</#list>
        </ul>
        <script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"  charset="UTF-8"></script>
        <script src="${staticHost}/js/chstring.js?v=${version}"></script>
        <script>
        	$(function () {
        		var li = $(".news").find("li a");
        		for (var i=0; i< li.length;i++){
					var str = $(li[i]).text().trim();
					if (str.getStringLength() > 40){
						if (i < 3) {
							$(li[i]).text(str.subCHString(0,37) + "...");	
						} else {
							$(li[i]).text(str.subCHString(0,40) + "...");
						}
					}
				}
        	})
        </script>
	</body>
</html>