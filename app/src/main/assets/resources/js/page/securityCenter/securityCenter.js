define(function(require, exports, module) {
	require.async('/resources/js/jquery', function() {

		var comfn = require('/resources/js/commonFn.js'); //调用公共文件
        require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		  require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() { 
  	            function deleteBank(value) {//删除银行卡
  	    	        $('.delete').click(function(e){
  	    	        	var id = $(this).attr('data-id');
  	    	        	console.log(id)
  	    	        	$.ajax({
  	    	        	    type: "post",
  	    	        	    url: "/modules/customer/customerAccountInfoAction/deleteCustomerBankAccountInfo.htm",
  	    	        	    data:{
  	    	        	    	id:id
  	    	        	    },
  	    	        	    dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
  	    	        	    success: function(result) {
  	    	        	       getBankList()
  	    	        	    },
  	    	        	    error: function(result) {
  	    	        	    	getBankList()
  	    	        	        console.log('ajaxError')
  	    	        	    }
  	    	        	});
  	    	        })
  	            };
  	            function getBankList(value) {//获取银行卡列表并渲染页面
			        $.ajax({
			            type: "get",
			            url: "/modules/customer/customerAccountInfoAction/getCustomerBankAccountInfoList.htm",
			            dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
			            success: function(result) {
			               var tpl = $('#bankListTpl').val();
			               var template = Handlebars.compile(tpl);
			               var html = template(result);
			               $(".bankList").html(html);
			               $(".bankList li").hover(function(){
			               	  $(this).find('.operation').stop();
			               	  $(this).find('.operation').animate({bottom:'0'});
			               },function(){
			               	    $(this).find('.operation').stop();
							    $(this).find('.operation').animate({bottom:'-40px'});
						   });
						   //修改银行卡-信息
	                        $('.modify').click(function(){
	                        	var data = $(this).attr('dataJson');
	                        	data = JSON.parse(data);
	                        	data.subbranchBankNameText = data.subbranchBankName;
	                        	$("#securityFrom3").setForm(data);
	                        	$('.securityCenterList').css("display",'none');
	                        	$('.securityCenterThird').css("display",'block');
	                        })
	                        //删除银行卡
	                        deleteBank()
			            },
			            error: function(result) {
			                console.log('ajaxError')
			            }
			        })
                };
                getBankList();
	    

        //修改密码 
        $('.modifyPassword').click(function(){
        	$('.securityCenterMain').css("display",'none');
        	$('.securityCenterFrist').css("display",'block');
        })
        //修改手机 
        $('.modifyMobie').click(function(){
        	$('.securityCenterMain').css("display",'none');
        	$('.securityCenterSecond').css("display",'block');
        })
        //修改银行卡-列表 
        $('.modifyBank').click(function(){
        	$('.securityCenterMain').css("display",'none');
        	$('.securityCenterList').css("display",'block');
        })
        //返回 
        $('.securityCenterBack').click(function(){
        	comfn.resetForm($("#securityFrom"));//重置form
        	comfn.resetForm($("#securityFrom2"));//重置form
        	$('.securityCenterMain').css("display",'block');
        	$(this).parent().parent().css("display",'none');
        })
        
        //返回银行卡列表
        $('.securityCenterBack2').click(function(){
        	getBankList();
        	comfn.resetForm($("#securityFrom3"));//重置form
        	$('.securityCenterList').css("display",'block');
        	$('.securityCenterThird').css("display",'none');
        })
        //新增银行卡-信息
        $('.addButton').click(function(){
        	$('.securityCenterList').css("display",'none');
        	$('.securityCenterThird').css("display",'block');
        })
        $('#bankMain').change(function(){
            var restData ={};
        	restData.subbranchBankNameText='';
            restData.subbranchBankName='';
            restData.subbranchBankCode='';
            $("#securityFrom3").setForm(restData); //切换总行清空支行
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
                //新增账户-开户支行名称 
                $("#holdAccountCodelist li").click(function (e) {
                	var restData ={};
                	restData.subbranchBankNameText='';
                    restData.subbranchBankName='';
                    restData.subbranchBankCode='';
                    $("#securityFrom3").setForm(restData); //切换总行清空支行
                    $("#holdAccountSubbranchNamelist").html(''); 
                    // var value = $(this).attr('data-value');
                    // sendholdAccountCode(value);
                })
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
        // function sendholdAccountCode(value) {
        //     var bankName = $("#holdAccountSubbranchNameText").val();
        //     if (bankName && value) {
        //         $.ajax({
        //             url: "/modules/common/action/bizBaseAction/getBankDetailList.htm",
        //             type: "get",
        //             dataType: 'json',
        //             data: {
        //                 bankCode: value,
        //                 bankName: bankName
        //             },
        //             success: function (result) {
        //                 var data = result.data;
        //                 var len = data.length;
        //                 var str = "";  
        //                 for (i = 0; i < len; i++) {
        //                     str += "<li title='" + data[i].bankName + "' data-value='" + data[i].bankNo + "'>" + data[i].bankName + "</li>";
        //                 } 
        //                 $("#holdAccountSubbranchNamelist").html(str);
        //                 $("#holdAccountSubbranchNamelist li").click(function (e) {
        //                     var value = $(this).attr('data-value');
        //                     $("#holdAccountSubbranchCode").val(value);
        //                 });
        //             }
        //         });
        //     }
        // }

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


    })
});
        

		//表单验证及提交
		require.async('/resources/js/module/jquery-validation-1.13.0/jquery.validate.min', function() {
			require.async(['/resources/js/module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/jquery.form', '/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {

				$("#securityFrom").validate({
					rules: {
						password: {
							required: true,
						},
						validCode: {
							required: true,
						},
						newPassword: {
							required: true,
							regexPassword: true
						},
						confirmNewPwd: {
							required: true,
							equalTo: "#newPassword"
						},

					},
					messages: {
						password: {
							required: "请输入密码",
						},
						validCode: {
							required: "请输入验证码",
						},
						newPassword: {
							required: "请输入新密码",
						},
						confirmNewPwd: {
							required: "请再次输入新密码",
							regexPassword: "密码格式错误",
							equalTo: "两次输入密码不一致"
						},

					},
					// 错误时
					errorPlacement: function(error, element) {
						// $("#errorText span").html(error.html())
						// console.log(element)
						element.parents("li").find(".errorText").html(error.html());
					},
					// 成功时
					success: function() {},
					submitHandler: function(form) {
						$(form).ajaxSubmit({
								type: "post",
								dataType: 'json',
								success: function(data) {
									if (data.code == 200) {
										layer.msg("修改成功", {
											"time": 500
										});
										location.reload();
										//window.location.href = "/User/login.htm";
									} else {
										layer.msg(data.msg);
									}
								}
							})
							// console.log("form")
							// console.log(form);
							// return;
					}
				});

                $("#securityFrom2").validate({
					rules: {
						password: {
							required: true,
						},
						code: {
							required: true,
						},
						newMobie: {
							required: true,
							isMobile: true
						}
					},
					messages: {
						password: {
							required: "请输入密码",
						},
						code: {
							required: "请输入短信验证码",
						},
						newMobie: {
							required: "请输入新手机号码",
							isMobile: "手机号码格式错误"
						}
					},
					// 错误时
					errorPlacement: function(error, element) {
						// $("#errorText span").html(error.html())
						// console.log(element)
						element.parents("li").find(".errorText").html(error.html());
					},
					// 成功时
					success: function() {},
					submitHandler: function(form) {
						$(form).ajaxSubmit({
								type: "post",
								dataType: 'json',
								success: function(data) {
									if (data.code == 200) {
										layer.msg("修改成功", {
											"time": 500
										});
										location.reload();
										comfn.resetForm($("#securityFrom2"));//重置form
									} else {
										layer.msg(data.msg);
									}
								}
							})
							// console.log("form")
							// console.log(form);
							// return;
					}
				});

                $("#securityFrom3").validate({
					rules: {
						accountName: {
							required: true
						},
						bankCodeText: {
							required: true
						},
						subbranchBankNameText: {
							required: true
						},
						subbranchBankCode: {
							required: true
						},
						bankAccount: {
							required: true,
							isNum: true,
							maxlength: 35,
                            minlength: 8
						}
					},
					messages: {
						accountName: {
							required: "必填"
						},
						bankCodeText: {
							required: "必填"
						},
						subbranchBankNameText: {
							required: "请输入开户支行关键字"
						},
						subbranchBankCode: {
							required: "必填"
						},
						bankAccount: {
							required: "必填",
							isNum: "只能输入数字",
							maxlength: "请输入8位-35位的银行卡",
                            minlength: "请输入8位-35位的银行卡"
						}
					},
					// 错误时
					errorPlacement: function(error, element) {
						// $("#errorText span").html(error.html())
						// console.log(element)
						element.parents("li").find(".errorText").html(error.html());
					},
					// 成功时
					success: function() {},
					submitHandler: function(form) {
						var param = $(form).serializeObject();
						//param.subbranchBankCode = param.subbranchBankName;
						param.subbranchBankName = param.subbranchBankNameText;
						$.ajax({
						    type: "post",
						    url: "/modules/customer/customerAccountInfoAction/saveOrUpdate.htm",
						    dataType: 'json',
						    data: {
						        bankAccount:JSON.stringify(param)
						    },
						    success: function (result) {
						        if (result.code == 200) {
						        	$('.securityCenterBack2').trigger('click');
						            layer.msg(result.msg);
						        } else {
						            layer.msg(result.msg);
						        }
						    }
						});
					}
				});


				//倒计时
				var spaceTime = 60; //倒计时时间
				var wait = spaceTime;
				var canClick = true;
				get_code_time = function(o) {
					var timer;
					var o = o;
					if (wait == 0) {
						o.removeClass('disabled');
						o.text("重新发送");
						o.css({
							background: '#fcf8f1',
							color: '#ffae22'
						});
						wait = spaceTime;
						canClick = true;
					} else {
						o.addClass('disabled');
						o.text("(" + wait + ")秒后重新获取");
						o.css({
							background: '#eaeaea',
							color: '#666'
						});
						wait--;
						timer = setTimeout(function() {
							get_code_time(o);
						}, 1000);
					}
				}
				$("#getValidCode").click(function() {
					var thisEle = $(this);
					if ($("#newPhone").val() == "") {
						layer.msg("请填写新手机号码", {
							time: 1000
						});
						return;
					}
					if (canClick) {
						$.ajax({
							type: "post",
							url: '/sendVerifyCode.htm',
							data: {
								mobile: $("#newPhone").val(),
								type: "bindMobile"
							},
							dataType: "json",
							success: function(result) {
								if (result.code == 200) {
									get_code_time(thisEle);
								} else {
									layer.msg(result.msg, {
										"time": 1000
									});
								}
							}
						});

					}
				})

			})
		})

	});
});