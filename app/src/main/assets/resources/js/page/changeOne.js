define(function (require, exports, modlue) {
    require('jQuery');
    //获取手机验证码
    $("#getVcode").click(function () {
        var o = this;

        var mobile = $("#mobile").val();
        if (!/0?(13|14|15|18|17)[0-9]{9}/.test(mobile)) {
            layer.msg("手机号码格式错误", {
                "time": 800
            });
            return;
        } else {
            if (canClick) {
                $.ajax({
                    url: '/sendVerifyCode.htm',
                    type: 'post',
                    data: {
                        mobile: mobile,
                        type: "forgotPassword"
                    },
                    success: function (result) {
                        if (result.code == 200) {
                            get_code_time(o);
                        } else {
                            layer.msg(result.msg, {
                                "time": 1000
                            });
                        }
                    }
                });

            }
        }
    })

    //验证码倒计时
    var wait = 60;
    var canClick = true;
    get_code_time = function (o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.style.cssText = "background:#DB2B1D;color:#fff";
            canClick = true;
            o.text = "重新发送";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.style.cssText = "background:#999;color:#fff";
            o.text = "请稍后" + wait + "s";
            wait--;
            canClick = false;
            setTimeout(function () {
                get_code_time(o);
            }, 1000)
        }
    }

    require.async('../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async('../module/jquery-validation-1.13.0/additional-methods', function () {
            require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
                $("#changeOne").validate({
                    rules: {
                        code: {
                            required: true,
                            minlength: 4
                        },
                        mobile: {
                            required: true,
                            isMobile: true
                        }
                    },
                    messages: {
                        code: {
                            required: "请输入验证码",
                            minlength: " "
                        },
                        mobile: {
                            required: "请输入手机号码",
                            isMobile: "手机号码格式错误"
                        },
                    },
                    errorPlacement: function (error, element) {
                        element.parents(".yanz").find(".msg_tip").html(error);
                    },
                    success: function (element) {
                        if (element.parents(".yanz").find("input").has("validateCode")) {
                            //element.parents(".yanz").find(".msg_tip").html('');
                            element.parents(".yanz").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                        } else {
                            element.parents(".yanz").find(".msg_tip").html('');
                        }
                    },
                    submitHandler: function (form) {
                        $.ajax({
                            url: "/modules/customer/customerAction/checkMobile.htm",
                            type: "get",
                            dataType: 'json',
                            data: {
                                mobile: $("#mobile").val(),
                                code: $("#code").val()
                            },
                            success: function (data) {
                                if (data == true) {
                                    var mobile = $("#mobile").val(); 
                                    var code = $("#code").val();;
                                    window.location.href = "/User/changeTwo.htm?mobile=" + mobile + "&code="+code;
                                } else {
                                    // 清空并刷新验证码
                                    $("#validateCode").val("")
                                    $("#yanzheng").click();
                                    $("#validateCode .msg_tip").html("")
                                    layer.msg(data.msg);
                                }
                            }
                        });
                    }

                })
            })
        })
    })
})