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
				padding: 3px 15px;
				font-size: 13px;
				font-family: microsoft yahei;
				font-weight:500;
			}
		</style>
	</head>
	<body>
		<div class="tjWrap">
			<table title="三层树结构：[用户-->>自己购买-->>推荐购买]" class="easyui-treegrid" style="width:1010px;height:600px" 
			url="../../report/findUserList" method="get" rownumbers="true" idField="id" treeField="text">
			<thead>
				<tr>
					<!-- <th field="id" width="100">用户</th> -->
					<th field="text" width="250" align="left">姓名[购买保单]</th>
					<th field="carNum" width="250" align="left">用户名[牌号]</th>
					<th field="buyMoney" width="250" align="left">购买金额</th>
					<th field="createTime" width="198" align="left">下单日期</th>
				</tr>
			</thead>
			</table>
		
		</div> 
		
<!-- <div  id="result">
 </div>
 <div class="zTreeDemoBackground left">
  <ul id="treeDemo" class="ztree"></ul>
 </div> -->
		<script type="text/javascript" src="<%=basePath%>static/js/page/agency/tj.js?v=<%=version%>"></script>
	</body>
</html>
