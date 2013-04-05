loginRerArgument(document.location.href);

var global={
	"registerData":{},
	loginUrl:"http://sq.7road.com/game7road/loginSubmit.action",
	logoutUrl:"http://sq.7road.com/game7road/indexLogout.action",
	checkUserNameUrl:"http://sq.7road.com/game7road/checkUserName.action",
	
	checkLogin:"http://sq.7road.com/game7road/checkLogin.action",//检测是否已经登陆
	serverList:"http://sq.7road.com/vote7road/onlinePlayer/queryAllGameServer.action",//获取全部区服
	//"serverList":"http://sq.7road.com/sqLucky/getServerList.action",
	isSignUp:"http://sq.7road.com/vote7road/onlinePlayer/queryUserIsSignUp.action",//检测用户是否报名
	addPlayer:"http://sq.7road.com/vote7road/onlinePlayer/addPlayerInfo.action",//用户报名
	queryNickName:"http://sq.7road.com/vote7road/onlinePlayer/queryNickName.action",//查询角色名
	queryProgress:"http://sq.7road.com/vote7road/onlinePlayer/queryProgress.action",//查询审核进度
	buyFlower:"http://sq.7road.com/vote7road/playerFlower/buyFlower.action",//用户购买鲜花
	addFreeFlower:"http://sq.7road.com/vote7road/playerFlower/addFreeFlower.action",//用户获得免费鲜花平台回调接口
	sendFlower:"http://sq.7road.com/vote7road/playerFlower/sendFlower.action",//用户送花
	token:"http://sq.7road.com/vote7road/onlinePlayer/myToken.action",//获取token,防止重复提交
	sendToken:"http://sq.7road.com/vote7road/playerFlower/mySendToken.action",
	flowerMany:"http://sq.7road.com/vote7road/playerFlower/queryUserFlower.action",//当前用户花的数量
	orderId:"http://sq.7road.com/vote7road/playerFlower/geneOrder.action",//订单生成
	fansList:"http://sq.7road.com/vote7road/playerFlower/queryPlayerFansTop10.action"//粉丝排行榜
};

function getPropertyCount(o){  
   var n, count = 0;  
   for(n in o){  
      if(o.hasOwnProperty(n)){  
         count++;  
      }  
   }  
   return count;  
}

//JQ扩展简单验证
jQuery.fn.extend({
		ckVal:function(str){
			if(str){//添加错误信息
					var wrongOb=$(this).parent().find(".wrongTip");
					if(wrongOb.length > 0){$(wrongOb).eq(0).html(str);}
					else{
							$(this).parent().append('<p class="wrongTip">'+str+'</p>');
						}
				}
				else{
					if($(this).val() == "")return false;
					return true;
				}
			},
		removeWrong:function(){
				$(this).parent().find(".wrongTip").remove();
			},
		//验证电话
		checkPhone:function(){
				if(!$(this).ckVal()){$(this).ckVal("手机号码不能为空"); return};//为空
				var phoVal=$(this).val();
				if(!(/^1[3|5][0-9]\d{4,8}$/.test(phoVal)))
					 { 
					 	$(this).ckVal("手机格式不正确");
					}else{
						$(this).removeWrong();
						}
			},
		//验证邮箱
		checkMail:function(){
				if(!$(this).ckVal()){$(this).ckVal("邮箱不能为空");return;}
				var mailVal=$(this).val();
				var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!myreg.test(mailVal)){
						$(this).ckVal("email格式不正确");
					}else{$(this).removeWrong();}
			},
		//用户名,需传参数表示最小与最大值
		checkName:function(len,mlen){
				if(!$(this),ckVal()){$(this).ckVal("用户名不能为空");return;}
				var nameVal=$(this).val();
				if(nameVal<len || nameVal>mlen)
					{
						$(this).ckVal("用户名必须大于"+len+"小于"+mlen);
					}else{
							$(this).removeWrong();
						}
			},
		//验证年龄
		checkAge:function(){
			if(!$(this).ckVal()){$(this).ckVal("年龄不能为空");return;}
			var ageVal=$(this).val();
			if(!(/^[0-9]*$/.test(ageVal)) || ageVal<16 || ageVal>120){
					$(this).ckVal("年龄输入非法");
				}else{$(this).removeWrong(); }
			
			},
		//验证QQ
		checkQQ:function(){
				if(!$(this).ckVal()){$(this).ckVal("QQ不能为空");return;}
				var qqVal=$(this).val();
				if(qqVal.length <4 || qqVal.length > 12 || !(/^[0-9]*$/.test(qqVal)))
					{
						$(this).ckVal("QQ输入不正确");
					}else{$(this).removeWrong();}
			},
		checkTrueN:function(){
			if(!$(this).ckVal()){$(this).ckVal("请填写真实姓名");}
			else{$(this).removeWrong();}	
			},
		//检查当前表单有哪些错误
		ckWrongId:function(i){//form-add
				var _wrongObj=$(this).find(".wrongTip");
				if(i){
						$(_wrongObj).eq(0).siblings("input").eq(0).focus();
					}else{
						
						if($(_wrongObj).length < 1){return true;}
						return false;
					}
				
			}
		
	});

$("#joinPop .realName").blur(function(){//真实姓名
		$(this).checkTrueN();
	})
$("#joinPop .mobile").blur(function(){//手机号码
		$(this).checkPhone();
	})
$("#joinPop .age").blur(function(){//年龄
		$(this).checkAge();
	})
$("#joinPop .qq").blur(function(){//QQ
		$(this).checkQQ();
	})
//事件交互
var ajaxEvt={
	token:"",
	getInfo:{},
	//获取token
	getToken:function(it){
		if(it){$.get(global.token,function(data){ajaxEvt.token=data.token;})//报名时用
		}else{
			$.get(global.sendToken,function(data){ajaxEvt.token=data.sendToken;});//赠送鲜花时用	
			};
		
		},
	//登录后显示
	loginSuccess:function(data){
		$(".topLoginInfo").hide();
		$(".topLoginedInfo").show();
		$(".idx_name,.gameName").text("欢迎您，"+data.outName);
		ajaxEvt.cutUser = data.outName;
		$(".showName").text(data.outName);
		$(".meinLogin").hide();
		$(".regBtn").hide();
		
		ajaxEvt.getFreeFl();
		$("body").append(data.bbsData);//同步论坛
		var userServerId_1 = $("#serverList,#serverList1").find("option:selected").val();
		var userServerNo_2 = $("#serverList,#serverList1").find("option:selected").attr("serverNo");
		if(userServerId_1>0){
			$(".text01").hide();
			$.get("http://sq.7road.com/sqLucky/getCount.action?serverId="+userServerId_1+"&serverNo="+userServerNo_2+"",function(data){
				if(data.code==1){
					$(".hasNum").show();
					$(".drawNum").empty().text(data.msg);
				}
			});
		};
	},
	//获取免费花
	getFreeFl:function(){
			$.get(global.addFreeFlower+"?_g=1&t="+Math.random(),function(data){});
		},
	//获取服务器列表
	getSerList:function(){
		$.get(global.serverList+"?_g=1&t="+Math.random(),function(data){
			if(data.resultMsg=="success"){
				var dataObj = eval("("+data.serverMap+")")[0];
				var dataObjLength = getPropertyCount(dataObj);
				for(var i in dataObj){
					$("#serverList,#serverList1").append('<option value="'+i+'">'+dataObj[i]+'</option>');
				}
			}else{
				//errer。。。
			}
		});
	},
	//检测登录
	checkLogin:function(fn,outF){
			$.post(global.checkLogin,function(data){
					if(data.result == "true"){
						fn(data);
						//ajaxEvt.loginSuccess();
						}else{
							if(outF){outF();}
							}
				});
		},
	//用户登录
	loginSubmit:function(userVal,userPas){
		$.post(global.loginUrl,{"inName":userVal,"password1":userPas},function(data){
			if(data.result == "true"){
				ajaxEvt.loginSuccess(data);
				$(".meinLogin").hide();
				$(".userPass").val("");
				$(".gameName").show();
				Dom.closePop();
			}else{
				alert(data.respMsg);
			}
		})
	},
	//退出登录
	outLogin:function(){
			$.post(global.logoutUrl,function(data){
				if(data.result == "true"){
					$(".gameName").hide();
					$(".meinLogin").show();
					$(".userPass").val("");
					$("body").append(data.bbsData);//同步论坛
				}else{alert("服务器繁忙，请稍后在试！")}
		
			})
		},
	//美女报名
	girlApply:function(dataAr,obj){
			$.ajax({
				type:"POST",
				url:global.addPlayer,
				data:dataAr,
				success:function(data){
					if(data.resultMsg == "success"){//保存资料成功
						alert("您的资料已提交成功!");
						setTimeout(function(){Dom.closePop();},1500)
					}
					$(obj).addClass("submitInfo1");//提交完后让按钮可点
				},
				error:function(data){
					alert(data.msg);
					$(obj).addClass("submitInfo1");//提交完后让按钮可点
				}
			});
		
		},
	//选择服务器时获取角色名称
	getNickName:function(url){
		$.get(url,function(data){
			if(data.resultMsg=="success"){
				var _datn=data._n;
				if(data._n==""){
						alert("您在该服没有角色");
					}else{
						$(".nikeName").val(data._n);
						}
				}
			});	
		},
//用户报名
	userJoin:function(){
		$.post(global.checkLogin,function(data){
			if(data.result==="true"){
				ajaxEvt.isSignUp();
			}else{
				Dom.showPop({"cN":"#loginPo","cT":"美女登录"});
			}
		});
		ajaxEvt.getToken(1);
	},
	//检测用户是否已报名
	isSignUp:function(){
			$.get(global.isSignUp+"?_g=1&t="+Math.random(),function(data){
			if(data.resultMsg == "success"){
				$.get(global.token,function(data){ajaxEvt.token=data.token;});
				Dom.showPop({"cN":"#joinPop","cT":"我要参加"});
			}else if(data.resultMsg == "signUp"){
				ajaxEvt.signUpStatus();
			}else if(data.resultMsg=="invalid" || data.resultMsg=="error"){
				//errer。。。
			}
		});
	},
//获取角色名
	queryNickName:function(){
		$.get(global.queryNickName+"?_g=1&t="+Math.random(),function(data){
			if(data.resultMsg == "success"){
				alert("获取角色名成功");
				alert(data._n);//游戏角色名
			}else{
				//error
			}
		});
	},
//判断审核状态
	signUpStatus:function(){
			$.get(global.queryProgress+"?_g=1&t="+Math.random(),function(data){
				switch(data.status)
				{
					case "1":
						Dom.showPop({"cN":"","cT":"","conT":'<p class="desc">您好，<em>'+ajaxEvt.cutUser+'</em> 您的资料正在审核中，请耐心等待。</p>'});
						break;
					case "2":
						alert("审核成功");
						break;
					case "3":
						alert("审核失败");
						break;
					default:
						break;
				}
			});
	},
//赠送鲜花
	sendFlower:function(dataAr,obj){
			$.ajax({
				type:"GET",
				url:global.sendFlower,
				data:dataAr,
				success:function(data){
					switch(data.resultMsg)
						{
							case "success":
								ajaxEvt.token = data.sendToken;
								//更新
								var _obj = $(ajaxEvt.getInfo.obj).find(".f_man").find("tbody");
								var _html = '<tr><td>'+data.userName+'</td><td>'+ajaxEvt.getInfo.sendFlo+'朵</td></tr>';
								if($(_obj).find("tr").length == 3){
										$(_obj).find("tr:last").remove();
									}
								$(_obj).prepend(_html);
								$.get(global.flowerMany+"?t="+Math.random(),function(data){
									if(data._n != -1){
										$("#surplusRose").text(data._n);
										Dom.closePop();
										Dom.showPop({"cN":"#giving-ok","cT":""});
										}else{alert("赠送成功");Dom.closePop();}	
									});
								break;
							case "repeat":
								alert("表单重复提交");
								break;
							case "lessFlower":
								$.get(global.flowerMany+"?t="+Math.random(),function(data){
									if(data._n != -1){
										$("#surplusRose_w").text(data._n);
										Dom.closePop();
										Dom.showPop({"cN":"#giving-fail","cT":""});
										}else{alert("您的鲜花数量不够");Dom.closePop();}	
									});
								break;
							default:
								ajaxEvt.token = data.sendToken;
						}
					$(obj).addClass("flowerBt");
					},
				error:function(data){$(obj).addClass("flowerBt");}
				
			});
		},
//获取当前用户鲜花的数量 
	flowMany:function(){
			$.get(global.flowerMany+"?t="+Math.random(),function(data){
					if(data._n != -1){$("#surplusFlo").text(data._n);}
					else{alert("服务器繁忙,请稍候在赠送");}
				});
		},
//去支付跳转
	goPay:function(dataAr){
			$.ajax({
					type:"GET",
					url:global.orderId+"?t="+Math.random(),
					success:function(data){
							if(data.order == "error"){alert("生成订单号失败,请重新提交");}
							else{
									dataAr["order"] = data.order;
									$.post(global.buyFlower,dataAr,function(){});
								}
						},
					error:function(msg){alert("生成订单号失败,请重新提交");}
				});
			
		},
//获取粉丝列表
	getFanlist:function(id){
		var url=global.fansList;
		$.ajax({
			type:"GET",
			url:global.fansList+"?cId="+id+"&t="+Math.random(),
			success:function(data){
					if(data.resultMsg == "success"){
							var _data=data.fansTop;
							var _len=_data.length;
							var hml="";
							for(var i=0; i<_len; i++){
									hml += '<li><em>'+(1+i)+'</em><p class="darkRd"><span class="r_n">'+_data[i]["giver"]+'</span><span class="r_a">9区</span><span class="r_m">'+_data[i]["flowerNum"]+'朵</span></p></li>'
								}
							$("#fan-list").find("ol").html(hml);
							$("#fan-list").find("ol").find("li:lt(3)").find("em").addClass("fst");
							$("#fan-list").find("ol").find("li:odd").addClass("cutBk");
							Dom.showPop({"cN":"#fan-list","cT":"粉丝排行榜"});
						}else{
								alert("获取排行榜失败,请稍候在试");
							}
					
				}
			});
		},
//返回修改付款信息
	backPay:function(){
			Dom.closePop();
			Dom.showPop({"cN":"#buyFr","cT":"购买鲜花"});
		}

};
	
$(function(){
	ajaxEvt.checkLogin(ajaxEvt.loginSuccess);
	ajaxEvt.getSerList();
	//初始化美女列表
	$(".rank_be li:not('.s_pic'):odd,.rank_fan li:not('.s_pic'):odd,#fan-list li:not('.s_pic'):odd").addClass("cutBk");
	$(".rank_be li").eq(1).show();
	$(".rank_be li").eq(0).addClass("cut-Bk");
	//美女排行榜移动效果
	$('.rank_be li:not(".s_pic")').live("mouseover",function(){
			$('.rank_be li:not(".s_pic")').removeClass("cut-Bk");
			$(this).parent().find(".s_pic").hide();
		
			$(this).addClass("cut-Bk");
			$(this).next(".s_pic").show();
	
		});
	//登录
	$(".loginBtn").live("click",function(){
		var pares=$(this).parents(".login");
		
		var userVal = $(pares).find(".userName").val(),userPas = $(pares).find(".userPass").val();
		if(!userVal || !userPas){
			alert("账号或密码不能为空!");
			return false;
		}else{
			ajaxEvt.loginSubmit(userVal,userPas);
		}
	});
	//退出登录
	$(".indexLogout").click(function(){
		ajaxEvt.outLogin();
		});

	//美女报名
	$(".submitInfo1").live("click",function(){
		var that=$(this);
		//发送请求时让按钮不可点
		$(that).removeClass("submitInfo1");
		var showName=$.trim($(".showName").val()),nikeName=$.trim($(".nikeName").val()),serverList1=$.trim($("#serverList1").val()),servName=$("#serverList1").find("option").get($("#serverList1")[0].selectedIndex).text,realName=$.trim($(".realName").val()),age=$.trim($(".age").val()),mobile = $.trim($(".mobile").val()),qq = $.trim($(".qq").val()),slogan =$.trim($(".slogan").val());
		var proVal=$("#province").val(),cityVal=$("#city").val();
		if(proVal == "" || cityVal == ""){alert("请选择城市");return;}
		if($(".form-add").ckWrongId()){
			var _data={"_g":1,"_n":nikeName,"_sId":serverList1,"_s":servName,"_r":realName,"age":age,"phone":mobile,"location":proVal+cityVal,"qq":qq,"slogan":slogan,"token":ajaxEvt.token};
			ajaxEvt.girlApply(_data,that);
		}else{$(".form-add").ckWrongId(1);$(that).addClass("submitInfo1");}
		
	});
	//报名选择服务器获取角色游戏昵称
	$("#serverList1").live("change",function(){
		var serverId = $("#serverList1").val();
		var url=global.queryNickName+"?_g=1&_sId="+serverId+"&t="+Math.random();
		ajaxEvt.getNickName(url);
		});
	


//Dom.placehold();
function ajaxSucc(data){
	switch(data.resultMsg)
		{
			case "success"://成功
					
				break;
			case "unLogin"://未登录
				Dom.showPop({"cN":"#loginPo","cT":"用户登录"});
				break;
			case "repeat"://订单重复提交
				alert("请不要重复提交");
				break;
			case "invalid"://参数错误
				alert("参数错误");
				break;
			case "faliure"://失败
				alert("失败");
				break;
			case "error"://系统错误
				alert("系统错误");
				break;
								
		}			
	}

//赠送鲜花
$(".give-flow").live("click",function(){
		//简单验证用户是否登录
		Dom.closePop();
		window._cId=$(this).parents("li").attr("cId");
		if($.trim($("#idx_name").text()) == ""){Dom.showPop({"cN":"#loginPo","cT":"用户登录"});return;}
		Dom.showPop({"cN":"#flower","cT":"赠送鲜花"});
		ajaxEvt.flowMany();
		ajaxEvt.getToken();
		ajaxEvt.getInfo.obj = $(this).parents("li");
		
	});
$(".flowerBt").live("click",function(){
		var flManys=$("#flower").find(".flower-many").eq(0);
		var cId=window._cId,anonymous=$("#flower input[name='anonymous']:checked").val();
		if($(flManys).val() == "" || !(/^[0-9]*$/.test($(flManys).val()))){alert("花的数量输入非法");$(flManys).focus(); return;}
		if(parseInt($(flManys).val()) > parseInt($("#surplusFlo").text())){alert("您没有足够的花");return;}
		if(anonymous == undefined){alert("请选择赠送方式"); return;}
		//按钮失效
		var that=$(this);
		$(that).removeClass("flowerBt");
		ajaxEvt.getInfo.sendFlo = $(flManys).val();
		var data={"_n":$(flManys).val(),"cId":cId,"anonymous":anonymous,"sendToken":ajaxEvt.token,"t":Math.random()};
		
		ajaxEvt.sendFlower(data,that);

	});
//购买鲜花
$(".buy-flower,.getFlo").live("click",function(){
	Dom.closePop();
	//简单验证用户是否登录
	if($.trim($("#idx_name").text()) == ""){Dom.showPop({"cN":"#loginPo","cT":"用户登录"});return;}
	Dom.showPop({"cN":"#buyFr","cT":"购买鲜花"});	
	
	})
//粉丝排行榜
$(".fans-top").live("click",function(){
		var cId = $(this).parents("li").attr("cid");
		ajaxEvt.getFanlist(cId);
	});
//内页美女风采按钮
$(".mienBt").on("click",function(){showDialog(1);});
//内页我要参加按钮
$(".ruleBt").on("click",function(){showDialog(3);});
//内页活动规则按钮
$(".forumBt").on("click",function(){showDialog(2);});
//内页活动奖励按钮
$(".awardBt").on("click",function(){showDialog(4);});
//内页火热报名
$(".applyBt").on("click",function(){showDialog(7);});
//购花自定义数量

$(".flowerNum").live("change",function(){
		var flows=$(this).val();
		var _ob=$(this).parents("li").next("li");
		if(flows == -1){
				$(_ob).find(".cust-flower").val("");
				$(_ob).show();
		}else{	
			$(_ob).hide();
			var _val = flows * 1;
			$("#pay-money").text(_val);
			 }
	});
$(".cust-flower").live("blur",function(){
		var _val = $(this).val();
		_val = parseInt(_val);
		$(this).val(_val);
		$("#pay-money").text(_val);
	});
//支付方式
$("input[name='payMeth']").live("click",function(){
		var payVal=$(this).val();
		var _ob=$(this).parents("li").next("li");
		if(payVal == 1){
				$(_ob).show();
			}else{ $(_ob).hide(); }
	});
});
//支付跳转
$(".buyBtn").live("click",function(){
		//花的数量
		var _n = $("#buyFr").find(".flowerNum").val();
		if(_n == 0){alert("请选择购买花的数量");return;}
		if(_n == -1){ _n = $("#buyFr").find(".cust-flower").val();}
		//花费的金钱
		var amount = _n * 1;
		//银行标识
		var _b = "";
		//支付方式
		var _s = $("#buyFr input[name = 'payMeth']:checked").val();
		if(_s == 1){ _b = $("#buyFr input[name = 'payItem']:checked").val();;}
		var data={
				"_n": _n,"_s":_s,"_b":_b,"amount":amount,"_t":Math.random()
				}
		ajaxEvt.goPay(data);//支付跳转
		Dom.closePop();
		Dom.showPop({"cN":"#buy-result","cT":""});
	})
function showDialog(args){
  	switch (args){
		case 1://1.美女风采
			window.open("video.html",'_blank');//新开页
			//Dom.showPop({"cN":"#buyFr","cT":"购买鲜花"});
        	break;
		case 2://2.活动规则
			Dom.showPop({"cN":"#dataRule","cT":"活动规则"});
        	break;
		case 3://3.我要参加
			ajaxEvt.userJoin();
			break;
		case 4://4.活动奖励
			Dom.showPop({"cN":"#dataReward","cT":"活动奖励"});
        	break;
		case 5://5.美女论坛
			window.open("http://bbs.7road.com/forum.php?gid=37/forum.php?gid=37",'_blank');
        	break;
        case 7://7.火热报名
			ajaxEvt.userJoin();
        	break;
		default :
		break;
	}
}