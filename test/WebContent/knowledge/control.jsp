<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>登入报表</title>
	<%@ include file="/layout/default.jsp" %>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/knowledge/control.css" />
	<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/control.js"></script>
</head>
<body>
	<div class="baseWrap">
		<div id="btnArea" class="baseArea">
			<input type="text" id="gradeTypeCode" name="gradeTypeCode"/>
			<input type="text" id="courseCode" name="courseCode"/>
			<input type="text" id="courseCategoryId" name="courseCategoryId"/>
			<input type="button" class="commonBtn" id="search" value="刷新"/>
			<input type="button" class="commonBtn" id="addNode" value="添加节点"/>
			<input type="button" class="commonBtn" id="importPoint" value="导入知识点"/>
			<!-- <input type="button" class="commonBtn" id="reset" value="重置"/> -->
			<!-- <input type="button" class="commonBtn" id="import" value="导入"/> -->
		</div>
		<div id="list"></div>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div data-options="iconCls:'icon-add'" id="addChild" class="btn1">添加子节点</div>
		<div data-options="iconCls:'icon-add'" id="addBeforeChild" class="btn1">向前添加节点</div>
		<div data-options="iconCls:'icon-add'" id="addAfterChild" class="btn1">向后添加节点</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-cut'" id="moveUp">向上移动</div>
		<div data-options="iconCls:'icon-cut'" id="moveDown">向后移动</div>
		<div class="menu-sep"></div>
		<div data-options="iconCls:'icon-remove'" id="deleteNode">删除</div>
		<div data-options="iconCls:'icon-edit'" id="rename" class="btn1">重命名</div>
	</div>
	<div id="win">
		<p id="box">
			<label>知识点名称：</label>
			<input type="text" id="knowPoint" name="knowPoint" maxlength="100"/>
		</p>
		<div style="text-align:center;">
			<input type="button" class="commonBtn" value="确定" id="sureBtn"/>
			<input type="button" class="commonBtn" value="取消" id="cancelBtn"/>
		</div>
	</div>
	<div id="importWin">
		<form id="pointForm" enctype="multipart/form-data" method="post">
			<p id="box">
				<label>知识点：</label>
				<input type="text" id="pointFile" name="file" maxlength="100"/>
			</p>
			<div style="text-align:center;">
				<input type="button" class="commonBtn" value="确定" id="sureBtn2"/>
				<input type="button" class="commonBtn" value="取消" id="cancelBtn2"/>
			</div>
		</form>
	</div>
</body>
</html>