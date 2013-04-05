$(document).ready(function(){
    // tabs
    $('a.tab').click(function (e) {
            e.preventDefault();
        })
    .hover(function () {
        $(this).parents('ul').find('.active').removeClass('active');  
        $(this).addClass('active');    
        $(this).parents('ul').siblings('.tab-content').hide();  
        var content_show = $(this).attr('rev');  
        $('#'+content_show).show();  
    })

    // set the scroll position-top
    function setsp() {
        var s = $('#scroll'), st = $(document).scrollTop();
        if ( st > 725 ) {
            s.css('top', st + 'px');
        } else {
            s.css('top', 725 + 'px');
        }
    }

    $('#scroll a').click(function (e) {
        e.preventDefault();
        $(this).parents('ul').find('.active').removeClass('active');
        $(this).addClass('active');
        $('html, body').animate({scrollTop:$(this).attr('rev')},500);
    });

    $(window).bind('scroll', setsp);
});  