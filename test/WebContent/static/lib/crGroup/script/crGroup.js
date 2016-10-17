(function($) {

		/**
		 * 创建复选框组、单选框组
		 * conf
		 * {
		 * 		textField: 'text' 						显示文字字段名，默认为text
		 * 		valueField: 'value'						值字段名，默认为value
		 * 		dataField: 'data'						异步获取数据的字段名，默认为data
		 * 		url: 'xx'								异步获取数据地址，与dataField配对，与data互斥
		 * 		data: [									数组数据，与url互斥
		 * 			{
		 * 				text: '选项1',
		 * 				value: '1',
		 * 				selected: true					表示选项是否被选中
		 * 			}
		 * 		]
		 * 		formatter: function(data) {}			文字内容格式化函数，默认为空，data为选项数据							
		 * 		type: 'checkbox'/'radio'				组件类型，默认为checkbox复选框组
		 * 		onClick: function(value, data, ck){}	点击相应选项触发的事件，value为选项值，data为选项的数据，ck为状态，true为选中
		 * 												默认为null
		 * 		onBeforeClick: function(value, data, ck){}	点击相应选项前触发的事件，如果返回false则阻止单击事件的触发，默认为null
		 * 		onSuccess: function(data, opts){}				组件初始化完成 data为数据，默认值为null， opts为配置项
		 * 		imgEnable: true/false					选项是否需要背景，默认值为false没有背景，true有背景
		 * 		autoEvent: true/false					初始化的时候是否需要触发事件，默认为true触发，false不触发
		 * }
		 *
		 * input   表单元素类型必须是text，而且带有name属性
		 */
		function CRGroup(conf, input) {

			var defaultOpts = {							//默认位置项
				textField: 'text',
				valueField: 'value',
				dataField: 'data',
				url: null,
				data: null,
				type: 'checkbox',
				imgEnable: false,
				formatter: false,
				autoEvent: true,
				onClick: null,
				onBeforeClick: null,
				onSuccess: null
			};

			var opts = $.extend({}, defaultOpts, conf);		//组合后的配置项
			var that = this;

			var con = $('<div>').addClass('crGroupCon');
			con.insertBefore(input);
			input.addClass('crGroupHide');


			if (typeof opts.url === 'string') {						//通过地址获取数据
				$.post(opts.url, function(data) {
					if (data.success === 1) {
						createList(con, data[opts.dataField], opts, input);
						if (typeof opts.onSuccess === 'function') {
							opts.onSuccess(data[opts.dataField], opts);
						}
					}
				}, 'json');
			}
			else if (Object.prototype.toString.call(opts.data) === '[object Array]') {		//直接通过数据获取数据
				createList(con, opts.data, opts, input);
				if (typeof opts.onSuccess === 'function') {
					opts.onSuccess(opts.data, opts);
				}
			}


			var fn = opts.onClick;
			if (opts.type === 'checkbox') {					//绑定复选框单击事件
				con.on('click', 'p', function() {
					if (that.status !== true) {
						return false;
					}
					var p = $(this),
						data = p.data('data');
					if (p.hasClass('selected')) {
						if (typeof opts.onBeforeClick === 'function') {
							var ck = opts.onBeforeClick(data[opts.valueField], data, true);
							if (ck === false) {
								return false;
							}
						}
						p.removeClass('selected');
						if (typeof fn === 'function') {
							fn(data[opts.valueField], data, false);
						}
					}
					else {
						if (typeof opts.onBeforeClick === 'function') {
							var ck = opts.onBeforeClick(data[opts.valueField], data, false);
							if (ck === false) {
								return false;
							}
						}
						p.addClass('selected');
						if (typeof fn === 'function') {
							fn(data[opts.valueField], data, true);
						}
					}
					var valArr = [],
						ps = con.find('p.selected');
					$.each(ps, function(index, val) {
						var data = ps.eq(index).data('data');
						valArr.push(data[opts.valueField]);
					});
					input.val(valArr.join(','));
				});
			}
			else if (opts.type === 'radio') {				//绑定单选框单击事件
				con.on('click', 'p', function() {
					if (that.status !== true) {
						return false;
					}
					var p = $(this),
						data = p.data('data');
					if (p.hasClass('selected')) {
						if (typeof fn === 'function') {
							fn(data[opts.valueField], data, true);
						}
						return false;
					}
					else {
						var selectedP = con.find('p.selected');
						if (selectedP.length) {
							var selectedData = selectedP.data('data');
							selectedP.removeClass('selected');
							if (typeof fn === 'function') {
								fn(selectedData[opts.valueField], selectedData, false);
							}
						}
						p.addClass('selected');
						var value = data[opts.valueField];
						input.val(value);
						if (typeof fn === 'function') {
							fn(value, data, true);
						}
						
					}
				});
			} 


			this.opts = opts;
			this.elems = {
				con: con,
				input: input
			};
			this.status = true;
		}

		CRGroup.prototype = {								//原型方法
			setData: function(data) {						//设置数据，data为选项的数据
				var elems = this.elems,
					con = elems.con,
					input = elems.input;
				con.find('p.crGroup').removeData('data').remove();
				input.val('');		
				if (data && data.length) {
					createList(con, data, this.opts, this.elems.input);
				}
			},
			destroy: function() {							//销毁方法
				var elems = this.elems,
					con = elems.con,
					input = elems.input;
				con.off('click', 'p');
				con.find('p.crGroup').removeData('data').remove();
				con.remove();
				input.removeClass('crGroupHide');
				input.removeData('ins');
				this.elems = null;
			},
			appendList: function(data) {					//添加选项，data为新增选项的数据
				if (data && data.length) {
					createList(this.elems.con, data, this.opts);
				}
			},
			disable: function() {							//禁用
				this.status = false;
			},
			enable: function() {							//弃用
				this.status = true;
			},
			getSelectedValue: function() {					//获取值
				return this.elems.input.val();
			},
			getSelectedText: function() {					//获取选中的文字
				var selected = this.elems.con.find('p.selected'),
					opts = this.opts;
				if (selected.length) {
					if (selected.length == 1) {
						return selected.data('data')[opts.textField];
					}
					else {
						var arr = [];
						$.each(selected, function(index, val) {
							arr.push(selected.eq(index).data('data')[opts.textField]);
						});
						return arr.join(',');
					}
				}
				
			},
			getSelectedLength: function() {					//获取选中元素的个数
				return this.elems.con.find('p.selected').length;
			},
			getSelectedData: function() {					//获取选中的数据
				return this.elems.con.find('p.selected').data('data');
			},
			unSelectAll: function() {						//取消选中的项
				var selectedP = this.elems.con.find('p.selected');
				if (this.opts.type == 'checkbox') {
					selectedP.click();
				}
				else {
					selectedP.removeClass('selected');
				}
				this.elems.input.val('');
			},
			unSelect: function(value) {						//取消某个选项
				var selectedP = this.elems.con.find('p.selected'),
					opts = this.opts;
				for (var i = 0, len = selectedP.length; i < len; i++) {
					var p = selectedP.eq(i),
						data = p.data('data');
					if (data[opts.valueField] == value) {
						p.removeClass('selected');
						if ($.isFunction(opts.onClick)) {
							opts.onClick(value, data, false);
						}
						return;
					}
				}
			},
			selectAll: function() {						//勾选所有选项
				var p = this.elems.con.find('p:not(".selected")');
				p.click();
			},
			setValue: function(value) {									//设置值   value可以是简单值类型数据，也可以是数组型数据
				if (Object.prototype.toString.call(value) !== '[object Array]') {
					var elems = this.elems,
						con = elems.con,
						ps = con.find('p'),
						opts = this.opts;
					for (var i = 0, len = ps.length; i < len; i++) {
						var ta = $(ps[i]),
							data = ta.data('data');
						if (data[opts.valueField] === value) {
							ta.click();
							return;
						}
					}
				}
				else {
					var elems = this.elems,
						con = elems.con,
						ps = con.find('p'),
						opts = this.opts;
					for (var i = 0, len = ps.length; i < len; i++) {
						var ta = $(ps[i]),
							data = ta.data('data');
						for (var j = 0, dLen = value.length; j < dLen; j++) {
							if (data[opts.valueField] === value[j]) {
								ta.click();
							}
						}
					}
				}
			},
			getCheckNums: function() {				//返回勾选项个数
				return this.elems.con.find('p').length;;
			},
			hideMenu: function() {					//菜单隐藏
				this.elems.con.hide();
			},
			showMenu: function() {
				this.elems.con.show();
			}
		};

		function createList(con, data, opts, input) {			//创建选项 con为容器  data为选项数据	opts为配置项  input为文本框
			var pTemplate = $('<p>').addClass('crGroup'),
				spanTemplate = $('<span>').addClass('btn'),
				fragment = document.createDocumentFragment(),
				textField = opts.textField,
				formatter = opts.formatter,
				selectedP = [];

			if (opts.type === 'checkbox') {
				pTemplate.addClass('checkbox');
			}
			else if (opts.type === 'radio') {
				pTemplate.addClass('radio');
			}
			$.each(data, function(index, val) {
				var p = pTemplate.clone();
				if (opts.imgEnable === true) {
					p.addClass('img');
				}
				if ($.isFunction(formatter)) {
					p.html(formatter(val)).prepend(spanTemplate.clone()).data('data', val);
				}
				else {
					p.html(val[textField]).prepend(spanTemplate.clone()).data('data', val);
				}
				if (val.selected === true) {
					//p.addClass('selected');
					selectedP.push(p);
				}
				fragment.appendChild(p[0]);
			});
			con[0].appendChild(fragment);
			if (opts.autoEvent) {
				setTimeout(function() {							//加延迟是为了防止不触发单击事件
					$.each(selectedP, function(index, val) {
						val.click();
					});
				}, 15);
			}
			else {
				$.each(selectedP, function(index, val) {
					val.addClass('selected');
				});
			}
		}

		$.fn.crGroup = function(conf, args) {
			var ins,
				triggerDom = $(this);
			if (typeof conf === 'object') {
				ins = triggerDom.data('ins');
				if (ins) {
					ins.destroy();
				}
				ins = new CRGroup(conf, triggerDom);
				triggerDom.data('ins', ins);
			}
			else if (typeof conf === 'string') {
				ins = triggerDom.data('ins');
				if (ins) {
					return ins[conf] && ins[conf](args);
				}
				return false;
			}
			return false;
		};

	}(jQuery));