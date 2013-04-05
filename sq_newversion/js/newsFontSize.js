function changeSize(id){
		var size = id + "px";
		var obj = document.getElementById("news_content");
		var spa = obj.getElementsByTagName("span");
		if(spa != null){
			for(var i = 0; i < spa.length; i++){
				spa[i].style.fontSize = size;
			}
		}
		obj.style.fontSize = size;
}