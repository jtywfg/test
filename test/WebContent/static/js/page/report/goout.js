$(document).ready(function() {
	$('#searchout').bind('click', function() {
		$('#grid_goout').datagrid({
			queryParams: {
				startTime: $('#startTime').datebox('getValue'),
				endTime: $('#endTime').datebox('getValue'),
				userName : $('#userName').textbox('getValue'),
				realName : $('#realName').textbox('getValue')
			}
		});
	});
	$('#exportout').bind('click', function() {
		window.location.href = "../../report/gooutExport?startTime="
			+ $('#startTime').datebox('getValue')
		+ '&endTime=' + $('#endTime').datebox('getValue')
		+ '&userName=' + $('#userName').textbox('getValue')
		+ '&realName=' + $('#realName').textbox('getValue');
	});
});