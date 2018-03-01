// 历史票据
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 数据获取 && 分页
	// 数据获取 && 分页
	var getListDate = function() {

		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/historyBill/historyBill.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#tradeClosed").html(html);
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
        
		$.ajax({
			url: "/modules/bills/action/billsAction/myHistoryBills.htm",
			type: "get",
			data:{
				currentPage:1,
				pageSize:8
			},
			dataType: "json",
			success: function(data) {
                var totalTips = data.page.total;
                if(totalTips>99){
                	totalTips=99
                }
                $(".buyTradeClose").html(totalTips);//列表条数小标
				//------- 插入数据 strat
				insetDate(data);
				//------- 插入数据 End

				// start-分页插件
				if (data.page.total > 0) {
					var obj11 = {
						pagerid: "kkpager-tradeClosed",
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
								url: "/modules/bills/action/billsAction/myHistoryBills.htm",
								data:{
                                    currentPage:n,
                                    pageSize:data.page.pageSize
                                },
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								success: function(data) {
                                    var totalTips = data.page.total;
                                    if(totalTips>99){
                                    	totalTips=99
                                    }
                                    $(".buyTradeClose").html(totalTips);//列表条数小标
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
					$("#kkpager-tradeClosed").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
				}
				// End-分页插件

			}
		})
	}
	getListDate();

	// 操作功能函数: 传入操作数据
	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {

             /**
 			 * [查看票据正反面]
 			 */
 			$('.lookUp').click(function(e) {
             	console.log($(this).attr("data-id"));
             	var tpl = require('/resources/tpls/myNotes/bill.tpl'); //载入tpl模板
             	var template = Handlebars.compile(tpl);
             	var data={};
             	var html = template(data);
             	$("#bill").html(html);
             	window.open('/UserCenterWin/bill.htm');  
            })
             /**
 			 * [票据修改/查看-返回] 
 			 */
            $('.billBack').click(function() {
             	var ids = window.ids;console.log(ids)
             	console.log($("#userInfoForm3"))
             	//comfn.resetForm($("#userInfoForm3"));
             	$('.theShelvesTopWarp.third').css('display','none');
             	$('.userContent').css('display','block');
            })

		})
	}

	//暴露刷新函数
	module.exports = getListDate;


});