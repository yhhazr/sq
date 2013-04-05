<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sz7road.web.model.gamemanage.GameInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<html>
<head>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page contentType="text/html;charset=utf-8"%> 
<%
	String baseURL=SystemConfig.getProperty("online.homepage.baseURL");
	String path = request.getContextPath();
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>充值中心| webgame-37wan网页游戏平台</title>
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/37wan.css" type="text/css" />
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/2010pay.css" type="text/css"/>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/jquery.js"></script>
<link rel="stylesheet" charset="UTF-8" href="<%=baseURL %>/css/alert.css" type="text/css"/>
<link href="<%=path %>/static/style/formValidatorStyle.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" charset="UTF-8" href="http://www.37wan.com/37wancss/auto_scroll.css" type="text/css"/>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/alert.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/alert.bgiframe.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=baseURL %>/js/md5.js"></script>

<base target="_self" />
</head>
  
  <body>

					<div class="content_header" >
						<ul class="title1">
												<li class="content"><span  class="font_1">您当前选择的充值方式是“</span><span id="typeMes" class="font_2">银行卡支付</span><span class="font_1">”方式</span></li>
						<li class="help"><a href="/help.php?helpname=25" target="_blank"><span class="font_3">充值帮助</span></a></li>
						</ul>
					</div>
					<div class="content">
                    <!--无刷新提交 target="postData"--> <!--前台验证函数onsubmit="return checkForm(this);-->
                    	<iframe name="postData" width="0" height="0" style="display::none"></iframe>
						<form id="gameAccountForm" method="post" action="payGameAcount.action">
						<div class="div_pay_for" >
							<span class="font_input_title">选择您要充值到哪里</span>
							<table><tr>
							<td class="tdname">充值到哪里：</td>
							<td>
							<input id="pay_for_game" type="radio" value="game" name="pay_for" checked="checked" onclick="clickPayFor('game')"/>
							<label for="pay_for_game">默认充值到游戏</label>
							<input id="pay_for_platform" type="radio" value="platform" name="pay_for"  onclick="clickPayFor('platform')"/>
							<label id="label_for_platform" for="pay_for_platform">充值到平台</label>
														</td>
							</tr></table>
														<div id="div_game" class="div_game">
							<table style="margin-top:0px">
							<!--充值游戏开始-->
							<tr>
							<td class="tdname"  width="84px">充值游戏：</td>
                          
							<td>
						
              						<!-- onchange="setGame(this.options[this.selectedIndex].value)" -->
								<select name="gameAccountOrder.gameId" class="selection" id="game_id">
								<s:iterator value="#request.gameList" status="statu" id="item">  		    
								 	<option value="<s:property value="gameId" />" unitName="<s:property value="unitName" />" scale="<s:property value="conversionScale" />" > <s:property value="gameName" /> 
								</option>
								</s:iterator> 													
	                            </select>
							</td>
							<td>  <s:fielderror cssClass="txt-err"><s:param>gameAccountOrder.gameId</s:param></s:fielderror></td>
							</tr>
							<!--充值游戏结束-->
							<!--充值服务器开始-->
							<tr>
							<td class="tdname">充值服务器：</td>
							<td>
							
								<select id="server_id" name="gameAccountOrder.serverId" class="selection"  >
	                                <option value="0">请选择服务器</option>
	                                <s:iterator value="#request.serverList">
	                                	<option value="<s:property value="id" />"> <s:property value="serverName" /> </option>   
	                                </s:iterator>	                             
	                            	</select>
							</td>
							<td>
								<s:fielderror cssClass="font_1"><s:param>gameAccountOrder.serverId</s:param></s:fielderror></td>
							</tr>
							<!--充值服务器结束-->
							</table>
							</div>
													</div>
						<div class="div_account">
						<span class="font_input_title">填写并确认充值帐号</span>
						<table>
						<!--充入帐号开始-->
						<tr>
						<td class="tdname">充入帐号：</td>
						<td>
			
							<input type="text" id="username"  onblur="userNameOnBlur()" onfocus="userNameOnFocus()" name="gameAccountOrder.userName"  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" 
							value="<s:property value="#request.cookieName"/>" class="g-ipt" style="border: 1px solid #3289D9;padding: 3px 0 2px 2px;" />
							
						
						</td>
						<td >
							
							<div id="userNameTip" style="width:280px"><s:if test="#request.isUserNameError=='no'"><P class='font_1'><B class='ico-succ'></B><SPAN class=font_1>用户名正确</SPAN></P></s:if>	
								<s:fielderror cssClass="txt-err"><s:param>gameAccountOrder.userName</s:param></s:fielderror>
								<s:if test="#request.fieldErrors.userName!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=font_1>${fieldErrors.userName[0]}</SPAN></P><script>document.getElementById("userName").className = "g-ipt"</script></s:if>
							</div>
						</td>
						</tr>
						<!--充入帐号结束-->
						<!--确认帐号开始-->
						<tr>
						<td class="tdname">确认帐号：</td>
						<td>
							<input type="text" id="username_1"   name="gameAccountOrder.userName1" value=""    onblur="userName1OnBlur()" onfocus="userName1OnFocus()"  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" 
							value="<s:property value="#request.cookieName"/>" class="g-ipt" style="border: 1px solid #3289D9;padding: 3px 0 2px 2px;" />
						
						</td>
						<td >
							
							<div id="userName1Tip" style="width:280px"><s:if test="#request.isUserName1Error=='no'"><P class='font_1'><B class='ico-succ'></B><SPAN class=font_1>用户名不一致</SPAN></P></s:if>	
								<s:fielderror cssClass="txt-err"><s:param>gameAccountOrder.userName1</s:param></s:fielderror>
								<s:if test="#request.fieldErrors.userName!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=font_1>${fieldErrors.userName1[0]}</SPAN></P><script>document.getElementById("userName").className = "g-ipt-err"</script></s:if>
							</div>
						</td>
						</tr>
						<!--确认帐号结束-->
													<!--游戏角色开始-->
														<tr>
							<td class="tdname">游戏角色：</td>
							<td >
								<select id="actor_name" name="gameAccountOrder.roleName" class="selection" onchange="selectRole()">
									<!--<option value="1">测试数据1</option>-->
								</select>
							</td>
							<td>
								<a href="c"  id="refresh_actor_name" style="text-decoration:underline">重新获取 </a>
								<span id="gameRoleTip">
									<s:fielderror cssClass="txt-err"><s:param>gameAccountOrder.roleName</s:param></s:fielderror>
								</span>
							</td>
							</tr>
																					<tr>
							<td class="tdname">角色说明：</td>
							<td colspan="2" >进游戏未创建神曲角色名，游戏角色将默认显示英雄。
							</td>
							</tr>
														<!--游戏角色结束-->
												</table>
						</div>
						<div class="div_money">
						<span class="font_input_title">选择充值金额</span>
						<table>
												<!--充值金额开始-->
												<tr>
						<td class="tdname">充值金额：</td>
						<td>
							<select id='card_money' name="gameAccountOrder.amount" class="selection" onchange="moneyChange()">
	                        <option value="0">请选择充值金额</option>
	                        	                        			                                    <option value="10">10
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="20">20
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="50">50
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="100">100
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="200">200
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="300">300
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="500">500
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="800">800
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="1000">1000
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="2000">2000
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="5000">5000
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="10000">10000
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="20000">20000
	                                    	                                    	元
	                                    </option>
                                                                	                        			                                    <option value="50000">50000
	                                    	                                    	元
	                                    </option>
                                                                	                        </select>
	                                                </td>
						<td></td>
						</tr>
												<!--充值金额结束-->
						<!--其它充值金额开始-->
						<tr id="moneyTd">
						
							<td class="tdname">其它金额：</td>
							<td>
								<input type="text" id="money" value="0" name="gameAccountOrder.otherAmount" 
								onkeyup="checkMoney()"  class="text_input" maxlength="5" autocomplete="off" />
							</td>
							<td >
								<div id="moneyTip" style="width:280px">
									<s:fielderror cssClass="txt-err"><s:param>gameAccountOrder.otherAmount</s:param></s:fielderror>
									<span class="font_1">请在此输入你想充值的金额(范围1~50000的整数)</span>
								</div>
							</td>
						
						</tr>
						
												<!--其它充值金额结束-->
						<!--对应的金币数量开始--><tr>
						<td class="tdname" id="coin_desciption">对应钻石：</td>
						<td><input type="text" class="text_input"  id="coin" name="gameAccountOrder.gameAmount" readonly="readonly" value="0"></input></td>
						<td >
												<span class="font_1">兑换比例<font color="red">1:<span id="scale">10</span></font></span>
						</td>
						</tr><!--对应的金币数量结束-->
												<!--平台币兑换比例公告开始-->
																		<!--平台币兑换比例公告结束-->
						<!--活动提示-->
																								<!--活动提示-->
						</table>
						<!--网银(支付宝)开始-->
												<table class="paybanktype">
						<tr id="selectBank">
						<td  class="tdname" width="84px">选择银行：</td><td colspan="2"><table>
<tr>
	<td><input type="radio" id="ICBCB2C" name="pay_bank" value="ICBCB2C" checked="checked" /><img src="<%=baseURL%>/images/paybank/ICBC_OUT.gif" border="0" onclick="checkedRaido('ICBCB2C');"/></td>
	<td><input type="radio" id="CMB" name="pay_bank" value="CMB"/><img src="<%=baseURL%>/images/paybank/CMB_OUT.gif" border="0"  onclick="checkedRaido('CMB');"/></td>
	<td><input type="radio" id="CCB" name="pay_bank" value="CCB"/><img src="<%=baseURL%>/images/paybank/CCB_OUT.gif" border="0"  onclick="checkedRaido('CCB');"/></td>
 </tr>
 <tr>
	<td><input type="radio" id="BOCB2C" name="pay_bank" value="BOCB2C" /><img src="<%=baseURL%>/images/paybank/BOC_OUT.gif" border="0"  onclick="checkedRaido('BOCB2C');"/></td>
	<td><input type="radio" id="ABC" name="pay_bank" value="ABC"/><img src="<%=baseURL%>/images/paybank/ABC_OUT.gif" border="0"  onclick="checkedRaido('ABC');"/></td>
	<td><input type="radio" id="COMM" name="pay_bank" value="COMM"/><img src="<%=baseURL%>/images/paybank/COMM_OUT.gif" border="0"  onclick="checkedRaido('COMM');"/></td>
 </tr>
 <tr>
	<td><input type="radio" id="SPDB" name="pay_bank" value="SPDB"/><img src="<%=baseURL%>/images/paybank/SPDB_OUT.gif" border="0"  onclick="checkedRaido('SPDB');"/></td>
	<td><input type="radio" id="GDB" name="pay_bank" value="GDB" /><img src="<%=baseURL%>/images/paybank/GDB_OUT.gif" border="0"  onclick="checkedRaido('GDB');"/></td>
	<td><input type="radio" id="CITIC" name="pay_bank" value="CITIC"/><img src="<%=baseURL%>/images/paybank/CITIC_OUT.gif" border="0"  onclick="checkedRaido('CITIC');"/></td>
 </tr>
 <tr>
	<td><input type="radio" id="CEBBANK" name="pay_bank" value="CEBBANK"/><img src="<%=baseURL%>/images/paybank/CEB_OUT.gif" border="0"  onclick="checkedRaido('CEBBANK');"/></td>
	<td><input type="radio" id="CIB" name="pay_bank" value="CIB"/><img src="<%=baseURL%>/images/paybank/CIB_OUT.gif" border="0"  onclick="checkedRaido('CIB');"/></td>
	<td><input type="radio" id="SDB" name="pay_bank" value="SDB" /><img src="<%=baseURL%>/images/paybank/SDB_OUT.gif" border="0"  onclick="checkedRaido('SDB');"/></td>
 </tr>
 <tr>
	<td><input type="radio" id="CMBC" name="pay_bank" value="CMBC"/><img src="<%=baseURL%>/images/paybank/CMBC_OUT.gif" border="0"  onclick="checkedRaido('CMBC');"/></td>
	<td><input type="radio" id="HZCBB2C" name="pay_bank" value="HZCBB2C"/><img src="<%=baseURL%>/images/paybank/HZCB_OUT.gif" border="0"  onclick="checkedRaido('HZCBB2C');"/></td>
	<td><input type="radio" id="SHBANK" name="pay_bank" value="SHBANK"/><img src="<%=baseURL%>/images/paybank/SHBANK_OUT.gif" border="0"  onclick="checkedRaido('SHBANK');"/></td>
 </tr>
 <tr>
	<td><input type="radio" id="NBBANK" name="pay_bank" value="NBBANK " /><img src="<%=baseURL%>/images/paybank/NBBANK_OUT.gif" border="0"  onclick="checkedRaido('NBBANK');"/></td>
	<td><input type="radio" id="SPABANK" name="pay_bank" value="SPABANK"/><img src="<%=baseURL%>/images/paybank/SPABANK_OUT.gif" border="0"  onclick="checkedRaido('SPABANK');"/></td>
	<td><input type="radio" id="BJBANK" name="pay_bank" value="BJBANK"/><img src="<%=baseURL%>/images/paybank/BJBANK_OUT.gif" border="0"  onclick="checkedRaido('BJBANK');"/></td>	
 </tr>
 <tr>
 	<td><input type="radio" id="BJRCB" name="pay_bank" value="BJRCB"/><img src="<%=baseURL%>/images/paybank/BJRCB_OUT.gif" border="0"  onclick="checkedRaido('BJRCB');"/></td>
	<td><input type="radio" id="FDB" name="pay_bank" value="FDB"/><img src="<%=baseURL%>/images/paybank/FDB_OUT.gif" border="0"  onclick="checkedRaido('FDB');"/></td>
	<td><input type="radio" id="PSBC-DEBIT" name="pay_bank" value="PSBC-DEBIT"/><img src="<%=baseURL%>/images/paybank/PSBC_OUT.gif" border="0"  onclick="checkedRaido('PSBC-DEBIT');"/></td>
 </tr>
</table>
<script type="text/javascript">
function checkedRaido(radioId){
	document.getElementById(radioId).checked='checked';	
}
</script></td>
						</tr>
						</table>
												<!--网银(支付宝)结束-->
						</table>
						</div>
						
						<div class="div_btn">
						<!--按钮开始-->
						<table style="margin-top:0px">
						<tr class="trbtn">
						<td class="tdname" width="84px"></td><td><input type="image" src="<%=baseURL%>/images/2010btn_payfinish.jpg" /></td>
						<td>
												</td>
						</tr><!--按钮结束-->
						</table>
						</div>
						<input type="hidden" name="step" value="4" />
						</form>
					</div>
					<div class="description" >
						<div class="info">
						<!--不能为空-->
						<font face=宋体><strong>银行卡支付说明：</strong></font>
							<p><font face='宋体'>1、您必须开通了网上银行业务；</font>
							<br><font face='宋体'>2、网上银行开通办法请咨询当地所属银行；</font>
							<br><font face='宋体'>3、请您关闭所有屏蔽弹出窗口之类的功能，否则在线支付将无法继续，比如：3721、上网助手、google toolbar、alexa toolbar、baidu等；</font>
							<br><font face='宋体'>4、如果您用信用卡支付，请确认该信用卡的网上交易限额大于等于您的充值金额；</font>
							<br><font face='宋体'>5、如果有疑问，请联系我们在线客服或拨打客服电话。</font>
							<br><font face='宋体' color="Red">6、请充值时务必确认好您的充值金额准确无误后再进行充值，避免输错金额导致的失误，如因未仔细确认金额造成的充值问题，我们将一律不予处理此类退款申诉。
						</font>						</div>
					</div>
	
  </body>
</html>
