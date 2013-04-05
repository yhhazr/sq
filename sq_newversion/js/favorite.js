//收藏夹
function bookmarkit(){
   
   if(window.sidebar && "object" == typeof( window.sidebar ) && "function" == typeof(window.sidebar.addPanel)){
       window.sidebar.addPanel( '《海神》印象站 -- 第七大道', "http://hs.7road.com", '' );
    }
    else if (document.all && "object" == typeof(window.external)){
       window.external.addFavorite("http://hs.7road.com",'《海神》印象站 -- 第七大道');
    } else {
        alert("您在使用webKit内核的浏览器，请使用 ctrl+D 添加收藏哦！");
    }
   return false;
}
//顶部导航图片
function menuFix(nav) {
var sfEls = document.getElementById(nav).getElementsByTagName("span");
for (var i=0; i<sfEls.length; i++) {
sfEls[i].onmouseover=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onMouseDown=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onMouseUp=function() {
this.className+=(this.className.length>0? " ": "") + "sfhover";
}
sfEls[i].onmouseout=function() {
this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"),"");
}
}
}

function menu(){

    var nowli = document.getElementById("sqPop").getElementsByTagName("span");

}
menu();
menuFix('sqPop')