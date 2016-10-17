<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
	Map user = (Map)request.getSession().getAttribute("user");
%>
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
			<div class="baseArea" id="user">
				<form class="easyui-form" method="post" data-options="url:'../../user/register'" id="form_user">
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
								 data-options="required:true,validType:'length[1,40]'" />
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
							<td>性別</td>
							<td colspan="3">
								<select class="easyui-combobox" name="sex" id="sex"
								data-options="required:true,editable:false,panelHeight:'auto'">
									<option value="1">男</option>   
									<option value="0">女</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>初始密码</td>
							<td>
								<input class="easyui-textbox" name="password"
								data-options="required:true,validType:'length[5,60]'" />
							</td>
							<td>银行</td>
							<td>
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
								<input class="easyui-textbox" data-options="required:true,validType:'length[16,19]'" name="bankCard" />
							</td>
						</tr>
						<tr>
							<td>身份证号</td>
							<td>
								<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="cardNum" />
							</td>
							<td>驾驶证号</td>
							<td>
								<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="drivingLicence" />
							</td>
						</tr>
						<tr>
							<!-- <td>车牌号</td>
							<td>
								<input class="easyui-textbox" data-options="required:true,validType:'length[5,60]'" name="carNum" />
							</td> -->
							<td>推荐人</td>
							<td colspan="3">
								<input class="easyui-textbox" data-options="disabled:true" name="tjRealName" value="<%=user.get("realName")%>"/>
							</td>
						</tr>
					</table>
				</form>
				<div style="text-align:center;padding:5px">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="btn_ok1">确定</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="btn_cancel1">取消</a>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../../static/js/page/customer/userAdd.js?v=<%=version%>"></script>
  </body>
</html>