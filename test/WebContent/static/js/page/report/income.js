$(document).ready(function() {
	$('#searchin').bind('click', function() {
		$('#grid_income').datagrid({
			queryParams: {
				startTime: $('#startTime').datebox('getValue'),
				endTime: $('#endTime').datebox('getValue'),
				userName : $('#userName').textbox('getValue'),
				realName : $('#realName').textbox('getValue')
			}
		});
	});
	$('#exportin').bind('click', function() {
		window.location.href = "../../report/incomeExport?startTime="
			+ $('#startTime').datebox('getValue')
		+ '&endTime=' + $('#endTime').datebox('getValue')
		+ '&userName=' + $('#userName').textbox('getValue')
		+ '&realName=' + $('#realName').textbox('getValue');
	});
});