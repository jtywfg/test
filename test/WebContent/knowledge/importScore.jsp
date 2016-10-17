<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入学生成绩</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/school.css?v=<%=version%>" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/knowledge/importScore.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/js/page/knowledge/importScore.js?v=<%=version%>"></script>
</head>
<body>

<div class="baseWrap">
		<div class="baseArea" id="schoolArea">
        	请选择学校：<input type="button" class="" value="选择学校" id="school-check" />
        			 <span id="school-title"></span><br/><br/>
                              请选择考试：<input type="button" class="" value="选择考试" id="exam-check" />
                     <span id="exam-title"></span><br/><br/>
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
           	  学校:<input style="width:120px;" type="text" class="easyui-textbox" name="schoolId" id="schoolId" data-options="prompt:'请输入学校名称'" />
           	  <input type="button" class="commonBtn" value="查  询" id="school-search" />
           	  <input type="button" class="commonBtn" value="重  置" id="school-reset" />
         </div>
         <div id="schoolList"></div>
     </div>
     
	<div id='examDetail' class='f-cb f-dn'>
		<div id="tool">
			考试名称：<input style="width:300px;" type="text" class="easyui-textbox" data-options="prompt:'请输入考试名称'" id="examName" name="examName">
			<input type="button" class="commonBtn" value="查  询" id="exam-search" />
		</div>
		<div id="examList"></div>
	</div>
	<div id="countWin" class='f-cb f-dn'></div>
</div>

</body>
</html>