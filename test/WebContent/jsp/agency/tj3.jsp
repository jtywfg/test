<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/layout/default.jsp" %>
	</head>
	<body>
		<div class="tjWrap">
			<ul id="tree_article" class="easyui-tree"
					data-options="url:'../../report/findUserList3',state:'closed',method:'get',animate:true">
			</ul>
		
		</div> 
		
<!-- <div  id="result">
 </div>
 <div class="zTreeDemoBackground left">
  <ul id="treeDemo" class="ztree"></ul>
 </div> -->
		<script type="text/javascript" src="<%=basePath%>static/js/page/agency/tj2.js?v=<%=version%>"></script>
	</body>
</html>
