$(document).ready(function() {
	//$.fn.datagrid.defaults.pageSize = 20;			//默认情况表格行数，所有界面的表格行数都用此变量
	$.extend($.fn.validatebox.defaults.rules, {
		integer: {// 验证整数 
			validator: function (value) {
				return /^[0-9]{1,4}$/i.test(value);
			},
			message: '请输入整数,并且最大数不能超过9999！'
		}
	});
	var selectedId = null,delNode=null;							//单击行时的主键
	$('#nav-navName').textbox({width:120});
	$('#menuName').textbox({width: 150,required:true,validType:['length[1,8]','CHS']});
	
	$('#sortNum').textbox({width: 150,required:true,validType:['length[1,2]','integer']});
	$('#menuUrl').textbox({width: 150,required:true});
	$('#bigImageUrl').textbox({width: 150});
	$('#smallImageUrl').textbox({width: 150});
	$('#remark').textbox({width: 150});
	$('#menuFlag').textbox({width: 150,required:true});
	$('#navForm').form({
		onSubmit: function(){
			return $("#navForm").form('validate');
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
				$('#nav-infoWin').dialog('close');
				$('#nav-grid').treegrid('reload');
			}
			else {
				$.messager.alert('信息提示', data.message);
			}
		}	
	});
	
	
	$('#nav-infoWin').dialog({		//新增编辑菜单窗体初始化
		title: '菜单信息',
		modal: true,
		closed: true,
		width: 350,
		height: 400,
		closed: true,
		buttons: [
	        {
	        	text: '提交',
	        	iconCls: 'icon-save',
	        	handler: function() {
	        		$('#navForm').form('submit');
	        	}
	        }
	    ]
	});
	$('#navArea').delegate('#nav-add','click',function() {			//打开新增菜单窗体
		$('#navForm').form('clear');
		$('#menuName').textbox({editable: true});
		$("#hideMenu").show();
		//菜单下拉树
		$.post('../adminMenus/menuTree',function(data){
			$('#parentId').combotree('loadData', data.object);
		},'json');
		$('#parentId').combotree({width: 150,panelHeight:150});
		$('#navForm').form({url: '../adminMenus/addAdminMenus'});
		$('#nav-infoWin').dialog('open');
	});
				
	$('#navArea').delegate('#nav-edit','click',function() {//编辑菜单窗体
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择菜单');
			return;
		}
		var data = $('#nav-grid').treegrid('getSelected');
		$('#navForm').form('clear');
		$("#hideMenu").hide();
		$('#parentId').combotree({required: false});
		$('#navForm').form({url: '../adminMenus/editAdminMenus'});
		$('#navForm').form('load', data);
		$('#nav-infoWin').dialog('open');
	});
	
	$('#nav-status').click(function() {				//删除
		if (!selectedId) {
			$.messager.alert('信息提示', '请选择菜单');
			return;
		}
		if(delNode.state=='closed')
		{
			$.messager.alert('信息提示', '该菜单下含有子菜单,不允许删除');
			return;
		}
		$.messager.confirm('信息提示', '确认删除该菜单吗？', function(r) {
			if (r === true) {
				$.post('../adminMenus/delAdminMenus',  {
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
						 $('#nav-grid').treegrid('reload');
					}
					else {
						$.messager.alert('信息提示', data.message);
					}
				}, 'json');
			}
		});
	});
	
	$("#nav-grid").treegrid({					//菜单初始化
		url:'../adminMenus/findAdminMenusByParentId?parentId=0',
		idField: 'id',
		treeField: 'menuName',
		height: '94%',
		columns:[[
		        { field: 'menuName', title: '菜单名称', width: 200, align: 'left'},
		   		{ field: 'menuFlag', title: '菜单标志', width: 120, align: 'center'},
		   		//{ field: 'level', title: '层级', width: 120, align: 'center'},
		   		//{ field: 'parentId', title: '上级菜单', width: 150, align: 'center'},
		   		{ field: 'menuUrl', title: '菜单地址', width: 150, align: 'center'},
		   		{ field: 'bigImageUrl', title: '大图标', width: 150, align: 'center'},
		   		{ field: 'smallImageUrl', title: '小图标', width: 150, align: 'center'},
		   		{ field: 'sortNum', title: '排序序号', width: 120, align: 'center'},
		   		{ field: 'remark', title: '备注', width: 150, align: 'center'}
		]],
		onClickRow: function(data) {
			selectedId=data.id;
			delNode=data;
		},
		onBeforeExpand:function(node){ 
            //选中的节点是否为叶子节点 
			if (!node.children) {
				getTreeData(node.id, function(backData) {
					$("#nav-grid").treegrid('append', {
						parent: node.id,
						data: backData
					});
					$("#nav-grid").treegrid('expand', node.id);
				});
				return false;
			}
		},
		loadFilter: function(data){
			if (data.object){
				return data.object;
			} else {
				return data;
			}
		}
	});
});

//获取菜单数据   parentId为子节点数据，如果不给则默认读取第一级数据  callback为回调函数  ck表示是不是根节点数据，默认为false
function getTreeData(parentId, callback, ck) {					
	$.post('../adminMenus/findAdminMenusByParentId', {
			parentId: parentId || 0
		}, function(data) {
			if (data.success === 1) {
				if (ck === true) {
					if (data.object) {
						temp = [
						  {
							 
							  children: obj
						  }
						];
						callback(temp);
					}else{
						callback('');
					}
				}else {
					if (data.object.length) {
						callback(data.object);
					}
				}
			}else{
				$.messager.alert('信息提示', data.message);
			}
		}, 'json');
	
	
}