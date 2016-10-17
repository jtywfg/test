$(document).ready(function() {
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_addUserOrder').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_addUserOrder').form('submit', {
							success: function() {
								$('#form_addUserOrder').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
							}
					});
				}
			});
		}
	});
	
	$('#btn_cancel1').bind('click', function() {
		$('#form_addUserOrder').form('clear');
	});
	
});

function getUserCar(newValue, oldValue) {
	$('#carId').combobox({url : '../../userCar/findUserCar?userId=' + newValue});
}

function getOrderCode(newValue, oldValue) {
	$('#tjOrderCode').combobox({url : '../../userOrder/findUserOrder?userId=' + newValue});
}