/*导航全部游戏*/


//顶部导航条弹出登录注册框


$(document).ready(function(){
	
	
	$(".quickregister").click(function(){
		$(".popChangePic").click();//获取最新验证码
		$(".popMain2 input").attr("value","");
		$(".popMain2 .leftmargin").removeClass("noTip").removeClass("yesTip")
		$(".popMain2 .msg_default,.popMain2 .msg_red").empty();
		$(".popLock,.popReg").show();
		$("#tab2_4").hide();	
	});
	
	$(".popClose").click(function(){
		$(".popLock,.popLogin,.popLogined,.popReg").hide();						  
	});				   
})

//点击换一张
$(".popChangePic").click(function(){
	$(this).prev("img").attr("src","http://sq.7road.com/game7road/captcha.do?timestamp"+(+new Date()));
})