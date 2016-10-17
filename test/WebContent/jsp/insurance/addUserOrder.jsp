<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
	Map user = (Map)request.getSession().getAttribute("user");
%>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>资源目录</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/customer/user.css" type="text/css" />
		<style type="text/css">
			#form_order td{padding:3px}
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="addUserOrder">
				<form class="easyui-form" method="post" data-options="url:'../../userOrder/addUserOrder'" id="form_addUserOrder">
					<table style="width:550px;margin:0 auto">
						<tr>
							<td width="48">用户</td>
							<td>
								<select class="easyui-combobox" name="userId" id="userId" 
			    					data-options="url:'../../user/findAllUser',required:true,onChange:getUserCar,method:'get',panelHeight:340,editable:false" style="width:150px;">
			    				</select>
							</td>
							<td>总金额</td>
							<td>
								<input class="easyui-numberbox" name="totalMoney" id="totalMoney"
									data-options="required:true" />
							</td>
						</tr>
						<tr>
							<!-- <td width="48">订单编号</td>
							<td>
								<input class="easyui-textbox" name="orderCode" id="orderCode"
									data-options="required:true,validType:'length[2,10]'" />
							</td> -->
							<td>车辆</td>
							<td colspan="3">
								<select class="easyui-combobox" name="carId" id="carId"
									data-options="method:'get',required:true,editable:false" style="width:150px;">
								</select>
							</td>
						</tr>
						<tr>
							<td>开始日期</td>
							<td>
								<input class="easyui-datebox" 
									data-options="required:true" name="startTime" />
							</td>
							<td>结束日期</td>
							<td>
								<input class="easyui-datebox" name="endTime"
									data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td width="48">推荐人</td>
							<td>
								<select class="easyui-combobox" name="userId" id="userId" 
			    					data-options="url:'../../user/findAllUser',method:'get',onChange:getOrderCode,panelHeight:340,editable:false" style="width:150px;">
			    				</select>
							</td>
							<td>推荐单号</td>
							<td>
								<select class="easyui-combobox" data-options="method:'get',editable:false"
									id="tjOrderCode" name="tjOrderCode" style="width:250px;" >
								</select>
							</td>
						</tr>
					</table>
				</form>
				<div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="btn_cancel1">取消</a>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/insurance/addUserOrder.js?v=<%=version%>"></script>
  </body>
</html>