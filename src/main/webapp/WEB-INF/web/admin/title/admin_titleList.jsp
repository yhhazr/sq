<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<s:form action="editTitleSubmit" namespace="/titleManage" method="post">
		<table>
			<tr>
				<td class="left_txt2">官网首页:</td>
				<td><s:textfield name="items[0]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">新闻中心:</td>
				<td><s:textfield name="items[1]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">赛事信息:</td>
				<td><s:textfield name="items[7]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">独家呈现:</td>
				<td><s:textfield name="items[17]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">玩家分享:</td>
				<td><s:textfield name="items[22]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">周边商城:</td>
				<td><s:textfield name="items[26]" size="18" maxlength="50"></s:textfield></td>
				
			</tr>
			<tr>
				<td rowspan="9"></td>
				<td rowspan="9"></td>
				<td class="left_txt2">最新资讯:</td>
				<td><s:textfield name="items[2]" size="18" maxlength="50" ></s:textfield></td>
				<td class="left_txt2">赛事日程:</td>
				<td><s:textfield name="items[8]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">精彩视频:</td>
				<td><s:textfield name="items[18]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">游戏攻略:</td>
				<td><s:textfield name="items[23]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">淘宝商城:</td>
				<td><s:textfield name="items[27]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td class="left_txt2">官方新闻:</td>
				<td><s:textfield name="items[3]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">赛事预告:</td>
				<td><s:textfield name="items[9]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">精美壁纸:</td>
				<td><s:textfield name="items[19]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">优秀战队:</td>
				<td><s:textfield name="items[24]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">新品预告:</td>
				<td><s:textfield name="items[28]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td class="left_txt2">官方公告:</td>
				<td><s:textfield name="items[4]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">赛事回顾:</td>
				<td><s:textfield name="items[10]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">版本专题:</td>
				<td><s:textfield name="items[20]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">原创漫画:</td>
				<td ><s:textfield name="items[25]" size="18" maxlength="50"></s:textfield></td>
				<td rowspan="7"></td>
				<td rowspan="7"></td>
			</tr>
			<tr>
				<td class="left_txt2">赛事新闻:</td>
				<td><s:textfield name="items[5]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">赛事新闻:</td>
				<td><s:textfield name="items[11]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">专属皮肤:</td>
				<td ><s:textfield name="items[21]" size="18" maxlength="50"></s:textfield></td>
				<td rowspan="6"></td>
				<td rowspan="6"></td>
			</tr>
			<tr>
				<td class="left_txt2">媒体活动:</td>
				<td ><s:textfield name="items[6]" size="18" maxlength="50"></s:textfield></td>
				<td class="left_txt2">赛事奖励:</td>
				<td><s:textfield name="items[12]" size="18" maxlength="50"></s:textfield></td>
				<td rowspan="5"></td>
				<td rowspan="5"></td>
				
			</tr>
			<tr>
				<td rowspan="4"></td>
				<td rowspan="4"></td>
				<td class="left_txt2">战队资料:</td>
				<td><s:textfield name="items[13]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td class="left_txt2">女子表演赛:</td>
				<td><s:textfield name="items[14]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td class="left_txt2">参赛选手:</td>
				<td><s:textfield name="items[15]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td class="left_txt2">比赛视频:</td>
				<td><s:textfield name="items[16]" size="18" maxlength="50"></s:textfield></td>
			</tr>
			<tr>
				<td colspan="12" align="center">
				
					<s:submit key="admin.common.update" method="editTitleSubmit" name="Submit" onclick="confirm('确认修改？')"></s:submit>
				</td>
			</tr>
			<tr>
				<td colspan="12" align="center">
		<%	
				if(request.getAttribute("editSuccess") != null){
					out.print("<span class='login_txt_bt'>修改成功！</span>");
					} 
		%>
				</td>
			</tr>
		</table>
	</s:form>
	
	
</body>
</html>