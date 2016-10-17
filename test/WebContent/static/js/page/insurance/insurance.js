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
	
	$('#addInsurance').on('click', function(e) {
		$('#form_insurance').form('clear');
		$('#win_insurance').window({title : "添加险种"}).window("open");
	});
	
	$('#isRate').combobox({
		onChange : function(newValue, oldValue) {
			if(newValue == 0) {
				$('#tr_money').show();
				$('#tr_rate').hide();
				$('#money').combobox({required:true});
				$('#rate').combobox({required:false});
			} else {
				$('#tr_money').hide();
				$('#tr_rate').show();
				$('#money').combobox({required:false});
				$('#rate').combobox({required:true});
			}
		}
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_insurance').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					console.info($('#id').val());
					//提交表单
					$('#form_insurance').form('submit', {
							url : $('#id').val() > 0 ? '../../product/updateProduct' : '../../product/addProduct',
							success: function() {
								$('#form_insurance').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								$('#win_insurance').window('close');
								$('#grid_insurance').datagrid("reload");
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
				url : "../../product/deleteProduct?id=" + id,
				type : "post",
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
	$.getJSON('../../product/findOne?id=' + id, function(json) {
			if (json.success === 1) {
				$('#form_insurance').form('load', json.object);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
	$('#win_insurance').window({title : "修改险种"}).window("open");
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