/**
 * [千分位方法]
 * @num 数字
 * @step 小数点位数【默认两位】
 */
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


//编号递增
Handlebars.registerHelper("increasingIndex", function (value) {
	return value + 1;
})

function formatFloat(f, digits) {
	digits = !!digits ? digits : 10; // 默认精度为10
	return parseFloat(f.toFixed(digits))
}
// 两个参数相乘
Handlebars.registerHelper("multiplicationFn", function (v1, v2, options) {
	// return value + 1;
	var v1 = v1 || 0,
		v2 = v2 || 0;
	// formatFloat = function(f, digits) {
	// 	digits = !!digits ? digits : 10; // 默认精度为10
	// 	return parseFloat(f.toFixed(digits))
	// }
	var result = formatFloat(v1 * v2);
	// console.log(formatFloat(result, 2))
	return commafy(formatFloat(result, 2));
})

// 两个参数相减
Handlebars.registerHelper("subtractionFn", function (v1, v2, options) {
	// return value + 1;
	var v1 = v1 || 0,
		v2 = v2 || 0;
	var result = formatFloat(v1 - v2);
	// console.log(formatFloat(result, 2))
	return commafy(formatFloat(result, 2));
})

// 将json对象转换成json对符串 
Handlebars.registerHelper("toJsonString", function (jsonDate) {
	return JSON.stringify(jsonDate);
})

// 将int转换成String 
Handlebars.registerHelper("toString", function (value) {
	return String(value);
})

//时间转换  2014-08-15 00:00:00转换2014-08-1
Handlebars.registerHelper("splitDate", function (value) {
	if (value) {
		return String(value.split(' ')[0]);
	} else {
		return " ";
	}

})

//时间转换  格式:2014-08-15 00:00:00
Handlebars.registerHelper("transFormatDate", function (value) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		if (second < 10) {
			second = '0' + second;
		}
		if (minute < 10) {
			minute = '0' + minute;
		}
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	}
	if (value == null || value == '') {
		return '-';
	}
	var d = new Date(value.replace(new RegExp(/-/gm), "/"));
	return formatDate(d);
});
//时间转换  格式:2014-08-15 00:00
Handlebars.registerHelper("transFormatShortDate", function (value) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		if (second < 10) {
			second = '0' + second;
		}
		if (minute < 10) {
			minute = '0' + minute;
		}
		return year + "-" + month + "-" + date + " " + hour + ":" + minute;
	}
	if (value == null || value == '') {
		return '';
	}
	var d = new Date(value.replace(new RegExp(/-/gm), "/"));
	return formatDate(d);
});
// 格式:2014/08/15
Handlebars.registerHelper("noticeDateFormat", function (value) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		if (second < 10) {
			second = '0' + second;
		}
		return year + "/" + month + "/" + date;
	}
	if (value == null || value == '') {
		return '';
	}
	var d = new Date(value.replace(new RegExp(/-/gm), "/"));
	return formatDate(d);
});

// 格式:2014-08-15
Handlebars.registerHelper("dateFormat", function (value) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		return year + "-" + month + "-" + date;
	}
	if (value == null || value == '') {
		return '';
	}
	var d = new Date(value.replace(new RegExp(/-/gm), "/"));
	return formatDate(d);
});

// 格式:2014/08/15 12:00
Handlebars.registerHelper("noticeDateFormatNew", function (value) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		if (month < 10) {
			month = '0' + month;
		}
		if (date < 10) {
			date = '0' + date;
		}
		if (hour < 10) {
			hour = '0' + hour;
		}
		if (second < 10) {
			second = '0' + second;
		}
		return year + "/" + month + "/" + date + " " + hour + ":" + second;
	}
	if (value == null || value == '') {
		return '';
	}
	var d = new Date(value.replace(new RegExp(/-/gm), "/"));
	return formatDate(d);
});



// 保留两位小数
Handlebars.registerHelper("tofixed", function (value) {
	return value.toFixed(2);
});

// 根据state转义按钮
Handlebars.registerHelper("turnStateBtn", function (value,role) {
		if(role&&role.indexOf('30')>-1){
			return ' '
		}else if (value == '10') {
			return '锁定'
		} else {
			return '解锁'
		}
});
// 根据state转义中文
Handlebars.registerHelper("turnState", function (value) {
	if (value == '10') {
		return '正常'
	} else {
		return '锁定'
	}
});

// 根据role转义中文
Handlebars.registerHelper("turnRole", function (value) {
	if (value) {
		value = value.split(',');
		var list = [];
		$.each(value, function (index, value) {
			if (value == '10') {
				list.push('经办')
			} else if (value == '20') {
				list.push('复核')
			} else if (value == '30') {
				list.push('管理员')
			}
		});
		return list
	} else {
		return '未知'
	}
});


//时间格式转换 2014-8-18 16:09:27
Handlebars.registerHelper("timeFormat", function (time) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		if (second < 10) {
			second = '0' + second;
		}
		return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
	}
	if (time == null || time == '') {
		return '';
	}
	var d = new Date(time);
	return formatDate(d);
})

//时间格式转换 2014-9-4
Handlebars.registerHelper("timeMonthFormat", function (time) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		if (month < 10) {
			month = "0" + month;
		}
		if (date < 10) {
			date = "0" + date;
		}
		return year + "-" + month + "-" + date;
	}
	if (time == null || time == '') {
		return '';
	}
	var d = new Date(time);
	return formatDate(d);
})

//时间格式转换 2014年7月10日 12:32:00
Handlebars.registerHelper("timeMsgFormat", function (time) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();

		if (hour < 10) {
			hour = '0' + hour;
		}

		if (minute < 10) {
			minute = '0' + minute;
		}

		if (second < 10) {
			second = '0' + second;
		}
		return year + '年' + month + '月' + date + '日' + '' + hour + ':' + minute + ':' + second;
	}
	if (time == null || time == '') {
		return '';
	}
	return formatDate(new Date(time));
})

//时间格式转换 2014年7月10日
Handlebars.registerHelper("dateFormatChinese", function (time) {
	function formatDate(now) {
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();

		if (hour < 10) {
			hour = '0' + hour;
		}

		if (minute < 10) {
			minute = '0' + minute;
		}

		if (second < 10) {
			second = '0' + second;
		}
		return year + '年' + month + '月' + date + '日';
	}
	if (time == null || time == '') {
		return '';
	}
	return formatDate(new Date(time));
})

/**
 * [金额格式化]
 * 格式: 1,000.00
 */
Handlebars.registerHelper("toThousands", function (num) {
	// var step = step || 2;
	if (typeof num == 'undefined' || num == 'NaN' || !num) {
		return "0";
	} else {
		var microValue = commafy(num, 4)
		return microValue;
	}
})

//--S--个人中心-我的资料
//婚姻状况
Handlebars.registerHelper("transMaritalStatus", function (value) {
	switch (value) {
		case 1:
			return "未婚";
			break;
		case 2:
			return "已婚";
			break;
		case 3:
			return "离异";
			break;
		case 4:
			return "丧偶";
			break;
		default:
			return "";
			break;
	}
});


//年龄
Handlebars.registerHelper("transBirthday", function (value) {
	return (new Date()).getFullYear() - (new Date(value)).getFullYear() + "岁";
});

//车产情况
Handlebars.registerHelper("transCar", function (value) {
	switch (value) {
		case 1:
			return "无";
			break;
		case 2:
			return "有";
			break;
		default:
			return "";
			break;
	}
});

//车产情况
Handlebars.registerHelper("transRepaymentType", function (value) {
	return {
		1: '先息后本',
		2: '等额本息'
	}[value];
});

//房产状况
Handlebars.registerHelper("transLive", function (value) {
	switch (value) {
		case 0:
			return "自有住房";
			break;
		case 1:
			return "租赁";
			break;
		case 2:
			return "与亲属同住";
			break;
		case 3:
			return "公司宿舍";
			break;
		case 4:
			return "其它";
			break;
		default:
			return "";
			break;
	}
});

//教育情况
Handlebars.registerHelper("transEducation", function (value) {
	switch (value) {
		case 1:
			return "博士";
			break;
		case 2:
			return "硕士";
			break;
		case 3:
			return "本科";
			break;
		case 4:
			return "专科";
			break;
		case 5:
			return "高中及以下";
			break;
		default:
			return "";
			break;
	}
});


//个人中心-我的贷款-贷款类型
Handlebars.registerHelper("transproductType", function (value) {
	switch (value) {
		case 1:
			return "短期";
			break;
		case 2:
			return "长期";
		case 3:
			return "融资租赁";
			break;
		default:
			return "";
			break;
	}
});

//个人中心-我的贷款-还款状态
Handlebars.registerHelper("transRepaymentStaus", function (value) {
	switch (value) {
		case 1:
			return "正常还款";
			break;
		case 2:
			return "逾期未还款";
			break;
		case 3:
			return "逾期还款";
			break;
		case 4:
			return "提前还款";
			break;
		case 0:
			return "还款中";
			break;
		default:
			return "";
			break;
	}
});


//个人中心-我的贷款-还款情况表-还款状态
Handlebars.registerHelper("tranrepaymentType", function (value) {
	switch (value) {
		case 0:
			return "还款中";
			break;
		case 1:
			return "结清";
			break;
		case 2:
			return "逾期";
			break;
		default:
			return "";
			break;
	}
});


// 个人中心-我的贷款-审批情况
Handlebars.registerHelper("transtaskDef", function (value) {
	switch (value) {
		case 0:
			return new Handlebars.SafeString('<span class="green">贷款受理</span>');
			break;
		case 1:
			return new Handlebars.SafeString('<span class="green">贷款受理</span>');
			break;
		case 2:
			return new Handlebars.SafeString('<span class="red">审批失败</span>');
			break;
		case 3:
			return new Handlebars.SafeString('<span class="green">已结清</span>');
			break;
		default:
			return "";
			break;
	}
});


Handlebars.registerHelper("getPlatformFee", function (record) {
	var fee = record.account - record.capital - record.interest - record.parkingFee;
	return fee.toFixed(2);
});

Handlebars.registerHelper("getAllAccount", function (record) {
	var allAccount = record.account + record.penalty + record.defaultInterest;
	return allAccount.toFixed(2);
});

//判断为空或者null或者undefined
Handlebars.registerHelper("isBlank", function (v1, options) {
	if (v1 == "" || v1 == null || v1 == undefined) {
		//满足添加继续执行
		return options.fn(this);
	} else {
		//不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

//判断不为空或者null或者undefined
Handlebars.registerHelper("isNotBlank", function (v1, options) {
	if (v1 != null && v1 != undefined && v1.toString() != "") {
		//满足添加继续执行
		return options.fn(this);
	} else {
		//不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

Handlebars.registerHelper('operation', function (first, operation, second) {
	if (first !== undefined && operation && second) {
		if (isNaN(first) || isNaN(second)) {
			return 'error';
		}

		var opt = {
			'+': function (l, r) {
				return l + r;
			},
			'-': function (l, r) {
				return l - r;
			},
			'*': function (l, r) {
				return l * r;
			},
			'/': function (l, r) {
				return l / r;
			}
		}
		var scale = arguments[3];

		if (scale && typeof scale == 'number') {
			return opt[operation](first, second).toFixed(scale);
		} else {
			return opt[operation](first, second);
		}
	} else {
		// return multiplicand;
		console.log('first:' + first + ' operation:' + operation + ' second:' + second);
		return 'error';
	}
});

// 判断相等
Handlebars.registerHelper("equal", function (v1, v2, options) {
	if (v1 == v2) {
		//满足添加继续执行
		return options.fn(this);
	} else {
		//不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

// 不相等
Handlebars.registerHelper("notEqual", function (v1, v2, options) {
	if (v1 != v2) {
		//满足添加继续执行
		return options.fn(this);
	} else {
		//不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

// 判断是否是正数
Handlebars.registerHelper("isPositive", function (value, options) {
	var isPositive = /^[1-9]\d*$/.test(value);
	if (isPositive) {
		//满足添加继续执行
		return options.fn(this);
	} else {
		//不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

// 判断对象是否存在id
Handlebars.registerHelper("hasElement", function (billNo) {
	if (window.ids.indexOf(billNo) > -1) {
		return 'checked'
	} else {
		return 'false'
	}
})
// 判断对象是否存在id添加onActive class
Handlebars.registerHelper("hasElementAddClass", function (billNo) {
	if (window.ids.indexOf(billNo) > -1) {
		return 'onActive'
	} else {
		return ''
	}
})


//剩余天数
Handlebars.registerHelper("stayDateFormat", function (dueDate, serverTime) {
	if (new Date(dueDate) > 0) {
		var nowDate = new Date(serverTime);
		var dueTime = new Date(dueDate.toString().substring(0, 10));
		var s1 = dueTime.getTime(),
			s2 = nowDate.getTime();
		var total = (s1 - s2) / 1000;
		var day = parseInt(total / (24 * 60 * 60)); //计算整数天数
		day = day > 0 ? day : 0;
		return day;
	} else {
		return "-";
	}

});
Handlebars.registerHelper("Exif", function (v1, operator, v2, options) {
	switch (operator) {
		case "==":
			return (v1 == v2) ? options.fn(this) : options.inverse(this);

		case "!=":
			return (v1 != v2) ? options.fn(this) : options.inverse(this);

		case "===":
			return (v1 === v2) ? options.fn(this) : options.inverse(this);

		case "!==":
			return (v1 !== v2) ? options.fn(this) : options.inverse(this);

		case "&&":
			return (v1 && v2) ? options.fn(this) : options.inverse(this);

		case "||":
			return (v1 || v2) ? options.fn(this) : options.inverse(this);

		case "<":
			return (v1 < v2) ? options.fn(this) : options.inverse(this);

		case "<=":
			return (v1 <= v2) ? options.fn(this) : options.inverse(this);

		case ">":
			return (v1 > v2) ? options.fn(this) : options.inverse(this);

		case ">=":
			return (v1 >= v2) ? options.fn(this) : options.inverse(this);

		default:
			return eval("" + v1 + operator + v2) ? options.fn(this) : options.inverse(this);
	}
});

//剩余时间 格式 4:00
Handlebars.registerHelper("getLastTime", function (countDown) {
	if (countDown > 0) {
		var min = parseInt(countDown / 60); //分钟
		var second = countDown % 60; //秒
		return min + ":" + second;
	} else {
		return "0:00"
	}
})

//倒计时功能
Handlebars.registerHelper("showListCountDownTimeFun", function (idName, idIndex, putStartTime, nowTime) {
	// console.log(idName)
	// var sys_second1 = (putStartTime - nowTime) / 1000;
	var putStartTime = putStartTime || 0;
	var sys_second1 = putStartTime; //剩余时间

	var timer1 = setInterval(function () {
		if (sys_second1 >= 1) {
			sys_second1 -= 1;
			var day1 = Math.floor((sys_second1 / 3600) / 24);
			var hour1 = Math.floor((sys_second1 / 3600) % 24);
			var minute1 = Math.floor((sys_second1 / 60) % 60);
			var second1 = Math.floor(sys_second1 % 60);
			var showDay1 = day1;
			var showHour1 = hour1 < 10 ? "0" + hour1 : hour1; //计算小时
			var showMinute1 = minute1 < 10 ? "0" + minute1 : minute1; //计算分钟
			var showSecond1 = second1 < 10 ? "0" + second1 : second1; //计算秒杀
			// $("#showTime" + id).html('<i>' + showDay1 + '</i>天<i>' + showHour1 + '</i>时<i>' + showMinute1 + '</i>分<i>' + showSecond1 + '</i>秒');
			$("#" + idName + idIndex).html(showMinute1 + ":" + showSecond1);
		} else {
			clearInterval(timer1);
			// window.location.reload();
		}
	}, 1000);
});

Handlebars.registerHelper("ruleListExpires", function (expires) {
	switch (expires) {
		case "0":
			return "不限";
			break;
		case "1":
			return "足月:180天";
			break;
		case "2":
			return "足月:360天";
			break;
		case "3":
			return "不足月:1-180天";
			break;
		case "4":
			return "不足月:181-360天";
			break;
		default:
			return "不限";
			break;
	}
})


/**
 * [状态列表-和数据字典对应]
 * @type {Object}
 */
var statusList = {
	"01": "等待买方",
	"02": "待买方确认",
	"06": "待卖家签收电子担保函",
	"09": "待卖家签署合同",
	"10": "待买方付款",
	"12": "待卖家背书",
	"15": "待买家签收",
	"17": "待平台转账",
	"18": "待卖家确认收款",
	"21": "交割完成",
	"24": "交割失败"
}

/**
 * [orderDetail.tpl 详情展示函数是否展开]
 * 参数：
 * 操作列表 pjsOrderProcesseList
 * 数据状态 orderStatus
 * 当前数据状态值 indexStatus
 * 剩余时间 countDown
 * 
 * 规则：
 * // 数据状态 == 当前数据状态值 时 展开
 * // 列表中数据状态 和 当前匹配 表示已操作
 */
Handlebars.registerHelper("orderDetailActive", function (pjsOrderProcesseList, orderStatus, indexStatus, countDown) {
	// console.log(orderStatus == indexStatus)

	var className = ""; //样式名称
	var listLength = pjsOrderProcesseList.length || 0;

	for (i = 0; i < listLength; i++) {
		var thisOrder = pjsOrderProcesseList[i];

		// 列表中数据状态 和 当前匹配 表示已操作
		if (thisOrder.status == indexStatus) {
			// 表示该操作已执行
			className = "isOver ";
			return className;
		} else if (orderStatus == indexStatus) {
			// 表示在当前状态
			className = "isOver active";
			return className;

			// if (pjsOrderProcesseList[listLength - 1].status == indexStatus) {
			// 	// 表示该操作已执行 并且当前状态
			// 	className = "isOver active";
			// 	return className;
			// } else {
			// 	// 在当前状态【未执行】
			// 	className = "isOver active";
			// 	return className;
			// }
		}
	}


})

/**
 * [展开状态函数]
 * 参数：
 * 操作列表 pjsOrderProcesseList
 * 数据状态 orderStatus
 * 当前数据状态值 indexStatus
 * 剩余时间 countDown
 */
Handlebars.registerHelper("orderDetailOpen", function (pjsOrderProcesseList, orderStatus, indexStatus, countDown) {

	var listLength = pjsOrderProcesseList.length || 0;
	var htmlTest;
	for (i = 0; i < listLength; i++) {
		var thisOrder = pjsOrderProcesseList[i];

		// 列表中数据状态 和 当前匹配 有操作时间
		if (thisOrder.status == indexStatus) {
			htmlTest = thisOrder.createTime + "&nbsp;&nbsp;&nbsp;&nbsp;已操作";
			return new Handlebars.SafeString(htmlTest);
		} else {
			//最后一条数据才能返回[最后一条数据判断是否需要倒计时]
			if (i == listLength - 1) {
				htmlTest = "未操作"
				// 倒计时大于0 && 数据状态和列表中数据状态一致时 才有倒计时
				// 资金转账、交易完成、交易关闭 不需要倒计时
				if (countDown > 0 && orderStatus == indexStatus && orderStatus != "17" && orderStatus != "21" && orderStatus != "24") {
					htmlTest = htmlTest + '<div class="time"><span class="alarm"></span><span id="detailTimeFun">0:00</span></div>'
				}

				return new Handlebars.SafeString(htmlTest);
			}
		}
	}
	// timer1();

})

/**
 * [每个状态返回的文案]
 * orderStatus 当前状态
 */
Handlebars.registerHelper("orderStatusText", function (orderStatus) {
	return statusList[orderStatus]
	// $.ajax({
	// 		url: "/modules/common/action/ComboAction/queryDic.htm?typeCode=ORDER_STATUS",
	// 		type: "get",
	// 		dataType: "json",
	// 		success: function(result) {
	// 			var resultDate = result.data
	// 			var dataLength = resultDate.length;
	// 			var newDate = {};
	// 			for (var i = 0; i < dataLength; i++) {
	// 				var valId = resultDate[i].value;
	// 				newDate[valId] = resultDate[i].text;
	// 			}
	// 			console.log(newDate)
	// 		}
	// 	})
})

/**
 * [返回买家操作状态]
 */
Handlebars.registerHelper("sentBuyerOperation", function (orderStatus, id, receiptId, buyerId, creator) {
	var commonHtml = '<a class="orderInfor"  href="javascript:;">查看</a>' + '<a class="cancel"  href="javascript:;" data-id=' + id + ' data-status=' + orderStatus + ' data-receiptId=' + receiptId + '>取消</a>';

	var confirm = '<a class="confirm" href="javascript:;" data-id=' + id + '>确认</a>'; //确认
	var payNow = '<a class="payNow" href="javascript:;" data-id=' + id + ' data-buyerId=' + buyerId + ' data-receiptId=' + receiptId + ' data-orderStatus=' + orderStatus + ' data-creator=' + creator + '>支付</a>'; //支付
	var signFor = '<a class="signFor" href="javascript:;" data-id=' + id + ' data-buyerId=' + buyerId + ' data-receiptId=' + receiptId + ' data-orderStatus=' + orderStatus + '>确认签收</a>' + '<a class="orderInfor"  href="javascript:;">查看</a>'; //确认签收


	if (orderStatus == "02") {
		//买家确认
		return new Handlebars.SafeString(confirm + commonHtml)
	} else if (orderStatus == "10") {
		// 支付
		return new Handlebars.SafeString(payNow + commonHtml)
	} else if (orderStatus == "15") {
		// 确认签收
		return new Handlebars.SafeString(signFor)
	} else if (orderStatus == "17" || orderStatus == "18") {
		// 平台转账 和 卖方确认收款 时候不能取消
		return new Handlebars.SafeString('<a class="orderInfor"  href="javascript:;">查看</a>')
	} else {
		// 返回 查看+取消 功能
		return new Handlebars.SafeString(commonHtml)
	}

})



/**
 * [普通用户-我是卖家-操作状态]
 */
// Handlebars.registerHelper("sellerAverageStatus", function (businessState, orderId,businessType) {

// 	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

// 	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

// 	var Revoke = '<p><a href="javascript:;" class="sellerAverageRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

// 	var Return = '<p><a href="javascript:;" class="sellerAverageReturn" data-id=' + orderId + ' >退回</a></p>';//退回

// 	var Change = '<p><a href="javascript:;" class="sellerAverageChange" data-id=' + orderId + '>修改</a></p>';//修改

// 	var Close = '<p><a href="javascript:;" class="sellerAverageClose" data-id=' + orderId + ' >关闭</a></p>';//关闭 

// 	var Confirm = '<p><a href="javascript:;" class="sellerAverageConfirm" data-id=' + orderId + ' >确认</a></p>';//确认 

// 	if (businessState == "0011") {
// 		if(businessType=="0200"){
// 			//待买方确认-代理商
// 			return new Handlebars.SafeString(OneLookUp) 
// 		} else{ 
// 			//待买方确认-用户
// 			return new Handlebars.SafeString(LookUp + Revoke)
// 		}
// 	} else if (businessState == "0020") {
// 		// 交易撤回
// 		if(businessType=="0100"){
// 			//交易撤回
// 			return new Handlebars.SafeString(OneLookUp)
// 		} else { 
// 			//交易撤回
// 			return new Handlebars.SafeString(LookUp + Change + Close)
// 		}
// 	} else if (businessState == "0021") {
// 		// 待卖家修改
// 		return new Handlebars.SafeString(LookUp + Change)
// 	} else if (businessState == "0012") {
// 		// 待卖家确认
// 		return new Handlebars.SafeString(LookUp + Confirm + Return)
// 	} else if (businessState == "0010") {
// 		// 待双方确认
// 		return new Handlebars.SafeString(LookUp + Confirm)
// 	} else if (businessState == "0022") {
// 		// 待卖方修改
// 		return new Handlebars.SafeString(LookUp + Change)
// 	} else {
// 		return new Handlebars.SafeString(OneLookUp)
// 	}
// })

	/**
	 * [普通用户-我是卖家-操作状态]
	 */
	Handlebars.registerHelper("sellerAverageStatus", function (data) { 

		var orderId = data.orderId;

		var sellerButtonList = data.sellerButtonList; //卖家

		var agentButtonList = data.agentButtonList;//买家

		var buyerButtonList = data.buyerButtonList; //代理商

		var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

		var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

		var recallOrder = '<p><a href="javascript:;" class="sellerAverageRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

		var returnOrder = '<p><a href="javascript:;" class="sellerAverageReturn" data-id=' + orderId + ' >退回</a></p>';//退回

		var updateOrder = '<p><a href="javascript:;" class="sellerAverageChange" data-id=' + orderId + '>修改</a></p>';//修改

		var closeOrder = '<p><a href="javascript:;" class="sellerAverageClose" data-id=' + orderId + ' >关闭订单</a></p>';//关闭订单 

		var confirmOrder = '<p><a href="javascript:;" class="sellerAverageConfirm" data-id=' + orderId + ' >确认交易</a></p>';//确认交易 

		var paymentOrder = '<p><a href="javascript:;" class="sellerAverageSignPay" data-id=' + orderId + ' >签收付款</a></p>';//签收付款 

		var reviewOrder = '<p><a href="javascript:;" class="sellerAverageReview" data-id=' + orderId + ' >复核</a></p>';//复核 

		var init = ""; 
		
		if (JSON.stringify(sellerButtonList) != "[]") {
			sellerButtonList.forEach(function(element) {
				if(element.id=='recallOrder') {//撤回
					init = init + recallOrder;
				}
				else if(element.id=='returnOrder') {//退回
					init = init + returnOrder;
				}
				else if(element.id=='updateOrder') {//修改
					init = init + updateOrder;
				}
				else if(element.id=='closeOrder') {//关闭订单 
					init = init + closeOrder;
				}
				else if(element.id=='confirmOrder') {//确认交易 
					init = init + confirmOrder;
				}
				else if(element.id=='paymentOrder') {//签收付款 
					init = init + paymentOrder;
				}
				else if(element.id=='reviewOrder') {//复核 
					init = init + reviewOrder;
				} 
			}, this); 
			return new Handlebars.SafeString(LookUp + init)
		} else {
			return new Handlebars.SafeString(OneLookUp)
		}

	})


/**
 * [普通用户-我是买家-操作状态]
 */
Handlebars.registerHelper("buyersAverageUserStatus", function (businessState, orderNo) {
	console.log(orderNo)
	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderNo + '>查看详情</a></p>';//查看详情

	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderNo + '>查看详情</a>';//单独查看详情

	var Close = '<p><a href="javascript:;" class="buyersAverageUserClose" data-id=' + orderNo + ' >关闭</a></p>';//关闭 

	if (businessState == "0060") {
		//待买家付款
		return new Handlebars.SafeString(LookUp + Close)
	} else {
		return new Handlebars.SafeString(OneLookUp)
	}
})
/**
 * [普通用户-我是买家-操作状态]
 */
	// 	Handlebars.registerHelper("buyersAverageUserStatus", function (data) { 

	// 	var orderId = data.orderId;

	// 	var sellerButtonList = data.sellerButtonList; //卖家

	// 	var agentButtonList = data.agentButtonList;//买家

	// 	var buyerButtonList = data.buyerButtonList; //代理商

	// 	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

	// 	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

	// 	var recallOrder = '<p><a href="javascript:;" class="buyersAverageRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

	// 	var returnOrder = '<p><a href="javascript:;" class="buyersAverageReturn" data-id=' + orderId + ' >退回</a></p>';//退回

	// 	var updateOrder = '<p><a href="javascript:;" class="buyersAverageChange" data-id=' + orderId + '>修改</a></p>';//修改

	// 	var closeOrder = '<p><a href="javascript:;" class="buyersAverageClose" data-id=' + orderId + ' >关闭订单</a></p>';//关闭订单 

	// 	var confirmOrder = '<p><a href="javascript:;" class="buyersAverageConfirm" data-id=' + orderId + ' >确认交易</a></p>';//确认交易 

	// 	var paymentOrder = '<p><a href="javascript:;" class="buyersAverageSignPay" data-id=' + orderId + ' >签收付款</a></p>';//签收付款 

	// 	var reviewOrder = '<p><a href="javascript:;" class="buyersAverageReview" data-id=' + orderId + ' >复核</a></p>';//复核 

	// 	var init = ""; 
		
	// 	if (JSON.stringify(buyerButtonList) != "[]") {
	// 		buyerButtonList.forEach(function(element) {
	// 			if(element.id=='recallOrder') {//撤回
	// 				init = init + recallOrder;
	// 			}
	// 			else if(element.id=='returnOrder') {//退回
	// 				init = init + returnOrder;
	// 			}
	// 			else if(element.id=='updateOrder') {//修改
	// 				init = init + updateOrder;
	// 			}
	// 			else if(element.id=='closeOrder') {//关闭订单 
	// 				init = init + closeOrder;
	// 			}
	// 			else if(element.id=='confirmOrder') {//确认交易 
	// 				init = init + confirmOrder;
	// 			}
	// 			else if(element.id=='paymentOrder') {//签收付款 
	// 				init = init + paymentOrder;
	// 			}
	// 			else if(element.id=='reviewOrder') {//复核 
	// 				init = init + reviewOrder;
	// 			} 
	// 		}, this); 
	// 		return new Handlebars.SafeString(LookUp + init)
	// 	} else {
	// 		return new Handlebars.SafeString(OneLookUp)
	// 	}

	// })
/**
 * [会员用户-我是买家-操作状态]
 */
// Handlebars.registerHelper("buyersMemberStatus", function (businessState, orderId, businessType) {

// 	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

// 	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

// 	var Confirm = '<p><a href="javascript:;" class="buyersMemberConfirm" data-id=' + orderId + ' >确认</a></p>';//确认

// 	var Return = '<p><a href="javascript:;" class="buyersMemberReturn" data-id=' + orderId + ' >退回</a></p>';//退回

// 	var Revoke = '<p><a href="javascript:;" class="buyersMemberRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

// 	var Change = '<p><a href="javascript:;" class="buyersMemberChange" data-id=' + orderId + '>修改</a></p>';//修改

// 	var Close = '<p><a href="javascript:;" class="buyersMemberClose" data-id=' + orderId + ' >关闭</a></p>';//关闭 

// 	var SignPay = '<p><a href="javascript:;" class="buyersMemberSignPay" data-id=' + orderId + ' >签收付款</a></p>';//签收付款 

// 	var Review = '<p><a href="javascript:;" class="buyersMemberReview" data-id=' + orderId + ' >复核</a></p>';//复核 

// 	if (businessState == "0011") {
// 		//待买方确认
// 		if (businessType == "0200") {
// 			//待卖方确认-代理商
// 			return new Handlebars.SafeString(LookUp + Confirm)
// 		} else {
// 			//待卖方确认-用户 
// 			return new Handlebars.SafeString(LookUp + Confirm + Return)
// 		}
// 	} else if (businessState == "0012") {
// 		if (businessType == "0200") {
// 			//待卖方确认-代理商
// 			return new Handlebars.SafeString(OneLookUp)
// 		} else {
// 			//待卖方确认-用户
// 			return new Handlebars.SafeString(LookUp + Revoke)
// 		}
// 	} else if (businessState == "0040") {
// 		// 待签收支付
// 		return new Handlebars.SafeString(LookUp + SignPay)
// 	} else if (businessState == "0041") {
// 		// 待支付复核
// 		return new Handlebars.SafeString(LookUp + Review)
// 	} else if (businessState == "0020") {
// 		// 交易撤回
// 		if (businessType == "0102") {
// 			//交易撤回- 普通用户发起卖票交易
// 			return new Handlebars.SafeString(LookUp + Close)
// 		} else {
// 			//交易撤回
// 			return new Handlebars.SafeString(LookUp + Change + Close)
// 		}
// 	} else if (businessState == "0010") {
// 		// 待双方确认
// 		return new Handlebars.SafeString(LookUp + Confirm)
// 	} else {
// 		return new Handlebars.SafeString(OneLookUp)
// 	}

// })

/**
 * [会员用户-我是买家-操作状态]
 */
	Handlebars.registerHelper("buyersMemberStatus", function (data) { 

		var orderId = data.orderId;

		var sellerButtonList = data.sellerButtonList; //卖家

		var agentButtonList = data.agentButtonList;//买家

		var buyerButtonList = data.buyerButtonList; //代理商

		var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

		var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

		var recallOrder = '<p><a href="javascript:;" class="buyersMemberRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

		var returnOrder = '<p><a href="javascript:;" class="buyersMemberReturn" data-id=' + orderId + ' >退回</a></p>';//退回

		var updateOrder = '<p><a href="javascript:;" class="buyersMemberChange" data-id=' + orderId + '>修改</a></p>';//修改

		var closeOrder = '<p><a href="javascript:;" class="buyersMemberClose" data-id=' + orderId + ' >关闭订单</a></p>';//关闭订单 

		var confirmOrder = '<p><a href="javascript:;" class="buyersMemberConfirm" data-id=' + orderId + ' >确认交易</a></p>';//确认交易 

		var paymentOrder = '<p><a href="javascript:;" class="buyersMemberSignPay" data-id=' + orderId + ' >签收付款</a></p>';//签收付款 

		var reviewOrder = '<p><a href="javascript:;" class="buyersMemberReview" data-id=' + orderId + ' >复核</a></p>';//复核 

		var init = "";

        var role = $('.role').val(); console.log(role)
		if (JSON.stringify(buyerButtonList) != "[]") {
			buyerButtonList.forEach(function(element) {
				if(element.id=='recallOrder') {//撤回
					init = init + recallOrder;
				}
				else if(element.id=='returnOrder') {//退回
					init = init + returnOrder;
				}
				else if(element.id=='updateOrder') {//修改
					init = init + updateOrder;
				}
				else if(element.id=='closeOrder') {//关闭订单 
					init = init + closeOrder;
				}
             	else if(role&&role.indexOf('10')>-1&&element.id=='confirmOrder') {//确认交易
					init = init + confirmOrder;
				}
				else if(element.id=='paymentOrder') {//签收付款 
					init = init + paymentOrder;
				}
				else if(role&&role.indexOf('20')>-1&&element.id=='reviewOrder') {//复核 
					init = init + reviewOrder;
				}
			}, this); 
			return new Handlebars.SafeString(LookUp + init)
		} else {
			return new Handlebars.SafeString(OneLookUp)
		}

	})
/**
 * [会员用户-我是卖家-操作状态]
 */
// Handlebars.registerHelper("sellerMemberUserStatus", function (businessState, orderId, businessType) {

// 	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

// 	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

// 	var Revoke = '<p><a href="javascript:;" class="sellerMemberRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

// 	var Return = '<p><a href="javascript:;" class="sellerMemberReturn" data-id=' + orderId + ' >退回</a></p>';//退回

// 	var Change = '<p><a href="javascript:;" class="sellerMemberChange" data-id=' + orderId + '>修改</a></p>';//修改

// 	var Close = '<p><a href="javascript:;" class="sellerMemberClose" data-id=' + orderId + ' >关闭</a></p>';//关闭 

// 	var Confirm = '<p><a href="javascript:;" class="sellerMemberConfirm" data-id=' + orderId + ' >确认</a></p>';//确认 

// 	if (businessState == "0011") {
// 		if (businessType == "0200") {
// 			//待买方确认-代理商
// 			return new Handlebars.SafeString(OneLookUp)
// 		} else {
// 			//待买方确认-用户
// 			return new Handlebars.SafeString(LookUp + Revoke)
// 		}
// 	} else if (businessState == "0020") {
// 		// 交易撤回
// 		return new Handlebars.SafeString(LookUp + Change + Close)
// 	} else if (businessState == "0022") {
// 		// 待卖家修改
// 		return new Handlebars.SafeString(LookUp + Change)
// 	} else if (businessState == "0012") {
// 		// 待卖家确认
// 		return new Handlebars.SafeString(LookUp + Confirm)
// 	} else if (businessState == "0010") {
// 		// 待双方确认
// 		return new Handlebars.SafeString(LookUp + Confirm)
// 	} else {
// 		return new Handlebars.SafeString(OneLookUp)
// 	}
// })
/**
 * [会员用户-我是卖家-操作状态]
 */
Handlebars.registerHelper("sellerMemberStatus", function (data) { 
		
		var orderId = data.orderId;

		var sellerButtonList = data.sellerButtonList; //卖家

		var agentButtonList = data.agentButtonList;//买家

		var buyerButtonList = data.buyerButtonList; //代理商

		var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderId + '>查看详情</a></p>';//查看详情

		var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderId + '>查看详情</a>';//单独查看详情

		var recallOrder = '<p><a href="javascript:;" class="sellerMemberRevoke" data-id=' + orderId + ' >撤回</a></p>';//撤回

		var returnOrder = '<p><a href="javascript:;" class="sellerMemberReturn" data-id=' + orderId + ' >退回</a></p>';//退回

		var updateOrder = '<p><a href="javascript:;" class="sellerMemberChange" data-id=' + orderId + '>修改</a></p>';//修改

		var closeOrder = '<p><a href="javascript:;" class="sellerMemberClose" data-id=' + orderId + ' >关闭订单</a></p>';//关闭订单 

		var confirmOrder = '<p><a href="javascript:;" class="sellerMemberConfirm" data-id=' + orderId + ' >确认交易</a></p>';//确认交易 

		var paymentOrder = '<p><a href="javascript:;" class="sellerMemberSignPay" data-id=' + orderId + ' >签收付款</a></p>';//签收付款 

		var reviewOrder = '<p><a href="javascript:;" class="sellerMemberReview" data-id=' + orderId + ' >复核</a></p>';//复核 

		var init = ""; 
		var role = $('.role').val(); console.log(role)
		if (JSON.stringify(sellerButtonList) != "[]") {
			sellerButtonList.forEach(function(element) {
				if(element.id=='recallOrder') {//撤回
					init = init + recallOrder;
				}
				else if(element.id=='returnOrder') {//退回
					init = init + returnOrder;
				}
				else if(element.id=='updateOrder') {//修改
					init = init + updateOrder;
				}
				else if(element.id=='closeOrder') {//关闭订单 
					init = init + closeOrder;
				}
                /**      qex 修改role 角色判断失误
                else if(element.id=='confirmOrder') {//确认交易
                    init = init + confirmOrder;
                }
                else if(element.id=='paymentOrder') {//签收付款
                    init = init + paymentOrder;
                }
                else if(element.id=='reviewOrder') {//复核
                    init = init + reviewOrder;
                }
                   qex 修改role 角色判断失误         */
				else if(role&&role.indexOf('10')>-1&&element.id=='confirmOrder') {//确认交易
					init = init + confirmOrder;
				}
				else if(element.id=='paymentOrder') {//签收付款 
					init = init + paymentOrder;
				}
				else if(role&&role.indexOf('20')>-1&&element.id=='reviewOrder') {//复核 
					init = init + reviewOrder;
				}
			}, this); 
			return new Handlebars.SafeString(LookUp + init)
		} else {
			return new Handlebars.SafeString(OneLookUp)
		}

	})

/**
 * [电商订单-买入票据操作状态]
 */
Handlebars.registerHelper("BuyingBillStatus", function (businessStateName, orderNo) { 
	
	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderNo + '>查看详情</a></p>';//查看详情

	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderNo + '>查看详情</a>';//单独查看详情

	var Recite = '<p><a href="javascript:;" class="BuyingBillRecite" data-id=' + orderNo + ' >背书</a></p>';//背书 

	var Cancel = '<p><a href="javascript:;" class="BuyingBillCancel" data-id=' + orderNo + ' >取消订单</a></p>';//取消订单 

	if (businessStateName == "待买家付款") {
		//待买家付款
		return new Handlebars.SafeString(LookUp + Cancel)
	} else {
		return new Handlebars.SafeString(OneLookUp)
	}
})

/**
 * [电商订单-卖出票据操作状态]
 */
Handlebars.registerHelper("SellingBillStatus", function (businessStateName, orderNo) {

	var LookUp = '<p><a class="lookUp lh2" href="javascript:;" data-id=' + orderNo + '>查看详情</a></p>';//查看详情

	var OneLookUp = '<a class="lookUp oneLookUp" href="javascript:;" data-id=' + orderNo + '>查看详情</a>';//单独查看详情

	var Recite = '<p><a href="javascript:;" class="SellingBillRecite" data-id=' + orderNo + ' >背书</a></p>';//背书  

	if (businessStateName == "待卖家背书") {
		//待卖家背书
		return new Handlebars.SafeString(LookUp + Recite)
	} else {
		return new Handlebars.SafeString(OneLookUp)
	}
})


/**
 * [票据-报价信息]
 */
Handlebars.registerHelper("priceStatus", function (quotationMethod, yearRate, discount) {

	var yearRate = Number(yearRate * 100).toFixed(4);

	if (quotationMethod == "10") {
		// 年利率
		return new Handlebars.SafeString('<div class="tabsquotation"><p>' + yearRate + '%</p><p>年利率</p></div>')
	} else if (quotationMethod == "20") {
		// 每十万贴现
		return new Handlebars.SafeString('<div class="tabsquotation"><p>' + discount + '元</p><p>每十万贴现</p></div>')
	} else {
		return new Handlebars.SafeString('')
	}
})

/**
 * [票据-报价信息]
 */
Handlebars.registerHelper("priceStatusDeal", function (quotationMethod, yearRate, discount) {

	var yearRate = Number(yearRate * 100).toFixed(4);
	
	if (quotationMethod == "10") {
		// 年利率
		return new Handlebars.SafeString('<p class="linehtDeal">年利率:' + yearRate * 100 + '%</p>')
	} else if (quotationMethod == "20") {
		// 每十万贴现
		return new Handlebars.SafeString('<p class="linehtDeal">每十万贴现:' + discount + '元</p>')
	} else {
		return new Handlebars.SafeString('')
	}
})


/**
 * [票据-类型]
 */
var businessTypeList = {
	"0100": "直接交易",//会员发起收票交易
	"0101": "直接交易",//会员发起卖票交易
	"0102": "直接交易",//普通用户发起卖票交易
	"0200": "撮合交易"//代理商发起撮合交易
}
Handlebars.registerHelper("businessTypeStatus", function (businessType) {

	return businessTypeList[businessType]
})


/**
 * [票据类型]
 */
var billsTypeList = {
	"11": "商票",
	"10": "银票"
}
Handlebars.registerHelper("billsTypeStatus", function (billsType) {

	return billsTypeList[billsType]
})
/**
 * [票据标题类型]
 *
 */
  Handlebars.registerHelper("billsTitleStatus", function(type){
  	if(type == "商票"){
  		return "电子商业承兑汇票"
	}else if(type == "银票"){
  		return "电子银行承兑汇票"
	}
  })

/**
 * [票据监管状态]
 */
  Handlebars.registerHelper("BillSupervisionStatus" ,function(type){
  	if(type == "商票" || type == "银票" ){
        return "监管中"
	}

  })
/**
 * [票据属性]
 */
var billsAttributeList = {
	"10": "国股",
	"11": "城商",
	"12": "农商",
	"13": "村镇",
	"14": "外资",
	"15": "财务公司",
	"16": "其他"
}
Handlebars.registerHelper("billsAttributeStatus", function (billsAttribute) {

	return billsAttributeList[billsAttribute]
})

/**
 * [订单状态]
 */
var orderStatusList = {
	"0042": "待卖家复核",
	"0001": "交易关闭",
	"9999": "交易失败",
	"0041": "待支付复核",
	"0040": "待签收支付",
	"0030": "票据待匹配",
	"0022": "待卖方修改",
	"0021": "待买方修改",
	"0000": "交易成功",
	"0050": "待卖家背书",
	"0010": "待双方确认",
	"0011": "待买方确认",
	"0020": "交易撤回",
	"0012": "待卖方确认",
	"0060": "待买家付款",
    "0080": "等待交易结果通知"
}
Handlebars.registerHelper("businessStateType", function (businessState) {
	return orderStatusList[businessState]
})

Handlebars.registerHelper("tBackState", function (backState) {
    if(backState&&backState!=""){
		return new Handlebars.SafeString('<div class="box">回头背书</div>');
	}else{
    	return "";
	}
})


/**
 * [卖家-我的票源状态]
 * orderStatus==null or  orderStatus== "24"可以发起交易
 */
Handlebars.registerHelper("myTikiteSellerStatus", function (orderStatus, receiptNo, receiptStatus) {
	if (orderStatus == null || orderStatus == "24") {
		return new Handlebars.SafeString('<a class="startTrade" data-tikiteNo="' + receiptNo + '" data-receiptStatus="' + receiptStatus + '">发起交割</a>')
	} else {
		return new Handlebars.SafeString('<a class="checkStartTrade" data-tikiteNo="' + receiptNo + '">查看</a>')
	}

})

/**
 * 根据areaCode 返回省市
 */
Handlebars.registerHelper("showBankArea", function (areaCode) {
	// console.log(areaCode)
	var selectRegion = function (regionCode) {
		var regionCode = regionCode || "110101"; //如果不传regionCode,默认显示北京
		var _data = CHINA_REGION;

		var selectProvinceCode = regionCode.substr(0, 2) + "0000"; //选择的省ID
		var selectCitysCode = regionCode.substr(0, 4) + "00"; //选择的市ID
		var selectAreasCode = regionCode; //选择的地区ID

		var province = _filter("0"); //省列表
		var citys = _filter(selectProvinceCode); //城市列表
		var areas = _filter(selectCitysCode); //地区列表

		var renturnProvinceName = selectProvince(selectProvinceCode); //返回的省的名字
		var renturnCityeName = selectCitys(selectCitysCode); //返回的省的名字

		/**
		 * 根据pid查询子节点
		 */
		function _filter(pid) {
			var result = [];

			for (var code in _data) {
				if (_data.hasOwnProperty(code) && _data[code][1] === pid) {
					result.push([code, _data[code][0]]);
				}
			}
			return result;
		}

		// 选择省
		function selectProvince(selectProvinceCode) {
			var provinceStr = ""
			for (var i = 0; i < province.length; i++) {
				var provinceName = province[i][1];
				var provinceCode = province[i][0];
				if (provinceCode == selectProvinceCode) {
					provinceStr = provinceName;
					break;
					// provinceStr += '<option value="' + provinceCode + '" selected = "selected">' + provinceName + '</option>'
				}

			}
			return provinceStr;
			// $("#province").html(provinceStr)
		}

		// 选着市
		function selectCitys(provinceCode) {
			var citysStr = ""
			// citys = _filter(provinceCode);
			for (var i = 0; i < citys.length; i++) {
				var citysName = citys[i][1];
				var citysCode = citys[i][0];

				if (citysCode == provinceCode) {
					citysStr = citysName;
					break;
					// citysStr += '<option value="' + citysCode + '" selected = "selected">' + citysName + '</option>'
				}
			}
			// console.log(citysStr)
			return citysStr;
		}

		return renturnProvinceName + "-" + renturnCityeName
	}

	return selectRegion(areaCode)

})


/**
 * 票据类型
 */
Handlebars.registerHelper("billType", function (type) {
	if (type == "银票") {
		return new Handlebars.SafeString('<img class="list-img" src="../../../resources/img/billlist/yinpiao.png">')
	} else if (type == "商票") {
		return new Handlebars.SafeString('<img class="list-img" src="../../../resources/img/billlist/shangpiao.png">')
	}

})

/**
 * 票据类型图标
 */
Handlebars.registerHelper("billTypeIco", function (type) {
	return new Handlebars.SafeString('<i class="billTypeIco">' + type + '</i>')
})

//票面金额
Handlebars.registerHelper("moneyFormat", function (faceAmount) {
	if (faceAmount > 0) {
		return Number((parseFloat(faceAmount) / 10000).toFixed(6));
	} else {
		return "0"
	}
})

//票面金额
Handlebars.registerHelper("billMoneyFormat", function (faceAmount) {
		var ret = "";
	    var str = "              "+String((((parseFloat(faceAmount)).toFixed(2))*100).toFixed(0));
		str =  str.substr(str.length-12);
		var last = str.lastIndexOf(" ");
    	var strs = (str).split("");
    	strs[last] = '￥';
    	for(var i=0;i<strs.length;i++){
            ret = ret +'<td>';
			ret = ret + strs[i];
            ret = ret +'</td>';
        }
        return  new Handlebars.SafeString(ret);
			//
});
//金额保留小数
Handlebars.registerHelper("moneyFor", function (faceAmount) {
	if (faceAmount > 0) {
		return parseFloat(faceAmount).toFixed(2);
	} else {
		return "0"
	}
})

/**
 * 报价
 */
Handlebars.registerHelper("offer", function (isDiscussPersonally, quotationMethod, yearRate, discount) {
	if (isDiscussPersonally == "10") {
		return "面议"
	} else {
		if (quotationMethod == "10") {
			return new Handlebars.SafeString('<span class="top">' + Number((parseFloat(yearRate)*100).toFixed(4)) + '%</span><span class="btm">年利率</span>')
		} else if (quotationMethod == "20") {
			return new Handlebars.SafeString('<span class="top">' + parseFloat(discount).toFixed(2) + '元</span><span class="btm">每十万</span>')
		}
	}

})

/**
 * 订单详情报价
 */
Handlebars.registerHelper("offers", function (isDiscussPersonally, quotationMethod, yearRate, discount) {
	if (isDiscussPersonally == "10") {
		return "面议"
	} else {
		if (quotationMethod == "10") {
			return new Handlebars.SafeString('年利率' + Number((parseFloat(yearRate)*100).toFixed(4)) + '%')
		} else if (quotationMethod == "20") {
			return new Handlebars.SafeString('每十万' + parseFloat(discount).toFixed(2) + '元')
		}
	}

})

/**
 * 交易按钮
 */
Handlebars.registerHelper("listInfor", function (transactionState, billNo) {
	if (transactionState == "10") {
		return new Handlebars.SafeString('<a class="listInfor" href="/BillShop/billDetails.htm?' + billNo + '">可转让</a>')
	} else if (transactionState == "20") { 
		return new Handlebars.SafeString('<a class="listInfor2" href="/BillShop/billDetails.htm?' + billNo + '">转让中</a>') 
	} 
})

/**
 * 多选按钮是否显示
 */
Handlebars.registerHelper("labelDis", function (isDiscussPersonally, transactionState, billNo) {
	if (transactionState == "10"&&isDiscussPersonally=='20') {
		if (window.ids.indexOf(billNo) > -1) {
			return new Handlebars.SafeString('<label for="' + billNo + '" class="labelCheck active onActive"></label>')
		} else {
			return new Handlebars.SafeString('<label for="' + billNo + '" class="labelCheck active"></label>')
		}
	} else if (transactionState == "20") {
		return new Handlebars.SafeString('<label for="" class="labelCheck"></label>')
	}

})

/**
 * 详情报价
 */
Handlebars.registerHelper("offerDetail", function (isDiscussPersonally, quotationMethod, yearRate, discount) {
	if (isDiscussPersonally == "10") {
		return new Handlebars.SafeString('<label>面议</label><span>报价</span>')
	} else {
		if (quotationMethod == "10") {
			return new Handlebars.SafeString('<label>' + (parseFloat(yearRate)*100).toFixed(4) + '<em>%</em></label><span>报价(年利率)</span>')
		} else if (quotationMethod == "20") {
			return new Handlebars.SafeString('<label>' + parseFloat(discount).toFixed(2) + '<em>元</em></label><span>报价(每十万)</span>')
		}
	}

})

/**
 * 详情持票方企业名称
 */
Handlebars.registerHelper("subString", function (enterpriseName) {
	if(typeof (enterpriseName)!= 'undefined'&&enterpriseName) {
        var nameLen = enterpriseName.length
        if (nameLen < 19) {
            return new Handlebars.SafeString('<span>' + enterpriseName + '</span>')
        } else {
            return new Handlebars.SafeString('<span title="' + enterpriseName + '">' + enterpriseName.substring(0, 17) + '...</span>')
        }
    }else{
		return "";
    }
})

/**
 * 详情产品状态
 */
Handlebars.registerHelper("stateBtn", function (isDiscussPersonally, transactionState, officePhone) {
	if(isDiscussPersonally=='10'){
		return new Handlebars.SafeString('<a class="enter grey">请询价</a><span>联系方式：' + officePhone + '</span>')
	}
	if (transactionState == 10) {
		return new Handlebars.SafeString('<a class="enter" id="Order-an">立即购买</a><span>联系方式：' + officePhone + '</span>')
	} else if (transactionState == 20) {
		return new Handlebars.SafeString(' <a class="enter" href="/BillShop/billList.htm">查看其它产品</a><span>该产品转让中</span>')
	}

})
/**
 * 我的票据-票据状态-lf
 */
Handlebars.registerHelper("transferStateType", function (billAttribute) {
	if (billAttribute == "10") {
		return "可转让"
	} else if (billAttribute == "20") {
		return "转让中"
	}
})

Handlebars.registerHelper("smalltoBig", function (n) {
    var fraction = ['角', '分'];
    var digit = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];
    var unit = [
        ['元', '万', '亿'],
        ['', '拾', '佰', '仟']
    ];
    var head = n < 0 ? '欠' : '';
    n = Math.abs(n);

    var s = '';

    for (var i = 0; i < fraction.length; i++) {
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');
    }
    s = s || '整';
    n = Math.floor(n);

    for (var i = 0; i < unit[0].length && n > 0; i++) {
        var p = '';
        for (var j = 0; j < unit[1].length && n > 0; j++) {
            p = digit[n % 10] + unit[1][j] + p;
            n = Math.floor(n / 10);
        }
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;
    }
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
})
	/**/
Handlebars.registerHelper("tIsCanTransfer", function (transfer) {
    if (transfer == "10" || transfer == "Y") {
        return "可转让"
    } else{
        return "不可转让"
    }
})

/*票据正面：转让状态恒为可转让*/
Handlebars.registerHelper("billFrontTransferState", function (transfer) {
    //if (transfer == "10" || transfer == "20") {
        return "可转让"
	//}
})

/**/
Handlebars.registerHelper("tEndorseType", function (transfer) {
    if (transfer == "E") {
        return "转让背书"
    } else if (transfer == "Q") {
        return "保证背书"
    } else if (transfer == "O") {
        return "质押背书"
    }  else if (transfer == "P") {
        return "解质押背书"
    } else{
        return "未知"
    }
})

/**
 * 我的票据-票据属性-lf
 */
Handlebars.registerHelper("billAttributeType", function (billAttribute) {
	if (billAttribute == "10") {
		return "国股"
	} else if (billAttribute == "20") {
		return "城商"
	} else {
		return "未知"
	}

})
/**
 * 我的票据-票据来源-lf
 */
Handlebars.registerHelper("sourceType", function (source) {
	if (source == "01") {
		return "会员收票"
	} else if (source == "02") {
		return "三方推送"
	} else {
		return "未知"
	}

})

/**
 * 纯票订单-复核按钮显示控制-lf
 */
Handlebars.registerHelper("lfbillReview", function (billReview, id) {
	var role = $('.role').val(); console.log(role)
	if (role && role.indexOf('20') > -1 && billReview == "0042") {
		return new Handlebars.SafeString('<p class="pTabs6"><a class="billReview" href="javascript:;" data-id=' + id + '>复核</a></p>')
	} else {
		return new Handlebars.SafeString('<div class="hideListDiv"></div>')
	}
})
/**
 * 银行总行转换
 */
Handlebars.registerHelper("bankNameType", function (bankCode) {
	var bankName = '未匹配到银行';
	$.each(window.bankData, function (index, item) {
		if (item.bankCode == bankCode) {
			bankName = item.bankName;
		}
	});
	return bankName

})
/**
 * 银行卡类型
 */
Handlebars.registerHelper("typeType", function (type) {
	if (type == "10") {
		return "type1"
	} else {
		return "type2"
	}
})

/**
 * 安全中心-银行卡显示控制-lf
 */
Handlebars.registerHelper("lfTypeBtn", function (data) {
	if (data.type == "10") {
		return new Handlebars.SafeString("<p class='operation'><span class='modify' dataJson=" + JSON.stringify(data) + ">修改</span><span class='delete' data-id=" + data.id + ">删除</span></p>")
	} else {
		return new Handlebars.SafeString('')
	}
})

/**
 * 元转换万元-lf
 */
Handlebars.registerHelper("yuanConversion", function (value) {
	if (value) {
		return Number((value / 10000).toFixed(6))
	} else {
		return '0'
	}
})

Handlebars.registerHelper("shelvesTimeType", function (value) {
	if (value) {
		return new Handlebars.SafeString("<p class='fristP'>" + value.split(' ')[0] + "</p><p>" + value.split(' ')[1] + "</p>")
	} else {
		return '0'
	}
})

Handlebars.registerHelper("billNoType", function (value) {
	if (value && value.length < 16) {
		return new Handlebars.SafeString("<div>" + value + "</div>")
	}
	else if (value && value.length > 15) {
		/*value = '123456789a123456789b123456789c'*/
		return new Handlebars.SafeString("<p class='fristP'>" + value.substring(0, 15) + "</p><p>" + value.substring(15) + "</p>")
	} else {
		return '0'
	}
})


/**
 * 我的票据-持有中按钮显示控制-lf
 */
Handlebars.registerHelper("lfDueDateBtn", function (dueDate,billNo) {//限制超过到期日，无法上架和纯票交易
	var date1=new Date(dueDate) //要对比的时间
	var date2=new Date();		//获取当前时间对象
	if (date2.getTime()>date1.getTime()) {
		return new Handlebars.SafeString(' ')
	} else {
		return new Handlebars.SafeString('<a class="theShelves" href="javascript:;" data-id='+billNo+'>上架</a><a class="unregulatedTransaction" href="javascript:;" data-id='+billNo+'>纯票交易</a>')
	}
})

/**
 * 我的票据-持有中多选按钮显示控制-lf
 */
Handlebars.registerHelper("lfChecksBtn", function (dueDate,billNo) {//限制超过到期日，无法上架和纯票交易
	var aClass='';
	var bClass='';
	if (window.ids.indexOf(billNo) > -1) {
		aClass= 'checked';
		bClass= 'onActive'
	} else {
		aClass= 'false';
		bClass='';
	}
	var date1=new Date(dueDate) //要对比的时间
	var date2=new Date();		//获取当前时间对象
	if (date2.getTime()>date1.getTime()) {
		return new Handlebars.SafeString('<div class="hideLabelCheck"></div>')
	} else {
		return new Handlebars.SafeString('<input type="checkbox" class="hideCheck" checked='+aClass+' id='+billNo+'/><label for='+billNo+' class="labelCheck '+bClass+'"></label>')
	}
})

/**
 * 我的票据-已上架按钮显示控制-lf
 */
Handlebars.registerHelper("lfTransferStateBtn", function (transferState,billNo) {//限制已转让的，不可下架和修改；
	if (transferState=='20') {
		return new Handlebars.SafeString(' ')
	} else {
		return new Handlebars.SafeString('<a class="offTheShelf" href="javascript:;" data-id='+billNo+'>下架</a><a class="modify" href="javascript:;" data-id='+billNo+'>修改</a>')
	}
})

