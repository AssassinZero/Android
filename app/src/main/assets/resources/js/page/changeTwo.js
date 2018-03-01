define(function(require, exports, modlue) {
    require('jQuery'); 

    //接受URL地址参数 
    function getQueryString(name) {                                       //name为传入参数
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");     
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;						
    }

    //用户注册表单验证
    require.async('../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async(['../module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
            $("#changeTwo").validate({
                rules: {  
                    password: {
                        required: true,
                        regexPasswords: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#password"
                    }
                },
                messages: {  
                    password: {
                        required: "请输入密码",
                        regexPassword: "密码格式错误"
                    },
                    confirmPassword: {
                        required: "请输入确认密码",
                        equalTo: "两次输入密码不一致"
                    }
                },
                errorPlacement: function (error, element) {
                    element.parents(".two").find(".msg_tip").html(error);
                },
                success: function (element) {
                    if (element.parents(".two").find("input").has("validateCode")) { 
                        element.parents(".two").find(".msg_tip").html('<i class="iconfont gre">&#xe61e;</i>');
                    } else {
                        element.parents(".two").find(".msg_tip").html('');
                    }
                },
                submitHandler: function (form) { 
                       $.ajax({
                            url: "/modules/customer/customerAction/setNewPassword.htm",
                            type: "post",
                            dataType: 'json',
                            data:{
                                mobile:getQueryString("mobile"),
                                code:getQueryString("code"),
                                password:$("#password").val()
                            },
                            success: function (data) {
                                if (data == true) {  
                                    window.location.href = "/User/changeThree.htm";
                                } else {
                                    // 清空并刷新验证码
                                    $("#validateCode").val("")
                                    $("#yanzheng").click();
                                    $("#validateCode .msg_tip").html("")
                                    //提示层
                                    layer.msg(data.msg);
                                } 
                            }
                        }); 
                }
            });

        })
    })

})