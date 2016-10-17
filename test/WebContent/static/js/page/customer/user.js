$(document).ready(function() {
//	$.parser.parse('#user');
	
	$('#user').on('click', 'table.datagrid-btable a[group="_oper_del"]', function(e) {
		var data = $('#grid_user').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		del(data[i].id);
	});
	$('#user').on('click', 'table.datagrid-btable a[group="_oper_freeze"]', function(e) {
		var data = $('#grid_user').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		freeze(data[i].id, data[i].Password);
	});
	$('#user').on('click', 'table.datagrid-btable a[group="_oper_edit"]', function(e) {
		var data = $('#grid_user').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openWin(data[i].id);
	});
	$('#user').on('click', 'table.datagrid-btable a[group="_oper_car"]', function(e) {
		var data = $('#grid_user').datagrid("getData").rows;
		var i = $(this).parent().parent().parent().attr('datagrid-row-index');
		openCarWin(data[i].id);
		$('#currUserId').val(data[i].id);
	});
	
	$('#addUser').on('click', function(e) {
		$('#win_user').window("open");
	});
	
	$('#btn_ok1').bind('click', function() {
		
		if($('#form_user').form("validate")) {
			$.messager.confirm('提示', '是否确定?', function(r) {
				if(r) {
					$.messager.progress();	// 显示进度条
					//提交表单
					$('#form_user').form('submit', {
							success: function() {
								$('#form_user').form('clear');
								$.messager.progress('close');	// 如果提交成功则隐藏进度条
								$('#win_user').window('close');
								$('#grid_user').datagrid("reload");
							}
					});
				}
			});
		}
	});
	
	$('#btn_cancel1').bind('click', function() {
		$('#form_user').form('clear');
		$('#win_user').window('close');
	});
	
	$('#searchuser').bind('click', function() {
		$('#grid_user').datagrid({
			queryParams: {
				minDate: $('#minDate').datebox('getValue'),
				maxDate: $('#maxDate').datebox('getValue'),
				telphone: $('#telphone').numberbox('getValue'),
				cardNum: $('#cardNum').numberbox('getValue'),
				drivingLicence: $('#drivingLicence').numberbox('getValue'),
				userName : $('#userName').textbox('getValue'),
				realName : $('#realName').textbox('getValue')
			}
		});
	});
	
});

function del(id) {
	$.messager.confirm('提示', '是否删除该用户?', function(r) {
		if(r) {
			$.ajax({
				url : "../../user/deleteUser?id=" + id,
				type : "get",
				dataType : "json",
				success : function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
						$('#grid_user').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

//车辆管理--行编辑功能--开始
var editRow = undefined;//定义全局变量：当前编辑的行

function addCar() {
	if (editRow != undefined) {
		$('#grid_car').datagrid("endEdit", editRow);
    }
    //添加时如果没有正在编辑的行，则在datagrid的第一行插入一行
    if (editRow == undefined) {
    	$('#grid_car').datagrid("insertRow", {
            index: 0, // index start with 0
            row: {userId : $('#currUserId').val()
            }
        });
        //将新插入的那一行开户编辑状态
    	$('#grid_car').datagrid("beginEdit", 0);
        //给当前编辑的行赋值
        editRow = 0;
    }
}

function delCar() {
	//删除时先获取选择行
    var rows = $('#grid_car').datagrid("getSelections");
    //选择要删除的行
    if (rows.length > 0) {
        $.messager.confirm("提示", "你确定要删除吗?", function (r) {
            if (r) {
            	$.getJSON('../../userCar/deleteUserCar?id=' + rows[0].id, function(json) {
        			if (json.success === 1) {
        				$.messager.alert('提示', json.message, 'info');
        				reload();
        			} else {
        				$.messager.alert('错误', json.message, 'error');
        			}
        		}
        	);
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的行", "error");
    }
}

function editCar() {
	//修改时要获取选择到的行
    var rows = $('#grid_car').datagrid("getSelections");
    //如果只选择了一行则可以进行修改，否则不操作
    if (rows.length == 1) {
        //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
        if (editRow != undefined) {
        	$('#grid_car').datagrid("endEdit", editRow);
        }
        //当无编辑行时
        if (editRow == undefined) {
            //获取到当前选择行的下标
            var index = $('#grid_car').datagrid("getRowIndex", rows[0]);
            //开启编辑
            $('#grid_car').datagrid("beginEdit", index);
            //把当前开启编辑的行赋值给全局变量editRow
            editRow = index;
            //当开启了当前选择行的编辑状态之后，
            //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
            $('#grid_car').datagrid("unselectAll");
        }
    }
}
function saveCar() {
	$('#grid_car').datagrid("endEdit", editRow);
}

function reject() {
	editRow = undefined;
	$('#grid_car').datagrid('rejectChanges');
	$('#grid_car').datagrid("unselectAll");
}

function onAfterEdit(rowIndex, rowData, changes) {
    //endEdit该方法触发此事件
    console.info(rowData);
//    以当前行发送请求
    if(rowData.id == undefined) {//添加
    	$.post("../../userCar/addUserCar"
    			, {userId: $('#currUserId').val(), carNum : rowData.carNum, vehicleLicence : rowData.vehicleLicence}
    			, function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
    			}
    			, "json");
    } else {//修改
    	$.post("../../userCar/updateUserCar"
    			, {id : rowData.id, carNum : rowData.carNum, vehicleLicence : rowData.vehicleLicence}
    			, function(json) {
					if (json.success === 1) {
						$.messager.alert('提示', json.message, 'info');
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
    			}
    			, "json");
    }
    editRow = undefined;
}

function onDblClickRow(rowIndex, rowData) {
	//双击开启编辑行
	if (editRow != undefined) {
		$('#grid_car').datagrid("endEdit", editRow);
	}
	if (editRow == undefined) {
		$('#grid_car').datagrid("beginEdit", rowIndex);
		editRow = rowIndex;
	}
}

function reload() {
	$.getJSON('../../userCar/findUserCar2?userId=' + $('#currUserId').val()
			, function(json) {
				if (json.success === 1) {
					if(json.rows == undefined) {
						json.rows = [];
					}
					$('#grid_car').datagrid("loadData", json);
				} else {
					$.messager.alert('错误', json.message, 'error');
				}
			}
	);
}

//车辆管理--行编辑功能--结束

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
						$('#grid_user').datagrid("reload");
					} else {
						$.messager.alert('错误', json.message, 'error');
					}
				}
			});
		}
	});
}

function openWin(id) {
	$.getJSON('../../user/findUser?id=' + id
			, function(json) {
				if (json.success === 1) {
					$('#form_user').form('load', json.object);
				} else {
					$.messager.alert('错误', json.message, 'error');
				}
			}
	);
	$('#win_user').window("open");
}
//加载车辆列表
function openCarWin(id) {
	$.getJSON('../../userCar/findUserCar2?userId=' + id
			, function(json) {
				if (json.success === 1) {
					if(json.rows == undefined) {
						json.rows = [];
					}
					$('#grid_car').datagrid("loadData", json);
				} else {
					$.messager.alert('错误', json.message, 'error');
				}
			}
	);
	reject();
	$('#win_car').window("open");
}

function fmtSex(val, row, index) {
	if(val == 1) {
		return '男';
	} else {
		return '女';
	}
}
function fmtUserType(val, row, index) {
	if(val == 1) {
		return '管理员';
	} else {
		return '普通用户';
	}
}

function fmtActn(val, row, index) {
	var _str = '';
	_str = 				'<a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-no" group="_oper_del"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">删除</span><span class="l-btn-icon icon-no">&nbsp;</span></span></a>';
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-edit" group="_oper_edit"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">修改</span><span class="l-btn-icon icon-edit">&nbsp;</span></span></a>');
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="icon-help" group="_oper_freeze"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">禁用</span><span class="l-btn-icon icon-help">&nbsp;</span></span></a>');
	_str = _str.concat(' <a href="javascript:;" class="easyui-linkbutton l-btn l-btn-small" iconcls="" group="_oper_car"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">车辆管理</span><span class="l-btn-icon">&nbsp;</span></span></a>');
	return _str;
}