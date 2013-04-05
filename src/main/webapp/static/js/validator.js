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

function emailOnFocus(){
		var email = document.getElementById("email");
		email.className = "g-ipt-active";
		$('#emailTip').empty();
	}
	function emailOnBlur(){
		var email = document.getElementById("email");
		if(email.value == "" || email.value == null){
			$('#emailTip').empty();
			email.className = "g-ipt";
			return;
		}
		var regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		if(email.value.length > 30){
			$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>邮箱过长</SPAN></P>");
			email.className = "g-ipt-err";
		}else{
			if(regex.test(email.value)){
			var url = "checkEmail.action?email="+email.value;
			$.get(url,function (data){
				if(data.result == "false"){
					$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该邮箱已注册</SPAN></P>");
					email.className = "g-ipt-err";
				}else{
					$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>邮箱正确</SPAN></P>");
					email.className = "g-ipt";
					}
				});
			}else{
			$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>电子邮箱格式错误</SPAN></P>");
			email.className = "g-ipt-err";
			}	
		}
			
	}
	function userNameOnFocus(){
		var nickname = document.getElementById("userName");
		nickname.className = "g-ipt-active";
		$('#userNameTip').empty();
	}
	function userNameOnBlur(){
		var reg = /^[a-zA-Z0-9_]{6,20}$/;
		var regRepeat = /([A-Za-z0-9_])\1{4,}/;
		var nickname = document.getElementById("userName");
		if(nickname.value == "" || nickname.value == null){
			$('#userNameTip').empty();
			nickname.className = "g-ipt";
			return;
		}
		if(nickname.value.length > 20 || nickname.value.length < 6){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			nickname.className = "g-ipt-err";
		}else if(!reg.test(nickname.value)){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名应由字母、数字、下划线组成</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(!regLetterFirst.test(nickname.value)) {
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>用户名第一位应为字母</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else if(regRepeat.test(nickname.value)){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}
		else if(!checkKeyword(nickname.value)){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else{
			var url = SERVER + "/checkUserName.action?userName="+nickname.value;
			$.get(url,function(data){
				if(data.result == "false"){
					$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
					nickname.className = "g-ipt-err";
				}else{
					$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>用户名正确</SPAN></P>");
					nickname.className = "g-ipt";
				}
			});
		}
	}
	function password1OnFocus(){
		var password1 = document.getElementById("password1");
		password1.className = "g-ipt-active";
		$('#password1Tip').empty();
	}
	function password1OnBlur(){
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		if(password1.value == "" || password1.value == null){
			$('#password1Tip').empty();
			password1.className = "g-ipt";
			return;
		}
		if(password1.value.length > 20 || password1.value.length < 6){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			password1.className = "g-ipt-err";
		}else{
			if(password2.value != "" && password2.value != null){
				if(password2.value != password1.value){
					$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次密码不一致</SPAN></P>");
					$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password2.className = "g-ipt-err";
					password1.className = "g-ipt";
				}else{
					$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password1.className = "g-ipt";
					$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
					password2.className = "g-ipt";
				}
			}else{
				$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
				password1.className = "g-ipt";
			}
		}
	}
	function password2OnFocus(){
		var password2 = document.getElementById("password2");
		password2.className = "g-ipt-active";
		$('#password2Tip').empty();
	}
	function password2OnBlur(){
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		if(password2.value == "" || password2.value == null){
			$('#password2Tip').empty();
			password2.className = "g-ipt";
			return;
		}
		if(password2.value != password1.value){
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次密码不一致</SPAN></P>");
			password2.className = "g-ipt-err";
		}else{
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
			password2.className = "g-ipt";
		}
	}
	function realNameOnFocus(){
		var realName = document.getElementById("realName");
		realName.className = "g-ipt-active";
		$('#realNameTip').empty();		
	}
	function realNameOnBlur(){
		var realName = document.getElementById("realName");
		if(realName.value == "" || realName.value == null){
			$('#realNameTip').empty();
			realName.className = "g-ipt";
			return;
		}
		if(!(/^[\u4e00-\u9fa5]+$/.test(realName.value))){
			$('#realNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入真实姓名</SPAN></P>");
			realName.className = "g-ipt-err";
		} else if (realName.value.length > 20) {
			$('#realNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>姓名过长</SPAN></P>");
			realName.className = "g-ipt-err";
		} else{
			$('#realNameTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>输入正确</SPAN></P>");
			realName.className = "g-ipt";
		}		
	}
	function idCardOnFocus(){
		var idCard = document.getElementById("idCard");
		idCard.className = "g-ipt-active";
		$('#idCardTip').empty();		
	}
	function idCardOnBlur(){
		var powers=new Array("7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2");     
	    var parityBit=new Array("1","0","X","9","8","7","6","5","4","3","2"); 
		var idCard = document.getElementById("idCard");
		if(idCard.value == "" || idCard.value == null){
			$('#idCardTip').empty();
			idCard.className = "g-ipt";
			return;
		}
		  _id=idCard.value +"";     
	        var _num=_id.substr(0,17);     
	        var _parityBit=_id.substr(17);     
	        var _power=0;     
	        for(var i=0;i< 17;i++){     
	            //校验每一位的合法性     
	    
	            if(_num.charAt(i)<'0'||_num.charAt(i)>'9'){     
	            	$('#idCardTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入正确的身份证号码</SPAN></P>");
	            	idCard.className = "g-ipt-err";   
	                break;     
	            }else{     
	                //加权     
	    
	                _power+=parseInt(_num.charAt(i))*parseInt(powers[i]);     
	                //设置性别     
	    
	                    
	            }     
	        }     
	        //取模     
	    
	        var mod=parseInt(_power)%11;     
	        if(parityBit[mod]==_parityBit){ 
	        	$('#idCardTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>输入正确</SPAN></P>");
	        	idCard.className = "g-ipt";
	            return ;     
	        }     
	        $('#idCardTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入正确的身份证号码</SPAN></P>");
        	idCard.className = "g-ipt-err";        
		
		
	}
	function verifyCodeOnFocus(){
		var verifyCode = document.getElementById("verifyCode");
		verifyCode.className = "g-ipt-active";
		$('#verifyCodeTip').empty();
	}
	function verifyCodeOnBlur(){
		var verifyCode = document.getElementById("verifyCode");
		verifyCode.className = "g-ipt";
	}
	function checkAll(){
		var result = true;
		var email = document.getElementById("email");
		var regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		var nickname = document.getElementById("userName");
		var password1 = document.getElementById("password1");
		var password2 = document.getElementById("password2");
		var verifyCode = document.getElementById("verifyCode");
		if(email.value == "" || email.value == null){
			$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入邮箱</SPAN></P>");
			email.className = "g-ipt-err";
			result = false;
		}else {
			if(email.value.length > 30){
				$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>邮箱过长</SPAN></P>");
				email.className = "g-ipt-err";
				result = false;
			}else{
			if(regex.test(email.value)){
			var url = "checkEmail.action?email="+email.value;
			$.ajax({
				type: "get",
				url: url,
				async: false,
				dataType: "json",
				success: function(data){
					if(data.result == "false"){
					$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该邮箱已注册</SPAN></P>");
						email.className = "g-ipt-err";
						result = false;
					}else{
						$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>邮箱正确</SPAN></P>");
						email.className = "g-ipt";
					}
				}			
			});
			}else{
				$('#emailTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>电子邮箱格式错误</SPAN></P>");
				email.className = "g-ipt-err";
				result = false;
			}
			}
		}
		if(nickname.value == "" || nickname.value == null){
			$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入用户名</SPAN></P>");
			nickname.className = "g-ipt-err";
			result = false;
		}else{
			if(nickname.value.length > 20 || nickname.value.length < 6){
				$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
				nickname.className = "g-ipt-err";
				result = false;
			}else{
				var url = "checkUserName.action?userName="+nickname.value;
				$.ajax({
					type: "get",
					url: url,
					async: false,
					dataType: "json",
					success: function(data){
						if(data.result == "false"){
							$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>该用户名已被注册</SPAN></P>");
							nickname.className = "g-ipt-err";
						}else{
							$('#userNameTip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>用户名正确</SPAN></P>");
							nickname.className = "g-ipt";
						}
					}			
				});
			}
		}
		if(password1.value == "" || password1.value == null){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入密码</SPAN></P>");
			password1.className = "g-ipt-err";
			result = false;
		}
		if(password1.value.length > 20 || password1.value.length < 6){
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>长度应在6~20之间</SPAN></P>");
			password1.className = "g-ipt-err";
			result = false;
		}else{
			$('#password1Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
			password1.className = "g-ipt";
		}
		if(password2.value == "" || password2.value == null){
			$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请再次输入密码</SPAN></P>");
			password2.className = "g-ipt-err";
			result = false;
		}else{
			if(password2.value != password1.value){
				$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>两次输入的密码不一致</SPAN></P>");
				password2.className = "g-ipt-err";
				result = false;
			}else{
				$('#password2Tip').empty().html("<P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P>");
				password2.className = "g-ipt";
			}
		}
		if(verifyCode.value == "" || verifyCode.value == null){
			$('#verifyCodeTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>请输入验证码</SPAN></P>");
			verifyCode.className = "g-ipt-err";
			result = false;
		}else{
			if(verifyCode.value.length != 4){
				$('#verifyCodeTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>验证码位数不正确</SPAN></P>");
				verifyCode.className = "g-ipt-err";
				result = false;
			}else{
				$.ajax({
				type: "get",
				url: "checkVerifyCode.action?verifyCode="+verifyCode.value,
				async: false,
				dataType: "json",
				success: function(data){
					if(data.result == "false"){
						$('#verifyCodeTip').empty().html("<P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>验证码输入错误</SPAN></P>");
						verifyCode.className = "g-ipt-err";
						document.getElementById("verifyCodeImg").src='captcha.do?'+(new Date().getTime());
						result = false;
					}else{
						$('#verifyCodeTip').empty();
						verifyCode.className = "g-ipt";
					}
				}			
				});
				
			}
		}
		if(result == true){
			document.getElementById("form1").submit();
		}else{
			return true;
		}
		
	}