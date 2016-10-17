/*全局变量Global*/
var _G_LAYER_WIN,_G_LAYER_LOAD;

//配置Class
var CONFIG = CONFIG || {};
CONFIG._baseUrlName = baseUrl;
CONFIG._staticBaseUrlName = CONFIG._baseUrlName + "static/";
CONFIG._staticVersion = version;
CONFIG._basePageToken = null;
CONFIG._libPath = "lib/";
CONFIG._jsPath = "js/";
CONFIG._cssPath = "css/";
CONFIG._alias = {
	"jquery" : CONFIG._libPath + "jquery/jquery-1.11.1.min",
	"jquery_cookie" : CONFIG._libPath + "jquery/cookie/jquery.cookie",
	"json" : CONFIG._libPath + "json/json2",
	"validForm" : CONFIG._libPath + "Validform/Validform_v5.3.2_min",
	"layer" : CONFIG._libPath + "layer/layer.min",
	"pagination" : CONFIG._libPath + "pagination/jquery.pagination.2.2",
	"common" : CONFIG._jsPath + "common",
	"common_data" : CONFIG._jsPath + "common_data",
	"school_data" : CONFIG._jsPath + "school_data",
	"paper_data" : CONFIG._jsPath + "paper_data",
	"mark_data" : CONFIG._jsPath + "mark_data",
	"jquery_form" : CONFIG._libPath + "jquery/jquery.form",
	"word" : CONFIG._libPath + "word",
	"user":	CONFIG._jsPath + "user",
	"WdatePicker":CONFIG._libPath + "datepicker/wdatepicker",
	"crGroup":CONFIG._libPath + "crGroup/script/crGroup"
};

//其他参数
var otherSet = otherSet || {};
otherSet.subject = ['语文','数学','英语','物理','化学','政治','历史','地理','生物','体育','音乐','美术','文科','理科'];

//判断SESSIION是否失效
function is_session(data){
	var _data = {};
	_data = typeof data == "string" ? $.parseJSON(data) : data;
	if(void 0 != _data.session && !_data.session){
		$.messager.alert('提示信息','SESSION失败，请重新登录！');	
		window.top.location.href = baseUrl + '/login.html';
		return false;
	}
}
