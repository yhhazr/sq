<div class="switchMain" style="display:block">	
	
	<ul class="list" id="list">

		<#list newsList as news>
			<li <#if news_index lt 3>class="hot"</#if> >
				<span class="newsDate">${news.modifyDate?string("yyyy-MM-dd")}</span>
				<span class="newsListDot">&gt;</span>
				<a href="detail/${news.typeId}_${news.newsId}.html#A0" title="${news.artTitle}">${news.artTitle}</a>
		    </li>
		    <#if news.typeId==5>
		    	<style type="text/css">
		    	.pageNewsList ul li .newsDate{color:#12c9e9;}
		    	.pageNewsList ul li.hot a, .pageNewsList ul li.hot .newsListDot {color: #12c9e9;}
		    	.pageNewsList ul li a,.pageNewsList ul li{ color:#12c9e9;}
		    	</style>
		    </#if>
			
		</#list>
	</ul>
</div>

	<#if (currentPage == 1 ) && (totalPage == currentPage)>
		<div class="pageNum"><a  href="javascript:void(0)">首页</a><a  href="javascript:void(0)">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)">下一页&nbsp;&gt;</a><a  href="javascript:void(0)" onclick="changePage('list_${typeId}_${totalPage}.html')">尾页</a></div>
	</#if>
	<#if (currentPage == 1 ) && (currentPage < totalPage)>
		<div class="pageNum"><a href="javascript:void(0)" onclick="changePage('list_${typeId}_1.html');">首页</a><a href="javascript:void(0)">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)" onclick="changePage('list_${typeId}_${currentPage + 1}.html')" >下一页&nbsp;&gt;</a><a href="javascript:void(0)" onclick="changePage('list_${typeId}_${totalPage}.html')">尾页</a></div>
	</#if>
	<#if (currentPage >1) && (currentPage < totalPage)>
		<div class="pageNum"><a href="javascript:void(0)" onclick="changePage('list_${typeId}_1.html');">首页</a><a href="javascript:void(0)" onclick="changePage('list_${typeId}_${currentPage - 1}.html')" href="javascript:void(0)">&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)" onclick="changePage('list_${typeId}_${currentPage + 1}.html')" href="javascript:void(0)">下一页&nbsp;&gt;</a><a href="javascript:void(0)" onclick="changePage('list_${typeId}_${totalPage}.html')">尾页</a></div>
	</#if>
	<#if (currentPage >1) && (currentPage == totalPage)>
		<div class="pageNum"><a href="javascript:void(0)" onclick="changePage('list_${typeId}_1.html');">首页</a><a href="javascript:void(0)" onclick="changePage('list_${typeId}_${currentPage - 1}.html')" >&lt;&nbsp;上一页</a>&nbsp;&nbsp;${currentPage}/${totalPage}&nbsp;&nbsp;<a href="javascript:void(0)">下一页&nbsp;&gt;</a><a href="javascript:void(0)">尾页</a></div>
	</#if>

<script type="text/javascript">
function toIndexPage(){
	
}
function toLastPage(){
	;
}
</script>