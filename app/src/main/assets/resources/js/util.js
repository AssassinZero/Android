// 公共方法
define(function(require, exports, module) {

	// 合并对象 合并多个对象
	// 使用：_extends({},obj1,obj2)
	util._extends = Object.assign || function(target) {
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

	util.conss=function(){
		console.log("0000")
	}


	// 暴露对应接口
	module.exports = util;

})