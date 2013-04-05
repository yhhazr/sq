// JavaScript Document
//weixiang.zhong
//2012-07-17



//幻灯
var t = n = 0, count;
$(document).ready(function(){    
	count=$("#banner_list a").length;
	$("#banner_list a:not(:first-child)").hide();
	//$("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt'));
	//$("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")});
	$("#banner li").click(function() {
		var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4
		n = i;
		if (i >= count) return;
		//$("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt'));
		//$("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")})
		$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
		document.getElementById("banner").style.background="";
		$(this).toggleClass("on");
		$(this).siblings().removeAttr("class");
	});
	t = setInterval("showAuto()", 4000);
	$("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval("showAuto()", 4000);});
	
})

function showAuto()
{
	n = n >=(count - 1) ? 0 : ++n;
	$("#banner li").eq(n).trigger('click');
}


//新闻切换
function Index_Tab(tab,tabcnt,context){
	function dochange(elem){
		$(tab,elem[0].c).removeClass('on');
		$(tabcnt,elem[0].c).hide();
		elem.addClass('on');
		$(tabcnt+':eq('+elem[0].z+')',elem[0].c).show();
	}
	var ctxt = context.split(",");
	for( var k = 0; k < ctxt.length; k++ ){
		var c = ctxt[k].split('|');
		$( tab,c[0] ).each( function(i){
			$(this)[0].z = i;
			$(this)[0].c = c[0];
			if( c[1] == '0' ){
				$(this).mouseover(function(){
					dochange($(this));
				});
			}else{
				$(this).mouseover(function(){
					dochange($(this));
				});
			}
		} );
	}
}
new Index_Tab(".switchTab span",".switchMain",".switch1|0,.switch2|0,.switch3|0,.switch4|0,.switch5|0");





