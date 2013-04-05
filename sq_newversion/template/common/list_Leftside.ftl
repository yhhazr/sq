<div class="pageModLeft f_l">
    <div class="pageLeft">
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
                            <a class="loginBtn" href="javascript:void(0);" >登录</a>
                        </div>
                        <div class="loginLink">
                            <a href="${accountCenterPath}forget.html"  target="_blank"><em class="e1"></em>忘记密码</a>
                            <a href="javascript:void(0);"  class="quickregister" style="margin-right:0;"><em class="e2"></em>快速注册</a>
                        </div>
                    </div>
                    <!--未登录end-->
                    
                    <!--已登录start-->
                    <div class="logined" style="display:none;">
                        <div class="blank5"></div>
                        <div class="loginedInfo">
                            <span>heart_11</span>, 您好！
                            [<a  class="indexLogout" href="javascript:void(0);" id="login-out">退出</a>]
                        </div>
                        <div class="blank5"></div>
                        <div class="btn3">
                            <a target="_blank" href="http://account.7road.com/">完善密保资料</a>
                            <!-- <div class="safeLevel f_l">
                                <span>安全级别：</span>
                                <i>4</i>
                                <em class="s4"></em>
                            </div> -->
                        </div>
                      
                        <div class="blank10"></div>
                        <div class="recentService">
                            <p>您上次登陆的服务器是：</p>
                            <a href="#" target="_blank">双线136服 魔影骑士</a>
                        </div>
                    </div>
                    <!--已登录end-->
                </section>
        <section class="guide">
            <a class="download" href="http://219.129.216.161/updates/shenqu/7road/7road神曲极速登录器.exe"  target="_blank">登录器下载</a>
            <a class="gift" href="http://sq.7road.com/others/newbGift.html"  target="_blank">礼包领取</a>
            <a class="quickPay" href="http://pay.7road.com/?gameid=1&type=game" target="_blank">快速充值</a>
            <a class="tool" href="http://sq.7road.com/skill.html"  target="_blank">工具大全</a>
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
                    <a href="${loginGamePath}${server.id}" serverId="${server.id}" gameUrl="${loginGameRealPath}${server.id}"   target="_blank">

                        <em>${server.serverName}</em>
                        <em>火爆开启</em>
                    </a>
                    <i>火爆</i>
                </li>
                </#list>
            </ul>
            <a class="moreService" href="http://sq.7road.com/serverList.html" title="查看更多服务器" target="_blank">查看更多服务器</a>
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
    </div>
    <div class="leftSideBottom"></div>
    <div class="newServiceBtn"></div>
</div>