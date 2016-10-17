<%
	String path = request.getContextPath();
	String basePath ;
	if(request.getServerPort() == 80){
		basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
	}else{
		basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	}

	String version = "20160814";
	String title = "保险";
%>