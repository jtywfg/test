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
				padding: 6px 15px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="cashback">
				<div class="baseArea datagrid-toolbar" id="catalogArea">
					<a href="../../report/exportAccount" class="easyui-linkbutton" iconCls="icon-ok" id="exports">导出报表</a>
				</div>
				<table id="grid_cashback" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,width: '98%',url:'../../report/returnReport',rownumbers:true,toolbar: '#tb'">
						<thead>
							<tr>
								<th data-options="field:'userName',align:'center'">用户名</th>
								<th data-options="field:'realName',align:'center'">姓名</th>
								<th data-options="field:'yijiCount',align:'center'">一级返金人数</th>
								<th data-options="field:'yijiAmount',align:'center'">一级返金金额</th>
								<th data-options="field:'erjiCount',align:'center'">二级返金人数</th>
								<th data-options="field:'erjiAmount',align:'center'">二级返金金额</th>
								<th data-options="field:'totalAmount',align:'center'">总返金金额</th>
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
		<script type="text/javascript" src="../../static/js/page/report/cashback.js?v=<%=version%>"></script>
	</body>
</html>
