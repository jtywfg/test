<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/dictionary.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="dictionaryArea">
			<input type="button" class="commonBtn" value="新增字典" id="dictionary-add"/>
			<input type="button" class="commonBtn" value="修改字典" id="dictionary-edit"/>
			<input type="button" class="commonBtn" value="启用/禁用" id="dictionary-del"/>
			<label>名称：</label>
			<input type="text" id="dic-dicName"/>
			<label>类型：</label>
			<input type="text" id="dic-dicType"/>
			<input type="button" class="commonBtn" value="查询" id="dic-search"/>
			<input type="button" class="commonBtn" value="重置" id="dic-reset"/>
		</div>
		<div id="dictionary-grid"></div>
	</div>
	
	<div id="dictionary-infoWin">
		<form id="dictionaryForm" method="post">
			<table  class="baseTable">
				<colgroup>
					<col width="110"/>
				</colgroup>
			    <tr>
					<td><label>字典名称：</label></td>
					<td><input type="text" id="name" class="text" name="name" /></td>
				</tr>
				<tr>
					<td><label>字典值：</label></td>
					<td><input type="text" id="value" class="text" name="value" /></td>
				</tr>
				<tr>
					<td><label>字典类型：</label></td>
					<td><input type="text" id="typeId" class="text" name="typeId" /></td>
				</tr>
				<tr>
					<td><label>排序序号：</label></td>
					<td><input type="text" id="sortNum" class="text" name="sortNum" /></td>
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