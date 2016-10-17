$(document).ready(function() {
	
	$.fn.datagrid.defaults.pageSize = 20;			//默认情况表格行数，所有界面的表格行数都用此变量
	
	var selectedId = null,roleMenu=null,row=null;							//单击行时的主键
	
	$('#role-roleName').textbox({width:120});
	$('#roleName').textbox({width:150,required:true,validType:'length[1,10]'});
	$('#roleForm').form({
		onSubmit: function(){
			return $("#roleForm").form('validate');
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
				$('#role-infoWin').dialog('close');
				$('#role-grid').datagrid('reload');
			}
			else {
				$.messager.alert('信息提示', data.message);
			}
		}	
	});
	
	$('#role-infoWin').dialog({		//新增编辑角色窗体初始化
		title: '角色信息',
		modal: true,
		closed: true,
		width: 300,
		height: 150,
		closed: true,
		buttons: [
	        {
	        	text: '提交',
	        	iconCls: 'icon-save',
	        	handler: function() {
	        		$('#roleForm').form('submit');
	        	}
	        }
	    ]
	});
	$('#role-menuWin').dialog({		//配置权限窗体初始化
		title: '权限信息',
		modal: true,
		closed: true,
		width: 280,
		height: 380,
		buttons: [
	        {
	        	text: '提交',
	        	iconCls: 'icon-save',
	        	handler: function() {
	        		var nodes = $('#moduleTree').tree('getChecked');
	        		var temp = {};
	        		for(var i=0;i<nodes.length;i++){
	        			var target =nodes[i].target;
	        			temp[nodes[i].id] = nodes[i].id;
	        			var parent = $('#moduleTree').tree('getParent', target);
	        			if (parent){
	        				if (!temp[parent.id]) {
	      	        		  temp[parent.id] = parent.id;
	      	        		}
	        			}
	        		}
	        		
	        	    var menuIds=[];
	        	    for (var menuId in temp) {
	        	        menuIds.push(menuId);
	        	    }
	        	    var ids= menuIds.join(',');
	        	    $.post('../adminRole/saveAdminRoleMenus',{roleId:selectedId,menus:ids},
	        				function(data){
	        					if (data.success === 1) {
	        						$.messager.show({
	        							title: '信息提示',
	        							msg: '信息提交成功',
	        							style: 'center',
	        							timeout: 1000
	        						});
	        						$('#role-menuWin').dialog('close');
	        					}
	        					else {
	        						$.messager.alert('信息提示', data.message);
	        					}
	        				
	        		},'json');
	        	}
	        }
	    ],
	    
	});
	$('#role-grid').datagrid({					//角色表格初始化
		url: '../adminRole/adminRolePageList',
		idField: 'id',
		singleSelect: true,
		rownumbers: true,
		height: '100%',
		columns: [[
				{ field: 'id', title: 'id', width: 80,hidden:true, align: 'center'},
				{ field: 'roleName', title: '角色名称', width: 300, align: 'center'},
		       ]],
		pagination:true,
		toolbar: $('#roleArea'),
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
	
	
	$('#role-add').click(function() {			//打开新增角色窗体
		$('#roleForm').form('clear');
		$('#roleForm').form({url: '../adminRole/addAdminRole'});
		$('#role-infoWin').dialog('open');
	});
	$('#role-menu').click(function() {			//打开权限配置窗体
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择角色');
			return;
		}
		var jsonData = $('#role-grid').datagrid('getSelected');
		$.post('../adminMenus/menuTree',function(data){
			if(data.success==1){
				if(data.object){
					//配置权限树
					$('#moduleTree').tree({
						checkbox: true,
						data:data.object,
						cascadeCheck: false,
						onCheck: function (node, checked) {
							if(checked){
							if($("#moduleTree").tree('getParent', node.target)){
									var parent=$("#moduleTree").tree('getParent', node.target);
									$("#moduleTree").tree('check', parent.target);
							}}
						},
						onLoadSuccess:function(node){ 
							$('#moduleTree').tree('expandAll');
							$.post('../adminRole/findAdminRoleMenus',{roleId:selectedId},function(data){
								if(data.success==1){
								if(data.object){
									roleMenu=data.object;
									$(data.object).each(function(i, obj){
					                    var n = $("#moduleTree").tree('find',obj.id);
					                    if(n){
					                        $("#moduleTree").tree('check',n.target);
					                    }
					                });
								}}else{
									$.messager.alert('信息提示', data.message);
								}
							},'json');
						},
					});
				}
			}
		},'json');
		$('#role-menuWin').dialog('open');
	});
	$('#role-edit').click(function() {			//编辑角色窗体
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择角色');
			return;
		}
		var data = $('#role-grid').datagrid('getSelected');
		$('#roleForm').form('clear');
		$('#roleForm').form({url: '../adminRole/editAdminRole'});
		$('#roleForm').form('load', data);
		$('#role-infoWin').dialog('open');
	});
		
	$('#role-search').click(function() {			//查询
		
		if(selectedId){
			selectedId=null;
			$("#role-grid").datagrid("unselectRow", row);
		}
		$('#role-grid').datagrid({
			queryParams: {
				roleName: $('#role-roleName').textbox('getValue'),
				page: 1
			}
		});
	});
	
	$('#role-reset').click(function() {				//重置
		$('#role-roleName').textbox('setValue', '');
		$('#role-grid').datagrid({
			queryParams: {
				roleName:null,
				page: 1
			}
		});
	});
	
	$("#role-del").click(function(){	//删除角色
		if(selectedId){
			$.messager.confirm('信息提示', '您确定要删除这条信息吗？',function(r){
				if(r === true){
					$.post('../adminRole/delAdminRole', {id: selectedId}, function(data) {
						if (data.success === 1) {
							$.messager.alert('信息提示', data.message);
							selectedId=null;
							$('#role-grid').datagrid('reload');
						}
						else {
							$.messager.alert('信息提示', data.message);
						}
					}, 'json');
				}
			});
		}else{
			$.messager.alert('信息提示', '请选择角色');
		}
	});
});