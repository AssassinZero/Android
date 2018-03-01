//票据管理-持有中
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	// 表单转化成json
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
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

		setTimeout(function() {
			getListDate();
			//pushTrade();
			//getCount();
		}, 1000)
	}
    
    //勾选方法
	function checkFn() {//列表多选checkbox-方法3/2
		var ids = window.ids;
		$('.listBoxs label').click(function() {
			if($(this).hasClass('onActive')){
			    $(this).removeClass('onActive');
			    ids.remove($(this)[0].htmlFor);
			    console.log(ids)
			}else{
			    $(this).addClass('onActive');
			    if(ids.indexOf($(this)[0].htmlFor)<0){
			        ids.push($(this)[0].htmlFor);
			    }
			    console.log(ids)
			} 
            checkAllFn();
		})
	}
	//全选按钮-判断是否全选
	function checkAllFn() {//列表多选checkbox-方法3/3
		var scope = $('.listBoxs label');	
		var checkedAll=true;
		$.each(scope, function() {
            if(!$(this).hasClass('onActive')){
            	checkedAll=false
            }
		})
		if(checkedAll){
            $('#checkAll').attr("checked", true);
            $('#checkAllLabel').addClass('onActive');
		}else{
			$('#checkAll').attr("checked", false);
			$('#checkAllLabel').removeClass('onActive');
		}
	}

	// 数据获取 && 分页
	var getListDate = function() {
		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/myNotes/tradingOrder.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#tradingOrder").html(html);
			// console.log("getListDate")
			checkFn();
			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
        //模拟数据
        // $.getJSON("/resources/js/test/data.js",function(result){
        //     insetDate(result);
        //     $(".buyTrading").html(result.data.length)
        //  })
        //模拟数据end  
		$.ajax({
			url: "/modules/billorder/action/bizBillCurrentMsgAction/hold.htm",
			type: "get",
			data:{
				currentPage:1,
				pageSize:8
			},
			dataType: "json",
			success: function(data) {
                comfn.setSmallScale(data.page.total,$(".buyTrading"));//设置小标数
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
						click: function(n, total, totalRecords) {
							$.ajax({
								type: "get",
								url: "/modules/billorder/action/bizBillCurrentMsgAction/hold.htm",
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
                                data:{
                                    currentPage:n,
                                    pageSize:data.page.pageSize
                                },
								success: function(data) {
                                    comfn.setSmallScale(data.page.total,$(".buyTrading"));//设置小标数
									//------- 插入数据 strat
									insetDate(data);
									//------- 插入数据 End
                                    checkAllFn();
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

    /**
	 * [批量上架]
	 */
    $('.buttons1').click(function() {
        comfn.scrollToTop();
    	window.btnType='batch';
    	var ids = window.ids;
    	ids.remove('checkAll');//删除数组中全选标识
    	console.log(ids)
    	if(ids.length<1){
    		layer.msg('请选择票据', {
    			time: 1000
    		})
    	}else{
    		$.ajax({
    		    type: "get",
    		    url: "/modules/billorder/action/bizBillCurrentMsgAction/billShelvesQueryTwo.htm",
    		    data:{
    		    	billNos:JSON.stringify(ids)
    		    },
    		    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
    		    success: function(result) {
    		       var tpl = $('#theShelvesTableTpl').val();
    		       var template = Handlebars.compile(tpl);
    		       var html = template(result);
    		       $(".theShelvesTbody").html(html);
    		    },
    		    error: function(result) {
    		        console.log('ajaxError')
    		    }
    		});
    		$('.userContent').css('display','none');
    		$('.theShelvesTopWarp.frist').css('display','block');
    	}
    })
    /**
	 * [上架-返回]
	 */
    $('.theShelvesBack').click(function() {
        comfn.scrollToTop();
        comfn.resetForm($("#userInfoForm"))//重置form
    	$('.theShelvesTopWarp.frist').css('display','none');
    	$('.userContent').css('display','block');
    })    
    /**
	 * [批量纯票交易]
	 */
    $('.buttons2').click(function() {
        comfn.scrollToTop();
    	var ids = window.ids;
        ids.remove('checkAll');//删除数组中全选标识
        console.log(ids)
    	if(ids.length<1){
    		layer.msg('请选择票据', {
    			time: 1000
    		})
    	}else{
    		$.ajax({
    		    type: "get",
    		    url: "/modules/billorder/action/bizBillCurrentMsgAction/billShelvesQueryTwo.htm",
    		    data:{
    		    	billNos:JSON.stringify(ids)
    		    },
    		    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
    		    success: function(result) {
    		       var tpl = $('#theShelvesTableTpl2').val();
    		       var template = Handlebars.compile(tpl);
    		       var html = template(result);
    		       $(".theShelvesTbody").html(html);
    		    },
    		    error: function(result) {
    		        console.log('ajaxError')
    		    }
    		});
    		$('.userContent').css('display','none');
    		$('.theShelvesTopWarp.second').css('display','block');
    	}
    })
    /**
	 * [纯票交易-返回] 
	 */
    $('.superviseBack').click(function() {
        comfn.scrollToTop();
    	comfn.resetForm($("#userInfoForm2"));//重置表单
    	$('.theShelvesTopWarp.second').css('display','none');
    	$('.userContent').css('display','block');
    }) 
    /**
	 * [票据查看-返回] 
	 */
    $('.billBack').click(function() {
        comfn.scrollToTop();
    	comfn.resetForm($("#userInfoForm3"));
    	$('.theShelvesTopWarp.third').css('display','none');
    	$('.userContent').css('display','block');
    })

	// 操作功能函数: 传入操作数据

	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
            
            /**
			 * [单个上架]
			 */
			$('.theShelves').click(function(e) {
            	console.log($(this).attr("data-id"));
                comfn.scrollToTop();
            	window.btnType='single';
            	window.id=[];
            	window.id.push($(this).attr("data-id"));
            	$.ajax({
            	    type: "get",
            	    url: "/modules/billorder/action/bizBillCurrentMsgAction/billShelvesQuery.htm",
            	    data:{
            	    	billNo:$(this).attr("data-id")
            	    },
            	    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
            	    success: function(result) {
            	       var tpl = $('#theShelvesTableTpl').val();
            	       var template = Handlebars.compile(tpl);
                       var list = [];
                       list.push(result.data);
                       result.data = list;
            	       var html = template(result);
            	       $(".theShelvesTbody").html(html);
            	    },
            	    error: function(result) {
            	        console.log('ajaxError')
            	    }
            	});
        		$('.userContent').css('display','none');
        		$('.theShelvesTopWarp.frist').css('display','block');
            }) 
            /**
			 * [单个纯票交易]
			 */
			$('.unregulatedTransaction').click(function(e) {
            	console.log($(this).attr("data-id"));
                comfn.scrollToTop();
            	window.btnType='single';
            	window.id=[];
            	window.id.push($(this).attr("data-id"));
            	$.ajax({
            	    type: "get",
            	    url: "/modules/billorder/action/bizBillCurrentMsgAction/billShelvesQuery.htm",
            	    data:{
            	    	billNo:$(this).attr("data-id")
            	    },
            	    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
            	    success: function(result) {
            	       var tpl = $('#theShelvesTableTpl2').val();
            	       var template = Handlebars.compile(tpl);
                       var list = [];
                       list.push(result.data);
                       result.data = list;
            	       var html = template(result);
            	       $(".theShelvesTbody").html(html);
            	    },
            	    error: function(result) {
            	        console.log('ajaxError')
            	    }
            	});
        		$('.userContent').css('display','none');
        		$('.theShelvesTopWarp.second').css('display','block');
            }) 
            /**
			 * [票据查看]
			 */
			$('#tradingOrder .lookUp').click(function(e) {
            	console.log($(this).attr("data-id"))
                comfn.scrollToTop();
            	$("#userInfoForm3")[0].reset();
            	$('.theShelvesTopWarp.third .theShelvesTop p').text('票据查看');
                $('.bearerInfor').css("display",'block');
                $('.quoteInfor').css("display",'none');
            	$('.lookNote-li').css("display",'block');
                $('.lookNote-li').attr("data-id",$(this).attr("data-id"));
            	$('.theShelvesTopWarp.third input').attr("disabled",true);
            	$('.theShelvesTopWarp.third input[type="button"]').attr("disabled",false);
            	$('.theShelvesTopWarp.third input[type="submit"]').css("display",'none');
        		$('.userContent').css('display','none');
        		$('.theShelvesTopWarp.third').css('display','block');
        		$.ajax({
        		    type: "get",
        		    url: "/modules/billorder/action/bizBillCurrentMsgAction/query.htm",
        		    data:{
        		    	billNo:$(this).attr("data-id")
        		    },
        		    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
        		    success: function(result) {
                        result.data.faceAmount=Number((result.data.faceAmount/10000).toFixed(6));
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

		})
	}

	//暴露刷新函数
	module.exports = getListDate;

});