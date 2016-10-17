<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/system.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/js/loadTree.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/page/role.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="roleArea">
			<input type="button" class="commonBtn" value="新增角色" id="role-add"/>
			<input type="button" class="commonBtn" value="配置权限" id="role-menu"/>
			<input type="button" class="commonBtn" value="修改角色" id="role-edit"/>
			<input type="button" class="commonBtn" value="删除角色" id="role-del"/>
			<label>角色名称：</label>
			<input type="text" id="role-roleName"/>
			<input type="button" class="commonBtn" value="查询" id="role-search"/>
			<input type="button" style="display:none;" class="commonBtn" value="重置" id="role-reset"/>
		</div>
		<div id="role-grid"></div>
	</div>
	
	<div id="role-infoWin">
		<div id="roleDiv">
			<form id="roleForm" method="post">
				<div id="roleTable">
					<table  class="baseTable" style="margin-top: 20px;">
						    <tr>
								<td><label>角色名称：</label></td>
								<td><input type="text" id="roleName" class="text" name="roleName" /></td>
							</tr>
						</table>
						<input type="hidden" name="menus" id="menus">
						<input type="hidden" name="id" id="roleId">
				</div>
			</form>
		</div>
	</div>
	
	<div id="role-menuWin">
		<div id="moduleTree">
		</div>
	</div>
</body>
</html>