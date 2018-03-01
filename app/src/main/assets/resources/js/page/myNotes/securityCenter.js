define(function(require, exports, module) {
	require.async('/resources/js/jquery', function() {



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
						newPwd: {
							required: true,
							regexPassword: true
						},
						confirmNewPwd: {
							required: true,
							equalTo: "#newPwd"
						},

					},
					messages: {
						password: {
							required: "请输入密码",
						},
						validCode: {
							required: "请输入验证码",
						},
						newPwd: {
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
										layer.msg("保存成功", {
											"time": 500
										});
									} else {
										layer.msg(data.msg);
									}
								}
							})
							// console.log("form")
							// console.log(form);
							// return;
					}
				})


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
					if ($("#userPhone").text() == "") {
						layer.msg("手机号码为空", {
							time: 1000
						});
						return;
					}
					if (canClick) {
						$.ajax({
							type: "post",
							url: '/sendSms.htm',
							data: {
								mobile: $("#userPhone").text(),
								type: "change"
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