function regist(){
				if(checkRegist()){
			
				  		var username=$("#username")[0].value;
						$.post(SERVER + "/registrationSubmit.action", {
								inName: $('#username').val(),
								password1: $('#password1').val(),
								password2: $('#password2').val(),
								verifyCode: $("#verifyCode").val()
							}, function(data){
								if(data.result == 'true') {
								//window.parent.callback2(data.data, data.bbsData);//进游戏
								//window.parent.callback4(data.username);
								//	alert("注册成功！");
									 window.location="xf.html?username="+escape(username);
								}else{
									document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
								}
						});	
				}else{
					document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
				}
            }
			function inputFocus(id){
				$("#"+id).attr("value","");
				// $("#"+id).css(); 
			}
			$(function(){
				//correctPNG();//透明处理
				//alphaBackgrounds();	//透明处理	
				$("#username").focus(function(){
						 inputFocus('username');
				});
				$("#password1").focus(function(){
						if( checkUsername()){
							$("#tips").css("display","none");
							$("#tips").empty(); 
						}else{
							//$("#username").focus();
						}
						 	inputFocus('password1');
				});
				$("#password1Text").focus(function(){
						$("#password1Text").css("display","none");
						  $("#password1").css("display","");
						 $("#password1").focus();
				
				});
				$("#password2").focus(function(){
					if( checkPassword()){
							$("#tips").css("display","none");
							$("#tips").empty(); 
						}else{
						//	$("#password1").focus();
						}
						inputFocus('password2');
				});
				$("#password2Text").focus(function(){
						$("#password2Text").css("display","none");
						  $("#password2").css("display","");
						 $("#password2").focus();
				});
				$("#verifyCode").focus(function(){
						if( checkRePassword()){
							$("#tips").css("display","none");
							$("#tips").empty(); 
						}else{
							//	$("#password2").focus();
						}
						inputFocus('verifyCode');
				});
			});
	 		//验证注册
		  function checkRegist() {
			var result =checkUsername()&&checkPassword()&&checkRePassword()&&checkverifyCode();
			return result;
			}
		  function checkUsername(){
			var result = true;
			var nickname = $("#username")[0];
			var reg = /^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){5,19}$/;
			var regRepeat = /([0-9_])\1{4,}/;
			if(nickname.value == "" || nickname.value == null){
				$("#tips").css("display","block");
				$("#tips").text("请输入用户名");
				//alert("请输入用户名");
				result = false;
			}else if(!reg.test(nickname.value)){
				$("#tips").css("display","block");
				$("#tips").text("账号应由字母开头，6到20位字母数字组成");
				result = false;
			}
			// else if(nickname.value.length > 20 || nickname.value.length < 6){
			// 	$("#tips").css("display","block");
			// 	$("#tips").text("账号长度应在6~20之间");
			// 	//alert("账号长度应在6~20之间");
			// 	result = false;
			// }
			else if(!checkKeyword(nickname.value)){
				$("#tips").css("display","block");
					$("#tips").text("该用户名已被注册");
					result = false;
			}
			// else if(regRepeat.test(nickname.value)){
			// 	$("#tips").css("display","block");
			// 		$("#tips").text("帐号只能以字母开头");
			// 		result = false;
			// }
			else{
				var url = SERVER + "/checkUserName.action?inName="+nickname.value;
				$.ajax({
					type: "get",
					url: url,
					async: false,
					dataType: "json",
					success: function(data){
						if(data.result == "false"){
							$("#tips").css("display","block");
							$("#tips").text("用户名已被注册");
							//alert("用户名已被注册");
							result = false;
						}
					}			
				});
			}
			return result;
		}	
		function checkPassword(){
			var result = true;


			var password = $("#password1")[0];

			if (/[\s*]/.test(password.value)) {
		        $("#tips").css("display","block");
				$("#tips").text("密码不能含有空格");
		        return false;
		    }

			if(password.value == "" || password.value == null){
				$("#tips").css("display","block");
				$("#tips").text("请输入密码");
				//alert("请输入密码");
				result = false;
			}
			if(password.value.length > 20 || password.value.length < 6){
				$("#tips").css("display","block");
				$("#tips").text("密码长度应在6~20之间");
				result = false;
			}
			return result;
		}
		function checkRePassword(){
			var result = true;
			var password = $("#password1")[0];
			var password2 = $("#password2")[0];

			if (/[\s*]/.test(password2.value)) {
		        $("#tips").css("display","block");
				$("#tips").text("密码不能含有空格");
		        return false;
		    }

			if(password.value == "" || password.value == null){
				$("#tips").css("display","block");
				$("#tips").text("请输入密码");
				//alert("请输入密码");
				result = false;
			}else if(password.value.length > 20 || password.value.length < 6){
				$("#tips").css("display","block");
				$("#tips").text("密码长度应在6~20之间");
				result = false;
			}else if(password2.value != password.value){
				$("#tips").css("display","block");
				$("#tips").text("两次输入的密码不一致");
				//alert("两次输入的密码不一致");
				result = false;
			}
			return result;
		}
		function getSite() {
			var value = "";
			var args=new Object();     
			var query=location.search.substring(1);//获取查询串     
			var pairs=query.split("&");//获得参数数组     
			for(var i=0;i<pairs.length;i++){     
				var pos=pairs[i].indexOf('=');//查找name=value  
				if(pos==-1) continue;//如果没有找到就跳过     
				var argname=pairs[i].substring(0,pos);//提取name   
				if(argname=="site") {
					value=pairs[i].substring(pos+1);//提取value   
					break;
				}
			}     
			return value;     
		}
		function checkverifyCode(){
			var  verifyCode =$("#verifyCode")[0];
			var result=true;
			if(verifyCode.value == "" || verifyCode.value == null){			
				$("#tips").css("display","block");
				$("#tips").text("请输入验证码");
				//alert("请输入验证码");
				result = false;
			}else{
				if(verifyCode.value.length != 4){
					$("#tips").css("display","block");
					$("#tips").text("验证码位数不够");
					//alert("验证码位数不够");
					result = false;
				}else{
					$.ajax({
					type: "get",
					url: "http://sq.7road.com/game7road/checkVerifyCode.action?verifyCode="+verifyCode.value,
					async: false,
					dataType: "json",
					success: function(data){
						if(data.result == "false"){
							$("#tips").css("display","block");
							$("#tips").text("验证码错误");
							//alert("验证码错误");
							document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
							result = false;
						}
					}			
					});
					
				}
			}
			return result;
		}
		var keyArray = new Array('GameMaster','gm','GM','shit','bitch','fvc','phuc','fuk','shenqu','fuck', 'FUCK', 'Fuck','admin','7road');
		//检查屏蔽关键字
		function checkKeyword(str){	
			for(i=0; i<keyArray.length; i++) {
				if(str.indexOf(keyArray[i])!=-1) {
					return false;
				}
			}
			return true;
		}
		$('#verifyCode').keyup(function(){
			var keyPressed;
		    if (window.event)
		        keyPressed = window.event.keyCode; // IE
		    else
		        keyPressed = e.which; // Firefox
		    if (keyPressed == 13) {
		    	regist();
		    }		
		});