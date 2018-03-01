// 已上架
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 数据获取 && 分页
	var getListDate = function() {

		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/myNotes/tradedOrder.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#tradedOrder").html(html);
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
        //模拟数据
        // $.getJSON("/resources/js/test/data.js",function(result){
        //     insetDate(result);
        //     $(".buyTraded").html(result.data.length)
        //  })
        //模拟数据end  
		$.ajax({
			url: "/modules/billorder/action/bizBillCurrentMsgAction/shelves.htm",
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
								url: "/modules/billorder/action/bizBillCurrentMsgAction/shelves.htm",
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
	 * [票据修改/查看-返回] 
	 */
    $('.billBack').click(function() {
    	comfn.scrollToTop();
    	var ids = window.ids;
    	comfn.resetForm($("#userInfoForm3"));
    	$('.theShelvesTopWarp.third').css('display','none');
    	$('.userContent').css('display','block');
    })
	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
             /**
 			 * [票据修改]
 			 */
 			$('.modify').click(function(e) {
             	console.log($(this).attr("data-id"));
             	$('.theShelvesTopWarp.third .theShelvesTop p').text('票据修改');
             	$('.quoteInfor').css("display",'block');
             	$('.shouldPayAmount').css("display",'block');
             	$('.originalMoney2').css("display",'none');
             	
             	$('.originalMoney').css("display",'block');
             	$('.bearerInfor').css("display",'none');
             	$('.lookNote-li').css("display",'none');
             	$('.theShelvesTopWarp.third input').attr("disabled",true);
             	$('.theShelvesTopWarp.third .quoteInfor input').attr("disabled",false);
             	$('.theShelvesTopWarp.third input[type="button"]').attr("disabled",false);
             	$('.theShelvesTopWarp.third input[type="submit"]').css("display",'block');
            	$('.theShelvesTopWarp.third input[type="submit"]').attr("disabled",false);
         		$('.userContent').css('display','none');
         		$('.theShelvesTopWarp.third').css('display','block');
         		$('.originalMoney2 input').attr("disabled",true);
         		$.ajax({
        		    type: "get",
        		    url: "/modules/billorder/action/bizBillCurrentMsgAction/queryShelves.htm",
        		    data:{
        		    	billNo:$(this).attr("data-id")
        		    },
        		    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
        		    success: function(result) {
        		    	if(result.data.faceAmount){
        		    		result.data.faceAmount=Number((result.data.faceAmount/10000).toFixed(6));
        		        }
        		        if(result.data.yearRate){
        		    		result.data.yearRate =Number((result.data.yearRate*100).toFixed(4));
        		    	}
        		    	if(result.data.originalMoney){
        		    		result.data.originalMoney =Number(Number(result.data.originalMoney).toFixed(2));
        		    	}
        		        $("#userInfoForm3").setForm(result.data);
        		    },
        		    error: function(result) {
        		        console.log('ajaxError')
        		    }
        		});
            }) 

             /**
 			 * [票据查看]
 			 */
 			$('#tradedOrder .lookUp').click(function(e) {
             	console.log($(this).attr("data-id"));
             	comfn.scrollToTop();
             	$('.theShelvesTopWarp.third .theShelvesTop p').text('票据查看');
             	$('.quoteInfor').css("display",'block');
             	$('.bearerInfor').css("display",'block');
             	$('.lookNote-li').css("display",'block');
                $('.lookNote-li').attr("data-id",$(this).attr("data-id"));
             	$('.originalMoney').css("display",'none');
             	$('.shouldPayAmount').css("display",'none');
             	$('.originalMoney2').css("display",'block');
             	$('.theShelvesTopWarp.third input').attr("disabled",true);
             	$('.theShelvesTopWarp.third input[type="button"]').attr("disabled",false);
             	$('.theShelvesTopWarp.third input[type="submit"]').css("display",'none');
         		$('.userContent').css('display','none');
         		$('.theShelvesTopWarp.third').css('display','block');
         		$('.originalMoney input').attr("disabled",true);
         		$.ajax({
        		    type: "get",
        		    url: "/modules/billorder/action/bizBillCurrentMsgAction/queryShelves.htm",
        		    data:{
        		    	billNo:$(this).attr("data-id")
        		    },
        		    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
        		    success: function(result) {
        		    	if(result.data.faceAmount){
        		    		result.data.faceAmount=Number((result.data.faceAmount/10000).toFixed(6));
        		    	}
        		    	if(result.data.yearRate){
        		    		result.data.yearRate =Number((result.data.yearRate*100).toFixed(4));
        		        }
        		        if(result.data.originalMoney){
        		    		result.data.originalMoney =Number(Number(result.data.originalMoney).toFixed(2));
        		    	}
        		        $("#userInfoForm3").setForm(result.data);
        		    },
        		    error: function(result) {
        		        console.log('ajaxError')
        		    }
        		});
                $('.lookNote-li').click(function(e) {
                    var billNo = $(this).attr("data-id");
                    window.open('/UserCenterWin/bill.htm?billNo='+billNo);
                })
            }) 
            
            
            /**
			 * [下架]
			 */
			$('.offTheShelf').click(function(e) {
				comfn.scrollToTop();
                var billNo = $(this).attr("data-id");
				layer.confirm("票据下架后若需要重新上架，请至未上架页面操作，是否继续？", {
					title: '下架', //标题
					icon: 0,
					// area: ['150px', '180px'], //大小
					btn: ['确定', '返回'] //按钮
				}, function(index) {
					$.ajax({
						url: "/modules/billorder/action/bizBillCurrentMsgAction/lower.htm",
						type: "post",
						data: {
							billNo:billNo
						},
						dataType: "json",
						success: function(result) {
							if (result.code == 200) {
								layer.msg("下架成功", {
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