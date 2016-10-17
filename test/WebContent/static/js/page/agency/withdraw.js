$(document).ready(function() {
	var vars = {};
	$.getJSON('../../fundaccount/findUsableMoney', function(json) {
			if (json.success === 1) {
				vars.totalMoney = json.object;
				$('#form_withdraw').form('load', json);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
	
	$('#drawMoney').numberbox({
		onChange: function(newValue, oldValue) {
				console.info(new Date);
				if(vars.totalMoney < parseFloat(newValue)) {
					$('#drawMoney').numberbox('clear');
				}
		}
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_withdraw').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
//					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_withdraw').form('submit', {
							success: function(json) {
								json = $.parseJSON(json);
								if (json.success === 1) {
									$.messager.alert('提示', json.message, 'info');
								} else {
									$.messager.alert('错误', json.message, 'error');
								}
								$.getJSON('../../fundaccount/findUsableMoney', function(json) {
										if (json.success === 1) {
											vars.totalMoney = json.object;
											$('#form_withdraw').form('load', json);
										} else {
											$.messager.alert('错误', json.message, 'error');
										}
									}
								);
							}
					});
				}
			});
		}
	});
	
	
});
