$(document).ready(function() {
//	$.parser.parse('#purchase');
	
	$('#purchase').on('click', 'table.datagrid-btable a[group="_oper_del"]', function(e) {
		var data = $('#grid_purchase').datagrid("getRows");
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		del(data[i].id);
	});
	$('#purchase').on('click', 'table.datagrid-btable a[group="_oper_freeze"]', function(e) {
		var data = $('#grid_purchase').datagrid("getRows");
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		freeze(data[i].id, data[i].Password);
	});
	$('#purchase').on('click', 'table.datagrid-btable a[group="_oper_edit"]', function(e) {
		var data = $('#grid_purchase').datagrid("getRows");
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openWin(data[i].id);
	});
	
	$('#grid_purchase').datagrid({
		onLoadSuccess : function(row) {
		}
		, onCheck : function(index, row) {
			$('#totalMoney').numberbox('setValue', parseFloat($('#totalMoney').numberbox('getValue')) + parseFloat(row.price));
		}
		, onUncheck : function(index, row) {
			$('#totalMoney').numberbox('setValue', parseFloat($('#totalMoney').numberbox('getValue')) - parseFloat(row.price));
		}
		, onUncheckAll : function(rows) {
			$('#totalMoney').numberbox('setValue', 0.00);
		}
		, onCheckAll : function(rows) {
			if(rows.length > 0) {
				var total = 0.00;
				for(var ii = 0; ii < rows.length; ii++) {
					total += parseFloat(rows[ii].price);
				}
				$('#totalMoney').numberbox('setValue', total);
			}
		}
	});
	
	$('#carMoney').numberbox({
		//获取每一行实际价格，并求和更新总价
		onChange: function(newValue, oldValue) {
			var rows = $('#grid_purchase').datagrid("getRows");
			var checked = $('#grid_purchase').datagrid("getChecked");
			var length = rows.length;
			console.info(checked.length);
			if(length > 0) {
				var total = 0.00;
				for(var i = 0; i < length; i++) {
					var isRate = rows[i].isRate;
					var realPrice = 0;
					if(isRate == 1) {//按比例
						realPrice = newValue * rows[i].rate;
					} else {//按单价
						realPrice = rows[i].money;
					}
					//更新表格的值
					$('#grid_purchase').datagrid('updateRow',{
						index: i,
						row: {
							price: realPrice.toFixed(2)
						}
					});
					if(checked.length > 0) {
						for(var j = 0; j < checked.length; j++) {
							if(checked[j].id == rows[i].id) {
								total += realPrice;
								$('#grid_purchase').datagrid('checkRow', i); 
								break;
							}
						}
					}
				}
				$('#totalMoney').numberbox('setValue', total.toFixed(2));
			}
		
		
		}
	});

	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_purchase').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					//提交表单, 先生成订单
					$.post('../../order/buyOrder'
							, {
								product : checkedIds('#grid_purchase', 'id'),
								totalMoney : $('#totalMoney').numberbox('getValue'),
								carMoney : $('#carMoney').numberbox('getValue')
							}
							, function(data) {
//								$('#form_purchase').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								if(data.success === 1) {
									$("#orderId").val(data.object.id);
									$.messager.confirm('提示', '是否现在付款?', function(r) {
										if(r) {
											document.topUpForm.submit();
										}
									});
								}
							}, "json");
				}
//				1 待支付 2 已支付3 已取消
			});
		}
	});
	
});

function del(id) {
	$.messager.confirm('提示', '是否删除该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../product/deleteProduct?id=" + id,
				type : "post",
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_purchase').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function freeze(id, Password) {
	$.messager.confirm('提示', '是否禁用该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../user/freezedUser",
				type : "get",
				data : {id : id
				, Password : Password},
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_purchase').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function openWin(id) {
	$.getJSON('../../product/findOne?id=' + id, function(json) {
			if (json.success === 1) {
				$('#form_purchase').form('load', json.object);
			} else {
				$.messager.alert('错误', json.message, 'error');
			}
		}
	);
}

function fmtSex(val, row, index) {
	if(val == 1) {
		return '男';
	} else {
		return '女';
	}
}

function fmtActn(val, row, index) {
	var _str = '';
	_str = 				'<a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-no" group="_oper_del"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">删除</span><span class="l-btn-icon icon-no">&nbsp;</span></span></a>';
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-edit" group="_oper_edit"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">修改</span><span class="l-btn-icon icon-edit">&nbsp;</span></span></a>');
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-help" group="_oper_freeze"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">禁用</span><span class="l-btn-icon icon-help">&nbsp;</span></span></a>');
	return _str;
}