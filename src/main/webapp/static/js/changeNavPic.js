function changeNavi(id){
	var dom=$("#"+id);
	
	var srcBefore=dom.attr("src");
	var srcHover=srcBefore.replace(".jpg","_on.jpg");
		dom.hover(
				 function () {
					 dom.attr("src",srcHover);
				 },
				 function () {
					 dom.attr("src",srcBefore);
				 }
		);

}
$(document).ready(function(){
	for(var i=1;i<5;i++) {
		changeNavi("nav_img"+i);
	}
	
}); 