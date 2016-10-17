$(document).ready(function() {
	$('#searchin').bind('click', function() {
		$('#grid_tj2').treegrid({
			queryParams: {
				userName : $('#userName').val(),
				realName : $('#realName').val()
			}
		});
	});
});
