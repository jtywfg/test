$(document).ready(function() {
	
	$('#addUser').on('click', function(e) {
		$('#win_user').window("open");
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_user').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_user').form('submit', {
							success: function(json) {
								$('#form_user').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								var data = eval('(' + json + ')');
								if (data.success === 1) {
									$.messager.alert('提示', data.message, 'info');
									$('#grid_user').datagrid("reload");
								} else {
									$.messager.alert('错误', data.message, 'error');
								}
							}
					});
				}
			});
		}
	});
	
	$('#btn_cancel1').bind('click', function() {
		$('#form_user').form('clear');
		$('#win_user').window('close');
	});
	
});
