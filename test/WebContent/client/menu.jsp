<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/knowledge/control.css" />
<script type="text/javascript" src="<%=basePath%>/static/js/page/client/menu.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="menuArea">
			<input type="button" class="commonBtn" value="新增顶级菜单" id="menu-add"/>
		</div>
		<div id="menu-grid"></div>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div data-options="iconCls:'icon-add'" id="addChild" class="btn1">添加子节点</div>
		<div data-options="iconCls:'icon-add'" id="addBeforeChild" class="btn1">向前添加节点</div>
		<div data-options="iconCls:'icon-add'" id="addAfterChild" class="btn1">向后添加节点</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-cut'" id="moveUp">向上移动</div>
		<div data-options="iconCls:'icon-cut'" id="moveDown">向后移动</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-edit'" id="edit" class="btn1">修改</div>
		<div data-options="iconCls:'icon-edit'" id="status" class="btn1">启用/禁用</div>
		<div data-options="iconCls:'icon-remove'" id="deleteNode">删除</div>
	</div>
	<div id="menu-infoWin">
		<form id="menuForm" method="post">
			<table  class="baseTable">
				<colgroup>
					<col width="110"/>
				</colgroup>
			    <tr>
					<td><label>菜单名称：</label></td>
					<td><input type="text" id="menuName" class="text" name="menuName" /></td>
				</tr>
				<tr>
					<td><label>菜单标志：</label></td>
					<td><input type="text" id="menuFlag" name="menuFlag" class="text"/></td>
				</tr>
				<tr>
					<td><label>菜单地址：</label></td>
					<td><input type="text" id="menuUrl" name="menuUrl" class="text"/></td>
				</tr>
				<tr>
					<td><label>排序序号：</label></td>
					<td><input type="text" id="sortNum" name="sortNum" class="text"/></td>
				</tr>
				<tr>
					<td><label>大图标：</label></td>
					<td><input type="text" id="bigImageUrl" name="bigImageUrl" class="text"/></td>
				</tr>
				<tr>
					<td><label>小图标：</label></td>
					<td><input type="text" id="smallImageUrl" name="smallImageUrl" class="text"/></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td><input type="text" id="remark" name="remark" class="text"/></td>
				</tr>
				<tr>
					<td><label>启用/禁用：</label></td>
					<td><input type="text" id="useFlag" class="text" name="useFlag" /></td>
				</tr>
			</table>
			<input type="hidden" name="id" id="id"/>
		</form>
	</div>
</body>
</html>