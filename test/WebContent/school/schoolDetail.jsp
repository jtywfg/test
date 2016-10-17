<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学校用户管理</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/school/schoolDetail.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="xxxqArea">
			<input type="button" class="commonBtn gridControl" value="学生" id="student"/>
			<input type="button" class="commonBtn gridControl" value="教师" id="teacher"/>
			<div id="queryArea" style="display:inline-block;"></div>
			<input type="button" class="commonBtn gridControl" value="返回" id="goBack"/>
		</div>
		<div id="xxxq-grid"></div>
	</div>
</body>
</html>