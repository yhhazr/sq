//游戏相关
var newestRecomendServerName = null;

//进入游戏
function enterGame(gameUrl){
	var flag = 1;
	var code = 1;
	var message = "";
	if(gameUrl && gameUrl != '' && gameUrl != '#'){
		$.ajax({
			   type: "get",
			   dataType: "json",
			   url: SERVER + "/onlineGame/checkLoginGame.action?timestamp=a" + new Date().getTime(),
			   async: false,
			   timeout: 5000,
			   data:{gameUrl:gameUrl},
			   success:function(respMsg){
				   code = respMsg.code;
				   message = respMsg.message;
			   }
		});
		if(code != 0){
			flag = 1;
			if(message != null && message != ""){
				alert(message);
			}else{
				alert("服务器连接超时，请稍后再试。");
			}
		}else{
			flag = 0;
		}
	}
		
	return flag;
}

//得到最新服务器id函数(同步)
function getNewestGameServerId() {
	var gameId = $.cookie('ngid');
	if(gameId==null || gameId=="" || gameId=="-1" || gameId=="0") {
		$.ajax({
			   type: "get",
			   dataType: "json",
			   url: SERVER + "/onlineGame/acquireId.action?timestamp=a" + new Date().getTime(),
			   async: false,
			   timeout: 5000,
			   success:function(msg){
				   gameId = msg.id;
				   newestRecomendServerName = msg.serverName;
				   $.cookie('ngid', gameId, {expires:0, path: '/'});
			   }
		});
	}
	return gameId;
}
//得到最新服务器url函数（同步）
function getNewestGameServerUrlBySync() {
	var gameId = $.cookie('ngid');
	if(gameId==null || gameId=="" || gameId=="-1" || gameId=="0") {
		gameId = getNewestGameServerId();
	}
	var gameZoneUrl = LOGINGAME + gameId + '&timestamp=' + Date.parse(new Date());
	return gameZoneUrl;
}


//得到最新服务器url函数
function getNewestGameServerUrl(functions) {
	var gameId = $.cookie('ngid');
	newestRecomendServerName = $.cookie('ngnm');
	var gameZoneUrl = '#';
	if(!gameId || gameId==="" || gameId==="-1" || gameId==="0") {
		$.ajax({
			   type: "get",
			   dataType: "json",
			   url: SERVER + "/onlineGame/acquireId.action?timestamp=a" + new Date().getTime(),
			   async: true,
			   timeout: 5000,
			   success:function(msg){
				   gameId = msg.id;
				   newestRecomendServerName = msg.serverName;
				   $.cookie('ngid', gameId, {expires:0, path: '/'});
				   $.cookie('ngnm', newestRecomendServerName, {expires:0, path: '/'});
					if(gameId != -1 && gameId != 0) {
						gameZoneUrl = LOGINGAME + gameId + '&timestamp=' + Date.parse(new Date());
					}
				   for(var func in functions) {
					   functions[func](gameZoneUrl);
				   }
			   }
		});
	}else{
		gameZoneUrl = LOGINGAME + gameId + '&timestamp=' + Date.parse(new Date());
		for(var func in functions) {
			functions[func](gameZoneUrl);
		}
	}
}

function formatDate(timestamp){
	var time = new Date(timestamp);
    var month = time.getMonth()+1;   
    var date = time.getDate();   
    var hour = time.getHours();   
    
    return month+"月"+date+"日 "+hour+"时";   
}   

 