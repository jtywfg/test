<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/customer/user.css" type="text/css" />
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="withdraw">
				<form class="easyui-form" method="post" data-options="url:'../../fundaccount/applyUsableMoney'" id="form_withdraw">
					<table style="width:220px;margin:100px auto">
						<tr>
							<td>可用金额</td>
							<td>
								<input class="easyui-numberbox" name="object" style="height:32px;"
								data-options="precision:2,editable:false,required:true,prefix:'￥  '" />
							</td>
						</tr>
						<tr>
							<td>申请提现</td>
							<td>
								<input name="drawMoney" id="drawMoney" style="height:32px;"
								data-options="min:0.00,precision:2,value:0.00,prefix:'￥  ',required:true,missingMessage:'提现金额不能大于可用金额'" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<script type="text/javascript" src="<%=basePath%>static/js/page/agency/withdraw.js?v=<%=version%>"></script>
  </body>
</html>
