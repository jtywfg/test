<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学生管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/school.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/js/page/school/student.js?v=<%=version%>"></script>
</head>
<body>

<div class="baseWrap">
		<div class="baseArea" id="schoolArea">
            <!-- 省 -->
            省:<input style="width:100px;" type="text" class="easyui-combobox searchProvince" name="provinceId" />
            <!-- 市 -->
            市:<input style="width:100px;" type="text" class="easyui-combobox searchCity" name="cityId" />
            <!-- 区 -->
            区:<input style="width:100px;" type="text" class="easyui-combobox searchAreas" name="areasId" />
            <!-- 学校 -->
            学校:<input style="width:100px;" type="text" class="easyui-combobox searchSchool" name="schoolId" />
            <!-- 年级 -->
            年级:<input style="width:100px;" type="text" class="easyui-combobox searchGrade" name="gradelId" />
            <!-- 班级 -->
            班级:<select class="easyui-combo searchClass" style="width:100px"></select>
            学生姓名:<input style="width:100px;" type="text" class="easyui-textbox" data-options="prompt:'学生姓名'" id="searchStuName" name="stuNameId">
            <input type="button" class="commonBtn" value="查  询" id="stu-search" />
   </div>
   <div id="dg"></div>
</div>

</body>
</html>