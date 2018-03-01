define(function(require, exports, module) {
    require('jquery');
    var comfn = require('/resources/js/commonFn.js');

    // $.fn.extend({
    //     slideFn: function(options) {
    //         var defaults = {
    //             isTop: true, //是否
    //             slideTimer: "2000"
    //         };
    //         var options = $.extend(defaults, options);
    //         this.each(function() {
    //             var o = options;
    //             var obj = $(this);
    //             var oUl = obj.find("ul:first");
    //             var oLi = $("li", oUl);
    //             var Timer;
    //             obj.hover(function() {
    //                 clearInterval(Timer);
    //             }, function() {
    //                 Timer = setInterval(function() {
    //                     if (o.isTop == true) {
    //                         slideTop(oUl);
    //                     } else {
    //                         slideLeft(oUl);
    //                     }
    //                 }, o.slideTimer)
    //             }).trigger("mouseleave");
    //             var slideTop = function(box) {
    //                 var oLiHeight = box.find("li:first").height();
    //                 box.animate({
    //                     "marginTop": -oLiHeight + "px"
    //                 }, 800, function() {
    //                     box.css("marginTop", 0).find("li:first").appendTo(box);
    //                 })
    //             }; //上滚
    //             var slideLeft = function(box2) {
    //                 box2.css("width", ((oLi.width()) * (oLi.length)) + "px");
    //                 var oLiWidth = box2.find("li:first").width();
    //                 box2.animate({
    //                     "marginLeft": -oLiWidth + "px"
    //                 }, 800, function() {
    //                     box2.css("marginLeft", 0).find("li:first").appendTo(box2);
    //                 })
    //             }; //左滚
    //         })
    //     }
    // })

   
    // $.ajax({
    //     url: '/modules/order/PjsOrderMsgAction/latestTradeRecord.htm',
    //     type: 'get',
    //     dataType: "json",
    //     success: function(result) {
    //         var len = result.data.length;
    //         var liStr = "";
    //         for (var i = 0; i < len; i++) {
    //             var pjsData = result.data[i].pjsReceipt;
    //             liStr += '<li> <p> <img src="/resources/img/home/laba.png">' +
    //                 '<i>最新成交</i>&nbsp;&nbsp; •【' + pjsData.receiptType + '】 ' + pjsData.maker + '<i>结算</i> <b>' + comfn.commafy(pjsData.billAmt) + '</b></p> </li> '
    //         }
    //         $("#news-roll-ul").html(liStr);
    //         if (len > 1) {
    //             $("#news-roll").slideFn();
    //         }

    //         // console.log(result)
    //     }
    // })



    // var data1;
    // $.ajax({
    //     url: '/cmsArticle/find.htm?siteNid=homePageBanner',
    //     type: 'get',
    //     success: function(result) {
    //         data1 = result.data;
    //         // console.log(data1)

    //         var len = data1.length;
    //         var str = "";
    //         for (i = 0; i < len; i++) {
    //             var picPath = data1[i].picPath;
    //             var url = data1[i].url;
    //             if (url) {
    //                 url = data1[i].url;
    //             } else {
    //                 url = "javascript:;";
    //             }
    //             picPath = '/modules/cms/CmsArticleAction/read.htm?path=' + picPath;
    //             str += "<li style='background:url(" + picPath + ") repeat center 0'><a href='" + url + "' ></a></li>";
    //         }
    //         $(".banner_con").html(str);
    //         require.async('module/jquery.flexslider-min', function() {
    //             $('.flexslider').flexslider({
    //                 directionNav: true,
    //                 pauseOnAction: false,

    //             });
    //         });
    //     }
    // })

    // var data = [{ picPath: "resources/img/home/banner.png" }, { picPath: "resources/img/home/banner.png" }]
    // var len = data.length;
    // var str = "";
    // for (i = 0; i < len; i++) {
    //     var picPath = data[i].picPath;
    /*var img=new Image();
    img.src=picPath;
    if(img.width==0){
        picPath="/resources/img/home/banner_2.jpg"
    }
    else{
        picPath=data[i].picPath
    }*/
    //     var url = data[i].url;
    //     if (url) {
    //         url = data[i].url;
    //     } else {
    //         url = "javascript:;";
    //     }
    //     str += "<li style='background:url(" + picPath + ") repeat center 0'><a href='" + url + "' ></a></li>";
    // }
    // $(".banner_con").html(str);
    // require.async('module/jquery.flexslider-min', function () {
    //     $('.flexslider').flexslider({
    //         directionNav: true,
    //         pauseOnAction: false
    //     });
    // });

    // $.ajax({
    //     url: buildUrl("/cms/article/page.htm"),
    //     type: "post",
    //     data:{
    //       siteId :21  
    //     },
    //     dataType: 'json',
    //     success: function (result) {
    //         var data = result.data;
    //         var len = data.length;
    //         var str = "";
    //         for (i = 0; i < len; i++) {
    //             var picPath = data[i].picPath;
    //             /*var img=new Image();
    //             img.src=picPath;
    //             if(img.width==0){
    //                 picPath="/resources/img/home/banner_2.jpg"
    //             }
    //             else{
    //                 picPath=data[i].picPath
    //             }*/
    //             var url = data[i].url;
    //             if(url){
    //                url = data[i].url;
    //             }else{
    //                url = "javascript:;";
    //             }    
    //             str += "<li style='background:url(" + picPath + ") repeat center 0'><a href='" + url + "' ></a></li>";
    //         }
    //         $(".banner_con").html(str);
    //         require.async('../module/jquery.flexslider-min', function () {
    //             $('.flexslider').flexslider({
    //                 directionNav: true,
    //                 pauseOnAction: false
    //             });
    //         });
    //     }
    // });
    
    function toThousands(num) {
        var result = [ ], counter = 0;
        num = (num || 0).toString().split('');
        for (var i = num.length - 1; i >= 0; i--) {
            counter++;
            result.unshift(num[i]);
            if (!(counter % 3) && i != 0) { result.unshift(','); }
        }
        return result.join('');
    }

    //20180209 千分位分隔方法
    function thousandBitSeparator(num) {
        return num && num
            .toString()
            .replace(/(\d)(?=(\d{3})+\.)/g, function($0, $1) {
                return $1 + ",";
            });
    }

    $.ajax({
        url: '/modules/common/statisticsAction/getStatisticsInfo.htm',
        type: 'post',
        success: function(result) {
            var data = result.data; 
            $("#accumulated").html(thousandBitSeparator(data.totalAmount))
            $("#pens").html(data.totalCount)
        }
    })


});