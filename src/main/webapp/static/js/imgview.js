$("div.img-part").mouseenter(function()
{
    $(this).children("a").show();
}).mouseleave(function()
{
    $(this).children("a").hide();
}).click(function()
{
    var picPath=$("div.imgDetail img").attr("src");
    var picList=$("div.movePicList a");
    var picSelected=picList.filter("[href='"+picPath+"']");
    var picIndex=picList.index(picSelected);
    if($(this).hasClass("prev-img-part"))
    {
         if(picIndex===0)//如果第一张点击前一张则从最后一张倒数重新开始
         {
            picIndex=15;
         }
         $("div.imgDetail img").attr("src",picList.eq(picIndex-1).attr("href"));      
    }
    if($(this).hasClass("next-img-part"))
    {
        if(picIndex===14)//如果最后一张点击后一张则从第一张一张倒数重新开始
        {
            picIndex=-1;
        }
         $("div.imgDetail img").attr("src",picList.eq(picIndex+1).attr("href"));      
    }
    picSelected.closest("li").addClass("img-border");
    picList.not(picSelected).closest("li").removeClass("img-border");
})

//注册点击缩略图
$("#List1_1 a").click(function()
{
   /* $("div.imgDetail img").attr("src",$(this).attr("href"));
    $(this).closest("a").addClass("img-border");
    $("#List1_1 a").not(this).closest("a").removeClass("img-border");

    return false;*/
});