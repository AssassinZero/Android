//会员-账户信息
define(function(require, exports, module) {
	require('/resources/js/jquery');
	require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
	    $.ajax({
	    	type: "get",
	    	url: "/modules/customer/customerAction/getAccountInfo.htm",
	    	dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
	    	success: function(result) {
                $("#lookForm").setForm(result.data);
	    	},
	    	error: function(result) {
                console.log('ajaxError')
	    	}
	    }); 
    

    })
});