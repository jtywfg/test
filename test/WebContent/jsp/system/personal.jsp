<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/layout/default_top.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>资源目录</title>
		<%@ include file="/layout/default.jsp" %>
		<link rel="stylesheet" href="<%=basePath%>static/css/customer/user.css" type="text/css" />
	</head>
	<body>
		<div class="baseWrap">
			<div class="baseArea" id="personal">
				<form class="easyui-form" method="post" data-options="url:'../../user/updateUser'" id="form_personal">
					<table style="width:500px;margin:0 auto">
						<tr>
							<td width="48">姓名</td>
							<td>
								<input class="easyui-textbox" name="realName" id="realName"
								 data-options="required:true,validType:'length[2,10]'" />
								 <input type="hidden" name="id" id="id" />
							</td>
							<td width="48">用户名</td>
							<td>
								<input class="easyui-textbox" name="userName" id="userName"
								 data-options="disabled:true,required:true,validType:'length[1,40]'" />
							</td>
						</tr>
						<tr>
							<td>手机号</td>
							<td>
								<input class="easyui-textbox" name="telphone" id="telphone"
								 data-options="required:true,validType:'length[11,11]'" />
							</td>
							<td>出生日期</td>
							<td>
								<input class="easyui-datebox" data-options="required:true" name="birthday" />
							</td>
						</tr>
						<tr>
							<td>银行</td>
							<td colspan="3">
								<input class="easyui-textbox" data-options="required:true,validType:'length[4,60]'" name="bank" />
							</td>
						</tr>
						<tr>
							<td>开户行</td>
							<td>
								<input class="easyui-textbox" data-options="required:true,validType:'length[3,60]'" name="bankAccount" />
							</td>
							<td>银行卡号</td>
							<td>
								<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="bankCard" />
							</td>
						</tr>
					</table>
				</form>
				<div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok_personal">确定</a>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/system/personal.js?v=<%=version%>"></script>
  </body>
</html>
