$(document).ready(function() {
	
	$('#announceManage').on('click', 'table.datagrid-btable a[group="_oper_del"]', function(e) {
		var data = $('#grid_announceManage').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		del(data[i].id);
	});
	$('#announceManage').on('click', 'table.datagrid-btable a[group="_oper_edit"]', function(e) {
		var data = $('#grid_announceManage').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openWin(data[i]);
	});
	
	$('#addAnnounce').on('click', function(e) {
		$('#form_announceManage').form('clear');
		$('#win_announceManage').window({title : "添加险种"}).window("open");
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_announceManage').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					console.info($('#id').val());
					//提交表单
					$('#form_announceManage').form('submit', {
							url : '../../announce/editAnnounce',
							success: function() {
								$('#form_announceManage').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								$('#win_announceManage').window('close');
								$('#grid_announceManage').datagrid("reload");
							}
					});
				}
			});
		}
	});
	
	$('#btn_cancel1').bind('click', function() {
		$('#form_announceManage').form('clear');
		$('#win_announceManage').window('close');
	});
	
});

function del(id) {
	$.messager.confirm('提示', '是否删除该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../announce/deleteAnnounce?id=" + id,
				type : "post",
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_announceManage').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function openWin(row) {
	$('#form_announceManage').form('load', row);
	$('#win_announceManage').window({title : "修改公告"}).window("open");
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
	return _str;
}