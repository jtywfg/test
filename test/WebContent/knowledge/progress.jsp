<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进度详情</title>
<%@ include file="/layout/default.jsp" %>
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/progress.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="jdxqArea">
			<input type="text" id="jdxq-school"/>
			<label>教师姓名：</label>
			<input type="text" id="jdxq-teacher"/>
			<input type="button" class="commonBtn" value="查询" id="xq-search"/>
			<input type="button" class="commonBtn" value="返回" id="back"/>
		</div>
		<div id="jdxq-grid"></div>
	</div>
    
</body>
</html>