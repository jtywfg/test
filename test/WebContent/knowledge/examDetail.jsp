<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考试管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/knowledge/examDetail.css?v=<%=version%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/lib/crGroup/style/crGroup.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/lib/crGroup/script/crGroup.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/examDetail.js?v=<%=version%>"></script>
</head>
<body>
	<div class="baseWrap">
		<div class="baseArea" id="ksglArea">
			<input type="button" class="commonBtn gridControl" value="学校" id="examSchool"/>
			<input type="button" class="commonBtn gridControl" value="考生" id="examStudent"/>
			<input type="button" class="commonBtn gridControl" value="阅卷教师" id="examTeacher"/>
			<input type="button" class="commonBtn gridControl" value="上传进度" id="upload"/>
			<input type="button" class="commonBtn gridControl" value="阅卷进度" id="marking"/>
			<input type="button" class="commonBtn gridControl" value="异常/仲裁" id="abnormal"/>
			<div id="queryArea" style="display:inline-block;"></div>
			<input type="button" class="commonBtn gridControl" value="返回" id="goBack"/>
		</div>
		<div id="ksgl-grid"></div>
	</div>
	<div id="school-infoWin">
		<label>请选择学校：</label><input type="text" id="school">
		<p class="crGroup checkbox" id="spCheck"><span class="btn"></span>全选</p>
		<input type="text" id="class" class='f-cb f-dn'>
    </div>
    <div id="schoolDetail">
		<div id="school-tool">
			<!-- 省 -->
                                省：<input style="width:100px;" type="text" class="easyui-combobox searchProvince" name="provinceId" />
            <!-- 市 -->
         	   市：<input style="width:100px;" type="text" class="easyui-combobox searchCity" name="cityId" />
            <!-- 区 -->
           	  区：<input style="width:100px;" type="text" class="easyui-combobox searchAreas" name="areasId" />
            <!-- 学校 -->
			 学校:<input style="width:120px;" type="text" class="easyui-textbox" id="add-schoolId" data-options="prompt:'请输入学校名称'" />
           	  <input type="button" class="commonBtn" value="查  询" id="school-add-search" />
           	   <input type="button" class="commonBtn" value="添 加" id="school-add-select" />
         </div>
         <div id="schoolList"></div>
     </div>
</body>
</html>