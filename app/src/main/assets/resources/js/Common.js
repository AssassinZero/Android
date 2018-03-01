// JavaScript Document
define(function (require, exports, module) {
    require('/resources/js/jquery');
    require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
    
    (function($){  //liFeng
        //备份下jquery的ajax方法  
        var _ajax=$.ajax;  
           
        //重写jquery的ajax方法  
        $.ajax=function(opt){  
            //备份opt中error和success方法  
            var fn = {  
                error:function(XMLHttpRequest, textStatus, errorThrown){},  
                success:function(data, textStatus){}  
            }  
            if(opt.error){  
                fn.error=opt.error;  
            }  
            if(opt.success){  
                fn.success=opt.success;  
            }  
               
            //扩展增强处理  
            var _opt = $.extend(opt,{  
                error:function(XMLHttpRequest, textStatus, errorThrown){  
                    //错误方法增强处理 
                    console.log(XMLHttpRequest);
                    if(XMLHttpRequest.responseText.indexOf('登录')>-1){//session超时后台返回'800'，跳转到登录页面
                        layer.msg('服务器连接超时,即将跳转到登录页面！', {time:1000}, function(){
                            window.location.href = "/User/login.htm";
                        });
                    }
                    fn.error(XMLHttpRequest, textStatus, errorThrown);  
                },  
                success:function(data, textStatus){  
                    // console.log(data);
                    // console.log(textStatus);
                    //成功回调方法增强处理  
                    // if(data&&data.code=='800'){//session超时后台返回'800'，跳转到登录页面
                    //     // layer.confirm('服务器连接超时，是否跳转到登录页面？', {  
                    //     //   btn: ['确定','关闭'] //按钮
                    //     // }, function(){
                    //     //   window.location.href = "/User/login.htm";
                    //     // }, function(){
                          
                    //     // }); 
                    //     layer.msg('服务器连接超时,即将跳转到登录页面！', {time:1000}, function(){
                    //         window.location.href = "/User/login.htm";
                    //     });
                    //     return; 
                    // }
                    fn.success(data, textStatus);  
                },  
                beforeSend:function(XHR){ 
                    //console.log(XHR);
                    //提交前回调方法  
                    //$('body').append("<div id='ajaxInfo' style=''>正在加载,请稍后...</div>");  
                },  
                complete:function(XHR, TS){  
                    //console.log(XHR);
                    //console.log(TS);  
                    //请求完成后回调函数 (请求成功或失败之后均调用)。  
                    //$("#ajaxInfo").remove();;  
                }  
            });  
            return _ajax(_opt);  
        };  
    })(jQuery);

    });

    $('input[name!="yearRate"]').bind('input propertychange', function(e){//限制部分input只能输入2位小数

        if(e.target.value&&!isNaN(e.target.value)){
            if(e.target.name=='billAmount'){
                value = e.target.value;
                value = value.replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/,'$1$2.$3');
                e.target.value =  value;
            } else { 
                value = e.target.value;
                value = value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
                e.target.value =  value;
            }
        }
    });

    $("input[name='yearRate']").bind('input propertychange', function(e){//限制年利率input只能输入4位小数

        if(e.target.value&&!isNaN(e.target.value)){
            if(e.target.name=='billAmount'){
                value = e.target.value;
                value = value.replace(/^(\-)*(\d+)\.(\d\d\d\d\d\d).*$/,'$1$2.$3');
                e.target.value =  value;
            } else {
                value = e.target.value;
                value = value.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3');
                e.target.value =  value;
            }
        }
    });
    window.ids = [];
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };

    //下拉
    $(".selectList input").click(function(){
        $('.selectList').children('ul').hide();
        $(this).parent('.selectList').children('ul').show();
    });
    /*$(".selectList input").blur(function(){
        $(this).parent('.selectList').children('ul').hide();
    });*/
    $(".selectList ul").delegate('li','click',function(){ 
        $(this).parent('ul').siblings("input[type='hidden']").val($(this).attr('data-value'));
        var obj = $(this).parent('ul').siblings("input[type='text']");
        var text = $(this).text(); 
        obj.val(text);
        obj.focus();
        obj.blur();
        //$(".selectList input[type='hidden']").val($(this).val());
        //$(".selectList input[type='mySelect']").val($(this).text());
    });
    $(document).on('click', function (e) {
        if (!$(e.target).is('.selectList input')) {
            $(".selectList ul").hide();
        }
    });

    $(function () { //网站顶部导航选中切换
        var url = window.location.href;
        $('#nav li').removeClass('active');
        if (url.indexOf('UserCenterMember') > 0) {
            $('.UserCenterMember').addClass('active');
        } else if (url.indexOf('BillShop') > 0) {
            $('.BillShop').addClass('active');
        } else if (url.indexOf('home') > 0) {
            $('.home').addClass('active');
        } else if (url.indexOf('AboutUs') > 0) {
            $('.AboutUs').addClass('active');
        }

    });

    function addNowClass(url, scopeList, scopeAll) {
        scopeAll.removeClass('active');
        for (var i = 0; i < scopeList.length; i++) {
            if (url.indexOf(scopeList[i]) > 0) {
                $('.' + scopeList[i]).addClass('active');
            }
        }
    };

    $(function () { //用户中心左边导航选中切换
        var url = window.location.href;
        var scopeAll = $('.userCenterNav li');
        var scopeList = ['accountOverview', 'buyersMemberUser', 'sellerMemberUser', 'ElectricityOrders', 'PureOrder', 'myNotes', 'securityCenter', 'userManage', 'accountInfor', 'buyersAverageUser', 'sellerAverageUser', 'historyBill', 'averageaccountInfor', 'myDeal'];
        addNowClass(url, scopeList, scopeAll)
    });


    $.fn.setForm = function (jsonValue) {  //公共赋值方法
        //alert("setForm");  
        var obj = this;
        $.each(jsonValue, function (name, ival) {
            var $oinput = obj.find("input[name=" + name + "]");
            if ($oinput.attr("type") == "radio" || $oinput.attr("type") == "checkbox") {
                $oinput.each(function () {
                    if (Object.prototype.toString.apply(ival) == '[object Array]') {//是复选框，并且是数组  
                        for (var i = 0; i < ival.length; i++) {
                            if ($(this).val() == ival[i])
                                $(this).attr("checked", "checked");
                        }
                    } else {
                        if ($(this).val() == ival) {
                            //$(this).parent().addClass("on");
                            //$(this).attr("checked", "checked"); 
                            
                            $(this).trigger("click")
                            $(this).parent().trigger("click")
                        }
                    }
                });
            } else if ($oinput.attr("type") == "textarea") {//多行文本框  
                obj.find("[name=" + name + "]").html(ival);
            } else if ($oinput.attr("type") == "hidden") {//自定义下拉框 
                var options = obj.find("[name=" + name + "]").next().find('li'); 
                if(options.length>0){
                    options.each(function () {
                        if ($(this).attr('data-value') == ival){
                          $(this).trigger("click")
                        }
                    })
                }else{//如果没有下拉选项或者未加载下拉，直接赋后台返回值
                   obj.find("[name=" + name + "]").val(ival); 
                   var name2 = name+'Text';
                   obj.find("[name=" + name2 + "]").val(ival); 
                }
            } else {
                obj.find("[name=" + name + "]").val(ival);
            }
        });
    };

    $('.parentMenu').click(function () {
        if ($('.userCenterNavIcon').hasClass('onActive')) {
            $('.userCenterNavIcon').removeClass('onActive')
            $('.hideChild').removeClass('onActive')
        } else {
            $('.userCenterNavIcon').addClass('onActive')
            $('.hideChild').addClass('onActive')
        }

    });
    $('.parentMenu2').click(function () {
        if ($('.userCenterNavIcon2').hasClass('onActive')) {
            $('.userCenterNavIcon2').removeClass('onActive')
            $('.hideChild2').removeClass('onActive')
        } else {
            $('.userCenterNavIcon2').addClass('onActive')
            $('.hideChild2').addClass('onActive')
        }

    });
    
    $('.labelCheck').click(function () {//列表多选checkbox-方法3/1
        var ids = window.ids;
        if ($(this).hasClass('onActive')) {
            $(this).removeClass('onActive');
            $('.listBoxs label').each(function (index, element) {
                if ($(element).hasClass("onActive")) {
                    $(this).removeClass('onActive')
                }
            });
            var chechedList = $('label');
            for (var i = 0; i < chechedList.length; i++) {
                ids.remove(chechedList[i].htmlFor)
            }
            console.log(ids)
            $(".shoplist #batchOrder").html("批量下单");
            $(".shoplist #batchOrder").removeClass("Order");
        } else {
            $(this).addClass('onActive');
            $('.listBoxs label').each(function (index, element) {
                if (!$(element).hasClass("onActive")) {
                    $(this).addClass('onActive')
                }
            });
            var chechedList = $('label.onActive');
            for (var i = 0; i < chechedList.length; i++) {
                if (ids.indexOf(chechedList[i].htmlFor) < 0) {
                    ids.push(chechedList[i].htmlFor)
                }
            }
            console.log(ids)
            $(".shoplist #batchOrder").html("确定下单");
            $(".shoplist #batchOrder").addClass("Order");
        }
    })

    $('.fuzzyQuery').bind('input propertychange', function(e) {//前台select模糊查询
        var value = e.target.value;
        var optionList=[];
        var oplist="";
        var bankData =window.bankData;
        for(i=0;i<bankData.length;i++){
            if(bankData[i].bankName.indexOf(value)>-1){
               optionList.push(bankData[i])
            }
        }
        for(var i=0;i<optionList.length;i++){
            ops="<li data-value='"+optionList[i].bankCode+"'>"+optionList[i].bankName+"</li>";
            oplist=oplist+ops;
        }
        if(oplist.length<1){
           oplist = "<a class='notFound'>无法找到</a>"
        }
        $("#holdAccountCodelist").html(oplist);
        $("#holdAccountCodelist li").click(function (e) {
            var restData ={};
            restData.analogueOpeningBankText='';
            restData.analogueOpeningBank='';
            restData.analogueBankNumber='';
            restData.subbranchBankNameText='';
            restData.subbranchBankName='';
            restData.subbranchBankCode='';
            $("#securityFrom3").setForm(restData); //切换总行清空支行
            $("#userInfoForm2").setForm(restData); //切换总行清空支行
            $("#holdAccountSubbranchNamelist").html(''); 
        });
    })
    /*$('.fuzzyQuery').bind('input propertychange', function(e) {//前台select模糊查询
        var value = e.target.value;
        var optionList=[];
        var oplist="";
        var bankBranchData =window.bankBranchData;
        if(bankBranchData&&bankBranchData.length>0){
            for(i=0;i<bankBranchData.length;i++){
                if(bankBranchData[i].bankName.indexOf(value)==0){
                   optionList.push(bankBranchData[i])
                }
            }
            for(var i=0;i<optionList.length;i++){
                ops="<li title='" + optionList[i].bankName + "' data-value='" + optionList[i].bankNo + "'>" + optionList[i].bankName + "</li>";
                oplist=oplist+ops;
            }
            if(oplist.length<1){
               oplist = "<a class='notFound'>无法找到</a>"
            }
            $("#holdAccountSubbranchNamelist").html(oplist);
        }
    })*/


    // //脢脰禄煤脢脟路帽脩茅脰陇tips脪脝脠毛脪脝鲁媒脢脗录镁 
    // require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {
    //     $('.sameIcons.userPhone').mouseover(function() {
    //         var scope = $(this);
    //         var msg = "";
    //         if (scope.is('.active')) {
    //             msg = '脢脰禄煤脪脩脩茅脰陇'
    //         } else {
    //             msg = '脢脰禄煤脦麓脩茅脰陇'
    //         }
    //         layer.tips(msg, scope, {
    //             time: 2000,
    //             tips: [1, '#000000'],
    //         });
    //     });
    //     $('.sameIcons.userPhone').mouseout(function() {
    //         var scope = $(this);
    //         var index = layer.tips();
    //         layer.close(index);
    //     });
    //     //脫脢脧盲脢脟路帽脩茅脰陇tips脪脝脠毛脪脝鲁媒脢脗录镁 
    //     $('.sameIcons.userEmail').mouseover(function() {
    //         var scope = $(this);
    //         var msg = "";
    //         if (scope.is('.active')) {
    //             msg = '脫脢脧盲脪脩脩茅脰陇'
    //         } else {
    //             msg = '脫脢脧盲脦麓脩茅脰陇'
    //         }
    //         layer.tips(msg, scope, {
    //             time: 2000,
    //             tips: [1, '#000000'],
    //         });
    //     });
    //     $('.sameIcons.userEmail').mouseout(function() {
    //         var scope = $(this);
    //         var index = layer.tips();
    //         layer.close(index);
    //     });
    // });  
});
