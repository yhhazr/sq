<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title></title>

<link href="${staticHost}/css/sq_login.css?v=${version}" rel="stylesheet" type="text/css" />
 <script src="${staticHost}/js/config.js?v=${version}" charset="UTF-8"></script>
    <script type="text/javascript" src="${staticHost}/js/jquery-1.7.1.js"  charset="UTF-8"></script>
    <script type="text/javascript" src="${staticHost}/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${staticHost}/js/road.js?v=${version}"></script>
	<script type="text/javascript" src="${staticHost}/js/game.js?v=${version}" charset="UTF-8"></script>
</head>



<body>

    <div class="xf">
      <div class="use">
      	<span id="username" style="color:#FF0;"></span><span>欢迎您登陆，请选择服务器！</span><a class="logout" href="javascript:logout()">注销</a>
      </div>    
      <div class="zjdl">
        <div class="list">            
				<span id="lastServerName" href="javascript:lastServerLogin()" class="zc">无最近登录记录</span>			
        </div>
		<a id="lastServerLogin" href="#"><img src="${imageHost[0]}/loginexe/btn_login.jpg" width="173" height="85" border="0" class="start" /></a>
	</div>

	  	<div class="clearfix" style="_margin-top:-10px;width:620px;margin:0 auto;">
	  	  <#assign n=100>
	      <div class="xfdh navTab">
	      	  <#if ((serverList?size%n)?int != 0)>
					<#assign i=(serverList?size/n)?int + 1>
			  <#else>
					<#assign i=(serverList?size/n)?int>
			  </#if>
			  <#list 0..i-1 as t>	
			  		<p <#if t_index == 0>class="on"</#if>>
			  			<#if t_index == 0>
						    <a  title="推荐服务器" href="javascript:void(0)">推荐服务器</a>
			  			<#else>
						    <a  title="${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区" href="javascript:void(0)">${(i-t_index - 1) * n + 1}区-${(i-t_index) * n}区</a>
			  			</#if>
				    </p>
			  </#list>
	    </div>

	    <div class="qb_list switchMain" style="display:block;overflow-x: hidden;overflow-y:auto; ">
	    	<div class="quickgame">
	    		<span class="quicklogin">输入服数&nbsp<input type="" class="inputbg" maxlength="3" value="<#if serverList?? && serverList?size gt 0><#list serverList as server><#if (server.recommand == 'true' && server.serverStatus == '1')>${server.serverNo}<#break></#if></#list></#if>"/>&nbsp服</span><a href="#" class="quickenter"></a>
      	
	    	</div>
	        <ul>
	        	<#if serverList?? && serverList?size gt 0>
			        <#assign remainder=(serverList?size %n)?int >
				    <#list serverList as server>
						<#if (server.serverStatus == '1')>
							 <li class="sHot"><a href="javascript:loginSubmit('${server.id}')">${server.serverName}</a></li> 
						</#if>
						<#if (server.serverStatus == '-2')>
							 <li class="stop"><a href="javascript:stopAlert()">${server.serverName}</a></li> 
						</#if>
						<#if (server.serverStatus == '2')>
						 <li class="sBusy"><a href="javascript:loginSubmit('${server.id}')">${server.serverName}</a></li> 
						</#if>
						<#if (server.serverStatus == '0')>
						 <li class="sFreely"><a href="javascript:loginSubmit('${server.id}')">${server.serverName}</a></li> 
						</#if>
						<#if ((server_index+1) == remainder) || (((server_index+1)>=(remainder + n)) && ((server_index+1-remainder)%n == 0)) >                		
	                			</ul>
	    						</div>
	                		<div class="qb_list switchMain"  style="display:none;overflow-x: hidden; overflow-y:auto; ">
	                				<div class="quickgame">
							    		<span class="quicklogin">输入服数&nbsp<input type="" class="inputbg" maxlength="3" value="<#if serverList?? && serverList?size gt 0><#list serverList as server><#if (server.recommand == 'true' && server.serverStatus == '1')>${server.serverNo}<#break></#if></#list></#if>"/>&nbsp服</span>
							    		<a href="#" class="quickenter"></a>
							    	</div>
	                			<ul>
			                	</#if>                                   
			            	</#list>
			            </#if>
				</div>
		    </div>
    </div>
	<div class="hidden" style="display:none"></div>
   <script type="text/javascript">
   var idList=[<#list serverListAsc as server>${server.id}, </#list>0];
	    function loginSubmit(id){
			if(id!=null) {
				$.ajax({
			        type:"POST",
			        dataType:"jsonp",
			        url:"${accountCenterPath}PlayGame2?game=S&subGame=0&g=1&_rs=registers",
			        jsonp:"jsoncallback",
			        data:{z:id},
			        beforeSend:function (XMLHttpRequest, textStatus) {

			        },
			        success:function(data) {
			            if(data.code == 0){
							callback2(data.object);
						}else{
							alert(data.msg);

						}
			        }
			    });
			} else {
				alert('请登录后进入游戏');
			}
	    		
	    }
		function logout(){
			//注销
			$.cookie('VVNFUklORk8%3D', null, { path: '/',domain: COOKIE_NAME });
			$.post(SERVER + "/indexLogout.action", {
			}, function(data){				
				if(data.result == 'true') {	
					alert('注销成功！');
					window.location.href="logexe_log.html";
				} else {
					alert('连接服务器超时，请稍后再试。');
				}
			}); 
		}
		function getRequestParm(paraName){ 
			   var reg= new RegExp("(^|&)"+paraName+"=([^&]*)(&|$)");   
				var r=window.location.search.substr(1).match(reg);   
				if(r!=null) return unescape(r[2]);return null;   
		} 
		function  initPageDate(){
			var username=getRequestParm('username');//初始化用户名
			var lastServName=getRequestParm('lastServerName');//最近登录服务器名
			if(username){
				$("#username").text(username);
			}
			if(lastServName&&lastServName!="还没有登录过游戏"&&lastServName!="null"){
				$("#lastServerName").text(lastServName);
			}
		}
		function checkLastServer(){
			var state=getRequestParm("state");
			var lsn=$("#lastServerName");
			if(state == '-2'){
				lsn.removeClass("zc");
				lsn.addClass("zc_gray");
			}
		}
		$(function(){
			initPageDate();
			checkLastServer();
		});
		// function changeArea(index){		
		// 	$("#index0").css("display","none");
		// 	$("#index0").nextAll().css("display","none");
		// 	$("#index"+index).css("display","block"); 	
		
		// 	$("#area0").removeClass("on"); 
		// 	$("#area0").nextAll().removeClass("on");
		// 	$("#area"+index).addClass("on"); 
		// }


		$(".navTab p").click(function(){
        	var index = $(".navTab p").index(this);
        	$(this).addClass("on").siblings().removeClass("on");
        	$(".switchMain").eq(index).show().siblings(".switchMain").hide();
    	}) ;
		
			var callback2 = function callback2(url){
				url=url.replace("game.jsp","Loading.swf");	
				url=url+"&rand="+randomNum();
				LoadGame(url);
			};
		
		function randomNum(){
			return new Date().getTime();
		}	
		function lastServerLogin(){
				var servId=getRequestParm('lastServerId');
				if(servId){
					loginSubmit(servId);
				}else{
					alert("没有您最近的登录信息，请从下面服务器列表登录");
				}
		}
		function stopAlert(){
			alert("服务器即将开启。");
		}
		
		$(function(){
			$('#lastServerLogin').click(function(){
				lastServerLogin();
			});
			$("a.quickenter").click(function(){
				var serverLength=idList.length-1;
				var num=$(".inputbg").val();
				if(num<=serverLength&&num>0){
					loginSubmit(idList[num-1])
				}
				else{
					alert("请输入存在的区服!");
				}
			})
		});



      </script>

	

</body>

</html>

