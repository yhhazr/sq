<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!--  meta http-equiv="refresh" content="0.1;url=http://www.dreamershop.com">-->
<body>
<form name=loading>
　<p align=center> <font color="#0066ff" size="2">正在进入，请稍等</font><font color="#0066ff" size="2" face="Arial">...</font>
　　<input type=text name=chart size=46 style="font-family:Arial; font-weight:bolder; color:#0066ff; background-color:#fef4d9; padding:0px; border-style:none;">
　　
　　<input type=text name=percent size=47 style="color:#0066ff; text-align:center; border-width:medium; border-style:none;">

　</p>
</form>
<p align="center"> 如果您的浏览器不支持跳转,<a style="text-decoration: none" href="javascript:;" onclick="location = '../dirty/queryDirty.action'"><font color="#FF0000">请点这里</font></a>.</p>
</body>
<script type="text/javascript">
var bar=0;
var line="||";
var amount="||";
count();
function count(){
	bar=bar+2;
	amount =amount + line;
	document.loading.chart.value=amount;
	document.loading.percent.value=bar+"%";
	if (bar<99)
		{setTimeout("count()",30);}
	else
		{window.location = "../dirty/queryDirty.action";}
}
</script>