/*
 * 主界面
 */
$(document).ready(function() {
	
	var tabPageCtrl = new TabPage('tabArea', 'main');											//框架页面选项卡
		window.tabPageCtrl = tabPageCtrl;
			
	var subMenuCtrl = new SubMenuController('subMenuArea', function(id, title, href) {			//左边菜单组
		tabPageCtrl.addTab({
			text: title,
			href: href,
			id: id
		});
	});
	
	$.get('menu/getTopMenu', function(data) {
		if (data.success === 1) {
			new Menu('nav', function(id, imgSrc, title, href) {				//顶部菜单管理器
				var ta = $('#' + id);
				if (ta.attr('ms') === 'true') {
					subMenuCtrl.addSubMenu(id);
				} else {
					$.post(ta.attr('href'), function(data) {					
						if (data.success === 1) {
							ta.attr('ms', 'true');
							subMenuCtrl.addSubMenu(id, imgSrc, title, data.object);
							$('#subMenuArea').find('a').eq(0).click();
						} else {
							$.messager.alert('信息提示', data.message);
						}
					}, 'json');
				}
				return false;
			}, 30, data.object, 'logo');
			
			$('#nav').find('a').eq(0).click();
		}
	}, 'json');
	                                                         				
	$('#slider').click(function() {				//折叠/展示左边菜单层
		var target = $('#midArea');
		if (!target.hasClass('shrink')) {
			target.addClass('shrink');
			$(window).resize();
		}
		else {
			target.removeClass('shrink');
			$(window).resize;
		}
	});
	
	$('#slider span').click(function(e) {		//折叠/展示左边菜单层
		e.stopPropagation();
		$('#slider').click();
	});
	
	$('#out').click(function() {			//退出系统
		$.messager.confirm('信息提示', '您确认要退出系统吗?', function(r) {
			if (r === true) {
				$.post('adminUser/logout', function(data) {
					/*if (data.success === 1) {
						window.location.href = 'login.html';
					}*/
				}, 'json');
				window.location.href = 'login.html';
			}
		});
	});
	
	$('#updatePwd').click(function() {				//弹出密码修改窗体
		$('#newPassword').textbox('clear');
		$('#passWord').textbox('clear');
		$('#editPasswordWin').dialog('open');
	});
	
	$("#editPassEForm").form({
		success : function(jsonData) {
			var data = $.parseJSON(jsonData);
			if(data.success == 1) {
				$.messager.alert('信息提示', '密码修改成功');
				$('#editPasswordWin').dialog('close');
			} else {
				$.messager.alert('信息提示', data.message);
			}
		}
	});
	
	$('#editPasswordWin').dialog({					//密码修改窗体
		title: '密码修改',
		modal: true,
		closed: true,
		width: 350,
		height: 220,
		buttons: [
	        {
	        	text: '保存',
	        	iconCls: 'icon-save',
	        	handler: function() {
	        		//验证用户输入信息
	        		var newPwd = $.trim($('#newPassword').textbox('getValue')),
	        			comPwd = $.trim($('#passWord').textbox('getValue'));
	        		
	        		if (newPwd != comPwd) {
	        			$.messager.alert('信息提示', '两次密码输入不相同');
	        			return;
	        		}
	        		if (newPwd.length < 6) {
	        			$.messager.alert('信息提示', '新密码长度至少为6位');
	        			return;
	        		}
	        		$("#editPassEForm").form('submit');
	        	}
	        }
	    ]
	});
			
	var timeHandler = null,									//页面大小变化时的定时器
		resizeFn = function() {								//页面大小变化时的事件	
			tabPageCtrl.resize();
		};
	
	$(window).resize(function() {
		if (timeHandler) {
			clearTimeout(timeHandler);
		}
		timeHandler = setTimeout(resizeFn, 200);
	});
	
	window.custom = {
		url: '',
		easyWindow: function(opts) {
			if (opts.url == window.custom.url) {
				$('#customWindow').dialog('open');
			} else {
				//window.custom.url = opts.url;
				var defOpts = {
					modal: true,
					//closed: true,
					content: '<iframe frameborder="0"  src="' + opts.url + '" style="width:100%;height:99%;"></iframe>',
				};
				$.extend(defOpts, opts);
				if(void 0 != opts.type) {
					$('#customWindow2').dialog(defOpts);
				} else {
					$('#customWindow').dialog(defOpts);
				}
			}
		}
	};
});