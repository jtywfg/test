<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/client/permissions.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="permissionsArea">
			<input type="button" class="commonBtn" value="新增权限" id="permissions-add"/>
			<input type="button" class="commonBtn" value="修改权限" id="permissions-edit"/>
			<input type="button" class="commonBtn" value="删除权限" id="permissions-del"/>
			<label>权限名称：</label>
			<input type="text" id="per-permissionsName"/>
			<input type="button" class="commonBtn" value="查询" id="permissions-search"/>
			<input type="button" style="display:none;" class="commonBtn" value="重置" id="permissions-reset"/>
		</div>
		<div id="permissions-grid"></div>
	</div>
	
	<div id="permissions-infoWin">
		<form id="permissionsForm" method="post">
			<table  class="baseTable">
				<colgroup>
					<col width="110"/>
				</colgroup>
			    <tr>
					<td><label>权限名称：</label></td>
					<td><input type="text" id="permissionsName" class="text" name="permissionsName" /></td>
				</tr>
				<tr>
					<td><label>方法名：</label></td>
					<td><input type="text" id="functionName" class="text" name="functionName" /></td>
				</tr>
				<tr>
					<td><label>功能地址：</label></td>
					<td><input type="text" id="url" class="text" name="url" /></td>
				</tr>
				<tr>
					<td><label>图标地址：</label></td>
					<td><input type="text" id="imageUrl" class="text" name="imageUrl" /></td>
				</tr>
				<tr>
					<td><label>菜单：</label></td>
					<td><input type="text" id="menuId" class="text" name="menuId" /></td>
				</tr>
				<tr>
					<td><label>排序序号：</label></td>
					<td><input type="text" id="sortNum" name="sortNum" class="text"/></td>
				</tr>
				<tr>
					<td><label>备注：</label></td>
					<td><input type="text" id="remark" name="remark" class="text"/></td>
				</tr>
			</table>
			<input type="hidden" name="id"/>
		</form>
	</div>
</body>
</html>