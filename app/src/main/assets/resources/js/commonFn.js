// 公共函数
define(function (require, exports, module) {
	require('./jquery');
	var comfn = {};

	//查找指定元素的下标
	Array.prototype.indexOf = function (val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	// tmp.indexOf("blue")

	//删除指定元素
	Array.prototype.remove = function (val) {
		var index = this.indexOf(val);
		while (index > -1) {
			this.splice(index, 1);
			index = this.indexOf(val);
		}
		return this;
	};
	// tmp.remove("blue")

	// 表单转化成json
	$.fn.serializeObject = function () {
		var o = {};

		var a = this.serializeArray();
		$.each(a, function () {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};
	$.fn.serializeObject();


	//合并对象
	comfn._extends = Object.assign || function (target) {
		for (var i = 1; i < arguments.length; i++) {
			var source = arguments[i];

			for (var key in source) {
				if (Object.prototype.hasOwnProperty.call(source, key)) {
					target[key] = source[key];
				}
			}
		}

		return target;
	};

	/**
	 * [点击元素时网页回到顶部]
	 * @scope 作用域
	 * 
	 */
	comfn.scrollToTop = function (scope) {
        $('html,body').animate({scrollTop:0},10);//回到顶端
	    return false;
	}
	/**
	 * [设置数字小标方法]
	 * @scope 作用域
	 * 
	 */
	comfn.setSmallScale = function (total,scope) {
		var totalTips = total;
		if(9<total&&total<100){ 
		    scope.css('width','26px');
		    scope.css('border-radius','16px');
		    scope.css('-moz-border-radius','16px');
		}else if(total>99){
			totalTips="···";
		    scope.css('font-weight','bold');
		}
		scope.html(totalTips);
	}
    /**
	 * [重置表单方法]
	 * @scope 表单域
	 * 
	 */
	comfn.resetForm = function (scope) {
		scope[0].reset();//置空  
		if(scope&&scope.validate){
			scope.validate().resetForm();//重置验证 
		}
		scope.find('p.errorText').text('');//置空错误提示
		var radioList = scope.find('input[type="radio"]');
		radioList.each(function () {
			if ($(this).attr("checked")) {
				//$(this).parent().addClass('on');
				$(this).trigger('click')
			} else {
				//$(this).parent().removeClass('on'); 
			}
		});
	}

	/**
	 * [千分位方法]
	 * @num 数字
	 * @step 小数点位数【默认两位】
	 */
	comfn.commafy = function (num, step) {
		var microValue;
		var floatSection; // 小数部分
		num = num.toString().replace(/,/g, ''); //避免带逗号的数据传入
		var value = String(parseFloat(num)); // 吧传入数据转换为float后再转换为字符串

		var arr = value.split('.');
		var isFloat = arr.length == 4||2; //判断是否是浮点数
		if (isFloat) {
			floatSection = arr[1].substr(0, step); // 小数部分
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
			microValue = microValue + '.' + "00"
		}

		if (typeof microValue == 'undefined' || microValue == 'NaN') {
			microValue = ""
		}
		return microValue;
	}

	/**
	 * [订单详细信息的tab切换]
	 * @author
	 * @Datatime 2017-03-11
	 */
	comfn.tabSwitch = function () {
		$('.lf-tab-title li').click(function (e) {
			var $this = $(this);
			var index = $this.index();
			var content = $('.lf-tab-content div.lf-tab-item');
			$tab = $this.parents('.lf-tab-title');
			$tab.find('li').removeClass('lf-this');
			$this.addClass('lf-this');
			content.removeClass('lf-show');
			content.eq(index).addClass('lf-show');
		});
		$('.lfTab-item-right li').click(function (e) {
			var $this = $(this);
			if ($this.is('.active')) {
				$this.removeClass('active');
				$this.find('div.silderDiv').hide();
			} else {
				$('.lfTab-item-right li div.silderDiv').hide();
				$('.lfTab-item-right li').removeClass('active');
				$this.addClass('active');
				$this.find('div.silderDiv').show();
			}
		});
	}

	/**
	 * [订单信息-弹窗][仅限查看操作的页面]
	 * [订单取消-弹出]
	 * @author
	 * @Datatime 2017-03-11
	 */
	var timer1; //倒计时对象
	comfn.operationClick = function (checkData) {
		require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function () {

			//订单信息-弹窗
			// console.log("checkData")
			/*$('.orderInfor').unbind("click").bind("click", function(e) {
				// console.log("0000")
				// var thisIndex = $(this).parents(".cashItem").index();
				var countDownTimeEle = $(this).parents(".cashItem").find(".tradeRecordLastTime").eq(0);

				// 注：ie8(兼容模式),ie7和ie6没有JSON对象，推荐采用JSON官方的方式，引入json.js。
				var checkData = JSON.parse($(this).parents(".cashItem").attr("data-json"))
					// console.log(checkData)

				layer.open({
					type: 1, //弹窗类型
					title: '订单信息', //标题
					area: ['835px', '605px'], //大小
					skin: 'layui-layer-rim', //样式类名
					closeBtn: 1, //0不显示关闭按钮,1显示
					btn: ['关闭'], //按钮
					anim: 2, //动画
					//shadeClose: true, //开启遮罩关闭
					content: $('.orderInforWarp'), //dom元素
					success: function(layer) {

						var tpl = require('/resources/tpls/orderDetail.tpl'); //载入tpl模板
						var template = Handlebars.compile(tpl);
						var html = template(checkData);
						$("#orderInforWarp").html(html);
						// console.log("countDownTimeEle.length-------"+countDownTimeEle.length)

						// 判断是否有倒计时
						if (countDownTimeEle.length > 0) {
							clearInterval(timer1);
							timer1 = setInterval(function() {
								$("#detailTimeFun").html(countDownTimeEle.html());
								// console.log(countDownTimeEle.html());
							}, 1000)
						}
						comfn.tabSwitch(); //订单详细信息的tab切换
					},
					end: function() {
						// 关闭时销毁倒计时
						clearInterval(timer1);
					}
				});

			});*/

			// 订单取消
			// $('.cancel').unbind("click").bind("click",function(e) {

			// 	var setData = {
			// 		id: $(this).attr("data-id"),
			// 		receiptId: $(this).attr("data-receiptId"),
			// 		orderStatus: $(this).attr("data-orderStatus")
			// 	}
			// 	layer.open({
			// 		type: 1, //弹窗类型
			// 		title: '取消交易', //标题
			// 		area: ['550px', '500px'], //大小
			// 		skin: 'layui-layer-rim', //样式类名
			// 		closeBtn: 1, //0不显示关闭按钮,1显示
			// 		btn: ['确定取消', '暂不取消'], //按钮
			// 		yes: function(index, layero) { //提交事件

			// 			var refueForm = $("#refuseFrom").serializeObject(); //获取取消理由
			// 			var refuseDate = _extends({}, setData, refueForm); //合并数据

			// 			layer.close(index); //关闭弹窗
			// 			$.ajax({
			// 				url: "/Buyer/reject.htm",
			// 				type: "post",
			// 				dataType: "json",
			// 				data: refuseDate,
			// 				success: function(result) {
			// 					if (result.code == 200) {
			// 						layer.msg('取消成功')
			// 						getListDate();
			// 							// layer.alert('取消成功', {
			// 							// 	title: false,
			// 							// 	icon: 1,
			// 							// 	closeBtn: 0,
			// 							// 	skin: 'myStyle'
			// 							// })
			// 						// setTimeout(function() {
			// 						// 	location.reload();
			// 						// }, 1500)
			// 					} else {
			// 						layer.msg(result.msg);
			// 					}
			// 				}
			// 			})
			// 		},
			// 		anim: 2, //动画
			// 		//shadeClose: true, //开启遮罩关闭
			// 		content: $('.cancelWarp') //dom元素
			// 	});
			// });

		});
	}

	/**
	 * [格式:2014-08-15]
	 * @author
	 * @Datatime 2017-03-11
	 */
	comfn.dateFormat = function (value) {
		function formatDate(now) {
			var year = now.getFullYear();
			var month = (now.getMonth() + 1) < 10 ? "0" + (now.getMonth() + 1) : (now.getMonth() + 1);
			var date = now.getDate() < 10 ? "0" + now.getDate() : now.getDate();
			return year + "-" + month + "-" + date;
		}
		if (value == null || value == '') {
			return '';
		}
		var d = new Date(value);
		return formatDate(d);
	}

	/**
	 * [包括中间空格,需要设置第2个参数为:g]
	 * 默认清除所有空格
	 */
	comfn.trimBank = function (str, is_global) {
		var result;
		is_global = is_global || "g";
		result = str.replace(/(^\s+)|(\s+$)/g, "");
		if (is_global.toLowerCase() == "g") {
			result = result.replace(/\s/g, "");
		}
		return result;
	}


	/**
	 * [正则校验]
	 */
	comfn.Reg = {
		// 正整数判断
		isNumber: function (value) {
			return /^[1-9]\d*$/.test(value);
		},
		//只能是数字
		isFigure: function (value) {
			return /^([+-]?)\d*\.?\d+$/.test(value);
		}
	}

	// 暴露对应接口
	module.exports = comfn;

	// 使用
	// var comfn = require('/resources/js/commonFn.js'); //调用公共文件
	// comfn.dateFormat();
})