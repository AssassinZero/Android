//票据商城-列表
define(function (require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	//获取企业搜索
	$.ajax({
		type: "get",
		url: "/modules/billorder/action/bizBillCurrentMsgMarketAction/getCountEnterprise.htm",
		dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
		data: {
			whether: 0
		},
		success: function (result) {

			require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
				require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
					var tpl = require('/resources/tpls/billList/enterpriseName.tpl'); //载入tpl模板
					var template = Handlebars.compile(tpl);
					var html = template(result);
					$(".warplist").html(html);
					$("#enterpriseName dd:first").addClass("selected");
					$("#enterpriseName dd").on("click", enterpriseNameclick);
					//默认加载全部
					var shelvesTimeVal = $("#shelvesTime dd.selected a").attr("data-val");
					var dueDateVal = $("#dueDate dd.selected a").attr("data-val");
					var faceAmountVal = $("#faceAmount dd.selected a").attr("data-val");
					var typeVal = $("#type dd.selected a").attr("data-val");
					var enterpriseNameVal = $("#enterpriseName dd.selected a").attr("data-val");
					var loading = "shelvesTime=" + shelvesTimeVal + "&dueDate=" + dueDateVal + "&faceAmount=" + faceAmountVal + "&type=" + typeVal + "&enterpriseName=" + enterpriseNameVal;
					ajaxUrl(loading);//初始化调用
				});
			});
		},
	});

	//点击加载更多
	$(".more").click(function () {
		if ($(this).hasClass('onActive')) {
			$(this).removeClass('onActive');
			$(this).html("更多");
			$(".company").addClass("companyhidden");
			$(".company .warplist").removeClass("overflow");
		} else {
			$(this).addClass('onActive');
			$(this).html("收起");
			$(".company").removeClass("companyhidden");
			$(".company .warplist").addClass("overflow");
		}
	});

	$(".more").click(function () {
		$(".Nav .NavWhite").toggle();
	});

	//发布日期
	$("#shelvesTime dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		var param = "shelvesTime=" + $("#shelvesTime dd.selected a").data("val") + "&dueDate=" + $("#dueDate dd.selected a").data("val") + "&faceAmount=" + $("#faceAmount dd.selected a").data("val") + "&type=" + $("#type dd.selected a").data("val") + "&enterpriseName=" + $("#enterpriseName dd.selected a").data("val");
		ajaxUrl(param);
		$(".shopmian #batchOrder").removeClass("Order");
		$(".shopmian #batchOrder").html("批量下单");
	});
	//到期期限
	$("#dueDate dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		var param = "shelvesTime=" + $("#shelvesTime dd.selected a").data("val") + "&dueDate=" + $("#dueDate dd.selected a").data("val") + "&faceAmount=" + $("#faceAmount dd.selected a").data("val") + "&type=" + $("#type dd.selected a").data("val") + "&enterpriseName=" + $("#enterpriseName dd.selected a").data("val");
		ajaxUrl(param);
		$(".shopmian #batchOrder").removeClass("Order");
		$(".shopmian #batchOrder").html("批量下单");
	});
	//票面金额
	$("#faceAmount dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		var param = "shelvesTime=" + $("#shelvesTime dd.selected a").data("val") + "&dueDate=" + $("#dueDate dd.selected a").data("val") + "&faceAmount=" + $("#faceAmount dd.selected a").data("val") + "&type=" + $("#type dd.selected a").data("val") + "&enterpriseName=" + $("#enterpriseName dd.selected a").data("val");
		ajaxUrl(param);
		$(".shopmian #batchOrder").removeClass("Order");
		$(".shopmian #batchOrder").html("批量下单");
	});
	//票据类型
	$("#type dd").click(function () {
		$(this).addClass("selected").siblings().removeClass("selected");
		var param = "shelvesTime=" + $("#shelvesTime dd.selected a").data("val") + "&dueDate=" + $("#dueDate dd.selected a").data("val") + "&faceAmount=" + $("#faceAmount dd.selected a").data("val") + "&type=" + $("#type dd.selected a").data("val") + "&enterpriseName=" + $("#enterpriseName dd.selected a").data("val");
		ajaxUrl(param);
		$(".shopmian #batchOrder").removeClass("Order");
		$(".shopmian #batchOrder").html("批量下单");
	});

	//所属企业
	function enterpriseNameclick() {
		$(this).addClass("selected").siblings().removeClass("selected");
		var param = "shelvesTime=" + $("#shelvesTime dd.selected a").data("val") + "&dueDate=" + $("#dueDate dd.selected a").data("val") + "&faceAmount=" + $("#faceAmount dd.selected a").data("val") + "&type=" + $("#type dd.selected a").data("val") + "&enterpriseName=" + $("#enterpriseName dd.selected a").data("val");
		ajaxUrl(param);
		$(".shopmian #batchOrder").removeClass("Order");
		$(".shopmian #batchOrder").html("批量下单"); 
		
		$(".more").removeClass('onActive');
		$(".more").html("更多");
		$(".company").addClass("companyhidden");
		$(".Nav .NavWhite").hide();
		$(".company .warplist").removeClass("overflow");
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
					console.log(ids)
				}
			}
		})
	}

	//采用正则表达式获取地址栏参数
	function GetRequest(param) { 
		var theRequest = new Object();
		var str = param;
		strs = str.split("&");
		for (var i = 0; i < strs.length; i++) { 
			theRequest[strs[i].split("=")[0]]=strs[i].split("=")[1]
		} 
		return theRequest; 
	}
	//列表数据获取 
	function ajaxUrl(param) {
		console.log(param)
		$.ajax({
			url: encodeURI('/modules/billorder/action/bizBillCurrentMsgMarketAction/marketList.htm?' + param),
			type: "get",
			dataType: "json",
			data: {
				currentPage: 1,
				pageSize: 10
			},
			success: function (data) {

				require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
					require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
						require.async(['/resources/js/module/newKkpager/kkpager.css', '/resources/js/module/newKkpager/kkpager.js'], function () {
							//------- 插入数据 strat
							var tpl = require('/resources/tpls/billList/billList.tpl'); //载入tpl模板
							var template = Handlebars.compile(tpl);
							var html = template(data);
							$("#shoplist").html(html);
							checkFn();
							//------- 插入数据 End

							var url = "/modules/billorder/action/bizBillCurrentMsgMarketAction/getCountEnterprise.htm?" + param;
							var enterpriseName = GetRequest(param).enterpriseName;
							var shelvesTime = GetRequest(param).shelvesTime;
							var dueDate = GetRequest(param).dueDate;
							var faceAmount = GetRequest(param).faceAmount;
							var type = GetRequest(param).type;  
							
							if (shelvesTime!="0"||dueDate!="0"||faceAmount!="0"||type!="0"){
								var whether=1
							}else{
								var whether=0
							} 
							$.ajax({
								type: "get",
								url: url,
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								data: {
									whether: whether
								},
								success: function (result) {
									var tpl = require('/resources/tpls/billList/enterpriseName.tpl'); //载入tpl模板
									var template = Handlebars.compile(tpl);
									var html = template(result);
									$(".warplist").html(html);
									$(".warplist dd a").map(function () {
										if ($(this).data("val") == enterpriseName) {
											$(this).parent().addClass("selected");
										}
									});
									$("#enterpriseName dd").on("click", enterpriseNameclick);
								},
							});


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
									click: function (n, total, totalRecords) {
										$.ajax({
											type: "get",
											url: "/modules/billorder/action/bizBillCurrentMsgMarketAction/marketList.htm?" + param,
											dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
											data: {
												currentPage: n,
												pageSize: data.page.pageSize
											},
											success: function (data) {
												//------- 插入数据 strat
												var tpl = require('/resources/tpls/billList/billList.tpl'); //载入tpl模板
												var template = Handlebars.compile(tpl);
												var html = template(data);
												$("#shoplist").html(html);
												if ($(".shopmian #batchOrder").hasClass('Order')) {
													$(".list-uli label.active").css('display', 'inline-block');
												}
												checkFn();
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
						})
					})
				})
			}
		})
	}

	// 确定下单
	$(".shopmian #batchOrder").click(function () {
		if ($(this).hasClass('Order')) {
			$.ajax({
				url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/preBillBatch.htm?billNos=' + JSON.stringify(ids),
				type: "get",
				dataType: "json",
				success: function (data) {
					require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
						if (data.code == 200) {
							window.location.href = "/BillShop/billOrder.htm?checkAll," + ids;
						} else {
							layer.confirm(data.msg, {
								title: '确定下单', //标题
								icon: 0,
								// area: ['150px', '180px'], //大小
								btn: ['确定'] //按钮
							}, function (index) {
								layer.close(index);
							});
						}
					});
				}
			})
		} else {
			$(this).addClass("Order");
			$(this).html("确定下单");
			$(".list-uli label.active").css('display', 'inline-block');
		}
	});


});

