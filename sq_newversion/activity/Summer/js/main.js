// JavaScript Document
//weixiang.zhong
//2012-07-25


//切换
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
new Index_Tab(".switchTab span",".switchMain",".switch1|0");
$(document).ready(function(){
	var type = getUrlParam("type");
		$(".switchTab #span"+type).addClass("on");
		$("#div"+type).show();
		
});
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}