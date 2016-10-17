/*
 * 图片特效组件
 * opts
 * {
 * 		tagCls: 'xxx',							需要触发特效图片的样式名，以此作为钩子抓取元素
 * 		orgImgUrl: 'xxx',						图片源地址对应的属性名，如果没有则获取点击图片的地址，默认是直接取图片的地址
 * 		panelWidth: xxx,						弹窗的宽度，默认为500
 * 		panelHeight: xxx,						弹窗的高度，默认为450
 * 		onBeforeOpen: function(src) {}			弹窗打开前触发的方法， src为点击图片的地址，如果函数返回false则窗口不会打开
 * 		onAfterOpen: function(src) {}			弹窗打开后触发的方法， src为点击图片的地址
 * 		resizeAble: true/false					是否开启滚轮放大缩小功能，默认为true开启同时附带拖拽功能
 * 		resizePercent: xx						缩放比例，默认为0.2
 * 		
 * }
 */
function ImgEffect(opts) {
	
	var defaultOpts = {										//默认配置项
		orgImgUrl: '',
		panelWidth: 500,
		panelHeight: 450,
		onBeforeOpen: function(src){return true;},
		onAfterOpen: function(src){},
		resizeAble: true,
		resizePercent: 0.2
	};
	
	var customOpts = $.extend({}, defaultOpts, opts);		//生成自定义配置项
	
	var body = $(document.body),
		div = $('<div>').addClass('dragImgWindow'),			//弹窗
		mask = $('<div>').addClass('dragImgMask'),			//遮罩
		title = $('<p>').addClass('dragImgTitle'),
		close = $('<a>').addClass('dragImgClose'),
		box = $('<div>').addClass('dragBox'),
		limit = $('<div>').addClass('dragLimit'),
		img = $('<img>');
	
	mask.appendTo(body);
	box.append(limit);
	limit.append(img);
	div.append(title).append(close).append(box).appendTo(body);
	
	div.css({												//设置弹窗大小
		width: customOpts.panelWidth,
		height: customOpts.panelHeight
	});
	box.css('height', customOpts.panelHeight - title.outerHeight() - 2 * parseInt(box.css('paddingTop')));
	
	if (customOpts.resizeAble === true) {					//根据情况设置大小和样式	绑定事件
		setAction();
	}
	else {
		limit.css('overflow', 'auto');
	}
	
	body.delegate('img.' + customOpts.tagCls, 'click', function() {			//打开弹窗
		var target = $(this),
			imgSrc = customOpts.orgImgUrl ? target.attr(customOpts.orgImgUrl) : target.attr('src'),
			str = target.attr('title') || '图片';							//如果图片有title属性，则窗体标题字就是title
		
		if (typeof customOpts.onBeforeOpen == 'function') {			//窗体打开前触发函数如果返回false，则取消窗体的打开
			var ck = customOpts.onBeforeOpen(imgSrc);
			if (ck !== true) {
				return;
			}
		}
		
		title.text(str);
		show();
		if (customOpts.resizeAble === true) {
			initImg();
		}
		img.attr('src', imgSrc);
	});
	
	close.click(function() {			//窗体关闭事件
		hide();
		if (customOpts.resizeAble === true) {
			initImg();
			img.css({
				top:0,
				left:0,
				marginLeft:0,
				marginTop:0
			});
		}
		img.attr('src', '#');
	});	
	
	function initImg() {		//拥有放大缩小功能时初始化图片
		img.css({
			width: box.width()/*,
			height: box.height()*/
		});
	}
	
	function setAction() {					//缩放的相关设置操作
		img.addClass('dragImg');
		limit.css('overflow', 'hidden');
		img.bind('mousewheel', function(e) {
			if (e.originalEvent.wheelDelta > 0) {
				resizeImg(customOpts.resizePercent);
			}
			else {
				resizeImg(-customOpts.resizePercent);
			}
		});
		img.bind('DOMMouseScroll', function(e) {
			if (e.originalEvent.detail > 0) {
				resizeImg(customOpts.resizePercent);
			}
			else {
				resizeImg(-customOpts.resizePercent);
			}
		});
		
		img.draggable();
	}
	
	function removeAction() {					//取消缩放的相关操作
		img.removeClass('dragImg').css({
			width: 'auto',
			height: 'auto'
		});
		limit.css('overflow', 'auto');
		img.unbind('mousewheel');
	}
	
	function resizeImg(num) {			//缩放图片
		num += 1;
		var width = img.width() * num,
			height= img.height();
		img.css('width', width);
		/*img.css({
			width: width,
			//height: height,
			top: '50%',									//此后面四个属性是用来定位中心
			left: '50%',
			marginLeft: - (width / 2),
			marginTop: - (height / 2)
		});*/
		
	}
	
	function show() {			//显示弹窗
		var screenWidth = document.documentElement.clientWidth,
			screenHeight = document.documentElement.clientHeight;
		div.css({
			top: (screenHeight - customOpts.panelHeight) / 2,
			left: (screenWidth - customOpts.panelWidth) / 2
		});
		mask.show();
		div.show();
	}
	
	function hide() {			//隐藏弹窗
		mask.hide();
		div.hide();
	}
	
	this.enable = function() {			//开启缩放功能
		setAction();
	};
	
	this.disable = function() {			//禁用缩放功能
		removeAction();
	};
}
