//实时弹出用户提醒
define(function(require, exports, module) {

	var webHost = window.location.host; //	主机名加端口号
	require.async('/resources/js/module/handlebars-v1.3.0/handlebars-v1.3.0', function() {
		require.async('/resources/js/module/handlebars-v1.3.0/transFormatJson', function() {
			require.async(['/resources/js/module/newKkpager/kkpager.css', '/resources/js/module/newKkpager/kkpager.js'], function() {

				var echo_websocket;

				function send_echo() {
					//var wsUri = "ws://localhost:8080/springws/websocket.ws";
					//writeToScreen("Connecting to " + wsUri);
					//echo_websocket = new WebSocket(wsUri);
					if ('WebSocket' in window) {
						echo_websocket = new WebSocket("ws://" + webHost + "/springws/websocket.ws");
					} else if ('MozWebSocket' in window) {
						echo_websocket = new MozWebSocket("ws://" + webHost + "/springws/websocket.ws");
					} else {
						echo_websocket = new SockJS("http://" + webHost + "/springws/websocket");
					}

					echo_websocket.onopen = function(evt) {
						// writeToScreen("Connected !");
						doSend("sadada");
					};
					echo_websocket.onmessage = function(evt) {
						writeToScreen(evt.data);
						// echo_websocket.close();
					};
					echo_websocket.onerror = function(evt) {
						// writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
						echo_websocket.close();
					};
				}


				function doSend(message) {
					echo_websocket.send(message);
					writeToScreen("Sent message: " + message);
				}

				// 展示给买家
				function showToBuyer(messageDate) {
					// var tpl = require('/resources/tpls/xieyi.tpl'); //载入tpl模板
					// var template = Handlebars.compile(tpl);
					// // 弹窗询问是否需要购买
					// var text = template("");
					layer.confirm("票据编号" + messageDate.receiptNo + "，融资企业已签合同了", {
						btn: ['我知道了'], //按钮
						title: "交易提示",
						closeBtn: 0,
					}, function(index) {
						var tradingOrder = require("/resources/js/page/buyer/tradingOrder"); // 交易完成

						tradingOrder();
						layer.close(index);
					});
				}

				// 展示给买家
				function showToSeller(messageDate) {
					// var tpl = require('/resources/tpls/xieyi.tpl'); //载入tpl模板
					// var template = Handlebars.compile(tpl);
					// // 弹窗询问是否需要购买
					// var text = template("");
					layer.confirm("票据编号" + messageDate.receiptNo + "，做市商已付款", {
							btn: ['保存协议', '我知道了'], //按钮
							title: "交易提示",
							closeBtn: 0,
						}, function(index) {
							// 协议地址
							window.location.href = "/modules/protocol/PjsProtocolAction/downloadPdf.htm?protocolType=1&orderId=" +messageDate.orderId;
							// var sellerTradding = require("/resources/js/page/seller/sellerTradding"); // 交易中数
							// sellerTradding();
							// layer.close(index);
						},
						function(index) {
							var sellerTradding = require("/resources/js/page/seller/sellerTradding"); // 交易中数
							sellerTradding();
							layer.close(index);
						});

				}


				//返回的消息展示
				function writeToScreen(message) {
					var getDate = message.match(/\{.*[\}]*.*(\S*)\}/);
					var messageDate = null;
					if (!!getDate) {
						messageDate = JSON.parse(getDate[0])
						// showToSeller(messageDate);

						// 买家弹窗
						if (messageDate.role == "buyerMsg") {
							showToBuyer(messageDate);
						}
						// 卖家弹窗
						if (messageDate.role == "sellerMsg") {
							showToSeller(messageDate);
						}						
					}
					// console.log(messageDate)



					// var pre = document.createElement("p");
					// pre.style.wordWrap = "break-word";
					// pre.innerHTML = message;
					// $("#output").append(pre);
				}

				// function login() {
				// 	$.post("/wsstest/login", {
				// 		userName: $("#userName").val()
				// 	}, function(data) {
				// 		writeToScreen(data);
				// 	});
				// }

				// function sendTest() {
				// 	$.post("/wsstest/send.htm", {
				// 		message: "ServerMessage"
				// 	}, function(data) {
				// 		writeToScreen(data.msg);
				// 	});
				// }

				send_echo();
			})
		})
	})
})