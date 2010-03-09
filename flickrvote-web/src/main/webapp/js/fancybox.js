$(document).ready(function () {
    $("a.flickrImage").fancybox({
        'cyclic' : true,
        'autoScale' : true
    });

    $("a.fancyboxImage").html(showLarge).bind("click", function(e) {
        $("#img_" + $(this).attr("id")).trigger('click');
    }).show();
});
