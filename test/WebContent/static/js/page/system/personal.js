$(document).ready(function() {
	
	$.getJSON('../../user/findLoginUser', function(json) {
			if (json.success === 1) {
				$('#form_personal').form('load', json.object);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
	
	$('#btn_ok_personal').bind('click', function() {
		if($('#form_personal').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
//					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_personal').form('submit', {
							success: function(json) {
								json = $.parseJSON(json);
								if (json.success === 1) {
									$.messager.alert('提示', json.message, 'info');
								} else {
									$.messager.alert('错误', json.message, 'error');
								}
							}
					});
				}
			});
		}
	});
});