<div class="blank30"></div>
<a class="enterGameBtn" href="http://sq.7road.com/serverList.html" target="_blank">进入游戏</a>
<div class="blank20"></div>
<div class="pageNum">
    

    <a href="javascript:void(0)" onclick="gotopage(1);">首页</a>
    <span id="pageInfo"></span>
    <a href="javascript:void(0)" onclick="gotopage(${totalPage})">尾页</a>
</div>
<script type="text/javascript"> 
	var totalpage,pagesize,cpage,count,curcount,outstr; 
	//初始化 
	cpage = 1; 
	
	totalpage = ${totalPage};//总页数
	
	pagesize = 9; 
	outstr = ""; 
	function gotopage(target){     
		changePage(target);
   		cpage = target;        //把页面计数定位到第几页 
 		setpage(); 
   		 //reloadpage(target);    //调用显示页面函数显示第几页,这个功能是用在页面内容用ajax载入的情况 
	} 
	function setpage(){ 
 	   if(totalpage<=5){        //总页数小于十页 
	        for (count=1;count<=totalpage;count++) {
	            if(count!=cpage){ 
                	outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'>"+count+"</a>&nbsp;&nbsp;"; 
            	}else{ 
                	outstr = outstr + "<span class='current' >"+count+"</span>&nbsp;&nbsp;"; 
            	} 
        	} 
    	} 
    	if(totalpage>5){        //总页数大于十页 
        	if(parseInt((cpage-1)/5) == 0){             
           	 for (count=1;count<=5;count++) 
            {    if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'>"+count+"</a>&nbsp;&nbsp;"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>&nbsp;&nbsp;"; 
                } 
            } 
            outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'> next </a>&nbsp;&nbsp;"; 
        } 
        else if(parseInt((cpage-1)/5) == parseInt(totalpage/5)) 
        {     
            outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+(parseInt((cpage-1)/5)*5)+")'>previous</a>&nbsp;&nbsp;"; 
            for (count=parseInt(totalpage/5)*5+1;count<=totalpage;count++) 
            {    if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'>"+count+"</a>&nbsp;&nbsp;"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>&nbsp;&nbsp;"; 
                } 
            } 
        } 
        else 
        {     
            outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+(parseInt((cpage-1)/5)*5)+")'>previous</a>&nbsp;&nbsp;"; 
            for (count=parseInt((cpage-1)/5)*5+1;count<=parseInt((cpage-1)/5)*5+5;count++) 
            {         
                if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'>"+count+"</a>&nbsp;&nbsp;"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>&nbsp;&nbsp;"; 
                } 
            } 
            outstr = outstr + "<a href='javascript:void(0)' onclick='gotopage("+count+")'> next </a>&nbsp;&nbsp;"; 
        } 
    }     
    document.getElementById("pageInfo").innerHTML = "<span id='info'>" + outstr + "</span>&nbsp;&nbsp;"; 
    outstr = ""; 
} 
function changePage(value){
	var url = "${serverHost}/picture/${catId}_" + value + ".html";
	if(value == 1){
        if("${catId}" == "v"){
            $.get(url, function(data){
                $("#downloadBox").html(data);
                CB_Init();
            });
        }else{
            $.get(url, function(data){
                $("#downloadBox").html(data);
                CB_Init();
            });
        }
	}else{
		$.get(url, function(data){
			$("#img_list").html(data);
			CB_Init();
		});
	}
}
setpage();    //调用分页 
 
</script>