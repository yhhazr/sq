//延迟加载
function delay_js(url) {
	var type = url.split("."), file = type[type.length - 1];
	if (file == "css") {
		var obj = document.createElement("link"), lnk = "href", tp = "text/css";
		obj.setAttribute("rel", "stylesheet");
	} else
		var obj = document.createElement("script"), lnk = "src", tp = "text/javascript";
	obj.setAttribute(lnk, url);
	obj.setAttribute("type", tp);
	file == "css" ? document.getElementsByTagName("head")[0].appendChild(obj)
			: document.body.appendChild(obj);
	return obj;
};

// 切换
function addload(func) {
	var old = window.onload;
	if (typeof window.onload != "function") {
		window.onload = func;
	} else {
		window.onload = function() {
			old();
			func();
		};
	}
};
function swap(elem) {
	var area = document.getElementById(elem), lnks = area
			.getElementsByTagName("strong"), divs = area
			.getElementsByTagName("div"), cnt = [], swaps = [], old = 0, nm = elem
			.slice(0, 1);
	for ( var i = 0, x = 0; i < divs.length; i++) {
		if (divs[i].id) {
			cnt[x] = divs[i];
			x++;
		}
	}
	for (i = 0; i < cnt.length; i++) {
		swaps[i] = lnks[i];
		swaps[i].cnt = i;
		swaps[i].onmouseover = function() {
			area.id = nm + (this.cnt + 1);
			var cls = cnt[old].className;
			cnt[old].className = "none";
			cnt[this.cnt].className = cls;
			old = this.cnt;
		};
	}
};

// 显示隐藏
function medshow() {
	var s1 = document.getElementById("s1");
	s1.onmouseover = function() {
		s1.className += "on";
	};
	s1.onmouseout = function() {
		s1.className = "rm";
	};
};
//测试
function change(elem) {
	var area = document.getElementById(elem), 
	lnks = area.getElementsByTagName("strong"), 
	//lnks = document.getElementsByName("strong");
	//divImg = document.getElementsByName("img"),
	//divLang = document.getElementsByName("lang"),
	divs = area.getElementsByTagName("div"),
	swaps = [],
	img =[],
	lang = [],
	oldi = 0,
	oldl = 0,
	nm = elem.slice(0,1);		
	//alert(divs.length);
	for(var i = 0 ,x=0;i<=lnks.length;i++){
		if(divs[i].id){
			img[x] = divs[i];
			lang[x] = divs[lnks.length+x+1];
			x++;
		}
	}
	//alert(lang[0].id);
	for(i=0;i<lnks.length;i++){
		swaps[i] = lnks[i];
		swaps[i].img = i;
		swaps[i].lang = i;
		swaps[i].onmouseover = function(){
			area.id = nm + (this.img + 1);
			var clsi = img[oldi].className;
			var clsl = lang[oldl].className;
			img[oldi].className = "none";
			lang[oldl].className = "none";
			img[this.img].className = clsi;
			lang[this.lang].className = clsl;
			oldi = this.img;
			oldl = this.lang;
		};
	}
};
// 延迟加载配置
function delay() {
	change("s1");
	swap("n1");
	swap("i1");
	swap("m1");
	swap("v1");
	medshow();
};
//FLASH
function flash(){
var menu=document.getElementById("menu"),size='width="960" height="400"',doc='http://xj.qq.com/web201110/flash/mainmenu.swf';
var swf='<object id="FlashID" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" '+size+'><param name="movie" value="'+doc+'" /><param name="wmode" value="transparent" /> <param name="allowScriptAcces" value="always"/><param name="quality" value="high" /><embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+doc+'" '+size+' wmode="transparent" /></object>';
menu.innerHTML=swf;var btn=document.getElementById("btn"),size='width="260" height="380"',doc='http://xj.qq.com/web201110/flash/btn.swf';var swf='<object id="FlashID" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" '+size+'><param name="movie" value="'+doc+'" /><param name="wmode" value="transparent" /> <param name="allowScriptAcces" value="always"/><param name="quality" value="high" /><embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+doc+'" '+size+' wmode="transparent" /></object>';btn.innerHTML=swf;
var cloud=document.getElementById("cloud"),size='width="485" height="100"',doc='http://xj.qq.com/web201110/flash/entry.swf';var swf='<object id="FlashID" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" '+size+'><param name="movie" value="'+doc+'" /><param name="wmode" value="transparent" /> <param name="allowScriptAcces" value="always"/><param name="quality" value="high" /><embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+doc+'" '+size+' wmode="transparent" /></object>';cloud.innerHTML=swf;
var portal=document.getElementById("portal"),size='width="245" height="215"',doc='http://xj.qq.com/web201110/flash/gameportal_ad.swf';var swf='<object id="FlashID" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" '+size+' base="/web201110/flash/"><param name="movie" value="'+doc+'" /><param name="wmode" value="transparent" /> <param name="allowScriptAcces" value="always"/><param name="base" value="/web201110/flash/"/><param name="quality" value="high" /><embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+doc+'" '+size+' wmode="transparent" base="/web201110/flash/" /></object>';
portal.innerHTML=swf;
}