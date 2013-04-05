package  {
	
	import flash.display.MovieClip;
	import flash.display.Loader;
	import flash.display.SimpleButton;
	import flash.display.DisplayObjectContainer;
	import flash.display.LoaderInfo;
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
	
		public function main() 
		{
			// constructor code
			//舞台参数设置
			//...
			
			var param:Object = root.loaderInfo.parameters;
			if(param["radio"]!=null){
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
			for(var i:int = 0; i < mXML.Buttons.@count; i++)
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
								if("" == mXML.Buttons.Btn.@over[index]){
									mc.gotoAndPlay("over");
								}else{
									mc.gotoAndPlay(mXML.Buttons.Btn.@over[index]);
								}
						}
					}
				}
			}
			//开始读取加载swf的信息
			swfCounts = 0;//需要加载的swf的总数
			for(var i:int = 0;i<mXML.swfs.@layer;i++)
			{
				var count:int = int(mXML.swfs.swf.@count[i]);
				trace("第"+i+"层有"+count+"个swf");
				for(var j:int = 0; j < count; j++)
				{
					trace(mXML.swfs.swf.path[swfCounts] + "层数：" + mXML.swfs.swf.path.@layer[swfCounts]);
					swfCounts++;
				}
			}
		  	if(curLay < mXML.swfs.@layer){
				trace("*****开始加载swf*****");
				loadSource(curLay);
		  	}
		}
		
		public function loadSource(i:int):void
		{
			trace("-----====开始加载第"+i+"层====-----");
			curLoadSum = 0;//开始加载当前层，把个数归零
			curSum = mXML.swfs.swf[i].@count;
			trace("当前需要加载的swf个数为：" + curSum);
			//if(map[i])
		  	{
				for(var j:int = swfLoadedCounts; j < swfLoadedCounts+curSum; j++)
				{
					//trace("加载第 " + j + "个：" + map[i][j]);
					trace("加载第 " + j + "个：" + mXML.swfs.swf.path[j]);
					var loader = new Loader();
					loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loadcomplete);
					loader.load(new URLRequest(mXML.swfs.swf.path[j]));
				}
		 	 }
		}
		
		public function loadcomplete(e:Event):void
		{
			var loader:Loader = e.target.loader as Loader;
			var lf:LoaderInfo = e.target as LoaderInfo;
			var swfName:String = lf.url.substr(lf.url.lastIndexOf("/")+1);
			//trace("当前完成加载的对象：" + swfName);
			curLoadSum++;
			swfLoadedCounts++;
			var index:int = getIndexBySWFName(mXML, swfName);
			trace("获得" + swfName + "的index: " + index);
			var myLayer:String = mXML.swfs.swf.path.@layer[index];
			var myContainer:String = mXML.swfs.swf.path.@container[index];
			var myX:String = mXML.swfs.swf.path.@x[index];
			var myY:String = mXML.swfs.swf.path.@y[index];
			trace(swfName + "的层数是: " + myLayer+ " 容器是： "+myContainer + " 坐标："+ myX+ " "+myY);
			//加载到容器上
			if(myContainer == "" || myContainer == "stage"){//加载到舞台
				var mc:MovieClip = new MovieClip();
				mc.addChild(loader);
				//放入舞台（层数）
				if(myLayer != ""){
					addChildAt(mc, int(myLayer));
				}else{
					addChild(mc);
				}
			}else{//加载到容器
				if(myContainer.lastIndexOf(".") != -1)
				{
					myContainer = myContainer.substr(myContainer.lastIndexOf(".")+1);
				}
				var mc:MovieClip = getMcByName(this, myContainer);
				if(mc != null && myLayer != ""){
					mc.addChildAt(loader, int(myLayer));
				}else if(mc != null){
					mc.addChild(loader);
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
			
			//trace("当前加载了："+ curLoadSum+"个，总共："+ curSum+"个");
			//当前层全部加载完后，才开始下一层
			if(curLoadSum == curSum && curLay < mXML.swfs.@layer-1){
				curLay += 1;
				loadSource(curLay);
			}
			if(curLoadSum == curSum && curLay >= mXML.swfs.@layer-1){
				trace("-----====加载完成====-----");
			}
		}
		
		function onClick(e:MouseEvent):void
		{
			var str:String = e.currentTarget.name;
			var index:int = getIndexByBtnName(mXML, str);
			
			if(index != -1){
				openWindow(mXML.Buttons.Btn[index]);
				trace("点击："+mXML.Buttons.Btn[index]);
			}
		}
		
		function onOver(e:MouseEvent):void
		{
			var str:String = e.currentTarget.name;
			var index = getIndexByBtnName(mXML, str);
			var mc:MovieClip = getMcByName(this, mXML.Buttons.Btn.@movieclip[index]);
			if(mc != null){
				var i:int = getIndexByBtnName(mXML, str);
				if("" == mXML.Buttons.Btn.@over[i]){
					mc.gotoAndPlay("over");
				}else{
					mc.gotoAndPlay(mXML.Buttons.Btn.@over[i]);
				}
			}
		}
		
		function onOut(e:MouseEvent):void
		{
			var str:String = e.currentTarget.name;
			var index = getIndexByBtnName(mXML, str);
			var mc:MovieClip = getMcByName(this, mXML.Buttons.Btn.@movieclip[index]);
			if(mc != null){
				var i:int = getIndexByBtnName(mXML, str);
				if("" == mXML.Buttons.Btn.@out[i]){
					mc.gotoAndPlay("out");
				}else{
					mc.gotoAndPlay(mXML.Buttons.Btn.@out[i]);
				}
			}
		}
		
		static public function getButtonByName(doc:DisplayObjectContainer, BtnName:String):SimpleButton
        {
			if(doc.numChildren <= 0)
				return null;
						
            for (var i:int = 0; i < doc.numChildren; i++){
            	var btn:SimpleButton = doc.getChildAt(i) as SimpleButton;
                if (btn != null && btn.name == BtnName){
                    return btn;  
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
									else{
                                       mc = getMcByName(mc as DisplayObjectContainer, mcName);
									   if(mc != null)
									   		return mc;
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
				var str = mXML.swfs.swf.path[index];
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
				
			var btnCount:int = myXML.Buttons.@count;
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
