<header class="header">
<div class="menu">
    <ul>
        <li>
            <a class="home" href="${serverHost}" title="官网首页">官网首页</a>
        </li>
        <li>
            <a class="newPlayerGuide" href="${serverHost}/gamedata/28.html" title="新手指引">新手指引</a>
        </li>
        <li>
            <a class="gameMaterial" href="${serverHost}/gamedata/dataList.html" title="游戏资料">游戏资料</a>
        </li>
        <li class="nothing">
            <h1>神曲</h1>
        </li>
        <li>
            <a class="activity" href="${serverHost}/activity/activity.html" title="活动专题">活动专题</a>
        </li>
        <li>
            <a class="pay" href="http://pay.7road.com/index.html?gameid=1&type=game" title="游戏充值" target="_blank">游戏充值</a>
        </li>
        <li>
            <a class="forum" href="${bbsPath}/forum.php?gid=37" title="游戏论坛" target="_blank">游戏论坛</a>
        </li>
    </ul>
</div>
<embed class='headerLogo' src='${staticHost}/flash/headerLogo.swf' type='application/x-shockwave-flash' width='275' height='165' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>

<embed class='startGame' src='${staticHost}/flash/startGame.swf' type='application/x-shockwave-flash' width='337' height='238' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>

<embed class="womanSwf" src='${staticHost}/flash/woman.swf?v=20130326' type='application/x-shockwave-flash' width='740' height='400' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>

<embed class="gonewbGift" src='${staticHost}/flash/newGift.swf?v=20130326' type='application/x-shockwave-flash' width='200' height='70' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>

<div class="sqIndexFlv">
<embed class="indexPlayFlv1" id="indexPlayFlv" src='${staticHost}/flash/play.swf' type='application/x-shockwave-flash' width='179' height='121' quality='high'  menu='false' pluginspage='http://www.macromedia.com/go/getflashplayer' wmode='transparent' play='true' loop='true' scale='showall' devicefont='false' allowScriptAccess='always'></embed>
</div>

</header>


 <div class="popLock"></div>
    <!--弹出登录start-->
    <div class="popWrap popLogin" >
        <a class="popClose" href="javascript:void(0);" title="关闭">关闭</a>
        <div class="popTopBg1"></div>
        <div class="popMain1">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr>
                        <td class="loginRegTxt">帐&nbsp;&nbsp;号：</td><td><input type="text" maxlength="20" id="toplog_userName" class="popInput1"  /></td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="tab1_1">字母开头，6-20个字母数字</span></td>
                    </tr>
                    <tr>
                        <td class="loginRegTxt">密&nbsp;&nbsp;码：</td><td><input type="password" id="toplog_password" class="popInput1" maxlength="20"  /></td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="tab1_2">长度为6-20位</span></td>
                    </tr>
                    <!--
                    <tr style="display:none" id="log_verify_tr">
                        <td class="loginRegTxt">验证码：</td><td><input type="text" maxlength="4" id="log_verifyCode" class="popInput2"><img width="58px" height="25px" onClick="javascript:this.src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());" class="logpic" src="http://sq.7road.com/game7road/captcha.do" style="vertical-align:middle;cursor:pointer;padding-left:5px" id="log_verifyCodeImg">
                            <a href="javascript:refleshVerifyCode('log_verifyCodeImg')" class="popChangePic">换一张</a>
                        </td>
                    </tr>
                    <tr style="display:none" id="log_verify_tr2">
                    </tr>
                    -->
                    <tr>
                        <td colspan="2">
                            <a id="entergame" class="popLoginBtn" href="javascript:void(0);">进入游戏</a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="popLink">
                                <span class="L1"></span>
                                <a href="http://account.7road.com/forget.html" title="忘记密码" target="_blank">忘记密码</a>
                                <span class="L2"></span>
                                <a href="#" title="快速注册" class="quickregister" target="_blank">快速注册</a>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="error-msg"></div>
        </div>
    </div>
    <!--弹出登录end-->
    
    <!--弹出已登录start-->
    <div class="popWrap popLogined" >
        <a class="popClose" href="javascript:void(0);" title="关闭">关闭</a>
        <div class="popTopBg1"></div>
        <div class="popMain1">
            <div class="popTxt1">
                <span id="toploged_name">heart_11</span>, 您好！
                <a id="slLogout" class="indexLogout" href="javascript:void(0);">[退出]</a>
            </div>
            <a class="popBtn2" title="完善密保资料" target="_blank" href="http://account.7road.com/">完善密保资料</a>
            <div class="popSafe">
                <!--<div class="popSafeLevel">
                    <span>目前安全级别：</span>
                    <i>4</i>
                    <em class="s4"></em>
                </div>-->
                <a id="bindEmail" target="_blank" href="http://account.7road.com/">为保障您的账号安全，请尽快完善密保！</a>
            </div>
            <div class="popRecentService">您上次登陆的服务器是：</div>
            <div class="popService">
                <a id="toplastserverBtn" class="popBtn3" href="#">
                    <strong class="serverName" id="toplastserver"></strong>
                    <span class="hotTxt">今日火爆指数：</span>
                    <span class="hotIco9"></span>
                    <span class="popDegree">90°</span>
                    <span class="red">正常开启</span>
                </a>
            </div>
        </div>
    </div>
    <!--弹出已登录end-->
        
    <!--弹出注册start-->
    <div class="popWrap popReg" >
        <a class="popClose" href="javascript:void(0);" title="关闭">关闭</a>


        <div class="popTopBg2"></div>
        <div class="popMain2">
            <table border="0" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr>
                        <td class="loginRegTxt">帐&nbsp;&nbsp;号：</td><td><input type="text" maxlength="20"  id="topreg_userName" class="popInput1"
                        tips="字母开头，6-20个字母数字" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"  onkeyup="value=value.replace(/\s/g,'')" /><span class="leftmargin"></span></td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="toptab_1">字母开头，6-20个字母数字</span></td>
                    </tr>
                    <tr>
                        <td class="loginRegTxt">密&nbsp;&nbsp;码：</td><td><input type="password"  id="topreg_password1" class="popInput1" tips="长度为6-20位" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" maxlength="20"  onkeyup="value=value.replace(/\s/g,'')"  /><span class="leftmargin"></span></td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="toptab_2">长度为6-20位</span></td>
                    </tr>
                    <tr>
                        <td class="loginRegTxt">确&nbsp;&nbsp;认：</td><td><input type="password"  id="topreg_password2" class="popInput1" tips="长度为6-20位" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" maxlength="20"  onkeyup="value=value.replace(/\s/g,'')"  /><span class="leftmargin"></span></td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="toptab_3">长度为6-20位</span></td>
                    </tr>
                    <tr class="h20">
                        <td class="loginRegTxt">验证码：</td>
                        <td>
                        <input type="text" maxlength="4" id="topreg_verifyCode" class="popInput2" tips="">
                        <img width="58px" height="25px" onClick="javascript:this.src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());" class="logpic" src="http://sq.7road.com/game7road/captcha.do" style="vertical-align:middle;cursor:pointer;padding-left:5px" id="topreg_verifyCodeImg">&nbsp;<a href="#" class="popChangePic">换一张</a>
                        </td>
                    </tr>
                    <tr class="h20">
                        <td></td><td>&nbsp;<span class="msg_default" id="toptab_4"></span></td>
                    </tr>
                    <tr>
                      <td align="center" style="color:#555; padding-top:8px;" colspan="2"><input type="checkbox" checked="true" id="topcheckbox" name="checkbox" class="popCheckbox">
                        <span style="color:#C89B65;font-size:12px;">同意并接受</span>&nbsp;<a target="_blank" href="http://account.7road.com//register_notice.html" class="popXieYi">《用户注册服务协议》</a></td>
                    </tr>
                      
                      <tr>
                        <td colspan="2">
                            <a id="quickregister" target="_blank" class="popRegBtn" href="javascript:void(0);">快速注册</a>
                        </td>
                    </tr>
                    <tr>
                        <td style="color: #ffde00;text-align:center;" colspan="2">&nbsp;<span style="display: none;" class="top_reg_message" id="toptab_4">注册中...</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

