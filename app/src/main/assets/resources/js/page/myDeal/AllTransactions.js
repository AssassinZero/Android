//卖家-全部交易
define(function (require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 表单转化成json
	$.fn.serializeObject = function () {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function () {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};


	//延时刷新页面方法

	function refreshFn() {
		//var getCount = require('/resources/js/page/buyer/count'); //计算总数
		//var pushTrade = require('/resources/js/page/buyer/pushTrade'); //票源规则

		setTimeout(function () {
			getListDate();
			//pushTrade();
			//getCount();
		}, 1000)
	}

	//勾选方法
	function checkFn() {//列表多选checkbox-方法2/2
		var ids = window.ids;
		$('.listBoxs label').click(function () {
			if ($(this).hasClass('onActive')) {
				$(this).removeClass('onActive');
				ids.remove($(this)[0].htmlFor);
				console.log(ids)
			} else {
				$(this).addClass('onActive');
				if (ids.indexOf($(this)[0].htmlFor) < 0) {
					ids.push($(this)[0].htmlFor);
				}
				console.log(ids)
			}
		})
	}

	// 数据获取 && 分页
	var getListDate = function () {

		// 插入数据函数
		function insetDate(data) {
			var tpl = "";
			tpl = require('/resources/tpls/myDeal/myDeal.tpl'); //载入tpl模板  

			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#tradingOrder").html(html);
			// console.log("getListDate")
			checkFn();
			//coutDownFn();
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
		$.ajax({
			url: "/modules/business/action/agentBusinessAction/marriedDealOrders.htm",
			type: "get",
			dataType: "json",
			data: {
				currentPage: 1,
				pageSize: 5
			},
			success: function (data) {

				//------- 插入数据 strat
				insetDate(data);
				//------- 插入数据 End

				// start-分页插件
				if (data.page.total > 0) {
					var obj11 = {
						pagerid: "kkpager-tradingOrder",
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
						click: function (n, total, totalRecords) {
							$.ajax({
								type: "get",
								url: "/modules/business/action/agentBusinessAction/marriedDealOrders.htm&pageNum=" + n + "&pageSize=" + data.page.pageSize,
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								success: function (data) {

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
					$("#kkpager-tradingOrder").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
				}
				// End-分页插件

			}
		})
	}
	getListDate();

	//票据类型获取下拉数据
	$.ajax({
		url: "/getDicts.htm",
		type: "get",
		dataType: 'json',
		data: {
			type: "BILL_TYPE"
		},
		success: function (result) {
			var data = result.data;
			var len = data.length;
			var str = "";

			for (i = 0; i < len; i++) {
				str += "<li data-value='" + data[i].value + "'>" + data[i].text + "</li>";
			}
			$("#billsTypelist").append(str);
		}
	});

	//票据属性获取下拉数据
	$.ajax({
		url: "/getDicts.htm",
		type: "get",
		dataType: 'json',
		data: {
			type: "BILL_ATTRIBUTE"
		},
		success: function (result) {
			var data = result.data;
			var len = data.length;
			var str = "";

			for (i = 0; i < len; i++) {
				str += "<li data-value='" + data[i].value + "'>" + data[i].text + "</li>";
			}
			$("#billsAttributelist").append(str);
		}
	});
	
	var businessStateList = {
		"0000": 	"交易成功", 
		"0010": 	"待双方确认", 
		"0011": 	"待买方确认", 
		"0012": 	"待卖方确认", 
		"0020": 	"交易撤回", 
		"0021": 	"待买方修改", 
		"0022": 	"待卖方修改", 
		"0030": 	"票据待匹配", 
		"0040": 	"待签收支付", 
		"0041": 	"待支付复核", 
		"9999": 	"交易失败", 
		"0001": 	"交易关闭",
        "0080": "等待交易结果通知"
	}

	// 操作功能函数: 传入操作数据

	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
			/**
				* [票据查看]
				*/ 
			$('.lookUp').click(function (e) {
				console.log($(this).attr("data-id")) 
				$('.theShelvesTopWarp.third .theShelvesTop p').text('票据查看');
				$('.lookNote-li').css("display", 'none');
				$('.theShelvesTopWarp.third input').attr("disabled", true); 
				$('.theShelvesTopWarp.third input[type="button"]').attr("disabled", false);
				$('.theShelvesTopWarp.third input[type="submit"]').css("display", 'none');
				$('.userContent').css('display', 'none');
				$('.theShelvesTopWarp.third').css('display', 'block');
				$.ajax({
					type: "get",
					url: "/modules/business/action/directBusinessAction/queryOrderDetail.htm",
					data: {
						orderId: $(this).attr("data-id"),
					},
					dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
					success: function (result) {
						var data = result.data;
						var tpl = $('#theShelvesTableTpl').val();
						var template = Handlebars.compile(tpl);
						console.log(data.orderBills)
						var html = template(data.orderBills);
						$(".theShelvesTbody").html(html);
						bankAccountlistFn(data.holdAccountId, data.bankAccount);
						if(data.yearRate){
							data.yearRate = Number(data.yearRate*100).toFixed(4);
						}
						data.businessState = businessStateList[data.businessState]
						$("#userInfoForm3").setForm(data);
						if (data.quotationMethod == "10") {
							$("#yearRateli").show();
							$("#discountid").hide();
						} else {
							$("#yearRateli").hide();
							$("#discountid").show();
						}
						if (data.uniformity == "10") {
							$(".uniformityli").css('display', 'none');
						} else {
							$(".uniformityli").css('display', 'block');
						}
					}
				});
			})


			function bankAccountlistFn(holdAccountId, bankAccount) {

				$.ajax({
					url: "/modules/common/action/bizBaseAction/getSignBankByAccount.htm",
					type: "get",
					dataType: 'json',
					data: {
						collectorAccountId: holdAccountId,
						signStatus: 10
					},
					success: function (result) {
						var data = result.data;
						var len = data.length;
						var str = "";
						for (i = 0; i < len; i++) {
							str += "<li data-value='" + data[i].bankAccount + "'>" + data[i].holdAccountName + "</li>";
						}
						$("#bankAccountlist").append(str);
						$("#userInfoForm3").setForm({ bankAccount: bankAccount });

					}
				});
			}


            /**
				* [订单修改/查看-返回] 
				*/
			$('.billBack').click(function () {
				var ids = window.ids; console.log(ids)
             	$("#userInfoForm3")[0].reset();
				$('.theShelvesTopWarp.third').css('display', 'none');
				$('.userContent').css('display', 'block');
				$('#submitLi').css("display", 'block');
				$('.stateLi').css("display", 'none');
			})
		})
	}

	//暴露刷新函数
	module.exports = getListDate;

});