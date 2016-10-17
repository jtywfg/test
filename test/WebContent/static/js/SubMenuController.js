
function SubMenuController(containerId, itemClick) {
	var container = $('#' + containerId),
		comDiv = $('<div>').addClass('subMenu').appendTo(container),
		titleTemplate = $('<div>').addClass('subMenuTitle'),
		ulTemplate = $('<ul>'),
		liTemplate = $('<li>'),
		pTemplate = $('<p>').addClass('subMenuArrow'),
		aTemplate = $('<a>'),
		imgTemplate = $('<img>'),
		labelTemplate = $('<label>'),
		fn = itemClick,
		num = 0,										//用于创建ID的计数
		timeHandle = null,
		createSubMenu, createMenuList, setCenter, setHeight;
	
	if (typeof fn === 'function') {								//绑定代理单击事件会自动传入连接的标识符名字和地址
		container.delegate('a', 'click', function(e) {
			var id = this.id,
				index = id.indexOf('-'),
				subId = id.substring(index + 1),
				ta = $(this);
			if (ta.attr('href') == '' || ta.attr('href') == '#') {			//禁止加载空页面
				return false;
			}
			fn(subId, this.innerHTML, this.href);
			return false;
		});
	}
	
	comDiv.delegate('div.subMenuTitle', 'click', function() {			//菜单栏展开收缩事件
		var ta = $(this);
		if (ta.hasClass('expand')) {
			ta.removeClass('expand').addClass('shrink');
			ta.next().hide(100);
		}
		else {
			var currentExpand = comDiv.find('div.expand');
			if (currentExpand.length) {
				currentExpand.click();
			}
			ta.removeClass('shrink').addClass('expand');
			var height = getHeight();
			ta.next().show(100);
		}
	});
	
	getHeight = function() {						//获取需要展开的高度
		var titles = comDiv.find('div.subMenuTitle'),
			height = 0,
			comDivHeight = comDiv.innerHeight();
		titles.each(function(index) {
			height += $(this).innerHeight();
		});
		return comDivHeight - height;
	};
	
	setCenter = function(con, elem1, elem2) {			//元素居中 con为容器 elem1和elem2为内部元素
		var conWidth = con.innerWidth(),
			elem1Width = elem1.outerWidth(true),
			elem2Width = elem2.outerWidth(true);
		elem1.css('marginLeft', (conWidth - elem1Width - elem2Width) / 2);
	};
	
	createSubMenu = function(id, imgSrc, title, menuInfo, ck) {
		/*
		 * 创建一个子菜单层
		 * id为菜单组的标志符
		 * imgSrc为菜单组的图片地址
		 * title为菜单组的名字，可选参数，新创建时需要填写
		 * menuInfo为子菜单的信息，可选参数，新创建时需要填写
		 * [
		 *		{
		 * 			menuCName: 'xxx',		菜单的名字
		 * 			url: 'xxx',		菜单页面地址，如果包含subMenu此属性不用写
		 * 			id: 'xxx',			菜单的标志符，可选参数，如果没有则自动生成，在配对href存在的情况下使用不能带有横线符号
		 * 			children: [
		 * 				{
		 * 					menuCName: 'xxx',		菜单的名字
		 * 					url: 'xxx',		菜单页面地址，如果包含subMenu此属性不用写
		 * 				},
		 * 				..........
		 * 			]
		 * 		},
		 * 		........
		 * ]
		 * ck表示是否为固定菜单，为true时是固定菜单， false为变更菜单 
		 */

		if (arguments.length === 1) {					//菜单组存在的情况
			var titleElem = $('#SubMenu-' + id);
			if (titleElem.length && titleElem.hasClass('shrink')) {
				var altMenu = comDiv.find('div[lxalt]');
				if (altMenu.length) {
					altMenu.removeAttr('lxalt').add(altMenu.next()).hide();
				}
				if (titleElem.css('display', 'none')) {
					titleElem.attr('lxalt', 'true').add(titleElem.next()).show();
				}
				titleElem.click();
			}
		}
		else {				//菜单组不存在的情况			
			var titleElem = titleTemplate.clone().attr('id', 'SubMenu-' + id).addClass('expand'),
				img = imgTemplate.clone().attr('src', imgSrc).appendTo(titleElem),
				label = labelTemplate.clone().html(title).appendTo(titleElem),
				ul = ulTemplate.clone(),
				altMenu = comDiv.find('div[lxalt]');
			
			createMenuList(ul, menuInfo);
			
			var expandMenu = comDiv.find('div.expand');
			if (expandMenu.length) {
				expandMenu.click();
			}
			
			if (ck === true) {
				titleElem.appendTo(comDiv);
				//setCenter(titleElem, img, label);
				ul.appendTo(comDiv);
			}
			else {
				if (altMenu.length) {
					altMenu.removeAttr('lxalt').add(altMenu.next()).hide();
					titleElem.attr('lxalt', 'true').insertBefore(altMenu);
					//setCenter(titleElem, img, label);
					ul.insertAfter(titleElem);
				}
				else {
					ul.prependTo(comDiv);
					titleElem.insertBefore(ul);
					//setCenter(titleElem, img, label);
					titleElem.attr('lxalt', 'true');
				}
			}		
		}
	};
	
	createMenuList = function(con, info) {
		/*
		 * 创建一个菜单
		 * con为菜单的容器
		 * info为菜单的信息，格式详见createSubMenu方法下menuInfo的数据格式
		 */
		if (info.length) {
			$.each(info, function(index, val) {
				var li = liTemplate.clone();
				if (!(val.children && val.children.length)) {
					var a = aTemplate.clone().attr('href', val.MENUURL).html(val.MENUNAME);
					if (val.MENUID) {
						a.attr('id', 'SubMenuList-' + val.MENUID);
					}
					else {
						a.attr('id', 'SubMenuList-' + num);
						num++;
					}
					li.append(a);
				} else {
					pTemplate.clone().html(val.menuCName).appendTo(li);
					var ul = ulTemplate.clone().appendTo(li);
					if(val.children && val.children.length) {
						createMenuList(ul, val.children);
					}
					
				}
				con.append(li);
			});
		}
	};
	
	this.addSubMenu = createSubMenu;			//添加一个菜单组，参数详见createSubMenu方法
}
