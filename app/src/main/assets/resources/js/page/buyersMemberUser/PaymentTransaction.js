// 卖家-待支付交易
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

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

	// 数据获取 && 分页
	var getListDate = function() {

		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/buyersMemberUser/PaymentTransaction.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#PaymentdOrder").html(html);
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		} 
		$.ajax({
			url: "/modules/business/action/membershipBusinessAction/buyBillOrders.htm?businessState="+"0040",
			type: "get",
			dataType: "json",
			data: {
				currentPage: 1,
				pageSize: 5
			},
			success: function(data) {

				//------- 插入数据 strat
				insetDate(data);
				comfn.setSmallScale(data.page.total,$(".buyPayment"));//设置小标数  
				//------- 插入数据 End

				// start-分页插件
				if (data.page.total > 0) {
					var obj11 = {
						pagerid: "kkpager-Paymentd",
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
								url: "/modules/business/action/membershipBusinessAction/buyBillOrders.htm?businessState="+'0040'+"&currentPage=" + n + "&pageSize=" + data.page.pageSize, 
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								success: function(data) {
									comfn.setSmallScale(data.page.total,$(".buyPayment"));//设置小标数 
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
					$("#kkpager-Paymentd").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
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
			$("#billsTypelist2").append(str);
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
			$("#billsAttributelist2").append(str);
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
	function lookBill(){
        $('td.lookUp').click(function(e) {
        	var billNo = $(this).attr("data-id");
        	var tpl = require('/resources/tpls/myNotes/bill.tpl'); //载入tpl模板
        	var template = Handlebars.compile(tpl);
        	var data={};
        	var html = template(data);
        	$("#bill").html(html);
        	window.open('/UserCenterWin/bill.htm?billNo='+billNo);
        })
	}
    			
	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {


            /**
				* [票据查看]
				*/ 
			$('#PaymentdOrder .lookUp').click(function (e) {
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
						//console.log(data.orderBills)
						var html = template(data.orderBills);
						$(".theShelvesTbody").html(html); 
						lookBill();//查看票据正反面
						bankAccountlistFn(data.holdAccountId, data.bankAccount);
						if(data.yearRate){
							data.yearRate = Number(data.yearRate*100).toFixed(4);
						}
						data.businessState = businessStateList[data.businessState];
						if (data.serviceFee > 0) {
							data.serviceFee = parseFloat(data.serviceFee).toFixed(2);
						} else {
							data.serviceFee= "0"
						}
						if (data.settlementAmount > 0) {
							data.settlementAmount = parseFloat(data.settlementAmount).toFixed(2);
						} else {
							data.settlementAmount= "0"
						}
						if(data.businessType=="0200"){
							data.holdAccountId=data.agentAccount;
							$(".labelId").text("代理商账号")
						}
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
             	$("#userInfoForm4")[0].reset();
				$('.theShelvesTopWarp.third').css('display', 'none');
				$('.userContent').css('display', 'block'); 
				$('#submitLi').css("display", 'block');
				$('.stateLi').css("display", 'none');
				$(".labelId").text("持票方账号");
			})


			/**
			 * [确认]
			 */
			$('#PaymentdOrder .buyersMemberConfirm').unbind("click").click(function (e) {
				var rid = $(this).attr("data-id")
				$.ajax({
					url: "/modules/business/action/directBusinessAction/queryOrderDetail.htm",
					type: "get",
					dataType: "json",
					data: {
						orderId: rid,
					},
					success: function (result) {
						var data = result.data;
						var tpl = $('#theShelvesTableTpl').val();
						var template = Handlebars.compile(tpl);
						//console.log(data.orderBills)
						var html = template(data.orderBills);
						$(".theShelvesTbody").html(html);
						lookBill();//查看票据正反面
						bankAccountlistFn(data.holdAccountId, data.bankAccount);
						if(data.yearRate){
							data.yearRate = Number(data.yearRate*100).toFixed(4);
						}
						data.businessState = businessStateList[data.businessState];
						if(data.businessType=="0200"){
							data.holdAccountId=data.agentAccount;
							$(".labelId").text("代理商账号")
						}
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
						$('.theShelvesTopWarp.third .theShelvesTop p').text('经办交易');
						$('.lookNote-li').css("display", 'none');
						$('.theShelvesTopWarp.third input').attr("disabled", true);
						$('.theShelvesTopWarp.third input[type="button"]').attr("disabled", false);
						$('.theShelvesTopWarp.third input[type="submit"]').css("display", 'none');
						$('.userContent').css('display', 'none');
						$('.theShelvesTopWarp.third').css('display', 'block');
						$('#submitLi').css("display", 'none');
						$('#confirmLi').css("display", 'block');
						//确认经办
						$('.confirmbt').click(function () {
							$.ajax({
								url: "/modules/business/action/directBusinessAction/confirmOrder.htm",
								type: "post",
								dataType: "json",
								data: {
									orderId: rid,
									comment: "confirm"
								},
								success: function (result) {
									if (result.code == 200) {
										layer.msg("确认成功", {
											time: 1000
										});
										$('#submitLi').css("display", 'block');
										$('.stateLi').css("display", 'none');
										getListDate(); //延时刷新页面方法
									} else {
										var msg = result.msg || "操作失败"
										layer.msg(msg);
									}
								}
							})
						})
						//拒绝交易
						$('.rejectbt').click(function () {
							$.ajax({
								url: "/modules/business/action/directBusinessAction/confirmOrder.htm",
								type: "post",
								dataType: "json",
								data: {
									orderId: rid,
									comment: "reject"
								},
								success: function (result) {
									if (result.code == 200) {
										layer.msg("拒绝成功", {
											time: 1000
										});
										$('#submitLi').css("display", 'block');
										$('.stateLi').css("display", 'none');
										getListDate(); //延时刷新页面方法
									} else {
										var msg = result.msg || "操作失败"
										layer.msg(msg);
									}
								}
							})
						})

					}
				})
			});

			/**
				* [订单撤回] 
				*/
			$('#PaymentdOrder .buyersMemberRevoke').click(function () {
				layer.confirm("撤回后对方无法查看此订单，是否确认撤回交易？", {
					title: '温馨提示', //标题
					// area: ['580px', '250px'],
					btn: ['确定', '取消'] //按钮
				}, function (index) {
					$.ajax({
						url: "/modules/business/action/directBusinessAction/recallOrder.htm ",
						type: "post",
						dataType: "json",
						data: {
							orderId: $(".buyersMemberRevoke").attr("data-id"),
						},
						success: function (result) {
							if (result.code == 200) {
								layer.msg("撤回成功", {
									time: 1000
								});
								$('.stateLi').css("display", 'none');
								getListDate(); //延时刷新页面方法
							} else {
								var msg = result.msg || "操作失败"
								layer.msg(msg);
							}
						}
					})

				}, function (index) {
					layer.close(index);
				});
			})


			/**
			 * [订单退回]
			 */
			$('#PaymentdOrder .buyersMemberReturn').unbind("click").bind("click", function (e) {
				layer.confirm("若对交易信息有疑问，请先与持票方沟通，确认退回需要持票方再次确认，是否继续？", {
					title: '温馨提示', //标题
					// area: ['580px', '250px'],
					btn: ['是', '否'] //按钮
				}, function (index) {
					$.ajax({
						url: "/modules/business/action/directBusinessAction/returnOrder.htm",
						type: "post",
						dataType: "json",
						data: {
							orderId: $(".buyersMemberReturn").attr("data-id"),
						},
						success: function (result) {
							if (result.code == 200) {
								layer.msg("退回成功", {
									time: 1000
								});
								getListDate(); //延时刷新页面方法
							} else {
								var msg = result.msg || "操作失败"
								layer.msg(msg);
							}
						}
					})
				}, function (index) {
					layer.close(index);
				});
			});


			
			/**
				* [订单关闭] 
				*/
			$('#PaymentdOrder .buyersMemberClose').click(function () {
				layer.confirm("是否确认关闭订单？", {
					title: '温馨提示', //标题
					// area: ['580px', '250px'],
					btn: ['确定', '取消'] //按钮
				}, function (index) {
					$.ajax({
						url: "/modules/business/action/directBusinessAction/closeOrder.htm",
						type: "post",
						dataType: "json",
						data: {
							orderId: $(".buyersMemberClose").attr("data-id"),
						},
						success: function (result) {
							if (result.code == 200) {
								layer.msg("关闭", {
									time: 1000
								});
								getListDate(); //延时刷新页面方法
							} else {
								var msg = result.msg || "操作失败"
								layer.msg(msg);
							}
						}
					})

				}, function (index) {
					layer.close(index);
				});
			})

			/**
			 * [试户]
			 */
			$('#PaymentdOrder .buyersMemberTestUser').click(function (e) {
				layer.confirm("试户操作不可撤销，等待第三方返回试户结果成功后，方可进行签收支付操作。请确认是否已发起试户？", {
					title: '温馨提示', //标题
					// area: ['150px', '180px'], //大小
					btn: ['是', '否'] //按钮
				}, function (index) {
					// $.ajax({
					// 	url: "/Buyer/pay.htm",
					// 	type: "post",
					// 	dataType: "json",
					// 	data: sentData,
					// 	success: function (result) {
					// 		if (result.code == 200) {
					// 			layer.msg("支付成功", {
					// 				time: 1000
					// 			});
					// 			getListDate(); //延时刷新页面方法
					// 		} else {
					// 			layer.msg(result.msg);
					// 		}
					// 	}
					// })

				}, function (index) {
					layer.close(index);
				});
			});


			/**
			 * [复核]
			 */
			$('#PaymentdOrder .buyersMemberReview').unbind("click").click(function (e) {
				var rid = $(this).attr("data-id")
				$.ajax({
					url: "/modules/business/action/directBusinessAction/queryOrderDetail.htm",
					type: "get",
					dataType: "json",
					data: {
						orderId: rid,
					},
					success: function (result) {
						var data = result.data;
						var tpl = $('#theShelvesTableTpl').val();
						var template = Handlebars.compile(tpl);
						//console.log(data.orderBills)
						var html = template(data.orderBills);
						$(".theShelvesTbody").html(html);
						lookBill();//查看票据正反面
						bankAccountlistFn(data.holdAccountId, data.bankAccount);
						if(data.yearRate){
							data.yearRate = Number(data.yearRate*100).toFixed(4);
						}
						data.businessState = businessStateList[data.businessState];
						if(data.businessType=="0200"){
							data.holdAccountId=data.agentAccount;
							$(".labelId").text("代理商账号")
						}
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
						$('.theShelvesTopWarp.third .theShelvesTop p').text('经办交易');
						$('.lookNote-li').css("display", 'none');
						$('.theShelvesTopWarp.third input').attr("disabled", true);
						$('.theShelvesTopWarp.third input[type="button"]').attr("disabled", false);
						$('.theShelvesTopWarp.third input[type="submit"]').css("display", 'none');
						$('.userContent').css('display', 'none');
						$('.theShelvesTopWarp.third').css('display', 'block');
						$('#submitLi').css("display", 'none');
						$('#reviewLi').css("display", 'block');
						//复核
						$('.reviewbt').click(function () {
							$.ajax({
								url: "/modules/business/action/directBusinessAction/reviewOrder.htm",
								type: "post",
								dataType: "json",
								data: {
									orderId: rid,
								},
								success: function (result) {
									if (result.code == 200) {
										layer.msg("复核成功", {
											time: 1000
										});
										$('#submitLi').css("display", 'block');
										$('.stateLi').css("display", 'none');
										getListDate(); //延时刷新页面方法
									} else {
										var msg = result.msg || "操作失败"
										layer.msg(msg);
									}
								}
							})
						}) 
					}
				})
			});



			/**
			 * [签收并支付]
			 */
			$('#PaymentdOrder .buyersMemberSignPay').unbind("click").click(function (e) {
                var now = new Date();
                if (now.getDay() > 0 && now.getDay() < 6&&now.getHours()>7&&now.getHours()<21) {
					var rid = $(this).attr("data-id")
					$.ajax({
						url: "/modules/business/action/directBusinessAction/queryOrderDetail.htm",
						type: "get",
						dataType: "json",
						data: {
							orderId: rid,
						},
						success: function (result) {
							var data = result.data;
							var tpl = $('#theShelvesTableTpl').val();
							var template = Handlebars.compile(tpl);
							//console.log(data.orderBills)
							var html = template(data.orderBills);
							$(".theShelvesTbody").html(html);
							lookBill();//查看票据正反面
							bankAccountlistFn(data.holdAccountId, data.bankAccount);
							if(data.yearRate){
								data.yearRate = Number(data.yearRate*100).toFixed(4);
							}
							data.businessState = businessStateList[data.businessState];
							if(data.businessType=="0200"){
								data.holdAccountId=data.agentAccount;
								$(".labelId").text("代理商账号")
							}
							$("#userInfoForm4").setForm(data);
							if (data.quotationMethod == "10") {
								$("#yearRateli2").show();
								$("#discountid2").hide();
							} else {
								$("#yearRateli2").hide();
								$("#discountid2").show();
							}
							if (data.uniformity == "10") {
								$(".uniformityli").css('display', 'none');
							} else {
								$(".uniformityli").css('display', 'block');
							}
							$('.theShelvesTopWarp.four .theShelvesTop p').text('签收并支付');
							$('.lookNote-li').css("display", 'none');
							$('.theShelvesTopWarp.four input').attr("disabled", true);
							$('.theShelvesTopWarp.four input[type="button"]').attr("disabled", false);
							$('.theShelvesTopWarp.four input[type="submit"]').css("display", 'none');
							$('.userContent').css('display', 'none');
							$('.theShelvesTopWarp.four').css('display', 'block');
							$('#submitLi2').css("display", 'none');
							$('#signPayLi').css("display", 'block');
							//签约并支付
							$('.signPaybt').click(function () {
								$.ajax({
									url: "/modules/business/action/directBusinessAction/paymentOrder.htm",
									type: "post",
									dataType: "json",
									data: {
										orderId: rid,
									},
									success: function (result) {
										if (result.code == 200) {
											layer.msg("支付成功", {
												time: 1000
											});
											$('#submitLi').css("display", 'block');
											$('.stateLi').css("display", 'none');
											getListDate(); //延时刷新页面方法
										} else {
											var msg = result.msg || "操作失败"
											layer.msg(msg);
										}
									}
								})
							})
						}
					})
				}  else {
                    layer.msg("请在工作日08:00-21:00内操作");
            	}
                /************************* 收发银行是否一致，更改后台 ********************/
                /************************* 收发银行是否一致，更改后台 ********************/

            });

			/**
			 * [修改订单]
			 */
			$('#PaymentdOrder .buyersMemberChange').unbind("click").click(function (e) {
				var rid = $(this).attr("data-id") 
				window.location.href = "/UserCenterMember/billTransactionBM.htm?"+rid;
			});

		})
	}

	//暴露刷新函数
	module.exports = getListDate;

});