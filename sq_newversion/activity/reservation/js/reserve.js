var action = {
    "serverList":"http://sq.7road.com/sqLucky/getServerList.action",
    "checkLogin":"http://sq.7road.com/game7road/checkLogin.action",//检测是否已经登陆
    "queryUserIsSignUp":"http://sq.7road.com/game7road/reservation/queryUserIsSignUp.action",//检测是否已经预约
    "getToken":"http://sq.7road.com/game7road/reservation/myToken.action",
    "reservation":"http://sq.7road.com/game7road/reservation/addUserToNewServerReservation.action",
    "verifyCode":"http://sq.7road.com/game7road/reservation/validatorUserVerifyCode.action"
}

var isSigned = function (){
    var result = true;
    $.ajax({
        type:"GET",
        url:action.queryUserIsSignUp + "?timestamp=" + (+ new Date()),
        async:false,
        dataType: "json",
        success: function(data){
            if (data.resultMsg == 'unSign') {
                // get token
                $.ajax({
                    type:"GET",
                    url:action.getToken + "?timestamp=" + (+ new Date()),
                    async:false,
                    dataType: "json",
                    success: function(data){
                        // 开始新服预约
                        var token = data.token;
                        if (token !== 'null') {
                            $('#dialog, .jqmOverlay, .reserve').show();
                            getVerifycode(token);
                            validateVerifyCode(token);
                        }
                    },
                    error:function(data){
                        alert("服务器连接超时请稍后再试！");
                    }
                });
            } else if (data.resultMsg == 'unStart'){
                alert('活动尚未开始');
            } else if (data.resultMsg == 'isEnd'){
                alert('活动已经结束');
            } else if (data.resultMsg == 'signed'){
                $('#dialog, .jqmOverlay, .success').show();
                showActiveCode(data.activeCode);
            }
        },
        error:function(data){
            alert("服务器连接超时请稍后再试！");
        }
    });
}

var getVerifycode = function (token){
    // get verify code
    var token = token;
    $('.get-captcha').off('click').on('click', function(){
        var phonenum = $('#phone').val();
        if (checkPhoneNum()){
            $.ajax({
                url:action.reservation + "?timestamp=" + (+ new Date()),
                type:'POST',
                data:{token:token,phoneNum:phonenum},
                async:true,
                dataType:'json',
                timeout:5000,
                success:function(data, textStatus, jqXHR){
                    switch (data.resultMsg) {
                        case 'success' : {
                            var tips = '验证码已发送...';
                            showTips(tips);
                            break;    
                        }
                        case 'phone' : {
                            showErrorMsg('.phone-field','该手机号码已经被绑定了');
                            break;    
                        }
                        case 'tokenError' : {
                            showErrorMsg('.phone-field','验证码已发送，请查收');
                            break;
                        }
                        case 'unLogin' : {
                            showErrorMsg('.phone-field','您还未登录！');
                            break;    
                        }
                        case 'failure' : {
                            showErrorMsg('.phone-field','预约失败，请稍后重新再试');
                            break;    
                        }
                        case 'error' : {
                            showErrorMsg('.phone-field','服务器繁忙，请稍后重新再试');
                            break;
                        }
                        case 'interval' : {
                            showErrorMsg('.phone-field','获取短信过于频繁，请稍后再试');
                            break;
                        }
                        case 'requestTimes' : {
                            showErrorMsg('.phone-field','该手机号码获取验证码次数过多');
                            break;
                        }
                    }
                    
                    // update token value
                    token = data.token;
                },
                error:function(jqXHR, textStatus, errorThrown){
                    alert("链接超时,请稍后再试");
                }
            });
        }
    });
}

var validateVerifyCode = function (token){
    // validate verify code
    var token = token;
    $('#reserves').off('submit').submit(function(){
        var phonenum = $('#phone').val();
        var code = $('#captcha').val();
        if (checkReserves()){
            $.ajax({
                url:action.verifyCode + "?timestamp=" + (+ new Date()),
                type:'POST',
                data:{token:token,phoneNum:phonenum,verifyCode:code},
                async:true,
                dataType:'json',
                timeout:5000,
                success:function(data, textStatus, jqXHR){
                    if (data.resultMsg == 'success' || data.resultMsg == 'signed') {
                        $('#dialog, .jqmOverlay, .reserve').hide();
                        $('#dialog, .jqmOverlay, .success').show();
                        showActiveCode(data.activeCode);
                    } else if (data.resultMsg == 'verifyCode'){
                        showErrorMsg('.captcha-field', '验证码错误');
                    } else if (data.resultMsg == 'expired'){
                        showErrorMsg('.captcha-field','验证码过期，请重新获取短信验证码');
                    } else if (data.resultMsg == 'failure'){
                        showErrorMsg('.captcha-field','预约失败，请稍后重新再试');
                    } else if (data.resultMsg == 'notExists'){
                        showErrorMsg('.captcha-field','验证码错误，请稍后重新再试');
                    } else if (data.resultMsg == 'error'){
                        showErrorMsg('.captcha-field','服务器出错了，请稍后重新再试');
                    }
                },
                error:function(jqXHR, textStatus, errorThrown){
                    alert("链接超时,请稍后再试");
                }
            });
        }
        return false;
    });
}

var showActiveCode = function (code){
    if (code.length == 1){
        $('.single .code').text(code[0]);
        $('.single').show();
    } else {
        $('.double .first').text(code[0]);
        $('.double .second').text(code[1]);
        $('.double').show();
    }

}

var showTips = function (tips) {
    $('.get-captcha').css('display', 'none');
    $('.phone-field').append('<span style="color:#999;font-size:12px;">' + tips + '</span>');
    setTimeout(function (){
        $('.phone-field span').remove();
        $('.get-captcha').css('display', 'inline');
    }, 1000 * 60);
}

var showErrorMsg = function (field,msg){
    $(field).find('.error').text(msg);
    $(field).find('.error').show();
    setTimeout(function (){
        $(field).find('.error').text('');
        $(field).find('.error').hide();
    }, 1000 * 6);
}

// 客户端表单验证
var checkReserves = function (){
    var result = checkPhoneNum() && checkCaptcha();
    return result;
}

var checkPhoneNum = function (){
    var result = true;
    var phonenum = $('#phone')[0];
    var reg = /^((13[0-9])|145|147|(15[0-3,5-9])|(18[0-9]))\d{8}$/;
    if (phonenum.value == '' || phonenum.value == null){
        $(".phone-field .error").css("display","block");
        $(".phone-field .error").text("手机号码不能为空");
        result = false;
    } else if (!reg.test(phonenum.value)) {
        $(".phone-field .error").css("display","block");
        $(".phone-field .error").text("手机号码格式不正确");
        result = false;
    } else {
        $(".phone-field .error").css("display","none");
    }
    return result;
}

var checkCaptcha = function (){
    var result = true;
    var captcha = $('#captcha')[0];
    if (captcha.value == '' || captcha.value == null){
        $(".captcha-field .error").css("display","block");
        $(".captcha-field .error").text("验证码不能为空");
        result = false;
    } else if (captcha.value.length != 6) {
        $(".captcha-field .error").css("display","block");
        $(".captcha-field .error").text("验证码位数不够");
        result = false;
    } else {
        $(".captcha-field .error").css("display","none");
    }
    return result;
}


$(function (){
    $('.logo').on('mouseover', 'img', function(e){
        if( $.browser.msie && $.browser.version < 9){
            return !1;
        }
        var logo = $(this);
        logo.animate({opacity:.7}, 300, function (){
            logo.animate({opacity:1}, 300);
        });
    });
   
    $('#reserve').on('click', function (){
        $.get(action.checkLogin + "?timestamp=" + (+ new Date()),function(data){
            if(data.result==="true") {
                isSigned();
            }else {
                var url = window.location.href;
                window.location = 'http://account.7road.com/login.html?fromUrl=' + url;
            }
        });
        
    });

    // copy the code
    $(".copy").on("click",function(){
        try {
            window.clipboardData.setData("Text",$(this).siblings(".code").text());
            alert("复制成功!");
        }
        catch(e) {
            alert("当前浏览器不支持此功能，请更换IE浏览器或手动复制！");
        }
    });

    // close the dialog
    $('.jqmOverlay').on('click', function (){
        $('#dialog, .jqmOverlay').hide();
    });

    // bind the phone 
    $('#reserves #phone').on('keyup', function (){
        checkPhoneNum();
    });

    $('#reserves #captcha').on('keyup', function (){
        checkCaptcha();
    });

});