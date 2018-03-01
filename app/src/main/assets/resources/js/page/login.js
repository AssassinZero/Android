define(function(require, exports, modlue) {
    require('jQuery');
    require('/resources/js/commonFn');
    require('page/common');//解决IE不支持HTML5表单属性placeholder的问题
    require('md5');//MD5
    // $("#password").on("propertychange input", function(){
    //     console.log($(this).val())
    //     $('#passwordReal').val(hex_md5(hex_md5($(this).val())))
    // });
    //密码大写输入提示 -S
    function  detectCapsLock(event){
        var e = event||window.event;
        var o = e.target||e.srcElement;
        var oTip = o.nextElementSibling;
        var keyCode  =  e.keyCode||e.which; // 按键的keyCode 
        var isShift  =  e.shiftKey ||(keyCode  ==   16 ) || false ; // shift键是否按住
         if (
         ((keyCode >=   65   &&  keyCode  <=   90 )  &&   !isShift) // Caps Lock 打开，且没有按住shift键 
         || ((keyCode >=   97   &&  keyCode  <=   122 )  &&  isShift)// Caps Lock 打开，且按住shift键
         ){oTip.style.display = '';}
         else{oTip.style.display  =  'none';} 
    }

    document.getElementById('password').onkeypress = detectCapsLock;
    $('#password').blur(function(){
        $('.capital').css('display','none')
    })
    //密码大写输入提示 -E


    $(function () { //为了适应浏览器高度调整div高度
        var windowHeight=$(window).height(); 
        if(windowHeight<750){
            windowHeight=750
        }
        var mainHieht = (windowHeight-95)+'px'; 
        $('#main').css("height",mainHieht);
    });
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
    //获取手机验证码
    $("#getPhoneVcode").click(function () {
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
                        type: "login"
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
    require.async('../module/jquery-validation-1.13.0/jquery.validate.min', function() {
        require.async('../module/jquery-validation-1.13.0/additional-methods', function() {
          require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
            $("#login").validate({
                rules: {
                    code: {
                        required: true,
                        minlength: 4,
                        remote: {
                            type: "post",
                            url: "/validateCode.htm",
                            data: {
                                signcode: function() {
                                    return $("#validateCode").val();
                                }
                            }
                        }
                    }
                },
                messages: {
                    code: {
                        required: "请输入验证码",
                        //minlength: " ",
                        remote: "验证码不正确"
                    }
                },
                errorPlacement: function(error, element) {
                    element.parents("#login").find(".msg_tip").html(error);
                },
                success: function(element) {
                    console.log(element[0].htmlFor)
                    if (element[0].htmlFor=='validateCode') {
                        element.parents("#login").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                    } else {
                        element.parents("#login").find(".msg_tip").html('');
                    }
                },
                submitHandler: function(form, event, validator) {
                    require.async('../module/jquery.form.js', function() {
                        var param = $(form).serializeObject();
                        param.password = hex_md5(hex_md5(param.password));
                        console.log(param)
                        $.ajax({
                            url: "/modules/customer/customerAction/login.htm",
                            type: "post",
                            data: param,
                            dataType: 'json',
                            success: function(result) {
                                if (result.code == 200) {
                                    //window.location.href = "/home.htm";
                                    var type=result.data.type;//type:账户类型 10普通用户 20代理商 30会员用户
                                    window.type= type;
                                    console.log(result);
                                    if (type == '10') {
                                        window.location.href = "/UserCenterMember/sellerAverageUser.htm";
                                    } else if(type == '20') {
                                        window.location.href = "/UserCenterMember/myDeal.htm";
                                    } else if(type == '30') {
                                        window.location.href = "/UserCenterMember/accountOverview.htm";
                                    } else if(type == '40') {
                                        window.location.href = "/UserCenterMember/accountOverview.htm";
                                    }

                                } else {
                                    // 清空并刷新验证码
                                    $("#validateCode").val("");
                                    $("#yanzheng").click();
                                    $(".msg_tip").html("")
                                    layer.msg(result.msg);
                                }
                            }
                        });
                    })
                }

            });
             
            $("#login2").validate({
                rules: {
                    mobieCode: {
                        required: true,
                        minlength: 4,
                        remote: {
                            type: "post",
                            url: "/validateCode.htm",
                            data: {
                                signcode: function() {
                                    return $("#validateCode2").val();
                                }
                            }
                        }
                    },
                },
                messages: {
                    mobieCode: {
                        required: "请输入验证码",
                        //minlength: " ",
                        remote: "验证码不正确"
                    },
                },
                errorPlacement: function(error, element) {
                    element.parents("#login2").find(".msg_tip").html(error);
                },
                success: function(element) {
                    if (element.parents("#login2").find("input").has(".code")) {
                        element.parents("#login2").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                    } else {
                        element.parents("#login2").find(".msg_tip").html('');
                    }
                },
                submitHandler: function(form, event, validator) {
                    require.async('../module/jquery.form.js', function() {
                        $(form).ajaxSubmit({
                            url: "/modules/customer/customerAction/login.htm",
                            type: "post",
                            dataType: 'json',
                            success: function(result) {
                                if (result.code == 200) {
                                    //window.location.href = "/home.htm";
                                    var type=result.data.type;//type:账户类型 10普通用户 20代理商 30会员用户
                                    window.type= type;
                                    if (type == '10') {
                                        window.location.href = "/UserCenterMember/sellerAverageUser.htm";
                                    } else if(type == '20') {
                                        window.location.href = "/UserCenterMember/myDeal.htm";
                                    } else if(type == '30') {
                                        window.location.href = "/UserCenterMember/accountOverview.htm";
                                    } else if(type == '40') {
                                        window.location.href = "/UserCenterMember/accountOverview.htm";
                                    }

                                } else {
                                    // 清空并刷新验证码
                                    $("#validateCode").val("");
                                    $("#yanzheng").click();
                                    $(".msg_tip").html("")
                                    layer.msg(result.msg);
                                }
                            }
                        });
                    })
                }

            }); 

            
          })
        })
    })
})