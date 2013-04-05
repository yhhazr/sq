package  {
	
	import flash.display.MovieClip;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLRequest;
	import flash.net.NetConnection;
	import flash.net.NetStream;
	import flash.media.Video;
	import flash.events.MouseEvent;
	import flashx.textLayout.operations.MoveChildrenOperation;
	import flash.net.URLLoader;
	import flash.net.navigateToURL;
	
	
	public class ren extends MovieClip {
		
		private var _cd:Number=0;
		private var _netStream:NetStream;
		//private var _videoMC:MovieClip = new MovieClip();
		private var _video:Video = new Video();
		private var _flvPath:String;
		private var _fulltime:uint;

		public function ren() {
			// constructor code
			//stage.addEventListener(Event.RESIZE, __onResize);
			//addChildAt(_videoMC, 0);
			var loader:URLLoader = new URLLoader();
			loader.addEventListener(Event.COMPLETE, __onCompleteXml);
			loader.load(new URLRequest("config.xml"));
		}
		
		private function __onCompleteXml(event:Event):void
		{
			var loader:URLLoader = event.target as URLLoader;
			loader.removeEventListener(Event.COMPLETE, __onCompleteXml);
			
			var xml:XML = XML(event.target.data);
			trace ("aaa = " + xml.config.flv);
			_flvPath = xml.config.flv;
			
			playFlv();
		}
		
		private function playFlv():void
		{
			var myConnection:NetConnection = new NetConnection();
			myConnection.connect(null);
			
			_netStream = new NetStream(myConnection);
			_videoMC.addChild(_video);
			
			_video.attachNetStream(_netStream);
			
			
			var dx:Object = new Object();
			dx.onMetaData = __onMetaData;//对名胜dx的元数据事件，调用onMetaData方法
			
			_netStream.client = dx;		//为_netStream调用回调方法，获父对象dx
						
			setVedioSize();
			
			_netStream.play(_flvPath);//http://static.7road.com/flash/sqIndex.flv
			
			//this.addEventListener(Event.ENTER_FRAME, __onFrame);
			//btn.addEventListener(MouseEvent.CLICK, __onMouseClick);
		}
		
		private function __onFrame(event:Event):void
		{
			if(timeFormat(_netStream.time) == timeFormat(_fulltime))
			{
				_netStream.seek(0);
			}
		}
		
		private function __onMetaData(data:Object):void
		{
			_cd=data.duration;//_cd获取数据的持续时间
			_fulltime = data.duration;  
		}
		
		private function __onMouseClick(event:MouseEvent):void
		{
			//navigateToURL(new URLRequest("http://sq.7road.com/serverList.html"));
			//_videoMC
		}
		
		private function setVedioSize():void
		{
			_video.width = _videoMC.width;
			_video.height = _videoMC.height;
		}
		
		private function timeFormat(_n:uint):String //时间（秒）转化格式为00：00  
		{  
			return ("0"+uint(_n/60)).substr(-2)+":"+("0"+_n%60).substr(-2);  
		}
	}
	
}
