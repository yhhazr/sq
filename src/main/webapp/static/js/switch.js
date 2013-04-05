// 2012-6-19 weixiang.zhong



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
new Index_Tab(".navTab a",".switchMain",".switchWrap1|0");

