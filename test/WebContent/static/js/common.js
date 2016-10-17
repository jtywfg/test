(function(){
/* 常用函数库 */

var COMMON = COMMON || {};

/*
 * AJAX默认参数
 */
COMMON.ajaxData = function(data){
	var _data = {};
	return $.extend({},_data , data);
}
/*
 * 自定义AJAX
 */
COMMON.ajaxFunction = function(options){
	var _this = this;
	var defaults = {
		url: "",
		type: "POST",
		timeout : 1000000,
		data: { },
		dataType: "text",
		async: !0,
		beforeSend : function(){
			//_G_LAYER_LOAD =  parent.layer.load("努力加载中，请耐心等待...",0,2);
		},
		success: function(jsonData) {
			
		},
		error: function(xhr, errorType, e) {
			void 0 != _G_LAYER_LOAD && parent.layer.close(_G_LAYER_LOAD);
			switch(errorType){
				case "parsererror" :
					//alert("数据异常：请检查返回数据的格式。");
					break;
				case "timeout" :
					//alert("请求超时：请检查网络连接是否正常。");
					break;
				default :
					//alert("网络异常：请联系系统管理员。");
					break;
			}
		}
	}
	options = $.extend(defaults, options);
	//加入默认传参
	options.data = _this.ajaxData(options.data);
	//ajax执行成功回调
	var _success = options.success;
	options.success = function(jsonData){
		void 0 != _G_LAYER_LOAD && parent.layer.close(_G_LAYER_LOAD);
		jsonData = $.parseJSON(jsonData);
		if(void 0 != jsonData.session && !jsonData.session){
			$.messager.alert('提示信息','SESSION失败，请重新登录！');	
			window.top.location.href = baseUrl + '/login.html';
			return false;
			//fnCallSeesionExpires();
			//evaluationClient.sessionExpires();
		}
		$.isFunction(_success) && _success(jsonData);
	}
	$.ajax(options);
}

/* 截取字符串 
 *  @str			被截取的字符串
 *  @len		截取长度（英文1个字符，中文2个字符）
 *  @hasDot	是否要显示...
 */
COMMON.toSubString = function(str, len, hasDot)
{
	if(void 0 != str){
		var newLength = 0;
		var newStr = "";
		var chineseRegex = /[^\x00-\xff]/g;
		var singleChar = "";
		var strLength = str.replace(chineseRegex,"**").length;
		for(var i = 0;i < strLength;i++){
			singleChar = str.charAt(i).toString();
			if(singleChar.match(chineseRegex) != null){
				newLength += 2;
			}else{
				newLength++;
			}
			if(newLength > len){
				break;
			}
			newStr += singleChar;
		}
		
		if(hasDot && strLength > len){
			newStr += "...";
		}
		return newStr;
	}
}
/*
 * 得到URL的参数值
 */
COMMON.getQueryString = function(name) 
{
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
}
//时间戳转正规格式
COMMON.js_date_time = function(unixtime,formate)
{
	var now = "";
	if(!/^[0-9]+$/.test(unixtime)){
		unixtime = unixtime.replace(/-/g,'/');
		now = new Date(unixtime);
	}else{
		now = new Date(parseInt(unixtime));
	}
	var year=now.getFullYear(); 
	var month=now.getMonth()+1; 
	month = (month < 10) ? "0" + month : month;
	var date=now.getDate(); 
	date = (date < 10) ? "0" + date : date;
	var hour=now.getHours(); 
	hour = (hour < 10) ? "0" + hour : hour;
	var minute=now.getMinutes(); 
	minute = (minute < 10) ? "0" + minute : minute;
	var second=now.getSeconds(); 
	second = (second < 10) ? "0" + second : second;
	if(void 0 != formate && formate != ''){
		formate.indexOf('Y') != -1 && (formate = formate.replace("Y",year));
		formate.indexOf('M') != -1 && (formate = formate.replace("M",month));
		formate.indexOf('D') != -1 && (formate = formate.replace("D",date));
		formate.indexOf('h') != -1 && (formate = formate.replace("h",hour));
		formate.indexOf('m') != -1 && (formate = formate.replace("m",minute));
		formate.indexOf('s') != -1 && (formate = formate.replace("s",second));
	}else{
		formate = 	year+"-"+month+"-"+date+" "+hour+":"+minute;
	}
	return formate; 
}
//转义特殊字符代码
COMMON.stripscript = function (str,flag){
	var a = $("<div id='zz'></div>");
	return !flag ? a.text(str).html() : a.html(str).text();
}

window.COMMON = COMMON;

})();