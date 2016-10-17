<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="/static/css/customer/user.css" type="text/css" />
		<style type="text/css">
			.datagrid-cell,.datagrid-cell-group,
			.datagrid-header-rownumber,.datagrid-cell-rownumber {
				padding: 3px 15px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="verify">
				<div class="baseArea datagrid-toolbar" id="catalogArea"><!--
					<input type="button" class="commonBtn" value="添加客户" id="addUser">
				--></div>
				<table id="grid_verify" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,width: '100%',url:'../../fundaccount/findDrawList',rownumbers:true,toolbar: '#tb'">
						<thead>
							<tr>
								<th data-options="field:'id',checkbox:true"></th>
								<th data-options="field:'userName',align:'center'">用户名</th>
								<th data-options="field:'realName',align:'center'">姓名</th>
								<th data-options="field:'telphone',align:'center'">电话号码</th>
								<th data-options="field:'type',align:'center', formatter:fmtType">类型</th>
								<th data-options="field:'isCheck',align:'center', formatter:fmtCheck">审核状态</th>
								<th data-options="field:'returnAmount',align:'center'">金额</th>
								<th data-options="field:'agentLevel',align:'center'">代理等级</th>
								<th data-options="field:'returnRatio',align:'center'">返率</th>
								<th data-options="field:'createTime',align:'center'">创建时间</th>
								<th data-options="field:'lastUpdateTime',align:'center'">更新时间</th>
								<th data-options="field:'actn',align:'center',formatter:fmtActn">操作</th>
							</tr>
						</thead>
				</table>
				<div id="win_verify" class="easyui-window" title="提现审核" 
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:true,modal:true,collapsible:false" 
						style="width:250px;padding:10px">
					<div style="text-align:center;padding:5px">
						<input type="hidden" name="currId" id="currId" />
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">通过</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" id="btn_no1">不通过</a>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/agency/verify.js?v=<%=version%>"></script>
	</body>
</html>
