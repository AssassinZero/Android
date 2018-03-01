//买家操作
define(function(require, exports, module) {
	require('/resources/js/jquery');

    	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
    		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
                require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {

                    $.ajax({
                        type: "get",
                        url: "/modules/customer/customerAction/getAccountOverviewInfo.htm?q="+Math.random(),
                        dataType: "json", //这个必不可少，如果缺少，导致传回来的不是json格式
                        success: function (result) {
                            var tpl = $('#accountOverviewTpl').val();
                            Handlebars.registerHelper("isSign", function (p, options) {
                                if (p == "20") {
                                    return options.fn(this);
                                } else {
                                    return options.inverse(this);
                                }
                            })
                                //
                            Handlebars.registerHelper("toSign", function (p, options) {
                                if (p == "12") {
                                    return options.fn(this);
                                } else {
                                    return options.inverse(this);
                                }
                            })
                            var template = Handlebars.compile(tpl);
                            var html = template(result.data);
                            $(".accountContent").html(html);
                            if (result.data && result.data.bankAccountInfo.length > 0) {//循环银行卡列表
                                var tpl = $('#myBankListTpl').val();

                                var template = Handlebars.compile(tpl);
                                var html = template(result.data.bankAccountInfo);
                                $(".myBankListUl").html(html);
                                $('.third a').click(function () {
                                    var value = $(this).text();
                                    if (value == '显示余额') {
                                        $(this).text($(this).prev().text());
                                    } else {
                                        $(this).text('显示余额');
                                    }
                                })
                                $('button.accign').click(function (event) {
                                    if ("ActiveXObject" in window) {
                                        var temp_form = document.createElement("form");
                                        temp_form.action = "/modules/qexbill/api/call/accsign.htm";
                                        temp_form.target = "_blank";
                                        temp_form.method = "post";
                                        temp_form.style.display = "none";
                                        var opt = document.createElement("textarea");
                                        opt.name = "id";
                                        opt.value = $(this).prev().text();
                                        temp_form.appendChild(opt);
                                        document.body.appendChild(temp_form);
                                        temp_form.submit();
                                    } else {
                                        layer.msg("请开始在IE浏览器中打开此网页完成签约");
                                    }

                                })

                            }
                        },
                        error: function (result) {
                            console.log('ajaxError')
                        }
                    });
                });
            })    
	    });


	








});