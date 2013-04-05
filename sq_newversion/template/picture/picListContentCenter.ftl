
<div style="display:block;" class="imgList switchMain" id="img_list">
	<ul id="photo_list">
	<#list photos  as photo>
		
		<#if photo_index < imageHost?size>
		    <#assign n=photo_index/>
		<#else>
			<#assign n=photo_index % imageHost?size/>
		</#if>
		       		
		<#if photo.thumbnail??><#--如果缩略图存在，就显示缩略图-->
		   	<li>
				<a  href="${imageHost[n]}/photo/${photo.photoName!}" class="imgListPic"  rel="clearbox[gallery=${photo.catId}]"><img  src="${imageHost[n]}/photo/${photo.thumbnail!}" name="photo"></a>
				<div class="imgListTxt">
					<a title="${photo.title!}" href="${imageHost[n]}/photo/${photo.photoName!}">${photo.title!}</a>
				</div>
				
			</li>
		<#else>
		   	<li>
				<a  href="${imageHost[n]}/photo/${photo.photoName!}" class="imgListPic" rel="clearbox[gallery=${photo.catId}]"><img  src="${imageHost[n]}/photo/${photo.photoName!}" name="photo"></a>
				<div class="imgListTxt">
					<a title="${photo.title!}" href="${imageHost[n]}/photo/${photo.photoName!}">${photo.title!}</a>
				</div>
				
			</li>
		</#if>
	</#list>
	</ul>
</div>


<div class="blank30"></div>

<a class="enterGameBtn" href="http://sq.7road.com/serverList.html" target="_blank">进入游戏</a>
<div class="blank20"></div>
<#if (currentPage == 1 ) && (totalPage == currentPage)>
		<div class="pageNum"><a class="pager">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a class="pager">下一页&nbsp;&gt;</a></div>
	</#if>
	<#if (currentPage == 1 ) && (currentPage < totalPage)>
		<div class="pageNum"><a class="pager">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)" onclick="changePage('${catId}_${currentPage + 1}.html')" class="pager">下一页&nbsp;&gt;</a></div>
	</#if>
	<#if (currentPage >1) && (currentPage < totalPage)>
		<div class="pageNum"><a href="javascript:void(0)" onclick="changePage('${catId}_${currentPage - 1}.html')" class="pager">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)" onclick="changePage('${catId}_${currentPage + 1}.html')" class="pager">下一页&nbsp;&gt;</a></div>
	</#if>
	<#if (currentPage >1) && (currentPage == totalPage)>
		<div class="pageNum"><a href="javascript:void(0)" onclick="changePage('${catId}_${currentPage - 1}.html')" class="pager">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a class="pager">下一页&nbsp;&gt;</a></div>
	</#if>


<script type="text/javascript">
 
function changePage(url){
  $.get(url, function(data){
     	document.getElementById("downloadBox").innerHTML = data;	
    });
}
</script>