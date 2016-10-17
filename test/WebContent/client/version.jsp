<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>

<%@ include file="/layout/default_top.jsp"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>版本管理</title>
<%@ include file="/layout/default.jsp"%>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/static/css/system.css?v=<%=version%>" />
<script type="text/javascript"
	src="<%=basePath%>/static/js/loadTree.js?v=<%=version%>"></script>
<script type="text/javascript"
	src="<%=basePath%>/static/js/page/client/version.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div id="versionArea" class="baseArea">
			<form id="versionForm" enctype="multipart/form-data" method="post"
				style="display:inline;">
				<label>版本号：</label></td>
				<input type="text" id="versionNo" name="versionNum"/>
				<label>zip文件：</label>
				<input id="zip" name="zip" type="text"/>
				<label>exe文件：</label>
				<input id="versionFile" name="softPackage" type="text"/>
				<input type="button" id="versionSub" value="上传" class="commonBtn" />
			</form>
			
			<input type="button" class="commonBtn" value="下载"
				id="versionDownload" />
			 <input type="button" class="commonBtn"
				value="删除" id="versionDelete" />
		</div>
		<div id="versionGrid"></div>
	</div>

	<div id="version-infoWin">
		<form id="versionForm" method="post">
			<table class="baseTable">
				<colgroup>
					<col width="110" />
				</colgroup>
				<tr>
					<td><label>版本号：</label></td>
					<td><input type="text" id="versionNum" class="text"
						name="versionNum" /></td>
				</tr>
				<tr>
					<td><label>文件大小：</label></td>
					<td><input type="text" id="fileSize" class="text"
						name="fileSize" /></td>
				</tr>
				<tr>
					<td><label>上传时间：</label></td>
					<td><input type="text" id="uploadTime" class="text"
						name="uploadTime" /></td>
				</tr>
			</table>
			<input type="hidden" name="id" />
		</form>
	</div>
</body>
</html>
