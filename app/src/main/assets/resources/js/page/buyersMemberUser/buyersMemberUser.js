//会员用户-我是买家操作
define(function (require, exports, module) {
	require('/resources/js/jquery');
	require('/resources/js/commonFn');

	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {

			require.async('/resources/js/module/newKkpager/kkpager.js', function () {

				$.ajax({
					type: "get",
					url: "/modules/business/action/membershipBusinessAction/getBuyBillsTotal.htm",
					dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
					success: function (result) {
						var tpl = $('#notesInfoTpl').val();
						var template = Handlebars.compile(tpl);
						var html = template(result.data);
						$(".notesInfo").html(html);
					},
					error: function (result) {
						console.log('ajaxError')
					}
				});

				var AllTransactions = require("/resources/js/page/buyersMemberUser/AllTransactions"); // 全部交易
				var PendingTransaction = require("/resources/js/page/buyersMemberUser/PendingTransaction"); // 待经办交易
				var ReviewTransaction = require("/resources/js/page/buyersMemberUser/ReviewTransaction"); // 待复核交易 
				var PaymentTransaction = require("/resources/js/page/buyersMemberUser/PaymentTransaction"); // 待支付交易 

				var tabIndex;
                		$("#proDocuList .productWrap").eq(0).show();
                		$(".tabTitle a").click(function() {
                			//getCount();
                			// console.log(getCount())
                			var isClick = tabIndex == $(this).parent().index() ? true : false;
                			tabIndex = $(this).parent().index();

                			// 如果页面未切换，不刷新
                			// console.log(tabIndex)
                			if (isClick) {
                				return;
                			}

                			var tabName = $(this).attr("data-nav");
                			//pushTrade();
                			eval(tabName)()

                			$(this).addClass("active").parent().siblings().find("a").removeClass("active");
                			$("#proDocuList .productWrap").hide().eq(tabIndex).show();

                		}) 

                		$(".pay_list_c1").on("click",function(){
                		  $(this).addClass("on").siblings().removeClass("on");
                		})


			});
		})
	})


});