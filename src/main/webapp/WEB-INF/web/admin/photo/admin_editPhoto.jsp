<%@page import="java.io.File"%>
<%@page import="com.sz7road.web.common.listener.SystemConfig"%>
<%@page import="com.sz7road.web.model.usermanage.UserInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="/WEB-INF/tld/displaytag.tld" prefix="display"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored="false" %>

<%	
	String path = request.getContextPath();
	String userId = request.getParameter("photoId");
	String url = SystemConfig.getProperty("image.server.host");
%>
<script type="text/javascript" src="<%=path %>/static/js/jquery-1.7.1.js"></script>
<script src="<%=path %>/static/js/jquery.Jcrop.js" type="text/javascript"></script>
  <link rel="stylesheet" href="<%=path %>/static/css/jquery.Jcrop.css" type="text/css" />
<script type="text/javascript">

var width=100;
var height=100;
var marginLeft=100;
var marginTop=100;
var Ratio = 1;
function loadimg(maxWidth,maxHeight,objImg){
		var img = new Image();
img.src = objImg.src;
	var hRatio;
	var wRatio;

var w = img.width;
var h = img.height;
wRatio = maxWidth / w;
hRatio = maxHeight / h;
if (maxWidth ==0 && maxHeight==0){
Ratio = 1;
}else if (maxWidth==0){//
if (hRatio<1) Ratio = hRatio;
}else if (maxHeight==0){
if (wRatio<1) Ratio = wRatio;
}else if (wRatio<1 || hRatio<1){
Ratio = (wRatio<=hRatio?wRatio:hRatio);
}
if (Ratio<1){
w = w * Ratio;
h = h * Ratio;
}
objImg.height = h;
objImg.width = w;
	}
	 jQuery(window).load(function(){

      // Create variables (in this scope) to hold the API and image size
      var jcrop_api, boundx, boundy;
      
      jQuery('#target').Jcrop({
        onChange: updatePreview,
        onSelect: updatePreview,
        aspectRatio: 1.10
      },function(){
        // Use the API to get the real image size
        var bounds = this.getBounds();
        boundx = bounds[0];
        boundy = bounds[1];
        // Store the API in the jcrop_api variable
        jcrop_api = this;
      });

      function updatePreview(c)
      {
        if (parseInt(c.w) > 0)
        {
          var rx = 162 / c.w;
          var ry = 148 / c.h;

          $('#preview').css({
            width: Math.round(rx * boundx) + 'px',
            height: Math.round(ry * boundy) + 'px',
            marginLeft: '-' + Math.round(rx * c.x) + 'px',
            marginTop: '-' + Math.round(ry * c.y) + 'px'
          });
          width = Math.round(c.w/Ratio);
          height = Math.round(c.h/Ratio);
          marginLeft = Math.round(c.x/Ratio);
          marginTop = Math.round(c.y/Ratio);
        }
      };

    });
    function saveThumbnail(photoId){
    	$.post("/game7road/thumbnailManage/saveThumbnail.action",{photoId:photoId,width:width,height:height,marginLeft:marginLeft,marginTop:marginTop},function(data){
    		if(data.result == "true"){
    			alert("保存成功！");
    			location.reload(false);
    		}else{
    			alert("保存失败!");
    		}
    	});
    }
    function deleteThumbnail(photoId){
    	$.post("/game7road/thumbnailManage/deleteThumbnail.action",{photoId:photoId},function(data){
    		if(data.result == "true"){
    			alert("删除成功！");
    			location.reload(false);
    		}else{
    			alert("删除失败!");
    		}
    	});
    }
</script>

<a href="/game7road/photoManage/uploadPhoto.action?catId=${photo.catId}"><h2>点击返回相册</h2></a>

<table class="line_table" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
	
	
  	<div>
		<div style="float:left;">
			<img onload="loadimg(800,500,this)" id="target" src="<%out.print(url);%>/photo/${photo.photoName }" />
		</div>
		<div style="float:left;margin-left:80px">
			<div style="margin-left:80px">预览</div>
		
			<div style="width:162px;height:148px;overflow:hidden;">
           	 	<img src="<%out.print(url);%>/photo/${photo.photoName }" id="preview" alt="Preview" class="jcrop-preview" />    	
       	  	</div>		
			<div style="margin-left:55px"><input type="button" value="保存缩略图" onclick="saveThumbnail(${photo.photoId})"></div>
			<div style="margin-left:80px">
				缩略图：
			</div>
				<s:if test="%{#attr.photo.thumbnail==null}">
					无
				</s:if> 
				<s:else>
				<div>
				<img style="width: 162px;height: 148px;" src="<%out.print(url);%>/photo/${photo.thumbnail }">
				<div style="margin-left:55px"><input type="button" value="删除缩略图" onclick="deleteThumbnail(${photo.photoId})"></div>
				</div>
				</s:else>
			
		</div>
	</div>
	
	<s:form name="editPhotoSubmit" namespace="/photoManage" action="editPhotoSubmit" method="post" theme="simple">
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoId" /><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="photo.photoId" cssClass="editbox4" size="20" readonly="true" />
			</td>
			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoName" /><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
			<s:hidden name="catId" value="%{photo.catId}"></s:hidden>
				<s:textfield name="photo.photoName" cssClass="editbox4" size="20" readonly="true"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photo.photoName</s:param>
					</s:fielderror>
				</span>
			</td>
			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<font>图片标题</font><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="photo.title" cssClass="editbox4" size="20" readonly="true"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photo.title</s:param>
					</s:fielderror>
				</span>
			</td>
			
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.photoCatName"/><s:text name="admin.common.colon" />
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:textfield name="photoCat.catName" cssClass="editbox4" size="20"  readonly="true"/>
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photoCat.catName</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		
		
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
				<s:text name="admin.photo.show"/><s:text name="admin.common.colon"/>
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="32%" height="30" bgcolor="#f2f2f2">
				<s:select list="#request.photoShowMap" name="photo.photoShow" listKey="key" listValue="value" value="photo.photoShow" />
				<span class="login_txt_bt">
					<s:fielderror cssStyle="color:red">
						<s:param>photo.photoShow</s:param>
					</s:fielderror>
				</span>
			</td>
		</tr>
		<tr>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="right"
				class="left_txt2">
			</td>
			<td width="3%" bgcolor="#f2f2f2">
				&nbsp;
			</td>
			<td width="20%" height="30" bgcolor="#f2f2f2" align="left"
				class="left_txt2">
				<s:submit key="admin.photoManage.editPhoto" name="Submit" cssClass="button" theme="simple" />
			</td>
		</tr>
	</s:form>
	</tbody>
</table>