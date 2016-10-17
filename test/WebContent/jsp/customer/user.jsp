<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>资源目录</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="../../static/css/customer/user.css" type="text/css" />
		<style type="text/css">
			.datagrid-cell,.datagrid-cell-group,
			.datagrid-header-rownumber,.datagrid-cell-rownumber {
				padding: 3px 10px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="user">
				<div id="tb0">
					姓名<input class="easyui-textbox" name="realName" id="realName" />
					用户名<input class="easyui-textbox" name="userName" id="userName" />
					手机号<input class="easyui-numberbox" name="telphone" id="telphone" />
					身份证号<input class="easyui-numberbox" name="cardNum" id="cardNum" />
					驾驶证号<input class="easyui-numberbox" name="drivingLicence" id="drivingLicence" />
					日期<input class="easyui-datebox" name="minDate" id="minDate" />-<input class="easyui-datebox" name="maxDate" id="maxDate" />
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchuser">查询</a>	
				</div>
				<table id="grid_user" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,url:'../../user/findUserList',rownumbers:true,toolbar: '#tb0'">
						<thead>
							<tr>
								<th data-options="field:'id',checkbox:true"></th>
								<th data-options="field:'realName',align:'center'">姓名</th>
								<th data-options="field:'balanceIntegral',align:'center'">积分</th>
								<th data-options="field:'userType',align:'center', formatter:fmtUserType">用户类型</th>
								<th data-options="field:'drivingLicence',align:'center'">驾驶证</th>
								<th data-options="field:'sex', align:'center',formatter:fmtSex">性别</th>
								<th data-options="field:'cardNum',align:'center'">身份证号</th>
								<th data-options="field:'bankAccount',align:'center'">开户行</th>
								<th data-options="field:'bankCard',align:'center'">银行卡号</th>
								<th data-options="field:'actn',align:'center', formatter:fmtActn">操作</th>
							</tr>
						</thead>
				</table>
				<div id="win_user" class="easyui-window" title="修改客户" 
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:true,modal:true,collapsible:false" 
						style="width:550px;padding:10px;">
					<form class="easyui-form" method="post" data-options="url:'../../user/updateUser'" id="form_user">
						<table style="width:500px;margin:0 auto">
							<tr>
								<td width="48">姓名</td>
								<td>
									<input class="easyui-textbox" name="realName" id="realName"
									 data-options="required:true,validType:'length[2,10]'" />
									 <input type="hidden" name="id" id="id" />
								</td>
								<td width="48">用户名</td>
								<td>
									<input class="easyui-textbox" name="userName" id="userName"
									 data-options="disabled:true,required:true,validType:'length[1,40]'" />
								</td>
							</tr>
							<tr>
								<td>手机号</td>
								<td>
									<input class="easyui-textbox" name="telphone" id="telphone"
									 data-options="required:true,validType:'length[11,11]'" />
								</td>
								<td>出生日期</td>
								<td>
									<input class="easyui-datebox" data-options="required:true" name="birthday" />
								</td>
							</tr>
							<tr>
								<td>银行</td>
								<td colspan="3">
									<input class="easyui-textbox" data-options="required:true,validType:'length[4,60]'" name="bank" />
								</td>
							</tr>
							<tr>
								<td>开户行</td>
								<td>
									<input class="easyui-textbox" data-options="required:true,validType:'length[3,60]'" name="bankAccount" />
								</td>
								<td>银行卡号</td>
								<td>
									<input class="easyui-textbox" data-options="required:true,validType:'length[16,19]'" name="bankCard" />
								</td>
							</tr>
							<tr>
								<td>身份证号</td>
								<td>
									<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="cardNum" />
								</td>
								<td>驾驶证号</td>
								<td>
									<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="drivingLicence" />
								</td>
							</tr>
							<tr>
								<td colspan="3">推荐人</td>
								<td>
									<input class="easyui-textbox" data-options="disabled:true" name="tjUserName" />
								</td>
							</tr>
						</table>
					</form>
					<div style="text-align:center;padding:5px">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="btn_cancel1">取消</a>
					</div>
				</div>
				<div id="win_car" class="easyui-window" title="车辆管理（双击某行修改,操作完成需要点击保存，一次只能保存当前操作的行）" 
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:true,modal:true,collapsible:false" 
						style="width:450px;height:350px;padding:5px;">
						<div id="tb" style="height:auto">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addCar()">添加</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="delCar()">删除</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editCar()">修改</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveCar()">保存</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
<!-- 							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="reload()">刷新</a>
 -->							<input type="hidden" id="currUserId" />
						</div>
						<table id="grid_car" class="easyui-datagrid"
								data-options="resizable:true,singleSelect:true,rownumbers:true,width: '100%',toolbar:'#tb', onDblClickRow : onDblClickRow, onAfterEdit : onAfterEdit">
								<thead>
									<tr>
										<th data-options="field:'id',checkbox:true"></th>
										<th data-options="field:'carNum',align:'center',width:90,editor:'textbox',editor:{type:'textbox',options:{required:true}}">车牌号</th>
										<th data-options="field:'vehicleLicence',align:'center',width:160,editor:{type:'textbox',options:{required:true}}">行驶证号</th>
									</tr>
								</thead>
						</table>
					
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/customer/user.js?v=<%=version%>"></script>
	</body>
</html>
