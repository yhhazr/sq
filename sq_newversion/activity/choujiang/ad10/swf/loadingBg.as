package  {
	
	import flash.display.MovieClip;
	import flash.display.Loader;
	import flash.net.URLRequest;
	import flash.events.Event;
	import flash.events.ProgressEvent;
	import flash.display.StageScaleMode;
	import flash.display.StageAlign;
	import flash.net.URLLoader;
	
	
	public class loadingBg extends MovieClip {
		private var _loader:Loader;
		private var _numFrames:int;
		private var _xmlListSwf:XMLList;
		
		public function loadingBg() {
			// constructor code
			/*stage.showDefaultContextMenu = false;
			stage.align = StageAlign.LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;*/
			
			//stage.addEventListener(Event.RESIZE, __onResize);
			
			_numFrames = loading_line.totalFrames;
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
			
			loadSwf();		//加载swf
		}
		
		private function loadSwf():void
		{
			_loader = new Loader();
			_loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, onProressHandler);			
			//_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onLoadCompleteHandler);			
			_loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onCompleteHandler);
			_loader.load( new URLRequest(_xmlListSwf[2]));
		}
		
		private function onProressHandler(e:ProgressEvent):void
		{
			var progressInt:int = int(e.bytesLoaded/e.bytesTotal*100.0);
			
			loading_line.gotoAndStop(progressInt);
		}
		
		private function onCompleteHandler(e:Event):void
		{
			trace("complete!bg!");
			this.removeChild(loading_mc);
			this.removeChild(loading_line);
			addChild(e.target.content);
			
			this.dispatchEvent(new Event("LoadingCompleteBg"));
		}
	}
	
}
