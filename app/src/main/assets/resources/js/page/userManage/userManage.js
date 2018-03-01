//买家操作
define(function(require, exports, module) {
	require('/resources/js/jquery');

        require.async('/resources/js/module/bootstrapSelect/bootstrap-select.min.js', function() {
                require.async('/resources/js/module/bootstrapSelect/bootstrap.min.js', function() {
                        $('.selectpicker').selectpicker({//select 多选配置
                          'selectedText': 'cat'
                        });
                        // $('.buttons1').click(function(){//新增的时候清空多选下拉值
                        //    $('.selectpicker').selectpicker('val', []);
                        // });
                })
        });

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
    changeRadio();
    
	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
			require.async('/resources/js/module/newKkpager/kkpager.js', function() {

                		var tradingOrder = require("/resources/js/page/userManage/userManageList"); // 用户管理
                		//getCount();
                		// tradingOrder()

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

                		})
                	        //  var TimOut
                		$('.tabTitle button').click(function() {
                			if (tabIndex == undefined || tabIndex == 0) {
                				tabIndex = 0
                			}

                			// clearTimeout(TimOut)
                			var tabName = $('.tabTitle a').eq(tabIndex).attr("data-nav");
                			eval(tabName)()
                			//pushTrade();
                			//getCount();
                			var me = this
                			$(me).addClass('rotateIn')
                			setTimeout(function() {
                				$(me).removeClass('rotateIn')
                			}, 1000)

                		})


                	        // //表单验证及提交
                	        // require.async('/resources/js/module/jquery-validation-1.13.0/jquery.validate.min', function() {
                	        //     	require.async(['/resources/js/module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/jquery.form'], function() {
                	        //     		$("#userInfoForm").validate({
                	        //     			rules: {
                	        //     				name: {
                	        //     					required: true
                	        //     				},
                	        //     				mobile: {
                	        //     					required: true,
                	        //     					isNum: true
                	        //     				},
                	        //     				role: {
                	        //     					required: true
                	        //     				}
                	        //     			},
                	        //     			messages: {
                	        //     				name: {
                	        //     					required: "必填"
                	        //     				},
                	        //     				mobile: {
                	        //     					required: "必填",
                	        //     					isNum: "只能输入数字"
                	        //     				},
                	        //     				role: {
                	        //     					required: "必填"
                	        //     				}
                	        //     			},
                	        //     			// 错误时
                	        //     			errorPlacement: function(error, element) {
                	        //     				// $("#errorText span").html(error.html())
                	        //     				element.parents("li").find(".errorText").html(error.html());
                	        //     			},
                	        //     			// 成功时
                	        //     			success: function(element) {
                	        //     				element.parents("li").find(".errorText").html('');
                	        //     			},
                	        //     			submitHandler: function(form) {
                	        //     				// console.log("form")
                	        //     				// console.log(form);
                	        //     				// return;
                	        //     				//window.location.href = "/BillShop/billlist.htm";
                	        //     				$(form).ajaxSubmit({
                	        //     					type: "post",
                	        //     					dataType: 'json',
                	        //     					success: function(data) {
                	        //     						if (data.code == 200) {
                	        //     							layer.msg("保存成功", {
                	        //     								"time": 500
                	        //     							});
                	        //     						} else {
                	        //     							layer.msg(data.msg, {
                	        //     								"time": 1000
                	        //     							});
                	        //     						}
                	        //     					}
                	        //     				})
                	        //     			}
                	        //     		})
                	        //     	})
                	        // });                             



			});
		})
	})


});