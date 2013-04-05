

<div style="display:block;" class="imgList switchMain" id="img_list">
	<ul>
  <#list videos as video>
    
    <li>
      <a  href="javascript:void(0);" onclick="videoplayer('${video.videoId}')" class="imgListPic"><img  src="${imageHost[0]}/${video.videoPicName!}"  onmouseover="showBon(this)"></a>
      <div class="imgListTxt">
        <a title="${video.videoTitle!}" href="">${video.videoTitle!}</a>
      </div>
    </li>
  </#list>
</ul>


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
function videoplayer(s){
	var video = "v_p" + s + ".html";
	window.open("${serverHost}/picture/" + video + "#AP");
}
function showBon(obj){
	
}
</script>
</div>

