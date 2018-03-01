//买家操作
define(function(require, exports, module) {
	require('/resources/js/jquery');


	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
			require.async('/resources/js/module/newKkpager/kkpager.js', function() {
 
				var AllTransactions = require("/resources/js/page/buyersAverageUser/AllTransactions"); // 全部交易  

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