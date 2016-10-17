<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="/layout/default_top.jsp" %>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>学校管理</title>
<%@ include file="/layout/default.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/static/css/school.css?v=<%=version%>" />
<script type="text/javascript" src="<%=basePath%>/static/js/page/school/school.js?v=<%=version%>"></script>
</head>
<body>

<div class="baseWrap">
		<div class="baseArea" id="schoolArea">
        	<!-- 省 -->
            省:<input style="width:100px;" type="text" class="easyui-combobox searchProvince" name="provinceId" />
            <!-- 市 -->
            市:<input style="width:100px;" type="text" class="easyui-combobox searchCity" id="" name="cityId" />
            <!-- 区 -->
            区:<input style="width:100px;" type="text" class="easyui-combobox searchAreas" id="" name="areasId" />
            <!-- 状态 -->
            状态:<input style="width:100px;" type="text" class="easyui-combobox searchStatus" id="" name="statusId" />
            <!-- 是否到期 --><%--
            是否到期:<input style="width:100px;" type="text" class="easyui-combobox searchOutDateFlag" id="" name="outDateFlagId" />
            --%><!-- 学校名称 -->
            学校名称:<input style="width:150px;" type="text" class="easyui-textbox searchSchoolName" data-options="prompt:'学校名称'" id="" name="schoolName">
            <input type="button" class="commonBtn" value="查  询" id="school-search" />
            <input type="button" class="commonBtn" value="重  置" id="school-reset" />
            <div class="buttonArea">
            	<!-- 开通新学校 -->
            	<input type="button" class="commonBtn" value="开通新学校" id="school-add"/>
                <input type="button" class="commonBtn" value="编辑" id="school-edit"/>
                <%--<input type="button" class="commonBtn" value="续用" id="school-xu"/>--%>
                <input type="button" class="commonBtn" value="启用/禁用" id="school-state"/>
                <input type="button" class="commonBtn" value="密码重置" id="school-repass"/>
                <input type="button" class="commonBtn" value="导出excel" id="school-report"/>
                <input type="button" class="commonBtn" value="下载学校模板" id="school-model"/>
                <input type="button" class="commonBtn" value="导入学校" id="school-import"/>
              <!--   <input type="button" class="commonBtn" value="续费记录" id="school-xu-list"/> -->
            </div>
		</div>
	<div id="dg"></div>
</div>

<!-- 新增/修改 S -->
<div id="school-infoWin">
    <form id="schoolForm" method="post">
        <table  class="baseTable">
            <tr>
                <td><label>学校名称：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox schoolName text" name="schoolName" /></td>
                 <td><label>省份：</label></td>
                <td><input style="width:150px" id="" class="easyui-combobox  searchProvince" name="provinceCode" /></td>
            </tr>
            <tr>
                <td><label>城市：</label></td>
                <td><input style="width:150px" type="text" id="" name="cityCode" class="easyui-combobox  searchCity"/></td>
                 <td><label>县区：</label></td>
                <td><input style="width:150px" type="text" id="" name="areaCode" class="easyui-combobox  searchAreas" /></td>
            </tr>
            <tr>
                <td><label>联系人：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox linkName" id="" name="linkName" /></td>
                <td><label>联系电话：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox linkPhone" id="" name="linkPhone" /></td>
            </tr>
            <tr>
                <td><label>邮箱：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox email" id="" name="email" /></td>
                <td><label>用户名：</label></td>
                <td><input validtype="UN" style="width:150px" type="text" class="easyui-textbox userName" id="" name="userName" /></td>
            </tr>
            <%--<tr>
                <td class="hide-box"><label>开始日期：</label></td>
                <td class="hide-box"><input data-options="editable:false,showSeconds:false" required type="text" class="easyui-datebox startDate" id="startDate" name="startDateStr" style="width:150px" /></td>
                <td class="hide-box"><label>结束日期：</label></td>
                <td class="hide-box"><input data-options="editable:false,showSeconds:false" required type="text" class="easyui-datebox endDate" id="endDate" name="endDateStr" style="width:150px"/></td>
            </tr>
            --%><tr>
                <td><label>扫描仪信息：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox" id="scanner" name="scanner" /></td>
                <td><label>条形码打印机信息：</label></td>
                <td><input style="width:150px" type="text" class="easyui-textbox" id="barcodePrinter" name="barcodePrinter" /></td>
            </tr>
            <tr>
            	<td class="hide-box"><label>初始密码：</label></td>
                <td class="hide-box"><input style="width:150px" type="text" class="easyui-textbox initPassword" id="" name="initPassword" /></td>
            </tr>
        </table>
        <input type="hidden" name="id"/>
    </form>
</div>
<!-- 新增/修改 E -->

<!-- 续用 S -->
<div id="school-xuWin">
    <form id="schoolXuForm" method="post">
    	<input type="hidden" name="schoolId"/>
        <table  class="baseTable">
            <tr>
                 <td><label>开始日期：</label></td>
                <td><input data-options="editable:false,showSeconds:false" required type="text" class="easyui-datebox beginDate" id=""  style="width:150px" name="beginDate"/></td>
            </tr>
            <tr>
                <td><label>续用天数：</label></td>
                <td><input required validtype="integer" type="text" class="easyui-textbox days text" name="days" style="width:150px" /></td>
            </tr>
            <tr>
                 <td><label>到期日期：</label></td>
                <td><input data-options="editable:false,showSeconds:false" type="text" class="easyui-textbox endDate" id="" name="" style="width:150px" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- 续用 E -->

<!-- 续费操作记录 S -->
<div id="school-xuListWin"><div id="dg-xu-list"></div></div>
<!-- 续费操作记录 E -->


</body>
</html>