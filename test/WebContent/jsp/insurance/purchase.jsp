<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>购买保险</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/insurance/user.css" type="text/css" />
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="purchase">
				<div id="tb">
					<form class="easyui-form" method="post" id="form_purchase">
						裸车价（单位：元）
							<input name="carMoney" id="carMoney"
									data-options="min:0.00,max:10000000,precision:2,required:true,missingMessage:'请输入数字'" />
						订单总金额（单位：元）
							<input class="easyui-numberbox" name="totalMoney" id="totalMoney"
									data-options="value:0.00,editable:false,precision:2,required:true" />
					</form>
				</div>
				<table id="grid_purchase" 
						data-options="method:'get',singleSelect:false,pagination:false,width: '100%',url:'../../product/getProduct',rownumbers:true,toolbar: '#tb'">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'productNo',align:'center',width:100">编码</th>
							<th data-options="field:'productName',align:'center',width:160">名称</th>
							<th data-options="field:'money',align:'right',width:100">单价</th>
							<th data-options="field:'rate',align:'right',width:80">比例</th>
							<th data-options="field:'price',align:'right',width:100">实际价格</th>
						</tr>
					</thead>
				</table>
				<div style="padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">提交订单</a>
				</div>
				<form name="topUpForm" id="aliPayForm" action="../../order/alipayapi" method="post" target="_blank">
			        <input type="hidden" name="orderId" id="orderId" />
			    </form>
				
			</div>
		</div>
		<script type="text/javascript" src="<%=basePath%>/static/js/page/insurance/purchase.js?v=<%=version%>"></script>
	</body>
</html>
