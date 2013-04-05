   var now_top = 0;
   var top_ses = [0,660,1326,2940,3768,4575]
   //目前导航+目前状态
   function find_nav(nav){
       $(nav).addClass('on').siblings('nav').removeClass('on');
   }
   
   function to_first(){
       $('body,html').animate({scrollTop:top_ses[1]},500);
       find_nav('#nav1');
   }
  
   function block_one(){  
        find_nav('#nav1');
   }
   
   function block_two(){
        find_nav('#nav2');     
   }
   
   function block_three(){
        find_nav('#nav3')  
   }
   
   function block_fourth(){
        find_nav('#nav4')  
   }
   
   function block_five(){
        find_nav('#nav5')  
   }
   //移动时执行
   function scrolling(){
        now_top = $(document).scrollTop();
        
		if(now_top > top_ses[5]){
           block_five();
        }
        if(now_top > top_ses[4] && now_top <= top_ses[5]){
           block_fourth();
        }
		if(now_top > top_ses[3] && now_top <= top_ses[4]){
           block_three();
        }
        if(now_top > top_ses[2] && now_top <= top_ses[3]){
           block_two();
        }
        if(now_top >top_ses[1] && now_top <= top_ses[2]){
           block_one();
        };

		if(now_top < 200){
			$('nav').removeClass('on');
		}else{
			
		} 
        
        
		if(($.browser.msie && $.browser.version == 6.0)){
			$('aside').css({position:'absolute'}).animate({top:now_top+100},50);
		}else{
			$('aside').css({position:'fixed',top:100});
		}
        
   }
   
   $(document).ready(function(){
        //滚动导航和元素漂浮
        $(window).scroll(scrolling);
        
        //lazyload
        $('.lazy').lazyload({
           threshold:200,
		   skip_invisible : false
        });
           
        $('.back_index1').click(function(){
            $('body,html').animate({'scrollTop':0},500)
        })
        
        $("aside #nav1,.prev3").click(function(){
            to_first();
        })
        $("aside #nav2,.next2,.prev4").click(function(){
            $('body,html').animate({'scrollTop':top_ses[2]+100},500);
            find_nav(this);
        })
        $("aside #nav3,.next3,.prev5").click(function(){
            $('body,html').animate({'scrollTop':top_ses[3]+100},500);
            find_nav(this);
        })
        $("aside #nav4,.next4,.prev6").click(function(){
            $('body,html').animate({'scrollTop':top_ses[4]+100},500);
            find_nav(this);
        })
		$("aside #nav5,.next5").click(function(){
            $('body,html').animate({'scrollTop':top_ses[5]+100},500);
            find_nav(this);
        })
        
   })


//收藏夹
function bookmarkit(){
   
   if(window.sidebar && "object" == typeof( window.sidebar ) && "function" == typeof(window.sidebar.addPanel)){
       window.sidebar.addPanel( '神曲贵宾俱乐部_第七大道《神曲》官方网站', "http://sq.7road.com/20120925/index.htm", '' );
    }
    else if (document.all && "object" == typeof(window.external)){
       window.external.addFavorite("http://sq.7road.com/20120925/index.htm",'神曲贵宾俱乐部_第七大道《神曲》官方网站');
    } else {
        alert("您在使用webKit内核的浏览器，请使用 ctrl+D 添加收藏哦！");
    }
   return false;
}

