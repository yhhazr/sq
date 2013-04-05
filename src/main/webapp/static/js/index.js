/*
 * 2010-10-12 line:108 注释掉蓝贴第二条新闻new标志
 */

var indexCheckLogin = function(logedFlag){
	$('.start_game').attr('href', HOST+'/serverList.html');
	if(logedFlag == 0){
		$('.head_login_content').hide();
		$('.head_login_suc').html('<div class="idx_loged"><a href=' + PLATE_FORM_HOST + ' class="idx_name" target="_blank">欢迎您,   ' + logedName
				+ '</a><a href="#" class="indexLogout">退出</a></div>').css({"position":"relative","right":"80px", "z-index":"100"});
		$('.head_login_suc').show();
		$('#share_username').empty().text(logedName);
		getNewestGameServerUrl({
			func: function(indNewGameZoneUrl) {
				$('.start_game').attr('href', indNewGameZoneUrl);
			}
		});
	}
}

var rightQuickRegist = function(logedFlag){
	if(logedFlag == 0){
		$('#hidden').html(bbsLoginMsg);
		$('#qrUsername').empty().html(logedName);
		$('#qrLoginTime').empty().html(getDate());
		$('#qrLastserver').empty().html('进入推荐服务器');
		getNewestGameServerUrl({
			func: function(gameUrl) {
				$('#qrNewestserver').attr('href', gameUrl);
			}
		});
		if(lastServerName != '进入推荐服务器'){
			$('#qrLastserver').html(lastServerName).css('font-size', '13px');
		}
		$('#qrLastserver').attr('href', lastServerUrl);
		$('#rqContainter').hide();
		$('#rqIsRequest').show();
	}
}

$(function(){
	showStartPage('site');
	
	$(".serviceName a").each(function(){
		var url = $(this).attr('href');
		var _t = Date.parse(new Date());
		$(this).attr('href', url + '&timestamp=' + _t);
	});
	
	if($('#select1 option').length>0){
		var initUrl=$('#select1 option:first').val();
		chagePlayerInfo(initUrl);
	}
	//验证登陆
	var logedFlag = checkLoged({
		func1: indexCheckLogin,
		func2: rightQuickRegist
	});
//	indCheckLogin(logedFlag);
//	rightQuickRegist(logedFlag);
	
	indexBindLogoutButton();
	
	$("#select2").hover(function(){
		//var height=$("#selectList2").css("height");
		//var top=height;
		//$("#selectList2").css("top",top);
		$("#selectList2").css("display","block"); 
	},function(){
		$("#selectList2").fadeOut("slow");
	});
	
	$("select#select1").change(function(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION45);
		var url= $("#select1").val();
		chagePlayerInfo(url);
		
	});
	
	//header滚动新闻
	$("#slideMessage ul").hover(function(){
		clearInterval(automov);
		},function(){
			automov = setInterval(function(){
		        $("#slideMessage li:first").animate({marginTop:"-20px", speed:'slow'},"2000",function(){
				$(this).css("margin-top","0").appendTo($(this).parent());
			});
		},2000);
	}).trigger("mouseleave");

	
	
	
	//资料站立刻进入游戏
	$('.start_game').click(function(){
		var cookie = $.cookie('VVNFUklORk8%3D');
		if(cookie != null && cookie != "") {
			var indCanLogGame = enterGame($(this).attr('href'));
			if(indCanLogGame == 0) {
				return true;
			}else {
				return false;
			}
		}else {
			$('.start_game').attr('href', HOST+'/serverList.html');
			return true;
		}
	});
	
	$(".lantie li:last").css("border-bottom","none");
	$(".hotNewsList .lantie li").eq(0).append("<img src='http://image.7road.com/serverList/newHot.gif' />");
//	$(".hotNewsList .lantie li").eq(1).append("<img src='http://image.7road.com/serverList/newHot.gif' />");

	
});

//退出按钮点击执行函数
var indexCheckLogoutButton = function() {
	$('.indexLogout').die('click', indexCheckLogoutButton).live('click', indexCheckLogoutButtonInvalid);
	var flag = logout({func1: indexCheckReturnOfLogout, func2: indexBindLogoutButton}, {func: indexBindLogoutButton});
	
	return false;
}

//退出按钮点击无效函数
var indexCheckLogoutButtonInvalid = function() {
	
	return false;
}

function chagePlayerInfo(url){
	$("#player").fadeOut("slow");
	$.ajax({ 
	    url:url, 
	    type: 'GET', 
	    dataType: 'text', 
	    timeout: 1000, 
	    error: function(html){ 
//	        alert('Error loading html document'+html); 
	    }, 
	    success: function(html){ 
	    	$("#player").html(html);
	    	$("#player").fadeIn("slow"); 
	    } 
	});
}
//退出登陆返回值判断函数
var indexCheckReturnOfLogout = function(flag) {
	if(flag === 0) {
		$('#hidden').html(bbsLoginMsg);
		$('.head_login_suc').hide();
		$('.head_login_content').show();
		$('.start_game').attr('href', HOST+'/serverList.html');
		$('#rqContainter').show();
		$('#rqIsRequest').hide();		
	}
}

//绑定退出登录执行函数
var indexBindLogoutButton = function() {
	$('.indexLogout').die('click', indexCheckLogoutButtonInvalid).live('click', indexCheckLogoutButton);
}




//弹出层关闭时判断登录改变首页header状态
var checkHeaderAtClosed = function(){	
	var logedFlag = checkLoged({
		func1: indexCheckLogin,
		func2: rightQuickRegist
	});
}; 
//弹出层进入游戏
var enterGameonBox = function(url, data){	
	$('#hidden').html(data);
	TINY.box.hide();
	openwin(url);
};
//同步论坛登录
var loginBBS = function(data){
	$('#hidden').html(data);	
}; 
//弹出层登录成功后立即改变首页header状态
var changeHeaderAtOnce = function(data){
	$('.head_login_content').hide();
	$('.head_login_suc').html("<div class='idx_loged'><a href='|" +PLATE_FORM_HOST+"' class='idx_name' target='_blank'>欢迎您,   "
			+ data
	+ "</a><a href='#' class='indexLogout'>退出</a></div>").css({"position":"relative","right":"80px", "z-index":"100"});
	$('.head_login_suc').show();
	//右侧浮动框
	$('#qrUsername').empty().html(data);
	$('#qrLoginTime').empty().html(getDate());
	$('#qrLastserver').empty().html('进入推荐服务器');
	checkLoged();
	getNewestGameServerUrl({
		func: function(gameUrl) {
			$('#qrNewestserver').attr('href', gameUrl);
		}
	});
	if(lastServerName != '进入推荐服务器'){
		$('#qrLastserver').html(lastServerName).css('font-size', '13px');
	}
	$('#qrLastserver').attr('href', lastServerUrl);
	$('#rqContainter').hide();
	$('#rqIsRequest').show();
	
	succeedLoginNewbieCard();
}
//退出登录时改变首页header状态
var changeHeaderWhenLogout = function(){
	$('.head_login_suc').hide();
	$('.head_login_content').show();
}
//打开url链接
var openwin = function openwin(url) {
//	window.open(url).location=url;
	var a = document.createElement("a");
	a.setAttribute("href", url);
	a.setAttribute("target", "_blank");
	a.setAttribute("id", "submit");
	document.body.appendChild(a);
	if(window.MessageEvent){
		var evt = document.createEvent("MouseEvents");  
	    evt.initEvent("click", true, true);  
	    a.dispatchEvent(evt);
	} else {
		a.click();
	}
	
//	setTimeout(function(){
//		window.location.href=url;
//	},1500);
};







function mouseOver_2(id){
	var obj = $(".otherBox_contant");
	var str2 = id + "_tag";
	var obj2 = document.getElementById(str2);
	for(var i = 0;i < obj.length;i++){
		obj[i].style.display="none";
		obj2.style.display="block";
	}
}

function mouseOver(id,str1){
	/*
	if(id == "bulletin" || id == "activity" || id == "media"){
		return ;
	}*/
	//var changes = document.getElementsByName("news_change");
	//var obj = document.getElementsByName(str1);
	//var obj2 = document.getElementById(id);
	var str2 = id + "_tag";
	//var obj3 = document.getElementById(str2);
	$(".news_menu ul li a").removeClass("nav_selected");
	$("#"+str2).addClass("nav_selected");
	//for(var i = 0;i < obj.length;i++){
		//obj[i].style.display="none";
		//changes[i].style.color = "";
		//document.getElementById(str2).style.color = "red";
		//obj2.style.display="block";
	//}
	$("div[name="+str1+"]").hide();
	$("#"+id).show();
}

function mouseOver_1(id,str1){		
	var str2 = id + "_tag";
	$(".dl_menu ul li a").removeClass("nav_select");
	$("#"+str2).addClass("nav_select");
	$("ul.videoList").hide();
	$("#"+id).show();
}

function redirect(str){
	/*
	if(str == "bulletin" || str == "activity" || str == "media"){
		return ;
	}*/
	
	if(str == 'news'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION36);
		window.open("news/list/index_url_" + str + "_1.html?type=" + str + "#A0");
	}else if(str == 'bulletin'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION37);
		window.open("news/list/index_url_" + str + "_1.html?type=" + str + "#A0");
	}else if(str == 'activity'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION38);
		window.open("news/list/index_url_" + str + "_1.html?type=" + str + "#A0");
	}else if(str == 'media'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION39);
		window.open("news/list/index_url_" + str + "_1.html?type=" + str + "#A0");
	}else if(str == 'lantie'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION64);//此处需在config.js中添加POSITION64等于什么。
		window.open("news/list/index_url_" + str + "_1.html?type=" + str + "#A0");
	}else{
		var getEnterInfoFlag=getGameEnterInfo(POSITION40);
		window.open("news/list/list_index.html#A0");
	}
}

function showFunction(str){
	alert("非常抱歉，"+ str +"还未开放!");
}

	
	
//收藏夹
function bookmarkit(){
	var getEnterInfoFlag=getGameEnterInfo(POSITION25);
   if(window.sidebar && "object" == typeof( window.sidebar ) && "function" == typeof(window.sidebar.addPanel)){
       window.sidebar.addPanel( '《神曲》官方网站 -- 第七大道', HOST, '' );
    }
    else if (document.all && "object" == typeof(window.external)){
       window.external.addFavorite(HOST,'《神曲》官方网站 -- 第七大道');
    } else {
    	alert("您在使用webKit内核的浏览器，请使用 ctrl+D 添加收藏哦！");
    }
   return false;
}


//打开登录弹出层
function openLoginPage() {
	TINY.box.show({
		iframe:HOST+'/login.html',
		boxid:'frameless',
		width:474,
		height:335,
		fixed:false,
		maskid:'bluemask',
		maskopacity:40,
		closejs:function(){checkHeaderAtClosed()}
	});
}
//打开注册弹出层
function openRegistPage() {
	TINY.box.show({
		iframe:HOST+'/regist.html',
		boxid:'frameless',
		width:474,
		height:378,
		fixed:false,
		maskid:'bluemask',
		maskopacity:40,
		closejs:function(){checkHeaderAtClosed()}
	});
}


$(".serviceName a").click(function(){
	var getEnterInfoFlag=getGameEnterInfo(POSITION33);
	var cookie = $.cookie('VVNFUklORk8%3D');
	if(cookie != null && cookie != "") {
		var indCanLogGame = enterGame($(this).attr('href'));
		if(indCanLogGame == 0) {
			return true;
		}else {
			return false;
		}
	}else{
		openLoginPage();
		return false;
	}
});

//获得当前时间
function getDate(){
	var date = new Date(); 
	var now = "";
	now = date.getFullYear()+"-";
	now = now + (date.getMonth()+1)+"-";
	now = now + date.getDate()+" ";
	now = now + date.getHours()+":";
	now = now + date.getMinutes()+":";
	now = now + date.getSeconds()+"";
	return now;
}
