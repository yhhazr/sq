        var loginVerifyFlag=false;
            function login(){
					var username=$("#username")[0].value;
		
				$.post(SERVER + "/loginSubmit.action", {
								inName: $('#username').val(),
								password1: $('#password').val()
							}, function(data){
								if(data.result == 'false') {
									// if(data.showLoginVerifyFlag ==true){
									// 	loginVerifyFlag=true;
									// }
									$("#tips").css("display","block");
						            $("#tips").text("账号或密码不正确");
									//document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
							} else if(data.result == 'true') {		
									 var lastServer=data.lastGameZoneName;
									 var lastServerId=data.lastGameZoneId;
									 var state = data.state;
									 window.location="xf.html?state=" + state + "&username="+escape(username)+"&lastServerId="+escape(lastServerId)+"&lastServerName="+escape(lastServer);
								}
						}); 		
			}
			function inputFocus(id){
				// $("#"+id).attr("value","");
				$("#"+id).attr("class","input-on");
			}
			$(function(){
			   //correctPNG();//透明处理
				//alphaBackgrounds();	//透明处理		
				$("#username").focus(function(){
						$("#tips").css("display","none");
						 $("#tips").empty(); 
						 inputFocus('username');
						 if($(this).val()==="请输入账号"){
						 	$(this).val("");
						 }
				});
				$("#password").focus(function(){
						 if(checkUsername()){
						 	$("#tips").css("display","none");
						 	$("#tips").empty(); 
						 }else{
							//$("#username").focus();
						 }
						  inputFocus('password');
				});
				$("#passwordText").focus(function(){
						 $("#passwordText").css("display","none");
						  $("#password").css("display","");
						 $("#password").focus();
				});
				// $("#verifyCode").focus(function(){
				// 		 if(checkPassword()){
				// 		 	$("#tips").css("display","none");
				// 		 	$("#tips").empty(); 
				// 		 }
				// 		 inputFocus('verifyCode');
				// });
				remeMe();
			});
			//验证登录

       function checkLogin() {
			var result =  checkUsername()&&checkPassword();
				if(result){
					login();
				}else{
					//document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
				}
		}

		function checkUsername(){
			var result = true;
			var nickname = $("#username")[0];
			if(nickname.value == "" || nickname.value == null||nickname.value==="请输入账号"){
				//alert("请输入用户名");
				$("#tips").css("display","block");
			    $("#tips").text("请输入用户名");
				result = false;
			}else if(nickname.value.length > 20 || nickname.value.length < 6){
				//alert("账号长度应在6~20之间");
				$("#tips").css("display","block");
			    $("#tips").text("账号长度应在6~20之间");
				result = false;
			}
			return result;
		}	
		function checkPassword(){
			var result = true;
			var password = $("#password")[0];
			if(password.value == "" || password.value == null){
				//alert("请输入密码");
				$("#tips").css("display","block");
			    $("#tips").text("请输入密码");
				result = false;
			}
			if(password.value.length > 20 || password.value.length < 6){
				//alert("密码长度应在6~20之间");
				$("#tips").css("display","block");
			    $("#tips").text("密码长度应在6~20之间");
				result = false;
			}
			return result;
		}
		function checkverifyCode(){
			var result=true;
			if(loginVerifyFlag){
				$("#verifyP").show();
				var  verifyCode =$("#verifyCode")[0];
				if(verifyCode.value == "" || verifyCode.value == null){			
					$("#tips").css("display","block");
					$("#tips").text("请输入验证码");
					result = false;
				}else{
					if(verifyCode.value.length != 4){
						$("#tips").css("display","block");
						$("#tips").text("验证码位数不够");
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
								document.getElementById("verifyCodeImg").src='http://sq.7road.com/game7road/captcha.do?'+(new Date().getTime());
								result = false;
							}
						}			
						});
						
					}
				}
			}
			return result;
		}
		var COOKIE_NAME ="login_user_name_sq" ;
		function remeMe(){
			//记住用户名
			var lastuser=$.cookie(COOKIE_NAME);
     			  if(lastuser){ //如果这个cookie变量确实存在；
     				 //把cookie变量的值设置为username的值；
     				 $("#username").val(lastuser); 
     				 //$("#username").attr("class","input-on");
              };   
			$("#remeMe").toggle(
				  function () {	
					$("#remeMe>img").attr("src","http://image.7road.com/loginexe/tb_02.gif");
					  $.cookie(COOKIE_NAME,$("#username").val(), { path: '/', expires: 10 });      
				  },
				  function () {
					$("#remeMe>img").attr("src","http://image.7road.com/loginexe/tb_01.gif");
				  }
				); 
		}
		$('#verifyCode').keyup(function(){
			var keyPressed;
		    if (window.event)
		        keyPressed = window.event.keyCode; // IE
		    else
		        keyPressed = e.which; // Firefox
		    if (keyPressed == 13) {
		    	checkLogin();
		    }		
		});
		$('#password').keyup(function(){
			var keyPressed;
		    if (window.event)
		        keyPressed = window.event.keyCode; // IE
		    else
		        keyPressed = e.which; // Firefox
		    if (keyPressed == 13) {
		    	checkLogin();
		    }		
		});
	 var t = n = 0, count;
    $(document).ready(function(){    
        count=$("#banner_list a").length;
        $("#banner_list a:not(:first-child)").hide();
       // $("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt'));
       // $("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")});
        $("#banner li").click(function() {
            var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4
            n = i;
            if (i >= count) return;
           // $("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
           // $("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")})
            $("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
            document.getElementById("banner").style.background="";
            $(this).toggleClass("on");
            $(this).siblings().removeAttr("class");
        });
        t = setInterval("showAuto()", 4000);
        $("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 4000);});
    })
    
    function showAuto()
    {
        n = n >=(count - 1) ? 0 : ++n;
        $("#banner li").eq(n).trigger('click');
    }