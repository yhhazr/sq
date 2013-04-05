function loginLeftTop(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION1);//记录位置为左上角
		TINY.box.show({iframe:HOST+'/login.html',boxid:'frameless',width:474,height:335,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){checkHeaderAtClosed()}});
	}
	function registLeftTop(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION2);//记录位置为左上角的注册
		TINY.box.show({iframe:HOST+'/regist.html',boxid:'frameless',width:474,height:378,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){checkHeaderAtClosed()}});
	}
	function novice(){ 
		var getEnterInfoFlag=getGameEnterInfo(POSITION3);//记录位置为新手礼包
		//window.open("${serverHost}/NoviceCard.html","_blank");
			var anchor=document.getElementById("jumpSite3");
			try
			{
				anchor.click();
			}
			catch(e)
			{
				window.open(anchor.href,"_blank");
			}
	} 
	function prepaidLeftTop(){ 
		var getEnterInfoFlag=getGameEnterInfo(POSITION4);//记录位置为左上角充值
		//window.open("${payPath}?gameid=1&type=game","_blank");
			var anchor=document.getElementById("jumpSite2");
			try
			{
				anchor.click();
			}
			catch(e)
			{
				window.open(anchor.href,"_blank");
			}
	} 
	function startGame(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION5);//记录位置为开始游戏	
	}
	function regist(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION6);//记录位置为充值旁边的注册
		TINY.box.show({iframe:HOST+'/regist.html',boxid:'frameless',width:474,height:378,fixed:false,maskid:'bluemask',maskopacity:40,closejs:function(){checkHeaderAtClosed()}});
	}
	function prepaid(){ 
		var getEnterInfoFlag=getGameEnterInfo(POSITION7);//记录位置为充值
		//window.open("${payPath}?gameid=1&type=game","_blank");
			var anchor=document.getElementById("jumpSite2");
			try
			{
				anchor.click();
			}
			catch(e)
			{
				window.open(anchor.href,"_blank");
			}
	} 
	function flashCarousel1(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION10);
	}
	function flashCarousel2(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION11);
	}
	function flashCarousel3(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION12);
	}
	function flashCarousel4(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION13);
	}
	function flashCarousel5(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION14);
	}
	function flashCarousel6(){
		var getEnterInfoFlag=getGameEnterInfo(POSITION15);
	}
	function hGameData(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION16);
		window.location.href=url;
	}
	function hGameAgency(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION17);
		window.location.href=url;
	}
	function hGameDownload(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION18);
		window.location.href=url;
	}
	function hGameToolbox(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION19);
		window.location.href=url;
	}
	function rightAD0(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION20);
		window.location.href=url;
	}
	function rightAD1(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION21);
		window.location.href=url;
	}
	function rightAD2(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION22);
		window.location.href=url;
	}
	function hHomepage(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION26);
		window.open(url);
	}
	function hNewPlayerGuide(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION27);
		window.open(url);
	}
	function htGameData(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION28);
		window.open(url);
	}
	function hBbs(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION29);
		window.open(url);
	}
	function hHotNews(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION30);
		window.open(url);
	}
	function hNewsInfo(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION31);
		window.open(url);
	}
	function hServerList(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION32);
		window.open(url);
	}
	function hNewPic(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION34);
		window.open(url);
	}
	function hNewsListArea(url){
		var getEnterInfoFlag=getGameEnterInfo(POSITION35);
		window.open(url);
	}

	function hNavi1Content(url,typeid){
		if(typeid=="1"){
			var getEnterInfoFlag=getGameEnterInfo(POSITION41);
		}else if(typeid=="2"){
			var getEnterInfoFlag=getGameEnterInfo(POSITION42);
		}else if(typeid=="3"){
			var getEnterInfoFlag=getGameEnterInfo(POSITION43);
		}else if(typeid=="4"){
			var getEnterInfoFlag=getGameEnterInfo(POSITION44);
		}else if(typeid=="5"){
			var getEnterInfoFlag=getGameEnterInfo(POSITION65);
		}else{
		}
		window.open(url);
	}
	
	
	
