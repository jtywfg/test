<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考试管理</title>
<%@ include file="/layout/default.jsp" %>
<link href="<%=basePath%>/static/lib/datepicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=basePath%>/static/lib/datepicker/wdatepicker.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/exam.js?v=<%=version%>"></script>
<style type="text/css">
#exam-startDate,#exam-endDate{
    border-radius: 5px;
    padding: 4px;
    width: 100px;
    height: 12px;
    border-color:#d6d6d6;
}
</style>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="examArea">
			<label>创建时间：</label>
			<input type="text" id="exam-startDate" class="Wdate"/> -
			<input type="text" id="exam-endDate" class="Wdate"/>
			<label>考试名称：</label>
			<input type="text" id="exam-examName"/>
			<label>年级：</label>
			<input type="text" id="grade"/>
			<input type="button" class="commonBtn" value="查询" id="exam-search"/>
		</div>
		<div id="exam-grid"></div>
	</div>
	
</body>
</html>