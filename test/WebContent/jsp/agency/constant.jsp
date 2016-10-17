<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>比例设置</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/customer/user.css" type="text/css" />
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="constant">
				<form class="easyui-form" method="get" data-options="url:'../../constant/updateConstant'" id="form_constant">
					<table style="width:270px;margin:100px auto">
						<tr style="display: none">
							<td>一级比例</td>
							<td>
								<input class="easyui-numberbox" name="oneParam" style="height:32px;"
								data-options="min:0,max:1,precision:4,required:true,missingMessage:'必须填写0~1之间的数字'" />
								<input type="hidden" name="id" id="id" />
							</td>
						</tr>
						<tr style="display: none">
							<td>二级比例</td>
							<td>
								<input class="easyui-numberbox" name="twoParam" style="height:32px;"
								data-options="min:0,max:1,precision:4,required:true,missingMessage:'必须填写0~1之间的数字'" />
							</td>
						</tr>
						<tr>
							<td>推荐一人返率</td>
							<td>
								<input class="easyui-numberbox" name="onePerson" style="height:32px;"
								data-options="min:0,max:1,precision:4,required:true,missingMessage:'必须填写0~1之间的数字'" />
							</td>
						</tr>
						<tr>
							<td>推荐二人返率</td>
							<td>
								<input class="easyui-numberbox" name="twoPerson" style="height:32px;"
								data-options="min:0,max:1,precision:4,required:true,missingMessage:'必须填写0~1之间的数字'" />
							</td>
						</tr>
						<tr>
							<td>推荐三人返率</td>
							<td>
								<input class="easyui-numberbox" name="threePerson" style="height:32px;"
								data-options="min:0,max:1,precision:4,required:true,missingMessage:'必须填写0~1之间的数字'" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
							</td>
						</tr>
					</table>
				</form>
				<!-- <div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
				</div> -->
			</div>
		</div>
		
		<script type="text/javascript" src="<%=basePath%>static/js/page/agency/constant.js?v=<%=version%>"></script>
  </body>
</html>
