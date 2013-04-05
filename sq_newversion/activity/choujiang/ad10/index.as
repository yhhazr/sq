package  {
	
	import flash.display.MovieClip;
	import flash.events.Event;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.display.Loader;
	
	
	public class index extends MovieClip {
		
		private var _xmlListSwf:XMLList;
		private var _swfID:int = 0;
		
		public function index() {
			// constructor code
			loadXMLData("config.xml");
		}
		
		//加载xml数据
		private function loadXMLData(path:String):void
		{
			var xLoader:URLLoader = new URLLoader();
			xLoader.addEventListener(Event.COMPLETE,__onCompleteLoadXML);
			xLoader.load(new URLRequest(path));
		}
		
		private function __onCompleteLoadXML(event:Event):void
		{
			var loader:URLLoader = event.target as URLLoader;
			loader.removeEventListener(Event.COMPLETE, __onCompleteLoadXML);
			
			var mXML:XML = XML(event.target.data);
			_xmlListSwf = mXML.config.swf;
			trace (_xmlListSwf.length());
			//for(var i:int = 0; i < _xmlListSwf.length() - 1; i++)
			//{
				loadSwf(_swfID);		//加载swf
			//}
		}
		
		//加载swf
		private function loadSwf(swfID:int):void
		{
			var loader:Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, __onCompleteLoadSwf);
			loader.load(new URLRequest(_xmlListSwf[swfID]));
		}
		
		private function __onCompleteLoadSwf(event:Event):void
		{
			var loader:Loader = event.target.loader as Loader;
			loader.removeEventListener(Event.COMPLETE, __onCompleteLoadSwf);
			
			addChild(loader);
			
			_swfID++;
			if(_swfID < _xmlListSwf.length() - 1)
			{
				loadSwf(_swfID);
			}
		}
	}
	
}
