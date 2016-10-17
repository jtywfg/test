<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/user.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="userArea">
			<input type="button" class="commonBtn" value="新增用户" id="user-add"/>
			<input type="button" class="commonBtn" value="修改用户" id="user-edit"/>
			<input type="button" class="commonBtn" value="密码重置" id="user-pwd"/>
			<input type="button" class="commonBtn" value="删除用户" id="user-del"/>
			<label>用户名：</label>
			<input type="text" id="user-userName"/>
			<label>姓名：</label>
			<input type="text" id="user-realName"/>
			<input type="button" class="commonBtn" value="查询" id="user-search"/>
			<input type="button" style="display:none;" class="commonBtn" value="重置" id="user-reset"/>
		</div>
		<div id="user-grid"></div>
	</div>
	
	<div id="user-infoWin">
		<form id="userForm" method="post">
			<table  class="baseTable">
				<colgroup>
					<col width="110"/>
				</colgroup>
			    <tr>
					<td><label>用户名：</label></td>
					<td><input type="text" id="userName" class="text" name="userName" /></td>
				</tr>
				<tr id="user-spLine">
					<td><label>用户密码：</label></td>
					<td><input type="password" id="passWord" class="text" name="passWord" /></td>
				</tr>
				<tr>
					<td><label>姓名：</label></td>
					<td><input type="text" id="realName" name="realName" class="text"/></td>
				</tr>
				<tr>
					<td><label>角色：</label></td>
					<td><input type="text" id="roleAdminId" name="roleAdminId" /></td>
				</tr>
			</table>
			<input type="hidden" name="id"/>
		</form>
	</div>
</body>
</html>