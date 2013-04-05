function showFlash(ur,w,h){
			 ur = ur + "?v=1.1";
          	 document.write('<object type="application/x-shockwave-flash" codebase="'+ur+'" width="'+w+'" height="'+h+'" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="movie_player"><param name="WMode" value="Transparent">');
			document.write('<param name="allowFullScreen" value="false">');
			document.write('<param name="allowScriptAccess" value="always">');
			document.write('<param name="quality" value="high">');   
			document.write('<param name="movie" value="'+ur+'">');
			document.write('<param value="noborder" name="SCALE"/>');
			 document.write('<param name="menu" value="false">');  
			document.write('<embed  quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" allowScriptAccess="always"  allowfullscreen="false" wmode="transparent" scale="noborder"  type="application/x-shockwave-flash" width="'+w+'" height="'+h+'" quality="high"  src="'+ur+'"></embed>');
             document.write('</object>');   
}  
