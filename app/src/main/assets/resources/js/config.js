;
(function() {
	var version = Date.parse(new Date());
	seajs.config({
		// 错误信息查看
		debug: false,
		// 文件映射
		map: [
			// 可配置版本号
			['.css', '.css?v=' + version],
			['.js', '.js?v=' + version],
			// ['.tpl', '.tpl?v=' + version]
		],
		// 路径配置
		// paths: {
		// 	"js": "./"
		// },
		// alias可以对较长的常用路径设置缩写。
		alias: {
			'jquery': '/resources/js/jquery'
		},
		// 使用preload配置项，可以在普通模块加载前，提前加载并初始化好指定模块。
		// 可以解决jquery插件无法调用到JQuery的问题
		preload: ["jquery"]
	});
})();