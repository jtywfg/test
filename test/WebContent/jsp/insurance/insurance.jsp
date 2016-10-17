<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>险种管理</title>
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
		</style>
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="insurance">
				<div class="baseArea datagrid-toolbar" id="catalogArea">
					<input type="button" class="commonBtn" value="添加险种" id="addInsurance">
				</div>
				<table id="grid_insurance" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:false,width: '100%',url:'../../product/getProduct',rownumbers:true,toolbar: '#tb'">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'productNo',align:'center',width:100">编码</th>
							<th data-options="field:'productName',align:'center',width:160">名称</th>
							<th data-options="field:'money',align:'right',width:100">单价</th>
							<th data-options="field:'rate',align:'right',width:80">比例</th>
							<th data-options="field:'actn',align:'center', formatter:fmtActn">操作</th>
						</tr>
					</thead>
				</table>
				<div id="win_insurance" class="easyui-window"
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:false,modal:true,collapsible:false" 
						style="width:270px;padding:10px">
						<form class="easyui-form" id="form_insurance" method="post"
								data-options="">
							<table style="width:240px;margin:0 auto">
								<tr>
									<td>编码</td>
									<td>
										<input class="easyui-textbox" name="productNo" id="productNo"
												data-options="required:true,validType:'length[2,10]'" style="height:32px;" />
										<input type="hidden" name="id" id="id" />
									</td>
								</tr>
								<tr>
									<td>名称</td>
									<td>
										<input class="easyui-textbox" name="productName" id="productName"
												data-options="required:true,validType:'length[1,40]'" style="height:32px;" />
									</td>
								</tr>
								<tr>
									<td>计费类型</td>
									<td>
										<select data-options="value:'0',panelHeight:55,required:true,editable:false" id="isRate" name="isRate" style="width:80px;height:32px;">
											<option value="0">按单价</option>
											<option value="1">按比例</option>
										</select>
									</td>
								</tr>
								<tr id="tr_money">
									<td>单价</td>
									<td>
										<input class="easyui-numberbox" name="money"
												data-options="min:0,precision:2" style="height:32px;" />  
									</td>
								</tr>
								<tr id="tr_rate">
									<td>比例</td>
									<td>
										<input class="easyui-numberbox" name="rate"
												data-options="min:0,max:1,precision:4,missingMessage:'必须填写0~1之间的数字'" style="height:32px;" />
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
		</div>
		<script type="text/javascript" src="<%=basePath%>static/js/page/insurance/insurance.js?v=<%=version%>"></script>
	</body>
</html>
