  	function change(url, obj){
    	changePage(url);
    	show(obj);
    }
  
    function changePage(url){
    	$.get(url, function(data){
      		document.getElementById("list").innerHTML = data;	
    	});
    }
    function changeUrl(id){
    	var obj = "type_" + id;
    	document.getElementById(obj).click();
    }
    
    function show(obj){ 	
    	//$(".tagList-2 a[class^='nav']").siblings("[class^='nav']").addClass("nav_off").removeClass("nav_on");
		//$(obj).addClass("nav_on").removeClass("nav_off");
    	$(".list_menu ul li a").removeClass("nav_on"); 
		$(obj).addClass("nav_on");
    }
    /*
    var Marquee2_1 = document.getElementById("patpic"); //滚动对象
	var iLineHeight2_1 = 58; //单行高度，像素
	var size_1 = ${size}/2;
	var xx_1 = 1; //每次滚动高度，像素
	function setZero_1(){
		Marquee2_1.scrollTop = 0;	
	}
	function runmcc_1() {
	Marquee2_1.scrollTop += xx_1;
		if ( Marquee2_1.scrollTop >= size_1 * iLineHeight2_1 )
		{
		window.setTimeout("setZero_1()",3000);
	}
	if ( Marquee2_1.scrollTop % iLineHeight2_1 == 0 ) {
			window.setTimeout( "runmcc_1()", 3000 );
		} else {
			window.setTimeout( "runmcc_1()", 30 );
	}
	}
	window.setTimeout( "runmcc_1()", 300);
	*/
	$(function(){
		var url = document.URL;
		var para = "";	
		if(url.lastIndexOf("?")>0){
			para = url.substring(url.lastIndexOf("=")+1,url.length);
			var string = para.split("#");
			var id =  string[0];
			$(".list_menu ul li a").removeClass("nav_on");

			$("#" + id ).addClass("nav_on");
		}
		
		$("#select2").hover(function(){
			//var height=$("#selectList2").css("height");
			//var top="-"+height;
			//$("#selectList2").css("top",top);
			$("#selectList2").css("display","block"); 
		},function(){
			$("#selectList2").fadeOut("slow");
		});	
		
	});
 