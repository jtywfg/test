/*
 * 登录
 */
$(document).ready(function() {
	var $username = $('#username'),
		$pwd = $('#pwd'),
		$ck = $('#ck'),
		//$ckimg = $('#ckimg'),
		$reset = $('#reset'),
		$sub = $('#sub');
	
//	$ckimg.click(function() {			//验证码更新
//		$(this).attr('src', 'loginAction!getCode.action?time=' + new Date());
//	});
	
	$sub.click(function() {						//登录事件
//		var ckValue = $ck.val();
//		if (ckValue == '请输入验证码' || ckValue.length === 0) {
//			alert('请输入验证码');
//			return false;
//		}
		if($.trim($username.val())==""){
			$.messager.alert('信息提示', '请输入用户名');
			return false;
		}
		if($.trim($pwd.val())==""){
			$.messager.alert('信息提示', '请输入密码');
			return false;
		}
		$.post('user/login', {
			username: $username.val(),
			passWord: $pwd.val(),
		}, function(josnData) {
			var data= eval('(' + josnData + ')');
			if (data.success === 1) {
				window.location.href = 'main.jsp';
			}
			else {
				$.messager.alert('信息提示', data.message);
			}
		}, 'text');
	});
	$(document.body).bind('keyup', function(event) {
		if (event.keyCode === 13) {
			$sub.click();
		}
	});
});