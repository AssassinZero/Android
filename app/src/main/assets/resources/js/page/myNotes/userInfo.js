define(function(require, exports, module) {
	require('/resources/js/jquery');
	var comfn = require('/resources/js/commonFn.js'); //调用公共文件
	require("/resources/js/module/chinaRegion.js"); //加载地区json
	// console.log(CHINA_REGION)

	// 表单转化成json
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
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

	//表单验证及提交
	require.async('/resources/js/module/jquery-validation-1.13.0/jquery.validate.min', function() {
		require.async(['/resources/js/module/jquery-validation-1.13.0/additional-methods', '/resources/js/module/jquery.form'], function() {
			$("#userInfoForm").validate({
				rules: {
					name: {
						required: true,
						chnName: true,
					},
					mobile: {
						isMobile: true
					},
					email: {
						regexEmail: true
					}
				},
				messages: {
					name: {
						required: "姓名必填",
						chnName: "只能输入中文"
					},
					mobile: {
						isMobile: "手机号码格式不正确"
					},
					email: {
						regexEmail: "邮箱格式不正确"
					}
				},
				// 错误时
				errorPlacement: function(error, element) {
					// $("#errorText span").html(error.html())
					element.parents("li").find(".errorText").html(error.html());
				},
				// 成功时
				success: function() {},
				submitHandler: function(form) {
					// console.log("form")
					// console.log(form);
					// return;
					$(form).ajaxSubmit({
						type: "post",
						dataType: 'json',
						success: function(data) {
							if (data.code == 200) {
								layer.msg("保存成功", {
									"time": 500
								});
							} else {
								layer.msg(data.msg, {
									"time": 1000
								});
							}
						}
					})
				}
			})
		})
	});

	/**
	 * [添加银行账户 --- begin]
	 */
	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
			require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
				require.async(['/resources/js/module/layer/skin/default/layer.css', '/resources/js/module/layer/layer.min'], function() {


					// 加载银行数据
					function getBanKList(checkBank) {
						var checkBank = checkBank || "";
						$.ajax({
							type: "POST",
							url: "/modules/common/action/ComboAction/queryDic.htm?typeCode=BANK",
							dataType: "json",
							success: function(data) {
								for (var i = 0; i < data.data.length; i++) {
									// 设置选中
									$("#bankListSelect").append("<option value=" + data.data[i].value + ">" + data.data[i].value + "</option>");

									if (data.data[i].value == checkBank) {
										$("#bankListSelect").append("<option  selected = 'selected' value=" + data.data[i].value + ">" + data.data[i].value + "</option>");
									} else {
										$("#bankListSelect").append("<option value=" + data.data[i].value + ">" + data.data[i].value + "</option>");
									}

								}
							}
						});
					}

					// 添加/编辑 银行数据
					function addBank(layerIndex, bankdata, isEdit) {
						// console.log(bankdata);
						//提交的表单校验
						var bankCard = bankdata.bankCard;
						if (comfn.trimBank(bankCard) == "") {
							layer.msg("银行账户不能为空", {
								"time": 500
							});
							return;
						} else if (!comfn.Reg.isNumber(bankCard) || comfn.trimBank(bankCard).length < 10) {
							layer.msg("银行账户格式错误", {
								"time": 500
							});
							return;
						}
						// bankdata.areaCode = bankdata.province.substring(0, 2) + bankdata.city.substring(0, 2) + "01";
						// bankdata.province = $("#province").find("option:selected").text();
						// bankdata.city = $("#city").find("option:selected").text();
						bankdata.areaCode = bankdata.area
							// console.log(bankdata.areaCode)


						if (isEdit) {
							// 编辑银行
							$.ajax({
								url: "/modules/account/PjsBankcardAction/update.htm",
								type: "get",
								data: bankdata,
								dataType: "json",
								success: function(data) {
									getBankList();
									layer.close(layerIndex); //关闭弹窗
									if (data.code == 200) {
										layer.msg("设置成功", {
											"time": 500
										});
										// getRuleList();
									} else {
										layer.msg(data.msg, {
											"time": 1000
										});
									}
								}
							})
						} else {
							// 添加银行
							$.ajax({
								url: "/modules/account/PjsBankcardAction/save.htm",
								type: "get",
								data: bankdata,
								dataType: "json",
								success: function(data) {
									getBankList()
									layer.close(layerIndex); //关闭弹窗
									if (data.code == 200) {
										layer.msg("设置成功", {
											"time": 500
										});
									} else {
										layer.msg(data.msg, {
											"time": 1000
										});
									}
								}
							})
						}

					}

					// 查看银行数据
					function getBankList() {
						$.ajax({
							url: "/modules/account/PjsBankcardAction/getBankCardList.htm",
							type: "get",
							dataType: "json",
							success: function(data) {
								var tpl = "";
								if (glovalIsbuyerManage) {
									tpl = require('/resources/tpls/bankListLook.tpl'); //载入tpl模板-仅仅查看
								} else {
									tpl = require('/resources/tpls/bankList.tpl'); //载入tpl模板
								}
								var template = Handlebars.compile(tpl);
								var html = template(data);
								$("#bankList").html(html);

								/**
								 * [删除银行]
								 */
								$(".delBank").click(function() {
									var bankId = $(this).attr("dataId");
									$.ajax({
										url: "/modules/account/PjsBankcardAction/deleteById.htm",
										type: "get",
										data: {
											id: bankId
										},
										dataType: "json",
										success: function(data) {
											getBankList();
											if (data.code == 200) {
												layer.msg("删除成功", {
													"time": 500
												});
											} else {
												layer.msg(data.msg, {
													"time": 1000
												});
											}
										}
									})
								})

								/**
								 * [设置默认]
								 */
								$(".setDefault").click(function() {
									var bankId = $(this).attr("dataId");
									$.ajax({
										url: "/modules/account/PjsBankcardAction/setDefault.htm",
										type: "get",
										data: {
											id: bankId
										},
										dataType: "json",
										success: function(data) {
											getBankList();
											if (data.code == 200) {
												layer.msg("设置成功", {
													"time": 500
												});
											} else {
												layer.msg(data.msg, {
													"time": 1000
												});
											}
										}
									})
								})

								/**
								 * [编辑银行-按钮]
								 */
								$(".editBank").click(function() {
									var oldBankData = {
											id: $(this).attr("dataId"),
											bankArea: $(this).attr("data-bank"),
											accountName: $(this).attr("data-accountName"),
											bankCard: $(this).attr("data-code"),
											areaCode: $(this).attr("data-areaCode"),
											// areaCode: "131101"
										}
										// console.log(oldBankData)
									layer.open({
										type: 1, //弹窗类型
										title: '编辑银行', //标题
										area: ['550px', '400px'], //大小
										skin: 'layui-layer-rim', //样式类名
										closeBtn: 1, //0不显示关闭按钮,1显示
										btn: ['提交', '关闭'], //按钮
										success: function(layer) {
											// var selectedProvince = "",
											// 	selectedCity = "";
											// if (oldBankData.areaCode != "") {
											// 	selectedProvince = oldBankData.areaCode.substr(0, 2) + "0000";
											// 	selectedCity = oldBankData.areaCode.substr(0, 4) + "00";

											// 	// console.log(selectedProvince, selectedCity)
											// }
											console.log(oldBankData.areaCode)
											selectRegion(oldBankData.areaCode)

											getBanKList(oldBankData.bankArea);
											$("#bankCard").val(oldBankData.bankCard);
											$("#bankAccountName").val(oldBankData.accountName)
										},
										yes: function(layerIndex) {
											console.log($("#area").val())
											var bankdata = $("#addBankForm").serializeObject();
											bankdata = comfn._extends({}, oldBankData, bankdata)
											addBank(layerIndex, bankdata, true);
										},
										anim: 2, //动画
										//shadeClose: true, //开启遮罩关闭
										content: $('.addBank') //dom元素
									})
								});
							}
						})
					}
					getBankList();

					/**
					 * 省市联动
					 */
					function selectRegion(regionCode) {
						// console.log(selectedProvince, selectedCity)
						var regionCode = regionCode || "110101"; //如果不传regionCode,默认显示北京
						var _data = CHINA_REGION;
						// console.log(CHINA_REGION)
						// var province = !!selectedProvince ? _filter(selectedProvince) : _filter('0');
						// var citys = !!selectedCity ? _filter(selectedCity) : _filter(province[0][0]);
						// var areas = [];
						var selectProvinceCode = regionCode.substr(0, 2) + "0000"; //选择的省ID
						var selectCitysCode = regionCode.substr(0, 4) + "00"; //选择的市ID
						var selectAreasCode = regionCode; //选择的地区ID

						var province = _filter("0"); //省列表
						var citys = _filter(selectProvinceCode); //城市列表
						var areas = _filter(selectCitysCode); //地区列表

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

						// console.log(selectProvinceCode)
						function selectProvince(selectProvinceCode) {
							var provinceStr = ""
							for (var i = 0; i < province.length; i++) {
								var provinceName = province[i][1];
								var provinceCode = province[i][0];
								if (provinceCode == selectProvinceCode) {
									provinceStr += '<option value="' + provinceCode + '" selected = "selected">' + provinceName + '</option>'
								} else {
									provinceStr += '<option value="' + provinceCode + '">' + provinceName + '</option>'
								}

							}
							$("#province").html(provinceStr)
						}

						function selectCitys(provinceCode) {
							var citysStr = ""
							citys = _filter( provinceCode.substr(0, 2) + "0000");
							provinceCode = provinceCode.substr(0, 4) + "01"; //选择的市ID

							for (var i = 0; i < citys.length; i++) {
								var citysName = citys[i][1];
								var citysCode = citys[i][0];
								// console.log(provinceName)
								// citysStr += '<option value="' + citysCode + '">' + citysName + '</option>'
								// $("#city").html(citysStr)
								console.log(citysCode, provinceCode,citysName)
								if (citysCode == provinceCode) {
									citysStr += '<option value="' + citysCode + '" selected = "selected">' + citysName + '</option>'

								} else {
									citysStr += '<option value="' + citysCode + '">' + citysName + '</option>'
								}
								$("#city").html(citysStr)
							}
						}
						// 选着地区
						function selectArea(cityCode) {
							var areasStr = ""
								// areas = _filter(cityCode);
							for (var i = 0; i < areas.length; i++) {
								var areasName = areas[i][1];
								var areasCode = areas[i][0];

								if (areasCode == cityCode) {
									areasStr += '<option value="' + areasCode + '" selected = "selected">' + areasName + '</option>'
								} else {
									areasStr += '<option value="' + areasCode + '">' + areasName + '</option>'
								}
								// console.log(provinceName)
								$("#area").html(areasStr)
							}
						}

						$("#province").change(function() {
								var provinceCode = $(this).val();
								selectCitys(provinceCode)
									// console.log($(this).val())
							})
							//市级筛选
						$("#city").change(function() {
							cityCode = $(this).val();
							selectArea(cityCode)
						})

						// selectProvince();
						console.log(selectCitysCode)
						selectProvince(selectProvinceCode);
						// selectCitys(province[0][0]);
						selectCitys(selectCitysCode)
					}
					selectRegion()

					/**
					 * [添加银行-按钮]
					 */
					$("#addBankCode").click(function() {
						layer.open({
							type: 1, //弹窗类型
							title: '添加银行账户', //标题
							area: ['550px', '400px'], //大小
							skin: 'layui-layer-rim', //样式类名
							closeBtn: 1, //0不显示关闭按钮,1显示
							btn: ['提交', '关闭'], //按钮
							success: function(layer) {
								document.getElementById("addBankForm").reset()
								selectRegion()
								getBanKList();
								// dataLaydate(); //日期控件
							},
							yes: function(layerIndex) {
								console.log($("#area").val())
								var bankdata = $("#addBankForm").serializeObject();
								addBank(layerIndex, bankdata);
								// 增加操作

							},
							anim: 2, //动画
							//shadeClose: true, //开启遮罩关闭
							content: $('.addBank') //dom元素
						})
					});



				})
			})
		})
		/**
		 * [添加银行账户 --- End]
		 */

});