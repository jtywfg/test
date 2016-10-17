<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教师管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/school.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/js/page/school/teacher.js?v=<%=version%>"></script>
</head>
<body>

<div class="baseWrap">
		<div class="baseArea" id="schoolArea">
            <!-- 省 -->
            省:<input style="width:100px;" type="text" class="easyui-combobox searchProvince" id="" name="provinceId" />
            <!-- 市 -->
            市:<input style="width:100px;" type="text" class="easyui-combobox searchCity" id="" name="cityId" />
            <!-- 区 -->
            区:<input style="width:100px;" type="text" class="easyui-combobox searchAreas" id="" name="areasId" />
            <!-- 学校 -->
            学校:<input style="width:100px;" type="text" class="easyui-combobox searchSchool" id="" name="schoolId" />
            <!-- 科目 -->
            科目:<input style="width:100px;" type="text" class="easyui-combobox searchCourse" id="" name="courseId" />
            教师姓名:<input style="width:100px;" type="text" class="easyui-textbox"  data-options="prompt:'教师姓名'" id="searchTchName" name="stuNameId">
            <input type="button" class="commonBtn" value="查  询" id="tec-search" />
            <input type="button" class="commonBtn" value="重  置" id="tec-reset" />
   </div>
   <div id="dg"></div>
</div>
</body>
</html>