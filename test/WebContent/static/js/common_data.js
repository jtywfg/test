(function(){

	//常用数据
	
	var COMMON_DATA = COMMON_DATA || {};
	
	//状态
	COMMON_DATA.status = [
		{ "id":0 , "text":"正常" },
		{ "id":1 , "text":"禁用" }		
	];
	
	//是否到期
	COMMON_DATA.outDateFlag = [
		{ "id":0 , "text":"未到期" },
		{ "id":1 , "text":"到期" }		
	];
	
	//年级
	COMMON_DATA.grade = {
		'01':"一年级",
		'02':"二年级",	
		'03':"三年级" ,
		'04':"四年级",
		'05':"五年级",
		'06':"六年级",
		'07':"初一",
		'08':"初二",
		'09':"初三",
		'10':"高一",
		'11':"高二",
		'12':"高三",
};
	
	/* 公用的combox加载信息的方法 */
	COMMON_DATA.getComboxValue = function(options,callback){
		COMMON.ajaxFunction({
			url: options.url,
			data : options.data,
			success: function(jsonData){
				jsonData = jsonData.object || jsonData;
				$.isFunction(callback) && callback(jsonData);
		   }
		});
	};
	
	/* 下拉菜单 */
	COMMON_DATA.combobox = function(options){
		$(options.ele).combobox({
			editable:false, 
			cache: false,
			data: options.data, 
			valueField: options.value, 
			textField: options.text, 
			onLoadSuccess : function(){
				$.isFunction(options.onLoadSuccess) && options.onLoadSuccess();
				/*var data = $(this).combobox("getData");
				if (data.length > 0) {
					$(this).combobox('select', data[0][options.value]);
				} */
			},
			onSelect : function(){
				$.isFunction(options.onSelect) && options.onSelect();
			}
		});
	};
	
	/* 省市区 */
	COMMON_DATA.ssq = function(options,callback){
		var _this = this;
		options = options || {};
		//加载省
		this.getComboxValue({url:'../school/findProvinceList'},function(data){
			$(options.ele_p+" .searchProvince").combobox("loadData",data);
		});
		this.combobox({
			ele : options.ele_p+' .searchProvince',
			value : 'provinceCode',
			text : 'provinceName',
			onSelect : function(){
				$(options.ele_p+" .searchCity").combobox("clear");
				$(options.ele_p+" .searchAreas").combobox("clear");
				void 0 != options.s1 && void 0 != options.s1.onSelect && $.isFunction(options.s1.onSelect) && options.s1.onSelect();
				var _province = $(options.ele_p+' .searchProvince').combobox('getValue');		
				if(_province!=''){
					var _data = {};
					_data.provinceCode = _province;
					_this.getComboxValue({url:'../school/findCityList' , data : _data},function(data){
						$(options.ele_p+" .searchCity").combobox("loadData",data);
					});
				}	
			}
		});
		
		
		//市
		this.combobox({
			ele : options.ele_p+' .searchCity',
			value : 'cityCode',
			text : 'cityName',
			onSelect : function(){
				$(options.ele_p+" .searchAreas").combobox("clear");
				void 0 != options.s2 && void 0 != options.s2.onSelect && $.isFunction(options.s2.onSelect) && options.s2.onSelect();
				var _city = $(options.ele_p+' .searchCity').combobox('getValue');		
				if(_city!=''){
					var _data = {};
					_data.cityCode  = _city;
					_this.getComboxValue({url:'../school/findAreasList' , data : _data},function(data){
						$(options.ele_p+" .searchAreas").combobox("loadData",data);
					});
				}
			}
		});
		
		//区
		this.combobox({
			ele : options.ele_p+' .searchAreas',
			value : 'areaCode',
			text : 'areaName',
			onSelect : function(){
				void 0 != options.s3 && void 0 != options.s3.onSelect && $.isFunction(options.s3.onSelect) && options.s3.onSelect();
			}
		});
	};

	/* 菜单查询 */
	COMMON_DATA.findMenu = function(options,callback){
		COMMON.ajaxFunction({
			url: "../adminMenus/findAdminMenusByParentId",
			data : options,
			success: function(jsonData){
			 $.isFunction(callback) && callback(jsonData);
		   }
		});
	};
	/* datagrid */
	COMMON_DATA.datagrid = function(options,callback){
		var _default = {
			url:'',
			loadMsg:'正在载入数据，请稍候......',
			singleSelect:true,
			collapsible:true,
			cache:false,
			rownumbers:true,
			collapsible:false,//是否可折叠的  
			fit: true,//自动大小
			border:false, 
			nowrap : false,
			fitColumns : true,
			pageNumber: 1, 
			pageSize: 20,//每页显示的记录条数，默认为10  
			pageList: [5, 10,15,20,25,30] , 
			pagination:true,
			queryParams: {},
			toolbar : "",
			frozenColumns: [[
				/*{ field: 'ck', checkbox: true }*/
			]],
			columns: [[
				
			]],
			onSortColumn : function(sort_,order_){
			},
			//点击行不选中Checkbox
			onClickRow: function (rowIndex, rowData) {
				//$(this).datagrid('unselectRow', rowIndex);
			},
			onLoadSuccess:function(data)  
			 {  
				//adminDo.tooltipShow($(".aClass1,.buttonClass1"));
			 },
			 onLoadError : function(data){
			 }
		}
		options = $.extend({},_default,options);
		$(options.ele).datagrid(options);
	};
	
	/* 窗体 */
	COMMON_DATA.dialog = function(options){
		var _default = {		
			title: '',
			modal: true,
			closed: true,
			width: 300,
			height: 150,
			closed: true,
			buttons: [
			]
		}
		options = $.extend({},_default,options);
		$(options.ele).datagrid(options);
	};
	
	window.COMMON_DATA = COMMON_DATA;

})();



