<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%
	Map user = (Map)request.getSession().getAttribute("user");
	System.out.println(user);
	//用户失效,跳转登录
	if(user==null){
		response.sendRedirect("login.html");
		return;
	} 
%>
<%@ include file="layout/default_top.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta charset="utf-8">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="">
	<title>管理系统</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/main.css?v=<%=version%>" />
    <link rel="stylesheet" href="jquery-easyui-1.5/themes/default/easyui.css" type="text/css"/>
    <link rel="stylesheet" href="jquery-easyui-1.5/themes/icon.css" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>static/lib/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>jquery-easyui-1.5/jquery.easyui.min.js?v=<%=version%>"></script>
    <script type="text/javascript" src="<%=basePath%>jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/Menu.js?v=<%=version%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/SubMenuController.js?v=<%=version%>"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/TabPage.js?v=<%=version%>"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/main.js?v=<%=version%>"></script>
    
</head> 
	<div id="desk">
   		<div id="topArea">
   			<p id="logo"></p>
   			<ul id="nav">
   			</ul>
   		</div>
   		<div id="midArea">
   			<div id="subMenuArea">
   			</div>
   			<div id="rightArea">
   				<div id="rightAreaTop">
   					<div id="tabArea">
   					</div>
   					<div id="systemTool">
   						<p id="out">退出系统</p>
   						<p id="updatePwd">修改密码</p>
   						<p id="username">欢迎您：<%=user.get("realName") %></p>
   					</div>
   				</div>
    			<div id="main">
    			</div>
   			</div>
   			<div id="slider"><span></span></div>
   		</div>
   		<div id="botArea">
   		</div>
    </div>
    <div id="editPasswordWin" class="easyui-dialog" style="width:400px;height:200px;"   
        data-options="title:'密码修改',closed:true,iconCls:'icon-save',resizable:true,modal:true">   				<!-- 修改密码窗体 -->
		<form id="editPassEForm" method="get" data-options="url:'user/changePassword'">
			<table class="baseTable" id="editPassTable">
			    <tr>
					<td><label for="用户名">用户名：</label></td>
					<td><input class="easyui-textbox" data-options="required:true,editable:false,value:'<%=user.get("userName")%>'" 
						id="userName" /></td>
			   </tr>
			   <tr>
			       <td><label for="新密码">新密码：</label></td>
				   <td><input id="newPassword" class="easyui-textbox" data-options="type:'password',required: true,validType:'length[6,20]'" /></td>
			   </tr>
			   <tr>
					<td><label for="确认密码">确认密码：</label></td>
					<td><input id="passWord" name="passWord" class="easyui-textbox" data-options="type:'password',required: true,validType:'length[6,20]'" />
						<input type="hidden" name="id" value="<%=user.get("id") %>" />
						<input type="hidden" name="type" value="1" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="customWindow"></div>
    <div id="customWindow2"></div>
</body>
</html>