//普通用户-账户信息
define(function(require, exports, module) {
	require('/resources/js/jquery');
	require('/resources/js/commonFn');
	require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
	    $.ajax({
	    	type: "get",
	    	url: "/modules/customer/customerAction/getAccountInfo.htm",
	    	dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
	    	success: function(result) {
	    		if(result.data&&result.data.birthday){
	    			result.data.birthday = result.data.birthday.split(' ')[0]
	    		}
                $("#lookForm").setForm(result.data);
	    	},
	    	error: function(result) {
                console.log('ajaxError')
	    	}
	    }); 


        //S保存方法
        require.async('../../module/jquery-validation-1.13.0/jquery.validate.min', function () {
          require.async('../../module/jquery-validation-1.13.0/additional-methods', function () {
                $("#lookForm").validate({
                    rules: {
                        enterpriseName: {
                            maxlength: 128,
                        },
                    },
                    messages: {
                        enterpriseName: {
                            maxlength: " ",
                        },
                    },
                    // 错误时
                    errorPlacement: function (error, element) {
                        element.parents("li").find(".errorText").html(error.html());
                    },
                    // 成功时
                    success: function (element) {
                        element.parents("li").find(".errorText").html('');
                    },
                    submitHandler: function(form) {
                    	var param = $(form).serializeObject();
                        $.ajax({
                            type: "post",
                            url: "/modules/customer/customerAction/saveOrUpdateAccountInfo.htm",
                            dataType: 'json',
                            data: {
                                accountInfo: JSON.stringify(param)
                            },
                            success: function (result) {
                                if (result.code == 200) {
                                    layer.msg(result.msg);
                                } else {
                                    layer.msg(result.msg);
                                }
                            }
                        })
                    }

                });
            })
        }) 
        //E保存方法








    

    })
});