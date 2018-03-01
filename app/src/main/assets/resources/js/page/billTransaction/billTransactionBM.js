//会员用户-我是卖家-发起交易
define(function (require, exports, module) {
    require('/resources/js/jquery');
    require('/resources/js/commonFn');

    //点击按钮添加表格行
    $(".theShelvesTable tbody .subtractBtn").on('click', subtractBtn);
    var $ShelvesTable = $(".theShelvesTable>tbody");
    var temp = $ShelvesTable.find("tr").eq(0).html();
    var parent = '<tr>' + temp + '</tr>';
    $(".addBtn").click(function () {
        $(".theShelvesTable tbody").append(parent);
        $(".theShelvesTable tbody .subtractBtn").on('click', subtractBtn);
        $("input[name='billNo']").on("blur", sendbillNo);
    })
    function subtractBtn() {
        $(this).parent().parent().remove();
    };

    function isEmpty(obj) {
        for (var name in obj) {
            return false;
        }
        return true;
    };
    //获取表格数据
    var getParams = function (form) {
        var param = {};
        $(form).find('input[name]').each(function () {
            var name = $(this).attr('name');
            if (name != null && name.trim() != '') {
                var value = $(this).val();
                if (value != null && value != "") {
                    if (name == "billAmount") {
                        value = value * 10000;
                    }
                    param[name] = value;
                }
            }
        });
        return param;
    };

    //radio选中-报价方式
    $(".quotation_pay").on("click", function () {
        $(this).addClass("on").siblings().removeClass("on");
    });

    //radio选中-收款账户是否与背书账户一致
    $(".radiono").on("click", function () {
        $("#NBaccount").show();
        $(this).addClass("on").siblings().removeClass("on");
    });
    $(".radioyes").on("click", function () {
        $("#NBaccount").hide();
        $(this).addClass("on").siblings().removeClass("on");
    });


    //新增银行
    $("#new").hide();
    $("#NBaccount").hide();
    $(".addAccount").on("click", function () {
        if ($(this).hasClass('onActive')) {
            $(this).removeClass('onActive');
            $(this).html("新增账户");
            $("#existing").show();
            $("#new").hide();
        } else {
            $(this).addClass('onActive');
            $(this).html("现有账户");
            $("#existing").hide();
            $("#new").show();
        }
    })

    //持票方信息-现有账户-查询银行详细信息
    function sendbankAccount(value) {
        console.log(value)
        $.ajax({
            url: "/modules/common/action/bizBaseAction/getSignBankAccountMsg.htm",
            data: {
                collectingBankAccount: value,
                collectorAccountId: $("input[name='holdAccountId']").val()
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                var data = result.data;
                $("#existing input[name='holdAccountName']").val(data.bankName);
                $("#existing input[name='holdAccountSubbranchName']").val(data.openingBank);
                $("#existing input[name='holdAccountSubbranchCode']").val(data.bankCode);
            }
        });
    };

    $("#bankAccountText").on("blur", sendbankAccount3);
    $("#holdAccountId").on("blur", sendbankAccount3);
    //持票方信息-现有账户-查询银行详细信息
    function sendbankAccount3() {
        var collectingBankAccount = $("input[name='bankAccountText']").val();
        var collectorAccountId = $("input[name='holdAccountId']").val();
        if (collectingBankAccount && collectorAccountId) {
            $.ajax({
                url: "/modules/common/action/bizBaseAction/getSignBankAccountMsg.htm",
                data: {
                    collectingBankAccount: collectingBankAccount,
                    collectorAccountId: collectorAccountId
                },
                type: "get",
                dataType: "json",
                success: function (result) {
                    var data = result.data;
                    if (result.code == 200) {
                        $("#existing input[name='holdAccountName']").val(data.bankName);
                        $("#existing input[name='holdAccountSubbranchNameText']").val(data.openingBank);
                        $("#existing input[name='holdAccountSubbranchCode']").val(data.bankCode);
                        $("#existing input[name='holdAccountCode']").val(data.bankNo);
                        $("#existing input[name='holdAccountCodeText']").val(data.bankNoName);
                        $('#existing .disabled').attr("disabled", true);
                    } else {
                        $("#existing input[name='holdAccountName']").val("");
                        $("#existing input[name='holdAccountSubbranchNameText']").val("");
                        $("#existing input[name='holdAccountSubbranchCode']").val("");
                        $("#existing input[name='holdAccountCode']").val("");
                        $("#existing input[name='holdAccountCodeText']").val("");
                        $('#existing .disabled').attr("disabled", false);
                    }
                }
            });
        }
    };

    //收票方信息-现有账户-查询银行详细信息
    function sendbankAccount2(value) {
        console.log(value)
        $.ajax({
            url: "/modules/common/action/bizBaseAction/getSignBankAccountMsg.htm",
            data: {
                collectingBankAccount: value,
                collectorAccountId: $("input[name='mobile']").val()
            },
            type: "get",
            dataType: "json",
            success: function (result) {
                var data = result.data;
                $(".basie2 input[name='bankName']").val(data.bankName);
                $(".basie2 input[name='collectorOpenBankName']").val(data.openingBank);
                $(".basie2 input[name='collectorBankCode']").val(data.bankCode);
                if (value) {
                    $.ajax({
                        type: "get",
                        url: "/modules/common/action/bizBaseAction/getAccountBalance.htm",
                        dataType: 'json',
                        data: {
                            bankAccount: $("#collectingBankAccount").val()
                        },
                        success: function (result) {
                            if (result.balance) {
                                $("#accountBalance").val(result.balance);
                            }
                        }
                    })
                }
            }
        });
    };


    $("#holdAccountId").on("blur", sendholdAccountId);
    //持票方信息-现有账户银行账户获取下拉数据
    function sendholdAccountId() {
        var collectorAccountId = $("input[name='holdAccountId']").val();
        if (collectorAccountId) {
            $.ajax({
                url: "/modules/common/action/bizBaseAction/getSignBankByAccount.htm",
                type: "get",
                dataType: 'json',
                data: {
                    collectorAccountId: $("input[name='holdAccountId']").val()
                    // signStatus: 10
                },
                success: function (result) {
                    var data = result.data;
                    var len = data.length;
                    var str = "";

                    for (i = 0; i < len; i++) {
                        str += "<li data-value='" + data[i].bankAccount + "'>" + data[i].bankAccount + "</li>";
                    }
                    $("#bankAccountlist").append(str);
                    //现有账户-查询银行详细信息 
                    $("#bankAccountlist li").click(function (e) {
                        var value = $(this).attr('data-value');
                        sendbankAccount(value)
                    })
                }
            });
        }
    };


    //收票方信息-银行账户获取下拉数据
    $.ajax({
        url: "/modules/common/action/bizBaseAction/getSignBankByAccount.htm",
        type: "get",
        dataType: 'json',
        data: {
            collectorAccountId: $("input[name='mobile']").val(),
            signStatus: 20
        },
        success: function (result) {
            var data = result.data;
            var len = data.length;
            var str = "";

            for (i = 0; i < len; i++) {
                str += "<li data-value='" + data[i].bankAccount + "'>" + data[i].bankAccount + "</li>";
            }
            $("#collectingBankAccountlist").append(str);
            //收票方信息-查询银行详细信息 
            $("#collectingBankAccountlist li").click(function (e) {
                var value = $(this).attr('data-value');
                sendbankAccount2(value)
            })
        }
    });

    //票据类型获取下拉数据
    $.ajax({
        url: "/getDicts.htm",
        type: "get",
        dataType: 'json',
        data: {
            type: "BILL_TYPE"
        },
        success: function (result) {
            var data = result.data;
            var len = data.length;
            var str = "";

            for (i = 0; i < len; i++) {
                str += "<li data-value='" + data[i].value + "'>" + data[i].text + "</li>";
            }
            $("#billsTypelist").append(str);
        }
    });
      //固定商票的属性

    $("#mySelect").on("blur", changeBillType);
    function changeBillType() {
        var billValue=$("#mySelect").val();
        if(billValue=="商票"){
            var len = dataBill.length;
            for(i=0;i<len; i++){
                if(dataBill[i].text=="其他"){
                    $("#billPropertie").val(dataBill[i].value);
                    $("#mySelectPropertie").val(dataBill[i].text);
                    $("#mySelectPropertie").attr("disabled",true);
                }
            }
        }else {
            $("#mySelectPropertie").val("");
            $("#mySelectPropertie").attr("disabled",false);
        }
    }
    //票据属性获取下拉数据
    $.ajax({
        url: "/getDicts.htm",
        type: "get",
        dataType: 'json',
        data: {
            type: "BILL_ATTRIBUTE"
        },
        success: function (result) {
            var data = result.data;
            var len = data.length;
             dataBill=result.data;
            var str = "";

            for (i = 0; i < len; i++) {
                str += "<li data-value='" + data[i].value + "'>" + data[i].text + "</li>";
            }
            $("#billsAttributelist").append(str);
        }
    });


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



    //查询银行
    $("#collectingBankAccount").on("blur", send);
    $("#collectorAccountId").on("blur", send);
    function send() {
        var collectingBankAccount = $("#collectingBankAccount").val();
        var collectorAccountId = $("#collectorAccountId").val();
        if (collectingBankAccount && collectorAccountId) {
            $.ajax({
                url: "/modules/common/action/bizBaseAction/getSignBankAccountMsg.htm",
                data: {
                    collectingBankAccount: collectingBankAccount,
                    collectorAccountId: collectorAccountId
                },
                type: "get",
                dataType: "json",
                success: function (result) {
                    var data = result.data;
                    if (result.code == 200) {
                        $("#bankName").val(data.bankName);
                        $("#collectorBankCode").val(data.bankCode);
                        $("#collectorOpenBankName").val(data.openingBank);
                    } else {
                        var restData = {};
                        restData.bankName = '';
                        restData.collectorBankCode = '';
                        restData.collectorOpenBankName = '';
                        $("#bill-form").setForm(restData); //切换总行清空支行
                    }
                }
            });
        }
    };

    //查询票号是否重复
    $("input[name='billNo']").on("blur", sendbillNo);
    function sendbillNo() {
        var me = this;
        var value = $(me).val();
        require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
            if (value) {
                if ( value.length == 30) {
                    var firstCharacter = value.substr(0,1);
                    if(firstCharacter != 1 && firstCharacter != 2){
                        layer.msg("票据号码有误", {
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function () {
                            $(me).val('');
                        });
                    }else{
                        $.ajax({
                            url: "/modules/business/action/directBusinessAction/checkBillIsOccupied.htm",
                            data: {
                                billNo: value
                            },
                            type: "get",
                            dataType: "json",
                            success: function (result) {
                                if (result.code == "400") {
                                    layer.msg(result.msg, {
                                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                    }, function () {
                                        $(me).val('');
                                    });
                                }else{
                                    if(firstCharacter == 1 ){
                                        $("#mySelect").val("银票")
                                        $("#billsType").val("10")
                                    }else{
                                        $("#mySelect").val("商票")
                                        $("#billsType").val("11")
                                    }
                                    changeBillType();
                                    $("#mySelect").attr("disabled",true);
                                }
                            }
                        });
                    }
                } else {
                    layer.msg("请输入30位的票号", {
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function () {
                        $(me).val('');
                    });
                }
            }
        });
    };

    //背书账户一致a
    $("#yes-a").on("click", function () {
        $("#existing").hide();
        $("#new").hide();
    });
    $("#no-a").on("click", function () {
        $("#new").hide();
        $("#existing").show();
    });

    //报价方式c
    $("#discountli").hide();
    $("#yes-c").on("click", function () {
        $("#yearRateli").show();
        $("#discountli").hide();
    });
    $("#no-c").on("click", function () {
        $("#yearRateli").hide();
        $("#discountli").show();
    });

    //查询余额
    $("#CheckBalance").click(function () {
        console.log($("#collectingBankAccount").val())
        if ($("#collectingBankAccount").val()) {
            $.ajax({
                type: "get",
                url: "/modules/common/action/bizBaseAction/getAccountBalance.htm",
                dataType: 'json',
                data: {
                    bankAccount: $("#collectingBankAccount").val()
                },
                success: function (result) {
                    if (result.balance) {
                        $("#accountBalance").val(result.balance);
                    }
                }
            })
        } else {
            require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
                layer.msg('请输入银行账户', {
                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                });
            });
        }
    });

    function VerificationList(list) {//验证票面信息全部必填
        var term = false
        for (var i = 0; i < list.length; i++) {
            var e = list[i];
            if (e.billNo && (e.billNo.length == 6 || e.billNo.length == 30) && e.billAmount && e.dueDate && e.adjustDays) {
                term = true;
            } else {
                term = false;
                break
            }
        }
        return term
    };

    function validateOrderBillType(list) {//验证订单中所有的票据类型是否统一
        var flag = true;
        var firstCharacter = list[0].substr(0,1);
        for (var i = 1; i < list.length; i++) {
            var nextCharacter = list[i].substr(0,1);
            if(firstCharacter != nextCharacter){
                flag = false;
                break;
            }
        }
        return flag
    };

    //表单验证
    require.async('../../module/jquery-validation-1.13.0/jquery.validate.min', function () {
        require.async('../../module/jquery-validation-1.13.0/additional-methods', function () {
            require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {
                //发起交易
                $("#bill-form").validate({
                    rules: {
                        bankAccountText: {
                            required: true,
                            maxlength: 35,
                            minlength: 8
                        },
                        billsTypeText: {
                            required: true
                        },
                        billsAttributeText: {
                            required: true
                        },
                        collectingBankAccount: {
                            required: true,
                            isNum: true
                        },
                        collectorAccountId: {
                            required: true
                        },
                        holdAccountId: {
                            required: true,
                            isMobile: true
                        },
                        serviceFee: {
                            required: true,
                            isNum: true,
                            max: 300
                        },
                        yearRate: {
                            required: true,
                            isNum: true,
                            max: 100,
                            min:0
                        },
                        discount: {
                            required: true,
                            isNum: true,
                            max: 99999.99
                        }
                    },
                    messages: {
                        bankAccountText: {
                            required: "必填",
                            maxlength: "请输入8位-35位的银行卡",
                            minlength: "请输入8位-35位的银行卡"
                        },
                        billsTypeText: {
                            required: "必填"
                        },
                        billsAttributeText: {
                            required: "必填"
                        },
                        collectingBankAccount: {
                            required: "必填",
                            isNum: "只能输入数字"
                        },
                        collectorAccountId: {
                            required: "必填"
                        },
                        holdAccountId: {
                            required: "必填",
                            isMobile: "请输入正确的手机号码格式"
                        },
                        serviceFee: {
                            required: "必填",
                            isNum: "只能输入数字",
                            max: "不得超过300元"
                        },
                        yearRate: {
                            required: "必填",
                            isNum: "只能输入数字",
                            max: "不得超过100%",
                            min: "不得小于0"
                        },
                        discount: {
                            required: "必填",
                            isNum: "只能输入数字",
                            max: "输入值过大"
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
                        var formData = $(form).serializeObject();
                        var postdata = {};
                        postdata['serviceFee'] = formData['serviceFee'];
                        postdata['quotationMethod'] = formData['quotationMethod'];
                        if (formData && formData.quotationMethod == "10") {
                            postdata['yearRate'] = formData['yearRate'] * 0.01;
                        } else {
                            postdata['discount'] = formData['discount'];
                        }
                        postdata['orderBills'] = formData
                        var orderBills = [];
                        $('.theShelvesTable > tbody > tr').each(function () {
                            var ssss = getParams(this);
                            if (Object.keys(ssss).length != 0) {
                                orderBills.push(ssss);
                            }
                        });
                        postdata['orderBills'] = orderBills;
                        formData['accountBalance'] = $("input[name='accountBalance']").val();
                        if (formData['accountBalance'] <= 0) {
                            layer.msg('请查询账户余额', {
                                time: 1000 //1秒关闭（如果不配置，默认是3秒）
                            });
                            return;
                        }
                        if (JSON.stringify(postdata.orderBills[0]) != undefined) {
                            if (!VerificationList(postdata.orderBills)) {//票面信息都必填
                                layer.msg('请补全票面信息', {
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                });
                                return;
                            }

                            /**需进行确认*/
                            /*if(validateOrderBillType(postdata.orderBills)){
                                layer.msg('同一订单中只能添加相同类型的票据，请对订单中的票据进行确认', {
                                    time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                });
                                return;
                            }*/
                            $.ajax({
                                type: "get",
                                url: "/modules/common/action/bizBaseAction/getTotalSettleAmount.htm",
                                dataType: 'json',
                                data: {
                                    dataMsg: JSON.stringify(postdata)
                                },
                                success: function (result) {
                                    var data = result.data;
                                    if (result.code == 200) {
                                        if (data.settlementAmount) {
                                            $("#settlementAmount").val(Number((data.settlementAmount).toFixed(2)));
                                        }
                                        var list = data.orderBills;
                                        if (list) {
                                            for (var index = 0; index < list.length; index++) {
                                                var element = list[index];
                                                $("#theShelvesTable tbody tr").eq(index).find(".singleSettleAmtlist").val(element.singleSettleAmt)
                                            }
                                        }
                                        layer.confirm("当前交易结算金额：" + Number((data.settlementAmount).toFixed(2)) + "元", {
                                            title: '发起交易', //标题 
                                            btn: ['发起交易', '取消'] //按钮
                                        }, function (index) {
                                            var param = $(form).serializeObject();
                                            var orderBills = [];
                                            $('.theShelvesTable > tbody > tr').each(function () {
                                                var ssss = getParams(this);
                                                if (Object.keys(ssss).length != 0) {
                                                    orderBills.push(ssss);
                                                }
                                            });
                                            param['orderBills'] = orderBills; 
                                            var temp = [];
                                            orderBills.forEach(function(item) {
                                                if (item.billNo) {
                                                    temp.push(item.billNo)
                                                }
                                            }); 

                                            var ary = new Array(temp); 
                                            var nary = temp.sort(); 
                                            for (var i = 0; i < ary.length; i++) { 
                                                if (nary[i] == nary[i + 1]) {
                                                    layer.msg('票据号码不能重复', {
                                                        time: 1000 //2秒关闭（如果不配置，默认是3秒）
                                                    });
                                                    return; 
                                                }
                                            }

                                            param['businessType'] = "0100";
                                            param['holdAccountId'] = $("input[name='holdAccountId']").val();
                                            if (param && param['quotationMethod'] == "10") {
                                                param['yearRate'] = param['yearRate'] * 0.01;
                                            }
                                            param = Object.assign(param, getParams($("#existing")));
                                            param['bankAccount'] = param['bankAccountText'];
                                            param['holdAccountSubbranchName'] = param['holdAccountSubbranchNameText'];

                                            param['settlementAmount'] = $("input[name='settlementAmount']").val();
                                            param['accountBalance'] = $("input[name='accountBalance']").val();
                                            param['collectorAccountId'] = $("input[name='collectorAccountId']").val();
                                            var url;
                                            if (window.location.search) {
                                                url = "/modules/business/action/directBusinessAction/updateOrder.htm";
                                                param['id'] = (window.location.search).substring(1);
                                            } else {
                                            	url = "/modules/business/action/directBusinessAction/initiateBusiness.htm";
                                            };
                                            if (param['accountBalance'] <= 0) {
                                                layer.msg('请查询账户余额', {
                                                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                                                });
                                                return;
                                            }
                                            if (!param['settlementAmount']) {
                                                layer.msg('请计算结算总金额', {
                                                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                                                });
                                                return;
                                            }

                                            $.ajax({
                                                type: "post",
                                                url: url,
                                                dataType: 'json',
                                                data: {
                                                    formData: JSON.stringify(param)
                                                },
                                                success: function (data) {
                                                    if (data.code == 200) {
                                                        layer.open({
                                                            type: 1,
                                                            title: "发起交易",
                                                            area: ['580px', '250px'],
                                                            border: [1, 1, '#cecfd0'],
                                                            content: $('#Orders-success'),
                                                            close: function (index) {
                                                                layer.close(index);
                                                            },
                                                            end: function () {
                                                                layer.close();
                                                            }
                                                        })
                                                    }
                                                    else if (data.code == 400) {
                                                        $("#Orders-fail p").html(data.msg);
                                                        layer.open({
                                                            type: 1,
                                                            title: "发起交易",
                                                            area: ['580px', '250px'],
                                                            btn: ['确定'],
                                                            border: [1, 1, '#cecfd0'],
                                                            content: $('#Orders-fail'),
                                                            close: function (index) {
                                                                layer.close(index);
                                                            },
                                                            end: function () {
                                                                layer.close();
                                                            }
                                                        })
                                                    } else {
                                                        $("#Orders-fail p").html("交易失败");
                                                        layer.open({
                                                            type: 1,
                                                            title: "发起交易",
                                                            btn: ['确定'],
                                                            area: ['580px', '250px'],
                                                            border: [1, 1, '#cecfd0'],
                                                            content: $('#Orders-fail'),
                                                            close: function (index) {
                                                                layer.close(index);
                                                            },
                                                            end: function () {
                                                                layer.close();
                                                            }
                                                        })
                                                    }
                                                }
                                            })
                                        }, function (index) {
                                            layer.close(index);
                                        });
                                    } else {
                                        layer.msg(result.msg, {
                                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                                        });
                                    }
                                }
                            })
                        } else {
                            console.log(666)
                            layer.msg('至少输入一条票面信息', {
                                time: 2000 //2秒关闭（如果不配置，默认是3秒）
                            });
                        }
                    }
                })
            });
        });
    });

    //修改-获取订单数据 
    if (window.location.search) {
        $.ajax({
            url: '/modules/business/action/directBusinessAction/queryOrderDetail.htm',
            type: "get",
            dataType: "json",
            data: {
                orderId: (window.location.search).substring(1)
            },
            success: function (result) {
                var data = result.data;
                var list = data.orderBills;
                if (list) {
                    var tbody = document.createElement("tbody");
                    $.each(list, function (i, n) {
                        var row = $(".template").clone();
                        row.find(".billNo").val(n.billNo);
                        row.find(".billAmount").val((n.billAmount) / 10000);
                        row.find(".dueDate").val(n.dueDate);
                        row.find(".adjustDays").val(n.adjustDays);
                        row.find(".singleSettleAmtlist").val(n.singleSettleAmt);
                        row.appendTo(tbody);//添加到模板的容器中)
                        row.find(".subtractBtn").on('click', subtractBtn);
                    });
                    $("#theShelvesTable").html("<thead><tr><th>票据号码</th><th>票面金额（万元）</th><th>到期日</th><th>调整天数(天)</th><th style='width: 70px;'>操作</th></tr></thead>").append(tbody);
                }
                if (data.yearRate) {
                    data.yearRate = data.yearRate * 100;
                }
                $("#bill-form").setForm(data);
                if (data.quotationMethod == "10") {
                    $("#yearRateli").show();
                    $("#discountli").hide();
                } else {
                    $("#yearRateli").hide();
                    $("#discountli").show();
                }
            }
        })
    }
});
