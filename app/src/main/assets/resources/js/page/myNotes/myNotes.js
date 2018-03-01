//票据管理
define(function(require, exports, module) {
	require('/resources/js/jquery');
    require('/resources/js/commonFn');
     

    $('input[name="quotationMethod"]').click(function(){//报价方式
        var value = $(this).val();
        if(value=='01'||value=='10'){
            $('input[name="discount"]').parent().addClass('hideActive');
            $('input[name="yearRate"]').parent().removeClass('hideActive');
        }else{
            $('input[name="yearRate"]').parent().addClass('hideActive');
            $('input[name="discount"]').parent().removeClass('hideActive');
        }
        
    })

    $('input[name="isDiscussPersonally"]').click(function(){//是否显示面议
        var value = $(this).val();
        if(value=='01'||value=='10'){
            $('.faceHide').css('display','none');
        }else{
            $('.faceHide').css('display','block');
        }
        
    })



	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
            $.ajax({
                type: "get",
                url: "/modules/billorder/action/bizBillCurrentMsgAction/statistics.htm",
                dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
                success: function(result) {
                   var tpl = $('#notesInfoTpl').val();
                   var template = Handlebars.compile(tpl);
                   var html = template(result.data[0]);
                   $(".notesInfo").html(html);
                },
                error: function(result) {
                    console.log('ajaxError')
                }
            });
            $('#bankMain').change(function(){
                var restData ={};
                restData.analogueOpeningBankText='';
                restData.analogueOpeningBank='';
                restData.analogueBankNumber='';
                $("#userInfoForm2").setForm(restData); //切换总行清空支行
                $("#holdAccountSubbranchNamelist").html(''); 
            });
            
            //新增账户-开户银行
            $.ajax({
                url: "/modules/common/action/bizBaseAction/getBankList.htm",
                type: "get",
                dataType: 'json',
                success: function (result) {
                    window.bankData=result.data;
                    var data = result.data;
                    var len = data.length;
                    var str = "";

                    for (i = 0; i < len; i++) {
                        str += "<li data-value='" + data[i].bankCode + "'>" + data[i].bankName + "</li>";
                    }
                    $("#holdAccountCodelist").html(str);
                    $("#holdAccountCodelist2").html(str);
                    //新增账户-开户支行名称 
                    $("#holdAccountCodelist li").click(function (e) {
                        var restData ={};
                        restData.analogueOpeningBankText='';
                        restData.analogueOpeningBank='';
                        restData.analogueBankNumber='';
                        $("#userInfoForm2").setForm(restData); //切换总行清空支行
                        $("#holdAccountSubbranchNamelist").html(''); 
                        // var value = $(this).attr('data-value');
                        // sendholdAccountCode(value);
                    });
                }
            });
            function isChinese(temp){//判断是否是中文
                if (/[^\u4e00-\u9fa5]/.test(temp)) {
                  return false;
                }else{
                  return true
                }
            } 
            //新增账户-开户支行名称 
            function sendholdAccountCode(value) {
                var bankName = $("#holdAccountSubbranchNameText").val();
                if (bankName && value) {
                    $.ajax({
                        url: "/modules/common/action/bizBaseAction/getBankDetailList.htm",
                        type: "get",
                        dataType: 'json',
                        data: {
                            bankCode: value,
                            bankName: bankName
                        },
                        success: function (result) {
                            var data = result.data;
                            var len = data.length;
                            var str = "";  
                            for (i = 0; i < len; i++) {
                                str += "<li title='" + data[i].bankName + "' data-value='" + data[i].bankNo + "'>" + data[i].bankName + "</li>";
                            } 
                            $("#holdAccountSubbranchNamelist").html(str);
                            $("#holdAccountSubbranchNamelist li").click(function (e) {
                                var value = $(this).attr('data-value');
                                $("#holdAccountSubbranchCode").val(value);
                            });
                        }
                    });
                }
            }
 
            $("#holdAccountSubbranchNameText").on("propertychange input", sendholdAccountCode2);
            function sendholdAccountCode2() {
                var bankCode = $("#holdAccountCodeid").val();
                var bankName = $("#holdAccountSubbranchNameText").val();
                var banklen = bankName.length;
                if (bankName&&isChinese(bankName) && bankCode && banklen>1&&bankName!='中国'&&bankName!='银行'&&bankName!='工商') {//只有为中文长度大于1时执行
                    $.ajax({
                        url: "/modules/common/action/bizBaseAction/getBankDetailList.htm",
                        type: "get",
                        dataType: 'json',
                        data: {
                            bankCode: bankCode,
                            bankName: bankName
                        },
                        success: function (result) {
                            var data = result.data;
                            var len = data.length;
                            var str = ""; 
                            if(len>0){
    	                        for (i = 0; i < len; i++) {
    	                            str += "<li title='" + data[i].bankName + "' data-value='" + data[i].bankNo + "'>" + data[i].bankName + "</li>";
    	                        } 
                            }else{
                            	str="<a>没有相关银行信息</a>"
                            }
                            $("#holdAccountSubbranchNamelist").html(str);
                            $("#holdAccountSubbranchNamelist li").click(function (e) {
                                var value = $(this).attr('data-value');
                                $("#holdAccountSubbranchCode").val(value);
                            })
                        }
                    });
                }
            } 

			require.async('/resources/js/module/newKkpager/kkpager.js', function() {

                		var tradingOrder = require("/resources/js/page/myNotes/tradingOrder"); // 持有中
                		var tradedOrder = require("/resources/js/page/myNotes/tradedOrder"); // 已上架
                		var tradeClosed = require("/resources/js/page/myNotes/tradeClosed"); // 历史票据
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
                		// $('.tabTitle button').click(function() {
                		// 	if (tabIndex == undefined || tabIndex == 0) {
                		// 		tabIndex = 0
                		// 	}

                		// 	// clearTimeout(TimOut)
                		// 	var tabName = $('.tabTitle a').eq(tabIndex).attr("data-nav");
                		// 	eval(tabName)()
                		// 	//pushTrade();
                		// 	//getCount();
                		// 	var me = this
                		// 	$(me).addClass('rotateIn')
                		// 	setTimeout(function() {
                		// 		$(me).removeClass('rotateIn')
                		// 	}, 1000)

                		// })

                		$(".pay_list_c1").on("click",function(){
                		  $(this).addClass("on").siblings().removeClass("on");
                		})

                	        
                	        require.async('/resources/js/module/jquery-validation-1.13.0/jquery.validate.min', function() {
                	            	require.async(['/resources/js/module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/jquery.form'], function() {
                                           require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {        
                        	            		//上架-表单验证及提交
                                                $("#userInfoForm").validate({
                                                    //onkeyup:false,
                        	            			rules: {
                        	            				discount: {
                        	            					required: true,
                        	            					isNum: true,
                                                            max: 1000000000
                        	            				},
                        	            				yearRate: {
                        	            					required: true,
                        	            					isNum: true,
                                                            max: 99.99,
                                                            min:0
                        	            				},
                        	            				serviceCharge: {
                        	            					required: true,
                        	            					isNum: true
                        	            				}
                        	            			},
                        	            			messages: {
                        	            				discount: {
                        	            					required: "必填",
                        	            					isNum: "只能输入数字",
                                                            max:"需小于10亿"
                        	            				},
                        	            				yearRate: {
                        	            					required: "必填",
                        	            					isNum: "只能输入数字",
                                                            max:"需小于100",
                                                            min: "不得小于0"
                        	            				},
                        	            				serviceCharge: {
                        	            					required: "必填",
                        	            					isNum: "只能输入数字"
                        	            				}
                        	            			},
                        	            			// 错误时
                        	            			errorPlacement: function(error, element) {
                        	            				// $("#errorText span").html(error.html())
                        	            				element.parents("li").find(".errorText").html(error.html());
                        	            			},
                        	            			// 成功时
                        	            			success: function(element) {
                        	            				element.parents("li").find(".errorText").html('');
                        	            			},
                        	            			submitHandler: function(form) {
                                                        var ids = []; 
                                                        var btnType = window.btnType;
                                                        if(btnType=='single'){
                                                            ids = window.id
                                                        }else{
                                                            ids = window.ids
                                                        }
                    	            				    var param = $(form).serializeObject();
                                                        if(param.quotationMethod=='10'){
                                                            Number(param.yearRate=(param.yearRate/100).toFixed(6));
                                                            delete param.discount
                                                        }else{
                                                            delete param.yearRate
                                                        }
                                                        var bills=[];
                                                        $('.billTbody1 tr').each(function(){//组装获取票据信息
                                                            var obj={};
                                                            
                                                            var billNo = $(this).find('td.billNoTd').text();
                                                            var adjustDays = $(this).find('input[name="adjustDays"]').val();
                                                            var faceAmount = $(this).find('input[name="faceAmount"]').val();
                                                            var dueDate = $(this).find('input[name="dueDate"]').val();

                                                            obj.billNo = billNo;
                                                            obj.adjustDays = adjustDays?adjustDays:0;
                                                            obj.faceAmount = faceAmount;
                                                            obj.dueDate = dueDate;
                                                            bills.push(obj)
                                                        })
                                                        param.billNos = JSON.stringify(bills);
                                                        $.ajax({
                                                            type: "post",
                                                            url: "/modules/billorder/action/bizBillCurrentMsgAction/batchShelves.htm",
                                                            dataType: 'json',
                                                            data: param,
                                                            success: function (result) {
                                                                if (result.code == 200) {
                                                                    layer.msg(result.msg);
                                                                    tradingOrder();//刷新列表
                                                                    window.ids=[];//置空多选票据-票号
                                                                    window.id=[];//置空单选票据-票号
                                                                    $('.theShelvesBack').trigger("click")
                                                                    //location.reload();//刷新页面
                                                                } else {
                                                                    if(result.code==400){
                                                                        layer.msg(result.msg);   
                                                                    }else{
                                                                        layer.msg(result.msg);
                                                                    }
                                                                    
                                                                }
                                                            }
                                                        });

                        	            			}
                        	            		});


                                                $("#userInfoForm2").validate({
                                                    rules: {
                                                        yearRate: {
                                                            isNum: true,
                                                            max: 100,
                                                            min:0
                                                        },
                                                        discount: {
                                                            isNum: true,
                                                            max: 1000000000
                                                        },
                                                        serviceFee: {
                                                            isNum: true,
                                                            max: 1000000000
                                                        },
                                                        analogueBankCodeText: {
                                                            required: true
                                                        },
                                                        analogueOpeningBankText: {
                                                            required: true
                                                        },
                                                        settlementAmount: {
                                                            required: true,
                                                            isNum: true
                                                        },
                                                        analogueBankAccount: {
                                                            required: true,
                                                            maxlength: 35,
                                                            minlength: 8
                                                        },
                                                        analogueAccountName: {
                                                            required: true
                                                        },
                                                        analogueOpeningBank: {
                                                            required: true
                                                        },
                                                        analogueBankNumber: {
                                                            required: true,
                                                            isNum: true
                                                        }
                                                    },
                                                    messages: {
                                                        yearRate: {
                                                            isNum: "只能输入数字",
                                                            max:"需小于100",
                                                            min: "不得小于0"
                                                        },
                                                        discount: {
                                                            isNum: "只能输入数字",
                                                            max:"需小于10亿"
                                                        },
                                                        serviceFee: {
                                                            isNum: "只能输入数字",
                                                            max:"需小于10亿"
                                                        },
                                                        analogueBankCodeText: {
                                                            required: "必填"
                                                        },
                                                        analogueOpeningBankText: {
                                                            required: "必填"
                                                        },
                                                        settlementAmount: {
                                                            required: "必填",
                                                            isNum: "只能输入数字"
                                                        },
                                                        analogueBankAccount: {
                                                            required: "必填",
                                                            maxlength: "请输入8位-35位的银行卡",
                                                            minlength: "请输入8位-35位的银行卡"
                                                        },
                                                        analogueAccountName: {
                                                            required: "必填"
                                                        },
                                                        analogueOpeningBank: {
                                                            required: "必填"
                                                        },
                                                        analogueBankNumber: {
                                                            required: "必填",
                                                            isNum: "只能输入数字"
                                                        },
                                                    },
                                                    // 错误时
                                                    errorPlacement: function(error, element) {
                                                        // $("#errorText span").html(error.html())
                                                        element.parents("li").find(".errorText").html(error.html());
                                                    },
                                                    // 成功时
                                                    success: function(element) {
                                                        element.parents("li").find(".errorText").html('');
                                                    },
                                                    submitHandler: function(form) {
                                                        var ids = []; 
                                                        var btnType = window.btnType;
                                                        if(btnType=='single'){
                                                            ids = window.id
                                                        }else{
                                                            ids = window.ids
                                                        }
                                                        var param = $(form).serializeObject();
                                                        if(param.quotationMethod=='10'){
                                                            Number(param.yearRate=(param.yearRate/100).toFixed(6));
                                                            delete param.discount
                                                        }else{
                                                            delete param.yearRate
                                                        }
                                                        var bills=[];
                                                        $('.billTbody2 tr').each(function(){//组装获取票据信息
                                                            var obj={};
                                                            // var billNo = $(this).find('input[name="billNo"]').val();
                                                            var type = $(this).find('td.typeTd').text();
                                                            var billAttribute = $(this).find('td.billAttributeTd').text();
                                                            var billNo = $(this).find('td.billNoTd').text();
                                                            var adjustDays = $(this).find('input[name="adjustDays"]').val();
                                                            obj.type = type;
                                                            obj.billAttribute = billAttribute;
                                                            obj.billNo = billNo;
                                                            obj.adjustDays = adjustDays;
                                                            bills.push(obj)
                                                        })
                                                        //console.log(bills)
                                                        param.bills = bills;
                                                        param.analogueOpeningBank = param.analogueOpeningBankText;
                                                        $.ajax({
                                                            type: "post",
                                                            url: "/modules/nonRegulatory/nonRegulationAction/initiatedTransactions.htm",
                                                            dataType: 'json',
                                                            data: {
                                                                tradeInfo:JSON.stringify(param)
                                                            },
                                                            success: function (result) {
                                                                if (result.code == 200) {
                                                                    layer.msg(result.msg);
                                                                    tradingOrder();//刷新列表
                                                                    window.ids=[];//置空多选票据-票号
                                                                    window.id=[];//置空单选票据-票号
                                                                    $('.superviseBack').trigger("click")
                                                                    //location.reload();//刷新页面
                                                                } else {
                                                                    layer.msg(result.msg);
                                                                }
                                                            }
                                                        });

                                                    }
                                                });


                                                //票据修改-表单验证及提交
                                                $("#userInfoForm3").validate({
                                                    rules: {
                                                        discount: {
                                                            required: true,
                                                            isNum: true,
                                                            max: 1000000000
                                                        },
                                                        yearRate: {
                                                            required: true,
                                                            isNum: true,
                                                            max: 100,
                                                            min:0
                                                        },
                                                        adjustDays: {
                                                            required: true,
                                                            isNum: true,
                                                            max: 100000
                                                        }
                                                    },
                                                    messages: {
                                                        discount: {
                                                            required: "必填",
                                                            isNum: "只能输入数字",
                                                            max:"需小于10亿"
                                                        },
                                                        yearRate: {
                                                            required: "必填",
                                                            isNum: "只能输入数字",
                                                            max:"需小于100",
                                                            min: "不得小于0"
                                                        },
                                                        adjustDays: {
                                                            required: "必填",
                                                            isNum: "只能输入数字",
                                                            max:"需小于100000"
                                                        }
                                                    },
                                                    // 错误时
                                                    errorPlacement: function(error, element) {
                                                        // $("#errorText span").html(error.html())
                                                        element.parents("li").find(".errorText").html(error.html());
                                                    },
                                                    // 成功时
                                                    success: function(element) {
                                                        element.parents("li").find(".errorText").html('');
                                                    },
                                                    submitHandler: function(form) {
                                                        var param = $(form).serializeObject();
                                                        var dueDate = $('input[name="dueDate"]').val();
                                                        var faceAmount = $('input[name="faceAmount"]').val();
                                                        param.dueDate =dueDate;
                                                        param.faceAmount =Number((faceAmount*10000).toFixed(2));
                                                        if(param.quotationMethod=='10'){
                                                            Number(param.yearRate=(param.yearRate/100).toFixed(6));
                                                            delete param.discount
                                                        }else{
                                                            delete param.yearRate
                                                        }
                                                        $.ajax({
                                                            type: "post",
                                                            url: "/modules/billorder/action/bizBillCurrentMsgAction/update.htm",
                                                            dataType: 'json',
                                                            data: param,
                                                            success: function (result) {
                                                                if (result.code == 200) {
                                                                    layer.msg(result.msg);
                                                                    tradedOrder();//刷新列表
                                                                    $('.billBack').trigger("click")
                                                                    //location.reload();//刷新页面
                                                                } else {
                                                                    layer.msg(result.msg);
                                                                }
                                                            }
                                                        });

                                                    }
                                                });
                                            })
                	            	})
                	        });



			});
		})
	})


});