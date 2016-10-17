<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/nav.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="navArea">
			<input type="button" class="commonBtn" value="新增菜单" id="nav-add"/>
			<input type="button" class="commonBtn" value="修改菜单" id="nav-edit"/>
			<input type="button" class="commonBtn" value="删除菜单" id="nav-status"/>
			
		</div>
		<div id="nav-grid"></div>
	</div>
	
	<div id="nav-infoWin">
		<form id="navForm" method="post">
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
				<tr id="hideMenu">
					<td><label>上级菜单：</label></td>
					<td><input type="text" id="parentId" name="parentId" class="text"/>
					</td>
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
			</table>
			<input type="hidden" name="id"/>
		</form>
	</div>
</body>
</html>