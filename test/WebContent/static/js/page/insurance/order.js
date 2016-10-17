$(document).ready(function() {
//	$.parser.parse('#insurance');
	
	$('#insurance').on('click', 'table.datagrid-btable a[group="_oper_del"]', function(e) {
		var data = $('#grid_insurance').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		del(data[i].id);
	});
	$('#insurance').on('click', 'table.datagrid-btable a[group="_oper_freeze"]', function(e) {
		var data = $('#grid_insurance').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		freeze(data[i].id, data[i].Password);
	});
	$('#insurance').on('click', 'table.datagrid-btable a[group="_oper_edit"]', function(e) {
		var data = $('#grid_insurance').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openWin(data[i].id);
	});
	
	$('#addUser').on('click', function(e) {
		$('#win_insurance').window("open");
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_insurance').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_insurance').form('submit', {
							success: function() {
								$('#form_insurance').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								$('#win_insurance').window('close');
							}
					});
				}
			});
		}
	});
	
	$('#btn_cancel1').bind('click', function() {
		$('#form_insurance').form('clear');
		$('#win_insurance').window('close');
	});
	
});

function del(id) {
	$.messager.confirm('提示', '是否删除该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../user/deleteUser?id=" + id,
				type : "get",
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_insurance').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function freeze(id, Password) {
	$.messager.confirm('提示', '是否禁用该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../user/freezedUser",
				type : "get",
				data : {id : id
				, Password : Password},
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_insurance').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function openWin(id) {
	$.getJSON('../../user/findUser?id=' + id, function(json) {
			if (json.success === 1) {
				$('#form_insurance').form('load', json.object);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
	$('#win_insurance').window("open");
}

function fmtSex(val, row, index) {
	if(val == 1) {
		return '男';
	} else {
		return '女';
	}
}

function fmtActn(val, row, index) {
	var _str = '';
	_str = 				'<a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-no" group="_oper_del"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">删除</span><span class="l-btn-icon icon-no">&nbsp;</span></span></a>';
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-edit" group="_oper_edit"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">修改</span><span class="l-btn-icon icon-edit">&nbsp;</span></span></a>');
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-help" group="_oper_freeze"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">禁用</span><span class="l-btn-icon icon-help">&nbsp;</span></span></a>');
	return _str;
}