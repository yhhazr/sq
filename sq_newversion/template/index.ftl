<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="神曲官网-神曲一款角色扮演类的网页游戏，神曲首页，提供神曲下载，神曲BOSS攻略，神曲新手卡，神曲礼包，神曲职业，神曲论坛等一系列的详细游戏介绍，休闲娱乐，从神曲开始。" />
<meta name="Keywords" content="神曲 神曲官网 第七大道神曲官网 shenqu 第七大道 神曲官方网站 第七大道神曲" />
<meta name="baidu-tc-verification" content="9b9f915f0782fc6d7d8c158ab5e8712f" />
<title>神曲_神曲官网_第七大道神曲网页游戏|占星|激活码|开服|攻略|技能加点－神曲2013</title>
<!--[if IE]>
<script src="${staticHost}/js/html5.js"></script>
<![endif]-->
<link rel="stylesheet" href="${staticHost}/css/main.css?ver=${version}" type="text/css" />
<link rel="stylesheet" href="${staticHost}/css/top.css?ver=${version}" type="text/css" />
<link href="${staticHost}/css/cb_style.css?ver=${version}" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${imageHost[0]}/fav_icon.ico" />
<style type="text/css">
 .header{height:384px;background-image:url(${imageHost[1]}/headerBg.jpg?v=20130326); background-repeat: no-repeat; background-position: 0 0; }
 .liveStream .liveStream_topborder{height:1px;background:#251C13;}
  /*default edit*/
.main{ height: 1864px; padding:0 27px 0 23px;} 
.screenCenter{ width: 1036px;}
.startGame{ left: -45px; top:216px; }
.loginTxt{ margin-top: 9px;}
.loginInfo{ padding-top: 8px;}
.loginIndex,.loginInput{ height: 70px;}
.loginInfo .loginLink{ margin:3px 0 0 2px;}
.latestService {padding: 83px 0 0; *padding: 80px 0 0;}
.ranking{ padding-top: 7px;}
.newsWrap{ padding-top: 1px; height: 300px; overflow: hidden;}
.newsListTit{ padding-top: 6px; *}
.newPlayerMain p{ height: 37px;}
.newPlayer{ padding-top: 19px; }
.sqIndexFlv{ display: block;}
.gonewbGift{ top: 300px;}
body{background:#291d13;}
</style>

</head>
<body>
<!-- 顶部导航条start -->
<#include "*/common/headerNav.ftl">
<!-- 顶部导航条end -->

<div class="mainWrap">
    <div class="screenCenter">
        <!-- 头部start -->
        
        <#include "*/common/header.ftl">
        <!-- 头部end -->
        <section class="main">
            
            <!-- 左边start -->
            <div class="modLeft f_l">
                <section class="loginWrap">
                    <p class="loginTxt">用户登录<em>LOGIN</em></p>
                    <!--未登录start-->
                    <div class="loginInfo">
                        <div class="loginIndex">
                            <div class="loginInput">
                                <input type="text" id="uid" holder="请输入帐号" maxlength="20"  />
                                <span id="tips_username" >请输入帐号</span>
								<div class="blank10"></div>
                                <input type="password" id="pwd" holder="请输入密码" maxlength="20"    />
                                <span id="tips_pass" >请输入密码</span>
                            </div>
                            <script type="text/javascript">
                                var tipsUser = document.getElementById("tips_username");
                                var tipPass = document.getElementById("tips_pass");
                                var tipUserName = document.getElementById("uid");
                                var tipPassName = document.getElementById("pwd");
                                if(tipUserName.value!=''||tipPassName.value!=''){tipUserName.value='';tipPassName=''};
                                tipsUser.onclick = function(){
                                    tipsUser.style.display = "none";
                                    tipUserName.focus()
                                }
                                tipPass.onclick = function(){
                                    tipPass.style.display = "none";
                                    tipPassName.focus()
                                }
                                tipUserName.onblur = function(){
                                    if(tipUserName.value==""){
                                        tipsUser.style.display = "block";
                                    }
                                }
                                tipPassName.onblur = function(){
                                    if(tipPassName.value==""){
                                        tipPass.style.display = "block";
                                    }
                                }
                                tipUserName.onfocus = function(){
                                    tipsUser.style.display = "none";
                                }
                                tipPassName.onfocus = function(){
                                    tipPass.style.display = "none";
                                }
                                tipUserName.onkeydown = function(){
                                    tipsUser.style.display = "none";
                                }
                                tipPassName.onkeydown = function(){
                                    tipPass.style.display = "none";
                                }
                            </script>
                            <a class="loginBtn" href="javascript:void(0);" title="登录">登录</a>
                        </div>
						<div class="blank5"></div>
                        <div class="loginLink">
                            <a href="${accountCenterPath}forget.html" title="忘记密码" target="_blank"><em class="e1"></em>忘记密码</a>
                            <a href="javascript:void(0);" title="快速注册" class="quickregister" style="margin-right:0;"><em class="e2"></em>快速注册</a>
                        </div>
                    </div>
                    <!--未登录end-->
                    
                    <!--已登录start-->
                    <div class="logined" style="display:none;">
                        <div class="blank5"></div>
                        <div class="loginedInfo">
                            <span>heart_11</span>, 您好！
                            [<a title="退出" class="indexLogout" href="javascript:void(0);" id="login-out">退出</a>]
                        </div>
                        <div class="blank5"></div>
                        <div class="btn3">
                            <a title="完善密保资料" target="_blank" href="http://account.7road.com/">完善密保资料</a>
                            <!-- <div class="safeLevel f_l">
                                <span>安全级别：</span>
                                <i>4</i>
                                <em class="s4"></em>
                            </div> -->
                        </div>
                       
                        <div class="recentService">
                            <p>您上次登陆的服务器是：</p>
                            <a href="#" target="_blank">双线136服 魔影骑士</a>
                        </div>
                    </div>
                    <!--已登录end-->
                </section>
                <section class="guide">
                    <a class="download" href="http://219.129.216.161/updates/shenqu/7road/7road神曲极速登录器.exe" title="登录器下载" target="_blank">登录器下载</a>
                    <a class="gift" href="http://sq.7road.com/others/newbGift.html" title="礼包领取" target="_blank">礼包领取</a>
                    <a class="quickPay" href="http://pay.7road.com/?gameid=1&type=game" title="快速充值" target="_blank">快速充值</a>
                    <a class="tool" href="http://sq.7road.com/skill.html" title="工具大全" target="_blank">工具大全</a>
                </section>
                <div class="latestService">
                    <ul>
                        <#list newServers as server>
						<#if server_index == 0>
						<li class="latest">
						<#else>
						<li>
						</#if>
                            <span>&gt;</span>
                            <a href="${loginGamePath}${server.id}" serverId="${server.id}" title="${server.serverName}" gameUrl="${loginGameRealPath}${server.id}" target="_blank">
                                <em>${server.serverName}</em>
                                <em>火爆开启</em>
                            </a>
                            <i>火爆</i>
                        </li>
                        </#list>
                    </ul>
                    <a class="moreService" href="${serverHost}/serverList.html" title="查看更多服务器" target="_blank">查看更多服务器</a>
                </div>
                <section class="ranking">
                    <h3></h3>
                    <div class="rankingSelect">
                        <select class="gameArea">
                            <#list rankServers as server>
                                <option value="${server.id}">${server.serverName}</option>
                            </#list>                           
                        </select>
                        <select class="gameRank">
                            <option>等级排行</option>
                            <option>个人战力</option>
                            <option>公会等级</option>
                            <option>公会战力</option>
                        </select>
                    </div>
                    <!-- 排行榜开始 -->
                    <div id="rank">
                        
                    </div>                        
                    <!-- 排行榜结束 -->
                </section>
                <div class="blank10"></div>
                <section class="serviceCenter">
                    <a style="cursor:text">
                        <span>客服电话</span>
                        <em>0755-61886777</em>
                    </a>
                    <a href="http://crm2.qq.com/page/portalpage/wpa.php?uin=800073277&f=1&ty=1&aty=0&a=&from=6" target="_blank">
                        <span>第七大道在线客服</span>
                        <em>24小时在线客服</em>
                    </a>
                    <a href="mailto:kefu@7road.com" target="_blank">
                        <span>客服投诉邮箱</span>
                        <em>kefu@7road.com</em>
                    </a>
                </section>
                <div class="blank10"></div>
                <section class="attention">
                    <ul>
                        <li style="height:80px;">
                            <a class="weiboPic" style="color:#C89B65;text-decoration:none;" title="孟治昀"><img src="${imageHost[2]}/m1.jpg" alt="" /></a>
                            <div class="weiboInfo">
                                <h4><span class="dot3" style="color:#C89B65; padding-right:18px;">第七大道孟治昀</span></h4>
                                <a class="weiboAttention" href="http://weibo.com/u/1843910742" title="加关注" target="_blank">加关注</a>
                                <a class="qqAttention" href="http://t.qq.com/mengshuxuan" title="加关注" target="_blank">加关注</a>
                            </div>
                            <div class="weiboTips">深圳第七大道科技有限公司COO</div>
                        </li> 
                        <li>
                            <a class="weiboPic" href="http://t.qq.com/sz7road" target="_blank" title=""><img src="${imageHost[3]}/m2.jpg" alt="" /></a>
                            <div class="weiboInfo">
                                <h4><a class="dot4" href="http://t.qq.com/sz7road" target="_blank">第七大道科技有限公司</a></h4>
                                <a class="weiboAttention" href="http://e.weibo.com/sz7road" target="_blank" title="加关注">加关注</a>
                                <a class="qqAttention" href="http://t.qq.com/sz7road" target="_blank" title="加关注">加关注</a>
                            </div>
                        </li> 
                        <li>
                            <a class="weiboPic" href="http://e.weibo.com/u/2597419482" target="_blank" title="神曲新浪微博"><img src="${imageHost[4]}/m3.jpg" alt="" /></a>
                            <div class="weiboInfo">
                                <h4><a class="dot3" href="http://e.weibo.com/u/2597419482" target="_blank">《神曲》新浪官方微博</a></h4>
                                <a class="weiboAttention" href="http://e.weibo.com/u/2597419482#" title="加关注" target="_blank">加关注</a>
                            </div>
                        </li> 
                        <li>
                            <a class="weiboPic" href="http://e.t.qq.com/shenqu7road" target="_blank" title="神曲腾讯微博"><img src="${imageHost[4]}/m3.jpg" alt="" /></a>
                            <div class="weiboInfo">
                                <h4><a class="dot4" href="http://e.t.qq.com/shenqu7road" target="_blank">《神曲》腾讯官方微博</a></h4>
                                <a class="qqAttention" href="http://e.t.qq.com/shenqu7road" title="加关注" target="_blank">加关注</a>
                            </div>
                        </li> 
                    </ul>
                </section>
            </div>
            <!-- 左边end -->
            <!-- 右边start -->
            <div class="modRight f_r">
                <!-- 幻灯start -->
                <section class="banner" id="banner">
                    <ul>
                        <#list carouselImages as carouselImage>
                            <#if carouselImage_index==0>
                                <li class="on">${carouselImage_index+1}</li>  
                            <#else>
                                <li>${carouselImage_index+1}</li>
                            </#if>
                        </#list>
                    </ul>
                   <div id="banner_list">
                        <#list carouselImages as carouselImage>
						<a href="${carouselImage.url}" target="_blank"><img src="${imageHost[5]}/${carouselImage.content}"  /></a>
						</#list>
                    </div>
                </section>
                <!-- 幻灯end -->
                
                <section class="newsWrap p12">
                    <!-- 新闻start -->
                    <article class="news switch1 f_l">
                        <h3 class="switchTab">
                            <span class="on"><a class="n1" href="${serverHost}/news/newsList.html?searchVal=hotnews" title="热点" target="_blank">热点</a></span>
                            <span><a class="n2" href="${serverHost}/news/newsList.html?searchVal=news" title="新闻" target="_blank">新闻</a></span>
                            <span><a class="n3" href="${serverHost}/news/newsList.html?searchVal=bulletin" title="公告" target="_blank">公告</a></span>
                            <span><a class="n4" href="${serverHost}/news/newsList.html?searchVal=activity" title="活动" target="_blank">活动</a></span>
                            <span><a class="n5" href="${serverHost}/news/newsList.html?searchVal=lantie" title="蓝贴" target="_blank">蓝贴</a></span>
                            <a class="more1" href="${serverHost}/news/newsList.html?searchVal=hotnews" title="更多" target="_blank" style="margin-right:5px;">+MORE</a>
                        </h3>
                        
                        <div class="switchMain" style="display:block;">
                            <div class="latestNews">
                                <h4>
                                    <span class="latestNewsDot">&nbsp;&nbsp;</span>
                                    <a href="${serverHost}/news/detail/${headlineNews.typeId}_${headlineNews.newsId}.html" title="${headlineNews.artTitle}">${headlineNews.artTitle}</a>
                                </h4>
                                <p>
                                   ${headlineNews.introduction}
                                    <a href="${serverHost}/news/detail/${headlineNews.typeId}_${headlineNews.newsId}.html" title="查看详细">查看详细</a>
                                </p>
                            </div>
                            <div class="newsList">
                                <div class="newsListTit">
                                    <ul>
                                        <#list hotList as hot>
                                            <li <#if hot_index <3 > class="hot" <#else></#if> >
                                                <span class="newsListDate">[${hot.modifyDate?string("yyyy-MM-dd")}]</span>
                                                <strong>&gt;</strong>
                                                <a href="${serverHost}/news/detail/${hot.typeId}_${hot.newsId}.html" title="${hot.artTitle}" target="_blank">${hot.artTitle}</a>

                                                <#if hot_index <3 > 
                                                     <img src="${imageHost[0]}/newHot.gif">
                                                </#if>
                                               
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="switchMain">
                            <div class="newsList1">
                                <div class="newsListTit1">
                                    <ul>
                                        <#list newsList as news>
                                            <li <#if news_index <3 > class="hot" <#else></#if> >
                                                <span class="newsListDate">[${news.modifyDate?string("yyyy-MM-dd")}]</span>
                                                <strong>&gt;</strong>
                                                <a href="${serverHost}/news/detail/${news.typeId}_${news.newsId}.html" title="${news.artTitle}" target="_blank">${news.artTitle}</a>
                                                <#if news.stateId==3 >
                                                    <img src="${imageHost[0]}/newHot.gif">
                                                </#if>                                                
                                            </li>
                                        </#list>
                                        
                                        
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="switchMain">
                            
                            <div class="newsList1">
                                <div class="newsListTit1">
                                    <ul>
                                        <#list bulletinList as bullet>
                                            <li <#if bullet_index <3 > class="hot" <#else></#if> >
                                                <span class="newsListDate">[${bullet.modifyDate?string("yyyy-MM-dd")}]</span>
                                                <strong>&gt;</strong>
                                                <a href="${serverHost}/news/detail/${bullet.typeId}_${bullet.newsId}.html" title="${bullet.artTitle}" target="_blank">${bullet.artTitle}</a>
                                                <#if bullet.stateId==3 >
                                                    <img src="${imageHost[0]}/newHot.gif">
                                                </#if>                                                
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="switchMain">
                            
                            <div class="newsList1">
                                <div class="newsListTit1">
                                    <ul>
                                        <#list activityList as activity>
                                            <li <#if activity_index <3 > class="hot" <#else></#if> >
                                                <span class="newsListDate">[${activity.modifyDate?string("yyyy-MM-dd")}]</span>
                                                <strong>&gt;</strong>
                                                <a href="${serverHost}/news/detail/${activity.typeId}_${activity.newsId}.html" title="${activity.artTitle}" target="_blank">${activity.artTitle}</a>
                                                <#if activity.stateId==3 >
                                                    <img src="${imageHost[0]}/newHot.gif">
                                                </#if>                                                
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="switchMain">
                            
                            <div class="newsList1">
                                <div class="newsListTit1">
                                    <ul>
                                       <#list lantieList as lantie>
                                            <li class="lantie">
                                                <span class="newsListDate">[${lantie.modifyDate?string("yyyy-MM-dd")}]</span>
                                                <strong>&gt;</strong>
                                                <a href="${serverHost}/news/detail/${lantie.typeId}_${lantie.newsId}.html" title="${lantie.artTitle}" target="_blank">${lantie.artTitle}</a>
                                                <#if lantie.stateId==3 >
                                                    <img src="${imageHost[0]}/newHot.gif">
                                                </#if>                                                
                                            </li>
                                        </#list>

                                    </ul>
                                </div>
                            </div>
                        </div>
                    </article>
                    <!-- 新闻end -->
                    <div class="newsListPic f_r">
                        <#list rightImages as rightImage>
						    <a href="${rightImage.url}" title="" target="_blank"><img src="${imageHost[1]}/${rightImage.content}" alt="" width="184" height="88" /></a>
						</#list>
                    </div>
                </section>
                <section class="newPlayer p12">
                    <div class="h40"><a class="more1" href="${serverHost}/gamedata/dataList.html" title="更多" target="_blank">+MORE</a></div>
					<div class="blank10"></div>
                    <h3 class="newPlayerTab f_l">
                        <span>初入神曲<em>&gt;</em></span>
                        <span>高手进阶<em>&gt;</em></span>
                        <span>特色系统<em>&gt;</em></span>
                        <span>大型玩法<em>&gt;</em></span>
                        <span>职业介绍<em>&gt;</em></span>
                    </h3>


                    <div class="newPlayerMain f_l">
                        <p><#list gameDate1 as gameDate><a href="${serverHost}/gamedata/${gameDate.id}.html" title="${gameDate.artTitle!}" target="_blank">${gameDate.artTitle!}</a>|</#list></p>
                        <p><#list gameDate2 as gameDate><a href="${serverHost}/gamedata/${gameDate.id}.html" title="${gameDate.artTitle!}" target="_blank">${gameDate.artTitle!}</a>|</#list></p>
                        <p><#list gameDate3 as gameDate><a href="${serverHost}/gamedata/${gameDate.id}.html" title="${gameDate.artTitle!}" target="_blank">${gameDate.artTitle!}</a>|</#list></p>
                        <p><#list gameDate4 as gameDate><a href="${serverHost}/gamedata/${gameDate.id}.html" title="${gameDate.artTitle!}" target="_blank">${gameDate.artTitle!}</a>|</#list></p>
                        <p><a href="${serverHost}/professionIntroduction/profession5.html" title="男法师" target="_blank">男法师</a>|<a href="${serverHost}/professionIntroduction/profession1.html" title="男战士" target="_blank">男战士</a>|<a href="${serverHost}/professionIntroduction/profession3.html" title="男射手" target="_blank">男射手</a>|<a href="${serverHost}/professionIntroduction/profession4.html" title="女射手" target="_blank">女射手</a>|<a href="${serverHost}/professionIntroduction/profession2.html" title="女战士" target="_blank">女战士</a>|<a href="${serverHost}/professionIntroduction/profession6.html" title="女法师" target="_blank">女法师</a>|</p>
                    </div>
                </section>
                <div class="blank10"></div>
                <!-- 滚动start -->
                <section class="movePic p12"> 
                    <div class="h40" ><a class="more1" href="${serverHost}/picture/picList.html?searchVal=screenshot" title="更多" target="_blank">+MORE</a></div>
                    <p>
                        <#list playerPhotos as photo>

						    <a href="${imageHost[2]}/photo/${photo.photoName}" rel="clearbox[gallery=playerPhoto]" title=""><img data-original="${imageHost[2]}/photo/${photo.thumbnail!photo.photoName}" src="${imageHost[0]}/grey.gif" alt="" /></a>

						</#list>
                    </p>
                </section>
                <!-- 滚动end -->
                <div class="blank10"></div>
                <section class="gamePic switch3 p12">
                    <h3 class="switchTab">
                        <span class="on"><a class="g1" href="${serverHost}/picture/picList.html?searchVal=wallpaper" title="壁纸" target="_blank">壁纸</a></span>
                        <span><a class="g2" href="${serverHost}/picture/picList.html?searchVal=paint" title="原画" target="_blank">原画</a></span>
                        <span><a class="g3" href="${serverHost}/picture/picList.html?searchVal=video" title="视频" target="_blank">视频</a></span>
                        <span><a class="g4" href="${serverHost}/picture/picList.html?searchVal=cartoon" title="漫画" target="_blank">漫画</a></span>
                        <a class="more2" href="${serverHost}/picture/picList.html?searchVal=wallpaper" title="更多" target="_blank">+MORE</a>
                    </h3>
                    <div class="switchMain" style="display:block;">
                        <div class="gamePicList">
                            <ul>
                                <#list wallpapers as photo>
									<li>
										<a href="${imageHost[3]}/photo/${photo.photoName}" rel="clearbox[gallery=wallpapers]" title="" ><img data-original="${imageHost[3]}/photo/${photo.thumbnail!photo.photoName}" src="${imageHost[0]}/grey.gif" alt="pic" /></a>

									</li>
								</#list>
                            </ul>
                        </div>
                    </div>
                    <div class="switchMain">
                        <div class="gamePicList">
                            <ul>
                                <#list paints as photo>
									<li>

										<a href="${imageHost[4]}/photo/${photo.photoName}" rel="clearbox[gallery=paints]" title="" ><img data-original="${imageHost[4]}/photo/${photo.thumbnail!photo.photoName}" alt="pic" src="${imageHost[0]}/grey.gif" /></a>

									</li>
								</#list>
                            </ul>
                        </div>
                    </div>
                    <div class="switchMain">
                        <div class="gamePicList">
                            <ul>
                                <#list videos as photo>
									<li>

										<a href="${photo.videoLink!}" rel="clearbox[gallery=videos,,width=600,,height=400]" title="" ><img data-original="${imageHost[5]}/${photo.videoPicName!}" src="${imageHost[0]}/grey.gif" alt="pic" /></a>

									</li>
								</#list>
                            </ul>
                        </div>
                    </div>
                    <div class="switchMain">
                        <div class="gamePicList">
                            <ul>
                                <#list cartoons as photo>
									<li>

										<a href="${imageHost[0]}/photo/${photo.photoName}" rel="clearbox[gallery=cartoons]" title="" ><img data-original="${imageHost[0]}/photo/${photo.thumbnail!photo.photoName}" src="${imageHost[0]}/grey.gif" alt="pic" /></a>

									</li>
								</#list>
                            </ul>
                        </div>
                    </div>
                </section>
                <section class="weiboIframe switch2">
                    <h3 class="switchTab">
                        <span class="on"><a class="w1" href="javascript:void(0);">微博话题墙</a></span>
                        <!-- <span><a class="w2" href="javascript:void(0);">腾讯微博</a></span> -->
                    </h3>
                    <div class="weiboIframeMain switchMain" style="display:block;">
                    <!-- 新浪微博start -->
                    <iframe width="100%" height="360" frameborder="0" scrolling="no" src="http://widget.weibo.com/livestream/listlive.php?language=zh_cn&width=0&height=360&uid=2991850457&skin=1&refer=1&appkey=&pic=0&titlebar=1&border=1&publish=1&atalk=1&recomm=0&at=0&colordiy=1&color=433422,251C13,C89B65,FF9307,433422,433422&atopic=%e7%ac%ac%e4%b8%83%e5%a4%a7%e9%81%93%e7%a5%9e%e6%9b%b2&ptopic=%e7%ac%ac%e4%b8%83%e5%a4%a7%e9%81%93%e7%a5%9e%e6%9b%b2&dpc=1"></iframe>
                    <!-- 新浪微博end -->
                    </div>
                   <!--  <div class="weiboIframeMain switchMain">
                    腾讯微博start
                    <iframe frameborder="0" scrolling="auto" src="http://wall.v.t.qq.com/index.php?c=wall&a=index&t=%e7%ac%ac%e4%b8%83%e5%a4%a7%e9%81%93%e7%a5%9e%e6%9b%b2&ak=801250820&w=725&h=360&o=7&s=1" width="725" height="360"></iframe>
                    腾讯微博end 
                    </div> -->
                </section>
            </div>
            <!-- 右边end -->
            <div class="blank10"></div>
            <section class="media">
                <div class="mediaPic" id="colee_bottom">
                    <ul id="colee_bottom1">
                        <li><a class="m01" href="http://www.17173.com/" title="" target="_blank">17173游戏</a></li>
                        <li><a class="m02" href="http://www.neotv.cn/" title="" target="_blank">NEOTV</a></li>
                        <li><a class="m03" href="http://www.52pk.com/" title="" target="_blank">52PK游戏网</a></li>
                        <li><a class="m04" href="http://www.766.com/" title="" target="_blank">766</a></li>
                        <li><a class="m05" href="http://www.tgbus.com/" title="" target="_blank">电玩巴士</a></li>
                        <li><a class="m06" href="http://www.sgamer.com/" title="" target="_blank">超级玩家</a></li>
                        <li><a class="m07" href="http://www.zmrgame.com/" title="" target="_blank">掌门人资讯网</a></li>
                        <li><a class="m08" href="http://www.yzz.cn/" title="" target="_blank">叶子猪</a></li>
                        <li><a class="m09" href="http://game.aipai.com/" title="" target="_blank">爱拍游戏</a></li>
                        <li><a class="m10" href="http://www.zol.com.cn/" title="" target="_blank">中关村在线</a></li>
                        <li><a class="m11" href="http://www.plu.cn/" title="" target="_blank">PLU试玩网</a></li>
                        <li><a class="m12" href="http://www.eeyy.com/" title="" target="_blank">一游网</a></li>
                    </ul>
                    <ul id="colee_bottom2" ></ul>   
                </div>
            </section>
        </section> 
	</div>
</div>
<div class="blank10"></div>

<!-- 底部start -->
<#include "*/common/footer.ftl">
<!-- 底部end -->

<div class="weixinBox">
    <img src="http://image.7road.com/weixinSmail.jpg" class="smailwx" />
    <img src="http://image.7road.com/weixinBig.jpg" class="bigwx" />
    <div class="closewx"></div>
</div>

<!--div class="indexPlay">
    <div class="closePlay" title="关闭"></div>
    <embed class="indexPlayFlv" id="indexPlayFlv" src='${staticHost}/flash/play.swf' type='application/x-shockwave-flash' width='700' height='340' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>
</div-->

<script type="text/javascript" src="${staticHost}/js/main.js?ver=${version}"></script>
<script type="text/javascript" src="${staticHost}/js/jquery.lazyload.min.js?ver=${version}"></script>
<script type="text/javascript" src="${staticHost}/js/clearbox.js?ver=${version}"></script>  
<!--[if IE 6]><script type="text/javascript" src="${staticHost}/js/DD_belatedPNG.js"></script>
<script type="text/javascript">
    DD_belatedPNG.fix(".sqIndexFlv");
</script>
<![endif]-->

<script type="text/javascript">
//媒体滚动

var speed=60;
var lineSize = 22;
var size = 22;
var line = 0;
if(size % 5 == 0){
    line = parseInt(size / 5);
}else{
    line = parseInt(size/5 + 1);
}

var height = line * lineSize;

var colee2=document.getElementById("colee_bottom2");
var colee1=document.getElementById("colee_bottom1");
var colee=document.getElementById("colee_bottom");

if(line <= 2){
    colee2.style.display = "none";  
}
colee2.innerHTML=colee1.innerHTML; //克隆colee1为colee2

function Marquee1(){
//当滚动至colee1与colee2交界时
if(height-colee.scrollTop<=0){
  colee.scrollTop=0; //colee跳到最顶端
 }else{
 colee.scrollTop++
}
}
var MyMar1=setInterval(Marquee1,speed)//设置定时器
//鼠标移上时清除定时器达到滚动停止的目的
colee.onmouseover=function() {clearInterval(MyMar1)}
//鼠标移开时重设定时器
colee.onmouseout=function(){MyMar1=setInterval(Marquee1,speed)}

//延迟加载图片
$("img[data-original]").lazyload({ 
    skip_invisible : false
});

setTimeout(function(){$(".womanSwf").css("display","block")},6000)


/*视频播放
$(function(){
    $(".sqIndexFlv").click(function(){
        $(".popLock,.indexPlay").show();
    });
    $(".popLock,.closePlay").click(function(){
        $(".popLock,.indexPlay").hide();
    })
})*/
</script>

</body>
</html>
