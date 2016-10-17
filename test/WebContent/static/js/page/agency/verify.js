$(document).ready(function() {
	$('#verify').on('click', 'table.datagrid-btable a[group="_oper_del"]', function(e) {
		var data = $('#grid_verify').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		del(data[i].id);
	});
	$('#verify').on('click', 'table.datagrid-btable a[group="_oper_freeze"]', function(e) {
		var data = $('#grid_verify').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		freeze(data[i].id, data[i].Password);
	});
	$('#verify').on('click', 'table.datagrid-btable a[group="_oper_edit"]', function(e) {
		var data = $('#grid_verify').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openWin(data[i].id);
	});
	
	$('#addUser').on('click', function(e) {
		$('#win_verify').window("open");
	});
	
	$('#btn_ok1').bind('click', function() {
		console.info("测试绑定次数1");
		reviewDraw(1, '');
	});
	
	$('#btn_no1').bind('click', function() {
		console.info("测试绑定次数2");
		reviewDraw(2, '不');
	});
	
});

function reviewDraw(isCheck, msg) {
	$.messager.confirm('提示', '是否' + msg + '通过?', function(r) {
		if(r) {
			$.messager.progress();
			$.post('../../fundaccount/reviewDraw'//url
					, {//data
						id : $('#currId').val(),
						isCheck : isCheck
					}
					, function(json) {
						$.messager.progress('close');
						if (json.success === 1) {
							$.messager.alert('提示', json.message, 'info');
							$('#win_verify').window('close');
							$('#grid_verify').datagrid("reload");
						}
					}
					, "json");
		}
	});
}

function del(id) {
	$.messager.confirm('提示', '是否删除该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../verify/deleteUser?id=" + id,
				type : "get",
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_verify').datagrid("reload");
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
				url : "../../verify/freezedUser",
				type : "get",
				data : {id : id
				, Password : Password},
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_verify').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function openWin(id) {
	$('#currId').val(id);
	$('#win_verify').window("open");
}

function fmtSex(val, row, index) {
	if(val == 1) {
		return '男';
	} else {
		return '女';
	}
}
function fmtType(val, row, index) {
	if(val == -1) {
		return '提现';
	} else if(val == 1) {
		return '返金';
	}
}
function fmtCheck(val, row, index) {
	if(val == 0) {
		return '未审核';
	} else if(val == 1) {
		return '审核通过';
	} else if(val == 2) {
		return '审核不通过';
	}
}

function fmtActn(val, row, index) {
	var _str = '';
//	_str = 				'<a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-no" group="_oper_del"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">删除</span><span class="l-btn-icon icon-no">&nbsp;</span></span></a>';
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-edit" group="_oper_edit"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">审核</span><span class="l-btn-icon icon-edit">&nbsp;</span></span></a>');
	return _str;
}