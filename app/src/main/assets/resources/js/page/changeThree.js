define(function (require, exports, modlue) {
    require('jQuery');
    var time = parseInt($(".remain_time").text())
    if (time > 0) {
        var t = setInterval(function () {
            time--;
            $(".remain_time").text(time);
            if (time <= 1) {
                window.location.href = "/User/login.htm";
            }
        }, 1000);
    }
})