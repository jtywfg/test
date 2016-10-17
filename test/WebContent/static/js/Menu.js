

function Menu(navId, itemClick, del, menuData) {
/*
 * 初始化界面主菜单
 * 参数：nav为菜单ul容器的Id名，限定为右浮
 *      itemClick为菜单项单击事件，会自动传入菜单的ID标识符、菜单图片地址、菜单名字和链接地址
 *      del为菜单与左浮元素的间距，默认为20
 *      menuData为菜单信息
 *      [
 *      	{
 *      		menuCName: 'xxx',		菜单的名字
 *      		id: 'xxx',			菜单的唯一标示符，拼接到地址上
 *      		iconCls: 'xxx'		菜单图片地址
 *      	},
 *      	........
 *      ]
 * 可选参数：为菜单外层容器的左浮元素的Id名
 */
	var nav = $('#' + navId),
		currentSelected = null,			//当前选中菜单
		container = nav.parent(),
		liTemplate = $('<li>'),
		aTemplate = $('<a>'),
		imgTemplate = $('<img>'),
		spanTemplate = $('<span>'),
		num = 1,
	    fn = itemClick,
		args = Array.prototype.slice.call(arguments, 4),
		elems = [],
		del = del || 20,
		space = 0,
		setSpace, setPadding, createMenu;
	
	nav.delegate('a', 'click', function(e) {
		if (currentSelected) {
			currentSelected.removeClass();
		}
		var ta = $(this);
		ta.addClass('selected');
		if (typeof fn === 'function') {
			fn(ta.attr('id'), ta.find('img').attr('src'), ta.find('span').html(), ta.attr('href'));
		}
		currentSelected = ta;
		return false;
	});
	
	createMenu = function(info) {
		$.each(info, function(index, val) {
			var li = liTemplate.clone(),
				a = aTemplate.clone().attr({
					href: 'menu/getMenuByParent?PARENTID=' + val.MENUID,
					id: val.MENUID
				}),
				img = imgTemplate.clone().attr('src', val.BIGIMAGEURL).appendTo(a),
				span = spanTemplate.clone().html(val.MENUNAME).appendTo(a);
			li.append(a);
			if (index === info.length) {
				li.addClass('last');
			}
			nav.append(li);
		});
	};
	
	setSpace = function() {
		space = container.innerWidth();
		if (args.length >= 0) {							//收集容器左浮元素，并得到剩余宽度 
			$.each(args, function(index, value) {
				var elem = $('#' + value),
					elemWidth = elem.outerWidth(true);
				elems.push(elem);
				space -= elemWidth;
			});
		}
	};
	
	setPadding = function() {
		var navlis = nav.find('li'),
			liLength = navlis.length,
			navFl = navlis.eq(0),
			navWidth = liLength * navFl.outerWidth(true),
			dis = space - navWidth;				
		if ( dis < 0) {
			var every = (dis - del) / liLength ,
				paddingLeft = parseInt(navFl.find('a').css('paddingLeft'), 10) / 2;
				temp = paddingLeft + every / 2;
			if(temp > 10 ) {
				navlis.find('a').css('padding', '0 ' + temp + 'px');
			} else {
				navlis.find('a').css('padding', '0px 10px');
			}
		} else {
			if (dis - del > 0) {
				var every = (dis - del) / liLength ,
				paddingLeft = parseInt(navFl.find('a:eq(0)').css('paddingLeft'), 10) / 2;
				temp = Math.floor(paddingLeft + every / 2);
				navlis.find('a').css('padding', '0 ' + temp + 'px');
			}
			
		}
	};
	
	createMenu(menuData);
	
	this.reset = function() {				//设置左右填充
		setSpace();
		setPadding();
	};
}

