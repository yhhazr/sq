
	<ul id="photo_list">
	<#list photos  as photo>
		
		<#if photo_index < imageHost?size>
		    <#assign n=photo_index/>
		<#else>
			<#assign n=photo_index % imageHost?size/>
		</#if>
		       		
		<#if photo.thumbnail??><#--如果缩略图存在，就显示缩略图-->
		   	<li>
				<a  href="${imageHost[n]}/photo/${photo.photoName!}" class="imgListPic"><img src="${imageHost[n]}/photo/${photo.thumbnail!}" name="photo"></a>
				<div class="imgListTxt">
					<a title="${photo.title!}" href="${imageHost[n]}/photo/${photo.photoName!}">${photo.title!}</a>
				</div>
				<div class="see" style="display:none">
			       	<a href="${imageHost[n]}/photo/${photo.photoName!}" rel="clearbox[gallery=${photo.catId}]">预览
			       		<img style="display:none;" src="${imageHost[n]}/photo/${photo.photoName!}" >
			       	</a>
			       	<a href="${imageHost[n]}/photo/${photo.photoName}">下载</a>
		       	</div>
			</li>
		<#else>
		   	<li>
				<a  href="${imageHost[n]}/photo/${photo.photoName!}" class="imgListPic"><img  src="${imageHost[n]}/photo/${photo.photoName!}" name="photo"></a>
				<div class="imgListTxt">
					<a title="${photo.title!}" href="${imageHost[n]}/photo/${photo.photoName!}">${photo.title!}</a>
				</div>
				<div class="see" style="display:none">
			       	<a href="${imageHost[n]}/photo/${photo.photoName}" rel="clearbox[gallery=${photo.catId}]">预览
			       		<img style="display:none;" src="${imageHost[n]}/photo/${photo.photoName!}" >
			       	</a>
			       	<a href="${imageHost[n]}/photo/${photo.photoName!}">下载</a>
		       	</div>
			</li>
		</#if>
	</#list>
	</ul>



