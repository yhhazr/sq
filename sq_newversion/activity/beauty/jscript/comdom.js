var _dom = document;
var Dom={
		//获取对象源
		evntObj:function(){
				var evt = window.event || arguments.callee.caller.arguments[0];
				var srcObj = evt.srcElement || evt.target;
				return srcObj;
			},
		//返回对象的宽高
		getDH:function(obj){
				var domB = obj;
				if(domB == document){domB = _dom.body || _dom.documentElement;}
				return {
					ofH:Math.max(domB.clientHeight,domB.offsetHeight),
					ofW:Math.max(domB.clientWidth,domB.offsetWidth)
					}
			},
		//显示弹出层,id为加载层的id
		showPop:function(obj){
				Dom.shade(1);
				<!--公用最外层弹出框-->		
				var _pop="";
				var warHml='<div class="titHd"><h2 id="popTit">我要留言</h2><a class="closePo" href="javascript:void(0)" onclick="Dom.closePop()" title="关闭">关闭</a></div>';
				if(Dom.popLayer) {
						_pop=Dom.popLayer;
						}
				else{
						var wraPop=document.createElement("div");
						wraPop.id="pop";
						wraPop.innerHTML=warHml;
						document.body.appendChild(wraPop);
						_pop=wraPop;
					};
				
				if(obj){
					var tit = obj.cT ? obj.cT : "温馨提示";
					Dom.getObj("#popTit").innerHTML = tit;
					Dom.addClass(_pop,obj.cN.substring(1));
					var _popCt = (obj.cN == "")? Dom.getObj("#dataTipS") : Dom.getObj(obj.cN);
					//传入的是文本
					if(obj.conT){
						var _tipCt = _dom.createElement("div");
						_tipCt.innerHTML = obj.conT;
						var _p = _dom.createElement("p");
						_p.className = "orderDv";
						_p.innerHTML = '<a href="javascript:void(0);" onclick="Dom.closePop()">确 定</a>';
						_popCt.appendChild(_tipCt);
						_popCt.appendChild(_p);
						}
					_pop.appendChild(_popCt); 
					_popCt.style.display = "block"; 
					Dom.popLayct = _popCt;
				}
				_pop.style.display = "block";
				_pop.style.marginTop = -(Dom.getDH(_pop).ofH/2) + "px";
				_pop.style.marginLeft = -(Dom.getDH(_pop).ofW/2) + "px";
				Dom.popLayer = _pop;
			},
		//关闭弹出层
		closePop:function(){
				if(Dom.popLayct){
					Dom.popLayct.style.display = "none";
					Dom.getObj("#dataTipS").innerHTML = "";
					Dom.popLayer.removeChild(Dom.popLayct);
					_dom.body.appendChild(Dom.popLayct);
					Dom.popLayct = null;
					}
				if(Dom.popLayer)Dom.popLayer.style.display = "none";
				Dom.shade(0);
			},
		//遮罩1为显示,0为隐藏
		shade:function(pd,obj){
				var _targ = Dom.shadeLayer;
				if(pd == 1){
						if(_targ){_targ.style.display = "block";}
						else{
								var shaDv = _dom.createElement("div");
								shaDv.style.cssText = "position:absolute;top:0;left:0;width:100%;background-color:#000;opacity:0.4;filter:alpha(opacity=40);z-index:98;";
								var tagObj = obj? obj : _dom.body; 
								tagObj.appendChild(shaDv);
								shaDv.style.height = Dom.getDH(tagObj).ofH + "px";
								Dom.shadeLayer = shaDv;
							}
					}else if(pd == 0){
							if(_targ){_targ.style.display = "none";}
						}else{return;}
				
			},
		//根据ID class target object返回	对象//目前只简单支持document级别查找
		getObj:function(str){
				var strType = Object.prototype.toString.apply(str);
				if(strType == "[object Object]") return str;
				if(strType != "[object String]") return;
				switch (str.charAt(0)){
						case "#":
							str = str.substring(1);
							return _dom.getElementById(str);
							break;
						case ".":
							str = str.substring(1);
							var obList = _dom.getElementsByTagName("*");
							var obLen = obList.length;
							var obArr = [];
							for(var i = 0; i < obLen; i++){
									if(obList[i].className.indexOf(str) != -1){
										 obArr.push(obList[i]);	
										}
								}
							return obArr;
							break;
						default :
							if(!isNaN(str.charAt(0))) return;
							return _dom.getElementsByTagName(str);
							break;
					}
				
			},
		//添加类
		addClass:function(obj,ncla)
			{
				var classes = obj.className.split(" "), clsIndx = Dom.hasClass(obj,ncla);
				if(clsIndx == -1){classes.push(ncla);}
				obj.className = classes.join(" ");	
			},
		//判断是否存在类
		hasClass:function(obj,cla)
			{
				var classes = obj.className.split(" ");
				for(var i in classes){if(classes[i] == cla)return i;}
				return -1;
			},
		//删除类
		removeClass:function(obj,ocla)
			{
				var classes = obj.className.split(" "), clsIndx = Dom.hasClass(obj,ocla);
				if(clsIndx != -1){classes.splice(clsIndx,1);}
				obj.className = classes.join(" ");
			},
		//创建XHR
		creXHR:function(){return window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP");},
		//绑定事件
		addEventList:function(obj,type,fun,argsOb)
				{
					var aFun = fun;
					if(argsOb){aFun = function(e){fun.call(this,argsOb);}}
					if(window.addEventListener){
						obj.addEventListener(type,aFun,false);	
					}else if(window.attachEvent){obj.attachEvent("on"+type,fun);
					}else{obj["on" + type] = aFun;}
				},
		//删除事件
		removeEventList:function(obj,type,fun)
			{
				if(window.removeEventListener){obj.removeEventListener(type,fun,false);
				}else if(window.detachEvent){obj.detachEvent("on" + type,fun);
				}else{obj["on" + type] = null;}
			},
		//模拟placeholder,目前只做了适应input
		placehold:function(){
				if("placeholder" in _dom.createElement("input")) {return;};//是否支持
				var inputList = Dom.getObj("input");
				var inputLen = inputList.length;
				var placStr = "";
				for(var i = inputLen-1; i >= 0; i--){
						placStr = inputList[i].getAttribute("placeholder")
						if(placStr !== null){
								inputList[i].value = placStr;
								Dom.addClass(inputList[i],"grayCo");
								inputList[i].onfocus = function(){
									Dom.removeClass(this,"grayCo");
									if(this.value == this.getAttribute("placeholder")) this.value = "";
									}
								inputList[i].onblur = function(){if(this.value == "") {this.value = this.getAttribute("placeholder"); Dom.addClass(this,"grayCo");}}
							}
					}
				
				
			},
		//设置不同屏幕下内容居中显示
		pageCenter:function(id){
				if(document.getElementById(id)==null) return;
				var domT=document.body || document.documentElement;
				var domW=Math.max(domT.clientWidth,domT.offsetWidth);//当前文档的宽度
				var flW=document.getElementById(id).clientWidth;//当前flash宽度
				if(flW > domW){
					domT.scrollLeft=(flW-domW)/2;
					}
			}
			
	}
/*--初始轮播结构--*/	
var imgFc=function imgInit(id,ar){
		var wrapObj = document.getElementById(id);
			if(wrapObj == null) return;
			var pT = document.createElement("p");
			var st = [], li = "";
			var ul = document.createElement("ul");
			var imgLen = ar.length;
			for(var i = 0; i < imgLen; i++){
					var stx = document.createElement("span");
					var s_text = document.createTextNode("●");
					stx.appendChild(s_text);
					st.push(stx);
					pT.appendChild(stx);
					li += "<li><img alt='' src="+ar[i]+" /></li>"
				}
			ul.innerHTML = li;
			pT.className = "control";
			wrapObj.appendChild(ul);
			wrapObj.appendChild(pT);
			var imgOb = document.getElementById(id).getElementsByTagName("img")[0];
			var loadEvt = (imgOb.onreadystatechange === undefined) ? "onload" : "onreadystatechange";
				imgOb[loadEvt]=function(){
						if(this.readyState == "complete" || this.complete == true){
							wrapObj.style.width = this.offsetWidth + "px";
							wrapObj.style["marginLeft"] = Math.round((wrapObj.parentNode.offsetWidth-this.offsetWidth)/2) + "px";
							ul.style.width = this.offsetWidth*imgLen + "px";
							ul.style.height = this.offsetHeight + "px";
							//图片加载完后
							//ob移动的对象,st菜单对象,away每次移动的距离
							focusInit(ul,st,imgOb.offsetWidth);
							}
				}			
	}
var imgAr = ["images/examp1.jpg","images/examp2.jpg","images/examp3.jpg"];
imgFc("imgFocs",imgAr);
//共用轮播方法
function focusInit(mOb,aImg,away)
{
	var aBtn =aImg || mOb.getElementsByTagName("img");
	var away=away || aBtn[0].offsetWidth; 
	var oBox = mOb.parentNode;//当前需要移动对象的父对象
	var oList = mOb;//当前需要移动的对象
	var timer = playTimer = null;//定时器
	var index = i = 0;//当前第几个
	cutover();
	//按钮点击切换
	for (i = 0; i < aBtn.length; i++)
	{
		aBtn[i].index = i;
		aBtn[i].onclick = function ()
		{
			index = this.index;
			cutover();
		}
	}
	function cutover()
	{
		for (i = 0; i < aBtn.length; i++) aBtn[i].className = "";
		aBtn[index].className = "current";			
		startMove(-(index * away));
	}
	function next()
	{
		(index == (aBtn.length - 1)) ? index = 0 :index++;
		cutover();
	}
	playTimer = setInterval(next, 4000);
	
	oBox.onmouseover = function (){clearInterval(playTimer)};
	oBox.onmouseout = function (){playTimer = setInterval(next, 4000)};
	
	function startMove(iTarget)
	{
		if(timer) clearTimeout(timer);
		var xpos = parseInt(oList.style["marginLeft"] || 0);
		if(xpos == iTarget){clearTimeout(timer);}
		if(xpos < iTarget){ xpos += Math.ceil((iTarget-xpos)/2);}
		if(xpos > iTarget){ xpos -= Math.ceil((xpos-iTarget)/2);}
		oList.style["marginLeft"] = xpos + "px";
		timer = setTimeout(function (){startMove(iTarget)}, 100)
	}
};


