/**
 * Created by Administrator on 2015/8/25.
 */
define(function (require, exports, modlue) {
    require('jQuery');
    //解决IE下不支持placeholder
    // require.async('./placeholder', function () {
    //     if ($.browser.msie) {
    //         $(".zhuce_1 :input[placeholder]").each(function () {
    //             $(this).placeholder({
    //                 posL: 10,
    //                 activeBorder: "#ffa185"
    //             });
    //         });
    //     }
    // })


    //注册切换 
    $(".zhuce_shop").hide();
    $(".zhucejt2").hide();
    $(".user").click(function () { 
        $(".zhuce_shop").hide();
        $(".zhuce_user").show(); 
        $(".zhucejt2").hide();
        $(".zhucejt").show();  
        $(".shop").removeClass("active");
        $(".user").addClass("active");
    })

    $(".shop").click(function () {
        $(".zhuce_user").hide(); 
        $(".zhuce_shop").show();
        $(".zhucejt").hide(); 
        $(".zhucejt2").show();
        $(".user").removeClass("active");
        $(".shop").addClass("active");
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

    //用户注册表单验证
    require.async('../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async(['../module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
            $("#zhuce_1").validate({
                rules: { 
                    mobile: {
                        required: true,
                        isMobile: true,
                        remote: {
                            type: "post",
                            url: "/modules/customer/customerAction/checkMobilePhone.htm",
                            data: {
                                mobile: function () {
                                    return $("#mobilePhone").val();
                                }
                            }
                        }
                    },
                    password: {
                        required: true,
                        regexPassword: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#password"
                    },
                    validateCode: {
                        required: true,
                        minlength: 4,
                        remote: {
                            type: "post",
                            url: "/validateCode.htm",
                            data: {
                                signcode: function () {
                                    return $("#validateCode").val();
                                }
                            }
                        }
                    },
                    code: {
                        required: true,
                        maxlength: 6
                    },
                    invitationCode: {
                        required: true
                    },
                    agree: {
                        required: true
                    }
                },
                messages: { 
                    mobile: {
                        required: "请输入手机号码",
                        isMobile: "手机号码格式错误",
                        remote: "该手机号码已存在"
                    },
                    password: {
                        required: "请输入密码",
                        regexPassword: "密码格式错误"
                    },
                    confirmPassword: {
                        required: "请输入确认密码",
                        equalTo: "两次输入密码不一致"
                    },
                    validateCode: {
                        required: "请输入验证码",
                        minlength: " ",
                        remote: "验证码不正确"
                    },
                    code: {
                        required: "请输入验证码",
                        maxlength: "验证码格式错误"
                    },
                    invitationCode: {
                        required: "请输入邀请码"
                    },
                    agree: {
                        required: "请勾选服务协议条款"
                    }
                },
                errorPlacement: function (error, element) {
                    element.parents("dd").find(".msg_tip").html(error);
                },
                success: function (element) {
                    if (element.parents("dd").find("input").has("validateCode")) {
                        //element.parents("dd").find(".msg_tip").html('');
                        element.parents("dd").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                    } else {
                        element.parents("dd").find(".msg_tip").html('');
                    }
                },
                submitHandler: function (form, event, validator) {
                    require.async('../module/jquery.form.js', function () {
                        $(form).ajaxSubmit({
                            type: "post",
                            dataType: 'json',
                            success: function (data) {
                                if (data.code == 200) {  
                                    $(".register_main").hide();
                                    $(".register_success").show();
                                    var time = parseInt($(".remain_time").text())
                                    if (time > 0) {
                                        var t = setInterval(function () {
                                            time--;
                                            $(".remain_time").text(time);
                                            if (time <= 1) {
                                                window.location.href = "/UserCenterMember/sellerAverageUser.htm";
                                            }
                                        }, 1000);
                                    } 
                                } else {
                                    // 清空并刷新验证码
                                    $("#validateCode").val("")
                                    $("#yanzheng").click();
                                    $("validateCode .msg_tip").html("")
                                    //提示层
                                    layer.msg(data.msg);
                                } 
                            }
                        });
                    })
                }
            });

        })
    })

    //代理商注册表单验证
    require.async('../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async(['../module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
            $("#zhuce_2").validate({
                rules: { 
                    mobile: {
                        required: true,
                        isMobile: true,
                        remote: {
                            type: "post",
                            url: "/modules/customer/customerAction/checkMobilePhone.htm",
                            data: {
                                mobile: function () {
                                    return $("#shopmobilePhone").val();
                                }
                            }
                        }
                    },
                    password: {
                        required: true,
                        regexPassword: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#shoppassword"
                    },
                    validateCode: {
                        required: true,
                        minlength: 4,
                        remote: {
                            type: "post",
                            url: "/validateCode.htm",
                            data: {
                                signcode: function () {
                                    return $("#shopvalidateCode").val();
                                }
                            }
                        }
                    },
                    code: {
                        required: true,
                        maxlength: 6
                    },
                    invitationCode: {
                        required: true
                    },
                    agree: {
                        required: true
                    }
                },
                messages: { 
                    mobile: {
                        required: "请输入手机号码",
                        isMobile: "手机号码格式错误",
                        //remote: "该手机号码已存在"
                    },
                    password: {
                        required: "请输入密码",
                        regexPassword: "密码格式错误"
                    },
                    confirmPassword: {
                        required: "请输入确认密码",
                        equalTo: "两次输入密码不一致"
                    },
                    validateCode: {
                        required: "请输入验证码",
                        minlength: " ",
                        remote: "验证码不正确"
                    },
                    code: {
                        required: "请输入验证码",
                        maxlength: "验证码格式错误"
                    },
                    invitationCode: {
                        required: "请输入邀请码"
                    },
                    agree: {
                        required: "请勾选服务协议条款"
                    }
                },
                errorPlacement: function (error, element) {
                    element.parents("dd").find(".msg_tip").html(error);
                },
                success: function (element) {
                    if (element.parents("dd").find("input").has("validateCode")) {
                        //element.parents("dd").find(".msg_tip").html('');
                        element.parents("dd").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                    } else {
                        element.parents("dd").find(".msg_tip").html('');
                    }
                },
                submitHandler: function (form, event, validator) {
                    require.async('../module/jquery.form.js', function () {
                        $(form).ajaxSubmit({
                            type: "post",
                            dataType: 'json',
                            success: function (data) {
                                if (data.code == 200) { 
                                    $(".register_main").hide();
                                    $(".register_success").show();
                                    var time = parseInt($(".remain_time").text())
                                    if (time > 0) {
                                        var t = setInterval(function () {
                                            time--;
                                            $(".remain_time").text(time);
                                            if (time <= 1) {
                                                window.location.href = "/UserCenterMember/myDeal.htm";
                                            }
                                        }, 1000);
                                    } 
                                } else {
                                    // 清空并刷新验证码
                                    $("#shopvalidateCode").val("")
                                    $("#shopyanzheng").click();
                                    $("validateCode .msg_tip").html("")
                                    //提示层
                                    layer.msg(data.msg);
                                } 
                            }
                        });
                    })
                }
            });

        })
    })


    //点击验证码切换
    //$(".change").click(function() {
    //  $(".valicode_img").attr("src", '/modules/system/controller/captchaAction/captchaImage.htm?t=' + Math.random());
    //})

    //协议弹出
    // $("#service_contract").click(function() {
    //     require.async(['../module/layer-v1.8.4/skin/layer.css', '../module/layer-v1.8.4/layer.min'], function() {
    //         var i = $.layer({
    //             type: 1,
    //             title: "车快融注册服务协议",
    //             closeBtn: [0, true],
    //             border: [10, 0.1, '#000', true],
    //             area: ['1034px', '750px'],
    //             page: {
    //                 dom: '#modal_dialog'
    //             }
    //         });
    //     })
    // });



    //获取手机验证码
    $("#getUserPhoneVcode").click(function () {
        var o = this;

        var mobile = $("#mobilePhone").val();
        if (!/0?(13|14|15|18|17)[0-9]{9}/.test(mobile)) {
            layer.msg("手机号码格式错误", {
                "time": 800
            });
            return;
        } else {
            if (canClick) {
                $.ajax({
                    url: '/sendVerifyCodeRegister.htm',
                    type: 'post',
                    data: {
                        mobile: mobile,
                        type: "register"
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

    //获取代理商注册手机验证码
    $("#getShopPhoneVcode").click(function () {
        var o = this;

        var mobile = $("#shopmobilePhone").val();
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
                        type: "register"
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
            //协议
    $("a[name='agreement']").click(function () {
        require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
            require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
                var tpl = require('/resources/tpls/registerTpl.tpl'); //载入tpl模板
                var template = Handlebars.compile(tpl);
                // 弹窗询问是否需要购买
                var text = template("");

                layer.open({
                    type: 1,
                    title: '协议内容',
                    skin: 'layui-layer-rim',
                    area: ['835px', '605px'],
                    content: text
                });
            })
        })
    })


})