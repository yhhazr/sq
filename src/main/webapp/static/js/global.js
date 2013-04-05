

//获得参数site的值
function getSite() {
	var value = "";
	var query=location.search.substring(1);//获取查询串     
	var pairs=query.split("&");//获得参数数组     
	for(var i=0;i<pairs.length;i++){     
		var pos=pairs[i].indexOf('=');//查找name=value  
		if(pos==-1) continue;//如果没有找到就跳过     
        var argname=pairs[i].substring(0,pos);//提取name   
		if(argname=="site") {
			value=pairs[i].substring(pos+1);//提取value
			$.cookie('site', value, {expires:0, path: '/',domain: COOKIE_NAME});
			break;
		}
	}
	if(value=="") {
		value = $.cookie('site');
		if(value == null) {
			value = "";
		}
	}
	return value;     
}

function getRequestParm(paraName){ 
	var reg= new RegExp("(^|&)"+paraName+"=([^&]*)(&|$)");   
	var r=window.location.search.substr(1).match(reg);   
	if(r!=null) return unescape(r[2]);return null;   
} 

//记录点击位置和次数
function getGameEnterInfo(positionKey){
	var result=false;
	$.ajax({
	  url: SERVER+'/getGameEnterInfo.action?positionKey='+positionKey,
	  type: 'GET',
	  dataType: 'json',
	  success: function(json) {
	    //called when successful
	    	if(json)
	    		result=true;
	  }
	});
	return result;
}

//广告页面跳转
function showStartPage(paramName){
	var flag=getRequestParm(paramName);
	if(flag=='07073_1'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION62);
	}else if(flag=='07073_2'){
		var getEnterInfoFlag=getGameEnterInfo(POSITION63);
	}else if(flag){
		var getEnterInfoFlag=getGameEnterInfo(POSITION23);
		//openRegistPage();
	}

}

$(function(){
	getSite();	
	showStartPage('site');

});
