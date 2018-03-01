// 卖家-卖出票据
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 数据获取 && 分页
	var getListDate = function() {

		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/ElectricityOrders/SellingBill.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#SellingBill").html(html);
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		} 
		
		$.ajax({
			url: "/modules/billorder/action/bizOnlineOrderAction/getPageListTwo.htm",
			type: "get",
			dataType: "json",
			data: {
				currentPage: 1,
				pageSize: 5
			},
			success: function(data) {

				//------- 插入数据 strat
				insetDate(data);
				$(".buyTraded").html(data.page.total)
				//------- 插入数据 End

				// start-分页插件
				if (data.page.total > 0) {
					var obj11 = {
						pagerid: "kkpager-SellingBill",
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
								url: "/modules/billorder/action/bizOnlineOrderAction/getPageListTwo.htm",
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式 
								data: {
									currentPage: n,
									pageSize: data.page.pageSize
								},
								success: function(data) {

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
					$("#kkpager-SellingBill").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
				}
				// End-分页插件

			}
		})
	}
	getListDate();
	// 操作功能函数: 传入操作数据
 

	var businessStateList = {
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
		"0012": "待卖方确认"
	}

	//现有账户-查询银行详细信息
	function sendbankAccount(value) {
		console.log(value)
		$.ajax({
			url: "/modules/billorder/action/bizBillCurrentMsgMarketAction/getCustomerBankAccountInfo.htm",
			data: {
				bankAccount: value,
			},
			type: "get",
			dataType: "json",
			success: function (result) {
				var data = result.data;
				$("#accountNameOwn").val(data.accountName);
				$("#openingBankOwn").val(data.subbarnchBankName);
				$("#bankNumberOwn").val(data.subbranchBankCode);
			}
		});
	}

	$.ajax({
		url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/getBizCustomerBankAccountByiphone.htm',
		type: "get",
		dataType: "json",
		success: function (result) {
			var data = result.data;
			var len = data.length;
			var str = "";

			for (i = 0; i < len; i++) {
				str += "<li data-value='" + data[i].bankAccount + "'>" + data[i].bankAccount + "</li>";
			}
			$("#bankAccountTwoList").append(str);
			//现有账户-查询银行详细信息
			$("#bankAccountTwoList li").click(function (e) {
				var value = $(this).attr('data-value');
				sendbankAccount(value)
			})
		}
	})

	// 操作功能函数: 传入操作数据

	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {


            /**
				* [票据查看]
				*/ 
			$('#SellingBill .lookUp').click(function (e) {
				console.log($(this).attr("data-id"))
				$('.theShelvesTopWarp.third .theShelvesTop p').text('票据查看');
				$('.lookNote-li').css("display", 'none');
				$('.theShelvesTopWarp.third input').attr("disabled", true);
				$('.theShelvesTopWarp.third input[type="button"]').attr("disabled", false);
				$('.theShelvesTopWarp.third input[type="submit"]').css("display", 'none');
				$('.userContent').css('display', 'none');
				$('.theShelvesTopWarp.third').css('display', 'block');
				$.ajax({
					type: "post",
					url: "/modules/billorder/action/bizOnlineOrderAction/getDetails.htm",
					data: {
						orderNo: $(this).attr("data-id"),
					},
					dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
					success: function (result) {
						var data = result.data;
						var tpl = $('#theShelvesTableTpl').val();
						var template = Handlebars.compile(tpl); 
						var html = template(data.bizOnlineOrderBillsList);
						$(".theShelvesTbody").html(html);
						// data.businessState = businessStateList[data.businessState];
						if(data.yearRate){
							data.yearRate = Number(data.yearRate*100).toFixed(4);
						}
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
						data.enterpriseNameEndorsed=data.enterpriseNameEndorsed?data.enterpriseNameEndorsed:'暂无'; 
						$("#userInfoForm3").setForm(data); 
					}
				});
			})


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


			/**
			 * [背书]
			 */
			$('#SellingBill .SellingBillRecite').unbind("click").click(function (e) {
				var rid = $(this).attr("data-id");
				$.ajax({
					url: "/modules/billorder/action/bizOnlineOrderAction/getDetails.htm",
					type: "post",
					dataType: "json",
					data: {
						orderNo: rid,
					},
					success: function (result) {
						var data = result.data;
						var tpl = $('#theShelvesTableTpl').val();
						var template = Handlebars.compile(tpl); 
						var html = template(data.bizOnlineOrderBillsList);
						$(".theShelvesTbody").html(html);
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
						$("#userInfoForm3").setForm(data); 
						$('.theShelvesTopWarp.third .theShelvesTop p').text('背书');
						$('.lookNote-li').css("display", 'none');
						$('.theShelvesTopWarp.third input').attr("disabled", true);
						$('.theShelvesTopWarp.third input[type="button"]').attr("disabled", false);
						$('.theShelvesTopWarp.third input[type="submit"]').css("display", 'none');
						$('.userContent').css('display', 'none');
						$('.theShelvesTopWarp.third').css('display', 'block');
						$('#submitLi').css("display", 'none');
						$('#confirmLi').css("display", 'block');
						//背书
						$('.confirmbt').click(function () {
                            var now = new Date();
                            if (now.getDay() > 0 && now.getDay() < 6&&now.getHours()>7&&now.getHours()<21) {
								$.ajax({
                                    url: "/modules/billorder/action/bizOnlineOrderAction/recite.htm",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        orderNo: rid,
                                    },
                                    success: function (result) {
                                        if (result.code == 200) {
                                            layer.msg("背书成功", {
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
                            }else {
                                layer.msg("请在工作日08:00-21:00内操作");
                            }
                                /************************* 收发银行是否一致，更改后台 ********************/
                                /************************* 收发银行是否一致，更改后台 ********************/
						}) 
					}
				})
			});




		})
	}


	//暴露刷新函数
	module.exports = getListDate;

});