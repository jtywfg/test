<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单管理</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/insurance/user.css" type="text/css" />
		<style type="text/css">
			.datagrid-cell,.datagrid-cell-group,
			.datagrid-header-rownumber,.datagrid-cell-rownumber {
				padding: 3px 15px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
			#form_order td{padding:3px}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="order">
				<table id="grid_order" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,width: '100%',url:'../../order/findOrder',rownumbers:true,toolbar: '#tb'">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'orderCode',align:'center'">编号</th>
							<th data-options="field:'RealName',align:'center'">客户姓名</th>
							<th data-options="field:'UserName',align:'center'">用户名</th>
							<th data-options="field:'createTime',align:'center'">下单时间</th>
							<th data-options="field:'totalMoney',align:'right'">金额</th>
							<th data-options="field:'actn',align:'center',width:200, formatter:fmtActn">操作</th>
						</tr>
					</thead>
				</table>
				<div id="win_order" class="easyui-window" title="添加客户" 
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:false,modal:true,collapsible:false" 
						style="width:270px;padding:10px">
				</div>
			</div>
		</div>
		<script type="text/javascript" src="<%=basePath%>/static/js/page/insurance/order.js?v=<%=version%>"></script>
	</body>
</html>
