var global={
	"serverHost":"http://sq.7road.com",
	"loginUrl":"http://sq.7road.com/game7road/loginSubmit.action",
	"gameUrl":"#"

}


//获取某一区所有排行榜
function getRankData()
{
    var serverId=$(".gameArea :selected").val();

    $.get(global.serverHost+"/player/"+serverId+".html",function(data){
        
        $(".ranking #rank").html(data);
        getSpecifiedRankData($("select.gameRank"));
    });
}
//获取某区某类型排行榜
function getSpecifiedRankData(obj)
{
     var selectedIndex= obj.children("option:selected").index();
    $(".ranking #rank > div").eq(selectedIndex).show("slow");
    $(".ranking #rank > div").not(":eq("+selectedIndex+")").hide();
}
//注册服务区更改事件
$("select.gameArea").bind("change",getRankData);
$("select.gameRank").bind("change",function(){
    getSpecifiedRankData($(this));
});


//登陆
function login(uid,pwd)
{
    $.get(global.loginUrl,{"inName":uid,"password1":pwd},function(data){
        if(data.result==="true")
        {
            loginSuccess();

        }
        else
        {
            $(".error-msg").text(data.respMsg);
            alert(data.respMsg);

        }    
    })
}
//左侧登陆框条件判断
function isLeftLoginOk()
{
    if(!$(".loginInput :text")||!$(".loginInput :password"))
    {
        alert("帐号或密码不能为空");
    }
    return false;
}

function loginSuccess()
{
    //同步左边登陆栏
   $(".loginInfo").hide();
   $(".logined").show();
   $(".loginedInfo span").text(data.outName);
   $(".recentService a").text(data.lastGameZoneName).attr("href",global.gameUrl);
   //同步顶部
   $(".topLoginInfo").hide();
   $(".topLoginedInfo").show().children("a.idx_name").text("欢迎您,"+data.outName);


   $(body).append(data.bbsData);//同步论坛
     //同步弹出层
   $("span.loged_name").text(data.outName);
}