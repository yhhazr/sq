function bind(id){
	var obj = document.getElementById(id);
	var imgs = obj.getElementsByTagName("img");
	for(var i = 0; i < imgs.length; i++){
		$(imgs[i]).bind("mouseenter", function(){show1(this);});
	}
}

function show1(obj){
	
	//var lx, ly;
	//var rect = obj.getBoundingClientRect();
	var cx = obj.offsetLeft;
	var cy = obj.offsetTop;
	var width = obj.getAttribute("width");
	var height = obj.getAttribute("height");
	var src = obj.getAttribute("src");
	createDiv(cx, cy, width, height, src);
	
}

function createDiv(x, y, w, h, s){
	var div = document.createElement("div");
	div.style.filter = "Alpha(opacity = 50)";
	div.innerHTML = "<a class='look' href='" + s + "' onclick=''>预览</a><a class='load' href='" + s + "'>下载</a>";
	div.style.background = "#c0c0c0";
	div.style.opacity = "0.5";
	div.id="little";
	div.style.left = x + "px";
	div.style.top = y + "px";
	div.style.width = w + "px";
	div.style.height = h + "px";
	div.style.display = "block";
	div.style.zIndex = "1000";
	div.style.position = "absolute";
	$(div).bind("mouseleave", function(){out1(this.id);});
	document.body.appendChild(div);
	//var size = img(s);
	$("#look").bind("click", function(){
		div.style.width = "100%";
		div.style.heitht = "100%";
	});
	$("#look").fancybox();
	//alert("123");
}
function out1(str){
	remove(str);
}
function remove(id){
	var dv = document.getElementById(id);
	
	document.body.removeChild(dv);
}
function show2(src){

	var bgdiv = document.createElement("div");
	bgdiv.id = "bgdiv";
	bgdiv.style.background = "#000000";
	bgdiv.style.opacity = "0.5";
	bgdiv.style.filter = "Alpha(opacity = 50)";
	bgdiv.style.width = (document.body.clientWidth - document.body.scrollLeft)+ "px";
	bgdiv.style.height = (document.body.clientHeight - document.body.scrollTop) + "px";
	bgdiv.style.left = "0px";
	bgdiv.style.top = "0px";
	bgdiv.style.display = "block";
	bgdiv.style.zIndex = "1000";
	bgdiv.style.position="absolute";
	$(bgdiv).bind("mousedown", function(){out2(this.id);});
	document.body.appendChild(bgdiv);

	var size = img(src);
	var dx = (document.body.clientWidth - size.x)/2 + document.body.scrollLeft;
	var dy = (document.body.clientHeight - size.y)/2 + document.body.scrollTop;
	var div = document.createElement("div");
	div.id = "bigImg";
	div.innerHTML = "<img src='" + src + "'/>";
	div.style.left = dx + "px";
	div.style.top = dy + "px";
	div.style.width = size.x + "px";
	div.style.height = size.y + "px";
	div.style.display = "block";
	div.style.zIndex = "1001";
	div.style.position = "absolute";
	//div.style.background = "#ffffff";
	//div.style.opacity = "1.0";
	//div.style.filter = "Alpha(opacity = 100)"
	//$(div).bind("mouseenter", function(){$(bgdiv).unbind();})
	//$(div).bind("mouseleave", function(){$(bgdiv).bind("mousedown", function(){out2(this.id);});})
	document.body.appendChild(div);
	
	var table = document.createElement("table");
	table.id="bigtab";
	div.appendChild(table);
	

}
function img(file){
	var size = {x:0, y:0};
	var img = new Image();
	img.src = file;
	size.x = img.width;
	size.y = img.height;
	return size;
}
function out2(id){
	remove(id);
	remove("bigImg");
}