//票据
define(function(require, exports, module) {
	require('/resources/js/jquery');
    require('/resources/js/commonFn');
            var billNo = location.search.match(new RegExp("[\?\&]billNo=([^\&]+)", "i"));
                    //
            $.ajax({
                url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/details.htm'+billNo[0],
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
                            $(".bill").html(html);
                                //
                        })
                    })
                }
                    //
            });
});