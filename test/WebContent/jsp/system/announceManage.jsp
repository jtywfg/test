<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公告管理</title>
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
			<div class="baseArea" id="announceManage">
				<div class="baseArea datagrid-toolbar" id="catalogArea">
					<input type="button" class="commonBtn" value="新增公告" id="addAnnounce">
				</div>
				<table id="grid_announceManage" class="easyui-datagrid"
						data-options="method:'get',singleSelect:true,pagination:true,width: '100%',url:'../../announce/findAnnounce',rownumbers:true,toolbar: '#tb'">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true"></th>
							<th data-options="field:'title'">标题</th>
							<th data-options="field:'createTime',align:'center'">发布时间</th>
							<th data-options="field:'actn',align:'center', formatter:fmtActn">操作</th>
						</tr>
					</thead>
				</table>
				<div id="win_announceManage" class="easyui-window"
						data-options="resizable:false,closed:true,minimizable:false,maximizable:false,draggable:false,modal:true,collapsible:false" 
						style="width:610px;padding:10px">
						<form class="easyui-form" id="form_announceManage" method="post" data-options="">
							<table style="width:580px;margin:0 auto">
								<tr>
									<td>标题</td>
									<td>
										<input class="easyui-textbox" name="title" id="title"
												data-options="required:true,width:500,validType:'length[2,100]'" style="height:32px;" />
										<input type="hidden" name="id" id="id" />
									</td>
								</tr>
								<tr>
									<td>内容</td>
									<td>
										<input class="easyui-textbox" name="content" id="content"
												data-options="multiline:true,width:500,height:400,required:true,validType:'length[5,1000]'" />
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
		<script type="text/javascript" src="<%=basePath%>static/js/page/system/announceManage.js?v=<%=version%>"></script>
	</body>
</html>
