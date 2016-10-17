<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/customer/user.css" type="text/css" />
		<style type="text/css">
			.datagrid-cell, .datagrid-cell-group,
			.datagrid-header-rownumber, .datagrid-cell-rownumber {
				padding: 6px 10px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="income">
				<div id="tb">
					
					开始日期
						<input class="easyui-datebox" name="startTime" id="startTime" />
					结束日期
						<input class="easyui-datebox" name="endTime" id="endTime" />
						用户名<input class="easyui-textbox" name="userName" id="userName" />
						姓名<input class="easyui-textbox" name="realName" id="realName" />
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchin">查询</a>	
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="exportin">导出报表</a>
				</div>
				<table id="grid_income" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,width: '98%',url:'../../report/income',rownumbers:true,toolbar: '#tb'">
					<thead>
						<tr>
							<th data-options="field:'userName',align:'center'">购买用户名</th>
							<th data-options="field:'realName',align:'center'">购买姓名</th>
							<th data-options="field:'orderCode',align:'center'">购买单号</th>
							<th data-options="field:'tjOrderCode',align:'center'">推荐订单号</th>
							<th data-options="field:'totalMoney',align:'right'">购买金额</th>
							<th data-options="field:'createTime',align:'center'">创建日期</th>
							<th data-options="field:'startTime',align:'center'">生效日期</th>
							<th data-options="field:'endTime',align:'center'">截止日期</th>
						</tr>
					</thead>
				</table>
				<!-- 
				pageSize:1,pageList:[1,5,10],
				<div id="win_drawList" class="easyui-window" title="修改客户" 
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:true,modal:true,collapsible:false" 
						style="width:550px;padding:10px">
				</div> -->
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/report/income.js?v=<%=version%>"></script>
	</body>
</html>
