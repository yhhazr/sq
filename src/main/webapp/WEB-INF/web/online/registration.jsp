<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=path %>/static/style/formValidatorStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=path%>/static/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=path%>/static/js/validator.js"></script>
<style type="text/css">
tr{height:30px;}
.STYLE1 {
	font-size: 12px;
	font-weight: bold;
}
</style>
<style type="text/css">
body{background: url(http://www.37wan.com/images/gr_bg.jpg) no-repeat center 0;}
#gr_main{margin:auto;width:666px;overflow:hidden;}
#gr_logo{float:left;background:url(http://www.7road.com/images/logo.jpg) no-repeat;margin-left:5px;width:666px;height:80px;overflow:hidden;margin-top:74px;}
#gr_frame{width:666px;overflow:hidden;padding-top:5px;}
#gr_frame .frame_top{width:666px;height:50px;background:url(http://www.37wan.com/images/gr_frame_top.jpg) no-repeat;overflow:hidden;}
#gr_frame .content{width:626px;background:url(http://www.37wan.com/images/gr_frame_bg.jpg) repeat-y;overflow:hidden;padding:5px 20px;}
#gr_frame .frame_btm{width:666px;height:30px;background:url(http://www.37wan.com/images/gr_frame_btm.jpg) no-repeat;overflow:hidden;}
.li_spet{width:600px;height:24px;overflow:hidden;margin:5px 0 10px;padding-left:10px;border-bottom:1px solid #aaa;line-height:24px;font-weight:bold;}
.gr_list{margin:auto;width:620px;height:34px;line-height:34px;font-size:12px;}
.gr_list dl{float:left;width:270px;height:34px;line-height:34px;}
.gr_list dt{float:left;width:90px;overflow:hidden;text-align:right;}
.gr_list dd{float:left;width:180px;text-align:left;}
.gr_info{float:left;width:350px;height:24px;}
.gr_botton_div{width:600px;overflow:hidden;line-height:30px;margin:auto;text-align:center;}
.gr_botton{width:110px;margin:auto;height:31px;}
</style>
<title>Insert title here</title>
</head>
<body>
<div id="gr_main">
	<div id="gr_logo"></div>
    <div id="gr_frame">
        <div class="frame_top"></div>
        <div class="content">
<form action="registrationSubmit.action" method="post" name="form1" id="form1" >
  
  <table border="0px" style="font-size:12px" width="630px">

	 <tr> 
      <td align="right"><span style="color:red;">*</span>&nbsp;用户账号：</td>
      <td><s:textfield name="userName" onblur="userNameOnBlur()" onfocus="userNameOnFocus()" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" cssClass="g-ipt" size="20"></s:textfield></td>
      <td><div id="userNameTip" style="width:280px"><s:if test="#request.isUserNameError=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>用户名正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.userName!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.userName[0]}</SPAN></P><script>document.getElementById("userName").className = "g-ipt-err"</script></s:if>
		</div>
		</td>
    </tr>

  
  
    <tr> 
      <td align="right"><span style="color:red;">*</span>&nbsp;登录密码：</td>
      <td><s:password name="password1" onblur="password1OnBlur()" onfocus="password1OnFocus()" cssClass="g-ipt" size="20"></s:password></td>
      <td><div id="password1Tip" style="width:280px"><s:if test="#request.isPassword1Error=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.password1!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.password1[0]}</SPAN></P><script>document.getElementById("password1").className = "g-ipt-err"</script></s:if>
		</div>
		</td>
    </tr>
    <tr>
      <td align="right"><span style="color:red;">*</span>&nbsp;确认密码：</td>
      <td><s:password name="password2" onblur="password2OnBlur()" onfocus="password2OnFocus()" cssClass="g-ipt" size="20"></s:password></td>
      <td><div id="password2Tip" style="width:280px"><s:if test="#request.isPassword2Error=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>密码正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.password2!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.password2[0]}</SPAN></P><script>document.getElementById("password2").className = "g-ipt-err"</script></s:if>
		</div>
		</td>
    </tr>
    
    <tr> 
      <td align="right"><span style="color:red;">*</span>&nbsp;电子邮箱：</td>
      <td><s:textfield name="email" onblur="emailOnBlur()" onfocus="emailOnFocus()" cssClass="g-ipt" size="20"></s:textfield></td>
      <td>
      	<div id="emailTip" style="width:280px">
      		<s:if test="#request.isEmailError=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>邮箱正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.email!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.email[0]}</SPAN></P><script>document.getElementById("email").className = "g-ipt-err"</script></s:if>
		</div>
	</td>
    </tr>
     <tr> 
      <td align="right"><span style="color:red;">*</span>&nbsp;真实姓名：</td>
      <td><s:textfield name="realName" onblur="realNameOnBlur()" onfocus="realNameOnFocus()" cssClass="g-ipt" size="20"></s:textfield></td>
      <td>
      	<div id="realNameTip" style="width:280px">
      		<s:if test="#request.isRealNameError=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>姓名正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.realName!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.realName[0]}</SPAN></P><script>document.getElementById("realName").className = "g-ipt-err"</script></s:if>
		</div>
	</td>
    </tr>
     <tr> 
      <td align="right"><span style="color:red;">*</span>&nbsp;身份证号码：</td>
      <td><s:textfield name="idCard" onblur="idCardOnBlur()" onfocus="idCardOnFocus()" cssClass="g-ipt" size="20"></s:textfield></td>
      <td>
      	<div id="idCardTip" style="width:280px">
      		<s:if test="#request.isIdCardError=='no'"><P class='noticeWrap'><B class='ico-succ'></B><SPAN class=txt-succ>身份证号码正确</SPAN></P></s:if>	
			<s:if test="#request.fieldErrors.idCard!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.idCard[0]}</SPAN></P><script>document.getElementById("idCard").className = "g-ipt-err"</script></s:if>
		</div>
	</td>
    </tr>
     <tr>
      <td align="right"><span style="color:red;">*</span>&nbsp;验证码：</td>
      <td><s:textfield name="verifyCode" onblur="verifyCodeOnBlur()" onfocus="verifyCodeOnFocus()" cssClass="g-ipt" size="10"></s:textfield>
      <img id="verifyCodeImg" style="vertical-align:middle;cursor:pointer;" src="captcha.do" width="90px" height="25px" title="Change verify image" onClick="javascript:this.src='captcha.do?'+(new Date().getTime());"></td>
      <td><div id="verifyCodeTip" style="width:280px">	
			<s:if test="#request.fieldErrors.verifyCode!=null"><P class='noticeWrap'><B class='ico-warning'></B><SPAN class=txt-err>${fieldErrors.verifyCode[0]}</SPAN></P><script>document.getElementById("verifyCode").className = "g-ipt-err"</script></s:if>
		</div>
		</td>
    </tr>
    <tr>
    	<td></td>
    	<td colspan="2"><input type="button" value="提交" onClick="checkAll()"></td>
    </tr>
    
 
  </table>
  </form>
   </div>
        <div class="frame_btm"></div>
    </div>
    </div>


</body>
</html>