function succeedLoginNewbieCard()
{
	$("#logInfo").html("请点击选区获取新手卡");
						 //选区监听
						 $("#server").change(function(){
						 var num=$('#server option:selected').val();
							$.ajax({
							  type: "GET",
							  url: SERVER+"/noviceCard.action?read="+num,
							  dataType: "json",
							  success: function(json){
									$("#cardNumShow").text(json.retu);
								}
							}); 
						
					 });
}

function checkLoginForNewbieCard()
{
	var actionUrl=SERVER+"/checkLogin.action";
	var loged="false";
	$.get(actionUrl, 
			function(data){				
				if(data.result == 'true') {	
					loged=data.username;
					//判断登录
					if(loged=="false"){
						$("#logInfo").html("<a href='javascript:login()'>请先登录</a>");
					}else{
						succeedLoginNewbieCard(); 
					}
				} 
			});	
}