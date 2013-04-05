// get cookie
function getCookie(cookie_name){
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(cookie_name + "=");
        if (c_start!=-1){ 
            c_start=c_start + cookie_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1) {c_end=document.cookie.length};
            return unescape(document.cookie.substring(c_start,c_end));
        }
    }
    return "";
}

// set cookie
function setCookie(cookie_name,value,expiredays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate()+expiredays);
    document.cookie = cookie_name + "=" + escape(value) + ((expiredays==null) ? "" : "; expires="+exdate.toGMTString());
}

// check cookie
function checkCookie(){
    var username = getCookie('username');
    if (username != null && username != "") {
        $('#username').val(username);
    }
}

// user sign in
$('#signin #username').on('focus', function (){
    $("#tips").css("display","none");
    $('.username-field i').removeClass('error');
});
$('#signin #password').on('focus', function (){
    $("#tips").css("display","none");
    $('.password-field i').removeClass('error');
});

$('#signin').submit(function (){
    var userVal = $("#username").val(),passVal = $("#password").val();
    if (!userVal || !passVal){
        if (!userVal){
            $('.username-field i').addClass('error');
        }
        if (!passVal) {
            $('.password-field i').addClass('error');    
        }
        $("#tips").css("display","block");
        $("#tips").text("帐号或者密码不能为空！");
        return false;
    } else {
        login(userVal,passVal,loginSuccessBack);
        return false;
    }
})

var loginSuccessBack = function(data, textStatus, jqXHR){//login ok
    if(data.result=="true"){
        loginSuccess(data);
    }else{
        $("#tips").css("display","block");
        $("#tips").text(data.respMsg);
    }
}

var loginSuccess = function (data){
    var username = $('#username').val();
    if (!$('.remember-field label.checkbox').hasClass('unchecked')) {
        setCookie('username', username, 7);
    }
    window.location = "logexe_server.html";
}

// sign up validation
$('#signup #username').on('keyup', function (){
    checkUsername();
});

$('#signup #password').on('keyup', function (){
    checkPassword();
});

$('#signup #repeat').on('keyup', function (){
    checkRePassword();
});

$('#signup #captcha').on('keyup', function (){
    checkCaptcha();
});

$('#signup').submit(function (){
    var userVal = $("#username").val(), passVal = $("#password").val(), repeatVal = $('#repeat').val();
    if (checkRegist()) {
        register(userVal, passVal, repeatVal, registSuccessBack);
    }
    return false;
});

var registSuccessBack = function(data, textStatus, jqXHR){//regist ok
    if(data.result=="true"){
        registSuccess(data);
    }else{
        $("#tips").css("display","block");
        //$("#tips").text(data.respMsg);
    }
}

var registSuccess = function (data){
    var username = $('#username').val();
    setCookie('username', username, 7);
    window.location = "logexe_server.html";
}

var checkRegist = function () {
    var result = checkUsername() && checkPassword() && checkRePassword() && checkCaptcha();
    return result;
}

var checkUserExistedBack = function(data, textStatus, jqXHR){
    if(data.result=="false"){
        $(".username-field .tips").text(data.respMsg);
        return result = false;
    } else {
        //$(".username-field label:last").removeClass("error");
        $(".username-field .tips").text("");
    }
}

function checkUsername(){
    var result = true;
    var nickname = $("#username")[0];
    var reg = /^[a-zA-Z0-9_]{1,}$/;
    var regRepeat = /([A-Za-z0-9_])\1{4,}/;
    if (nickname.value == "" || nickname.value == null){
        //$(".username-field label:last").addClass("error");
        $(".username-field .tips").css("display","block");
        $(".username-field .tips").text("请输入用户名");
        result = false;
    } else if (!reg.test(nickname.value)){
        //$(".username-field label:last").addClass("error");
        $(".username-field .tips").css("display","block");
        $(".username-field .tips").text("账号应由字母、数字、下划线组成");
        result = false;
    } else if (nickname.value.length > 20 || nickname.value.length < 6){
        //$(".username-field label:last").addClass("error");
        $(".username-field .tips").css("display","block");
        $(".username-field .tips").text("账号长度应在6~20之间");
        result = false;
    } else if (result){
        checkUserNameExisted(nickname.value, checkUserExistedBack);
    } else {
        $(".username-field .tips").css("display","none");
    } 
    return result; 
}

function checkPassword(){
    var result = true;
    var password = $("#password")[0];
    if (password.value == "" || password.value == null){
        $(".password-field .tips").css("display","block");
        $(".password-field .tips").text("请输入密码");
        result = false;
    }else if (password.value.length > 20 || password.value.length < 6){
        $(".password-field .tips").css("display","block");
        $(".password-field .tips").text("密码长度应在6~20之间");
        result = false;
    }else if (password.value.indexOf(" ") !== -1){
        $(".password-field .tips").css("display","block");
        $(".password-field .tips").text("密码不能包含空格");
        result = false;
    }else {
        $(".password-field .tips").css("display","none");
    }
    return result;
}
    
function checkRePassword(){
    var result = true;
    var password = $("#password")[0];
    var repeat = $("#repeat")[0];
    if(repeat.value == "" || repeat.value == null){
        $(".repeat-field .tips").css("display","block");
        $(".repeat-field .tips").text("请确认密码");
        result = false;
    }else if(repeat.value != password.value){
        $(".repeat-field .tips").css("display","block");
        $(".repeat-field .tips").text("两次输入的密码不一致");
        result = false;
    }else{
        $(".repeat-field .tips").css("display","none");
    }
    return result;
}

function checkCaptcha(){
    var result = true;
    var captcha = $('#captcha')[0];
    var src = 'http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
    if (captcha.value == "" || captcha.value == null) {
        $(".captcha-field .tips").css("display","block");
        $(".captcha-field .tips").text("请输入验证码");
        result = false;
    } else {
        if (captcha.value.length != 4) {
            $(".captcha-field .tips").css("display","block");
            $(".captcha-field .tips").text("验证码位数不够");
            result = false;
        } else {
            $.ajax({
                type: "get",
                url: "http://sq.7road.com/game7road/checkVerifyCode.action?verifyCode="+ captcha.value,
                async: false,
                dataType: "json",
                success: function(data){
                    if(data.result == "false"){
                        $(".captcha-field .tips").css("display","block");
                        $(".captcha-field .tips").text("验证码错误");
                        $('#verifyCodeImg').attr('src', src);
                        result = false;
                    } else {
                        $(".captcha-field .tips").css("display","none");
                    }
                }
            })
        }
    }
    return result; 
}

// server page
var initPageDate = function (data){
    if (data.outName && data.outName != 'null') {
        $('.welcome h1 em').text(data.outName + ' ');
    } else {
        $('.welcome h1').text('请先登录帐号再试!');
    }
    if (data.lastGameZoneName && data.lastGameZoneName != 'null'){
        $(".last a").attr('serverid',data.lastGameZoneId)
        $(".last a").text(data.lastGameZoneName);
    } else {
        $(".last").text('无最近登录信息');
    }
}

var checkUserIsLogined = function () {
    var checked;
    checkIsLogined(checkIsLoginedBack);
    return checked;
}

var checkIsLoginedBack = function(data, textStatus, jqXHR){
    if(data.result=="true"){
        initPageDate(data);
        return checked = true;
    } else if (data.result=="false") {
        initPageDate(data);
        return checked = false;
    }
}

var checkLoginGameOkBack = function(data, textStatus, jqXHR){
    if(data.code== 0){//可以进入
        enterGame(data.serverId);
    } else {
        var e = event ? event : window.event;
        if(e.preventDefault){
            e.preventDefault(); 
        }else{
            e.returnValue = false;
        }
        alert(data.message);
        return false;
    }
}

// login game
$('.last a, .gamestart').on('click', function (e){
    e.preventDefault();
    var serverId = $('.last a').attr("serverid");
    if(checkIsLogined && serverId){// user logined
        checkLoginGame(2,serverId,checkLoginGameOkBack);
    } else {
        return false;
    }
});

$(".server-area li a").on("click",function(e){
    e.preventDefault();
    var serverId = $(this).attr("serverid");
    if(checkIsLogined){// user logined
        checkLoginGame(2,serverId,checkLoginGameOkBack);
    } else {
        return false;
    }
});

$('#quick-start').submit(function (){
    var serverId = + $('.quick-start input').val();
    var serverIdList = $('#serverid-list').text().replace(/^\s+|\s+$/g, '').split(' ').reverse();
    var len = serverIdList.length;
    if(checkIsLogined){// user logined
        if (0 < serverId && serverId <= len) {
            checkLoginGame(2, + (serverIdList[serverId - 1]),checkLoginGameOkBack);
            return false;
        } else {
            alert('请输入正确的区服');
            return false;
        }
    } else {
        return false;
    }
});

var enterGame = function(id){
    if(id != null) {
        $.ajax({
            type:"POST",
            dataType:"jsonp",
            url:"http://account.7road.com/PlayGame2?game=S&subGame=0&g=1&_rs=registers",
            jsonp:"jsoncallback",
            data:{z:id},
            //beforeSend:function (XMLHttpRequest, textStatus){},
            success:function(data) {
                if(data.code == 0){
                    callback(data.object);
                }else{
                    alert(data.msg);
                }
            }
        });
    }
}

var callback = function (url){
    if (url !== null){
        url=url.replace("game.jsp","Loading.swf");
        //url=url+"&rand="+randomNum();
        LoadGame(url);
    }
}

// user login out
var loginOutBack = function(data, textStatus, jqXHR){//login out
    if(data.result=="true"){
        loginOutSuccess(data);
    }else{
        alert(data.respMsg);
    }
}

var loginOutSuccess = function (data){
    alert('注销成功！');
    window.location = 'logexe_signin.html';
}

$(".logout").on("click",function(e){
    e.preventDefault();
    logout(loginOutBack);
});