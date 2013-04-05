package  {
	
	import flash.display.MovieClip;
	import flash.display.Loader;
	import flash.display.SimpleButton;
	import flash.display.DisplayObjectContainer;
	import flash.display.LoaderInfo;
	import flash.display.*;
	import flash.net.URLRequest;
	import flash.net.URLLoader;
	import flash.net.navigateToURL;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	
	
	public class main extends MovieClip {
	
	//参数
	public var paramObj:Object;
	//XML
	private var mXML:XML;
	//加载swf的信息
	private var swfCounts:int = 0;				//XML中需要加载的swf的个数
	private var swfLoadedCounts:int = 0;		//XML中已经加载的swf的个数
	//当前层的信息
	private var curLay:int = 0;					//当前加载的层数
	private var curLoadSum:int = 0;				//当前层中已经加载swf个数
	private var curSum:int = 0;					//当前层中需要加载的swf总个数
	//被选中的导航按钮
	private var radioBtn:String;
	private var showBtnArray:Array = [];
	
	//ImageBtn 的ID
	private var imgBtnID:int = 0;
	
	private var layerArray:Array = new Array();
	
		public function main() 
		{
			// constructor code
			//舞台参数设置
			//...
			stage.showDefaultContextMenu = false;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			
			var param:Object = root.loaderInfo.parameters;
			if(param["radio"]!=null)
			{
				radioBtn = param["radio"];
			}
			loadXMLData("config.xml");
		}
		
		//加载xml数据
		function loadXMLData(path:String):void
		{
			var xLoader:URLLoader = new URLLoader();
			xLoader.addEventListener(Event.COMPLETE,completeLoadXML);
			xLoader.load(new URLRequest(path));
		}
		
		function completeLoadXML(e:Event):void
		{
			mXML = XML(e.target.data);
			
			//开始读取按钮信息
			var xmlListBtn:XMLList = mXML.Buttons.Btn;
			
			for(var i:int = 0; i < xmlListBtn.length(); i++)
			{
				var button:SimpleButton = getButtonByName(this, mXML.Buttons.Btn.@id[i]);
				if(button != null)
				{
					if(button.name != radioBtn)
					{
						button.addEventListener(MouseEvent.CLICK, onClick);
						if(mXML.Buttons.Btn.@movieclip[i] != "")
						{
							button.addEventListener(MouseEvent.MOUSE_OVER, onOver);
							button.addEventListener(MouseEvent.MOUSE_OUT, onOut);
						}
					}
					else
					{
						var index = getIndexByBtnName(mXML, radioBtn);
						var mc:MovieClip = getMcByName(this, mXML.Buttons.Btn.@movieclip[index]);
						if(mc != null)
						{
							//var i:int = getIndexByBtnName(mXML, radioBtn);
							if("" == mXML.Buttons.Btn.@over[index])
							{
								mc.gotoAndPlay("over");
							}
							else
							{
								mc.gotoAndPlay(mXML.Buttons.Btn.@over[index]);
							}
						}
					}
				}
				else
				{
					trace("按钮未找到:" +mXML.Buttons.Btn.@id[i]);
				}
			}
			//加载其他
			addShowBtn();								//添加显示按钮数组
			//开始读取加载swf的信息
			var xmlListSwf:XMLList = mXML.swfs.swf;
			swfCounts = xmlListSwf.length();			//需要加载的swf的总数
			trace (swfCounts);
			for (var j:int = 0; j < swfCounts; j++)
			{
				var order:int = int(xmlListSwf[j].@order);
				if(layerArray.indexOf(order) == -1)
				{
					layerArray.push(order);
				}
			}
			
			layerArray.sort();			//对数组进行排序
			
			if(curLay < layerArray.length)
			{
				trace("*****开始加载swf*****");
				loadSource(curLay);
		  	}
		}
		
		public function loadSource(i:int):void
		{
			trace("-----====开始加载第"+i+"层====-----");
			for(var j:int = 0; j < swfCounts; j++)
			{
				if(i == int(mXML.swfs.swf[j].@order))
				{
					curLoadSum++;
					var loader = new Loader();
					loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loadcomplete);
					loader.load(new URLRequest(mXML.swfs.swf[j]));
				}
			}
		}
		
		public function addShowBtn():void
		{
			var xmlList:XMLList = mXML.Buttons.Btn;
			for(var i:int = 0; i < xmlList.length(); i++)
			{
				showBtnArray.push(xmlList[i].@movieclip.toString());
			}
		}
		
		public function loadImgSource(i:int):void
		{		
			var loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loadImgcomplete);
			loader.load(new URLRequest(mXML.Buttons.Btn[i]));
		}
		
		private function loadImgcomplete(e:Event):void
		{
			var loader:Loader = e.target.loader as Loader;
			loader.removeEventListener(Event.COMPLETE, loadImgcomplete);
			var spSwf:MovieClip = loader.content as MovieClip;
			addChild(spSwf);

			//停止所有MC
			//spSwf.addEventListener(MouseEvent.CLICK, onMouseClick);
			//stopAll(stage, spSwf);
		}

		/*public function onMouseClick(e:MouseEvent):void
		{
			var swf:MovieClip =  e.target as MovieClip;
			trace ("swf = " + swf);
			if(swf == null)
			{
				startAll(stage);
			}
		}*/
		
		public function loadcomplete(e:Event):void
		{
			var loader:Loader = e.target.loader as Loader;
			var lf:LoaderInfo = e.target as LoaderInfo;
			var swfName:String = lf.url.substr(lf.url.lastIndexOf("/")+1);
			swfLoadedCounts++;
			var index:int = getIndexBySWFName(mXML, swfName);
			trace("获得" + swfName + "的index: " + index);
			
			var myLayer:String = mXML.swfs.swf.@layer[index];
			var myContainer:String = mXML.swfs.swf.@container[index];
			var myX:String = mXML.swfs.swf.@x[index];
			var myY:String = mXML.swfs.swf.@y[index];
			trace(swfName + "的层数是: " + myLayer+ " 容器是： "+myContainer + " 坐标："+ myX+ " "+myY);
			//加载到容器上
			if(myContainer == "" || myContainer == "stage")
			{//加载到舞台
				var mc:MovieClip = new MovieClip();
				mc.addChild(loader);
				//放入舞台（层数）
				if(myLayer != "")
				{
					addChildAt(mc, int(myLayer));
				}
				else
				{
					addChild(mc);
				}
			}
			else
			{//加载到容器
				if(myContainer.lastIndexOf(".") != -1)
				{
					myContainer = myContainer.substr(myContainer.lastIndexOf(".")+1);
				}
				var mc2:MovieClip = getMcByName(this, myContainer);
				if(mc2 != null && myLayer != "")
				{
					mc2.addChildAt(loader, int(myLayer));
				}
				else if(mc != null)
				{
					mc2.addChild(loader);
				}
			}	
			//设置mc中swf的坐标
			if(myX != "" && myY != "")
			{
				loader.x = int(myX);
				loader.y = int(myY);
			}
			//设置mc的坐标
			/*if(myX != "" && myY != "")
			{
				mc.x = int(myX);
				mc.y = int(myY);
			}*/
			if(curLoadSum != swfCounts)
			{
				curLay += 1;
				loadSource(curLay);	
			}
		}
		
		function onClick(e:MouseEvent):void
		{
			
			var str:String = e.currentTarget.name;
			var index:int = getIndexByBtnName(mXML, str);

			var strPath:String = mXML.Buttons.Btn[index].substr(0,4);
			trace ("btn " + strPath);
			
			if (strPath != "http")
			{
				if (strPath == "jmp:")
				{
					//ExternalInterface.call(mXML.Buttons.Btn[index].substr(4,10), index + 1);
					ExternalInterface.call(mXML.Buttons.Btn[index].substr(4,20), index + 1);
					//trace (mXML.Buttons.Btn[index].substr(4,20) + " " + index);
				}
				else
				{
					//加载图片
					var btn:SimpleButton = e.currentTarget as SimpleButton;
					imgBtnID = getIndexByBtnName(mXML, btn.name);
					loadImgSource(imgBtnID);
				}
			}
			else
			{
				openWindow(mXML.Buttons.Btn[index]);
				//navigateToURL(new URLRequest(mXML.Buttons.Btn[index]), "_top");
				trace(index +" 点击："+mXML.Buttons.Btn[index]);
			}
		}
		
		public static function stopAll(doc:DisplayObjectContainer, swf:MovieClip):void
        {
			if(doc.numChildren <= 0)
				return ;
						
            for (var i:int = 0; i < doc.numChildren; i++)
            {
                var mc:MovieClip = doc.getChildAt(i) as MovieClip;
                if (mc != null && mc != swf)
                {
                    mc.stop();
                    stopAll(mc as DisplayObjectContainer, swf);
                }
            }
        }
		
		public function startAll(doc:DisplayObjectContainer):void
        {
			if(doc.numChildren <= 0)
				return ;
            for (var i:int = 0; i < doc.numChildren; i++)
            {
            	var mc:MovieClip = doc.getChildAt(i) as MovieClip;
            	if (mc != null)
            	{
					if(showBtnArray.lastIndexOf(mc.name) == -1)
						mc.play();
                    startAll(mc as DisplayObjectContainer);
                }
            }
         }
		
		function onOver(e:MouseEvent):void
		{
			var str:String = e.currentTarget.name;
			var index = getIndexByBtnName(mXML, str);
			var mc:MovieClip = getMcByName(this, mXML.Buttons.Btn.@movieclip[index]);
			if(mc != null)
			{
				var i:int = getIndexByBtnName(mXML, str);
				if("" == mXML.Buttons.Btn.@over[i])
				{
					mc.gotoAndPlay("over");
				}
				else
				{
					mc.gotoAndPlay(mXML.Buttons.Btn.@over[i]);
				}
			}
		}
		
		function onOut(e:MouseEvent):void
		{
			var str:String = e.currentTarget.name;
			
			var index = getIndexByBtnName(mXML, str);
			var mc:MovieClip = getMcByName(this, mXML.Buttons.Btn.@movieclip[index]);
			if(mc != null)
			{
				var i:int = getIndexByBtnName(mXML, str);
				if("" == mXML.Buttons.Btn.@out[i])
				{
					mc.gotoAndPlay("out");
				}
				else
				{
					mc.gotoAndPlay(mXML.Buttons.Btn.@out[i]);
				}
			}
		}

		public static function getButtonByName(doc:DisplayObjectContainer, BtnName:String):SimpleButton
        {
			if(doc.numChildren <= 0)
				return null;
						
            for (var i:int = 0; i < doc.numChildren; i++)
			{
            	var btn:SimpleButton = doc.getChildAt(i) as SimpleButton;
                if (btn != null && btn.name == BtnName)
				{//trace("===============>  "+ btn.name);
                    return btn;  
                }
            }
			for (var j:int = 0; j < doc.numChildren; j++)
			{
				var mc:MovieClip = doc.getChildAt(j) as MovieClip;
            	if (mc != null )
				{
					btn = getButtonByName(mc, BtnName);
					if(btn != null)
					{
						return btn;
					}
				}
			}
			return null;
        }
		
		public static function getMcByName(doc:DisplayObjectContainer, mcName:String):MovieClip
        {
			if(doc.numChildren <= 0)
			{
				return null;
			}
            for (var i:int = 0; i < doc.numChildren; i++)
            {
				var mc:MovieClip = doc.getChildAt(i) as MovieClip;
                if (mc != null )
                {
					if(mc.name == mcName)
					{
						return mc;
					　}
					else
					{
						mc = getMcByName(mc as DisplayObjectContainer, mcName);
						if(mc != null)
						{
							return mc;
						}
					}
				}
			}
			return null;            
        }
		
		public function getIndexBySWFName(myXML:XML, swfName:String):int
		{
			if(myXML == null)
				 return -1;
			
			var swfCount:int = swfCounts;
			for(var index:int = 0; index < swfCount; index++){
				var str = mXML.swfs.swf[index];
				str = str.substr(str.lastIndexOf("/")+1);
				if(swfName == str){
					return index;
				}
			}
			return -1;
		}
		
		public function getIndexByBtnName(myXML:XML, btnName:String):int
		{
			if(myXML == null)
				 return -1;
			
			var xmlList:XMLList = myXML.Buttons.Btn;
			var btnCount:int = xmlList.length();
			for(var index:int = 0; index < btnCount; index++){
				if(btnName == mXML.Buttons.Btn.@id[index]){
					return index;
				}
			}
			return -1;
		}
				
		/**
		 * Open a new browser window and prevent browser from blocking it.
		 *
		 * @param url        url to be opened
		 * @param window     window target
		 * @param features   additional features for window.open function
		 */
		public static function openWindow(url:String, window:String = "_blank", features:String = ""):void
		{
			url = url.split("%").join("%25").split("\\").join("%5c").split("\"").join("%22").split("&").join("%26"); //encode
			//url = url.replace(/%22/g, "\"").replace(/%5c/g, "\\").replace(/%26/g, "&").replace(/%25/g, "%"); //decode
			var browserName:String = getBrowserName();
			//Debug.trace("openWindow: " + browserName + " " + url+ " "+window);
			if (browserName == "Firefox")
			{
				//ExternalInterface.call(WINDOW_OPEN_FUNCTION, url, window, features);
				ExternalInterface.call("function setWMWindow() {window.open('" + url + "');}");
			}
			//If IE, 
			else if (browserName == "IE")
			{
				ExternalInterface.call("function setWMWindow() {window.open('" + url + "');}");
			}
			//If Safari 
			else if (browserName == "Safari")
			{
				navigateToURL(new URLRequest(url), window);
			}
			//If Opera 
			else if (browserName == "Opera")
			{
				navigateToURL(new URLRequest(url), window);
			}
			//Otherwise, use Flash's native 'navigateToURL()' function to pop-window. 
			//This is necessary because Safari 3 no longer works with the above ExternalInterface work-a-round.
			else
			{
				navigateToURL(new URLRequest(url), window);
			}
		}
		/**
		 * return current browser name.
		 */
		private static function getBrowserName():String
		{
			var browser:String;
			
			//Uses external interface to reach out to browser and grab browser useragent info.
			var browserAgent:String = "";
			if (ExternalInterface.available)
			{
				browserAgent = ExternalInterface.call("function getBrowser(){return navigator.userAgent;}");
			}
			//Determines brand of browser using a find index. If not found indexOf returns (-1).
			if (browserAgent != null && browserAgent.indexOf("Firefox") >= 0)
			{
				browser = "Firefox";
			}
			else if (browserAgent != null && browserAgent.indexOf("Safari") >= 0)
			{
				browser = "Safari";
			}
			else if (browserAgent == null || browserAgent.indexOf("MSIE") >= 0)
			{
				browser = "IE";
			}
			else if (browserAgent != null && browserAgent.indexOf("Opera") >= 0)
			{
				browser = "Opera";
			}
			else
			{
				browser = "Undefined";
			}
			return (browser);
		}
	}
	
}
