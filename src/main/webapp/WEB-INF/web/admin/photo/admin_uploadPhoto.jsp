<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib prefix="pg" uri="/WEB-INF/pager-taglib.tld"%>
<%@ page import="com.sz7road.web.model.photomanage.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.pagination.PaginationResult"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String contextPath = request.getContextPath();
	PaginationResult<Photo> pageationResult = (PaginationResult<Photo>)request.getAttribute("pageationResult");
	request.setAttribute("totalItems", new Integer(pageationResult.getTotal()));
	request.setAttribute("maxPageItems", new Integer(SystemConfig.getProperty("admin.search.page.size")));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<style type="text/css">
.image{border:1px solid #000;-moz-box-shadow:3px 3px 4px #000;-webkit-box-shadow:3px 3px 4px #000;box-shadow:3px 3px 4px #000;background:#fff;filter:progid:DXImageTransform.Microsoft.Shadow(Strength=4,Direction=135,Color='#000000');}
</style>
<style type="text/css">
#face{
 width:780px;
 border:1px solid #b4b4b4;
 height:600px;
 margin:0 0 0 50px;
 background:#ffffff;
 overflow: auto;
 }
 #faceul{
 margin:0 0 0 0;
 width:720px;
 }
 #s{
 float:left;
 margin:20px 0 0 10px;
 height:148px;
 width:163px; 
 }
 #ss{
 float:left;
 border: solid #b4b4b4;
 margin:20px 0 0 10px;
 height:135px;
 width:160px; 
 }
 #sss{
 float:left;
 height:140px;
 width:160px; 
 }
 #faceul img{
 
 }
#custom-queue {
  border: 1px solid grey;
  height: 500px;
margin-bottom: 10px;
  width: 400px;
  overflow: auto;
}			
 

</style>

<link href="<%=path %>/static/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=path %>/static/uploadify/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/static/uploadify/swfobject.js"></script>
<script type="text/javascript"
	src="<%=path %>/static/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var catId = getCatId();
		var PLAYER_PHOTO_MAX_SIZE = 200 * 1024;
		$("#uploadify").uploadify({
			'uploader' : '<%=path %>/static/uploadify/uploadify.swf', //是组件自带的flash，用于打开选取本地文件的按钮
			'script' : 'upload.action',//处理上传的路径，这里使用Struts2是XXX.action
			'scriptData' :{'catId':$('#catId').val(), 'photoShow':$('#photoShow').val()},
			'cancelImg' : '<%=path %>/static/uploadify/cancel.png',//取消上传文件的按钮图片，就是个叉叉
			'folder' : 'uploads',//上传文件的目录
			'fileDataName' : 'uploadify',//和input的name属性值保持一致就好，Struts2就能处理了
			'queueID' : 'fileQueue',
			'auto' : false,//是否选取文件后自动上传
			'multi' : true,//是否支持多文件上传
			'simUploadLimit' : 10,//每次最大上传文件数
			'queueSizeLimit' : 50,
			'sizeLimit': 3072000,
			'removeCompleted' : true,
			'buttonText' : 'BORWSE',//按钮上的文字
			'displayData' : 'percentage',//有speed和percentage两种，一个显示速度，一个显示完成百分比
			'fileDesc' : '支持格式:jpg/gif/jpeg/png/bmp.', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
			'fileExt' : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',//允许的格式
			'onAllComplete' : function(event, queueID, fileObj, response, data) {
				//$("#result").html(response);//显示上传成功结果
				//setInterval("showResult()", 2000);//两秒后删除显示的上传成功结果
				location.href="<%=basePath%>photoManage/uploadPhoto.action?catId="+$('#catId').val();
			},
			'onSelect': function(e, queueId, fileObj){
				if(catId == 94 && fileObj.size > PLAYER_PHOTO_MAX_SIZE) {
					setTimeout(function(){cancelFile(queueId)},1000);
//					setTimeout(cancelFile(queueId), 300);
					alert('您上传的玩家靓照大于' + PLAYER_PHOTO_MAX_SIZE / 1024 + 'KB，请重新上传。');
					
				}	            
			}			
		});
	});
	function cancelFile(queueId) {
		$('#uploadify').uploadifyCancel(queueId);
	}
	function uploadFile() {//上传文件
		$('#uploadify').uploadifySettings('scriptData',{'catId':$('#catId').val(), 'photoShow':$('#photoShow').val()});
		jQuery('#uploadify').uploadifyUpload();
	}
	function clearFile() {//清空所有上传队列
		jQuery('#uploadify').uploadifyClearQueue();
	}
	function loadPhoto(ImgD){
		var image=new Image();
		image.src=ImgD.src;
		if(image.width>0 && image.height>0){
			if(image.width/image.height>= 160/120){
				if(image.width>160){
					ImgD.width=160;
					ImgD.height=(image.height*160)/image.width;
				}else{
					ImgD.width=image.width;
					ImgD.height=image.height;
				}
				ImgD.alt=image.width+"×"+image.height;
			}
			else{
				if(image.height>120){
					ImgD.height=120;
					ImgD.width=(image.width*120)/image.height;
				}else{
					ImgD.width=image.width;
					ImgD.height=image.height;
				}
				ImgD.alt=image.width+"×"+image.height;
			}
		}
	}
	function deletePhoto(photoId,catId){
		if(window.confirm("<s:text name="admin.photo.delete.message"></s:text>")){
			location.href="<%=path%>/photoManage/deletePhoto.action?photoId="+photoId+"&catId="+catId;
			return false;
			}
		else
			return false;
	}
	function deleteChecked(){
		var checkBox = document.getElementsByTagName("input");
		var catId = document.getElementsByName("catId")[0].value;
		var ids = "";
		for(var i = 0 ; i < checkBox.length ; i++){
			if(checkBox[i].checked == true){
				ids = ids + checkBox[i].id + ",";
			}
		}
		
		if(window.confirm("<s:text name="admin.common.deleteChecked.message"></s:text>"))
			location.href="<%=path%>/photoManage/deletePhoto.action?photoId="+ids+"&catId="+catId;
		else
			return;	
	}
	/* function checkAll(){
		var checkBoxs = document.getElementsByTagName("input");
		var j = 0;
		for(var i = 2; i < checkBoxs.length; i++){
			if(checkBoxs[i].checked == true){
				j++;
			}
		}
		if(j == (checkBoxs.length - 2)/2){
			for(var k = 2 ; k < checkBoxs.length; k++){
			checkBoxs[k].checked = false;
			}
		}else{
			for(var i = 2; i < checkBoxs.length; i++){
				checkBoxs[i].checked = true;
				}
		}
	} */
	function mouseover(name){
		var i = document.getElementsByName(name);
		if(i.length == 6){
			if(i[5].value == 'false'){
				i[0].className='image';
				i[0].style.backgroundColor="#f2f2f2";
				i[1].className = "";
				i[2].style.display = "";
				i[3].style.display = "";
				i[4].style.display = "";
			}
		}else{
			var div = document.getElementById(name);
			if(i[4].value == 'false'){
				div.className='image';
				div.style.backgroundColor="#f2f2f2";
				i[0].className = "";
				i[1].style.display = "";
				i[2].style.display = "";
				i[3].style.display = "";
			}
		}
	}
	function mouseout(name){
		var i = document.getElementsByName(name);
		if(i.length == 6){
			if(i[5].value == 'false'){
				i[0].className = "";
				i[0].style.backgroundColor="";
				i[1].className = "image";
				i[2].style.display = "none";
				i[3].style.display = "none";
				i[4].style.display = "none";
			}
		}else{
			var div = document.getElementById(name);
			if(i[4].value == 'false'){
				div.className="";
				div.style.backgroundColor="";
				i[0].className = "image";
				i[1].style.display = "none";
				i[2].style.display = "none";
				i[3].style.display = "none";
			}
		}
	}
	function clickDiv(name){
		var i = document.getElementsByName(name);
		if(i.length == 6){
			if(i[5].value == 'false'){
				i[0].className = "image";
				i[1].className = "";
				i[2].style.display = "";
				i[3].style.display = "";
				i[4].style.display = "";
				i[2].checked = true;
				i[4].value = true;
				i[5].value = true;
			}else{
				i[0].className = "";
				i[1].className = "image";
				i[2].style.display = "none";
				i[3].style.display = "none";
				i[4].style.display = "none";
				i[2].checked = false;
				i[4].value = false;
				i[5].value = false;
			}
		}else{
			var div = document.getElementById(name);
			if(i[4].value == 'false'){
				div.className = "image";
				i[0].className = "";
				i[1].style.display = "";
				i[2].style.display = "";
				i[3].style.display = "";
				i[1].checked = true;
				i[3].value = true;
				i[4].value = true;
			}else{
				div.className = "";
				i[0].className = "image";
				i[1].style.display = "none";
				i[2].style.display = "none";
				i[3].style.display = "none";
				i[1].checked = false;
				i[3].value = false;
				i[4].value = false;
			}
		}
	}
	
	//获得参数catId的值
	function getCatId() {
		var value = "";
		var query=location.search.substring(1);//获取查询串     
		var pairs=query.split("&");//获得参数数组     
		for(var i=0;i<pairs.length;i++){     
			var pos=pairs[i].indexOf('=');//查找name=value  
			if(pos==-1) continue;//如果没有找到就跳过     
	        var argname=pairs[i].substring(0,pos);//提取name   
			if(argname=="catId") {
				value=pairs[i].substring(pos+1);//提取value
				break;
			}
		}
		if(value=="") {
			value = $.cookie('site');
			if(value == null) {
				value = "";
			}
		}
		return value;     
	}

</script>

</head>
<%	
		List<Photo> photoList = (ArrayList)request.getAttribute("photoList");
		
 %>
<body>
	<table class="line_table" border="0"  cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="400" bgcolor="#f2f2f2" rowspan="2">
			<input type="file" name="uploadify" id="uploadify"/>
				<s:hidden name="catId"></s:hidden>	
				<div id="custom-queue">
				<div id="fileQueue"></div>
				</div>
				<p>
					<s:text name="admin.photo.show" /><s:text name="admin.common.colon"/>
					<s:select name="photoShow" id="photoShow" list="#request.photoShowMap" listKey="key" listValue="value" />
				</p>
				<p>
					<s:submit key="admin.photo.beginUpload" onclick="uploadFile()"></s:submit>
					<s:submit key="admin.photo.cancelAllUpload" onclick="clearFile()"></s:submit>
				</p> 
			</td>
			<td bgcolor="#f2f2f2" align="right">
				<s:submit key="admin.common.deleteChecked" onclick="deleteChecked()"></s:submit>
			</td>
		</tr>
		<tr>
			<td width="800" bgcolor="#f2f2f2">
				<div id="face">
				<ul id="faceul">
				<%	
					if(photoList.size() > 0){
						for(int i = 0; i < photoList.size(); i++){
							out.print("<div id='s'><div onclick='clickDiv("+photoList.get(i).getPhotoId()+")' onmouseout='mouseout("+photoList.get(i).getPhotoId()+")' onmouseover='mouseover("+photoList.get(i).getPhotoId()+")' id='"+photoList.get(i).getPhotoId()+"' style='float:left;height:140px;width:160px; cursor: pointer;'><div style='width: 160px;height: 120px;'>");
							out.print("<img class='image' onload='loadPhoto(this);' name='"+photoList.get(i).getPhotoId()+"' src='"+photoList.get(i).getPhotoName()+"'/></div>");
							out.print("<div style='width: 160px;height: 17px; margin-bottom: 0;'>");
							out.print("<input align='top' style='margin-right: 106px;display: none;' id='"+photoList.get(i).getPhotoId()+"' name='"+photoList.get(i).getPhotoId()+"' type='checkbox'><a href='photoManage/editPhoto.action?photoId="+photoList.get(i).getPhotoId()+"' title='编辑'><img name='"+photoList.get(i).getPhotoId()+"' style='border:0;display: none;' src='"+path+"/static/images/260.png'></a><a href='#' title='删除' onClick='return deletePhoto("+photoList.get(i).getPhotoId()+","+photoList.get(i).getCatId()+")'>");
							out.print("<img name='"+photoList.get(i).getPhotoId()+"' id='delete' style='border:0;display: none;' align='top' src='"+path+"/static/uploadify/cancel.png'></a><input type='hidden' value=false name='"+photoList.get(i).getPhotoId()+"' /></div></div></div>");
						}
					}
				 %>
				 </ul>
				 </div>			
			</td>
		</tr>
		
		<tr>
			<td>
			<p></p>
			<p></p>
				
			</td>
		</tr>
	</table>
	
</body>
	
</html>
<script type="text/javascript">
	highlightTableRows("photo");  

	function deletePhotoInfo(photoId){
		if(window.confirm("<s:text name="admin.common.delete.message"><s:param>"+ photoId + "</s:param></s:text>"))
			location.href="<%=contextPath%>/photoManage/deletePhoto.action?photoId="+photoId;
		else
			return;
	}
</script>