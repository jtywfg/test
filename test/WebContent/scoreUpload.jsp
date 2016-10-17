<%@ page contentType="text/html;charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta charset="utf-8">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="上传考生得分">
	<title>上传考生得分</title>

</head>
<body>
<div>
	<form action="http://192.168.223.200:8080/test/order/alipayapi" enctype="multipart/form-data"method="post" >
	UserName<input name="orderId"  type="text" value="1">
	 RealName<input name="RealName"  type="text" value="王金涛">
	 Password<input name="Password"  type="text" value="123456">
   	<input value="提交"  type="submit" >
    </form>
   
   

 </div>  
</body>
</html>