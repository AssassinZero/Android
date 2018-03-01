//票据商城-票据详情
define(function (require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件


        require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
            require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
                /*var tpl = require('/resources/tpls/myNotes/bill.tpl'); //载入tpl模板
                var template = Handlebars.compile(tpl);
                var now = new Date();
                var newDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
                var html = template({'newDate':newDate});
                $(".frontbill").html(html);
                $(".backbill").html(html);*/
            })
        })

    $.ajax({
        url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/details.htm?billNo='+(window.location.search).substring(1),
        type: "get",
        dataType: "json",
        success: function (result) {
            require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
					require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
                        var data = result.data;
                            //
                        var tpl = require('/resources/tpls/myNotes/bill.tpl'); //载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var now = new Date();
                        var newDate = now.getFullYear()+"-"+((now.getMonth()+1)<10?"0":"")+(now.getMonth()+1)+"-"+(now.getDate()<10?"0":"")+now.getDate();
                        data.newDate = newDate;
                        var html = template(data);
                        $(".frontbill").html(html);
                        $(".backbill").html(html);


                        var tpl = require('/resources/tpls/billList/billDetail.tpl'); //载入tpl模板
                        var template = Handlebars.compile(tpl);

                        var html = template(data);
                        $(".yesDetails").html(html);
                        var tpl2 = require('/resources/tpls/billList/billDetailNo.tpl'); //载入tpl模板
                        var template2 = Handlebars.compile(tpl2);
                        var html2 = template2(data);
                        $(".noDetails").html(html2);
                        // 立即购买
                        $("#Order-an").click(function () {
                            require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
                                layer.open({
                                    type: 1,
                                    title: "温馨提示",
                                    area: ['580px', '250px'],
                                    border: [1, 1, '#cecfd0'],
                                    content: '<div id="Order-tc" class="OrderBox">'+
                                                '<div class="topMsg">'+
                                                    '<img src="../../../resources/img/billlist/gantanhao.png">'+
                                                    '<span>请于30分钟之内完成付款</span>'+
                                                '</div>'+
                                                '<div class="tipsMsg">'+
                                                    '<span class="remain_time">3</span>秒种后，将跳转至<a href="/BillShop/billOrder.htm?'+data.billNo+'">订单页面</a>'+
                                                '</div>'+
                                            '</div>',
                                    close: function (index) {
                                        window.location.reload();
                                    },
                                    end: function () {
                                        window.location.reload();
                                    },
                                    success: function (layero) { 
                                        var time = parseInt($(".remain_time").text()); 
                                        if (time > 0) {
                                            var t = setInterval(function () {
                                                time--;
                                                $(".remain_time").text(time);
                                                if (time <= 1) {
                                                    window.location.href = "/BillShop/billOrder.htm?"+data.billNo;
                                                }
                                            }, 1000);
                                        }
                                    }
                                })
                            });
                        });
                    })
            })
        }
    })

    /* tab */
    $(".J-tab a").on('click', function (e) {
        var $this = $(this);
        $tab = $this.parents('.J-tab');
        $tab.find('a').removeClass('active');
        $this.addClass('active');
        var target = $this.attr('data-target');
        $tab.next('.tab_content').find('[data-tab]').removeClass('active').end().find('[data-tab=' + target + "]").addClass('active');
    });

});

