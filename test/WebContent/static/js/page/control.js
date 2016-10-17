$(document).ready(function() {
	$.extend($.fn.validatebox.defaults.rules,{
		englishOrNum : {// 只能输入英文和数字
            validator : function(value) {
                return /^[a-zA-Z0-9_]{1,}$/.test(value);
            },
            message : '请输入英文、数字、下划线'
        }, //验证汉字
        CHS: {  
            validator: function (value) {  
                return /^[\u0391-\uFFE5]+$/.test(value);  
            },  
            message: '只能输入汉字'  
        } 
	});
	$.fn.datagrid.defaults.pageSize = 20;			//默认情况表格行数，所有界面的表格行数都用此变量
	
	var currUser = {
			url: '../school/findOnlineUsers'
		},
		historyUser = {
			url: '../school/findHisOnlineUsers'
		};
	
	createGrid(currUser);
	$('#name').textbox({width:120});
	$('#ip').textbox({width:120});
	$('#startDate').datebox({width:120});
	$('#endDate').datebox({width:120});
	$('.commonBtn.gridControl').on('click', function() {
		var girdType = $(this).attr('id');
		
		if(girdType == "currUser") {
			createGrid(currUser);
			$('#login-excel').hide();
		}else if(girdType == "historyUser") {
			createGrid(historyUser);
			$('#login-excel').show();
		}
	});
	$('#login-search').click(function() {			//查询
		var params = {
				page: 1,
				rows: 20
			},
			name = $('#name').textbox('getValue'),
			ip = $('#ip').textbox('getValue'),
			startDate = $('#startDate').datebox('getValue'),
			endDate = $('#endDate').datebox('getValue');
		
		name != "" ? params.name = name : "";
		ip != "" ? params.ip = ip : "";
		startDate != "" ? params.startDate = startDate : "";
		endDate != "" ? params.endDate = endDate : "";
		$('#login-grid').datagrid({queryParams: params});
	});
	$('#login-excel').click(function() {			//导出excel
		location.href = '/netmarkback/school/downloadOnlineUsers';
	});
});

// 生成列表
function createGrid(opts) {
	$('#login-grid').datagrid({					//角色表格初始化
		url: opts.url,
		idField: 'id',
		singleSelect: true,
		rownumbers: true,
		height: '100%',
		queryParams: {
			page: 1,
			rows: 20
		},
		columns: [[
	   		{ field: 'name', title: '姓名', width: 120, align: 'center'},
	   		{ field: 'userName', title: '用户名', width: 120, align: 'center'},
	   		{ field: 'schoolName', title: '学校', width: 120, align: 'center'},
	   		{ field: 'loginIp', title: '登录IP', width: 180, align: 'center'},
	   		{ field: 'loginDate', title: '登录时间', width: 180, align: 'center'}
       ]],
		pagination:true,
		toolbar: $('#loginArea'),
		loadFilter: function(data){
			return data;
		}
	});	
}