//买家-交易订单-交易中
define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件

	//延时刷新页面方法

	function refreshFn() {

		setTimeout(function() {
			getListDate();
		}, 1000)
	}
    function changeRadio(){
        var review = $('.review').val();
        if(review=='20'){
           $('.pay_list_c1').removeClass('on');
           $('#noReview').parent('span').addClass('on'); 
           $('#noReview').attr("checked",true);
           $('#yesReview').attr("checked",false);
        }else if(review=='10'){
           $('.pay_list_c1').removeClass('on');
           $('#yesReview').parent('span').addClass('on'); 
           $('#yesReview').attr("checked",true);
           $('#noReview').attr("checked",false);
        } 
        console.log(review)
    }

	// 数据获取 && 分页
	var getListDate = function() {
		// 插入数据函数
		function insetDate(data) {
			var tpl = require('/resources/tpls/userManage/userManageList.tpl'); //载入tpl模板
			var template = Handlebars.compile(tpl);
			var html = template(data);
			$("#userManageList").html(html);

			operationClick(data); //操作功能函数
			comfn.operationClick(data); //公共操作功能函数
		}
        //模拟数据
        // $.getJSON("/resources/js/test/data.js",function(result){
        //     insetDate(result);
        //  })
        //模拟数据end  
		$.ajax({
			url: "/modules/customer/customerAction/getPersonnelList.htm",
			type: "get",
			dataType: "json",
			data:{
				currentPage:1,
				pageSize:8
			},
			success: function(data) {

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
								url: "/modules/customer/customerAction/getPersonnelList.htm",
								dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
								data:{
									currentPage:n,
									pageSize:data.page.pageSize
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
					$("#kkpager-tradingOrder").html('<p style="text-align:center; margin:20px 0;">暂无数据</p>');
				}
				// End-分页插件

			}
		})
	}
	getListDate();

	// 操作功能函数: 传入操作数据

    /**
	 * [新增]
	 */
    $('.buttons1').bind("click", function(e) {
    	comfn.resetForm($("#userInfoForm"));
    	$('.selectpicker').selectpicker('val', []);
    	$('#userInfoForm').find('input').attr("disabled",false);
        $('#userInfoForm').find('select').attr("disabled",false);
        $('#userInfoForm').find('input[name="mobile"]').attr("readonly",false);
    	layer.open({
    		type: 1, //弹窗类型
    		title: '新增', //标题
    		area: ['550px', '400px'], //大小
    		skin: 'layui-layer-rim', //样式类名
    		closeBtn: 1, //0不显示关闭按钮,1显示
    		btn: ['提交', '返回'], //按钮
    		yes: function(index, layero) { //提交事件
    			var param = $(layero).find('form').serializeObject();
    			if(jQuery.isArray(param.role)){
    				param.role=param.role[0]+','+param.role[1];
    			}
    			var isMobile = (param.mobile.length == 11 && /^0?(13[0-9]|15[012356789]|17[04678]|18[0-9]|14[5768]|16[6]|19[9])[0-9]{8}$/.test(param.mobile));
				if(!param.name){
					layer.msg('请填写真实姓名');
					return;
				}else if(!param.mobile){
					layer.msg('请填写手机号');
					return;
				}else if(!isMobile){
					layer.msg('请填写正确的手机号');
					return;
				}else if(!param.role){
					layer.msg('请填写用户角色');
					return;
				}
    			$.ajax({
    				url: "/modules/customer/customerAction/personnelManagement.htm",
    				type: "post",
    				dataType: "json",
    				data: {
    					customerAccountInfo:JSON.stringify(param)
    				},
    				success: function(result) {
    					if (result.code == 200) {
    						layer.msg(result.msg)
    						layer.closeAll();
    						refreshFn(); //延时刷新页面方法
    					} else {
    						layer.msg(result.msg);
    					}
    				}
    			})
    		},
    		anim: 2, //动画
    		//shadeClose: true, //开启遮罩关闭
    		content: $('.theShelvesTopWarp') //dom元素
    	});
    });

	function operationClick(checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
            /**
			 * [会员权限设置]
			 */
			$('.buttons2').click(function(e) {
				var sentData = {
					id: $(this).attr("data-id"),
					// buyerId: $(this).attr("data-buyerId"),
					receiptId: $(this).attr("data-receiptId"),
					orderStatus: $(this).attr("data-orderStatus"),
				}
				layer.confirm('',{
					title: '会员权限设置', //标题
					icon: 0,
					area: ['400px', '250px'], //大小
					content: $('.permissionWin').html(),
					btn: ['确定', '取消'], //按钮
					success: function(layer) {
						$(".pay_list_c1").on("click",function(){
		                  $(this).addClass("on").siblings().removeClass("on");
		                })
					}
				}, function(index) {
					var radioa = $('.pay_list_c1 input[name="radioa"]:checked ').val();
					$.ajax({
						url: "/modules/customer/customerAction/memberRightsSettings.htm",
						type: "post",
						dataType: "json",
						data: {
							review:radioa
						},
						success: function(result) {
							if (result.code == 200) {
								$('.review').val(radioa);
								changeRadio();
								layer.closeAll();
								//refreshFn(); //延时刷新页面方法
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

            /**
			 * [修改]
			 */
            $('.edit').bind("click", function(e) {
            	var record = $(this).attr("data-record");
            	record = JSON.parse(record);
            	if(record.role){
            		record.role=record.role.split(',');
            	}
            	$("#userInfoForm").setForm(record);
            	$('.selectpicker').selectpicker('val', record.role);//赋值多选下拉框
            	$('#userInfoForm').find('input').attr("disabled",false);
            	$('#userInfoForm').find('select').attr("disabled",false);
            	$('#userInfoForm').find('input[name="mobile"]').attr("readonly",true);
            	layer.open({
            		type: 1, //弹窗类型
            		title: '修改', //标题
            		area: ['550px', '400px'], //大小
            		skin: 'layui-layer-rim', //样式类名
            		closeBtn: 1, //0不显示关闭按钮,1显示
            		btn: ['提交', '返回'], //按钮
            		yes: function(index, layero) { //提交事件
            			var param = $(layero).find('form').serializeObject();
            			
            			if(jQuery.isArray(param.role)){
            				param.role=param.role[0]+','+param.role[1];
            			}
            			var isMobile = (param.mobile&&param.mobile.length == 11 && /^0?(13[0-9]|15[012356789]|17[04678]|18[0-9]|14[5768]|16[6]|19[9])[0-9]{8}$/.test(param.mobile));
						if(!param.name){
							layer.msg('请填写真实姓名');
							return;
						}else if(!param.mobile){
							layer.msg('请填写手机号');
							return;
						}else if(!isMobile){
							layer.msg('请填写正确的手机号');
							return;
						}else if(!param.role){
							layer.msg('请填写用户角色');
							return;
						}
            			$.ajax({
            				url: "/modules/customer/customerAction/personnelManagement.htm",
            				type: "post",
            				dataType: "json",
            				data: {
            					customerAccountInfo:JSON.stringify(param)
            				},
            				success: function(result) {
            					if (result.code == 200) {
            						layer.msg(result.msg)
            						layer.closeAll();
            						refreshFn(); //延时刷新页面方法
            					} else {
            						layer.msg(result.msg);
            					}
            				}
            			})
            		},
            		anim: 2, //动画
            		//shadeClose: true, //开启遮罩关闭
            		content: $('.theShelvesTopWarp') //dom元素
            	});
            });

            /**
			 * [查看]
			 */
            $('.lookUp').bind("click", function(e) {
            	$('#userInfoForm').find('input').attr("disabled",true);
            	$('#userInfoForm').find('select').attr("disabled",true);
            	var record = $(this).attr("data-record");
            	record = JSON.parse(record);
            	if(record.role){
            		record.role=record.role.split(',');
            	}
            	$("#userInfoForm").setForm(record);
            	$('.selectpicker').selectpicker('val', record.role);//赋值多选下拉框
            	layer.open({
            		type: 1, //弹窗类型
            		title: '查看', //标题
            		area: ['550px', '400px'], //大小
            		skin: 'layui-layer-rim', //样式类名
            		closeBtn: 1, //0不显示关闭按钮,1显示
            		btn: '返回', //按钮
            		yes: function(index, layero) { //提交事件
            			layer.closeAll(); //关闭弹窗
            		},
            		anim: 2, //动画
            		//shadeClose: true, //开启遮罩关闭
            		content: $('.theShelvesTopWarp') //dom元素
            	});
            });

            /**
			 * [锁定]
			 */
			 $('.locking').bind("click", function(e) {
			 	    var id = $(this).attr("data-id");
			 	    var state = $(this).attr("data-state");
			 	    if(state=='10'){
			 	    	state='20'
			 	    }else{
			 	    	state='10'
			 	    }
		            $.ajax({
		            	url: "/modules/customer/customerAction/updateState.htm",
		            	type: "post",
		            	dataType: "json",
		            	data: {
		            		id:id,
		            		state:state
		            	},
		            	success: function(result) {
		            		if (result.code == 200) {
		            			layer.msg(result.msg)
		            			refreshFn(); //延时刷新页面方法
		            		} else {
		            			layer.msg(result.msg);
		            		}
		            	}
		            })
		    });    
		    
		})//外
	}

	//暴露刷新函数
	module.exports = getListDate;

});