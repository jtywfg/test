<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/dataType.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="typeArea">
			<input type="button" class="commonBtn" value="新增类型" id="type-add"/>
			<input type="button" class="commonBtn" value="修改类型" id="type-edit"/>
			<input type="button" class="commonBtn" value="启用/禁用" id="type-del"/>
			<label>名称：</label>
			<input type="text" id="type-typeName"/>
			<input type="button" class="commonBtn" value="查询" id="type-search"/>
		</div>
		<div id="type-grid"></div>
	</div>
	
	<div id="type-infoWin">
		<form id="typeForm" method="post">
			<table  class="baseTable">
				<colgroup>
					<col width="110"/>
				</colgroup>
			    <tr>
					<td><label>类型名称：</label></td>
					<td><input type="text" id="typeName" class="text" name="typeName" /></td>
				</tr>
				<tr>
					<td><label>类型标志：</label></td>
					<td><input type="text" id="typeFlag" class="text" name="typeFlag" /></td>
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