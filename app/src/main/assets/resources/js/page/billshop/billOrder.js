define(function (require, exports, module) {
    require('jQuery');

    function commafy(num, step) {
        var microValue;
        var floatSection; // 小数部分
        num = num && num.toString().replace(/,/g, ''); //避免带逗号的数据传入
        var value = String(parseFloat(num)); // 吧传入数据转换为float后再转换为字符串

        var arr = value.split('.');
        var isFloat = arr.length == 2; //判断是否是浮点数
        if (isFloat) {
            floatSection = arr[1].substr(0, step); // 小数部分
            floatSection = floatSection.length == 2 ? floatSection : floatSection + "0"
            value = arr[0]; //整数部分
        }

        // 小数点前面部分设置千分位
        var microValue = value.replace(/,/g, '').replace(/\d+?(?=(?:\d{3})+$)/g, function (s) {
            // console.log(s)
            return s + ',';
        });

        if (isFloat) {
            microValue = microValue + '.' + floatSection
        } else {
            //microValue = microValue + '.' + "00"
            microValue = microValue
        }

        if (typeof microValue == 'undefined' || microValue == 'NaN') {
            microValue = ""
        }
        return microValue;
    }

    //现有账户-查询银行详细信息
    function sendbankAccount(value) {
        console.log(value)
        $.ajax({
            url: "/modules/billorder/action/bizBillCurrentMsgMarketAction/getCustomerBankAccountInfo.htm",
            data: {
                bankAccount: value,
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                var data = result.data;
                $("#accountNameOwn").val(data.accountName);
                $("#openingBankOwn").val(data.subbarnchBankName);
                $("#bankNumberOwn").val(data.subbranchBankCode);
            }
        });
    }

    $.ajax({
        url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/getBizCustomerBankAccountByiphone.htm',
        type: "get",
        dataType: "json",
        success: function (result) {
            var data = result.data;
            var len = data.length;
            var str = "";

            for (i = 0; i < len; i++) {
                str += "<li data-value='" + data[i].bankAccount + "'>" + data[i].bankAccount + "</li>";
            }
            $("#bankAccountOwnList").append(str);
            //现有账户-查询银行详细信息
            $("#bankAccountOwnList li").click(function (e) {
                var value = $(this).attr('data-value');
                sendbankAccount(value)
            })
        }
    })

    var isAll = (window.location.search).substring(1).split(",")[0];

    var dataForm, url, parseDoubleForm;
    var dataobj = [];
    if (isAll == "checkAll") {
        var arrBillNos = (window.location.search).substring(1).split(",").splice(1);
        url = '/modules/billorder/action/bizBillCurrentMsgMarketAction/preBillBatchTwo.htm?billNos=' + JSON.stringify(arrBillNos);
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (result) {
                require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
                    require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
                        var tpl = require('/resources/tpls/billList/billInfoTwo.tpl'); //载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var data = result.data;
                        var html = template(data);
                        $("#billInfo").html(html);
                        var datas = {};
                        datas.enterpriseNameOwn = data[0].enterpriseNameOwn;
                        datas.accountNameOwn = data[0].accountNameOwn;
                        datas.openingBankOwn = data[0].openingBankOwn;
                        datas.bankNumberOwn = data[0].bankNumberOwn;
                        datas.bankAccountOwn = data[0].bankAccountOwn;
                        datas.enterpriseName = data[0].enterpriseName;
                        datas.accountName = data[0].accountName;
                        datas.openingBank = data[0].openingBank;
                        datas.mobileOwn = data[0].mobile;
                        datas.bankNumber = data[0].bankNumber;
                        datas.bankAccount = data[0].bankAccount;
                        $(".Order").setForm(datas);
                        parseDoubleForm = parseFloat(data[0].parseDouble).toFixed(2);
                        var originalMoney = parseFloat(data[0].parseDouble);
                        var microValue = commafy(originalMoney.toFixed(2), 4);
                        $("#originalMoney").html(microValue);
                        dataForm = data;
                    });
                });
            }
        })
    } else {
        url = '/modules/billorder/action/bizBillCurrentMsgMarketAction/preBill.htm?billNo=' + (window.location.search).substring(1);
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (result) {
                require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function () {
                    require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function () {
                        var tpl = require('/resources/tpls/billList/billInfo.tpl'); //载入tpl模板
                        var template = Handlebars.compile(tpl);
                        var data = result.data
                        var html = template(data);
                        $("#billInfo").html(html);

                        var datas = {};
                        datas.enterpriseNameOwn = data.enterpriseNameOwn;
                        datas.accountNameOwn = data.accountNameOwn;
                        datas.openingBankOwn = data.openingBankOwn;
                        datas.bankNumberOwn = data.bankNumberOwn;
                        datas.bankAccountOwn = data.bankAccountOwn;
                        datas.enterpriseName = data.enterpriseName;
                        datas.mobileOwn = data.mobileOwn;
                        datas.accountName = data.accountName;
                        datas.openingBank = data.openingBank;
                        datas.bankNumber = data.bankNumber;
                        datas.bankAccount = data.bankAccount;
                        $(".Order").setForm(datas);
                        parseDoubleForm = parseFloat(data.parseDouble).toFixed(2);
                        var originalMoney = parseFloat(data.parseDouble);
                        var microValue = commafy(originalMoney.toFixed(2), 4);
                        $("#originalMoney").html(microValue);
                        dataobj.push(data);
                        dataForm = dataobj;
                    });
                });
            }
        })
    }

    $('#serviceCharge').keyup(function () {
        var c = $(this);
        if (/[^\d]/.test(c.val())) {//替换非数字字符  
            var temp_amount = c.val().replace(/[^\d]/g, '');
            $(this).val(temp_amount);
        }
    })
    $("#serviceCharge").on("propertychange input", function () {
        originalMoney = parseDoubleForm - $(this).val();
        $("#originalMoney").html(originalMoney);
    });

    //新增银行
    // $("#new").hide(); 
    // $(".addAccount").on("click", function () {
    //     if ($(this).hasClass('onActive')) {
    //         $(this).removeClass('onActive');
    //         $(this).html("新增账户");
    //         $("#existing").show();
    //         $("#new").hide();
    //     } else {
    //         $(this).addClass('onActive');
    //         $(this).html("现有账户");
    //         $("#existing").hide();
    //         $("#new").show();
    //     }
    // })

    //新增账户-开户银行
    $.ajax({
        url: "/modules/common/action/bizBaseAction/getBankList.htm",
        type: "get",
        dataType: 'json',
        success: function (result) {
            var data = result.data;
            window.bankData = data;
            var len = data.length;
            var str = "";

            for (i = 0; i < len; i++) {
                str += "<li data-value='" + data[i].bankCode + "'>" + data[i].bankName + "</li>";
            }
            $("#holdAccountCodelist").html(str);
            //新增账户-开户支行名称 
            $("#holdAccountCodelist li").click(function (e) {
                var value = $(this).attr('data-value');

                var restData = {};
                restData.holdAccountSubbranchNameText = '';
                restData.holdAccountSubbranchName = '';
                restData.holdAccountSubbranchCode = '';
                $("#bill-form").setForm(restData); //切换总行清空支行
                $("#holdAccountSubbranchNamelist").html('');

                sendholdAccountCode(value)
            })
        }
    });


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
                        str += "<li data-value='" + data[i].bankNo + "'>" + data[i].bankName + "</li>";
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

    $("#bankAccount2").on("blur", sendbankAccount3);
    //持票方信息-现有账户-查询银行详细信息
    function sendbankAccount3() {
        var bankAccount = $("#bankAccount2").val();
        if (bankAccount) {
            $.ajax({
                url: "/modules/billorder/action/bizBillCurrentMsgMarketAction/getCustomerBankAccountInfo.htm",
                data: {
                    bankAccount: bankAccount,
                },
                type: "get",
                dataType: "json",
                success: function (result) {
                    var data = result.data;
                    if (result.code == 200 && data) {
                        $("input[name='holdAccountName']").val(data.accountName);
                        $("input[name='holdAccountSubbranchCode']").val(data.subbranchBankCode);
                        $("input[name='holdAccountSubbranchNameText']").val(data.subbarnchBankName);
                        $("input[name='holdAccountCode']").val(data.bankCode);
                        $("input[name='holdAccountCodeText']").val(data.bankName);
                        $('#new .disabled').attr("disabled", true);
                    } else {
                        $("input[name='holdAccountName']").val("");
                        $("input[name='holdAccountSubbranchCode']").val("");
                        $("input[name='holdAccountSubbranchNameText']").val(""); 
                        $("input[name='holdAccountCode']").val("");
                        $("input[name='holdAccountCodeText']").val("");
                        $('#new .disabled').attr("disabled", false);
                    }

                }
            });
        };
    };

    function isChinese(temp) {//判断是否是中文
        if (/[^\u4e00-\u9fa5]/.test(temp)) {
            return false;
        } else {
            return true
        }
    }

    $("#holdAccountSubbranchNameText").on("propertychange input", sendholdAccountCode2);
    function sendholdAccountCode2() {
        var bankCode = $("#holdAccountCodeid").val();
        var bankName = $("#holdAccountSubbranchNameText").val();
        var banklen = bankName.length;
        if (bankName && isChinese(bankName) && bankCode && banklen > 1 && bankName != '中国' && bankName != '银行' && bankName != '工商') {//只有为中文长度大于1时执行
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
                    if (len > 0) {
                        for (i = 0; i < len; i++) {
                            str += "<li title='" + data[i].bankName + "' data-value='" + data[i].bankNo + "'>" + data[i].bankName + "</li>";
                        }
                    } else {
                        str = "<a>没有相关银行信息</a>"
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

    // 立即购买 
    require.async('../../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async('../../module/jquery-validation-1.13.0/additional-methods', function () {
            require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
                $("#bill-form").validate({
                    rules: {
                        bankAccount2: {
                            required: true,
                            isNum: true
                        },
                        holdAccountName: {
                            required: true
                        },
                        holdAccountCodeText: {
                            required: true
                        },
                        holdAccountSubbranchNameText: {
                            required: true
                        },
                        serviceCharge: {
                            required: true,
                            isNum: true,
                            max: 300
                        }
                    },
                    messages: {
                        bankAccount2: {
                            required: "必填",
                            isNum: "只能输入数字"
                        },
                        holdAccountName: {
                            required: "必填"
                        },
                        holdAccountCodeText: {
                            required: "必填"
                        },
                        holdAccountSubbranchNameText: {
                            required: "必填"
                        },
                        serviceCharge: {
                            required: "必填",
                            isNum: "只能输入数字",
                            max: "不得超过300元"
                        }
                    },
                    // 错误时
                    errorPlacement: function (error, element) {
                        element.parents("li").find(".errorText").html(error.html());
                    },
                    // 成功时
                    success: function (element) {
                        element.parents("li").find(".errorText").html('');
                    },
                    submitHandler: function (form) {
                        var coverReciteForm = {
                            accountNameOwn: $("#holdAccountName").val(),
                            openingBankOwn: $("#holdAccountSubbranchNameText").val(),
                            bankNumberOwn: $("#holdAccountSubbranchCode").val(),
                            bankAccountOwn: $("#bankAccount2").val(),
                            bankCode: $("#holdAccountCodeid").val(), 
                        }
                        var paymentInformationForm = {
                            accountName: $("#accountName").val(),
                            openingBank: $("#openingBank").val(),
                            bankNumber: $("#bankNumber").val(),
                            bankAccount: $("#bankAccount").val(),
                            serviceCharge: $("#serviceCharge").val(),
                            mobileOwn: $("#mobileOwn").val(),
                        }
                        if (coverReciteForm.accountNameOwn == "") {//防止账号原来未添加普通银行账户直接提交。
                            layer.msg('请先新增账户');
                            return;
                        }
                        if(coverReciteForm.bankNumberOwn== ""){
                            layer.msg('请选择正确的开户银行名称');
                            return;
                        }
                        var originalMoney = Number($("#originalMoney").text());
                        $.ajax({
                            url: '/modules/billorder/action/bizBillCurrentMsgMarketAction/placeAnOrder.htm',
                            type: "post",
                            dataType: "json",
                            data: {
                                billSurfaceList: JSON.stringify(dataForm),
                                coverRecite: JSON.stringify(coverReciteForm),
                                paymentInformation: JSON.stringify(paymentInformationForm),
                                settlementAmount: originalMoney, 
                            },
                            success: function (data) {
                                if (data.code == 200) {
                                    var remark = data.data.uuid;
                                    var hrefAction;
                                    if (data.data.type == "10") {
                                        hrefAction = "/UserCenterMember/sellerAverageUser.htm";
                                    } else if (data.data.type == "30" || data.data.type == "40") {
                                        hrefAction = "/UserCenterMember/ElectricityOrders.htm";
                                    } else {
                                        hrefAction = "/UserCenterMember/buyersMemberUser.htm";
                                    }

                                    console.log(isAll+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                    console.log(window.location.search+"!!!!!!!!!!!!!!!!!!!!!!!!!   ");
                                    if (isAll == "checkAll") {
                                        var arrBillNos = (window.location.search).substring(1).split(",").splice(1);
                                        url = '/modules/billorder/action/bizBillCurrentMsgMarketAction/completePayment.htm?billNos=' + JSON.stringify(arrBillNos);
                                    } else {
                                        var arrBillNos = (window.location.search).substring(1).split(",");
                                        url = '/modules/billorder/action/bizBillCurrentMsgMarketAction/completePayment.htm?billNos=' + JSON.stringify(arrBillNos);
                                    }
                                    $.ajax({
                                        url: url,
                                        type: "get",
                                        dataType: "json",
                                        success: function (data) {
                                            if (data.code == 200) {
                                                layer.open({
                                                    type: 1,
                                                    title: "提交订单",
                                                    closeBtn: 0,
                                                    area: ['580px', '580px'],
                                                    border: [1, 1, '#cecfd0'],
                                                    content: '<div id="Orders-tc" class="OrderBox">' +
                                                    '<div class="Orders-success"><img class="img" src="../../../resources/img/billlist/success.png">订单提交成功！</div>' +
                                                        '<p>请在网银中按以下信息完成汇款操作</p>' +
                                                    '<div class="Orders-Details">' +
                                                    '<dl>' +
                                                    '<dt>收款账户信息</dt>' +
                                                    '<dd><label>银行账户：</label>' + $("#bankAccount").val() + '</dd>' +
                                                    '<dd><label>账户名称：</label>' + $("#accountName").val() + '</dd>' +
                                                    '<dd><label>开户行名称：</label>' + $("#openingBank").val() + '</dd>' +
                                                    '<dd class="bor"><label>开户行行号：</label>' + $("#bankNumber").val() + '</dd>' +
                                                    '<dd><label>交易金额：</label>' + originalMoney + '元</dd>' +
                                                    '<dd><label>备注信息：</label>订单号：' + remark + '</dd>' +
                                                    '</dl>' +
                                                    '</div>' +
                                                    '<div class="Orders-button">' +
                                                    '<a class="cancelBtn" href=' + hrefAction + '>支付完成</a>' +
                                                    '<a href=' + hrefAction + '>查看订单</a>' +
                                                    '</div>' +
                                                    '</div>'
                                                })
                                            } else {
                                                layer.msg(data.msg);
                                            }
                                        }
                                    })
                                } else {
                                    layer.msg(data.msg);
                                }
                            }
                        })
                    }
                })
            })
        });
    });

});

