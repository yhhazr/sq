/*导航全部游戏*/
$('#navCon .nList').each(function(i){
	$(this).bind('mouseenter',function(e){
		$(this).addClass('currentHover');
		//$(this).find('.nLink').addClass('nLink2');
		//$(this).find('.arrow').addClass('arrow2');
		$(this).find('.navSelectCon').show();
	})
 
	$(this).bind('mouseleave',function(){
		$(this).removeClass('currentHover');
		//$(this).find('.nLink').removeClass('nLink2');
		//$(this).find('.arrow').removeClass('arrow2');
		$(this).find('.navSelectCon').hide();
	})
});

//顶部导航图片
function menuFix(nav) {
var sfEls = document.getElementById(nav).getElementsByTagName("span");
for (var i=0; i<sfEls.length; i++) {
sfEls[i].onmouseover=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onMouseDown=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onMouseUp=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onmouseout=function() {
this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
}
}
}

function menu(){

    var nowli = document.getElementById("sqPop").getElementsByTagName("span");

}
menu();
menuFix('sqPop')

//顶部导航条弹出登录注册框


$(document).ready(function(){
	$(".topLogin").click(function(){
		$(".popLock,.popLogin").show();	
	});
	
	$(".topReg").click(function(){
		$(".popLock,.popReg").show();
		$("#tab2_4").hide();	
	});
	$(".quickregister").click(function(){
		$(".popLock,.popReg").show();
		$("#tab2_4").hide();
		return false;	
	});

	$(".popClose").click(function(){
		$(".popLock,.popLogin,.popLogined,.popReg").hide();						  
	});				   
})

//点击换一张
$(".popChangePic").click(function(){
	$(this).prev("img").attr("src","http://sq.7road.com/game7road/captcha.do?timestamp"+(+new Date()));
})