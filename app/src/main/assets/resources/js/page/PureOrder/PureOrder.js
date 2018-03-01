//卖出票据
define(function(require, exports, module) {
	require('/resources/js/jquery');
    require('/resources/js/commonFn');
    
    $('input[name="quotationMethod"]').click(function(){
        var value = $(this).val();
        if(value=='01'||value=='10'){
            $('input[name="discount"]').parent().addClass('hideActive');
            $('input[name="yearRate"]').parent().removeClass('hideActive');
        }else{
            $('input[name="yearRate"]').parent().addClass('hideActive');
            $('input[name="discount"]').parent().removeClass('hideActive');
        }
        
    })

	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
			require.async('/resources/js/module/newKkpager/kkpager.js', function() {

				var SellingBill = require("/resources/js/page/PureOrder/SellingBill"); // 卖出票据

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

				});
				$(".pay_list_c1").on("click",function(){
				  $(this).addClass("on").siblings().removeClass("on");
				});

                require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() { 
			        
                    $('.submitBtn1').click(function(){
                    	var id = $('.oderId').val();
                    	$.ajax({
                    	    type: "post",
                    	    url: "/modules/nonRegulatory/nonRegulationAction/reviewOrder.htm",
                    	    dataType: 'json',
                    	    data: {
                    	        id:id,
                    	        result:'pass'
                    	    },
                    	    success: function (result) {
                    	        if (result.code == 200) {
                    	            layer.msg(result.msg);
                    	            SellingBill();//刷新列表
                    	            $('.superviseBack').trigger("click")
                    	            //location.reload();//刷新页面
                    	        } else {
                    	            layer.msg(result.msg);
                    	        }
                    	    }
                    	});
                    });
                    $('.submitBtn2').click(function(){
                    	var id = $('.oderId').val();
                    	$.ajax({
                    	    type: "post",
                    	    url: "/modules/nonRegulatory/nonRegulationAction/reviewOrder.htm",
                    	    dataType: 'json',
                    	    data: {
                    	        id:id,
                    	        result:'noPass'
                    	    },
                    	    success: function (result) {
                    	        if (result.code == 200) {
                    	            layer.msg(result.msg);
                    	            SellingBill();//刷新列表
                    	            $('.superviseBack').trigger("click")
                    	            //location.reload();//刷新页面
                    	        } else {
                    	            layer.msg(result.msg);
                    	        }
                    	    }
                    	});
                    });

			                 

		        });







			});
		})
	});


});