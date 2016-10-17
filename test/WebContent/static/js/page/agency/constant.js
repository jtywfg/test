$(document).ready(function() {
	$.getJSON('../../constant/findConstant', function(json) {
			if (json.success === 1) {
				$('#form_constant').form('load', json.object);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_constant').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
//					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_constant').form('submit', {
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
