<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/control.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="loginArea">
			<input type="button" class="commonBtn gridControl" value="当前在线用户" id="currUser"/>
			<input type="button" class="commonBtn gridControl" value="历史在线用户" id="historyUser"/>
			<div id="queryArea" style="float: right;">
				<label>姓名：</label>
				<input type="text" id="name"/>
				<label>IP：</label>
				<input type="text" id="ip"/>
				<label>开始时间：</label>
				<input type="text" id="startDate"/>
				<label>结束时间：</label>
				<input type="text" id="endDate"/>
				<input type="button" class="commonBtn" value="查询" id="login-search"/>
				<input type="button" class="commonBtn" value="导出excel" id="login-excel" style="display:none;"/>
			</div>
		</div>
		<div id="login-grid"></div>
	</div>
</body>
</html>