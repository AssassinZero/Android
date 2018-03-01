// 卖家-卖出票据
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 数据获取 && 分页
	var getListDate = function() {

		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/PureOrder/SellingBill.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#tradedOrder").html(html);
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
        
		$.ajax({
			url: "/modules/nonRegulatory/nonRegulationAction/getOrderList.htm",
			type: "get",
			data:{
				currentPage:1,
				pageSize:8
			},
			dataType: "json",
			success: function(data) {
                comfn.setSmallScale(data.page.total,$(".buyTraded"));//设置小标数
				//------- 插入数据 strat
				insetDate(data);
				//------- 插入数据 End

				// start-分页插件
				if (data.page.total > 0) {
					var obj11 = {
						pagerid: "kkpager-tradedOrder",
						pno: data.page.pageNum, //当前页码
						total: data.page.pages, //总页码
						totalRecords: data.page.total, //总数据条数
						isShowFirstPageBtn: false,
						isShowLastPageBtn: false,
						isShowTotalPage: false,
						isShowTotalRecords: false,
						isGoPage: false,
						lang: {
							prePageText: '<',
							nextPageText: '>'
						},
						mode: 'click', //click模式匹配getHref 和 click
						click: function(n, total, totalRecords) {
							$.ajax({
								type: "get",
								url: "/modules/nonRegulatory/nonRegulationAction/getOrderList.htm",
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								data:{
                                    currentPage:n,
                                    pageSize:data.page.pageSize
                                },
								success: function(data) {
                                    comfn.setSmallScale(data.page.total,$(".buyTraded"));//设置小标数
									//------- 插入数据 strat
									insetDate(data);
									//------- 插入数据 End

								}
							});
							this.selectPage(n); //处理完后可以手动条用selectPage进行页码选中切换
						}
					};
					new KingPaging(null, obj11);
				} else {
					$("#kkpager-tradedOrder").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
				}
				// End-分页插件

			}
		})
	}
	getListDate();
	// 操作功能函数: 传入操作数据

    /**
	 * [纯票交易-返回] 
	 */
    $('.superviseBack').click(function() {
    	//comfn.resetForm($("#userInfoForm2"));//重置表单
    	$("#userInfoForm2")[0].reset();//重置表单
    	$('.theShelvesTopWarp.second').css('display','none');
    	$('.userContent').css('display','block');
    }) 
	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
            var orderStatusList = {
            	"0042": "待卖家复核",
            	"0001": "交易关闭",
            	"9999": "交易失败",
            	"0041": "待支付复核",
            	"0040": "待签收支付",
            	"0030": "票据待匹配",
            	"0022": "待卖方修改",
            	"0021": "待买方修改",
            	"0000": "交易成功",
            	"0050": "待卖家背书",
            	"0010": "待双方确认",
            	"0011": "待买方确认",
            	"0020": "交易撤回",
            	"0012": "待卖方确认",
            	"0060": "待买家付款"
            };
            /**
			 * [票据查看]
			 */
			$('.lookUp').click(function(e) {
            	console.log($(this).attr("data-id"));
            	$('.submitBtn').css('display','none');
            	window.btnType='single';
            	window.id=[];
            	window.id.push($(this).attr("data-id"));
            	$.ajax({
            	    type: "get",
            	    url: "/modules/nonRegulatory/nonRegulationAction/getOrderInfoById.htm",
            	    data:{
            	    	id:$(this).attr("data-id")
            	    },
            	    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
            	    success: function(result) {
            	       var tpl = $('#theShelvesTableTpl2').val();
            	       var template = Handlebars.compile(tpl);
            	       var html = template(result.data.bills);
            	       $(".theShelvesTbody").html(html);
                       if(result.data.businessState){
            	       	  result.data.businessState = orderStatusList[result.data.businessState];
            	       }
            	       if(result.data.yearRate){
            	          result.data.yearRate =Number((result.data.yearRate*100).toFixed(4));
            	       }
            	       if(result.data.settlementAmount){
            	          result.data.settlementAmount =Number(Number(result.data.settlementAmount).toFixed(2));
            	       }
            	       $("#userInfoForm2").setForm(result.data);
            	    },
            	    error: function(result) {
            	        console.log('ajaxError')
            	    }
            	});
        		$('.userContent').css('display','none');
        		$('.theShelvesTopWarp.second').css('display','block');
            });

            /**
			 * [票据复核]
			 */
			$('.billReview').click(function(e) {
            	console.log($(this).attr("data-id"));
            	$('.submitBtn').css('display','block');
            	window.btnType='single';
            	window.id=[];
            	window.id.push($(this).attr("data-id"));
            	$.ajax({
            	    type: "get",
            	    url: "/modules/nonRegulatory/nonRegulationAction/getOrderInfoById.htm",
            	    data:{
            	    	id:$(this).attr("data-id")
            	    },
            	    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
            	    success: function(result) {
            	       var tpl = $('#theShelvesTableTpl2').val();
            	       var template = Handlebars.compile(tpl);
            	       var html = template(result.data.bills);
            	       $(".theShelvesTbody").html(html);
            	       if(result.data.businessState){
            	       	  result.data.businessState = orderStatusList[result.data.businessState];
            	       }
            	       if(result.data.yearRate){
            	          result.data.yearRate =Number((result.data.yearRate*100).toFixed(4));
            	       }
            	       if(result.data.settlementAmount){
            	          result.data.settlementAmount =Number(Number(result.data.settlementAmount).toFixed(2));
            	       }
            	       $("#userInfoForm2").setForm(result.data);
            	    },
            	    error: function(result) {
            	        console.log('ajaxError')
            	    }
            	});
        		$('.userContent').css('display','none');
        		$('.theShelvesTopWarp.second').css('display','block');
            });

            /**
			 * [取消订单]
			 */
			$('.cancelOrder').click(function(e) {
                var billNo = $(this).attr("data-id");
				layer.confirm("是否取消订单？", {
					title: '取消订单', //标题
					icon: 0,
					// area: ['150px', '180px'], //大小
					btn: ['确定', '返回'] //按钮
				}, function(index) {
					$.ajax({
						url: "/lf/test.htm",
						type: "post",
						data: {
							billNo:billNo
						},
						dataType: "json",
						success: function(result) {
							if (result.code == 200) {
								layer.msg("取消订单成功", {
									time: 1000
								});
								getListDate(); //延时刷新页面方法
							} else {
								var msg = result.msg || "操作失败"
								layer.msg(msg);
							}
						}
					})

				}, function(index) {
					layer.close(index);
				});
			});


		})
	}	

	//暴露刷新函数
	module.exports = getListDate;

});