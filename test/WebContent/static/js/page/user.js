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
        }, 
		 });
	$.fn.datagrid.defaults.pageSize = 20;			//默认情况表格行数，所有界面的表格行数都用此变量
	_myFunction_.getComboxValue({url:'../adminRole/findAllAdminRole'},function(role){
		$('#roleAdminId').combobox({editable:false, required:true,listHeight:150,width: 150, data: role, valueField: 'id', textField: 'roleName'});
	});
	/*$('#roleAdminId').combobox({editable:false, required:true, panelHeight: 'auto',width: 150});*/
	var selectedId = null,row=null;							//单击行时的主键
	$('#user-userName').textbox({width:120});
	$('#user-realName').textbox({width:120});
	$('#userName').textbox({width: 150,required:true,validType:['length[0,20]']});
	$('#passWord').textbox({width: 150,validType:['englishOrNum','length[6,20]'], type:'password'});
	$('#realName').textbox({width: 150,required:true,validType:['length[1,5]','CHS']});
	$('#status').combobox({width: 150,editable:false, data: COMMON_DATA.status, valueField: 'id', textField: 'text', required:true});
	$('#userForm').form({
		onSubmit: function(){
			return $("#userForm").form('validate');
	    },
		success: function(data) {
			data = $.parseJSON(data);
			if (data.success === 1) {
				$.messager.show({
					title: '信息提示',
					msg: '信息提交成功',
					style: 'center',
					timeout: 1000
				});
				$('#user-infoWin').dialog('close');
				$('#user-grid').datagrid('reload');
			}
			else {
				$.messager.alert('信息提示', data.message);
			}
		}
	});
	
	
	$('#user-infoWin').dialog({		//新增编辑用户窗体初始化
		title: '用户信息',
		modal: true,
		closed: true,
		width: 350,
		height: 240,
		closed: true,
		buttons: [
	        {
	        	text: '提交',
	        	iconCls: 'icon-save',
	        	handler: function() {
	        		$('#userForm').form('submit');
	        	}
	        }
	    ]
	});
	
	$('#user-grid').datagrid({					//角色表格初始化
		url: '../adminUser/adminUserPageList',
		idField: 'id',
		singleSelect: true,
		rownumbers: true,
		height: '100%',
		columns: [[
		   		{ field: 'userName', title: '用户名', width: 120, align: 'center'},
		   		{ field: 'realName', title: '姓名', width: 120, align: 'center'},
		   		{ field: 'roleName', title: '角色', width: 120, align: 'center'}
		       ]],
		pagination:true,
		toolbar: $('#userArea'),
		onClickRow: function(index, data) {
			selectedId = data.id;
			row=index;
		},
		loadFilter: function(data){
			if (data.object){
				var object={};
					object.total=data.object.count;
					object.rows=data.object.list;
				return object;
			} else {
				return data;
			}
		}
	});
	$('#userArea').delegate('#user-add','click',function() {	//打开新增用户窗体
		$('#userForm').form('clear');
		$('#userName').textbox({editable: true});
		$('#passWord').textbox('enable');
		$.post('../adminRole/findAllAdminRole',function(data){
			$('#roleAdminId').combobox('loadData',data.object);
		},'json');
		
		$('#userForm').form({url: '../adminUser/addAdminUser'});
		$('#user-spLine').show();
		$('#passWord').textbox({required: true});
		$('#user-infoWin').dialog('open');
	});
			
	$('#userArea').delegate('#user-edit','click',function() {//编辑用户窗体
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择用户');
			return;
		}
		var data = $('#user-grid').datagrid('getSelected');
		$('#userForm').form('clear');	
		$('#userName').textbox({editable: false});
		$('#user-spLine').hide();
		$('#passWord').textbox({required: false});//加此行是为了防止表单不会提交
		$.post('../adminRole/findAllAdminRole',function(data){
			$('#roleAdminId').combobox('loadData',data.object);
		},'json');
		$('#userForm').form('load', data);
		$('#passWord').textbox('clear');
		$('#userForm').form({url: '../adminUser/editAdminUser'});
		$('#user-infoWin').dialog('open');
	});
		
	$('#user-pwd').click(function() {				//重置密码
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择用户');
			return;
		}
		$.messager.confirm('信息提示', '确认重置该用户的密码吗？', function(r) {
			if (r === true) {
				$.post('../adminUser/resetPassWord', {
					id: $('#user-grid').datagrid('getSelected').id,
					passWord: '123456'
				}, function(data) {
					if (data.success === 1) {
						$.messager.show({
							title: '信息提示',
							msg: '该用户密码重置成功，密码重置成123456',
							style: 'center',
							timeout: 700
						});
					}
					else {
						$.messager.alert('信息提示', data.message);
					}
				}, 'json');
			}
		});
	});
	
	$('#user-del').click(function() {				//删除用户
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择用户');
			return;
		}
		$.messager.confirm('信息提示', '确认删除该用户吗？', function(r) {
			if (r === true) {
				$.post('../adminUser/delAdminUser',  {
					id: selectedId,
				}, function(data) {
					if (data.success === 1) {
						$.messager.show({
							title: '信息提示',
							msg: '删除成功',
							style: 'center',
							timeout: 700
						});
						selectedId=null;
						 $('#user-grid').datagrid('reload');
					}
					else {
						$.messager.alert('信息提示', data.message);
					}
				}, 'json');
			}
		});
	});
	
	$('#user-search').click(function() {			//查询
		if(selectedId){
			selectedId=null;
			$("#user-grid").datagrid("unselectRow", row);
		}
		$('#user-grid').datagrid({
			queryParams: {
				userName: $('#user-userName').textbox('getValue'),
				realName: $('#user-realName').textbox('getValue'),
				page: 1
			}
		});
	});
	
	$('#user-reset').click(function() {				//重置
		$('#user-userName').textbox('setValue', '');
		$('#user-grid').datagrid({
			queryParams: {
				page: 1
			}
		});
	});
});