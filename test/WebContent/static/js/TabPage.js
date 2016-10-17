

function TabPage(tabId, contentId) {
	/*
	 * 主框架页面选项卡
	 * 参数说明
	 * tabId				选项卡容器Id名
	 * contentId			内容容器Id名
	 */
	
	
	var tabArea = $('#' + tabId),
		tabAreaWidth = tabArea.innerWidth(),
		contentArea = $('#' + contentId),
		tabPrev = $('<p>').addClass('tabPrev').appendTo(tabArea),
		tabNext = $('<p>').addClass('tabNext').appendTo(tabArea),
		tabButtonWidth = tabPrev.outerWidth(true),
		limitArea = $('<div>').addClass('tabConLimit').appendTo(tabArea),
		noLimitArea = $('<div>').addClass('tabConNoLimit').appendTo(limitArea),
		ul = $('<ul>').appendTo(noLimitArea),
		totalWidth = 0,
		liTemplate = $('<li>'),
		spanLeftTemplate = $('<span>').addClass('tabLeft'),
		spanRightTemplate = $('<span>').addClass('tabRight'),
		spanTextTemplate = $('<span>').addClass('tabText'),
		spanCloseTemplate = $('<span>').addClass('tabClose'),
		pageContentTemplate = $('<div>').addClass('tabPageContent'),
		iframeTemplate = $('<iframe>').attr({
			width: '100%',
			frameborder: '0'
		}),
		currentTab = null,					//当前激活的选项卡
		currentContent = null,				//当前激活的内容
		setActiveTab, changeTabButtonStatus, moveTab, createTab;
	
	
	ul.delegate('li', 'click', function() {				//激活选项卡
		setActiveTab($(this));
	});
	
	ul.delegate('li span.tabClose', 'click', function(e) {			//删除选项卡
		e.stopPropagation();
		var ta = $(this),
			taP = ta.parent(),
			id = taP.attr('id'),
			index = id.indexOf('-'),
			subId = id.substring(index + 1),
			page = $('#TabPageContent-' + subId),
			liElem = taP.next();

		if (currentTab && (currentTab.attr('id') == id)) {
			if (!liElem.length) {
				liElem = taP.prev();
				if(liElem.length) {
					setActiveTab(liElem);
				}
				else {
					currentTab = null;
					currentContent = null;
				}
			}
			else {
				setActiveTab(liElem);
			}
		}
		totalWidth -= taP.outerWidth(true);
		taP.add(page).remove();	
		if (totalWidth < tabAreaWidth) {
			changeTabButtonStatus(false);
		}
	});
	
	tabPrev.click(function() {						//激活前一个选项卡
		if (currentTab) {
			var ta = currentTab.prev();
			if (ta.length && (!ul.is(':animated'))) {
				setActiveTab(ta);
			}
		}
	});
	
	tabNext.click(function() {						//激活后一个选项卡
		if (currentTab) {
			var ta = currentTab.next();
			if (ta.length && (!ul.is(':animated'))) {
				setActiveTab(ta);
			}
		}
	});
	
	moveTab = function(liElem) {
		var tabOffset = liElem.offset(),
			tabLeft = tabOffset.left,
			tabWidth = liElem.outerWidth(true),
			tabRight = tabLeft + tabWidth,	
			limitOffset = limitArea.offset(),
			limitLeft = limitOffset.left,
			limitWidth = limitArea.innerWidth(),
			limitRight = limitLeft + limitWidth;
			
		
		if (tabLeft < limitLeft) {
			var dis = limitLeft - tabLeft;
			ul.animate({
				marginLeft: parseInt(ul.css('marginLeft')) + dis
			});
		}
		else {
			if (tabRight > limitRight) {
				var dis = tabRight - limitRight;
				ul.animate({
					marginLeft: parseInt(ul.css('marginLeft')) - dis
				});
			}
		}
	};
	
	changeTabButtonStatus = function(ck) {				//根据情况显示隐藏选项卡切换按钮，true为显示  false为隐藏
		if (ck === true) {
			tabPrev.add(tabNext).show();
			limitArea.css('width', tabAreaWidth - 2 * tabButtonWidth);
		}
		else {
			tabPrev.add(tabNext).hide();
			limitArea.css('width', tabAreaWidth);
			ul.css('marginLeft', 0);
		}
	};
	
	setActiveTab = function(liElem) {			//激活选项卡，liElem为选项卡元素
		var id = liElem.attr('id'),
			index = id.indexOf('-'),
			subId = id.substring(index + 1),
			page = $('#TabPageContent-' + subId);
		
		if (currentTab == null) {
			liElem.addClass('selected');
			currentTab = liElem;
			currentContent = page;
		}
		else {
			if (currentTab.attr('id') == id) {
				return;
			}
			else {			
				currentTab.removeClass();
				currentContent.hide();		
				liElem.addClass('selected');
				page.show();
				currentContent = page;
				currentTab = liElem;
			}
		}
		moveTab(liElem);
	};
	
	createTab = function(info) {
		/*
		 * 创建一个选项
		 * info
		 * {
		 * 		text: 'xxx',			选项卡标题
		 * 		id: 'xxx',				选项卡标识符
		 * 		href: 'xxx',			选项卡页面地址
		 * 		closeAble: true			选项卡是否可以关闭，默认为true,
		 * 		pageType: iframe/html	页面加载的方式 默认iframe形式
		 * }
		 */
		
		
		var ta = $('#TabPage-' + info.id);
		if (ta.length) {
			setActiveTab(ta);
			return;
		}
		var li = liTemplate.clone().attr('id', 'TabPage-' + info.id),
			page = pageContentTemplate.clone().attr('id', 'TabPageContent-' + info.id);
		li.append(spanLeftTemplate.clone()).append(spanTextTemplate.clone().html(info.text));
		if (info.closeAble !== false) {
			li.append(spanCloseTemplate.clone());
		}
		li.append(spanRightTemplate.clone());
		ul.append(li);
		if (currentContent) {
			currentContent.hide();
		}

		if (info.pageType === 'html') {
			$.get(info.href + '?date=' + new Date(), function(data) {
				data = '<input type="hidden" name="tabPageId" value="' + info.id + '"/>' + data;			//设置页面模块的标识符
				page.html(data);
			}, 'html');
		}
		else {
			var iframe = iframeTemplate.clone().attr('src', info.href);
			iframe.css('height', contentArea.outerHeight() - 5);
			page.append(iframe);
		}
		page.appendTo(contentArea);
		totalWidth += li.outerWidth(true);
		if (totalWidth > tabAreaWidth) {
			changeTabButtonStatus(true);
		}
		setActiveTab(li);
	};
	
	
	
	this.addTab = createTab;			//添加一个选项卡，参数详见createTab方法
	
	this.resize = function() {
		tabAreaWidth = tabArea.innerWidth();
		if (totalWidth > tabAreaWidth) {
			changeTabButtonStatus(true);
		}
		else {
			changeTabButtonStatus(false);
		}
		contentArea.find('iframe').css('height', contentArea.outerHeight() - 5);
	};
}